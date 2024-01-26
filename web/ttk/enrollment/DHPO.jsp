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
function memberUploadToDHPO()
{
	document.forms[1].mode.value="doDefaultEmailAndXmlUpload";
	document.forms[1].action="/memberUploadToDHPO.do";
	document.forms[1].submit();
	}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>DHPO</title>
</head>
<body onload="myFunction()">
<form action="">
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">Member Register XML Uploaded in Web Portals e-claim</td>
			<%-- <td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td> --%>
		</tr>
	</table>

<table align="left">

<tr>
<td>
<a href="#" onclick="ongetLogFiles()"><b>Log Files from Web Portals</b></a>
</td>
</tr>
<tr>
<td>
<a  href="#" onclick="memberUploadToDHPO()"><b>Upload Member Register XML to Web Portals</b></a>
</td>
</tr>
</table>
<input type="hidden" name="mode">
</form>
</body>
</html>