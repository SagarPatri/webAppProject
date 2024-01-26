<%
/** @ (#) preauthorizationgrpreports.jsp
 * Project     : TTK Healthcare Services
 * File        : preauthorizationgrpreports.jsp
 * Author      : Manikanta Kumar G G
 * Company     : Span Systems Corporation
 * Date Created: Nov 29, 2010
 *
 * @author 		 : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/misreports/preauthorizationgrpreports.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<html:form action="/PreAuthGrpReportsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>Group Cashless Detail Report</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Form Fields -->
	<fieldset>
		<legend>Report Parameters </legend>
			<table border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
				<tr>
					<td nowrap>Group ID <span class="mandatorySymbol">*</span></td>
					<td>
						<html:text property="groupId"	styleClass="textBox textBoxMedium" maxlength="60" onchange="javascript:onGroupChange();" style="background-color: #EEEEEE;" readonly="true"/>&nbsp;&nbsp;&nbsp; 
						<a href="#" onClick="javascript:onSelectGroup();"><img src="/ttk/images/EditIcon.gif" alt="Select Group" width="16" height="16" border="0" align="absmiddle"></a>
					</td>
					<td nowrap>Scheme No. <span class="mandatorySymbol">*</span></td>
					<td>
						<html:select property="policySeqID"  styleClass="selectBox selectBoxLargest" >
							<html:option value="">Select from list</html:option>
							<html:optionsCollection name="frmMISReports" property="alPolicyNo"  label="cacheDesc" value="cacheId" />							
		    			</html:select>
					</td>
				</tr>
				<tr>
					<td width="22%" class="formLabel">Report From <span class="mandatorySymbol">*</span></td>
					<td width="29%" class="formLabel">
						<html:text name="frmMISReports" styleClass="textBox textBoxSmall" property="sStartDate" maxlength="10"/>
						<a name="CalendarObjectReport_From" id="CalendarObjectReport_From"  href="#" onClick="javascript:show_calendar('CalendarObjectReport_From','forms[1].sStartDate',document.forms[1].sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="imgStart_Date" width="24" height="17" border="0" align="absmiddle"></a> 
					</td>
					<td width="22%" class="formLabel">Report To <span class="mandatorySymbol">*</span></td>
					<td width="29%" class="formLabel">
						<html:text name="frmMISReports" styleClass="textBox textBoxSmall" property="sEndDate" maxlength="10"/>
						<a name="CalendarObjectReport_To" id="CalendarObjectReport_To"  href="#" onClick="javascript:show_calendar('CalendarObjectReport_To','forms[1].sEndDate',document.forms[1].sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="imgStart_Date" width="24" height="17" border="0" align="absmiddle"></a> 
					</td>
				</tr>
				<tr>
        			<td class="formLabel">Status:<span class="mandatorySymbol">*</span></td>
        			<td>
			  			<select name="sStatus" class="selectBox selectBoxMedium" id="status">
			  				<option value="">Select from list</option>
			  				<option value="ALL">All</option>
							<option value="APR">Approved</option>
							<option value="INP">In-Progress</option>
							<option value="REQ">Required Information</option>
							<option value="PCN">Cancelled</option>
							<option value="REJ">Rejected</option>
						</select>
        			</td>
       		    </tr>
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
	<html:hidden property="fileName"/>
	<input type="hidden" name="mode" value="" />
	<input type="hidden" name="reportID" value="" />
</html:form>