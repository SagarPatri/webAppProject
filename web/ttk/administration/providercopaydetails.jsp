<!DOCTYPE html>
<%
/** @ (#) providercopaydetails.jsp 11 May 2017
 * Project     :Project X
 * File        : providercopaydetails.jsp
 * Author      : Nagababu K
 * Company     : RCS Technologies
 * Date Created: 11 May 2017
 *
 * @author 	   : Nagababu K
 * Modified by   : 
 * Modified date : 
 * Reason        :
 *
 */
%>


<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%
  pageContext.setAttribute("listGeneralCodePlan",Cache.getCacheObject("generalCodePlan"));
  pageContext.setAttribute("listDurationType",Cache.getCacheObject("durationType"));
  pageContext.setAttribute("benefitTypes",Cache.getCacheObject("benefitTypes"));
  pageContext.setAttribute("claimTypes", Cache.getCacheObject("claimType"));
  boolean viewmode=true;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }//end of if(TTKCommon.isAuthorized(request,"Edit"))
  pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>
<html>
<head>
<title>Provider Co-pay Details</title>
<script type="text/javascript"  SRC="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/administration/providercopaydetails.js"></script>
<script type="text/javascript" src="/ttk/scripts/utils.js"></script>
<script type="text/javascript" SRC="/ttk/scripts/trackdatachanges.js"></script>
<script type="text/javascript" SRC="/ttk/scripts/common/healthcarelayout.js"></script>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<script type="text/javascript">
JS_SecondSubmit=false;
</script>
<style type="text/css">

.buttonsHover{
  color:#CD0B3D;
cursor: pointer;
}

</style>
</head>
<body>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ProviderCopayDetailsAction.do" >

<!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td> <bean:write name="frmProviderCopayRules" property="caption" /></td>
    </tr>
  </table>
  <logic:notEmpty name="successMsg" scope="request">
				<table align="center" class="successContainer" style="display:"
					border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="successMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
<!-- E N D : Page Title -->
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
<div class="contentArea" id="contentArea">
  <fieldset>
  <legend><bean:write name="frmProviderCopayRules" property="benefitTypeDesc"/>&nbsp;Configuration</legend>
  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
  <logic:equal name="frmProviderCopayRules" property="benefittypeflag"  value="Y"  >
      <tr>
        <td width="20%" class="formLabel">Benefit Type: <span class="mandatorySymbol">*</span></td>
         <td width="30%">
           <html:select property="benefitType" name="frmProviderCopayRules"  styleClass="selectBox selectBoxMedium"  disabled="true">
            <html:option value="" >Select from list</html:option>
           <html:optionsCollection name="benefitTypes" label="cacheDesc" value="cacheId" />
            </html:select> 
      </td>
    
        
                <td width="20%" class="formLabel">Encounter Type: <span class="mandatorySymbol">*</span></td>
            <td width="30%">
    <table>
    <tr>
    <td> <html:text name="frmProviderCopayRules"	property="encountertypeId" styleId="encountertypeId"  styleClass="textBox textBoxMedium"	readonly="true" /></td>
    <td>
     <a href="#" accesskey="g"	onClick="javascript:onSelectLocations('<bean:write name="frmProviderCopayRules" property="benefitType"/>');" class="search"> <img	src="/ttk/images/EditIcon.gif" alt=""	width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
    </td>
    </tr>
    </table>
   
    </td>
           
        </tr>
      
    <tr>
      <td width="20%" class="formLabel">Claim Type: <span class="mandatorySymbol">*</span></td>
      
         <td width="30%">
           <html:select name="frmProviderCopayRules" property="claimType"  styleClass="selectBox selectBoxMedium" onchange="onChangeClaimType();">
            <html:option value="" >Select from list</html:option>
           <html:option value="CNH" >Cashless</html:option>
           <html:option value="CTM" >Reimbursement</html:option>
           <html:option value="ALL" >ALL</html:option>
            </html:select>             
      </td>
      
  
    <td width="20%" class="formLabel">Network: <span class="mandatorySymbol">*</span></td>
    <td width="30%">
    
    <logic:equal name="frmProviderCopayRules" property="claimType" value="CNH">
       <html:select name="frmProviderCopayRules" property="networkYN" styleClass="selectBox selectBoxMedium" disabled="true" >
							  <html:option value="Y">NETWORK</html:option>
					  </html:select>
    </logic:equal>
     <logic:notEqual name="frmProviderCopayRules" property="claimType" value="CNH">
    <html:select name="frmProviderCopayRules" property="networkYN" styleClass="selectBox selectBoxMedium">
							 <html:option value="">Select from list</html:option>
							  <html:option value="Y">NETWORK</html:option>
							  <html:option value="N">NON-NETWORK</html:option>
							  <html:option value="ALL">ALL</html:option>
					  </html:select>
					  </logic:notEqual>
					  
<!-- 					  -- -->
    </td>
      </tr>
    <tr>
    <td width="20%">Geographical Location:<span class="mandatorySymbol">*</span></td>
    <td width="30%">
    <table>
    <tr>
    <td> <html:text name="frmProviderCopayRules"	property="geoLocation" styleId="geoLocations"  styleClass="textBox textBoxMedium"	readonly="true" /></td>
    <td>
     <a href="#" accesskey="g"	onClick="javascript:onSelectLocations('GEO');" class="search"> <img	src="/ttk/images/EditIcon.gif" alt=""	width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
    </td>
    </tr>
    </table>
   
    </td>

 <td width="20%">Country/Emirate:<span class="mandatorySymbol">*</span></td>
    <td width="30%">
    <table>
    <tr>
    <td> <html:text name="frmProviderCopayRules" styleId="countryIDs"	property="countryIDs"  styleClass="textBox textBoxMedium"	readonly="true" /></td>
    <td>
     <a href="#" accesskey="g"	onClick="javascript:onSelectLocations('CON');" class="search"> <img	src="/ttk/images/EditIcon.gif" alt=""	width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
    </td>
    </tr>
    </table>
   
    </td>
    </tr>
<tr>
     <td width="20%">Provider Type:<span class="mandatorySymbol">*</span></td>
    <td width="30%">
    
    <html:select name="frmProviderCopayRules" property="providerType"  styleClass="selectBox selectBoxMedium">
            <html:option value="" >Select from list</html:option>
           <html:option value="SGO" >Government</html:option>
           <html:option value="SPR" >Private</html:option>
           <html:option value="ALL" >ALL</html:option>
            </html:select>
    </td>

 <td width="20%">Provider Facility Type:<span class="mandatorySymbol">*</span></td>
    <td width="30%">
    <table>
    <tr>
    <td> <html:text name="frmProviderCopayRules" styleId="providerFacilities"	property="providerFacilities"  styleClass="textBox textBoxMedium"	readonly="true" /></td>
    <td>
     <a href="#" accesskey="g"	onClick="javascript:onSelectLocations('PFT');" class="search"> <img	src="/ttk/images/EditIcon.gif" alt=""	width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
    </td>
    </tr>
    </table>
   
    </td>
    </tr>

<tr>
     <td width="20%">Provider Co-pay Details:</td>
    <td width="30%">
    <table>
    <tr>
    <td> <html:text name="frmProviderCopayRules"	property="providerSeqIDs"  styleClass="textBox textBoxMedium"	readonly="true" /></td>
    <td>
     <a href="#" accesskey="g"	onClick="javascript:onSelectLocations('PRO');" class="search"> <img	src="/ttk/images/EditIcon.gif" alt=""	width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
    </td>
    </tr>
    </table>
   
    </td>
</tr>

<tr>
<td colspan="4">
 <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
   <logic:match name="viewmode" value="false">
        <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddCondition();"><u>A</u>dd</button>&nbsp;
		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
    </logic:match>
    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:self.close();"><u>C</u>lose</button>
	</td>
  </tr>
</table>
</td>
</tr>
</logic:equal>
 <logic:equal name="frmProviderCopayRules" property="benefittypeflag"  value="N"  >
   <tr>
        <td width="20%" class="formLabel">Benefit Type: <span class="mandatorySymbol">*</span></td>
         <td width="30%">
           <html:select property="benefitType" name="frmProviderCopayRules"  styleClass="selectBox selectBoxMedium"  disabled="true">
            <html:option value="" >Select from list</html:option>
           <html:optionsCollection name="benefitTypes" label="cacheDesc" value="cacheId" />
            </html:select> 
      </td>
      <td width="20%" class="formLabel">Claim Type: <span class="mandatorySymbol">*</span></td>
      
         <td width="30%">
           <html:select name="frmProviderCopayRules" property="claimType"  styleClass="selectBox selectBoxMedium" onchange="onChangeClaimType();">
            <html:option value="" >Select from list</html:option>
           <html:option value="CNH" >Cashless</html:option>
           <html:option value="CTM" >Reimbursement</html:option>
           <html:option value="ALL" >ALL</html:option>
            </html:select>             
      </td>
      
    </tr>
    <tr>
    <td width="20%" class="formLabel">Network: <span class="mandatorySymbol">*</span></td>
    <td width="30%">
    
    <logic:equal name="frmProviderCopayRules" property="claimType" value="CNH">
       <html:select name="frmProviderCopayRules" property="networkYN" styleClass="selectBox selectBoxMedium" disabled="true" >
							  <html:option value="Y">NETWORK</html:option>
					  </html:select>
    </logic:equal>
     <logic:notEqual name="frmProviderCopayRules" property="claimType" value="CNH">
    <html:select name="frmProviderCopayRules" property="networkYN" styleClass="selectBox selectBoxMedium">
							 <html:option value="">Select from list</html:option>
							  <html:option value="Y">NETWORK</html:option>
							  <html:option value="N">NON-NETWORK</html:option>
							  <html:option value="ALL">ALL</html:option>
					  </html:select>
					  </logic:notEqual>
					  
<!-- 					  -- -->
    </td>
    <td width="20%">Geographical Location:<span class="mandatorySymbol">*</span></td>
    <td width="30%">
    <table>
    <tr>
    <td> <html:text name="frmProviderCopayRules"	property="geoLocation" styleId="geoLocations"  styleClass="textBox textBoxMedium"	readonly="true" /></td>
    <td>
     <a href="#" accesskey="g"	onClick="javascript:onSelectLocations('GEO');" class="search"> <img	src="/ttk/images/EditIcon.gif" alt=""	width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
    </td>
    </tr>
    </table>
   
    </td>
</tr>
<tr>
 <td width="20%">Country/Emirate:<span class="mandatorySymbol">*</span></td>
    <td width="30%">
    <table>
    <tr>
    <td> <html:text name="frmProviderCopayRules" styleId="countryIDs"	property="countryIDs"  styleClass="textBox textBoxMedium"	readonly="true" /></td>
    <td>
     <a href="#" accesskey="g"	onClick="javascript:onSelectLocations('CON');" class="search"> <img	src="/ttk/images/EditIcon.gif" alt=""	width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
    </td>
    </tr>
    </table>
   
    </td>
    
     <td width="20%">Provider Type:<span class="mandatorySymbol">*</span></td>
    <td width="30%">
    
    <html:select name="frmProviderCopayRules" property="providerType"  styleClass="selectBox selectBoxMedium">
            <html:option value="" >Select from list</html:option>
           <html:option value="SGO" >Government</html:option>
           <html:option value="SPR" >Private</html:option>
           <html:option value="ALL" >ALL</html:option>
            </html:select>
    </td>
</tr>

<tr>
 <td width="20%">Provider Facility Type:<span class="mandatorySymbol">*</span></td>
    <td width="30%">
    <table>
    <tr>
    <td> <html:text name="frmProviderCopayRules" styleId="providerFacilities"	property="providerFacilities"  styleClass="textBox textBoxMedium"	readonly="true" /></td>
    <td>
     <a href="#" accesskey="g"	onClick="javascript:onSelectLocations('PFT');" class="search"> <img	src="/ttk/images/EditIcon.gif" alt=""	width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
    </td>
    </tr>
    </table>
   
    </td>
    
     <td width="20%">Provider Co-pay Details:</td>
    <td width="30%">
    <table>
    <tr>
    <td> <html:text name="frmProviderCopayRules"	property="providerSeqIDs"  styleClass="textBox textBoxMedium"	readonly="true" /></td>
    <td>
     <a href="#" accesskey="g"	onClick="javascript:onSelectLocations('PRO');" class="search"> <img	src="/ttk/images/EditIcon.gif" alt=""	width="16" height="16" border="0" align="absmiddle">&nbsp;</a>
    </td>
    </tr>
    </table>
   
    </td>
</tr>

<tr>
<td colspan="4">
 <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
   <logic:match name="viewmode" value="false">
        <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddCondition();"><u>A</u>dd</button>&nbsp;
		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
    </logic:match>
    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:self.close();"><u>C</u>lose</button>
	</td>
  </tr>
</table>
</td>
</tr>
 
 </logic:equal>
    </table>


  </fieldset>
  <!-- E N D : Form Fields -->
   

<fieldset>
  <legend><bean:write name="frmProviderCopayRules" property="benefitTypeDesc"/>&nbsp;Summary</legend>
 <ttk:ProviderCopayCondionDetails/>
 </fieldset>

</div>
<!-- E N D : Buttons -->

<input type="hidden" name="child" value="">
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<html:hidden property="benefitTypeDesc"/>
<html:hidden property="locationType"/>
<html:hidden property="claimTypeDesc"/>
<html:hidden property="networkYNDesc"/>
<html:hidden property="providerTypeDesc"/>
<html:hidden property="benefittypeflag" name="frmProviderCopayRules"/>
<html:hidden property="rownum"/>

<logic:notEmpty name="frmProviderCopayRules" property="frmChanged">
  <script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
</html:form>
</body>
</html>