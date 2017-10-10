/**
 * 
 */
package com.consoleconnect.sdk.workflow.service.rest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;

/**
 * @author dxiong
 *
 */
public class RequestTest {

  private Request request;

  @Before
  public void setUp() {
    request = Request.build();
  }

  @Test
  public void testWithMethod() {
    request.withMethod(HttpMethod.GET);
    assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);

    request.withMethod(HttpMethod.POST);
    assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);

    request.withMethod(HttpMethod.GET.toString());
    assertThat(request.getMethod()).isEqualTo(HttpMethod.GET);

    request.withMethod(HttpMethod.POST.toString());
    assertThat(request.getMethod()).isEqualTo(HttpMethod.POST);

  }

  @Test(expected = IllegalArgumentException.class)
  public void testWithMethodException() {
    request.withMethod("NOT SUPPORTED METHOD");
  }

  @Test
  public void testWithBody() {
    request.setBody(null);
    assertThat(request.getBody()).isNull();

    request.setBody("this is a body");
    assertThat(request.getBody()).isEqualTo("this is a body");
  }


  @Test
  public void testWithHeaders() {
    request.setHeaders(null);
    assertThat(request.getHeaders()).isNull();

    request.setHeaders(Collections.emptyMap());
    assertThat(request.getHeaders()).isEmpty();

    Map<String, String> headers = new HashMap<String, String>();
    headers.put("uuid", UUID.randomUUID().toString());
    request.setHeaders(headers);
    assertThat(request.getHeaders()).hasSameSizeAs(headers);
    assertThat(request.getHeaders().get("uuid")).isSameAs(headers.get("uuid"));
  }
}
