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
<link href="/ttk/styles/OnlineDefault.css" media="screen" rel="stylesheet"></link>
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

<body onLoad="javascript:resizeDocument();TrackFormData('webBoard');handleFocus();window.history.forward(1);" onkeypress="return disableCtrlModifer(event);" onkeydown="return disableCtrlModifer(event);">
<logic:empty name="UserSecurityProfile">
	<script language="javascript">
			openFullScreenMode("/ttk/onlineaccess.jsp?sessionexpired=true");
	</script>
</logic:empty>
<table width="100%" border="0" cellspacing="0" cellpadding="0" id="tableone">
<tr>
	<td>
		<!-- S T A R T : Header Section-->
		<tiles:insert attribute="header"/>
	</td>
</tr>
<tr>
	<td>
		<!-- S T A R T : Main Container Table -->
		<table width="100%" id="mainContainerTable" class="mainContainerTable"  border="1" cellspacing="0" cellpadding="0">
		  <tr>
		    <td  width="163" class="leftNavigationBandWeblogin" nowrap valign="top">
			<!-- S T A R T : Left Navigation Menu -->
		    	<tiles:insert attribute="leftlinks"/> 
    			<div id="tt" style="height:50px;border-top:1px solid #022665;padding-top:2px;" onclick="test(this)">
        		<!-- <img src="/ttk/images/prestigehealth160x50.jpg" alt="PrestigeHealth" width="160" height="50" >
        		-->
			</div> 
		    </td>
		    <td width="100%" valign="top">
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
<tr>
</table>
<!--
<script langauage="javascript">
//   function test(x){
   
 setTimeout(test, 5);
 function test(){
   var totheight = parseInt(document.getElementById('tableone').clientHeight);
   
   var xheight = parseInt(document.getElementById('tt').style.height);
  
   
   document.getElementById('tt').style.position = "absolute";
   document.getElementById('tt').style.top = totheight - xheight-30+"px"; 
   //alert(document.getElementById('tt').style.top);
   
  }
  
  </script>
  -->
</body>