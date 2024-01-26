<%
/**
 * @ (#) cancelenroll.jsp jan 18th 2008
 * Project      : TTK HealthCare Services
 * File         : cancelenroll.jsp
 * Author       : Yogesh S.C
 * Company      : Span Systems Corporation
 * Date Created : jan 18th 2008
 *
 * @author       : Yogesh S.C
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.WebBoardHelper,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/onlineforms/cancelenroll.js"></script>
<!-- S T A R T : Content/Form Area -->	
<html:form action="/CancelEnrollmentAction.do" > 
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	    	<td>Cancel Enrollment - <bean:write name="frmCancelEnroll" property="caption" /><html:hidden property="caption" /></td> 
		</tr>
	</table>
	<!-- E N D : Page Title --> 
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:message name="updated" scope="request"/>
			  	</td>
			</tr>
		</table>
	</logic:notEmpty>
    <!-- S T A R T : Form Fields -->
	<fieldset>
	<legend>Cancellation</legend>
		<table align="center" class="formContainerWeblogin" border="0" cellspacing="0" cellpadding="0">
		    <tr>
			    <td width="21%" nowrap class="formLabelWeblogin">Date of Exit: <span class="mandatorySymbol">*</span><br></td>
			    <td width="24%" nowrap>
					<html:text property="exitDate"  styleClass="textBoxWeblogin textDate" /><a name="CalendarObjectBirthDate" id="CalendarObjectBirthDate" href="#" onClick="javascript:show_calendar('CalendarObjectBirthDate','forms[1].exitDate',document.forms[1].exitDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;&nbsp;<br>
				</td>
			    <td colspan="2" nowrap></td>
		    </tr>
		    <tr>
			    <td nowrap class="formLabelWeblogin">Stop Cashless/Claims:</td>
			    <td align="left" nowrap><html:checkbox property="stopPatClmProcessYN" value="Y" styleId="stopPatClmProcessYN" onclick="javascript:onStopPatClmProcess();"/></td>
			    <td width="14%" nowrap class="formLabelWeblogin">Received After:</td>
			    <td width="41%" align="left" nowrap><html:text property="receivedAfter" styleClass="textBoxWeblogin textDate"  styleId="receivedAfter" disabled="true" /><A NAME="calStartDate" style="display:" ID="calStartDate" HREF="#"  onClick="javascript:show_calendar('calStartDate','forms[1].receivedAfter',document.forms[1].receivedAfter.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></td>
		    </tr>
	    </table>	
	</fieldset> 
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  	<tr>
		    <td width="100%" align="center">
			    <%
				    if(TTKCommon.isAuthorized(request,"Edit"))
				    {
				%>
			    <logic:equal name="frmCancelEnroll" property="allowEditYN" value="Y" >
				    <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
				    <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;                 
			    </logic:equal>
			    <%
					}
	    		%>
		    	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>
		    </td>
	  	</tr>
	</table> 
	<!-- E N D : Form Fields -->
	<p>&nbsp;</p>
	</div>
	<script>
	onStopPatClmProcess();
	</script>
	<input type="hidden" name="mode" >
	<input type="hidden" name="child" >
	<input type="hidden" name="stopPatClmProcessYN" value="N" >
	<html:hidden property="policyGroupSeqID"/>
	<html:hidden property="memberSeqID"/>
	<html:hidden property="policySeqID"/>
	<html:hidden property="relationTypeID"/>
	<html:hidden property="flag"/>
	<html:hidden property="allowEditYN"/>
	<html:hidden property="startDate"/>
	<html:hidden property="endDate"/>
</html:form>
