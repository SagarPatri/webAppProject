<!DOCTYPE html>
<html>
<%/**
 * @ (#) companRuleConfig.jsp June 28th 2018
 * Project       :Vidal Health TPA Services
 * File          : consultationRuleConfig.jsp
 * Author        : Nagababu K
 * Company       : Span Systems Corporation
 * Date Created  : June 28th 2017
 * @author       : Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList"%>
<head>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/administration/companRuleConfig.js"></script>

<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<script type="text/javascript">
<!--//-->

</script>
<style type="text/css">
.tdClass{
padding-top: 5px;
padding-bottom: 5px;
}
</style>
</head>
<body>
<!-- S T A R T : Content/Form Area -->
<div class="contentAreaHScroll" id="contentArea" style="">
	<html:form action="/DynCompanAddRuleAction.do" >
	<!-- S T A R T : Page Title -->
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td >List of all Applicable Clauses - <bean:write name="frmDefineRules" property="caption"/></td>
		<td align="right" class="webBoard">&nbsp;
			
		</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	
	<html:errors/>
	<!-- S T A R T : Success Box -->
	 <logic:notEmpty name="displayMsg" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
					  <bean:write name="displayMsg" scope="request"/>
				  </td>
				</tr>
			</table>
	</logic:notEmpty>
 	<!-- E N D : Success Box -->

	<!-- S T A R T : Form Fields -->
	
	<fieldset>
	<legend>Rule Date</legend>
		<table style="margin-left: 15px;width: 75%;border: 1px solid gray;" align="center" class="formContainer" cellpadding="0" cellspacing="0" border="0" width="80%">
         <tr style="margin-top: 10px;margin-left: 10px;">
			<td class="formLabel tdClass" nowrap>Pre-approval Required<span class="mandatorySymbol">*</span>:</td>
			<td align="left" width="30%" class="textLabel tdClass">
			<html:select name="frmDynCompanRulesConfig"	property="preApprvReqYN" onchange="javascript:document.getElementById('preApprvLimitID').value='';" style="height:20px;width:50px;" 	styleClass="selectBox selectBoxSmall">
							<html:option value="Y">Yes</html:option>
							<html:option value="N">No</html:option>	
			</html:select>
			</td>
			<td class="formLabel tdClass" nowrap>Pre-approval Limit<span class="mandatorySymbol">*</span>:</td>
			<td width="30%" class="textLabel tdClass">
			<html:text name="frmDynCompanRulesConfig" styleId="preApprvLimitID"	property="preApprvLimit" onkeyup="isNumeric(this);"  styleClass="textBox textBoxTiny" />(AED)
			</td>
			<td class="formLabel tdClass" nowrap>Provider Facility Types<span class="mandatorySymbol">*</span>:</td>
			<td width="30%" class="textLabel tdClass">
			<table>
			<tr>
			<td><html:text name="frmDynCompanRulesConfig" readonly="true" styleId="providerTypesID"	property="providerTypes" styleClass="textBox textBoxSmall" /></td>
			<td>
			<a style="margin-left: -4px;"  href="#" accesskey="g" onClick="javascript:selectProviderTypes('providerTypesID','S')" class="search"> 
			<img	src="/ttk/images/EditIcon.gif" alt="Select Provider Type" width="16" height="16" border="0" align="absmiddle">
			</a>
			</td>
			</tr>
			</table>		
			
			</td>
			
		</tr>
		<tr>
		<td class="formLabel tdClass" nowrap>Geographical Location<span class="mandatorySymbol">*</span>:</td>
			<td width="30%" class="textLabel tdClass">
			<table>
			<tr>
			<td><html:text name="frmDynCompanRulesConfig" readonly="true" styleId="geoLocationID"	property="geoLocationID" styleClass="textBox textBoxSmall" /></td>
			<td>
			<a style="margin-left: -4px;" href="#" accesskey="g" onClick="javascript:openGeoLocation('geoLocationID','S');" class="search"> 
			<img	src="/ttk/images/EditIcon.gif" alt="Select Geographical Location" width="16" height="16" border="0" align="absmiddle">
			</a>
			</td>
			</tr>
			</table>
			</td>
			<td class="formLabel tdClass" nowrap>Country<span class="mandatorySymbol">*</span>:</td>
			<td width="30%" class="textLabel tdClass">
			<table>
			<tr>
			<td><html:text name="frmDynCompanRulesConfig" styleId="countryID"	property="countryIDs" styleClass="textBox textBoxSmall" /></td>
			<td>
			<a style="margin-left: -4px;" href="#" accesskey="g" onClick="javascript:openGeoCountryList('countryID','S','')" class="search"> 
			<img	src="/ttk/images/EditIcon.gif" alt="Select Countries" width="16" height="16" border="0" align="absmiddle">
			</a>
			</td>
			</tr>
			</table>			
			
			</td>
			<td class="formLabel tdClass" nowrap>Emirates:</td>
			<td width="30%" class="textLabel tdClass">
			<table>
			<tr>
			<td><html:text name="frmDynCompanRulesConfig" styleId="emiratesID"	property="emiratesIDs" styleClass="textBox textBoxSmall" /></td>
			<td>
			<a style="margin-left: -4px;" href="#" accesskey="g" onClick="javascript:selectEmirates('emiratesID','S')" class="search"> 
			<img	src="/ttk/images/EditIcon.gif" alt="Select Emirates" width="16" height="16" border="0" align="absmiddle">
			</a></td>
			</tr>
			</table>
			
			</td>
		</tr>
		<tr>
		 <td width="20%">Provider Type:<span class="mandatorySymbol">*</span></td>
    <td width="30%">
    
    <html:select name="frmDynCompanRulesConfig" property="hospType"  styleClass="selectBox selectBoxMedium">
            <html:option value="" >Select from list</html:option>
           <html:option value="SGO" >Government</html:option>
           <html:option value="SPR" >Private</html:option>
           <html:option value="SGO|SPR" >ALL</html:option>
            </html:select>
    </td>
			<td class="formLabel tdClass" nowrap>Per-policy Limit:</td>
			<td width="30%" class="textLabel tdClass">
			<html:text name="frmDynCompanRulesConfig"	property="perPolicyLimit" onkeyup="isNumeric(this);"  styleClass="textBox textBoxTiny" />(AED)
			</td>
			<td class="formLabel tdClass" nowrap>Per-claim Limit:</td>
			<td width="30%" class="textLabel tdClass">
			<html:text name="frmDynCompanRulesConfig"	property="perClaimLimit" onkeyup="isNumeric(this);"  styleClass="textBox textBoxTiny" />(AED)
			</td>
			
		</tr>
		<tr>
		<td class="formLabel tdClass" nowrap>Coapy(or)Deductible:</td>
			<td width="30%" class="textLabel tdClass">
			<table>
			<tr>
			<td nowrap>
			<html:text name="frmDynCompanRulesConfig"	property="ovrCopay" onkeyup="isNumeric(this);"  styleClass="textBox textBoxTiny" />%
			</td>			
			<td nowrap>
			(or)<html:text name="frmDynCompanRulesConfig"	property="ovrDeductible" onkeyup="isNumeric(this);"  styleClass="textBox textBoxTiny" />(AED)
			</td>
			<td>
			<html:select name="frmDynCompanRulesConfig"	property="ovrMinMaxFlag" style="height:20px;width:50px;" 	styleClass="selectBox selectBoxSmall">
							<html:option value="MIN">MIN</html:option>
							<html:option value="MAX">MAX</html:option>	
			</html:select>
			</td>
			<td nowrap>
			of<html:select name="frmDynCompanRulesConfig"	property="ovrApplOn" style="height:20px;" 	styleClass="selectBox selectBoxSmall">
							<html:option value="DA">Discounted Gross Amount</html:option>	
							<%-- <html:option value="RA">Requested Amount</html:option>	 --%>						
			</html:select>
			</td>
			</tr>
			</table>			
			</td>
			<td></td>
		<td></td>
		<td></td>
		<td></td>
		</tr>
		<tr>
				
			<td class="formLabel tdClass" nowrap>Age range:</td>
			<td width="30%" class="textLabel tdClass">
			<table>
			<tr>
			<td>From</td>
			<td>
			<html:text name="frmDynCompanRulesConfig"	property="ageRangeFrom" onkeyup="isNumeric(this);checkAgeRange();"  styleClass="textBox textBoxTiny" />			
			</td>
			<td>To</td>
			<td>
			
			<html:text name="frmDynCompanRulesConfig"	property="ageRangeTo" onkeyup="isNumeric(this);checkAgeRange();"  styleClass="textBox textBoxTiny" />			
			</td>
			<td>
			<html:select name="frmDynCompanRulesConfig"	property="followUpPeriodType" style="height:20px;width:50px;" 	styleClass="selectBox selectBoxSmall">
							<html:option value="DD">Days</html:option>
							<html:option value="MM">Months</html:option>
							<html:option value="YY">Years</html:option>							
			</html:select>
			</td>
			</table>
			</td>
		<td class="formLabel tdClass" nowrap>
		 NO. of days allowed per claim<span class="mandatorySymbol">*</span>
		</td>
		<td>
		<html:text name="frmDynCompanRulesConfig"	property="noOfdaysAllowPerCalim" onkeyup="isNumeric(this);"  styleClass="textBox textBoxTiny" />
		</td>
		<td class="formLabel tdClass" nowrap>
		 Max limit allowed per day
		</td>
		<td>
		<html:text name="frmDynCompanRulesConfig"	property="maxLimitAllowPerDay" onkeyup="isNumeric(this);"  styleClass="textBox textBoxTiny" />(AED)
		</td>
		</tr>
		<tr>
		<td class="formLabel tdClass" nowrap>
		Per-policy copay/Deductible limit:
		</td>
		<td>
		<html:text name="frmDynCompanRulesConfig"	property="perPolicyCpyDdctLimit" onkeyup="isNumeric(this);"  styleClass="textBox textBoxTiny" />(AED)
		</td>
		<td class="formLabel tdClass" nowrap>
		Per-claim copay/Deductible limit:
		</td>
		<td>
		<html:text name="frmDynCompanRulesConfig"	property="perClaimCpyDdctLimit" onkeyup="isNumeric(this);"  styleClass="textBox textBoxTiny" />(AED)
		</td>
		<td></td>
		<td></td>
		</tr>
		
		<tr>
		<td colspan="6">
			
			<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    <logic:notEqual value="V" name="OPConfType" scope="session">
				<button type="button" id="addConsDtlsBtnID" name="Button1" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:addCompanDtls();"><u>A</u>dd</button>&nbsp;
		</logic:notEqual>
		    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:window.self.close();"><u>C</u>lose</button>
		 
	    </td>
	  </tr>
	</table>
		</td>
		</tr>
		</table>
		
	<br>
			<ttk:DynCompanRuleConfDetails/>
		   <!-- S T A R T : Buttons -->
	
	</fieldset>
	

	<table  cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td valign="top" >
			
		</td>
	</tr>
	</table>


	<!-- E N D : Buttons -->
	
	<!-- E N D : Buttons -->
	
	<input type="hidden" name="child" value="">
	
	<input type="hidden" name="confType"/>
	<input type="hidden" name="currentSubLink" value="<%=TTKCommon.getActiveSubLink(request)%>"/>
	
	
	<input type="hidden" id="dynaTypeId" name="dynaTypeId"/>
	<input type="hidden" id="dynaParam" name="dynaParam"/>
	<input type="hidden"  name="mode"/>
	<input type="hidden"  name="rowNum"/>
	
	<input type="hidden" name="bnfRuleCompltYN" value="<bean:write name="frmDefineRules" property="benefitRuleCompletedYN"/>"/>
	<script type="text/javascript">  
 document.forms[0].setAttribute( "autocomplete", "off" );      
  </script>	
	</html:form>
	</div>
	</body>
	</html>
	<!-- E N D : Content/Form Area -->