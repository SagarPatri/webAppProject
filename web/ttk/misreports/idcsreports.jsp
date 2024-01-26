
<%
/** @ (#) idcsreports.jsp May 27, 2007
 * Project     : TTK Healthcare Services
 * File        : idcsreports.jsp
 * Author      : Ajay Kumar
 * Company     : WebEdge Technologies Pvt.Ltd.
 * Date Created: 
 *
 * @author 		 : Ajay Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.misreports.ReportCache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/idcsreports.js"></script>

<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/IdCardsGenerateReportsAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td width="51%">Id Cards Status Report</td>
    	
	</tr>
</table>
<!-- E N D : Page Title -->
<!-- S T A R T : Search Box -->
<html:errors/>
<div class="contentArea" id="contentArea">

<fieldset>
<legend>Report Parameters</legend>
<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">

 <tr>
	 <td class="formLabel">Date:</td>
        	<td>
        		<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="IdcsReportStartDate" ID="IdcsReportStartDate" HREF="#" onClick="javascript:show_calendar('IdcsReportStartDate','frmMISReports.sStartDate',document.frmMISReports.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>
 </tr>
</table>
</fieldset>
<!-- E N D : Search Box -->
 <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
      Report Type
			<select name="reportType" class="selectBox" id="reporttype">
			    <option value="PDF">PDF</option>
		        <option value="EXL">EXCEL</option>
		   </select>
		   &nbsp;
           <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerate Report</button>&nbsp;
	       <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td>
  </tr>
</table>
	<!-- E N D : Buttons -->
</div>

    <INPUT TYPE="hidden" NAME="mode" VALUE="">
	<!-- <INPUT TYPE="hidden" NAME="sortId" VALUE=""> -->
	<!-- <INPUT TYPE="hidden" NAME="pageId" VALUE=""> -->
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	
	<html:hidden property="parameterValues"/>
	<html:hidden property="reportID"/>
	<html:hidden property="fileName"/>
	</html:form>
<!-- E N D : Content/Form Area -->