<!DOCTYPE HTML>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/onlineforms/insuranceLogin/focusedViewProceed.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<head>
    <meta charset="utf-8">
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


function onFocusedView()
{
	document.forms[1].mode.value="doFocusedView";
   	document.forms[1].action="/InsuranceLoginAction.do";
   	document.forms[1].submit();
}

</script>
<html:form action="/InsuranceLoginAction.do" >
<body id="pageBody">
<div class="contentArea" id="contentArea">
<div style="background-image:url('/ttk/images/Insurance/content.png');">

<div class="container"  style="background:#fff;">
    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<!-- <div id="navigateBar"> Corporate > Focused View</div> -->
<div id="contentOuterSeparator"></div>
<h4 class="sub_heading">Corporate - Detailed</h4>
<div id="contentOuterSeparator"></div>
</div>



<div id="contentOuterSeparator"></div>

      <div style="width:90%;margin-left:auto;margin-right:auto;">
	  	<div class="row-fluid">

<div class="span4">
<div style="margin-top:50px">
<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onTOB()">TOB</a></p>
<p style="margin-bottom: 0px;margin-left:1%;margin-top: -4%;"><img class="hexagon_small" src="ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onEndorsements()">Endorsements</a></p>
<br />
</div>
</div>
	

<div class="row-fluid">	
                 <div class="span12" align="center" >

<!-- <div class="span2" style="padding-bottom:15%;"><br /><p>&nbsp;</p>
<p>&nbsp;</p><img src="ttk/images/Insurance/pes_info_tab.png" alt=""></div> -->
<div class="span4">


<h3 style="text-align:center; padding-top:25%;"> Personal Information</h3>
<table class="table table-striped" border="1">
    
      <tbody>

        <tr>
          <th colspan="2">Personal Information</th>
        </tr>
        <tr>
          <th>Name</th>
          <td><bean:write name="frmInsCorporate" property="personalInfoVO.name"/> </td>
        </tr>
        <tr>
          <th>Date of birth</th>
          <td><bean:write name="frmInsCorporate" property="personalInfoVO.dob"/></td>
        </tr>
        <tr>
          <th>Age</th>
          <td><bean:write name="frmInsCorporate" property="personalInfoVO.age"/></td>
       <tr>
          <th>Gender</th>
          <td><bean:write name="frmInsCorporate" property="personalInfoVO.gender"/></td>
        </tr><tr>
          <th>Marital Status</th>
          <td><bean:write name="frmInsCorporate" property="personalInfoVO.maritalStatus"/></td>
        </tr>
 
         <tr>
        <th colspan="2" align="center"><h5>Dependent Details</h5> </th>
        </tr>
        
        <tr>
        <td colspan="2">
         <ttk:DependentDetails/>
        </td>
        </tr>
       
        
      </tbody>
    </table></div>
<!-- <div class="span2" style="margin-left=0%"><br /><img src="ttk/images/Insurance/policy.png" alt="">


</div> -->

<div class="span1"> &nbsp; </div>

<div class="span7">
<h3 style="text-align:center;"> Policy Information</h3>

<table class="table table-striped" border="1">
     
      <tbody>
      <tr>
          <th>Policy Number</th>
          <td style="text-align:right;"><bean:write name="frmInsCorporate" property="policyDetailVO.policyNo"/></td>
        </tr>
        <tr>
          <th>Inception Date of the policy</th>
          <td style="text-align:right;"><bean:write name="frmInsCorporate" property="policyDetailVO.dateOfInception"/></td>
        </tr>
        <tr>
          <th>Expiry Date of the policy</th>
          <td style="text-align:right;"><bean:write name="frmInsCorporate" property="policyDetailVO.expiryDate"/></td>
        </tr>
        <tr>
          <th>Joining Date of the insured</th>
          <td style="text-align:right;"><bean:write name="frmInsCorporate" property="policyDetailVO.joiningDate"/></td>
        </tr>
        <tr>
          <th>Date of exit from policy</th>
          <td style="text-align:right;"> <bean:write name="frmInsCorporate" property="policyDetailVO.exitDate"/></td>
        </tr>
       <tr>
          <th>Aggregate sum insured</th>
          <td style="text-align:right;"><bean:write name="frmInsCorporate" property="policyDetailVO.aggSumInsured"/></td>
        </tr>
        <tr>
          <th>Balance sum insured</th>
          <td style="text-align:right;"><bean:write name="frmInsCorporate" property="policyDetailVO.balSumInsured"/></td>
        </tr>
         <tr>
          <th>Employee Number</th>
          <td style="text-align:right;"><bean:write name="frmInsCorporate" property="policyDetailVO.employeeNbr"/></td>
        </tr>
        <tr>
          <th>Network</th>
          <td style="text-align:right;"><bean:write name="frmInsCorporate" property="policyDetailVO.network"/></td>
        </tr>
        <tr>
          <th>Product name</th>
          <td style="text-align:right;"><bean:write name="frmInsCorporate" property="policyDetailVO.productName"/></td>
        </tr>
        
        <tr>
          <th>Special Exclusions</th>
          <td style="text-align:right;"><bean:write name="frmInsCorporate" property="policyDetailVO.specialExclusions"/></td>
        </tr>
        <tr>
          <th>Waiting period</th>
          <td style="text-align:right;"><bean:write name="frmInsCorporate" property="policyDetailVO.waitingPeriod"/></td>
        </tr>
        <tr>
          <th>Benefit Tree with sublimit</th>
          <td style="text-align:left;">
          <ul> <li>IP Limit - <bean:write name="frmInsCorporate" property="policyDetailVO.ipLimit"/></li>
          <li>OP  Limit - <bean:write name="frmInsCorporate" property="policyDetailVO.opLimit"/></li>
          <li>Chronic Limit - <bean:write name="frmInsCorporate" property="policyDetailVO.chronicLimit"/></li>
          <li>Maternity 
          <ul>
          <li>Maternity Limit - <bean:write name="frmInsCorporate" property="policyDetailVO.maternity"/></li> 
          <li>Normal Delivery - <bean:write name="frmInsCorporate" property="policyDetailVO.maternityNormal"/></li>
          <li>C-Section - <bean:write name="frmInsCorporate" property="policyDetailVO.maternityCSection"/></li> </ul>
          </li>
          
          <li>Dental Limit - <bean:write name="frmInsCorporate" property="policyDetailVO.dentalLimit"/></li>
          <li>Optical Limit - <bean:write name="frmInsCorporate" property="policyDetailVO.opticalLimit"/></li>
          <li>Alternative Medicine Limit - <bean:write name="frmInsCorporate" property="policyDetailVO.alternativeMedicineLimit"/></li>
          <li>Physiotherapy Limit - <bean:write name="frmInsCorporate" property="policyDetailVO.physiotherapyLimit"/></li>
          <li>Pharmacy - <bean:write name="frmInsCorporate" property="policyDetailVO.pharmacy"/></li>
          <li>MRI and CT scan - <bean:write name="frmInsCorporate" property="policyDetailVO.mriandCTscan"/></li>
          </ul></td>
        </tr>
        
      </tbody>
    </table>

</div></div>

<!-- <div class="span2" style="padding-bottom:15%;"><br /><img src="ttk/images/Insurance/pes_info_tab.png" alt=""></div> -->


	<div class="span12">
	<h4 style="text-align:left; "> Claims</h4> <!-- <a href="#" onclick="javascript:showClaims()">Show Claims</a> -->
    
    <!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData_Claims" className="table table-striped"/>

	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		    <ttk:PageLinks name="tableData_Claims"/>
	</table>
    </div>
	
<div class="span12">
<h4 style="text-align:left; "> Pre-Approval</h4> <!-- <a href="#" onclick="javascript:showAuthorizations()">Show Authorizations</a> -->

<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData_Authorizations" className="table table-striped"/>

	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		    <ttk:PageLinksNew name="tableData_Authorizations"/>
	</table>
	
	</div>
  	<div class="row-fluid" align="center">

<div style="">
<p style="margin-bottom:0px;margin-left:12%"><img class="hexagon_small" src="ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onFocusedView()">Back to Detailed Entry</a> </p>
<p style="margin-bottom: 0px; margin-left: 1%; margin-top: -1%;"><img class="hexagon_small" src="ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle"> &nbsp;&nbsp;&nbsp;
<a href="#" onclick="javascript:onBackToCorporate()" >Back to Corporate Page</a></p>
<br />
</div>

                 </div>     
                 
                 		</div>
                  </div>
                    
             </div>
       </div>
			<!--End Main Content Area here-->
</div>
</div>
</div>
</div>

<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<input type="hidden" name="mode" value="">
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="modeType" value="<%=request.getAttribute("modeType")%>">
	<input type="hidden" name="policySeqId" value="<bean:write name="frmInsCorporate" property="policyDetailVO.policySeqId"/>">
</body>
</html:form>
