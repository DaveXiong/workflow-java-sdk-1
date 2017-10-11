package com.consoleconnect.sdk.workflow.process.aws;

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.execute;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.job;
import static org.camunda.bpm.extension.mockito.CamundaMockito.autoMock;


import java.util.UUID;

import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class RestServiceTest{


  @ClassRule
  @Rule
  public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();


  @Before
  public void setUp() {
    init(rule.getProcessEngine());
  }

  /**
   * Just tests if the process definition is deployable.
   */
  @Test
  @Deployment(resources = "restServiceProcess.bpmn")
  public void testParsingAndDeployment() {
    // nothing is done here, as we just want to check for exceptions during
    // deployment
  }



  @Test
  @Deployment(resources = "restServiceProcess.bpmn")
  public void testHappyPath() throws Exception {

    autoMock("restServiceProcess.bpmn");

    // start process
    ProcessInstance processInstance = processEngine().getRuntimeService().startProcessInstanceByKey(
        "restServiceProcess", UUID.randomUUID().toString(),
        BpmnAwareTests.withVariables("vlan", true, "interconnectId", true));

    // process is started
    assertThat(processInstance).isStarted().isNotEnded();
    
    assertAndExecuteJob(processInstance,"update_connection");

    assertThat(processInstance).isEnded();
  }


  private void assertAndExecuteJob(ProcessInstance processInstance, String activityId) {
    assertThat(processInstance).hasNotPassed(activityId);
    Job job = job(processInstance);
    assertThat(job).isNotNull().hasActivityId(activityId);
    execute(job);
    assertThat(processInstance).hasPassedInOrder(activityId);
  }

}
