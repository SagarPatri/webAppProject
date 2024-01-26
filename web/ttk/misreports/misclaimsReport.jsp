

<%
/** @ (#) misclaimsReport.jsp 09th Aug 2015
 * Project     : TTK Healthcare Services
 * File        : misclaimsReport.jsp
 * Author      : Kishor kumar S H
 * Company     : RCS Technologies
 * Date Created: 
 *
 * @author 		 : Kishor kumar S H
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



<script language="javascript" src="/ttk/scripts/misreports/preauthreportslist.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/PreAuthReportsListAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td> Reports List</td>
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
    <li class="liPad"><a href="#" onClick="javascript:onSelectClaimAnalysis()">Claim Analysis</a></li>
    <li class="liPad"><a href="#" onClick="javascript:onSelectTechincalResults()">Techincal Results</a></li>
    <li class="liPad"><a href="#" onClick="javascript:onSelectTreatyStatistics()">Treaty Statistics </a></li>
    </ul>
    </td>
    </tr>
    </table>
    </fieldset>
    
<!-- E N D : AllReportsList -->
</div>

    <INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	
	
	</html:form>
<!-- E N D : Content/Form Area -->