<%
/** @ (#) callcenterreportlist.jsp 30th July 2008
 * Project     : TTK Healthcare Services
 * File        : callcenterreportlist.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 30th July 2008
 *
 * @author 		 : Chandrasekaran J
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


<script language="javascript" src="/ttk/scripts/misreports/callcenterreportlist.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/CallCenterPendingRptAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>Call Center Reports</td>
	</tr>
</table>
<!-- E N D : Page Title -->
<html:errors/>
<div class="contentArea" id="contentArea">
<!-- S T A R T : AllReportsList -->
     <fieldset>
    <legend>Call Center Reports</legend>
    <table class="formContainer" align="left"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td class="formLabel">
	 <ul class="liBotMargin">
     <li class="liPad"><a href="#" onClick="javascript:onSelectCallPendingReport()">Call Pending Report</a></li>
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