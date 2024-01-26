<%
/**
 * @ (#) policydetails.jsp Feb 2, 2006
 * Project      : TTK HealthCare Services
 * File         : policydetails.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : Feb 2, 2006
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
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.WebBoardHelper"%>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/enrollment/policydetails.js"></script>
<script type="text/javascript" src="/ttk/scripts/jquery/ttk-jquery.js"></script>
<script type="text/javascript" src="/ttk/scripts/async.js"></script>
<script type="text/javascript">
function getNetworkType(){
	var productSeqID=document.getElementById("productSeqID").value;
	$(document).ready(function(){
	  	 $.ajax({
	  	     url :"/asynchronAction.do?mode=getProductNetworkType&productSeqID="+productSeqID,
	  	     dataType:"text",
	  	     success : function(data) {	  	     
	  	 var networkType = document.getElementById("networkType");
	  	 while (networkType.firstChild) {
	  		networkType.removeChild(networkType.firstChild);
	  		}	  	
	  		 var div=document.createElement("div");	
	  		 var node = document.createTextNode(data);
	  		 div.appendChild(node);
	  		networkType.appendChild(div);	  		     
	  	     }
	  	 });
	 });	
}
</script>

<%
	boolean viewmode=true;
	boolean viewtype=false;
	boolean blnENDViewmode = false;
	boolean blnInsEndorsement=false;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	String strSubLink=TTKCommon.getActiveSubLink(request);
	pageContext.setAttribute("officeinfo",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("policyStatusForScheme",Cache.getCacheObject("policyStatusForScheme"));
	pageContext.setAttribute("insNetworkType",Cache.getCacheObject("insNetworkType"));
	pageContext.setAttribute("clarificationstatus", Cache.getCacheObject("clarificationstatus"));
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	String policyStatus = (String)request.getSession().getAttribute("policyStatusSeqID");
//	System.out.println("Active Link ::"+TTKCommon.getActiveLink(request));
//	System.out.println("Active SubLink ::"+TTKCommon.getActiveSubLink(request));
//	System.out.println("Active Tab ::"+TTKCommon.getActiveTab(request));
%>
<logic:match name="frmPolicyList" property="switchType" value="END">
<% blnENDViewmode =true; %>
</logic:match>

<html:form action="/PolicyDetailsAction.do" enctype="multipart/form-data">
<!-- S T A R T : Page Title -->

<logic:empty name="frmPolicyDetails" property="display">

<logic:match name="frmPolicyDetails" property="enrollmentDesc" value="Non-Corporate">
<% viewtype=true; %>
</logic:match>

	<logic:match name="frmPolicyList" property="switchType" value="ENM">
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmPolicyList" property="switchType" value="END">
	<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	  	<tr>
	    	 <td><bean:write name="frmPolicyDetails" property="caption"/></td>
			 <td align="right" class="webBoard">
			 		<logic:notEmpty name="frmPolicyDetails" property="policySeqID">
			 		<%@ include file="/ttk/common/toolbar.jsp" %>
			 		</logic:notEmpty >
			 </td>
		</tr>
	</table>
	<%--<logic:notMatch name="frmPolicyList" property="switchType" value="END">
		<%// strENDViewmode = false;%>
	</logic:notMatch>--%>
<!-- E N D : Page Title -->

	<!-- Added Rule Engine Enhancement code : 27/02/2008 -->
	<ttk:BusinessErrors name="BUSINESS_ERRORS" scope="request" />
	<!--  End of Addition -->

	<div class="contentArea" id="contentArea">
	<logic:match name="frmPolicyList" property="switchType" value="ENM">
		<table width="98%" align="center"  border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	   		 <td></td>
	   		  <td align="right">
			  	<strong><bean:write property="eventName" name="frmPolicyDetails"/></strong>&nbsp;&nbsp;<logic:match name="viewmode" value="false"><ttk:ReviewInformation/></logic:match>&nbsp;
			 </td>
	    </tr>
		</table>
	</logic:match>
	<logic:notEmpty name="fileError" scope="request" >
	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="fileError" scope="request"/>
	        </td>
	      </tr>
   	 </table>
</logic:notEmpty>
	<html:errors/>
	<!-- S T A R T : Success Box -->
	 <logic:notEmpty name="updated" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" title="Success" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="updated" scope="request"/>
	     </td>
	   </tr>
	  </table>
	 </logic:notEmpty>
	  <logic:notEmpty name="Delmsg" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" title="Success" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="Delmsg" scope="request"/>
	     </td>
	   </tr>
	  </table>
	 </logic:notEmpty>
	   <logic:notEmpty name="Successmsg" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" title="Success" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="Successmsg" scope="request"/>
	     </td>
	   </tr>
	  </table>
	 </logic:notEmpty>
 	<!-- E N D : Success Box -->

    <!-- S T A R T : Form Fields -->
	<fieldset>
    	<legend>Insurance Company</legend>
	    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="21%" class="formLabel">Insurance Name:</td>
	        <td width="33%" class="textLabelBold"><bean:write name="frmPolicyDetails" property="companyName"/></td>
	        <td width="23%" class="formLabel">Insurance ID: </td>
	        <td width="23%" class="textLabelBold">
	        	<bean:write name="frmPolicyDetails" property="officeCode"/>
	        </td>
	        
	      </tr>
	      <tr>
	      	<td class="formLabel" width="23%">Co-Insurance: <span class="mandatorySymbol">*</span></td>
	        <td width="23%">
		      <html:select property="coInsuranceV" styleClass="selectBox selectBoxMedium" onchange="onChangeCoIns()">
				<html:option value="">Select from list</html:option>
				<html:option value="Y">YES</html:option>
				<html:option value="N">NO</html:option>
			  </html:select>
	        </td>
	        <td width="23%" nowrap class="formLabel">Transaction Id: <span class="mandatorySymbol">*</span></td>
    		<td width="23%" class="textLabelBold">
    			<html:text size="50" property="transactionID" styleClass="textBox textBoxMedium"/>
    		</td>
	      </tr>
	      <tr>
	      	<td class="formLabel" width="23%">Insurance Network Type: <span class="mandatorySymbol">*</span></td>
	        <td width="23%">
			<html:select property="insNetworkType" styleClass="selectBox selectBoxMedium">
				<html:option value="">Select from list</html:option>
				<html:optionsCollection name="insNetworkType" label="cacheDesc" value="cacheId" /> 
			</html:select>
	        </td>
	        <logic:equal value="Y" property="coInsuranceV" name="frmPolicyDetails">
	        <td width="23%" nowrap class="formLabel">Co-insurance company details: </td>
    		<td width="23%" class="textLabelBold">
    			 <%-- <html:text size="50" property="compNameString" styleClass="textBox textBoxMedium" disabled="true"/>
    			 --%>
    			<a href="#" accesskey="g"onClick="javascript:onSearch()" class="search"> <img src="/ttk/images/EditIcon.gif" alt="Select Co-insurance company details"
					 width="16" height="16" border="0" align="absmiddle"></a>
    		</td>
    		</logic:equal>
	        </tr>
	        <tr>
	        <td width="23%" nowrap class="formLabel">Unified insurance plan Id: </td>
    		<td width="23%" class="textLabelBold">
    			<html:text size="50" property="unifiedInsuranceID" styleClass="textBox textBoxMedium"/>
    		</td>
	        </tr>
	    </table>
	</fieldset>

	<ttk:PolicyInfo/>
	<ttk:InsuranceRelatedInformation/>

	<fieldset>
    <legend>TPA Related Information</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="formLabel">Policy Status: <span class="mandatorySymbol">*</span></td>
        <td class="formLabelBold">


        <logic:match name="frmPolicyList" property="switchType" value="END">
        <% if(request.getSession().getAttribute("policyStatusSeqID").equals("POE")) {%>
			<bean:write name="frmPolicyDetails" property="policyStatusDesc"/>
			<%}else{ 
			if(TTKCommon.getActiveSubLink(request).equals("Corporate Policy") && (request.getSession().getAttribute("policyStatusSeqID").equals("PAR"))){ %>
			<html:select property="policyStatusID" styleClass="selectBox selectBoxMedium" styleId="changepolicyStatusId" onchange="changePolicyStatus();">
				<html:option value="POA">Active</html:option>
				<html:option value="PAR">Awaited for Renewal</html:option>
			</html:select>
			<%} else {%>
			<html:select property="policyStatusID" styleClass="selectBox selectBoxMedium" styleId="changepolicyStatusId" onchange="changePolicyStatus();" disabled="<%= viewmode %>">
				<html:optionsCollection name="policyStatusForScheme" label="cacheDesc" value="cacheId" />
			</html:select>
			<%}} %>
		</logic:match>

        <logic:match name="frmPolicyList" property="switchType" value="ENM">
        <% 
        if(TTKCommon.getActiveSubLink(request).equals("Corporate Policy") && (request.getSession().getAttribute("policyStatusSeqID").equals("PAR"))){%>
			<html:select property="policyStatusID" styleClass="selectBox selectBoxMedium" styleId="changepolicyStatusId">
				<html:option value="POA">Active</html:option>
				<html:option value="PAR">Awaited for Renewal</html:option>
			</html:select>
			<%}else if(request.getSession().getAttribute("freshOrRenewalPolicyStatusFlag").equals("Renewal") &&  "".equals(request.getSession().getAttribute("policyStatusSeqID"))){
				%>
			<html:select property="policyStatusID" styleClass="selectBox selectBoxMedium" styleId="changepolicyStatusId">
				<html:option value="">Select From List</html:option>
				<html:option value="POA">Active</html:option>
				<html:option value="PAR">Awaited for Renewal</html:option>
			</html:select>
			<%}else{ %>
			<bean:write name="frmPolicyDetails" property="policyStatusDesc"/>
			<%} %>
		</logic:match>
         &nbsp; <a href="#" onclick="policyModifyHistory(240,530);">Status Info</a>

        </td>
        <td class="formLabel">Term Status: </td>
        <td class="formLabelBold"><bean:write name="frmPolicyDetails" property="TPAStatusTypeID"/></td>
      </tr>
        <logic:match name="frmPolicyList" property="switchType" value="END">
      <tr>
        <td class="formLabel">Remarks: <span class="mandatorySymbol">*</span></td>
        <td class="formLabelBold" colspan="3">
        <html:textarea property="endRemarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
       </td>
      </tr>
     </logic:match>
      
      <tr>
        <td width="21%" class="formLabel">Policy Received Date: <span class="mandatorySymbol">*</span></td>
        <td width="33%">
        <html:text property="recdDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
       <logic:match name="viewmode" value="false">
		        <a name="CalendarObjectClDate" id="CalendarObjectClDate" href="#" onClick="javascript:show_calendar('CalendarObjectClDate','frmPolicyDetails.recdDate',document.frmPolicyDetails.recdDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" title="Calendar" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a>
       </logic:match>
        </td>
        <td class="formLabel" width="23%">Branch Location: <span class="mandatorySymbol">*</span></td>
        <td width="23%">
	      <html:select property="officeSeqID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode | blnENDViewmode %>">
			<html:option value="">Select from list</html:option>
			<html:optionsCollection name="officeinfo" label="cacheDesc" value="cacheId" />
		  </html:select>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Policy Condoned: </td>
        <td><span class="textLabel">
			<html:checkbox property="condonedYN" value="Y" disabled="<%=viewmode%>"/>
	        </span>
	    </td>
	   
	    
	   <!--  <td class="formLabel">&nbsp;</td>
        <td>&nbsp;</td> -->
      </tr>
    </table>
	</fieldset>


	<fieldset>
    <legend>Sum Insured</legend> 
    <% if(TTKCommon.getActiveSubLink(request).equals("Corporate Policy")){ %>
  <%-- <table class="empmem">
  <tr class="empmemrowodd">
    <th style="text-align: center;">Employee/Member Status</th>
    <th style="text-align: center;">Count</th>
    <th style="text-align: center;">Total Sum Insured(OMR)</th>
    <th style="text-align: center;">Total Net Premium(OMR)</th>
  </tr>
  <tr class="empmemroweven">
    <td class="empmemroweventd" style="padding-left: 69px;">Employee (Inception)</td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeInceptionCount"/></td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeInceptionTotalSumAssured"/></td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeInceptionTotalNetPremium"/></td>
  </tr>
  <tr class="empmemrowodd">
    <td class="empmemrowoddtd" style="padding-left: 69px;">Dependants (Inception)</td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentInceptionCount"/></td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentInceptionTotalSumAssured"/></td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentInceptionTotalNetPremium"/></td>
  </tr>
  <tr class="empmemroweven">
    <td class="empmemroweventd" style="padding-left: 69px;">Employee (Addition)</td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeAdditionCount"/></td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeAdditionTotalSumAssured"/></td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeAdditionTotalNetPremium"/></td>
  </tr>
  <tr class="empmemrowodd">
    <td class="empmemrowoddtd" style="padding-left: 69px;">Dependants (Addition)</td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentAdditionCount"/></td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentAdditionTotalSumAssured"/></td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentAdditionTotalNetPremium"/></td>
  </tr>
  <tr class="empmemroweven">
    <td class="empmemroweventd" style="padding-left: 69px;">Employee (Cancelled)</td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeCancelledCount"/></td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeCancelledTotalSumAssured"/></td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeCancelledTotalNetPremium"/></td>
  </tr>
  <tr class="empmemrowodd">
    <td class="empmemrowoddtd" style="padding-left: 69px;">Dependants (Cancelled)</td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentCancelledCount"/></td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentCancelledTotalSumAssured"/></td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentCancelledTotalNetPremium"/></td>
  </tr>
  <tr class="empmemroweven">
    <td class="empmemroweventd" style="padding-left: 69px;">Employee (Suspended)</td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeSuspendedCount"/></td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeSuspendedTotalSumAssured"/></td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeSuspendedTotalNetPremium"/></td>
  </tr>
  <tr class="empmemrowodd">
    <td class="empmemrowoddtd" style="padding-left: 69px;">Dependants (Suspended)</td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentSuspendedCount"/></td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentSuspendedTotalSumAssured"/></td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentSuspendedTotalNetPremium"/></td>
  </tr>
  <tr class="empmemroweven">
    <td class="empmemroweventd" style="padding-left: 69px;">Employee (Active)</td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeActiveCount"/></td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeActiveTotalSumAssured"/></td>
    <td class="empmemroweventd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="employeeActiveTotalNetPremium"/></td>
  </tr>
  <tr class="empmemrowodd">
    <td class="empmemrowoddtd" style="padding-left: 69px;">Dependants (Active)</td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentActiveCount"/></td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentActiveTotalSumAssured"/></td>
    <td class="empmemrowoddtd" style="text-align: center;"><bean:write name="frmPolicyDetails" property="dependentActiveTotalNetPremium"/></td>
   </tr>
   </table> --%>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">  
   		<tr>
        <td width="21%" nowrap class="formLabel">Total Group Sum Insured : </td>
        <td width="33%" class="formLabel">
		 	<html:text property="totGroupSumInsuredCorp" styleClass="textBox textBoxStandard" readonly="true" style="background-color:#EEEEEE"/>
		 	<html:text property="currencyFormat"  styleClass="textBox textBoxTiny"  value="OMR"    styleId="currencyFormat"  readonly="true"/>
		 	 <a href="#" onclick="openRadioList('currencyFormat','CURRENCY_GROUP','option')">
		  <img src="/ttk/images/EditIcon.gif" width="16" height="16" alt="Select Currancy" border="0" ></a>
		 	</td>
		 <td class="formLabel" width="23%"></td>   
         <td class="formLabel" width="19%"></td>
		</tr>
		<tr>
        <td width="20%" class="formLabel">Sum Insured per Member :<span class="mandatorySymbol">*</span> </td>
         <td width="21%" class="formLabel">
		 	<html:text property="sumInsuredPerMemberCorp" styleClass="textBox textBoxStandard" maxlength="13" onkeyup="isNumeric(this)"/>
		 	<html:text property="currencyFormat1"  styleClass="textBox textBoxTiny"  value="OMR"    styleId="currencyFormat1"  readonly="true"/>
		 	 <a href="#" onclick="openRadioList('currencyFormat1','CURRENCY_GROUP','option')">
		  <img src="/ttk/images/EditIcon.gif" width="16" height="16" alt="Select Currancy" border="0" ></a>
		 	</td>
		</tr>
		<tr>
        <td width="20%" class="formLabel">Gross Premium : </td>
         <td width="21%" class="formLabel">
		 	<html:text property="grossPremiumCorp" styleClass="textBox textBoxStandard" readonly="true"	style="background-color:#EEEEEE"/>
		 </td>
		 <td class="formLabel" width="14%">Premium Tax : </td>
         <td class="formLabel" width="33%">
		 	<html:text property="premiumTaxCorp" styleClass="textBox textBoxStandard" maxlength="13" onkeyup="isNumeric(this)"/>
		 </td>
      </tr>
      <tr>
        <td width="20%" class="formLabel">Net Premium : </td>
         <td width="21%" class="formLabel">
		 	<html:text property="netPremiumCorp" styleClass="textBox textBoxStandard" maxlength="13" onkeyup="isNumeric(this)"/>
		 </td>
		 <td class="formLabel" width="14%">VAT Amount : </td>
         <td class="formLabel" width="33%">
		 	<html:text property="vatAmountCorp" styleClass="textBox textBoxStandard" maxlength="13" onkeyup="isNumeric(this)"/>
		 </td>
      </tr>
   </table>
   <%}else{ %>
   <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">  
      	<tr>
	        <td width="21%" nowrap class="formLabel">Total Group Sum Insured : </td>
	        <td width="33%" class="formLabel">
			 	<html:text property="totGroupSumInsuredCorp" styleClass="textBox textBoxStandard" readonly="true" style="background-color:#EEEEEE"/>
			 	<html:text property="currencyFormat"  styleClass="textBox textBoxTiny"  value="OMR"    styleId="currencyFormat"  readonly="true"/>
		 	 	<a href="#" onclick="openRadioList('currencyFormat','CURRENCY_GROUP','option')">
		  		<img src="/ttk/images/EditIcon.gif" width="16" height="16" alt="Select Currancy" border="0" ></a>
			 	</td>
			 <td class="formLabel" width="23%"></td>
	         <td class="formLabel" width="19%"></td>
		</tr>
      <tr>
       <td width="14%" nowrap class="formLabel">Total Sum Insured : </td>
       <td width="33%" class="formLabel">
        	<html:text property="totalSumInsured" styleClass="textBox textBoxStandard" maxlength="17" style="background-color:#EEEEEE" readonly="true" />
        	<html:text property="currencyFormat1"  styleClass="textBox textBoxTiny"  value="OMR"    styleId="currencyFormat1"  readonly="true"/>
		 	 <a href="#" onclick="openRadioList('currencyFormat1','CURRENCY_GROUP','option')">
		  	<img src="/ttk/images/EditIcon.gif" width="16" height="16" alt="Select Currancy" border="0" ></a>
       	</td>       	
		<%-- <td width="61%" class="textLabelBold"><bean:write name="frmPolicyDetails" property="sumWording"/></td> --%>
      		<td class="formLabel" width="23%">Net Premium :<span class="mandatorySymbol">*</span> </td>
        	<td class="formLabel" width="19%">
		 	<html:text property="totalPremium" styleClass="textBox textBoxStandard" maxlength="13" disabled="<%= viewtype | viewmode | blnENDViewmode %>" readonly="<%= viewmode | blnENDViewmode %>" onkeyup="isNumeric(this)"/>
		 	</td>
      </tr>
      <%-- <tr>
        <td class="formLabel">Gross Premium : <span class="mandatorySymbol">*</span></td>
        <td class="textLabelBold">
		 	<html:text property="grossPremium" styleClass="textBox textBoxStandard" maxlength="13" onkeyup="isNumeric(this)"/>
		 </td>	
      </tr> --%>
     <tr>
		 <td width="21%" nowrap class="formLabel">Gross Premium Amount:</td>
         <td width="21%" class="formLabel">
		 	<html:text property="totPremium" style="background-color:#EEEEEE" styleClass="textBox textBoxStandard" maxlength="13" onkeyup="isNumeric(this)" readonly="true"/>
			<html:text property="currencyFormat"  styleClass="textBox textBoxTiny"  value="OMR"  readonly="true"/>
		 </td>	
		 <td class="formLabel" width="14%">Premium VAT Amount : </td>
         <td class="formLabel" width="33%">
		 	<html:text property="premiumVatAmount" styleClass="textBox textBoxStandard" maxlength="13" onkeyup="isNumeric(this)"/>
		 </td>
      </tr>
      <tr>
        	<td width="20%" class="formLabel">Premium Tax Amount: </td>
         	<td width="21%" class="formLabel">
		 	<html:text property="premiumTax" styleClass="textBox textBoxStandard" maxlength="13" onkeyup="isNumeric(this)"/>
		 	<html:text property="currencyFormat"  styleClass="textBox textBoxTiny"  value="OMR"  readonly="true"/>
		 </td>
      </tr>
    </table>
   <%} %>
	</fieldset>
	<!-- AK -->
		  	
	<%if(strSubLink.equals("Corporate Policy")){
		%>
	<fieldset>
    <legend>Broker Details 
		  <a href="#" accesskey="a"
							onClick="javascript:openBrokerDetails()"><img
							src="/ttk/images/AddIcon.gif" title="Add Activity Details" ALT="Add Activity Details"
							width="13" height="13" border="0" align="absmiddle"></a>
		  </legend>
	       <ttk:HtmlGrid name="tableDataBro"/>
		 	</fieldset>
		 <%} %>
		 
		 <%-- <%
	        if(TTKCommon.isAuthorized(request,"Edit"))
            {
        %>
	        
	        <a href="#" onclick="javascript:onDeleteIcon();">
	    <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	    %> --%>
		 
		 
		 
		 
		 <!-- EAK -->
	<%-- added for koc 1278 PED --%>
	<fieldset>
    	<legend>Other Insurance Administrator Related Information</legend>
    		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    			<tr>
    				<td width="20%" nowrap class="formLabel">Policy Beneficiary Name: </td>
    				<td width="21%" class="textLabelBold">
    					<html:text size="50" property="otherTPAInsuredname" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" maxlength="30" disabled="<%=viewmode%>"/>
    				</td>
    				<td class="formLabel">Policy No.: </td>
    				<td class="formLabel">
    					<html:text property="otherTPAPolicyNr" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" maxlength="30" disabled="<%=viewmode%>"/>
    				</td>
    			</tr>
    			<tr>
 					<td width="20%" class="formLabel">From Date:</td>
 					<td width="21%" class="textLabelBold">
		      			<html:text size="50" property="TPAStartDate" styleClass="textBox textDate" maxlength="10"/>
		      			<a name="CalendarObjectstartDate" id="CalendarObjectstartDate" href="#" onClick="javascript:show_calendar('CalendarObjectstartDate','frmPolicyDetails.TPAStartDate',document.frmPolicyDetails.TPAStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
		      				<img src="ttk/images/CalendarIcon.gif" title="Calendar" alt="Calendar" name="startDate" width="24" height="17" border="0" align="absmiddle" ></a>
		      		</td>		
 					<td class="formLabel" width="23%">To Date:</td>
 					<td class="formLabel" width="23%">
		      			<html:text property="TPAEndDate" styleClass="textBox textDate" maxlength="10"/>
		      			<a name="CalendarObjectendDate" id="CalendarObjectendDate" href="#" onClick="javascript:show_calendar('CalendarObjectendDate','frmPolicyDetails.TPAEndDate',document.frmPolicyDetails.TPAEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
		      				<img src="ttk/images/CalendarIcon.gif" title="Calendar" alt="Calendar" name="endDate" width="24" height="17" border="0" align="absmiddle" ></a>
		      		</td>
    			</tr>
    			<tr>
 			        <td width="21%" class="formLabel">Portability Approved: </td>
        			<td class="formLabel" width="33%">
        			    <html:select property="otherTPAPortApproved" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode | blnENDViewmode %>">
						<html:option value="">Select from list</html:option>
						<html:option value="Y">Yes</html:option>
						<html:option value="N">No</html:option>
						</html:select>
					</td>
    				<td class="formLabel" width="23%">Delay Condoned: </td>
    				<td class="textLabelBold">
        			    <html:select property="otherTPADelayCondoned" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode | blnENDViewmode %>">
						<html:option value="">Select from list</html:option>
						<html:option value="Y">Yes</html:option>
						<html:option value="N">No</html:option>
						</html:select>
    				</td>
    			</tr>
			 	<tr>
    				<td width="20%" class="formLabel">SI Value: </td>
    				<td class="formLabel">
    					<html:text property="otherTPASIValue" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" maxlength="10" disabled="<%=viewmode%>"/>
    				</td>
			        <td>&nbsp;</td>
			        <td>&nbsp;</td>
     			</tr>
		    </table>
	</fieldset>
	<%-- added for koc 1278 PED --%>

	<ttk:InsuredAddressInformation/>
	<ttk:BankAccountInformation/>
	<ttk:BeneficiaryInformation/>

	<fieldset>
		<legend>Clarification with Insurance Company</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="21%" class="formLabel">Clarification Status:</td>
		    <td class="formLabel" width="33%">
		     <html:select property="clarificationTypeID" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
  	 		  <html:option value="">Select from list</html:option>
		      <html:optionsCollection name="clarificationstatus" label="cacheDesc" value="cacheId" />
			</html:select>
		    </td>
		    <td class="formLabel" width="23%">Clarified Date:</td>
		    <td class="formLabel" width="23%">
			    <html:text property="clarifiedDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
		     <logic:match name="viewmode" value="false">
		        <a name="CalendarObjectClDate" id="CalendarObjectClDate" href="#" onClick="javascript:show_calendar('CalendarObjectClDate','frmPolicyDetails.clarifiedDate',document.frmPolicyDetails.clarifiedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" title="Calendar" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a>
		    </logic:match>
		    </td>
		  </tr>
		  <tr>
		    <td class="formLabel">Remarks:</td>
		    <td class="formLabel" colspan="3"><html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/></td>
		  </tr>
		</table>
	</fieldset>
	<!-- Test Nag -->	
	<fieldset>
		<legend>Policy TOB Documents</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td class="textLabelBold" colspan="2">
   				<a href="#" onClick="javascript:onUploadDocs()">Documents Upload/View</a>
   			</td>
	    </tr>
	    </table>
	</fieldset>

	<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="100%" align="center">
		      <logic:match name="viewmode" value="false">
		      	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUserSubmit()"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
		    </logic:match>
		    </td>
		</tr>
		
		</table>
	<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="brokerCount" VALUE='<%=request.getSession().getAttribute("brokerCount")%>'/>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="">
<INPUT TYPE="hidden" NAME="condonedYN" VALUE="">
<html:hidden property="policyTypeID"/>
<html:hidden property="DMSRefID"/>
<%-- <html:hidden property="compareYN"/> --%>
<html:hidden property="tenure"/>
 <logic:match name="frmPolicyList" property="switchType" value="ENM">
			<html:hidden property="policyStatusID"/>
		</logic:match>


<html:hidden property="endPolicyStatus"/>

<INPUT TYPE="hidden" NAME="switType" value='<bean:write name="frmPolicyList" property="switchType"/>'/>
<INPUT TYPE="hidden" NAME="proposalFormYN" VALUE="">
<html:hidden property="policySeqID"/>
<html:hidden property="eventSeqID"/>
<html:hidden property="endorsmentFlag"/>

<html:hidden property="premiumDatesFlag"/>

<input type="hidden" name="rownum" value=""> 
<html:hidden property="policyStatusIDForEnrollemtEndrosment" styleId="policyStatusIDFlag" value="<%= policyStatus %>"/>
<script type="text/javascript">
	getReferenceNo(document.forms[1].DMSRefID.value);
</script>
</logic:empty>

<logic:notEmpty name="frmPolicyDetails" property="display">
	<html:errors/>

	<script> TC_Disabled = true;</script>
</logic:notEmpty>

<logic:notEmpty name="frmPolicyDetails" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>

</html:form>