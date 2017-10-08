package com.consoleconnect.sdk.workflow.process.plugin;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateTask;
import org.camunda.bpm.engine.delegate.TaskListener;

public class ProcessTaskListener implements TaskListener {

  private static final Logger LOGGER = Logger.getLogger(ProcessTaskListener.class.getName());

  @Override
  public void notify(DelegateTask task) {
    LOGGER.info("Event '" + task.getEventName() + "' received by Task Listener for Task:"
        + " activityId=" + task.getTaskDefinitionKey() + ", name='" + task.getName() + "'"
        + ", taskId=" + task.getId() + ", assignee='" + task.getAssignee() + "'"
        + ", candidateGroups='" + task.getCandidates() + "'");

    // TODO: send email
  }

}
