<%
/** @ (#) claimspendingrptparams.jsp July 4, 2006
 * Project     : TTK Healthcare Services
 * File        : claimspendingrptparams.jsp
 * Author      : Balakrishna Erram
 * Company     : Span Systems Corporation
 * Date Created: Feb 2, 2006
 *
 * @author 		 : Balakrishna Erram
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/reports/claimspendingrptparams.js"></script>
<%
	String strSubLink=TTKCommon.getActiveSubLink(request);
	pageContext.setAttribute("strSubLink",strSubLink);
	pageContext.setAttribute("listInsuranceCompany",Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("claimType", Cache.getCacheObject("claimType"));
	pageContext.setAttribute("claimspending", Cache.getCacheObject("claimspending"));
%>
<html:form action="/ClaimsPendingReportsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><%=TTKCommon.getActiveSubLink(request)%> Reports - <bean:write name="frmReportList" property="reportName" /></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Form Fields -->
    <table border="0" align="center" cellpadding="0" cellspacing="0" class="tablePad">
         <tr>
         <td width="13%" nowrap class="textLabelBold" align="center">Select Type </td>
         <td width="39%" align="center">
         	<html:select property="selectRptType" styleId="select" styleClass="selectBox selectBoxMedium" onchange="javascript:onReportType()" >
            	<html:options collection="claimspending" property="cacheId" labelProperty="cacheDesc"/>
            </html:select>
          </td>
          <td>&nbsp;</td><td>&nbsp;</td>
          <td>&nbsp;</td><td>&nbsp;</td>
         </tr>
    </table>
	<fieldset>
		<legend>Report Parameters </legend>
			<table border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
				<tr>
					<td nowrap>Start Date <span class="mandatorySymbol">*</span></td>
					<td>
						<html:text property="startDate"	styleClass="textBox textDate" maxlength="10" /> 
						<a NAME="CalendarObjectstartDate" ID="CalendarObjectstartDate" HREF="#"	onClick="javascript:show_calendar('CalendarObjectstartDate','forms[1].startDate',document.forms[1].startDate.value,'',event,148,178);return false;"	onMouseOver="window.status='Calendar';return true;"	onMouseOut="window.status='';return true;">
						<img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="startDate" width="24" height="17" border="0" align="absmiddle"></a>
					</td>
					<td nowrap>End Date <span class="mandatorySymbol">*</span></td>
					<td>
						<html:text property="endDate" styleClass="textBox textDate" maxlength="10" />
						<a NAME="CalendarObjectendDate" ID="CalendarObjectendDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectendDate','forms[1].endDate',document.forms[1].endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
						<img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="endDate" width="24" height="17" border="0" align="absmiddle"></a>
					</td>
				</tr>
				<tr>
					<logic:notMatch name="frmReportList" property="selectRptType" value="CAC">
						<td width="22%" class="formLabel">Float Account No. <span class="mandatorySymbol">*</span></td>
						<td width="29%" class="formLabel">
							<html:text name="frmReportList" property="floatAccNo" maxlength="250" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);"/>
						</td>
					</logic:notMatch>
					<logic:match name="frmReportList" property="selectRptType" value="CAC">
						<td width="22%" class="formLabel">Float Account No. <span class="mandatorySymbol">*</span></td>
						<td width="29%" class="formLabel">
							<html:text name="frmReportList" property="floatAccNo" maxlength="250" style="background-color: #EEEEEE;" readonly="true" />
						</td>
						<td width="22%" class="formLabel">Batch No. <span class="mandatorySymbol">*</span></td>
						<td width="29%" class="formLabel">
							<html:text name="frmReportList" property="batchNo" maxlength="250" style="background-color: #EEEEEE;" readonly="true" />&nbsp;&nbsp;&nbsp;
							<a href="#" onClick="javascript:onBatchNumber();"><img src="/ttk/images/EditIcon.gif" alt="Batch No" width="16" height="16" border="0" align="absmiddle"></a>
						</td>
					</logic:match>
				</tr>
				<logic:match name="frmReportList" property="selectRptType" value="CAC">
					<tr>
						<td width="22%" class="formLabel">Claim Type </td>
						<td width="29%" class="formLabel">
							<html:select property="claimTypeID" styleId="select" styleClass="selectBox selectBoxMedium">
								<html:option value="">Select from list</html:option>
								<html:options collection="claimType" property="cacheId"	labelProperty="cacheDesc" />
							</html:select>
						</td>
					</tr>
				</logic:match>
			</table>
	</fieldset>
	<!-- E N D : Form Fields -->
	<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="center">Report Type 
				<select name="reportType" class="selectBox" id="reporttype">
					<option value="PDF">PDF</option>
					<option value="EXL">EXCEL</option>
				</select> &nbsp;
				<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerateReport</button>
				&nbsp;
				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
			</td>
		</tr>
	</table>
	<!-- End of Reports List -->
	</div>	
	<html:hidden property="reportName"/>
	<input type="hidden" name="mode" value="" />
</html:form>
