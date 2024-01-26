<% 
/**
 * @ (#) suspensionlist.jsp 7th Feb 2006
 * Project      : TTK HealthCare Services
 * File         : suspensionlist.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created :7th Feb 2006 
 *
 * @author       :Chandrasekaran J 
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/enrollment/suspensionlist.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->	
<html:form action="SuspensionAction.do" > 	
<!-- S T A R T : Page Title -->
	<logic:match name="frmPolicyList" property="switchType" value="ENM">
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmPolicyList" property="switchType" value="END">
		<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
			<tr>
	    		<td width="100%">Suspension  Details- <bean:write name="frmSuspension" property="caption"/></td>
			</tr>
		</table>
<!-- E N D : Page Title --> 

<div class="scrollableGrid" style="height:350px;">
<html:errors/>
<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:message name="updated" scope="request"/>
				</td>
			</tr>
		</table>
	</logic:notEmpty>
<!-- E N D : Success Box -->
<br>	
<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableDataSuspension" className="gridWithCheckBox zeroMargin"/> 
<!-- E N D : Grid -->
</div>
<table class="buttonsSavetolistGrid"  border="0" cellspacing="0" cellpadding="0">

	<tr>
		<td width="100%" align="right" nowrap class="formLabel">
		<%
	    		if(TTKCommon.isDataFound(request,"tableDataSuspension") && TTKCommon.isAuthorized(request,"Delete"))
				{
		%> 
					<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>
		<%
        		}
        	%>			
		</td>
	</tr>
</table>
<fieldset>
    <legend>Suspension  Details</legend>
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
        		<td align="left" nowrap>From Date:<br>
        			<input name="startDate" type="text" maxlength="10" class="textBox textDate" onBlur="javascript:calculate_days_months()" value="<bean:write name="frmSuspension" property="startDate"/>"><A NAME="CalendarObjectStartDate" ID="CalendarObjectStartDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectStartDate','forms[1].startDate',document.forms[1].startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>	     
        	<% pageContext.setAttribute("strSublink",TTKCommon.getActiveSubLink(request));  %>
        	<logic:match name="frmPolicyList" property="switchType" value="END">		
        	<logic:equal name="strSublink" value="Individual Policy">	
        		<td align="left" nowrap>Period (Days/Months):<br>
        			<select name="drOfPeriodType" class="selectBox" id="drOfPeriodType" onChange="javascript:calculate_days_months()">
	        			<option value="d">Days</option>
	                	<option value="m">Months</option>
	                </select>
	                <input type="text" name="drOfPeriod" id="drOfPeriod" onBlur="javascript:calculate_days_months()" maxlength="3" class="textBox textDate"/>     	
        		</td>	
	    	</logic:equal>
	    	</logic:match>
        		<td align="left" nowrap>To Date:<br>
	    			<input name="endDate" type="text" maxlength="10" class="textBox textDate" value="<bean:write name="frmSuspension" property="endDate"/>"><A NAME="CalendarObjectEndDate" ID="CalendarObjectEndDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectEndDate','forms[1].endDate',document.forms[1].endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
        		<td width="100%" valign="bottom" nowrap class="formLabel">
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave to List</button>&nbsp;
        		</td>
    	</tr>
    </table>
</fieldset>
<table class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%" align="center" nowrap class="formLabel">
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
		</td>
	</tr>
</table>
<INPUT TYPE="hidden" NAME="rownum" VALUE='<bean:write name="frmSuspension" property="rownum"/>'>
<html:hidden property="supensionSeqID"/>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->	