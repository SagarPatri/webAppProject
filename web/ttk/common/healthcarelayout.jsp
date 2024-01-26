<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<title>Vidal Healthcare Administrator</title>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile" errorPage="/ttk/common/error.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<head>
<title>Vidal Healthcare Administrator - List of Healthcare Companies</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=7">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9"/>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/trackdatachanges.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/common/healthcarelayout.js"></SCRIPT>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<SCRIPT LANGUAGE="JavaScript">
var bAction = true;
ClientReset = true;
var JS_SecondSubmit=false;
</SCRIPT>
<script language="JavaScript">
function disableCtrlModifer(evt)
{
	var disabled = {a:0, x:0,w:0, n:0, F4:0, j:0};
	var ctrlMod = (window.event)? window.event.ctrlKey : evt.ctrlKey;
	var key = (window.event)? window.event.keyCode : evt.which;
	key = String.fromCharCode(key).toLowerCase();
	return (ctrlMod && (key in disabled))? false : true;
}//end of disableCtrlModifer(evt)

</script>
</head>
<!-- Changes Added onunload in body for Password Policy CR KOC 1235 -->
<body onunload="javascript:logoutwindow(event);" onLoad="javascript:resizeDocument();TrackFormData('webBoard');handleFocus();window.history.forward(1);<%=com.ttk.common.TTKCommon.checkNull(request.getAttribute("OLB_JS_FUNCTION")) %>" onkeypress="return disableCtrlModifer(event);" onkeydown="return disableCtrlModifer(event);">
<logic:empty name="UserSecurityProfile">
	<script language="javascript">
			openFullScreenMode("/ttk/index.jsp?sessionexpired=true");
	</script>
</logic:empty>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
<tr>
	<td>
		<!-- S T A R T : Header Section-->
		<tiles:insert attribute="header"/>
	</td>
</tr>
<tr>
<td>
		<!-- S T A R T : Main Container Table -->
		<table width="100%" id="mainContainerTable" class="mainContainerTable" border="0" cellspacing="0" cellpadding="0">
		  <tr>
<!-- start Modifications as per Multiple Browser  -->
		  <%
		  UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
		  if(userSecurityProfile.getLoginType().equalsIgnoreCase("U") || userSecurityProfile.getLoginType().equalsIgnoreCase("E"))
		  {%>
			   <td width="163" class="leftNavigationBandWeblogin" nowrap valign="top">
			  	<!-- S T A R T : Left Navigation Menu -->
			   	<tiles:insert attribute="leftlinks"/>
		    </td>
		  <%}else
		  {
			  %><td width="163" class="leftNavigationBand" nowrap valign="top">
			  	<!-- S T A R T : Left Navigation Menu -->
			  	<tiles:insert attribute="leftlinks"/>
		    </td>
		  <%}
		  %>
		  		 <!-- END Modifications as per Multiple Browser  -->
		    <td width="100%"  valign="top">


			<!-- S T A R T : Tab Navigation -->
			<tiles:insert attribute="tablinks"/>
			<!-- S T A R T : Content Section-->
			<tiles:insert attribute="content"/>

		    </td>
		  </tr>
		</table>
	</td>
</tr>
<tr>
	<td>
		<!-- S T A R T : Footer Section -->
		<tiles:insert attribute="footer"/>
		<!-- E N D : Main Container Table -->
	</td>
</tr>
</table>
</body>