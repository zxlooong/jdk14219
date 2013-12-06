package org.omg.PortableInterceptor;


/**
* org/omg/PortableInterceptor/ServerRequestInfo.java .
* Generated by the IDL-to-Java compiler (portable), version "3.1"
* from ../../../../src/share/classes/org/omg/PortableInterceptor/Interceptors.idl
* Monday, November 10, 2008 6:32:18 AM GMT
*/


/**
   * Request Information, accessible to server-side request interceptors.
   * <p>
   * Some attributes and operations on <code>ServerRequestInfo</code> are not 
   * valid at all interception points.  The following table shows the validity 
   * of each attribute or operation.  If it is not valid, attempting to access 
   * it will result in a <code>BAD_INV_ORDER</code> being thrown with a 
   * standard minor code of 14.
   * <p>
   *
   *
   * <table border=1 summary="Shows the validity of each attribute or operation">
   *   <thead>
   *     <tr>
   *       <th>&nbsp;</th>
   *       <th id="rec_req_ser_con" valign="bottom">receive_request_<br>service_contexts</th>
   *       <th id="rec_req"  valign="bottom">receive_request</th>
   *       <th id="send_rep" valign="bottom">send_reply</th>
   *       <th id="send_exc" valign="bottom">send_exception</th>
   *       <th id="send_oth" valign="bottom">send_other</th>
   *     </tr>
   *   </thead>
   *  <tbody>
   *
   *
   * <tr>
   *   <td id="ri" colspan=6><i>Inherited from RequestInfo:</i></td>
   * </tr>
   *
   * <tr><th id="req_id"><p align="left">request_id</p></th>
   * <td headers="ri req_id rec_req_ser_con">yes</td> 
   * <td headers="ri req_id rec_req">yes</td> 
   * <td headers="ri req_id send_rep">yes</td> 
   * <td headers="ri req_id send_exc">yes</td> 
   * <td headers="ri req_id send_oth">yes</td></tr>
   *
   * <tr><th id="op"><p align="left">operation</p></th>
   * <td headers="ri op rec_req_ser_con">yes</td> 
   * <td headers="ri op rec_req">yes</td> 
   * <td headers="ri op send_rep">yes</td> 
   * <td headers="ri op send_exc">yes</td> 
   * <td headers="ri op send_oth">yes</td></tr>
   *
   * <tr><th id="args"><p align="left">arguments</p></th>
   * <td headers="ri args rec_req_ser_con">no </td> 
   * <td headers="ri args rec_req">yes<sub>1</sub></td>
   * <td headers="ri args send_rep">yes</td> 
   * <td headers="ri args send_exc">no<sub>2</sub></td>
   * <td headers="ri args send_oth">no<sub>2</sub>
   * </td></tr>
   *
   * <tr><th id="exps"><p align="left">exceptions</p></th>
   * <td headers="ri exps rec_req_ser_con">no </td> 
   * <td headers="ri exps rec_req">yes</td> 
   * <td headers="ri exps send_rep">yes</td> 
   * <td headers="ri exps send_exc">yes</td> 
   * <td headers="ri exps send_oth">yes</td></tr>
   *
   * <tr><th id="contexts"><p align="left">contexts</p></th>
   * <td headers="ri contexts rec_req_ser_con">no </td> 
   * <td headers="ri contexts rec_req">yes</td> 
   * <td headers="ri contexts send_rep">yes</td> 
   * <td headers="ri contexts send_exc">yes</td> 
   * <td headers="ri contexts send_oth">yes</td></tr>
   *
   * <tr><th id="op_con"><p align="left">operation_context</p></th>
   * <td headers="ri op_con rec_req_ser_con">no </td> 
   * <td headers="ri op_con rec_req">yes</td> 
   * <td headers="ri op_con send_rep">yes</td> 
   * <td headers="ri op_con send_exc">no </td> 
   * <td headers="ri op_con send_oth">no </td>
   * </tr>
   * 
   * <tr><th id="result"><p align="left">result</p></th>
   * <td headers="ri result rec_req_ser_con">no </td> 
   * <td headers="ri result rec_req">no </td> 
   * <td headers="ri result send_rep">yes</td> 
   * <td headers="ri result send_exc">no </td> 
   * <td headers="ri result send_oth">no </td>
   * </tr>
   *
   * <tr><th id="res_ex"><p align="left">response_expected</p></th>
   * <td headers="ri res_ex rec_req_ser_con">yes</td> 
   * <td headers="ri res_ex rec_req">yes</td> 
   * <td headers="ri res_ex send_rep">yes</td> 
   * <td headers="ri res_ex send_exc">yes</td> 
   * <td headers="ri res_ex send_oth">yes</td></tr>
   *
   * <tr><th id="syn_scp"><p align="left">sync_scope</p></th>
   * <td headers="ri syn_scp rec_req_ser_con">yes</td> 
   * <td headers="ri syn_scp rec_req">yes</td> 
   * <td headers="ri syn_scp send_rep">yes</td> 
   * <td headers="ri syn_scp send_exc">yes</td> 
   * <td headers="ri syn_scp send_oth">yes</td></tr>
   * 
   * <tr><th id="rep_stat"><p align="left">reply_status</p></th>
   * <td headers="ri rep_stat rec_req_ser_con">no </td> 
   * <td headers="ri rep_stat rec_req">no </td> 
   * <td headers="ri rep_stat send_rep">yes</td> 
   * <td headers="ri rep_stat send_exc">yes</td> 
   * <td headers="ri rep_stat send_oth">yes</td></tr>
   *
   * <tr><th id="for_ref"><p align="left">forward_reference</p></th>
   * <td headers="ri for_ref rec_req_ser_con">no </td> 
   * <td headers="ri for_ref rec_req">no </td> 
   * <td headers="ri for_ref send_rep">no </td> 
   * <td headers="ri for_ref send_exc">no </td> 
   * <td headers="ri for_ref send_oth">yes<sub>2</sub>
   *                                                            </td></tr>
   *
   * <tr><th id="get_slot"><p align="left">get_slot</p></th>
   * <td headers="ri get_slot rec_req_ser_con">yes</td> 
   * <td headers="ri get_slot rec_req">yes</td> 
   * <td headers="ri get_slot send_rep">yes</td> 
   * <td headers="ri get_slot send_exc">yes</td> 
   * <td headers="ri get_slot send_oth">yes</td></tr>
   *
   * <tr><th id="g_r_s_c"><p align="left">get_request_service_context</p></th>
   * <td headers="ri g_r_s_c rec_req_ser_con">yes</td> 
   * <td headers="ri g_r_s_c rec_req">no </td> 
   * <td headers="ri g_r_s_c send_rep">yes</td> 
   * <td headers="ri g_r_s_c send_exc">yes</td> 
   * <td headers="ri g_r_s_c send_oth">yes</td></tr>
   *
   * <tr><th id="g_rp_s_c"><p align="left">get_reply_service_context</p></th>
   * <td headers="ri g_rp_s_c rec_req_ser_con">no </td> 
   * <td headers="ri g_rp_s_c rec_req">no </td> 
   * <td headers="ri g_rp_s_c send_rep">yes</td> 
   * <td headers="ri g_rp_s_c send_exc">yes</td> 
   * <td headers="ri g_rp_s_c send_oth">yes</td></tr>
   *
   * <tr>
   *   <td id="sri" colspan=6><i>ServerRequestInfo-specific:</i></td>
   * </tr>
   *
   * <tr><th id="send_ex"><p align="left">sending_exception</p></th>
   *   <td headers="sri send_ex rec_req_ser_con">no </td> 
   *   <td headers="sri send_ex rec_req">no </td> 
   *   <td headers="sri send_ex send_rep">no </td> 
   *   <td headers="sri send_ex send_exc">yes</td> 
   *   <td headers="sri send_ex send_oth">no </td>
   *   </tr>
   *
   * <tr><th id="oid"><p align="left">object_id</p></th>
   *   <td headers="sri oid rec_req_ser_con">no </td> 
   *   <td headers="sri oid rec_req">yes</td> 
   *   <td headers="sri oid send_rep">yes</td> 
   *   <td headers="sri oid send_exc">yes<sub>3</sub></td>
   *   <td headers="sri oid send_oth">yes<sub>3</sub>
   *   </td></tr>
   *
   * <tr><th id="aid"><p align="left">adapter_id</p></th>
   * <td headers="sri aid rec_req_ser_con">no </td> 
   * <td headers="sri aid rec_req">yes</td> 
   * <td headers="sri aid send_rep">yes</td> 
   * <td headers="sri aid send_exc">yes<sub>3</sub></td>
   * <td headers="sri aid send_oth">yes<sub>3</sub>
   * </td></tr>
   *
   * <tr><th id="tmdi"><p align="left">target_most_derived_interface</p></th>
   * <td headers="sri tmdi rec_req_ser_con">no </td> 
   * <td headers="sri tmdi rec_req">yes</td> 
   * <td headers="sri tmdi send_rep">no<sub>4</sub></td>
   * <td headers="sri tmdi send_exc">no<sub>4</sub>
   * <td headers="sri tmdi send_oth">no<sub>4</sub>
   * </td></tr>
   *
   * <tr><th id="gsp"><p align="left">get_server_policy</p></th>
   * <td headers="sri gsp rec_req_ser_con">yes</td> 
   * <td headers="sri gsp rec_req">yes</td> 
   * <td headers="sri gsp send_rep">yes</td> 
   * <td headers="sri gsp send_exc">yes</td> 
   * <td headers="sri gsp send_oth">yes</td></tr>
   *
   *
   *
   * <tr><th id="set_slot"><p align="left">set_slot</p></th>
   * <td headers="sri set_slot rec_req_ser_con">yes</td> 
   * <td headers="sri set_slot rec_req">yes</td> 
   * <td headers="sri set_slot send_rep">yes</td> 
   * <td headers="sri set_slot send_exc">yes</td> 
   * <td headers="sri set_slot send_oth">yes</td></tr>
   *
   * <tr><th id="target_is_a"><p align="left">target_is_a</p></th>
   * <td headers="sri target_is_a rec_req_ser_con">no </td> 
   * <td headers="sri target_is_a rec_req">yes</td> 
   * <td headers="sri target_is_a send_rep">no<sub>4</sub></td>
   * <td headers="sri target_is_a send_exc">no<sub>4</sub></td>
   * <td headers="sri target_is_a send_oth">no<sub>4</sub>
   * </td></tr>
   *
   * <tr><th id="arsc"><p align="left">add_reply_service_context</p></th>
   * <td headers="sri arsc rec_req_ser_con">yes</td> 
   * <td headers="sri arsc rec_req">yes</td> 
   * <td headers="sri arsc send_rep">yes</td> 
   * <td headers="sri arsc send_exc">yes</td> 
   * <td headers="sri arsc send_oth">yes</td></tr>
   * </tbody>
   * </table>
   *
   * <ol>
   *   <li>When <code>ServerRequestInfo</code> is passed to 
   *       <code>receive_request</code>, there is an entry in the list for 
   *       every argument, whether in, inout, or out. But only the in and 
   *       inout arguments will be available.</li>
   *   <li>If the <code>reply_status</code> attribute is not 
   *       <code>LOCATION_FORWARD</code>, accessing this attribute will throw
   *       <code>BAD_INV_ORDER</code> with a standard minor code of 14.</li>
   *   <li>If the servant locator caused a location forward, or thrown an 
   *       exception, this attribute/operation may not be available in this 
   *       interception point. <code>NO_RESOURCES</code> with a standard minor 
   *       code of 1 will be thrown if it is not available.</li>
   *   <li>The operation is not available in this interception point because 
   *       the necessary information requires access to the target object's 
   *       servant, which may no longer be available to the ORB. For example, 
   *       if the object's adapter is a POA that uses a 
   *       <code>ServantLocator</code>, then the ORB invokes the interception 
   *       point after it calls <code>ServantLocator.postinvoke()</code></li>.
   * </ol>
   *
   * @see ServerRequestInterceptor
   */
public interface ServerRequestInfo extends ServerRequestInfoOperations, org.omg.PortableInterceptor.RequestInfo, org.omg.CORBA.portable.IDLEntity 
{
} // interface ServerRequestInfo
