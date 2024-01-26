<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function otpValidate() {
	var otpnum=document.getElementById("otpnum").value;
	if(otpnum==null||otpnum===""||otpnum.length<1){
      alert("Enter OTP");
      return false;
		}
	else{
		window.close();
	}
}
</script>
</head>
<body>
<center>

<form action="/OnlineCashlessHospAction.do" onsubmit="return otpValidate();" method="post">
<table>
<tr>
<td>Member Id:</td><td><input type="text" name="mid" id="mid" readonly="readonly" style="width:200px;height:16px;" value="<%=request.getParameter("memberId")%>"></td>
</tr>
<tr>
<td> OTP:</td><td><input type="text" name="otpNumber" id="otpnum" style="width:200px;height:16px;"></td>
</tr>
<tr>
 <td><input type="hidden" name="mode" value="validationOTP"></td><td><input type="button" value="VALIDATE" onclick="return otpValidate();"></td>
 </tr>
 </table> 
 </form>
 
</center>
</body>
</html>