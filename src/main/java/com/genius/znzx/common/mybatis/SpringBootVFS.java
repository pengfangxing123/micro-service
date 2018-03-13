package com.genius.znzx.common.mybatis;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.VFS;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class SpringBootVFS extends VFS {
	private static Logger log = Logger.getLogger(SpringBootVFS.class);
	private final ResourcePatternResolver resourceResolver;

	public SpringBootVFS() {
		this.resourceResolver = new PathMatchingResourcePatternResolver(getClass().getClassLoader());
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	protected List<String> list(URL url, String path) throws IOException {
		log.info("-----url:"+url.toString()+";path:"+path);
		Resource[] resources = resourceResolver.getResources("classpath*:" + path + "/**/*.class");
		List<String> resourcePaths = new ArrayList<String>();
		for (Resource resource : resources) {
			resourcePaths.add(preserveSubpackageName(resource.getURI(), path));
		}
		for(String s: resourcePaths){
			log.info("-----"+s);
		}
		return resourcePaths;
	}

	private static String preserveSubpackageName(final URI uri, final String rootPath) {
		final String uriStr = uri.toString();
		final int start = uriStr.indexOf(rootPath);
		return uriStr.substring(start);
	}

}
