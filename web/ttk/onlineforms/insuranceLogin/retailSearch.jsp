<!DOCTYPE HTML>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/onlineforms/insuranceLogin/retailSearch.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<head>
</style>
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

<html:form action="/InsuranceRetailAction.do" >
<div class="contentArea" id="contentArea">
<body id="pageBody">
                  
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">


<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<!-- <div id="navigateBar">Retail > Search</div> --> 
<div id="contentOuterSeparator"></div>
<h4 class="sub_heading">Retail Page</h4>
<html:errors/>
<div id="contentOuterSeparator"></div>
</div>



<div id="contentOuterSeparator"></div>

             <div class="span12" style="margin:0% 4%">
                
                   <div class="span4"> 
                   		<label>Product Name</label>
                    	<html:text property="prodName" styleClass="textBox textBoxMedLarge" />
                   </div>
 
                   <div class="span4"> 
                    	<label>Date From</label>
                    	<html:text property="dateFrom" styleClass="textBox textBoxMedium" />
						<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmInsRetail.dateFrom',document.frmInsRetail.dateFrom.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
							<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
						</a>
					</div> 
					
					<div class="span4"> 
                    	<label>Date To</label>
                    	<html:text property="dateTo" styleClass="textBox textBoxMedium" />
						<a name="CalendarObjectempDate22" id="CalendarObjectempDate22" href="#" onClick="javascript:show_calendar('CalendarObjectempDate22','frmInsRetail.dateTo',document.frmInsRetail.dateTo.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
							<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate1" width="24" height="17" border="0" align="absmiddle">
						</a>
					</div> 

               </div>
               
<div class="span12" align="center" style="margin-left:0%;">
<div style="width: 70%;">

<div>
<br>
<p style="margin-bottom: 0px; margin-left: 16%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
 <a href="#" onclick="javascript:onRetailSearchProceed()" > Proceed </a>
 </p>

</div>
  
</div>
</div>

<div id="contentOuterSeparator"></div>

<div id="contentOuterSeparator"></div>

<div class="span12" align="center" style="margin-left:0%;margin-top:5%">
<div style="width: 70%;">
<!-- <div class="span3" style="margin-left=0%"><br /><img src="/ttk/images/Insurance/hexagon_with_line.png" alt=""></div> -->
<div class="span9">

<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData" className="table table-striped"/>

	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		    <ttk:PageLinks name="tableData"/>
	</table>
</div>
                 </div></div>      
                    



             </div>
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
</body>
</html:form>
