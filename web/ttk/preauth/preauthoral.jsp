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
<script type="text/javascript" src="/ttk/scripts/preauth/preauthoral.js"></script>
<script type="text/javascript"
	src="/ttk/scripts/preauth/preauthgeneral-async.js"></script>
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

</head>
<body>

	<%
	
	boolean flag;
	String stylecls;
	String styledisplayDiagnosis="display:none";
	String styledisplayServices="display:none";
	String styledisplayAprAmt="display:none";
	
	if(request.getAttribute("styledisplayDiagnosis")!=null)
	{
		styledisplayDiagnosis="";
	}
	if(request.getAttribute("styledisplayServices")!=null)
	{
		styledisplayServices="";
	}
	if(request.getAttribute("styledisplayAprAmt")!=null)
	{
		styledisplayAprAmt="";
	}
	if(request.getAttribute("flag")==null)
	{
	
		flag=false;
		stylecls="";
	}
	else
	{
		flag=true;
		stylecls="textBox textBoxArea textBoxAreaDisabled";
	}
	
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
		//pageContext.setAttribute("treatmentTypes", Cache.getCacheObject("treatmentTypes"));
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
		pageContext.setAttribute("benefitTypes",
				Cache.getCacheObject("benefitTypes"));
		//pageContext.setAttribute("stateCodeList",Cache.getCacheObject("stateCode"));
		//pageContext.setAttribute("cityCodeList",Cache.getCacheObject("cityCode"));
		pageContext.setAttribute("countryCodeList",
				Cache.getCacheObject("countryCode"));
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
		name="frmPreAuthoral">
		<%
			enhancedViewMode = true;
		%>
	</logic:equal>
	<html:form action="/PreauthOralAction.do">
		<!-- S T A R T : Page Title -->

		<table align="center" class="pageTitle" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td width="57%">General Details - <bean:write
						name="frmPreAuthoral" property="caption" /></td>
				<td align="right" class="webBoard">&nbsp; <logic:notEmpty
						name="frmPreAuthoral" property="preAuthSeqID">
						<%@ include file="/ttk/common/toolbar.jsp"%>
					</logic:notEmpty>
				</td>
			</tr>
		</table>


		<!-- E N D : Page Title -->

		<div class="contentArea" id="contentArea">
			<html:errors />
			<logic:notEmpty name="errorMsg" scope="request">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error" title="Error"
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
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:message
								name="updated" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			<logic:notEmpty name="clinicianStatus" scope="request">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="clinicianStatus" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
				<logic:notEmpty property="memActiveStatus" name="frmPreAuthoral" scope="session">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write name="frmPreAuthoral" property="memActiveStatus" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			
			<logic:notEmpty name="frmPreAuthoral" property="policySuspeMsg">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmPreAuthGeneral" property="policySuspeMsg"/></td>
					</tr>
				</table>
			</logic:notEmpty>
			
			<logic:notEmpty name="frmPreAuthoral"
				property="duplicatePreauthAlert">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmPreAuthoral" property="duplicatePreauthAlert" /></td>
					</tr>
				</table>
			</logic:notEmpty>

			<logic:notEmpty name="successMsg" scope="request">
				<table align="center" class="successContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="successMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			<!-- S T A R T : Form Fields -->
			<fieldset>
				<legend>Pre-Authorization Details</legend>
				<table align="center" class="formContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td width="22%" class="formLabel">Pre-Auth No.:</td>
						<td width="30%" class="textLabelBold"><logic:notEmpty
								name="frmPreAuthoral" property="preAuthNo">
								<bean:write name="frmPreAuthoral" property="preAuthNo" />[<bean:write
									name="frmPreAuthoral" property="preAuthNoStatus" />]
			    </logic:notEmpty></td>
			    
			    
			    		  <logic:empty name="frmPreAuthoral" scope="session" property="preAuthNo">
						<td class="formLabel">System Of Medicine:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabel"><html:select
								property="systemOfMedicine"
								styleClass="selectBox selectBoxMoreMedium"
								disabled="<%=enhancedViewMode%>">
								<html:optionsCollection name="systemOfMedicines"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
					</tr>
			
					<tr>
						<td width="22%" class="formLabel">Pre-Auth Received Date:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabelBold">
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td><html:text name="frmPreAuthoral"
											property="receiveDate" styleClass="textBox textDate"
											maxlength="10" disabled="<%=viewmode%>"
											readonly="<%=viewmode%>" />
										<logic:match name="viewmode" value="false">
											<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#"
												onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthoral.receiveDate',document.frmPreAuthoral.receiveDate.value,'',event,148,178);return false;"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar"
												name="empDate" width="24" height="17" border="0"
												align="absmiddle"></a>
										</logic:match>&nbsp;</td>
									<td><html:text name="frmPreAuthoral"
											property="receiveTime" styleClass="textBox textTime"
											maxlength="5" disabled="<%=viewmode%>"
											readonly="<%=viewmode%>" />&nbsp;</td>
									<td><html:select name="frmPreAuthoral"
											property="receiveDay" styleClass="selectBox"
											disabled="<%=viewmode%>">
											<html:options name="ampm" labelName="ampm" />
										</html:select></td>
								</tr>
							</table>
						</td>
						</tr>
						</logic:empty>
						
						
						
						<logic:notEmpty name="frmPreAuthoral" scope="session" property="preAuthNo">
						
						
							<td class="formLabel">System Of Medicine:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabel"><html:select
								property="systemOfMedicine"
								styleClass="selectBox selectBoxLarge textBoxDisabled"
								disabled="true" readonly="true">
								<html:optionsCollection name="systemOfMedicines"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
					</tr>
			
					<tr>
						<td width="22%" class="formLabel">Pre-Auth Received Date:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabelBold">
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td><html:text name="frmPreAuthoral"
											property="receiveDate" styleClass="textBox textBoxMedium textBoxDisabled"
											maxlength="10" disabled="<%=viewmode%>"
											readonly="<%=viewmode%>" />
									<%-- 	<logic:match name="viewmode" value="false">
											<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#"
												onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthoral.receiveDate',document.frmPreAuthoral.receiveDate.value,'',event,148,178);return false;"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar"
												name="empDate" width="24" height="17" border="0"
												align="absmiddle"></a>
										</logic:match> --%>&nbsp;</td>
									<td><html:text name="frmPreAuthoral"
											property="receiveTime" styleClass="textBox textTime textBoxDisabled"
											maxlength="5" disabled="true"
											readonly="true" />&nbsp;</td>
									<td><html:select name="frmPreAuthoral"
											property="receiveDay" styleClass="selectBox textBoxDisabled"
											disabled="true" readonly="true">
											<html:options name="ampm" labelName="ampm" />
										</html:select></td>
								</tr>
							</table>
						</td>
						</tr>
						
						</logic:notEmpty>
						<%-- <td class="formLabel">Accident Related Cases:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabel"><html:select
								property="accidentRelatedCase"
								styleClass="selectBox selectBoxMoreMedium"
								disabled="<%=enhancedViewMode%>">
								<html:optionsCollection name="accidentRelatedCases"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
					</tr> --%>
					<tr>
						
						<td class="formLabel">Member ID:<span class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<table>
								<tr>
									<td colspan="2"><div id="memberIdResult1">
											<div id="memberIdResult2"></div>
										</div></td>
								</tr>
								<tr>
									<td><html:text property="memberId" styleId="memberId"
											styleClass="textBox textBoxLarge"
											style="width:200px;height:16px;" maxlength="60"
											disabled="<%=enhancedViewMode%>" readonly="true"/></td>
									<td><logic:notEqual value="ENHANCEMENT"
											property="preAuthNoStatus" name="frmPreAuthoral">
											<logic:notEqual property="preAuthRecvTypeID"
												name="frmPreAuthoral" value="DHP">
												<!-- <a href="#" accesskey="g"
													onClick="javascript:openOTPWindow()" class="search"> <img
													src="/ttk/images/validate_blue.png" alt="Validate OTP"
													width="30" height="30" border="0" align="middle"
													id="validateIcon"></a> -->
														<a href="#" accesskey="g"
													onClick="javascript:selectMember()" class="search"> <img
												src="/ttk/images/EditIcon.gif" alt="Select MemberId" title="Select MemberId"
												width="16" height="16" border="0" align="absmiddle"></a>
											</logic:notEqual>
										</logic:notEqual></td>
								</tr>
							</table> <html:hidden property="memberSeqID" styleId="memberSeqID" />
						</td>
					</tr>
					<tr>
						<td class="formLabel">Civil Id:</td>
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
								styleClass="textBox textBoxLong textBoxDisabled" />&nbsp;&nbsp;&nbsp;<a href="#" style="color:#0c48a2; font-family:Verdana, Arial, Helvetica, sans-serif;font-size:11px; font-weight:bold;" onClick="javascript:onUploadDocs();">Policy Docs</a></td>
						<td class="formLabel">Corporate Name:</td>
						<td width="30%" class="textLabel"><html:text
								property="corporateName" styleId="corporateName"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
					</tr>
					<tr>
					<td class="formLabel">Policy Category:</td>
					<td width="30%" class="textLabel"><html:text
								property="policyCategory" styleId="policyCategory" readonly="true"
								styleClass="textBox textBoxLong textBoxDisabled" /></td>
					<td></td>
					<td></td>
				  </tr>

					<!-- my code -->

					<tr>
						<td class="formLabel">Product Name.:</td>
						<td width="30%" class="textLabel"><html:text
								property="productName" styleId="productName" readonly="true"
								styleClass="textBox textBoxLong textBoxDisabled" /></td>
						<td class="formLabel">Authority.:</td>
						<td width="30%" class="textLabel"><html:text
								property="payerAuthority" styleId="payerAuthority"      
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>									
					</tr>

					<tr>
						<td class="formLabel">Payer Id:</td>
						<td width="30%" class="textLabel"><html:text
								property="payerId" styleId="payerId"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /> <html:hidden property="insSeqId"
								styleId="insSeqId" /> <html:hidden property="policySeqId"
								styleId="policySeqId" /></td>
						<td class="formLabel">Payer Name:</td>
						<td width="30%" class="textLabel"><html:text
								property="payerName" styleId="payerName"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
					</tr>
					<tr>
						<td class="formLabel">Policy Start Date:</td>
						<td width="30%" class="textLabel"><html:text
								property="policyStartDate" styleId="policyStartDate"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>
						<td class="formLabel">Policy End Date:</td>
						<td width="30%" class="textLabel"><html:text
								property="policyEndDate" styleId="policyEndDate"
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
								property="nationality" styleId="nationality"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>
						<td class="formLabel">Eligible Networks:</td>
						<td width="30%" class="textLabel"><html:text
								property="eligibleNetworks"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
					</tr>
					<tr>
					<td class="formLabel">VIP:</td>
						<td width="30%" class="textLabel"><html:text
								property="vipYorN" styleId="vipYorN"
								styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" /></td>
								<td class="formLabel">Member First Inception Date:</td>
						<td width="30%" class="textLabel"><html:text
								property="preMemInceptionDt" styleId="preMemInceptionDt"
								styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" /></td>
								
					</tr>
					<logic:empty name="frmPreAuthoral"  property="preAuthNo" scope="session">
					<tr>
						<td class="formLabel">Benefit Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select property="benefitType"
								styleClass="selectBox selectBoxMedium"
								onchange="setOralMaternityMode()" disabled="<%=enhancedViewMode%>">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="benefitTypes" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
						<td class="formLabel" width="19%" colspan="2"><logic:equal
								name="frmPreAuthoral" value="MTI" property="benefitType">
								<table>
									<tr>
										<td class="formLabel">Gravida:<span
											class="mandatorySymbol">*</span> <html:text
												name="frmPreAuthoral" property="gravida"
												styleId="gravida" onkeyup="isZero(this);"
												styleClass="textBox textBoxTooTiny" maxlength="2"
												onblur="setGPLA('GR');" />
										</td>
										<td class="formLabel">Para:<span class="mandatorySymbol">*</span>
											<html:text name="frmPreAuthoral" property="para"
												onkeyup="isNumeric(this);" styleId="para"
												styleClass="textBox textBoxTooTiny" maxlength="2"
												onblur="setGPLA('PA');" />
										</td>
										<td class="formLabel">Live:<span class="mandatorySymbol">*</span>
											<html:text name="frmPreAuthoral" property="live"
												onkeyup="isNumeric(this);" styleId="live"
												styleClass="textBox textBoxTooTiny" maxlength="2"
												onblur="setGPLA('LI');" />
										</td>
										<td class="formLabel">Abortion:<span
											class="mandatorySymbol">*</span> <html:text
												name="frmPreAuthoral" property="abortion"
												onkeyup="isNumeric(this);" styleId="abortion"
												styleClass="textBox textBoxTooTiny" maxlength="2"
												onblur="setGPLA('AB');" />
										</td>
									</tr>
								</table>
							</logic:equal></td>
					</tr>
					
					<tr> 
					
						<td class="formLabel">Provider Id:</td>
						<td class="textLabel">
							<html:text property="providerId" styleId="providerId"
											styleClass="textBox textBoxMedium textBoxDisabled"
											
											readonly="true" /> 
											

						</td>
						<td class="formLabel">Provider Name:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">	
	<table>
	
	<tr>
        <td>
       	<html:text property="providerName"  styleClass="searchBox" styleId="providerName" onkeyup="ConvertToUpperCase(this);onProviderSearch(this);" />
      </td>
      </tr>
      <tr>
      <td>
      <div class="searchResults" id="spDivID" style="display:none;"></div>
      </td>
      </tr> 
      </table>
	       </td>
					</tr>					
					<tr>
						<td class="formLabel" width="19%">Encounter Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select property="encounterTypeId"
								styleClass="selectBox selectBoxLarge" onchange="setDateMode();"
								disabled="<%=enhancedViewMode%>">
								<logic:notEmpty name="encounterTypes" scope="session">
									<html:optionsCollection name="encounterTypes" value="key"
										label="value" />
								</logic:notEmpty>
							</html:select></td>
							
							
							
							
						<td class="formLabel">Reference No:<span class="mandatorySymbol">*</span></td>
						<td><html:text property="referenceNo" styleClass="textBox textBoxLarge" styleId="referenceNo"/></td>
					</tr>					
					<tr>
						<td class="formLabel">Start Date:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td><html:text name="frmPreAuthoral"
											property="admissionDate" styleClass="textBox textDate"
											maxlength="10" onblur="setEndDate();"
											disabled="<%=enhancedViewMode%>" />
										<logic:notEqual value="ENHANCEMENT" property="preAuthNoStatus"
											name="frmPreAuthoral">
											<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#"
												onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthoral.admissionDate',document.frmPreAuthoral.admissionDate.value,'',event,148,178);return false;"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar"
												name="empDate" width="24" height="17" border="0"
												align="absmiddle"></a>
										</logic:notEqual>&nbsp;</td>
									<td><html:text name="frmPreAuthoral"
											property="admissionTime" styleClass="textBox textTime"
											maxlength="5" onblur="setEndDate();"
											disabled="<%=enhancedViewMode%>" />&nbsp;</td>
									<td><html:select property="admissionDay"
											name="frmPreAuthoral" styleClass="selectBox"
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
									<td><html:text name="frmPreAuthoral"
											property="dischargeDate" styleClass="textBox textDate"
											maxlength="10" onblur="endDateValidation();" />
										<logic:match name="viewmode" value="false">
											<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#" onClick="checkCalender();"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar"
												id="discImg" name="empDate" width="24" height="17"
												border="0" align="absmiddle"></a>
										</logic:match>&nbsp;</td>
									<td><html:text name="frmPreAuthoral"
											property="dischargeTime" styleClass="textBox textTime"
											maxlength="5" />&nbsp;</td>
									<td><html:select property="dischargeDay"
											name="frmPreAuthoral" styleClass="selectBox">
											<html:options name="ampm" labelName="ampm" />
										</html:select></td>
								</tr>
							</table>
						</td>
					</tr>
			<tr>
		   <td class="formLabel">Diagnosis:</td>
		   <td class="textLabel" colspan="3">
		   <html:textarea name="frmPreAuthoral" styleId="diagnosis" property="diagnosis" cols="60" rows="2" styleClass="<%= stylecls%>" readonly="<%=flag%>"/>
		 </td>
		 <td></td><td></td>
		 </tr>
		<%--  <logic:notEmpty name="frmPreAuthoral" property="revisedDiagnosis"> --%>
		 <tr id="diag" style="<%=styledisplayDiagnosis%>">
		   <td class="formLabel">RevisedDiagnosis:</td>
		   <td class="textLabel" colspan="3">
		   <html:textarea name="frmPreAuthoral" property="revisedDiagnosis" styleId="revDiagnosis"  cols="60" rows="2" styleClass="<%= stylecls%>" readonly="<%=flag%>" />
		 </td>		 
		 </tr>
		<%--  </logic:notEmpty> --%>
		 <tr>
		   <td class="formLabel">Services:</td>
		   <td class="textLabel" colspan="3">
		   <html:textarea name="frmPreAuthoral" styleId="services" property="services" styleClass="<%= stylecls%>"  cols="60" rows="2" readonly="<%=flag%>"/>
		 </td>
		 <td></td><td></td>
		 </tr>
		<%--   <logic:notEmpty name="frmPreAuthoral" property="revisedServices"> --%>
		 <tr id="ser" style="<%=styledisplayServices%>">
		   <td class="formLabel">RevisedServices:</td>
		   <td class="textLabel" colspan="3">
		   <html:textarea name="frmPreAuthoral"   property="revisedServices"  styleId="revService" cols="60" rows="2" styleClass="<%= stylecls%>" readonly="<%=flag%>"/>
		 </td>
		 </tr>
		<%--  </logic:notEmpty> --%>
		 <tr>
				    <td class="formLabel">Approved Amount:  </td>
					  <td class="textLabel">
					  	<html:text name="frmPreAuthoral" property="oralApprovedAmount" styleId="apprAmount"  onkeyup="numberValidation(this)"  styleClass="<%= stylecls%>" readonly="<%=flag%>" />
					  </td>	
					  </tr>
				<%--  <logic:notEmpty name="frmPreAuthoral" property="oralRevisedApprovedAmount">  --%>
				<tr id="approved" style="<%=styledisplayAprAmt%>">
				    <td class="formLabel">Revised Approved Amount:  </td>
					  <td class="textLabel">
					  	<html:text name="frmPreAuthoral" property="oralRevisedApprovedAmount" onkeyup="numberValidation(this)" styleId="revApprAmount" styleClass="<%= stylecls%>" readonly="<%=flag%>"/>
					  </td>	
					  </tr>	
					  </logic:empty>
					  
					  
					  
					  
					  
					  
					  
					  
					  
					  
					  
					  
					  
					  
					  
					  
					  
					  
					  
					  <logic:notEmpty name="frmPreAuthoral" scope="session" property="preAuthNo">
					  
					  
					  	<tr>
						<td class="formLabel">Benefit Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select property="benefitType"
								styleClass="selectBox selectBoxMedium textBoxDisabled"
								onchange="setOralMaternityMode()" disabled="true" readonly="true">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="benefitTypes" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
						<td class="formLabel" width="19%" colspan="2"><logic:equal
								name="frmPreAuthoral" value="MTI" property="benefitType">
								<table>
									<tr>
										<td class="formLabel">Gravida:<span
											class="mandatorySymbol">*</span> <html:text
												name="frmPreAuthoral" property="gravida"
												styleId="gravida" onkeyup="isZero(this);"
												styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" maxlength="2"
												onblur="setGPLA('GR');" />
										</td>
										<td class="formLabel">Para:<span class="mandatorySymbol">*</span>
											<html:text name="frmPreAuthoral" property="para"
												onkeyup="isNumeric(this);" styleId="para"
												styleClass="textBox textBoxTooTiny textBoxDisabled" maxlength="2" readonly="true"
												onblur="setGPLA('PA');" />
										</td>
										<td class="formLabel">Live:<span class="mandatorySymbol">*</span>
											<html:text name="frmPreAuthoral" property="live"
												onkeyup="isNumeric(this);" styleId="live"
												styleClass="textBox textBoxTooTiny textBoxDisabled" maxlength="2" readonly="true"
												onblur="setGPLA('LI');" />
										</td>
										<td class="formLabel">Abortion:<span
											class="mandatorySymbol">*</span> <html:text
												name="frmPreAuthoral" property="abortion"
												onkeyup="isNumeric(this);" styleId="abortion"
												styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" maxlength="2"
												onblur="setGPLA('AB');" />
										</td>
									</tr>
								</table>
							</logic:equal></td>
					</tr>
					<tr>
					  	<td class="formLabel">Provider Id:</td>
						<td class="textLabel">
							<html:text property="providerId" styleId="providerId"
											styleClass="textBox textBoxMedium textBoxDisabled"
											
											readonly="true" /> 
											

						</td>
						<td class="formLabel">Provider Name:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">	
	<table>
	
	<tr>
        <td>
       	<html:text property="providerName"  styleClass="textBox textBoxMedium textBoxDisabled" styleId="providerName"  readonly="true" onkeyup="ConvertToUpperCase(this);onProviderSearch(this);" />
      </td>
      </tr>
      <tr>
      <td>
      <div class="searchResults" id="spDivID" style="display:none;"></div>
      </td>
      </tr> 
      </table>
	       </td>
					</tr>					
					<tr>
						<td class="formLabel" width="19%">Encounter Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select property="encounterTypeId"
								styleClass="selectBox selectBoxLarge textBoxDisabled" disabled="true" onchange="setDateMode();"
								readonly="true">
								<logic:notEmpty name="encounterTypes" scope="session">
									<html:optionsCollection name="encounterTypes" value="key"
										label="value" />
								</logic:notEmpty>
							</html:select></td>
						
						
						<td class="formLabel">Reference No:<span class="mandatorySymbol">*</span></td>
						<td><html:text property="referenceNo" styleClass="textBox textBoxLarge" styleId="referenceNo"/></td>
						
					</tr>					
					<tr>
						<td class="formLabel">Start Date:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td><html:text name="frmPreAuthoral"
											property="admissionDate" styleClass="textBox textBoxMedium textBoxDisabled"  
											maxlength="10" onblur="setEndDate();"
											readonly="true" />
										<logic:notEqual value="ENHANCEMENT" property="preAuthNoStatus"
											name="frmPreAuthoral">
									<!-- 		<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#"
												onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthoral.admissionDate',document.frmPreAuthoral.admissionDate.value,'',event,148,178);return false;"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar"
												name="empDate" width="24" height="17" border="0"
												align="absmiddle"></a> -->
										</logic:notEqual>&nbsp;</td>
									<td><html:text name="frmPreAuthoral"
											property="admissionTime" styleClass="textBox textTime textBoxDisabled" 
											maxlength="5" onblur="setEndDate();"
											readonly="true"  />&nbsp;</td>
									<td><html:select property="admissionDay"
											name="frmPreAuthoral" styleClass="selectBox textBoxDisabled"  disabled="true" 
											onchange="setEndDate();" >
											<html:options name="ampm" labelName="ampm" />
										</html:select></td>
								</tr>
							</table>
						</td>
						<td class="formLabel">End Date:<span class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td><html:text name="frmPreAuthoral"
											property="dischargeDate" styleClass="textBox textBoxMedium textBoxDisabled"  
											maxlength="10" onblur="endDateValidation();" readonly="true"/>
										<logic:match name="viewmode" value="false">
										<!-- 	<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#" onClick="checkCalender();"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar"
												id="discImg" name="empDate" width="24" height="17"
												border="0" align="absmiddle"></a> -->
										</logic:match>&nbsp;</td>
									<td><html:text name="frmPreAuthoral"
											property="dischargeTime" styleClass="textBox textTime textBoxDisabled" 
											maxlength="5" disabled="true" />&nbsp;</td>
									<td><html:select property="dischargeDay"
											name="frmPreAuthoral" styleClass="selectBox textBoxDisabled" disabled="true" >
											<html:options name="ampm" labelName="ampm" />
										</html:select></td>
								</tr>
							</table>
						</td>
					</tr>
			<tr>
		   <td class="formLabel">Diagnosis:</td>
		   <td class="textLabel" colspan="3">
		   <html:textarea name="frmPreAuthoral" styleId="diagnosis" property="diagnosis" cols="60" rows="2" styleClass="<%= stylecls+"textBoxDisabled"%>"  readonly="true"/>
		 </td>
		 <td></td><td></td>
		 </tr>
		<%--  <logic:notEmpty name="frmPreAuthoral" property="revisedDiagnosis"> --%>
		 <tr id="diag" style="<%=styledisplayDiagnosis%>">
		   <td class="formLabel">RevisedDiagnosis:</td>
		   <td class="textLabel" colspan="3">
		   <html:textarea name="frmPreAuthoral" property="revisedDiagnosis" styleId="revDiagnosis"  cols="60" rows="2" styleClass="<%= stylecls+"textBoxDisabled"%>"  readonly="true" />
		 </td>		 
		 </tr>
		<%--  </logic:notEmpty> --%>
		 <tr>
		   <td class="formLabel">Services:</td>
		   <td class="textLabel" colspan="3">
		   <html:textarea name="frmPreAuthoral" styleId="services" property="services" styleClass="<%= stylecls+"textBoxDisabled"%>"  cols="60" rows="2" readonly="true"/>
		 </td>
		 <td></td><td></td>
		 </tr>
		<%--   <logic:notEmpty name="frmPreAuthoral" property="revisedServices"> --%>
		 <tr id="ser" style="<%=styledisplayServices%>">
		   <td class="formLabel">RevisedServices:</td>
		   <td class="textLabel" colspan="3">
		   <html:textarea name="frmPreAuthoral"   property="revisedServices"  styleId="revService" cols="60" rows="2" styleClass="<%= stylecls+"textBoxDisabled"%>" readonly="true"/>
		 </td>
		 </tr>
		<%--  </logic:notEmpty> --%>
		 <tr>
				    <td class="formLabel">Approved Amount:  </td>
					  <td class="textLabel">
					  	<html:text name="frmPreAuthoral" property="oralApprovedAmount" styleId="apprAmount"  onkeyup="numberValidation(this)"  styleClass="textBox textBoxMedium textBoxDisabled"  readonly="true" />
					  </td>	
					  </tr>
				<%--  <logic:notEmpty name="frmPreAuthoral" property="oralRevisedApprovedAmount">  --%>
				<tr id="approved" style="<%=styledisplayAprAmt%>">
				    <td class="formLabel">Revised Approved Amount:  </td>
					  <td class="textLabel">
					  	<html:text name="frmPreAuthoral" property="oralRevisedApprovedAmount" onkeyup="numberValidation(this)" styleId="revApprAmount" styleClass="textBox textBoxMedium textBoxDisabled"  readonly="true"/>
					  </td>	
					  </tr>
				      </logic:notEmpty>
					 <%--  </logic:notEmpty>	 --%>
					<tr>
						<td align="center" colspan="4">
							<button type="button" name="Button2" accesskey="s"
								class="buttons" onMouseout="this.className='buttons'"
								onMouseover="this.className='buttons buttonsHover'"
								onClick="onOralUserSubmit()">
								<u>S</u>ave
							</button>&nbsp; <!-- button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button-->
		<logic:notEmpty name="frmPreAuthoral" property="oralORsystemStatus">
						<button type="button" name="Button2" accesskey="s" id="edit"
								class="buttons" onMouseout="this.className='buttons'"
								onMouseover="this.className='buttons buttonsHover'"
								onClick="onEdit()">
								<u>E</u>dit
							</button>&nbsp;
							
							<button type="button" name="Button2" accesskey="s" id="cancel" style="display: none"
								class="buttons" onMouseout="this.className='buttons'"
								onMouseover="this.className='buttons buttonsHover'"
								onClick="onCancel()">
								<u>C</u>ancel
							</button>&nbsp;
							
							
							<button type="button" name="Button2" accesskey="s" id="link"
								class="buttons" onMouseout="this.className='buttons'"
								onMouseover="this.className='buttons buttonsHover'"
								onClick="onLink()">
								<u>L</u>ink
							</button>&nbsp;
						</logic:notEmpty>
						</td>						
						
					</tr>
				
				</table>
				
			</fieldset>
			
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
			value='<bean:write name="frmPreAuthoral" property="memberId"/>' />

		<html:hidden property="authType" styleId="authType"
			name="frmPreAuthoral" value="PAT" />
		<html:hidden property="letterPath" name="frmPreAuthoral" />
		<html:hidden property="preauthCompleteStatus" name="frmPreAuthoral" />
		<html:hidden property="validateIcdCodeYN" name="frmPreAuthoral" />

		<html:hidden name="frmPreAuthoral" property="preAuthNo" />
		<html:hidden property="preAuthSeqID" name="frmPreAuthoral" />
		<html:hidden property="parentPreAuthSeqID" name="frmPreAuthoral" />
		<html:hidden property="authNum" name="frmPreAuthoral" />
		<html:hidden property="enhancedYN" name="frmPreAuthoral" />
		<html:hidden property="preAuthNoStatus" name="frmPreAuthoral" />
		<html:hidden indexed="preauthMode" property="preauthMode"  value="TEL"/>
		<html:hidden property="networkProviderType" value="Y"/>
		<html:hidden property="providerSeqId" styleId="providerSeqId" />
		<%-- <html:hidden property="oralORsystemStatus" name="frmPreAuthoral" value="ORAL"/> --%>
		<html:hidden property="preAuthRecvTypeID" value="NTEL"/>
		<html:hidden property="providerSeqId" styleId="providerSeqId" />
		<html:hidden name="frmPreAuthoral" property="tempdiagnosis" styleId="tempdiagnosis"/>
		<html:hidden name="frmPreAuthoral" property="tempservices" styleId="tempservices"/>
	    <html:hidden name="frmPreAuthoral" property="tempAprAmt" styleId="tempAprAmt"/> 
		
		<html:hidden property="preMemInceptionDt" styleId="preMemInceptionDt" styleClass="textBox textBoxMedium textBoxDisabled"/>
		<input type="hidden" name="flaghistory" value="Y"/>
	</html:form>
</body>
<script type="text/javascript">
 document.forms[1].setAttribute( "autocomplete", "off" );//added to disable autocomplete f
  </script>
</html>


