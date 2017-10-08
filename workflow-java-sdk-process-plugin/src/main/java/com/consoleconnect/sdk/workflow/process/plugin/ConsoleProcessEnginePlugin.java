package com.consoleconnect.sdk.workflow.process.plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.bpmn.parser.BpmnParseListener;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.camunda.bpm.engine.impl.incident.IncidentHandler;
import org.camunda.bpm.engine.runtime.Incident;

/**
 * Process engine plugin to add handlers for parsing bpmn and incidents
 * 
 * @author dxiong
 *
 */
public class ConsoleProcessEnginePlugin implements ProcessEnginePlugin {

  private static final Logger LOGGER = Logger.getLogger(ProcessEnginePlugin.class.getName());

  @Override
  public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    LOGGER.info("preInit.................................");
    List<BpmnParseListener> postParseListeners =
        processEngineConfiguration.getCustomPostBPMNParseListeners();
    if (postParseListeners == null) {
      postParseListeners = new ArrayList<>();
      processEngineConfiguration.setCustomPostBPMNParseListeners(postParseListeners);
    }
    postParseListeners.add(new ProcessParseListener());

    List<IncidentHandler> incidentHandlers = processEngineConfiguration.getCustomIncidentHandlers();
    if (incidentHandlers == null) {
      incidentHandlers = new ArrayList<>();
      processEngineConfiguration.setCustomIncidentHandlers(incidentHandlers);
    }
    incidentHandlers.add(new ProcessIncidentHandler(Incident.FAILED_JOB_HANDLER_TYPE));
  }

  @Override
  public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
    // do nothing
  }

  @Override
  public void postProcessEngineBuild(ProcessEngine processEngine) {
    // do nothing
  }

}
