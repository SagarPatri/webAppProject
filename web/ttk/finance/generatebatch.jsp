<%
/**
 * @ (#) generatebatch.jsp 21st April 2010
 * Project      : TTK HealthCare Services
 * File         : chequeseries.jsp
 * Author       : Balakrishna E
 * Company      : Span Systems Corporation
 * Date Created : 21st April 2010
 *
 * @author       : Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%
  pageContext.setAttribute("tdsbatchQuarter",Cache.getCacheObject("tdsbatchQuarter"));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/generatebatch.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>

<html:form action="/GenerateBatchAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>Generate Batch</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div style="width: 99%; float: right;">
	<div class="scrollableGrid" style="height:290px;">
	<html:errors />
	<!-- S T A R T : Success Box --> 
	<logic:notEmpty name="updated"	scope="request">
		<table align="center" class="successContainer" style="display: " border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
					width="16" height="16" align="absmiddle">&nbsp; <bean:message
					name="updated" scope="request" /></td>
			</tr>
		</table>
	</logic:notEmpty> <!-- E N D : Success Box --> <!-- S T A R T : Form Fields --> 
	<logic:notEmpty name="failed" scope="request">
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   		<tr>
	     		<td>
	     			<img src="/ttk/images/Error.gif" alt="Error" width="16" height="16" align="absmiddle">&nbsp;
	         		<bean:message name="failed" scope="request"/>
	     		</td>
	   		</tr>
	  	</table>
	</logic:notEmpty>
	<br>
	<!-- S T A R T : Grid --> 
	<ttk:HtmlGrid name="tableData" className="gridWithCheckBox zeroMargin" /> 
	<!-- E N D : Grid -->
	</div>
	<!-- S T A R T : Buttons -->
	<fieldset>
	<legend>Generate Batch Details</legend>
	<table class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="19%" align="left" nowrap>&nbsp;&nbsp;Financial Year:<span class="mandatorySymbol">*</span></td>
			<td width="26%" align="left" nowrap>
						  <html:text name="frmGenerateBatch" property="financeYear" styleClass="textBox textBoxSmall" maxlength="4" onblur="javascript:finendyear(document.forms[1].financeYear.value);"/>
          		- <html:text property="finYearTo" readonly="true" styleClass="textBox textBoxTiny textBoxDisabled" />&nbsp;</td>
          	<td width="10%" align="left" nowrap>&nbsp;&nbsp;Frequency:<span class="mandatorySymbol">*</span></td>
          	<td width="26%" align="left" nowrap>
             <html:select property="tdsbatchQtr"  styleClass="selectBox selectBoxMedium">
                <html:option value="">Select from list</html:option>
              	<html:optionsCollection name="tdsbatchQuarter" value="cacheId" label="cacheDesc"/>              
             </html:select>
            </td>
			<td width="100%" valign="bottom" nowrap class="formLabel">
			<button type="button" name="Button2" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onGenerate()"><u>G</u>enerate</button>&nbsp;
			<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"	onClick="onClose()"><u>C</u>lose</button>&nbsp;</td>
		</tr>
	</table>
	</fieldset>
	</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<html:hidden property="seqID" />
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
</html:form>
