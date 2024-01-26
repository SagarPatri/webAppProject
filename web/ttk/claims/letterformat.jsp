<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<script type="text/javascript">
function callsubmit() 
{
	var claimSeqID=document.getElementById("claimSeqID").value;
	var claimStatus=document.getElementById("claimStatus").value;
	var claimSettelmentNo=document.getElementById("claimSettelmentNo").value;
	
	
	var claimType=document.getElementById("claimType").value;
	var processType=document.getElementById("processType").value;
	
	
	
	var networkProviderType=document.getElementById("networkProviderType").value;
	var providerCountry=document.getElementById("providerCountry").value;
	
	
	
	
	
	var format=null;
	var oRadio = document.forms[0].elements["format"];
	 
	   for(var i = 0; i < oRadio.length; i++)
	   {
	  
	      if(oRadio[i].checked)
	      {
	    	  format= oRadio[i].value;
	        document.getElementById("worning").style.visibility="hidden";
	        
	      }
	   }
	if(format!=null)
		{
		 var openPage = "/ClaimGeneralAction.do?mode=generateClaimLetterMember&claimSeqID="+claimSeqID+"&claimStatus="+claimStatus+"&claimSettelmentNo="+claimSettelmentNo+"&format="+format+"&claimType="+claimType+"&processType="+processType+"&networkProviderType="+networkProviderType+"&providerCountry="+providerCountry;
	  	   var w = screen.availWidth - 10;
	  	   var h = screen.availHeight - 49;
	  	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	  	   window.open(openPage,'',features);
	  	window.close();
		}
	
	 else
	 {
		 document.getElementById("worning").style.color="red";
	 document.getElementById("worning").style.display="";
	 return
	 }
	  
	  
		
	
	//  var openPage = "/ClaimGeneralAction.do?mode=generateClaimLetterMember&claimSeqID="+claimSeqID+"&claimStatus="+claimStatus+"&claimSettelmentNo="+claimSettelmentNo+"&format="+format;
	  // var w = screen.availWidth - 10;
	  // var h = screen.availHeight - 49;
	 //  var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	 //  window.open(openPage,'',features);
	//window.close(); 
}
</script>
</head>
<body>
<form action="/ClaimGeneralAction.do">
<h2>Kindly Select Format Of The Letter</h2>
<div id="worning" style="display: none"><label>Please Select Letter Format</label> </div>
<input type="radio" id="" name="format" value="GENERAL">General PO Format<br>
<input type="radio" id="" name="format" value="ASCANA">Ascana PO Format<br>
<input type="radio" id="" name="format" value="TAKAFUL">Takaful PO Format<br>
<input type="radio" id="" name="format" value="NLGI">NLGI PO Format<br>
<input type="radio" id="" name="format" value="OMAN">OMAN PO Format<br>
<!-- <input type="hidden" name="mode" value="generateClaimLetter"> -->
<table align="center">
<tr>
<td align="center">
<input type="hidden" id="claimSeqID" name="claimSeqID" value="<%=request.getParameter("claimSeqID")%>">
<input type="hidden" id="claimStatus" name="claimStatus" value="<%=request.getParameter("claimStatus")%>">
<input type="hidden" id="claimSettelmentNo" name="claimSettelmentNo" value="<%=request.getParameter("claimSettelmentNo")%>">


<input type="hidden" id="claimType" name="claimType" value="<%=request.getParameter("claimType")%>">
<input type="hidden" id="processType" name="processType" value="<%=request.getParameter("processType")%>">


<input type="hidden" id="networkProviderType" name="networkProviderType" value="<%=request.getParameter("networkProviderType")%>">
<input type="hidden" id="providerCountry" name="providerCountry" value="<%=request.getParameter("providerCountry")%>">


</td>
</tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr><td></td></tr>
<tr>
<td align="center">
<input type="button" value="GenerateLetter" onclick="callsubmit()">
</td>
</tr>
</table>
</form>
</body>
</html>