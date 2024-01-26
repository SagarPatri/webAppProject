<%
/** @ (#) financereportlist.jsp
 * Project     : TTK Healthcare Services
 * File        : financereportlist.jsp
 * Author      : Balaji C R B
 * Company     : Span Systems Corporation
 * Date Created: 5th August,2008
 * @author 		 : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<script language="javascript" src="/ttk/scripts/misreports/financereportlist.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/MISFinanceReportsListAction.do">
<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>Finance Reports</td>
	</tr>
	</table>
<!-- E N D : Page Title -->
	<html:errors/>
	
<div class="contentArea" id="contentArea">
<!-- S T A R T : AllReportsList -->
    <fieldset>
    	<legend>Finance Reports</legend>
	    <table class="formContainer" align="left"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
	    <td class="formLabel">
		 <ul class="liBotMargin">
	    <!-- <li class="liPad"><a href="#" onClick="javascript:onSelectDoBoClaimsDetail()">DoBo Claims Detail</a></li>
	    <li class="liPad"><a href="#" onClick="javascript:onSelectFGPendingReport()">Payment Pending Report</a></li>
	    <li class="liPad"><a href="#" onClick="javascript:onSelectOrientalPaymentAdvice()">Payment Advice Report</a></li>
	    <li class="liPad"><a href="#" onClick="javascript:onSelectCitibankClaimsDtsRpt()">CitiBank Claims Detail Report</a></li>
	    <li class="liPad"><a href="#" onClick="javascript:onUniversalSampo()">Universal Sompo Pending Report</a></li> -->
	    
	    
	    <li class="liPad"><a href="#" onClick="javascript:onSelectDtlRpt()">Paid Report</a></li>
	    <li class="liPad"><a href="#" onClick="javascript:onPendingReport()">Pending Report</a></li>
	    <li class="liPad"><a href="#" onClick="javascript:onBankAcc()">Bank Account Details</a></li>
	    <li class="liPad"><a href="#" onClick="javascript:onRevenueReport()">Invoice Report for Revenue</a></li>
        <li class="liPad"><a href="#" onClick="javascript:onRemittanceAdvice()">Remittance Advice</a></li>
          <li class="liPad"><a href="#" onClick="javascript:onExchangeRates()">Exchange rates</a></li>
	    <!-- <li class="liPad"><a href="#" onClick="javascript:onHospitals()">Hospitals - Bank Account Details</a></li> -->
	    </ul>
	    </td>
    	</tr>
	    </table>
    </fieldset>
<!-- E N D : AllReportsList -->
</div>
    <INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	</html:form>
<!-- E N D : Content/Form Area -->