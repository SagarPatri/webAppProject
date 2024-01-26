<%
/** @ (#) batchreport.jsp Jul 7, 2008
 * Project     : TTK Healthcare Services
 * File        : batchreport.jsp
 * Author      : Sendhil Kumar V
 * Company     : Span Systems Corporation
 * Date Created: Jul 7, 2008
 *
 * @author 		 : Sendhil Kumar V
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
<script language="javascript" src="/ttk/scripts/reports/batchlist.js"></script>
<%
	pageContext.setAttribute("claimspending",Cache.getCacheObject("claimspending"));
	pageContext.setAttribute("paymentMethod",Cache.getCacheObject("paymentMethod"));
%>
<html:form action="/ReportsAction.do">

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><%=TTKCommon.getActiveSubLink(request)%> Reports - <bean:write name="frmReportList" property="reportName"/></td>
		</tr>
	</table>
	<div class="contentArea" id="contentArea">
	<!-- Start of Form Fields -->
    <table border="0" align="center" cellpadding="0" cellspacing="0" class="tablePad">
         <tr>
         <td width="13%" nowrap class="textLabelBold" align="center">Select Type </td>
         <td width="31%" align="center">
         	<html:select property="selectRptType" styleId="select" styleClass="selectBox selectBoxMedium" onchange="javascript:onSelectType()">
            	<html:options collection="claimspending" property="cacheId" labelProperty="cacheDesc"/>
            </html:select>
          </td>
          <td>&nbsp;</td><td>&nbsp;</td>
          <td>&nbsp;</td><td>&nbsp;</td>
         </tr>
    </table>
	<fieldset>
	<legend>Report Parameters </legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="formLabel">Batch Number <span class="mandatorySymbol">*</span></td>
			<td>
				<html:text property="batchNo" maxlength="250" styleClass="textBox textBoxMedium" />
				<a href="#" onClick="javascript:onBatchNumber();"><img src="/ttk/images/EditIcon.gif" alt="Batch Number" width="16" height="16" border="0" align="absmiddle"></a>
			</td>
			<logic:notMatch name="frmReportList" property="selectRptType" value="CAC">
				<td class="formLabel">Payment Mode </td>
				<td>
					<html:select property="payMode"  styleId="selectMode" styleClass="selectBox selectBoxMedium" style="background-color:" disabled="false">
	          			<html:options collection="paymentMethod"  property="cacheId" labelProperty="cacheDesc"/>
	          		</html:select>
				</td>
			</logic:notMatch>
			<logic:match name="frmReportList" property="selectRptType" value="CAC">
				<td class="formLabel">Payment Mode </td>
				<td>
					<html:select property="payMode"  styleId="selectMode" styleClass="selectBox selectBoxMedium" style="background-color: #EEEEEE;" disabled="true">
	          			<html:options collection="paymentMethod"  property="cacheId" labelProperty="cacheDesc"/>
	          		</html:select>
				</td>
			</logic:match>
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
