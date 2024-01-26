<%
/** @ (#) inscompanyinfo.jsp 25th Feb 2008
 * Project     : TTK Healthcare Services
 * File        : cashlessAdd.jsp
 * Author      : kishor kumar 
 * Company     : RCS Technologies
 * Date Created: 28/11/2014
 *
 * @author 		 : kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 * welcome to Qatar
 */
 %>
 <%@ page import="java.util.ArrayList,com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@ page import="java.util.Arrays"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper,org.apache.struts.action.DynaActionForm"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript"  src="/ttk/scripts/partner/cashlessAddNew.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/jquery/ttk-jquery.js"></script>
<html:form action="/OnlineCashlessPartActionNew.do" >
<%
	boolean viewmode=false;
	boolean bEnabled=false;
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("altest",Cache.getCacheObject("dctest"));
	pageContext.setAttribute("benifitType",Cache.getCacheObject("main4benifitTypes"));
	pageContext.setAttribute("modeType",Cache.getCacheObject("modeTypes"));
	pageContext.setAttribute("alCountryCode",Cache.getCacheObject("countryCode"));
	pageContext.setAttribute("partnerEncounterTypes", Cache.getCacheObject("partnerEncounterTypes"));
	
	
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	Long hospSeqId	=	userSecurityProfile.getHospSeqId();
	String loginType = userSecurityProfile.getLoginType();
	//String flag 	=	(String)request.getAttribute("flag")==null?(String)request.getSession().getAttribute("flag"):(String)request.getAttribute("flag");
	String flag 	=	(String)request.getAttribute("flag");
	String logicVar 	=	(String)request.getAttribute("logicVar")==null?"showValidate":(String)request.getAttribute("logicVar");
	String logicValidateVar 	=	(String)request.getAttribute("logicValidateVar")==null?"showValidate":(String)request.getAttribute("logicValidateVar");
	String sOffIds	=	(String)request.getSession().getAttribute("sOffIds");
	
	if(flag!=null){
	if(flag.equalsIgnoreCase("true"))
	 {
	    viewmode=true;
	   // logicVar	=	"true";
	  }
	}
	DynaActionForm frmCashlessAdd=(DynaActionForm)request.getSession().getAttribute("frmCashlessAdd");

%>
<div class="contentArea" id="contentArea">

<!-- <div id="navigateBar">Home > Corporate > Detailed > Claim Details</div> -->
	<h4 class="sub_heading">Check Eligibility</h4>
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
		<!-- E N D : Success Box -->
		<fieldset style="width: 70%;" align="center">
			<legend>Validate Vidal ID</legend>
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0" >
		    	<tr>
			        <td class="formLabel" style="width: 34%"><strong>Vidal Enrollment ID / DHPO Member ID </strong><span class="mandatorySymbol">*</span></td>
			        <td width="1%" align="left">:</td>
			        <td style="width: 59%">
			          <html:text property="enrollId" styleId="enrollId" name="frmCashlessAdd" 
			          value="Vidal Enrollment Id" 
			          onblur="if (this.value == '') {this.value = 'Vidal Enrollment Id';}"
					  onfocus="if (this.value == 'Vidal Enrollment Id') {this.value = '';}"
			          onmouseover="if (this.value == 'Vidal Enrollment Id') {this.value = '';}" 
			          onmouseout="if (this.value == '') {this.value = 'Vidal Enrollment Id';}"
			          onkeydown="if (this.value == 'Vidal Enrollment Id') {this.value = '';}"
			          styleClass="textBoxWeblogin textBoxMediumWeblogin"/>
			          <!-- styleClass="disabledfieldType" disabled="<%=viewmode %>" -->
						<!--
						<a href="#" accesskey="g" onClick="javascript:openOTPWindow()"> validate </a>
						<br/>
						<div id="memberIdResult1"> <div id="memberIdResult2"></div> </div>-->
			        </td>
		      	</tr>
		      	
		      	<tr>
			        <td class="formLabel" style="width: 34%;height: 20px"><strong>Benefit Type </strong><span class="mandatorySymbol">*</span></td>
			        <td width="1%" align="left">:</td>
			         <td style="width: 59%">
			        <html:select property ="benifitTypeCode" styleId="benifitTypeID" styleClass="selectBox selectBoxMedium" >
                 			<%-- <html:option value="">Select from list</html:option> --%>
                 			<html:options collection="benifitType" property="cacheId" labelProperty="cacheDesc"/>
          			</html:select>
          			</td>
		      	</tr>
		      	<tr>
			        <td class="formLabel" style="width: 34%;height: 20px"><strong>Encounter Type </strong><span class="mandatorySymbol">*</span></td>
			        <td width="1%" align="left">:</td>
			         <td style="width: 59%">
			         
			        <html:select property="ptrEncounterType" styleId="encounterTypeIndex" styleClass="selectBox sselectBoxMedium" >
			        				<html:options collection="partnerEncounterTypes" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
          			</td>
		      	</tr>
		      	<tr>
			        <td class="formLabel" style="width: 34%;height: 20px"><strong>Date of Treatment / Date of Admission</strong><span class="mandatorySymbol">*</span></td>
			       <td width="1%" align="left">:</td>
			        <td style="width: 59%">
			          <html:text property="treatmentDate" styleClass="textBox textDateNewDesign" maxlength="10" value="<%=TTKCommon.getServerDateNewFormat() %>" readonly="true"/>
       					 <a name="CalendarObjectTreatmentDate" id="CalendarObjectTreatmentDate" href="#" onClick="javascript:show_calendar('CalendarObjectTreatmentDate','frmCashlessAdd.elements[\'treatmentDate\']',document.frmCashlessAdd.elements['treatmentDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
       						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
       					</a> 
			        </td>
		      	</tr>
		      	
		      	
		      	<tr>
			        <td class="formLabel" style="width: 34%;height: 20px"><strong>Probable Date of Discharge</strong></td>
			       <td width="1%" align="left">:</td>
			        <td style="width: 59%">
			          <html:text property="dischargeDate" styleClass="textBox textDateNewDesign" maxlength="10" value="<%=TTKCommon.getServerDateNewFormat() %>" readonly="true"/>
			        	<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmCashlessAdd.dischargeDate',document.frmCashlessAdd.dischargeDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
					<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="toDate1" width="24" height="17" border="0" align="absmiddle">
				</a>   
			        </td>
		      	</tr>
		      	<tr>
			        <td class="formLabel" style="width: 34%;height: 20px"><strong>Provider Name</strong><span class="mandatorySymbol">*</span></td>
			       <td width="1%" align="left">:</td>
			        <td style="width: 59%">
			          <html:text property="providerName" name="frmCashlessAdd" styleClass="textBox textBoxMedium"/>
			        </td>
		      	</tr>
		      	<tr>
			        <td class="formLabel" style="width: 34%;height: 20px"><strong>Provider Country</strong><span class="mandatorySymbol">*</span></td>
			        <td width="1%" align="left">:</td>
			        <td style="width: 59%">
			          <html:select property ="country" styleId="country" styleClass="selectBox selectBoxMedium" onchange="getcurrencyCode();">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="alCountryCode" property="cacheId" labelProperty="cacheDesc"/>
			        </html:select>
			        </td>
		      	</tr>
		      	<tr>
			        <td class="formLabel" style="width: 34%;height: 20px"><strong>Estimated Cost</strong><span class="mandatorySymbol">*</span></td>
			        <td width="1%" align="left">:</td>
			        <td style="width: 59%">
			          <html:text property="estimatedCost" name="frmCashlessAdd" styleClass="textBox textBoxMedium"/>
			        </td>
		      	</tr>
		      	<tr>
			        <td class="formLabel" style="width: 34%;height: 20px"><strong>Currency</strong><span class="mandatorySymbol">*</span></td>
			        <td width="1%" align="left">:</td>
			        <td style="width: 59%">
			          <html:text property="currency" styleId="currency"  value="OMR" styleClass="textBox textBoxMedium textBoxDisabled" readonly="false" /><a href="#" onclick="openRadioList('currency','CURRENCY_GROUP','option');clearConversionRate();">
		        				  <img src="/ttk/images/search_edit.gif" width="18" height="18" alt="Select Currency" border="0" align="bottom" > </a>
			        </td>
		      	</tr>
		      	
		      	<%-- 
		      	Not required as per new requirement by Yasmin - mailed on 10-02-2016
		      	<tr>
			        <td class="formLabel">Mobile No:</td>
			        <td width="78%" colspan="3">
			          <html:text property="isdCode" name="frmCashlessAdd" size="3" maxlength="3" readonly="true" value="ISD" styleClass="textBox textBoxSmall"/>
			          <html:text property="mobileNo" name="frmCashlessAdd" styleClass="textBox textBoxMedium"/>
			        </td>
		      	</tr>
		      	
		      	<tr>
			        <td width="22%" class="formLabel">Mode: <span class="mandatorySymbol">*</span></td>
			         <td width="22%">
			        <html:select property ="modeType" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                 			<html:option value="">Select from list</html:option>
                 			<html:options collection="modeType" property="cacheId" labelProperty="cacheDesc"/>
          			</html:select>
          			</td>
		      	</tr> --%>
		    </table>
		    <br>
		    
		<!-- <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">		    
			<tr>
		    	<td width="22%" class="formLabel"><font color="blue"> Note: For faster Pre-Auth select online option </font></td>
		    </tr>
		</table> -->
		</fieldset>
		
<%if(logicValidateVar.equals("showValidate") &&  !logicVar.equals("true")) { %>
		<!-- S T A R T : Buttons -->
	    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
		        <td width="100%" align="center">
		        
					  <!-- <button type="button" name="Button" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onValidateEnroll();"><u>V</u>alidate and Generate OTP</button>&nbsp; -->
					  <button type="button" name="Button" accesskey="c" class="olbtnLarge" onClick="javascript:onCheckEligibility();"><u>C</u>heck Eligibility</button>&nbsp;
		        </td>
	      	</tr>
	    </table>
<%} %>	

  
	    <!-- logic:match name="frmCashlessAdd" property="logicVar" value="true"-->	 
<%if(logicVar.equals("true")){ %>
	    <fieldset>
			<legend>Select Tests</legend>
	    <table width="98%" align="center"  border="0" cellspacing="0" cellpadding="0" >
	
	<tr>
		<td align="center">
			 <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onShowTests()"><u>S</u>how Tests</button>
		</td>
		<td align="center">
			 <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onCancel()"><u>C</u>ancel</button>
		</td>
		
	</tr>

	</table>
	</fieldset>
	<%} %>
	<!-- /logic:match-->
	<input type="hidden" name="mode" value="">
	<input type="hidden" name="GroupID" value="<%=hospSeqId %>"/>
	<input type="hidden" name="flag" value="<%=flag%>"/>
	<html:hidden property="loginType" value="<%=loginType %>" />
	<INPUT TYPE="hidden" NAME="leftlink" VALUE="">
	<INPUT TYPE="hidden" NAME="sublink" VALUE="">
	<br><br><br>
	</div>
</html:form> 