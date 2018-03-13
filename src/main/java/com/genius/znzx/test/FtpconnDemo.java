package com.genius.znzx.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class FtpconnDemo {
	private static String url;
	private static int port;
	private static String username;
	private static String password;
	private static String path;
	private static String filename;
	
	static{
		url="ftp-1.fuiou.com";
		port=9022;
		username="xhxtsftp";
		password="Srdy23sdr9Pwus";
		path="/xhxtsftp/upload";
	}
	
	public static List<String> connectFTP() {  
		 List<String> list = new ArrayList<String>();  
	        ChannelSftp sftp = null;  
	        Channel channel = null;  
	        Session sshSession = null;  
	        try {  
	            JSch jsch = new JSch();  
	            jsch.getSession(username, url, port);  
	            sshSession = jsch.getSession(username, url, port);  
	            sshSession.setPassword(password);  
	            Properties sshConfig = new Properties();  
	            sshConfig.put("StrictHostKeyChecking", "no");//不进行公钥确认
	            sshSession.setConfig(sshConfig);  
	            sshSession.connect();  
	            channel = sshSession.openChannel("sftp");  
	            channel.connect();    
	            sftp = (ChannelSftp) channel;  
	            Vector<?> vector = sftp.ls(path);  
	            for (Object item:vector) {  
	                LsEntry entry = (LsEntry) item;  
	                System.out.println(entry.getFilename());  
	            }  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            closeChannel(sftp);  
	            closeChannel(channel);  
	            closeSession(sshSession);  
	        }  
	        return list;  
    } 
	
	private static void closeChannel(Channel channel) {  
        if (channel != null) {  
            if (channel.isConnected()) {  
                channel.disconnect();  
            }  
        }  
	}  
	  
    private static void closeSession(Session session) {  
        if (session != null) {  
            if (session.isConnected()) {  
                session.disconnect();  
            }  
        }  
    }  

	public static void main(String[] args) {	
		connectFTP();
	}
}
