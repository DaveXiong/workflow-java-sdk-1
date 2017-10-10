/**
 * 
 */
package com.consoleconnect.sdk.workflow.service.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.consoleconnect.sdk.workflow.service.rest.ConsoleRestServiceImplTest.MockConfiguration;
import com.consoleconnect.sdk.workflow.service.rest.TestDefinitions.HeartBeat;


/**
 * @author dxiong
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MockConfiguration.class})
@TestPropertySource("classpath:rest.properties")
public class ConsoleRestServiceImplTest {

  private static RestTemplate restTemplate = Mockito.mock(RestTemplate.class);


  static class MockConfiguration {
    @Bean
    public RestTemplate restTemplate() {
      return restTemplate;
    }

    @Bean
    RestServiceProvider consoleRestService() {
      return new RestServiceProvider("console");
    }
  }

  @Autowired
  private RestServiceProvider consoleRestService;


  @Before
  public void setUp() {
    Mockito.reset(restTemplate);
  }


  @Test
  public void testGetHeartbeat_200() {
    Mockito
        .when(restTemplate.exchange(Mockito.anyString(), Mockito.<HttpMethod>any(),
            Mockito.<HttpEntity<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(ResponseEntity.ok().body(new HeartBeat("0.0.1",100L)));

    ResponseEntity<Object> response =
        consoleRestService.execute(Request.build().withProvider(TestDefinitions.PROVIDER_CONSOLE)
            .withMethod(HttpMethod.GET).withEndPoint("/heartbeat"));

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
    assertThat(response.getBody()).isInstanceOf(HeartBeat.class);
    
    Mockito.verify(restTemplate).exchange(Mockito.anyString(), Mockito.<HttpMethod>any(),
        Mockito.<HttpEntity<?>>any(), Mockito.<Class<?>>any());
  }

  @Test
  public void testGetHeartbeat_400() {
    Mockito
        .when(restTemplate.exchange(Mockito.anyString(), Mockito.<HttpMethod>any(),
            Mockito.<HttpEntity<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(ResponseEntity.badRequest().body(new Object()));

    ResponseEntity<Object> response =
        consoleRestService.execute(Request.build().withProvider(TestDefinitions.PROVIDER_CONSOLE)
            .withMethod(HttpMethod.PUT).withEndPoint("/heartbeat"));

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode().is4xxClientError()).isTrue();
    Mockito.verify(restTemplate).exchange(Mockito.anyString(), Mockito.<HttpMethod>any(),
        Mockito.<HttpEntity<?>>any(), Mockito.<Class<?>>any());
  }
}
