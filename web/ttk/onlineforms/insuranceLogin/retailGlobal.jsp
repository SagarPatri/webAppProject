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
function onRetailFocusedView()
{
	document.forms[1].mode.value="doRetailFocused";
   	document.forms[1].action="/InsuranceLoginAction.do";
   	document.forms[1].submit();
}

</script>
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
	<style type="text/css">
	.dropup, .dropdown {
    border: 2px solid #ffffff;
    border-radius: 5px;
	}
	
	.navbar .nav-pills > li > a {
  
    padding: 7px 30px;
   
}
	#categories{
  overflow:hidden;
  width:510px;
  margin:0 auto;
}
.clr:after{
  content:"";
  display:block;
  clear:both;
}
#categories li{
  position:relative;
  list-style-type:none;
  width:27.85714285714286%; /* = (100-2.5) / 3.5 */
  padding-bottom: 32.16760145166612%; /* =  width /0.866 */
  float:left;
  overflow:hidden;
  visibility:hidden;
 
  -webkit-transform: rotate(-60deg) skewY(30deg);
  -ms-transform: rotate(-60deg) skewY(30deg);
  transform: rotate(-60deg) skewY(30deg);
}
#categories li:nth-child(3n+2){
  margin:0 1%;
}
#categories li:nth-child(6n+4){
  margin-left:0.5%;
}
#categories li:nth-child(6n+4), #categories li:nth-child(6n+5), #categories li:nth-child(6n+6) {
	margin-top: -6.9285714285%;
  margin-bottom: -6.9285714285%;
  
  -webkit-transform: translateX(50%) rotate(-60deg) skewY(30deg);
  -ms-transform: translateX(50%) rotate(-60deg) skewY(30deg);
  transform: translateX(50%) rotate(-60deg) skewY(30deg);
}
#categories li:nth-child(6n+4):last-child, #categories li:nth-child(6n+5):last-child, #categories li:nth-child(6n+6):last-child{
  margin-bottom:0%;
}
#categories li *{
  position:absolute;
  visibility:visible;
}
#categories li > div{
  width:100%;
  height:100%;
  text-align:center;
  color:#000;
  overflow:hidden;
  
  -webkit-transform: skewY(-30deg) rotate(60deg);
  -ms-transform: skewY(-30deg) rotate(60deg);
  transform: skewY(-30deg) rotate(60deg);
  
	-webkit-backface-visibility:hidden;
  
}

/* HEX CONTENT */
#categories li img{
  left:-100%; right:-100%;
  width: auto; height:100%;
  margin:0 auto;   
}

#categories div h1, #categories div p{
  width:90%;
  padding:0 5%;
  //background-color:rgb(94, 142, 55); 
  //background-color: rgb(94, 142, 55);
  font-family: 'Raleway', sans-serif;
  
  -webkit-transition: top .2s ease-out, bottom .2s ease-out, .2s padding .2s ease-out;
  -ms-transition: top .2s ease-out, bottom .2s ease-out, .2s padding .2s ease-out;
  transition: top .2s ease-out, bottom .2s ease-out, .2s padding .2s ease-out;
}
#categories li h1{
  bottom:110%;
  font-style:italic;
  font-weight:normal;
  font-size:1.5em;
  padding-top:100%;
  padding-bottom:100%;
}
#categories li h1:after{
	content:'';
  display:block;
  position:absolute;
  bottom:-1px; left:45%;
  width:10%;
  text-align:center;
  z-index:1;
  border-bottom:2px solid #fff;
}
#categories li p{
	padding-top:30%;
	padding-bottom:50%;
}


/* HOVER EFFECT  */

#categories li div:hover h1 {

}

#categories li div:hover p{

}
	</style>
</head>
                  
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
<h4 class="sub_heading">Summary</h4>
<div id="contentOuterSeparator"></div>
</div>



<div id="contentOuterSeparator"></div>

      <div style="width:90%;margin-left:auto;margin-right:auto;">
	

<div class="row-fluid">	
                 <div class="span12" align="center" >

<div class="span2" style="padding-top:15%;padding-bottom:15%;"><img src="/ttk/images/Insurance/pes_info_tab.png" alt=""></div>
<div class="span4">


<h3 style="text-align:left; padding-top:40%;"> Pre-Authourization</h3>
<table class="table table-striped" >
    <thead><th>Preauth Type</th><th>Number</th><th>Amount-OMR</th><th>As On Date</th></thead>
      <tbody>

        <tr>
          <td width="40%">Number of preapprovals  
			<br/>received</td>
          <td>&nbsp;</td>
		   <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
       
          <tr>
       <td  width="40%">Total Amount of 
			<br/>preapprovals received</td>
          <td>&nbsp;</td>
		   <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
       
           <tr>
          <td  width="40%">Number of preapproval 
			<br/>approved as on date</td>
          <td>&nbsp;</td>
		   <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
       
          <tr>
           <td  width="40%">Total Amount of 
			<br/>preapprovals authorized</td>
          <td>&nbsp;</td>
		   <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        
        <tr>
           <td  width="40%">Number of preapproval  
			<br/>denied as on date</td>
          <td>&nbsp;</td>
		   <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        
         <tr>
           <td  width="40%">Total amount of  
			<br/>preapprovals denied</td>
          <td>&nbsp;</td>
		   <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        
        <tr>
           <td  width="40%">Number of preapprovals   
			<br/>outstaning</td>
          <td>&nbsp;</td>
		   <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        
        <tr>
           <td width="40%">Total amount of    
			<br/>preapprovals outstanding</td>
          <td>&nbsp;</td>
		   <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
        
        <tr>
           <td width="40%">TAT for approvals</td>
          <td>&nbsp;</td>
		   <td>&nbsp;</td>
          <td>&nbsp;</td>
        </tr>
      </tbody>
    </table></div>
<div class="span2" style="margin-left=0%"><br /><img src="/ttk/images/Insurance/enrolment_tab1.png" alt="">


</div>

<div class="span4">
<h3 style="text-align:left;"> Enrolment </h3>

<table class="table table-striped">
     
      <tbody>

        <tr>
          <td>Number of Policies</td>
          <td><p></p></td>
        </tr>
      
          <tr>
         <td>Number of Lives</td>
          <td><p></p></td>
        </tr>
      
           <tr>
         <td>Total Gross Premium</td>
          <td><p></p></td>
        </tr>
      
          <tr>
          <td>Total Earned Premium</td>
          <td><p></p></td>
        </tr>
     
        
      </tbody>
    </table>

</div></div></div>
<div class="row-fluid" >	
<div class="span2" style="padding-top:20%;" ><br /><img src="/ttk/images/Insurance/pes_info_tab.png" alt="" ></div>
	
<div class="span4" style="margin-top:20%;">





<h3> Claims</h3>


<table class="table table-striped" >
    <thead><!-- <tr><th colspan="2">Name:ABCDEF</th><th colspan="2">Spouse</th></tr>
	<tr><th>Claim No.</th><th>Date From</th><th>Date To</th><th>Claimed Amt.</th></tr> -->
	<tr><th rowspan="2" align="center">Claims</th><th colspan="2" align="center">Data</th> </tr>
	<tr><th>Month</th><th>As on date</th></tr>
	
	</thead>
      <tbody>

        <!-- <tr>
          <td>122454</td>
        
<td>122454</td>
          <td>1122222</td>
		            <td>Complete</td>
        </tr>
         <tr>
          <td>122454</td>
        
<td>122454</td>
          <td>1122222</td>
		            <td>Complete</td>
        </tr>
		 <tr>
          <td>122454</td>
        
<td>122454</td>
          <td>1122222</td>
		            <td>Complete</td>
        </tr>
		 <tr>
          <td>122454</td>
        
<td>122454</td>
          <td>1122222</td>
		            <td>Complete</td>
        </tr>
		 <tr>
          <td>122454</td>
       
<td>122454</td>
          <td>1122222</td>
		            <td>Complete</td>
        </tr>		<tr><td colspan="4"><p align="center">Click On Claim Number To View Details</p></td></tr> -->
        
        <tr>
<td> Total claims Paid </td>
<td></td>
<td> </td>
</tr>

<tr>
<td> Number of claims outstanding   </td>
<td></td>
<td> </td>
</tr>

<tr>
<td> Outstanding Claims  amount   </td>
<td></td>
<td> </td>
</tr>

<tr>
<td> Number of claims reported </td>
<td></td>
<td> </td>
</tr>

<tr>
<td> Reported claims amount </td>
<td></td>
<td> </td>
</tr>

<tr>
<td> Number of claims approved </td>
<td></td>
<td> </td>
</tr>

<tr>
<td> Total claims approved amountd </td>
<td></td>
<td> </td>
</tr>

<tr>
<td> Number of claims denied
 </td>
<td></td>
<td> </td>
</tr>

<tr>
<td> Total claims denied amount
 </td>
<td></td>
<td> </td>
</tr>

<tr>
<td> Average TAT for claims
 </td>
<td></td>
<td> </td>
</tr>

<tr>
<td> Top 5 Employees based on utilization 
 </td>
<td></td>
<td> </td>
</tr>

<tr>
<td> Top 5 Providers  based on utilization 
 </td>
<td></td>
<td> </td>
</tr>
        

      </tbody>
    </table></div>
	
	
	<div class="span2" style="padding-bottom:15%;"><br /><img src="/ttk/images/Insurance/pes_info_tab.png" alt=""></div>

	
	<div class="span2">


<h3 style="text-align:center; "> Network</h3>
<table class="table table-striped" >
    <thead>
	<!-- <tr><th>Provider Type</th><th>Number</th></tr> -->
	<tr><th colspan="2">Network Type</th></tr>
	</thead>
      <tbody>
      <tr>
	  		<th>&nbsp;</th>
      		<td>&nbsp;</td>
		</tr>
		<tr>
	  		<th> </th>
      		<td>&nbsp;</td>
		</tr>
		<tr>
	  		<th>&nbsp;</th>
      		<td>&nbsp;</td>
		</tr>
		<tr>
	  		<th>&nbsp;</th>
      		<td>&nbsp;</td>
		</tr>
		<tr>
	  		<th>&nbsp;</th>
      		<td>&nbsp;</td>
		</tr>
		<tr>
	  		<th>&nbsp;</th>
      		<td>&nbsp;</td>
		</tr>
		
        <!-- <tr>
          
          <td>provider</td>
		            <td>111</td>
        </tr>
         <tr>
          
        <td>provider</td>
		            <td>111</td>
        </tr>
		 <tr>
         
      <td>provider</td>
		            <td>111</td>
        </tr>
		 <tr>
          
          <td>provider</td>
		            <td>111</td>
        </tr>
		 <tr>
        <td>provider</td>
		            <td>111</td>
        </tr>
			 <tr>
          
          <td>provider</td>
		            <td>111</td>
        </tr>
		 <tr>
        <td>provider</td>
		            <td>111</td>
        </tr> -->
      </tbody>
    </table></div>
	<div class="span2" style="padding-bottom:7%;"><br /><img src="/ttk/images/Insurance/networ_hex_tabs.png" alt=""></div>

</div>
	
	
	</div>
		<div class="row-fluid" style="margin-top: -12%;">	
	<div class="span2"></div><div class="span4"></div>
		<div class="span2">
	<div style="padding-top:60%;"><img src="/ttk/images/Insurance/call_center_tab.png" alt=""></div>
	</div>
	<div class="span4">
	<h3 style="text-align:left; padding-top:25%;"> Call Center</h3>
	<table class="table table-striped" >
    <thead>
	<tr><th colspan=2>Number of Calls</th></tr>
	</thead>
      <tbody>

        <tr>
          
          <td>This Month</td>
		            <td></td>
        </tr>
         <tr>
          
        <td>This Year</td>
		            <td></td>
        </tr>
		 <tr>
         
      <td>Overall</td>
		            <td></td>
        </tr>
      </tbody>
    </table>
		<h3 style="text-align:left;"> Details</h3>

	</div></div>
	<div class="row-fluid">	


	

	</div>

  	<div class="row-fluid" align="center">

<div style="">
<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onBackRetail()">Back To Corporate Page</a> </p>
<br>
</div>

                 </div>     </div> </div>
                    



             </div>
       </div>
			<!--End Main Content Area here-->

   


<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<input type="hidden" name="mode" value="">
</body>
</html:form>

