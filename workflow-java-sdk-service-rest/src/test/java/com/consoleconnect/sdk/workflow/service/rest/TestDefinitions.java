/**
 * 
 */
package com.consoleconnect.sdk.workflow.service.rest;

/**
 * @author dxiong
 *
 */
public final class TestDefinitions {



  public static final String PROVIDER_CONSOLE = "console";
  public static final String PROVIDER_SL = "sl";


  private TestDefinitions() {}


  public static final class HeartBeat {
    private String version;
    private long upTime;

    public HeartBeat(String version, long upTime) {
      this.version = version;
      this.upTime = upTime;
    }


    public String getVersion() {
      return version;
    }

    public void setVersion(String version) {
      this.version = version;
    }

    public long getUpTime() {
      return upTime;
    }

    public void setUpTime(long upTime) {
      this.upTime = upTime;
    }

    @Override
    public String toString() {
      return "HeartBeat [version=" + version + ", upTime=" + upTime + "]";
    }


  }

}
