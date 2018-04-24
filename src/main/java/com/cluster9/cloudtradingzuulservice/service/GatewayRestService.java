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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
public class GatewayRestService {
	@Autowired
	private RestTemplate rt;
	private static Logger log = LoggerFactory.getLogger(RestTemplate.class);
	// resources: General helper to easily create a wrapper for a collection of entities
	// parameterizedTypeReference: captures and passes a generic type through http+json
	private String urlGETList = "http://trading-company-service/companylist";
	
	// this method calls a Spring restfull service built from the entity annotations
	@RequestMapping(value="/names")
	public Collection<Company>  listCompanies(){
		ParameterizedTypeReference<Resources<Company>> respType = 
				new ParameterizedTypeReference<Resources<Company>>() {};
		System.out.println("typename :" + respType.getType().getTypeName()); 
		
		ResponseEntity<Resources<Company>> response = 
				rt.exchange("http://trading-company-service/companies", HttpMethod.GET, null, respType);
		System.out.println("response getBody" + response.getBody().getContent().toString());
		System.out.println("response getStatusCodeValue" + response.getStatusCodeValue());
		System.out.println("response getHeaders" + response.getHeaders());
		System.out.println("response hasBody" + response.hasBody());
		return response.getBody().getContent();

	}
	
	// this method calls a Srping restfull controller built from repo  interface 
	@RequestMapping(value="/list")
	public Object[] listAll(){
		ResponseEntity<Object[]> responseEntity = rt.getForEntity(urlGETList, Object[].class);
		Object[] objects = responseEntity.getBody();
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();
		
		System.out.println("responseEntity getBody " + responseEntity.getBody());
		System.out.println("responseEntity getStatusCodeValue " + statusCode);
		System.out.println("responseEntity getHeaders " + contentType);
		System.out.println("arrayList of objects: " + objects.toString());
		
		return objects;
	}
	
	// added a nested Companies class to be used by the Jackson converter
	@RequestMapping(value="/company")
	public Company  getRespEntity(){
		ResponseEntity<Company> responseEntity = rt.getForEntity("http://trading-company-service/companies" + "/1", Company.class);
		Company c = responseEntity.getBody();
		MediaType contentType = responseEntity.getHeaders().getContentType();
		HttpStatus statusCode = responseEntity.getStatusCode();
		
		System.out.println("responseEntity getBody " + responseEntity.getBody().getCompanyName());
		System.out.println("responseEntity getStatusCodeValue " + statusCode);
		System.out.println("responseEntity getHeaders " + contentType);
		
		System.out.println("arrayList of company: " + c.toString());
		
		return c;
	}
	
	@RequestMapping(value="/test")
	public String test(){
		ParameterizedTypeReference<Resources<String>> listOfStrings = 
				new ParameterizedTypeReference<Resources<String>>() {};
		System.out.println("typename :" + listOfStrings.getType().getTypeName()); 
		
		ResponseEntity<Resources<String>> response = 
				rt.exchange("http://trading-company-service/spec", HttpMethod.GET, null, listOfStrings);
		System.out.println("response getBody: " + response.getBody());
		System.out.println("response getStatusCodeValue: " + response.getStatusCodeValue());
		System.out.println("response getHeaders; " + response.getHeaders());
		log.info("RestTemplate: " + String.format
				("StatusCode %s ;Body is  %s",response.getStatusCodeValue() ,response.getBody()));
		return response.getBody().toString();
	}

}


// added from StackOverflow: 
@JsonIgnoreProperties(ignoreUnknown = true)
class Companies{
	public List<Company> getList() {
		return list;
	}

	public void setList(List<Company> list) {
		this.list = list;
	}

	@JsonProperty("companies")
	private List<Company> list;
}

@JsonIgnoreProperties(ignoreUnknown = true)
class Company  {
	
	public Company(Long id, String companyName) {
		super();
		this.id = id;
		this.companyName = companyName;
	}
	private Long id;
	private String companyName;
	
	public Company(Long id) {
		super();
		this.id = id;
	}
	public Company() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public Company(String companyName) {
		super();
		this.companyName = companyName;
	}

}
