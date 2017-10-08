package com.consoleconnect.sdk.workflow.process.plugin;

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;

import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class UserTaskProcessHappyPathTest extends AbstractProcessEngineTest {

  @ClassRule
  @Rule
  public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

  @BeforeClass
  public static void setup() {
    init(rule.getProcessEngine());
  }

  @Test
  @Deployment(resources = "usertask.bpmn")
  public void testDeploymentUserTaskProcess() {

  }

  @Test
  @Deployment(resources = "usertask.bpmn")
  public void testUserTaskHappyPath() {
    ProcessInstance processInstance =
        processEngine().getRuntimeService().startProcessInstanceByKey("usertaskprocess");
    assertThat(processInstance).task("Task_DoSomething");
    complete(task());
    assertThat(processInstance).isEnded();
  }
}
