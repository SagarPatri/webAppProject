
<%@page import="org.apache.struts.action.DynaActionForm"%>
<%
	/**
	 * @ (#) claimgeneral.jsp July 13, 2015
	 * Project 	     : ProjectX
	 * File          : claimgeneral.jsp
	 * Author        : Nagababu K
	 * Company       : RCS Technologies
	 * Date Created  : July 13, 2015
	 *
	 * @author       :  Nagababu K
	 * Modified by   :  
	 * Modified date :  
	 * Reason        :  
	 */
%>
    
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>      
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page
	import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper"%>
<head>
<script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/preauth/history.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/claims/claimgeneral.js"></script>
<script type="text/javascript" src="/ttk/scripts/preauth/preauthgeneral-async.js"></script>

</head>
<body>
<logic:notEmpty name="JS_Focus_ID" scope="request">
<script type="text/javascript">
JS_Focus_ID='<bean:write name="JS_Focus_ID" scope="request"/>';

</script>

</logic:notEmpty>
	<%
	
		boolean viewmode = true;
		boolean bEnabled = false;
		boolean viewmode1 = true;
		String ampm[] = { "AM", "PM" };
		boolean submissionMode = false;
		boolean blnAmmendmentFlow = false;
		if (TTKCommon.isAuthorized(request, "Edit")) {
			viewmode = false;
			viewmode1 = false;
		}
		
	 
		//end of if(TTKCommon.isAuthorized(request,"Edit"))
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
		pageContext.setAttribute("modeOfClaims",
				Cache.getCacheObject("modeOfClaims"));
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
		pageContext.setAttribute("patientTypes",
				Cache.getCacheObject("patientTypes"));
		//pageContext.setAttribute("stateCodeList",Cache.getCacheObject("stateCode"));
		//pageContext.setAttribute("cityCodeList",Cache.getCacheObject("cityCode"));
		pageContext.setAttribute("countryCodeList",
				Cache.getCacheObject("countryCode"));
		pageContext.setAttribute("claimType",
				Cache.getCacheObject("claimType"));
		pageContext.setAttribute("memberClaimFrom",
				Cache.getCacheObject("MemberClaimFromList"));
		pageContext.setAttribute("providerFecilTypes",Cache.getCacheObject("providerType"));
		pageContext.setAttribute("provRegAuthority",Cache.getCacheObject("regAuthority"));
		pageContext.setAttribute("consultationTypes",Cache.getCacheObject("consultTypeCode"));
		//OPD_4_hosptial
		//pageContext.setAttribute("paymentType", Cache.getCacheObject("PaymentType"));
		//OPD_4_hosptial
		//added as per kOC 1285 Change Request  
		//pageContext.setAttribute("domicilaryReasonList", Cache.getCacheObject("domHospReason"));//1285 change request
		//added as per kOC 1285 Change Request

		//pageContext.setAttribute("productName", Cache.getCacheObject("productName"));

		pageContext.setAttribute("viewmode", new Boolean(viewmode));
		pageContext.setAttribute("provRegAuthority",Cache.getCacheObject("regAuthority"));
		pageContext.setAttribute("submissionCatagory",Cache.getCacheObject("submissionCatagory"));
		pageContext.setAttribute("paymentTo",Cache.getCacheObject("paymentTo"));
		boolean netWorkStatus = true;
		boolean memberValid = false;
		
	%>
	<logic:equal value="DTR" property="claimSubmissionType"
		name="frmClaimGeneral">
		<%
			submissionMode = true;
		%>
	</logic:equal>
	
	
	<html:form action="/ClaimGeneralAction.do">
	<bean:define id="tempPreCronValue" value="N"/>
		<!-- S T A R T : Page Title -->


		<table align="center" class="pageTitle" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td width="57%">General Details - <bean:write
						name="frmClaimGeneral" property="caption" /></td>
				<td align="right" class="webBoard">&nbsp;<%@ include
						file="/ttk/common/toolbar.jsp"%>
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
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error" title="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="errorMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			
			<logic:notEmpty name="validationMessage" scope="request">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error" title="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="validationMessage" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			
			
			<logic:notEmpty name="frmClaimGeneral" property="proStatusErrMsg">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error" title="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmClaimGeneral" property="proStatusErrMsg"/></td>
					</tr>
				</table>
			</logic:notEmpty>
	
			<logic:notEmpty name="errorMsg1" scope="request">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error" title="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="errorMsg1" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			
			<logic:notEmpty name="frmClaimGeneral" property="duplicateClaimAlert">
				<table align="center" class="errorContainer" style="color: yellow;" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmClaimGeneral" property="duplicateClaimAlert" /></td>
					</tr>
				</table>				
			</logic:notEmpty>
				
			<logic:notEmpty name="frmClaimGeneral" property="memCoveredAlert">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Wrning" title="Wrning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmClaimGeneral" property="memCoveredAlert" />
								</td>
					</tr>
				</table>				
			</logic:notEmpty>
			
			<logic:notEmpty name="frmClaimGeneral" property="copayLimitReachedActivityYN">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Wrning" title="Wrning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmClaimGeneral" property="copayLimitReachedActivityYN" />
								</td>
					</tr>
				</table>				
			</logic:notEmpty>
			<logic:notEmpty name="frmClaimGeneral" property="ipCopayLimitYN">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Wrning" title="Wrning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmClaimGeneral" property="ipCopayLimitYN" />
								</td>
					</tr>
				</table>				
			</logic:notEmpty>
			
			<logic:notEmpty name="frmClaimGeneral" property="clinicianWarning">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Wrning" title="Wrning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmClaimGeneral" property="clinicianWarning" />
								</td>
					</tr>
				</table>				
			</logic:notEmpty>
			
			<logic:notEmpty name="frmClaimGeneral" property="irdrgAltMsg">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="irdrgAltMsg" title="irdrgAltMsg"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmClaimGeneral" property="irdrgAltMsg" />
								</td>
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
			<logic:notEmpty name="warningmessage" scope="session">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="warningmessage" scope="session" /></td>
					</tr>
				</table>
			</logic:notEmpty>
				<%-- <logic:notEmpty name="sum_exe_flagmsg" scope="session"> --%>
				<logic:notEmpty name="frmClaimGeneral" property="sum_exc_flag">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmClaimGeneral" property="sum_exc_flag" scope="session" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			<logic:empty name="frmClaimGeneral" property="sum_exc_flag">
		<logic:notEmpty name="frmClaimGeneral" property="req_exc_flag">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmClaimGeneral" property="req_exc_flag" scope="session" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			</logic:empty>
			<logic:notEmpty name="frmClaimGeneral" property="eclaimDuplicateWrongAlert">
						<table align="center" class="errorContainer" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="/ttk/images/warning.gif" alt="Wrning" title="Wrning"
									width="16" height="16" align="absmiddle">&nbsp; <bean:write
										name="frmClaimGeneral" property="eclaimDuplicateWrongAlert" />
										</td>
							</tr>
						</table>				
			</logic:notEmpty>
			
			<logic:notEmpty name="frmClaimGeneral" property="negativeAmtFlg">
						<table align="center" class="errorContainer" border="0"
							cellspacing="0" cellpadding="0">
							<tr>
								<td><img src="/ttk/images/warning.gif" alt="Wrning" title="Wrning"
									width="16" height="16" align="absmiddle">&nbsp; <bean:write
										name="frmClaimGeneral" property="negativeAmtFlg" />
										</td>
							</tr>
						</table>				
			</logic:notEmpty>
				<logic:notEmpty name="delErrorMsg" scope="request">
					<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="alert" title="alert"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="delErrorMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			
			<logic:notEmpty name="frmClaimGeneral" property="policySuspeMsg">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmClaimGeneral" property="policySuspeMsg"/></td>
					</tr>
				</table>
				</logic:notEmpty>
			<!-- S T A R T : Form Fields -->
			<fieldset>
				<legend>Claim Details</legend>
				<table align="center" class="formContainer" border="0"
					cellspacing="0" cellpadding="0">
					
					
					<tr>
					<td class="formLabel">Submission category:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabel"><html:select
								property="processType"
							
								styleClass="selectBox selectBoxMedium"
								disabled="true">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="submissionCatagory"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
							<td></td><td></td>
							</tr>
					
					 <logic:match value="DBL" name="frmClaimGeneral" property="processType">
				 <logic:match value="CNH" property="claimType" name="frmClaimGeneral">
					
					 <tr><td class="formLabel">Payment To :<span class="mandatorySymbol">*</span></td>
					 <td class="textLabel">
							 <html:select
								name="frmClaimGeneral" property="paymentTo" disabled="true" styleId ="paymentToId"
								styleClass="selectBox selectBoxMedium">
								<html:optionsCollection name="paymentTo"
									label="cacheDesc" value="cacheId" />
							</html:select>
					 </td> 
					 <logic:match value="PTN" property="paymentTo" name="frmClaimGeneral">
					
					<td id="partnerIdLabel" style="display:inline;" class="formLabel">Partner Name:<span class="mandatorySymbol">*</span></td>
					  <td id="partnerId" style="display:inline;" class="textLabel">
					  
					  		<html:select property="partnerName" styleClass="selectBox selectBoxMedium" styleId="partnerName" >
								 <logic:notEmpty name="partnersList" scope="session">
									<html:option value="">Select from list</html:option>
									<html:optionsCollection name="partnersList" value="key" label="value" />
								</logic:notEmpty>
							</html:select>
					  
					 </td>
					 </logic:match>
					 </tr>
				</logic:match>
				</logic:match>
					
					
					
					
					
					
					
					
					<tr>
						<td width="22%" class="formLabel">Claim No.:</td>
						<td width="30%" class="textLabelBold"><logic:notEmpty
								name="frmClaimGeneral" property="claimNo">
								<bean:write name="frmClaimGeneral" property="claimNo" />
								<%-- [<bean:write name="frmClaimGeneral" property="preAuthNoStatus"/>] --%>
							</logic:notEmpty> <html:hidden name="frmClaimGeneral" property="claimNo" /> <html:hidden
								property="preAuthSeqID" styleId="preAuthSeqID" name="frmClaimGeneral" /> <html:hidden
								property="claimSeqID" name="frmClaimGeneral" /> <html:hidden
								property="parentClaimSeqID" name="frmClaimGeneral" /> <html:hidden
								property="authNum" name="frmClaimGeneral" /> <html:hidden
								property="enhancedYN" name="frmClaimGeneral" /> <html:hidden
								property="preAuthNoStatus" name="frmClaimGeneral" />
								<html:hidden
								property="exitdate" name="frmClaimGeneral" />
								
								</td>
								  
								  
								  <logic:equal value="CTM" property="claimType" name="frmClaimGeneral">
								<logic:equal name="frmClaimGeneral" value="Y" property="takafulYN">
						<td width="22%" class="formlabel">Takaful Claim Ref No.:<span class="mandatorySymbol">*</span> </td>
						<td><html:text property="takafulClaimRefNo" styleClass="textBox textBoxLong"/></td>
						</logic:equal>
						</logic:equal>
						
						
					</tr>
					<logic:equal value="DTR" property="claimSubmissionType"
						name="frmClaimGeneral">
						<tr>
							<td width="22%" class="formLabel">Claims Submission Type:</td>
							<td width="30%" class="textLabelBold">RE-SUBMISSION</td>
							<logic:equal value="CTM" property="claimType" name="frmClaimGeneral">
								<td class="formLabel" width="19%">Claim From:</td>
								<td class="textLabel"><html:select property="claimFrom"
										styleClass="selectBox selectBoxMedium selectBoxDisabled"
										disabled="true">
										<html:option value="">Select from list</html:option>
										<html:optionsCollection name="memberClaimFrom"
											label="cacheDesc" value="cacheId" />
									</html:select></td>
							</logic:equal>
						</tr>
						<tr>
							<td class="formLabel" colspan="1">Re-Submission Remarks:<span
								class="mandatorySymbol">*</span>
							</td>
							<td class="textLabel" colspan="3"><html:textarea
									name="frmClaimGeneral" property="remarks" rows="2" cols="80"
									disabled="true" /></td>
						</tr>
					</logic:equal>
					<logic:equal value="DTC" property="claimSubmissionType"
						name="frmClaimGeneral">
						<tr>
							<td width="22%" class="formLabel">Claims Submission Type:</td>
							<td width="30%" class="textLabelBold">SUBMISSION</td>
							<logic:equal value="CTM" property="claimType"
								name="frmClaimGeneral">
								<td class="formLabel" width="19%">Claim From:</td>
								<td class="textLabel"><html:select property="claimFrom"
										styleClass="selectBox selectBoxMedium" disabled="true">
										<html:option value="">Select from list</html:option>
										<html:optionsCollection name="memberClaimFrom"
											label="cacheDesc" value="cacheId" />
									</html:select></td>
							</logic:equal>
						</tr>
					</logic:equal>

					<tr>
						<td width="22%" class="formLabel">Claim Received Date:</td>
						<td width="30%" class="textLabelBold">
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td><html:text name="frmClaimGeneral"
											property="receiveDate"
											styleClass="textBox textDate textBoxDisabled" maxlength="10"
											readonly="true" />&nbsp;</td>
									<td><html:text name="frmClaimGeneral"
											property="receiveTime"
											styleClass="textBox textTime textBoxDisabled" maxlength="5"
											readonly="true" />&nbsp;</td>
									<td><html:select name="frmClaimGeneral"
											property="receiveDay" styleClass="selectBox" disabled="true">
											<html:options name="ampm" labelName="ampm" />
										</html:select></td>
								</tr>
							</table>
						</td>
						<td class="formLabel">Claim Type:</td>
						<td width="30%" class="textLabel"><html:select
								property="claimType" styleClass="selectBox selectBoxMedium"
								disabled="true">
								<html:optionsCollection name="claimType" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
					</tr>

					<tr>
						<td width="22%" class="formLabel">Invoice No.:</td>
						<td width="30%" class="textLabel"><html:text
								property="invoiceNo"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>
						<td width="22%" class="formLabel">Batch No.:</td>
						<td width="30%" class="textLabelBold"><bean:write
								name="frmClaimGeneral" property="batchNo" /></td>
					</tr>
					
					<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral">
					<tr>
						<td width="22%" class="formLabel"></td>
						<td width="30%" class="textLabel"></td>
						<td width="22%" class="formLabel">Sub Batch No.:</td>
						<td width="30%" class="textLabelBold"><bean:write
								name="frmClaimGeneral" property="subBatchID" /></td>
					</tr>
					</logic:equal>
					
					<tr>
						<td class="formLabel">Bill Date:
							<td colspan="3">
							<html:text property="billDate" styleClass="textBox textDate" /><A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmClaimGeneral.billDate',document.frmClaimGeneral.billDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
						</td>
					</tr>
					
					<tr>
						<td class="formLabel">System Of Medicine:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabel"><html:select
								property="systemOfMedicine"
								styleClass="selectBox selectBoxMedium"
								disabled="<%=submissionMode%>">
								<html:optionsCollection name="systemOfMedicines"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
						<td class="formLabel">Accident Related Cases:<span
							class="mandatorySymbol">*</span></td>
						<td colspan="3">
	       				<html:select property="accidentRelatedCase" styleClass="selectBox selectBoxMedium" disabled="<%= submissionMode %>" onchange="onChangeCases()">
                  			<html:option value="">Select from list</html:option> 
                  			<html:option value="Y">Yes</html:option> 
                  			<html:option value="N">No</html:option>  
            			</html:select>
        				</td>
        				<tr>
        					<td class="formLabel"></td>
							<td width="30%" class="textLabel"></td>
        					<logic:equal value="Y" property="accidentRelatedCase" name="frmClaimGeneral">
        					<td class="formLabel">Work related injury:<span
							class="mandatorySymbol">*</span></td>
							<td colspan="3">
		       				<html:select property="workRelatedInjury" styleClass="selectBox selectBoxMedium" onchange="onChangeCases()">
	                  			<html:option value="">Select from list</html:option> 
	                  			<html:option value="Y">Yes</html:option> 
	                  			<html:option value="N">No</html:option>  
	            			</html:select>
        					</logic:equal>
        				</tr>
        				<tr>
        					<td class="formLabel"></td>
							<td width="30%" class="textLabel"></td>
        					<logic:equal value="Y" property="workRelatedInjury" name="frmClaimGeneral">
        					<td class="formLabel">Alcohol/drug intoxication:<span
							class="mandatorySymbol">*</span></td>
							<td colspan="3">
		       				<html:select property="alcoholIntoxication" styleClass="selectBox selectBoxMedium" onchange="onChangeCases()">
	                  			<html:option value="">Select from list</html:option> 
	                  			<html:option value="Y">Yes</html:option> 
	                  			<html:option value="N">No</html:option>  
	            			</html:select>
        					</logic:equal>
        				</tr>	
        				<tr>
        					<td class="formLabel"></td>
							<td width="30%" class="textLabel"></td>
        					<logic:equal value="Y" property="alcoholIntoxication" name="frmClaimGeneral">
        					<td class="formLabel">RTA (Road traffic accident):<span
							class="mandatorySymbol">*</span></td>
							<td colspan="3">
		       				<html:select property="roadTrafficAccident" styleClass="selectBox selectBoxMedium" onchange="onChangeCases()">
	                  			<html:option value="">Select from list</html:option> 
	                  			<html:option value="Y">Yes</html:option> 
	                  			<html:option value="N">No</html:option>  
	            			</html:select>
        				</logic:equal>
        				</tr>
        				<tr>
        					<td class="formLabel"></td>
							<td width="30%" class="textLabel"></td>
        					<logic:equal value="Y" property="roadTrafficAccident" name="frmClaimGeneral">
        					<td class="formLabel">Date of injury:<span
							class="mandatorySymbol">*</span></td>
							<td colspan="3">
							<html:text property="dateOfInjury" styleClass="textBox textDate" /><A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmClaimGeneral.dateOfInjury',document.frmClaimGeneral.dateOfInjury.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
        				</logic:equal>
        				</tr>	
						<%-- <td width="30%" class="textLabel"><html:select
								property="accidentRelatedCase"
								styleClass="selectBox selectBoxMedium"
								disabled="<%=submissionMode%>">
								<html:optionsCollection name="accidentRelatedCases"
									label="cacheDesc" value="cacheId" />
							</html:select></td> --%>
					</tr>
					<tr>
						<td class="formLabel">Priority:</td>
						<td class="textLabel"><html:select property="priorityTypeID"
								styleClass="selectBox selectBoxMedium"
								disabled="<%=submissionMode%>">
								<html:optionsCollection name="preauthPriority" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
						<td class="formLabel" width="19%">Network (Y/N):</td>
						<td class="textLabel"><html:select
								property="networkProviderType"
								styleClass="selectBox selectBoxMedium" disabled="true">
								<html:option value="">Select From List</html:option>
								<html:option value="Y">YES</html:option>
								<html:option value="N">NO</html:option>
							</html:select></td>
					</tr>
					
					
					
					<logic:match value="GBL" name="frmClaimGeneral" property="processType">
					<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">

						<tr>
							<td class="formLabel">Provider Name:<span
								class="mandatorySymbol">*</span></td>
							<td class="textLabel">
								<table>
									<tr>
										<td><bean:define id="providerNameTitle"
												name="frmClaimGeneral" property="providerName"
												type="java.lang.String" /> <html:text
												property="providerName" title="<%=providerNameTitle%>"
												styleId="providerName"
												styleClass="textBox textBoxLarge textBoxDisabled"
												maxlength="60" readonly="true" /></td>
										<td><logic:equal value="CTM" name="frmClaimGeneral"
												property="claimType">
												 <logic:notEqual value="DTR" property="claimSubmissionType" name="frmClaimGeneral">
												<a href="#" accesskey="g"
													onClick="javascript:selectProvider()" class="search"> <img
													src="/ttk/images/EditIcon.gif" alt="Select Clinician" title="Select Clinician"
													width="16" height="16" border="0" align="absmiddle">&nbsp;
												</a>
												 </logic:notEqual>
											</logic:equal></td>
									</tr>
								</table>
							</td>
							<td class="formLabel">Provider Id:</td>
							<td class="textLabel"><html:text property="providerId"
									styleId="providerId"
									styleClass="textBox textBoxLarge textBoxDisabled"
									readonly="true" /> <html:hidden property="providerSeqId"
									styleId="providerSeqId" /> <html:hidden
									property="hiddenHospitalID" /></td>
						</tr>
					
					<logic:notEmpty property="providerId" name="frmClaimGeneral">	
					<tr>
					<td class="formLabel">Provider Authority:</td>
						<td width="30%" class="textLabel">
						<html:text property="provAuthority" styleId="provAuthority" styleClass="textBox textBoxLarge textBoxDisabled" readonly="true" /></td>
					
					<td class="formLabel"> VAT TRN:</td>
						<td width="30%" class="textLabel">
						<html:text property="vatTrnCode" styleId="vatTrnCode" styleClass="textBox textBoxLarge textBoxDisabled" readonly="true" /></td>
					</tr>
					</logic:notEmpty>	
					
					
					
					</logic:equal>
					</logic:match>
					
					
					<logic:match value="DBL" name="frmClaimGeneral" property="processType">
					<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">
						<logic:notMatch value="PTN" property="paymentTo" name="frmClaimGeneral">

						<tr>
							<td class="formLabel">Provider Name:<span
								class="mandatorySymbol">*</span></td>
							<td class="textLabel">
								<table>
									<tr>
										<td><bean:define id="providerNameTitle"
												name="frmClaimGeneral" property="providerName"
												type="java.lang.String" /> <html:text
												property="providerName" title="<%=providerNameTitle%>"
												styleId="providerName"
												styleClass="textBox textBoxLarge textBoxDisabled"
												maxlength="60" readonly="true" /></td>
										<td><logic:equal value="CTM" name="frmClaimGeneral"
												property="claimType">
												<a href="#" accesskey="g"
													onClick="javascript:selectProvider()" class="search"> <img
													src="/ttk/images/EditIcon.gif" alt="Select Clinician" title="Select Clinician"
													width="16" height="16" border="0" align="absmiddle">&nbsp;
												</a> 
											</logic:equal></td>
									</tr>
								</table>
							</td>
							<td class="formLabel">Provider Id:</td>
							<td class="textLabel"><html:text property="providerId"
									styleId="providerId"
									styleClass="textBox textBoxLarge textBoxDisabled"
									readonly="true" /> <html:hidden property="providerSeqId"
									styleId="providerSeqId" /> <html:hidden
									property="hiddenHospitalID" /></td>
						</tr>


					<logic:notEmpty property="providerId" name="frmClaimGeneral">	
					<tr>
					<td class="formLabel">Provider Authority:</td>
						<td width="30%" class="textLabel">
						<html:text property="provAuthority" styleId="provAuthority" styleClass="textBox textBoxLarge textBoxDisabled" readonly="true" /></td>
					
					<td class="formLabel"> VAT TRN:</td>
						<td width="30%" class="textLabel">
						<html:text property="vatTrnCode" styleId="vatTrnCode" styleClass="textBox textBoxLarge textBoxDisabled" readonly="true" /></td>
					</tr>
					</logic:notEmpty>








						</logic:notMatch>
					</logic:equal>
					
					</logic:match>
					
					
					<logic:equal value="N" name="frmClaimGeneral"
						property="networkProviderType">
						<tr>
						
							<td class="formLabel">Provider Type:<span	class="mandatorySymbol">*</span></td>
							
							<td class="textLabel">
							<html:select
								property="providerType"
								styleClass="selectBox selectBoxMedium">
								<html:option value="">Select From List</html:option>
								<html:option value="SGO">Government</html:option>
								<html:option value="SPR">Private</html:option>
							</html:select>	
							</td>
							
							<td class="formLabel">Provider Facility Type:<span	class="mandatorySymbol">*</span></td>
							<td class="textLabel">
							<html:select
								property="providerFecilType"
								styleClass="selectBox selectBoxMoreMedium">
								<html:option value="">Select From List</html:option>
								<html:optionsCollection name="providerFecilTypes" label="cacheDesc" value="cacheId" />
							</html:select>
							
									</td>
							
						</tr>
						
						<tr>
							<td class="formLabel">Provider Name:<span
								class="mandatorySymbol">*</span></td>
							<td class="textLabel"><html:text property="providerName"
									styleId="providerName" styleClass="textBox textBoxLarge"
									maxlength="60" /></td>
							<td class="formLabel">Provider Authority:<span
								class="mandatorySymbol">*</span></td>
							<td class="textLabel">
				<html:select property ="provRegAuthority" styleClass="selectBox selectBoxMedium">	
				<html:option value="">Select from list</html:option>	
     			<html:options collection="provRegAuthority" property="cacheId" labelProperty="cacheDesc"/>
				</html:select>	 
							</td>
			   </tr>
											
						<tr>
							<td class="formLabel">Country:<span	class="mandatorySymbol">*</span></td>
							<td class="textLabel"><html:select
									property="providerCountry" styleId="providerCountry"
									styleClass="selectBox selectBoxMoreMedium"
									onchange="getStates();getcurrencyCode();clearConversionRate();" disabled="<%=submissionMode%>">
									<html:option value="">Select From List</html:option>
									<html:optionsCollection name="countryCodeList"
										label="cacheDesc" value="cacheId" />
								</html:select></td>
							<td class="formLabel">Emirate/State:</td>
							<td class="textLabel"><html:select
									property="providerEmirate" styleId="providerEmirate"
									styleClass="selectBox selectBoxMoreMedium"
									onchange="getAreas()" disabled="<%=submissionMode%>">
									<html:option value="">Select From List</html:option>
									<logic:notEmpty name="providerStates" scope="session">
										<html:optionsCollection name="providerStates" value="key"
											label="value" />
									</logic:notEmpty>
								</html:select></td>
						</tr> 
					
						<tr>
							<td class="formLabel">Area/City:</td>
							<td class="textLabel"><html:select property="providerArea"
									styleId="providerArea"
									styleClass="selectBox selectBoxMoreMedium"
									disabled="<%=submissionMode%>">
									<html:option value="">Select From List</html:option>
									<logic:notEmpty name="providerAreas" scope="session">
										<html:optionsCollection name="providerAreas" value="key"
											label="value" />
									</logic:notEmpty>
								</html:select></td>
							<td class="formLabel">Po-Box:</td>
							<td class="textLabel"><html:text property="providerPobox"
									styleClass="textBox textBoxLong" disabled="<%=submissionMode%>" onkeyup="isNumeric(this);"/>
							</td>
						</tr>		
                      <tr>
	                <td class="formLabel">Address:</td>
							
							<td class="textLabel">
							<html:text property="providerAddress" styleClass="textBox textBoxLong" disabled="<%=submissionMode%>" />
							</td>
                      <td></td> <td></td>
						</tr>					
					</logic:equal>
					
					
					<tr>
						<td class="formLabel" width="19%">Mode of Claim:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select name="frmClaimGeneral"
								property="modeOfClaim" styleClass="selectBox selectBoxMedium"
								styleId="preauthMode" onchange="setValidateIconTitle();"
								disabled="true">
								<html:optionsCollection name="modeOfClaims" label="cacheDesc"
									value="cacheId" />
							</html:select></td>

						<td class="formLabel">Member Id:<span class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:text property="memberId"
								styleId="memberId" styleClass="textBox textBoxMedium"  style="width:222px;"
								readonly="true" disabled="<%=submissionMode%>" />
									<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
								
								<a href="#" accesskey="g"
													onClick="javascript:selectMember()" class="search"> <img
												src="/ttk/images/EditIcon.gif" alt="Select MemberId" title="Select MemberId"
												width="16" height="16" border="0" align="absmiddle"></a>
								
								<!-- <a href="#" accesskey="s" style="color:blue;" onClick="javascript:selectMember()" class="search"><u>Search</u></a> -->
									</logic:notEqual>
									<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
										<logic:equal value="enable" property="disableMemberId" name="frmClaimGeneral">
								
											<a href="#" accesskey="g"
																	onClick="javascript:selectMember()" class="search"> <img
																src="/ttk/images/EditIcon.gif" alt="Select MemberId" title="Select MemberId"
																width="16" height="16" border="0" align="absmiddle"></a>
										</logic:equal>	
										<logic:equal value="disable" property="disableMemberId" name="frmClaimGeneral">
											
											<img src="/ttk/images/EditIcon.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;	
												
										</logic:equal>	
											
									</logic:equal>
										
							<html:hidden property="memberSeqID" styleId="memberSeqID" /> <html:hidden
								property="memberIDValidYN" /> <html:hidden
								property="hiddenMemberID" /></td>
					</tr>
					
					
					
					<tr>
					<td></td>
					<td></td>
					<td></td>
						<td class="textLabelBold">Pre-Auths Count: <bean:write name="frmClaimGeneral" property="preAuthCount" /></td>
					</tr>
					<tr>
					<td></td>
					<td></td>
					<td></td>
						<td class="textLabelBold">Claims Count: <bean:write name="frmClaimGeneral" property="clmCount" /></td>
					</tr>
					
					
					
					
					
					
					<tr>
						<td class="formLabel">Civil Id:</td>
						<td width="30%" class="textLabel"><html:text
								property="emirateId" styleId="emirateId" readonly="true"
								styleClass="textBox textBoxLong textBoxDisabled" /></td>
						<td class="formLabel">Patient Name:</td>
						<td width="30%" class="textLabel"><html:text
								property="patientName" styleId="patientName"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
					</tr>
                   
                     
					<tr>
						<td class="formLabel">Product Name:</td>
						<td width="30%" class="textLabel"><html:text
								property="productName" styleId="productName" readonly="true"
								styleClass="textBox textBoxLong textBoxDisabled" /></td>
								
						<td class="formLabel">Authority:</td>
						<td width="30%" class="textLabel"><html:text
								property="payerAuthority" styleId="payerAuthority"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
					</tr>
					
					<!-- ADDING PATIENT ID -->	
					
				    <tr>
					<td></td>
					<td></td>
					
					
					   <logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral">
					   <logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">
								  <td class="formLabel" width="30%">Patient Id:<span
							class="mandatorySymbol">*</span></td>
								 <td width="30%" class="textLabelBold"><html:text
								property="patient_id"  styleId="patient_id"
								styleClass="textBox textBoxLong textBoxDisabled" maxlength="25"
								readonly="true" /></td>
								</logic:equal>
								<logic:notEqual value="Y" name="frmClaimGeneral"
						property="networkProviderType">
								  <td class="formLabel" width="30%">Patient Id:</td>
								 <td width="30%" class="textLabelBold"><html:text
								property="patient_id"  styleId="patient_id"
								styleClass="textBox textBoxLong textBoxDisabled" maxlength="25"
								readonly="true" /></td>
								</logic:notEqual>
							</logic:equal>
								    
                  
								
								<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
								<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">
								
								<td class="formLabel" width="30%">Patient Id:<span
							class="mandatorySymbol">*</span></td>
								 <td width="30%" class="textLabelBold"><html:text
								property="patient_id" styleId="patient_id"
								styleClass="textBox textBoxLong" maxlength="25"
								 /></td>
							</logic:equal>
							<logic:notEqual value="Y" name="frmClaimGeneral"
						property="networkProviderType">
								
								<td class="formLabel" width="30%">Patient Id:</td>
								 <td width="30%" class="textLabelBold"><html:text
								property="patient_id" styleId="patient_id"
								styleClass="textBox textBoxLong" maxlength="25"
								 /></td>
							</logic:notEqual>
							 </logic:notEqual>
							 </tr> 
		               <!-- END PATIENT ID -->
					
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
						  <td width="30%" class="textLabel"> <html:text
								 property="policyNumber"  styleId="policyNumber"  readonly="true"
								 styleClass="textBox textBoxLong textBoxDisabled" />&nbsp;&nbsp;&nbsp;<a href="#" style="color:#0c48a2;font-family:Verdana, Arial, Helvetica, sans-serif;font-size:11px;font-weight:bold;"  onClick="javascript:onUploadDocs()"> Policy Docs </a>
							 <html:hidden property="policySeqId"
								styleId="policySeqId" /></td>
						<td class="formLabel"><logic:equal value="COR"
								name="frmClaimGeneral" property="policyType">
			      Corporate Name:
			     </logic:equal></td>
						<td width="30%" class="textLabel"><logic:equal value="COR"
								name="frmClaimGeneral" property="policyType">
								<html:text property="corporateName" styleId="corporateName"
									styleClass="textBox textBoxLarge textBoxDisabled"
									readonly="true" />
							</logic:equal></td>
					</tr>
					<tr>
					
					<td class="formLabel">Policy Category:</td>
			        <td width="30%" class="textLabel"><html:text
								property="policyCategory"  styleId="policyCategory" readonly="true"
								styleClass="textBox textBoxLong textBoxDisabled" /></td>
					<td></td>
					<td></td>			
				    </tr>
				    
					<tr>
						<td class="formLabel">Payer Id:</td>
						<td width="30%" class="textLabel"><html:text
								property="payerId" styleId="payerId"
								styleClass="textBox textBoxLong textBoxDisabled" readonly="true" />
							<html:hidden property="insSeqId" styleId="insSeqId" /> 
							<html:hidden property="policySeqId" styleId="policySeqId" />
							</td>
						<td class="formLabel">Payer Name:</td>
						<td width="30%" class="textLabel"><html:text
								property="payerName" styleId="payerName"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
					</tr>
					
					
					
					
					
					
					<logic:equal value="ECL" name="frmClaimGeneral" property="modeOfClaim">
					<tr>
						<td class="formLabel">Provider Specified Payer ID:</td>
						<td width="30%" class="textLabel"><html:text
								property="providerSpecifiedPayerId" styleId="providerSpecifiedPayerId"
								styleClass="textBox textBoxLong textBoxDisabled" readonly="true" />
							</td>
						<td class="formLabel">Provider Specified Payer Name:</td>
						<td width="30%" class="textLabel"><html:text
								property="providerSpecifiedPayerName" styleId="providerSpecifiedPayerName"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
					</tr>
					
					</logic:equal>
					
					
					
					
					
					
					
					
					<tr>
						<td class="formLabel">Policy Start Date:</td>
						<td width="30%" class="textLabel"><html:text
								property="policyStartDate" styleId="policyStartDate"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>
						<td class="formLabel">Policy End Date:</td>
						<td width="30%" class="textLabel"><html:text
								property="policyEndDate" styleId="policyEndDate"
								styleClass="textBox textBoxMedium textBoxDisabled"
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
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>
					</tr>
					<tr>
						<td class="formLabel">Nationality:</td>
						<td width="30%" class="textLabel"><html:text
								property="nationality" styleId="nationality"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>
						<td class="formLabel">Account No.:</td>
						<td class="textLabel"><html:text property="accountNumber"
								styleClass="textBox textBoxLarge textBoxDisabled" maxlength="60"
								readonly="true" /></td>
					</tr>
					<tr>
					
                       <td class="formLabel">Member Network:</td>
						<td width="30%" class="textLabel"><html:text
								property="eligibleNetworks"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
								<td class="formLabel">VIP:</td>
								<td width="30%" class="textLabel"><html:text indexed="vipYorN" styleId="vipYorN"
								property="vipYorN"
								styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" /></td>
								</tr>

						
					<tr>
						<td class="formLabel">Benefit Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
								<html:select property="benefitType" styleClass="selectBox selectBoxMedium" 	disabled="<%=submissionMode%>" onchange="setMaternityMode();">
								<html:option value="">Select from list</html:option>
									<html:optionsCollection name="benefitTypes" label="cacheDesc" value="cacheId" />
								</html:select>
							</logic:notEqual>
							<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral">
								<html:select property="benefitType" styleClass="selectBox selectBoxMedium" disabled="true" onchange="setMaternityMode();">
									<html:option value="">Select from list</html:option>
									<html:optionsCollection name="benefitTypes" label="cacheDesc" value="cacheId" />
								</html:select>
							</logic:equal>
						</td>
						
						<td class="formLabel" width="19%" colspan="2"><logic:equal
								name="frmClaimGeneral" value="MTI" property="benefitType">
								<table>
									<tr>
				  <td class="formLabel">Gravida:
				  <html:text name="frmClaimGeneral" property="gravida" onkeyup="isNumeric(this);" styleId="gravida"  styleClass="textBox textBoxTooTiny" maxlength="2"  disabled="<%=submissionMode%>"/>
										</td>
				  <td class="formLabel">Para:
				  <html:text name="frmClaimGeneral" property="para" onkeyup="isNumeric(this);" styleId="para" styleClass="textBox textBoxTooTiny" maxlength="2"  disabled="<%=submissionMode%>"/>
										</td>
				  <td class="formLabel">Live:
				  <html:text name="frmClaimGeneral" property="live" onkeyup="isNumeric(this);" styleId="live" styleClass="textBox textBoxTooTiny" maxlength="2"  disabled="<%=submissionMode%>"/>
										</td>
				  <td class="formLabel">Abortion:
				  <html:text name="frmClaimGeneral" property="abortion" onkeyup="isNumeric(this);" styleId="abortion" styleClass="textBox textBoxTooTiny" maxlength="2"  disabled="<%=submissionMode%>"/>
										</td>
									</tr>
								</table>
							</logic:equal></td>
					</tr>
					
					<tr>
						
					<td></td>
					<td></td>
				
		 	<logic:equal value="ECL" name="frmClaimGeneral"
						property="modeOfClaim">
					 <logic:equal value="DHA" name="frmClaimGeneral"
						property="provAuthority">
					<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType"> 
						<logic:equal value="Y" name="frmClaimGeneral"
						property="memberwtflag"> 
				 	<logic:equal value="3" name="frmClaimGeneral"
						property="encounterTypeId">
					<td class="formLabel">Patient Age:<!-- <span
							class="mandatorySymbol">*</span> --></td>
						<td class="textLabel">
								<%-- <html:text property="patientyr"
									styleClass="textBox textBoxTooTiny textBoxDisabled"
									readonly="true" /> years&nbsp;&nbsp;
									<html:text property="patientmonth"
									styleClass="textBox textBoxTooTiny textBoxDisabled"
									readonly="true" />months&nbsp;&nbsp; --%>
									<html:text property="patientdays"
									styleClass="textBox textBoxSmall textBoxDisabled"
									readonly="true" />days
								</td>
						 </logic:equal> 
						<logic:equal value="4" name="frmClaimGeneral"
						property="encounterTypeId">
				<td class="formLabel">Patient Age:<!-- <span
							class="mandatorySymbol">*</span> --></td>
						<td class="textLabel">
								<%-- <html:text property="patientyr"
									styleClass="textBox textBoxTooTiny textBoxDisabled"
									readonly="true" /> years&nbsp;&nbsp;
									<html:text property="patientmonth"
									styleClass="textBox textBoxTooTiny textBoxDisabled"
									readonly="true" />months&nbsp;&nbsp; --%>
									<html:text property="patientdays"
									styleClass="textBox textBoxSmall textBoxDisabled"
									readonly="true" />days
								</td>
					 		</logic:equal> 
					 		</logic:equal>
					</logic:equal> 
					
						 </logic:equal> 
				 	</logic:equal> 
							
			 	<logic:equal value="PCLM" name="frmClaimGeneral"
						property="modeOfClaim">
						<logic:equal value="DHA" name="frmClaimGeneral"
						property="provAuthority">
						<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">
							<logic:equal value="Y" name="frmClaimGeneral"
						property="memberwtflag"> 
						<logic:equal value="4" name="frmClaimGeneral"
						property="encounterTypeId">
						<td class="formLabel">Patient Age:<!-- <span
							class="mandatorySymbol">*</span> --></td>
							<td class="textLabel">
								<%-- <html:text property="patientyr"
									styleClass="textBox textBoxTooTiny textBoxDisabled"
									readonly="true" /> years&nbsp;&nbsp;
									<html:text property="patientmonth"
									styleClass="textBox textBoxTooTiny textBoxDisabled"
									readonly="true" />months&nbsp;&nbsp; --%>
									<html:text property="patientdays"
									styleClass="textBox textBoxSmall textBoxDisabled"
									readonly="true" />days
								</td>
								</logic:equal>
								<logic:equal value="3" name="frmClaimGeneral"
						property="encounterTypeId">
					<td class="formLabel">Patient Age:<!-- <span
							class="mandatorySymbol">*</span> --></td>
						<td class="textLabel">
								<%-- <html:text property="patientyr"
									styleClass="textBox textBoxTooTiny textBoxDisabled"
									readonly="true" /> years&nbsp;&nbsp;
									<html:text property="patientmonth"
									styleClass="textBox textBoxTooTiny textBoxDisabled"
									readonly="true" />months&nbsp;&nbsp; --%>
									<html:text property="patientdays"
									styleClass="textBox textBoxSmall textBoxDisabled"
									readonly="true" />days
								</td>
								</logic:equal>
								</logic:equal>
							
						   </logic:equal>
								</logic:equal>
								</logic:equal> 
							
								
								
					</tr>
					
					
					
					<tr>
					<td></td>
					<td></td>
				<logic:equal value="ECL" name="frmClaimGeneral"
						property="modeOfClaim">
					<logic:equal value="DHA" name="frmClaimGeneral"
						property="provAuthority">
						<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">
							<logic:equal value="Y" name="frmClaimGeneral"
						property="memberwtflag"> 
						<logic:equal value="3" name="frmClaimGeneral"
						property="encounterTypeId">
						
					<td class="formLabel">Member/Patient Weight: <span
							class="mandatorySymbol">*</span> </td>
						<td width="30%" class="textLabel"><html:text  styleId="memberWeight"
								property="memberWeight" styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" />kgs</td>
								</logic:equal>
								<logic:equal value="4" name="frmClaimGeneral"
						property="encounterTypeId">
					<td class="formLabel">Member/Patient Weight:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabel"><html:text  styleId="memberWeight"
								property="memberWeight" styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true"/>kgs</td>
								</logic:equal>
								</logic:equal>
								</logic:equal>
								</logic:equal>
						</logic:equal>
							
				 	<logic:equal value="PCLM" name="frmClaimGeneral"
						property="modeOfClaim">
						<logic:equal value="DHA" name="frmClaimGeneral"
						property="provAuthority">
						<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">
							<logic:equal value="Y" name="frmClaimGeneral"
						property="memberwtflag"> 
						<logic:equal value="4" name="frmClaimGeneral"
						property="encounterTypeId">
					<td class="formLabel">Member/Patient Weight:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabel"><html:text  styleId="memberWeight"
								property="memberWeight" styleClass="textBox textBoxSmall"/>kgs</td>
								</logic:equal>
								<logic:equal value="3" name="frmClaimGeneral"
						property="encounterTypeId">
					<td class="formLabel">Member/Patient Weight:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabel"><html:text  styleId="memberWeight"
								property="memberWeight" styleClass="textBox textBoxSmall"/>kgs</td>
								</logic:equal>
								</logic:equal>
								</logic:equal>
								</logic:equal>
								</logic:equal> 
								
								
					</tr>
					
				<tr>
					<td class="formLabel">Patient type:</td>
					<td class="textLabel">
						<html:select property="patientType" styleClass="selectBox selectBoxMedium">
						<html:option value="">Select from list</html:option>
							<html:optionsCollection name="patientTypes" label="cacheDesc" value="cacheId" />
						</html:select>
					</td>
				</tr>
				
					<tr>
					
<td class="formLabel">Member First Inception Date: </td>
						<td width="30%" class="textLabel"><html:text
								property="clmMemInceptionDate" styleId="clmMemInceptionDate"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" />
						</td>
					<td class="formLabel">Member Exit Date:</td>
						<td width="30%" class="textLabel"><html:text
								property="clmMemExitDate" styleId="clmMemExitDate"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" />
						</td>	
						
					</tr>
					
					<tr>
					
<td class="formLabel">PED Description: </td>
						<td width="30%" class="textLabel"><html:text
								property="clmPedDescription" styleId="clmPedDescriptionId"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" />
						</td>
					<td class="formLabel">Maternity Description:</td>
						<td width="30%" class="textLabel"><html:text
								property="clmMaterinityDescription" styleId="clmMaterinityDescriptionId"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" />
						</td>	
						
					</tr>
					
				        <bean:define id="processTypeTemp" property="processType" name="frmClaimGeneral"/>
					    <bean:define id="claimTypeTemp" property="claimType" name="frmClaimGeneral"/>	
					     <bean:define id="networkProviderTypeTemp" property="networkProviderType" name="frmClaimGeneral"/>
			
			<%if("CTM".equals(claimTypeTemp)&&"DBL".equals(processTypeTemp)){ %>	
			
			
			<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
			
			<tr>
	<td class="formLabel">Requested Amount(VAT Added):</td>
		<td class="textLabel"><html:text property="requestedAmount"
								styleClass="textBox textBoxMedium " styleId="requestedAmount" onkeyup="isNumeric(this);"
								 />&nbsp; <html:text property="requestedAmountcurrencyType" styleId="requestedAmountcurrencyType" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />&nbsp; <a href="#" onclick="openRadioList('requestedAmountcurrencyType','CURRENCY_GROUP','option');" onblur="AEDValidation();">
		          <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" title="Select Currency" border="0" align="bottom" > </a></td>
		          
		          
		          
		          
	
				</tr>	
				</logic:notEqual>
				<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral">
					<tr>

<td class="formLabel">Requested Amount:</td>
						<td class="textLabel"><html:text property="requestedAmount"
								styleClass="textBox textBoxMedium textBoxDisabled" styleId="requestedAmount"
								readonly="true" />&nbsp; <html:text property="requestedAmountcurrencyType" styleId="requestedAmountcurrencyType" styleClass="textBox textBoxTooTiny textBoxDisabled" value="OMR" readonly="true" />&nbsp; <a href="#" onclick="openRadioList('requestedAmountcurrencyType','CURRENCY_GROUP','option');" onblur="AEDValidation();">
		          <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" title="Select Currency" border="0" align="bottom" > </a></td>
</tr>
</logic:equal>
			
	        <%}
			else {%>	

<%if("GBL".equals(processTypeTemp) && "CTM".equals(claimTypeTemp)&& "N".equals(networkProviderTypeTemp)){ %>	

<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
  <logic:equal value="123" property="providerCountry" name="frmClaimGeneral">
  <tr>
<td class="formLabel">Requested Amount:</td>
						<td class="textLabel"><html:text property="requestedAmount"
								styleClass="textBox textBoxMedium textBoxDisabled" styleId="requestedAmount"
								readonly="true" />&nbsp; <html:text property="requestedAmountcurrencyType" styleId="requestedAmountcurrencyType" styleClass="textBox textBoxTooTiny textBoxDisabled" value="OMR" readonly="true" />&nbsp; <a href="#" onclick="openRadioList('requestedAmountcurrencyType','CURRENCY_GROUP','option');" onblur="AEDValidation();">
		          <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" title="Select Currency" border="0" align="bottom" > </a></td>


</tr>
  </logic:equal>
  <logic:notEqual value="123" property="providerCountry" name="frmClaimGeneral">
  	<tr>
	<td class="formLabel">Requested Amount(VAT Added):</td>
		<td class="textLabel"><html:text property="requestedAmount"
								styleClass="textBox textBoxMedium " styleId="requestedAmount" onkeyup="isNumeric(this);"
								 />&nbsp; <html:text property="requestedAmountcurrencyType" styleId="requestedAmountcurrencyType" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />&nbsp; <a href="#" onclick="openRadioList('requestedAmountcurrencyType','CURRENCY_GROUP','option');" onblur="AEDValidation();">
		          <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency"  title="Select Currency" border="0" align="bottom" > </a></td>
	
				</tr>	
  
  </logic:notEqual>
</logic:notEqual>
 <logic:equal  value="ECL" property="modeOfClaim" name="frmClaimGeneral">
 	<tr>
<td class="formLabel">Requested Amount:</td>
						<td class="textLabel"><html:text property="requestedAmount"
								styleClass="textBox textBoxMedium textBoxDisabled" styleId="requestedAmount"
								readonly="true" />&nbsp; <html:text property="requestedAmountcurrencyType" styleId="requestedAmountcurrencyType" styleClass="textBox textBoxTooTiny textBoxDisabled" value="OMR" readonly="true" />&nbsp; <a href="#" onclick="openRadioList('requestedAmountcurrencyType','CURRENCY_GROUP','option');" onblur="AEDValidation();">
		          <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" title="Select Currency" border="0" align="bottom" > </a></td>


</tr>
 </logic:equal>
 <%}
			else {%>	
			
			<tr>
<td class="formLabel">Requested Amount:</td>
						<td class="textLabel"><html:text property="requestedAmount"
								styleClass="textBox textBoxMedium textBoxDisabled" styleId="requestedAmount"
								readonly="true" />&nbsp; <html:text property="requestedAmountcurrencyType" styleId="requestedAmountcurrencyType" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />&nbsp; <a href="#" onclick="openRadioList('requestedAmountcurrencyType','CURRENCY_GROUP','option');" onblur="AEDValidation();">
		          <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" title="Select Currency" border="0" align="bottom" > </a></td>


</tr>


			 <%}%>

			<% }%>	

					<tr>
						<td class="formLabel" width="19%">Encounter Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
								<html:select property="encounterTypeId"
								styleClass="selectBox selectBoxLarge" onchange="setDateMode();"
								disabled="<%=submissionMode%>">
								<logic:notEmpty name="encounterTypes" scope="session">
									<html:optionsCollection name="encounterTypes" value="key"
										label="value" />
								</logic:notEmpty>
								</html:select>
							</logic:notEqual>	
							<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
								<html:select property="encounterTypeId"
									styleClass="selectBox selectBoxLarge" onchange="setDateMode();"
									disabled="true">
									<logic:notEmpty name="encounterTypes" scope="session">
										<html:optionsCollection name="encounterTypes" value="key"
											label="value" />
									</logic:notEmpty>
								</html:select>
							</logic:equal>	
						</td>
						<td class="formLabel">Converted Amount:</td>
						<td class="textLabel"> <html:text property="convertedAmount" styleId="convertedAmount"  styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" onblur="caluculateReqVatAmt();" /> &nbsp;
						<html:text property="currencyType" styleId="totalAmountCurrency" value="OMR"  styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" /> <!-- <a href="#" onclick="openRadioList('totalAmountCurrency','CURRENCY_GROUP','option')">
		          <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" border="0" align="bottom" > </a> --></td>
					</tr>
					
					<tr>
							<td class="formLabel">Reference Number:</td>
							<td class="textLabel"><html:text property="referenceNo"
									styleClass="textBox textBoxLong"/>
							</td>
						<td class="formLabel" width="19%">Conversion Rate:</td> 
						<logic:notEmpty property="conversionRate" name="frmClaimGeneral">
						<logic:equal property="requestedAmountcurrencyType" name="frmClaimGeneral" value="OMR">
						<td class="textLabel"> <table> <tr><td> <input type="checkbox" id="converionRateYN" checked="checked"   onclick="enableConversionRate();caluculateReqVatAmt();"  /></td><td><div id="conversionRatediv" style="display:" > <html:text property="conversionRate" styleId="conversionRate" onkeyup="isNumber(this,'Conversion Rate'); clearField();AEDValidation();caluculateReqVatAmt();"  styleClass="textBox textBoxSmall textBoxDisabled" readonly="true" /></div></td> </tr></table> </td>
						</logic:equal>
						<logic:notEqual property="requestedAmountcurrencyType" name="frmClaimGeneral" value="OMR">
						<td class="textLabel"> <table> <tr><td> <input type="checkbox" id="converionRateYN" checked="checked" onclick="enableConversionRate();caluculateReqVatAmt();" /></td><td><div id="conversionRatediv" style="display:" > <html:text property="conversionRate" styleId="conversionRate" onkeyup="isNumber(this,'Conversion Rate'); clearField();AEDValidation();caluculateReqVatAmt();"  styleClass="textBox textBoxSmall" /></div></td> </tr></table> </td>
						</logic:notEqual>
						</logic:notEmpty>
						
						<logic:empty property="conversionRate" name="frmClaimGeneral">
						<logic:equal property="requestedAmountcurrencyType" name="frmClaimGeneral" value="OMR">
						<td class="textLabel"> <table> <tr><td> <input type="checkbox" id="converionRateYN"  onclick="enableConversionRate();caluculateReqVatAmt();" /></td><td><div id="conversionRatediv" style="display:none;" > <html:text property="conversionRate" onkeyup="isNumber(this,'Conversion Rate'); clearField();AEDValidation();caluculateReqVatAmt();" styleClass="textBox textBoxSmall textBoxDisabled" readonly="true" /></div></td> </tr></table> </td>
						</logic:equal>
						<logic:notEqual property="requestedAmountcurrencyType" name="frmClaimGeneral" value="OMR">
						<td class="textLabel"> <table> <tr><td> <input type="checkbox" id="converionRateYN"  onclick="enableConversionRate();caluculateReqVatAmt();" /></td><td><div id="conversionRatediv" style="display:none;" > <html:text property="conversionRate" onkeyup="isNumber(this,'Conversion Rate'); clearField(); AEDValidation();caluculateReqVatAmt();" styleClass="textBox textBoxSmall" /></div></td> </tr></table> </td>
						</logic:notEqual>
						</logic:empty>
					</tr> 
					
					
					
					
					
					
					
						<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral">
					<tr>
					<td class="formLabel">Claim VAT[OMR]:</td>
					<td><html:text property="claimVatAED" styleClass="textBox textBoxMedium textBoxDisabled" styleId="claimVatAED" readonly="true"/>
					</td>
					<td class="formLabel">Requested Amount(VAT Added):</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />
					<html:text property="currencyType" styleId="requestedAmountcurrencyType" value="OMR" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />
					</td>
					</tr>
					</logic:equal>
					
					<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
					
					
					 <logic:equal value="GBL" property="processType" name="frmClaimGeneral"> 
					  <logic:equal value="CNH" property="claimType" name="frmClaimGeneral">
					  <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" >
					<tr>
					<td class="formLabel">Claim VAT[OMR]:<span class="mandatorySymbol">*</span></td>
					<td><html:text property="claimVatAED" styleClass="textBox textBoxMedium" styleId="claimVatAED" onkeyup="isPositiveNumber(this,'Claim VAT[OMR]');caluculateReqVatAmt();" />
					</td>
					<td class="formLabel">Requested Amount(VAT Added):</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />
					<html:text property="currencyType" styleId="currencyType" value="OMR" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />
					</td>
					</tr>
					 </logic:equal>
					 <logic:notEqual  value="HAAD" property="provAuthority" name="frmClaimGeneral" >
					 <tr>
					<td class="formLabel">Claim VAT[OMR]:<span class="mandatorySymbol">*</span></td>
					<td><html:text property="claimVatAED" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" styleId="claimVatAED" onkeyup="isPositiveNumber(this,'Claim VAT[OMR]');caluculateReqVatAmt();" />
					</td>
					<td class="formLabel">Requested Amount(VAT Added):</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />
					<html:text property="currencyType" styleId="currencyType" value="OMR" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />
					</td>
					</tr>
					</logic:notEqual>
					 </logic:equal> 
					 <logic:equal value="CTM" property="claimType" name="frmClaimGeneral">
					 <logic:equal value="Y" name="frmClaimGeneral" property="networkProviderType">
					  <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" >
					<tr>
					<td class="formLabel">Claim VAT[OMR]:<span class="mandatorySymbol">*</span></td>
					<td><html:text property="claimVatAED" styleClass="textBox textBoxMedium" styleId="claimVatAED" onkeyup="isPositiveNumber(this,'Claim VAT[OMR]');caluculateReqVatAmt();" />
					</td>
					<td class="formLabel">Requested Amount(VAT Added):</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />
					<html:text property="currencyType" styleId="currencyType" value="OMR" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />
					</td>
					</tr>
					 </logic:equal>
					 <logic:notEqual  value="HAAD" property="provAuthority" name="frmClaimGeneral" >
					 <tr>
					<td class="formLabel">Claim VAT[OMR]:<span class="mandatorySymbol">*</span></td>
					<td><html:text property="claimVatAED" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" styleId="claimVatAED" onkeyup="isPositiveNumber(this,'Claim VAT[OMR]');caluculateReqVatAmt();" />
					</td>
					<td class="formLabel">Requested Amount(VAT Added):</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />
					<html:text property="currencyType" styleId="currencyType" value="OMR" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />
					</td>
					</tr>
					</logic:notEqual>
					 </logic:equal>
					 <logic:equal value="N" name="frmClaimGeneral" property="networkProviderType">
					  <logic:equal value="123" property="providerCountry" name="frmClaimGeneral">
					   <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" >
					<tr>
					<td class="formLabel">Claim VAT[OMR]:<span class="mandatorySymbol">*</span></td>
					<td><html:text property="claimVatAED" styleClass="textBox textBoxMedium" styleId="claimVatAED" onkeyup="isPositiveNumber(this,'Claim VAT[OMR]');caluculateReqVatAmt();" />
					</td>
					<td class="formLabel">Requested Amount(VAT Added):</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />
					<html:text property="currencyType" styleId="currencyType" value="OMR" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />
					</td>
					</tr>
					 </logic:equal>
					 <logic:notEqual  value="HAAD" property="provAuthority" name="frmClaimGeneral" >
					 <tr>
					<td class="formLabel">Claim VAT[OMR]:<span class="mandatorySymbol">*</span></td>
					<td><html:text property="claimVatAED" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" styleId="claimVatAED" onkeyup="isPositiveNumber(this,'Claim VAT[OMR]');caluculateReqVatAmt();" />
					</td>
					<td class="formLabel">Requested Amount(VAT Added):</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />
					<html:text property="currencyType" styleId="currencyType" value="OMR" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />
					</td>
					</tr>
					</logic:notEqual>
					</logic:equal>
					 </logic:equal>
					 </logic:equal>
					 </logic:equal>
					 
					 <logic:equal value="DBL" property="processType" name="frmClaimGeneral"> 
					 <logic:equal value="CNH" property="claimType" name="frmClaimGeneral">
					  <logic:equal value="HAAD" property="provAuthority" name="frmClaimGeneral" >
					<tr>
					<td class="formLabel">Claim VAT[OMR]:<span class="mandatorySymbol">*</span></td>
					<td><html:text property="claimVatAED" styleClass="textBox textBoxMedium" styleId="claimVatAED" onkeyup="isPositiveNumber(this,'Claim VAT[OMR]');caluculateReqVatAmt();" />
					</td>
					<td class="formLabel">Requested Amount(VAT Added):</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />
					<html:text property="currencyType" styleId="currencyType" value="OMR" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />
					</td>
					</tr>
					 </logic:equal> 
					
					  <logic:notEqual  value="HAAD" property="provAuthority" name="frmClaimGeneral" >
					 <tr>
					<td class="formLabel">Claim VAT[OMR]:<span class="mandatorySymbol">*</span></td>
					<td><html:text property="claimVatAED" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" styleId="claimVatAED" onkeyup="isPositiveNumber(this,'Claim VAT[OMR]');caluculateReqVatAmt();" />
					</td>
					<td class="formLabel">Requested Amount(VAT Added):</td>
					<td><html:text property="vatAddedReqAmnt" styleClass="textBox textBoxMedium textBoxDisabled" styleId="vatAddedReqAmnt" readonly="true" />
					<html:text property="currencyType" styleId="currencyType" value="OMR" styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />
					</td>
					</tr>
					</logic:notEqual>
					 
					  </logic:equal>
					 </logic:equal> 
					 
					</logic:notEqual>
					
					  <bean:define id="processTypeTemp" property="processType" name="frmClaimGeneral"/>
					    <bean:define id="claimTypeTemp" property="claimType" name="frmClaimGeneral"/>
					     <%--  <bean:define id="paymentToTemp" property="paymentTo" name="frmClaimGeneral"/> --%>
					      
			 	  	   <%String paymentToTemp=""; %>
				 	  <logic:notEmpty property="paymentTo" name="frmClaimGeneral">
					   <bean:define id="paymentToTemp2" property="paymentTo" name="frmClaimGeneral"/>
			 		   <% paymentToTemp=paymentToTemp2.toString(); %>
			 		  </logic:notEmpty>
					      
					<%-- <logic:equal value="DBL" name="frmClaimGeneral" property="processType">
					<logic:equal value="CNH" name="frmClaimGeneral" property="claimType">
					<logic:equal value="PTN" name="frmClaimGeneral" property="paymentTo"> --%>
					
					<%if("DBL".equals(processTypeTemp)&&"CNH".equals(claimTypeTemp)&&"PTN".equals(paymentToTemp)){ %>
					
						<tr>
							<td class="formLabel">Clinician Id:</td>
							<td class="textLabel"><html:text property="clinicianId" styleId="clinicianId"
									styleClass="textBox textBoxLong"/>
							</td>
							<%-- <td class="textLabel">
								<table>
									<tr>
										<td><html:text property="clinicianId"
												styleId="clinicianId"
												styleClass="textBox textBoxLarge"
												/></td>
										<td><!-- <a href="#" accesskey="g"
											onClick="javascript:selectClinician()" class="search"> <img
												src="/ttk/images/EditIcon.gif" alt="Select Clinician"
												width="16" height="16" border="0" align="absmiddle">&nbsp;
										</a> --></td>
									</tr>
								</table> --%>
							<td class="formLabel">Clinician Name:</td>
							<td class="textLabel"><html:text property="clinicianName" styleId="clinicianName"
									styleClass="textBox textBoxLong"/>
							</td>
							<%-- <td><html:text property="clinicianName"
									styleClass="textBox textBoxLarge"
									styleId="clinicianName" /></td> --%>
						</tr>
						<%} %>
					<%-- </logic:equal>
					</logic:equal>
					</logic:equal> --%>
					
					<%-- <logic:equal value="DBL" name="frmClaimGeneral" property="processType">
					<logic:equal value="CNH" name="frmClaimGeneral" property="claimType">
					<logic:equal value="PRV" name="frmClaimGeneral" property="paymentTo"> --%>
					
					 <%if("DBL".equals(processTypeTemp)&&"CNH".equals(claimTypeTemp)&&"PRV".equals(paymentToTemp)){ %>
						<tr>
							<td class="formLabel">Clinician Id:</td>
							<td class="textLabel"><html:text property="clinicianId" styleId="clinicianId"
									styleClass="textBox textBoxLong"/>
							</td>
							<%-- <td class="textLabel">
								<table>
									<tr>
										<td><html:text property="clinicianId"
												styleId="clinicianId"
												styleClass="textBox textBoxLarge"
												readonly="true" /></td>
										<td> <a href="#" accesskey="g"
											onClick="javascript:selectClinician()" class="search"> <img
												src="/ttk/images/EditIcon.gif" alt="Select Clinician" title="Select Clinician"
												width="16" height="16" border="0" align="absmiddle">&nbsp;
										</a></td>
									</tr>
								</table> --%>
							<td class="formLabel">Clinician Name:</td>
							<td class="textLabel"><html:text property="clinicianName" styleId="clinicianName"
									styleClass="textBox textBoxLong"/>
							</td>
							<%-- <td><html:text property="clinicianName"
									styleClass="textBox textBoxLarge"
									styleId="clinicianName" readonly="true" /></td> --%>
						</tr>
					<%-- </logic:equal>
					</logic:equal>
					</logic:equal> --%>
						<%} %>
					
					
					<%if("DBL".equals(processTypeTemp)&&"CTM".equals(claimTypeTemp)){ %>
					
					
					<logic:equal value="Y" name="frmClaimGeneral"
							property="networkProviderType">
							<tr>
								<td class="formLabel">Clinician Id:</td>
								<td class="textLabel"><html:text property="clinicianId" styleId="clinicianId"
									styleClass="textBox textBoxLong"/>
							</td>
								<%-- <td class="textLabel">
									<table>
										<tr>
											<td><html:text property="clinicianId"
													styleId="clinicianId"
													styleClass="textBox textBoxLarge textBoxDisabled"
													readonly="true" /></td>
											<td><a href="#" accesskey="g"
												onClick="javascript:selectClinician()" class="search"> <img
													src="/ttk/images/EditIcon.gif" alt="Select Clinician"  title="Select Clinician"
													width="16" height="16" border="0" align="absmiddle">&nbsp;
											</a></td>
										</tr>
									</table> --%>
								<td class="formLabel">Clinician Name:</td>
								<td class="textLabel"><html:text property="clinicianName" styleId="clinicianName"
									styleClass="textBox textBoxLong"/>
							</td>
								<%-- <td><html:text property="clinicianName"
										styleClass="textBox textBoxLarge textBoxDisabled"
										styleId="clinicianName" readonly="true" /></td> --%>
							</tr>
						</logic:equal>

						<logic:equal value="N" name="frmClaimGeneral"
							property="networkProviderType">
							<tr>
								<td class="formLabel">Clinician Name:</td>
								<td><html:text property="clinicianName"
										styleClass="textBox textBoxLarge" styleId="clinicianName"
										disabled="<%=submissionMode%>" /></td>
								<td class="formLabel"></td>
								<td class="textLabel"></td>
							</tr>
						</logic:equal>
					
					<%} %>
					
					
					
					<%-- <logic:equal value="GBL" name="frmClaimGeneral" property="processType">
					<logic:equal value="CNH" name="frmClaimGeneral" property="claimType"> --%>
					 <%if("GBL".equals(processTypeTemp)&&"CNH".equals(claimTypeTemp)){ %>
					<tr>
							<td class="formLabel">Clinician Id:</td>
							<td class="textLabel"><html:text property="clinicianId" styleId="clinicianId"
									styleClass="textBox textBoxLong"/>
							</td>
							<%-- <td class="textLabel">
								<table>
									<tr>
										<td><html:text property="clinicianId"
												styleId="clinicianId"
												styleClass="textBox textBoxLarge textBoxDisabled"
												readonly="true" /></td>
										<td><a href="#" accesskey="g"
											onClick="javascript:selectClinician()" class="search"> <img
												src="/ttk/images/EditIcon.gif" alt="Select Clinician"  title="Select Clinician"
												width="16" height="16" border="0" align="absmiddle">&nbsp;
										</a></td>
									</tr>
								</table> --%>
							<td class="formLabel">Clinician Name:</td>
							<td class="textLabel"><html:text property="clinicianName" styleId="clinicianName"
									styleClass="textBox textBoxLong"/>
							</td>
							<%-- <td><html:text property="clinicianName"
									styleClass="textBox textBoxLarge textBoxDisabled"
									styleId="clinicianName" readonly="true" /></td> --%>
						</tr>
					
					<%-- </logic:equal>
					</logic:equal>
					 --%>
					<%} %>
					
					
					<%-- <logic:equal value="GBL" name="frmClaimGeneral" property="processType">
					<logic:equal value="CTM" name="frmClaimGeneral" property="claimType"> --%>
					 <%if("GBL".equals(processTypeTemp)&&"CTM".equals(claimTypeTemp)){ %>
						<logic:equal value="Y" name="frmClaimGeneral"
							property="networkProviderType">
							<tr>
								<td class="formLabel">Clinician Id:</td>
								<td class="textLabel"><html:text property="clinicianId" styleId="clinicianId"
									styleClass="textBox textBoxLong"/>
							</td>
								<%-- <td class="textLabel">
									<table>
										<tr>
											<td><html:text property="clinicianId"
													styleId="clinicianId"
													styleClass="textBox textBoxLarge textBoxDisabled"
													readonly="true" /></td>
											<td><a href="#" accesskey="g"
												onClick="javascript:selectClinician()" class="search"> <img
													src="/ttk/images/EditIcon.gif" alt="Select Clinician" title="Select Clinician"
													width="16" height="16" border="0" align="absmiddle">&nbsp;
											</a></td>
										</tr>
									</table> --%>
								<td class="formLabel">Clinician Name:</td>
								<td class="textLabel"><html:text property="clinicianName" styleId="clinicianName"
									styleClass="textBox textBoxLong"/>
							</td>
								<%-- <td><html:text property="clinicianName"
										styleClass="textBox textBoxLarge textBoxDisabled"
										styleId="clinicianName" readonly="true" /></td> --%>
							</tr>
						</logic:equal>

						<logic:equal value="N" name="frmClaimGeneral"
							property="networkProviderType">
							<tr>
								<td class="formLabel">Clinician Name:</td>
								<td><html:text property="clinicianName"
										styleClass="textBox textBoxLarge" styleId="clinicianName"
										disabled="<%=submissionMode%>" /></td>
								<td class="formLabel"></td>
								<td class="textLabel"></td>
							</tr>
						</logic:equal>
					<%-- </logic:equal>
					</logic:equal> --%>
					<%} %>
					
					
					<tr>
					<td class="formLabel">Clinician Consultation :</td>
					<td>
					<html:select property="consultationType"	styleClass="selectBox selectBoxMedium">
					<html:option value="">Select from list</html:option>
					<html:optionsCollection name="consultationTypes" label="cacheDesc"	value="cacheId" />
					</html:select>
					</td>
					<td></td>
					<td></td>
					</tr>
					<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
					<tr>
						<td class="formLabel" width="19%">Encounter Start Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select
								property="encounterStartTypeId"
								styleClass="selectBox selectBoxLarge"
								disabled="<%=submissionMode%>">
								<html:optionsCollection name="encounterStartTypes"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
						<td class="formLabel" width="19%">Encounter End Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">
								<html:select
								property="encounterEndTypeId"
								styleClass="selectBox selectBoxLarge"
								disabled="<%=submissionMode%>">
								<html:optionsCollection name="encounterEndTypes"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
					</tr>
					</logic:notEqual>
					
					<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral">
					<tr>
						<td class="formLabel" width="19%">Encounter Start Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select
								property="encounterStartTypeId"
								styleClass="selectBox selectBoxLarge"
								disabled="true" readonly="true">
								<html:optionsCollection name="encounterStartTypes"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
						<td class="formLabel" width="19%">Encounter End Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">
								<html:select
								property="encounterEndTypeId"
								styleClass="selectBox selectBoxLarge"
								disabled="true" readonly="true">
								<html:optionsCollection name="encounterEndTypes"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
					</tr>
					</logic:equal>
					
					
					<tr>
						<td class="formLabel">Start Date:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td>
									<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
											<html:text name="frmClaimGeneral"
											property="admissionDate" styleClass="textBox textDate"
											maxlength="10" onblur="setEndDate();clearConversionRate();"
											disabled="<%=submissionMode%>"/> 
									</logic:notEqual>
									<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral">
											<html:text name="frmClaimGeneral"
											property="admissionDate" styleClass="textBox textDate"
											maxlength="10" onblur="setEndDate();clearConversionRate();"
											disabled="true" readonly="true"/> 
									</logic:equal>
											
											
									<logic:notEqual	value="ENHANCEMENT" property="preAuthNoStatus" name="frmClaimGeneral">
											
										<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
											<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#"
												onClick="javascript:show_calendar('CalendarObjectPARDate','frmClaimGeneral.admissionDate',document.frmClaimGeneral.admissionDate.value,'',event,148,178);return false;"
												onkeyup="clearConversionRate();"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar"  title="Calendar"
												name="empDate" width="24" height="17" border="0"
												align="absmiddle"></a>
										</logic:notEqual>
										<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
												<img src="/ttk/images/CalendarIcon.gif" title="Calendar" name="empDate" width="24" height="17" border="0"
												align="absmiddle"></a>
										</logic:equal>
												
										</logic:notEqual>&nbsp;</td>
									
									
									<td>
											<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
												<html:text name="frmClaimGeneral" styleId="admissionTime"
											property="admissionTime" styleClass="textBox textTime"
											maxlength="5" onblur="setEndDate();"
												disabled="<%=submissionMode%>" />&nbsp;
											</logic:notEqual>
											<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
												<html:text name="frmClaimGeneral" styleId="admissionTime"
												property="admissionTime" styleClass="textBox textTime"
												maxlength="5" onblur="setEndDate();"
												disabled="true" readonly="true"/>&nbsp;
											</logic:equal>
									</td>
									<td>
											<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
											<html:select property="admissionDay"
											name="frmClaimGeneral" styleClass="selectBox"
											onchange="setEndDate();" disabled="<%=submissionMode%>">
											<html:options name="ampm" labelName="ampm" />
											</html:select>
											</logic:notEqual>
											
											<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
											<html:select property="admissionDay"
											name="frmClaimGeneral" styleClass="selectBox"
											onchange="setEndDate();" disabled="true">
											<html:options name="ampm" labelName="ampm" />
											</html:select>
											</logic:equal>
									</td>
								</tr>
							</table>
						</td>
						<td class="formLabel">End Date:<span class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td>
										<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
	
											<html:text name="frmClaimGeneral"
											property="dischargeDate"  styleId="dischargeDate"   disabled="<%=submissionMode%>"
											styleClass="textBox textDate" maxlength="10"  onblur="endDateValidation();clearConversionRate();" />
										</logic:notEqual>
										<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
	
											<html:text name="frmClaimGeneral"
											property="dischargeDate"  styleId="dischargeDate"   disabled="true" readonly="true"
											styleClass="textBox textDate" maxlength="10"  onblur="endDateValidation();clearConversionRate();" />
										</logic:equal>	
											 
											<logic:match name="viewmode" value="false">
												<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 

											<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#" onClick="checkCalender();"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar"
												id="discImg" name="empDate" width="24" height="17"
												border="0" align="absmiddle"></a>
												</logic:notEqual>
												
												<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
													<img src="/ttk/images/CalendarIcon.gif" title="Calendar" 
													id="discImg" name="empDate" width="24" height="17"
													border="0" align="absmiddle">
												</logic:equal>
										</logic:match>&nbsp;</td>
									<td>
										<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
											<html:text name="frmClaimGeneral"
											property="dischargeTime" disabled="<%=submissionMode%>"
											styleClass="textBox textTime" maxlength="5" />&nbsp;
										</logic:notEqual>	
										
										<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
											<html:text name="frmClaimGeneral"
											property="dischargeTime" disabled="true" readonly="true"
											styleClass="textBox textTime" maxlength="5" />&nbsp;
										</logic:equal>	
									</td>
									<td>
										<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
											<html:select property="dischargeDay"
											name="frmClaimGeneral" disabled="<%=submissionMode%>"
											styleClass="selectBox">
											<html:options name="ampm" labelName="ampm" />
											</html:select>
										</logic:notEqual>
										<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
											<html:select property="dischargeDay"
												name="frmClaimGeneral" disabled="true"
												styleClass="selectBox">
												<html:options name="ampm" labelName="ampm" />
											</html:select>
										</logic:equal>		
										
									</td>
								</tr>
							</table>
						</td>
					</tr> 
					<logic:equal value="CTM" name="frmClaimGeneral" property="claimType">
					
					<tr>
					<td class="formLabel">RI Copar:<span class="mandatorySymbol">*</span></td>
					
					 <td class="textLabel">
					<%--  <html:checkbox name="frmClaimGeneral" property="enablericopar" value="Y" /> --%>
					 <html:select
								name="frmClaimGeneral" property="enablericopar"
								styleClass="selectBox selectBoxMedium">
								<html:option value="">Select From List</html:option>
								<html:option value="Y">YES</html:option>
								<html:option value="N">NO</html:option>
							</html:select>
					
					 </td>
					 
					 <td class="formLabel">UCR :<span class="mandatorySymbol">*</span></td>
					
					 <td class="textLabel">
					<%--  <html:checkbox name="frmClaimGeneral" property="enablericopar" value="Y" /> --%>
					 <html:select
								name="frmClaimGeneral" property="enableucr"
								styleClass="selectBox selectBoxMedium">
								<html:option value="">Select From List</html:option>
								<html:option value="Y">YES</html:option>
								<html:option value="N">NO</html:option>
							</html:select>
					
					 </td> 
					</tr>
					
				</logic:equal>
					
					
					
					
					
					<logic:equal value="CNH" name="frmClaimGeneral"
						property="claimType">
						<tr>
							<td class="formLabel">Authorization No.:</td>
							<td class="textLabel">
								<table>
									<tr>
							<logic:empty name="frmClaimGeneral" property="authNum">
											<td><html:text property="authNum"
												styleClass="textBox textBoxLarge textBoxDisabled"
												readonly="true" /></td>
										</logic:empty >	
										<logic:notEmpty name="frmClaimGeneral" property="authNum">
												<td>
													<a href="#" onClick="javascript:viewPreauthGeneral();">
													<bean:write name="frmClaimGeneral" property="authNum"/></a>
												</td>
										</logic:notEmpty>			
										<td>&nbsp;&nbsp;<a href="#" accesskey="g"
											onClick="javascript:selectAuthorizationdetails()"
											class="search"> <img src="/ttk/images/EditIcon.gif"
												alt="Select Authorization Details" title="Select Authorization Details" width="16" height="16"
													border="0" align="absmiddle"></a>
											</td>
										
										<logic:notEmpty name="frmClaimGeneral" property="authNum">		
											<td>
												<a href="#"  onClick="delAuthorizationdetails();" > 
												<img src="/ttk/images/DeleteIcon.gif" alt="Delink Prior Authorization from Claims"  title="Delink Prior Authorization from Claims" width="16" height="16" border="0" align="absmiddle"></a>
											</td>		
										</logic:notEmpty>		
												
												
									</tr>
								</table>
							</td>
							
							<logic:equal value="DBL" property="processType" name="frmClaimGeneral">
							<td class="formLabel">Pre-Auth Approved Amt:<br>in Incurred Currency
							</td>
							<td class="textLabel"><html:text property="preAuthIncurredAmt"
									styleClass="textBox textBoxSmall textBoxDisabled"
									readonly="true" />&nbsp;
							<html:text property="preAuthIncurredCurrencyType" styleId="preAuthIncurredCurrencyType"  styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />
									
									</td>
								</logic:equal>	
						
						</tr>
						<tr>
							<td class="formLabel">PreApproved Date :</td>
							<td class="textLabel"><html:text
									property="preAuthReceivedDateAsString"
									styleClass="textBox textBoxMedium PreApproved Date :"
									readonly="true" /></td>
									
									
									
									
								<td class="formLabel">Pre-Auth Approved Amt:<br>in OMR
							</td>
							<td class="textLabel"><html:text property="preAuthApprAmt"
									styleClass="textBox textBoxSmall textBoxDisabled"
									readonly="true" />&nbsp;
							<html:text property="preAuthApprAmtCurrency" styleId="preAuthApprAmtCurrency"  styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" value="OMR"/>
									
									</td>	
									
									
									
									
							 
							
						</tr>
					</logic:equal>
					<tr>
						<td class="formLabel">Assigned To:</td>

						<td width="40%" class="textLabelBold"><bean:write
								name="frmClaimGeneral" property="assignedTo" /></td>
						<logic:match value="APR"  name="frmClaimGeneral"   property="claimStatus">
							<td class="formLabel">Remittance Advice Uploaded Date:</td>
							<td><html:text name="frmClaimGeneral"
									property="remittanceUploadedDate"
									styleClass="textBox textDate textBoxDisabled" maxlength="10"
									readonly="true" />&nbsp;</td>
						</logic:match>
					   <logic:match value="REJ" name="frmClaimGeneral" property="claimStatus">
							<td class="formLabel">Remittance Advice Uploaded Date:</td>
							<td><html:text name="frmClaimGeneral"
									property="remittanceUploadedDate"
									styleClass="textBox textDate textBoxDisabled" maxlength="10"
									readonly="true" />&nbsp;</td>
						</logic:match>
						<logic:empty  name="claimstatusid" scope="session">
							<td></td>
							<td></td>
						</logic:empty>
					</tr>
					
					
					
					
				<logic:notEmpty name="frmClaimGeneral" property="completed_date">
				<tr>
		 		  <td class="formLabel">Completed Date:</td>
		  		 <td class="textLabel" colspan="3">
		 	    <html:text name="frmClaimGeneral"   property="completed_date"   styleClass="textBox textBoxMedium textBoxDisabled" readonly="true"/>
			   </td>
			    </tr>	
		     </logic:notEmpty>
					
					<tr>
						<td></td>
						<td></td>
						<logic:match value="APR"  name="frmClaimGeneral"   property="claimStatus">
							<td class="formLabel">Remittance Advice File Name:</td>
							<td><html:text name="frmClaimGeneral"
									property="remittanceAdviceFileName" readonly="true"
									styleClass="textBox textBoxLarge" maxlength="5" />&nbsp;</td>
						</logic:match>
						<logic:match value="REJ" name="frmClaimGeneral" property="claimStatus">
							<td class="formLabel">Remittance Advice File Name:</td>
							<td><html:text name="frmClaimGeneral"
									property="remittanceAdviceFileName" readonly="true"
									styleClass="textBox textBoxLarge" maxlength="5" />&nbsp;</td>
						</logic:match>
						<logic:empty  name="frmClaimGeneral" property="claimStatus">
							<td></td>
							<td></td>
						</logic:empty>
					</tr>

					<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">
						<logic:notEmpty name="frmClaimGeneral" property="providerId">
					<tr>
						<td width="20%" class="formLabel">Provider Specific Remarks:</td>
						<td colspan="3"><html:textarea property="providerSpecificRemarks"
								name="frmClaimGeneral" styleClass="textBox textAreaLong textBoxDisabled"
								disabled="true" readonly="true" /></td>
						<td></td>
						<td></td>		
					</tr>
					</logic:notEmpty>
					</logic:equal>
					
					
					<tr>
				    <td class="formLabel">VAT IS INCLUDED WITHIN SUM INSURED(YES/NO):</td>
		  		    <td class="textLabel" colspan="3">
		  		    
		  		    <logic:equal name="frmClaimGeneral"   property="vatFlag"  value="Y">
		 	        <html:text name="frmClaimGeneral"   property="vatFlag"  value="YES" styleClass="textBox textBoxSmall textBoxDisabled" readonly="true"/>
		 	        </logic:equal>
		 	        <logic:notEqual name="frmClaimGeneral"   property="vatFlag"  value="Y">
		 	         <html:text name="frmClaimGeneral"   property="vatFlag"  value="NO" styleClass="textBox textBoxSmall textBoxDisabled" readonly="true"/>
		 	        </logic:notEqual>
		 	        
			        </td>
				        <td></td>
						<td></td>	
					</tr>
					
					
					
					
					
					<tr>
						<td align="center" colspan="4">
							<button type="button" name="Button2" accesskey="s"
								class="buttons" onMouseout="this.className='buttons'"
								onMouseover="this.className='buttons buttonsHover'"
								onClick="onUserSubmit()">
								<u>S</u>ave
							</button>&nbsp; <!-- button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button-->
						</td>
					</tr>
				</table>
			</fieldset>
			<logic:notEmpty name="frmClaimGeneral" property="claimNo">
			
			
			
				<fieldset>
					<legend>Diagnosis Details</legend>
					<table align="center" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th>Presenting Complaints:</th>
							<td><html:textarea name="frmClaimGeneral"
									property="presentingComplaints" cols="50" rows="2" /></td>
						<th>Details of any past history relevant to present illness:</th>
							<td>	
									<html:textarea property="relPresentIllness" cols="50" rows="2" />
							</td>
						</tr>
						<tr>
							<th>Relevant clinical findings:</th>
							<td>		
									<html:textarea property="relevantFindings" cols="50" rows="2" />
							</td>
						</tr>
					</table>
					<br> <br>


					<div align="center" style="align: center;">
						<div
							style="border: 2px solid #a1a1a1; background: #F8F8F8; border-radius: 25px; width: 1100px; height: auto; text-align: center;">
							<br>
							<table align="center">
								<tr>
									<td colspan="3"><div id="icdResult1">
											<div id="icdResult2"></div>
										</div></td>
								</tr>
								<tr>
									<td align="center">ICD Code:<span class="mandatorySymbol">*</span></td>
									<td align="center">Principal:<span class="mandatorySymbol">*</span></td>
									<td align="center">Diagnosis Desription:<span
										class="mandatorySymbol">*</span></td>
									<td class="formLabel">Duration of present Ailment:<span class="mandatorySymbol">*</span></td>
			      					<td  width="20%" class="textLabel">		      
			      					<html:text name="frmClaimGeneral" property="ailmentDuration" styleClass="textBox textBoxSmall" maxlength="3" onkeyup="isNumeric(this);"/>
				 			<html:select
								name="frmClaimGeneral" property="ailmentDurationFlag"
								styleClass="selectBox textBoxSmall">
								<html:option value="DAYS">DAYS</html:option>
								<html:option value="WEEKS">WEEKS</html:option>
								<html:option value="MONTHS">MONTHS</html:option>
								<html:option value="YEARS">YEARS</html:option>
							</html:select>
							</td>
									
										<td style="padding-right: 20px;" width="30%">
											<logic:equal value="DHA" name="frmClaimGeneral"
						property="provAuthority">
						<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">
						<logic:equal value="3" name="frmClaimGeneral"
						property="encounterTypeId">
				           Info Type:<span class="mandatorySymbol">*</span>	
						</logic:equal>
						<logic:equal value="4" name="frmClaimGeneral"
						property="encounterTypeId">
					        Info Type:<span class="mandatorySymbol">*</span>	
						</logic:equal>
						</logic:equal>
						</logic:equal>
						
						</td>
											<td width="30%">
											
                    <logic:equal value="ECL" name="frmClaimGeneral"
						property="modeOfClaim">
					<logic:equal value="DHA" name="frmClaimGeneral"
						property="provAuthority">
						<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">
						<logic:equal value="3" name="frmClaimGeneral"
						property="encounterTypeId">
			             	Info Code:<span class="mandatorySymbol">*</span>
								</logic:equal>
								<logic:equal value="4" name="frmClaimGeneral"
						property="encounterTypeId">
			             	Info Code:<span class="mandatorySymbol">*</span>
								</logic:equal>
								</logic:equal>
								</logic:equal>
								</logic:equal>
								
								
								<logic:equal value="PCLM" name="frmClaimGeneral"
						property="modeOfClaim">
						<logic:equal value="DHA" name="frmClaimGeneral"
						property="provAuthority">
						<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">
						<logic:equal value="3" name="frmClaimGeneral"
						property="encounterTypeId">
					   Info Code:<span class="mandatorySymbol">*</span>								
					    </logic:equal>
					    <logic:equal value="4" name="frmClaimGeneral"
						property="encounterTypeId">
						Info Code:<span class="mandatorySymbol">*</span>	
					    </logic:equal>
						</logic:equal>
					    </logic:equal>
						</logic:equal>
						
						
						
						</td>
								</tr>
								<tr>
									<td align="center">
										<table>
											<tr>
												<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
												<td><html:text name="frmClaimGeneral"
														property="icdCode" styleId="icdCodeFIELD"
														styleClass="textBox textBoxSmall" maxlength="20"
														onblur="getIcdCodeDetails();" /></td>
												<td><a href="#" accesskey="g"
													onClick="javascript:selectDiagnosisCode()" class="search">
														<img src="/ttk/images/EditIcon.gif"
														alt="Select Diagnosis Code" title="Select Diagnosis Code" width="16" height="16"
														border="0" align="absmiddle">&nbsp;
												</a></td>
												</logic:notEqual>
												
												<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
													<td><html:text name="frmClaimGeneral"
															property="icdCode" styleId="icdCodeFIELD"
															styleClass="textBox textBoxSmall" maxlength="20"
															onblur="getIcdCodeDetails();" disabled="true" readonly="true" /></td>
													<td>	<img src="/ttk/images/EditIcon.gif"  width="16" height="16" border="0" align="absmiddle">&nbsp;
													</td>
												</logic:equal>
												
											</tr>
										</table> <html:hidden name="frmClaimGeneral" property="icdCodeSeqId"
											styleId="icdCodeSeqId" /> <html:hidden
											name="frmClaimGeneral" property="diagSeqId"
											styleId="diagSeqId" />
									</td>
									<td align="center">
										<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral">
											<html:checkbox name="frmClaimGeneral" property="primaryAilment" styleId="primaryAilment" value="Y" onclick="getIcdCodeDetails();"/>
										</logic:notEqual>
										<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
											<html:checkbox name="frmClaimGeneral" property="primaryAilment" styleId="primaryAilment" value="Y" onclick="getIcdCodeDetails();" disabled="true"/>
										</logic:equal>
											
									</td>
									<td align="center">
										<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
											<html:textarea rows="1" cols="80"
											name="frmClaimGeneral" styleId="diagnosisDescription"
											property="ailmentDescription" />
										</logic:notEqual>	
										<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
											<html:textarea rows="1" cols="80" name="frmClaimGeneral" styleId="diagnosisDescription"
											property="ailmentDescription" disabled="true"   readonly="true" />
										</logic:equal>	
									</td>
											
											
												<td width="30%" class="textLabel">
												
												<logic:equal value="DHA" name="frmClaimGeneral"
						property="provAuthority">
						<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">
						<logic:equal value="4" name="frmClaimGeneral"
						property="encounterTypeId">
				
					<html:text styleId="infoType"
								property="infoType"
								styleClass="textBox textBoxSmall textBoxDisabled" value="POA"
								readonly="true" />
								</logic:equal>
									<logic:equal value="3" name="frmClaimGeneral"
						property="encounterTypeId">
					
					<html:text styleId="infoType"
								property="infoType"
								styleClass="textBox textBoxSmall textBoxDisabled" value="POA"
								readonly="true" />
								</logic:equal>
								</logic:equal>
								</logic:equal>
											
											
											
											
											</td>
											
											
											<td width="30%" class="textLabel">
											
											
											<logic:equal value="ECL" name="frmClaimGeneral"
						property="modeOfClaim">
					<logic:equal value="DHA" name="frmClaimGeneral"
						property="provAuthority">
						<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">
						<logic:equal value="3" name="frmClaimGeneral"
						property="encounterTypeId">
					<html:text  styleId="infoCode"
								property="infoCode"
								styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" />
								</logic:equal>
								<logic:equal value="4" name="frmClaimGeneral"
						property="encounterTypeId">
					<html:text  styleId="infoCode"
								property="infoCode"
								styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" />
								</logic:equal>
								</logic:equal>
								</logic:equal>
								</logic:equal>
								
								
										<logic:equal value="PCLM" name="frmClaimGeneral"
						property="modeOfClaim">
						<logic:equal value="DHA" name="frmClaimGeneral"
						property="provAuthority">
						<logic:equal value="Y" name="frmClaimGeneral"
						property="networkProviderType">
						<logic:equal value="3" name="frmClaimGeneral"
						property="encounterTypeId">
			
					<html:select property="infoCode" styleId="infoCode"
							styleClass="selectBox selectBoxMedium">
							             <html:option value="">Select from list</html:option>
										<html:option value="Y">Yes</html:option>
									    <html:option value="N">No</html:option>
									    <html:option value="U">Unknown</html:option>
									    <html:option value="W">Clinically Undetermined</html:option>
									    <html:option value="1">Unreported/Not used</html:option>
									    <html:option value="OP">Outpatient claim</html:option>
										<%-- <html:optionsCollection name="memberClaimFrom"
											label="cacheDesc" value="cacheId" /> --%>
									</html:select>
								</logic:equal>
										<logic:equal value="4" name="frmClaimGeneral"
						property="encounterTypeId">
				
					<html:select property="infoCode" styleId="infoCode"
							styleClass="selectBox selectBoxMedium">
							            <html:option value="">Select from list</html:option>
										<html:option value="Y">Yes</html:option>
									    <html:option value="N">No</html:option>
									    <html:option value="U">Unknown</html:option>
									    <html:option value="W">Clinically Undetermined</html:option>
									    <html:option value="1">Unreported/Not used</html:option>
									    <html:option value="OP">Outpatient claim</html:option>
										<%-- <html:optionsCollection name="memberClaimFrom"
											label="cacheDesc" value="cacheId" /> --%>
									</html:select>
								</logic:equal>
								</logic:equal>
								</logic:equal>
								</logic:equal>
								
								
								</td>
								
								</tr>
								<tr>
								<td align="left" colspan="5">
							<bean:define id="tempPreCron" value="display:none;"/>
							
							<logic:equal property="productAuthority" name="frmClaimGeneral" value="MOH">
							<logic:equal property="preCronTypeYN" name="frmClaimGeneral" value="Y">
							<logic:equal property="primaryAilment" name="frmClaimGeneral" value="Y">
							<%
							tempPreCronValue="Y";
							tempPreCron="display:;";
							%>
							
							</logic:equal>
							</logic:equal>
							</logic:equal>						
							<span id="preCronTypeID" style="<%=tempPreCron%>">							
							Condition Type:<span class="mandatorySymbol">*</span>
							<html:select  property="preCronTypeID" 	styleClass="selectBox selectBoxMedium">
							            <html:option value="">Select from list</html:option>
										<html:option value="CH">Chronic</html:option>
									    <html:option value="PX">Pre-Exist</html:option>
							</html:select>
						  </span>
						   <span>
						   <logic:equal property="productAuthority" name="frmClaimGeneral" value="MOH">
						  <bean:define id="perOneMedConID" name="frmClaimGeneral" property="benefitType"></bean:define>
						<%if("DAYC".equals(perOneMedConID)||"IPT".equals(perOneMedConID)){ %>
							Per one medical condition:<span class="mandatorySymbol">*</span>
							<html:select  property="preOneMedicalCondition" 	styleClass="selectBox selectBoxMedium">
							            <html:option value="">Select from list</html:option>
										<html:option value="Y">YES</html:option>
									    <html:option value="N">NO</html:option>
							</html:select>
							<%} %>
							</logic:equal>
							</span>
							</td>
								</tr>
								
								<tr>
									<td colspan="3">
											<logic:empty name="frmClaimGeneral" property="diagSeqId">
												<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
											<button type="button" id="icdbtnID" name="Button3" accesskey="d"
												class="buttons" onMouseout="this.className='buttons'"
												onMouseover="this.className='buttons buttonsHover'"
												onClick="addDiagnosisDetails()">
												A<u>d</u>d
											</button>&nbsp;
												</logic:notEqual>	
												<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
													<button type="button"  name="Button3">A<u>d</u>d</button>&nbsp;
												</logic:equal>	
					  						</logic:empty> 
					 						 <logic:notEmpty name="frmClaimGeneral" property="diagSeqId">
											<button type="button" id="EDITBTNDIAGONSIS" name="Button3" accesskey="d"
												class="buttons" onMouseout="this.className='buttons'"
												onMouseover="this.className='buttons buttonsHover'"
												onClick="addDiagnosisDetails()">Edit</button>&nbsp;
					   						 </logic:notEmpty>
			    					</td>
								</tr>
							</table>
							<br>
						</div>
					</div>
					<br>
					<ttk:DiagnosisDetails flow="CLM" />
				</fieldset>
				<fieldset>
					<legend>
						<logic:notEqual value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
						Activity Details <a href="#" accesskey="a" id="actImageID"
							onClick="javascript:addActivityDetails()"><img
							src="/ttk/images/AddIcon.gif" ALT="Add Activity Details" title="Add Activity Details"
							width="13" height="13" border="0" align="absmiddle"></a>
						</logic:notEqual>
						<logic:equal value="ECL" property="modeOfClaim" name="frmClaimGeneral"> 
							Activity Details&nbsp;<img src="/ttk/images/AddIcon.gif" width="13" height="13" border="0" align="absmiddle">
						</logic:equal>
							
					</legend>
					<ttk:ActivityDetails flow="CLM" />
					<br>
					<div align="center" style="align: center;"> 
						<div
							style="border: 2px solid #a1a1a1; background: #F8F8F8; border-radius: 25px; width: 99%; height: auto; text-align: center;">
							<br>
							<table align="center">
								<tr>
									<!-- <td></td> --> 
									<td></td> 
									<td align="center">Gross Amount</td>
									<td align="center">Discount</td>
									<td align="center">Discount Amount</td>
									<td align="center">Patient Share</td>
									<td align="center">Net Amount</td>
									<td align="center">Dis Allowed Amount</td>
									<td align="center">Allowed Amount</td>
								</tr>
								
								<tr>
								<!-- <td>
										<button type="button" accesskey="d" class="buttons"
											onMouseout="this.className='buttons'"
											onMouseover="this.className='buttons buttonsHover'"
											onClick="onCeed()">CEED</button>
									</td> -->
									<td>
										<button type="button" id="calculatBtnId" accesskey="d" class="buttons"
											onMouseout="this.className='buttons'"
											onMouseover="this.className='buttons buttonsHover'" 
											onClick="calculateClaimAmount()">Calculate</button> 
									</td>
									<td align="center"><html:text name="frmClaimGeneral"
											property="grossAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /></td>
									<td align="center"><html:text name="frmClaimGeneral"
											property="discountAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /></td>
									<td align="center"><html:text name="frmClaimGeneral"
											property="discountGrossAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /></td>
									<td align="center"><html:text name="frmClaimGeneral"
											property="patShareAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /></td>
									<td align="center"><html:text name="frmClaimGeneral"
											property="netAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /></td>
									<td align="center"><html:text name="frmClaimGeneral"
											property="disAllowedAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /></td>
									<td align="center"><html:text name="frmClaimGeneral"
											property="approvedAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /></td>
								</tr>
							<!-- 	<tr> -->
								<!-- <td> -->
							<!-- 	<table> -->
								<tr>
									
									
									<logic:equal name="frmClaimGeneral" property="vatFlag" value="Y">
									<td nowrap="nowrap">Final Approved Amount (VAT added):</td>
									</logic:equal>
									<logic:notEqual name="frmClaimGeneral" property="vatFlag" value="Y">
									<td nowrap="nowrap">Final Approved Amount:</td>
									</logic:notEqual>
									
									
									<td colspan="2"><html:text name="frmClaimGeneral"
											property="finalApprovedAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /> <html:text property="currencyType" styleId="totalAmountCurrency" value="OMR"  styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" /></td>
											
											
											<%-- <html:text property="currencyType" styleId="totalAmountCurrency"  styleClass="textBox textBoxSmall" readonly="true" />
											<a href="#" onclick="openRadioList('totalAmountCurrency','CURRENCY_GROUP','option')">
		          <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" border="0" align="bottom" ></a> --%>
											
			      <!-- </tr>
			      <tr> -->
			      <td></td>
									<td colspan="2"></td>		
											
			      </tr>
							<!-- </table>				
								</td>			
									</tr> -->
									<tr>
									  <td nowrap="nowrap"></td>
									<td class="formLabel">Additional Deduction Remarks:</td>
									<td class="textLabel" colspan="4"><html:textarea
											property="additionaDeductionRemarks" cols="60" rows="2" /></td>
								</tr>
									
									<tr>
									  <td nowrap="nowrap">Converted Approved Amount:</td>
									<td colspan="2"><html:text name="frmClaimGeneral"
											property="convertedFinalApprovedAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /> <html:text property="requestedAmountcurrencyType" styleId="requestedAmountcurrencyType"  styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" /></td>				
									<td class="formLabel">Final Remarks:</td>
									<td class="textLabel" colspan="4"><html:textarea
											property="finalRemarks" cols="60" rows="2" /></td>
								</tr>
								<tr>
									<td>Status:</td>
									<td colspan="2"><html:select property="claimStatus"  styleId="claimStatusfield"
											name="frmClaimGeneral" styleClass="selectBox selectBoxMedium">
											<html:option value="">Select from list</html:option>
											<html:optionsCollection name="preauthStatusList"
												label="cacheDesc" value="cacheId" />
										</html:select></td>
									<td class="formLabel">Medical Opinion Remarks:<br>(for provider)</td>
									<td colspan="4"><html:textarea
											property="medicalOpinionRemarks" cols="60" rows="2" /></td>
								</tr>
								<tr>
									<td>Settlement Number:</td>
									<td colspan="2" class="textLabelBold" style="background:;" align="left" nowrap="nowrap"><bean:write
											property="claimSettelmentNo" name="frmClaimGeneral" /></td>
									<td>Override Remarks:</td>
									<td colspan="4"><html:textarea property="overrideRemarks"
											cols="60" rows="2" /></td>
								</tr>
								<tr>
									<td colspan="8" align="center">
									<logic:notEqual value="Y" property="claimCompleteStatus" name="frmClaimGeneral">
										<button type="button" name="Button1" accesskey="v" id="SAVEBTNIDFORCLAIM"
											class="buttons" onMouseout="this.className='buttons'" 
											onMouseover="this.className='buttons buttonsHover'"
											onClick="rejectAndCompleteClaim()">
											Sa<u>v</u>e
										</button>&nbsp;
										</logic:notEqual>
										 <logic:equal value="Y" property="claimCompleteStatus" name="frmClaimGeneral">
										 <button type="button"  name="Button1" accesskey="v" id="SAVEBTNIDFORCLAIM"
											class="buttons" onMouseout="this.className='buttons'"
											onMouseover="this.className='buttons buttonsHover'"
											onClick="saveAndCompleteClaim()">
											Sa<u>v</u>e
										</button>&nbsp;
										 <logic:equal value="CNH" name="frmClaimGeneral" property="claimType">
											<button type="button" name="Button1" accesskey="g"
												class="buttons" onMouseout="this.className='buttons'"
												onMouseover="this.className='buttons buttonsHover'"
												onClick="generateClaimLetter()">
												<u>G</u>enerate Letter
											</button>&nbsp;
											</logic:equal>
											<logic:equal value="CTM" name="frmClaimGeneral" property="claimType">
											<button type="button" name="Button1" accesskey="g"
												class="buttons" onMouseout="this.className='buttons'"
												onMouseover="this.className='buttons buttonsHover'"
												onClick="openPopUpWindow()">
												<u>G</u>enerate Letter
											</button>&nbsp;
											</logic:equal>
											
       <button type="button" name="Button1" accesskey="e"  id="sendbtnid"
	class="buttons" onMouseout="this.className='buttons'"
												onMouseover="this.className='buttons buttonsHover'"
												onClick="sendClaimLetter()">
												S<u>e</u>nd
											</button>&nbsp;   
      <%
         	if (TTKCommon.isAuthorized(request, "ClaimOverride")) {
         %>
											<button type="button" name="Button1" accesskey="o"
												class="buttons" onMouseout="this.className='buttons'"
												onMouseover="this.className='buttons buttonsHover'"
												onClick="overridClaimDetails()">
												<u>O</u>verride
											</button>&nbsp;
       <%
       	}
       %>
										</logic:equal>
									</td>
								</tr>
							</table>
							<br>
						</div>
					</div>
				</fieldset>
				<fieldset>
					<legend>
						Generate ShortFalls <a href="#" accesskey="q"
							onClick="javascript:addClaimShortFalls()"><img
							src="/ttk/images/AddIcon.gif" ALT="Raise ShortFalls" title="Raise ShortFalls" width="13"
							height="13" border="0"></a>
					</legend>
					<ttk:Shortfalls flow="CLM" />
				</fieldset>
				<fieldset>
					<legend>
						Benefit and Sub benefit utilization <a href="#" accesskey="a"
							onClick="javascript:onbenefitsDetails()"><img
							src="/ttk/images/AddIcon.gif" ALT="Benefit and Sub benefit utilization"
							width="13" height="13" border="0" align="absmiddle"></a>
					</legend>
					</fieldset>
					
				
					
			</logic:notEmpty>
		</div>
		<% session.removeAttribute("warningmessage");%>
		<INPUT TYPE="hidden" NAME="mode" id="mode" VALUE="">
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
		<html:hidden property="letterPath" name="frmClaimGeneral" />
		<html:hidden property="claimCompleteStatus" name="frmClaimGeneral" />
		<html:hidden property="validateIcdCodeYN" name="frmClaimGeneral" />
		<html:hidden property="authType" styleId="authType"
			name="frmClaimGeneral" value="CLM" />
		<html:hidden property="provAuthority" name="frmClaimGeneral" />
		<html:hidden property="claimSettelmentNo" name="frmClaimGeneral" />
		<html:hidden property="memberDOB" name="frmClaimGeneral" />
		<input type="hidden" name="denailcode" id="denailcode">
		
		<html:hidden name="frmClaimGeneral"  property="allowOverrideYN"/>
		
	<input type="hidden" name="glbRuleOvrPermYN" value="<%=TTKCommon.isAuthorizedYN(request,"GlobalRuleOverride")%>">
	<input type="hidden" name="genRuleOvrPermYN" value="<%=TTKCommon.isAuthorizedYN(request,"GeneralRuleOverride")%>">
	<input type="hidden" name="clnRuleOvrPermYN" value="<%=TTKCommon.isAuthorizedYN(request,"ClinicalRuleOverride")%>">

<html:hidden property="memberwtflag" name="frmClaimGeneral"/>
<html:hidden property="patientdays" name="frmClaimGeneral"/>
<html:hidden property="memberWeight" name="frmClaimGeneral"/>		
<html:hidden property="memberDOB" name="frmClaimGeneral"/>	
<html:hidden property="autodenialyn" name="frmClaimGeneral"/>
<html:hidden property="claimOvrFlag" name="frmClaimGeneral"/>


<html:hidden property="preCronTypeYN" styleId="preCronTypeYN" name="frmClaimGeneral" />
<html:hidden property="productAuthority" styleId="productAuthority" name="frmClaimGeneral" />
<input type="hidden" id="chroPreValueID" name="chroPreValue" value="<%=tempPreCronValue%>">
<html:hidden property="disableMemberId" name="frmClaimGeneral"/>


<%-- 
<logic:equal value="DBL" property="processType" name="frmClaimGeneral"> 
<logic:equal value="CTM" property="claimType" name="frmClaimGeneral">      
<script type="text/javascript">
	getVatAddedReqAmt();
</script>
   
  </logic:equal></logic:equal>    


 <html:hidden property="vatAddedReqAmnt"  name="frmClaimGeneral"/>  --%>


	</html:form>
</body>
</html>