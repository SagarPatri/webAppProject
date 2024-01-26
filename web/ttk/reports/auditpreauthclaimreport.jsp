<%@page import="com.ttk.dto.administration.ReportVO"%>
<%@ page language="java" import="java.util.*,javax.naming.*,java.io.* "contentType="application/vnd.ms-excel" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>


<html>
<head>
<title>Insert title here</title>
</head>
<logic:equal name="flag"	scope="session" value="generatereport">
<%
String parameter= (String)request.getAttribute("parameter");

if(parameter.equals("CLM"))
{
response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-Disposition", "attachment; filename=ClaimsAuditReport.xls");
}
else if(parameter.equals("PAT"))
{
	response.setContentType("application/vnd.ms-excel");
	response.setHeader("Content-Disposition", "attachment; filename=Pre-AuthorizationAuditReport.xls");
}

%>
</logic:equal>
<logic:equal name="flag"	scope="session" value="errorlog">
<%

response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-Disposition", "attachment; filename=AuditErrorlog.xls");
%>
</logic:equal>
<logic:equal name="flag"	scope="session" value="Tariff">
<%

response.setContentType("application/vnd.ms-excel");
response.setHeader("Content-Disposition", "attachment; filename=TariffErrorlog.xls");
%>
</logic:equal>
<%
ArrayList alData	=	(ArrayList)request.getAttribute("alData");

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
		%><td> <font color="blue"><strong> <%=alresults.get(j).toString().trim().toUpperCase()%></strong></font></td>
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