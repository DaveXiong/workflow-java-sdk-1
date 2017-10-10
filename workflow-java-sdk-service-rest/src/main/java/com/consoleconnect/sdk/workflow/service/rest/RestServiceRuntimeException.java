/**
 * 
 */
package com.consoleconnect.sdk.workflow.service.rest;

/**
 * @author dxiong
 *
 */
public class RestServiceRuntimeException extends RuntimeException {

  /**
   * 
   */
  private static final long serialVersionUID = -8147070477340275335L;

  private final transient Request request;

  /**
   * 
   */
  public RestServiceRuntimeException(String message, Request request) {
    super(message);
    this.request = request;
  }

  public Request getRequest() {
    return request;
  }



}
