package com.hybird.example.cmmn;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
	
	@Bean
	public RestTemplate restTemplate() {
		PoolingHttpClientConnectionManager mng = new PoolingHttpClientConnectionManager();
		mng.setDefaultMaxPerRoute(50);
		mng.setMaxTotal(500);
		HttpClientBuilder bld = HttpClientBuilder.create().setConnectionManager(mng).disableCookieManagement();
		HttpComponentsClientHttpRequestFactory fct = new HttpComponentsClientHttpRequestFactory();
		fct.setHttpClient(bld.build());
		fct.setConnectTimeout(1000);
		fct.setReadTimeout(1000);
		return new RestTemplate(fct);
	}
	
}
