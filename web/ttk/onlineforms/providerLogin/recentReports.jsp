<%@page import="java.util.ArrayList"%>
<%
	/**
	 * @ (#) chequeWiseReport.jsp Nov 27 2015 
	 * Project      : TTK HealthCare Services Dubai
	 * File         : chequeWiseReport.jsp
	 * Author       : Kishor kumar S H
	 * Company      : RCS Technologies
	 * Date Created : Nov 27 2015
	 *
	 * @author       :
	 * Modified by   :
	 * Modified date :
	 * Reason        :
	 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page
	import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.common.security.Cache"%>
<head>
<meta charset="utf-8">
<link href="/ttk/scripts/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css"
	rel="stylesheet">
<link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet"
	type="text/css" />

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript">
function onDownload(obj){
	var partmeter = "?mode=doShowPreAuthShortfall&fileName="+obj+"&reportType=recentReports";
	var openPage = "/OnlineReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	document.forms[1].mode.value="doShowPreAuthShortfall";
	window.open(openPage,'',features);
}
</script>
</head>
<html:form action="/OnlineFinanceAction.do">
	
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
	<h7 class="sub_heading">Recent Downloaded Reports</h7>
<html:errors/>
	<div id="contentOuterSeparator"></div>
</div>           

       </div>
        <div class="row-fluid" >
        <div style="width: 100%;">
<div class="span12" style="margin:0% 0%"> 

	<table style="background: #e3e3e3;width: 80%" align="center" border="1" cellspacing="3" cellpadding="3">
	<tr>
		<th align="left" width="10%">Date</th>
		<th align="left" width="10%">Time</th>
		<th align="left" width="70%">Report Type</th>
		<th align="left" width="10%">Status</th>
	</tr>
        <c:forEach var="alData1" items="${requestScope.alRecentReps}">
      <tr>
         <c:forEach var="strD" items="${pageScope.alData1}" varStatus="theCount">
         <td>
            <c:set var="test" value="${theCount.index}"/>
            <%if(pageContext.getAttribute("test").toString().equals("2")){%>
            <a href="#" onclick="javascript:onDownload('<c:out value="${strD}"/>')"><c:out value="${strD}"/></a>
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
		
	</div>
	<!-- E N D : Content/Form Area -->
</div>


</div>
</div>
 <br><br><br> <br><br><br>
  <br><br><br> 
 
</div>
</div>
</div>
<!--E N D : Content/Form Area -->
<INPUT TYPE="hidden" NAME="mode" VALUE="">

</body>
</html:form>

