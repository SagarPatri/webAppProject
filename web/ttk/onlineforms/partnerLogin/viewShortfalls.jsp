<%
/**
 * @ (#) viewShortfalls.jsp Nov 19 2015 
 * Project      : TTK HealthCare Services Dubai
 * File         : viewShortfalls.jsp
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : Nov 23 2015
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Arrays"%>
<head>
<meta charset="utf-8">
    <link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
    
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/providerLogin/viewShortfalls.js"></SCRIPT>
</head>
<html:form action="/ViewPartnerAuthorizationDetails.do" >
<body id="pageBody">
<div class="contentArea" id="contentArea">
<!-- S T A R T : Content/Form Area -->
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">
<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<!-- <div id="navigateBar">Home > Corporate > Detailed > Claim Details</div> -->
	<div id="contentOuterSeparator"></div>
	<h4 class="sub_heading">Shortfall Details</h4>
<html:errors/>
	<div id="contentOuterSeparator"></div>
</div>           

       </div>
        <div class="row-fluid" >
        <div style="width: 100%;">
<div class="span12" style="margin:0% 0%"> 

	<table style="background: #e3e3e3" align="center" border="1" cellspacing="3" cellpadding="3" style="width: 90%">
      <tr>
		<th>Date of Shortfall</th>
		<th>Shortfall No.</th>
		<th>Shortfall Type</th>
		<th>Status</th>
		<th>Reply Received</th>
		<th>Remarks</th>
	</tr>
		<%-- JSTL foreach tag example to loop --%>
		<%-- <jsp:scriptlet>
            String[] windows = new String[]{"Windows XP", "Windows 7", "Windows 8", "Windows mobile"};
            String[] windows1 = new String[]{"Kishor", "Kiran", "Jibi", "Seenu"};
            ArrayList alData	=	new ArrayList();
            alData.add(windows);
            alData.add(windows1);
            pageContext.setAttribute("alData", alData);
        </jsp:scriptlet> --%>
        <c:forEach var="alData1" items="${sessionScope.alShortFallList}">
      <tr>
         <c:forEach var="strD" items="${pageScope.alData1}" varStatus="theCount">
         <td>
            <c:set var="test" value="${theCount.index}"/>
            <%if(pageContext.getAttribute("test").toString().equals("1")){%>
            <a href="#" onclick="javascript:onShortfall('<c:out value="${strD}"/>')"><c:out value="${strD}"/></a>
            <% }else{%>
              <c:out value="${strD}"/>
            <% }%>
         </td>
         </c:forEach>
     </tr>
        </c:forEach>
       
	
	</table>

   
   <br>
   
   <!-- E N D : Form Fields -->
		
    	<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
		    		<button type="button" name="Button" accesskey="b" class="olbtnSmall" onClick="javascript:onBack();"><u>B</u>ack</button>&nbsp;
 				</td>
  			</tr>  		
		</table>
		<!-- E N D : Buttons -->
	</div>
	<!-- E N D : Content/Form Area -->
</div>


</div>
</div>
 <br><br><br> <br><br><br>
  <br><br><br> <br><br><br>
  <br><br><br> <br><br><br>
 
</div>
</div>
</div>
<!--E N D : Content/Form Area -->
<INPUT TYPE="hidden" NAME="mode" VALUE="">

</body>
</html:form>

		