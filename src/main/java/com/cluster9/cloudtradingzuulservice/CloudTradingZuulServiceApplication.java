package com.cluster9.cloudtradingzuulservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
public class CloudTradingZuulServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudTradingZuulServiceApplication.class, args);
	}
	
//	@Bean
//	@LoadBalanced
	public RestTemplate getRestClient() {
	    RestTemplate restClient = new RestTemplate();
	    return restClient;
	}
	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){

	    RestTemplate template = restTemplateBuilder.requestFactory(new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
	                                                //.interceptors(logRestRequestInterceptor) //This is your custom interceptor bean
	                                                .messageConverters(new MappingJackson2HttpMessageConverter())
	                                                .build();
	    return template;


	}
}
