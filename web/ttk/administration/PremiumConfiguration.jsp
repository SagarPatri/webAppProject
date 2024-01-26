<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.WebBoardHelper,com.ttk.common.security.Cache"%>
<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/PremiumConfiguration.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript">
function isPositiveIntegerPre(obj,field_name)
{
   for (var i=0;i<obj.value.length;i++)
    {
	     if(isNaN(obj.value.charAt(i)))
	      {
	         alert("Please enter a valid "+ field_name);
	         obj.focus();
	         obj.value = "";
	         return false;
	       }
    }
    return true;
}
function onClose()
{
/*	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doConfigureProductPremium";
	document.forms[1].action="/Configuration.do";
	document.forms[1].submit();*/
	document.forms[1].mode.value = "doClose";
	document.forms[1].action = "/Configuration.do";
	document.forms[1].submit();	
}




function onReset()
{
	//if(typeof(ClientReset)!= 'undefined' && !ClientReset)	 {
		/*document.forms[1].minAge.value="";  
		 document.forms[1].maxAge.value="";  
		 document.forms[1].relation.value="";  
		 document.forms[1].salaryBand.value="";  
		 document.forms[1].grossPremium.value="";  
		 document.forms[1].authorityProductId.value="";  
		 document.forms[1].updateRemarks.value=""; 
		 */
	  document.forms[1].mode.value="doReset";
	  document.forms[1].action="/Configuration.do";
	  document.forms[1].submit();
	// }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
/* 	 else {
		 document.forms[1].reset();
		 document.forms[1].minAge.value="";  
		 document.forms[1].maxAge.value="";  
		 document.forms[1].grossPremium.value="";  
	    
	 }//end //of else  */ 
}


function onSave()
{
	
	var updateRemarks;
	var salaryBand = document.getElementById("salaryBand").value;
	if(salaryBand=="" || salaryBand==='')
	{
 		alert("Salary Band should not be empty");
 		document.forms[1].salaryBand.focus();
 		return false;
	}
	
	var minAge = document.getElementById("minAge").value;
	if(minAge=="" || minAge==='')
	{
		alert("Minimum Age should not be empty");
		document.forms[1].minAge.focus();
		return false;
	}
	
	var maxAge = document.getElementById("maxAge").value;
	if(maxAge=="" || maxAge==='')
	{
		alert("Maximum Age should not be empty");
		document.forms[1].maxAge.focus();
		return false;
	}
	
	if(document.getElementById("updateRemarks")==null)
	{
		updateRemarks="";
	}
	else
	{
		updateRemarks = document.getElementById("updateRemarks").value;
		if(updateRemarks=="" || updateRemarks==='')
		{
			alert("updateRemarks should not be empty");
			document.forms[1].updateRemarks.focus();
			return false;
		}		
	}
	
	
	
	
	var capitationYN= document.forms[1].capitationYN.value;
	
	
	
	if(capitationYN!="1"){
		
		 var ipNetPremium= document.getElementById("ipNetPremium").value;
		 var healthAuthority= document.getElementById("healthAuthority").value;
		 
	        if (healthAuthority == 'DHA') {
				var tpaFeeType = document.forms[1].tpaFeeType.value;
				var tpaFee = document.forms[1].tpaFee.value;
				if (tpaFee == "" || tpaFee === '') {
					alert("TPA FEE should not be empty");
					document.forms[1].tpaFee.focus();
					return false;
				}
				if (tpaFeeType == "" || tpaFeeType === '') {
					alert("TPA Fee Type should not be empty");
					document.forms[1].tpaFeeType.focus();
					return false;
				}
			
			}
			var opNetPremium = document.getElementById("opNetPremium").value;
			var medicalPremium = document.getElementById("medicalPremium").value;

			var ipNetPremiumAmt = document.forms[1].ipNetPremium.value;
			ipNetPremiumAmt = (ipNetPremiumAmt == null
					|| ipNetPremiumAmt === "" || ipNetPremiumAmt === "0") ? 0
					: ipNetPremiumAmt;
			var opNetPremiumAmt = document.forms[1].opNetPremium.value;
			opNetPremiumAmt = (opNetPremiumAmt == null
					|| opNetPremiumAmt === "" || opNetPremiumAmt === "0") ? 0
					: opNetPremiumAmt;
			var netPremium = document.forms[1].netPremium.value;
			var ipOpSum = Number(ipNetPremiumAmt) + Number(opNetPremiumAmt);

			var grossPremiumAmt = document.forms[1].grossPremium1.value;
			grossPremiumAmt = (grossPremiumAmt == null
					|| grossPremiumAmt === "" || grossPremiumAmt === "0") ? 0
					: grossPremiumAmt;

			var insurerMarginAED = document.forms[1].insurerMarginAED.value;
			insurerMarginAED = (insurerMarginAED == null
					|| insurerMarginAED === "" || insurerMarginAED === "0") ? 0
					: insurerMarginAED;
			var brokerMarginAED = document.forms[1].brokerMarginAED.value;
			brokerMarginAED = (brokerMarginAED == null
					|| brokerMarginAED === "" || brokerMarginAED === "0") ? 0
					: brokerMarginAED;
			var reInsBrkMarginAED = document.forms[1].reInsBrkMarginAED.value;
			reInsBrkMarginAED = (reInsBrkMarginAED == null
					|| reInsBrkMarginAED === "" || reInsBrkMarginAED === "0") ? 0
					: reInsBrkMarginAED;
			var otherMarginAED = document.forms[1].otherMarginAED.value;
			otherMarginAED = (otherMarginAED == null || otherMarginAED === "" || otherMarginAED === "0") ? 0
					: otherMarginAED;

			var totalAmt = Number(insurerMarginAED) + Number(brokerMarginAED)
					+ Number(reInsBrkMarginAED) + Number(otherMarginAED);
			totalAmt = (totalAmt == null || totalAmt === "" || totalAmt === "0") ? 0
					: totalAmt;
			if (document.getElementById("capitationtype")) {
				var capitationtype = document.getElementById("capitationtype").value;
				if (capitationtype == "") {
					alert("Please Select Asplus(OP/IP&OP)");
					document.getElementById("capitationtype").focus();
					return false;
				}
			}
			if (medicalPremium == "") {

				alert("Please Select Medical Premium)");
				document.getElementById("medicalPremium").focus();
				return false;
			}

			if (ipNetPremium == "" && opNetPremium == "") {
				alert("Please Select either IP Net Premium or OP Net Premium");
				document.getElementById("ipNetPremium").focus();
				return false;
			}

			if (totalAmt > grossPremiumAmt) {
				alert("Sum of all margins should not be greater than the Gross Premium");
				return false;
			}

			if (ipOpSum > netPremium) {
				alert("Sum of IP Net Premium and OP Net Premium should not be greater than the Net Premium");
				return false;
			}

		}
	
	if(capitationYN=="1"){
		var medicalPremium= document.getElementById("medicalPremium").value;
		var tpaFeeType= document.forms[1].tpaFeeType.value;
		var tpaFee= document.forms[1].tpaFee.valuee;

	if(medicalPremium ==""){
				
				alert("Please Select Medical Premium");
				document.getElementById("medicalPremium").focus();
				 return false;
			} 
		 
		 if(tpaFeeType=="" || tpaFeeType==='')
			{
				alert("TPA Fee Type should not be empty");
				document.forms[1].tpaFeeType.focus();
				return false;
			}
		 
		 if(tpaFee=="" || tpaFee==='')
			{
				alert("TPA FEE should not be empty");
				document.forms[1].tpaFee.focus();
				return false;
			}
		 
		 if(document.getElementById("asoFromDateid").value==''){
				alert("Effective From is required");
				return;
			}
			
         if(document.getElementById("asoToDateid").value==''){
         	alert("Effective To is required");
				return;
			}
         
         if(document.getElementById("asoFromDateid").value!=''){
        	 var reg = /^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/;
        	 var value = document.getElementById("asoFromDateid").value;
        	 if(value!=""){
        		    if(!reg.test(value)){
        			  document.getElementById("asoFromDateid").value="";
        			  alert("Invalid Date Format");
        		      return false;
        		      }
        		    }
         }
         
         if(document.getElementById("asoToDateid").value!=''){
        	 var reg = /^([0-2][0-9]|(3)[0-1])(\/)(((0)[0-9])|((1)[0-2]))(\/)\d{4}$/;
        	 var value = document.getElementById("asoToDateid").value;
        	 if(value!=""){
        		    if(!reg.test(value)){
        			  document.getElementById("asoToDateid").value="";
        			  alert("Invalid Date Format");
        		      return false;
        		      }
        		    }
         }
	}

		if (!((document.getElementById("maritalStatus").value) === "")
				&& !((document.getElementById("relation").value) === "")
				&& !((document.getElementById("maritalStatus").value) === "")) {
			document.forms[1].mode.value = "doSavePremiumConfigurarion";
			document.forms[1].action = "/Configuration.do";
			document.forms[1].submit();
		} else {
			alert("Fields should not be Empty");
		}
	}

	function ageCompare() {

		if (!((document.getElementById("minAge").value) === "")
				&& !((document.getElementById("maxAge").value) === "")) {

			if (Number(document.getElementById("minAge").value) > Number(document
					.getElementById("maxAge").value)) {
				alert("Max age always Greater Then Min age");
				document.getElementById("maxAge").value = "";
				document.getElementById("maxAge").focus();
			}

		}

	}

	function onSalaryBandConfiguration(strControlName, strTypeID) {
		var w = 350;
		var h = 500;
		var strControlValue = "";
		var relation = document.getElementById("relation").value;
		strControlValue = document.getElementById("healthAuthority").value;
		var openPage = "/ttk/common/showlistintx.jsp?typeId=" + strTypeID
				+ "&controlName=" + strControlName + "&controlVal="
				+ strControlValue + "&showRadio=N&relation=" + relation;
		var features = "scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="
				+ w + ",height=" + h;
		window.open(openPage, '', features);
	}

	function onSalaryBandConfigurationRadio(strControlName, strTypeID) {
		var w = 350;
		var h = 500;
		var strControlValue = "";
		var relation = document.getElementById("relation").value;
		strControlValue = document.getElementById("healthAuthority").value;
		var openPage = "/ttk/common/showlistintx.jsp?typeId=" + strTypeID
				+ "&controlName=" + strControlName + "&controlVal="
				+ strControlValue + "&showRadio=Y&relation=" + relation;
		var features = "scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="
				+ w + ",height=" + h;
		window.open(openPage, '', features);
	}

	function onRelationConfiguration(strControlName, strTypeID) {
		var w = 350;
		var h = 500;
		var strControlValue = "";
		//strControlValue = document.getElementById("healthAuthority").value;
		var openPage = "/ttk/common/showlistintx.jsp?typeId=" + strTypeID
				+ "&controlName=" + strControlName + "&controlVal="
				+ strControlValue + "&showRadio=N";
		var features = "scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="
				+ w + ",height=" + h;
		window.open(openPage, '', features);
	}

	function onRelationConfigurationRadio(strControlName, strTypeID) {
		var w = 350;
		var h = 500;
		var strControlValue = "";
		//strControlValue = document.getElementById("healthAuthority").value;
		var openPage = "/ttk/common/showlistintx.jsp?typeId=" + strTypeID
				+ "&controlName=" + strControlName + "&controlVal="
				+ strControlValue + "&showRadio=Y";
		var features = "scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="
				+ w + ",height=" + h;
		window.open(openPage, '', features);
	}
</script>
<%

pageContext.setAttribute("gender", Cache.getCacheObject("gender"));
pageContext.setAttribute("maritalStatuses", Cache.getCacheObject("maritalStatuses"));

/* pageContext.setAttribute("capitationtype", Cache.getCacheObject("capitationtype")); */

%>
<!-- S T A R T : Content/Form Area -->	
<html:form action="/Configuration.do"  >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>Configuration Information - <%=request.getSession().getAttribute("ConfigurationTitle")%> </td>
		<td align="right" >&nbsp;</td>     
    </tr>
	</table>
	<!-- E N D : Page Title --> 
	<div class="contentArea" id="contentArea">	
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<logic:notEmpty name="successMsg" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success" width="16" height="16" align="middle">&nbsp;
						<bean:write name="successMsg" scope="request"/>
				  </td>
				</tr>
			</table>
		</logic:notEmpty> 	
	<fieldset>
	<legend>Member Eligibility Rules </legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
    
       
       
        <%
        if("Policies".equals(TTKCommon.getActiveSubLink(request))){
        %>
       <tr><td><label>Product Name:</label></td><td><bean:write name="frmRenewalDays" property="productName"/> </td> </tr>
        <%} %>
        <%
        if("Policies".equals(TTKCommon.getActiveSubLink(request))){
        %>
        <logic:equal value="1" name="frmRenewalDays" property="capitationYN" scope="session">
       <tr>
       <td width="10%" nowrap="nowrap">Effective From:</td>
              <td width="40%" class="textLabel">
             
             <html:text property="asoFromDate" styleClass="textBox textDate" maxlength="10" styleId="asoFromDateid" />
             
              <A NAME="CalendarObjectempDate11" ID="CalendarObjectempDate11" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmRenewalDays.asoFromDate',document.frmRenewalDays.asoFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
              <img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
              </td>
         <td width="10%" nowrap="nowrap">Effective To:</td>
              <td width="40%" class="textLabel">
             
             <html:text property="asoToDate" styleClass="textBox textDate" maxlength="10" styleId="asoToDateid" />
             
              <A NAME="CalendarObjectempDate11" ID="CalendarObjectempDate11" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmRenewalDays.asoToDate',document.frmRenewalDays.asoToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
              <img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
              </td>
        </tr>
        </logic:equal>
        <%} %>
        <tr><td>Minimun Age:</td>
        
        
        <td><html:text property="minAge" styleId="minAge" onblur="isNumericvalidate(this,'Minimum Age');ageCompare();" ></html:text></td> 
        <td>Maximum Age:</td><td><html:text property="maxAge" styleId="maxAge" onblur="isNumericvalidate(this,'Maximum Age');ageCompare();" ></html:text></td></tr>
        <tr><td>Marital status:</td>
        <td>
        <html:select property="maritalStatus" styleClass="selectBox" styleId="maritalStatus">
			  <%-- <html:option value="">Select from list</html:option> --%>
			  <html:option value="ALL">All</html:option>
			  <html:option value="SNG">Single</html:option>
			  <html:option value="MRD">Married</html:option>
			  <%--  <html:optionsCollection name="maritalStatuses" label="cacheDesc" value="cacheId" /> --%>
		             </html:select>
        </td>
         <td>Gender Applicable:</td> <td>
        <html:select property="gender"  styleClass="selectBox" styleId="gender">
			<%--   <html:option value="">Select from list</html:option> --%>
			  <html:option value="ALL">Both Male And Female</html:option>
			  <html:option value="MAL">Male</html:option>
			  <html:option value="FEM">Female</html:option>
			  <html:option value="EUN">Not Disclosed</html:option>
		   <%--    <html:optionsCollection name="gender" label="cacheDesc" value="cacheId" /> --%>
		     
        </html:select>
        </td>
        </tr>
         <tr>
        <td>Applicable to Relation:</td> <td>
           <html:text property="relation" styleId="relation" readonly="true" ></html:text>
      <logic:empty property="editflag" name="frmRenewalDays" scope="session">
        <a href="#" onClick="onRelationConfiguration('relation','RELATION')"><img src="/ttk/images/EditIcon.gif" alt="Relation Configuration" title="Relation Configuration" width="16" height="16" border="0" align="absmiddle"></a>
       </logic:empty>
        <logic:notEmpty property="editflag" name="frmRenewalDays"  scope="session">
         <a href="#" onClick="onRelationConfiguration('relation','RELATION')"><img src="/ttk/images/EditIcon.gif" alt="Relation Configuration" title="Relation Configuration" width="16" height="16" border="0" align="absmiddle"></a>
     </logic:notEmpty>
         
       <%--  <html:select property="relation"  styleClass="selectBox" styleId="relation">
        <html:option  value="">Select from list</html:option>
        <html:option value="ALL">All</html:option>
        <html:option value="NSF">Self</html:option>
        <html:option value="YSP">Spouse</html:option>
        <html:option value="NCH">Children</html:option>
         <html:option value="NFR">Father</html:option>
          <html:option value="YMO">Mother</html:option>
          <html:option value="OTH">Others</html:option>
           
        </html:select> --%>
        </td>
         <td>Salary Band(OMR):</td>
        <td>
        <html:text property="salaryBand" styleId="salaryBand" readonly="true"  ></html:text>
      <logic:empty property="editflag" name="frmRenewalDays" scope="session">
        <a href="#" onClick="onSalaryBandConfiguration('salaryBand','SALARY BAND')"><img src="/ttk/images/EditIcon.gif" alt="Salary Band Configuration" title="Salary Band Configuration" width="16" height="16" border="0" align="absmiddle"></a>
      </logic:empty>
      <logic:notEmpty property="editflag" name="frmRenewalDays"  scope="session">
         <a href="#" onClick="onSalaryBandConfiguration('salaryBand','SALARY BAND')"><img src="/ttk/images/EditIcon.gif" alt="Salary Band Configuration" title="Salary Band Configuration" width="16" height="16" border="0" align="absmiddle"></a>
     </logic:notEmpty>
        </td>
       
        </tr>
        <tr>
                <%if("Policies".equals(TTKCommon.getActiveSubLink(request))){ %>
       <%--  <logic:notEqual value="Y" name="frmPoliciesEdit" property="capitationflag" scope="session"> --%>
        
       <%--  <logic:equal  value="1" name="frmPoliciesEdit" property="capitationflag" scope="session">  
        
       <td>Gross Premium(AED):</td>
        <td>
        <html:text property="grossPremium" styleId="grossPremium"  onkeyup="isNumaricOnly(this)" readonly="true" disabled="true" />
        </td>
         </logic:equal> --%>
         
         
         
        <%-- </logic:notEqual> --%>
         <%} %>
         <%-- <%if("Products".equals(TTKCommon.getActiveSubLink(request))){ 
        	
         %> --%>
     
        <%-- <logic:notEqual value="Y" name="frmRenewalDays" property="capitationYN" scope="request"> --%>
        <%-- <logic:equal  value="1" name="frmRenewalDays" property="capitationYN" scope="session"> 
        <td>Gross Premium(AED):</td>
        <td>
        <html:text property="grossPremium" styleId="grossPremium" onkeyup="isNumaricOnly(this)" readonly="true" disabled="true" />
        </td>
         </logic:equal> --%>
         
         
       <%--    <logic:notEqual value="1" name="frmRenewalDays" property="capitationYN" scope="session">  
              <td>AsPlus(OP/IPandOP): <span class="mandatorySymbol">*</span> </td>
                  <td>
              <html:select property="capitationtype" name="frmRenewalDays"   styleClass="selectBox" styleId="capitationtype">
		      <html:option value="">Select from list</html:option> 
			  <html:option value="2">OP</html:option>
			  <html:option value="3">IP&OP</html:option>
			  
        </html:select>
        </td>
         </logic:notEqual>  --%>
         

         
         
         
         
         
         
         
         
         
         <%-- </logic:notEqual> --%>
       <%--   <%} %> --%>
     <%--    <logic:equal property="healthAuthority" name="frmRenewalDays" value="DHA" scope="session">
           <td>Authority Product ID</td>
           </logic:equal>
             <logic:equal property="healthAuthority" name="frmRenewalDays" value="MOH" scope="session">
           <td>Authority Product ID</td>
           </logic:equal>
           <logic:equal property="healthAuthority" name="frmRenewalDays" value="CMA" scope="session">
           <td>Authority Product ID</td>
           </logic:equal>
             <logic:equal property="healthAuthority" name="frmRenewalDays" value="HAAD" scope="session">
           <td>Authority Product<br>Reference Number :</td>
           </logic:equal>
           <logic:equal property="healthAuthority" name="frmRenewalDays" value="CMA" scope="session">
           <td>Authority Product ID</td>
           </logic:equal>
           <logic:equal property="healthAuthority" name="frmRenewalDays" value="" scope="session">
           <td>Authority Product ID:</td>
           </logic:equal> --%>
	<td>Authority Product ID:</td>
        <td>
        <html:text property="authorityProductId" styleId="authorityProductId" ></html:text>
        </td>
        </tr>
        <logic:equal property="editflag" name="frmRenewalDays" value="Y" scope="session">
        <tr>
        <td>Updated Remarks:<span class="mandatorySymbol">*</span></td>
        <td><html:text property="updateRemarks" styleId="updateRemarks" styleClass="textBox textBoxLong" >
        </html:text>
        </td>
        </tr>
        <html:hidden property="editflag" name="frmRenewalDays"/>
         </logic:equal> 
        </table>
        
         </fieldset>
        
        
          <logic:notEqual value="1" name="frmRenewalDays" property="capitationYN" scope="session"> 
        <fieldset>
	     <legend>Premium Details </legend>
        
        
        <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
  <%--  <tr>
   		<td class="formLabel">Age Range:<span class="mandatorySymbol">*</span></td>
        <td>
      		  <html:text name="frmRenewalDays"  property="ageRange" styleClass="textBox textBoxMedium" onkeyup="isNumeric(this);"   disabled="<%=viewmode%>"/>
        </td>
          </tr>  
  --%>
  <%--        
  <tr>
   		<td class="formLabel">Min Age:</td>
        <td>
      		  <html:text name="frmRenewalDays" readonly="true" property="minAge" maxlength="3"  styleId="minAge" value="0" styleClass="textBox textBoxMedium textBoxDisabled"  disabled="<%=viewmode%>"/>
        </td>
        <td class="formLabel">Max Age:</td>
        <td>
      		  <html:text name="frmRenewalDays" readonly="true"  property="maxAge"  maxlength="3"   styleId="maxAge" value="65" styleClass="textBox textBoxMedium textBoxDisabled"  disabled="<%=viewmode%>"/>
        </td>
  </tr>
  --%> 
  <tr>
   		<td class="formLabel">Medical Premium :<span class="mandatorySymbol">*</span></td>
        <td>
      		  <html:text name="frmRenewalDays"  property="medicalPremium"  styleId="medicalPremium"  styleClass="textBox textBoxMedium"   onkeyup="isNumaricOnly(this);calcCapNetPremium();calculatePerAED(this,'otherMargin');calculatePerAED(this,'reInsBrkMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'insurerMargin');"/>
        </td>
        <td class="formLabel">Insurer Margin:<span class="mandatorySymbol">*</span></td>
       
        <td>
        <div id="displayinsurerMargin" style="display: block;">
        <html:text name="frmRenewalDays"  property="insurerMargin" styleId="insurerMargin" styleClass="textBox textBoxMedium" onkeyup="isNumaricOnly(this);calculatePerAED(this,'insurerMargin');" />&nbsp; 
        <html:select property="insurerMarginAEDPER" styleId="insurerMarginAEDPER"  styleClass="selectBox selectBoxSmall"  onchange="calculatePerAED(this,'insurerMargin')">
      		 <html:option value="PER">%</html:option>
      		 <html:option value="AED">OMR</html:option>
      		 </html:select>&nbsp;
        <!-- onblur="numbervalidation('Insurer Margin');"-->
        </div>
         <logic:equal name="frmRenewalDays" property="insurerMarginAEDPER" value="AED">
        <div id="displayPERinsurerMargin" style="display: ;">
        </logic:equal>
         <logic:notEqual name="frmRenewalDays" property="insurerMarginAEDPER" value="AED">
          <div id="displayPERinsurerMargin" style="display: none;">
         </logic:notEqual>
      		 <html:text name="frmRenewalDays"  property="insurerMarginPER" styleClass="textBox textBoxMedium textBoxDisabled" readonly="readonly" styleId="insurerMarginPER"  />  [%]&nbsp;
      		  </div>
      		    <logic:equal name="frmRenewalDays" property="insurerMarginAEDPER" value="PER">
      		    <div id="displayAEDinsurerMargin" style="display: ;">
      		    </logic:equal>
      		     <logic:notEqual name="frmRenewalDays" property="insurerMarginAEDPER" value="PER">
      		     <div id="displayAEDinsurerMargin" style="display: none;">
      		     </logic:notEqual>
      		  <html:text name="frmRenewalDays"  property="insurerMarginAED" styleClass="textBox textBoxMedium textBoxDisabled" readonly="readonly" styleId="insurerMarginAED"  />  [OMR]&nbsp;
      		  <!--  <input type="text" class="textBox textBoxSmall" readonly="readonly" onfocus="calcCapNetPremium(this);" id="insurerMarginAED">AED -->
      		  </div>
      	</td>
      	
 </tr>
  
  <tr>
   		<td class="formLabel">Maternity Premium :</td>
        <td>
      		  <html:text name="frmRenewalDays"  property="maternityPremium"  styleId="maternityPremium"  styleClass="textBox textBoxMedium"   onkeyup="isNumaricOnly(this);calcCapNetPremium();calculatePerAED(this,'otherMargin');calculatePerAED(this,'reInsBrkMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'insurerMargin');"/>
        </td>
        <td class="formLabel">Broker Margin: <span class="mandatorySymbol">*</span></td>
        <td>
        <div id="displaybrokerMargin" style="display:block; " > 
        		 <html:text name="frmRenewalDays"  property="brokerMargin" styleId="brokerMargin" styleClass="textBox textBoxMedium"  onkeyup="isNumaricOnly(this);calculatePerAED(this,'brokerMargin');" />
        		  <html:select property="brokerMarginAEDPER" styleId="brokerMarginAEDPER"  styleClass="selectBox selectBoxSmall"  onchange="calculatePerAED(this,'brokerMargin')">
      		 <html:option value="PER">%</html:option>
      		 <html:option value="AED">OMR</html:option>
      		 </html:select>&nbsp;
        		 </div> 
        		
        		  <logic:equal name="frmRenewalDays" property="brokerMarginAEDPER" value="AED">
        		 <div id="displayPERbrokerMargin" style="display:;">
        		 </logic:equal>
        		  <logic:notEqual name="frmRenewalDays" property="brokerMarginAEDPER" value="AED">
        		  <div id="displayPERbrokerMargin" style="display: none;">
        		   </logic:notEqual>
        		  <html:text name="frmRenewalDays" styleId="brokerMarginPER" property="brokerMarginPER" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true"  /> [%]
        		  </div>
        		  
        		   <logic:equal name="frmRenewalDays" property="brokerMarginAEDPER" value="PER">
        		  <div id="displayAEDbrokerMargin" style="display: ;">
        		   </logic:equal>
        		   
        		    <logic:notEqual name="frmRenewalDays" property="brokerMarginAEDPER" value="PER">
        		  <div id="displayAEDbrokerMargin" style="display: none;">
        		   </logic:notEqual>
        		   
        		  <html:text name="frmRenewalDays" styleId="brokerMarginAED" property="brokerMarginAED" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" /> [OMR]
        		  <!-- <input type="text" id="brokerMarginAED" Class="textBox textBoxMedium" >AED -->
        		 </div>
        </td>
       
  </tr>
  
  <tr>
   		<td class="formLabel">Optical Premium :</td>
        <td>
      		  <html:text name="frmRenewalDays"  property="opticalPremium"   styleId="opticalPremium"   styleClass="textBox textBoxMedium"   onkeyup="isNumaricOnly(this);calcCapNetPremium();calculatePerAED(this,'otherMargin');calculatePerAED(this,'reInsBrkMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'insurerMargin');"/>
        </td>
        
        
         <td class="formLabel">ReIns.Brk.Margin:</td>
        <td>
        	<html:text name="frmRenewalDays"  property="reInsBrkMargin" styleId="reInsBrkMargin"  styleClass="textBox textBoxMedium" onkeyup="isNumaricOnly(this);calculatePerAED(this,'reInsBrkMargin');calcCapNetPremium();" />
        	<html:select property="reInsBrkMarginAEDPER" styleId="reInsBrkMarginAEDPER"  styleClass="selectBox selectBoxSmall"  onchange="calculatePerAED(this,'reInsBrkMargin')">
      		 <html:option value="PER">%</html:option>
      		 <html:option value="AED">OMR</html:option>
      		 </html:select>&nbsp;
      		 
      		 <logic:equal name="frmRenewalDays" property="reInsBrkMarginAEDPER" value="AED">
      		 <div id="displayPERreInsBrkMargin" style="display: ;">
      		 </logic:equal>
      		 <logic:notEqual name="frmRenewalDays" property="reInsBrkMarginAEDPER" value="AED">
      		 <div id="displayPERreInsBrkMargin" style="display: none;">
      		 </logic:notEqual>
        	<html:text name="frmRenewalDays"  property="reInsBrkMarginPER" styleId="reInsBrkMarginPER"  styleClass="textBox textBoxMedium textBoxDisabled"   readonly="true" />  [%]
        	</div>
        	
        	 <logic:equal name="frmRenewalDays" property="reInsBrkMarginAEDPER" value="PER">
        	 <div id="displayAEDreInsBrkMargin" style="display:;">
        	 </logic:equal>
        	  <logic:notEqual name="frmRenewalDays" property="reInsBrkMarginAEDPER" value="PER">
        	  <div id="displayAEDreInsBrkMargin" style="display: none;">
        	  </logic:notEqual>
        	<html:text name="frmRenewalDays"  property="reInsBrkMarginAED" styleId="reInsBrkMarginAED"  styleClass="textBox textBoxMedium textBoxDisabled"   readonly="true" />  [OMR]
        	</div>
        </td>
       
     
  </tr>
  
  <tr>
   		<td class="formLabel">Dental Premium :</td>
        <td>
      		  <html:text name="frmRenewalDays"  property="dentalPremium"  styleId="dentalPremium"   styleClass="textBox textBoxMedium" onkeyup="isNumaricOnly(this);calcCapNetPremium();calculatePerAED(this,'otherMargin');calculatePerAED(this,'reInsBrkMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'insurerMargin');" />
        </td>
         <td class="formLabel">Other Margin:</td>
        <td>
        	<html:text name="frmRenewalDays"  property="otherMargin"  styleId="otherMargin" styleClass="textBox textBoxMedium" onkeyup="isNumaricOnly(this);calculatePerAED(this,'otherMargin');" />
        	<html:select property="otherMarginAEDPER" styleId="otherMarginAEDPER" styleClass="selectBox selectBoxSmall"  onchange="calculatePerAED(this,'otherMargin')">
      		 <html:option value="PER">%</html:option>
      		 <html:option value="AED">OMR</html:option>
      		 </html:select>&nbsp;
      		 
      		  <logic:equal name="frmRenewalDays" property="otherMarginAEDPER" value="AED">
      		  <div id="displayPERotherMargin" style="display: ;">
      		  </logic:equal>
      		   <logic:notEqual name="frmRenewalDays" property="otherMarginAEDPER" value="AED">
      		   <div id="displayPERotherMargin" style="display: none;">
      		   </logic:notEqual>
        	<html:text name="frmRenewalDays" styleId="otherMarginPER"  property="otherMarginPER"  styleClass="textBox textBoxMedium textBoxDisabled" readonly="true"  />  [%]
        	</div>
        	
        	
        	 <logic:equal name="frmRenewalDays" property="otherMarginAEDPER" value="PER">
        	 <div id="displayAEDotherMargin" style="display: ;">
        	 </logic:equal>
        	 
        	  <logic:notEqual name="frmRenewalDays" property="otherMarginAEDPER" value="PER">
        	  <div id="displayAEDotherMargin" style="display: none;">
        	  </logic:notEqual>
        	
        	<html:text name="frmRenewalDays" styleId="otherMarginAED"  property="otherMarginAED"  styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" />  [OMR]
        	</div>
        </td>
       
  </tr>
  
  <tr>
   		<td class="formLabel">Wellness Premium :</td>
        <td>
      		  <html:text name="frmRenewalDays"  property="wellnessPremium"   styleId="wellnessPremium"    styleClass="textBox textBoxMedium"   onkeyup="isNumaricOnly(this);calcCapNetPremium();calculatePerAED(this,'otherMargin');calculatePerAED(this,'reInsBrkMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'insurerMargin');"/>
        </td>
     
  </tr>
  
  <tr>         
   		<td class="formLabel">Gross Premium :<span class="mandatorySymbol">*</span></td> 
        <td>
      		  <html:text name="frmRenewalDays"  property="grossPremium1"   styleId="grossPremium1" readonly="true" disabled="true"  styleClass="textBox textBoxMedium"   /> 
        </td>
        <td class="formLabel">Net Premium :<span class="mandatorySymbol">*</span></td> 
        <td>
        	<html:text name="frmRenewalDays"  property="netPremium" styleId="netPremium" readonly="true" disabled="true" styleClass="textBox textBoxMedium"   />  [OMR]
        	<!--  onblur="calcmarginmatchgrosspremium();" -->
        </td>    
        
        
        
        
        
            
   		
        
        
        
        
  </tr>        
  <tr>
  <%-- <td class="formLabel">Pro-rata Premium (Actual Premium) :<span class="mandatorySymbol">*</span></td> 
   		 <td>
   			<html:text name="frmRenewalDays"   property="actualPremium" styleClass="textBox textBoxMedium"  readonly="true" styleId="actualPremium"   style="background-color: #F5F5DC;" /> 
         </td> --%>
         
         
         
            <td class="formLabel">IP Net Premium :<span class="mandatorySymbol">*</span></td> 
        <td>
        	<html:text name="frmRenewalDays"  property="ipNetPremium" styleId="ipNetPremium"  styleClass="textBox textBoxMedium"  onkeyup="isNumaricOnly(this);" />  [OMR]
        	<!--  onblur="calcmarginmatchgrosspremium();" -->
        </td>  
         
         
   		<td class="formLabel">OP Net Premium :<span class="mandatorySymbol">*</span></td> 
   		 <td>
   			<html:text name="frmRenewalDays"  property="opNetPremium" styleClass="textBox textBoxMedium"   styleId="opNetPremium" onkeyup="isNumaricOnly(this)" /> 
         </td>
  </tr>
   <logic:notEqual  value="1" name="frmRenewalDays" property="capitationYN" scope="session">
   <logic:equal property="healthAuthority" name="frmRenewalDays" value="DHA" scope="session">
    <tr>
       <td class="formLabel"></td> 
        <td>      	
        </td>  
         <td class="formLabel">TPA Fee :<span class="mandatorySymbol">*</span></td>
        <td>
        <div id="displayinsurerMargin" style="display: block;">
        <html:text name="frmRenewalDays"  property="tpaFee" styleId="tpa_fee_id" styleClass="textBox textBoxMedium" onkeyup="isNumaricOnly(this);calculateActualFee(this);" />&nbsp; 
        <html:select property="tpaFeeAEDPER" styleId="tpa_fee_aedper_id"  styleClass="selectBox selectBoxSmall"  onchange="calculateActualFee(this);">
      		 <html:option value="PER">%</html:option>
      		 <html:option value="AED">OMR</html:option>
      		 </html:select>&nbsp;
        <!-- onblur="numbervalidation('Insurer Margin');"-->
        </div>
  </tr>
  
  <tr>
  <td class="formLabel"></td> 
  <td></td>
  <td class="formLabel"></td> 
  <td>
  <html:text name="frmRenewalDays"  property="tpaActualFee" styleId="tpa_actual_fee_id" styleClass="textBox textBoxMedium textBoxDisabled" readonly="readonly" />  <!-- [AED]&nbsp; -->
  <span id='tpa_fee_select_id'>
  <logic:equal name="frmRenewalDays" property="tpaFeeAEDPER" value="AED">
  %
  </logic:equal>
  <logic:equal name="frmRenewalDays" property="tpaFeeAEDPER" value="PER">
  [OMR]
  </logic:equal>
  </span>
  </td></tr>
  
  
    <tr>

       <td class="formLabel"></td> 
        <td>
        	
        </td>  
         
         
   		<td class="formLabel">TPA Fee Type :<span class="mandatorySymbol">*</span></td> 
   		 <td>
   			<html:select property="tpaFeeType" styleClass="selectBox selectBoxMedium"  styleId="tpa_fee_type_id">
  	 		  <html:option value="">Select from list</html:option>
		      <html:optionsCollection name="frmRenewalDays" property="alTPAFeeType"  label="cacheDesc" value="cacheId" />
		</html:select>
         </td>
  </tr>
 </logic:equal>
    </logic:notEqual>
</table>
        
        
       </fieldset>
       </logic:notEqual> 
       

 <logic:equal value="1" name="frmRenewalDays" property="capitationYN" scope="session"> 
        <fieldset>
	     <legend>Premium Details </legend>
        
        
        <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
   		<td class="formLabel">Medical Premium :<span class="mandatorySymbol">*</span></td>
        <td>
      		  <html:text name="frmRenewalDays"  property="medicalPremium"  styleId="medicalPremium"  styleClass="textBox textBoxMedium"   onkeyup="isNumaricOnly(this);calcCapNetPremium();calculatePerAED(this,'otherMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'insurerMargin');calculatePerAED(this,'tpaFee');"/>
        </td>
        <td class="formLabel">Insurer Margin:</td>
       
        <td>
        <div id="displayinsurerMargin" style="display: block;">
        <html:text name="frmRenewalDays"  property="insurerMargin" styleId="insurerMargin" styleClass="textBox textBoxMedium" onkeyup="isNumaricOnly(this);calculatePerAED(this,'insurerMargin');" />&nbsp; 
        <html:select property="insurerMarginAEDPER" styleId="insurerMarginAEDPER"  styleClass="selectBox selectBoxSmall"  onchange="calculatePerAED(this,'insurerMargin','insurerMarginFlag')">
      		 <html:option value="PER">%</html:option>
      		 <html:option value="AED">OMR</html:option>
      		 </html:select>&nbsp;
        <!-- onblur="numbervalidation('Insurer Margin');"-->
        </div>
         <logic:equal name="frmRenewalDays" property="insurerMarginAEDPER" value="AED">
        <div id="displayPERinsurerMargin" style="display: ;">
        </logic:equal>
         <logic:notEqual name="frmRenewalDays" property="insurerMarginAEDPER" value="AED">
          <div id="displayPERinsurerMargin" style="display: none;">
         </logic:notEqual>
      		 <html:text name="frmRenewalDays"  property="insurerMarginPER" styleClass="textBox textBoxMedium textBoxDisabled" readonly="readonly" styleId="insurerMarginPER"  />  [%]&nbsp;
      		  </div>
      		    <logic:equal name="frmRenewalDays" property="insurerMarginAEDPER" value="PER">
      		    <div id="displayAEDinsurerMargin" style="display: ;">
      		    </logic:equal>
      		     <logic:notEqual name="frmRenewalDays" property="insurerMarginAEDPER" value="PER">
      		     <div id="displayAEDinsurerMargin" style="display: none;">
      		     </logic:notEqual>
      		  <html:text name="frmRenewalDays"  property="insurerMarginAED" styleClass="textBox textBoxMedium textBoxDisabled" readonly="readonly" styleId="insurerMarginAED"  />  [OMR]&nbsp;
      		  <!--  <input type="text" class="textBox textBoxSmall" readonly="readonly" onfocus="calcCapNetPremium(this);" id="insurerMarginAED">AED -->
      		  </div>
      	</td>
      	
 </tr>
  
  <tr>
   		<td class="formLabel">Maternity Premium :</td>
        <td>
      		  <html:text name="frmRenewalDays"  property="maternityPremium"  styleId="maternityPremium"  styleClass="textBox textBoxMedium"   onkeyup="isNumaricOnly(this);calcCapNetPremium();calculatePerAED(this,'otherMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'insurerMargin');calculatePerAED(this,'tpaFee');"/>
        </td>
        <td class="formLabel">Broker Margin:</td>
        <td>
        <div id="displaybrokerMargin" style="display:block; " > 
        		 <html:text name="frmRenewalDays"  property="brokerMargin" styleId="brokerMargin" styleClass="textBox textBoxMedium"  onkeyup="isNumaricOnly(this);calculatePerAED(this,'brokerMargin');" />
        		  <html:select property="brokerMarginAEDPER" styleId="brokerMarginAEDPER"  styleClass="selectBox selectBoxSmall"  onchange="calculatePerAED(this,'brokerMargin','brokerMarginFlag')">
      		 <html:option value="PER">%</html:option>
      		 <html:option value="AED">OMR</html:option>
      		 </html:select>&nbsp;
        		 </div> 
        		
        		  <logic:equal name="frmRenewalDays" property="brokerMarginAEDPER" value="AED">
        		 <div id="displayPERbrokerMargin" style="display:;">
        		 </logic:equal>
        		  <logic:notEqual name="frmRenewalDays" property="brokerMarginAEDPER" value="AED">
        		  <div id="displayPERbrokerMargin" style="display: none;">
        		   </logic:notEqual>
        		  <html:text name="frmRenewalDays" styleId="brokerMarginPER" property="brokerMarginPER" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true"  /> [%]
        		  </div>
        		  
        		   <logic:equal name="frmRenewalDays" property="brokerMarginAEDPER" value="PER">
        		  <div id="displayAEDbrokerMargin" style="display: ;">
        		   </logic:equal>
        		   
        		    <logic:notEqual name="frmRenewalDays" property="brokerMarginAEDPER" value="PER">
        		  <div id="displayAEDbrokerMargin" style="display: none;">
        		   </logic:notEqual>
        		   
        		  <html:text name="frmRenewalDays" styleId="brokerMarginAED" property="brokerMarginAED" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" /> [OMR]
        		  <!-- <input type="text" id="brokerMarginAED" Class="textBox textBoxMedium" >AED -->
        		 </div>
        </td>
       
  </tr>
  
  <tr>
   		<td class="formLabel">Optical Premium :</td>
        <td>
      		  <html:text name="frmRenewalDays"  property="opticalPremium"   styleId="opticalPremium"   styleClass="textBox textBoxMedium"   onkeyup="isNumaricOnly(this);calcCapNetPremium();calculatePerAED(this,'otherMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'insurerMargin');calculatePerAED(this,'tpaFee');"/>
        </td>
        
        <td class="formLabel">Other Margin:</td>
        <td>
        	<html:text name="frmRenewalDays"  property="otherMargin" styleId="otherMargin"  styleClass="textBox textBoxMedium" onkeyup="isNumaricOnly(this);calculatePerAED(this,'otherMargin');" />
        	<html:select property="otherMarginAEDPER" styleId="otherMarginAEDPER" styleClass="selectBox selectBoxSmall"  onchange="calculatePerAED(this,'otherMargin','otherMarginFlag')">
      		 <html:option value="PER">%</html:option>
      		 <html:option value="AED">OMR</html:option>
      		 </html:select>&nbsp;
      		 
      		  <logic:equal name="frmRenewalDays" property="otherMarginAEDPER" value="AED">
      		  <div id="displayPERotherMargin" style="display: ;">
      		  </logic:equal>
      		   <logic:notEqual name="frmRenewalDays" property="otherMarginAEDPER" value="AED">
      		   <div id="displayPERotherMargin" style="display: none;">
      		   </logic:notEqual>
        	<html:text name="frmRenewalDays" styleId="otherMarginPER"  property="otherMarginPER"  styleClass="textBox textBoxMedium textBoxDisabled" readonly="true"  />  [%]
        	</div>
        	
        	
        	 <logic:equal name="frmRenewalDays" property="otherMarginAEDPER" value="PER">
        	 <div id="displayAEDotherMargin" style="display: ;">
        	 </logic:equal>
        	 
        	  <logic:notEqual name="frmRenewalDays" property="otherMarginAEDPER" value="PER">
        	  <div id="displayAEDotherMargin" style="display: none;">
        	  </logic:notEqual>
        	
        	<html:text name="frmRenewalDays" styleId="otherMarginAED"  property="otherMarginAED"  styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" />  [OMR]
        	</div>
        </td>  
     
  </tr>
  
  <tr>
   		<td class="formLabel">Dental Premium :</td>
        <td>
      		  <html:text name="frmRenewalDays"  property="dentalPremium"  styleId="dentalPremium"   styleClass="textBox textBoxMedium" onkeyup="isNumaricOnly(this);calcCapNetPremium();calculatePerAED(this,'otherMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'insurerMargin');" />
        </td>
        <td class="formLabel">TPA Fee Type :<span class="mandatorySymbol">*</span></td> 
   		 <td>
   			<html:select property="tpaFeeType" styleClass="selectBox selectBoxMedium"  styleId="tpa_fee_type_id">
  	 		  <html:option value="">Select from list</html:option>
		      <html:optionsCollection name="frmRenewalDays" property="alTPAFeeType"  label="cacheDesc" value="cacheId" />
		</html:select>
         </td>  
  </tr>
  
  <tr>
   		<td class="formLabel">Wellness Premium :</td>
        <td>
      		  <html:text name="frmRenewalDays"  property="wellnessPremium"   styleId="wellnessPremium"    styleClass="textBox textBoxMedium"   onkeyup="isNumaricOnly(this);calcCapNetPremium();calculatePerAED(this,'otherMargin');calculatePerAED(this,'brokerMargin');calculatePerAED(this,'insurerMargin');calculatePerAED(this,'tpaFee');"/>
        </td>
      <td class="formLabel">TPA Fee :<span class="mandatorySymbol">*</span></td>
        <td>
        <div id="displayinsurerMargin" style="display: block;">
        <html:text name="frmRenewalDays"  property="tpaFee" styleId="tpa_fee_id" styleClass="textBox textBoxMedium" onkeyup="isNumaricOnly(this);calculateActualFee(this);" />&nbsp; 
        <html:select property="tpaFeeAEDPER" styleId="tpa_fee_aedper_id"  styleClass="selectBox selectBoxSmall"  onchange="calculatePerAED(this,'tpaFee','tpaFeeFlag');">
      		 <html:option value="PER">%</html:option>
      		 <html:option value="AED">OMR</html:option>
      		 </html:select>&nbsp;
        <!-- onblur="numbervalidation('Insurer Margin');"-->
        </div>
        </td>
  </tr>
  
  <tr>         
   		<td class="formLabel">Gross Premium :<span class="mandatorySymbol">*</span></td> 
        <td>
      		  <html:text name="frmRenewalDays"  property="grossPremium1"   styleId="grossPremium1" readonly="true" disabled="true"  styleClass="textBox textBoxMedium"   /> 
        </td>
        <td class="formLabel"></td>
        <td>
  <html:text name="frmRenewalDays"  property="tpaActualFee" styleId="tpa_actual_fee_id" styleClass="textBox textBoxMedium textBoxDisabled" readonly="readonly" />  <!-- [AED]&nbsp; -->
  <span id='tpa_fee_select_id'>
  <logic:equal name="frmRenewalDays" property="tpaFeeAEDPER" value="AED">
  %
  </logic:equal>
  <logic:equal name="frmRenewalDays" property="tpaFeeAEDPER" value="PER">
  [OMR]
  </logic:equal>
  </span>
  </td>        
  </tr>        
</table>
        
        
       </fieldset>
       </logic:equal> 
        
        <table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="center">
			<%
	       if(TTKCommon.isAuthorized(request,"Edit"))
	       {
    	%>
    	
    	  <%if("Products".equals(TTKCommon.getActiveSubLink(request))){ %>
    	
    	    <logic:equal  value="1" name="frmRenewalDays" property="capitationYN" scope="session"> 
    	
			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'"	onClick="javascript:onSaveASO();"><u>S</u>ave</button>
			
			 </logic:equal>
			
			
			
			
			
			<logic:notEqual value="1" name="frmRenewalDays" property="capitationYN" scope="session">  
    	
			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'"	onClick="javascript:onSaveAsPlus();"><u>S</u>ave</button>
			
			
			 </logic:notEqual> 
			
			 <%} %>
			
			 <%if("Policies".equals(TTKCommon.getActiveSubLink(request))){ %>
			
			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'"	onClick="javascript:onSave();"><u>S</u>ave & Synchronize to Members</button>
			
			
			
			 <%} %>
			
			
			
			
			
			&nbsp;
			<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>
			&nbsp;
		 <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		%>
			<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
			</td>
			
		</tr>
	</table>
        
       
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        </div>
        <html:hidden name="frmRenewalDays" property="healthAuthority" styleId="healthAuthority"  />
        <html:hidden name="frmRenewalDays" property="premiumConfigSwrId"  />
        <html:hidden property="editInsertFlag"/>
        <html:hidden property="ruleSequenceId"/>
         <html:hidden name="frmRenewalDays" styleId="capitationYNid" property="capitationYN"  />
         <%-- <html:hidden name="frmRenewalDays" property="asoFromDate"  />
           <html:hidden name="frmRenewalDays" property="asoToDate"  /> --%>
            <html:hidden name="frmRenewalDays" property="asPlusFromDate"  />
             <html:hidden name="frmRenewalDays" property="asPlusToDate"  />
             <html:hidden name="frmRenewalDays" property="forward"  />
        <input type="hidden" name="mode"/>
        </html:form>