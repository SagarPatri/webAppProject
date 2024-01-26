<%
/**
 * @ (#) preauthgeneral.jsp May 10, 2006  //koc 11 koc11 preauthclaimgeneralinvestigation.jsp
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
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/preauth/preauthgeneral.js"></script>
<script language="javascript">
	var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>
<%
	boolean viewmode=true;
	boolean bEnabled=false;
	boolean viewmode1=true;
	String ampm[] = {"AM","PM"};	
	String switchType=TTKCommon.checkNull((String)request.getSession().getAttribute("switchType"));
	String ActiveLink=TTKCommon.getActiveLink(request);
        
	boolean blnAmmendmentFlow=false;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
		viewmode1=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	if((TTKCommon.getActiveLink(request).equals("Claims") || (ActiveLink.equals("Support") && switchType.equals("Claim"))) && "Y".equals(ClaimsWebBoardHelper.getAmmendmentYN(request)))
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
	
	//added as per kOC 1285 Change Request  
	pageContext.setAttribute("domicilaryReasonList", Cache.getCacheObject("domHospReason"));//1285 change request
	//added as per kOC 1285 Change Request
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	if(TTKCommon.getActiveLink(request).equals("Pre-Authorization") || (ActiveLink.equals("Support") && switchType.equals("PreAuth")))
	{
		pageContext.setAttribute("codingReview",PreAuthWebBoardHelper.getCodingReviewYN(request));
	}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
	else if(TTKCommon.getActiveLink(request).equals("Claims") || (ActiveLink.equals("Support") && switchType.equals("Claim")))
	{
		pageContext.setAttribute("codingReview",ClaimsWebBoardHelper.getCodingReviewYN(request));
	}//end of else if(TTKCommon.getActiveLink(request).equals("Claims"))	
	
%>

<html:form action="/PreAuthGeneralAction.do" >
<!-- S T A R T : Page Title -->
<logic:empty name="frmPreAuthGeneral" property="display">
<%
	if(TTKCommon.getActiveLink(request).equals("Pre-Authorization") || (ActiveLink.equals("Support") && switchType.equals("PreAuth")))
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
	if(TTKCommon.getActiveLink(request).equals("Claims") || (ActiveLink.equals("Support") && switchType.equals("Claim")))
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
				if((TTKCommon.getActiveLink(request).equals("Pre-Authorization") || (ActiveLink.equals("Support") && switchType.equals("PreAuth"))) && !(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y")))
				 {
			  %>
					<logic:notEmpty name="frmPreAuthGeneral" property="preAuthSeqID">
			    	<strong><bean:write property="eventName" name="frmPreAuthGeneral"/></strong>&nbsp;&nbsp;<logic:match name="viewmode" value="false"><ttk:ReviewInformation/></logic:match>&nbsp;
			    	</logic:notEmpty >
			  <%
			   	}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization")&& !(PreAuthWebBoardHelper.getCodingReviewYN(request).equals("Y")))
			  %>

			  <%
				if((TTKCommon.getActiveLink(request).equals("Claims") || (ActiveLink.equals("Support") && switchType.equals("Claim"))) && !(ClaimsWebBoardHelper.getCodingReviewYN(request).equals("Y")))
				 {
			  %>
			    	<strong><bean:write property="eventName" name="frmPreAuthGeneral"/></strong>&nbsp;&nbsp;<logic:match name="viewmode" value="false"><ttk:ReviewInformation/></logic:match>&nbsp;
			  <%
			   	}
			  %>
		   </td>
	    </tr>
	</table>
	<html:errors/>
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
		if(TTKCommon.getActiveLink(request).equals("Pre-Authorization") || (ActiveLink.equals("Support") && switchType.equals("PreAuth")))
		 {
	%>
		<fieldset>
			<legend>Cashless Details</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			  <tr>
			    <td width="22%" class="formLabel">Cashless No.:</td>
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
				  <td class="formLabel">Vidal Healthcare Branch: <span class="mandatorySymbol">*</span></td>
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
		</fieldset>


	<%
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
		}
	}
	%>

	<ttk:ClaimantDetails/>

	<%
		if(TTKCommon.getActiveLink(request).equals("Claims") || (ActiveLink.equals("Support") && switchType.equals("Claim")))
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
				    <td class="textLabel">
				    	<html:text property="claimRequestAmount" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
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
				    <td height="20" class="formLabel">Vidal Healthcare Branch: </td>
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
			<logic:notMatch name="frmPreAuthGeneral" property="claimDetailVO.claimSubTypeID" value="HCU">
				<div id="hospitalizationdetail" style="display:">
			</logic:notMatch>
				<ttk:HospitalizationDetails/>
			</div>
	<%
		}
	%>

	<ttk:PolicyDetails/>
	<ttk:InsuranceCompany/>
	<ttk:CorporateDetails/>

<%
		if(TTKCommon.getActiveLink(request).equals("Pre-Authorization") || (ActiveLink.equals("Support") && switchType.equals("PreAuth")))
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
	<ttk:HospitalDetails/>


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
				   				}//end of if(TTKCommon.isAuthorized(request,"SpecialPermission"))
				   			%>
			   			</logic:notEqual>
			   		</logic:notEmpty>
			   		<%
			   			}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
			   			else if(!(ActiveLink.equals("Support") && switchType.equals("Claim")))
			   			{
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