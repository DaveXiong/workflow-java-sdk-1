/**
 * 
 */
package com.consoleconnect.sdk.workflow.service.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author dxiong
 *
 */
@Component
public class DummyRestServiceProvider extends RestServiceProvider {

  /**
   * 
   */
  public DummyRestServiceProvider() {
    super("dummy");
  }

  @Override
  public String getBaseUrl() {
    return "http://localhost:8080";
  }

  @Override
  public ResponseEntity<Object> execute(Request request) {
    String url = getBaseUrl() + request.getEndPoint();
    log.info("{} {}", request.getMethod(), url);
    return ResponseEntity.ok().body(new Object());
  }
}
