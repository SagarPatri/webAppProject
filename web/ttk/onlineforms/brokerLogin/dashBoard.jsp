<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%
TTKCommon.setActiveLinks("Broker","Home","DashBoard",request);
%>
<head>
<style type="text/css">

</style>
<script type="text/javascript" src="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/onlineforms/brokerLogin/dashBoard.js"></script>
</head>
<html:form action="/BrokerAction.do" >
<html:errors/>
<br>
<div id="healthTip">
<span style="color: green;">*</span>  Make it a practice to eat slow
ensure eating slowly and thoughtfully at all
times. It uses all of the senses to explore , savor
and taste the food. Hence, it encourages healthy
choece and also helps you realize when you have
eaten enough.
</div>
<br>
<div id="sideHeading">Dashboard</div>
<br>
<table>
 <tr>
 <td valign="top">
<table id="textTableSmall">
<%
if(TTKCommon.isAuthorized(request,"Management"))
		    	{
	    	%>
     		
 <tr>
  <th>Number Of Policies</th><td><bean:write name="frmBroDashBoard" property="numberOfPolicies"/></td>
 </tr>
 <tr>
  <th>Number Of Lives</th><td><bean:write name="frmBroDashBoard" property="numberOfLives"/></td>
 </tr>
 <tr>
  <th>Total Gross Premium</th><td><bean:write name="frmBroDashBoard" property="totalGrossPremium"/></td>
 </tr>
<!--  <tr> -->
<%--   <th>Total Earned Premium</th><td><bean:write name="frmBroDashBoard" property="totalErnedPremium"/></td> --%>
<!--  </tr> -->
 <%
  }
 %>
</table>
 </td> 
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
 <td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
 <td valign="top">
<ul id="hexagonLinks">
	<li>
	<table>
	<tr>
	<td><img title="Corporate" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" href="#" title="Corporate" onclick="javascript:onClickLinks('Corporate');">Corporate</a></td>	
	</tr>
	</table>	
	</li>
	<li>
	<table>
	<tr>
	<td><img title="My Profile" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" href="#" title="My Profile" onclick="javascript:onClickLinks('Profile');">My Profile</a></td>	
	</tr>
	</table>
	</li>
	<li>
	<table>
	<tr>
	<td><img title="Reports" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" href="#" title="Reports" onclick="javascript:onClickLinks('Reports');">Reports</a></td>	
	</tr>
	</table>
	</li>
	<li>
	<table>
	<tr>
	<td><img title="Log Details" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" href="#" title="Log Details" onclick="javascript:onClickLinks('LogDetails');">Log Details</a></td>	
	</tr>
	</table>
	</li>
	<li>
	<table>
	<tr>
	<td><img title="Change Password" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" href="#" title="Change Password" onclick="javascript:onClickLinks('PasswordChange');">Change Password</a></td>
	</tr>
	</table>
	</li>
</ul>
</td>
</tr>
</table>

<input type="hidden" name="mode" value="">
<input type="hidden" name="tab" value="">
<input type="hidden" name="leftlink" value="">
<input type="hidden" name="sublink" value="">
<input type="hidden" name="linkMode" value="">

</html:form>
