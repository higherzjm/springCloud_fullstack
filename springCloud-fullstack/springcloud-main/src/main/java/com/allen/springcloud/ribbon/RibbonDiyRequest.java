package com.allen.springcloud.ribbon;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;

import java.net.URI;
import java.util.Random;

public class RibbonDiyRequest implements HttpRequest {
	
	HttpRequest sourceRequest;
	
	public RibbonDiyRequest(HttpRequest sourceRequest) {
		this.sourceRequest = sourceRequest;
	}

	public HttpHeaders getHeaders() {
		return sourceRequest.getHeaders();
	}

	public HttpMethod getMethod() {
		return sourceRequest.getMethod();
	}

	public URI getURI() {
		try {
			Random random=new Random();
			int i=random.nextInt(10);
			//System.out.println("随机数:"+i);
			URI newUri=null;
			//更偏向hello2
			if (i>7){
				 newUri = new URI("http://localhost:8080/baseRibbonServer");
			}else {
				newUri = new URI("http://localhost:8081/baseRibbonServer");
			}

			//System.out.println("sourceRequest.getURI():"+sourceRequest.getURI());
			return newUri;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return sourceRequest.getURI();
	}

}
