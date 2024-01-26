<%
/**
 * @ (#) preauthgeneral.jsp May 10, 2006
 * Project      : TTK HealthCare Services
 * File         : preauthgeneral.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : May 10, 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper"%>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/preauth/preauthgeneral.js"></script>
<script type="text/javascript">
	var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";

	function addActivityDetails(){      
		document.forms[0].mode.value="addActivityDetails";
		document.forms[0].action="/AddActivityDetailsAction.do";
		document.forms[0].submit();
		}
	function openOTPWindow(){
		//window.open("/ttk/preauth/otpValidation.jsp?mid="+mid,"OTP","width=500, height=250");
	var	preauthMode=document.getElementById("preauthMode").value;
	var mid=document.getElementById("memberId").value;
	 if(mid==null||mid.length<1) alert("Enter Member Id");
	 else{
		  if(preauthMode=='EML1'||preauthMode==='EML1'||preauthMode=='FAX'||preauthMode==='FAX')		  
			 window.open("/ttk/preauth/otpValidation.jsp?mid="+mid,"Child","width=500,height=200,left=300,top=200,toolbar=no,scrollbars=no,status=no"); 
		 }        
		}
	function getHistory(){		
		document.forms[0].mode.value = "doView2";		
		document.forms[0].action="/HistoryDetailAction.do";
		document.forms[0].submit();

		}	
</script>
<%
	boolean viewmode=true;
	boolean bEnabled=false;
	boolean viewmode1=true;
	String ampm[] = {"AM","PM"};
	boolean blnAmmendmentFlow=false;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
		viewmode1=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	if(TTKCommon.getActiveLink(request).equals("Claims") && "Y".equals(ClaimsWebBoardHelper.getAmmendmentYN(request)))
	{
		blnAmmendmentFlow=true;
	}//end of if(TTKCommon.getActiveLink(request).equals("Claims") && "Y".equals(ClaimsWebBoardHelper.getAmmendmentYN(request)))
	pageContext.setAttribute("preauthType",Cache.getCacheObject("preauthType"));
	pageContext.setAttribute("ampm",ampm);
	pageContext.setAttribute("hospitalizedFor",Cache.getCacheObject("hospitalizedFor"));
	pageContext.setAttribute("preauthPriority", Cache.getCacheObject("preauthPriority"));
	pageContext.setAttribute("officeInfo", Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("source", Cache.getCacheObject("source"));
	pageContext.setAttribute("commCode", Cache.getCacheObject("commCode"));

	pageContext.setAttribute("conflict", Cache.getCacheObject("conflict"));
	pageContext.setAttribute("relationshipCode", Cache.getCacheObject("relationshipCode"));
	pageContext.setAttribute("receivedFrom", Cache.getCacheObject("receivedFrom"));
	pageContext.setAttribute("claimSubType", Cache.getCacheObject("claimSubType"));
	
	pageContext.setAttribute("source", Cache.getCacheObject("source"));
	pageContext.setAttribute("encounterTypes", Cache.getCacheObject("encounterTypes"));
	pageContext.setAttribute("treatmentTypes", Cache.getCacheObject("treatmentTypes"));
	
	//OPD_4_hosptial
	pageContext.setAttribute("paymentType", Cache.getCacheObject("PaymentType"));
	//OPD_4_hosptial
	//added as per kOC 1285 Change Request  
	pageContext.setAttribute("domicilaryReasonList", Cache.getCacheObject("domHospReason"));//1285 change request
	//added as per kOC 1285 Change Request
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
	{
		pageContext.setAttribute("codingReview",PreAuthWebBoardHelper.getCodingReviewYN(request));
	}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
	else if(TTKCommon.getActiveLink(request).equals("Claims"))
	{
		pageContext.setAttribute("codingReview",ClaimsWebBoardHelper.getCodingReviewYN(request));
	}//end of else if(TTKCommon.getActiveLink(request).equals("Claims"))	
	
%>

<html:form action="/PreAuthGeneralAction.do" >
<!-- S T A R T : Page Title -->
<logic:empty name="frmPreAuthGeneral" property="display">


	

<%
	if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
		 {
%>
			<logic:match name="frmPreAuthGeneral" property="showBandYN" value="Y">
				<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
			</logic:match>
			<logic:notMatch name="frmPreAuthGeneral" property="showBandYN" value="Y">
				<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
			</logic:notMatch>
			  <tr>
				    <td width="57%">General Details - <bean:write name="frmPreAuthGeneral" property="caption"/></td>
					<td align="right" class="webBoard">&nbsp;
					<logic:notEmpty name="frmPreAuthGeneral" property="preAuthSeqID">
						<%@ include file="/ttk/common/toolbar.jsp" %>
					</logic:notEmpty >
			  		</td>
			  </tr>
			</table>
			
<%
	}
	if(TTKCommon.getActiveLink(request).equals("Claims"))
	 {

%>
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				    <td width="57%">General Details - <bean:write name="frmPreAuthGeneral" property="caption"/></td>
					<td  align="right" class="webBoard">
						<%@ include file="/ttk/common/toolbar.jsp" %>
			  		</td>
			  </tr>

		</table>
	
<%
	}
%>

	<!-- E N D : Page Title -->
		
	<div class="contentArea" id="contentArea">
	<table width="98%" align="center"  border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	   		 <td></td>
	   		  <td align="right">
	   		  <%
	   		if(TTKCommon.getActiveLink(request).equals("Pre-Authorization")&& !(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y")))
				 {
				  if(TTKCommon.isAuthorized(request,"Edit"))
				   {
			  %>
					<logic:notEmpty name="frmPreAuthGeneral" property="preAuthSeqID">
						<logic:match name="frmPreAuthGeneral" property="showCodingOverrideYN" value="Y">
							<logic:match name="frmPreAuthGeneral" property="completedYN" value="N">
								<a href="#" onClick="onRevert()"><img src="ttk/images/RevertButton.gif" alt="Revert" width="81" height="17" border="0" align="absbottom"></a>
							</logic:match>
						</logic:match>
					</logic:notEmpty >
			  <%
			   	 }//end of if(TTKCommon.isAuthorized(request,"Edit"))
			   	}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization")&& !(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y")))
			  %>
			  
			  <%
			  if(TTKCommon.getActiveLink(request).equals("Claims")&& !(ClaimsWebBoardHelper.getAmmendmentYN(request).equals("Y"))  && !(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y")))
				 {
				  if(TTKCommon.isAuthorized(request,"Edit"))
				   {
			  %>
			    	<logic:match name="frmPreAuthGeneral" property="showCodingOverrideYN" value="Y">
			    		<logic:match name="frmPreAuthGeneral" property="completedYN" value="N">
			    			<a href="#" onClick="onRevert()"><img src="ttk/images/RevertButton.gif" alt="Revert" width="81" height="17" border="0" align="absbottom"></a>					
			    		</logic:match>
					</logic:match>
			  <%
			     }//end of if(TTKCommon.isAuthorized(request,"Edit"))
			   	}//end of if(TTKCommon.getActiveLink(request).equals("Claims")&& !(ClaimsWebBoardHelper.getAmmendmentYN(request).equals("Y"))  && !(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y")))
			  %>
	   		  
	   		  <%
				if(TTKCommon.getActiveLink(request).equals("Pre-Authorization")&& !(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y")))
				 {
			  %>
					<logic:notEmpty name="frmPreAuthGeneral" property="preAuthSeqID">
			    	<strong><bean:write property="eventName" name="frmPreAuthGeneral"/></strong>&nbsp;&nbsp;<logic:match name="viewmode" value="false"><ttk:ReviewInformation/></logic:match>&nbsp;
			    	</logic:notEmpty >
			  <%
			   	}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization")&& !(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y")))
			  %>

			  <%
				if(TTKCommon.getActiveLink(request).equals("Claims")&& !(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y")))
				 {
			  %>
			    	<strong><bean:write property="eventName" name="frmPreAuthGeneral"/></strong>&nbsp;&nbsp;<logic:match name="viewmode" value="false"><ttk:ReviewInformation/></logic:match>&nbsp;
			  <%
			   	}//added for Critical Illness CR KOC-1273
			  	if(TTKCommon.getActiveLink(request).equals("Claims"))
			  	{%>
			  		<logic:notEmpty name="frmPreAuthGeneral" property="criticalIllnessMsg">
			  		<table align="left" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   				<tr>
	     			<td><img src="/ttk/images/ErrorIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
			 			<strong><bean:write property="criticalIllnessMsg" name="frmPreAuthGeneral"/></strong> 
					</td>
	   				</tr>
	  				</table>			  		
			  		</logic:notEmpty>
			  		
			 <%	}//ended
			  %>
		   </td>
	    </tr>
	</table>
	<html:errors/>
	<!--KOC FOR Grievance-->
	<logic:notEmpty name="seniorCitizen" scope="request">
    <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
          <bean:message name="seniorCitizen" scope="request"/>
        </td>
      </tr>
    </table>
    
  </logic:notEmpty>
  <logic:notEmpty name="BufferNote" scope="request">
    <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/ttk/images/ErrorIcon.gif" alt="Error" width="16" height="16" align="absmiddle" >&nbsp;
          <bean:message name="BufferNote" scope="request" />
          </td>
      </tr>
    </table>
   </logic:notEmpty>

    
  <logic:notEmpty name="BufferRestrictyn" scope="request">
    <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/ttk/images/ErrorIcon.gif" alt="Error" width="16" height="16" align="absmiddle" >&nbsp;
         <bean:message name="BufferRestrictyn" scope="request"/>
           </td>
      </tr>
    </table>
   </logic:notEmpty>

  
	<!--<logic:notEmpty name="frmPreAuthGeneral" property="seniorCitizen">
			  		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   				<tr>
	     			<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
			 			<strong><bean:write property="seniorCitizen" name="frmPreAuthGeneral"/></strong> 
					</td>
	   				</tr>
	  				</table>			  		
			  		</logic:notEmpty>
 		--><!--KOC FOR Grievance-->
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
	<logic:notEmpty name="enrollmentChange" scope="request">
	  <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
			 <!--<bean:message name="enrollmentChange" scope="request"/>-->
			 <bean:write property="enrolChangeMsg" name="frmPreAuthGeneral"/>
		</td>
	   </tr>
	  </table>
 	</logic:notEmpty>
 	
    <!-- S T A R T : Form Fields -->
	<%
		if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
		 {
	%>
		<fieldset>
			<legend>Pre-Authorization Details</legend>
			<%-- 
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td width="22%" class="formLabel">Pre-Auth No.:</td>
			    <td width="30%" class="textLabelBold"><bean:write name="frmPreAuthGeneral" property="preAuthNo"/></td>
			    <td class="formLabel" width="19%">Cashless Type:</td>
			    <td class="textLabel" width="29%">
			        <bean:write name="frmPreAuthGeneral" property="preAuthTypeDesc"/>
			    </td>
			  </tr>
			  <tr>
			    <td class="formLabel">Received Date / Time: <span class="mandatorySymbol">*</span></td>
			    <td class="textLabel">
			     <table cellspacing="0" cellpadding="0">
			    	<tr>
			    	<td><html:text property="receivedDate" styleClass="textBox textDate" maxlength="10"  readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjecttxtRecdDate" ID="CalendarObjecttxtRecdDate" HREF="#" onClick="javascript:show_calendar('CalendarObjecttxtRecdDate','frmPreAuthGeneral.receivedDate',document.frmPreAuthGeneral.receivedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;</logic:match>&nbsp;</td>
			    	<td><html:text property="receivedTime"  styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
			    	<td><html:select property="receivedDay" name="frmPreAuthGeneral" styleClass="selectBox" disabled="<%=viewmode%>"><html:options name="ampm" labelName="ampm"/></html:select></td>
			    	</tr>
			     </table>
			    </td>
			    <td class="formLabel">Admission Date / Time:<span class="mandatorySymbol">*</span></td>
			    <td class="textLabel">
			    <table cellspacing="0" cellpadding="0">
			    	<tr>
			    		<td><html:text property="admissionDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.admissionDate',document.frmPreAuthGeneral.admissionDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match>&nbsp;</td>
			    		<td><html:text property="admissionTime"  styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
			    		<td><html:select property="admissionDay" name="frmPreAuthGeneral" styleClass="selectBox" disabled="<%=viewmode%>"><html:options name="ampm" labelName="ampm"/></html:select></td>
			    	</tr>
			    </table>
			    </td>
			    
			  </tr>
				<tr>
				  <td class="formLabel">Prev. Approved Amt. (Rs): </td>
				  <td class="textLabel">
				  	<html:text property="prevApprovedAmount" styleClass="textBox textBoxSmall textBoxDisabled" maxlength="13" disabled="<%= viewmode %>" readonly="true"/>
				  </td>
				  <td class="formLabel">Requested Amt. (Rs): <span class="mandatorySymbol">*</span></td>
				  <td class="textLabel">
				  	<html:text property="requestAmount" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
				  </td>
				</tr>
				<tr>
			      <td class="formLabel">Treating Doctor's Name:</td>
			      <td class="textLabel">
			      	<html:text property="doctorName" styleClass="textBox textBoxLarge" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
			      </td>
			      <td class="formLabel">Priority:</td>
			      <td class="textLabel">
				        <html:select property="priorityTypeID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
							<html:optionsCollection name="preauthPriority" label="cacheDesc" value="cacheId" />
			 			</html:select>
				  </td>
				  </tr>
				<tr>
				  <td class="formLabel">Vidal Health Branch: <span class="mandatorySymbol">*</span></td>
				  <td class="formLabelBold"><span class="textLabel">
			        <html:select property="officeSeqID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
					        <html:option value="">Select from list</html:option>
							<html:optionsCollection name="officeInfo" label="cacheDesc" value="cacheId" />
					  </html:select>
				  </span></td>
				  <td class="formLabel">Source: <span class="mandatorySymbol">*</span></td>
				  <td class="textLabel">
				      <html:select property="preAuthRecvTypeID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
							<html:optionsCollection name="source" label="cacheDesc" value="cacheId" />
					  </html:select>
			      </td>
				</tr>
				<tr>
				  <td class="formLabel">Phone (When in Hosp.):</td>
				  <td class="formLabelBold">
						<html:text property="hospitalPhone" styleClass="textBox textBoxMedium" maxlength="25" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
			      </td>
				  <td class="formLabel"></td>
				  <td class="textLabel">
				  </td>
				</tr>
	 			 <tr>
			      <td class="formLabel">Remarks:</td>
			      <td colspan="3" class="textLabel">
			      	<html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
			      </td>
				  </tr>
				</table>
		
		 --%>
		 <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td width="22%" class="formLabel">Pre-Auth No.:</td>
			    <td width="30%" class="textLabelBold"><bean:write name="frmPreAuthGeneral" property="preAuthNo"/></td>
			    <td class="formLabel" width="19%">Mode of Preauth:</td>
			      <td class="textLabel">
				      <html:select name="frmPreAuthGeneral" property="preAuthRecvTypeID" styleId="preauthMode" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
							<html:optionsCollection name="source" label="cacheDesc" value="cacheId" />
					  </html:select>
			      </td>
			  </tr>
			  <tr>
			   <td class="formLabel">Admission Date / Time:<span class="mandatorySymbol">*</span></td>
			    <td class="textLabel">
			    <table cellspacing="0" cellpadding="0">
			    	<tr>
			    		<td><html:text name="frmPreAuthGeneral" property="admissionDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmPreAuthGeneral.admissionDate',document.frmPreAuthGeneral.admissionDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match>&nbsp;</td>
			    		<td><html:text name="frmPreAuthGeneral" property="admissionTime"  styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
			    		<td><html:select  property="admissionDay" name="frmPreAuthGeneral" styleClass="selectBox" disabled="<%=viewmode%>"><html:options name="ampm" labelName="ampm"/></html:select></td>
			    	</tr>
			    </table>
			    </td>
			    <td class="formLabel" width="19%">Treatment Type:</td>
			      <td class="textLabel">
				      <html:select property="treatmentTypeID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
							<html:optionsCollection name="treatmentTypes" label="cacheDesc" value="cacheId" />
					  </html:select>
			      </td>
			  </tr>
			  <tr>
			   <td class="formLabel">Member Id:</td>
			      <td class="textLabel">
			      	<html:text property="memberId" styleId="memberId" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"  readonly="<%= viewmode %>" onblur="openOTPWindow()"/>
			      </td>
			  <td class="formLabel">Patient Name:</td>
			      <td class="textLabel">
			      	<html:text property="patientName" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
			      </td>
			  </tr>
			   <tr>
			   <td class="formLabel">Age:</td>
			      <td class="textLabel">
			      	<html:text property="memberAge" styleClass="textBox textBoxSmall" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
			      </td>
			  <td class="formLabel">Emirate Id:</td>
			      <td class="textLabel">
			      	<html:text property="emirateId" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
			      </td>
			  </tr>
			  <tr>
			  <td class="formLabel" width="19%">Encounter Type:</td>
			      <td class="textLabel">
				      <html:select property="encounterTypeId" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
							<html:option value="">---Select from list---</html:option>
							<html:optionsCollection name="encounterTypes" label="cacheDesc" value="cacheId" />
					  </html:select>
			      </td>
			      <td class="formLabel">Encounter Facility Id:</td>
			      <td class="textLabel">
			      	<html:text property="encounterFacilityId" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
			      </td>
			  </tr>
			  <tr>
			   <td class="formLabel">Id Payer:</td>
			      <td class="textLabel">
			      	<html:text property="idPayer" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
			      </td>
			      <td class="formLabel">Payer Id:</td>
			      <td class="textLabel">
			      	<html:text property="payerId" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
			      </td>
			  </tr>
			  <tr>
			   <td class="formLabel">Payer Name:</td>
			      <td class="textLabel">
			      	<html:text property="payerName" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
			      </td>
			      <td class="formLabel">Provider Name:</td>
			      <td class="textLabel">
			      	<html:text property="providerName" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
			      </td>
			  </tr>
			  <tr>
			   <td class="formLabel">Provider Id:</td>
			      <td class="textLabel">
			      	<html:text property="providerId" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
			      </td>
			      <td class="formLabel">Provider Details:</td>
			      <td class="textLabel">
			      	<html:text property="providerDetails" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
			      </td>
			  </tr>
			  <tr>
			   <td class="formLabel">Provider Id:</td>
			      <td class="textLabel">
			      	<html:text property="providerId" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
			      </td>
			      <td class="formLabel">Provider Details:</td>
			      <td class="textLabel">
			      	<html:text property="providerDetails" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
			      </td>
			  </tr>
			  <tr>
			  <td ></td><td ></td><td ></td> <td ><html:submit value="  Save  " onclick="onUserSubmit()"/></td>
			  </tr>
		</table>		
		</fieldset>
		
		<fieldset>	         
			<legend>Diagnosis Details</legend>
			 <table align="center"  border="0" cellspacing="0" cellpadding="0">
		         <tr>
		         <th>Presenting Complaints:</th>
		         <td><html:textarea property="presentingComplaints"  cols="50" rows="2" /> </td>
		         </tr>
		    </table>
		    <br><br>
		    <table align="center"  border="1pt" cellspacing="0" cellpadding="0" style="width:650px;height:auto;">
			  <tr>
			   <th class="formLabel" align="center" style="height:20px;background-color: #85C2FF;color:white; ">ICD Code</th>
			    <th class="formLabel" align="center" style="height:20px;background-color: #85C2FF;color:white; ">Ailment Desription</th>			   
			    <th class="formLabel" align="center" style="height:20px;background-color: #85C2FF;color:white; ">Primary Ailment</th>
			    <th rowspan="2"><html:submit value=" ADD "/></th>			    
			  </tr>
			  <tr>
			   <td align="center">
				  	<input type="text" name="icdCode" Id="icdCodes" Class="textBox textBoxSmall" maxlength="15" readonly="true"/>
				  </td>
			    <td align="center">
			      	<%-- <html:text property="ailmentDesription" styleId="ailmentDesriptionAuto" styleClass="textBox textBoxLarge"/> --%>			      
			       <input type="text" id="ailmentDesriptionAuto"  size="25" name="ailmentDesription" class="input_text" onblur="getIcdCode();"/>			       
			      </td>			     
				   <td align="center">
				  <input type="checkbox" name="primaryAilment" value="Y"  />				   				  			  	
				  </td>				 			  				  
			  </tr>			 		 
			  </table>
			  <br><br>
			  <table align="center"  border="1"  style="width:750px;height:auto;">
			   <tr style="height:20px;background-color: #85C2FF;color:white; ">
			    <th align="center">ICD Code</th>
			    <th align="center">Ailment Desription</th>			   
			    <th align="center">Ailement Type </th>
			    <th align="center">Delete</th>
			   </tr>
			   
			   <logic:notEmpty name="list" scope="request">
                 <c:forEach items="${requestScope.list}" var="list1">                 
                 <tr>
                 <td align="center"><a href="#"><c:out value="${list1[0]}"/></a> </td>
		<td align="center"> <c:out value="${list1[1]}"/></td>
		<td align="center"> <c:out value="${list1[2]}"/></td>
		<td align="center">Delete<a href="#" onClick="javascript:clearEnrollmentID();"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Enrollment Id" width="16" height="16" border="0"></a></td>		
		</tr>
    </c:forEach>
    </logic:notEmpty>
			  </table>
			 			
		</fieldset>
		<fieldset>	         
			<legend>
			Activity Details
			<a href="#" onClick="javascript:addActivityDetails()"><img src="/ttk/images/AddIcon.gif" ALT="Add Activity Details" width="14" height="14" border="0" align="absmiddle"></a>
			</legend>
			  <table align="center" class="formContainer" border="1" cellspacing="0" cellpadding="0">
			<tr style="height:20px;background-color: #85C2FF;color:white; ">
			<th>Sl.No</th><th>Activity Code</th><th>Modifier</th>
			<th>Unit Type</th><th>Quantity</th><th>Start Date</th>
			<th>Gross Amt</th><th>Discount</th><th>Discounted Gross</th>
		    <th>Patient Share</th><th>Net Amt</th><th>Approved Amt</th>
		    <th>Denial Dec</th><th>Remarks</th><th>Edit</th><th>Observ</th>
			</tr>			   
			  </table>
			  </fieldset>
<fieldset>
<legend><a href="#" onclick="getHistory()">History</a></legend>
</fieldset>	

  
	<%-- <%
		if(TTKCommon.isAuthorized(request,"SpecialPermission"))
		 {
	%>
		<fieldset>
			<legend>Assignment</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td width="22%" class="formLabel">Assigned To:</td>
			    <td width="30%" class="textLabelBold"><bean:write name="frmPreAuthGeneral" property="assignedTo"/></td>
			    <td class="formLabel" width="19%">Processing Branch:</td>
			    <td class="textLabelBold" width="29%"><bean:write name="frmPreAuthGeneral" property="officeName"/></td>
			  </tr>
			</table>
		</fieldset>
	<%
		}--%>
	<%} %>
	
	
<%-- 
	<ttk:ClaimantDetails/>
--%>
	<%
		if(TTKCommon.getActiveLink(request).equals("Claims"))
		 {
	%>

			<fieldset>
				<legend>Claim Details</legend>
				<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				  <tr>
				    <td width="22%" class="formLabel">Claim No.:</td>
				    <td width="30%" class="textLabelBold"><bean:write name="frmPreAuthGeneral" property="preAuthNo"/></td>
				    <td width="19%" class="formLabel">Claim File No.:</td>
				    <td width="29%" class="textLabelBold"><bean:write name="frmPreAuthGeneral" property="claimDetailVO.claimFileNbr"/></td>
				  </tr>
				  <tr>
				    <td class="formLabel">Request Type: </td> 
				    <td class="textLabel">
				    <bean:write name="frmPreAuthGeneral" property="claimDetailVO.requestTypeDesc"/>

				   </td>
				    <logic:match name="frmPreAuthGeneral" property="claimDetailVO.requestTypeID" value="DTA">
					    <td class="formLabel" id="prevClaimNo" style="visibility:">
				    </logic:match>

					<logic:notMatch name="frmPreAuthGeneral" property="claimDetailVO.requestTypeID" value="DTA">
				    	<td class="formLabel" id="prevClaimNo" style="visibility: hidden; ">
				    </logic:notMatch>
				    Prev. Claim No.: </td>
				    <logic:match name="frmPreAuthGeneral" property="claimDetailVO.requestTypeID" value="DTA">
				    	<td class="textLabelBold" id="prevClaimNoValue" style="visibility: ">
			    	</logic:match>
			    	<logic:notMatch name="frmPreAuthGeneral" property="claimDetailVO.requestTypeID" value="DTA">
				    	<td class="formLabel" id="prevClaimNo" style="visibility: hidden; ">
				    </logic:notMatch>

						<bean:write name="frmPreAuthGeneral" property="prevClaimNbr" />
				     <%--<html:select property="clmParentSeqID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode1 %>">
						    <html:option value="">Select from list</html:option>
						    <logic:notEmpty name="frmPreAuthGeneral" property="alPrevClaimList">
								<html:optionsCollection property="alPrevClaimList" label="cacheDesc" value="cacheId" />
							</logic:notEmpty>
		 			</html:select>--%>

				    </td>
				  </tr>
				  <tr>
				    <td class="formLabel">Claim Type:</td>
				    <td class="textLabelBold"><bean:write name="frmPreAuthGeneral" property="claimDetailVO.claimTypeDesc"/></td>
				    <td class="formLabel">Claim Sub Type: <span class="mandatorySymbol">*</span> </td>
				    <td class="textLabel">
				    <logic:match name="frmPreAuthGeneral" property="claimDetailVO.claimTypeID" value="CNH">
					    <html:select property="claimDetailVO.claimSubTypeID" styleClass="selectBox selectBoxMedium" disabled="true" >
						    <html:optionsCollection name="claimSubType" label="cacheDesc" value="cacheId" />
			 			</html:select>
			 		</logic:match>

			 		<logic:notMatch name="frmPreAuthGeneral" property="claimDetailVO.claimTypeID" value="CNH">
			 			<html:select property="claimDetailVO.claimSubTypeID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode | blnAmmendmentFlow %>" onchange="showhideClaimSubType(this);">
						    <html:optionsCollection name="claimSubType" label="cacheDesc" value="cacheId" />
			 			</html:select>
			 		</logic:notMatch>

				    </td>
				  </tr>
				 
			         <logic:match name="frmPreAuthGeneral" property="claimDetailVO.claimSubTypeID" value="CSD">
			 			    <tr id="domicilaryinfo" style="display:">
			 			    
			 			     <td class="formLabel" colspan="1">Reason for Domicilary Hospitilization:</td>
				             <td class="formLabel" colspan="3">
			 			           <html:select property="claimDetailVO.domicilaryReason" styleClass="selectBox selectBoxListMoreLargest1"  disabled="<%=viewmode%>" >
						       <%--<html:optionsCollection name="domicilaryReasonList" label="cacheDesc" value="cacheId" />--%>
						           <html:option value="DCT">Condition of the Patient is tough such he/ she cannot be shifted to Hospital</html:option>
						           <html:option value="DLA">The patient cannot be removed to Hospital/Nursing home due to lack of accommodation in any hospital in that city/town/village</html:option>
						           </html:select>
			 			       </td>
			 			    </tr>
			 			 
			 			 <tr id ="domicilaryinfocheckBox" style="display:">
			 		         <td class="formLabel">Treatment Certified by Doctor : 	</td>
			 		   	     <td>
			 		   	       <logic:equal name="frmPreAuthGeneral" property="claimDetailVO.doctorCertificateYN" value="Y">
			 		     	      <html:checkbox styleClass="margin-left:-4px;" property="claimDetailVO.doctorCertificateYN" value="Y"  disabled="<%=viewmode%>" onclick="javascript:onDoctorCertificate();" />
			 		            </logic:equal>
			 		            <logic:notEqual name="frmPreAuthGeneral" property="claimDetailVO.doctorCertificateYN" value="Y">
			 		        	  <html:checkbox styleClass="margin-left:-4px;" property="claimDetailVO.doctorCertificateYN"  disabled="<%=viewmode%>" value="" onclick="javascript:onDoctorCertificate();" />
			 		            </logic:notEqual>
			 		        </td>
			 		   </tr>
			 		</logic:match>
			 		<logic:notMatch name="frmPreAuthGeneral" property="claimDetailVO.claimSubTypeID" value="CSD">
			 			  <tr id="domicilaryinfo" style="display: none">
			 			  
			 			     <td class="formLabel" colspan="1">Reason for Domicilary Hospitilization:</td>
				             <td class="formLabel" colspan="3" >
			 			           <html:select property="claimDetailVO.domicilaryReason" styleClass="selectBox selectBoxListMoreLargest1"  disabled="<%=viewmode%>" >
						       <%--<html:optionsCollection name="domicilaryReasonList" label="cacheDesc" value="cacheId" />--%>
						           <html:option value="DCT">Condition of the Patient is tough such he/ she cannot be shifted to Hospital</html:option>
						           <html:option value="DLA">The patient cannot be removed to Hospital/Nursing home due to lack of accommodation in any hospital in that city/town/village</html:option>
						           </html:select>
			 			       </td>
			 			    </tr>
			 			
			 		       
			 		       <tr id ="domicilaryinfocheckBox" style="display: none">
  						<td class="formLabel">Treatment Certified by Doctor : 	</td>
			 		              <td>
			 		   	        <logic:equal name="frmPreAuthGeneral" property="claimDetailVO.doctorCertificateYN" value="Y">
			 		     	      <html:checkbox styleClass="margin-left:-4px;" property="claimDetailVO.doctorCertificateYN" value="Y"  disabled="<%=viewmode%>" onclick="javascript:onDoctorCertificate();" />
			 		            </logic:equal>
			 		            <logic:notEqual name="frmPreAuthGeneral" property="claimDetailVO.doctorCertificateYN" value="Y">
			 		        	  <html:checkbox styleClass="margin-left:-4px;" property="claimDetailVO.doctorCertificateYN"  disabled="<%=viewmode%>" value="N" onclick="javascript:onDoctorCertificate();" />
			 		            </logic:notEqual>
			 		             </td>
							</tr>
			 		</logic:notMatch>
				  <tr>
				  
				    <td class="formLabel">Reopen Type: </td>
				    <td class="textLabel">
				    	<bean:write name="frmPreAuthGeneral" property="claimDetailVO.reopenTypeDesc"/>
					</td>
					 <%--OPD_4_hosptial --%>
				  <logic:match name="frmPreAuthGeneral" property="claimDetailVO.claimSubTypeID" value="OPD">
			 			   			    
			 			     <td class="formLabel" colspan="1">Payment To:</td>
				             <td class="formLabel" id="paymentto" style="display:" colspan="3">
			 	            <html:select property="claimDetailVO.paymentType" styleClass="selectBox selectBoxMedium"  disabled="<%= viewmode | blnAmmendmentFlow %>" >
						 	<html:optionsCollection name="paymentType" label="cacheDesc" value="cacheId" />
						           </html:select>
			 			       </td>
			 			
			 		</logic:match>
			 		 <logic:notMatch name="frmPreAuthGeneral" property="claimDetailVO.claimSubTypeID" value="OPD">
			 			    			    
			 			     <td class="formLabel" colspan="1">Payment To:</td>
				             <td class="formLabel" id="paymentto" style="display:none" colspan="3">
			 	            <html:select property="claimDetailVO.paymentType" styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>" >
						 	<html:optionsCollection name="paymentType" label="cacheDesc" value="cacheId" />
						           </html:select>
			 			       </td>
			 		</logic:notMatch>
			 		<%--OPD_4_hosptial --%>
				  </tr>
				  <tr>
				    <td class="formLabel"> Intimation Date: </td>
				    <td class="textLabelBold"><bean:write name="frmPreAuthGeneral" property="claimDetailVO.intimationDate"/></td>
				    <td class="formLabel">Mode:</td>
				    <td class="textLabelBold">
				      	<bean:write name="frmPreAuthGeneral" property="claimDetailVO.modeTypeDesc"/>
				    </td>
				  </tr>
				  <tr>
				    <td class="formLabel"> Received Date / Time:<span class="mandatorySymbol">*</span></td>
				    <td class="textLabel">
					    <html:text property="claimDetailVO.claimReceivedDate" styleClass="textBoxDisabled textDate" maxlength="10" disabled="true"/>
					</td>
					
					<td class="formLabel"> Requested  Amt. (Rs):<span class="mandatorySymbol">*</span> </td>
				    <td   class="textLabel">
				    	<html:text property="claimRequestAmount" styleId="requestamt" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
				    </td>
				   </tr>
				  <tr>
				    <td class="formLabel">Treating Doctor's Name: </td>
				    <td class="textLabel">
				    	<html:text property="doctorName" styleClass="textBox textBoxLarge" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
				    </td>
				    <td class="formLabel">Doctor's Regn. No.: </td>
				    <td class="textLabel">
				    	<html:text property="claimDetailVO.doctorRegnNbr" styleClass="textBox textBoxLarge" maxlength="60" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
				    </td>
				    </tr>
				    <tr>
				    <td class="formLabel"> In Patient No.: </td>
				    <td class="textLabel">
				    	<html:text property="claimDetailVO.inPatientNbr" styleClass="textBox textBoxLarge" maxlength="10" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);" readonly="<%= viewmode %>"/>
				    </td>
				  </tr>
				  <tr>
				    <td height="20" class="formLabel">Vidal Health Branch: </td>
				    <td class="textLabelBold"><bean:write name="frmPreAuthGeneral" property="officeName"/></td>
				    <td class="formLabel">Source:</td>
				    <td class="formLabelBold"><bean:write name="frmPreAuthGeneral" property="claimDetailVO.sourceDesc"/></td>
				  </tr>
					<tr>
				      <td height="20" class="formLabel">Assigned To:</td>
				      <td class="textLabelBold"><bean:write name="frmPreAuthGeneral" property="assignedTo"/></td>
				      <td class="formLabel">Processing Branch:</td>
				      <td class="textLabelBold"><bean:write name="frmPreAuthGeneral" property="processingBranch"/></td>
					  </tr>
					<tr>
				      <td class="formLabel">Remarks:</td>
				      <td colspan="3" class="textLabel">
				      	<html:textarea property="claimDetailVO.claimRemarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
				      </td>
				    </tr>
				    
					</table>
			</fieldset>
		
			<logic:match name="frmPreAuthGeneral" property="claimDetailVO.claimSubTypeID" value="HCU">
				<div id="hospitalizationdetail" style="display:none;">
			</logic:match>
			<!-- added for KOC-1273 -->
			<logic:notMatch name="frmPreAuthGeneral" property="claimDetailVO.claimSubTypeID" value="HCU">
			 <logic:equal name="frmPreAuthGeneral" property="claimDetailVO.claimSubTypeID" value="CTL">
				<div id="hospitalizationdetail" style="display:none;">
				</logic:equal>
				
			<logic:notEqual name="frmPreAuthGeneral" property="claimDetailVO.claimSubTypeID" value="CTL">
				<div id="hospitalizationdetail" style="display:">
			</logic:notEqual>

			 </logic:notMatch>
			<!-- Ended -->
				<ttk:HospitalizationDetails/>
			</div>
	<%
		}
	%>
<%-- 
	<ttk:PolicyDetails/>
	<ttk:InsuranceCompany/>
	<ttk:CorporateDetails/>
--%>
<%-- 
<%
		if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
		 {

%>
	<div id="additionalinfo" style="display:none;">
	<fieldset>
			<legend>Additional Details</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			  	<tr>
				    <td width="22%" height="20" class="formLabel">Relationship:</td>
				    <td width="30%" class="textLabelBold">
				      <html:select property="additionalDetailVO.relationshipTypeID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
						<html:option value="">Select from list</html:option>
						<html:optionsCollection name="relationshipCode" label="cacheDesc" value="cacheId" />
				  	  </html:select>
				     </td>
				    
			    	<td id="empNoLabel" class="formLabel" width="19%">Employee No.:</td>
				    <td id="empNoField" class="textLabelBold" width="29%">
				    	<html:text property="additionalDetailVO.employeeNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
					</td>
				    
				</tr>
			  	 <tr id="empName" style="display:none;">
			  	 <td class="formLabel">Employee Name: </td>
					  <td class="formLabel">
					  	<html:text property="additionalDetailVO.employeeName" styleClass="textBox textBoxLarge" maxlength="60" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
					  </td>
				  <td class="formLabel">Date of Joining: </td>
				  <td class="textLabel">
				  	<html:text property="additionalDetailVO.joiningDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
		    		<logic:match name="viewmode" value="false">
					    <a name="CalendarObjecttxtjoinDate" id="CalendarObjecttxtjoinDate" href="#" onClick="javascript:show_calendar('CalendarObjecttxtjoinDate','frmPreAuthGeneral.elements[\'additionalDetailVO.joiningDate\']',document.frmPreAuthGeneral.elements['additionalDetailVO.joiningDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a>
				    </logic:match>
				 </td>
				</tr>
				<tr id="schemeName" style="display:none;">	  
				 <td class="formLabel">Scheme Name: </td>
				  <td class="formLabel">
				  	<html:text property="additionalDetailVO.insScheme" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
				  </td>
				  <td class="formLabel">Certificate No.: </td>
				  <td class="formLabel">
				  	<html:text property="additionalDetailVO.certificateNo" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
				  </td>
				</tr>
				
				<tr>
				  <td class="formLabel">Received Date / Time: </td>
				  <td class="textLabel">
				      <html:text property="additionalDetailVO.addReceivedDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
					    <logic:match name="viewmode" value="false"><a name="CalendarObjecttxtjoinDate" id="CalendarObjecttxtjoinDate" href="#" onClick="javascript:show_calendar('CalendarObjecttxtjoinDate','frmPreAuthGeneral.elements[\'additionalDetailVO.addReceivedDate\']',document.frmPreAuthGeneral.elements['additionalDetailVO.addReceivedDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match>&nbsp;
				    	<html:text property="additionalDetailVO.addReceivedTime"  styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
				    	<html:select property="additionalDetailVO.addReceivedDay" name="frmPreAuthGeneral" styleClass="selectBox" disabled="<%=viewmode%>">
        				 <html:options name="ampm" labelName="ampm"/>
						</html:select>
			      </td>
				  <td class="formLabel">Received From: </td>
				  <td class="formLabel">
					  <html:select property="additionalDetailVO.receivedFromType" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
							<html:option value="">Select from list</html:option>
							<html:optionsCollection name="receivedFrom" label="cacheDesc" value="cacheId" />
					  </html:select>
				  </td>
				  </tr>
			  	<tr>
				  <td class="formLabel">Source: </td>
				  <td class="textLabel">
					  <html:select property="additionalDetailVO.sourceTypeID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
							<html:option value="">Select from list</html:option>
							<html:optionsCollection name="commCode" label="cacheDesc" value="cacheId" />
					  </html:select>
			       </td>
				  <td class="formLabel">Reference No.: </td>
				  <td class="formLabel">
					  <html:text property="additionalDetailVO.referenceNbr" styleClass="textBox textBoxMedium" maxlength="25" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
				  </td>
				</tr>
				 <tr>
				    <td class="formLabel">Contact Person: </td>
				    <td class="formLabel">
					    <html:text property="additionalDetailVO.contactPerson" styleClass="textBox textBoxLarge" maxlength="60" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
				    </td>
				    <td class="textLabel">&nbsp;</td>
				    <td class="textLabel">&nbsp;</td>
				  </tr>
				  <tr>
			      <td class="formLabel">Remarks:</td>
			      <td colspan="3" class="textLabel">
			      	<html:textarea property="additionalDetailVO.additionalRemarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
			      </td>
				  </tr>
			  </table>
		  </fieldset>
	 </div>
<%
}
%>
--%>
<%-- 	<ttk:HospitalDetails/>--%>
	


	<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="100%" align="center">
			      	<logic:match name="viewmode" value="false">
			      	<%
						if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
		 				{
					%>
			      	<logic:empty name="frmPreAuthGeneral" property="preAuthSeqID">
			      			<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onUserSubmit()"><u>S</u>ave</button>&nbsp;
					    	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
					</logic:empty>
			      	<logic:notEmpty name="frmPreAuthGeneral" property="preAuthSeqID">
			      		<logic:notEqual name="codingReview" value="Y">
					    	<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onUserSubmit()"><u>S</u>ave</button>&nbsp;
					    	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
					    	<%
								if(TTKCommon.isAuthorized(request,"SpecialPermission"))
								 {
							%>
							<logic:match name="frmPreAuthGeneral" property="discPresentYN" value="Y">
					    			<button type="button" name="Button2" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onDiscrepancySubmit()">R<u>e</u>solve Discrepancy</button>&nbsp;
				   			</logic:match>
				   			<%
							if((TTKCommon.isAuthorized(request,"UnFreeze")) || (TTKCommon.isAuthorized(request,"Override Insurance Config")))
								 {//1274A
				   				%>
				   				<logic:equal name="frmPreAuthGeneral" property="insUnfreezeButtonYN" value="Y">
				   			       <logic:match name="frmPreAuthGeneral" property="completedYN" value="N">
				   				   <button type="button" name="Button3" accesskey="f" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onUnfreeze()">Un<u>f</u>reeze </button>&nbsp;<%--1274A --%>
				   				  </logic:match>
							</logic:equal>
							<logic:equal name="frmPreAuthGeneral" property="insDecisionyn" value="Y">
				   				<logic:match name="frmPreAuthGeneral" property="completedYN" value="N">
				   				  <button type="button" name="Button4" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onInsOverrideConf()">O<u>v</u>erride Healthcare Config </button>&nbsp;<%--1274A --%>
								</logic:match>
								</logic:equal>
							
				   			<%
								 }//if(TTKCommon.isAuthorized(request,"UnFreeze"))
				   				}//end of if(TTKCommon.isAuthorized(request,"SpecialPermission"))
				   			
				   			%>
				   				
			   			</logic:notEqual>
			   		</logic:notEmpty>
			   		<%
			   			}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
			   			else{
			   		%>	
			   		<logic:notEmpty name="frmPreAuthGeneral" property="claimSeqID">
			      		<logic:notEqual name="codingReview" value="Y">
					    	<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onUserSubmit()"><u>S</u>ave</button>&nbsp;
					    	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
					    	<%
								if(TTKCommon.isAuthorized(request,"SpecialPermission"))
								 {
							%>
							<logic:match name="frmPreAuthGeneral" property="discPresentYN" value="Y">
					    			<button type="button" name="Button2" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onDiscrepancySubmit()">R<u>e</u>solve Discrepancy</button>&nbsp;
				   			</logic:match>
				   			<%
							//1274A
				   			if(TTKCommon.isAuthorized(request,"UnFreeze") || TTKCommon.isAuthorized(request,"Override Insurance Config"))
								 {//1274A
				   				%>
				   				<logic:equal name="frmPreAuthGeneral" property="insUnfreezeButtonYN" value="Y">
				   				<logic:match name="frmPreAuthGeneral" property="completedYN" value="N">
				   					<button type="button" name="Button3" accesskey="f" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onUnfreeze()">Un<u>f</u>reeze </button>&nbsp;<%--1274A --%>
	                           </logic:match>
								</logic:equal>
								<logic:equal name="frmPreAuthGeneral" property="insDecisionyn" value="Y">
				   				<logic:match name="frmPreAuthGeneral" property="completedYN" value="N">
				   				  <button type="button" name="Button4" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onInsOverrideConf()">O<u>v</u>erride Healthcare Config </button>&nbsp;<%--1274A --%>
								</logic:match>
								</logic:equal>
				   			<%
								 }//if(TTKCommon.isAuthorized(request,"UnFreeze"))
									 //1274A
				   				}//end of if(TTKCommon.isAuthorized(request,"SpecialPermission"))
				   			%>
			   			</logic:notEqual>
			   		</logic:notEmpty>
			   		<%
			   			}//end of else
			   		%>	
					</logic:match>
		    </td>
		</tr>
		</table>
	<!-- E N D : Buttons -->

</div>

<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="">
<html:hidden property="preAuthHospitalVO.hospSeqId"/>
<html:hidden property="claimantDetailsVO.policySeqID"/>
<html:hidden property="claimantDetailsVO.policySeqID"/>
<html:hidden property="claimantDetailsVO.policyGroupSeqID"/>
<input type="hidden" name="paymentto" value="<bean:write name="frmPreAuthGeneral" property="claimDetailVO.paymentType"/>">
<html:hidden property="DMSRefID"/>
<html:hidden property="preAuthTypeID"/>
<INPUT TYPE="hidden" NAME="doctorCertificateYN" VALUE=""><%--1285 change request --%>
<html:hidden property="completedYN"/>
<html:hidden property="flowType"/>
<html:hidden property="authNbr"/>
<input type="hidden" name="focusID" value="">
<script language="javascript">
	getPreauthReferenceNo(document.forms[1].DMSRefID.value);
</script>
</logic:empty>
<logic:notEmpty name="frmPreAuthGeneral" property="display">
	<html:errors/>

<script> TC_Disabled = true;</script>
</logic:notEmpty>
<logic:notEmpty name="frmPreAuthGeneral" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
<logic:match name="frmPreAuthGeneral" property="flowType" value="PRE">
	<script language="javascript">
				onDocumentLoad();
	</script>
</logic:match>
</html:form>