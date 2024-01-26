<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page
	import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper"%>

<%! DynaActionForm frmPreAuthGeneral=null; %>


<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript"
	src="/ttk/scripts/preauth/preauthgeneral.js"></script>
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
	function testUpload() {
		 document.forms[0].action="/PreAuthGeneralAction.do?mode=testUpload&page=y";
		  document.forms[0].submit();  
	     
	}
	
</script>

<logic:notEmpty name="JS_Focus_ID" scope="request">
<script type="text/javascript">
JS_Focus_ID='<bean:write name="JS_Focus_ID" scope="request"/>';
</script>

</logic:notEmpty>
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
		pageContext.setAttribute("patientTypes",
				Cache.getCacheObject("patientTypes"));
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
		
		pageContext.setAttribute("consultationTypes",Cache.getCacheObject("consultTypeCode"));
		 pageContext.setAttribute("submissionCatagory", Cache.getCacheObject("submissionCatagory")); 
		 pageContext.setAttribute("providerFecilTypes",Cache.getCacheObject("providerType"));
		 pageContext.setAttribute("partnersList",Cache.getCacheObject("partnersList"));
		 
		String dhpoFlagYN = (String)request.getSession().getAttribute("dhpoFlagYN");
		
		boolean netWorkStatus = true;
	%>
	<logic:equal value="ENHANCEMENT" property="preAuthNoStatus"
		name="frmPreAuthGeneral">
		<%
			enhancedViewMode = true;
		%>
	</logic:equal>
	<html:form action="/PreAuthGeneralAction.do"  >
	<bean:define id="tempPreCronValue" value="N"/>
		<!-- S T A R T : Page Title -->

		<table align="center" class="pageTitle" border="0" cellspacing="0"
			cellpadding="0">
			<tr>
				<td width="57%">General Details - <bean:write
						name="frmPreAuthGeneral" property="caption" />
						<a href="#" onclick="testUpload();">[Test]</a>
						</td>
						
				<td align="right" class="webBoard">&nbsp; <logic:notEmpty
						name="frmPreAuthGeneral" property="preAuthSeqID">
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
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error" title="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="errorMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			<logic:notEmpty name="errorMsg2" scope="request">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error" title="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="errorMsg2" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			<logic:notEmpty name="frmPreAuthGeneral" property="proStatusErrMsg">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error" title="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmPreAuthGeneral" property="proStatusErrMsg"/></td>
					</tr>
				</table>
			</logic:notEmpty>
	<!-- <table align="center" style="display:none" class="errorContainer" id="memberIdResult3" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error"
							width="16" height="16" align="absmiddle">&nbsp;
							Invalid Member ID: More than one member found for the searched Member ID. Please select any one member id from the search results.
							</td>
					</tr>
</table>	 -->	
			
			
			
			
			
			
			

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
			<logic:notEmpty name="frmPreAuthGeneral" property="policySuspeMsg">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmPreAuthGeneral" property="policySuspeMsg"/></td>
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
			
				<logic:notEmpty property="memActiveStatus" name="frmPreAuthGeneral" scope="session">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write name="frmPreAuthGeneral" property="memActiveStatus" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			
			<logic:notEmpty name="frmPreAuthGeneral"
				property="duplicatePreauthAlert">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error" title="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmPreAuthGeneral" property="duplicatePreauthAlert" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			<logic:notEmpty name="frmPreAuthGeneral" property="copayLimitReachedActivityYN">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Wrning" title="Wrning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmPreAuthGeneral" property="copayLimitReachedActivityYN" />
								</td>
					</tr>
				</table>				
			</logic:notEmpty>
		<logic:notEmpty name="frmPreAuthGeneral" property="ipCopayLimitYN">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Wrning" title="Wrning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmPreAuthGeneral" property="ipCopayLimitYN" />
								</td>
					</tr>
				</table>				
			</logic:notEmpty>
			
			
			<%-- 	<logic:notEmpty name="errorMsg" scope="request">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="errorMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			 --%>
			
			
			
				<logic:notEmpty name="frmPreAuthGeneral" property="negativeAmtFlg">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error" title="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmPreAuthGeneral" property="negativeAmtFlg" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			
			
			
			
			
			
			
			<logic:notEmpty name="successMsg2" scope="request">
				<table align="center" class="successContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="successMsg2" scope="request" /></td>
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
					<logic:notEmpty name="frmPreAuthGeneral" property="sum_exc_flag" scope="session">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Success" title="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								property="sum_exc_flag" name="frmPreAuthGeneral" scope="session"  /></td>
					</tr>
				</table>				
			</logic:notEmpty>
			
			<logic:notEmpty name="frmPreAuthGeneral" property="irdrgAltMsg">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="irdrgAltMsg" title="irdrgAltMsg"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmPreAuthGeneral" property="irdrgAltMsg" /></td>
					</tr>
				</table>
			</logic:notEmpty>
				
			
			<!-- S T A R T : Form Fields -->
			<fieldset>
				<legend>Pre-Authorization Details</legend>
				<table align="center" class="formContainer" border="0"
					cellspacing="0" cellpadding="0">
					
					
					
					
					<tr>
					<td class="formLabel">Submission catagory:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabel"><html:select
								property="processType"
							onchange="onchangeProcessType();onchangePartner()"
								styleClass="selectBox selectBoxMoreMedium"
								disabled="<%=enhancedViewMode%>">
								<%-- <html:option value="">Select from list</html:option> --%>
								<html:optionsCollection name="submissionCatagory"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
						<!-- 	<td></td><td></td> -->
							
						<logic:equal value="DBL" property="processType" name="frmPreAuthGeneral">
						
						<td class="formLabel" id="partnerSelectLabel" style="">Partner Name:</td>
								<td width="30%" class="textLabel" id="partnerSelectBox" style="">
								
							<html:select property="partnerName" styleClass="selectBox selectBoxMedium" styleId="partnerName" onchange="onchangePartner()" disabled="<%=enhancedViewMode%>">
									<html:option value="">Select from list</html:option>
									<html:optionsCollection name="partnersList"	label="cacheDesc" value="cacheId" />
						</html:select>	
						</td>
						</logic:equal>
						
						<logic:notEqual value="DBL" property="processType" name="frmPreAuthGeneral">
						<td class="formLabel" id="partnerSelectLabel" style="display:none">Partner Name:</td>
								<td width="30%" class="textLabel" id="partnerSelectBox" style="display:none">
								<html:select property="partnerName" styleClass="selectBox selectBoxMedium" styleId="partnerName" onchange="onchangePartner()" disabled="<%=enhancedViewMode%>">
									<html:option value="">Select from list</html:option>
									<html:optionsCollection name="partnersList"	label="cacheDesc" value="cacheId" />
								</html:select>
						</td>
						</logic:notEqual>
						
						</tr>
					
					
					
					
					
					
					
					
					
					
					
					
					
					<tr>
						<td width="22%" class="formLabel">Pre-Auth No.:</td>
						<td width="30%" class="textLabelBold"><logic:notEmpty
								name="frmPreAuthGeneral" property="preAuthNo">
								<bean:write name="frmPreAuthGeneral" property="preAuthNo" />[<bean:write
									name="frmPreAuthGeneral" property="preAuthNoStatus" />]
			    </logic:notEmpty></td>
			    
			    
			    
			    
			    
				  <logic:empty name="frmPreAuthGeneral" scope="session" property="overrideRemarks">
					
					
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
								<logic:empty name="frmPreAuthGeneral" property="preAuthNo">
								<tr>
									<td>
										<html:text name="frmPreAuthGeneral" property="receiveDate" styleClass="textBox textDate" maxlength="10" disabled="false" readonly="false" />
										<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.receiveDate',document.frmPreAuthGeneral.receiveDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
									</td>
									<td>
										<html:text name="frmPreAuthGeneral" property="receiveTime" styleClass="textBox textTime" maxlength="5" disabled="false" readonly="false" />&nbsp;
									</td>
									<td>
										<html:select name="frmPreAuthGeneral" property="receiveDay" styleClass="selectBox" disabled="false" readonly="false" >
											<html:options name="ampm" labelName="ampm" />
										</html:select>
									</td>
								</tr>
								</logic:empty>
								<logic:notEmpty name="frmPreAuthGeneral" property="preAuthNo">
										<logic:equal name="frmPreAuthGeneral" property="preAuthNoStatus" value="ENHANCEMENT">
											<logic:empty name="frmPreAuthGeneral" property="receiveDate">
												<tr>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveDate" styleClass="textBox textDate" maxlength="10" disabled="false" readonly="false" />
														<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.receiveDate',document.frmPreAuthGeneral.receiveDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
													</td>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveTime" styleClass="textBox textTime" maxlength="5" disabled="false" readonly="false" />&nbsp;
													</td>
													<td>
														<html:select name="frmPreAuthGeneral" property="receiveDay" styleClass="selectBox" disabled="false" readonly="false" >
															<html:options name="ampm" labelName="ampm" />
														</html:select>
													</td>
												</tr>
											</logic:empty>
											<logic:notEmpty name="frmPreAuthGeneral" property="receiveDate">
												<tr>
												<%if(request.getAttribute("oraerrormsg")!=null && request.getAttribute("oraerrormsg").equals("enhancement.date.not.lessthan.original.date")) {%>
												<td>
														<html:text name="frmPreAuthGeneral" property="receiveDate" styleId="receivedDateId" styleClass="textBox textDate" maxlength="10" />
														<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.receiveDate',document.frmPreAuthGeneral.receiveDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
													</td>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveTime" styleId="receivedTimeId" styleClass="textBox textTime" maxlength="5"/>&nbsp;
													</td>
													<td>
														<html:select name="frmPreAuthGeneral" property="receiveDay" styleId="receivedDayId" styleClass="selectBox">
															<html:options name="ampm" labelName="ampm" />
														</html:select>
													</td>
												<%}else{ %>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveDate" styleId="receiveDateId" styleClass="textBox textDate" maxlength="10" disabled="true" readonly="true" />
														<!-- <A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.receiveDate',document.frmPreAuthGeneral.receiveDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp; -->
													</td>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveTime" styleId="receiveTimeId" styleClass="textBox textTime" maxlength="5" disabled="true" readonly="true" />&nbsp;
													</td>
													<td>
														<html:select name="frmPreAuthGeneral" property="receiveDay" styleId="receiveDayId" styleClass="selectBox" disabled="true" readonly="true" >
															<html:options name="ampm" labelName="ampm" />
														</html:select>
													</td>
												<%} %>	
												</tr>
											</logic:notEmpty>
										</logic:equal>
										<logic:notEqual name="frmPreAuthGeneral" property="preAuthNoStatus" value="ENHANCEMENT">
												<tr>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveDate" styleClass="textBox textDate" maxlength="10" disabled="true" readonly="true" />
														<!-- <A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.receiveDate',document.frmPreAuthGeneral.receiveDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp; -->
													</td>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveTime" styleClass="textBox textTime" maxlength="5" disabled="true" readonly="true" />&nbsp;
													</td>
													<td>
														<html:select name="frmPreAuthGeneral" property="receiveDay" styleClass="selectBox" disabled="true" readonly="true" >
															<html:options name="ampm" labelName="ampm" />
														</html:select>
													</td>
												</tr>	
										</logic:notEqual>
								</logic:notEmpty>			
							</table>
						</td>
						
						<td class="formLabel">Accident Related Cases:<span
							class="mandatorySymbol">*</span></td>
						<td colspan="3">
	       				<html:select property="accidentRelatedCase" styleClass="selectBox selectBoxMedium" disabled="<%= enhancedViewMode %>" onchange="onChangeCases()">
                  			<html:option value="">Select from list</html:option> 
                  			<html:option value="Y">Yes</html:option> 
                  			<html:option value="N">No</html:option>  
            			</html:select>
        				</td>
        				<tr>
        					<td class="formLabel"></td>
							<td width="30%" class="textLabel"></td>
        					<logic:equal value="Y" property="accidentRelatedCase" name="frmPreAuthGeneral">
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
        					<logic:equal value="Y" property="workRelatedInjury" name="frmPreAuthGeneral">
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
        					<logic:equal value="Y" property="alcoholIntoxication" name="frmPreAuthGeneral">
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
        					<logic:equal value="Y" property="roadTrafficAccident" name="frmPreAuthGeneral">
        					<td class="formLabel">Date of injury:<span
							class="mandatorySymbol">*</span></td>
							<td colspan="3">
							<html:text property="dateOfInjury" styleClass="textBox textDate" /><A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmPreAuthGeneral.dateOfInjury',document.frmPreAuthGeneral.dateOfInjury.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
        				</logic:equal>
        				</tr>	
						
						
						<%-- <td class="formLabel">Accident Related Cases:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabel"><html:select
								property="accidentRelatedCase"
								styleClass="selectBox selectBoxMoreMedium"
								disabled="<%=enhancedViewMode%>">
								<html:optionsCollection name="accidentRelatedCases"
									label="cacheDesc" value="cacheId" />
							</html:select></td> --%>
					</tr>
					<tr>
						<td class="formLabel" width="19%">Mode of Pre-Auth:<span
							class="mandatorySymbol">*</span></td>
							
							<logic:empty name="frmPreAuthGeneral" property="preAuthRecvTypeID">
						    <td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium" styleId="preauthMode"
								onchange="setValidateIconTitle();"
								disabled="<%=enhancedViewMode%>">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
								</logic:empty>
								
								
								<logic:notEmpty name="frmPreAuthGeneral" property="preAuthRecvTypeID">
						    	<logic:equal name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="ONL1">
								  
								<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium textBoxDisabled" styleId="preauthMode"
								onchange="setValidateIconTitle();"
								disabled="true" readonly="true">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
							</logic:equal>
							
								<logic:equal name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="DHP">
								  
								<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium textBoxDisabled" styleId="preauthMode"
								onchange="setValidateIconTitle();"
								disabled="true" readonly="true">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
							</logic:equal>
							
							<logic:equal name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="TEL">
								  
								<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium textBoxDisabled" styleId="preauthMode"
								onchange="setValidateIconTitle();"
								disabled="true" readonly="true">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
							</logic:equal>
							
							<logic:equal name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="FAX1">
								
								<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium" styleId="preauthMode"
								onchange="setValidateIconTitle();">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
							</logic:equal>
							
							<logic:equal name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="EML1">
								
								<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium" styleId="preauthMode"
								onchange="setValidateIconTitle();">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
							</logic:equal>
							
							<logic:equal name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="NTEL">
								
								<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium" styleId="preauthMode"
								onchange="setValidateIconTitle();" disabled="true" readonly="true">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
							</logic:equal>
							
							
							<logic:equal name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="PTNR">
								
								<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium" styleId="preauthMode"
								onchange="setValidateIconTitle();" disabled="true" readonly="true">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
							</logic:equal>
							
							
							
							</logic:notEmpty>
						<td class="formLabel">Member ID:<span class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<table>
								<tr>
									<td colspan="2"><div id="memberIdResult1">
											<div id="memberIdResult2"></div>
											<!-- <div id="memberIdResult3"></div> -->
										</div></td>
								</tr>
								<tr>
									<td>
									<html:text property="memberId" styleId="memberId"
											styleClass="textBox textBoxLarge"
											style="width:216px;height:16px;" maxlength="60" readonly="true" 
											disabled="<%=enhancedViewMode%>"  onblur="getMemberDetails();"/>
											
										<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">	
											<a href="#" accesskey="g"
													onClick="javascript:selectMember()" class="search"> <img
												src="/ttk/images/EditIcon.gif" alt="Select MemberId" title="Select MemberId"
												width="16" height="16" border="0" align="absmiddle">
											</a>
											
										</logic:equal>
										
										<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
														<logic:equal value="ENA" property="cntFlag" name="frmPreAuthGeneral">
																<a href="#" accesskey="g"
																		onClick="javascript:selectMember()" class="search"> <img
																	src="/ttk/images/EditIcon.gif" alt="Select MemberId" title="Select MemberId"
																	width="16" height="16" border="0" align="absmiddle">
																</a>
														</logic:equal>
														<logic:equal value="DIS" property="cntFlag" name="frmPreAuthGeneral">
																<img src="/ttk/images/EditIcon.gif" width="16" height="16" border="0" align="absmiddle">
														</logic:equal>
										</logic:equal>				
											</td>
						
									
									<td><logic:notEqual value="ENHANCEMENT"
											property="preAuthNoStatus" name="frmPreAuthGeneral">
											<logic:notEqual property="preAuthRecvTypeID"
												name="frmPreAuthGeneral" value="DHP">
												<!-- <a href="#" accesskey="g"
													onClick="javascript:openOTPWindow()" class="search"> <img
													src="/ttk/images/validate_blue.png" alt="Validate OTP"
													width="30" height="30" border="0" align="middle"
													id="validateIcon"></a> -->
												<!-- 	
													<a href="#" accesskey="g"
													onClick="javascript:selectMember()" class="search"> <img
												src="/ttk/images/EditIcon.gif" alt="Select Provider"
												width="16" height="16" border="0" align="absmiddle"></a> -->
													
													<!-- 
													<img
												src="/ttk/images/EditIcon.gif" alt="Select Provider"
												width="16" height="16" border="0" align="absmiddle">
												<a href="#" accesskey="s" style="color:blue;" onClick="javascript:selectMember()" class="search"><u>Search</u></a>	
													
													 -->
													
													
													
											</logic:notEqual>
										</logic:notEqual></td>
								</tr>
								
							</table> <html:hidden property="memberSeqID" styleId="memberSeqID" />
						</td>
						
						<tr>
						<td></td><td></td><td></td>
									<td class="textLabelBold">Pre-Auths Count: <bean:write name="frmPreAuthGeneral" property="preAuthCount" /></td>
								</tr>
								<tr>
								<td></td><td></td><td></td>
									<td class="textLabelBold">Claims Count: <bean:write name="frmPreAuthGeneral" property="clmCount" /></td>
								</tr>
						
					</tr>
					
					</logic:empty>
					
					
					
					
					<logic:notEmpty name="frmPreAuthGeneral" scope="session" property="overrideRemarks">
					
					
					<td class="formLabel">System Of Medicine:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabel"><html:select
								property="systemOfMedicine"
								styleClass="selectBox selectBoxMoreMedium textBoxDisabled"
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
								<logic:empty name="frmPreAuthGeneral" property="preAuthNo">
								<tr>
									<td>
										<html:text name="frmPreAuthGeneral" property="receiveDate" styleClass="textBox textDate" maxlength="10" disabled="false" readonly="false" />
										<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.receiveDate',document.frmPreAuthGeneral.receiveDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
									</td>
									<td>
										<html:text name="frmPreAuthGeneral" property="receiveTime" styleClass="textBox textTime" maxlength="5" disabled="false" readonly="false" />&nbsp;
									</td>
									<td>
										<html:select name="frmPreAuthGeneral" property="receiveDay" styleClass="selectBox" disabled="false" readonly="false" >
											<html:options name="ampm" labelName="ampm" />
										</html:select>
									</td>
								</tr>
								</logic:empty>
								<logic:notEmpty name="frmPreAuthGeneral" property="preAuthNo">
										<logic:equal name="frmPreAuthGeneral" property="preAuthNoStatus" value="ENHANCEMENT">
											<logic:empty name="frmPreAuthGeneral" property="receiveDate">
												<tr>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveDate" styleClass="textBox textDate" maxlength="10" disabled="false" readonly="false" />
														<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.receiveDate',document.frmPreAuthGeneral.receiveDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
													</td>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveTime" styleClass="textBox textTime" maxlength="5" disabled="false" readonly="false" />&nbsp;
													</td>
													<td>
														<html:select name="frmPreAuthGeneral" property="receiveDay" styleClass="selectBox" disabled="false" readonly="false" >
															<html:options name="ampm" labelName="ampm" />
														</html:select>
													</td>
												</tr>
											</logic:empty>
											<logic:notEmpty name="frmPreAuthGeneral" property="receiveDate">
												<tr>
												<%if(request.getAttribute("oraerrormsg")!=null && request.getAttribute("oraerrormsg").equals("enhancement.date.not.lessthan.original.date")) {%>
												<td>
														<html:text name="frmPreAuthGeneral" property="receiveDate" styleId="receivedDateId" styleClass="textBox textDate" maxlength="10" />
														<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.receiveDate',document.frmPreAuthGeneral.receiveDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
													</td>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveTime" styleId="receivedTimeId" styleClass="textBox textTime" maxlength="5"/>&nbsp;
													</td>
													<td>
														<html:select name="frmPreAuthGeneral" property="receiveDay" styleId="receivedDayId" styleClass="selectBox">
															<html:options name="ampm" labelName="ampm" />
														</html:select>
													</td>
												<%}else{ %>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveDate" styleId="receiveDateId" styleClass="textBox textDate" maxlength="10" disabled="true" readonly="true" />
														<!-- <A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.receiveDate',document.frmPreAuthGeneral.receiveDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp; -->
													</td>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveTime" styleId="receiveTimeId" styleClass="textBox textTime" maxlength="5" disabled="true" readonly="true" />&nbsp;
													</td>
													<td>
														<html:select name="frmPreAuthGeneral" property="receiveDay" styleId="receiveDayId" styleClass="selectBox" disabled="true" readonly="true" >
															<html:options name="ampm" labelName="ampm" />
														</html:select>
													</td>
												<%} %>	
												</tr>
											</logic:notEmpty>
										</logic:equal>
										<logic:notEqual name="frmPreAuthGeneral" property="preAuthNoStatus" value="ENHANCEMENT">
												<tr>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveDate" styleClass="textBox textDate" maxlength="10" disabled="true" readonly="true" />
														<!-- <A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.receiveDate',document.frmPreAuthGeneral.receiveDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp; -->
													</td>
													<td>
														<html:text name="frmPreAuthGeneral" property="receiveTime" styleClass="textBox textTime" maxlength="5" disabled="true" readonly="true" />&nbsp;
													</td>
													<td>
														<html:select name="frmPreAuthGeneral" property="receiveDay" styleClass="selectBox" disabled="true" readonly="true" >
															<html:options name="ampm" labelName="ampm" />
														</html:select>
													</td>
												</tr>	
										</logic:notEqual>
								</logic:notEmpty>			
							</table>
						</td>
						<td class="formLabel">Accident Related Cases:<span
							class="mandatorySymbol">*</span></td>
						<td width="30%" class="textLabel"><html:select
								property="accidentRelatedCase"
								styleClass="selectBox selectBoxMoreMedium textBoxDisabled"
								disabled="true" readonly="true">
								<html:optionsCollection name="accidentRelatedCases"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
					</tr>
					<tr>
						<td class="formLabel" width="19%">Mode of Pre-Auth:<span
							class="mandatorySymbol">*</span></td>
						
						
					
						
						<logic:equal name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="ONL1">
								  
								<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium textBoxDisabled" styleId="preauthMode"
								onchange="setValidateIconTitle();"
								disabled="true" readonly="true">
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
							</logic:equal>
							
								<logic:equal name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="DHP">
								  
								<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium textBoxDisabled" styleId="preauthMode"
								onchange="setValidateIconTitle();"
								disabled="true" readonly="true">
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
							</logic:equal>
							
							<logic:equal name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="TEL">
								  
								<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium textBoxDisabled" styleId="preauthMode"
								onchange="setValidateIconTitle();"
								disabled="true" readonly="true">
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
							</logic:equal>
							
							<logic:equal name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="FAX1">
								
								<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium" styleId="preauthMode"
								onchange="setValidateIconTitle();">
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
							</logic:equal>
							
							<logic:equal name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="EML1">
								
								<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium" styleId="preauthMode"
								onchange="setValidateIconTitle();">
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
							</logic:equal>
							
							<logic:equal name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="NTEL">
								
								<td class="textLabel"><html:select name="frmPreAuthGeneral"
								property="preAuthRecvTypeID"
								styleClass="selectBox selectBoxMoreMedium" styleId="preauthMode"
								onchange="setValidateIconTitle();" disabled="true" readonly="true" >
								<html:optionsCollection name="source" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
							</logic:equal>
							

							
						<td class="formLabel">Member Id:<span class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<table>
								<tr>
									<td colspan="2"><div id="memberIdResult1">
											<div id="memberIdResult2"></div>
										</div></td>
								</tr>
								<tr>
									<td>
	
												<html:text property="memberId" styleId="memberId"
											styleClass="textBox textBoxLarge textBoxDisabled"
											style="width:200px;height:16px;" maxlength="60"
											disabled="true" readonly="true"  />
											
											<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">	
													<a href="#" accesskey="g"
														onClick="javascript:selectMember()" class="search"> <img
													src="/ttk/images/EditIcon.gif" alt="Select MemberId" title="Select MemberId"
													width="16" height="16" border="0" align="absmiddle"></a> 
											</logic:equal>
											
											<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
									 
												<logic:equal value="ENA" property="cntFlag" name="frmPreAuthGeneral">
												<a href="#" accesskey="g"
													onClick="javascript:selectMember()" class="search"> <img
												src="/ttk/images/EditIcon.gif" alt="Select MemberId" title="Select MemberId"
												width="16" height="16" border="0" align="absmiddle"></a> 
												</logic:equal>	
											
												<logic:equal value="DIS" property="cntFlag" name="frmPreAuthGeneral">	
													<img src="/ttk/images/EditIcon.gif" width="16" height="16" border="0" align="absmiddle"> 
												</logic:equal>
											</logic:equal>
											
											</td>
									<td><logic:notEqual value="ENHANCEMENT"
											property="preAuthNoStatus" name="frmPreAuthGeneral">
											<logic:notEqual property="preAuthRecvTypeID" 
												name="frmPreAuthGeneral" value="DHP">
										
											</logic:notEqual>
										</logic:notEqual>
									
								
									
									
										</td>
										
										
										
								</tr>
							</table> <html:hidden property="memberSeqID" styleId="memberSeqID" />
							
							
						
							
						</td>
					</tr>
					
					
					</logic:notEmpty>
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
						  <td width="30%" class="textLabel"> <html:text
								 property="policyNumber"  styleId="policyNumber"  readonly="true"
								 styleClass="textBox textBoxLong textBoxDisabled" />
								 &nbsp;&nbsp;&nbsp;<a  href="#"   style="color:#0c48a2; font-family:Verdana, Arial, Helvetica, sans-serif;font-size:11px; font-weight:bold;"  onClick="javascript:onUploadDocs()">Policy Docs </a>
							<html:hidden property="policySeqId"
								styleId="policySeqId" />
								</td>
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
								
					<td class="formLabel">Contact Number:</td>
       				 <td>
          				 <html:text property="mobileIsdCode" styleId="isdCode4" styleClass="textBox textBoxDisabled" size="3" maxlength="3" readonly="true"/>
          				 <html:text property="mobileNbr" styleClass="textBox textBoxLong textBoxDisabled" maxlength="15" readonly="true"/>
       				</td>
					</tr>

					<!-- my code -->

					<tr>
						<td class="formLabel">Product Name.:</td>
						<td width="30%" class="textLabel"><html:text
								property="productName" styleId="productName" readonly="true"
								styleClass="textBox textBoxLong textBoxDisabled" /></td>
						<td class="formLabel">Product Authority:</td>
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
					
					
					
						<logic:equal value="DHP" name="frmPreAuthGeneral" property="preAuthRecvTypeID">
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
						<td class="formLabel">Member Network:</td>
						<td width="30%" class="textLabel"><html:text
								property="eligibleNetworks"
								styleClass="textBox textBoxLarge textBoxDisabled"
								readonly="true" /></td>
					</tr>
					


<tr>
					 <td class="formLabel">Member First Inception Date: </td>
							<td width="30%" class="textLabel"><html:text
								property="preMemInceptionDt" styleId="preMemInceptionDt"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>
						
					<td class="formLabel">Member Exit Date:	</td>
					<td width="30%" class="textLabel"> <html:text property="preMemExitDt" styleId="preMemExitDt"
								styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" />
					</td>			
</tr>	
<tr>
					 <td class="formLabel">PED Description: </td>
							<td width="30%" class="textLabel"><html:text
								property="prePedDescription" styleId="prePedDescriptionId"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>
						
					<td class="formLabel">Maternity Description: </td>
					<td width="30%" class="textLabel"> <html:text property="preMaterinityDescription" styleId="preMaterinityDescriptionId"
								styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" />
					</td>			
</tr>				
					
<tr>
<td class="formLabel">VIP:</td>
						<td width="30%" class="textLabel"><html:text
								property="vipYorN" styleId="vipYorN"
								styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" /></td>
						
					<td class="formLabel">Patient type:</td>
					<td class="textLabel">
						<html:select property="patientType" styleClass="selectBox selectBoxMedium">
						<html:option value="">Select from list</html:option>
							<html:optionsCollection name="patientTypes" label="cacheDesc" value="cacheId" />
						</html:select>
					</td>			



 <logic:empty name="frmPreAuthGeneral" scope="session" property="overrideRemarks">
					<tr>
						<td class="formLabel">Benefit Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
											
								<html:select property="benefitType"
								styleClass="selectBox selectBoxMedium"
								onchange="setMaternityMode()" disabled="<%=enhancedViewMode%>">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="benefitTypes" label="cacheDesc"
									value="cacheId" />
								</html:select>
							</logic:equal>	
							<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
											
								<html:select property="benefitType" styleClass="selectBox selectBoxMedium"  onchange="setMaternityMode()" disabled="true">
									<html:option value="">Select from list</html:option>
									<html:optionsCollection name="benefitTypes" label="cacheDesc"
										value="cacheId" />
								</html:select>
							</logic:equal>	
							
						
						</td>
	</logic:empty>	
							
	
	<logic:notEmpty name="frmPreAuthGeneral" scope="session" property="overrideRemarks">
	
		<tr>
						<td class="formLabel">Benefit Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select property="benefitType"
								styleClass="selectBox selectBoxMedium textBoxDisabled"
								onchange="setMaternityMode()" disabled="true" readonly="true">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="benefitTypes" label="cacheDesc"
									value="cacheId" />
							</html:select></td>		
							
							
	</logic:notEmpty>
						<td class="formLabel" width="19%" colspan="2"><logic:equal
								name="frmPreAuthGeneral" value="MTI" property="benefitType">
								<table>
									<tr>
										<td class="formLabel">Gravida:<span
											class="mandatorySymbol">*</span> <html:text
												name="frmPreAuthGeneral" property="gravida"
												styleId="gravida" onkeyup="isZero(this);"
												styleClass="textBox textBoxTooTiny" maxlength="2"
												onblur="setGPLA('GR');" />
										</td>
										<td class="formLabel">Para:<span class="mandatorySymbol">*</span>
											<html:text name="frmPreAuthGeneral" property="para"
												onkeyup="isNumeric(this);" styleId="para"
												styleClass="textBox textBoxTooTiny" maxlength="2"
												onblur="setGPLA('PA');" />
										</td>
										<td class="formLabel">Live:<span class="mandatorySymbol">*</span>
											<html:text name="frmPreAuthGeneral" property="live"
												onkeyup="isNumeric(this);" styleId="live"
												styleClass="textBox textBoxTooTiny" maxlength="2"
												onblur="setGPLA('LI');" />
										</td>
										<td class="formLabel">Abortion:<span
											class="mandatorySymbol">*</span> <html:text
												name="frmPreAuthGeneral" property="abortion"
												onkeyup="isNumeric(this);" styleId="abortion"
												styleClass="textBox textBoxTooTiny" maxlength="2"
												onblur="setGPLA('AB');" />
										</td>
									</tr>
								</table>
							</logic:equal></td>
							
										
					</tr>
					<%-- <tr>
						<td></td> <td></td> <td class="formLabel">Member Inception Date:</td>
							<td width="30%" class="textLabel"><html:text
								property="preMemInceptionDt" styleId="preMemInceptionDt"
								styleClass="textBox textBoxMedium textBoxDisabled"
								readonly="true" /></td>
						
					</tr> --%>
					
			<logic:empty name="frmPreAuthGeneral" scope="session" property="overrideRemarks">	
				
					<tr>
						<td class="formLabel">Priority:</td>
						<td class="textLabel"><html:select property="priorityTypeID"
								styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
								<html:optionsCollection name="preauthPriority" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
								
									<td class="formLabel" width="19%">Network (Y/N):<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select
								property="networkProviderType"
								styleClass="selectBox selectBoxMoreMedium"
								onchange="setProviderMode();" disabled="<%=enhancedViewMode%>">
								
								
								<logic:empty  name="frmPreAuthGeneral"  property="partnerName">
								<html:option value="Y">YES</html:option>
								</logic:empty>
								<logic:notEmpty name="frmPreAuthGeneral"  property="partnerName">
								<html:option value="N">NO</html:option>
								</logic:notEmpty>	
							</html:select>
							
<logic:equal value="N" property="networkProviderType"
								name="frmPreAuthGeneral">
								<%
									netWorkStatus = false;
								%>
							</logic:equal></td>
					</tr>
					<tr>
						<td class="formLabel">Provider Id:</td>
						<td class="textLabel">
							<table>
								<tr>
								
									<td><html:text property="providerId" styleId="providerId"
											styleClass="textBox textBoxMedium"
											readonly="<%=netWorkStatus%>"
											disabled="<%=enhancedViewMode%>" /> <html:hidden
											property="providerSeqId" styleId="providerSeqId" /></td>
									<td>
										<logic:notEqual value="ENHANCEMENT" property="preAuthNoStatus" name="frmPreAuthGeneral">
											<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
											<a href="#" accesskey="g"
												onClick="javascript:selectProvider()" class="search"> <img
												src="/ttk/images/EditIcon.gif" alt="Select Provider" title="Select Provider"
												width="16" height="16" border="0" align="absmiddle">&nbsp;
											</a>
											</logic:equal>
											
											<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
												<img src="/ttk/images/EditIcon.gif" width="16" height="16" border="0" align="absmiddle">&nbsp;
											</logic:equal>
										</logic:notEqual></td>
									<td>
										<div id="providerResult1">
											<div id="providerResult2"></div>
										</div>
									</td>
								</tr>
							</table>
						</td>
						
						<td class="formLabel">Provider Name:
						<logic:empty name="frmPreAuthGeneral" property="partnerName">
						<span class="mandatorySymbol">*</span>
						</logic:empty>	
						</td>
							
						<logic:equal value="DBL" property="processType"
								name="frmPreAuthGeneral">	
						<td class="textLabel"><html:text property="providerName"
								styleId="providerName" styleClass="textBox textBoxLarge"
								maxlength="60" /></td>
							
						</logic:equal>
						<logic:equal value="GBL" property="processType"
								name="frmPreAuthGeneral">	
						<td class="textLabel"><html:text property="providerName"
								styleId="providerName" styleClass="textBox textBoxLarge"
								maxlength="60" readonly="<%=netWorkStatus%>"
								disabled="<%=enhancedViewMode%>" /></td>
							
						</logic:equal>
								
								
					</tr>
					
					
					
					
					
					
					<logic:equal value="Y" property="networkProviderType" name="frmPreAuthGeneral">
					<logic:notEmpty property="providerId" name="frmPreAuthGeneral">
					<tr>
					<td class="formLabel">Provider Authority:</td>
						<td width="30%" class="textLabel">
						<html:text property="provAuthority" styleId="provAuthority" styleClass="textBox textBoxLarge textBoxMedium" readonly="true" /></td>
					
					<td class="formLabel"> VAT TRN:</td>
						<td width="30%" class="textLabel">
						<html:text property="vatTrnCode" styleId="vatTrnCode" styleClass="textBox textBoxLarge textBoxDisabled" readonly="true" /></td>
					</tr>
					</logic:notEmpty>
					</logic:equal>
					
					
					
					
					
					
					
					
					
					</logic:empty>
					
					<logic:notEmpty name="frmPreAuthGeneral" scope="session" property="overrideRemarks">
					
					<tr>
						<td class="formLabel">Priority:</td>
						<td class="textLabel"><html:select property="priorityTypeID"
								styleClass="selectBox selectBoxMedium textBoxDisabled" disabled="true" readonly="true">
								<html:optionsCollection name="preauthPriority" label="cacheDesc"
									value="cacheId" />
							</html:select></td>
						<td class="formLabel" width="19%">Network (Y/N):<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select
								property="networkProviderType"
								styleClass="selectBox selectBoxMoreMedium textBoxDisabled"  readonly="true"
								onchange="setProviderMode();"  disabled="<%=enhancedViewMode%>">
								<html:option value="Y">YES</html:option>
								<html:option value="N">NO</html:option>
							</html:select>
							<logic:equal value="N" property="networkProviderType"
								name="frmPreAuthGeneral">
								<%
									netWorkStatus = false;
								%>
							</logic:equal></td>
					</tr>
					<tr>
						<td class="formLabel">Provider Id:</td>
						<td class="textLabel">
							<table>
								<tr>
								
									<td><html:text property="providerId" styleId="providerId"
											styleClass="textBox textBoxMedium textBoxDisabled" disabled="true" readonly="true"
											 /> <html:hidden
											property="providerSeqId" styleId="providerSeqId" /></td>
									<td><logic:notEqual value="ENHANCEMENT"
											property="preAuthNoStatus" name="frmPreAuthGeneral">
										<!-- 	<a href="#" accesskey="g"
												onClick="javascript:selectProvider()" class="search"> <img
												src="/ttk/images/EditIcon.gif" alt="Select Provider"
												width="16" height="16" border="0" align="absmiddle">&nbsp;
											</a> -->
										</logic:notEqual></td>
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
						<td class="textLabel"><html:text property="providerName" disabled="true" readonly="true"
								styleId="providerName" styleClass="textBox textBoxLarge textBoxDisabled"
								maxlength="60" /></td>
					</tr>
					
						
						
					<logic:equal value="Y" property="networkProviderType" name="frmPreAuthGeneral">
					<tr>
					<td class="formLabel">Provider Authority:</td>
						<td width="30%" class="textLabel">
						<html:text property="provAuthority" styleId="provAuthority" styleClass="textBox textBoxLarge textBoxDisabled" readonly="true" /></td>
					
					<td class="formLabel"> VAT TRN:</td>
						<td width="30%" class="textLabel">
						<html:text property="vatTrnCode" styleId="vatTrnCode" styleClass="textBox textBoxLarge textBoxDisabled" readonly="true" /></td>
					</tr>
					</logic:equal>	
						
							 
					
					</logic:notEmpty>
					
					
				<logic:equal value="DBL" property="processType" name="frmPreAuthGeneral">	
				<tr>
					<td></td><td></td>
				
					<td class="formLabel"  id="requestedAmountLabel" style="">Requested Amount:<span class="mandatorySymbol">*</span></td>
						<td class="textLabel" id="requestedAmountBox" style=""><html:text property="requestedAmount"
								styleClass="textBox textBoxMedium"
								readonly="" />&nbsp; <html:text property="requestedAmountcurrencyType" styleId="requestedAmountcurrencyType"  styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />&nbsp; <a href="#" onclick="openRadioList('requestedAmountcurrencyType','CURRENCY_GROUP','option');" onblur="AEDValidation();">
		          <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" title="Select Currency" border="0" align="bottom" > </a></td>
                   </tr>
					<tr>
					<td></td><td></td>
					<td class="formLabel" id="convertedAmountLabel" style="">Converted Amount:</td>
						<td class="textLabel" id="convertedAmountBox" style=""> <html:text property="convertedAmount" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true"  /> &nbsp;
						<html:text property="currencyType" styleId="totalAmountCurrency" value="OMR"  styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" /> <!-- <a href="#" onclick="openRadioList('totalAmountCurrency','CURRENCY_GROUP','option')">
		          <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" border="0" align="bottom" > </a> --></td>
					</tr>
					<tr>
					  <td></td>
						<td></td>
						
						<td class="formLabel" width="19%"  id="conversionRatetLabel" style="">Conversion Rate:</td> 
							<logic:notEmpty property="conversionRate" name="frmPreAuthGeneral">
						<logic:equal property="requestedAmountcurrencyType" name="frmPreAuthGeneral" value="OMR">
						<td class="textLabel"  id="conversionRateBox" style=""> <table> <tr><td> <input type="checkbox" id="converionRateYN" checked="checked"   onclick="enableConversionRate();"/></td><td><div id="conversionRatediv" style="display:" > <html:text property="conversionRate" styleId="conversionRate" onkeyup="isNumber(this,'Conversion Rate');AEDValidation();"  onblur= "clearField()" styleClass="textBox textBoxSmall textBoxDisabled" readonly="true" /></div></td> </tr></table> </td>
						</logic:equal>
						<logic:notEqual property="requestedAmountcurrencyType" name="frmPreAuthGeneral" value="OMR">
						<td class="textLabel" id="conversionRateBox" style=""> <table> <tr><td> <input type="checkbox" id="converionRateYN" checked="checked" onclick="enableConversionRate();"/></td><td><div id="conversionRatediv" style="display:" > <html:text property="conversionRate" styleId="conversionRate" onkeyup="isNumber(this,'Conversion Rate');AEDValidation();"  onblur= "clearField()" styleClass="textBox textBoxSmall" /></div></td> </tr></table> </td>
						</logic:notEqual>
						</logic:notEmpty>
						
						
						<logic:empty property="conversionRate" name="frmPreAuthGeneral">
						<logic:equal property="requestedAmountcurrencyType" name="frmPreAuthGeneral" value="OMR">
						<td class="textLabel" id="conversionRateBox" style=""> <table> <tr><td> <input type="checkbox" id="converionRateYN"  onclick="enableConversionRate();"/></td><td><div id="conversionRatediv" style="display:none;" > <html:text property="conversionRate" onkeyup="isNumber(this,'Conversion Rate'); AEDValidation();" onblur= "clearField()" styleClass="textBox textBoxSmall textBoxDisabled" readonly="true" /></div></td> </tr></table> </td>
						</logic:equal>
						<logic:notEqual property="requestedAmountcurrencyType" name="frmPreAuthGeneral" value="OMR">
						<td class="textLabel" id="conversionRateBox" style=""> <table> <tr><td> <input type="checkbox" id="converionRateYN"  onclick="enableConversionRate();"/></td><td><div id="conversionRatediv" style="display:none;" > <html:text property="conversionRate" onkeyup="isNumber(this,'Conversion Rate'); AEDValidation();" onblur= "clearField()" styleClass="textBox textBoxSmall" /></div></td> </tr></table> </td>
						</logic:notEqual>
						</logic:empty>
					</tr> 
						
					
				</logic:equal>	
				<logic:notEqual value="DBL" property="processType" name="frmPreAuthGeneral">	
				<tr>
					<td></td><td></td>
				
					<td class="formLabel"  id="requestedAmountLabel" style="display:none">Requested Amount:</td>
						<td class="textLabel" id="requestedAmountBox" style="display:none"><html:text property="requestedAmount"
								styleClass="textBox textBoxMedium"
								readonly="" />&nbsp; <html:text property="requestedAmountcurrencyType" styleId="requestedAmountcurrencyType"  styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />&nbsp; <a href="#" onclick="openRadioList('requestedAmountcurrencyType','CURRENCY_GROUP','option');" onblur="AEDValidation();">
		          <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" title="Select Currency" border="0" align="bottom" > </a></td>
                   </tr>
					<tr>
					<td></td><td></td>
					<td class="formLabel" id="convertedAmountLabel" style="display:none">Converted Amount:</td>
						<td class="textLabel" id="convertedAmountBox" style="display:none"> <html:text property="convertedAmount" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true"  /> &nbsp;
						<html:text property="currencyType" styleId="totalAmountCurrency" value="OMR"  styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" /> <!-- <a href="#" onclick="openRadioList('totalAmountCurrency','CURRENCY_GROUP','option')">
		          <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" border="0" align="bottom" > </a> --></td>
					</tr>
					<tr>
					  <td></td>
						<td></td>
						
						<td class="formLabel" width="19%"  id="conversionRatetLabel" style="display:none">Conversion Rate:</td> 
							<logic:notEmpty property="conversionRate" name="frmPreAuthGeneral">
						<logic:equal property="requestedAmountcurrencyType" name="frmPreAuthGeneral" value="OMR">
						<td class="textLabel"  id="conversionRateBox" style="display:none"> <table> <tr><td> <input type="checkbox" id="converionRateYN" checked="checked"   onclick="enableConversionRate();"/></td><td><div id="conversionRatediv" style="display:" > <html:text property="conversionRate" styleId="conversionRate" onkeyup="isNumber(this,'Conversion Rate');AEDValidation();"  onblur= "clearField()" styleClass="textBox textBoxSmall textBoxDisabled" readonly="true" /></div></td> </tr></table> </td>
						</logic:equal>
						<logic:notEqual property="requestedAmountcurrencyType" name="frmPreAuthGeneral" value="OMR">
						<td class="textLabel" id="conversionRateBox" style="display:none"> <table> <tr><td> <input type="checkbox" id="converionRateYN" checked="checked" onclick="enableConversionRate();"/></td><td><div id="conversionRatediv" style="display:" > <html:text property="conversionRate" styleId="conversionRate" onkeyup="isNumber(this,'Conversion Rate');AEDValidation();"  onblur= "clearField()" styleClass="textBox textBoxSmall" /></div></td> </tr></table> </td>
						</logic:notEqual>
						</logic:notEmpty>
						
						
						<logic:empty property="conversionRate" name="frmPreAuthGeneral">
						<logic:equal property="requestedAmountcurrencyType" name="frmPreAuthGeneral" value="OMR">
						<td class="textLabel" id="conversionRateBox" style="display:none"> <table> <tr><td> <input type="checkbox" id="converionRateYN"  onclick="enableConversionRate();"/></td><td><div id="conversionRatediv" style="display:none;" > <html:text property="conversionRate" onkeyup="isNumber(this,'Conversion Rate'); AEDValidation();" onblur= "clearField()" styleClass="textBox textBoxSmall textBoxDisabled" readonly="true" /></div></td> </tr></table> </td>
						</logic:equal>
						<logic:notEqual property="requestedAmountcurrencyType" name="frmPreAuthGeneral" value="OMR">
						<td class="textLabel" id="conversionRateBox" style="display:none"> <table> <tr><td> <input type="checkbox" id="converionRateYN"  onclick="enableConversionRate();"/></td><td><div id="conversionRatediv" style="display:none;" > <html:text property="conversionRate" onkeyup="isNumber(this,'Conversion Rate'); AEDValidation();" onblur= "clearField()" styleClass="textBox textBoxSmall" /></div></td> </tr></table> </td>
						</logic:notEqual>
						</logic:empty>
					</tr> 
						
					
				</logic:notEqual>		
					
					
					
					
					
					
					
					
					
					
					<tr>
						<td class="formLabel">Clinician Id:</td>
						<td class="textLabel">
							<table>
								<tr>
									<td><html:text property="clinicianId"
											styleId="clinicianId" styleClass="textBox textBoxMedium"/></td>
									<td><!-- <a href="#" accesskey="g"
										onClick="javascript:selectClinician()" class="search"> <img
											src="/ttk/images/EditIcon.gif" alt="Select Clinician" title="Select Clinician"
											width="16" height="16" border="0" align="absmiddle">&nbsp;
									</a> --></td>
									<td>
										<div id="clinicianResult1">
											<div id="clinicianResult2"></div>
										</div>
									</td>
								</tr>
							</table>
						<td class="formLabel">Clinician Name:</td>
						<td><html:text property="clinicianName"
								styleClass="textBox textBoxLarge" styleId="clinicianName"/></td>
					</tr>
									<tr>
						<td></td>
						<td></td>
							

						
					<logic:notEqual value="FRESH" property="preAuthNoStatus" name="frmPreAuthGeneral" scope="session">	
					<logic:equal  property="preAuthRecvTypeID" name="frmPreAuthGeneral"  value="DHP" scope="session">
					<logic:equal value="DHA" name="frmPreAuthGeneral"
						property="provAuthority" scope="session">
						<logic:equal value="Y" name="frmPreAuthGeneral"
						property="networkProviderType" scope="session">
						<logic:equal value="Y" name="frmPreAuthGeneral" property="memberwtflag" scope="session">
						<logic:equal value="3" name="frmPreAuthGeneral"
						property="encounterTypeId" scope="session">
				   	<td class="formLabel">Patient Age:<!-- <span
							class="mandatorySymbol">*</span> --></td>
						<td class="textLabel">
							<%-- 	<html:text property="patientyr"
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
								<logic:equal value="4" name="frmPreAuthGeneral"
						property="encounterTypeId" scope="session">
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
								</logic:notEqual>
								
						<logic:notEqual value="FRESH" property="preAuthNoStatus" name="frmPreAuthGeneral" scope="session">			
						<logic:notEqual  property="preAuthRecvTypeID" name="frmPreAuthGeneral"  value="DHP" scope="session">			
					<logic:equal value="DHA" name="frmPreAuthGeneral"
						property="provAuthority" scope="session">
						<logic:equal value="Y" name="frmPreAuthGeneral"
						property="networkProviderType" scope="session">
							<logic:equal value="Y" name="frmPreAuthGeneral" property="memberwtflag" scope="session">
						<logic:equal value="3" name="frmPreAuthGeneral"
						property="encounterTypeId" scope="session">
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
								<logic:equal value="4" name="frmPreAuthGeneral"
						property="encounterTypeId" scope="session">
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
								</logic:notEqual>
								</logic:notEqual>
								
							
								
									<logic:equal value="FRESH" property="preAuthNoStatus" name="frmPreAuthGeneral" scope="session">			
						<logic:equal  property="preAuthRecvTypeID" name="frmPreAuthGeneral"  value="DHP" scope="session">			
					<logic:equal value="DHA" name="frmPreAuthGeneral"
						property="provAuthority" scope="session">
						<logic:equal value="Y" name="frmPreAuthGeneral"
						property="networkProviderType" scope="session">
							<logic:equal value="Y" name="frmPreAuthGeneral" property="memberwtflag" scope="session">
						<logic:equal value="3" name="frmPreAuthGeneral"
						property="encounterTypeId" scope="session">
						<td class="formLabel">Patient Age:<!-- <span
							class="mandatorySymbol">*</span> --></td>
						<td class="textLabel">
							<%-- 	<html:text property="patientyr"
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
								<logic:equal value="4" name="frmPreAuthGeneral"
						property="encounterTypeId" scope="session">
						<td class="formLabel">Patient Age:<!-- <span
							class="mandatorySymbol">*</span> --></td>
						<td class="textLabel">
							<%-- 	<html:text property="patientyr"
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
								</logic:equal>
				
								
					<logic:equal value="FRESH" property="preAuthNoStatus" name="frmPreAuthGeneral" scope="session">	
					<logic:notEqual  property="preAuthRecvTypeID" name="frmPreAuthGeneral"  value="DHP" scope="session">		
					<logic:equal value="Y" name="frmPreAuthGeneral"
						property="networkProviderType" scope="session">
							<logic:equal value="DHA" name="frmPreAuthGeneral"
						property="provAuthority" scope="session">
							<logic:equal value="Y" name="frmPreAuthGeneral" property="memberwtflag" scope="session">		
						<logic:equal value="3" name="frmPreAuthGeneral"
						property="encounterTypeId" scope="session">
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
								<logic:equal value="4" name="frmPreAuthGeneral"
						property="encounterTypeId" scope="session">
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
							
							</logic:notEqual>
							</logic:equal>
					
						
						</tr>
				
						<tr>
							<td></td>
							<td></td>
							<logic:notEqual value="FRESH" property="preAuthNoStatus"
								name="frmPreAuthGeneral" scope="session">
								<logic:equal property="preAuthRecvTypeID"
									name="frmPreAuthGeneral" value="DHP" scope="session">
									<logic:equal value="DHA" name="frmPreAuthGeneral"
										property="provAuthority" scope="session">
										<logic:equal value="Y" name="frmPreAuthGeneral"
											property="networkProviderType" scope="session">
												<logic:equal value="Y" name="frmPreAuthGeneral" property="memberwtflag" scope="session">
											<logic:equal value="3" name="frmPreAuthGeneral"
												property="encounterTypeId" scope="session">
												<td class="formLabel">Member/Patient Weight: <span
													class="mandatorySymbol">*</span></td>
												<td width="30%" class="textLabel"><html:text
														styleId="memberWeight" property="memberWeight"
														styleClass="textBox textBoxSmall textBoxDisabled"
														readonly="true" />kgs</td>
											</logic:equal>
											<logic:equal value="4" name="frmPreAuthGeneral"
												property="encounterTypeId" scope="session">
												<td class="formLabel">Member/Patient Weight: <span
													class="mandatorySymbol">*</span></td>
												<td width="30%" class="textLabel"><html:text
														styleId="memberWeight" property="memberWeight"
														styleClass="textBox textBoxSmall textBoxDisabled"
														readonly="true" />kgs</td>
											</logic:equal>
											</logic:equal>
										</logic:equal>
									</logic:equal>
								</logic:equal>
							</logic:notEqual>

							<logic:notEqual value="FRESH" property="preAuthNoStatus"
								name="frmPreAuthGeneral" scope="session">
								<logic:notEqual property="preAuthRecvTypeID"
									name="frmPreAuthGeneral" value="DHP" scope="session">
									<logic:equal value="DHA" name="frmPreAuthGeneral"
										property="provAuthority" scope="session">
										<logic:equal value="Y" name="frmPreAuthGeneral"
											property="networkProviderType" scope="session">
												<logic:equal value="Y" name="frmPreAuthGeneral" property="memberwtflag" scope="session">
											<logic:equal value="3" name="frmPreAuthGeneral"
												property="encounterTypeId" scope="session">
												<td class="formLabel">Member/Patient Weight: <span
													class="mandatorySymbol">*</span></td>
												<td width="30%" class="textLabel"><html:text
														styleId="memberWeight" property="memberWeight"
														styleClass="textBox textBoxSmall" />kgs</td>
											</logic:equal>
											<logic:equal value="4" name="frmPreAuthGeneral"
												property="encounterTypeId" scope="session">
												<td class="formLabel">Member/Patient Weight: <span
													class="mandatorySymbol">*</span></td>
												<td width="30%" class="textLabel"><html:text
														styleId="memberWeight" property="memberWeight"
														styleClass="textBox textBoxSmall" />kgs</td>
											</logic:equal>
											</logic:equal>
										</logic:equal>
									</logic:equal>
								</logic:notEqual>
							</logic:notEqual>

	

							<logic:equal value="FRESH" property="preAuthNoStatus"
								name="frmPreAuthGeneral" scope="session">
								<logic:equal property="preAuthRecvTypeID"
									name="frmPreAuthGeneral" value="DHP" scope="session">
									<logic:equal value="DHA" name="frmPreAuthGeneral"
										property="provAuthority" scope="session">
										<logic:equal value="Y" name="frmPreAuthGeneral"
											property="networkProviderType" scope="session">
												<logic:equal value="Y" name="frmPreAuthGeneral" property="memberwtflag" scope="session">
											<logic:equal value="3" name="frmPreAuthGeneral"
												property="encounterTypeId" scope="session">
												<td class="formLabel">Member/Patient Weight: <span
													class="mandatorySymbol">*</span></td>
												<td width="30%" class="textLabel"><html:text
														styleId="memberWeight" property="memberWeight"
														styleClass="textBox textBoxSmall textBoxDisabled"
														readonly="true" />kgs</td>
											</logic:equal>
											<logic:equal value="4" name="frmPreAuthGeneral"
												property="encounterTypeId" scope="session">
												<td class="formLabel">Member/Patient Weight: <span
													class="mandatorySymbol">*</span></td>
												<td width="30%" class="textLabel"><html:text
														styleId="memberWeight" property="memberWeight"
														styleClass="textBox textBoxSmall textBoxDisabled"
														readonly="true" />kgs</td>
											</logic:equal>
											</logic:equal>
										</logic:equal>
									</logic:equal>
								</logic:equal>
							</logic:equal>


							<logic:equal value="FRESH" property="preAuthNoStatus"
								name="frmPreAuthGeneral" scope="session">
								<logic:notEqual property="preAuthRecvTypeID"
									name="frmPreAuthGeneral" value="DHP" scope="session">
									<logic:equal value="Y" name="frmPreAuthGeneral"
										property="networkProviderType" scope="session">
										<logic:equal value="DHA" name="frmPreAuthGeneral"
											property="provAuthority" scope="session">
												<logic:equal value="Y" name="frmPreAuthGeneral" property="memberwtflag" scope="session">
											<logic:equal value="3" name="frmPreAuthGeneral"
												property="encounterTypeId" scope="session">
												<td class="formLabel">Member/Patient Weight: <span
													class="mandatorySymbol">*</span></td>
												<td width="30%" class="textLabel"><html:text
														styleId="memberWeight" property="memberWeight"
														styleClass="textBox textBoxSmall" />kgs</td>
											</logic:equal>
											<logic:equal value="4" name="frmPreAuthGeneral"
												property="encounterTypeId" scope="session">
												<td class="formLabel">Member/Patient Weight: <span
													class="mandatorySymbol">*</span></td>
												<td width="30%" class="textLabel"><html:text
														styleId="memberWeight" property="memberWeight"
														styleClass="textBox textBoxSmall" />kgs</td>
											</logic:equal>
											</logic:equal>
										</logic:equal>
									</logic:equal>
								</logic:notEqual>
							</logic:equal>
				
						
			

					</tr>
						
							
			
						<logic:empty name="frmPreAuthGeneral" scope="session" property="overrideRemarks">
						
							
					<tr>
					
					<td class="formLabel">Clinician Consultation :</td>
					<td>
					<html:select property="consultationType"	styleClass="selectBox selectBoxMedium">
					<html:option value="">Select from list</html:option>
					<html:optionsCollection name="consultationTypes" label="cacheDesc"	value="cacheId" />
					</html:select>
					</td>
					
					 <logic:notEqual name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="ONL1">
					 <logic:notEqual name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="DHP">
					<td class="formLabel" id="referenceNolabel">Reference No:<span class="mandatorySymbol">*</span></td>
						<td><html:text property="referenceNo" styleClass="textBox textBoxLarge"  maxlength="25" styleId="referenceNo"/></td>
					</logic:notEqual>
					</logic:notEqual>
					</tr>
					<tr>
						<td class="formLabel" width="19%">Encounter Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">	
								<html:select property="encounterTypeId"
								styleClass="selectBox selectBoxLarge" onchange="setDateMode();"
								disabled="<%=enhancedViewMode%>">
								<logic:notEmpty name="encounterTypes" scope="session">
									<html:optionsCollection name="encounterTypes" value="key"
										label="value" />
								</logic:notEmpty>
								</html:select>
							</logic:equal>	
						
							<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">	
								<html:select property="encounterTypeId" styleClass="selectBox selectBoxLarge" disabled="true">
										<logic:notEmpty name="encounterTypes" scope="session">
											<html:optionsCollection name="encounterTypes" value="key" label="value" />
										</logic:notEmpty>
								</html:select>
							</logic:equal>	
						</td>
						
					<logic:equal name="frmPreAuthGeneral" value="IPT" property="benefitType">	
					<td class="formLabel">Document Status:</td>
					<td>
					<html:select property="documentStatus"	styleClass="selectBox selectBoxMedium">
					<html:option value="">Select from list</html:option>
					<html:option value="DOCRECEIVED">Documents Received</html:option>
					<html:option value="DOCAWAITED">Documents Awaited</html:option>
					</html:select>
					</td>
					</logic:equal>
					
					
					<logic:equal name="frmPreAuthGeneral" value="MTI" property="benefitType">	
					<td class="formLabel">Document Status:</td>
					<td>
					<html:select property="documentStatus"	styleClass="selectBox selectBoxMedium">
					<html:option value="">Select from list</html:option>
					<html:option value="DOCRECEIVED">Documents Received</html:option>
					<html:option value="DOCAWAITED">Documents Awaited</html:option>
					</html:select>
					</td>
					</logic:equal>
					
					
					
					
					
					<logic:equal name="frmPreAuthGeneral" value="DAYC" property="benefitType">
					<td class="formLabel">Document Status:</td>
					<td>
					<html:select property="documentStatus"	styleClass="selectBox selectBoxMedium">
					<html:option value="">Select from list</html:option>
					<html:option value="DOCRECEIVED">Documents Received</html:option>
					<html:option value="DOCAWAITED">Documents Awaited</html:option>
					</html:select>
					</td>
					</logic:equal>
					
					
					
					
					
					
					
					
					
						
						
					</tr>
					<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
					<tr>
						<td class="formLabel" width="19%">Encounter Start Type:<span
							class="mandatorySymbol">*</span></td>
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
					</logic:equal>
					
					<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
							<tr>
								<td class="formLabel" width="19%">Encounter Start Type:<span
									class="mandatorySymbol">*</span></td>
								<td class="textLabel"><html:select
										property="encounterStartTypeId"
										styleClass="selectBox selectBoxLarge" disabled="true"
										readonly="true">
										<html:optionsCollection name="encounterStartTypes"
											label="cacheDesc" value="cacheId" />
									</html:select></td>
								<td class="formLabel" width="19%">Encounter End Type:<span
									class="mandatorySymbol">*</span></td>
								<td class="textLabel"><html:select
										property="encounterEndTypeId" 
										styleClass="selectBox selectBoxLarge" readonly="true" disabled="true">
										<html:optionsCollection name="encounterEndTypes"
											label="cacheDesc" value="cacheId" />
									</html:select></td>
							</tr>
					</logic:equal>
					
					
					</logic:empty>
					
					<logic:notEmpty name="frmPreAuthGeneral" scope="session" property="overrideRemarks">	
					
					<tr>
						<td class="formLabel" width="19%">Encounter Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
								<html:select property="encounterTypeId"
								styleClass="selectBox selectBoxLarge textBoxDisabled"  onchange="setDateMode();"
								disabled="true" readonly="true">
								<logic:notEmpty name="encounterTypes" scope="session">
									<html:optionsCollection name="encounterTypes" value="key"
										label="value" />
								</logic:notEmpty>
								</html:select>
							</logic:equal>	
						
							<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
								<html:select property="encounterTypeId" styleClass="selectBox selectBoxLarge textBoxDisabled"  	disabled="true" readonly="true">
									<logic:notEmpty name="encounterTypes" scope="session">
										<html:optionsCollection name="encounterTypes" value="key"  label="value" />
									</logic:notEmpty>
								</html:select>
							</logic:equal>	
						</td>
						
 					<logic:notEqual name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="ONL1">
					 <logic:notEqual name="frmPreAuthGeneral" property="preAuthRecvTypeID" value="DHP">
					<td class="formLabel" id="referenceNolabel">Reference No:<span class="mandatorySymbol">*</span></td>
						<td><html:text property="referenceNo" styleClass="textBox textBoxLarge"  maxlength="25" styleId="referenceNo"/></td>
					</logic:notEqual>
					</logic:notEqual>
					</tr>
					
					
					<tr>
					<td></td>
					<td></td>
					<logic:equal name="frmPreAuthGeneral" value="IPT" property="benefitType">	
					<td class="formLabel">Document Status:</td>
					<td>
					<html:select property="documentStatus"	styleClass="selectBox selectBoxMedium">
					<html:option value="">Select from list</html:option>
					<html:option value="DOCRECEIVED">Documents Received</html:option>
					<html:option value="DOCAWAITED">Documents Awaited</html:option>
					</html:select>
					</td>
					</logic:equal>
					
					<logic:equal name="frmPreAuthGeneral" value="MTI" property="benefitType">	
					<td class="formLabel">Document Status:</td>
					<td>
					<html:select property="documentStatus"	styleClass="selectBox selectBoxMedium">
					<html:option value="">Select from list</html:option>
					<html:option value="DOCRECEIVED">Documents Received</html:option>
					<html:option value="DOCAWAITED">Documents Awaited</html:option>
					</html:select>
					</td>
					</logic:equal>
					
					<logic:equal name="frmPreAuthGeneral" value="DAYC" property="benefitType">
					<td class="formLabel">Document Status:</td>
					<td>
					<html:select property="documentStatus"	styleClass="selectBox selectBoxMedium">
					<html:option value="NA">Select from list</html:option>
					<html:option value="DOCRECEIVED">Documents Received</html:option>
					<html:option value="DOCAWAITED">Documents Awaited</html:option>
					</html:select>
					</td>
					</logic:equal>
					</tr>
					
					
					
					
					<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
					
					<tr>
						<td class="formLabel" width="19%">Encounter Start Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select
								property="encounterStartTypeId"
									styleClass="selectBox selectBoxLarge" disabled="true"
									 readonly="true">
								<html:optionsCollection name="encounterStartTypes"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
						<td class="formLabel" width="19%">Encounter End Type:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel"><html:select
								property="encounterEndTypeId"
								styleClass="selectBox selectBoxLarge textBoxDisabled" disabled="true" readonly="true">
								<html:optionsCollection name="encounterEndTypes"
									label="cacheDesc" value="cacheId" />
							</html:select></td>
					</tr>
					</logic:equal>
					
					<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
					
						<tr>
							<td class="formLabel" width="19%">Encounter Start Type:<span
								class="mandatorySymbol">*</span></td>
							<td class="textLabel"><html:select
									property="encounterStartTypeId"
									styleClass="selectBox selectBoxLarge"
									 readonly="true" disabled="true">
									<html:optionsCollection name="encounterStartTypes"
										label="cacheDesc" value="cacheId" />
								</html:select></td>
							<td class="formLabel" width="19%">Encounter End Type:<span
								class="mandatorySymbol">*</span></td>
							<td class="textLabel"><html:select
									property="encounterEndTypeId"
									styleClass="selectBox selectBoxLarge"  readonly="true" disabled="true">
									<html:optionsCollection name="encounterEndTypes"
										label="cacheDesc" value="cacheId" />
								</html:select></td>
						</tr>
					</logic:equal>
					
					
					
					</logic:notEmpty>
					
					<tr>
						<td class="formLabel">Start Date:<span
							class="mandatorySymbol">*</span></td>
						<td class="textLabel">
							<table cellspacing="0" cellpadding="0">
								<tr>
									<td>
											<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
												<html:text name="frmPreAuthGeneral"
											property="admissionDate" styleClass="textBox textDate"
											maxlength="10" onblur="setEndDate();"
											disabled="<%=enhancedViewMode%>" />
											</logic:equal>
											
											<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
												<html:text name="frmPreAuthGeneral" property="admissionDate" styleClass="textBox textDate" disabled="true" readonly="true" />
											</logic:equal>
											
										<logic:notEqual value="ENHANCEMENT" property="preAuthNoStatus" name="frmPreAuthGeneral">
												
											<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">	
											<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#"

												onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.admissionDate',document.frmPreAuthGeneral.admissionDate.value,'',event,148,178);return false;"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar"
												name="empDate" width="24" height="17" border="0"
												align="absmiddle"></a>
											</logic:equal>
											
											<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">	
												<img src="/ttk/images/CalendarIcon.gif" width="24" height="17" border="0" align="absmiddle">
											</logic:equal>	
										</logic:notEqual>&nbsp;
										
									</td>
									<td>
										<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">	
												<html:text name="frmPreAuthGeneral"
											property="admissionTime"   styleId="admissionTime" styleClass="textBox textTime"
											maxlength="5" onblur="setEndDate();"
												disabled="<%=enhancedViewMode%>" />&nbsp;
										</logic:equal>	
										<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">	
												<html:text name="frmPreAuthGeneral" property="admissionTime"   styleId="admissionTime" styleClass="textBox textTime" disabled="true" readonly="true"/>&nbsp;
										</logic:equal>	
									</td>
									<td>
											 <logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">	
												<html:select property="admissionDay"
											name="frmPreAuthGeneral" styleClass="selectBox"
											onchange="setEndDate();" disabled="<%=enhancedViewMode%>">
											<html:options name="ampm" labelName="ampm" />
											</html:select>
											</logic:equal>	
											
											<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">	
												<html:select property="admissionDay" name="frmPreAuthGeneral" styleClass="selectBox" disabled="true">
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
										<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">	
											<html:text name="frmPreAuthGeneral"
											property="dischargeDate" styleId="dischargeDate" styleClass="textBox textDate"
											maxlength="10" onblur="endDateValidation();" />				
										<logic:match name="viewmode" value="false">
											<A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"
												HREF="#" onClick="checkCalender();"
												onMouseOver="window.status='Calendar';return true;"
												onMouseOut="window.status='';return true;"><img
												src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar"
												id="discImg" name="empDate" width="24" height="17"
												border="0" align="absmiddle"></a>
											</logic:match>&nbsp;
										</logic:equal>
										
										<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">	
											<html:text name="frmPreAuthGeneral" property="dischargeDate" styleId="dischargeDate" styleClass="textBox textDate" disabled="true" readonly="true"/>				
											<img src="/ttk/images/CalendarIcon.gif" width="24" height="17" border="0" align="absmiddle">
										</logic:equal>
									</td>
									<td>	
											<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">	
											<html:text name="frmPreAuthGeneral"
											property="dischargeTime" styleClass="textBox textTime" 	maxlength="5" />&nbsp;
											</logic:equal>
											
											<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">	
												<html:text name="frmPreAuthGeneral" property="dischargeTime" styleClass="textBox textTime" disabled="true" readonly="true" />&nbsp;
											</logic:equal>
									</td>
									<td>
										 <logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">	
											<html:select property="dischargeDay" name="frmPreAuthGeneral" styleClass="selectBox">
											<html:options name="ampm" labelName="ampm" />
											</html:select>
										</logic:equal>
										<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">	
											<html:select property="dischargeDay" name="frmPreAuthGeneral" styleClass="selectBox" disabled="true">
												<html:options name="ampm" labelName="ampm" />
											</html:select>
										</logic:equal>
											
									</td>
								</tr>
							</table>
						</td>
					</tr>
			
					
					
					
					<logic:equal value="TEL" name="frmPreAuthGeneral" property="preAuthRecvTypeID">
					
					<tr>
		   <td class="formLabel">Diagnosis:</td>
		   <td class="textLabel" colspan="3">
		   <html:textarea name="frmPreAuthGeneral" indexed="diagnosis" property="diagnosis" styleClass="textBox textBoxMedium textBoxDisabled" cols="60" rows="2" readonly="true"/>
		 </td>
		 <td></td><td></td>
		 </tr>
		  <logic:notEmpty name="frmPreAuthGeneral" property="revisedDiagnosis">
		 <tr>
		   <td class="formLabel">RevisedDiagnosis:</td>
		   <td class="textLabel" colspan="3">
		   <html:textarea name="frmPreAuthGeneral" property="revisedDiagnosis" styleId="revDiagnosis"  cols="60" rows="2" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true" />
		 </td>		 
		 </tr>
		 </logic:notEmpty>
		 <tr>
		   <td class="formLabel">Services:</td>
		   <td class="textLabel" colspan="3">
		   <html:textarea name="frmPreAuthGeneral" indexed="services" property="services" styleClass="textBox textBoxMedium textBoxDisabled"  cols="60" rows="2" readonly="true"/>
		 </td>
		 </tr>
		 <logic:notEmpty name="frmPreAuthGeneral" property="revisedServices">
		 <tr>
		   <td class="formLabel">RevisedServices:</td>
		   <td class="textLabel" colspan="3">
		   <html:textarea name="frmPreAuthGeneral"   property="revisedServices"  styleId="revService" cols="60" rows="2" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true"/>
		 </td>
		 </tr>
		 </logic:notEmpty>
		         <tr>
				    <td class="formLabel">Approved Amount:  </td>
					  <td class="textLabel">
					  	<html:text name="frmPreAuthGeneral" property="oralApprovedAmount" indexed="apprAmount" styleClass="textBox textBoxSmall textBoxDisabled" readonly="true" />
					  </td>	
					  </tr>
					  <logic:notEmpty name="frmPreAuthGeneral" property="oralRevisedApprovedAmount">
				<tr>
				    <td class="formLabel">Revised Approved Amount:  </td>
					  <td class="textLabel">
					  	<html:text name="frmPreAuthGeneral" property="oralRevisedApprovedAmount" onkeyup="numberValidation(this)" styleId="revApprAmount" styleClass="textBox textBoxMedium textBoxDisabled" readonly="true"/>
					  </td>	
					  </tr>	
					</logic:notEmpty>
					</logic:equal>
					
					<!-- country code -->
					
					<logic:equal value="DBL" property="processType" name="frmPreAuthGeneral">
					<logic:equal value="N" name="frmPreAuthGeneral" property="networkProviderType">
						<tr>
							<td class="formLabel" id="providerCountryLabel" style="">Country:<span class="mandatorySymbol">*</span></td>
							<td class="textLabel" id="providerCountryBox" style="">
							<html:select property="providerCountry" styleId="providerCountry" styleClass="selectBox selectBoxMoreMedium"
									onchange="getStates();getcurrencyCode();clearConversionRate();" disabled="<%=enhancedViewMode%>">
									<html:option value="">Select From List</html:option>
									<html:optionsCollection name="countryCodeList"
										label="cacheDesc" value="cacheId" />
								</html:select></td>
								 <td class="formLabel" id="providerEmirateLabel" style="">Emirate/State:</td>
							<td class="textLabel"  id="providerEmirateBox" style=""><html:select
									property="providerEmirate" styleId="providerEmirate"
									styleClass="selectBox selectBoxMoreMedium"
									onchange="getAreas()" disabled="<%=enhancedViewMode%>">
									<html:option value="">Select From List</html:option>
									<logic:notEmpty name="providerStates" scope="session">
										<html:optionsCollection name="providerStates" value="key"
											label="value" />
									</logic:notEmpty>
								</html:select></td>
						</tr>
								
					 	<tr>
							
							<td class="formLabel" id="providerTypeLabel" style="">Provider Type:<span	class="mandatorySymbol">*</span></td>
							
							<td class="textLabel" id="providerTypeBox" style="">
							<html:select
								property="providerType"
								styleClass="selectBox selectBoxMedium">
								<html:option value="">Select From List</html:option>
								<html:option value="SGO">Government</html:option>
								<html:option value="SPR">Private</html:option>
							</html:select>	
							</td>
							
							
							<td class="formLabel" id="providerFacilityTypeLabel" style="">Provider Facility Type:<span	class="mandatorySymbol">*</span></td>
							<td class="textLabel" id="providerFacilityTypeBox" style="">
							<html:select
								property="providerFecilType"
								styleClass="selectBox selectBoxMoreMedium">
								<html:option value="">Select From List</html:option>
								<html:optionsCollection name="providerFecilTypes" label="cacheDesc" value="cacheId" />
							</html:select>
							
									</td>
							
						</tr>	
						
						
						<tr>
							<td class="formLabel" id="providerAreaLabel" style="">Area/City:</td>
							<td class="textLabel"  id="providerAreaBox" style=""><html:select property="providerArea"
									styleId="providerArea"
									styleClass="selectBox selectBoxMoreMedium"
									disabled="<%=enhancedViewMode%>">
									<html:option value="">Select From List</html:option>
									<logic:notEmpty name="providerAreas" scope="session">
										<html:optionsCollection name="providerAreas" value="key"
											label="value" />
									</logic:notEmpty>
								</html:select></td>
							<td class="formLabel" id="providerPoBoxLabel" style="">Po-Box:</td>
							<td class="textLabel"  id="providerPoBox" style=""><html:text property="providerPobox"
									styleClass="textBox textBoxLong" /></td>
						</tr>
						<tr>
							<td class="formLabel" id="providerAddressLabel" style="">Address:</td>
							<td class="textLabel"  id="providerAddressBox" style=""><html:text property="providerAddress"
									styleClass="textBox textBoxLong"
									disabled="<%=enhancedViewMode%>" /></td>
							<td></td>
							<td></td> 
						</tr>
						
					</logic:equal>
					</logic:equal>
					
					<tr>
							<td class="formLabel">Assigned To:</td>
							
							<td width="40%" class="textLabelBold">
								<bean:write name="frmPreAuthGeneral" property="assignedTo" />
			                 </td>	
			                 
			                 <td></td>	<td></td>				
							<%-- <td class="formLabel">Requested Amount:</td>
						<td class="textLabel"><html:text property="requestedAmount" styleClass="textBox textBoxMedium" onkeyup="isNumeric(this);"/></td> --%>
						</tr>
				
				
				
				 <logic:notEmpty name="frmPreAuthGeneral" property="completed_date">
					<tr>
		 		  <td class="formLabel">Completed Date:</td>
		  		 <td class="textLabel" colspan="3">
		 	    <html:text name="frmPreAuthGeneral"   property="completed_date"   styleClass="textBox textBoxMedium textBoxDisabled" readonly="true"/>
			   </td>
			  </tr>	

		     </logic:notEmpty>
				
				
				
				
				
				<logic:notEmpty  property="preAuthSeqID" name="frmPreAuthGeneral">

						<tr>
							<td class="formLabel"></td>
							<td width="40%" class="textLabelBold"></td>						
							<td></td>
							<td><a href="#" onclick="doViewProviderDocs();">View Uploaded Docs</a></td>
						</tr>

				</logic:notEmpty>
						
						
				<logic:notEmpty name="frmPreAuthGeneral" property="providerId">
					<tr>
        			<td width="20%" class="formLabel">Provider Specific Remarks:<br>
        			(Captured from the Provider Empanelment module)</td>
        			<td colspan="3">
        				<html:textarea property="providerSpecificRemarks" name="frmPreAuthGeneral" styleClass="textBox textAreaLong textBoxDisabled" disabled="true" readonly="true"/>
        			</td>
				</logic:notEmpty>
					
				<tr>
				<td class="formLabel">
                            <logic:equal value="DHP" property="preAuthRecvTypeID" name="frmPreAuthGeneral">
                            DHPO Upload Status:
                            </logic:equal>
                            </td>
                            <td width="40%" class="textLabelBold">
                            <logic:equal value="DHP" property="preAuthRecvTypeID" name="frmPreAuthGeneral">
                               <logic:equal value="Y" property="dhpoUploadStatus" name="frmPreAuthGeneral">
                               YES (<bean:write name="frmPreAuthGeneral" property="dhpoUploadedDate"/>)
                               </logic:equal>
                                <logic:notEqual value="Y" property="dhpoUploadStatus" name="frmPreAuthGeneral">
                               NO 
                               </logic:notEqual>
                               </logic:equal>
                             </td> 
				</tr>
				
					<!--tr>
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
							<button type="button" name="Button2" accesskey="s" id="firstSaveBtn"
								class="buttons" onMouseout="this.className='buttons'"
								onMouseover="this.className='buttons buttonsHover'"
								onClick="onUserSubmit()">
								<u>S</u>ave
							</button>&nbsp;
						</td>
					</tr>
				</table>
			</fieldset>
			<logic:notEmpty name="frmPreAuthGeneral" property="preAuthSeqID">
			<logic:empty name="frmPreAuthGeneral" property="proStatusErrMsg">
			
				
				<fieldset>
					<legend>Diagnosis Details</legend>
					<table align="center" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<th>Presenting Complaints:</th>
							<td>
								<logic:equal value="ONL1" property="preAuthRecvTypeID" name="frmPreAuthGeneral">		
									<html:textarea name="frmPreAuthGeneral" property="presentingComplaints" cols="50" rows="2" readonly="true" disabled="true"/>
								</logic:equal>
								<logic:notEqual value="ONL1" property="preAuthRecvTypeID" name="frmPreAuthGeneral">		
									<html:textarea name="frmPreAuthGeneral" property="presentingComplaints" cols="50" rows="2" />
								</logic:notEqual>
							</td>
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
					<br>
					<br>


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
			      					<html:text name="frmPreAuthGeneral" property="ailmentDuration" styleClass="textBox textBoxSmall" maxlength="3" onkeyup="isNumeric(this);"/>
				 			<html:select
								name="frmPreAuthGeneral" property="ailmentDurationFlag"
								styleClass="selectBox textBoxSmall">
								<html:option value="DAYS">DAYS</html:option>
								<html:option value="WEEKS">WEEKS</html:option>
								<html:option value="MONTHS">MONTHS</html:option>
								<html:option value="YEARS">YEARS</html:option>
							</html:select>
							</td>
											<td style="padding-right: 20px;" width="30%">
											
						<% 
						 frmPreAuthGeneral =(DynaActionForm)session.getAttribute("frmPreAuthGeneral");
						   String strencounterTypeId = frmPreAuthGeneral.getString("encounterTypeId");
						%>
							<logic:equal value="DHA" name="frmPreAuthGeneral"
						property="provAuthority">
						<logic:equal value="Y" name="frmPreAuthGeneral"
						property="networkProviderType" >
						<logic:equal value="3" name="frmPreAuthGeneral"
						property="encounterTypeId">
				           Info Type:<span class="mandatorySymbol">*</span>	
						</logic:equal>
						<logic:equal value="4" name="frmPreAuthGeneral"
						property="encounterTypeId">
					        Info Type:<span class="mandatorySymbol">*</span>	
						</logic:equal>
						</logic:equal>
						</logic:equal>
						</td>
						<td width="30%">
					
				
						<logic:equal value="DHA" name="frmPreAuthGeneral"
						property="provAuthority">
						<logic:equal value="3" name="frmPreAuthGeneral"
						property="encounterTypeId">
			             	Info Code:<span class="mandatorySymbol">*</span>
								</logic:equal>
								<logic:equal value="4" name="frmPreAuthGeneral"
						property="encounterTypeId">
			             	Info Code:<span class="mandatorySymbol">*</span>
								</logic:equal>
								</logic:equal>
								
							
							
						</td>
						
								</tr>
								<tr>
									<td align="center">
										<table>
											<tr>
												<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
												<td><html:text name="frmPreAuthGeneral"
														property="icdCode" styleId="icdCode"
														styleClass="textBox textBoxSmall" maxlength="20"
														onblur="getIcdCodeDetails();" /></td>
												<td><a href="#" accesskey="g"
													onClick="javascript:selectDiagnosisCode()" class="search">
														<img src="/ttk/images/EditIcon.gif" title="Select Diagnosis Code"
														alt="Select Diagnosis Code" width="16" height="16"
														border="0" align="absmiddle">&nbsp;
												</a></td>
												</logic:equal>
												
												<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
												<td><html:text name="frmPreAuthGeneral" property="icdCode" styleId="icdCode" 
																styleClass="textBox textBoxSmall" maxlength="20" readonly="true" disabled="true" /></td>
												<td>
														<img src="/ttk/images/EditIcon.gif" width="16" height="16"
														border="0" align="absmiddle">&nbsp;
												</td>
												</logic:equal>
											</tr>
										</table> <html:hidden name="frmPreAuthGeneral" property="icdCodeSeqId"
											styleId="icdCodeSeqId" /> <html:hidden
											name="frmPreAuthGeneral" property="diagSeqId"
											styleId="diagSeqId" />
									</td>
									
									<td align="center">
									 <logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
										<html:checkbox name="frmPreAuthGeneral" property="primaryAilment" styleId="primaryAilment" onclick="getIcdCodeDetails();" value="Y" />
									</logic:equal>	
									
									<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
										<html:checkbox name="frmPreAuthGeneral" property="primaryAilment" styleId="primaryAilment" onclick="getIcdCodeDetails();" value="Y" disabled="true" />
									</logic:equal>	
									</td>
									<td align="center">
										<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
											<html:textarea rows="1" cols="80" 	name="frmPreAuthGeneral" styleId="diagnosisDescription" property="ailmentDescription" />
										</logic:equal>
										
										<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
											<html:textarea rows="1" cols="80" name="frmPreAuthGeneral" styleId="diagnosisDescription" 
											 property="ailmentDescription" disabled="true"   readonly="true"/>
										</logic:equal>
											
									</td>
												<td width="30%" class="textLabel">
						<logic:equal value="DHA" name="frmPreAuthGeneral"
						property="provAuthority">
							<logic:equal value="Y" name="frmPreAuthGeneral"
						property="networkProviderType" scope="session">
						<logic:equal value="3" name="frmPreAuthGeneral"
						property="encounterTypeId">
					<html:text  styleId="infoType"
								property="infoType" value="POA"
								styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" />
								</logic:equal>
								<logic:equal value="4" name="frmPreAuthGeneral"
						property="encounterTypeId">
					<html:text  styleId="infoType"
								property="infoType" value="POA"
								styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" />
								</logic:equal>
								</logic:equal>
								</logic:equal>
							</td>
							<td width="30%" class="textLabel">
						<logic:equal property="preAuthRecvTypeID" name="frmPreAuthGeneral"  value="DHP" scope="session">
						<logic:equal value="DHA" name="frmPreAuthGeneral"
						property="provAuthority">
							<logic:equal value="Y" name="frmPreAuthGeneral"
						property="networkProviderType" scope="session">
						<logic:equal value="3" name="frmPreAuthGeneral"
						property="encounterTypeId">
					<html:text  styleId="infoCode"
								property="infoCode"
								styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" />
								</logic:equal>
								<logic:equal value="4" name="frmPreAuthGeneral"
						property="encounterTypeId">
					<html:text  styleId="infoCode"
								property="infoCode"
								styleClass="textBox textBoxSmall textBoxDisabled"
								readonly="true" />
								</logic:equal>
								</logic:equal>
								</logic:equal>
								</logic:equal>
								
									<logic:notEqual  property="preAuthRecvTypeID" name="frmPreAuthGeneral"  value="DHP" scope="session">
									<logic:equal value="DHA" name="frmPreAuthGeneral"
						property="provAuthority">
								<logic:equal value="3" name="frmPreAuthGeneral"
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
								<logic:equal value="4" name="frmPreAuthGeneral"
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
									</logic:notEqual>
							</td>
							
								</tr>
								<tr>
								<td align="left" colspan="5">
							<bean:define id="tempPreCron" value="display:none;"/>
							
							<logic:equal property="productAuthority" name="frmPreAuthGeneral" value="MOH">
							<logic:equal property="preCronTypeYN" name="frmPreAuthGeneral" value="Y">
							<logic:equal property="primaryAilment" name="frmPreAuthGeneral" value="Y">
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
									&nbsp;&nbsp;
									  <span>
						   <logic:equal property="productAuthority" name="frmPreAuthGeneral" value="MOH">
						  <bean:define id="perOneMedConID" name="frmPreAuthGeneral" property="benefitType"></bean:define>
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
									<td colspan="4"><logic:empty name="frmPreAuthGeneral" property="diagSeqId">
														<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
											<button type="button" name="Button3" accesskey="d" id="addDiagnosisBtn"
												class="buttons" onMouseout="this.className='buttons'"
												onMouseover="this.className='buttons buttonsHover'"
												onClick="addDiagnosisDetails()">
												A<u>d</u>d
											</button>&nbsp;
														</logic:equal>
														
														<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
															<button type="button" name="Button3"  disabled="disabled">A<u>d</u>d</button>&nbsp;
														</logic:equal>
			  										</logic:empty>
			   <logic:notEmpty name="frmPreAuthGeneral" property="diagSeqId">
											<button type="button" name="Button3" accesskey="d"  id="EDITBTNDIAGONSIS"
												class="buttons" onMouseout="this.className='buttons'"
												onMouseover="this.className='buttons buttonsHover'"
												onClick="addDiagnosisDetails()">Edit</button>&nbsp;
			    </logic:notEmpty></td>
								</tr>
							</table>
							<br>
						</div>
					</div>
					<br>
					<ttk:DiagnosisDetails flow="PAT" />
				</fieldset>

				<fieldset>
					<legend>
						<logic:equal value="ENABLE" property="modeFlag" name="frmPreAuthGeneral">
						Activity/Service Details <a href="#" id="actImageID" accesskey="a"
							onClick="javascript:addActivityDetails()"><img 
							src="/ttk/images/AddIcon.gif" ALT="Add Activity Details" title="Add Activity Details"
							width="13" height="13" border="0" align="absmiddle"></a>
						</logic:equal>
						
						<logic:equal value="DISABLE" property="modeFlag" name="frmPreAuthGeneral">
							Activity/Service Details&nbsp;<img src="/ttk/images/AddIcon.gif" 	width="13" height="13" border="0" align="absmiddle" >
						</logic:equal>
							
					</legend>
				
					<ttk:ActivityDetails flow="PAT"/>
					<br>
					<div align="center" style="align: center;">
						<div
							style="border: 2px solid #a1a1a1; background: #F8F8F8; border-radius: 25px; width: 90%; height: auto; text-align: center;">
							<br>
							<table align="center">
								<tr>
									<!-- <td></td> -->
									<td></td>
									<td align="center">Gross Amount</td>
									<logic:notEqual value="HAAD" property="provAuthority" name="frmPreAuthGeneral" scope="session">
									<td align="center">Discount</td>
									<td align="center">Discount Amount</td>
									</logic:notEqual>
									<td align="center">Patient Share</td>
									<td align="center">Net Amount</td>
									<td align="center">Dis Allowed Amount</td>
									<td align="center">Allowed Amount</td>
									
									
									<logic:equal value="DBL" property="processType" name="frmPreAuthGeneral">	
									<td align="center">Conv Appr Amt:</td>
									</logic:equal>
									
									
								</tr>
								<tr>
								<!-- <td align="right">
										<button type="button" accesskey="d" class="buttons"
											onMouseout="this.className='buttons'"
											onMouseover="this.className='buttons buttonsHover'"
											onClick="onCeed()">CEED</button>
									</td> -->
									<td align="right">
										<button type="button" accesskey="d" class="buttons" id="calculatepreauthbtnid"
											onMouseout="this.className='buttons'"
											onMouseover="this.className='buttons buttonsHover'"
											onClick="calculatePreauthAmount()">Calculate</button>
									</td>
									<td align="center"><html:text name="frmPreAuthGeneral"
											property="grossAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /></td>
								<logic:notEqual value="HAAD" property="provAuthority" name="frmPreAuthGeneral" scope="session">
									<td align="center"><html:text name="frmPreAuthGeneral"
											property="discountAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /></td>
									<td align="center"><html:text name="frmPreAuthGeneral"
											property="discountGrossAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /></td>
								</logic:notEqual>											
									<td align="center"><html:text name="frmPreAuthGeneral"
											property="patShareAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /></td>
									<td align="center"><html:text name="frmPreAuthGeneral"
											property="netAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /></td>
									<td align="center"><html:text name="frmPreAuthGeneral"
											property="disAllowedAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" /></td>
									<td align="center"><html:text name="frmPreAuthGeneral"
											property="approvedAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" />
											<logic:equal value="DBL" property="processType" name="frmPreAuthGeneral">	
											&nbsp;<html:text property="currencyType"  value="OMR"  styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />		
									        </logic:equal>
									</td>	
									
									<logic:equal value="DBL" property="processType" name="frmPreAuthGeneral">	
									<td align="center"><html:text name="frmPreAuthGeneral"
											property="convertedFinalApprovedAmount" onkeyup="isNumeric(this);"
											styleClass="textBox textBoxSmall textBoxDisabled"
											readonly="true" />&nbsp;<html:text property="requestedAmountcurrencyType"   styleClass="textBox textBoxTooTiny textBoxDisabled" readonly="true" />
			    				  </td>	 
			    				  </logic:equal>                                                
								</tr>
								
								<tr>
									<td class="formLabel">Final Approved Amount:</td>
									<td><html:text property="finalAprAmt"  styleId="finalAprAmt" styleClass="textBox textBoxLong"/></td>
								
								</tr>
								
								<tr>
									<td>Status:</td>
									<td colspan="7" align="left">
										<table>
											<tr>
												<td><html:select property="preauthStatus" styleId="preauthStatus"
														name="frmPreAuthGeneral"
														styleClass="selectBox selectBoxMedium">
														<html:option value="">Select from list</html:option>
														<html:optionsCollection name="preauthStatusList"
															label="cacheDesc" value="cacheId" />
													</html:select></td>
												<td>&nbsp;&nbsp;&nbsp;&nbsp;Authorization Number:</td>
												<td class="textLabelBold" style="background:;"><bean:write
														property="authNum" name="frmPreAuthGeneral" /></td>
											</tr>
										</table>
									</td>
								</tr>
								<tr>
									<td class="formLabel">Medical Opinion Remarks:<br>
									(for provider)</td>
									<td class="textLabel" colspan="7">
										<logic:equal value="INP" property="preauthStatus" name="frmPreAuthGeneral">
											<html:textarea property="medicalOpinionRemarks" cols="80" rows="2" onblur="onSpaceValidation(this,'Medical Opinion Remarks');" />
										</logic:equal>
										<logic:notEqual value="INP" property="preauthStatus" name="frmPreAuthGeneral">
											<html:textarea property="medicalOpinionRemarks" cols="80" rows="2" onblur="onSpaceValidation(this,'Medical Opinion Remarks');" readonly="true" disabled="true"/>
										</logic:notEqual>
									</td>
								</tr>
								<tr>
									<td class="formLabel">Processor's Internal Remarks:</td>
									<td class="textLabel" colspan="7">
										<logic:equal value="INP" property="preauthStatus" name="frmPreAuthGeneral">
											<html:textarea property="processorInternalRemarks" cols="80" rows="2" onblur="onSpaceValidation(this,'Processors Internal Remarks');" />
										</logic:equal>
										<logic:notEqual value="INP" property="preauthStatus" name="frmPreAuthGeneral">
											<html:textarea property="processorInternalRemarks" cols="80" rows="2" onblur="onSpaceValidation(this,'Processors Internal Remarks');" readonly="true" disabled="true" />
										</logic:notEqual>	
									</td>
								</tr>
							<tr>											
									<td class="formLabel">Override Remarks:</td>
									<td class="textLabel" colspan="7"><html:textarea property="overrideRemarks"
											cols="80" rows="2" onblur="onSpaceValidation(this,'Override Remarks');" /></td>											
								</tr>
								
								

								<tr>
									<td colspan="8" align="center">
										<button type="button" name="Button1" accesskey="s" id="saveAndCompleteBtnid"
											class="buttons" onMouseout="this.className='buttons'"
											onMouseover="this.className='buttons buttonsHover'"
											onClick="saveAndCompletePreauth()">
											<u>S</u>ave
										</button>&nbsp; 
										
									
						<logic:equal value="Y" property="preauthCompleteStatus"
											name="frmPreAuthGeneral">
											<button type="button" name="Button1" accesskey="g"
												class="buttons" onMouseout="this.className='buttons'"
												onMouseover="this.className='buttons buttonsHover'"
												onClick="generatePreAuthLetter()">
												<u>G</u>enerate Letter
											</button>&nbsp;
     <button type="button" name="Button1" accesskey="e" class="buttons"
												onMouseout="this.className='buttons'"
												onMouseover="this.className='buttons buttonsHover'"
												onClick="sendPreAuthLetter()">
												S<u>e</u>nd
											</button>&nbsp;
<%
         	if (TTKCommon.isAuthorized(request, "PreAuthOverride")) {
         %>
											<button type="button" name="Button1" accesskey="o"
												class="buttons" onMouseout="this.className='buttons'"
												onMouseover="this.className='buttons buttonsHover'"
												onClick="overridPreAuthDetails()">
												<u>O</u>verride
											</button>&nbsp;
       <%
       	}
       %>											
										
							
    </logic:equal>
                    
                    <logic:equal value="Y" property="dispDhpoBtn" name="frmPreAuthGeneral">
                    <button type="button" name="Button2" id="dhpoUploadBtnID"	class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" 	onClick="uploadToDhpo()">
									Upload To DHPO
								</button>&nbsp;
								
								<button type="button" name="Button2"	class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" 	onClick="viewDhpoLogs()">
									View Dhpo Logs
								</button>
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
							onClick="javascript:addPreauthShortFalls()"><img
							src="/ttk/images/AddIcon.gif" ALT="Raise ShortFalls" width="13" title="Raise ShortFalls"
							height="13" border="0"></a>
					</legend>
					<ttk:Shortfalls flow="PAT" />
				</fieldset>
				<fieldset>
				<legend>
						Benefit and Sub benefit utilization <a href="#" accesskey="a"
							onClick="javascript:onbenefitsDetails()"><img
							src="/ttk/images/AddIcon.gif" ALT="Benefit and Sub benefit utilization" title="View Benefits Details"
							width="13" height="13" border="0" align="absmiddle"></a>
					</legend>
					</fieldset>
					</logic:empty>
				
			</logic:notEmpty>
			
					
		</div>
		<% session.removeAttribute("warningmessage");%>
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

		<html:hidden property="authType" styleId="authType"
			name="frmPreAuthGeneral" value="PAT" />
		<html:hidden property="letterPath" name="frmPreAuthGeneral" />
		<html:hidden property="preauthCompleteStatus" name="frmPreAuthGeneral" />
		<html:hidden property="validateIcdCodeYN" name="frmPreAuthGeneral" />

		<html:hidden name="frmPreAuthGeneral" property="preAuthNo" />
		<html:hidden property="preAuthSeqID" name="frmPreAuthGeneral" />
		<html:hidden property="parentPreAuthSeqID" name="frmPreAuthGeneral" />
		<html:hidden property="authNum" name="frmPreAuthGeneral" />
		<html:hidden property="enhancedYN" name="frmPreAuthGeneral" />
		<html:hidden property="preAuthNoStatus" name="frmPreAuthGeneral" />
		<INPUT type="hidden" name="haadFlag" value="<bean:write property="provAuthority" name="frmPreAuthGeneral" scope="session"/>">
		<html:hidden property="oralORsystemStatus" value="GENERAL"/>
		<input type="hidden" name="denailcode" id="denailcode">
    	<html:hidden name="frmPreAuthGeneral"  property="allowOverrideYN"/>
	
		<html:hidden name="frmPreAuthGeneral"  property="provAuthority"/>
		 <html:hidden name="frmPreAuthGeneral"  property="hmaternityStatus"/>
	
	
		<html:hidden name="frmPreAuthGeneral" property="memberWeight" />
		<html:hidden name="frmPreAuthGeneral" property="memberDOB" />
		<html:hidden name="frmPreAuthGeneral" property="patientdays" />
		<html:hidden property="memberwtflag" name="frmPreAuthGeneral" />
		<html:hidden property="overrideAllowYN" name="frmPreAuthGeneral" />			
		<html:hidden property="dhpoUploadStatus" name="frmPreAuthGeneral" />	
	
		<html:hidden property="preCronTypeYN" styleId="preCronTypeYN" name="frmPreAuthGeneral" />
	    <html:hidden property="productAuthority" styleId="productAuthority" name="frmPreAuthGeneral" />
	    <input type="hidden" id="chroPreValueID" name="chroPreValue" value="<%=tempPreCronValue%>">
	<html:hidden name="frmPreAuthGeneral" property="processType" />
		<html:hidden property="partnerPreAuthSeqId" styleId="PreauthRefSeqId"/>
		<html:hidden property="preauthPartnerRefId" styleId="preauthPartnerRefId" />
	<%-- <input type="hidden" name="glbRuleOvrPermYN" value="<%=TTKCommon.isAuthorizedYN(request,"GlobalRuleOverride")%>">
	<input type="hidden" name="genRuleOvrPermYN" value="<%=TTKCommon.isAuthorizedYN(request,"GeneralRuleOverride")%>">
	<input type="hidden" name="clnRuleOvrPermYN" value="<%=TTKCommon.isAuthorizedYN(request,"ClinicalRuleOverride")%>"> --%>
	
	<input type="hidden" name="flaghistory" value="Y"/>
	<html:hidden name="frmPreAuthGeneral" property="disableMemberId" />
	
	<html:hidden property="partnerName" name="frmPreAuthGeneral" />
	<html:hidden name="frmPreAuthGeneral" property="cntFlag" />
	<html:hidden name="frmPreAuthGeneral" property="modeFlag" />
	<html:hidden name="frmPreAuthGeneral" property="observationFlag" />
	
	</html:form>

 <script type="text/javascript">  
 document.forms[1].setAttribute( "autocomplete", "off" );      
  </script>	
