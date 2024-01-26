<!DOCTYPE HTML>
<%@page import="com.ttk.dto.onlineforms.insuranceLogin.PolicyDetailVO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<head>
<script type="text/javascript">
function onBackToProdSearch()
{
	document.forms[1].mode.value="doBackToProdSearch";
   	document.forms[1].action="/InsuranceProductPoliciesAction.do";
   	document.forms[1].submit();
}

function showPolicyBenefits(obj,enrolTypeId)
{
	document.forms[1].mode.value="doShowPolicyBenefits";
   	document.forms[1].action="/InsuranceProductPoliciesAction.do?polSeqId="+obj+"&enrolTypeId="+enrolTypeId;
   	document.forms[1].submit();
}
</script>
	<link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
</head>

<html:form action="/InsuranceProductPoliciesAction.do" >
<div class="contentArea" id="contentArea">
<body id="pageBody">
                  
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">


<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<!-- <div id="navigateBar">Product And Policies > Policies List</div> --> 
<div id="contentOuterSeparator"></div>
<h4 class="sub_heading">Policies</h4>
<html:errors/>
<div id="contentOuterSeparator"></div>
</div>




               
<div class="span12" align="center" style="margin-left:0%;">
<div style="width: 70%;">

<div>

<%
ArrayList alPolicyDetails	=	(ArrayList)request.getAttribute("alPolicyDetails")==null?new ArrayList():(ArrayList)request.getAttribute("alPolicyDetails");
PolicyDetailVO policyDetailVO	=	null;
%>
<fieldset style="width:550px;" align="center"><div id="navigateBar"><%out.print(request.getAttribute("prodId")); %> - Policies list</div>
	<br>
	<table align="center" class="table table-striped"  border="0" cellspacing="0" cellpadding="0" style="width:500px;height:auto;">
	  <tr>
	  <th style="width: 10%" align="center" class="gridHeader"> Sl No. </th>
	  <th style="width: 50%" align="center" class="gridHeader"> Policy Number </th>
	  <th style="width: 20%" align="center" class="gridHeader"> Effective From Date </th>
	  <th style="width: 20%" align="center" class="gridHeader"> Effective To Date </th>
	  </tr>
	  <%
	  String strTob	=	"";
	  for(int i=0;i<alPolicyDetails.size();i++)
	  {
		  policyDetailVO	=	(PolicyDetailVO)alPolicyDetails.get(i);
		 
		  /* strTob	=	policyDetailVO.getTob();
      	if(!"".equals(strTob) && strTob!=null)
      		strTob	=	strTob.replace("#13", "&#13;");
      	else
      		strTob	=	""; */
      
	  %>
	  <tr class="<%=i%2==0?"gridEvenRow":"gridOddRow" %>"> 
	  		<td align="center"> <%=(i+1) %> </td>
	  		<td align="center"> <%-- <a href="#" onclick="javascript:showCircularFile('<%=policyDetailVO.getPolicySeqId()%>')"> <%=policyDetailVO.getPolicyNo() %></a> --%>
	  		<%-- <p id="markClass" title="<%= strTob %>"> <%=policyDetailVO.getPolicyNo() %></p> --%>
	  		<a href="#" onclick="javascript:showPolicyBenefits('<%=policyDetailVO.getPolicySeqId() %>','<%=policyDetailVO.getEnrolTypeId() %>')"> <%=policyDetailVO.getPolicyNo() %></a> 
	  		</td>
	  		<td align="center">  <%=TTKCommon.getFormattedDate(policyDetailVO.getJoiningDate()) %> </td>
	  		<td align="center">  <%=TTKCommon.getFormattedDate(policyDetailVO.getExpiryDate()) %> </td>
	  </tr>
	  
	  <%} %>
   </table>
 </fieldset>


<div style="">
<p style="margin-bottom: 0px; margin-left: 1%; margin-top: -1%;"><img class="hexagon_small" src="ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onBackToProdSearch()" >Back</a> </p>
<br />
</div>

  
</div>
</div>

<div id="contentOuterSeparator"></div>


                    



             </div>
       </div>
			<!--End Main Content Area here-->

</div>
</div>
</div>
<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">

</body>
</html:form>
