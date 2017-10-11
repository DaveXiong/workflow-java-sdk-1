/**
 * 
 */
package com.consoleconnect.sdk.workflow.service.rest;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

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

  Logger log = Logger.getLogger(this.getClass().getName());


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
    log.log(Level.INFO, "created rest service provider:{0}", provider);
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

    log.log(Level.INFO, "{0} {1}", new Object[] {request.getMethod(), url});

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
