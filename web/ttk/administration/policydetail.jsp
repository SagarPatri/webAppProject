
<%
	/**
	 * @ (#) policydetail.jsp Feb 27 2006
	 * Project      : TTK HealthCare Services
	 * File         : policydetail.jsp
	 * Author       : Pradeep R
	 * Company      : Span Systems Corporation
	 * Date Created : Feb 27 2006
	 *
	 * @author       : Pradeep R
	 * Modified by   : Arun K N
	 * Modified date : May 24 2006
	 * Reason        : added buffer related information, reset button
	 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%
	pageContext.setAttribute("listIssueCheque", Cache.getCacheObject("issueCheque"));
	pageContext.setAttribute("listStatusCode", Cache.getCacheObject("productStatusCode"));
	pageContext.setAttribute("listEnrollmentID", Cache.getCacheObject("genEnrollmentID"));
	pageContext.setAttribute("listUserGrp", Cache.getCacheObject("userGroup"));
	pageContext.setAttribute("listAdminAuthority", Cache.getCacheObject("adminAuthority"));
	pageContext.setAttribute("listAllocatedType", Cache.getCacheObject("allocatedType"));
	pageContext.setAttribute("CardTemplateID", Cache.getCacheObject("template"));
	pageContext.setAttribute("VoucherRequired", Cache.getCacheObject("voucherRequired"));
	pageContext.setAttribute("healthCheckType", Cache.getCacheObject("healthCheckType"));//OPD_4_hosptial
	ArrayList alUserGroup = (ArrayList) session.getAttribute("alUserGroup");
	ArrayList alBrokerGroup = (ArrayList) session.getAttribute("alBrokerGroup");
	

	boolean viewmode = true;
	boolean viewModeForExpiredPolicy=false;
	boolean bBufferAllowedYN = true;
	boolean bBrokerAllowedYN = true;
	if (TTKCommon.isAuthorized(request, "Edit")) {
		viewmode = false;
	}
	if(request.getSession().getAttribute("policyStatusFlag").equals("POE")){
		viewModeForExpiredPolicy=true;
	}
%>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/administration/policydetail.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/EditPoliciesSearchAction.do">
	<!--<logic:notEmpty name="GenerateXL" scope="request">
		<script language="JavaScript">
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 82;
			var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open("<bean:write name="GenerateXL" scope="request"/>",'',features);
		</script>
	</logic:notEmpty>
	--><!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>Policy Details</td>
			<td align="right">
				<a href="#" onClick="onConfiguration()">
					<img src="/ttk/images/EditIcon.gif" alt="Configuration List" width="16"	height="16" border="0" align="absmiddle">
				</a>
			</td>
			<td align="right" class="webBoard">
				&nbsp;<%@ include file="/ttk/common/toolbar.jsp"%>
			</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
		<html:errors /> 
			
		<!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td>
						<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp; 
						<bean:message name="updated" scope="request" />
					</td>
				</tr>
			</table>
		</logic:notEmpty> 
		<!-- E N D : Success Box --> 
		
		<!-- S T A R T : Form Fields --> 
		<logic:match name="frmPoliciesEdit" property="bufferAllowedYN" value="Y">
		<%
			bBufferAllowedYN = false;
		%>
		</logic:match>
		
		<%-- <logic:match name="frmPoliciesEdit" property="brokerYN" value="Y">
		<%
			bBrokerAllowedYN = false;
		%>
		</logic:match> --%>
		
		<fieldset>
			<legend>Insurance Company</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="21%" class="formLabel">Insurance Company:</td>
					<td width="30%" class="textLabel">
						<bean:write	name="frmPoliciesEdit" property="companyName" />
					</td>
					<td width="21%" class="formLabel">Insurance Code:</td>
					<td width="28%" class="textLabel">
						<bean:write	name="frmPoliciesEdit" property="officeCode" />
					</td>
				</tr>
			</table>
		</fieldset>

		<fieldset>
			<legend>Group Information </legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="21%" class="formLabel">Group Name:</td>
					<td width="30%" class="textLabel">
						<bean:write	name="frmPoliciesEdit" property="groupName" />
					</td>
					<td width="21%" class="formLabel">Group-Sub Group Id:</td>
					<td width="28%" class="textLabel">
						<bean:write	name="frmPoliciesEdit" property="groupID" />
					</td>
				</tr>
			</table>
		</fieldset>

		<fieldset>
			<legend>Policy Information</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="21%" nowrap class="formLabel">Policy No.:</td>
					<td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="policyNbr" />
					</td>
					<td width="21%" nowrap class="formLabel">Policy Received Date:</td>
					<td width="28%" nowrap class="textLabel">
						<bean:write	name="frmPoliciesEdit" property="recdDate" />
					</td>
				</tr>
				<tr>
					<td class="formLabel">Policy Type:</td>
					<td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="enrollmentDesc" />
					</td>
					<td class="formLabel">Policy Sub Type:</td>
					<td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="policySubType" />
					</td>
				</tr>
				<tr>
					<td class="formLabel">Policy Category:</td>
					<td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="policyCategory" />
					</td>
					<td class="formLabel">Policy Category Remarks:</td>
					<td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="policyCategoryRemarks" />
					</td>
				</tr>
				<tr>
					<td class="formLabel">Product / Policy Name:</td>
					<td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="productName" />
					</td>
					<td class="formLabel">Policy Status:</td>
					<td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="policyStatus" />
					</td>
				</tr>
				<tr>
					<td class="formLabel">
					<%--
					Changes By Rishi, 
					Capturing Multiple Product Authority ID of a Product
					 --%>
					<%-- <logic:equal name="frmPoliciesEdit" property="healthAuthority" value="HAAD">
	    			Authority Product Reference No:
	    			</logic:equal> --%>
	    			<%-- <logic:notEqual name="frmPoliciesEdit" property="healthAuthority" value="HAAD">
	    			<span id="authorityID">Authority Product ID:</span>
	    			</logic:notEqual> --%>
					</td>	
					
					<td width="30%" nowrap>						
					<%-- 	<html:text styleClass="textBox textBoxSmall" name="frmPoliciesEdit" property="authorityProductCode" maxlength="100" disabled="<%=viewmode%>" /> --%>
					</td>
					  <td class="formLabel">Product Authority:</td>
					<td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="healthAuthority" />
					</td>
					<%-- <td class="textLabel">														
						<bean:write name="frmPoliciesEdit" property="authorityProductCode" /> --%>
				</tr>
				
				
				
				
				 <tr>
			 
			 
			 
			 
			 <logic:equal name="frmPoliciesEdit" property="premiumDatesFlag"  value="Y">
        	  <td  class="formLabel">VAT(%): <span class="mandatorySymbol">*</span></td>
        	   <td>
        	  <logic:equal name="frmPoliciesEdit" property="policyStatus" value="Cancelled">
        	   <html:text property="vatPercent" styleClass="textBox textBoxSmall" maxlength="5" onkeyup="isNumaricOnly(this);" disabled="true" readonly="true" />
	      	</logic:equal>
	      	<logic:notEqual name="frmPoliciesEdit" property="policyStatus" value="Cancelled">
	      	<html:text property="vatPercent" styleClass="textBox textBoxSmall" maxlength="5" onkeyup="isNumaricOnly(this);"  />
	      	</logic:notEqual>
	      	</td> 
			</logic:equal>
				
				
				
				
				
			 <td class="formLabel">Pre-defined/SME product   : </td>
			 <td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="sMEproductYN" />
					</td>
					
			</tr>		
				
				
				
				
				
				
				
				
				
				<tr>
					<td class="formLabel">Policy Start Date:</td>
					<td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="startDate" />
					</td>
					<td class="formLabel">Policy End Date:</td>
					<td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="endDate" />
					</td>
				</tr>
				<tr>
					<td class="formLabel">Policy Issue Date:</td>
					<td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="issueDate" />
					</td>
				    <%-- <td class="formLabel">Administration Policy Type:</td>
					<td class="textLabel">
							<logic:equal value="2" name="frmPoliciesEdit" property="capitationflag" scope="session">
							<span style="font-size:11px;">
						    AS Plus(OP)
						    </span>
						 </logic:equal>
						 
						 <logic:equal value="3" name="frmPoliciesEdit" property="capitationflag" scope="session">
							<span style="font-size:11px;">
						    AS Plus(IP & OP)
						    </span>
						 </logic:equal>
						 
						 	<logic:notEqual value="Y" name="frmPoliciesEdit" property="capitationflag" scope="session">
						   <span style="font-size:11px;">
						    ASO
						    </span>
						 </logic:notEqual>
						 
						 
						 
						 
						  <logic:equal value="1" name="frmPoliciesEdit" property="capitationflag" scope="session">
							<span style="font-size:11px;">
						    ASO
						    </span>
						 </logic:equal>
						 
					</td> --%>
				</tr>
				
				<tr>
					<td class="formLabel">Premium to be refunded on policy cancellation: <span class="mandatorySymbol">*</span> </td>
	    			<td colspan="3">
	    			
	    			<logic:equal name="frmPoliciesEdit" property="policyStatus" value="Cancelled">
	    				<html:select property="refundedYN" name="frmPoliciesEdit" styleClass="selectBox" disabled="true" readonly="true">
                  			<html:option value="">Select from list</html:option>
                  				<html:option value="Y">YES</html:option>
                  					<html:option value="N">NO</html:option>
            			</html:select>
	    			
	    			</logic:equal>
	    			<logic:notEqual name="frmPoliciesEdit" property="policyStatus" value="Cancelled">
	    			<html:select property="refundedYN" name="frmPoliciesEdit" styleClass="selectBox" disabled="<%= viewmode %>">
                  			<html:option value="">Select from list</html:option>
                  				<html:option value="Y">YES</html:option>
                  					<html:option value="N">NO</html:option>
            			</html:select>
	    			</logic:notEqual>
	       				
            			  &nbsp; <a href="#" onclick="modificationRemarks(240,530);"><b>Modification Remarks</b></a>
        			</td>
				
				</tr>
				
				
				<tr>
				<td class="formLabel">Allowed Grace Period for Renewal(Days):</td>
					<td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="allowedgradeperiod" />
					</td>
			    <td class="formLabel">Allowed Gap Period for Renewal(Days):</td>
					<td class="textLabel">
						<bean:write name="frmPoliciesEdit" property="allowedgapperiod" />
					</td>
				</tr>
				
				
				
				
			</table>
		</fieldset>
		
		<fieldset>
			<legend>Others</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="21%">Generate Enrollment Id:</td>
					<td width="30%" nowrap class="formLabel">
						<html:select property="enrollOnEmployeeNbr"	styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
							<html:options collection="listEnrollmentID" property="cacheId" labelProperty="cacheDesc" />
						</html:select>
					</td>
					<td class="formLabel" width="21%">Status:</td>
					<td width="28%">
						<html:select property="adminStatusID" styleClass="selectBox" style="width:160px;" disabled="<%=viewmode%>">
							<html:option value="">Select from list</html:option>
							<html:options collection="listStatusCode" property="cacheId" labelProperty="cacheDesc" />
						</html:select>
					</td>
				</tr>
				<tr>
				<td class="formLabel">Card Template: </td>
				<td colspan="3">
				
				           <html:select property="templateID"   styleClass="selectBox selectBoxMedium"  disabled="<%= viewmode %>">
							<html:option value="">Select from list</html:option>
							<logic:notEmpty name="frmPoliciesEdit" property="cardTempList">
							<html:optionsCollection name="frmPoliciesEdit" property="cardTempList" label="cacheDesc" value="cacheId" />
							</logic:notEmpty>
  							
  						 	</html:select>
				        
  							
				</td>
					<td class="formLabel" width="21%">Discharge Voucher:</td>
					<td width="28%">
						<html:select property="dischargeVoucherMandatoryYN"	styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
							<html:option value="">Select from list</html:option>
							<html:options collection="VoucherRequired" property="cacheId" labelProperty="cacheDesc" />
						</html:select>
					</td>
				</tr>
				<tr>
	 				<td class="formLabel">Stop Cashless:</td>	 				
	  				<td>	  					
	 					<html:checkbox property="stopPreAuthsYN" styleId="stopPreAuthsYN"/>
	 				</td>	 				
	 				<td class="formLabel">Stop Claims:</td>	 				
	  				<td>	  				
	 					<html:checkbox property="stopClaimsYN" styleId="stopClaimsYN"/>	 					
	 				</td>
	 			</tr>	 			
				<tr>
				<!-- KOC 1286 for OPD Benefit -->
					<td class="formLabel">OPD Benefit(OutSideSumInsured):</td>	 				
	  				<td>	  				
	 					<html:checkbox property="opdClaimsYN" styleId="opdClaimsYN"/>	 					
	 				</td>
	 				<!-- KOC 1286 for OPD Benefit -->
					<td class="formLabel">Survival Period:</td>	 				
	  				<td>	  				
	 					<html:checkbox property="survivalPeriodYN" styleId="survivalPeriodYN"/>	 					
	 				</td>
					<td width="21%">Tenure:</td>
					<td width="28%" nowrap class="formLabel">
						<html:text property="tenureDays" styleClass="textBox textBoxSmall" maxlength="3" />
					</td>
				</tr>				
			</table>
		</fieldset>
		<!--KOC 1270 for hospital cash benefit-->	
			<fieldset>
			<legend>Cash Benefit</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">		
				<tr>
				<td class="formLabel" width="21%" >Hospital Cash Benefit(WithInSumInsured):</td>	 				
	  				<td width="4%">	  				
	 					<html:checkbox property="cashBenefitYN" styleId="cashBenefitYN"/>	 					
	 				</td>
	 				</tr>
	 				<tr>
	 				<td class="formLabel" width="21%">Canvalescence Cash Benefit(WithInSumInsured):</td>	 				
	  				<td width="4%">	  				
	 					<html:checkbox property="convCashBenefitYN" styleId="convCashBenefitYN"/>	 					
	 				</td>		
				</tr>	
			</table>
			</fieldset>
			<!--KOC 1270 for hospital cash benefit-->
			<!-- AK -->
			<fieldset>
			<legend>Broker Details</legend>
       <!-- S T A R T : Grid -->
       
	 <ttk:HtmlGrid name="tableDataBro"/>
	 
     <!-- E N D : Grid -->
			</fieldset>  
			<!-- <EAK> --> 
		<%--  <fieldset>
			<legend>User Group</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="21%" class="formLabel">User Group:</td>
					<td width="30%" nowrap class="formLabel">
						<html:select property="groupBranchSeqID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
							<html:option value="">Select from list</html:option>
							<html:options collection="alUserGroup" property="cacheId" labelProperty="cacheDesc" />
						</html:select>
					</td>
					
				</tr>	
					  <!--  added for 1347 expenses-->
          <tr>
	 		<td class="formLabel" width="20%">Broker:(Yes/No):</td>	 				
	  				
	  				<td width="4%">	  				
	 					<html:checkbox name="frmPoliciesEdit" property="brokerYN"  value="Y" styleId="brokerYN" onclick="showHideType2()" />
	 					<input type="hidden" name="brokerYN" value="N">	 					
	 				</td>
	 				
	 				
	 		<td class="textLabelBold">		
			<logic:equal name="frmPoliciesEdit" property="isBrokerAllowedSaved" value="Y">
				<html:checkbox property="brokerYN" value="Y" disabled="true" styleId="brokerYN" onclick="return false" />
				<html:hidden property="brokerYN" value="Y" />
			</logic:equal> 
			<logic:notEqual name="frmPoliciesEdit" property="isBrokerAllowedSaved" value="Y">
				<html:checkbox property="brokerYN" value="Y" disabled="<%=viewmode%>" styleId="brokerYN" onclick="javascript:isBrokerAllowedYN()" />
			</logic:notEqual>
				</td>
						
	 				
	     	<td>&nbsp;</td>
	     		
	 	  </tr>
	 	 
		   <tr>
                    <td width="10%" class="formLabel">Broker Group:</td>
					<td width="20%" nowrap class="textLabelBold">
					<logic:equal value="Y" name="frmPoliciesEdit" property="brokerYN">
					<html:select property="brokerID" styleClass="selectBox selectBoxMedium" disabled="<%=(viewmode)%>">
							<html:option value="">Select from list</html:option>
							<html:options collection="alBrokerGroup" property="cacheId" labelProperty="cacheDesc" />
						</html:select>
					</logic:equal>
					<logic:notEqual value="Y" name="frmPoliciesEdit" property="brokerYN">
					<html:select property="brokerID" styleClass="selectBox selectBoxMedium" disabled="<%=(viewmode)%>" style="display: none;">
							<html:option value="">Select from list</html:option>
							<html:options collection="alBrokerGroup" property="cacheId" labelProperty="cacheDesc" />
						</html:select>
					</logic:notEqual>
					</td>
          </tr>
	 	 <tr>
			<td class="formLabel" width="21%">Vidal Health Branch:</td>
			<td class="textLabel" width="28%">
				<bean:write name="frmPoliciesEdit" property="officeName" />
			</td>
		</tr>
			</table>
		</fieldset>  
		 --%>
			
		<fieldset>
			<legend>Agreement</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="21%" nowrap class="formLabel">Card Clearance (Days):</td>
					<td width="30%" nowrap>
						<html:text styleClass="textBox textBoxSmall" name="frmPoliciesEdit" property="cardClearance" maxlength="3" disabled="<%=viewmode%>" />
					</td>
					<td width="21%" nowrap class="formLabel">Issue Cheques To:</td>
					<td width="38%" nowrap class="formLabel">
						<html:select property="issueChqTypeID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
							<html:option value="">Select from list</html:option>
							<html:options collection="listIssueCheque" property="cacheId" labelProperty="cacheDesc" />
						</html:select>
					</td>
				</tr>
				<tr>
					<td class="formLabel">Claim Clearance (Days):</td>
					<td class="textLabelBold">
						<html:text styleClass="textBox textBoxSmall" name="frmPoliciesEdit" property="claimClearance" maxlength="3" disabled="<%=viewmode%>" />
					</td>
					<td class="formLabel">Cashless Clearance (Hrs):</td>
					<td class="textLabelBold">
						<html:text styleClass="textBox textBoxSmall" name="frmPoliciesEdit" property="preAuthClearance" maxlength="2" disabled="<%=viewmode%>" />
					</td>
				</tr>
				<%--OPD_4_hosptial --%>
				
				<tr>
				<td width="21%" nowrap class="formLabel">Health Check UP Payment To:</td>
					<td width="38%" nowrap class="formLabel">
						<html:select property="healthCheckType" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="showhideheealthType(this);">
							<html:option value="">Select from list</html:option>
							<html:options collection="healthCheckType" property="cacheId" labelProperty="cacheDesc" />
						</html:select>
					</td>
					<logic:match name="frmPoliciesEdit" property="healthCheckType" value="TPA">
					<td width="21%" nowrap  class="formLabel">Third Party Name:</td>
					<td width="30%" nowrap id="paymentto" style="display:" >
						<html:text styleClass="textBox textBoxSmall" name="frmPoliciesEdit" property="paymentTo" maxlength="100" disabled="<%=viewmode%>" />
					</td>
					</logic:match>
					<logic:notMatch name="frmPoliciesEdit" property="healthCheckType" value="TPA">
					<td width="21%" nowrap  class="formLabel">Third Party Name:</td>
					<td width="30%" nowrap id="paymentto" style="display:none">
						<html:text styleClass="textBox textBoxSmall" name="frmPoliciesEdit" property="paymentTo" maxlength="100" disabled="<%=viewmode%>" />
					</td>
					</logic:notMatch>
				</tr>
				
				<%--OPD_4_hosptial --%>
			</table>
		</fieldset>
		<fieldset>
			<legend>Buffer Details</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="formLabel">Buffer Allowed:</td>
					<td class="textLabelBold">
						<logic:equal name="frmPoliciesEdit" property="isBufferAllowedSaved" value="Y">
							<html:checkbox property="bufferAllowedYN" value="Y" disabled="true" styleId="bufferAllowedYN" onclick="return false" />
							<html:hidden property="bufferAllowedYN" value="Y" />
						</logic:equal> 
						<logic:notEqual name="frmPoliciesEdit" property="isBufferAllowedSaved" value="Y">
							<html:checkbox property="bufferAllowedYN" value="Y" disabled="<%=viewmode%>" styleId="bufferAllowedYN" onclick="javascript:isBufferAllowedYN()" />
						</logic:notEqual>
					</td>
					<!-- Changes as per KOC 1216B Change Request  -->
					
						<td class="formLabel">Member Buffer Allowed:</td>	 				
	  				<td>	  					
	 					<html:checkbox property="memberBufferYN" styleId="memberBufferYN"/>
	 			
	 				</td>	
	 				
	 				<!-- added for KOC-1273 -->
	 				
	 				<td class="formLabel">Critical Illness Benefit(within sum insured):</td>	 				
	  				<td>	  					
	 					<html:checkbox property="criticalBenefitYN" styleId="criticalBenefitYN"/>
	 				</td>
	 				 		
					<%--<td class="formLabel"> </td>
					<td class="textLabelBold">&nbsp;
				    <logic:match  property="memberBufferYN"  name="frmPoliciesEdit" value="Y">
					<input type="checkbox"  id="memberBufferYN" name="memberBufferYN"  checked="checked" value="Y" onclick="javascript:isCheck()"  disabled="<%=(viewmode || bBufferAllowedYN)%>"/>		
					</logic:match>	
					<logic:notMatch property="memberBufferYN"  name="frmPoliciesEdit" value="Y">
					<input type="checkbox"  id="memberBufferYN" name="memberBufferYN" value="N" onclick="javascript:isCheck()"  disabled="<%=(viewmode || bBufferAllowedYN)%>"/>		
					</logic:notMatch>
					</td>--%>
					
					<!-- Changes as per KOC 1216B Change Request  -->
				</tr>
				<tr>
					<td width="21%" class="formLabel">Administering Authority:</td>
					<td width="30%" class="textLabelBold">
						<html:select property="admnAuthorityTypeID" styleClass="selectBox selectBoxMedium" disabled="<%=(viewmode || bBufferAllowedYN)%>">
							<html:option value="">Select from list</html:option>
							<html:options collection="listAdminAuthority" property="cacheId" labelProperty="cacheDesc" />
						</html:select>
					</td>
					<td width="21%" class="formLabel">Allocated Type:</td>
					<td width="28%" class="textLabelBold">
						<html:select property="allocatedTypeID" styleClass="selectBox selectBoxMedium" disabled="<%=(viewmode || bBufferAllowedYN)%>">
							<html:option value="">Select from list</html:option>
							<html:options collection="listAllocatedType" property="cacheId" labelProperty="cacheDesc" />
						</html:select>
					</td>
				</tr>
				<!-- //added for hyundaiBuffer on 19.05.2014 by Rekha  -->
				 <tr>
	 		        <td class="formLabel" width="21%">HR Approval Required(Yes/No):</td>	 				
	  				<td width="30%">	  				
	 					<html:checkbox  property="hrAppYN"  value="Y" styleId="hrAppYN"  />
	 					<input type="hidden" name="hrAppYN" value="N">	 					
	 				</td>
	 				<td>&nbsp;</td>
	     	            <td>&nbsp;</td>
	     		    <html:hidden property="hrApp" /> 
	 	             </tr>
	 	           <%--    <tr>
	 		        <td class="formLabel" width="21%">Critical Buffer Required(Yes/No):</td>	 				
	  				<td width="30%">	  				
	 					<html:checkbox  property="criBufferYN"  value="Y" styleId="criBufferYN"  />
	 					<input type="hidden" name="criBufferYN" value="N">	 					
	 				</td>
	 				<td>&nbsp;</td>
	     	            <td>&nbsp;</td>
	     		    <html:hidden property="criBuffer" /> 
	 	             </tr> --%>
	 	             <!-- //end  for hyundaiBuffer on 19.05.2014 by Rekha  -->
			</table>
		</fieldset>
		<fieldset>
			<legend>Mail Notificaion </legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="formLabel" width="25%">Cashless mail Notification</td>
					<td class="textLabelBold" width="25%"><html:select property="patEnableYN" name="frmPoliciesEdit" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
							<html:option value="N">Disable</html:option>
							<html:option value="Y">Enable</html:option>
					</html:select>
					</td>
					
					<td class="formLabel" width="25%">Claim mail Notification</td>
					<td class="textLabelBold" width="25%"><html:select property="clmEnableYN" name="frmPoliciesEdit" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
							<html:option value="N">Disable</html:option>
							<html:option value="Y">Enable</html:option>
					</html:select>
					</td>
					
				</tr>
				<tr>
					<td class="formLabel" width="25%">To  :</td>
				<td class="textLabelBold" width="25%"><html:text styleClass="textBox textBoxMedium" name="frmPoliciesEdit" property="patMailTo" maxlength="100" disabled="<%=viewmode%>" />
				</td>
					<td class="formLabel" width="25%">To  :</td>
				<td class="textLabelBold" width="25%"><html:text styleClass="textBox textBoxMedium" name="frmPoliciesEdit" property="clmMailTo" maxlength="100" disabled="<%=viewmode%>" />
				</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">CC  :</td>
				<td class="textLabelBold" width="25%"><html:text styleClass="textBox textBoxMedium" name="frmPoliciesEdit" property="patMailCC" maxlength="100" disabled="<%=viewmode%>" />
				</td>
					<td class="formLabel" width="25%">CC  :</td>
				<td class="textLabelBold" width="25%"><html:text styleClass="textBox textBoxMedium" name="frmPoliciesEdit" property="clmMailCC" maxlength="100" disabled="<%=viewmode%>" />
				</td>
				</tr>
			</table>
		</fieldset>
		<fieldset>
			<legend>Card Benefits</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			<%if(request.getSession().getAttribute("policyStatusFlag").equals("POE")){ %>
			<tr>
					<td class="formLabel" width="25%">Co-insurance</td>
					<td class="textLabelBold" width="25%">
					<html:text onkeyup="isNumeric(this);" styleClass="textBox textBoxSmall" name="frmPoliciesEdit" property="coInsurance" maxlength="3" disabled="<%=viewModeForExpiredPolicy%>" />(%)
					</td>
					<td class="formLabel" width="25%">Deductable</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="deductable" disabled="<%=viewModeForExpiredPolicy%>" />
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Class</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="classRoomType" disabled="<%=viewModeForExpiredPolicy%>" />
					</td>
					<td class="formLabel" width="25%">Plan</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="planType" disabled="<%=viewModeForExpiredPolicy%>" />
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Maternity(Y/N)</td>
					<td class="textLabelBold" width="25%"><html:select property="maternityYN" name="frmPoliciesEdit" styleClass="selectBox selectBoxMedium" disabled="<%=viewModeForExpiredPolicy%>" onchange="validateCopay(this,'MT');">
							<html:option value="N">NO</html:option>
							<html:option value="Y">YES</html:option>
       					</html:select>
					</td>
					
					<td class="formLabel" width="25%">Maternity Co-Pay</td>
					<td class="textLabelBold" width="25%">
					<logic:equal name="frmPoliciesEdit" property="maternityYN" value="Y">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="maternityCopay" disabled="<%=viewModeForExpiredPolicy%>"/>
					</logic:equal>
					<logic:notEqual name="frmPoliciesEdit" property="maternityYN" value="Y">
					<html:text styleClass="textBox textBoxLarge disabledBox" name="frmPoliciesEdit" property="maternityCopay" readonly="true" disabled="<%=viewModeForExpiredPolicy%>"/>
					</logic:notEqual>
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Optical(Y/N)</td>
					<td class="textLabelBold" width="25%">
					<html:select property="opticalYN" name="frmPoliciesEdit" styleClass="selectBox selectBoxMedium" disabled="<%=viewModeForExpiredPolicy%>" onchange="validateCopay(this,'OT');">
							<html:option value="N">NO</html:option>
							<html:option value="Y">YES</html:option>
       					</html:select>
					</td>
					<td class="formLabel" width="25%">Optical Co-Pay</td>
					<td class="textLabelBold" width="25%">
					<logic:equal name="frmPoliciesEdit" property="opticalYN" value="Y">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="opticalCopay" disabled="<%=viewModeForExpiredPolicy%>"/>
					</logic:equal>
					<logic:notEqual name="frmPoliciesEdit" property="opticalYN" value="Y">
					<html:text styleClass="textBox textBoxLarge disabledBox" name="frmPoliciesEdit" property="opticalCopay" readonly="true" disabled="<%=viewModeForExpiredPolicy%>"/>
					</logic:notEqual>
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Dental(Y/N)</td>
					<td class="textLabelBold" width="25%">
					<html:select property="dentalYN" name="frmPoliciesEdit" styleClass="selectBox selectBoxMedium" disabled="<%=viewModeForExpiredPolicy%>" onchange="validateCopay(this,'DT');">
							<html:option value="N">NO</html:option>
							<html:option value="Y">YES</html:option>
       					</html:select>
					</td>
					<td class="formLabel" width="25%">Dental Co-Pay</td>
					<td class="textLabelBold" width="25%">
					<logic:equal name="frmPoliciesEdit" property="dentalYN" value="Y">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="dentalCopay" disabled="<%=viewModeForExpiredPolicy%>"/>
					</logic:equal>
					<logic:notEqual name="frmPoliciesEdit" property="dentalYN" value="Y">
					<html:text styleClass="textBox textBoxLarge disabledBox" name="frmPoliciesEdit" property="dentalCopay" readonly="true" disabled="<%=viewModeForExpiredPolicy%>"/>
					</logic:notEqual>
					
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Eligibility</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="eligibility"  disabled="<%=viewModeForExpiredPolicy%>" />
					</td>
					<td class="formLabel" width="25%">IP/OP Services</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="ipopServices" disabled="<%=viewModeForExpiredPolicy%>" />
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Pharmaceutical</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="pharmaceutical"  disabled="<%=viewModeForExpiredPolicy%>" />
					</td>
					<td class="formLabel" width="25%"></td>
					<td class="textLabelBold" width="25%">
					</td>
				</tr>
			
			<%}else{%>
			<tr>
					<td class="formLabel" width="25%">Co-insurance</td>
					<td class="textLabelBold" width="25%">
					<html:text onkeyup="isNumeric(this);" styleClass="textBox textBoxSmall" name="frmPoliciesEdit" property="coInsurance" maxlength="3" disabled="<%=viewmode%>" />(%)
					</td>
					<td class="formLabel" width="25%">Deductable</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="deductable" disabled="<%=viewmode%>" />
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Class</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="classRoomType" disabled="<%=viewmode%>" />
					</td>
					<td class="formLabel" width="25%">Plan</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="planType" disabled="<%=viewmode%>" />
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Maternity(Y/N)</td>
					<td class="textLabelBold" width="25%"><html:select property="maternityYN" name="frmPoliciesEdit" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="validateCopay(this,'MT');">
							<html:option value="N">NO</html:option>
							<html:option value="Y">YES</html:option>
       					</html:select>
					</td>
					
					<td class="formLabel" width="25%">Maternity Co-Pay</td>
					<td class="textLabelBold" width="25%">
					<logic:equal name="frmPoliciesEdit" property="maternityYN" value="Y">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="maternityCopay" disabled="<%=viewmode%>"/>
					</logic:equal>
					<logic:notEqual name="frmPoliciesEdit" property="maternityYN" value="Y">
					<html:text styleClass="textBox textBoxLarge disabledBox" name="frmPoliciesEdit" property="maternityCopay" readonly="true" disabled="<%=viewmode%>"/>
					</logic:notEqual>
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Optical(Y/N)</td>
					<td class="textLabelBold" width="25%">
					<html:select property="opticalYN" name="frmPoliciesEdit" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="validateCopay(this,'OT');">
							<html:option value="N">NO</html:option>
							<html:option value="Y">YES</html:option>
       					</html:select>
					</td>
					<td class="formLabel" width="25%">Optical Co-Pay</td>
					<td class="textLabelBold" width="25%">
					<logic:equal name="frmPoliciesEdit" property="opticalYN" value="Y">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="opticalCopay" disabled="<%=viewmode%>"/>
					</logic:equal>
					<logic:notEqual name="frmPoliciesEdit" property="opticalYN" value="Y">
					<html:text styleClass="textBox textBoxLarge disabledBox" name="frmPoliciesEdit" property="opticalCopay" readonly="true" disabled="<%=viewmode%>"/>
					</logic:notEqual>
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Dental(Y/N)</td>
					<td class="textLabelBold" width="25%">
					<html:select property="dentalYN" name="frmPoliciesEdit" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="validateCopay(this,'DT');">
							<html:option value="N">NO</html:option>
							<html:option value="Y">YES</html:option>
       					</html:select>
					</td>
					<td class="formLabel" width="25%">Dental Co-Pay</td>
					<td class="textLabelBold" width="25%">
					<logic:equal name="frmPoliciesEdit" property="dentalYN" value="Y">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="dentalCopay" disabled="<%=viewmode%>"/>
					</logic:equal>
					<logic:notEqual name="frmPoliciesEdit" property="dentalYN" value="Y">
					<html:text styleClass="textBox textBoxLarge disabledBox" name="frmPoliciesEdit" property="dentalCopay" readonly="true" disabled="<%=viewmode%>"/>
					</logic:notEqual>
					
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Eligibility</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="eligibility"  disabled="<%=viewmode%>" />
					</td>
					<td class="formLabel" width="25%">IP/OP Services</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="ipopServices" disabled="<%=viewmode%>" />
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Pharmaceutical</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmPoliciesEdit" property="pharmaceutical"  disabled="<%=viewmode%>" />
					</td>
					<td class="formLabel" width="25%"></td>
					<td class="textLabelBold" width="25%">
					</td>
				</tr>
				<%} %>
				</table>
				</fieldset>
				
				<logic:equal property="healthAuthority" name="frmPoliciesEdit" value="DHA" scope="session">
						<fieldset>
				<legend>Plan Benefits</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="formLabel" width="25%">Maternity Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmPoliciesEdit" property="planMaternityCoPay" cols="30" rows="2" disabled="<%=viewmode%>" onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
					<td class="formLabel" width="25%">IP Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmPoliciesEdit" property="planIpCoPay" cols="30" rows="2" disabled="<%=viewmode%>" onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Optical Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmPoliciesEdit" property="planOpticalCoPay" cols="30" rows="2" disabled="<%=viewmode%>" onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
					<td class="formLabel" width="25%">OP Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmPoliciesEdit" property="planOpCoPay" cols="30" rows="2" disabled="<%=viewmode%>" onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
				</tr>
				
				<tr>
					<td class="formLabel" width="25%">Dental Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmPoliciesEdit" property="planDentalCoPay" cols="30" rows="2" disabled="<%=viewmode%>" onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
					<td class="formLabel" width="25%">OP Consultation Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmPoliciesEdit" property="planOpConsultationCoPay" cols="30" rows="2" disabled="<%=viewmode%>" onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
				</tr>
				
				<tr>
					<td class="formLabel" width="25%" />
					<td class="textLabelBold" width="25%" />
					<td class="formLabel" width="25%">Pharmacy Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmPoliciesEdit" property="planPharmacyCoPay" cols="30" rows="2" disabled="<%=viewmode%>"  onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
				</tr>
				</table>
				</fieldset>
				</logic:equal>
				<!--END Card Benefits add by bikki -->
		<!-- E N D : Form Fields --> 
		
		<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" align="center">
				<%
				if (TTKCommon.isAuthorized(request, "Edit")) 
				{
				%>
					<button type="button" name="Button1" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateXL();"><u>G</u>enerateXL</button>&nbsp;
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp; 
				<%
 				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
 				%>
				</td>
			</tr>
		</table>
		<!-- E N D : Buttons --> 				
	</div>
	<input type="hidden" name="mode" value=""> 
	<input type="hidden" name="rownum" value="">
	<input type="hidden" name="bufferAllowedYN" value="N">
	<input type="hidden" name="brokerYN" value="N">
	<html:hidden property="memberBuffer" /> 
	<!-- added for KOC-1273 -->
	<html:hidden property="criticalBenefit"/>
	<html:hidden property="healthCheckType"/><%--OPD_4_hosptial --%>
	<html:hidden property="survivalPeriod"/>
	<html:hidden property="stopPreAuth" />        
    <html:hidden property="stopClaim" />
	<html:hidden property="opdClaim" /><!-- KOC 1286 for OPD Benefit -->
	<html:hidden property="cashBenefit" /> <!--KOC 1270 for hospital cash benefit-->
    <html:hidden property="convCashBenefit" /> <!--KOC 1270 for hospital cash benefit-->
    <html:hidden property="policySeqID" />
    <html:hidden property="refundFalg" name="frmPoliciesEdit"/>
     <html:hidden property="previousRefundedYN" name="frmPoliciesEdit"/>
    <html:hidden property="refundedYNRemarks" name="frmPoliciesEdit"/>
     <html:hidden property="premiumDatesFlag" name="frmPoliciesEdit"/>
   <%--  <html:hidden property="UserAssociaedStatus" /> --%>
    <script language="javascript">
		stopPreAuthClaim();
		memberBuffer();
		stopHRApp();//<!-- //added for hyundaiBuffer on 19.05.2014 by Rekha  -->
		   
		//added for KOC-1273
		criticalBenefit();
		survivalPeriod();
		function tenure()
		{
			resizeDocument();
			TrackFormData('webBoard');
			handleFocus();
			window.history.forward(1);		
			document.forms[1].tenureDays.focus();
		}//end of tenure()
    	//tenure function is called to call the functions that have to be called while loading the page 
    	//and setting Tenure text field as default focus
    	window.onload =tenure;
		</script> 
</html:form>
<!-- E N D : Content/Form Area -->
