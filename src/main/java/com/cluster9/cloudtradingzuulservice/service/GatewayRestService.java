package com.cluster9.cloudtradingzuulservice.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@RestController
public class GatewayRestService {
	@Autowired
	private RestTemplate rt;
	
	@RequestMapping(value="/names")
	public Collection<Company>  listCompanies(){
		ParameterizedTypeReference<Resources<Company>> listOfStrings = new ParameterizedTypeReference<Resources<Company>>() {};
		ResponseEntity<Resources<Company>> response = 
				rt.exchange("http://trading-company-service/companies", HttpMethod.GET, null, listOfStrings);
		//System.out.println("response" + response.getBody().getContent());
		return response.getBody().getContent();
		
	}

}

class Company{
	private Long id;
	private String name;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
		
}