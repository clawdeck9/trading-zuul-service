package com.cluster9.cloudtradingzuulservice.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class GatewayRestService {
	@Autowired
	private RestTemplate rt;
	
	@RequestMapping(value="/names")
	public Collection<Company> listCompanies(){
		ParameterizedTypeReference<Resource<Company>> responseType=
				new ParameterizedTypeReference<Resource<Company>>() {};
		return (Collection<Company>) rt.exchange("http;//trading-company-service/companies", HttpMethod.GET, null, responseType)
				.getBody().getContent();
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