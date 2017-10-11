/**
 * 
 */
package com.consoleconnect.sdk.workflow.service.rest;

import java.util.logging.Level;

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
    log.log(Level.INFO, "{0} {1}", new Object[] {request.getMethod(), url});
    return ResponseEntity.ok().body(new Object());
  }
}
