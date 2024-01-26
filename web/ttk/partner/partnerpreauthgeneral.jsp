
 <%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page
	import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript"
	src="/ttk/scripts/partner/preauthgeneral.js"></script>
<script type="text/javascript"
	src="/ttk/scripts/partner/preauthgeneral-async.js"></script>
<script>
	//jQuery(function(){
	//    $("#icdCodes").autocomplete("/asynchronAction.do?mode=getIcdCodes");

	//});  
	//jQuery(function(){
	//$("#diagnosisDescription").autocomplete("/asynchronAction.do?mode=getDiagnosisDesc");

	//}); 
	function resetMemSeqId() {
		if (!(document.forms[1].validateMemId.value === document.forms[1].memberId.value)) {
			document.forms[1].memberSeqID.value = "";
		}
	}
</script>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getAttribute("focusObj"))%>";
</script>

</head>
<body>

	<%
		boolean viewmode = true;
		boolean bEnabled = false;
		boolean viewmode1 = true;
		String ampm[] = { "AM", "PM" };
		boolean enhancedViewMode = false;
		boolean blnAmmendmentFlow = false;
		if (TTKCommon.isAuthorized(request, "Edit")) {
			viewmode = false;
			viewmode1 = false;
		}//end of if(TTKCommon.isAuthorized(request,"Edit"))

		//pageContext.setAttribute("preauthType",Cache.getCacheObject("preauthType"));
		pageContext.setAttribute("ampm", ampm);
		//pageContext.setAttribute("hospitalizedFor",Cache.getCacheObject("hospitalizedFor"));
		pageContext.setAttribute("preauthPriority",
				Cache.getCacheObject("preauthPriority"));
		//pageContext.setAttribute("officeInfo", Cache.getCacheObject("officeInfo"));
		pageContext.setAttribute("source", Cache.getCacheObject("source"));
		//pageContext.setAttribute("encounterTypes", Cache.getCacheObject("encounterTypes"));
		//pageContext.setAttribute("OutPatientEncounterTypes", Cache.getCacheObject("OutPatientEncounterTypes"));
		pageContext.setAttribute("encounterStartTypes",
				Cache.getCacheObject("encounterStartTypes"));
		pageContext.setAttribute("encounterEndTypes",
				Cache.getCacheObject("encounterEndTypes"));
		pageContext.setAttribute("dentalTreatmentTypes", Cache.getCacheObject("dentalTreatmentTypes"));
		//pageContext.setAttribute("commCode", Cache.getCacheObject("commCode"));

		//pageContext.setAttribute("conflict", Cache.getCacheObject("conflict"));
		//pageContext.setAttribute("relationshipCode", Cache.getCacheObject("relationshipCode"));
		//pageContext.setAttribute("receivedFrom", Cache.getCacheObject("receivedFrom"));
		//pageContext.setAttribute("claimSubType", Cache.getCacheObject("claimSubType"));
		pageContext.setAttribute("preauthStatusList",
				Cache.getCacheObject("preauthStatusList"));
		pageContext.setAttribute("systemOfMedicines",
				Cache.getCacheObject("systemOfMedicines"));
		pageContext.setAttribute("accidentRelatedCases",
				Cache.getCacheObject("accidentRelatedCases"));
		pageContext.setAttribute("benefitTypes",Cache.getCacheObject("prebenefitTypes"));
		//pageContext.setAttribute("stateCodeList",Cache.getCacheObject("stateCode"));
		//pageContext.setAttribute("cityCodeList",Cache.getCacheObject("cityCode"));
		pageContext.setAttribute("countryCodeList",
				Cache.getCacheObject("countryCode"));
		pageContext.setAttribute("natureOfConception",	Cache.getCacheObject("natureOfConception"));
		pageContext.setAttribute("modeofdelivery",	Cache.getCacheObject("modeofdelivery"));

		//OPD_4_hosptial
		//pageContext.setAttribute("paymentType", Cache.getCacheObject("PaymentType"));
		//OPD_4_hosptial
		//added as per kOC 1285 Change Request  
		//pageContext.setAttribute("domicilaryReasonList", Cache.getCacheObject("domHospReason"));//1285 change request
		//added as per kOC 1285 Change Request
		pageContext.setAttribute("viewmode", new Boolean(viewmode));
		boolean netWorkStatus = true;
	%>
	<logic:equal value="ENHANCEMENT" property="preAuthNoStatus"
		name="frmPreAuthGeneral">
		<%
			enhancedViewMode = true;
		%>
	</logic:equal>
	<html:form action="/PartnerPreAuthGeneralAction.do">
		<!-- S T A R T : Page Title -->
		<table align="center" class="pageTitle" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td width="57%">General Details - <bean:write
						name="frmPreAuthGeneral" property="caption" /></td>
				<td align="right" class="webBoard">&nbsp; <logic:notEmpty
						name="frmPreAuthGeneral" property="partnerPreAuthSeqId">
						<%@ include file="/ttk/common/toolbar.jsp"%>
					</logic:notEmpty>
				</td>
			</tr>
		</table>


<div id="dialogoverlay"></div>
<div id="dialogbox">
  <div>
    <div id="dialogboxhead"></div>
    <div id="dialogboxbody"></div>
    <div id="dialogboxfoot"></div>
  </div>
</div>


		<!-- E N D : Page Title -->

		<div class="contentArea" id="contentArea">
			<html:errors />
			<logic:notEmpty name="errorMsg" scope="request">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="errorMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>

			<!-- S T A R T : Success Box -->
			<logic:notEmpty name="updated" scope="request">
				<table align="center" class="successContainer" style="display:"
					border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:message
								name="updated" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			<logic:notEmpty name="clinicianStatus" scope="request">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="clinicianStatus" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			<logic:notEmpty name="frmPreAuthGeneral"
				property="duplicatePreauthAlert">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Warning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmPreAuthGeneral" property="duplicatePreauthAlert" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			
			

			<logic:notEmpty name="successMsg" scope="request">
				<table align="center" class="successContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="successMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			<!-- S T A R T : Form Fields -->
			<fieldset>
				<legend>PreApproval Details</legend>
				<table align="center" class="formContainer" border="0"
					cellspacing="0" cellpadding="0">
					
					<tr>
						<td width="22%" class="formLabel">Partner Reference No.:</td>
						<td width="30%" class="textLabelBold"><logic:notEmpty
								name="frmPreAuthGeneral" property="preauthPartnerRefId">
								<bean:write name="frmPreAuthGeneral" property="preauthPartnerRefId" />
			    </logic:notEmpty></td>
			    	<td class="formLabel">Member ID:<span class="mandatorySymbol">*</span></td>
						 <td class="textLabel" style="margin-left:10PX;"> 
							<table>
								<tr>
									<td colspan="2"><div id="memberIdResult1">
											<div id="memberIdResult2"></div>
										</div></td>
								</tr>
								<tr>
									<td><html:text property="memberId" styleId="memberId"
											styleClass="textBox textBoxLarge"
											style="width:200px;height:16px;" maxlength="60" readonly="true"
											disabled="<%=enhancedViewMode%>" /></td>
									<td><!-- <a href="#" accesskey="g"
												onClick="javascript:selectMember()" class="search"> <img
												src="/ttk/images/EditIcon.gif" alt="Select Member"
												width="16" height="16" border="0" align="absmiddle">&nbsp;
											</a> -->
											<!-- <a href="#" accesskey="g"
												onClick="javascript:deleteMember()" class="search"> <img
												src="/ttk/images/DeleteIcon.gif" alt="Delete Member"
												width="16" height="16" border="0" align="absmiddle">&nbsp;
											</a> -->
									</td>
								</tr>
							</table> 
							</td>
					</tr>
					<tr>
						<td width="22%" class="formLabel">PreApproval Request Received Date:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabelBold">
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td><html:text name="frmPreAuthGeneral"
											property="receiveDate" styleClass="textBox textDate"
											maxlength="10" disabled="<%=viewmode%>"
											readonly="<%=viewmode%>" />
										<logic:match name="viewmode" value="false">
											<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#"
												onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.receiveDate',document.frmPreAuthGeneral.receiveDate.value,'',event,148,178);return false;"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar"
												name="empDate" width="24" height="17" border="0"
												align="absmiddle"></a>
										</logic:match>&nbsp;</td>
									 <td><html:text name="frmPreAuthGeneral"
											property="receiveTime" styleClass="textBox textTime"
											maxlength="5" disabled="<%=viewmode%>"
											readonly="<%=viewmode%>" />&nbsp;</td> 
									<td><html:select name="frmPreAuthGeneral"
											property="receiveDay" styleClass="selectBox"
											disabled="<%=viewmode%>">
											<html:options name="ampm" labelName="ampm" />
										</html:select></td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td class="formLabel" width="19%">Mode of PreApproval Request:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium" styleId="preauthMode"
								onchange="setValidateIconTitle();"
								disabled="<%=enhancedViewMode%>">
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
					</tr>
					<tr>
						<td class="formLabel">Emirate Id:</td>
						<td width="30%" class="textLabel"><html:text
								property="emirateId" styleId="emirateId" readonly="true"
								styleClass="textBox textBoxLong textBoxDisabled" /></td>
						<td class="formLabel">Patient Name:</td>
						<td width="30%" class="textLabel"><html:text
								property="patientName" styleId="patientName"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>
					</tr>
					<tr>
						<td class="formLabel">Age:</td>
						<td width="30%" class="textLabelBold"><html:text
								property="memberAge" styleId="memberAge"
								styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" /></td>
						<td class="formLabel">Gender:</td>
						<td width="30%" class="textLabel"><html:text
								property="patientGender" styleId="patientGender"
								styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" /></td>
					</tr>
					<tr>
						<td class="formLabel">Policy No.:</td>
						<td width="30%" class="textLabel"><html:text
								property="policyNumber" styleId="policyNumber" readonly="true"
								styleClass="textBox textBoxLong textBoxDisabled" /></td>
						<td class="formLabel">Corporate Name:</td>
						<td width="30%" class="textLabel"><html:text
								property="groupName" styleId="groupName"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
					</tr>

					<!-- my code -->

					<tr>
						<td class="formLabel">Product Name.:</td>
						<td width="30%" class="textLabel"><html:text
								property="productName" styleId="productName" readonly="true"
								styleClass="textBox textBoxLong textBoxDisabled" /></td>
						<td class="formLabel">Authority.:</td>
						<td width="30%" class="textLabel"><html:text
								property="authorityType" styleId="payerAuthority"      
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>									
					</tr>

					<tr>
						<td class="formLabel">Insurer Id:</td>
						<td width="30%" class="textLabel"><html:text
								property="memberId" styleId="memberId"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /> <html:hidden property="insSeqId"
								styleId="insSeqId" /> <html:hidden property="policySeqId"
								styleId="policySeqId" /></td>
						<td class="formLabel">Insurer Name:</td>
						<td width="30%" class="textLabel"><html:text
								property="insuredName" styleId="insuredName"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
					</tr>
					<tr>
						<td class="formLabel">Policy Start Date:</td>
						<td width="30%" class="textLabel"><html:text
								property="effectiveFromDate" styleId="effectiveFromDate"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>
						<td class="formLabel">Policy End Date:</td>
						<td width="30%" class="textLabel"><html:text
								property="effectiveToDate" styleId="effectiveToDate"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
					</tr>
					<tr>
						<td class="formLabel">Sum Insured:</td>
						<td width="30%" class="textLabel"><html:text
								property="sumInsured" styleId="sumInsured"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>
						<td class="formLabel">Available Sum Insured:</td>
						<td width="30%" class="textLabel"><html:text
								property="availableSumInsured" styleId="availableSumInsured"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
					</tr>
					<tr>
						<td class="formLabel">Nationality:</td>
						<td width="30%" class="textLabel"><html:text
								property="description" styleId="description"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>
						<td class="formLabel">Eligible Networks:</td>
						<td width="30%" class="textLabel"><html:text
								property="productCatTypeId"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
					</tr>
<tr>
<td class="formLabel">VIP:</td>
						<td width="30%" class="textLabel"><html:text
								property="vipYorN" styleId="vipYorN"
								styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" /></td>
						 <td class="formLabel">Member Inception Date:</td>
							<td width="30%" class="textLabel"><html:text
								property="policyIssueDate" styleId="policyIssueDate"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>	
</tr>
					
					
					
				
					
					<tr>
						<td class="formLabel"><!-- Priority: --></td>
						<td class="textLabel"><%-- <html:select property="priorityTypeID"
								styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
								<html:optionsCollection name="preauthPriority" label="cacheDesc"
									value="cacheId" />
							</html:select> --%></td>
						<td class="formLabel">Requested Amount:</td>
						<td width="30%" class="textLabel"><html:text
								property="requestedAmount" styleId="requestedAmount"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
						
					</tr>
					<tr>
						<td class="formLabel"><!-- Provider Id: --></td>
						<td class="textLabel">
							<table>
								<tr>
								
									<td></td>
									<td></td>
									<td>
										<div id="providerResult1">
											<div id="providerResult2"></div>
										</div>
									</td>
								</tr>
							</table>
						</td>
						<td class="formLabel">Provider Name:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:text property="providerName"
								styleId="providerName" styleClass="textBox textBoxLarge"
								maxlength="60" readonly="<%=netWorkStatus%>"
								disabled="<%=enhancedViewMode%>" /></td>
					</tr>
					
					
					<tr>
						<td class="formLabel">Benefit Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select property="benefitType"
								styleClass="selectBox selectBoxMedium"
								onchange="setMaternityMode()" disabled="<%=enhancedViewMode%>">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="benefitTypes" label="cacheDesc"
									value="cacheId" />
							</html:select></td>							
							
							
						<td class="formLabel" width="19%">Encounter Type:</td>
						<td class="textLabel"><html:select property="encounterTypeId"
								styleClass="selectBox selectBoxLarge" onchange="setDateMode();"
								disabled="<%=enhancedViewMode%>">
								<logic:notEmpty name="encounterTypes" scope="session">
									<html:optionsCollection name="encounterTypes" value="key"
										label="value" />
								</logic:notEmpty>
							</html:select></td>
					</tr>
					
					<tr>
						<td class="formLabel" width="19%">Encounter Start Type:</td>
						<td class="textLabel"><html:select
								property="encounterStartTypeId"
								styleClass="selectBox selectBoxLarge"
								disabled="<%=enhancedViewMode%>">
								<html:optionsCollection name="encounterStartTypes"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
						<td class="formLabel" width="19%">Encounter End Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select
								property="encounterEndTypeId"
								styleClass="selectBox selectBoxLarge">
								<html:optionsCollection name="encounterEndTypes"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
					</tr>
					<tr>
						<td class="formLabel">Start Date:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td><html:text name="frmPreAuthGeneral"
											property="hospitalzationDate" styleClass="textBox textDate"
											maxlength="10" onblur="setEndDate();"
											disabled="<%=enhancedViewMode%>" />
										<logic:notEqual value="ENHANCEMENT" property="preAuthNoStatus"
											name="frmPreAuthGeneral">
											<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#"
												onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.admissionDate',document.frmPreAuthGeneral.admissionDate.value,'',event,148,178);return false;"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar"
												name="empDate" width="24" height="17" border="0"
												align="absmiddle"></a>
										</logic:notEqual>&nbsp;</td>
									<td><html:text name="frmPreAuthGeneral"
											property="admissionTime" styleClass="textBox textTime"
											maxlength="5" onblur="setEndDate();endDateValidation();"
											disabled="<%=enhancedViewMode%>" />&nbsp;</td>
									<td><html:select property="admissionDay"
											name="frmPreAuthGeneral" styleClass="selectBox"
											onchange="setEndDate();" disabled="<%=enhancedViewMode%>">
											<html:options name="ampm" labelName="ampm" />
										</html:select></td>
								</tr>
							</table>
						</td>
						<td class="formLabel">End Date:<span class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td><html:text name="frmPreAuthGeneral"
											property="dischargeDate" styleClass="textBox textDate"
											maxlength="10"  onblur="endDateValidation();"/>
										<logic:match name="viewmode" value="false">
											<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#" onClick="checkCalender();"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar"
												id="discImg" name="empDate" width="24" height="17"
												border="0" align="absmiddle"></a>
										</logic:match>&nbsp;</td>
									<td><html:text name="frmPreAuthGeneral"
											property="dischargeTime" styleClass="textBox textTime"
											maxlength="5" />&nbsp;</td>
									<td><html:select property="dischargeDay"
											name="frmPreAuthGeneral" styleClass="selectBox">
											<html:options name="ampm" labelName="ampm" />
										</html:select></td>
								</tr>
							</table>
						</td>
					</tr>
				
					<!-- tr>
			       <td class="formLabel">Requested Amount:</td>
			      <td class="textLabel">
			       	<table><tr>
			      <td><html:text property="requestedAmount"  styleClass="textBox textBoxSmall textBoxDisabled" onkeyup="isNumeric(this)" /></td>
			      <td>
			      <logic:empty property="requestedAmountCurrency" name="frmPreAuthGeneral">
			      <html:text property="requestedAmountCurrency" value="AED" styleId="requestedAmountCurrency"  styleClass="textBox textBoxSmall textBoxDisabled" readonly="true" />
			      </logic:empty>
			       <logic:notEmpty property="requestedAmountCurrency" name="frmPreAuthGeneral">
			       <html:text property="requestedAmountCurrency"  styleId="requestedAmountCurrency"  styleClass="textBox textBoxSmall textBoxDisabled" readonly="true" />
			      </logic:notEmpty>
			      </td>
			      <td><a href="#" onclick="openRadioList('requestedAmountCurrency','CURRENCY_GROUP','option')">
		         <img src="/ttk/images/EditIcon.gif" width="16" height="16" alt="Select Currancy" border="0" ></a></td>
			      </tr></table>
			      </td>
			       <td></td>
			       <td></td>
			  </tr-->
			  
			  
					<tr>
						<td align="center" colspan="4">
						<logic:empty property="preAuthSeqID" name="frmPreAuthGeneral">
							<button type="button" name="Button2" accesskey="s"
							class="buttons" onMouseout="this.className='buttons'"
								onMouseover="this.className='buttons buttonsHover'" onclick="onNewPreApproval()">
								<u>G</u>o to new Pre-Auth Screen
							</button>&nbsp;
							</logic:empty>
							
							
							 <!-- button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button-->
						</td>
					</tr>
				</table>
			</fieldset>
		</div>
		<INPUT TYPE="hidden" NAME="mode" VALUE="">
		<input type="hidden" name="leftlink" value="">
		<input type="hidden" name="sublink" value="">
		<input type="hidden" name="tab" value="">
		<input type="hidden" name="child" value="">
		<input type="hidden" name="activitySeqId" id="activitySeqId" />
		<input type="hidden" name="activityDtlSeqId" id="activityDtlSeqId" />
		<input type="hidden" name="shortFallSeqId" id="shortFallSeqId" />
		<input type="hidden" name="reforward" id="reforward" />
		<input type="hidden" name="rownum" id="rownum" />
		<input type="hidden" name="override" id="override" value="N" />
		<input type="hidden" name="validateMemId" id="validateMemId"
			value='<bean:write name="frmPreAuthGeneral" property="memberId"/>' />
        <input type="hidden" name="focusObj" value="<%= request.getAttribute("focusObj")%>">
		<html:hidden property="authType" styleId="authType"
			name="frmPreAuthGeneral" value="PAT" />
		<html:hidden property="letterPath" name="frmPreAuthGeneral" />
		<html:hidden property="preauthCompleteStatus" name="frmPreAuthGeneral" />
		<html:hidden property="validateIcdCodeYN" name="frmPreAuthGeneral" />

		<html:hidden name="frmPreAuthGeneral" property="preAuthNo" />
		<html:hidden property="preAuthSeqID" name="frmPreAuthGeneral" value=""/>
		<html:hidden property="parentPreAuthSeqID" name="frmPreAuthGeneral" />
		<html:hidden property="authNum" name="frmPreAuthGeneral" />
		<html:hidden property="enhancedYN" name="frmPreAuthGeneral" />
		<html:hidden property="preAuthNoStatus" name="frmPreAuthGeneral" />
		<html:hidden property="oralORsystemStatus" value="GENERAL"/>
		<html:hidden property="memberSeqID" styleId="memberSeqID" />
		<input type="hidden" name="denailcode" id="denailcode">
		<html:hidden property="memberSeqID" styleId="memberSeqID" />
		<html:hidden property="partnerPreAuthSeqId" styleId="PreauthRefSeqId"/>
		<html:hidden property="partnerSeqId" styleId="partnerSeqId"/>
		<html:hidden property="requestedAmountcurrencyType" />
		<html:hidden property="processType"/>
		<html:hidden property="memberSeqID"/>
		<html:hidden property="insSeqId"/>
		<html:hidden property="policySeqId"/>

<script type="text/javascript">
/* changeTreatment(document.forms[1].treatmentType); */
</script>
	</html:form>
</body>
</html>


