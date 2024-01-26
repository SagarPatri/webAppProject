<%
/** @ (#) dailyremitancerpt.jsp Mar 29, 2010
 * Project     : TTK Healthcare Services
 * File        : dailyremitancerpt.jsp
 * Author      : Balakrishna E
 * Company     : Span Systems Corporation
 * Date Created: Mar 29, 2010
 *
 * @author 		 : Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/reports/dailyremitancerpt.js"></script>
<%
	pageContext.setAttribute("tdsRemittanceStatus",Cache.getCacheObject("tdsRemittanceStatus"));
	pageContext.setAttribute("tdsInsuranceInfo",Cache.getCacheObject("tdsInsuranceInfo"));
%>
<html:form action="/ReportsAction.do">

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><%=TTKCommon.getActiveSubLink(request)%> Reports - <bean:write name="frmReportList" property="reportName"/></td>
		</tr>
	</table>
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<!-- Start of Form Fields -->
    <fieldset>
	<legend>Report Parameters </legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="formLabel">Healthcare Company </td>
			<td>
				<html:select property="insInfo"  styleId="selectMode" styleClass="selectBox selectBoxLargest" style="background-color:" disabled="false">
          			<html:options collection="tdsInsuranceInfo"  property="cacheId" labelProperty="cacheDesc"/>
          		</html:select>
			</td>
			<td class="formLabel">Status </td>
			<td>
				<html:select property="payMode"  styleId="selectMode" styleClass="selectBox selectBoxMedium" style="background-color:" disabled="false">
          			<html:options collection="tdsRemittanceStatus"  property="cacheId" labelProperty="cacheDesc"/>
          		</html:select>
			</td>
		</tr>
		<tr>
		<td class="formLabel">From Date </td>
			    <td class="textLabel">
			     <table cellspacing="0" cellpadding="0">
			    	<tr>
			    	<td><html:text property="startDate" styleClass="textBox textDate" maxlength="10" /><A NAME="CalendarObjecttxtRecdDate" ID="CalendarObjecttxtRecdDate" HREF="#" onClick="javascript:show_calendar('CalendarObjecttxtRecdDate','frmReportList.startDate',document.frmReportList.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="startDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;</td>
			    	</tr>
			     </table>
			</td>
		<td class="formLabel">To Date <span class="mandatorySymbol">*</span></td>
			    <td class="textLabel">
			     <table cellspacing="0" cellpadding="0">
			    	<tr>
			    	<td><html:text property="endDate" styleClass="textBox textDate" maxlength="10" /><A NAME="CalendarObjecttxtRecdDate" ID="CalendarObjecttxtRecdDate" HREF="#" onClick="javascript:show_calendar('CalendarObjecttxtRecdDate','frmReportList.endDate',document.frmReportList.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="endDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;</td>
			    	</tr>
			     </table>
			</td>	     
		</tr>
		<td class="formLabel">Bank Account No </td>
			<td>
				<html:text property="bankAccountNo" maxlength="30" styleClass="textBox textBoxMedium" />
				<a href="#" onClick="javascript:onBankAcctNo();"><img src="/ttk/images/EditIcon.gif" alt="Bank Account No" width="16" height="16" border="0" align="absmiddle"></a>
			</td>
		<tr>
			
		</tr>	    	
	</table>
	</fieldset>
	<!-- E N D : Form Fields -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="center">Report Type
			<select name="reportType" class="selectBox" id="reporttype">
				<option value="PDF">PDF</option>
				<option value="EXL">EXCEL</option>
			</select> &nbsp;
		   	&nbsp;
			<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerate Report</button>&nbsp;
           	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
       	</td>
      </tr>
    </table>
    </div>
<html:hidden property="fileName"/>
<html:hidden property="reportID"/>
<html:hidden property="reportName"/>
<input type="hidden" name="mode" value=""/>	
</html:form>
