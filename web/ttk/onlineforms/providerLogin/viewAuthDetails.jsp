<%
/**
 * @ (#) viewAuthDetails.jsp Nov 19 2015 
 * Project      : TTK HealthCare Services Dubai
 * File         : viewAuthDetails.jsp
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : Nov 23 2015
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<head>
<meta charset="utf-8">
    <link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
    
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/providerLogin/viewAuthDetails.js"></SCRIPT>
</head>
<html:form action="/ViewAuthorizationDetails.do" >
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
	<h4 class="sub_heading">Authorization Details</h4>
<html:errors/>
	<div id="contentOuterSeparator"></div>
</div>           

       </div>
        <div class="row-fluid" >
        <div style="width: 100%;">
<div class="span12" style="margin:0% 0%"> 

	<table align="center" border="0" cellspacing="3" cellpadding="3" style="width: 80%">
      <tr>
		<td>
		<fieldset>
		<legend> Pre-authorization details </legend>
		     <table style="background: #e3e3e3;border-radius:10px; width:80%; height:auto">
		  		 <tr>
					<td width="40%">Pre-Auth type:</td>
					<td width="60%"><bean:write name="frmAuthDetailsView" property="preAuthType"/> </td>
			     </tr> 
			     <tr>
					<td width="40%">Received date / time:</td> 
					<td><bean:write name="frmAuthDetailsView" property="receivedDate"/>
			     </tr>  
			     <tr>
					<td width="40%">Admission date / time:</td>
					<td><bean:write name="frmAuthDetailsView" property="admissionDate"/>
			     </tr>  
			     <tr>
					<td width="40%">Benefit type (OP / IP / Maternity / Dental):</td>
					<td width="40%"><bean:write name="frmAuthDetailsView" property="benefitType"/>
			     </tr>     
			     <tr>
					<td width="40%">Previous Approved amount:</td>
					<td><bean:write name="frmAuthDetailsView" property="previousApprAmt"/>
			     </tr> 
			     <tr>
					<td width="40%">Requested amount:</td>
					<td><bean:write name="frmAuthDetailsView" property="requestedAmt"/>
			     </tr>
			     <tr>
					<td width="40%">Doctor's name:</td>
					<td><bean:write name="frmAuthDetailsView" property="treatingDoctor"/>
			     </tr>  
			     <tr>
					<td width="40%">Vidal Health  Branch:</td>
					<td><bean:write name="frmAuthDetailsView" property="vidalBranch"/>
			     </tr>  
			     <tr>
					<td width="40%">Probable Date of Discharge:</td>
					<td><bean:write name="frmAuthDetailsView" property="probableDischargeDt"/>
			     </tr>  
		     </table>
		</fieldset>
		</td>
		</tr>
		
		
		<tr>
		<td>
		<fieldset>
		<legend> Claimant details </legend>
		     <table style="background: #e3e3e3;border-radius:10px; width:80%; height:auto">
		  		 <tr>
					<td width="40%">Beneficiary ID:</td>
					<td><bean:write name="frmAuthDetailsView" property="beneficiaryId"/>
			     </tr> <tr>
					<td width="40%">Emirate ID:</td>
					<td><bean:write name="frmAuthDetailsView" property="emirateID"/>
			     </tr><tr>
					<td width="40%">Claimant name:</td>
					<td><bean:write name="frmAuthDetailsView" property="patientName"/>
			     </tr>  
		     </table>
		</fieldset>
		</td>
		</tr>
		
		<tr>
		<td>
		<fieldset>
		<legend> Provider details </legend>
		     <table style="background: #e3e3e3;border-radius:10px; width:80%; height:auto">
		  		 <tr>
					<td width="40%">Provider Name:</td>
					<td><bean:write name="frmAuthDetailsView" property="providerName"/>
			     </tr> <tr>
					<td width="40%">Empanelment ID:</td>
					<td><bean:write name="frmAuthDetailsView" property="empanelId"/>
			     </tr> 
			     <tr>
					<td width="40%">State/Emirates:</td>
					<td><bean:write name="frmAuthDetailsView" property="emirate"/>
			     </tr> <tr>
					<td width="40%">Area:</td>
					<td><bean:write name="frmAuthDetailsView" property="city"/>
			     </tr>  
		     </table>
		</fieldset>
		</td>
		</tr>
		
		<tr>
		<td>
		<fieldset>
		<legend> Diagnosis Details </legend>
		     <ttk:ProviderDiagnosisDetails flow="PAT"/>
		</fieldset>
		</td>
		</tr>
		
		<tr>
		<td>
		<fieldset>
		<legend>Activity/Drug Details </legend>
		     <ttk:Activity flow="PAT"/>
		</fieldset>
		</td>
		</tr>
		
		<tr>
		<td>
		<fieldset>
		<legend> Approval details </legend>
		     <table style="background: #e3e3e3;border-radius:10px; width:80%; height:auto">
		  		 <tr>
					<td width="40%">Authorization number:</td>
					<td><bean:write name="frmAuthDetailsView" property="approvalNo"/>
			     </tr> <tr>
					<td width="40%">Status:</td>
					<td><bean:write name="frmAuthDetailsView" property="statusID"/>
			     </tr> 
			     <tr>
					<td width="40%">Date / time of decision:</td>
					<td><bean:write name="frmAuthDetailsView" property="decisionDt"/>
			     </tr>
			     <tr>
					<td width="40%">Medical Opinion Remarks:</td>
					<td><bean:write name="frmAuthDetailsView" property="medicalOpinionRemarks"/>
			     </tr>  
		     </table>
		</fieldset>
		</td>
		</tr>
		</table>

   
   <br>
   
   <!-- E N D : Form Fields -->
		
    	<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
		    		<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBack();"><u>B</u>ack</button>&nbsp;
		    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onShortfall();"><u>S</u>hortfall History</button>&nbsp;
		    		<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateLetter();"><u>G</u>enerate Letter</button>&nbsp;
 				</td>
  			</tr>  		
		</table>
		<!-- E N D : Buttons -->
	</div>
	<!-- E N D : Content/Form Area -->
</div>


</div>
</div>
</div>
</div>
</div>
</div>
<!--E N D : Content/Form Area -->
<INPUT TYPE="hidden" NAME="mode" VALUE="">

</body>
</html:form>

		