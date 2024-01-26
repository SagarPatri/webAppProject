<%@page import="com.ttk.dto.administration.ReportVO"%>
<%@ page language="java" import="java.util.*,javax.naming.*,java.io.* "contentType="application/vnd.ms-excel"%>
<html>
<head>
<title>Insert title here</title>
</head>
<%
response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-Disposition", "attachment; filename=BatchDetailReport.xls");
%>
<%
ArrayList alData	=	(ArrayList)session.getAttribute("alData");
%>
<body>
<table border="1">

<%try{
ReportVO reportVO	=	null;
ArrayList alresults	=	null;
int temp	=	0;

for(int i=0;i<alData.size();i++)
{
	reportVO	=	(ReportVO)alData.get(i);
	alresults	=	(ArrayList)reportVO.getAlColumns();

	%> <tr> 
	
	<%for(int j=0;j<alresults.size();j++)
		{
		temp++;
	if(i==0){
		%><td> <font color="blue"><strong> <%=alresults.get(j) %></strong></font></td>
		<%}else{%>
			<td> <%=alresults.get(j) %></td>
		<%}
		}	%>	  
		</tr>
<%
if(alData.size()==1)
{%>
	<tr>
	<td colspan="<%= temp%>" align="center">
	<font color="red"> <strong> No Data Found</strong> </font>
	</td>
	</tr>
<%}

	}}catch(Exception ex)
{
	System.out.println("Error in Excel::"+ex);
}
%>

</table>
</body>
</html>