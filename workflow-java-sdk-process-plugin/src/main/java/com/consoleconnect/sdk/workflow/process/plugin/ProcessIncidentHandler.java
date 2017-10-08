/**
 * 
 */
package com.consoleconnect.sdk.workflow.process.plugin;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.camunda.bpm.engine.impl.incident.DefaultIncidentHandler;
import org.camunda.bpm.engine.impl.incident.IncidentContext;

/**
 * Incident event handler
 * 
 * @author dxiong
 *
 */
public class ProcessIncidentHandler extends DefaultIncidentHandler {

  private static final Logger LOGGER = Logger.getLogger(ProcessIncidentHandler.class.getName());

  public ProcessIncidentHandler(String type) {
    super(type);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.camunda.bpm.engine.impl.incident.IncidentHandler#handleIncident(org.
   * camunda.bpm.engine.impl.incident.IncidentContext, java.lang.String)
   */
  @Override
  public void handleIncident(IncidentContext context, String message) {
    super.handleIncident(context, message);

    LOGGER.log(Level.INFO, () -> "handleIncident:" + context + message);

    // add business logic here to handle new incident

  }

  /*
   * (non-Javadoc)
   * 
   * @see org.camunda.bpm.engine.impl.incident.IncidentHandler#resolveIncident(org.
   * camunda.bpm.engine.impl.incident.IncidentContext)
   */
  @Override
  public void resolveIncident(IncidentContext context) {
    super.resolveIncident(context);

    LOGGER.log(Level.INFO, () -> "resolveIncident:" + context);

    // add business logic here to handle a incident was resolved


  }

  /*
   * (non-Javadoc)
   * 
   * @see org.camunda.bpm.engine.impl.incident.IncidentHandler#deleteIncident(org.
   * camunda.bpm.engine.impl.incident.IncidentContext)
   */
  @Override
  public void deleteIncident(IncidentContext context) {
    super.deleteIncident(context);

    LOGGER.log(Level.INFO, () -> "deleteIncident:" + context);
    // add business log here to handle a incident was deleted


  }

}
