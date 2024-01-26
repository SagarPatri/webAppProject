<%@ page language="java" import="java.util.*,javax.naming.*,java.io.* "contentType="application/vnd.ms-excel"%>
<html>
<head>
<title>Claim TAT</title>
</head>
<%
long[] noOfClaims			=	(long[])request.getAttribute("noOfClaims");
ArrayList alClaimTatData	=	(ArrayList)request.getAttribute("alClaimTatData");
String[] clmData	=	null;
%>
<body>
<%try{
	int zeroTo5		=	0;
	int fiveTo15	=	0;
	int above15		=	0;
	
	int NzeroTo5	=	0;
	int NfiveTo15	=	0;
	int Nabove15	=	0;
	
	for(int k=0;k<noOfClaims.length;k++){
		 clmData	=	(String[])alClaimTatData.get(k);
		 if(clmData[0].equals("MEMBER"))
		 {
			 if(noOfClaims[k]<6)
					zeroTo5++;
			 if(noOfClaims[k]>5 && noOfClaims[k]<=15)
					fiveTo15++;
			 if(noOfClaims[k]>15)
					above15++;
		 }else if(clmData[0].equals("NETWORK")){
			 if(noOfClaims[k]<16)
				 NzeroTo5++;
			 if(noOfClaims[k]>15 && noOfClaims[k]<=30)
				 fiveTo15++;
			 if(noOfClaims[k]>30)
				 Nabove15++;
		 }
		
	}
	
%>
<table border="1">
	<tr bgcolor="#FF7F50"> <th>Type of Claim </th><th> 0--5 </th><th> 6--15 </th><th> Above 15 Days</th> </tr>
	<tr> <td>MEMBER </td><td> <%=zeroTo5 %>  </td><td> <%=fiveTo15 %> </td><td> <%=above15 %></td> </tr>
</table>

<br> <br>

<table border="1">
	<tr bgcolor="#FF7F50"> <th>Type of Claim </th><th> 0--15 </th><th> 16--30 </th><th> Above 30 Days</th> </tr>
	<tr> <td>NETWORK </td><td> <%=NzeroTo5 %>  </td><td> <%=NfiveTo15 %> </td><td> <%=Nabove15 %></td> </tr>
</table>


<%}catch(Exception ex)
{
	System.out.println("Error in Excel::"+ex);
} %>
</body>
</html>