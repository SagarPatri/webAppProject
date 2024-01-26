<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="com.ttk.common.TTKCommon" %>
<head>
<script type="text/javascript" src="/ttk/scripts/onlineforms/brokerLogin/policySelectionView.js"></script>
</head>
<%
TTKCommon.setActiveLinks("Broker","Corporate","Search",request);
%>
<html:form action="/BrokerCorporateSummaryAction.do" method="POST">
<div id="sideHeading">Summary/Detailed</div>
<br><br><br>
<table style="margin-left: 300px;margin-top: 150px;">
<tr>
<td>
<%
if(TTKCommon.isAuthorized(request,"Management"))
		    	{
	    	%>
<table>
	<tr>
	<td><img title="Summary" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a href="#" id="imgAncMedium"   title="Summary" onclick="javascript:onSelectedView('Summary');">Summary</a></td>
	</tr>
	</table>
	<%} %>
</td>
<td>
<table>
	<tr>
	<td><img title="Detailed" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" href="#" title="Detailed" onclick="javascript:onSelectedView('Detailed');">Detailed</a></td>
	</tr>
	</table>
</td>
<td>
<table>
	<tr>
	<td><img title="Back" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" href="#" title="Back" onclick="javascript:onSelectedView('Back');">Back</a></td>
	</tr>
	</table>
</td>
</tr>
</table>
<input type="hidden" name="mode"/>
<input type="hidden" name="btnMode"/>
</html:form>