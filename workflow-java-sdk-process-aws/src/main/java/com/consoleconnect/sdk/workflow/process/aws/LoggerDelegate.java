package com.consoleconnect.sdk.workflow.process.aws;

import java.util.logging.Logger;

import org.camunda.bpm.engine.ProcessEngineException;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

/**
 * This is an empty service implementation illustrating how to use a plain Java class as a BPMN 2.0
 * Service Task delegate.
 */
@Component("serviceTaskDelegate")
public class LoggerDelegate implements JavaDelegate {

  private static final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

  public void execute(DelegateExecution execution) throws Exception {

    LOGGER.info("\n\n  ... LoggerDelegate invoked by " + "processDefinitionId="
        + execution.getProcessDefinitionId() + ", activtyId=" + execution.getCurrentActivityId()
        + ", activtyName='" + execution.getCurrentActivityName() + "'" + ", processInstanceId="
        + execution.getProcessInstanceId() + ", businessKey=" + execution.getProcessBusinessKey()
        + ", executionId=" + execution.getId() + " \n\n");

    LOGGER.info("vairables:" + execution.getVariables());

    if (execution.hasVariable("retry") && (Boolean) execution.getVariable("retry")) {
      throw new ProcessEngineException("retry it .........");
    }
  }

}
