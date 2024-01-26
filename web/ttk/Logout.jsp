<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<title>Logout Successfully</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/trackdatachanges.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/common/healthcarelayout.js"></SCRIPT>
</head>

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
<script language="JavaScript">

</script>
</head>
<body onLoad="window.history.forward(1);" onkeypress="return disableCtrlModifer(event);" onkeydown="return disableCtrlModifer(event);">
<%  
response.setHeader("Pragma","no-cache");  
response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

response.setHeader("Expires","0");  
response.setDateHeader("Expires",-1);  
 
%>
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="width:100%; background-color:#FFFFFF;">
  		<tr>
  			<td style="border-bottom:2px solid #000;"><img style="padding-top:15px;" src="/ttk/images/TTLLogo.gif" alt="Logo" width="502" height="74"></td>
			<td style="border-bottom:2px solid #000;"></td>
			<td align="right" style="border-bottom:2px solid #000;"><img src="/ttk/images/ttkimage.gif" alt="Logoimage" width="297" height="76"></td>
		</tr>
	</table>
	
	<div align="center" style="font-type:Arial;font-size:13px;padding-top:10px;">
<b>
<div style="margin-top:10px;padding-top:10px;">Thank you for using Vidal Healthcare Administrator Service. You have been successfully logged out of Vidal Health  Service</div>
<div style="font-type:Arial;font-size:13px;padding:10px 0 10px 0">For the Security of your Account, request you to close the window.</div>
</b>
</div>
</body>
</html>
	<%--<fieldset>
	<legend> <FONT COLOR="blue">LogOut</FONT></legend>
    <table align="center" class="formContainer" border="0" cellspacing="1" cellpadding="2">
      
         <tr>
         <td>
	<b>
<div style="margin-top:10px;padding-top:10px;">Thank you for using Vidal Healthcare Administrator Service. You have been successfully logged out of  Vidal Health TPA Services</div>
<div style="font-type:Arial;font-size:13px;padding:10px 0 10px 0">For the Security of your Account, request you to close the window.</div>
</b>
	  </td>
	  </tr>
	  </table>
     </fieldset>--%>
	 
