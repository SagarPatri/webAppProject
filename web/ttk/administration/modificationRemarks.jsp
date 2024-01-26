<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<script>  

var  popupWindow=null;
function onClose() {
	  popupWindow=  window.close();
	
}



</script>













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
 <div overflow: scroll >
<body style="text-align: center; font-size: 12px;">
<table class="historyTable" align="center" >
<tr>
<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Modified Date&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th><th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Modified from value &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th><th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Modified to value&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th><th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Modification Remarks&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>                                           
</tr>

 

<%
java.util.ArrayList<String[]> policyHistoryDetails=(java.util.ArrayList<String[]>)request.getAttribute("modificationRemarksList");
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
	<td>
	<%=strDetails[3] %>
	</td>
	</tr>
	<%	
}
%>
 
</table>
</body>
</div>


<br></br><br></br>

<!-- <table align="center"  >
	  		<tr>
	    		<td width="0%" align="center"> -->
  	    		
	       			<button align="center" type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
			<!-- 	
       			</td>
	  		</tr>
		</table> -->






</html>