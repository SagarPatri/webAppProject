
<%
/** @ (#) allreportslist.jsp May 18, 2007
 * Project     : TTK Healthcare Services
 * File        : allreportslist.jsp
 * Author      : Ajay Kumar
 * Company     : WebEdge Technologies Pvt.Ltd.
 * Date Created: 
 *
 * @author 		 : Ajay Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>


<script language="javascript" src="/ttk/scripts/misreports/enrollmentreportslist.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/EnrolReportsListAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>All Enrollment Reports List</td>
    	
	</tr>
</table>
<!-- E N D : Page Title -->
<html:errors/>
<div class="contentArea" id="contentArea">
<!-- S T A R T : AllReportsList -->
     <fieldset>
    <legend>Report Links</legend>
    <table class="formContainer" align="left"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td class="formLabel">
	 <ul class="liBotMargin">
<!--      <li class="liPad"><a href="#" onClick="javascript:onSelectEnrolMonitor()">Enrollment Monitor</a></li>
     <li class="liPad"><a href="#" onclick="javascript:onAccentureEnrolReport()">Group Enrollment Detail Report</a></li>
    <li class="liPad"><a href="#" onClick="javascript:onPremiumReport()">Premium Report</a></li> 
    <li class="liPad"><a href="#" onClick="javascript:onClaimSummary()">Claim Summary</a></li>-->
    <li class="liPad"><a href="#" onClick="javascript:onEnReport()">Enrollment Report</a></li>
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