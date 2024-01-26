
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.ttk.action.preauth.PreAuthGeneralAction" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<style>
			table, th, td 
			{
			    border: 1px solid black;
			    border-collapse: collapse;
			}
			th, td 
			{
			    padding: 5px;
			    text-align: left;    
			}
	</style>
<script type="text/javascript" src="/ttk/scripts/preauth/preauthgeneral.js"></script>
<script type="text/javascript">

function onPreauthReject() 
{
	var preAuthSeqID=document.getElementById("preAuthSeqID").value;
	var denailcode=document.getElementsByName("denailcode");
	var denailcodeValue;
	
	for(var i=0;i<denailcode.length;i++)
	{
			if(denailcode[i].checked)
			{
				denailcodeValue = denailcode[i].value;
			}
	}
	
	if(denailcodeValue==null)
	{
		document.getElementById("warning").style.color="red";
		document.getElementById("warning").style.display="";
		document.getElementById("warning").scrollIntoview();
		return
	}
	
	var pstatus=window.opener.document.forms[1].preauthStatus.value;
	window.opener.document.getElementById("denailcode").value=denailcodeValue;	
	window.close();
	window.opener.document.forms[0].mode.value="saveAndCompletePreauth"; 
	window.opener.document.forms[0].action="/PreAuthGeneralAction.do?denailcode="+denailcodeValue+"&pstatus="+pstatus+"&preAuthSeqID="+preAuthSeqID; 
	window.opener.document.forms[0].submit();
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form name="preauthreject">
<h3 style="color: green" align="center"><b> Please Select Appropriate Denial Reason </b></h3>
<div id="warning" style="display: none"><h4 align="center">Please Select Denial Reason</h4> </div>
<table>

<tr>
	<th colspan="1">SELECT</th>
	<th colspan="1">DENIAL_CODE</th>
	<th colspan="1">DENIAL_DESCRIPTION</th>
</tr>
<%

	PreAuthGeneralAction preAuthGeneralAction =new PreAuthGeneralAction();
   	HashMap<String,String> hashMap=preAuthGeneralAction.getPreauthDiagDetails();
   	Set<String> s=hashMap.keySet();
   	Iterator<String> it=s.iterator();
   	while(it.hasNext())
   	{
	   
%>
<tr>
<%String key=it.next();%>
<td align="center"><input type="radio" name="denailcode" value="<%=key %>"></td>
<td><font color="blue"><%=key %></font></td>
<td>
<%=hashMap.get(key)%>  
</td>
<%} %>
</tr>
<%-- <tr>
<td>
<%=request.getParameter("preAuthSeqID")%>
</td>
</tr> --%>
</table>
<input type="button" value="Save" onclick="onPreauthReject();" align="bottom">
<input type="hidden" id="preAuthSeqID" name="preAuthSeqID" value="<%=request.getParameter("preAuthSeqID")%>"> 
<input type="hidden" id="PreauthStatus" name="PreauthStatus" value="<%=request.getParameter("PreauthStatus")%>"> 
<input type="hidden" id="mode" name="mode">
</form>
</body>
</html>