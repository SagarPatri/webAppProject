<%
/** @ (#) addreviseplan.jsp 21st Oct 2005
 * Project     : Vidal Health TPA Services
 * File        : addreviseplan.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 21st Oct 2005
 *
 * @author 			Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.dto.administration.TariffPlanVO" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%
	TariffPlanVO tariffPlanVO=(TariffPlanVO)session.getAttribute("TariffPlanVO"); //get the plan information from session
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/addreviseplan.js"></script>

<!-- S T A R T : Content/Form Area -->
    <html:form action="/AddTariffRevisePlanAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Revise Plan - <%=(tariffPlanVO!=null)?"["+tariffPlanVO.getTariffPlanName()+"]":""%></td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<fieldset>
	<legend>Effective From</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" class="formLabel">Start Date: <span class="mandatorySymbol">*</span></td>
        <td width="30%"><html:text property="startDate" styleClass="textBox textDate" maxlength="10" onkeypress="javascript:blockEnterkey(event.srcElement);"/><a name="CalendarObjectincpDate" id="CalendarObjectincpDate" href="#" onClick="javascript:show_calendar('CalendarObjectincpDate','forms[1].startDate',document.forms[1].startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a></td>
		<td width="13%">&nbsp;</td>
        <td width="39%">&nbsp;</td>
        </tr>
    </table>
	</fieldset>
	<!-- E N D : Form Fields -->

    <!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="100%" align="center">
		    <%
	    		if(TTKCommon.isAuthorized(request,"Edit"))
	    		{
	    	%>
		    	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
		    <%
		    	}
		    %>

		    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		    </td>
		  </tr>
		</table>
	<!-- E N D : Buttons -->
	</div>
	<input type="hidden" name="mode">
	<input type="hidden" name="child" value="AddRevisePlan">
	</html:form>
	<!-- E N D : Content/Form Area -->