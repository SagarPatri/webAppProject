<!DOCTYPE HTML>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/hospitalgeneral.js"></script>
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

function onRetailGlobalView()
{
	document.forms[1].mode.value="doRetailGlobal";
   	document.forms[1].action="/InsuranceLoginAction.do";
   	document.forms[1].submit();
}
function onRetailFocusedView()
{
	document.forms[1].mode.value="doRetailFocused";
   	document.forms[1].action="/InsuranceLoginAction.do";
   	document.forms[1].submit();
}

</script>
<html:form action="/InsuranceLoginAction.do" >
<div class="contentArea" id="contentArea">
<body id="pageBody">
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">


<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<div id="contentOuterSeparator"></div>
<h4 class="sub_heading">Retail Page</h4>
<div id="contentOuterSeparator"></div>
</div>



<div id="contentOuterSeparator"></div>

             <div class="span12" style="margin:0% 4%">
                
                   <div class="span4"> 
                    <label>Product Name:</label>
                    <input placeholder="Standard Input" type="text">            
                   </div>
 
                   <div class="span4"> 
                    <label>Data From:</label>
                    <input placeholder="Standard Input" type="text">            
                   </div>

                   <div class="span4"> 
                    <label>Data To:</label>
                    <input placeholder="Standard Input" type="text">            
                   </div> 


                    <div class="span4"> 
                             
                   </div>
 
                   <div class="span4"> 
                    
                      
                


                   </div>

                   <div class="span4"> 
                    
                   </div> 
               </div>
<div class="span12" align="center" style="margin-left:0%;">
<div style="width: 70%;">

<div>
<p style="margin-bottom: 0px; margin-left: 9%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onRetailGlobalView()"> Summary </a></p>
<p style="margin-bottom: 0px; margin-left: 1%; margin-top: -2%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onRetailFocusedView()"> Detailed </a></p>
</div>
  
</div>
</div>

<div id="contentOuterSeparator"></div>

<div id="contentOuterSeparator"></div>


<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>

             </div>
       </div>
			<!--End Main Content Area here-->

    </div>

</div>
<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<input type="hidden" name="mode" value="">
</body>
</html:form>
