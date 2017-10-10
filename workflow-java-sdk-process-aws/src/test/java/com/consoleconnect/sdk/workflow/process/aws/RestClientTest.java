package com.consoleconnect.sdk.workflow.process.aws;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.consoleconnect.sdk.workflow.service.rest.RestClient;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:testContext.xml")
public class RestClientTest extends AbstractRuleEngineUnitTest {


  @Autowired
  private RestClient client;

  @Test
  public void test() {
    assert (client.getServiceProviders().size() == 3);
  }
}
