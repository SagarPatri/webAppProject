
<%
	/** @ (#) detailreportparam.jsp
	 * Project     : TTK Healthcare Services
	 * File        : detailreportparam.jsp
	 * Author      :  Rishi Sharma
	 * Company     : RCS Technologies
	 * Date Created:  08 August 2017
	 * @author 		 :Rishi Sharma
	 * Modified by   :
	 * Modified date :
	 * Reason        :
	 *
	 */
%>
<%@ page
	import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/reports/auditreportslist.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>

<!-- <link rel="stylesheet" type="text/css" href="css/autoComplete.css" /> -->
<!-- <script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script> -->
<!--  <SCRIPT>
	 $(document).ready(function() {
		$("#sproviderName").autocomplete("auto.jsp?mode=prerequisite");
	});
	function getProviderName() {
		$(document)
				.ready(
						function() {
							$("#sproviderName")
									.blur(
											function() {
												var ID = document.getElementById("sproviderName").value;
												document.getElementById("validHosp").innerHTML	=	'';
												var regAuthority= document.getElementById("regAuthority").value;
												$
														.ajax({
															url : "/AsynchronousAction.do?mode=getCommonMethod&id="
																	+ ID+"&regAuthority="+regAuthority+"&getType=GetProviderName",
															success : function(
																	result) {
																var res = result
																		.split("@");
																document.forms[1].sproviderName.value = res[0];
															
																
																
														}
															
															/* ,
															 */
														/* 	failure : function(
																	result) {
																document.getElementById("validHosp").innerHTML	=	'Invalid Provider';
																document.getElementById("validHosp").style.color = 'red';
																
																document.getElementById("sproviderName").value="";
															} */
														});
											
											});
							
						});
	} 
</SCRIPT>   -->
<%
	pageContext.setAttribute("listSwitchType",
			Cache.getCacheObject("claims"));
	pageContext.setAttribute("insuranceCompany",
			Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("sTtkBranch",
			Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("sStatus",
			Cache.getCacheObject("preauthStatus"));
	pageContext.setAttribute("alCorpCodeSearch",
			Cache.getCacheObject("corpCodeSearch"));
	pageContext.setAttribute("regAuthority",Cache.getCacheObject("regAuthority"));

%>
<html:form action="/ReportsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<td>Audit Report
			<logic:equal name="frmReportList" property="caption" scope="session" value="Pre-Authorization">
			<bean:write name="frmReportList" property="caption"/>
			</logic:equal>
			<logic:equal name="frmReportList" property="caption" scope="session" value="Claim">
			<bean:write name="frmReportList" property="caption"/>
			</logic:equal>
			</td>

		</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors />

	<table align="center" class="tablePad" border="0" cellspacing="0"
		cellpadding="0">
		<tr>
			<td width="10%" nowrap class="textLabelBold">Switch to:</td>
			<td width="90%"><html:select   property="switchType" styleId="switchType" name="frmReportList"	styleClass="specialDropDown" styleId="switchType" onchange="javascript:onSwitch()">
                 <html:options collection="listSwitchType" property="cacheId"
								labelProperty="cacheDesc" />
				</html:select></td>
				
				  
		</tr>
	</table>
	<!-- Start of form fields -->
	<!-- Start of Parameter grid -->
	<div class="contentArea" id="contentArea">
		<fieldset>
			<legend>Report Parameters </legend>
			<table border="0" align="center" cellpadding="0" cellspacing="0"
				class="searchContainer" width="92%;">
				<tr>
				
				<td nowrap>
				Registering Authority:<br/>
				<html:select name="frmReportList" property ="regAuthority" styleId="regAuthority" styleClass="selectBox selectBoxMedium">	
				<html:option value="">Select from list</html:option>	
     			<html:options collection="regAuthority" property="cacheId" labelProperty="cacheDesc"/>
				</html:select>	  
			   </td>
			  <!--  <td> <div id="validHosp"> </div> </td> -->
					<td nowrap>Provider Name:<br/>
					
	<%-- 				 <html:text property="sproviderName"
							styleId="sproviderName" styleClass="textBox textBoxLarge" onkeypress="ConvertToUpperCase(event.srcElement);"
							maxlength="250" onblur="getProviderName()" />
							<span id="mylocation"></span>   --%>
							<html:text  name="frmReportList" property="sproviderName"
							styleId="sproviderName" styleClass="textBox textBoxLarge" onkeypress="ConvertToUpperCase(event.srcElement);"
							maxlength="250" />
								<a href="#" accesskey="g"
													onClick="javascript:selectProvider()" class="search"> <img
													src="/ttk/images/EditIcon.gif" alt="Select Clinician"
													width="16" height="16" border="0" align="absmiddle">&nbsp;
												</a>
					</td>
					<%-- onkeypress="ConvertToUpperCase(event.srcElement);" --%>
					<td nowrap>Payer Name:<br> 
					<html:select property="sPayerName" name="frmReportList"
							styleClass="selectBox selectBoxLarge">
							<html:option value="">Any</html:option>
							<html:options collection="insuranceCompany" property="cacheId"
								labelProperty="cacheDesc" />
						</html:select>
					</td>
					<logic:equal property="reportName" name="frmReportList"
						scope="session" value="CLM">
						<td nowrap>Claim No.:<br> 
						<html:text name="frmReportList" property="sClaimNO" styleId="sClaimNO" name="frmReportList"
								styleClass="textBox textBoxLarge" maxlength="60" />
						</td>
						<td nowrap>Settlement No.:<br> 
						<html:text name="frmReportList" property="sSettlementNO" styleId="sSettlementNO" styleClass="textBox textBoxLarge"
								maxlength="60" />
						</td>
					</logic:equal>
					<logic:equal property="reportName" name="frmReportList"
						scope="session" value="PAT">
						<td nowrap>Pre-Auth No.:<br> 
						<html:text property="sPreAuthNumber" name="frmReportList"
								styleClass="textBox textBoxLarge" maxlength="60" />
						</td>

						<td nowrap>Authorization No.:<br>
						 <html:text property="sAuthorizationNO" styleId="sAuthorizationNO" name="frmReportList"
								styleClass="textBox textBoxLarge" maxlength="60" />
						</td>
					</logic:equal>

				
				</tr>

				<tr>
					<td nowrap>Member Name:<br> 
					<html:text property="sClaimantName" styleId="sClaimantName" name="frmReportList" styleClass="textBox textBoxMedium"
							maxlength="250" />
					</td>
					<td nowrap>Enrollment Id:<br>
					 <html:text name="frmReportList" property="sEnrollmentId" styleId="sEnrollmentId" styleClass="textBox textBoxLarge"
							maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement)" />
					</td>
					<td nowrap>Status:<br>
					 <html:select property="sStatus" name="frmReportList" styleClass="selectBox selectBoxMedium">
							<html:option value="">Any</html:option>
							<html:optionsCollection name="sStatus" label="cacheDesc"
								value="cacheId" />
					</html:select>
					</td>

					<td nowrap>Policy No.:<br> 
					<html:text property="sPolicyNumber" name="frmReportList" styleClass="textBox textBoxLarge"
							maxlength="60" />
					</td>

					<td nowrap>Product Name:<br>
					 <html:text property="sProductName" name="frmReportList" styleClass="textBox textBoxLarge"
							maxlength="250"
							onkeypress="javascript:blockEnterkey(event.srcElement);" />
					</td>

				</tr>


				<tr>
					<td nowrap>From Date :<!-- <span class="mandatorySymbol">*</span> --> <br/> 
					    <html:text name="frmReportList"	property="startDate" styleClass="textBox textDate"	maxlength="10"/>
										
						<a NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate"	HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmReportList.startDate',document.frmReportList.startDate.value,'',event,148,178);return false;"
						onMouseOver="window.status='Calendar';return true;"
						onMouseOut="window.status='';return true;">
						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar"	name="empDate" width="24" height="17" border="0"align="absmiddle"></a>
				
					</td>

					<td nowrap>to Date :<!--  <span class="mandatorySymbol">*</span> --><br/>
					<html:text property="endDate" styleId="endDate" styleClass="textBox textDate" maxlength="10" value=""/>
					 <a name="CalendarObjectendDate" id="CalendarObjectendDate" href="#" onClick="javascript:show_calendar('CalendarObjectendDate','frmReportList.endDate',document.frmReportList.endDate.value,'',event,148,178);return false;"
						onMouseOver="window.status='Calendar';return true;"
						onMouseOut="window.status='';return true;">
						 <img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
					</a>
					</td>
					
				</tr>


			</table>

		</fieldset>

		<!-- End of parameter grid -->
		<!-- Start of Report Type - PDF/EXCEL list and generate button -->
		<table align="center" class="buttonsContainer" border="0"
			cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" align="center">

					<button type="button" name="Button" accesskey="g" class="buttons"
						onMouseout="this.className='buttons'"
						onMouseover="this.className='buttons buttonsHover'"
						onClick="javascript:onAuditPreAuthClaimGeneratReport();">
						<u>G</u>enerateReport
					</button> &nbsp;&nbsp;&nbsp;&nbsp;
					<button type="button" name="Button" accesskey="c" class="buttons"
						onMouseout="this.className='buttons'"
						onMouseover="this.className='buttons buttonsHover'"
						onClick="javascript:onClose();">
						<u>C</u>lose
					</button>
				</td>
			</tr>
		</table>
	</div>
	<!-- End of Report Type - PDF/EXCEL list and generate button -->
	<!-- End of form fields -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>