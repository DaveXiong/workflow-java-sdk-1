/**
 * 
 */
package com.consoleconnect.sdk.workflow.process.plugin;

import org.apache.ibatis.logging.LogFactory;

/**
 * @author dxiong
 *
 */
public abstract class AbstractProcessEngineTest {

  static {
    LogFactory.useSlf4jLogging(); // MyBatis
  }

}
