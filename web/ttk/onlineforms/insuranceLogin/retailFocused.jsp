<!DOCTYPE HTML>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/hospitalgeneral.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript">

function onBackRetail()
{
	document.forms[1].mode.value="doRetailBack";
   	document.forms[1].action="/InsuranceLoginAction.do";
   	document.forms[1].submit();
}
function onCorporate()
{
	document.forms[1].mode.value="doCorporate";
   	document.forms[1].action="/InsuranceLoginAction.do";
   	document.forms[1].submit();
}
function onInPatient()
{
	document.forms[1].mode.value="doInPatient";
   	document.forms[1].action="/InsuranceLoginAction.do";
   	document.forms[1].submit();
}
function onDental()
{
	document.forms[1].mode.value="doDental";
   	document.forms[1].action="/InsuranceLoginAction.do";
   	document.forms[1].submit();
}

</script>
<html:form action="/InsuranceLoginAction.do" >
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
<div class="contentArea" id="contentArea">
<body id="pageBody">

                  
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">
<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<div id="contentOuterSeparator"></div>
<p style="color:#008c8d;margin-top: 10px;">&lt; Company Name &gt; - &lt; Policy Number &gt; </p>
<h4 class="sub_heading">Detailed</h4>
<div id="contentOuterSeparator"></div>
</div>



<div id="contentOuterSeparator"></div>

      <div style="width:90%;margin-left:auto;margin-right:auto;">
	  	<div class="row-fluid">

<div class="span4">
<div style="margin-top:50px">
<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;TOS </p>
<p style="margin-bottom: 0px;margin-left:1%;margin-top: -4%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;Endorsements</p>
<br />
</div>
</div>
	

<div class="row-fluid">	
                 <div class="span12" align="center" >

<div class="span2" style="padding-bottom:15%;"><br /><p>&nbsp;</p>
<p>&nbsp;</p><img src="/ttk/images/Insurance/pes_info_tab.png" alt=""></div>
<div class="span4">


<h3 style="text-align:left; padding-top:25%;"> Personal Info</h3>
<table class="table table-striped" >
    
      <tbody>

        <tr>
          <td colspan="2">Personal Information</td>
        </tr>
        <tr>
          <th>Name</th>
          <td></td>
        </tr>
        <tr>
          <th>Date of birth</th>
          <td></td>
        </tr>
        <tr>
          <th>Age</th>
          <td></td>
       <tr>
          <th>Gender</th>
          <td></td>
        </tr><tr>
          <th>Marital Status</th>
          <td></td>
        </tr>
        
        <tr>
          <th>Dependent details </th>
          <td></td>
        </tr>
        
      </tbody>
    </table></div>
<div class="span2" style="margin-left=0%"><br /><img src="/ttk/images/Insurance/policy.png" alt="">


</div>

<div class="span4">
<h3 style="text-align:left;"> Policy </h3>

<table class="table table-striped">
     
      <tbody>
        <tr>
          <th>Date of inception of the policy</th>
          <td></td>
        </tr>
        <tr>
          <tr>
          <th>Date of expiry of the policy</th>
          <td></td>
        </tr>
        <tr>
          <th>Joining Date of the insured</th>
          <td></td>
        </tr>
        <tr>
          <tr>
          <th>Date of exit from policy</th>
          <td> &nbsp;</td>
        </tr>
       <tr>
          <th>Aggregate sum insured</th>
          <td></td>
        </tr><tr>
          <tr>
          <th>Balance sum insured</th>
          <td></td>
        </tr><tr>
          <tr>
          <th>Employee Number</th>
          <td></td>
        </tr><tr>
          <tr>
          <th>Network</th>
          <td></td>
        </tr><tr>
          <tr>
          <th>Product name</th>
          <td></td>
        </tr>
        
        <tr>
          <th>Special Exclusions</th>
          <td></td>
        </tr>
        <tr>
          <th>Waiting period</th>
          <td></td>
        </tr>
        <tr>
          <th>Benefit Tree with sublimit</th>
          <td>
          <ul> <li>IP Limit</li>
          <li>IP Limit</li>
          <li>OP  Limit</li>
          <li>Chronic Limit</li>
          <li>Maternity Limit
          <ul> <li>Normal Delivery </li>
          <li>C-Section </li> </ul>
          </li>
          
          <li>Dental Limit</li>
          <li>Optical Limit</li>
          <li>Alternative Medicine Limit</li>
          <li>Physiotherapy Limit</li>
          <li>Pharmacy</li>
          <li>MRI and CT scan</li>
          
          </ul></td>
        </tr>
        
      </tbody>
    </table>
	<p style="font-weight:normal;font-style: normal;font-family:Open Sans;font-size: 24px;float:left"> Details <img style="width: 140px;padding-left: 13%;padding-top: 6%;" src="/ttk/images/Insurance/line_1.png" alt="" align="right"></p>

</div></div></div>
<div class="row-fluid">	

<div class="span6" style="padding-bottom:15%;"><br /></div>
<div class="span2">


</div>

	<div class="span4">
	<div style="">

<div style="margin-bottom: 0px;margin-top:-4%;margin-left:39%;"> 
<a href="#" onclick="javascript:onInPatient()">In-Patient</a> &nbsp;&nbsp;<img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> </div>
<div style="margin-bottom: 0px;margin-top: -3%;margin-left: 47%;">Out-Patient &nbsp;&nbsp;<img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle">  </div>
<div style="margin-bottom: 0px;margin-top: -3%;margin-left: 46%;">Cronic &nbsp;&nbsp;<img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> </div>
<div style="margin-bottom: 0px;margin-top: -3%;margin-left: 51%;">Matemity &nbsp;&nbsp;<img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle">  </div>
<div style="margin-bottom: 0px;margin-top: -3%;margin-left: 44%;">Optical &nbsp;&nbsp;<img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> </div>
<div style="margin-bottom: 0px;margin-top:-3%;margin-left:26%;">Alternative Medicine&nbsp;&nbsp;<img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle">  </div>
<div style="margin-bottom: 0px;margin-top:-3%;margin-left:47%;"> 
<a href="#" onclick="javascript:onDental()">Dental </a>&nbsp;&nbsp;<img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"></div>
<div style="margin-bottom: 0px;margin-top:-3%;margin-left:37%;">Pharmacy &nbsp;&nbsp;<img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle">  </div>
<div style="margin-bottom: 0px;margin-top:-3%;margin-left:36%;">MRI & CT Scans &nbsp;&nbsp;<img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"></div>

<div style="margin-bottom: 0px;margin-top:-3%;margin-left:37%;">Pharmacy &nbsp;&nbsp;<img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle">  </div>
<div style="margin-bottom: 0px;margin-top:-3%;margin-left:36%;">MRI & CT Scans &nbsp;&nbsp;<img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"></div>
        </div>
	</div>
	</div>
	

<div class="row-fluid">	

<div class="span2" style="padding-bottom:15%;"><br /><img src="/ttk/images/Insurance/pes_info_tab.png" alt=""></div>
<div class="span8">


<h3 style="text-align:left; "> Claims</h3>
<table class="table table-striped" >
    <thead><tr><th colspan="3">Name:ABCDEF</th><th colspan="3">Spouse</th></tr>
	<tr><th>Auth No.</th><th>Date From</th><th>Date To</th><th>Requested Amt.</th><th>Auth Amt.</th><th>Status</th></tr>
	</thead>
      <tbody>

        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
<td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
        </tr>
         <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
<td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
        </tr>
		 <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
<td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
        </tr>
		 <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
<td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
        </tr>
		 <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
<td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
        </tr>
		<tr>&nbsp;<td colspan="6" class="background_a8e08e" style="text-align:center">Click On Claim Number To View Details</td></tr>
              
      </tbody>
    </table></div>
	
	<div class="span2" style="width: 8%;">
	
	</div></div>








	<div class="row-fluid">	

<div class="span2" style="padding-bottom:15%;"><br /><img src="/ttk/images/Insurance/pes_info_tab.png" alt=""></div>
<div class="span8">


<h3 style="text-align:left; "> Authourisation</h3>
<table class="table table-striped" >
    <thead><tr><th colspan="3">Name:ABCDEF</th><th colspan="3">Spouse</th></tr>
	<tr><th>Auth No.</th><th>Date From</th><th>Date To</th><th>Requested Amt.</th><th>Auth Amt.</th><th>Status</th></tr>
	</thead>
      <tbody>

        <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
<td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
        </tr>
         <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
<td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
        </tr>
		 <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
<td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
        </tr>
		 <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
<td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
        </tr>
		 <tr>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
<td>&nbsp;</td>
          <td>&nbsp;</td>
		            <td>&nbsp;</td>
        </tr>
		<tr><td colspan="6" class="background_a8e08e" style="text-align:center">Click On Claim Number To View Details</td></tr>
              
      </tbody>
    </table></div>
	
	<div class="span2" style="width: 8%;">
	
	</div></div>




  	<div class="row-fluid" align="center">

<div style="">
<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onBackRetail()">Back to Detailed Entry </a></p>
<p style="margin-bottom: 0px; margin-left: 1%; margin-top: -1%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onCorporate()">Back to Corporate Page</a></p>
<br />
</div>

                 </div>     </div> </div>
                    



             </div>
       </div>
			<!--End Main Content Area here-->

   

</div>
<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
</div>
<input type="hidden" name="mode" value="">
</body>
</html:form>

