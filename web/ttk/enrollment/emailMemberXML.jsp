<%
/** @ (#) DHPO.jsp
 * Project     : TTK Healthcare Services
 * File        : DHPO.jsp
 * Author      : Lohith.M
 * Company     : RCS
 *
 */
%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<script type="text/javascript">
function ongetLogFiles() 
{
	document.forms[1].mode.value="memberUploadLogFiles";
	document.forms[1].action="/MemberUploadAction.do";
	document.forms[1].submit();
		
}

function onconfigureMail() 
{
	document.forms[1].mode.value="doDefaultconfigureMailID";
	document.forms[1].action="/MemberUploadAction.do";
	document.forms[1].submit();
}

function emailTriger(){
	document.forms[1].mode.value="doDefaultEmailAndXmlUpload";
	document.forms[1].action="/memberUploadXMLmailTrigger.do";
	document.forms[1].submit();
}
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DHPO</title>
</head>
<body>
<form >
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">Member Register XML Triggered as e-claim</td>
			<%-- <td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td> --%>
		</tr>
	</table>

<table align="left">
<tr>
<td>
<label style="color: ${color};"><b>${DHPOUploadStatus}</b></label> 
</td>
</tr>
<tr>
<td>
<a  href="#" onclick="emailTriger();"><b>Trigger Email</b></a>
</td>
</tr>
<tr>
<td>
<a href="#"  onclick="onconfigureMail();"><b>Configure Receiver Mail ID</b></a>
</td>
</tr>
</table>
<input type="hidden" id="mode"  name="mode">
</form>
</body>
</html>