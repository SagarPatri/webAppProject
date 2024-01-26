<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activity Details</title>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
  <script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
  <link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>  	
<script type="text/javascript">
function editViewFile(filename)
{
	document.forms[0].action="/ViewAuthorizationDetails.do?filename="+filename;
    document.forms[0].mode.value="doFileDownload";
    document.forms[0].submit();     
}
</script>
</head>
<body>
<%
%>
<div class="contentArea" id="contentArea">
<br>
<html:errors/>
<logic:notEmpty name="successMsg" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:write name="successMsg" scope="request"/>
				  </td>
				</tr>
			</table>
</logic:notEmpty>
<br><br>
<html:form action="/ViewAuthorizationDetails.do" method="post" enctype="multipart/form-data">
	<table align="center" class="formContainer" border="1" cellspacing="0" cellpadding="0">
			<tr  class="gridHeader">
			<th align="center">Type</th>
			<th align="center">Code</th>
			<th align="center">Value</th>
			<th align="center">Value Type</th>
		    <th align="center">Remarks</th>
			</tr>
  <logic:notEmpty name="observationDetails" scope="session">  
      <c:forEach items="${sessionScope.observationDetails}" var="observ">                 
       <tr>
		<td align="center">
		<c:out value="${observ[2]}"/> 
		</td>
		<td align="center"> <c:out value="${observ[3]}"/></td>
		
		
		
		<td align="center">
			<c:if test = "${observ[2] eq 'File'}"> 
				<a href="#" accesskey="g"  onClick="javascript:editViewFile('${observ[4]}');"><c:out value="${observ[4]}"/></a>
			</c:if>
			<c:if test = "${observ[2] ne 'File'}">
				<c:out value="${observ[4]}"/>
			</c:if> 
		</td>
		
	<%-- 	<td align="center"><c:out value="${observ[4]}"/></td> --%>
		<td align="center"><c:out value="${observ[5]}"/></td>
		<td align="center"><c:out value="${observ[6]}"/></td>
	  </tr>
    </c:forEach>
  </logic:notEmpty>	
  	   <tr align="center">
     <td align="center"  colspan="5">     
     <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:window.self.close();"><u>C</u>lose</button>&nbsp;    
     </td>
   </tr>
</table>
<input type="hidden" name="mode"/>

</html:form>
</div>	 			    
</body>
</html>