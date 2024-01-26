
<%
/** @ (#) allreportslist.jsp May 18, 2007
 * Project     : TTK Healthcare Services
 * File        : empanelreportlist.jsp
 * Author      : Rishi Sharma
 * Company     : RCS TECHNOLOGIES
 * Date Created: 
 *
 * @author 		 : RISHI sHARMA
 * Modified by   :
 * Modified date : 12-05-2017
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>


<script language="javascript" src="/ttk/scripts/misreports/empanelreportlist.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/EnrolReportsListAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>All Empanelment Reports List</td>
    	
	</tr>
</table>
<!-- E N D : Page Title -->
<html:errors/>
<div class="contentArea" id="contentArea">
<!-- S T A R T : AllReportsList -->
     <fieldset>
    <legend>Empanelment Reports</legend>
    <table class="formContainer" align="left"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td class="formLabel">
	 <ul class="liBotMargin">

    <li class="liPad"><a href="#" onClick="javascript:onRemittanceReport()">Remittance Advice Report</a></li>
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