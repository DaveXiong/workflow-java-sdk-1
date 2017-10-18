package com.consoleconnect.sdk.workflow.process.aws;


import java.util.Map;
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

  private final Logger log = Logger.getLogger(LoggerDelegate.class.getName());

  public void execute(DelegateExecution execution) throws Exception {

    
    log.info("\n\n  ... LoggerDelegate invoked by " + "processDefinitionId="
        + execution.getProcessDefinitionId() + ", activtyId=" + execution.getCurrentActivityId()
        + ", activtyName='" + execution.getCurrentActivityName() + "'" + ", processInstanceId="
        + execution.getProcessInstanceId() + ", businessKey=" + execution.getProcessBusinessKey()
        + ", executionId=" + execution.getId() + " \n\n");

    System.out.println("variables:"+execution.getVariable("order"));
    log.info("vairables:" + execution.getVariables());
    
    System.out.println("local:"+execution.getVariableNamesLocal());
    System.out.println("speed:"+execution.getVariableLocal("speed"));
    System.out.println("id:"+execution.getVariableLocal("id"));
    
    System.out.println("TYPE:"+execution.getVariablesTyped());
    System.out.println(execution.getVariablesLocal());
    
    Map<String,Object> data = (Map<String,Object>)execution.getVariable("data");
    
    if(data != null) {
      System.out.println(data.get("speed"));
      System.out.println(data.get("name"));
    }

    if (execution.hasVariable("retry") && (Boolean) execution.getVariable("retry")) {
      throw new ProcessEngineException("retry it .........");
    }
  }

}
