/**
 * 
 */
package com.consoleconnect.sdk.workflow.service.rest;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author dxiong
 *
 */
public class RestServiceProvider {

  Logger log = LoggerFactory.getLogger(this.getClass());


  private String provider;

  @Autowired
  private RestTemplate restTemplate;

  @Autowired
  private Environment env;
  
  public RestServiceProvider(String provider) {
    this.provider = provider;
  }

  @PostConstruct
  public void initi() {
    log.info("created rest service provider:{}", provider);
  }



  public String getProvider() {
    return provider;
  }



  public RestTemplate getRestTemplate() {
    return restTemplate;
  }



  public String getBaseUrl() {
    return env.getProperty(String.format("rest.%s.baseUrl", provider));

  }



  public ResponseEntity<Object> execute(Request request) {
    String url = getBaseUrl() + request.getEndPoint();

    log.info("{} {}", request.getMethod(), url);

    HttpHeaders headers = new HttpHeaders();
    if (request.getHeaders() != null) {
      headers.setAll(request.getHeaders());
    }
    
    return getRestTemplate().exchange(url, request.getMethod(),
        new HttpEntity<String>(request.getBody(), headers), Object.class);

  }

  @Override
  public String toString() {
    return "RestServiceProvider [provider=" + provider + "]";
  }


}
