<!--/* KOC 1270 for hospital cash benefit*/-->

<%
/** @ (#) changeplanhospital.jsp Mar 8, 2006
 * Project      : TTK Healthcare Services
 * File         : changeplanhospital.jsp
 * Author       : 
 * Company       :
 * Date Created  :
 * 
 * @author 		 :
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/enrollment/changeplanhospital.js"></script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>

<html:form action="/ChangePlanAction.do" >

<!--S T A R T : Page Title -->


	<logic:match name="frmPolicyList" property="switchType" value="ENM">
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmPolicyList" property="switchType" value="END">
	
	<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	    <tr>
    		<td width="100%"><bean:write name="frmChangePlan" property="caption" /></td>
    		<td align="right">&nbsp;&nbsp;&nbsp;</td>    
   		</tr>
	</table>

<!--E N D : Page Title -->

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
 <!-- S T A R T : Form Fields -->


	<fieldset>
		<legend>Change Plan / Hospital Cash Benefit /Canvalescence Cash Benefit</legend>
		 		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
	 				<td class="formLabel" width="20%">Hospital Cash Benefit:</td>	 				
	  				<td width="4%">	  				
	 					<html:checkbox name="frmChangePlan" property="hospCashBenefitsYN"  value="Y" styleId="hospCashBenefitsYN"/>
	 					<input type="hidden" name="hospCashBenefitsYN" value="N">	 					
	 				</td>
	 				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	 			</tr>
	 			
	 			<tr>
	 				<td class="formLabel" width="20%">Canvalescence Cash Benefit:</td>	 				
	  				<td width="4%">	  				
	 					<html:checkbox name="frmChangePlan" property="convCashBenefitsYN" value="Y" styleId="convCashBenefitsYN"/>
	 					<input type="hidden" name="convCashBenefitsYN" value="N">	 					
	 				</td>
	 				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	 			</tr>
	 			<!-- added for KOC-1273 -->
	 			<tr>
	 				<td class="formLabel" width="20%">Critical Illness Benefit:</td>	 				
	  				<td width="4%">	  				
	 					<html:checkbox name="frmChangePlan" property="criticalBenefitYN"  value="Y" styleId="reductWaitingPeriodYN"/>
	 					<input type="hidden" name="criticalBenefitYN" value="N">	 					
	 				</td>
	 				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	 			</tr>
	 			
	 			
	 			</table>
	</fieldset>
		<fieldset>
	        <legend>Reduction In Waiting Period  Benefit</legend>
	
		 		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
	 				<td class="formLabel" width="20%">Reduction In Waiting Period:</td>	 				
	  				<td width="4%">	  				
	 					<html:checkbox name="frmChangePlan" property="reductWaitingPeriodYN"  value="Y" styleId="reductWaitingPeriodYN"/>
	 					<input type="hidden" name="reductWaitingPeriodYN" value="N">	 					
	 				</td>
	 				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	 			</tr>
	 			
	 			</table>
				
				</fieldset>
		 
		 
		 	<fieldset>
	        <legend>SumInsured Benefit</legend><!-- addedd for policy restoration -->
	
		 		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
	 				<td class="formLabel" width="20%">SumInsured Restoration Benefit:</td>	 				
	  				<td width="4%">	  				
	 					<html:checkbox name="frmChangePlan" property="sumInsuredBenefitYN"  value="Y" styleId="sumInsuredBenefitYN"/>
	 					<input type="hidden" name="sumInsuredBenefitYN" value="N">	 					
	 				</td>
	 				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	 			</tr>
	 			
	 			</table>
				
				</fieldset>
	<!-- added for koc 1278 -->
	<fieldset>
		<legend>Personal Waiting Period</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
	 				<td class="formLabel" width="20%">Personal Waiting Period:</td>	 				
	  				<td width="4%">	  				
	 					<html:checkbox name="frmChangePlan" property="personalWaitingPeriodYN"  value="Y" styleId="personalWaitingPeriodYN"/>
	 					<input type="hidden" name="personalWaitingPeriodYN" value="N">	 					
	 				</td>
	 				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	 			</tr>
			</table>
	</fieldset>
	<!-- added for koc 1278 -->	 
<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" align="center">
				<%
				if (TTKCommon.isAuthorized(request, "Edit")) 
				{
				%>
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>&nbsp; 
				<%
 				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
 				%>
				</td>
			</tr>
		</table>
		
<!-- E N D : Buttons -->
<!-- E N D : Form Fields -->

<input type="hidden" name="mode" value="">
	<html:hidden property="policyGroupSeqID"/>	
    <html:hidden property="caption" />
    <html:hidden property="hospCashBenefit" />        
    <html:hidden property="convCashBenefit" />
    <html:hidden property="personalWaitingPeriod" />	<!-- added for koc 1278 -->	
	<html:hidden property="reductWaitingBenefit" />
    <html:hidden property="sumInsuredBenefit" /><!-- addedd for policy restoration -->
    <!-- added for KOC-1273 -->
    <html:hidden property="criticalBenefit"/>
     <script>
    stopPreAuthClaim();
    </script>
</html:form>
	<!-- E N D : Content/Form Area -->