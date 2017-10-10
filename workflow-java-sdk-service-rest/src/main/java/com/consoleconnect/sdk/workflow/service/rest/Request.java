package com.consoleconnect.sdk.workflow.service.rest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpMethod;

public class Request {


  String serviceProvider;
  HttpMethod method = HttpMethod.GET;
  String endPoint = "";
  Map<String, String> headers = Collections.synchronizedMap(new HashMap<String, String>());
  String body = null;


  private Request() {

  }

  public static Request build() {
    return new Request();
  }


  public Request withProvider(String provider) {
    this.setServiceProvider(provider);
    return this;
  }

  public Request withMethod(String method) {
    this.setMethod(HttpMethod.valueOf(method));
    return this;
  }
  
  public Request withMethod(HttpMethod method) {
    this.setMethod(method);
    return this;
  }

  public Request withEndPoint(String endPoint) {
    this.setEndPoint(endPoint);
    return this;
  }

  public Request withHeaders(Map<String, String> headers) {
    this.setHeaders(headers);
    return this;
  }

  public Request withHeader(String key, String value) {
    headers.put(key, value);
    return this;
  }

  public Request withBody(String body) {
    this.setBody(body);
    return this;
  }

  public String getServiceProvider() {
    return serviceProvider;
  }

  public void setServiceProvider(String serviceProvider) {
    this.serviceProvider = serviceProvider;
  }

  public HttpMethod getMethod() {
    return method;
  }

  public void setMethod(HttpMethod method) {
    this.method = method;
  }

  public String getEndPoint() {
    return endPoint;
  }

  public void setEndPoint(String endPoint) {
    this.endPoint = endPoint;
  }

  public Map<String, String> getHeaders() {
    return headers;
  }

  public void setHeaders(Map<String, String> headers) {
    this.headers = headers;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String toString() {
    return "RestInputParameters [serviceProvider=" + serviceProvider + ", method=" + method
        + ", endPoint=" + endPoint + ", headers=" + headers + ", body=" + body + "]";
  }

}
