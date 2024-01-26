<%
/** @ (#) detailreportparam.jsp
 * Project     : TTK Healthcare Services
 * File        : detailreportparam.jsp
 * Author      :  Rishi Sharma
 * Company     : RCS Technologies
 * Date Created:  11 May 2017
 * @author 		 :Rishi Sharma
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/empanelreport.js"></script>

<html:form action="/EmpanelReportsListAction.do">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>All Empanelment Reports List</td>
    	
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
		<td nowrap>Remittance Advice  Uploaded :<span class="mandatorySymbol">*</span>
		<html:text property="sremittancefrmDate" styleId="sremittancefrmDate"  styleClass="textBox textDate" maxlength="10" />
		<a name="CalendarObjectremittancefrmDate" id="CalendarObjectremittancefrmDate" href="#" onClick="javascript:show_calendar('CalendarObjectremittancefrmDate','frmEmpanelReport.sremittancefrmDate',document.frmEmpanelReport.sremittancefrmDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
			<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
		</a>
		<br>&nbsp;&nbsp;&nbsp;&nbsp;from Date
     		</td>
     	
     		<td nowrap>Remittance Advice  Uploaded :<span class="mandatorySymbol">*</span>
		<html:text property="sremittancetoDate" styleId="sremittancetoDate" styleClass="textBox textDate" maxlength="10" value=""/>
		<a name="CalendarObjectremittancetoDate" id="CalendarObjectremittancetoDate" href="#" onClick="javascript:show_calendar('CalendarObjectremittancetoDate','frmEmpanelReport.sremittancetoDate',document.frmEmpanelReport.sremittancetoDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
			<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
		</a>
		<br>&nbsp;&nbsp;&nbsp;&nbsp;to Date
     		</td>
     	
     	  <td nowrap>Remittance Advice File Name:
            <html:text property="sremittanceFileName" styleId="sremittanceFileName" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        	<br>&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
     	</tr>
     	<tr>
     	  <td nowrap>Provider ID :
            <html:text property="sproviderID" styleId="sproviderID"  styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
        
        <td nowrap>Provider Name :
            <html:text property="sproviderName" styleId="sproviderName" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>	
        
          <td nowrap>Provider Empanelment:
            <html:text property="sproviderEmanelmentNumber" styleId="sproviderEmanelmentNumber"  styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
           <br>Number
        </td>
      
        </tr>
        <tr>
          <td nowrap>Payment Reference Number:
            <html:text property="spaymentRefChequeNumber" styleId="spaymentRefChequeNumber" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
         <br>(Cheque Number)
        </td>
      
        <td></td>
        <td></td>
        </tr>	
		
	
	</table>
	
	</fieldset>
	
	<!-- End of parameter grid -->
	<!-- Start of Report Type - PDF/EXCEL list and generate button -->
	<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%" align="center">
	
			<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateDetailReport();"><u>G</u>enerateReport</button>

		&nbsp;&nbsp;&nbsp;&nbsp;
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		</td>
	</tr>
    </table>
		</div>
	<!-- End of Report Type - PDF/EXCEL list and generate button -->
<!-- End of form fields -->	
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>