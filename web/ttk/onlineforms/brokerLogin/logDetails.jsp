<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<head>
<script type="text/javascript" src="/ttk/scripts/onlineforms/brokerLogin/logDetails.js"></script>
</head>
<html:form action="/BrokerAction.do" >
<html:errors/>
<div id="sideHeading">Log Details</div>
<br><br>

<table id="textTableSmall" style="margin-left: 30%;width: 600px;">
<tr>
<th>S.NO</th>
<th>User ID</th>
<th>User Name</th>
<th>Loged On</th>
</tr>
<logic:iterate id="broVO1" name="broLogDetails" scope="session">
<tr>
<td> <bean:write name="broVO1" property="serialNO"/></td>
<td><bean:write name="broVO1" property="userID"/></td>
<td><bean:write name="broVO1" property="contactName"/></td>
<td><bean:write name="broVO1" property="loginDate"/></td>
</tr>
</logic:iterate>
</table>

	<br><br>
	<table style="margin-left: 45%;">
	<tr>
	<td><img title="Back" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" onclick="onBack();" href="#" title="Back">Back</a></td>
	</tr>
	</table>
<input type="hidden" name="mode" value="">
</html:form>
