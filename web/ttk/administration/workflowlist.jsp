<%
/** @ (#) workflow.jsp Dec 28, 2005
 * Project      : TTK Healthcare Services
 * File         : workflow.jsp
 * Author       : Balakrishna.E
 * Company      : Span Systems Corporation
 * Date Created : Dec 28, 2005
 * @author 		: Balakrishna.E
 * Modified by  : Raghavendra T.M
 * Modified date: Jan 17, 2006
 * Reason       : 
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/administration/workflowlist.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->	
<html:form action="/WorkflowAction.do"> 
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>List of Workflows</td>
      </tr>
    </table>
    <!-- E N D : Page Title --> 
<html:errors/>
<div class="contentArea" id="contentArea">
<!-- S T A R T : WorkflowList -->
		<logic:iterate id="workflow" name="frmWorkflow" property="alWorkflowList" >
		<fieldset class="UGSmall">
		<legend><bean:write name="workflow" property="workflowName"/></legend>
		<table class="formContainer" align="left"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="formLabel">
	 			<ul class="liBotMargin">
	 			<logic:iterate id="event" name="workflow" property="eventVO">
	 				<li class="liPad"><a href="#" class="events" onClick="javascript:onEventDetails('<bean:write name="event" property="eventSeqID"/>','<bean:write name="workflow" property="workflowName"/>')"><bean:write name="event" property="eventName"/></a></li>
	 			</logic:iterate>	
	 			</ul>
				</td>
			</tr>
		</table>
		</fieldset>
		</logic:iterate>	
<!-- E N D : WorkflowList -->
</div>
	<input type="hidden" name="child" value="">	
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="workflowName" />
	<INPUT TYPE="hidden" NAME="caption" />
	<INPUT TYPE="hidden" NAME="eventSeqID" />
	<!-- E N D : Buttons -->
</html:form>