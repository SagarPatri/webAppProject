<%
/** @ (#) onlinepreauth.jsp 
 * Project     : TTK Healthcare Services
 * File        : addLaboratories.jsp
 * Author      : kishor kumar 
 * Company     : RCS Technologies
 * Date Created: 29/04/2015
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
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>`
<%@ page import=" com.ttk.common.TTKCommon" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper,org.apache.struts.action.DynaActionForm"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/hospital/onlinepreauth.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/jquery/ttk-jquery.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript">
$(document).ready(function(){
	/*   $("#clinicianName").blur(function(){
    	var clinicianName	=	document.getElementById("clinicianName").value;
    	clinicianName	=	clinicianName.substring(clinicianName.indexOf("::")+2,clinicianName.length);
        $.ajax({
        		url: "/AsynchronousAction.do?mode=getCommonMethod&id="+clinicianName+"&getType=clinicianName",  
        		success: function(result){
      			var res	=	result.split("@");
     			 document.forms[1].clinicianId.value=res[0];
      			 document.forms[1].speciality.value=res[1];
      			 document.forms[1].consultation.value=res[2];
        }}); 
    });
     */
    
    //onblur of Diag Code
    
   /*  $("#icdCode").blur(function(){
    	var icdCode	=	document.getElementById("icdCode").value;
        $.ajax({
        		url: "/AsynchronousAction.do?mode=getCommonMethod&id="+icdCode+"&getType=icdCode",  
        		success: function(result){
      			var res	=	result.split("@");
     			 document.forms[1].icdCode.value=res[0];
     			 document.forms[1].icdDesc.value=res[1];
        }}); 
    }); */
    
});

//Activity Details

/* $("#activityCode").blur(function(){
	var icdCode	=	document.getElementById("activityCode").value;
    $.ajax({
    		url: "/AsynchronousAction.do?mode=getCommonMethod&id="+activityCode+"&getType=activityCode",  
    		success: function(result){
  			var res	=	result.split("@");
 			 document.forms[1].icdDesc.value=res[0];
    }}); 
});

}); */
</script>
<head>
<link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
<link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<link rel="stylesheet" type="text/css" href="css/autoComplete.css" />
	<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
	<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>
	<script>
	 /* $(document).ready(function() {
		  $("#clinicianName").autocomplete("/AsynchronousAction.do?mode=getAutoCompleteMethod&getType=cliniciansForOnlinePreAuth");
			});  */
			
	/*  $(document).ready(function() {
		  $("#icdCode").autocomplete("/AsynchronousAction.do?mode=getAutoCompleteMethod&getType=icdCodesForOnlinePreAuth");
			});  
	 $(document).ready(function() {
		  $("#icdDesc").autocomplete("/AsynchronousAction.do?mode=getAutoCompleteMethod&getType=icdDescForOnlinePreAuth");
			}); */
			var JS_Focus_Disabled =true;
	</script>
</head>
<%
	boolean viewmode=false;
	boolean bEnabled=false;

	pageContext.setAttribute("encounterType", Cache.getCacheObject("encounterTypes"));
	pageContext.setAttribute("treatmentType", Cache.getCacheObject("treatmentTypes"));
	pageContext.setAttribute("consultTypeCode", Cache.getCacheObject("consultTypeCode"));
	pageContext.setAttribute("specialityTypeCode", Cache.getCacheObject("specialityType"));
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	String readonly	=	(String)request.getAttribute("readonly");
	if("readonly".equals(readonly))
		viewmode	=	true;
%>	

<html:form action="/OnlinePreAuthAction.do">
<body id="pageBody">
<%try{ %>
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
								<h4 class="sub_heading">Online Pre-Auth</h4>
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
	<!-- <div class="contentArea" id="contentArea"> -->
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
		<logic:notEmpty name="clinicianStatus" scope="request">
             <table align="center" class="errorContainer"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><img src="/ttk/images/warning.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
                             <bean:write name="clinicianStatus" scope="request"/>
                    </td>
                  </tr>
             </table>
        </logic:notEmpty>
		
		<!-- E N D : Success Box -->
		<%--
		COMMENTING THIS BLOCK FOR CHANGE IN THE REQUIREMENT
		 <fieldset>
			<legend>Validate Vidal ID</legend>
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    	<tr>
			        <td width="22%" class="formLabel">Vidal Id: <span class="mandatorySymbol">*</span></td>
			        <td width="22%" align="left">
			          <html:text property="vidalId" styleId="vidalId" name="frmOnlinePreAuth" styleClass="textBox textBoxLarge" disabled="<%=viewmode %>" />
			        </td>
		      	</tr>
		    </table>
		    <br>
		</fieldset>
		
		<!-- S T A R T : Buttons -->
	    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
		        <td width="100%" align="center">
					  <button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onValidateVidalId();"><u>N</u>ext</button>&nbsp;
		        </td>
	      	</tr>
	    </table> 
	    TILL HERE COMMENTED
	    --%>

  
  		<span class="fieldGroupTabHeader"> &nbsp;&nbsp;Member Details&nbsp;&nbsp;&nbsp;</span>
		  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
		        <td width="25%" class="formLabel">Vidal Health ID </td>
		        <td width="25%">: <bean:write name="frmOnlinePreAuth" property="enrollId"/> </td>
		        <td width="25%" class="formLabel">Card Holder Name</td>
		        <td width="25%">: <bean:write name="frmOnlinePreAuth" property="memberName"/> </td>
	      	</tr>
	      	<tr>
		        <td width="25%" class="formLabel">Date of Birth</td>
		        <td width="25%"> : <bean:write name="frmOnlinePreAuth" property="memDob"/> </td>
		        <td width="25%" class="formLabel">Gender </td>
		        <td width="25%"> : <bean:write name="frmOnlinePreAuth" property="gender"/> </td>
	      	</tr>
	      	<tr>
		        <td width="25%" class="formLabel">Emirate ID</td>
		        <td width="25%"> : <bean:write name="frmOnlinePreAuth" property="emirateID"/> </td>
	      	</tr>
	      	
	      	
	      	
	      	
	      	  <tr>
	      	  <td width="25%" class="formLabel">Policy No</td>
		       <td width="25%"> : <bean:write name="frmOnlinePreAuth" property="policyNum"/></td>
		        
		        
		        <td width="25%" class="formLabel">Network Type</td>
		        <td width="25%"> : <bean:write name="frmOnlinePreAuth" property="member_network"/></td>
		        
		        </tr>
	      	
	      	
	      	
	      	
	      	
	    </table>
	    <hr color="red">
	    <span class="fieldGroupTabHeader"> &nbsp;&nbsp;General Information &nbsp;&nbsp;</span>
		  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
		        <td width="25%" class="formLabel">Date of Treatment/Admission<span class="mandatorySymbol">*</span> </td>
		        <td width="25%"> 
		       
		        
: <html:text property="treatmentDate" styleClass="textBox textDateNewDesign" maxlength="10" readonly="<%=viewmode %>"/>
<logic:notEqual value="true" name="readonly">
<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmOnlinePreAuth.treatmentDate',document.frmOnlinePreAuth.treatmentDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
</logic:notEqual>
&nbsp;
   				</td>
		        <td width="25%" class="formLabel">Probable Date of Discharge / (For In-Patient) </td>
		        <td width="25%"> 
					
					: <html:text property="dischargeDate" styleClass="textBox textDateNewDesign" maxlength="10" />
					<logic:notEqual value="true" name="readonly">
   					<a name="CalendarObjectDischargeDate" id="CalendarObjectDschargeDate" href="#" onClick="javascript:show_calendar('CalendarObjectDischargeDate','frmOnlinePreAuth.elements[\'dischargeDate\']',document.frmOnlinePreAuth.elements['dischargeDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
   						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="disDate" width="24" height="17" border="0" align="absmiddle">
   					</a>
					</logic:notEqual> 
				</td>
	      	</tr>
	      	
	      	<tr>
		        
		        <td width="25%" class="formLabel">Clinician Id <span class="mandatorySymbol">*</span> </td>
		        <td width="25%"> 
		        <logic:equal value="true" name="readonly">
				 : <bean:write name="frmOnlinePreAuth" property="clinicianId"/> 
				</logic:equal>
					
				<logic:notEqual value="true" name="readonly">
	        	: <html:text property="clinicianId" styleId="clinicianId" styleClass="textBox textBoxMedium" onblur="validateClinician(this)"/>
		       <a href="#" accesskey="g"  onClick="javascript:selectClinician()" class="search"> <img src="/ttk/images/EditIcon.gif" alt="Select Clinician" width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
				</logic:notEqual> 
		        </td>
		        <td width="25%" class="formLabel">Clinician Name <span class="mandatorySymbol">*</span> </td>
		        <td width="25%"> 
		        
		         <logic:equal value="true" name="readonly">
		         : <bean:write name="frmOnlinePreAuth" property="clinicianName"/> 
		        </logic:equal>
		        
		        <logic:notEqual value="true" name="readonly">
	        		: <html:text property="clinicianName" styleId="clinicianName" styleClass="textBox textBoxLarge" maxlength="250"/>
	        	</logic:notEqual> 
		        </td>
	      	</tr>
	      	
      	<tr>
	        <td width="25%" class="formLabel">Clinician Specialty <span class="mandatorySymbol">*</span> </td>
	        <td width="25%"> 
	        :
       		<%-- <logic:equal value="true" name="readonly">
					  <bean:write name="frmOnlinePreAuth" property="speciality"/> 
			</logic:equal> 
	        <logic:notEqual value="true" name="readonly">--%>
		        <html:select property ="speciality" styleClass="selectBox selectBoxMedium">
          			<html:option value="">Select from list</html:option>
          			<html:options collection="specialityTypeCode" property="cacheId" labelProperty="cacheDesc"/>
   				</html:select>
   			<%-- </logic:notEqual> --%>
	        </td>
        	<td width="25%" class="formLabel">Consultation Type </td>
        	<td width="25%"> 
        	:
        	<%-- <logic:notEqual value="true" name="readonly"> --%>
        	<html:select property ="consultation" styleClass="selectBox selectBoxMedium">
       			<html:option value="">Select from list</html:option>
       			<html:options collection="consultTypeCode" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
			<%-- </logic:notEqual>
	        <logic:equal value="true" name="readonly">
				 <bean:write name="frmOnlinePreAuth" property="consultation"/> 
			</logic:equal> --%>
	        </td>
      	</tr>
      	<tr>
      		<td width="25%" class="formLabel">PreAuth Reference No. </td>
        	<td width="25%">: <bean:write name="frmOnlinePreAuth" property="preAuthRefNo"/></td>
        </tr>
      	
   </table>
	    <hr color="red"><!-- style="background: #91c85f;" -->
	     <span class="fieldGroupTabHeader">  &nbsp;&nbsp;Clinical Details &nbsp;&nbsp;</span>
	    <!-- <span class="fieldGroupHeader" > &nbsp;&nbsp;Clinical Details &nbsp;&nbsp;</span> -->
		  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
		        <td width="25%" class="formLabel">Presenting Complaints <span class="mandatorySymbol">*</span> </td>
		        <td width="25%" colspan="3"> 
		        <logic:equal value="true" name="readonly">
		        	: <html:textarea property="presentingComplaints" styleClass="textBox textAreaLong" readonly="true">
		        	<bean:write name="frmOnlinePreAuth" property="presentingComplaints"/>
		        	</html:textarea>
					</logic:equal>
					
					<logic:notEqual value="true" name="readonly">
		        	: <html:textarea property="presentingComplaints" styleClass="textBox textAreaLong"/>
					</logic:notEqual> 
		        </td>
		    </tr>
		    <tr>
		        <td width="25%" class="formLabel">Details of any Past history relevant to the present illness </td>
		        <td width="25%" colspan="3"> 
		       		<logic:equal value="true" name="readonly">
						: <html:textarea property="pastHistory" styleClass="textBox textAreaLong" readonly="true">
							<bean:write name="frmOnlinePreAuth" property="pastHistory"/>
						</html:textarea>							
					</logic:equal>
					
					<logic:notEqual value="true" name="readonly">
					: <html:textarea property="pastHistory" styleClass="textBox textAreaLong"/> 
					</logic:notEqual> 
					</td>
	      	</tr>
	      	<tr>
	      		<td width="25%" class="formLabel">Duration of present ailment <span class="mandatorySymbol">*</span> </td>
		        <td width="25%"> 
		        	
		        	
		        	<logic:equal value="true" name="readonly">
		        	: <bean:write name="frmOnlinePreAuth" property="ailmentDurationText"/> 
					 <bean:write name="frmOnlinePreAuth" property="ailmentDuration"/> 
					</logic:equal>
					
					<logic:notEqual value="true" name="readonly">
					: <html:text property="ailmentDurationText" styleClass="textBox textBoxSmallest" maxlength="10"/>
						<html:select property ="ailmentDuration" styleClass="selectBox selectBoxSmall">
              			<html:option value="DAYS">Days</html:option>
              			<html:option value="WEEKS">Weeks</html:option>
              			<html:option value="MONTHS">Months</html:option>
              			<html:option value="YEARS">Years</html:option>
       				</html:select> 					</logic:notEqual> 
       			</td>
	     	 	<td width="25%" class="formLabel">Duration of the Past History<br> Relevant to the present illness </td>
		        <td width="25%"> 
			        
			        
			        <logic:equal value="true" name="readonly">
						: <bean:write name="frmOnlinePreAuth" property="illnessDurationText"/> 
					 <bean:write name="frmOnlinePreAuth" property="illnessDuration"/> 
					</logic:equal>
					
					<logic:notEqual value="true" name="readonly">
			       : <html:text property="illnessDurationText" styleClass="textBox textBoxSmallest" maxlength="10"/>
						<html:select property ="illnessDuration" styleClass="selectBox selectBoxSmall">
              			<html:option value="DAYS">Days</html:option>
              			<html:option value="WEEKS">Weeks</html:option>
              			<html:option value="MONTHS">Months</html:option>
              			<html:option value="YEARS">Years</html:option>
       				</html:select>			</logic:notEqual> 
				</td>
	      	</tr>
	      	<tr>
	      		<td width="25%" class="formLabel">Benefit Type <span class="mandatorySymbol">*</span> </td>
		        <td width="25%">: <bean:write name="frmOnlinePreAuth" property="benifitType"/> </td>
	      		<td width="25%" class="formLabel">Treatment Type </td>
		        <td width="25%">
       				<logic:equal value="true" name="readonly">
					: <bean:write name="frmOnlinePreAuth" property="treatmentType"/> 
					</logic:equal>
					
					<logic:notEqual value="true" name="readonly">
						: <html:select property ="treatmentType" styleClass="selectBox selectBoxMedium">
              			<html:options collection="treatmentType" property="cacheId" labelProperty="cacheDesc"/>
       				</html:select>   
       				</logic:notEqual> 
       				
				</td>
	      	</tr>
	      	<tr>
	      		<td width="25%" class="formLabel">Encounter Type <span class="mandatorySymbol">*</span> </td>
		        <td width="25%"> 
		        
		        <%-- <logic:equal value="true" name="readonly">
					 : <bean:write name="frmOnlinePreAuth" property="encounterType"/> 
					</logic:equal>
					
					<logic:notEqual value="true" name="readonly"> --%>
						: <html:select property ="encounterType" styleClass="selectBox selectBoxMedium">
      					<%-- <html:option value="">Select from list</html:option> --%>
              			<html:options collection="encounterType" property="cacheId" labelProperty="cacheDesc"/>
       				</html:select>
       				<%-- </logic:notEqual> --%> 
       			</td>
       			<td width="25%" class="formLabel">Requested Amount</td>
		        <td width="25%"> 
		        	: <html:text property="proReqAmt" styleId="proReqAmt" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" />
		        </td>
	      	</tr>
	    </table>
		    
		    
		<%-- <logic:notEmpty name="lPreAuthIntSeqId" scope="session"> --%>
 		<hr color="red">
	    <span class="fieldGroupTabHeader">  &nbsp;&nbsp;Diagnosis Details &nbsp;&nbsp;</span>
	    <logic:notEqual value="true" name="readonly">
		<a href="#" accesskey="a" onClick="javascript:addDiagnosisDetails();"><img src="/ttk/images/AddIcon.gif" ALT="Add Activity Details" width="13" height="13" border="0" align="absmiddle"></a>
	    <table align="center" class="formContainerWithoutPad"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
	    	
	    	
                        <td width="25%" class="formLabel">Diagnosis Code </td>
                        <td class="textLabel">
		 <table>
   		 <tr>
        <td>
          : <html:text property="diagnosisDetailsVO.icdCode" styleClass="textBox textBoxMedium" styleId="icdCode" onkeyup="ConvertToUpperCase(this);onProviderSearch10(this)" />
      	</td>
      </tr>
      <tr>
      <td>
      <div class="searchResults" id="spDivID" style="display:none;"></div>
      </td>
      </tr>
      </table> 
			
			
		        <td width="25%" class="formLabel">Diagnosis Description </td>
		        <td width="25%"> 
		        	<html:textarea property="diagnosisDetailsVO.ailmentDescription" styleId="icdDesc" styleClass="textBox textAreaMedium" onkeyup="ConvertToUpperCase(this);onIcdDescSearch(this)"/> 
		        </td>
		         <tr>
		         <td colspan="2"> &nbsp;</td>
		          <td> &nbsp;</td>
			      <td>
			     : <div class="searchResults" id="icdDescDivID" style="display:none;"></div>
			      </td>
			      </tr>
      
		    </tr>
		    <tr>    
		        <td colspan="4" align="right"> 
		        	<button id="diagnosisBtnID" type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddDiags('icdCode');"><u>A</u>dd</button>&nbsp;
		        </td>
		    </tr>
		</table>
		</logic:notEqual>	
		 <ttk:OnlineDiagnosisDetails flow="PAT"/>
		<br>
		
		<hr color="red">
	    <span class="fieldGroupTabHeader">  &nbsp;&nbsp;Treatment Details &nbsp;&nbsp;
	    </span>
	<logic:notEqual value="true" name="readonly">
	    <a href="#" accesskey="a" onClick="javascript:addActivityDetails();"><img src="/ttk/images/AddIcon.gif" ALT="Add Activity Details" width="13" height="13" border="0" align="absmiddle"></a>
	    <table align="center" class="formContainerWithoutPad"  border="0" cellspacing="0" cellpadding="0">
	    
	    <tr>
	    	
	    	
             <td width="25%" class="formLabel">Activity Code </td>
             <td width="25%">
       			: <html:text property="activityDetailsVO.activityCode" styleId="activityCode" styleClass="textBox textBoxMedium" onkeyup="ConvertToUpperCase(this);onActivityCodeSearch(this)"/>
		     	<br/>	 <div class="searchResults" id="activityCodeDivID" style="display:none;"></div>
		      </td>
			
			
		        <td width="25%" class="formLabel">Activity Description </td>
		        <td width="25%"> 
		        	: <html:textarea property="activityDetailsVO.activityCodeDesc" styleId="activityCodeDesc" styleClass="textBox textAreaMedium" onkeyup="ConvertToUpperCase(this);onActivityDescSearch(this)" />
 				<br/>
			     	<div class="searchResults" id="activityDescDivID" style="display:none;"></div>
			     	</td>
		     	
      
		    </tr> 
		    <tr>
		        <td width="25%" class="formLabel">Quantity </td>
		        <td width="25%"> 
		        	: <html:text property="activityDetailsVO.quantity" styleId="activityQuantity" styleClass="textBox textBoxMedium"/>
		        </td>
		   </tr>     
		   
	        <tr>    
		        <td colspan="4" align="right"> 
		        	<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveActivities();"><u>A</u>dd</button>&nbsp;
		        </td>
		    </tr>
		</table>
		</logic:notEqual>
		<ttk:OnlineActivityDetails flow="PAT"/>
		<br>
		
		<hr color="red">
	    <span class="fieldGroupTabHeader">  &nbsp;&nbsp;Drug Details &nbsp;&nbsp;</span>
 <logic:notEqual value="true" name="readonly">
	    <a href="#" accesskey="a" onClick="javascript:addDrugDetails();"><img src="/ttk/images/AddIcon.gif" ALT="Add Activity Details" width="13" height="13" border="0" align="absmiddle"></a>
	    <table align="center" class="formContainerWithoutPad"  border="0" cellspacing="0" cellpadding="0">
		      
		      <tr>
	    	
                        <td width="25%" class="formLabel">DDC Code </td>
                        <td class="textLabel">
			        	: <html:text property="drugDetailsVO.drugCode" styleId="drugCode" styleClass="textBox textBoxMedium" onkeyup="ConvertToUpperCase(this);onDrugCodeSearch(this)"/>
					<br/>      <div class="searchResults" id="drugCodeDivID" style="display:none;"></div>
						</td>			
			
		        <td width="25%" class="formLabel">DDC Description </td>
		        <td width="25%"> 
		        	: <html:textarea property="drugDetailsVO.drugDesc" styleId="drugDesc" styleClass="textBox textAreaMedium" onkeyup="ConvertToUpperCase(this);onDrugDescSearch(this)"/> 
		        <br/>
			      <div class="searchResults" id="drugDescDivID" style="display:none;"></div>
      			</td>
		    </tr>
		    
		    <tr>
		    	<td width="25%" class="formLabel">Posology </td>
		        <td width="25%"> 
		        	: <html:select property ="drugDetailsVO.posology" styleId="posology" styleClass="selectBox selectBoxMedium" onchange="calcQtyOnPosology()">
      					<html:option value="1">OD</html:option>
      					<html:option value="2">BD</html:option>
      					<html:option value="3">TDS</html:option>
      					<html:option value="4">QID</html:option>
       				</html:select>
       			</td>
       			<td width="25%" class="formLabel">Days </td>
		        <td width="25%"> 
		        	: <html:text property="drugDetailsVO.days" styleId="drugdays" styleClass="textBox textBoxSmall" onblur="calcQtyOnPosology()"/><!-- caclQty -->
		        </td>
		    </tr>
		    <tr>
		    	<td width="25%" class="formLabel">Type of Unit </td>
		        <td width="25%"> 
		        	: <html:select property ="drugDetailsVO.drugunit" styleId="drugUnit" styleClass="selectBox selectBoxMedium" onchange="calcQtyOnPosology()"><!-- checkQty(this) -->
      					<html:option value="PCKG">Package</html:option>
      					<html:option value="LOSE">Unit</html:option>
       				</html:select>
       			</td>
       			<td width="25%" class="formLabel">Quantity </td>
		        <td width="25%"> 
		        	: <html:text property="drugDetailsVO.drugqty" styleId="drugquantity" styleClass="textBox textBoxSmall" readonly="readonly"/>
		        </td>
		   </tr>
		   <tr>    
		        <td colspan="4" align="right"> 
		        	<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveDrugs();"><u>A</u>dd</button>&nbsp;
		        </td>
		        
		    </tr>
		</table>
		</logic:notEqual>
		<ttk:OnlineDrugDetails flow="PAT"/>
		<br>
		
<logic:notEmpty  name="frmOnlinePreAuth" property="preAuthRefNo">	
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
   	<tr><td>
	<%--<html:file property="file" styleId="file"/>
 	 <a href="#" onclick="showUplodedFile('<bean:write name="frmOnlinePreAuth" property="fileName"/>')"> <bean:write name="frmOnlinePreAuth" property="fileName"/> </a> --%>
			<a href="#" onClick="javascript:onUploadDocs()">Document Uploads </a>
	</td>
</tr></table>
</logic:notEmpty>
    
 <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
   	<tr>
        <td width="100%" align="center">
        <logic:empty name="frmOnlinePreAuth" property="preAuthNo"><!-- IF preAuth number generated -->
			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSavePartialPreAuth();"><u>S</u>ave</button>&nbsp;&nbsp;&nbsp;
		</logic:empty>
        <logic:notEqual value="true" name="readonly">
        	<logic:empty name="frmOnlinePreAuth" property="preAuthNo"><!-- IF preAuth number generated -->
			<button type="button" name="Button" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveOnlinePreAuth();"><u>V</u>erify Details</button>&nbsp;
			</logic:empty>
			<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onExit();"><u>E</u>xit</button>&nbsp;
		</logic:notEqual>	
	<logic:notEmpty name="frmOnlinePreAuth" property="preAuthRefNo">	<!-- IF preAuth number generated -->
		<logic:equal value="true" name="readonly">
			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSubmitOnlinePreAuth();"><u>S</u>ubmit</button>&nbsp;&nbsp;&nbsp;
			<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onEditOnlinePreAuth();"><u>E</u>dit</button>&nbsp;&nbsp;&nbsp;
			<button type="button" name="Button" accesskey="x" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onExit();">E<u>x</u>it</button>&nbsp;&nbsp;&nbsp;
		</logic:equal>
	</logic:notEmpty>
				       
		 </td>
      	<td width="100%" align="center">
   	</tr>
</table>
		<!-- E N D : Buttons -->
		<%-- </logic:notEmpty> --%>
	<input type="hidden" name="mode" value="">
	<INPUT TYPE="hidden" NAME="leftlink">
	<INPUT TYPE="hidden" NAME="sublink">
	<INPUT TYPE="hidden" NAME="tab">
	<INPUT TYPE="hidden" NAME="rownum">
	<INPUT TYPE="hidden" NAME="preAuthNoYN" value="<bean:write name="frmOnlinePreAuth" property="preAuthNo"/> ">
	<input type="hidden" name="gran" id="gran" value="<%= request.getAttribute("gran")%>">
	<input type="hidden" name="focusObj" value="<%= request.getAttribute("focusObj")%>">
	<input type="hidden" name="lPreAuthIntSeqId" value="<%= request.getSession().getAttribute("lPreAuthIntSeqId")%>">
<script type="text/javascript">
	
	</script>
	
	<!-- </div> --></div></div></div></div></div></div></div>
	
	<%}catch(Exception e) {
	System.out.println("Error is::"+e);
	e.printStackTrace();
	}
	%></body>
</html:form>
<logic:notEmpty name="focusFieldID">
<script>
$(document).ready(function() {
	
	callFocusObj('<bean:write name="focusFieldID"/>');
	
});
</script>
</logic:notEmpty>