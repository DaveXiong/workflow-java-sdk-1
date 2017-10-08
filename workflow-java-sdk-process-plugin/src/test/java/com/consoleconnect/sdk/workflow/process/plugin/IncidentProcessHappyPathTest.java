package com.consoleconnect.sdk.workflow.process.plugin;

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.execute;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.job;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.mockito.DelegateExpressions;
import org.camunda.bpm.extension.mockito.delegate.DelegateExecutionFake;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class IncidentProcessHappyPathTest extends AbstractProcessEngineTest {

  @ClassRule
  @Rule
  public static ProcessEngineRule rule = TestCoverageProcessEngineRuleBuilder.create().build();

  @BeforeClass
  public static void setup() {
    init(rule.getProcessEngine());
  }

  @Test
  @Deployment(resources = "incident.bpmn")
  public void testDeploymentIncidentProcess() {

  }

  @Test
  @Deployment(resources = "incident.bpmn")
  public void testIncidentHappyPath() throws Exception {
    DelegateExpressions.registerJavaDelegateMock("serviceTaskDelegate")
        .onExecutionThrowException(new ProcessEngineException("retry it"));

    ProcessInstance processInstance =
        processEngine().getRuntimeService().startProcessInstanceByKey("incident");

    assertThat(processInstance).isStarted().isNotEnded();
    assertThat(processInstance).hasNotPassed("task_job_example");

    Job job = job(processInstance);
    assertThat(job).isNotNull().hasActivityId("task_job_example");

    do {
      try {
        execute(job);
        break;
      } catch (Exception ex) {

      }
    } while (job(processInstance).getRetries() > 0);

    assertThat(job(processInstance)).hasRetries(0).hasExceptionMessage();

    DelegateExpressions.registerJavaDelegateMock("serviceTaskDelegate")
        .execute(new DelegateExecutionFake());

    execute(job);

    assertThat(processInstance).isEnded();

  }

}
