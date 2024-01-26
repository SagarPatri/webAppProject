<%
/** @ (#) onlinepreauth.jsp 
 * Project     : TTK Healthcare Services
 * File        : addLaboratories.jsp(Partner)
 * Author      :
 * Company     : RCS Technologies
 * Date Created: 
 * @author 		 : k
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import="java.util.ArrayList,com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>`
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper,org.apache.struts.action.DynaActionForm"%>


<script language="javascript" src="/ttk/scripts/partner/onlinepreauth.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/common/customeautocomplete.js"></script>

<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<script>
			var JS_Focus_Disabled =true;
			var JS_Focus_ID="<%=TTKCommon.checkNull(request.getAttribute("focusId"))%>";
	</script>
	
	<!-- <style type="text/css">
	#divParent{
    position:relative;
}
#icdDivID{
position: absolute;
}
#icdDescDivID{
position: absolute;
}
#activityCodeDivID{
position: absolute;
}
#internalCodeDivID{
position: absolute;
}
#activityDescDivID{
position: absolute;
}
#drugCodeDivID{
position: absolute;
}
#drugDescDivID{
position: absolute;
}
#clinicianNameDivID{
position: absolute;
}
#clinicianId{
position: absolute;
}
	</style> -->
</head>
<%
	boolean viewmode=false;
	boolean bEnabled=false;
	boolean savePartialPreAuthFlag = false;

	//pageContext.setAttribute("encounterType", Cache.getCacheObject("encounterTypes"));
	pageContext.setAttribute("treatmentType", Cache.getCacheObject("treatmentTypes"));
	pageContext.setAttribute("benifitType",Cache.getCacheObject("main4benifitTypes"));
	pageContext.setAttribute("consultTypeCode", Cache.getCacheObject("consultTypeCode"));
	pageContext.setAttribute("specialityTypeCode", Cache.getCacheObject("specialityType"));
	pageContext.setAttribute("provDocsType", Cache.getCacheObject("provDocsType"));
	pageContext.setAttribute("dentalTreatmentTypes", Cache.getCacheObject("dentalTreatmentTypes"));
	pageContext.setAttribute("alCountryCode",Cache.getCacheObject("countryCode"));
	pageContext.setAttribute("partnerEncounterTypes", Cache.getCacheObject("partnerEncounterTypes"));
	
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	String readonly	=	(String)request.getAttribute("readonly");
	String savePartialPreAuthFlagvalue = (String)request.getAttribute("savePartialPreAuthFlag");
	pageContext.setAttribute("natureOfConception",	Cache.getCacheObject("natureOfConception"));
	pageContext.setAttribute("modeofdelivery",	Cache.getCacheObject("modeofdelivery"));
	pageContext.setAttribute("systemOfMedicines",Cache.getCacheObject("systemOfMedicines"));
	if("readonly".equals(readonly))
		viewmode	=	true;

	if("savePartialPreAuth".equals(savePartialPreAuthFlagvalue))
		savePartialPreAuthFlag = true;
%>	

<html:form action="/OnlinePreAuthAction.do" method="post" enctype="multipart/form-data" styleId="testId">
			<!-- S T A R T : Content/Form Area -->
			<!-- <div id="navigateBar">Home > Corporate > Detailed > Claim Details</div> -->
<h4 class="sub_heading">Partner Online Pre-Approval</h4>
<div class="contentArea" id="contentArea">
<br>

	<!-- S T A R T : Page Title -->
	<html:errors/>
	<!-- E N D : Page Title --> 
	<!-- S T A R T : Form Fields -->
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
		

		  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
		        <td width="10%" class="formLabel">Vidal Enrollment Id </td>
		        <td width="90%">: <html:text property="enrollId" styleClass="textBox textDateNewDesign" maxlength="10"  readonly="true"/> </td>
	      	</tr>
	      	<tr>
		        <td width="10%" class="formLabel">Benefit Type </td>
		        <td width="90%">: <html:select property ="benifitType" styleId="benifitTypeID" styleClass="selectBox selectBoxMedium"  disabled="true">
                 			<%-- <html:option value="">Select from list</html:option> --%>
                 			<html:options collection="benifitType" property="cacheId" labelProperty="cacheDesc"/>
          					</html:select> 
	      		<html:hidden property="benifitType"/>
	      		</td>
	      	</tr>
	      	<tr>
			        <td class="formLabel" >Encounter Type </td>
			        <td width="90%" >: <html:select property="ptrEncounterType" styleId="encounterTypeIndex" styleClass="selectBox sselectBoxMedium" disabled="true">
			        				<html:options collection="partnerEncounterTypes" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
					<html:hidden property="ptrEncounterType"/>
          			</td>
		      	</tr>
	      	<tr>
		        <td width="10%" class="formLabel">Date of Treatment/Admission<span class="mandatorySymbol">*</span> </td>
		        <td width="90%"> : <html:text property="treatmentDate" styleClass="textBox textDateNewDesign" maxlength="10" readonly="true"/>
				<%-- <logic:notEqual value="true" name="readonly">
				<!-- <A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmOnlinePreAuth.treatmentDate',document.frmOnlinePreAuth.treatmentDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a> -->
			<a name="CalendarObjectTreatmentDate" id="CalendarObjectTreatmentDate" href="#" onClick="javascript:show_calendar('CalendarObjectTreatmentDate','frmCashlessAdd.elements[\'treatmentDate\']',document.frmCashlessAdd.elements['treatmentDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
       						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
       					</a> 
				</logic:notEqual> --%>
				&nbsp;
   				</td>
   			</tr>
   			<tr>
   				<td width="10%" class="formLabel">Probable Date of Discharge </td>
		        <td width="90%"> : <html:text property="dischargeDate" styleClass="textBox textDateNewDesign" maxlength="30"  readonly="true"/>
				<%-- 	<logic:notEqual value="true" name="readonly">
   					<a name="CalendarObjectDischargeDate" id="CalendarObjectDschargeDate" href="#" onClick="javascript:show_calendar('CalendarObjectDischargeDate','frmOnlinePreAuth.elements[\'dischargeDate\']',document.frmOnlinePreAuth.elements['dischargeDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
   						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="disDate" width="24" height="17" border="0" align="absmiddle">
   					</a>
					</logic:notEqual> --%>				
				</td>
   			</tr>
	      	<tr>
		        <td width="10%" class="formLabel">Estimated Cost </td>
		        <td width="90%">: <html:text property="estimatedCost" styleClass="textBox textDateNewDesign" maxlength="30" readonly="true"/> </td>
	      	</tr>
	      	<tr>
		        <td width="10%" class="formLabel">Provider Name </td>
		        <td width="90%">: <html:text property="providerName" styleClass="textBox textDateNewDesign" maxlength="30"  readonly="true"/> </td>
	      	</tr>
	      	<tr>
		        <td width="10%" class="formLabel">Provider Country </td>
		        <td width="90%">: <html:select property ="country" styleId="country" styleClass="selectBox selectBoxMedium" disabled="true" readonly="true">
                 			<%-- <html:option value="">Select from list</html:option> --%>
                 			<html:options collection="alCountryCode" property="cacheId" labelProperty="cacheDesc"/>
			        </html:select> 
			        <html:hidden property="country"/>
			       </td>
	      	</tr>
	      	<tr>
		        <td width="10%" class="formLabel">Currency </td>
		        <td width="90%">: <html:text property="currency" styleId="currency"  styleClass="textBox textBoxMedium textBoxDisabled" disabled="true" readonly="true" />
		        <a href="#" >
		        <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" border="0" align="bottom" > </a> </td>
	      	</tr>
	      	<logic:equal name="frmOnlinePreAuth" property="benifitType" value="IPT">
	      	<tr>
		        <td width="10%" class="formLabel">Line Of Treatment<span class="mandatorySymbol">*</span> </td>
		          <td colspan="3"><html:textarea property="lineOfTreatment" styleClass="textBox textAreaMedium" /></td>
	      	</tr>
	      	</logic:equal>
	    </table>
	    
	    <%if(!savePartialPreAuthFlag){ %>
	    
	    
	    
	    
	    <logic:notEmpty name="clinicianStatus" scope="request">
             <table align="center" class="errorContainer"  border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td><img src="/ttk/images/warning.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
                        <bean:write name="clinicianStatus" scope="request"/>
                    </td>
                  </tr>
             </table>
        </logic:notEmpty>

<logic:notEmpty  name="frmOnlinePreAuth" property="preAuthRefNo">		
	    <span class="fieldGroupTabHeader"> &nbsp;&nbsp;Pre-Approval Details&nbsp;&nbsp;&nbsp;</span><br>
	    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0"> <tr> 
	    	<td width="25%" class="formLabel"> Pre-Approval No. </td>
	    	<td width="25%"> : <bean:write name="frmOnlinePreAuth" property="preAuthNo"/> </td>
	    </tr> <tr>
	    	<td width="25%" class="formLabel"> Reference No. </td>
	    	<td width="25%"> : <bean:write name="frmOnlinePreAuth" property="preAuthRefNo"/> </td>  
<logic:notEmpty name="frmOnlinePreAuth" property="preAuthNo"> 		<!-- Once PreAuth Submitted show only Form Data -->	    
     <tr>
	   <td width="25%" class="formLabel">Authorization No. </td>
       <td width="25%">: <bean:write name="frmOnlinePreAuth" property="authorizationNo"/> </td>
       <td width="25%" class="formLabel">Pre-Approval Amount </td>
       <td width="25%">: <bean:write name="frmOnlinePreAuth" property="totalAgreedRate"/> </td>
	</tr>
</logic:notEmpty>       
	</table>
	<br>
</logic:notEmpty>

  		<span class="fieldGroupTabHeader"> &nbsp;&nbsp;Patient Details&nbsp;&nbsp;&nbsp;</span>
		  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
		        <td width="25%" class="formLabel">Al Koot ID </td>
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
		        <td width="25%" class="formLabel">Qatar ID</td>
		        <td width="25%"> : <bean:write name="frmOnlinePreAuth" property="emirateID"/> </td>
	      	</tr>
	    </table>
	    <br>
	    <span class="fieldGroupTabHeader"> &nbsp;&nbsp;Hospitalization Details &nbsp;&nbsp;</span>
		  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0" style="width: 99%">
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
		        
		        <td width="25%" class="formLabel">Name of Treating Doctor<span class="mandatorySymbol">*</span> </td>
		        <td width="25%"> 
		        
		         <logic:equal value="true" name="readonly">
		         : <bean:write name="frmOnlinePreAuth" property="clinicianName"/> 
		        </logic:equal>
		        <logic:notEqual value="true" name="readonly">
		        <div id="divParent">
	        		: <html:text property="clinicianName" styleId="clinicianName" styleClass="textBox textBoxMedium" maxlength="250" onkeyup="onClinicianNameSearch();"/>
	        		<div class="sdMainDiv" id="clinicianNameDivID"  style="display:none;width: 190px;"></div>
	        	</div>
	        	</logic:notEqual> 
		        </td>
		        <td width="25%" class="formLabel">License No. <span class="mandatorySymbol">*</span> </td>
		        <td width="25%"> 
		        <logic:empty name="frmOnlinePreAuth" property="preAuthNo"> <!-- Once PreAuth Submitted show only Form Data -->
		       	<div id="divParent">
	       			: <html:text property="clinicianId" styleId="clinicianId" styleClass="textBox textBoxMedium" onkeyup="onClinicianSearch()"/>
			     	 <div id="clinicianDivID" style="display:none;">
			     	 </div>
			     </div>			        
		       </logic:empty>
		       
		       <logic:notEmpty name="frmOnlinePreAuth" property="preAuthNo">
		       	: <bean:write name="frmOnlinePreAuth" property="clinicianId"/> 
		       </logic:notEmpty>
		       
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
	        </td>
        	<td width="25%" class="formLabel">Consultation Type </td>
        	<td width="25%"> 
        	:
        	<html:select property ="consultation" styleClass="selectBox selectBoxMedium">
       			<html:option value="">Select from list</html:option>
       			<html:options collection="consultTypeCode" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
      	
  <!-- </table>
    <br>
	     <span class="fieldGroupTabHeader">  &nbsp;&nbsp;Clinical Details &nbsp;&nbsp;</span>
	    <span class="fieldGroupHeader" > &nbsp;&nbsp;Clinical Details &nbsp;&nbsp;</span>
		  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0" style="width: 99%">
	    	--><tr> 
		        <td width="25%" class="formLabel">Presenting Complaints <span class="mandatorySymbol">*</span>
		        </td>
		        <td colspan="3"> 
		        <logic:equal value="true" name="readonly">
					<html:textarea property="presentingComplaints" styleClass="textBox textAreaMedium" readonly="true">
		        	: <bean:write name="frmOnlinePreAuth" property="presentingComplaints"/>
		        	</html:textarea>
					</logic:equal>
					
					<logic:notEqual value="true" name="readonly">
						: <html:textarea property="presentingComplaints" styleClass="textBox textAreaMedium"/>
					</logic:notEqual> 
		        &nbsp;&nbsp;&nbsp;Duration
		        <bean:define id="benefitTemp" name="frmOnlinePreAuth" property="benifitType"/>	
		        <% if(!"MTI".equals(String.valueOf(benefitTemp)) && !"Maternity".equals(String.valueOf(benefitTemp))){
		    	   %>
		        	<span class="mandatorySymbol">*</span>
	        	<%} %>
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
       				
       				
       				<logic:equal value="In-Patient Maternity"  property="benifitTypeCode" name="frmOnlinePreAuth">
       				<br>
       				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date of LMP <span class="mandatorySymbol">*</span> : <html:text property="dateOfLMP" styleClass="textBox textDateNewDesign" maxlength="10" />
   					<a name="CalendarObjectDateOfLMP" id="CalendarObjectDateOfLMP" href="#" onClick="javascript:show_calendar('CalendarObjectDateOfLMP','frmOnlinePreAuth.elements[\'dateOfLMP\']',document.frmOnlinePreAuth.elements['dateOfLMP'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
   						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="disDate" width="24" height="17" border="0" align="absmiddle">
   					</a>
					</logic:equal> 
       				
					<logic:equal value="Out-Patient Maternity"  property="benifitTypeCode" name="frmOnlinePreAuth">
       				<br>
       				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Date of LMP <span class="mandatorySymbol">*</span> : <html:text property="dateOfLMP" styleClass="textBox textDateNewDesign" maxlength="10" />
   					<a name="CalendarObjectDateOfLMP" id="CalendarObjectDateOfLMP" href="#" onClick="javascript:show_calendar('CalendarObjectDateOfLMP','frmOnlinePreAuth.elements[\'dateOfLMP\']',document.frmOnlinePreAuth.elements['dateOfLMP'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
   						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="disDate" width="24" height="17" border="0" align="absmiddle">
   					</a>
					</logic:equal> 
       			</td>
		    </tr>
		    <tr>
		        <td width="25%" class="formLabel">Details of any Past history relevant to the present illness </td>
		        <td colspan="3"> 
		       		<logic:equal value="true" name="readonly">
					<html:textarea property="pastHistory" styleClass="textBox textAreaMedium" readonly="true">
							: <bean:write name="frmOnlinePreAuth" property="pastHistory"/>
						</html:textarea>							
					</logic:equal>
					
					<logic:notEqual value="true" name="readonly">
						: <html:textarea property="pastHistory" styleClass="textBox textAreaMedium"/> 
					</logic:notEqual> 
			         
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
       				</html:select>			
       				</logic:notEqual> 
				
				
				<logic:equal value="In-Patient Maternity"  property="benifitTypeCode" name="frmOnlinePreAuth">
				Nature of Conception <span class="mandatorySymbol">*</span> : 
					  <html:select property="natureOfConception" styleClass="selectBox selectBoxSmall" >
								<html:optionsCollection name="natureOfConception" label="cacheDesc" value="cacheId" />
						</html:select>
				</logic:equal>	
				
				<logic:equal value="Out-Patient Maternity"  property="benifitTypeCode" name="frmOnlinePreAuth">
				Nature of Conception <span class="mandatorySymbol">*</span> : 
					  <html:select property="natureOfConception" styleClass="selectBox selectBoxSmall" >
								<html:optionsCollection name="natureOfConception" label="cacheDesc" value="cacheId" />
						</html:select>
				</logic:equal>	
				</td>	
			
				
	      	</tr>
	      	<tr>
	      		<td width="25%" class="formLabel">Benefit Type <span class="mandatorySymbol">*</span> </td>
		        <td width="25%">
		        	: <bean:write name="frmOnlinePreAuth" property="benifitTypeCode"/>
		        </td>
		       <% if(!"IMTI".equals(String.valueOf(benefitTemp)) && !"Maternity".equals(String.valueOf(benefitTemp))){%>
	      		<td width="25%" class="formLabel">Treatment Type <span class="mandatorySymbol">*</span></td>
		        <td width="25%">
       				<logic:equal value="true" name="readonly">
					: <bean:write name="frmOnlinePreAuth" property="treatmentType"/> 
					</logic:equal>
				<%}%>
						
						
						<%if("DNTL".equals(benefitTemp) || "Dental".equals(benefitTemp)) {%>
							: 	<html:select property ="treatmentType" styleClass="selectBox selectBoxMedium" onchange="changeTreatment(this)">
									<html:option value="">--Select from List--</html:option>
									<html:optionsCollection name="dentalTreatmentTypes" label="cacheDesc" value="cacheId" />
							</html:select>   
						<%}else if("IMTI".equals(String.valueOf(benefitTemp)) || "In-Patient Maternity".equals(String.valueOf(benefitTemp))){%>
					       
					       <td width="25%" class="formLabel">Mode Of Delivery </td>
					        <td width="25%"> 
					        
									: <html:select property="modeofdelivery" styleClass="selectBox selectBoxMedium" >
												<html:optionsCollection name="modeofdelivery" label="cacheDesc" value="cacheId" />
										</html:select>
			       			</td>
					       
			       				<%}else{ %>
								: 	<html:select property ="treatmentType" styleClass="selectBox selectBoxMedium" >
          						<html:options collection="treatmentType" property="cacheId" labelProperty="cacheDesc"/>
          					</html:select>   
					<%} %> 

				</td>
		    	   
       				
	      	</tr>
	      	<tr>
	      		<td width="25%" class="formLabel">Encounter Type <span class="mandatorySymbol">*</span> </td>
		        <td width="25%"> 
		        
						: <html:select property ="encounterType" styleClass="selectBox selectBoxMedium">
      					<%-- <html:option value="">Select from list</html:option> --%>
              			<html:optionsCollection name="frmOnlinePreAuth" property="encounterTypes" label="cacheDesc" value="cacheId"/>
       				</html:select>
       			</td>
       			<td width="25%" class="formLabel">System of Medicine <span class="mandatorySymbol">*</span> </td>
		        <td width="25%"> 
		        
						: <html:select property ="systemOfMedicine" styleClass="selectBox selectBoxMedium">
						<html:optionsCollection name="systemOfMedicines" label="cacheDesc" value="cacheId" />    				</html:select>
       			</td>
	      	</tr>
	      	
	      	
	      	<tr> 
			<td colspan="4"> 
			<%@include file="/ttk/preauth/dentalDetails.jsp" %>
			</td>
			</tr>
	    </table>
		    <br>
<logic:empty name="frmOnlinePreAuth" property="preAuthNo"> 		<!-- Once PreAuth Submitted show only Form Data -->		    
	    <span class="fieldGroupTabHeader">  &nbsp;&nbsp;Diagnosis Details &nbsp;&nbsp;</span>
	    <logic:notEqual value="true" name="readonly">
		<a href="#" accesskey="a" onClick="javascript:addDiagnosisDetails();"><img src="/ttk/images/AddIcon.gif" ALT="Add Diagnosis Details" width="13" height="13" border="0" align="absmiddle"></a>
	    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0" style="width: 99%">
	    <tr>
	        <td width="25%" class="formLabel">Diagnosis Description<span class="mandatorySymbol">*</span> </td>
	        <td width="35%"> 
	        <div id="divParent">
        		: <html:textarea property="diagnosisDetailsVO.ailmentDescription" styleId="icdDesc"  styleClass="textBox textAreaMedium" onkeyup="onDiagDescSearch();"/>
        		<div id="icdDescDivID"  style="display:none;width: 290px;"></div>
        	</div>
	        </td>
	        <td width="15%" class="formLabel">Diagnosis Code. <span class="mandatorySymbol">*</span> </td>
	        <td width="25%"> 
	        <div id="divParent">
				: <html:text property="diagnosisDetailsVO.icdCode" styleClass="textBox textBoxSmall" styleId="icdCode" onkeyup="onDiagCodeSearch()"/>
				<div id="icdDivID"  style="display:none;width: 190px;"></div>
 			</div>
	        </td>
      	</tr>
	      	
		    <tr>    
		        <td colspan="4" align="right"> 
		        	<button type="button" name="Button" accesskey="a" class="olbtnSmall" onClick="javascript:onAddDiags('icdDesc');"><u>A</u>dd</button>&nbsp;
		        </td>
		    </tr>
		</table>
		</logic:notEqual>	
</logic:empty>		

<logic:empty name="frmOnlinePreAuth" property="preAuthNo">
	<ttk:OnlineDiagnosisDetails flow="PAT" preAuthNoYN="N"/>
	
</logic:empty>
<logic:notEmpty name="frmOnlinePreAuth" property="preAuthNo">
	<ttk:OnlineDiagnosisDetails flow="PAT" flag="PATCreate"/>
</logic:notEmpty>


		<br>
		<br><br>
		<br>
		<br>
		<br>
<logic:empty name="frmOnlinePreAuth" property="preAuthNo"> 		<!-- Once PreAuth Submitted show only Form Data -->
	    <span class="fieldGroupTabHeader">  &nbsp;&nbsp;Treatment Details &nbsp;&nbsp;
	    </span>
	<logic:notEqual value="true" name="readonly">
	    <a href="#" accesskey="a" onClick="javascript:addActivityDetails();"><img src="/ttk/images/AddIcon.gif" ALT="Add Activity Details" width="13" height="13" border="0" align="absmiddle"></a> 
	   <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0" style="width: 99%">
	    <tr>
	        <td width="10%" class="formLabel">Activity Description </td>
		        <td width="30%">
		        <div id="divParent">
					: <html:textarea property="activityDetailsVO.activityCodeDesc" styleId="activityCodeDesc" styleClass="textBox textAreaMedium" onkeyup="onActivityDescSearch()" />
			     	<div id="activityDescDivID" style="display:none;"></div>
			    </div>
			     	</td>
			     	
	     	<td width="10%" class="formLabel">Activity Code </td>
	             <td width="25%">
	             <div id="divParent">
	       			: <html:text property="activityDetailsVO.activityCode" styleId="activityCode" styleClass="textBox textBoxSmall" onkeyup="onActivityCodeSearch()"/>
			     	 <div id="activityCodeDivID" style="display:none;">
			     	 </div>
			     	<%if("DNTL".equals(benefitTemp) || "Dental".equals(benefitTemp)) {%>
	           		Tooth No.<html:text property="activityDetailsVO.toothNo" styleId="toothNo" styleClass="textBox textBoxTiny"/>
	           		<a href="#" onClick="openListIntX('toothNo','TOOTHNOS')"><img src="/ttk/images/EditIcon.gif" alt="Select Tooth NO" width="16" height="16" border="0" align="absmiddle"></a>
	           	<%} %>
			     </div>
		      </td>
	        <td width="10%" class="formLabel">Internal Code </td>
		        <td width="20%">
		        <div id="divParent">
	       			: <html:text property="activityDetailsVO.internalCode" styleId="internalCode" styleClass="textBox textBoxSmall" onkeyup="onInternalCodeSearch()"/>
			     	 <div id="internalCodeDivID" style="display:none;">
			     	 </div>
			     </div>
			     	</td>
			     	
		    </tr> 
		    <tr>
		        <td width="10%" class="formLabel">Quantity </td>
		        <td width="30%">
					: <html:text property="activityDetailsVO.quantity" styleId="activityQuantity" styleClass="textBox textBoxMedium" value="1"/>
		        </td>
		   </tr>     
	        <tr>    
		        <td colspan="4" align="right"> 
		        	<button type="button" name="Button" accesskey="a" class="olbtnSmall" onClick="javascript:onSaveActivities('activityCodeDesc');"><u>A</u>dd</button>&nbsp;
		        </td>
		    </tr>
		</table>
		</logic:notEqual>
</logic:empty>		
<logic:empty name="frmOnlinePreAuth" property="preAuthNo">
	<ttk:OnlineActivityDetails flow="PAT" preAuthNoYN="N"/>
</logic:empty>
<logic:notEmpty name="frmOnlinePreAuth" property="preAuthNo">
	<ttk:OnlineActivityDetails flow="PAT" flag="PATCreate"/>
</logic:notEmpty>
	<br>
		<br>
		
<logic:empty name="frmOnlinePreAuth" property="preAuthNo"> <!-- Once PreAuth Submitted show only Form Data -->		    
	    <span class="fieldGroupTabHeader">  &nbsp;&nbsp;Drug Details &nbsp;&nbsp;</span>
 <logic:notEqual value="true" name="readonly">
	    <a href="#" accesskey="a" onClick="javascript:addDrugDetails();"><img src="/ttk/images/AddIcon.gif" ALT="Add Activity Details" width="13" height="13" border="0" align="absmiddle"></a>
	    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0" style="width: 99%">
		      <tr>
		        <td width="25%" class="formLabel">QDC Description</td>
			        <td width="35%">
			        <div id="divParent">
					: <html:textarea property="drugDetailsVO.drugDesc" styleId="drugDesc" styleClass="textBox textAreaMedium" onkeyup="onDrugDescSearch()"/> 
			      <div id="drugDescDivID" style="display:none;"></div>
			      </div>
      			</td>
      			
      			<td width="15%" class="formLabel">QDC Code </td>
                        <td class="textLabel" width="25%">
                        <div id="divParent">
			        	: <html:text property="drugDetailsVO.drugCode" styleId="drugCode" styleClass="textBox textBoxMedium" onkeyup="onDrugCodeSearch()"/>
					    <div id="drugCodeDivID" style="display:none;"></div>
					</div>
				</td>
						
		    </tr>
		    
		    <tr>
       			<td width="25%" class="formLabel">Days </td>
		        <td width="35%"> 
		        	: <html:text property="drugDetailsVO.days" styleId="drugdays" styleClass="textBox textBoxSmall" onblur="calcQtyOnPosology()"/><!-- caclQty -->
		        </td>
		    	<td width="15%" class="formLabel">Type of Unit </td>
		        <td width="25%"> 
		        	: <html:select property ="drugDetailsVO.drugunit" styleId="drugUnit" styleClass="selectBox selectBoxMedium" onchange="calcQtyOnPosology()"><!-- checkQty(this) -->
      					<html:option value="LSE">Unit</html:option>
      					<html:option value="PKG">Package</html:option>
       				</html:select>
       			</td>
		    </tr>
		    <tr>
       			<td width="25%" class="formLabel">Quantity </td>
		        <td width="35%"> 
		        	: <html:text property="drugDetailsVO.drugqty" styleId="drugquantity" styleClass="textBox textBoxSmall" readonly="readonly"/>
		        </td>
		   </tr>
		   <tr>    
		        <td colspan="4" align="right"> 
		        	<button type="button" name="Button" accesskey="a" class="olbtnSmall" onClick="javascript:onSaveDrugs();"><u>A</u>dd</button>&nbsp;
		        </td>
		        
		    </tr>
		</table>
		</logic:notEqual>
	</logic:empty>
<logic:empty name="frmOnlinePreAuth" property="preAuthNo">
	<ttk:OnlineDrugDetails flow="PAT" preAuthNoYN="N"/>
</logic:empty>
<logic:notEmpty name="frmOnlinePreAuth" property="preAuthNo">
	<ttk:OnlineDrugDetails flow="PAT" flag="PATCreate"/>
</logic:notEmpty>	
		<%-- <ttk:OnlineDrugDetails flow="PAT" preAuthNoYN="<bean:write name='frmOnlinePreAuth' property='preAuthNo'/>"/> --%>
		<br>
		
		
	<!-- Shotfall Docs S T A R T S-->
	<logic:notEmpty name="alShortFallList">
		<fieldset>
			<legend>ShortFall details</legend>
			<table align="center" class="gridWithCheckBox"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="gridHeader" align="left">Date of Shortfall</td>
				<td class="gridHeader" align="left">Shortfall No.</td>
				<td class="gridHeader" align="left">Shortfall Type</td>
				<td class="gridHeader" align="left">Status</td>
				<td class="gridHeader" align="left">Reply Received</td>
				<td class="gridHeader" align="left">Remarks</td>
			</tr>
			<% int i=0; %>
				<c:forEach var="alData1" items="${sessionScope.alShortFallList}">
				<%
					String strClass=i%2==0 ? "gridOddRow" : "gridEvenRow" ;
					i++;
				%>
		      <tr class=<%=strClass%>>
		         <c:forEach var="strD" items="${pageScope.alData1}" varStatus="theCount">
		         <td height="20px">
		            <c:set var="test" value="${theCount.index}"/>
		            <%if(!pageContext.getAttribute("test").toString().equals("6")){%>
			            <%if(pageContext.getAttribute("test").toString().equals("1")){%>
			            <a href="#" onclick="javascript:onViewShortfall('<c:out value="${pageScope.alData1[6]}"/>')"><c:out value="${strD}"/></a>
			            <% }else{%>
			              <c:out value="${strD}"/>
			            <% }%>
		            <% }%>
		         </td>
		         </c:forEach>
		     </tr>
		        </c:forEach>
		  </table>
		</fieldset>	
	</logic:notEmpty>
<%} %>
    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    <%if(savePartialPreAuthFlag){ %>
    	<BR>
		<fieldset>
		<legend>Document Uploads<span class="mandatorySymbol">*</span></legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0" style="width: 99%">
			<tr  style="border:1px solid;">
          		<td width="19%"    align="left" nowrap>&nbsp;&nbsp;Description:</td>
          		<td width="18%"    align="left" nowrap>
          			<html:select property ="description" styleClass="selectBox selectBoxMedium">
          					<html:option value="">--Select from list--</html:option>
                 			<html:options collection="provDocsType" property="cacheId" labelProperty="cacheDesc"/>
          			</html:select>
          		</td>
          	          
            <td align="left">Browse File : </td>
			<td>
				<html:file property="file" styleId="file"/>
			</td>
			</tr>
			  </table>
		</fieldset>	
 <%} %>
	
	
 <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
   	<tr>
        <td width="100%" align="center">
        <logic:empty name="frmOnlinePreAuth" property="preAuthRefNo"><!-- IF preAuth number generated -->
			<%if(savePartialPreAuthFlag){ %>
			<button type="button" name="Button" accesskey="s" class="olbtnSmall" onClick="javascript:onSavePartialPreAuth();"><u>S</u>ave</button>&nbsp;&nbsp;&nbsp;
			<%} %>
			<button type="button" name="Button" accesskey="b" class="olbtnSmall" onClick="javascript:onBack();"><u>B</u>ack</button>&nbsp;&nbsp;&nbsp;
		</logic:empty>
		
	<logic:notEmpty name="frmOnlinePreAuth" property="preAuthRefNo">		
			<button type="button" name="Button" accesskey="x" class="olbtnSmall" onClick="javascript:onExit();">E<u>x</u>it</button>&nbsp;&nbsp;&nbsp;
	</logic:notEmpty>
		
		       
		 </td>
   	</tr>
</table>
   	<table width="98%" align="center"  border="0" cellspacing="0" cellpadding="0" >
   	<tr>
   	</tr>
   		<tr>
   			<td width="20%" class="formLabel">NOTE: It is mandatory to upload Pre-Approval Form for submitting the pre-approval details.</td>
       				<td width="30%" class="textLabelBold">
       				</td>	
   		</tr>
   	</table>
<br><br>
		<!-- E N D : Buttons -->
		<%-- </logic:notEmpty> --%>
	<input type="hidden" name="mode" value="">
	<INPUT TYPE="hidden" NAME="leftlink">
	<INPUT TYPE="hidden" NAME="sublink">
	<INPUT TYPE="hidden" NAME="tab">
	<INPUT TYPE="hidden" NAME="rownum">
	<html:hidden property="diagCodeSeqID" styleId="diagCodeSeqID" name="frmOnlinePreAuth"/>
	<html:hidden property="preAuthRefNo" styleId="preAuthRefNo" name="frmOnlinePreAuth"/>
	<INPUT TYPE="hidden" NAME="preAuthNoYN" value="<bean:write name="frmOnlinePreAuth" property="preAuthNo"/> ">
	<input type="hidden" name="gran" id="gran" value="<%= request.getAttribute("gran")%>">
	<input type="hidden" name="focusObj" value="<%= request.getAttribute("focusObj")%>">
	<input type="hidden" name="lPreAuthIntSeqId" value="<%= request.getSession().getAttribute("lPreAuthIntSeqId")%>">
	<input type="hidden" name="fromFlag" value="<%=request.getSession().getAttribute("fromFlag")%>">
	<input type="hidden" name="hospMailId" value="<%=request.getSession().getAttribute("hospMailId")%>">
	<input type="hidden" name="hospSeqID" id="hospSeqID" value="<%=request.getSession().getAttribute("hospSeqID")%>">
	<html:hidden property="benifitTypeCode" name="frmOnlinePreAuth"/>
	<html:hidden property="encounterType"/>
	
	<script type="text/javascript">
	callFocusObj();
	</script>
	<br><br><br>
	</div>
<script type="text/javascript">
changeTreatment(document.forms[1].treatmentType,'<bean:write name="frmOnlinePreAuth" property="activityDetailsVO.toothNoReqYN"/>');
</script>	
	</body>
</html:form>