<%@ page language="java" import="java.util.*,javax.naming.*,java.io.* "contentType="application/vnd.ms-excel"%>
<html>
<head>
<title>Claim TAT</title>
</head>
<%
long[] noOfPreAuths			=	(long[])request.getAttribute("noOfPreAuths");
ArrayList alPreAuthTatData	=	(ArrayList)request.getAttribute("alPreAuthTatData");
String[] clmData	=	null;
%>
<body>
<%try{
	int zeroTo5		=	0;
	int fiveTo15	=	0;
	int above15		=	0;
	
	int NzeroTo5		=	0;
	int NfiveTo15	=	0;
	int Nabove15		=	0;
	for(int k=0;k<noOfPreAuths.length;k++){
		 clmData	=	(String[])alPreAuthTatData.get(k);
		 if(clmData[0].equals("OUT-PATIENT"))
		 {
			 if(noOfPreAuths[k]<31)
					zeroTo5++;
			 if(noOfPreAuths[k]>30 && noOfPreAuths[k]<=60)
					fiveTo15++;
			 if(noOfPreAuths[k]>60)
					above15++;
		 }else if(clmData[0].equals("IN-PATIENT")){
			 if(noOfPreAuths[k]<=180)
				 NzeroTo5++;
			 if(noOfPreAuths[k]>180)
				 fiveTo15++;
		 }
		
	}
	
%>
<table border="1">
	<tr bgcolor="#FF7F50"> <th>TYPE OF PREAUTH</th><th> 0-30 Minutes </th><th> 30-1 Hour</th><th> Above 1 Hour</th> </tr>
	<tr> <td>Out Patient </td><td> <%=zeroTo5 %>  </td><td> <%=fiveTo15 %> </td><td> <%=above15 %></td> </tr>
</table>
<br><br>

<table border="1">
	<tr bgcolor="#FF7F50"> <th>TYPE OF PREAUTH</th><th> 0-3 Hours</th><th> Above 3 Hours</th> </tr>
	<tr> <td>In Patient </td><td> <%=NzeroTo5 %>  </td><td> <%=fiveTo15 %>  </tr>
</table>

<%}catch(Exception ex)
{
	System.out.println("Error in Excel::"+ex);
} %>
</body>
</html>