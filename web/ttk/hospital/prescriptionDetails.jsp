<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@page import="com.ttk.dto.empanelment.LabServiceVO"%>
<%
/** @ (#) prescriptionDetails.jsp 
 * Project     : Vidal Healthcare Services
 * File        : prescriptionDetails.jsp
 * Author      : kishor kumar 
 * Company     : RCS Technologies
 * Date Created: 05/03/2015
 *
 * @author 		 : kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
<%@ page import="java.util.ArrayList,com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/hospital/prescriptionDetails.js"></script>


<head>

	<link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
	<link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>

	<script src="jquery-1.11.1.min.js"></script>
	
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
	<script src="js/jquery.autocomplete.js"></script>
      
<SCRIPT>
  $(document).ready(function() {
	  var hospSeqId	=	document.forms[1].hospSeqId.value;
    $("#clinicianID").autocomplete("auto.jsp?mode=preAuthProfessions&hospSeqId="+hospSeqId);
});
  
function getClinicainId(obj)
{
  var hospSeqId	=	document.forms[1].hospSeqId.value;
  $(document).ready(function() {
  $("#clinicianID").blur(function(){
    	var ID	=	obj.value;
        $.ajax({
        		url: "/AsynchronousAction.do?mode=getCommonMethod&id="+ID+"&getType=clinicianId&hospSeqId="+hospSeqId, 
        		success: function(result){
      				var res	=	result.split("@");
					document.forms[1].clinicianId.value		=	res[0];
					document.forms[1].speciality.value		=	res[1]; 
					
        		}}); 
   		 });
  });
}

function onChangeAccident(obj){
 $(document).ready(function() {
	  $("#accidentCase").change(function(){
	    	var ID	=	obj.value;
	        if(ID=='Y')
        	{
        		document.getElementById("firNo").style.display	=	"inline";
        	}
	        else{

        		document.getElementById("firNo").style.display	=	"none";
	        }
	   		 });
	  });
}

$(document).ready(function(){
    $("#firNumb").on("focus blur", function(){
    	var	val	=	document.getElementById("firNumb").value;
  	  if(val=='FIR No.')
  		  document.getElementById("firNumb").value="";
  	  else if(val=='')
  		  document.getElementById("firNumb").value='FIR No.';
    });
});


</SCRIPT>

</head>
<%
UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
%>

<style type="text/css">
/*----- Tabs -----*/
.tabs {
    width:100%;
    display:inline-block;
}
 
    /*----- Tab Links -----*/
    /* Clearfix */
    .tab-links:after {
        display:block;
        clear:both;
        content:'';
        color:#4c4c4c;
    }
 
    .tab-links li {
        margin:0px 5px;
        float:left;
        list-style:none;
    }
 
        .tab-links a {
            padding:5px 10px;
            display:inline-block;
            border-radius:2px 2px 0px 0px;
            background:#7FB5DA;
            font-size:11px;
            font-weight:600;
            color:#4c4c4c;
            transition:all linear 0.15s;
        }
 
        .tab-links a:hover {
            background:#a7cce5;
            text-decoration:none;
        }
 
    li.active a, li.active a:hover {
        background:#fff;
        color:#4c4c4c;
    }
 
    /*----- Content of Tabs -----*/
    .tab-content {
        padding:1px;
        border-radius:3px;
        box-shadow:-1px 1px 1px rgba(0,0,0,0.15);
        background:#fff;
    }
 
        .tab {
            display:none;
        }
 
        .tab.active {
            display:block;
        }
</style>
<script type="text/javascript">
jQuery(document).ready(function() {
    jQuery('.tabs .tab-links a').on('click', function(e)  {
        var currentAttrValue = jQuery(this).attr('href');
 
        // Show/Hide Tabs
        jQuery('.tabs ' + currentAttrValue).show().siblings().hide();
 
        // Change/remove current tab to active
        jQuery(this).parent('li').addClass('active').siblings().removeClass('active');
 
        e.preventDefault();
    });
});
</script>
<%

pageContext.setAttribute("medicineSystem",Cache.getCacheObject("medicineSystem"));
pageContext.setAttribute("ailmentClaimType",Cache.getCacheObject("ailmentClaimType"));
pageContext.setAttribute("surgeryType",Cache.getCacheObject("surgeryType"));
pageContext.setAttribute("treatmentPlan",Cache.getCacheObject("treatmentPlan"));

DynaActionForm frmOnlinePreAuth	=	(DynaActionForm)request.getSession().getAttribute("frmOnlinePreAuth");
%>
<html:form action="/OnlinePreAuthAction.do" method="post" enctype="multipart/form-data">
<body id="pageBody">

		<div class="contentArea" id="contentArea">
			<!-- S T A R T : Content/Form Area -->
			<div
				style="background-image: url('/ttk/images/Insurance/content.png'); background-repeat: repeat-x;">
				<div class="container" style="background: #fff;">

					<div class="divPanel page-content">
						<!--Edit Main Content Area here-->
						<div class="row-fluid">

							<div class="span8">
								<!-- <div id="navigateBar">Home > Corporate > Detailed > Claim Details</div> -->
								<div id="contentOuterSeparator"></div>
								<h4 class="sub_heading">Pre - Authorization</h4>
								<html:errors />
								<div id="contentOuterSeparator"></div>
							</div>

						</div>
						<div class="row-fluid">
							<div style="width: 100%;">
								<div class="span12" style="margin: 0% 0%">
	<!-- S T A R T : Page Title -->
	<html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">
		<!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:message name="updated" scope="request"/>
					</td>
				</tr>
			</table>
		</logic:notEmpty>
		<!-- E N D : Success Box -->
		<fieldset>
		<legend> Member Details</legend>
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		     <tr>
		        <td width="15%"><b>Vidal Id :</b></td>
		        <td width="30%" align="left"><bean:write name="frmOnlinePreAuth" property="enrollId"/></td>
		        <td width="15%"><b>Card Holder Name :</b></td>
		        <td width="30%" align="left" ><bean:write name="frmOnlinePreAuth" property="insurredName"/></td>
	      	</tr>
	      	<tr>
		        <td width="15%"><b>Age :</b></td>
		        <td width="30%" align="left" ><bean:write name="frmOnlinePreAuth" property="age"/></td>
		        <td width="15%"><b>Gender :</b></td>
		        <td width="30%"  align="left"><bean:write name="frmOnlinePreAuth" property="gender"/></td>
	      	</tr>
	      	<tr>
		        <td width="15%"><b>Payer :</b></td>
		        <td width="30%"  align="left"><bean:write name="frmOnlinePreAuth" property="payer"/></td>
		        <td width="15%"><b>Treatment date :</b></td>
		        <td width="30%"  align="left"> <bean:write name="frmOnlinePreAuth" property="treatmentDate"/> </td>
	      	</tr>
	      	<tr>
		        <td width="15%"><b>Provider Name :</b></td>
		        <td width="30%"  align="left"><bean:write name="frmOnlinePreAuth" property="hospitalName"/></td>
		        <td width="15%"><b>Intimation No. </b></td>
		        <td width="30%"  align="left"> <bean:write name="frmOnlinePreAuth" property="intimationNo"/> </td>
	      	</tr>
	      	
		    </table>
		    </fieldset>
		    <br>
		  <fieldset>
		<legend> General</legend>
		  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	      	<tr>
		        <td width="25%">Medical Record No :</td>
		        <td width="25%"  align="left"><html:text property="medicalRecordNo" name="frmOnlinePreAuth" ></html:text> </td>
		        <td width="25%">Emergency : &nbsp;&nbsp;&nbsp;&nbsp; <html:checkbox property="emergencyYN"></html:checkbox> </td> 
		      
	      	</tr>
	      	<tr>
		      	<td width="25%">Clinician Name :</td>
		      	<td width="25%"  align="left"><html:text property="clinicianName" styleClass="textBoxLongIntX" styleId="clinicianID" onblur="getClinicainId(this)" name="frmOnlinePreAuth" /> </td>
		        <td width="25%">Clinician ID :</td>
		        <td width="25%" align="left">
		        	<html:text property="clinicianId" name="frmOnlinePreAuth" styleClass="textBoxMediumIntX" />
		        </td>
	      	</tr>
	      	<tr>
		        <td width="25%">Speciality :</td>
		        <td width="25%" align="left">
		        	<html:text property="speciality" name="frmOnlinePreAuth" styleClass="textBoxMediumIntX"/>
		       </td>
		       
		        <td width="25%"><b>Invoice Number :</b></td>
		        <td width="25%" align="left"><html:text property="invoiceNo" name="frmOnlinePreAuth"/></td>
	      	
	      	</tr>
		    </table>
		 </fieldset>
		 
		    
		 
		 <fieldset>
		<legend> Consultation:</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    <tr>
		        <td width="25%">Presenting Complaints:</td>
		        <td width="25%"  align="left"><html:text property="consultation" name="frmOnlinePreAuth"/></td>
		        <td width="25%">System of Medicine:</td>
		        <td width="25%"  align="left">
		        	<html:select property="systemOfMedicine" onchange="javascript:showNoofVisits();" name="frmOnlinePreAuth">
		            <html:options collection="medicineSystem" property="cacheId" labelProperty="cacheDesc"/>
		      	</html:select>
				</td>
	      	</tr>
	      	<tr>
		        <%-- <td width="25%">Medical Related Case:</td>
		        <td width="25%" align="left">
					<html:select property="otpcode" styleClass="selectBox">
		            <html:options collection="ailmentClaimType" property="cacheId" labelProperty="cacheDesc"/>
		      	</html:select>
				</td> --%>
				<td width="25%">Accident Related Cases:</td>
		        <td width="25%" align="left">
				<html:select property="accidentYN" styleId="accidentCase" onchange="onChangeAccident(this)" name="frmOnlinePreAuth">
		            <html:option value="">--Select from List--</html:option>
		            <html:option value="Y">Yes</html:option>
		            <html:option value="N">No</html:option>
		      	</html:select>
		      	<br>
		      	<div  id="firNo" style="display: none;">
		      	FIR No. : <html:text property="firNo" styleId="firNumb" name="frmOnlinePreAuth" value="FIR No."/> 
		      	<!-- <input type="text" name="firNo" id="firNumb" class="textBox textBoxSmall" value="FIR No."/> -->
		      	</div>
				</td>
	      		<td width="25%">Procedure Type:</td>
		        <td width="25% align="left">
					<html:select property="procedureType" styleClass="selectBox" name="frmOnlinePreAuth">
					<html:option value="">Select from list</html:option>
					<html:options collection="surgeryType" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
				</td>
	      	</tr>
	      	<tr>
		        <td width="25%">Since when:</td>
		        <td width="25%" align="left"> 
		        	<html:select property="sinceWhen" styleClass="selectBox" name="frmOnlinePreAuth">
					<html:option value="">Select from list</html:option>
					<html:option value="1">1</html:option>
					<html:option value="2">2</html:option>
					<html:option value="3">3</html:option>					
					<html:option value="4">4</html:option> 
					</html:select>&nbsp; days 
				</td>
				
		        <td width="25%">Plan of Treatment:</td>
		        <td width="25%" align="left">
		        	<html:select property="planOfTreatment" styleClass="selectBox selectBoxMedium" name="frmOnlinePreAuth">
          			<html:option value="">Select from list</html:option>
		    		<html:options collection="treatmentPlan" property="cacheId" labelProperty="cacheDesc"/>
		  			</html:select>
		        </td>
	      	</tr>
	      	<tr>
		        <td width="25%">Estimation:</td>
		        <td width="25%" align="left"><html:text property="estimation" name="frmOnlinePreAuth"/></td>
	      	</tr>
	      	<tr>
			        <td width="25%">Blood Pressure - Systolic: </td>
			        <td width="25%">
			          <html:text property="bpSystolic" size="3" maxlength="3" name="frmOnlinePreAuth"/>&nbsp;mm HG
			        </td>
			        <td width="25%">Blood Pressure - Diastolic:</td>
		        <td width="25%">
		          <html:text property="bpDiastolic" size="3" maxlength="3" name="frmOnlinePreAuth"/>&nbsp;mm HG
		        </td>
      		</tr>
		      	
	      	<tr>
		        
		        <td width="25%">Details of Treatment:</td>
		        <td align="left" colspan="3">
		        	<html:textarea property="detailsOfTreatment" styleClass="textAreaMedium" name="frmOnlinePreAuth" rows="2"/>
		        	
		        </td>
	      	</tr>
	      	<tr>
	      	<td width="25%">Medical Findings:</td>
		        <td align="left" colspan="3">
		        <html:textarea property="medicalFindings" styleClass="textAreaMedium" name="frmOnlinePreAuth" rows="2"/>
		        </td>
		    </tr>
		    </table>
		</fieldset>
		
    <!-- S T A R T : Buttons -->
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
	        <td width="100%" align="center">
			  <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveGeneral();">S<u>a</u>ve</button>&nbsp;
	        </td>
      	</tr>
    </table>
    <!-- E N D :  -->
		
		<fieldset>
		<legend> Prescriptions </legend>
	
		<div class="tabs">
    <ul class="tab-links">
        <li class="active"><a href="#tab1">Laboratory</a></li>
        <li><a href="#tab2">Radiology</a></li>
        <li><a href="#tab3">Minor Surgeries/Services</a></li>
        <li><a href="#tab4">Surgery</a></li>
        <li><a href="#tab5">Consumables</a></li>
        <li><a href="#tab6">Pharmacy</a></li>
    </ul>
 <br>
 
 
    <div class="tab-content">
<%
ArrayList allabs		= null;
ArrayList alRadios		= null;
ArrayList alSurgeries	= null;
ArrayList alConsumables	= null;
ArrayList alMinors		= null;

HashMap prescriptions	= (HashMap)request.getSession().getAttribute("prescriptions");
if(prescriptions!=null){
	allabs			= (ArrayList)prescriptions.get("LABORATORY");
	alRadios		= (ArrayList)prescriptions.get("RADIOLOGY");
	alSurgeries		= (ArrayList)prescriptions.get("SURGERIES");
	alConsumables	= (ArrayList)prescriptions.get("CONSUMABLES");
	alMinors		= (ArrayList)prescriptions.get("MINORSURGERIES");
}
%>        
            <table id="tab1" class="tab active" align="center" border="1" cellspacing="0" cellpadding="0">
            <tr> <td>&nbsp;</td></tr>
		      <tr> <th colspan="5" align="left"> <strong>Laboratory</strong> <a href="#" onclick="javascript:addLaboratory()"><font color="green">Add Item</font> </a></th></tr>
	      	<tr bgcolor="#126354">
		        <td width="5%"><font color="white"><strong>Sl.No.</strong></font></td>
		        <td width="25%"><font color="white"><strong>Activity Code</strong></font></td>
		        <td width="45%"><font color="white"><strong>Short Description</strong></font></td>
		        <td width="25%" align="right"><font color="white"><strong>Price</strong></font></td>
		        <td> &nbsp;</td>
	      	</tr>
	      	
	      	<%if(allabs!=null)
			{
				for(int i=0;i<allabs.size();i++)
				{
					LabServiceVO labServiceVO	=	(LabServiceVO)allabs.get(i);
					%>
					<tr>
					<td width="25%"><font color="white"><%=(i+1) %></font></td>
			        <td width="25%"><font color="white"><%=labServiceVO.getActivityCode() %></font></td>
			        <td width="25%"><font color="white"><%=labServiceVO.getShortDesc() %></font></td>
			        <td width="25%"><font color="white"><%=labServiceVO.getPrice() %></font></td>
			        </tr>
					<%
				}
			}else{%>
	      	<tr>
		        <td width="100%" colspan="5" align="center"> <font color="red">No Data</font> </td>
	      	</tr>
	      	<%} %>
		    </table>
		    
		    <table id="tab2" class="tab" align="center" border="1" cellspacing="0" cellpadding="0">
            <tr><td>&nbsp;</td></tr>
		     <tr>  <th colspan="5" align="left"> <strong>Radiology</strong> <a href="#" onclick="javascript:addRadiology()"><font color="green">Add Item</font> </a></th></tr>
	      	<tr bgcolor="#126354">
		        <td width="25%"><font color="white"><strong>Sl.No.</strong></font></td>
		        <td width="25%"><font color="white"><strong>Activity Code</strong></font></td>
		        <td width="25%"><font color="white"><strong>Short Description</strong></font></td>
		        <td width="25%"><font color="white"><strong>Price</strong></font></td>
		        <td> &nbsp;</td>
	      	</tr>
	      	<%if(alRadios!=null)
			{
				for(int i=0;i<alRadios.size();i++)
				{
					LabServiceVO labServiceVO	=	(LabServiceVO)alRadios.get(i);
					%>
					<tr>
					<td width="25%"><font color="white"><%=(i+1) %></font></td>
			        <td width="25%"><font color="white"><%=labServiceVO.getActivityCode() %></font></td>
			        <td width="25%"><font color="white"><%=labServiceVO.getShortDesc() %></font></td>
			        <td width="25%"><font color="white"><%=labServiceVO.getPrice() %></font></td>
			        </tr>
					<%
				}
			}else{%>
	      	<tr>
		        <td width="100%" colspan="5" align="center"> <font color="red">No Data</font> </td>
	      	</tr>
	      	<%} %>
		    </table>
		    
		    
		    <table id="tab3" class="tab" align="center" border="1" cellspacing="0" cellpadding="0">
            <tr><td>&nbsp;</td></tr>
		      <tr> <th colspan="5" align="left"> <strong>Minor Surgeries/Services</strong> <a href="#" onclick="javascript:addMinors()"><font color="green">Add Item</font> </a></th></tr>
	      	<tr bgcolor="#126354">
		        <td width="25%"><font color="white"><strong>Sl.No.</strong></font></td>
		        <td width="25%"><font color="white"><strong>Activity Code</strong></font></td>
		        <td width="25%"><font color="white"><strong>Short Description</strong></font></td>
		        <td width="25%"><font color="white"><strong>Price</strong></font></td>
		        <td> &nbsp;</td>
	      	</tr>
	      	<%if(alMinors!=null)
			{
				for(int i=0;i<alMinors.size();i++)
				{
					LabServiceVO labServiceVO	=	(LabServiceVO)alMinors.get(i);
					%>
					<tr>
					<td width="25%"><font color="white"><%=(i+1) %></font></td>
			        <td width="25%"><font color="white"><%=labServiceVO.getActivityCode() %></font></td>
			        <td width="25%"><font color="white"><%=labServiceVO.getShortDesc() %></font></td>
			        <td width="25%"><font color="white"><%=labServiceVO.getPrice() %></font></td>
			        </tr>
					<%
				}
			}else{%>
	      	<tr>
		        <td width="100%" colspan="5" align="center"> <font color="red">No Data</font> </td>
	      	</tr>
	      	<%} %>
		    </table>
		    
		    <table id="tab4" class="tab" align="center" border="1" cellspacing="0" cellpadding="0">
            <tr><td>&nbsp;</td></tr>
		       <tr><th colspan="5" align="left"> <strong>Surgery</strong> <a href="#" onclick="javascript:addSurgery()"><font color="green">Add Item</font> </a></th></tr>
	      	<tr bgcolor="#126354">
		        <td width="25%"><font color="white"><strong>Sl.No.</strong></font></td>
		        <td width="25%"><font color="white"><strong>Activity Code</strong></font></td>
		        <td width="25%"><font color="white"><strong>Short Description</strong></font></td>
		        <td width="25%"><font color="white"><strong>Price</strong></font></td>
		        <td> &nbsp;</td>
	      	</tr>
	      	<%if(alSurgeries!=null)
			{
				for(int i=0;i<alSurgeries.size();i++)
				{
					LabServiceVO labServiceVO	=	(LabServiceVO)alSurgeries.get(i);
					%>
					<tr>
					<td width="25%"><font color="white"><%=(i+1) %></font></td>
			        <td width="25%"><font color="white"><%=labServiceVO.getActivityCode() %></font></td>
			        <td width="25%"><font color="white"><%=labServiceVO.getShortDesc() %></font></td>
			        <td width="25%"><font color="white"><%=labServiceVO.getPrice() %></font></td>
			        </tr>
					<%
				}
			}else{%>
	      	<tr>
		        <td width="100%" colspan="5" align="center"> <font color="red">No Data</font> </td>
	      	</tr>
	      	<%} %>
		    </table>
		    
		    
		     <table id="tab5" class="tab" align="center" border="1" cellspacing="0" cellpadding="0">
            <tr><td>&nbsp;</td></tr>
		       <tr><th colspan="5" align="left"> <strong>Consumables</strong> <a href="#" onclick="javascript:addConsumables()"><font color="green">Add Item</font> </a></th></tr>
		       <tr bgcolor="#126354">
		        <td width="10%"><font color="white"><strong>Sl.No.</strong></font></td>
		        <td width="15%"><font color="white"><strong>Activity Code</strong></font></td>
		        <td width="60%"><font color="white"><strong>Short Description</strong></font></td>
		        <td width="15%"><font color="white"><strong>Price</strong></font></td>
		        <td> &nbsp;</td>
	      	</tr>
	      	<%if(alConsumables!=null)
			{
				for(int i=0;i<alConsumables.size();i++)
				{
					LabServiceVO labServiceVO	=	(LabServiceVO)alConsumables.get(i);
					%>
					<tr>
					<td><font color="white"><%=(i+1) %></font></td>
			        <td><font color="white"><%=labServiceVO.getActivityCode() %></font></td>
			        <td><font color="white"><%=labServiceVO.getShortDesc() %></font></td>
			        <td><font color="white"><%=labServiceVO.getNetAmount() %></font></td>
			        </tr>
					<%
				}
			}else{%>
	      	<tr>
		        <td width="100%" colspan="5" align="center"> <font color="red">No Data</font> </td>
	      	</tr>
	      	<%} %>
		    </table>
		    
		    <table id="tab6" class="tab" align="center" border="1" cellspacing="0" cellpadding="0">
            <tr><td>&nbsp;</td></tr>
		      <tr> <th colspan="5" align="left"> <strong>Pharmacy</strong> <a href="#" onclick="javascript:addPharmacy()"><font color="green">Add Item</font> </a></th></tr>
	      	<tr bgcolor="#126354">
		        <td width="25%"><font color="white"><strong>Sl.No.</strong></font></td>
		        <td width="25%"><font color="white"><strong>Activity Code</strong></font></td>
		        <td width="25%"><font color="white"><strong>Short Description</strong></font></td>
		        <td width="25%"><font color="white"><strong>Price</strong></font></td>
		        <td> &nbsp;</td>
	      	</tr>
	      	<tr>
		        <td width="100%" colspan="5" align="center"> <font color="red">No Data</font> </td>
	      	</tr>
		    </table>
	    </div>
	</div>
		</fieldset>
		
	<%-- 
	File Upload is not required here
		<fieldset>
		
		<legend>Attachments</legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			     <tr>
			     <td colspan="4">
			     <a href="#" onclick="javascript:onUploadAttachments()">Upload Files</a>
			     </td>
			     </tr>
		      	<tr>
			        <td width="25%">File:</td>
			        <td width="25%"  align="left"> 
			        
			        <html:file property="uploadFile"/> 
			        
			        </td>
					<td width="25%">&nbsp;</td>
					<td width="25%">&nbsp;</td>
		      	</tr>
			    </table>
			    
			    <br>
			    
			<table class="formContainer" align="center" border="1" cellspacing="0" cellpadding="0">
		       
	      	<tr bgcolor="#126354">
		        <td width="25%"><font color="white"><strong>Type</strong></font></td>
		        <td width="25%"><font color="white"><strong>Date</strong></font></td>
		        <td width="25%"><font color="white"><strong>Name</strong></font></td>
		        <td width="25%"><font color="white"><strong>Uploaded By</strong></font></td>
		        <td> &nbsp;</td>
	      	</tr>
	      	<tr>
		        <td width="100%" colspan="5" align="center"> <font color="red">No Data</font> </td>
	      	</tr>
		    </table>
		</fieldset> --%>
		 
	<!-- S T A R T : Buttons -->
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
	        <td width="100%" align="center">
			  <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSavePreAuth();"><u>S</u>ave</button>&nbsp;
        	 
        	  <button type="button" name="Button" accesskey="l" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseOTP();">C<u>l</u>ose</button>&nbsp;		       
	        </td>
      	</tr>
    </table>
    
    <logic:notEmpty name="MemberSave" scope="request"> 	
</logic:notEmpty>
	<input type="hidden" name="mode" value="">
	<input type="hidden" name="hospSeqId" value=<%=userSecurityProfile.getHospSeqId() %>> 
</div></div></div></div></div></div></div></div></body>
</html:form>