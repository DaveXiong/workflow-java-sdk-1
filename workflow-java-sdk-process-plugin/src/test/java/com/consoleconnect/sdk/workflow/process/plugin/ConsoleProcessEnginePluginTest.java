/**
 * 
 */
package com.consoleconnect.sdk.workflow.process.plugin;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.camunda.bpm.engine.impl.incident.DefaultIncidentHandler;
import org.camunda.bpm.engine.impl.incident.IncidentHandler;
import org.camunda.bpm.engine.runtime.Incident;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dxiong
 *
 */
public class ConsoleProcessEnginePluginTest extends AbstractProcessEngineTest {
  private ConsoleProcessEnginePlugin plugin;

  @Before
  public void setUp() {
    plugin = new ConsoleProcessEnginePlugin();
  }

  @Test
  public void shouldAddIncidentHandlerWhenNull() {
    ProcessEngineConfigurationImpl configuration = new StandaloneProcessEngineConfiguration();
    configuration.setCustomIncidentHandlers(null);
    plugin.preInit(configuration);
    assertThat(configuration.getCustomIncidentHandlers()).isNotNull().hasSize(1);
  }

  @Test
  public void shouldAddIncidentHandlerWhenEmpty() {
    ProcessEngineConfigurationImpl configuration = new StandaloneProcessEngineConfiguration();
    configuration.setCustomIncidentHandlers(new ArrayList<IncidentHandler>());
    plugin.preInit(configuration);
    assertThat(configuration.getCustomIncidentHandlers()).isNotNull().hasSize(1);
  }

  @Test
  public void shouldAddIncidentHandlerWhenNotEmpty() {
    ProcessEngineConfigurationImpl configuration = new StandaloneProcessEngineConfiguration();
    List<IncidentHandler> handlers = new ArrayList<IncidentHandler>();
    handlers.add(new DefaultIncidentHandler(Incident.FAILED_JOB_HANDLER_TYPE));
    configuration.setCustomIncidentHandlers(handlers);
    plugin.preInit(configuration);
    assertThat(configuration.getCustomIncidentHandlers()).isNotNull().hasSize(2);
  }
}
