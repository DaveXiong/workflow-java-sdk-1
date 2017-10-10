/**
 * 
 */
package com.consoleconnect.sdk.workflow.service.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.web.client.RestTemplate;


/**
 * @author dxiong
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-config-test.xml"})
public class RestClientTest {


  private MockRestServiceServer mockRestServer;

  @Autowired
  private RestClient client;

  @Autowired
  private RestTemplate restTemplate;


  @Before
  public void setUp() {
    mockRestServer = MockRestServiceServer.createServer(restTemplate);
    mockRestServer.expect(MockRestRequestMatchers.anything())
        .andExpect(MockRestRequestMatchers.method(HttpMethod.GET))
        .andExpect(MockRestRequestMatchers.header("token", "TEST"))
        .andRespond(MockRestResponseCreators.withSuccess());
  }

  @Test
  public void testRestServiceProviderCount() {
    //there is a dummy service provider
    assertThat(client.getServiceProviders()).hasSize(3);
  }


  @Test
  public void testConsoleHappyPath() {
    ResponseEntity<Object> response = client.execute(
        Request.build().withHeader("token", "TEST").withProvider(TestDefinitions.PROVIDER_CONSOLE)
            .withMethod(HttpMethod.GET).withEndPoint("/heartbeat"));

    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

    mockRestServer.verify();
  }

  @Test
  public void testServiceLayerHappyPath() {

    ResponseEntity<Object> response = client.execute(
        Request.build().withHeader("token", "TEST").withProvider(TestDefinitions.PROVIDER_SL)
            .withMethod(HttpMethod.GET).withEndPoint("/heartbeat"));


    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    mockRestServer.verify();
  }

  @Test(expected = RestServiceRuntimeException.class)
  public void testNotSupportServiceProvider() {
    try {
      client.execute(Request.build().withProvider("THIS_IS_A_NOT_SUPPORTED_SERVICE"));
    } catch (RestServiceRuntimeException ex) {
      assertThat(ex.getRequest()).isNotNull();
      assertThat(ex.getRequest().getServiceProvider()).isEqualTo("THIS_IS_A_NOT_SUPPORTED_SERVICE");
      throw ex;
    }
  }
}
