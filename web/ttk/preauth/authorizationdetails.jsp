<%
/** @ (#) authorizationdetails.jsp May 18, 2006
 * Project     : TTK Healthcare Services
 * File        : authorizationdetails.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: May 18, 2006
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper,org.apache.struts.action.DynaActionForm"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/preauth/authorizationdetails.js">

</script>

<%
  boolean aprviewmode=true;//1274A
  boolean viewmode=true;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }//end of if(TTKCommon.isAuthorized(request,"Edit"))
  String strLink=TTKCommon.getActiveLink(request);
  String ampm[] = {"AM","PM"};
  pageContext.setAttribute("preauthStatus",Cache.getCacheObject("preauthStatus"));
   pageContext.setAttribute("claimStatus",Cache.getCacheObject("claimStatus"));
  pageContext.setAttribute("approveReason",Cache.getCacheObject("approveReason"));
  pageContext.setAttribute("claimStatusAprRejFlow",Cache.getCacheObject("claimStatusAprRejFlow")); //1274 A insurance
  pageContext.setAttribute("preauthStatusAprRejFlow",Cache.getCacheObject("preauthStatusAprRejFlow")); //1274 A insurance

  //added for Policy Deductable - KOC -1277
  pageContext.setAttribute("approveReasonClosed",Cache.getCacheObject("approveReasonClosed"));
  
  pageContext.setAttribute("ampm",ampm); 
  String claimsubgenraltypeid = (String)request.getSession().getAttribute("claimsubgenraltypeid");
  
  //<!--Added for Policy Deductable - KOC-1277  -->
  DynaActionForm frmAuthorizationDetails = (DynaActionForm)request.getSession().getAttribute("frmAuthorizationDetails"); 
  String Policy_deductable_yn = (String)frmAuthorizationDetails.getString("policy_deductable_yn");
  

%>  
<!-- S T A R T : Content/Form Area -->
<html:form action="/AuthorizationDetailsAction.do">
  <!-- S T A R T : Page Title -->
  <%
  if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
  {
  %>
    <table align="center" class="<%=TTKCommon.checkNull(PreAuthWebBoardHelper.getShowBandYN(request)).equals("Y") ? "pageTitleHilite" :"pageTitle" %>" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><bean:write name="frmAuthorizationDetails" property="caption"/></td>
        <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
      </tr>
    </table>
  <%
  }//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
  if(TTKCommon.getActiveLink(request).equals("Claims"))
  {
  %>
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><bean:write name="frmAuthorizationDetails" property="caption"/></td>
        <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
      </tr>
    </table>
  <%
  }//end of if(TTKCommon.getActiveLink(request).equals("Claims"))
    %>
  <!-- E N D : Page Title -->
  <div class="contentArea" id="contentArea">
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
  <!--denial process--> 
<logic:notEmpty name="denialBanyn" scope="request">
<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	<tr>
	<td><img src="/ttk/images/ErrorIcon.gif" alt="Error" width="16" height="16" align="absmiddle">
	 <bean:message name="denialBanyn" scope="request"/>
	</td>
	</tr>
</table>
</logic:notEmpty>
	<!--denial process-->
   <logic:notEmpty name="dischargeStatus" scope="request">
  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
		 <bean:message name="dischargeStatus" scope="request"/>
	</td>
   </tr>
  </table>
 </logic:notEmpty> 
  <!-- E N D : Success Box -->


  <!-- S T A R T : Form Fields -->
  <fieldset>
    <legend>General</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="22%" height="25" class="formLabel">Total Sum Insured (Rs):</td>
          <td width="30%" class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="totalSumInsured"/></strong></td>
          <td height="25" width="22%">Bal. Sum Insured (Rs):</td>
          <td height="20" class="textLabel" width="26%">          	
          	<strong><bean:write name="frmAuthorizationDetails" property="availSumInsured"/></strong>
          	<%
			if(TTKCommon.isAuthorized(request,"BalanceSI"))
			{				
		  	%>
		  	<logic:notEqual name="frmAuthorizationDetails" property="balanceSeqID" value="">
          	&nbsp;<a href="#" onClick="javascript:onBalanceSI();"><img src="/ttk/images/RedStar.gif" alt="Member Info" width="16" height="16" border="0" align="absmiddle" /></a>
          	</logic:notEqual>
          	<%
			}
          	%>
          </td>
      </tr>
      <tr>
      	<td height="25" class="formLabel">Avail. Cumu. Bonus (Rs):</td>
        <td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="availCumBonus"/></strong></td>
        <td height="25" class="formLabel">Buffer Amt. (Rs): </td>
        <td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="approvedBufferAmount"/></strong></td>
      </tr>
      <%
    	if(strLink.equals("Pre-Authorization"))
    	{
      %>
	   <%--START MODIFICATION ACCORDING TO KOC 1140(SUM InSURED RESTRICTION)  --%>
      <logic:equal name="frmAuthorizationDetails" property="famRestrictedYN" value="Y">
       <tr>
	      	<td class="formLabel">Rest.Family Sum Insured. (Rs): </td>
	        <td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="memberRestrictedSIAmt"/></strong></td>
	        <td height="25" class="formLabel">Family Bal.Sum Insured. (Rs):</td>
      		<td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="avaRestrictedSIAmt"/></strong></td>
	      </tr>
	     <html:hidden name="frmAuthorizationDetails" property="famRestrictedYN"/>
        <html:hidden name="frmAuthorizationDetails" property="memberRestrictedSIAmt"/>
        <html:hidden name="frmAuthorizationDetails" property="avaRestrictedSIAmt"/>
      </logic:equal>
       <%--END MODIFICATION ACCORDING TO KOC 1140(SUM InSURED RESTRICTION)--%>
	      <tr>
	      	<td class="formLabel">Approval Limit. (Rs): </td>
	        <td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="totalAmount"/></strong></td>
	        <td height="25" class="formLabel">Requested Amt. (Rs):</td>
	        <td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="requestedAmount"/></strong></td>
	      </tr>
	      <tr>
	      	<td height="25" class="formLabel">Prev. Approved Amt. (Rs):</td>
	        <td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="prevApprovedAmount"/></strong></td>
	        <td class="formLabel">Approved Amt. (Tariff) (Rs): </td>
	        <td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="maxAllowedAmt"/></strong></td>
	      </tr> 
	  <%
    	}// end of if(strLink.equals("Pre-Authorization"))
    	else
    	{
      %>
	  <%--START MODIFICATION ACCORDING TO KOC 1140(SUM InSURED RESTRICTION)  --%>
      <logic:equal name="frmAuthorizationDetails" property="famRestrictedYN" value="Y">
       <tr>
	      	<td class="formLabel">Rest.Family Sum Insured. (Rs): </td>
	        <td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="memberRestrictedSIAmt"/></strong></td>
	        <td height="25" class="formLabel">Family Bal.Sum Insured. (Rs):</td>
      		<td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="avaRestrictedSIAmt"/></strong></td>
	      </tr>
    
	       <%--Start Modification as per KOC 1140(Sum Insured Restriction) --%>
    <html:hidden name="frmAuthorizationDetails" property="famRestrictedYN"/>
        <html:hidden name="frmAuthorizationDetails" property="memberRestrictedSIAmt"/>
        <html:hidden name="frmAuthorizationDetails" property="avaRestrictedSIAmt"/>
     </logic:equal>
       <%--END MODIFICATION ACCORDING TO KOC 1140(SUM InSURED RESTRICTION)  --%>
       	  <tr>
	      	<td class="formLabel">Approval Limit. (Rs): </td>
	        <td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="totalAmount"/></strong></td>
	        <td height="25" class="formLabel">Avail.Dom.Trt.Limit :</td>
      		<td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="availDomTrtLimit"/></strong></td>
	      </tr>
	      <tr>
	      	<td height="25" class="formLabel">Requested Amt. (Rs):</td>
	        <td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="requestedAmount"/></strong></td>
	        <td class="formLabel">Approved Amt. (Bills) (Rs): </td>
	        <td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="maxAllowedAmt"/></strong></td>
	      </tr> 
	  <%
        }//end of else
      %>    	    
    </table>
  </fieldset>
  <%
  	if(strLink.equals("Claims"))
    {
  %>
  		<fieldset>
  			<legend>Cashless Details</legend>
  			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
  				<tr>
  					<td width="22%" height="25" class="formLabel">Approved Amt. (Tariff) (Rs):</td>
  					<td class="textLabel" width="30%"><strong><bean:write name="frmAuthorizationDetails" property="PATariffAppAmt"/></strong></td>
  					<td height="25" width="22%" class="formLabel">Cashless Co-Pay Amt. (Rs):</td>
  					<td height="20" class="textLabel" width="26%"><strong><bean:write name="frmAuthorizationDetails" property="PACopayAmt"/></strong></td>
  				</tr>
  				<tr>
  					<td height="25" class="formLabel">Final Approved Amt. (Rs):</td>
  					<td class="textLabel"><strong><bean:write name="frmAuthorizationDetails" property="PAApprovedAmt"/></strong></td>
  					
  						<%--Changes as per KOC 1216B --%>
  					<td height="25" width="22%" class="formLabel">Cashless Buffer Co-pay Amt. (Rs):</td>
  					<td height="20" class="textLabel" width="26%"><strong><bean:write name="frmAuthorizationDetails" property="preCopayBufferamount"/></strong></td>
  					<%--Changes as per KOC 1216B --%>
  				</tr>
  			</table>
  		</fieldset>
  <%
    }// end of if(strLink.equals("Claims"))
  %>
  <fieldset>
  	<%
    if(strLink.equals("Claims"))
    {
    %>
  		<legend>Claims Deductions</legend>
  	<%
    }// end of if(strLink.equals("Claims"))
    else
    {
    %>
    	<legend>Cashless Deductions</legend>	
    <%
    }// end of else
    %>	
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
       	<tr>
    		<td width="22%" nowrap="nowrap" height="25" class="formLabel">Discount Amt. (Rs):</td>
    		<%
    		if(strLink.equals("Claims"))
    		{
    		%>
	          <logic:match name="frmAuthorizationDetails" property="statusTypeID" value="INP">
	          	<td class="textLabelBold"  width="30%">	
	          		<html:text property="discountAmount" styleClass="textBox textBoxMedium" onkeyup="javascript:onPress(event);" maxlength="13"   disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
	          	</td>
	          </logic:match>
	          <logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="INP">
	          	<td class="textLabelBold"  width="30%">
	          		<html:text property="discountAmount" styleClass="textBox textBoxMedium textBoxDisabled" onkeyup="javascript:onPress(event);" maxlength="13" disabled="true"/>
	          	</td>
	          </logic:notMatch>
	        <%
      		}// end of if(strLink.equals("Claims"))
      		else
      		{
      		%>  
	    		<td class="textLabelBold" width="30%">
	          		<html:text property="discountAmount" styleClass="textBox textBoxMedium textBoxDisabled" disabled="true"/>
	       		</td>
	       	<%
      		}// end of else
    		%>	
          	<td height="25" width="22%" class="formLabel">Co-Pay Amt. (Rs):</td>
          	<td class="textLabelBold" width="26%">
	        <logic:match name="frmAuthorizationDetails" property="statusTypeID" value="INP">
	          	<html:text property="copayAmount" styleId="copayAmount" styleClass="textBox textBoxMedium" maxlength="15"  onkeyup="javascript:onPress(event);" onchange="javascript:calculatetotCopay()"  disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
	        </logic:match>
          	<logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="INP">
          		<html:text property="copayAmount" styleId="copayAmount" styleClass="textBox textBoxMedium textBoxDisabled"  onkeyup="javascript:onPress(event);" onchange="javascript:calculatetotCopay()" maxlength="15" disabled="true"/>
          	</logic:notMatch>
          	</td>
          	
    	</tr>      
          	    		<%--Changes as per KOC 1216B modified coapyAmount also--%>
    	<tr>
    	  	<td height="25" width="22%" class="formLabel">Co-Pay Buffer :</td>
          	<td class="textLabelBold" width="30%">
	        <logic:match name="frmAuthorizationDetails" property="statusTypeID" value="INP">
	          	<html:text property="copayBufferamount" styleId="copayBufferamount" styleClass="textBox textBoxMedium" maxlength="15"  onkeyup="javascript:onPress(event);"  onchange="javascript:calculatetotCopay()"  disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
	        </logic:match>
          	<logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="INP">
          		<html:text property="copayBufferamount" styleId="copayBufferamount" styleClass="textBox textBoxMedium textBoxDisabled"  onkeyup="javascript:onPress(event);"  onchange="javascript:calculatetotCopay()" maxlength="15" disabled="true"/>
          	</logic:notMatch>
          	</td>
          	
          		<td height="25" width="22%" class="formLabel">Total CoPay :</td>
          	<td class="textLabelBold" width="26%">
	        <logic:match name="frmAuthorizationDetails" property="statusTypeID" value="INP">
	 	<html:text property="totcopayAmount" styleId="totcopayAmount" styleClass="textBox textBoxMedium textBoxDisabled" maxlength="15"  onkeyup="javascript:onPress(event);"  disabled="true"/>
        	</logic:match>
          	<logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="INP">
          		<html:text property="totcopayAmount" styleId="totcopayAmount" styleClass="textBox textBoxMedium textBoxDisabled"  onkeyup="javascript:onPress(event);" maxlength="15" disabled="true"/>
          	</logic:notMatch>
          	</td>
          	
    	</tr> 
    	<%
    		if(strLink.equals("Claims"))
    		{
    	%>
    	<tr>
    	  	<td height="25" width="22%" class="formLabel">Disallowed Investigation Amount:</td>
          	<td class="textLabelBold" width="30%">
	        <logic:match name="frmAuthorizationDetails" property="statusTypeID" value="INP">
	          	<html:text property="invDisallowdedAmt" styleId="copayBufferamount" styleClass="textBox textBoxMedium" maxlength="15"  onkeyup="javascript:onPress(event);"  onchange=""  disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
	        </logic:match>        	        
          	<logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="INP">
          		<html:text property="invDisallowdedAmt" styleId="copayBufferamount" styleClass="textBox textBoxMedium textBoxDisabled"  onkeyup="javascript:onPress(event);"  onchange="" maxlength="15" disabled="true"/>
          	</logic:notMatch>
          	</td>
         </tr> 
        <%
      		}// end of if(strLink.equals("Claims"))      		
        %> 
		<!-- Policy Deductable KOC-1277-->
    	<tr>
    		<td height="25" width="22%" class="formLabel">Scheme Deductable Amount :</td>
          	<td class="textLabelBold" width="30%">
	     	<logic:match name="frmAuthorizationDetails" property="statusTypeID" value="INP">
	     		<html:text property="balanceDedAmount" styleId="balanceDedAmount" styleClass="textBox textBoxMedium textBoxDisabled" maxlength="15"  onkeyup="javascript:onPress(event);"  disabled="true"/>	     	
	     	</logic:match>
	     	
	     	<logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="INP">
          		<html:text property="balanceDedAmount" styleId="balanceDedAmount" styleClass="textBox textBoxMedium textBoxDisabled"  maxlength="15" disabled="true"/>
          	</logic:notMatch>
        	</td>
    	
      </tr>
    <%--Changes as per KOC 1216B --%>
    </table>
  </fieldset>
  <%
    if(strLink.equals("Claims"))
    {
    %>
  <fieldset>
  	
  		<legend>Service Tax Info</legend>
  	
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
       	<tr>
    		<td width="22%" nowrap="nowrap" height="25" class="formLabel">Requested Tax Amt.(Rs):</td>
	          	<td class="textLabelBold">	
	          		<html:text property="requestedTaxAmt" styleClass="textBox textBoxMedium textBoxDisabled" maxlength="13" readonly="true" />
	          	</td>
          	<td height="25" width="22%" class="formLabel" >Tax Amt. Paid(Rs):</td>
          	<td class="textLabelBold" width="26%"  >
          	<logic:match name="frmAuthorizationDetails" property="calcButDispYN"  value="Y" >
          	<logic:match name="frmAuthorizationDetails" property="statusTypeID" value="INP">
	          	 <html:text property="taxAmtPaid" name="frmAuthorizationDetails" styleClass="textBox textBoxMedium" maxlength="13" onblur="javascript:onFinalApprAmtCalculation();" ></html:text>&nbsp;
	        </logic:match>
	        <logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="INP">
	          	 <html:text property="taxAmtPaid" name="frmAuthorizationDetails" styleClass="textBox textBoxMedium textBoxDisabled" maxlength="13" disabled="true" onblur="javascript:onFinalApprAmtCalculation();"></html:text>&nbsp;
	          </logic:notMatch>	 
	          </logic:match>
	          <logic:notMatch name="frmAuthorizationDetails" property="calcButDispYN"  value="Y">
	           <html:text property="taxAmtPaid" name="frmAuthorizationDetails" styleClass="textBox textBoxMedium textBoxDisabled" maxlength="13" disabled="true" ></html:text>
	          </logic:notMatch>
	        <logic:match name="frmAuthorizationDetails" property="calcButDispYN"  value="Y" >
	          	 <a href="#" onClick="javascript:onServTaxCal();"><img src="/ttk/images/Reassociate.gif" alt="Calculate" border="0" align="absmiddle" ></a>  
	         </logic:match>    
          	</td>
          	
    	</tr>  
    
    </table>
    
  </fieldset>
  <%
    }// end of if(strLink.equals("Claims"))
    
    %>
    <%
    if(strLink.equals("Claims"))
    {
    %>
  <fieldset>
  	
  		<legend>Pre-Final Adjustment</legend>
  	
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
       	<tr>
    		<td width="22%" nowrap="nowrap" height="25" class="formLabel">Final Approved Amt. (Rs):</td>
		  			<td class="textLabel">
		  				<html:text property="finalApprovedAmt" styleClass="textBox textBoxDisabled" maxlength="30" readonly="true"/>
		  			</td>
		  			<td height="25" width="22%" class="formLabel">Deposit Amt. (Rs):</td>
		  			<td class="textLabelBold" width="26%">
			  			<logic:match name="frmAuthorizationDetails" property="statusTypeID" value="INP">
				          	<html:text property="depositAmt" styleClass="textBox textBoxMedium" maxlength="15" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
				        </logic:match>
			          	<logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="INP">
			          		<html:text property="depositAmt" styleClass="textBox textBoxMedium textBoxDisabled" maxlength="15" disabled="true"/>
			          	</logic:notMatch>
  			</td>
    	</tr>
    
    </table>
    
  </fieldset>
  <%
    }// end of if(strLink.equals("Claims"))
    
    %>
  <fieldset>
    <%
        if(strLink.equals("Claims"))
      {
    %>
   	<legend>Settlement Details</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="22%" height="25" class="formLabel" nowrap="nowrap">Claim Settlement No:</td>
            <td width="30%" class="textLabelBold" nowrap="nowrap"><bean:write name="frmAuthorizationDetails" property="authorizationNo"/></td>
            <logic:match name="frmAuthorizationDetails" property="statusTypeID" value="APR">
            <td height="25" class="formLabel" id="approvedamt" style="display:">
              Final Amount to be Paid (Rs):
            </td>  
              <td class="formLabel" id="approvedamt1" style="display:">
              <html:text property="approvedAmount" styleClass="textBoxDisabled textBoxMedium" readonly="true"/>
              </td>
            </logic:match>
            <logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="APR">
            <td height="25" class="formLabel" id="approvedamt" style="display:none;" nowrap="nowrap">
              Final Amount to be Paid (Rs):
            </td>  
              <td class="formLabel" id="approvedamt1" style="display:none;" nowrap="nowrap">
              <html:text property="approvedAmount" styleClass="textBoxDisabled textBoxMedium" readonly="true"/>
              </td>
            </logic:notMatch>
        </tr>
    <%
      }// end of if(strLink.equals("Claims"))
      else
      {
      %>
      	<legend>Authorization Details</legend>
	    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
          <tr>
          <td width="22%" height="25" class="formLabel" nowrap="nowrap">Authorization No:</td>
            <td width="30%" class="textLabelBold" nowrap="nowrap"><bean:write name="frmAuthorizationDetails" property="authorizationNo"/></td>
            <logic:match name="frmAuthorizationDetails" property="statusTypeID" value="APR">
            <td height="25" class="formLabel" id="approvedamt" style="display:" nowrap="nowrap">
              Final Approved Amt. (Rs):
            </td>  
              <td class="textLabelBold" id="approvedamt1" style="display:" nowrap="nowrap">
              <html:text property="approvedAmount" styleClass="textBoxDisabled textBoxMedium" readonly="true"/>
              </td>
            </logic:match>
            <logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="APR">
            <td height="25" class="formLabel" id="approvedamt" style="display:none;" nowrap="nowrap"> 
              Final Approved Amt. (Rs):
             </td> 
              <td class="textLabelBold" id="approvedamt1" style="display:none;" nowrap="nowrap">
              <html:text property="approvedAmount" styleClass="textBoxDisabled textBoxMedium" readonly="true"/>
              </td>
            </logic:notMatch>
        </tr>
    <%
      }// end of else
    %>
      <tr>
        <td height="25" class="formLabel" >Status:</td>
        <%
          if(strLink.equals("Claims"))
        {
		%>
   <%-- <logic:match name="frmAuthorizationDetails" property="statusTypeID" value="REJ">
   
   							<logic:match name="frmAuthorizationDetails" property="insurerApprovedStatus" value="Awaiting Insurer Advice">
      						   <logic:match name="frmAuthorizationDetails" property="completedYN" value="N">	 
      							   <td height="25" class="textLabel" nowrap="nowrap">
          							 <html:select property="statusTypeID" styleClass="selectBox selectBoxMedium"  disabled="<%= aprviewmode %>">
				                 			<html:option value="REJ">Referred To Insurer</html:option>
           							 </html:select>
        							 </td>
  							</logic:match>
  							<logic:notMatch name="frmAuthorizationDetails" property="completedYN" value="N">	 
      							  <td height="25" class="textLabel" nowrap="nowrap">
          							 <html:select property="statusTypeID" styleClass="selectBox selectBoxMedium" onchange="showhideReasonAuth1()" disabled="<%= viewmode %>">
           								 <html:optionsCollection name="claimStatus" label="cacheDesc" value="cacheId" />
           							 </html:select>
        							 </td>
  							</logic:notMatch>
  							</logic:match>
  						  <logic:notMatch name="frmAuthorizationDetails" property="insurerApprovedStatus" value="Awaiting Insurer Advice">
  							<td height="25" class="textLabel" nowrap="nowrap">
          							 <html:select property="statusTypeID" styleClass="selectBox selectBoxMedium" onchange="showhideReasonAuth1()" disabled="<%= viewmode %>">
           								 <html:optionsCollection name="claimStatus" label="cacheDesc" value="cacheId" />
           							 </html:select>
        							 </td>
  							
  							 </logic:notMatch>
 	</logic:match>
 	
 	<logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="REJ"> --%>
          <td height="25" class="textLabel" nowrap="nowrap">
            <html:select property="statusTypeID" styleClass="selectBox selectBoxMedium" onchange="showhideReasonAuth1()" disabled="<%= viewmode %>">
            <html:optionsCollection name="claimStatus" label="cacheDesc" value="cacheId" />
            </html:select>
          </td>
    <%-- </logic:notMatch> --%>
        <%
        }
        else
        {
        %>
          <%-- <td height="25" class="textLabel" nowrap="nowrap">
            <html:select property="statusTypeID" styleClass="selectBox selectBoxMedium" onchange="showhideReasonAuth1()" disabled="<%= viewmode %>">
            <html:optionsCollection name="preauthStatus" label="cacheDesc" value="cacheId" />
            </html:select>
          </td>--%>
          
          	<%-- <logic:match name="frmAuthorizationDetails" property="statusTypeID" value="REJ">
   								<logic:match name="frmAuthorizationDetails" property="insurerApprovedStatus" value="Awaiting Insurer Advice">
      								 <logic:match name="frmAuthorizationDetails" property="completedYN" value="N">	 
      							   <td height="25" class="textLabel" nowrap="nowrap">
          							 <html:select property="statusTypeID" styleClass="selectBox selectBoxMedium"  disabled="<%= aprviewmode %>">
				                 			<html:option value="REJ">Referred To Insurer</html:option>
           							 </html:select>
        							 </td>
  							</logic:match>
  							<logic:notMatch name="frmAuthorizationDetails" property="completedYN" value="N">	 
      							  <td height="25" class="textLabel" nowrap="nowrap">
          							 <html:select property="statusTypeID" styleClass="selectBox selectBoxMedium" onchange="showhideReasonAuth1()" disabled="<%= viewmode %>">
           								 <html:optionsCollection name="claimStatus" label="cacheDesc" value="cacheId" />
           							 </html:select>
        							 </td>
  							</logic:notMatch>
  							</logic:match>
  							<logic:notMatch name="frmAuthorizationDetails" property="insurerApprovedStatus" value="Awaiting Insurer Advice">
  							 		<td height="25" class="textLabel" nowrap="nowrap">
          							 <html:select property="statusTypeID" styleClass="selectBox selectBoxMedium" onchange="showhideReasonAuth1()" disabled="<%= viewmode %>">
           								 <html:optionsCollection name="preauthStatus" label="cacheDesc" value="cacheId" />
           							 </html:select>
        							 </td>
  							</logic:notMatch>
 		</logic:match>
 	
 		<logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="REJ"> --%>
          <td height="25" class="textLabel" nowrap="nowrap">
            <html:select property="statusTypeID" styleClass="selectBox selectBoxMedium" onchange="showhideReasonAuth1()" disabled="<%= viewmode %>">
            <html:optionsCollection name="preauthStatus" label="cacheDesc" value="cacheId" />
            </html:select>
          </td>
    	<%-- </logic:notMatch> --%>
        <%
        }
        %>
            <logic:match name="frmAuthorizationDetails" property="statusTypeID" value="APR">
              <td height="25" id="permission" style="display:" nowrap="nowrap">
                <span>Permission Sought from:</span>
              </td>  
                <td height="25" id="permission1" style="display:" nowrap="nowrap">
                  <span>
                  <html:text property="authPermittedBy"  onkeypress="ConvertToUpperCase(event.srcElement);"styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>"/>
                </span>
              </td>
            </logic:match>
          <logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="APR">
              <td height="25" id="permission" style="display:none;" nowrap="nowrap">
                <span>Permission Sought from:</span>
               </td> 
                <td height="25" id="permission1" style="display:none;" nowrap="nowrap">
                  <span>
                  <html:text property="authPermittedBy"  styleClass="textBox textBoxMedium" maxlength="60" />
                </span>
              </td>
            </logic:notMatch>
      </tr>
    <%
        if(strLink.equals("Claims"))
      {
    %>
        <tr>
          <td class="formLabel" nowrap="nowrap" height="25">Settled By: </td>
            <td class="textLabel" nowrap="nowrap"><strong><bean:write name="frmAuthorizationDetails" property="authorizedBy"/></strong></td>
            <td width="22%" height="25" class="formLabel" nowrap="nowrap">Settled Date / Time: </td>
            <td width="26%" class="textLabel" nowrap="nowrap"><strong><strong><bean:write name="frmAuthorizationDetails" property="authorizedDate"/>&nbsp;<bean:write name="frmAuthorizationDetails" property="authorizedTime"/>&nbsp;<bean:write name="frmAuthorizationDetails" property="authorizedDay"/></strong></td>
        </tr>
    <%
      }// end of if(strLink.equals("Claims"))
      else
      {
      %>
          <tr>
          <td class="formLabel" height="25" nowrap="nowrap">Authorized By: </td>
            <td class="textLabel" ><strong><bean:write name="frmAuthorizationDetails" property="authorizedBy"/></strong></td>
            <td  width="22%" height="25" class="formLabel" nowrap="nowrap">Authorized Date / Time: </td>
            <td width="26%" class="textLabel" nowrap="nowrap"><strong><strong><bean:write name="frmAuthorizationDetails" property="authorizedDate"/>&nbsp;<bean:write name="frmAuthorizationDetails" property="authorizedTime"/>&nbsp;<bean:write name="frmAuthorizationDetails" property="authorizedDay"/></strong></td>
        
        	<td>Final Approval:</td>
            <td nowrap="nowrap">
            <html:checkbox name="frmAuthorizationDetails" property="mailNotifyYN"></html:checkbox>            
            </td>
        
        	
        
        </tr>
        
    <%
      }// end of else
    %>
      <logic:match name="frmAuthorizationDetails" property="status" value="Y">
        <tr id="Reason" style="display:">
      </logic:match>
      <logic:notMatch name="frmAuthorizationDetails" property="status" value="Y">
        <tr id="Reason" style="display:none;">
      </logic:notMatch>
        <td  height="25" styleId="Reason" class="formLabel" nowrap="nowrap">Reason: <span class="mandatorySymbol">*</span></td>
            <td colspan="3" nowrap="nowrap">
              <html:select property="reasonTypeID" styleClass="selectBox selectBoxListLargest" disabled="<%= viewmode %>">
              <html:option value="">Select from list</html:option>
                <html:optionsCollection name="approveReason" label="cacheDesc" value="cacheId" />
              </html:select>
            </td>
      </tr>
      <tr>
        <td class="formLabel" height="25">Remarks: </td>
            <td colspan="3" nowrap="nowrap">
              <html:textarea  property="remarks" styleClass="textBox textAreaLong" disabled="<%= viewmode %>"/>
            </td>
      </tr>
      <%
  		if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
  		{
    	%>
    	<logic:match name="frmAuthorizationDetails" property="statusTypeID" value="REJ">
   		 <tr id="clauseremarks" style="display:">
        	<td class="formLabel" height="25">Clauses: </td>              
              <td class="textLabel"  nowrap="nowrap"><strong><bean:write name="frmAuthorizationDetails" property="clauseRemarks"/></strong></td>
      	</tr>
      	</logic:match>
      	<logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="REJ">
   		 <tr id="clauseremarks" style="display:none">
        	<td class="formLabel" height="25">Clauses: </td>              
              <td class="textLabel" nowrap="nowrap"><strong><bean:write name="frmAuthorizationDetails" property="clauseRemarks"/></strong></td>
      	</tr>
      	</logic:notMatch>
		<!-- KOC 1286 FOR OPD -->
      <logic:match name="frmAuthorizationDetails" property="opdAmountYN" value="Y">
        <tr id="Reason" style="display:">
      </logic:match>
       <logic:notMatch name="frmAuthorizationDetails" property="opdAmountYN" value="Y">
        <tr id="Reason" style="display:none;">
      </logic:notMatch>
      <logic:match name="frmAuthorizationDetails" property="claimsubtype" value="Y">
        <tr id="Reason" style="display:">
      </logic:match>
       <logic:notMatch name="frmAuthorizationDetails" property="claimsubtype" value="Y">
        <tr id="Reason" style="display:none;">
      </logic:notMatch>
       <!-- KOC 1286 FOR OPD -->
 		 <%
  			}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
 		 %>
    </table>
  </fieldset>
  <%---Bajaj  --%>
  <logic:notEmpty name="frmAuthorizationDetails"  property="insurerApprovedStatus" >
  <fieldset>
  <legend>Insurer Approval Details</legend>
   <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
  
   <tr>
        <td class="formLabel" height="25">Insurer Approval Status: </td>
            <td colspan="3" nowrap="nowrap">
              <bean:write name="frmAuthorizationDetails"  property="insurerApprovedStatus" />
            </td>
      </tr>
        <tr>
        <td class="formLabel" width="22%" height="25">Insurer Remarks: </td>
            <td colspan="3" width="78%" nowrap="nowrap">
              <html:textarea  name="frmAuthorizationDetails" property="insremarks" styleClass="textBox textAreaLongHt" readonly="true" />
           </td>
      </tr>
    </table>
  </fieldset>
 </logic:notEmpty>
 
   <%---Bajaj  --%>
  <!-- E N D : Form Fields -->
  <!-- S T A R T : Buttons -->
  <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="100%" align="center">
		<%
		if(TTKCommon.isAuthorized(request,"Edit"))
      	{
		%>
        <logic:match name="frmAuthorizationDetails" property="preauthstatus" value="Y">
        	<button type="button" name="Button" accesskey="g" id="authletter" class="buttons" style="display:" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReport();"><u>G</u>enerate Letter</button>&nbsp;
        </logic:match>
        <logic:notMatch name="frmAuthorizationDetails" property="preauthstatus" value="Y">
        	<button type="button" name="Button" accesskey="g" id="authletter" class="buttons" style="display:none" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReport();"><u>G</u>enerate Letter</button>&nbsp;
        </logic:notMatch>
       <%
       }
      if(strLink.equals("Pre-Authorization"))
      {
        if(TTKCommon.isAuthorized(request,"Special Permission"))
        {
        %>
        <logic:match name="frmAuthorizationDetails" property="completedYN" value="Y">
        	<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSend();">S<u>e</u>nd</button>&nbsp;
        </logic:match>
      <%
        }
      }//end of if(strLink.equals("Pre-Authorization"))
      if(strLink.equals("Claims"))
      {
        if(TTKCommon.isAuthorized(request,"Special Permission"))
        {
        %>
        <logic:match name="frmAuthorizationDetails" property="completedYN" value="Y">
        	<logic:match name="frmAuthorizationDetails" property="statusTypeID" value="APR">
			<logic:match name="frmAuthorizationDetails" property="cigna_Ins_Cust" value="N">
	        	<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSend();">S<u>e</u>nd</button>&nbsp;
        	</logic:match>
			</logic:match>
            <!-- added for Mail-SMS Template for Cigna -->
			<logic:match name="frmAuthorizationDetails" property="statusTypeID" value="REJ">
        	<logic:match name="frmAuthorizationDetails" property="cigna_Ins_Cust" value="Y">
        		<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSend();">S<u>e</u>nd</button>&nbsp;
           	</logic:match>
        	</logic:match>
        	<logic:match name="frmAuthorizationDetails" property="statusTypeID" value="PCO">
        	<logic:match name="frmAuthorizationDetails" property="cigna_Ins_Cust" value="Y">
        		<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSend();">S<u>e</u>nd</button>&nbsp;
           	</logic:match>
        	</logic:match>
        </logic:match>
      <%
        }
      }//end of if(strLink.equals("Claims"))
      if(TTKCommon.isAuthorized(request,"Edit"))
      {
        %>
        <logic:notEmpty name="frmAuthorizationDetails" property="memberSeqID">
        <logic:match name="frmAuthorizationDetails" property="statusTypeID" value="REJ">
			<button type="button" name="Button" accesskey="c" id="clauseid" style="display:" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSelectClause();"><u>C</u>lause</button>&nbsp;
		</logic:match>
	    <logic:notMatch name="frmAuthorizationDetails" property="statusTypeID" value="REJ">
			<button type="button" name="Button" accesskey="c" style="display:none" id="clauseid" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSelectClause();"><u>C</u>lause</button>&nbsp;
		</logic:notMatch>
		</logic:notEmpty>
		<logic:equal name="frmAuthorizationDetails" property="insDecisionyn" value="Y">
		<logic:match name="frmAuthorizationDetails" property="completedYN" value="N">
		<button type="button" name="Button4" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onInsOverrideConf()">O<u>v</u>erride Healthcare Config </button>&nbsp;<%--1274A --%>
		</logic:match>
		</logic:equal>
		
		
        <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
		<!--denial process-->
		<logic:match name="frmAuthorizationDetails" property="denialBanyn" value="Y">
		<logic:match name="frmAuthorizationDetails" property="statusTypeID" value="REJ">
        <button type="button" name="Button3" accesskey="i" id="intimate" style="display:" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onIntimate();">Recommend To <u>I</u>nsurer</button>&nbsp;<%--Bajaj 1274A --%>
	</logic:match>
	</logic:match>
	<logic:match name="frmAuthorizationDetails" property="denialBanyn" value="Y">
		<logic:match name="frmAuthorizationDetails" property="statusTypeID" value="APR">
        <button type="button" name="Button3" accesskey="i" id="intimate" style="display:" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onIntimate();">Recommend To <u>I</u>nsurer</button>&nbsp;<%--Bajaj 1274A --%>
	</logic:match>
	</logic:match>
	<!--denial process-->
	 <%
        }//end of checking for Edit permission
        %>
      </td>
    </tr>
  </table>
  <!-- E N D : Buttons -->
  </div>
  <INPUT TYPE="hidden" NAME="mode" VALUE="">
  <html:hidden property="status"/>
  <input type="hidden" name="child" value="">
  <input type="hidden" name="claimsubgenraltypeid" value="<%=claimsubgenraltypeid%>">
  <html:hidden property="preauthstatus"/>
  <html:hidden property="maxAllowedAmt"/>
  <html:hidden property="requestedAmount"/>
  <html:hidden property="totalAmount"/>
  <html:hidden property="enrollDtlSeqID"/>
  <html:hidden property="preAuthSeqID"/>
  <html:hidden property="serTaxCalPer"/>
  <html:hidden property="clmEnrollDtlSeqID"/>
  <html:hidden property="claimSeqID"/>
  <html:hidden property="DMSRefID"/>
  <html:hidden property="completedYN"/>
  <html:hidden property="balanceSeqID"/>
  <html:hidden property="calcButDispYN"/>
  <html:hidden property="pressButManYN"/>
  <html:hidden property="availDomTrtLimit"/><!--KOC 1286 FOR OPD -->
  <html:hidden property="opdAmountYN"/><!--KOC 1286 FOR OPD -->
  <html:hidden property="claimsubtype"/><!--KOC 1286 FOR OPD -->
  <html:hidden property="clmapramt"/><!--INVESTIGATION UAT -->

  <html:hidden name="frmAuthorizationDetails" property="cigna_Ins_Cust"/>  
  <!-- added for CR KOC - Mail-SMS for Cigna -->
  <html:hidden property="mailNotify"/>
  <script>
  mailNotify();
  </script>

  <!--Modified for Policy Deductable - KOC-1277  -->
  <html:hidden name="frmAuthorizationDetails" property="policy_deductable_yn"/>
  <html:hidden name="frmAuthorizationDetails" property="preauthDifference"/>
  <html:hidden name="frmAuthorizationDetails" property="preauthYN"/>
 <!-- ended -->	
  <input type="hidden" id="authorizationNo" name="authorizationNo" value="<bean:write name="frmAuthorizationDetails" property="authorizationNo"/>">
  <input type="hidden" id="authLtrTypeID" name="authLtrTypeID" value="<bean:write name="frmAuthorizationDetails" property="authLtrTypeID"/>">
  <input type="hidden" id="preAuthNo" name="preAuthNo" value="<%=TTKCommon.checkNull(PreAuthWebBoardHelper.getWebBoardDesc(request))%>">
  <logic:notEmpty name="frmAuthorizationDetails" property="frmChanged">
    <script> ClientReset=false;TC_PageDataChanged=true;</script>
  </logic:notEmpty>
  
</html:form>
<!-- E N D : Content/Form Area -->