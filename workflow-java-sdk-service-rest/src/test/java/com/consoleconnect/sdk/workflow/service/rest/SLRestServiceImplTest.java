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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.consoleconnect.sdk.workflow.service.rest.SLRestServiceImplTest.MockConfiguration;


/**
 * @author dxiong
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {MockConfiguration.class})
@TestPropertySource("classpath:rest.properties")
public class SLRestServiceImplTest {

  private static RestTemplate restTemplate = Mockito.mock(RestTemplate.class);


  static class MockConfiguration {
    @Bean
    public RestTemplate restTemplate() {
      return restTemplate;
    }

    @Bean
    RestServiceProvider consoleRestService() {
      return new RestServiceProvider("sl");
    }
  }

  @Autowired
  private RestServiceProvider restService;



  @Before
  public void setUp() {
    Mockito.reset(restTemplate);
  }

  @Test
  public void testGetHeartbeat_200() {

    Mockito
        .when(restTemplate.exchange(Mockito.anyString(), Mockito.<HttpMethod>any(),
            Mockito.<HttpEntity<?>>any(), Mockito.<Class<Object>>any()))
        .thenReturn(ResponseEntity.ok(new Object()));
    ResponseEntity<Object> response =
        restService.execute(Request.build().withProvider(TestDefinitions.PROVIDER_SL)
            .withMethod(HttpMethod.GET).withEndPoint("/heartbeat"));

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();

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
        restService.execute(Request.build().withProvider(TestDefinitions.PROVIDER_SL)
            .withMethod(HttpMethod.GET).withEndPoint("/heartbeat"));

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode().compareTo(HttpStatus.BAD_REQUEST)).isZero();

    Mockito.verify(restTemplate).exchange(Mockito.anyString(), Mockito.<HttpMethod>any(),
        Mockito.<HttpEntity<?>>any(), Mockito.<Class<?>>any());
  }
}
