<%
/** @ (#) tpareportparameters.jsp July 4, 2006
 * Project     : TTK Healthcare Services
 * File        : tpareportparameters.jsp
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
<%
	String strSubLink=TTKCommon.getActiveSubLink(request);
	pageContext.setAttribute("strSubLink",strSubLink);
	pageContext.setAttribute("listInsuranceCompany",Cache.getCacheObject("insuranceCompany"));
%>
<SCRIPT LANGUAGE="JavaScript">
function changeOffice()
{
	document.forms[1].mode.value="doBatchChangeOffice";
	document.forms[1].action="/TPAComReportsAction.do";
	document.forms[1].submit();
}//end of changeOffice()

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/TPAComReportsAction.do";
	document.forms[1].submit();
}
function onGenerateReport()
{
	trimForm(document.forms[1]);	
	if(document.forms[1].invNumber.value=="" || document.forms[1].insuranceSeqID.value=="")
	{
		alert("Please enter the mandatory fields");
	}//end of if(document.forms[1].invNumber.value=="" || document.forms[1].insuranceSeqID.value=="")
	else{
	var parameterValue = "|"+document.forms[1].invNumber.value+"|"+document.forms[1].insuranceSeqID.value+"|";
	var openPage = "/GenerateReportsAction.do?mode=doTPACommissionRpt&reportType=EXL&parameter="+parameterValue;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	}//end of else
}//end of onGenerateReport()
</SCRIPT>
<html:form action="/TPAComReportsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<td><%=TTKCommon.getActiveSubLink(request)%> Reports - <bean:write
				name="frmReportList" property="reportName" /></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea"><!-- S T A R T : Form Fields -->
	<fieldset>
    <legend>Healthcare Company</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
         <td width="22%" class="formLabel">Healthcare Company:</td>
        <td width="29%" class="textLabelBold"><bean:write property="companyName" name="frmReportList"/></td>
        <td width="20%" class="formLabel">Company Code: <span class="mandatorySymbol">*</span></td>
        <td width="31%" class="textLabelBold"><bean:write property="officeCode" name="frmReportList"/>&nbsp;&nbsp;&nbsp;
       
        <a href="#" onClick="javascript:changeOffice();"><img src="/ttk/images/EditIcon.gif" alt="Change Office" width="16" height="16" border="0" align="absmiddle"></a>
       
    
        </td>
      </tr>
      <tr>
			<td class="formLabel">Invoice Number <span class="mandatorySymbol">*</span>
			</td>
			<td><html:text name="frmReportList" property="invNumber" maxlength="250" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);"/></td>
		</tr>
    </table>
	</fieldset>
	

	<table align="center" class="buttonsContainer" border="0"
		cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="center">Report Type <select name="reportType"
				class="selectBox" id="reporttype">
				<option value="PDF">PDF</option>
				<option value="EXL">EXCEL</option>
			</select> &nbsp;
			<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"
				onMouseover="this.className='buttons buttonsHover'"onClick="javascript:onGenerateReport();"><u>G</u>enerate Report</button>
			&nbsp;
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'"
				onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
			</td>
		</tr>
	</table>
	<!-- End of Reports List --></div>	
	<input type="hidden" name="mode" value="" />
	<input type="hidden" name="reportName" value="" />	
	<input type="hidden" name="insuranceSeqID" value="<bean:write name="frmReportList" property="insuranceSeqID"/>" />
</html:form>
