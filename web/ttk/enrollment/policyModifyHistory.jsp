<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Policy Status History</title>
<style type="text/css">
.historyTable {
    border-collapse: collapse;
     border: 1px solid black;
}

.historyTable  th, td {
    border: 1px solid black;
}
</style>
</head>
<body style="text-align: center; font-size: 12px;">
<table class="historyTable" align="center" >
<tr>
<th>&nbsp;&nbsp;&nbsp;Status&nbsp;&nbsp;&nbsp;</th><th>&nbsp;&nbsp;&nbsp;Effective from&nbsp;&nbsp;&nbsp;</th><th>&nbsp;&nbsp;&nbsp;Remarks captured when policy got cancelled with prior requests or Claims available in VINGS system&nbsp;&nbsp;&nbsp;</th>
</tr>
<%
java.util.ArrayList<String[]> policyHistoryDetails=(java.util.ArrayList<String[]>)request.getAttribute("policyHistoryDtls");
for(String[] strDetails:policyHistoryDetails){
%>
	<tr>
	<td>
	<%=strDetails[0] %>
	</td>
	<td>
	<%=strDetails[1] %>
	</td>
	<td>
	<%=strDetails[2] %>
	</td>
	</tr>
	<%	
}
%>

</table>
</body>
</html>