<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<head>
<script type="text/javascript" src="/ttk/scripts/onlineforms/brokerLogin/corporateSummary.js"></script>
</head>
<html:form action="/BrokerCorporateSummaryAction.do" >
<%
String enrID="imgAncLarge";
String clmID="imgAncLarge";
String patID="imgAncLarge";
%>
<html:errors/>
<logic:equal value="ENR" property="summaryView" name="frmBroDashBoard">
<%enrID="imgAncLargeClicked"; %>
</logic:equal>
<logic:equal value="CLM" property="summaryView" name="frmBroDashBoard">
<%clmID="imgAncLargeClicked"; %>
</logic:equal>
<logic:equal value="PAT" property="summaryView" name="frmBroDashBoard">
<%patID="imgAncLargeClicked"; %>
</logic:equal>
<div id="sideHeading">Corporate-Summary</div>
<br><br>
<table>
<tr>
<td valign="top">
<ul style="list-style: none;">
 <li id="liTabs">
 <a href="#" id="<%=clmID%>"   title="CLAIMS" onclick="javascript:onSummaryView('CLM');">CLAIMS</a>
 </li>
 <li id="liTabs">
 <a href="#" id="<%=patID%>"   title="PREAUTHORIZATION" onclick="javascript:onSummaryView('PAT');">PREAUTHORIZATION</a>
 </li>
 <li id="liTabs">
 <a href="#" id="<%=enrID%>"   title="ENROLEMENT" onclick="javascript:onSummaryView('ENR');">ENROLEMENT</a>
 </li>
</ul>
</td>
<td valign="top">
<ttk:BrokerSummaryViews/>
</td>
</tr>
</table>
<table align="center">
	<tr>
	<td><img title="Back" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" onclick="onBack('SelectionView');" href="#" title="Back">Back</a></td>
	</tr>
	</table>
<html:hidden property="summaryView" name="frmBroDashBoard"/>
<input type="hidden" name="mode" value="">
<input type="hidden" name="rownum" value="">
<input type="hidden" name="sortId" value="">
<input type="hidden" name="pageId" value="">
<input type="hidden" name="child" value="">
<input type="hidden" name="backID" value="">
</html:form>
