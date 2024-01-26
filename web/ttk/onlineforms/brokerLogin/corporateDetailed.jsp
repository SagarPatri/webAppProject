<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<head>
<script type="text/javascript" src="/ttk/scripts/onlineforms/brokerLogin/corporateDetailed.js"></script>
</head>
<html:form action="/BrokerCorporateDetailedAction.do" >
<%
String priID="imgAncLarge";
String clmID="imgAncLarge";
String patID="imgAncLarge";
String tobID="imgAncLarge";
String endID="imgAncLarge";
%>
<html:errors/>
<logic:equal value="PRI" property="summaryView" name="frmBroDashBoard">
<%priID="imgAncLargeClicked"; %>
</logic:equal>
<logic:equal value="CLM" property="summaryView" name="frmBroDashBoard">
<%clmID="imgAncLargeClicked"; %>
</logic:equal>
<logic:equal value="CLD" property="summaryView" name="frmBroDashBoard">
<%clmID="imgAncLargeClicked"; %>
</logic:equal>
<logic:equal value="PAT" property="summaryView" name="frmBroDashBoard">
<%patID="imgAncLargeClicked"; %>
</logic:equal>
<logic:equal value="PAD" property="summaryView" name="frmBroDashBoard">
<%patID="imgAncLargeClicked"; %>
</logic:equal>
<logic:equal value="TOB" property="summaryView" name="frmBroDashBoard">
<%tobID="imgAncLargeClicked"; %>
</logic:equal>
<logic:equal value="END" property="summaryView" name="frmBroDashBoard">
<%endID="imgAncLargeClicked"; %>
</logic:equal>
<div id="sideHeading">Corporate-Detailed</div>
<br><br>
<table>
<tr>
<td valign="top">
<ul style="list-style: none;">
 <li id="liTabs">
 <a href="#" id="<%=priID%>"   title="PERSONAL INFORMATION" onclick="javascript:onDetailedView('PRI');">PERSONAL INFO</a>
 </li>
 <li id="liTabs">
 <a href="#" id="<%=clmID%>"   title="CLAIMS" onclick="javascript:onDetailedView('CLM');">CLAIMS</a>
 </li>
 <li id="liTabs">
 <a href="#" id="<%=patID%>"   title="PREAUTHORIZATION" onclick="javascript:onDetailedView('PAT');">PREAUTHORIZATION</a>
 </li>
 <li id="liTabs">
 <a href="#" id="<%=tobID%>"   title="TOB" onclick="javascript:onTob();">TOB</a>
 </li>
 <li id="liTabs">
 <a href="#" id="<%=endID%>"   title="Endorsements" onclick="javascript:onEndorsements();">ENDORSEMENT</a>
 </li>
</ul>
</td>
<td valign="top">
<ttk:BrokerDetailedViews/>
</td>
</tr>
</table>
<br>
<bean:define id="summaryViewID" name="frmBroDashBoard" property="summaryView"/>
<table align="center">
	<tr>
	<td><img title="Back" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td>
	<%if("CLD".equals(summaryViewID)){ %>
	<a id="imgAncMedium" onclick="onDetailedView('CLM');" href="#" title="Back">Back</a>
	<%}else if("PAD".equals(summaryViewID)){ %>
	<a id="imgAncMedium" onclick="onDetailedView('PAT');" href="#" title="Back">Back</a>
	<%}else{%>
	<a id="imgAncMedium" onclick="onBack('MemSearch');" href="#" title="Back">Back</a>
	<%} %>
	</td>
	</tr>
	</table>


<html:hidden property="summaryView" name="frmBroDashBoard"/>
<html:hidden property="claimNO" name="frmBroDashBoard"/>
<html:hidden property="preAuthNO" name="frmBroDashBoard"/>
<input type="hidden" name="mode" value="">
<input type="hidden" name="selIndex" value="">
<input type="hidden" name="backID" value="">
<input type="hidden" name="fileName">
</html:form>
