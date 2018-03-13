package com.genius.znzx.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

/**
 * httpclient 工具类
 * @author fangxing.peng
 *
 */
@Service
public class HttpClientUtils {
	private static Logger log = Logger.getLogger(HttpClientUtils.class);
	
	private static ThreadSafeClientConnManager cm = null;
	static {
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		//schemeRegistry.register(new Scheme("https", MySSLSocketFactory.getInstance(), 443)); 
		cm = new ThreadSafeClientConnManager(schemeRegistry);
		int maxTotal = 500;		
		int defaultMaxConnection = 400;
		
		try {
			cm.setMaxTotal(maxTotal);
		} catch (NumberFormatException e) {
			log.error("Key[httpclient.max_total] Not Found in systemConfig.properties",e);
		}
		try {
			
			cm.setDefaultMaxPerRoute(defaultMaxConnection);
		} catch (NumberFormatException e) {
			log.error("Key[httpclient.default_max_connection] Not Found in systemConfig.properties",e);
		}
	}
	
	/**
	 * 获取HttpClient
	 * @return
	 */
	public static HttpClient getHttpClient() {
		HttpParams params = new BasicHttpParams();
		params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION,HttpVersion.HTTP_1_1);
		
		//平安环境设置代理
		/*String proxyHost = "10.17.171.11";
		int proxyPort = 8080;		
		HttpHost proxy = new HttpHost(proxyHost,proxyPort);
		params.setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);*/
		//平安环境设置代理结束,其它地方关掉
		
		//请求超时
		int CONNECTION_TIMEOUT=180000; //可以比datacenterJob设置的大一些
		params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, CONNECTION_TIMEOUT); // 3000ms
		//读取超时 
		int SO_TIMEOUT=180000;
		params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT); 
		long CONN_MANAGER_TIMEOUT = 30000; //该值就是连接不够用的时候等待超时时间，一定要设置，而且不能太大 ()
		params.setLongParameter(ClientPNames.CONN_MANAGER_TIMEOUT, CONN_MANAGER_TIMEOUT);
		
		DefaultHttpClient httpClient = new DefaultHttpClient(cm, params);
		// 可以自动重连  
		HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception,
			    int executionCount,HttpContext context) {
				    // 设置恢复策略，在发生异常时候将自动重试3次    
			        if (executionCount >= 3) {
			        	System.out.println("retry request1:"+executionCount);
			            // 如果超过最大重试次数，那么就不要继续了
			            return false;
			        }
			        if (exception instanceof NoHttpResponseException) {
			        	System.out.println("retry request2:"+executionCount);
			            // 如果服务器丢掉了连接，那么就重试
			            return true;
			        }
			        if (exception instanceof SSLHandshakeException) {
			        	System.out.println("retry request3:"+executionCount);
			            // 不要重试SSL握手异常
			            return false;
			        }
			        HttpRequest request = (HttpRequest) context.getAttribute(ExecutionContext.HTTP_REQUEST);  
			        boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);  
			        if (idempotent)  
			        {  
			        	// 如果请求是幂等的，就再次尝试 
			        	System.out.println("retry request4:"+executionCount);
			            return true;  
			        }  
			        System.out.println("retry request:"+executionCount);
			        return false;
			    }
		};
		httpClient.setHttpRequestRetryHandler(myRetryHandler);
		return httpClient;
	} 
	
	/**
	 * httpclient,默认gb2312编码
	 * @param url
	 * @return
	 */
	public static String getDataByUrl(String url){
		String data="";
		long l1 = System.currentTimeMillis();		
		HttpClient client = null;
		HttpGet get = null;
		HttpResponse response = null;
		
		try{	
			client =getHttpClient();
			get=new HttpGet(url);
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();				
				InputStream inputStream = entity.getContent();			
				data=parseNoGZipRes(inputStream);
				if (inputStream!=null) {inputStream.close();}		
			}
			//System.out.println("Time consuming:" + (System.currentTimeMillis() - l1)+"ms");	
		
		}catch(ClientProtocolException e){
			log.error("url error:"+e); 
			e.printStackTrace();
		}catch(IOException ex){
			log.error("url error:"+ex); 
			ex.printStackTrace();
		}catch(Exception exx){
			log.error("url error:"+exx); 
			exx.printStackTrace();
		}finally { 			  
	        try {   
	            if (response != null) {   
	                response.getEntity().getContent().close();   
	            }   
	        } catch (IllegalStateException e) {   
	        	log.error("url error:"+e);   
	        } catch (IOException e) {	        	
	        	log.error("url error:"+e);   
	        } 
		}
		//System.out.println("data="+data);	
		return data;
	}
	
	/**
	 * httpclient Get请求 
	 * @param url
	 * @param 自定义编码
	 * @return
	 */
	public static String getDataByUrlAndChart(String url,String chartSet){
		String data="";
		long l1 = System.currentTimeMillis();		
		HttpClient client = null;
		HttpGet get = null;
		HttpResponse response = null;
		
		try{	
			client = getHttpClient();
			get=new HttpGet(url);
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity entity = response.getEntity();				
				InputStream inputStream = entity.getContent();			
				data=parseDataByChartSet(inputStream,chartSet);
				if (inputStream!=null) {inputStream.close();}		
			}
			//System.out.println("Time consuming:" + (System.currentTimeMillis() - l1)+"ms");	
		
		}catch(ClientProtocolException e){
			log.error("url error:"+e); 
			e.printStackTrace();
		}catch(IOException ex){
			log.error("url error:"+ex); 
			ex.printStackTrace();
		}catch(Exception exx){
			log.error("url error:"+exx); 
			exx.printStackTrace();
		}finally { 			  
	        try {   
	            if (response != null) {   
	                response.getEntity().getContent().close();   
	            }   
	        } catch (IllegalStateException e) {   
	        	log.error("url error:"+e);   
	        } catch (IOException e) {	        	
	        	log.error("url error:"+e);   
	        } 
		}
		//System.out.println("data="+data);	
		return data;
	}
	
	
	
	public static String parseDataByChartSet(InputStream inputStream,String chartSet) throws IOException {
		StringBuilder strBuf = new StringBuilder();
		InputStreamReader isr = new InputStreamReader(inputStream, chartSet);
		BufferedReader br = new BufferedReader(isr);
		try{
			String line = null;
			while ((line = br.readLine()) != null) {
				strBuf.append(line);
			}
		}catch(Exception e){
			log.error("parseGZipRes error:"+e);
			e.printStackTrace();
		}finally{
			if (isr!=null){isr.close();} 
			if (inputStream!=null) {inputStream.close();}
		}
		return strBuf.toString();
	}
	
	/**
	 * 非GZip压缩后的http响应报文
	 * 
	 * @param req
	 *            HTTP请求
	 * @return 解压结果
	 * @throws IOException
	 */
	public static String parseNoGZipRes(InputStream inputStream) throws IOException {
		StringBuilder strBuf = new StringBuilder();
		InputStreamReader isr = new InputStreamReader(inputStream, "GB2312");
		BufferedReader br = new BufferedReader(isr);
		try{
			String line = null;
			while ((line = br.readLine()) != null) {
				strBuf.append(line);
			}
		}catch(Exception e){
			log.error("parseGZipRes error:"+e);
			e.printStackTrace();
		}finally{
			if (isr!=null){isr.close();} 
			if (inputStream!=null) {inputStream.close();}
		}
		return strBuf.toString();
	}
}
