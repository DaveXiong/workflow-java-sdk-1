/**
 * 
 */
package com.consoleconnect.sdk.workflow.process.aws;

/**
 * @author dxiong
 *
 */
public interface ProvisionAwsConnectionDefinition {
  String BPMN = "provisionAwsConnection.bpmn";
  String PROCESS_KEY = "ProvisionAwsConnection";

  // process variables
  String PROCESS_VAR_SRC_PORT_UUID = "srcPortUuid";
  String PROCESS_VAR_DEST_PORT_UUID = "destPortUuid";
  String PROCESS_VAR_CONNECTION_ID = "connectionId";
  String PROCESS_VAR_CONNECTION_SPEED = "conenectionSpeed";
  String PROCESS_VAR_CUSTOMER_ID = "customerId";
  String PROCESS_VAR_CUSTOMER_EMAIL = "customerEmail";
  String PROCESS_VAR_REQUESTEDBY = "requestedBy";

  // process task activityId
  String JOB_UPDATE_CONNECTION_PROCESSING = "task_update_connection_processing";
  String JOB_SEND_CREATE_L2_CONNECTION = "task_send_create_l2_connection";
  String JOB_WAIT_CREATE_L2_CONNECTION = "task_wait_create_l2_connection";
  String MESSAGE_CREATE_L2_CONNECTION = "message_create_l2_connection";
  String JOB_PERSIST_L2_CONNECTION = "task_persist_l2_connection";
  String JOB_SELECT_INTERCONNECT = "task_select_interconnect";
  String USER_TASK_SELECT_INTERCONNECT = "task_select_interconnect_manual";
  String JOB_ALLOCATE_CONNECTION_ON_INTERCONNECT = "task_allocate_connection_on_interconnect";
  String JOB_PERSIST_CONNECTION = "task_persist_connection";
  String JOB_UPDATE_CONNECTION_PENDING_ACCEPTANCE = "task_update_connection_pending_acceptance";
  String USER_TASK_HANDLE_L2_CONNECTION_ERROR = "task_handle_l2_connection_error";

  // output for create l2 connection
  String MESSAGE_CREATE_L2_CONNECTION_DATA_SERVICE_VLAN = "serviceVlan";
  String MESSAGE_CREATE_L2_CONNECTION_DATA_CUSTOMER_VLAN = "customerVlan";
  String MESSAGE_CREATE_L2_CONNECTION_DATA_CONNECTION_UUID = "connectionUuid";


  // output for select interconnect
  String JOB_SELECT_INTERCONNECT_OUTPUT_INTERCONNECT_ID = "interconnectId";

}
