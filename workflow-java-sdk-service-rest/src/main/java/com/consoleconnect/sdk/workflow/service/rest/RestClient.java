/**
 * 
 */
package com.consoleconnect.sdk.workflow.service.rest;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * @author dxiong
 *
 */
@Component
public class RestClient {

  private static final Logger LOG = Logger.getLogger(RestClient.class.getName());



  @Autowired
  private List<RestServiceProvider> serviceProviders;

  public ResponseEntity<Object> execute(Request request) {
    for (RestServiceProvider provider : serviceProviders) {
      if (provider.getProvider().equalsIgnoreCase(request.getServiceProvider())) {
        LOG.log(Level.INFO, "Service provider:{0} for {1}", new Object[] {provider, request});
        return provider.execute(request);
      }
    }

    String error = String.format("No service provider for %s,available providers:%s",
        request.getServiceProvider(), serviceProviders.stream()
            .map(RestServiceProvider::getProvider).collect(Collectors.toList()));
    LOG.log(Level.SEVERE, error);
    throw new RestServiceRuntimeException(error, request);
  }


  public List<RestServiceProvider> getServiceProviders() {
    return Collections.unmodifiableList(serviceProviders);
  }


}
