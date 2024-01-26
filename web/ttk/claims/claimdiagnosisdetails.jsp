<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@page import="com.ttk.action.claims.ClaimGeneralAction" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style>
table, th, td {
    border: 1px solid black;
    border-collapse: collapse;
}
th, td {
    padding: 5px;
    text-align: left;    
}
</style>
<script type="text/javascript" src="/ttk/scripts/claims/claimgeneral.js"></script>
<script type="text/javascript">

function onClaimReject() 
{
	//document.forms[0].mode.value="saveAndCompleteClaim";
	document.forms[0].mode.value="rejectClaim";
	var claimSeqID=document.getElementById("claimSeqID").value;
	var denialcode=document.getElementsByName("denailcode");
	var denialcodeValue;
	for(var i=0;i<denialcode.length;i++)
		{
		if(denialcode[i].checked)
			{
			denialcodeValue=denialcode[i].value;
			}
		
		}
	
	if(denialcodeValue==null)
	{
		 document.getElementById("worning").style.color="red";
		 document.getElementById("worning").style.display="";
		document.getElementById("worning").scrollIntoview();
		 return
	}
	window.close();
	window.opener.document.getElementById("denailcode").value=denialcodeValue;
	
	onRejectClaim(claimSeqID,denialcodeValue);
	
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form name="claimreject">
<h3 style="color: green" align="center"><b> Please Select Appropriate Denial Reason </b></h3>
<div id="worning" style="display: none"><h4 align="center">Please Select Denial Reason</h4> </div>
<table>

<tr>
<th colspan="1">SELECT</th>
<th colspan="1">DENIAL_CODE</th>
<th colspan="1">DENIAL_DESCRIPTION</th>
</tr>
<%
//PreAuthDetailVO preAuthDetailVO = null;
ClaimGeneralAction claimGeneralAction=new ClaimGeneralAction();
   HashMap<String,String> hashMap=claimGeneralAction.getClaimDiagDetails();
   Set<String> s=hashMap.keySet();
   Iterator<String> it=s.iterator();
   while(it.hasNext())
   {
%>
<tr>
<%String key=it.next();%>
<td align="center"><input type="radio" name="denailcode" value="<%=key %>"></td>
<td><font color="blue"><%=key %></font></td>
<%-- <td>
<%=key%>
</td> --%>
<td>
<%=hashMap.get(key)%>
</td>
<%} %>
</tr>
<%-- <tr>
<td>
<%=request.getParameter("claimSeqID")%>
</td>
</tr> --%>
</table>
<input type="button" value="Save" onclick="onClaimReject() ;" align="bottom">
<input type="hidden" id="claimSeqID" name="claimSeqID" value="<%=request.getParameter("claimSeqID")%>">
<input type="hidden" id="claimSeqID" name="mode">
</form>
</body>
</html>