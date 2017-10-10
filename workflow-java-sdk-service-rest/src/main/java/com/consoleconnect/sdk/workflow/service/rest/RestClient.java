/**
 * 
 */
package com.consoleconnect.sdk.workflow.service.rest;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author dxiong
 *
 */
@Component
public class RestClient {

  private static final Logger LOG = LoggerFactory.getLogger(RestClient.class);



  @Autowired
  private List<RestServiceProvider> serviceProviders;

  public ResponseEntity<Object> execute(Request request) {
    for (RestServiceProvider provider : serviceProviders) {
      if (provider.getProvider().equalsIgnoreCase(request.getServiceProvider())) {
        LOG.info("Service provider:{} for {}", provider, request);
        return provider.execute(request);
      }
    }



    String error = String.format("No service provider for %s,available providers:%s",
        request.getServiceProvider(), serviceProviders.stream()
            .map(RestServiceProvider::getProvider).collect(Collectors.toList()));
    LOG.error(error);
    throw new RestServiceRuntimeException(error, request);
  }


  public List<RestServiceProvider> getServiceProviders() {
    return Collections.unmodifiableList(serviceProviders);
  }


}
