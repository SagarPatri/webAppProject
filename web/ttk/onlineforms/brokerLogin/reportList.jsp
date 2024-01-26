<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<head>
<script type="text/javascript" src="/ttk/scripts/onlineforms/brokerLogin/reportList.js"></script>
</head>
<html:form action="/BrokerReportsAction.do" >
<html:errors/>
<div id="sideHeading">Reports</div>
<br><br>
<ul id="ulLiAnc">
	<li>
	<a href="#" onclick="showReports('BroClaimUtilizationRep','Claim Utilization Report');">Claim Utilization Report</a>
	</li>
	<li>
	<a href="#" onclick="showReports('BroAuthUtilizationRep','Auth Utilization Report');">Auth Utilization Report</a>
	</li>
	<li>
	<a href="#" onclick="showReports('BroTATForClaims','TAT For Claims');">TAT For Claims</a>
	</li>
	<li>
	<a href="#" onclick="showReports('BroTATForPreAuth','TAT For Pre-Auth');">TAT For Pre-Auth</a>
	</li>
	<!-- As per UAT<li>
	<a href="#" onclick="showReports('BroTechnicalResultReport','Technical Result Report');">Technical Result Report</a>
	</li>
	<li>
	<a href="#" onclick="showReports('BroTATFSCORPOARTE','TAT Enrollment Corporate');">TAT Enrollment Corporate</a>
	</li> -->
<!-- 	<li> -->
<!-- 	<a href="#" onclick="showReports('BroTATFSINDIVIDUAL','TAT Enrollment Individual');">TAT Enrollment Individual</a> -->
<!-- 	</li> -->
<!-- 	<li> -->
<!-- 	<a href="#" onclick="showReports('BroHIPA','HIPA');">HIPA</a> -->
<!-- 	</li> -->
	</ul>	
	<br><br>
	<table style="margin-left: 45%;">
	<tr>
	<td><img title="Back" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" onclick="onBack();" href="#" title="Back">Back</a></td>
	</tr>
	</table>
<input type="hidden" name="mode">
<html:hidden property="reportID"/>
<html:hidden property="reportLabel"/>
</html:form>
