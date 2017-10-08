package com.consoleconnect.sdk.workflow.process.plugin;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.TaskListener;
import org.camunda.bpm.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.camunda.bpm.engine.impl.bpmn.parser.AbstractBpmnParseListener;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.pvm.delegate.ActivityBehavior;
import org.camunda.bpm.engine.impl.pvm.process.ActivityImpl;
import org.camunda.bpm.engine.impl.pvm.process.ScopeImpl;
import org.camunda.bpm.engine.impl.util.xml.Element;

/**
 * Listener for parsing bpmn process
 * 
 * @author dxiong
 *
 */
public class ProcessParseListener extends AbstractBpmnParseListener implements BpmnParseListener {

  private static final Logger LOGGER = Logger.getLogger(ProcessParseListener.class.getName());

  @Override
  public void parseUserTask(Element userTaskElement, ScopeImpl scope, ActivityImpl activity) {
    LOGGER.info("Adding Task Listener to User Task:" + " activtyId=" + activity.getId()
        + ", activtyName='" + activity.getName() + "'" + ", scopeId=" + scope.getId()
        + ", scopeName=" + scope.getName());
    ActivityBehavior behavior = activity.getActivityBehavior();
    if (behavior instanceof UserTaskActivityBehavior) {

      ProcessTaskListener listener = new ProcessTaskListener();

      ((UserTaskActivityBehavior) behavior).getTaskDefinition()
          .addTaskListener(TaskListener.EVENTNAME_CREATE, listener);
      ((UserTaskActivityBehavior) behavior).getTaskDefinition()
          .addTaskListener(TaskListener.EVENTNAME_ASSIGNMENT, listener);
      ((UserTaskActivityBehavior) behavior).getTaskDefinition()
          .addTaskListener(TaskListener.EVENTNAME_COMPLETE, listener);
      ((UserTaskActivityBehavior) behavior).getTaskDefinition()
          .addTaskListener(TaskListener.EVENTNAME_DELETE, listener);
    }
  }

}
