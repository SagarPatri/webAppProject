<%
/** @ (#) iobreportparam.jsp July 18, 2008
 * Project     : TTK Healthcare Services
 * File        : iobreportparam.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: July 18, 2008
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ page import="com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/reports/iobreportparam.js"></script>
<%
	pageContext.setAttribute("iobSelectBatch",Cache.getCacheObject("iobSelectBatch"));
%>
<html:form action="/IOBReportsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>Enrollment Reports - IOB Reports</td>
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
						<html:text property="groupId"	styleClass="textBox textBoxMedium" maxlength="60" />&nbsp;&nbsp;&nbsp; 
						<a href="#" onClick="javascript:onSelectGroup();"><img src="/ttk/images/EditIcon.gif" alt="Select Group" width="16" height="16" border="0" align="absmiddle"></a>
					</td>
					<td nowrap>Batch</td>
					<td>
						<html:select property="selectBatch"  styleClass="selectBox selectBoxMedium" onchange="javascript:onChangeBatch();">
							<html:option value="">Any</html:option>
	  						<html:options collection="iobSelectBatch"  property="cacheId" labelProperty="cacheDesc"/>
		    			</html:select>
					</td>
				</tr>
				<tr style="display:none;" id="batch">
					<td width="22%" class="formLabel">Batch No. </td>
					<td width="29%" class="formLabel">
						<html:text name="frmReportList" styleClass="textBox textBoxMedium" property="batchNo" maxlength="250" style="background-color: #EEEEEE;" readonly="true"/>&nbsp;&nbsp;&nbsp;
							<a href="#" onClick="javascript:onBatchNumber();"><img src="/ttk/images/EditIcon.gif" alt="Batch No" width="16" height="16" border="0" align="absmiddle"></a>
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
				<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateIOBReport();"><u>G</u>enerateReport</button>
				&nbsp;
				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
			</td>
		</tr>
	</table>
	<!-- End of Reports List -->
	</div>	
	<html:hidden property="reportName"/>
	<input type="hidden" name="mode" value="" />
	<input type="hidden" name="reportID" value="" />
	<script language="javascript">
	onLoadBatch();
	</script>
</html:form>