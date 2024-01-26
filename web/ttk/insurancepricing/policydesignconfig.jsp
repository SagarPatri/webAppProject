
<%
/**
 * @ (#)  bajaj separation 1274A
 * Reason        :  
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/insurancepricing/policydesignconfig.js"></script>
	<%
  boolean viewmode=true;
	pageContext.setAttribute("roomType",Cache.getCacheObject("roomTypeInpatient"));
	    pageContext.setAttribute("outPatientCoverageGroup",Cache.getCacheObject("outPatientCoverageGroup"));
	    pageContext.setAttribute("systemOfMedicineGroup",Cache.getCacheObject("systemOfMedicineGroup"));
  %>
<!-- S T A R T : Content/Form Area -->
<html:form action="/PlanDesignConfigurationAction.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0"	cellpadding="0">
		<tr>
			<td>Plan Design<bean:write
				name="frmPolicyConfig" property="caption" /></td>
			<td align="right"></td>
			<td align="right"></td>
		</tr>
	</table>

	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors />
	 <!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display: " border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
					width="16" height="16" align="absmiddle">&nbsp; 
					<bean:message name="updated" scope="request" /></td>
			</tr>
		</table>
	</logic:notEmpty> <!-- E N D : Success Box --> <!-- S T A R T : Form Fields -->
	
<!-- GROUP LEVEL FEATURES -->
<logic:match name="frmPolicyConfig" property="Message" value="N">

			<fieldset>
                            <legend>Group Level Features</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel"><b>Applicable</b> 
	        		         <html:checkbox name="frmPolicyConfig" property="groupLevelYN"  value="Y" styleId="groupLevelYN" onclick="showHideDetails('GroupLevel');" />
	 					      <input type="hidden" name="groupLevelYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        
	        		          <logic:match name="frmPolicyConfig" property="groupLevelYN" value="Y" >
		                      <tr id="allowedGroupLevel" style="display: ">
			                   <td class="formLabel" width="25%">Initial Waiting Period (No of days) </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="initialWatiingPeriod"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
			                         <html:option value="180">180</html:option>
				                     <html:option value="90">90</html:option>
				                     <html:option value="60">60</html:option>
				                     <html:option value="30">30</html:option> 
			                         </html:select>
			                    </td>
			                    
			                    <td class="formLabel" width="25%">Non Network Reimbursement Copay %  </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="nonNetworkRemCopayGroup"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="40">Not Covered</html:option>
				                    <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     <html:option value="30">30%</html:option>
				                     
			                         </html:select>
			                    </td>
			                    </tr>
			                    
			                    <tr id="allowedGroupLevel1" style="display: ">
			                   <td class="formLabel" width="25%">Elective care outside Area of Cover </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="electiveOutsideCover"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="SA">South Asia</html:option>
				                     <html:option value="SEA">South East Asia</html:option> 
			                         </html:select>
			                    </td>
			                    
			                    <td class="formLabel" width="25%">Elective Care outside Area of Cover - No of days limit </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:text property="electiveOutsideCoverDays" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr>
			                    
			                     <tr id="allowedGroupLevel2" style="display: ">
			                    <td class="formLabel" width="25%">Transportation overseas AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="transportaionOverseasLimit" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                     <td class="formLabel" width="25%">Repatriation of remains - AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="repatriationRemainsLimit" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedGroupLevel3" style="display: ">
			                   <td class="formLabel" width="25%">Work related accidents coverage </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="specialistConsultationReferal"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option> 
				                     <html:option value="COV">Covered</html:option>
			                         </html:select>
			                    </td> 
			                    <td class="formLabel" width="25%">Emergency Evacuation AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="emergencyEvalAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedGroupLevel4" style="display: ">
			                   <td class="formLabel" width="25%">International Medical Assistance </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="internationalMedicalAssis"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option> 
				                     <html:option value="COV">Covered</html:option>
			                         </html:select>
			                    </td> 
			                    <td class="formLabel" width="25%">Specialist consultations by referral only </td>
			                   <td class="textLabelBold" width="25%"> 
			                          <html:select property="specialistConsultationReferalGroup"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="APPL">Applicable</html:option> 
				                     <html:option value="NOTAPPL">Not Applicable</html:option> 
				                     
				                      </html:select>
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedGroupLevel5" style="display: ">
			                   <td class="formLabel" width="25%">Per Life AML </td>
			                  <td class="textLabelBold" width="25%">  
			                          <html:text property="perLifeAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    <td class="formLabel" width="25%">Outpatient Coverage </td>
			                   <td class="textLabelBold" width="25%"> 
			                          <html:select property="outpatientCoverage"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
			                          <html:option value="ICO">In Clinics Only</html:option>
				                      <html:option value="IHO">In Hospitals Only</html:option>
				                     <html:option value="BCH">Both Clinics And Hospital</html:option>
				                     <html:option value="NOC">Not Covered</html:option>
			                          
				                      </html:select>
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedGroupLevel6" style="display: ">
			                   <td class="formLabel" width="25%">Maternity Copay</td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="maternityCopayGroup"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                      <html:option value="30">30%</html:option>
				                      <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>

			                         </html:select>
			                          &nbsp;&nbsp;
				                     <html:text property="maternityCopayGroupNumeric" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    <td class="formLabel" width="25%">Ground Transportation service within UAE  - Copay &/Deductible </td>
			                   <td class="textLabelBold" width="25%"> 
			                          <html:select property="groundTransportaionPerc"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>	                    
				                     
				                      </html:select>
				                      &nbsp;&nbsp;
				                     <html:text property="groundTransportaionNumeric" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                   </logic:match>
			                   
			                   
			                   
			                    <logic:notMatch name="frmPolicyConfig" property="groupLevelYN" value="Y" >
		                       <tr id="allowedGroupLevel" style="display: none ">
			                   <td class="formLabel" width="25%">Initial Waiting Period (No of days) </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="initialWatiingPeriod"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="180">180</html:option> 
				                     <html:option value="90">90</html:option>
				                     <html:option value="60">60</html:option>
				                     <html:option value="30">30</html:option> 
			                         </html:select>
			                    </td>
			                    
			                    <td class="formLabel" width="25%">Non Network Reimbursement Copay %  </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="nonNetworkRemCopayGroup"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="40">Not Covered</html:option>
				                    <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     <html:option value="30">30%</html:option>
				                     
			                         </html:select>
			                    </td>
			                    </tr>
			                    
			                    <tr id="allowedGroupLevel1" style="display: none">
			                   <td class="formLabel" width="25%">Elective care outside Area of Cover </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="electiveOutsideCover"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="SA">South Asia</html:option>
				                     <html:option value="SEA">South East Asia</html:option> 
			                         </html:select>
			                    </td>
			                    
			                    <td class="formLabel" width="25%">Elective Care outside Area of Cover - No of days limit </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:text property="electiveOutsideCoverDays" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr>
			                    
			                     <tr id="allowedGroupLevel2" style="display: none">
			                    <td class="formLabel" width="25%">Transportation overseas AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="transportaionOverseasLimit" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                     <td class="formLabel" width="25%">Repatriation of remains - AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="repatriationRemainsLimit" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedGroupLevel3" style="display: none">
			                   <td class="formLabel" width="25%">Work related accidents coverage </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="specialistConsultationReferal"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option> 
				                     <html:option value="COV">Covered</html:option>
			                         </html:select>
			                    </td> 
			                    <td class="formLabel" width="25%">Emergency Evacuation AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="emergencyEvalAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedGroupLevel4" style="display: none">
			                   <td class="formLabel" width="25%">International Medical Assistance </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="internationalMedicalAssis"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option> 
				                     <html:option value="COV">Covered</html:option>
			                         </html:select>
			                    </td> 
			                    <td class="formLabel" width="25%">Specialist consultations by referral only </td>
			                   <td class="textLabelBold" width="25%"> 
			                          <html:select property="specialistConsultationReferalGroup"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="APPL">Applicable</html:option> 
				                     <html:option value="NOTAPPL">Not Applicable</html:option> 
				                      </html:select>
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedGroupLevel5" style="display: none">
			                   <td class="formLabel" width="25%">Per Life AML </td>
			                  <td class="textLabelBold" width="25%">  
			                          <html:text property="perLifeAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    <td class="formLabel" width="25%">Outpatient Coverage </td>
			                   <td class="textLabelBold" width="25%"> 
			                          <html:select property="outpatientCoverage"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                      <html:option value="ICO">In Clinics Only</html:option>
				                      <html:option value="IHO">In Hospitals Only</html:option>
				                     <html:option value="BCH">Both Clinics And Hospital</html:option>
				                     <html:option value="NOC">Not Covered</html:option>
			                          
				                      </html:select>
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedGroupLevel6" style="display: none">
			                   <td class="formLabel" width="25%">Maternity Copay</td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="maternityCopayGroup"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                      <html:option value="30">30%</html:option>
				                      <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
        
			                         </html:select>
			                         &nbsp;&nbsp;
				                     <html:text property="maternityCopayGroupNumeric" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    <td class="formLabel" width="25%">Ground Transportation service within UAE  - Copay &/Deductible  </td>
			                   <td class="textLabelBold" width="25%"> 
			                          <html:select property="groundTransportaionPerc"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                      <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                   				                     
				                      </html:select>
				                      &nbsp;&nbsp;
				                     <html:text property="groundTransportaionNumeric" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                   </logic:notMatch> 
		  	                </table>
                  </fieldset>
                  
	
<!-- INPATIENT -->
          	<fieldset>
                            <legend>InPatient</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel"><b>Applicable </b>
	        		         <html:checkbox name="frmPolicyConfig" property="insPatYN"  value="Y" styleId="insPatYN" onclick="showHideDetails('Inpatient');" />
	 					      <input type="hidden" name="insPatYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        <!-- added for bajaj enhancement1 -->
	        		          <logic:match name="frmPolicyConfig" property="insPatYN" value="Y" >
	        		          <%-- <tr id="allowedInpat6" style="display: ">
	        		          <td class="formLabel" width="25%">IP coverage </td>
			                     	<td class="textLabelBold" width="25%"><html:select property="inpatientcoverage"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    </td>
			                    </tr> --%>
		                      <tr id="allowedInpat" style="display: ">
			                   <td class="formLabel" width="25%">IP Annual Max Limit (AML) </td>
			                   	<td class="textLabelBold" width="25%">	
			                   		<html:text property="annualMaxLimit" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    <td class="formLabel" width="25%">Room Type </td>
			                  <td class="textLabelBold" width="25%"> 
			                  		<html:select property="roomType"   name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                      <html:option value="WRD">Ward</html:option>
				                     <html:option value="SMI">Semi Private</html:option>
				                     <html:option value="PRV">Private</html:option> 
			                         </html:select>
			                    </td>
			                    </tr>
			                    <tr id="allowedInpat1" style="display: ">
			                    <td class="formLabel" width="25%">IP Copay % </td>
			                   <td class="textLabelBold" width="25%"><html:select property="copay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                    </td>
			                    <td class="formLabel" width="25%">Companion benefit </td>
			                   <td class="textLabelBold" width="25%"><html:select property="companionBenefit"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxLarge">
				                     <html:option value="NOTCOV">Not Covered</html:option> 
				                     <html:option value="COV">Covered upto age 18</html:option> 
			                         </html:select>
			                    </td>
			                    </tr>
			                    
			                     <tr id="allowedInpat2" style="display: ">
			                     <td class="formLabel" width="25%">Companion benefit AML per night</td> 
			                      <td class="textLabelBold" width="25%">  <html:text property="companionBenefitAMl" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    <td class="formLabel" width="25%">Cash Benefit </td>
			                     	<td class="textLabelBold" width="25%"><html:select property="inpatientCashBenefit"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    </td>
			                    </tr>
			                    <tr id="allowedInpat3" style="display: "> 
			                     <td class="formLabel" width="25%">Cash benefit AML </td> 
			                      <td class="textLabelBold" width="25%">  <html:text property="cashBenefitAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr> 
			                    
			                    <%-- <tr id="allowedInpat4" style="display: ">
			                   
			                    <td class="formLabel" width="25%">Emergency Dental </td>
			                     <td class="textLabelBold" width="25%">	<html:text property="emergencyDental" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                     <td class="formLabel" width="25%">Emergency Maternity </td>
			                      <td class="textLabelBold" width="25%">  <html:text property="emergencyMaternity" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr> --%>
			                    
			                    <tr id="allowedInpat5" style="display: ">
			                   
			                    <td class="formLabel" width="25%">Ambulance </td>
			                     	<td class="textLabelBold" width="25%"><html:select property="ambulance"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    </td>
			                     <td class="formLabel" width="25%">ICU Coverage</td>
			                     	<td class="textLabelBold" width="25%"><html:select property="inpatientICU"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    </td>
			                    </tr> 
			                    <tr id="allowedInpat4" style="display: ">
			                   
			                    <td class="formLabel" width="25%">ICU AML </td>
			                     <td class="textLabelBold" width="25%">	<html:text property="inpatientICUAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                     
			                    </tr>
			                   </logic:match>
			                    
			                   
			                    <logic:notMatch name="frmPolicyConfig" property="insPatYN" value="Y" >
			                    <%-- <tr id="allowedInpat6" style="display: none ">
	        		          <td class="formLabel" width="25%">IP coverage </td>
			                     	<td class="textLabelBold" width="25%"><html:select property="inpatientcoverage"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    </td>
			                    </tr> --%>
		                       <tr id="allowedInpat" style="display: none ">
			                   <td class="formLabel" width="25%">IP Annual Max Limit (AML) </td>
			                   	<td class="textLabelBold" width="25%">	
			                   		<html:text property="annualMaxLimit" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    <td class="formLabel" width="25%">Room Type </td>
			                  <td class="textLabelBold" width="25%"> <html:select property="roomType"   name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                      <html:option value="WRD">Ward</html:option>
				                     <html:option value="SMI">Semi Private</html:option>
				                     <html:option value="PRV">Private</html:option> 
			                         </html:select>
			                    </td>
			                    </tr>
			                    <tr id="allowedInpat1" style="display: none">
			                    <td class="formLabel" width="25%">IP Copay % </td>
			                   <td class="textLabelBold" width="25%"><html:select property="copay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                    </td>
			                    <td class="formLabel" width="25%">Companion benefit </td>
			                   <td class="textLabelBold" width="25%"><html:select property="companionBenefit"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxLarge">
				                     <html:option value="NOTCOV">Not Covered</html:option> 
				                     <html:option value="COV">Covered upto age 18</html:option> 
			                         </html:select>
			                    </td>
			                    </tr>
			                    
			                     <tr id="allowedInpat2" style="display: none">
			                     <td class="formLabel" width="25%">Companion benefit AML per night</td> 
			                      <td class="textLabelBold" width="25%">  <html:text property="companionBenefitAMl" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    <td class="formLabel" width="25%">Cash Benefit </td>
			                     	<td class="textLabelBold" width="25%"><html:select property="inpatientCashBenefit"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    </td>
			                    </tr>
			                    <tr id="allowedInpat3" style="display: none"> 
			                     <td class="formLabel" width="25%">Cash benefit AML </td> 
			                      <td class="textLabelBold" width="25%">  <html:text property="cashBenefitAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr> 
			                    
			                    <%-- <tr id="allowedInpat4" style="display: none">
			                   
			                    <td class="formLabel" width="25%">Emergency Dental </td>
			                     <td class="textLabelBold" width="25%">	<html:text property="emergencyDental" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                     <td class="formLabel" width="25%">Emergency Maternity </td>
			                      <td class="textLabelBold" width="25%">  <html:text property="emergencyMaternity" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr> --%>
			                    
			                    <tr id="allowedInpat5" style="display: none">
			                   
			                    <td class="formLabel" width="25%">Ambulance </td>
			                     	<td class="textLabelBold" width="25%"><html:select property="ambulance"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    </td>
			                     <td class="formLabel" width="25%">ICU Coverage </td>
			                     	<td class="textLabelBold" width="25%"><html:select property="inpatientICU"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    </td>
			                    </tr> 
			                    <tr id="allowedInpat4" style="display: none">
			                   
			                    <td class="formLabel" width="25%">ICU AML </td>
			                     <td class="textLabelBold" width="25%">	<html:text property="inpatientICUAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                     
			                    </tr>
			                   </logic:notMatch> 
		  	                </table>
                  </fieldset>
                  
                  
<!-- PHARMACY -->
                  <fieldset>
                            <legend>Pharmacy</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel"><b>Applicable </b>
	        		         <html:checkbox name="frmPolicyConfig" property="pharmacyYN"  value="Y" styleId="pharmacyYN" onclick="showHideDetails('Pharmacy');" />
	 					      <input type="hidden" name="pharmacyYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        <!-- added for bajaj enhancement1 -->
	        		          <logic:match name="frmPolicyConfig" property="pharmacyYN" value="Y" >
		                      <tr id="allowedPharmacy" style="display: ">
			                   <td class="formLabel" width="25%">Copay % </td>
			                   <td class="textLabelBold" width="25%"><html:select property="copaypharmacy"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                    </td> 
			                    <td class="formLabel" width="25%">Pharmacy AML </td>
			                     <td class="textLabelBold" width="25%">	<html:text property="amlPharmacy" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr>
			                    
			                    <%-- <tr id="allowedPharmacy1" style="display: ">
			                   <td class="formLabel" width="25%">Chronic AML </td>
			                   <td class="textLabelBold" width="25%">
			                    	<html:text property="chronicAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    <td class="formLabel" width="25%">Chronic Pharmacy Copay </td>
			                   <td class="textLabelBold" width="25%"><html:select property="chronicPharmacyCopayPerc"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                         
			                         &nbsp;&nbsp;
			                         <html:text property="chronicPharmacyCopayNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr> --%>
			                     <tr id="allowedPharmacy2" style="display: ">
			                    <td class="formLabel" width="25%">Preauth Limit (VIP) - Amount per claim </td>
			                     <td class="textLabelBold" width="25%">	<html:text property="preauthLimitVIP" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr>
			                    
			                    <tr id="allowedPharmacy3" style="display: ">
			                   <td class="formLabel" width="25%">Non Network Reimbursements Copay % </td>
			                   <td class="textLabelBold" width="25%">
			                    	<html:select property="nonNetworkRemCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="20">20%</html:option> 
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     
			                         </html:select>
			                    </td> 
			                    <td class="formLabel" width="25%">Preauth Limit (non VIP) Amount per claim </td>
			                     <td class="textLabelBold" width="25%">	<html:text property="preauthLimitNonVIP"  styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr>
			                   </logic:match> 
			                   
			                    <logic:notMatch name="frmPolicyConfig" property="pharmacyYN" value="Y" >
		                       <tr id="allowedPharmacy" style="display: none ">
			                   <td class="formLabel" width="25%">Copay % </td>
			                   <td class="textLabelBold" width="25%"><html:select property="copaypharmacy"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                    </td> 
			                    <td class="formLabel" width="25%">Pharmacy AML </td>
			                     <td class="textLabelBold" width="25%">	<html:text property="amlPharmacy" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr>
			                    
			                    <%-- <tr id="allowedPharmacy1" style="display: none">
			                   <td class="formLabel" width="25%">Chronic AML </td>
			                   <td class="textLabelBold" width="25%">
			                    	<html:text property="chronicAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    <td class="formLabel" width="25%">Chronic Pharmacy Copay </td>
			                   <td class="textLabelBold" width="25%"><html:select property="chronicPharmacyCopayPerc"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                         
			                         &nbsp;&nbsp;
			                         <html:text property="chronicPharmacyCopayNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr> --%>
			                     <tr id="allowedPharmacy2" style="display: none">
			                    <td class="formLabel" width="25%">Preauth Limit (VIP) - Amount per claim </td>
			                     <td class="textLabelBold" width="25%">	<html:text property="preauthLimitVIP" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr>
			                    
			                    <tr id="allowedPharmacy3" style="display: none ">
			                   <td class="formLabel" width="25%">Non Network Reimbursements Copay % </td>
			                   <td class="textLabelBold" width="25%">
			                    	<html:select property="nonNetworkRemCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="20">20%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                      
			                         </html:select>
			                    </td> 
			                    <td class="formLabel" width="25%">Preauth Limit (non VIP) Amount per claim </td>
			                     <td class="textLabelBold" width="25%">	<html:text property="preauthLimitNonVIP"  styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr>
			                   </logic:notMatch> 
		  	                </table>
                  </fieldset>
                  
                  
                  
<!-- LAB AND DIAGNOSTICS    -->              
                  <fieldset>
                            <legend>Lab and Diagnostics </legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel"><b>Applicable</b>
	        		         <html:checkbox name="frmPolicyConfig" property="labdiagYN"  value="Y" styleId="labdiagYN" onclick="showHideDetails('LabDiagnostics');" />
	 					      <input type="hidden" name="labdiagYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        <!-- added for bajaj enhancement1 -->
	        		          <logic:match name="frmPolicyConfig" property="labdiagYN" value="Y" >
		                      <tr id="allowedLabDiag" style="display: ">
			                   <td class="formLabel" width="25%">Copay % </td>
			                   <td class="textLabelBold" width="25%"><html:select property="copayLab"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Non Network Reimbursements Copay % </td>
			                   <td class="textLabelBold" width="25%"><html:select property="nonNetworkRemCopayLab"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="20">20%</html:option> 
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     
			                         </html:select>
			                    </td>  
			                    </tr>
			                    
			                    <tr id="allowedLabDiag4" style="display: ">
			                 
			                    
			                    <td class="formLabel" width="25%">Labs and diagnostics AML </td>
			                   <td class="textLabelBold" width="25%">	
			                   		<html:text property="labsAndDiagnosticsAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    </tr>
			                    
			                      <tr id="allowedLabDiag1" style="display: ">
			                   <td class="formLabel" width="25%">Oncology Tests </td>
			                   <td class="textLabelBold" width="25%"><html:select property="oncologyTest"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Oncology Tests AML </td>
			                   <td class="textLabelBold" width="25%">	
			                   		<html:text property="oncologyTestAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    </tr>
			                    
			                    <tr id="allowedLabDiag2" style="display: ">
			                   <td class="formLabel" width="25%">Preventive screening for diabetics </td>
			                   <td class="textLabelBold" width="25%">
			                   <html:select property="preventiveScreeningDiabetics"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option>
				                     <html:option value="COVDHA">Covered as per DHA protocol </html:option> 
				                      </html:select>
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Preventive screening for diabetics - Annual limit No of times </td>
			                   <td class="textLabelBold" width="25%">	
			                   		<html:select property="preventiveScreenDiabeticsAnnual"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="ANL">Annually</html:option>
				                     <html:option value="TWI">Twice a Year </html:option>
				                     <html:option value="THR">Thrice a Year </html:option> 
				                      </html:select>
			                    </td> 
			                    
			                    </tr>
			                     <tr id="allowedLabDiag3" style="display: ">
			                   <td class="formLabel" width="25%">Preventive screening for diabetics - age cut off</td>
			                   <td class="textLabelBold" width="25%">
			                   <html:select property="preventiveScreeningDiabeticsAge"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="30">Beyond Age 30</html:option>
				                     <html:option value="40">Beyond Age 40</html:option> 
				                     <html:option value="45">Beyond Age 45</html:option> 
				                      </html:select>
			                    </td> 
			                    </tr>
			                   </logic:match> 
			                   
			                    <logic:notMatch name="frmPolicyConfig" property="labdiagYN" value="Y" >
		                       <tr id="allowedLabDiag" style="display: none ">
			                   <td class="formLabel" width="25%">Copay % </td>
			                   <td class="textLabelBold" width="25%"><html:select property="copayLab"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Non Network Reimbursements Copay % </td>
			                   <td class="textLabelBold" width="25%"><html:select property="nonNetworkRemCopayLab"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="20">20%</html:option> 
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     
			                         </html:select>
			                    </td>  
			                    </tr>
			                    <tr id="allowedLabDiag4" style="display: none">
			                 
			                    
			                    <td class="formLabel" width="25%">Labs and diagnostics AML </td>
			                   <td class="textLabelBold" width="25%">	
			                   		<html:text property="labsAndDiagnosticsAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    </tr>
			                    
			                      <tr id="allowedLabDiag1" style="display: none">
			                   <td class="formLabel" width="25%">Oncology Tests </td>
			                   <td class="textLabelBold" width="25%"><html:select property="oncologyTest"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Oncology Tests AML </td>
			                   <td class="textLabelBold" width="25%">	
			                   		<html:text property="oncologyTestAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    </tr>
			                    <tr id="allowedLabDiag2" style="display: none">
			                   <td class="formLabel" width="25%">Preventive screening for diabetics </td>
			                   <td class="textLabelBold" width="25%">
			                   <html:select property="preventiveScreeningDiabetics"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option>
				                     <html:option value="COVDHA">Covered as per DHA protocol </html:option> 
				                      </html:select>
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Preventive screening for diabetics - Annual limit No of times </td>
			                   <td class="textLabelBold" width="25%">	
			                   		<html:select property="preventiveScreenDiabeticsAnnual"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="ANL">Annually</html:option>
				                     <html:option value="TWI">Twice a Year </html:option>
				                     <html:option value="THR">Thrice a Year </html:option> 
				                      </html:select>
			                    </td> 
			                    
			                    </tr>
			                     <tr id="allowedLabDiag3" style="display: none">
			                   <td class="formLabel" width="25%">Preventive screening for diabetics - age cut off</td>
			                   <td class="textLabelBold" width="25%">
			                   <html:select property="preventiveScreeningDiabeticsAge"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="30">Beyond Age 30</html:option>
				                     <html:option value="40">Beyond Age 40</html:option> 
				                     <html:option value="45">Beyond Age 45</html:option> 
				                      </html:select>
			                    </td> 
			                    </tr>
			                   </logic:notMatch> 
		  	                </table>
                  </fieldset>


<!-- OP CONSULTATIONS  -->                
                   <fieldset>
                            <legend>OP Consultations</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel"><b>Applicable</b>
	        		         <html:checkbox name="frmPolicyConfig" property="opConsultYN"  value="Y" styleId="opConsultYN" onclick="showHideDetails('OPConsultation');" />
	 					      <input type="hidden" name="opConsultYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        
	        		          <logic:match name="frmPolicyConfig" property="opConsultYN" value="Y" >
		                      <tr id="allowedOPConsult" style="display: ">
		                      
		                      <td class="formLabel" width="25%">Consultations AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="consultationAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                   <%-- <td class="formLabel" width="25%">GP Consultations </td>
			                   <td class="textLabelBold" width="25%"><html:select property="gpConsultationList"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                         &nbsp;&nbsp;
			                         <html:text property="gpConsultationNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> --%> 
			                    </tr> 
			                    <tr id="allowedOPConsult1" style="display: ">
			                    <td class="formLabel" width="25%">Consultations - Copay &/Deductible </td>
			                   <td class="textLabelBold" width="25%"><html:select property="opConsultationCopayList"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                         &nbsp;&nbsp;
			                         <html:text property="opConsultationCopayListNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    <td class="formLabel" width="25%">Specialists Consultations - Copay &/Deductible </td>
			                   <td class="textLabelBold" width="25%"><html:select property="specConsultationList"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                         &nbsp;&nbsp;
			                         <html:text property="specConsultationNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedOPConsult2" style="display: ">
			                   <td class="formLabel" width="25%">Physiotherapy consultations - No of sessions </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="physiotherapySession" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Physiotherapy consults - AML Limit </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="physiotherapyAMLLimit" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <%-- <tr id="allowedOPConsult3" style="display: ">
			                   <td class="formLabel" width="25%">Maternity consultations - No of consults </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="noOfmaternityConsults" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Maternity consultations </td>
			                   <td class="textLabelBold" width="25%"> 
			                        <html:select property="maternityConsultations"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                         
			                          &nbsp;&nbsp;
			                         <html:text property="maternityConsultationsNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedOPConsult4" style="display: ">
			                    
			                    <td class="formLabel" width="25%">Chronic Disease - no of consults </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="chronicDiseaseConsults" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                   <td class="formLabel" width="25%">Chronic Disease - AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="chronicDiseaseAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    <tr id="allowedOPConsult5" style="display: ">
			                    <td class="formLabel" width="25%">Chronic Disaese - Copay % & Deductible </td>
			                   <td class="textLabelBold" width="25%"> 
			                        <html:select property="chronicDiseaseCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                         &nbsp;&nbsp;
			                         <html:text property="chronicDiseaseDeductible" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr> --%>
			                   </logic:match>
			                   
			                   
			                    <logic:notMatch name="frmPolicyConfig" property="opConsultYN" value="Y" >
		                       <tr id="allowedOPConsult" style="display: none ">
			                   <td class="formLabel" width="25%">Consultations AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="consultationAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                   <%-- <td class="formLabel" width="25%">GP Consultations </td>
			                   <td class="textLabelBold" width="25%"><html:select property="gpConsultationList"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                         &nbsp;&nbsp;
			                         <html:text property="gpConsultationNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>  --%>
			                    </tr>
			                    <tr id="allowedOPConsult1" style="display: none">
			                    <td class="formLabel" width="25%">Consultations - Copay &/Deductible </td>
			                   <td class="textLabelBold" width="25%"><html:select property="opConsultationCopayList"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                         &nbsp;&nbsp;
			                         <html:text property="opConsultationCopayListNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    <td class="formLabel" width="25%">Specialists Consultations - Copay &/Deductible </td>
			                   <td class="textLabelBold" width="25%"><html:select property="specConsultationList"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                         &nbsp;&nbsp;
			                         <html:text property="specConsultationNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedOPConsult2" style="display:none ">
			                   <td class="formLabel" width="25%">Physiotherapy consultations - No of sessions </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="physiotherapySession" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Physiotherapy consults - AML Limit </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="physiotherapyAMLLimit" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <%-- <tr id="allowedOPConsult3" style="display: none">
			                   <td class="formLabel" width="25%">Maternity consultations - No of consults </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="noOfmaternityConsults" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Maternity consultations </td>
			                   <td class="textLabelBold" width="25%"> 
			                        <html:select property="maternityConsultations"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                         
			                          &nbsp;&nbsp;
			                         <html:text property="maternityConsultationsNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedOPConsult4" style="display: none">
			                    
			                    <td class="formLabel" width="25%">Chronic Disease - no of consults </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="chronicDiseaseConsults" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                   <td class="formLabel" width="25%">Chronic Disease - AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="chronicDiseaseAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    <tr id="allowedOPConsult5" style="display: none">
			                    <td class="formLabel" width="25%">Chronic Disaese - Copay % & Deductible </td>
			                   <td class="textLabelBold" width="25%"> 
			                        <html:select property="chronicDiseaseCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                         &nbsp;&nbsp;
			                         <html:text property="chronicDiseaseDeductible" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr> --%>
			                   </logic:notMatch> 
		  	                </table>
                  </fieldset>
                  
                  
 <!-- MATERNITY -->
                  <fieldset>
                            <legend>Maternity</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel"><b>Applicable </b>
	        		         <html:checkbox name="frmPolicyConfig" property="maternityYN"  value="Y" styleId="maternityYN" onclick="showHideDetails('Maternity');" />
	 					      <input type="hidden" name="maternityYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        
	        		          <logic:match name="frmPolicyConfig" property="maternityYN" value="Y" >
	        		           <tr id="allowedMaternity3" style="display: ">
			                   <td class="formLabel" width="25%">Emergency Maternity AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="emergencyMaternityAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Maternity AnteNatal Tests </td>
			                   <td class="textLabelBold" width="25%"> 
			                        <html:select property="maternityAnteNatalTests"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="DHA">Covered as per DHA protocol</html:option>
				                     <html:option value="COV">Covered</html:option> 
				                     <html:option value="NOTCOV">Not covered</html:option>
				                     
				                     
			                         </html:select>
			                         
			                   
			                    </td> 
			                    </tr>
	        		          
	        		          
	        		          <tr id="allowedMaternity4" style="display: ">
			                   <td class="formLabel" width="25%">Maternity consultations - No of consults </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="noOfmaternityConsults" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Maternity Consultations - Copay &/Deductible </td>
			                   <td class="textLabelBold" width="25%"> 
			                        <html:select property="maternityConsultations"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                         
			                          &nbsp;&nbsp;
			                         <html:text property="maternityConsultationsNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
		                      <tr id="allowedMaternity" style="display: ">
			                  <td class="formLabel" width="25%">Maternity Normal Delivery AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="normalDeliveryAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Maternity C-Section  AML</td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="maternityCsectionAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    
			                    <tr id="allowedMaternity1" style="display: ">
			                  <td class="formLabel" width="25%">Maternity Day 1 coverage   </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:select property="dayCoverage"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
				                      <html:option value="COVDAY">Covered for 30 days</html:option> 
				                    
			                         </html:select>
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Maternity Day 1 coverage - Vaccinations  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:select property="dayCoverageVaccination"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    </td> 
			                    </tr>
			                    <tr id="allowedMaternity2" style="display: ">
			                    <td class="formLabel" width="25%">Maternity complications AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="maternityComplicationAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                   <%--  <tr id="allowedMaternity2" style="display: "> 
			                    
			                    <td class="formLabel" width="25%">Circumcision AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="circumcisionAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr> --%>
			                   </logic:match>
			                   
			                   
			                   
			                    <logic:notMatch name="frmPolicyConfig" property="maternityYN" value="Y" >
			                    <tr id="allowedMaternity3" style="display: none ">
			                   <td class="formLabel" width="25%">Emergency Maternity AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="emergencyMaternityAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Maternity AnteNatal Tests </td>
			                   <td class="textLabelBold" width="25%"> 
			                        <html:select property="maternityAnteNatalTests"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="DHA">Covered as per DHA protocol</html:option>
				                     <html:option value="COV">Covered</html:option> 
				                     <html:option value="NOTCOV">Not covered</html:option>
				                     
				                     
			                         </html:select>
			                         
			                    </td> 
			                    </tr>
	        		          
	        		          
	        		          <tr id="allowedMaternity4" style="display: none ">
			                   <td class="formLabel" width="25%">Maternity consultations - No of consults </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="noOfmaternityConsults" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Maternity Consultations - Copay &/Deductible </td>
			                   <td class="textLabelBold" width="25%"> 
			                        <html:select property="maternityConsultations"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                         
			                          &nbsp;&nbsp;
			                         <html:text property="maternityConsultationsNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
		                       <tr id="allowedMaternity" style="display: none ">
			                   <td class="formLabel" width="25%">Maternity Normal Delivery AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="normalDeliveryAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Maternity C-Section  AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="maternityCsectionAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                   <tr id="allowedMaternity1" style="display: none">
			                  <td class="formLabel" width="25%">Maternity Day 1 coverage   </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:select property="dayCoverage"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
				                      <html:option value="COVDAY">Covered for 30 days</html:option> 
			                         </html:select>
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Maternity Day 1 coverage - Vaccinations  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:select property="dayCoverageVaccination"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    </td> 
			                    </tr>
			                    <tr id="allowedMaternity2" style="display: none">
			                    <td class="formLabel" width="25%">Maternity complications AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="maternityComplicationAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                   <%--  <tr id="allowedMaternity2" style="display: none"> 
			                    
			                    <td class="formLabel" width="25%">Circumcision AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="circumcisionAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr> --%>
			                   </logic:notMatch> 
		  	                </table>
                  </fieldset>
                  
 <!-- DENTAL    -->             
                 
                 <fieldset>
                            <legend>Dental</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel"><b>Applicable </b>
	        		         <html:checkbox name="frmPolicyConfig" property="dentalYN"  value="Y" styleId="dentalYN" onclick="showHideDetails('Dental');" />
	 					      <input type="hidden" name="dentalYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        
	        		          <logic:match name="frmPolicyConfig" property="dentalYN" value="Y" >
	        		          <tr id="allowedDental3" style="display: ">
			                   <td class="formLabel" width="25%">Emergency Dental AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="emergencyDentalAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                   
			                    </tr>
		                      <tr id="allowedDental" style="display: ">
			                   <td class="formLabel" width="25%">Dental AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="dentalAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Dental - Copay &/Deductible </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:select property="dentalCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                         &nbsp;&nbsp;
			                         
			                         <html:text property="dentalDeductible" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedDental1" style="display: ">
			                  <td class="formLabel" width="25%">Dental Cleaning - AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="cleaningAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Dental Orthodontics - AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="orthodonticsAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedDental2" style="display: "> 
			                    
			                    <td class="formLabel" width="25%">Dental - Non Network Reimbursement Copay % </td>
			                   <td class="textLabelBold" width="25%"> 
			                          <html:select property="dentalNonNetworkRem"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="20">20%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                    </td> 
			                    </tr>
			                   </logic:match>
			                   
			                   
			                   
			                   
			                   
			                   
			                   
			                   
			                    <logic:notMatch name="frmPolicyConfig" property="dentalYN" value="Y" >
		                       <tr id="allowedDental3" style="display: none">
			                   <td class="formLabel" width="25%">Emergency Dental AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="emergencyDentalAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                   
			                    </tr>
		                      <tr id="allowedDental" style="display:  none">
			                   <td class="formLabel" width="25%">Dental AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="dentalAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Dental - Copay &/Deductible </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:select property="dentalCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                         &nbsp;&nbsp;
			                         
			                         <html:text property="dentalDeductible" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedDental1" style="display: none">
			                  <td class="formLabel" width="25%">Dental Cleaning - AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="cleaningAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Dental Orthodontics - AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="orthodonticsAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedDental2" style="display: none"> 
			                    
			                    <td class="formLabel" width="25%">Dental - Non Network Reimbursement Copay % </td>
			                   <td class="textLabelBold" width="25%"> 
			                          <html:select property="dentalNonNetworkRem"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="20">20%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                    </td> 
			                    </tr>
			                   </logic:notMatch> 
		  	                </table>
                  </fieldset>
  <!-- OPTICAL -->                  
                  
                  <fieldset>
                            <legend>Optical</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel"><b>Applicable </b>
	        		         <html:checkbox name="frmPolicyConfig" property="opticalYN"  value="Y" styleId="opticalYN" onclick="showHideDetails('Optical');" />
	 					      <input type="hidden" name="opticalYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        
	        		          <logic:match name="frmPolicyConfig" property="opticalYN" value="Y" >
	        		          
	        		          <tr id="allowedOptical2" style="display: ">
			                   <td class="formLabel" width="25%">Optical AML</td>
			                  <td class="textLabelBold" width="25%"> 
			                         <html:text property="opticalAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Emergency - Optical - AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="emergencyOpticalAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
	        		          
		                      <tr id="allowedOptical" style="display: ">
			                   <td class="formLabel" width="25%">Optical - Copay %</td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="opticalCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="20">20%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>				                     
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                    </td>
			                    
			                    <td class="formLabel" width="25%">Optical - Frames and Contact Lenses AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="frameContactLensAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedOptical1" style="display: ">
			                     <td class="formLabel" width="25%">Optical - Consultations - Copay &/Deductible </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="opticalConsulCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>	
				                     <html:option value="20">20%</html:option>			                     
				                     
			                         </html:select>
			                          &nbsp;&nbsp;
			                         <html:text property="opticalConsulAmount" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    <td class="formLabel" width="25%">Optical - Non Network Reimbursement Copay % </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="opticalNonNetworkRem"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="20">20%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>				                     
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                           
			                    </td>
			                    </tr>
			                   </logic:match>
			                   
			                   
			                   
			                    <logic:notMatch name="frmPolicyConfig" property="opticalYN" value="Y" >
		                        <tr id="allowedOptical2" style="display: none">
			                   <td class="formLabel" width="25%">Optical AML</td>
			                  <td class="textLabelBold" width="25%"> 
			                         <html:text property="opticalAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    
			                    <td class="formLabel" width="25%">Emergency - Optical - AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="emergencyOpticalAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
	        		          
		                      <tr id="allowedOptical" style="display: none">
			                   <td class="formLabel" width="25%">Optical - Copay %</td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="opticalCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="20">20%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>				                     
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                    </td>
			                    
			                    <td class="formLabel" width="25%">Optical - Frames and Contact Lenses AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="frameContactLensAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedOptical1" style="display: none">
			                     <td class="formLabel" width="25%">Optical - Consultations - Copay &/Deductible </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="opticalConsulCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>	
				                     <html:option value="20">20%</html:option>			                     
				                     
			                         </html:select>
			                          &nbsp;&nbsp;
			                         <html:text property="opticalConsulAmount" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    <td class="formLabel" width="25%">Optical - Non Network Reimbursement Copay % </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="opticalNonNetworkRem"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="20">20%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>				                     
				                     <html:option value="30">30%</html:option>
			                         </html:select>
			                           
			                    </td>
			                    </tr>
			                   </logic:notMatch> 
		  	                </table>
                  </fieldset>
                  
<!-- CHRONIC -->             
                    <fieldset>
                            <legend>Chronic</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel"><b>Applicable </b>
	        		         <html:checkbox name="frmPolicyConfig" property="chronicYN"  value="Y" styleId="chronicYN" onclick="showHideDetails('Chronic');" />
	 					      <input type="hidden" name="chronicYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        
	        		          <logic:match name="frmPolicyConfig" property="chronicYN" value="Y" >
	        		         <%--  <tr id="allowedChronic4" style="display: ">
	        		          <td class="formLabel" width="25%">Chronic Coverage </td>
			                  		<td class="textLabelBold" width="25%"> 
			                         	<html:select property="chronicCoverage"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    	</td>
 
			                    </tr> --%> 

		                      <tr id="allowedChronic" style="display: ">
			                   		<td class="formLabel" width="25%">Chronic AML  </td>
			                   		<td class="textLabelBold" width="25%"> 
			                         	<html:text property="chronicAMLNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    	</td> 
			                    	<td class="formLabel" width="25%">Chronic - Copay &/Deductible </td>
			                   		<td class="textLabelBold" width="25%"> 
				                         <html:select property="chronicCopayDeductibleCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
					                     <html:option value="30">30%</html:option>
					                     <html:option value="0">0%</html:option>
					                     <html:option value="10">10%</html:option>
					                     <html:option value="20">20%</html:option>
					                     
				                         </html:select>
				                          &nbsp;&nbsp;
				                         <html:text property="chronicCopayDeductibleAmount" styleClass="textBox textBoxSmall" maxlength="13" /> 	
			                    	</td> 
			                    
			                    	
			                    </tr> 
			                    <tr id="allowedChronic1" style="display: ">
			                    <td class="formLabel" width="25%">Chronic Pharmacy AML </td>
			                  		<td class="textLabelBold" width="25%"> 
			                         	<html:text property="pharmacyAML" styleClass="textBox textBoxSmall" maxlength="13" /> 
			                    	</td> 
			                   		<td class="formLabel" width="25%">Chronic Pharmacy - Copay &/Deductible </td>
			                   		<td class="textLabelBold" width="25%"> 
				                         <html:select property="pharmacyAMLCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
					                     <html:option value="30">30%</html:option>
					                     <html:option value="0">0%</html:option>
					                     <html:option value="10">10%</html:option>
					                     <html:option value="20">20%</html:option>
					                     
				                         </html:select>
				                          &nbsp;&nbsp;
				                         <html:text property="pharmacyAMLAmount" styleClass="textBox textBoxSmall" maxlength="13" /> 	
			                    	</td> 
			                   		
			                    </tr>
			                      
			                      <tr id="allowedChronic2" style="display: "> 
			                      <td class="formLabel" width="25%">Chronic Labs and Diagnostics AML </td>
			                   		<td class="textLabelBold" width="25%"> 
			                         	<html:text property="chronicLabDiag" styleClass="textBox textBoxSmall" maxlength="13" /> 
			                      	</td>
			                       		<td class="formLabel" width="25%">Chronics Labs and Diagnostics - Copay &/Deductible </td>
			                   			<td class="textLabelBold" width="25%"> 
					                         <html:select property="chronicLabDiagCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
						                     <html:option value="30">30%</html:option>
						                     <html:option value="0">0%</html:option>
						                     <html:option value="10">10%</html:option>
						                     <html:option value="20">20%</html:option>
						                     
					                         </html:select>
					                         &nbsp;&nbsp;
					                         <html:text property="chronicLabDiagAmount" styleClass="textBox textBoxSmall" maxlength="13" />
			                   			 </td> 
			                    		
			                      </tr>
			                       
			                     <tr id="allowedChronic3" style="display: "> 
			                     <td class="formLabel" width="25%">Chronic Consultations AML  </td>
			                   			<td class="textLabelBold" width="25%"> 
			                         		<html:text property="chronicConsultationAML" styleClass="textBox textBoxSmall" maxlength="13" /> 
			                      		</td>
			                    <td class="formLabel" width="25%">Chronic Consultations - Copay &/Deductible</td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:select property="chronicConsultationCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                          &nbsp;&nbsp;
			                           <html:text property="chronicConsultation" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                   </logic:match>
			                   
			                   
			                    <logic:notMatch name="frmPolicyConfig" property="chronicYN" value="Y" >
		                      <%--  <tr id="allowedChronic4" style="display: none">
	        		          <td class="formLabel" width="25%">Chronic Coverage </td>
			                  		<td class="textLabelBold" width="25%"> 
			                         	<html:select property="chronicCoverage"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="NOTCOV">Not Covered</html:option>
				                     <html:option value="COV">Covered</html:option> 
			                         </html:select>
			                    	</td>
 
			                    </tr>  --%>

		                      <tr id="allowedChronic" style="display: none">
			                   		<td class="formLabel" width="25%">Chronic AML  </td>
			                   		<td class="textLabelBold" width="25%"> 
			                         	<html:text property="chronicAMLNum" styleClass="textBox textBoxSmall" maxlength="13" />
			                    	</td> 
			                    	<td class="formLabel" width="25%">Chronic - Copay &/Deductible </td>
			                   		<td class="textLabelBold" width="25%"> 
				                         <html:select property="chronicCopayDeductibleCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
					                     <html:option value="30">30%</html:option>
					                     <html:option value="0">0%</html:option>
					                     <html:option value="10">10%</html:option>
					                     <html:option value="20">20%</html:option>
					                     
				                         </html:select>
				                          &nbsp;&nbsp;
				                         <html:text property="chronicCopayDeductibleAmount" styleClass="textBox textBoxSmall" maxlength="13" /> 	
			                    	</td> 
			                    
			                    	
			                    </tr> 
			                    <tr id="allowedChronic1" style="display: none">
			                    <td class="formLabel" width="25%">Chronic Pharmacy AML </td>
			                  		<td class="textLabelBold" width="25%"> 
			                         	<html:text property="pharmacyAML" styleClass="textBox textBoxSmall" maxlength="13" /> 
			                    	</td> 
			                   		<td class="formLabel" width="25%">Chronic Pharmacy - Copay &/Deductible </td>
			                   		<td class="textLabelBold" width="25%"> 
				                         <html:select property="pharmacyAMLCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
					                     <html:option value="30">30%</html:option>
					                     <html:option value="0">0%</html:option>
					                     <html:option value="10">10%</html:option>
					                     <html:option value="20">20%</html:option>
					                     
				                         </html:select>
				                          &nbsp;&nbsp;
				                         <html:text property="pharmacyAMLAmount" styleClass="textBox textBoxSmall" maxlength="13" /> 	
			                    	</td> 
			                   		
			                    </tr>
			                      
			                      <tr id="allowedChronic2" style="display: none"> 
			                      <td class="formLabel" width="25%">Chronic Labs and Diagnostics AML </td>
			                   		<td class="textLabelBold" width="25%"> 
			                         	<html:text property="chronicLabDiag" styleClass="textBox textBoxSmall" maxlength="13" /> 
			                      	</td>
			                       		<td class="formLabel" width="25%">Chronics Labs and Diagnostics - Copay &/Deductible </td>
			                   			<td class="textLabelBold" width="25%"> 
					                         <html:select property="chronicLabDiagCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
						                     <html:option value="30">30%</html:option>
						                     <html:option value="0">0%</html:option>
						                     <html:option value="10">10%</html:option>
						                     <html:option value="20">20%</html:option>
						                     
					                         </html:select>
					                         &nbsp;&nbsp;
					                         <html:text property="chronicLabDiagAmount" styleClass="textBox textBoxSmall" maxlength="13" />
			                   			 </td> 
			                    		
			                      </tr>
			                       
			                     <tr id="allowedChronic3" style="display: none"> 
			                     <td class="formLabel" width="25%">Chronic Consultations AML  </td>
			                   			<td class="textLabelBold" width="25%"> 
			                         		<html:text property="chronicConsultationAML" styleClass="textBox textBoxSmall" maxlength="13" /> 
			                      		</td>
			                    <td class="formLabel" width="25%">Chronic Consultations - Copay &/Deductible</td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:select property="chronicConsultationCopay"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                          &nbsp;&nbsp;
			                           <html:text property="chronicConsultation" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                   </logic:notMatch> 
		  	                </table>
                  </fieldset>
                  
<!-- PED -->                 
                   <fieldset>
                            <legend>PED</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel"><b>Applicable</b> 
	        		         <html:checkbox name="frmPolicyConfig" property="pedYN"  value="Y" styleId="pedYN" onclick="showHideDetails('PED');" />
	 					      <input type="hidden" name="pedYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        
	        		          <logic:match name="frmPolicyConfig" property="pedYN" value="Y" >
		                      <tr id="allowedPED" style="display: ">
		                      
		                     <%--  <td class="formLabel" width="25%">PED Coverage  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:select property="coveredPED"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                    <html:option value="COV">Covered</html:option> 
				                    <html:option value="NOTCOV">Not Covered</html:option>
				                     
			                         </html:select>
			                    </td> --%> 
			                    
			                   <td class="formLabel" width="25%">AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="pedAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    <td class="formLabel" width="25%">PED - Copay &/Deductible </td>
			                   <td class="textLabelBold" width="25%">  
			                         <html:select property="pedCopayDeductible"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                         &nbsp;&nbsp;
			                         <html:text property="pedDeductible" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                   <%--  <tr id="allowedPED1" style="display: ">
			                    <td class="formLabel" width="25%">PED - Copay  &/Deductible </td>
			                   <td class="textLabelBold" width="25%">  
			                         <html:select property="pedCopayDeductible"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                         &nbsp;&nbsp;
			                         <html:text property="pedDeductible" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr>  --%>
			                   </logic:match> 
			                   
			                    <logic:notMatch name="frmPolicyConfig" property="pedYN" value="Y" >
		                       <tr id="allowedPED" style="display: none ">
			                   <%--  <td class="formLabel" width="25%">PED Coverage  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:select property="coveredPED"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="COV">Covered</html:option>
				                    <html:option value="NOTCOV">Not Covered</html:option>
				                     
			                         </html:select>
			                    </td>  --%>
			                    
			                   <td class="formLabel" width="25%">AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="pedAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    <td class="formLabel" width="25%">PED - Copay  &/Deductible </td>
			                   <td class="textLabelBold" width="25%">  
			                         <html:select property="pedCopayDeductible"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                         &nbsp;&nbsp;
			                         <html:text property="pedDeductible" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr>
			                   <%--  <tr id="allowedPED1" style="display: none">
			                    <td class="formLabel" width="25%">PED - Copay  &/Deductible </td>
			                   <td class="textLabelBold" width="25%">  
			                         <html:select property="pedCopayDeductible"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                         &nbsp;&nbsp;
			                         <html:text property="pedDeductible" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr>  --%>
			                   </logic:notMatch> 
		  	                </table>
                  </fieldset>
                  
<!-- PSYCHIATRY -->              
                  <fieldset>
                            <legend>Psychiatry</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel"><b>Applicable </b>
	        		         <html:checkbox name="frmPolicyConfig" property="psychiatryYN"  value="Y" styleId="psychiatryYN" onclick="showHideDetails('Psychiatry');" />
	 					      <input type="hidden" name="psychiatryYN" value="N">	
	        		        </td>
	        		        </tr>
	        		         
	        		          <logic:match name="frmPolicyConfig" property="psychiatryYN" value="Y" >
		                      <tr id="allowedPsychiatry" style="display: ">
			                  <td class="formLabel" width="25%">Psychiatry - Inpatient AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="inpatientAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    
			                    <td class="formLabel" width="25%">Psychiatry - Inpatient No of days  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="noofInpatientDays" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedPsychiatry1" style="display: ">
			                  <td class="formLabel" width="25%">Psychiatry - Outpatient AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="outpatientAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    
			                    <td class="formLabel" width="25%">Psychiatry - Outpatient  No of consultations  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="noofOutpatientConsul" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                     <tr id="allowedPsychiatry2" style="display: ">
			                     <td class="formLabel" width="25%">Psychiatry - Copay &/Deductible </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="psychiatryCopayDeduc"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                          &nbsp;&nbsp;
			                         <html:text property="psychiatryDeductible" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr>
			                   </logic:match>
			                   
			                   
			                   
			                   
			                   
			                    <logic:notMatch name="frmPolicyConfig" property="psychiatryYN" value="Y" >
		                       <tr id="allowedPsychiatry" style="display: none ">
			                   <td class="formLabel" width="25%">Psychiatry - Inpatient AML</td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="inpatientAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    
			                    <td class="formLabel" width="25%">Psychiatry - Inpatient No of days  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="noofInpatientDays" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                    <tr id="allowedPsychiatry1" style="display: none">
			                  <td class="formLabel" width="25%">Psychiatry - Outpatient AML </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="outpatientAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    
			                    <td class="formLabel" width="25%">Psychiatry - Outpatient  No of consultations  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="noofOutpatientConsul" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                    
			                     <tr id="allowedPsychiatry2" style="display: none">
			                     <td class="formLabel" width="25%">Psychiatry - Copay &/Deductible </td>
			                  <td class="textLabelBold" width="25%">  
			                         <html:select property="psychiatryCopayDeduc"  name="frmPolicyConfig"  style="width:100px;" styleClass="selectBox selectBoxMedium">
				                     <html:option value="30">30%</html:option>
				                     <html:option value="0">0%</html:option>
				                     <html:option value="10">10%</html:option>
				                     <html:option value="20">20%</html:option>
				                     
			                         </html:select>
			                          &nbsp;&nbsp;
			                         <html:text property="psychiatryDeductible" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td>
			                    </tr>
			                   </logic:notMatch> 
		  	                </table>
                  </fieldset>
                  

                  
                  
 <!-- ALTERNATIVE MEDICINES -->                 
                   <fieldset>
                            <legend>Alternative Medicines</legend>
                            <table align="center" class="formContainer" border="0" cellspacing="0"	cellpadding="0">
		                      <tr>
		                    <td class="formLabel"><b>Applicable </b>
	        		         <html:checkbox name="frmPolicyConfig" property="othersYN"  value="Y" styleId="othersYN" onclick="showHideDetails('Others');" />
	 					      <input type="hidden" name="othersYN" value="N">	
	        		        </td>
	        		        </tr>
	        		        
	        		          <logic:match name="frmPolicyConfig" property="othersYN" value="Y" >
	        		          
		                      <tr id="allowedOthers" style="display: ">
			                   <td class="formLabel" width="25%">Chiropractory </td>
			                   <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="systemOfMedicine"  value="Y"  />
	 					      <input type="hidden" name="systemOfMedicine" value="N">	
	        		        	</td>
			                    
			                    <td class="formLabel" width="25%">Osteopathy </td>
			                    <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="osteopathyMedicine"  value="Y"  />
	 					      <input type="hidden" name="osteopathyMedicine" value="N">	
	        		        	</td> 
			                    </tr>
			                    
			                    <tr id="allowedOthers1" style="display: ">
			                   <td class="formLabel" width="25%">Homeopathy </td>
			                   <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="homeopathyMedicine"  value="Y"  />
	 					      <input type="hidden" name="homeopathyMedicine" value="N">	
	        		        	</td>
			                    
			                    <td class="formLabel" width="25%">Ayurveda </td>
			                    <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="ayurvedaMedicine"  value="Y"  />
	 					      <input type="hidden" name="ayurvedaMedicine" value="N">	
	        		        	</td> 
			                    </tr>
			                    
			                    <tr id="allowedOthers2" style="display: ">
			                   <td class="formLabel" width="25%">Accupuncture </td>
			                   <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="accupunctureMedicine"  value="Y"  />
	 					      <input type="hidden" name="accupunctureMedicine" value="N">	
	        		        	</td>
			                    
			                    <td class="formLabel" width="25%">Naturopathy </td>
			                    <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="naturopathyMedicine"  value="Y"  />
	 					      <input type="hidden" name="naturopathyMedicine" value="N">	
	        		        	</td> 
			                    </tr>
			                    
			                    <tr id="allowedOthers3" style="display: ">
			                   <td class="formLabel" width="25%">Unani </td>
			                   <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="unaniMedicine"  value="Y"  />
	 					      <input type="hidden" name="unaniMedicine" value="N">	
	        		        	</td>
			                    
			                    <td class="formLabel" width="25%">Chinese Medicine </td>
			                    <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="chineseMedicine"  value="Y"  />
	 					      <input type="hidden" name="chineseMedicine" value="N">	
	        		        	</td> 
			                    </tr>
			                    
			                    <tr id="allowedOthers4" style="display: ">
			             
			                    
			                    <td class="formLabel" width="25%">Alternative Medicines AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="alternativeAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                   </logic:match>
			                   
			                   
			                   
			                    <logic:notMatch name="frmPolicyConfig" property="othersYN" value="Y" >
			                   <tr id="allowedOthers" style="display: none">
			                   <td class="formLabel" width="25%">Chiropractory </td>
			                   <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="systemOfMedicine"  value="Y"  />
	 					      <input type="hidden" name="systemOfMedicine" value="N">	
	        		        	</td>
			                    
			                    <td class="formLabel" width="25%">Osteopathy </td>
			                    <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="osteopathyMedicine"  value="Y"  />
	 					      <input type="hidden" name="osteopathyMedicine" value="N">	
	        		        	</td> 
			                    </tr>
			                    
			                    <tr id="allowedOthers1" style="display: none">
			                   <td class="formLabel" width="25%">Homeopathy </td>
			                   <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="homeopathyMedicine"  value="Y"  />
	 					      <input type="hidden" name="homeopathyMedicine" value="N">	
	        		        	</td>
			                    
			                    <td class="formLabel" width="25%">Ayurveda </td>
			                    <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="ayurvedaMedicine"  value="Y"  />
	 					      <input type="hidden" name="ayurvedaMedicine" value="N">	
	        		        	</td> 
			                    </tr>
			                    
			                    <tr id="allowedOthers2" style="display: none">
			                   <td class="formLabel" width="25%">Accupuncture </td>
			                   <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="accupunctureMedicine"  value="Y"  />
	 					      <input type="hidden" name="accupunctureMedicine" value="N">	
	        		        	</td>
			                    
			                    <td class="formLabel" width="25%">Naturopathy </td>
			                    <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="naturopathyMedicine"  value="Y"  />
	 					      <input type="hidden" name="naturopathyMedicine" value="N">	
	        		        	</td> 
			                    </tr>
			                    
			                    <tr id="allowedOthers3" style="display: none">
			                   <td class="formLabel" width="25%">Unani </td>
			                   <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="unaniMedicine"  value="Y"  />
	 					      <input type="hidden" name="unaniMedicine" value="N">	
	        		        	</td>
			                    
			                    <td class="formLabel" width="25%">Chinese Medicine </td>
			                    <td class="textLabelBold" width="25%">
	        		         <html:checkbox name="frmPolicyConfig" property="chineseMedicine"  value="Y"  />
	 					      <input type="hidden" name="chineseMedicine" value="N">	
	        		        	</td> 
			                    </tr>
			                    
			                    <tr id="allowedOthers4" style="display: none">
			             
			                    
			                    <td class="formLabel" width="25%">Alternative Medicines AML  </td>
			                   <td class="textLabelBold" width="25%"> 
			                         <html:text property="alternativeAML" styleClass="textBox textBoxSmall" maxlength="13" />
			                    </td> 
			                    </tr>
			                   </logic:notMatch> 
		  	                </table>
                  </fieldset> 
 
	 
		        
	<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="center">
			 
			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'"	onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
			 <button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClosePlan();"><u>B</u>ack</button>&nbsp;
			 <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerate();"><u>G</u>enerate Quote >></button>&nbsp;
			<!-- <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button> -->
			</td>
			
		</tr>
	</table>
	</logic:match>
	</div>
	<!-- E N D : Buttons -->
	<input type="hidden" name="mode"> 
	<html:hidden property="insClm" /> 
    <html:hidden property="insPat" />  
	<html:hidden property="caption" />  
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->