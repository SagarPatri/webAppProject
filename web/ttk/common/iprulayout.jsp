<%
/** @ (#) iprulayout.jsp 13th Nov 2008
 * Project     : TTK Healthcare Services
 * File        : iprulayout
 * Author      : Ramakrishna K M
 * Company     : Span Systems Corporation
 * Date Created: 13th Nov 2008
 *
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 *
 */
%>
<%@ taglib uri="/WEB-INF/struts-tiles.tld" prefix="tiles" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<title>IPRU Weblogin Access</title>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile" errorPage="/ttk/common/error.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<head>
<title>IPRU Weblogin Access</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/trackdatachanges.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/common/healthcarelayout.js"></SCRIPT>
<link href="/ttk/styles/style.css" media="screen" rel="stylesheet"></link>
<SCRIPT LANGUAGE="JavaScript">
var bAction = true;
ClientReset = true;
var JS_SecondSubmit=false;
</SCRIPT>
</head>

<body onLoad="javascript:resizeDocument();handleFocus();window.history.forward(1);">
	<logic:empty name="UserSecurityProfile">
		<script language="javascript">
			openFullScreenMode("/ttk/ipruaccess.jsp?sessionexpired=true");
		</script>
	</logic:empty>

	<!-- S T A R T : Header Section -->
	<tiles:insert attribute="header"/>

	<!-- S T A R T : Left Navigation Menu -->
	<tiles:insert attribute="leftlinks"/>

	<div id="ContentArea">
		<!-- S T A R T : Content Section-->
		<tiles:insert attribute="content"/>
	</div>
</body>