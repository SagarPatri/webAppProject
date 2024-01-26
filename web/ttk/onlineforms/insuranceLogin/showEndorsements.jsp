<!DOCTYPE HTML>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/onlineforms/insuranceLogin/corporate.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<head>
<script type="text/javascript">
function onBackToDetailView()
{
   	document.forms[1].mode.value="doBackFromTOBFromAuth";
   	if("Retail"==document.forms[1].leftlink.value)
   	   	document.forms[1].action="/InsuranceRetailAction.do";
   	else
	   	document.forms[1].action="/EditInsuranceCorporateAction.do";
   	document.forms[1].submit();
   	
}

//this function is called on click of the link in grid to display the Files Upload
function edit(rownum)
{
	//document.forms[1].fileName.value = strFileName;
	var openPage = "/ReportsAction.do?mode=doViewCommonForAll&module=policyDocs&rownum="+rownum;
   var w = screen.availWidth - 10;
   var h = screen.availHeight - 49;
   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
   window.open(openPage,'',features);
}//end of edit(rownum)
</script>
    <meta charset="utf-8">
    <title>Your Name Here - Welcome</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">      
	<meta name="author" content="Html5TemplatesDreamweaver.com">
        <!-- Remove this Robots Meta Tag, to allow indexing of site -->
	<META NAME="ROBOTS" CONTENT="NOINDEX, NOFOLLOW"> 
    
<link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
</head>

<html:form action="/InsuranceCorporateAction.do" >
<body id="pageBody">
<div class="contentArea" id="contentArea">
                  
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">


<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<!-- <div id="navigateBar">Home > Corporate</div>  -->
<div id="contentOuterSeparator"></div>
<h4 class="sub_heading">Enrdorsements</h4>
<html:errors/>
<div id="contentOuterSeparator"></div>
</div>


<div class="span12" align="center" style="margin-left:0%;margin-top:5%;">
<div style="width: 70%;">
<!-- <div class="span3" style="margin-left=0%"><br /><img src="/ttk/images/Insurance/hexagon_with_line.png" alt=""></div> -->
<div class="span9" align="center">
<%ArrayList alEndorseList	=	(ArrayList)(request.getSession().getAttribute("alEndorseList")==null?new ArrayList(): request.getSession().getAttribute("alEndorseList"));%>
<table class="table table-striped" border="1">
	<tr>
	<td><strong> Policy No. </strong></td>
	<td><strong> Insured </strong></td>
	<td><strong> Dependents </strong></td>
	<td><strong> Endorsement Effective Date </strong></td>
	<!-- <td><strong> End Date </strong></td> -->
	<td><strong> Status </strong></td>
	</tr>
	<tr>
	<% int i=0; %>
		        <logic:iterate id="policyDetailVO" name="frmInsCorporate" property="alEndorseList">
					<%
						String strClass=i%2==0 ? "gridOddRow" : "gridEvenRow" ;
						i++;
					%>
					<tr class=<%=strClass%>>
				    	<td><bean:write name="policyDetailVO" property="policyNo" /></td>
				        <td><bean:write name="policyDetailVO" property="insured" /></td>
				        <td><bean:write name="policyDetailVO" property="dependent" /></td>
				        <td nowrap="nowrap"><bean:write name="policyDetailVO" property="dateOfInception" /></td>
				       <%--  <td nowrap="nowrap"><bean:write name="policyDetailVO" property="expiryDate" /></td> --%>
				        <td><bean:write name="policyDetailVO" property="status" /></td>
					</tr>
				</logic:iterate>
	</tr>
</table>
</div>
<br>
<div class="span9" align="center">
<p style="margin-bottom: 0px; margin-left: 1%; margin-top: -1%;"><img class="hexagon_small" src="ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onBackToDetailView()" >Back</a> </p>
<br />
</div>
                 </div></div>      
             </div>
             <p> &nbsp; </p>
             <p> &nbsp; </p>
             <p> &nbsp; </p>
             <p> &nbsp; </p>
             <p> &nbsp; </p>
             <p> &nbsp; </p>
             <p> &nbsp; </p>
       </div>
			<!--End Main Content Area here-->

</div>
</div>
</div>
<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="leftlink" VALUE="<%=TTKCommon.getActiveLink(request) %>">
</body>
</html:form>
