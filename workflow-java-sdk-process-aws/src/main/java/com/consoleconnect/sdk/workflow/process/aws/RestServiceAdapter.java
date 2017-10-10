/**
 * 
 */
package com.consoleconnect.sdk.workflow.process.aws;

import java.util.Map;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.consoleconnect.sdk.workflow.service.rest.Request;
import com.consoleconnect.sdk.workflow.service.rest.RestClient;

/**
 * @author dxiong
 *
 */
@Component("restServiceDelegate")
public class RestServiceAdapter implements JavaDelegate {

  private static final Logger LOGGER = Logger.getLogger(RestServiceAdapter.class.getName());


  @Autowired
  private RestClient client;



  public static final String INPUT_METHOD = "method";
  public static final String INPUT_ENDPOINT = "endpoint";
  public static final String INPUT_HEADERS = "headers";
  public static final String INTPUT_BODY = "body";
  public static final String SERVICE_PROVIDER = "service";

  /*
   * (non-Javadoc)
   * 
   * @see org.camunda.bpm.engine.delegate.JavaDelegate#execute(org.camunda.bpm.engine.delegate.
   * DelegateExecution)
   */
  @Override
  public void execute(DelegateExecution execution) throws Exception {

    Map<String, Object> variables = execution.getVariables();

    String provider = (String) variables.get(SERVICE_PROVIDER);
    String method = (String) variables.getOrDefault(INPUT_METHOD, HttpMethod.GET);
    String endpoint = (String) variables.get(INPUT_ENDPOINT);
    @SuppressWarnings("unchecked")
    Map<String, String> headers = (Map<String, String>) variables.get(INPUT_HEADERS);
    String body = (String) variables.get(INTPUT_BODY);

    Request request = Request.build().withProvider(provider).withMethod(method)
        .withEndPoint(endpoint).withHeaders(headers).withBody(body);

    LOGGER.info(request.toString());


    ResponseEntity<Object> response = client.execute(request);

    LOGGER.info(response.toString());

  }



}
