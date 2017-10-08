package com.consoleconnect.sdk.workflow.process.aws;

import static org.assertj.core.api.Assertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.processEngine;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.claim;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.execute;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.job;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;
import static org.camunda.bpm.extension.mockito.CamundaMockito.autoMock;

import java.util.UUID;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.runtime.Incident;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests;
import org.camunda.bpm.extension.mockito.DelegateExpressions;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class ProvisionAwsConnectionTest extends AbstractRuleEngineUnitTest
    implements ProvisionAwsConnectionDefinition {


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
  // @Test
  // @Deployment(resources = "provisionAwsConnection.bpmn")
  public void testParsingAndDeployment() {
    // nothing is done here, as we just want to check for exceptions during
    // deployment
  }

  private void assertAndExecuteJob(ProcessInstance processInstance, String activityId) {
    assertThat(processInstance).hasNotPassed(activityId);
    Job job = job(processInstance);
    assertThat(job).isNotNull().hasActivityId(activityId);
    execute(job);
    assertThat(processInstance).hasPassedInOrder(activityId);
  }

  private void assertAndExecuteJob(ProcessInstance processInstance, String... activityIds) {
    for (String activityId : activityIds) {
      assertAndExecuteJob(processInstance, activityId);
    }
  }



  @Test
  @Deployment(resources = BPMN)
  public void testHappyPath() throws Exception {
    // mock all EL in the process
    autoMock(BPMN);


    // start process
    ProcessInstance processInstance = processEngine().getRuntimeService().startProcessInstanceByKey(
        PROCESS_KEY, UUID.randomUUID().toString(),
        BpmnAwareTests.withVariables("vlan", true, "interconnectId", true));

    // process is started
    assertThat(processInstance).isStarted().isNotEnded();

    assertAndExecuteJob(processInstance, JOB_UPDATE_CONNECTION_PROCESSING,
        JOB_SEND_CREATE_L2_CONNECTION);


    // WAIT FOR JOB_WAIT_CREATE_L2_CONNECTION
    assertThat(processInstance).isWaitingFor(MESSAGE_CREATE_L2_CONNECTION);
    processEngine().getRuntimeService().correlateMessage(MESSAGE_CREATE_L2_CONNECTION,
        processInstance.getBusinessKey());
    assertThat(processInstance).hasPassedInOrder(JOB_WAIT_CREATE_L2_CONNECTION);

    assertAndExecuteJob(processInstance, JOB_PERSIST_L2_CONNECTION, JOB_SELECT_INTERCONNECT,
        JOB_ALLOCATE_CONNECTION_ON_INTERCONNECT, JOB_PERSIST_CONNECTION,
        JOB_UPDATE_CONNECTION_PENDING_ACCEPTANCE);
    assertThat(processInstance).isEnded();
  }

  private void assertAndCompleteUserTask(ProcessInstance processInstance, String activityId) {
    assertThat(task()).hasDefinitionKey(USER_TASK_SELECT_INTERCONNECT).isNotAssigned();
    claim(task(), "fozzie");
    assertThat(task()).isAssignedTo("fozzie");
    complete(task());
    assertThat(processInstance).hasPassedInOrder(USER_TASK_SELECT_INTERCONNECT);
  }

  @Test
  @Deployment(resources = BPMN)
  public void testAutoSelectInterConnectFailed() throws Exception {
    autoMock(BPMN);

    ProcessInstance processInstance = processEngine().getRuntimeService()
        .createProcessInstanceByKey(PROCESS_KEY).startAfterActivity(JOB_SELECT_INTERCONNECT)
        .setVariables(BpmnAwareTests.withVariables("interconnectId", false)).execute();

    assertThat(processInstance).isStarted();
    assertAndCompleteUserTask(processInstance, USER_TASK_SELECT_INTERCONNECT);

  }

  @Test
  @Deployment(resources = BPMN)
  public void testAllocateVlanFailed() throws Exception {
    autoMock(BPMN);

    ProcessInstance processInstance = processEngine().getRuntimeService()
        .createProcessInstanceByKey(PROCESS_KEY).startAfterActivity(JOB_WAIT_CREATE_L2_CONNECTION)
        .setVariables(BpmnAwareTests.withVariables("vlan", false)).execute();
    assertThat(processInstance).isStarted().task()
        .hasDefinitionKey(USER_TASK_HANDLE_L2_CONNECTION_ERROR).isNotAssigned();

    claim(task(), "fozzie");
    assertThat(task()).isAssignedTo("fozzie");
    complete(task());
    assertThat(processInstance).hasPassedInOrder(USER_TASK_HANDLE_L2_CONNECTION_ERROR);
    assertThat(processInstance).isEnded();
  }

  // @Test
  @Deployment(resources = "provisionAwsConnection.bpmn")
  public void testUpdateConnectionStatusFailed() throws Exception {
    try {
      DelegateExpressions.registerJavaDelegateMock("serviceTaskDelegate")
          .onExecutionThrowException(new ProcessEngineException("retry it"));
      ProcessInstance processInstance =
          processEngine().getRuntimeService().startProcessInstanceByKey(PROCESS_KEY);

      assertThat(processInstance).isStarted().isNotEnded();
      assertThat(processInstance).hasNotPassed("task_move_connection_processing");

      Job job = job(processInstance);
      assertThat(job).isNotNull().hasActivityId("task_move_connection_processing");
      do {
        try {
          execute(job);
          break;
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      } while (job(processInstance).getRetries() > 0);

      job = job(processInstance);

      assertThat(job).hasRetries(0).hasExceptionMessage();

      assertThat(processInstance).hasNotPassed("task_move_connection_processing");

      Incident incident = processEngine().getRuntimeService().createIncidentQuery()
          .activityId("task_move_connection_processing").singleResult();
      assertThat(incident.getIncidentMessage()).isEqualTo("retry it");
      System.out
          .println(processEngine().getManagementService().getJobExceptionStacktrace(job.getId()));
      assertThat(processInstance).isActive();

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

}
