<!DOCTYPE HTML>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/onlineforms/insuranceLogin/productsPolicies.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<head>
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
<script type="text/javascript">

function onCorporate()
{
	document.forms[1].mode.value="doCorporate";
   	document.forms[1].action="/InsuranceLoginAction.do";
   	document.forms[1].submit();
}
function onRetail()
{
	document.forms[1].mode.value="doRetail";
   	document.forms[1].action="/InsuranceLoginAction.do";
   	document.forms[1].submit();
}

</script>
<html:form action="/InsuranceProductPoliciesAction.do" >
<%
pageContext.setAttribute("regAuthority",Cache.getCacheObject("regAuthority"));
%>
<body id="pageBody">
<div class="contentArea" id="contentArea">
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">


<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<div id="contentOuterSeparator"></div>
<h4 class="sub_heading">Product & Policies</h4>
<div id="contentOuterSeparator"></div>
</div>           

<!-- <div class="span4">
<div style="margin-top:70px">
<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onCorporate()" > Corporate </a></p>
<p style="margin-bottom: 0px;margin-left:1%;margin-top: -4%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onRetail()" > Retail </a></p>
<br />
</div>
</div> -->

       </div>

<div class="row-fluid" style="margin-top:2%">
<div class="span12" style="margin:0% 3%">
				<div class="span4"> 
                 		<label>Product Name</label>
                  	<html:text property="ProdName" styleClass="textBox textBoxMedLarge" />
               	</div>

                 <div class="span4"> 
                  	<label>Product Code</label>
                  	<html:text property="ProdCode" styleClass="textBox textBoxMedLarge" />        
                 </div>

                 <div class="span4"> 
                  	<label>Authority Type</label>
                  	<html:select property ="authType" styleClass="selectBox selectBoxMedium" onchange="onChangeRegAuth()">		
     				<html:options collection="regAuthority" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
				</div> 
					
<div class="span10" align="center" style="margin-left:5%;">
<div style="width: 70%;">

<div>
<p style="margin-bottom: 0px; margin-left: 16%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
 <a href="#" onclick="javascript:onProceedProducts()" > Proceed </a></p>
</div>
  
</div>
<br>
<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData" className="table table-striped"/>

	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		    <ttk:PageLinks name="tableData"/>
	</table>
	
</div>


</div>

</div>
			<!--End Main Content Area here-->

    </div>



	
	
</div>
<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
</div>
</div>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</body>
</html:form>
