<%
/** @ (#) detailreportparam.jsp
 * Project     : TTK Healthcare Services
 * File        : detailreportparam.jsp
 * Author      : Kishor kumar S H
 * Company     : RCS Technologies
 * Date Created: August 5,2008
 * @author 		 : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%
	pageContext.setAttribute("insuranceCompany", Cache.getCacheObject("insuranceCompany"));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/detailreportparam.js"></script>

<html:form action="/MISFinanceReportsAction.do">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td><% String temp=(String)request.getParameter("repType");
    	out.print(request.getParameter("repType")); %> </td>
	</tr>
</table>
<!-- E N D : Page Title -->
<html:errors/>
<!-- Start of form fields -->
	<!-- Start of Parameter grid -->
	<div class="contentArea" id="contentArea">
	<fieldset>
	 <legend>Report Parameters </legend>
	  <table border="0" align="center" cellpadding="0" cellspacing="0" class="searchContainer">
		<tr>
		 
		<td nowrap>Insurance Company:<br>
			<html:select property="insuranceCompany" styleClass="selectBox selectBoxMedium">
		  	 	  <html:option value="">Any</html:option>
		          <html:optionsCollection name="insuranceCompany" label="cacheDesc" value="cacheId" />
            </html:select>
	    </td>
	    
	    <td nowrap>Float Account No. :<br>
            <html:text property="sFloatAccNo" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
        
        <td nowrap>Debit Note No. :<br>
            <html:text property="fDebitNoteNo" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
	</tr>
	
	<tr>

	    <td nowrap>Provider Name :<br>
            <html:text property="sProviderName" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
        <td nowrap>Corporate Name :<br>
            <html:text property="fCorpName" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
        <logic:equal value="pending" scope="request" name="logicType">
        <td nowrap>Group Id :<br>
            <html:text property="fGroupId" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
        </logic:equal>
        <logic:equal value="detailed" scope="request" name="logicType">    
        <td nowrap>Group Id :<br>
            <html:text property="fGroupId" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
        </logic:equal>
    </tr>
        
    <tr>
        <td nowrap>From Date:<span class="mandatorySymbol">*</span><br>
		<html:text property="sChequeFromDate" styleClass="textBox textDate" maxlength="10" value=""/>
		<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmMISFinanceReports.sChequeFromDate',document.frmMISFinanceReports.sChequeFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
			<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
		</a>
     		</td>
	<%-- <logic:notEqual value="pending" scope="request" name="logicType">
	<td nowrap>As on Date:<br>
		<html:text property="sChequeToDate" styleClass="textBox textDate" maxlength="10" value=""/>
		<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmMISFinanceReports.sChequeToDate',document.frmMISFinanceReports.sChequeToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
			<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
		</a>
     		</td>
	</logic:notEqual> --%>
	
    	<td nowrap>To Date:<span class="mandatorySymbol">*</span><br>
		<html:text property="sChequeToDate" styleClass="textBox textDate" maxlength="10" value=""/>
		<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmMISFinanceReports.sChequeToDate',document.frmMISFinanceReports.sChequeToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
			<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
		</a>
     		</td>
	</tr>
	</table>
	
	</fieldset>
	
	<!-- End of parameter grid -->
	<!-- Start of Report Type - PDF/EXCEL list and generate button -->
	<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%" align="center">
		<logic:equal value="detailed" scope="request" name="logicType">
			<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateDetailReport();"><u>G</u>enerateReport</button>
		</logic:equal>
		<logic:equal value="pending" scope="request" name="logicType">
			<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGeneratePendingReport();"><u>G</u>enerateReport</button>
		</logic:equal>
			&nbsp;
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		</td>
	</tr>
    </table>
		</div>
	<!-- End of Report Type - PDF/EXCEL list and generate button -->
<!-- End of form fields -->	
<input type="hidden" name="mode">
</html:form>