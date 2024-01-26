<%
/** @ (#) policydetail.jsp Jul 28, 2007
 * Project     : Vidal Health TPA Services
 * File        : policydetail.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: Jul 28, 2007
 *
 * @author 		 : Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<script language="JavaScript">
function onViewEndorsements()
{
	document.forms[1].mode.value="doViewEndorsements";
	document.forms[1].action="/AccInfoEndorseAction.do";
	document.forms[1].submit();
}//end of onViewEndorsements()
</script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/PolicyAccountInfoDetailAction.do" >

<!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>Policy Details</td>
        <td align="right" class="webBoard">
        	&nbsp;
        	<logic:notEqual name="frmPolicyAccountInfoDetail" property="endorsementCnt" value="0">
        	<a href="#" onClick="javascript:onViewEndorsements()"><img src="ttk/images/HistoryIcon.gif" alt="Endorsement List" width="16" height="16" border="0" align="absmiddle" class="icons"></a>&nbsp;&nbsp;<img src="ttk/images/IconSeparator.gif" width="1" height="15" align="absmiddle" class="icons">&nbsp;
        	</logic:notEqual>
        	<%@ include file="/ttk/common/toolbar.jsp" %>
       	</td>
    </tr>
  </table>
<!-- E N D : Page Title -->


<!-- S T A R T : Form Fields -->
<div class="contentArea" id="contentArea">
<fieldset>
<legend>Policy Information</legend>
<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">

	<tr>
	      <td width="24%" height="25" class="formLabel">Policy No.:</td>
	      <td width="33%" class="textLabelBold"><bean:write name="frmPolicyAccountInfoDetail" property="policyNbr" /></td>
	      <td width="23%" class="formLabel">Product / Policy Name: </td>
	      <td width="20%" class="textLabelBold"><bean:write name="frmPolicyAccountInfoDetail" property="productName" /></td>
	</tr>
	<logic:equal name="frmPolicyAccountInfoDetail" property="policyTypeID" value="IND">
	<tr>
	      <td height="25" class="formLabel">Policy Holder's Name: </td>
	      <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="memberName" /></td>
	      <td colspan="2"></td>
	</tr>
	</logic:equal>
	<tr>
	      <td height="25" class="formLabel">Healthcare Company:</td>
	      <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="companyName" /></td>
	      <td class="formLabel">Company Code:</td>
	      <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="companyCode" /></td>
    </tr>
    <logic:notEqual name="frmPolicyAccountInfoDetail" property="policyTypeID" value="IND">
	<tr>
	      <td height="25" class="formLabel">Group Name:</td>
	      <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="groupName" /></td>
	      <td class="formLabel">Group Id:</td>
	      <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="groupID" /></td>
    </tr>
    </logic:notEqual>
	<tr>
	  <td height="25" class="formLabel">Policy Type: </td>
	  <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="policyTypeDesc" /></td>
	  <td class="formLabel">Policy Sub Type: </td>
	  <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="policySubType" /></td>
	</tr>
	<tr>
	  <td height="25" class="formLabel">Policy Start Date:</td>
	  <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="startDate" /></td>
	  <td class="formLabel">Policy End Date: </td>
	  <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="endDate" /></td>
	</tr>
	<tr>
	  <td height="25" class="formLabel">Proposal Date:</td>
	  <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="proposalDate" /></td>	  
	</tr>
	<tr>
	  <td height="25" class="formLabel">Total Sum Insured (AED): </td>
	  <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="totalSumInsured" /></td>
	  <td class="formLabel">Term Status: </td>
	  <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="TPAStatusType" /></td>
	</tr>
	<logic:equal name="frmPolicyAccountInfoDetail" property="policyTypeID" value="IND">
	<tr>
	  <td height="25" class="formLabel">Home Phone: </td>
	  <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="homePhoneNbr" /></td>
	  <td class="formLabel">Mobile:</td>
	  <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="mobileNbr" /></td>
	</tr>
	</logic:equal>
	<logic:equal name="frmPolicyAccountInfoDetail" property="policyTypeID" value="ING">
	<tr>
	  <td height="25" class="formLabel">Home Phone: </td>
	  <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="homePhoneNbr" /></td>
	  <td class="formLabel">Mobile:</td>
	  <td class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="mobileNbr" /></td>
	</tr>
	</logic:equal>
	<tr>
	  <td height="25" class="formLabel">Zone Code:</td>
	  <td colspan="3" class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="zoneCode" /></td>
	<tr>
	<tr>
	  <td height="25" class="formLabel">Remarks:</td>
	  <td colspan="3" class="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="remarks" /></td>
	<tr>
</table>
	</fieldset>
	<fieldset><legend>Beneficiary Address Information</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="24%" height="25"  CLASS="formLabel">Address 1:</td>
			<td width="33%" CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="familyAddressVO.address1" /></td>
			<td width="23%" height="25"  CLASS="formLabel">Address 2:</td>
			<td width="20%" CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="familyAddressVO.address2" /></td>
		</tr>
		<tr>
			<td height="25" CLASS="formLabel">Address 3:</td>
			<td CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="familyAddressVO.address3" /></td>
			<td height="25" CLASS="formLabel">Emirate/State:</td>
			<td CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="familyAddressVO.stateName" /></td>
		</tr>
		<tr>
			<td height="25" CLASS="formLabel">Area:</td>
			<td CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="familyAddressVO.cityDesc" /></td>

			<td height="25" CLASS="formLabel">Pincode:</td>
			<td CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="familyAddressVO.pinCode" /></td>
		</tr>
		</table>
	</fieldset>
	<fieldset><legend>Healthcare Company Address</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="24%" height="25" CLASS="formLabel">Address 1:</td>
			<td width="33%" CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="insuranceAddressVO.address1" /></td>
			<td width="23%" height="25" CLASS="formLabel">Address 2:</td>
			<td width="20%" CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="insuranceAddressVO.address2" /></td>
		</tr>
		<tr>
			<td height="25" CLASS="formLabel">Address 3:</td>
			<td CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="insuranceAddressVO.address3" /></td>
			<td height="25" CLASS="formLabel">State:</td>
			<td CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="insuranceAddressVO.stateName" /></td>
		</tr>
		<tr>
			<td height="25" CLASS="formLabel">City:</td>
			<td CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="insuranceAddressVO.cityDesc" /></td>

			<td height="25" CLASS="formLabel">Pincode:</td>
			<td CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="insuranceAddressVO.pinCode" /></td>
		</tr>
	</table>
	</fieldset>
	<logic:notEqual name="frmPolicyAccountInfoDetail" property="policyTypeID" value="IND">
	<fieldset><legend>Group Address Information</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="24%" height="25" CLASS="formLabel">Address 1:</td>
			<td width="33%" CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="groupAddressVO.address1" /></td>
			<td width="23%" height="25" CLASS="formLabel">Address 2:</td>
			<td width="20%" CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="groupAddressVO.address2" /></td>
		</tr>
		<tr>
			<td height="25" CLASS="formLabel">Address 3:</td>
			<td CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="groupAddressVO.address3" /></td>
			<td height="25" CLASS="formLabel">State:</td>
			<td CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="groupAddressVO.stateName" /></td>
		</tr>
		<tr>
			<td height="25" CLASS="formLabel">City:</td>
			<td CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="groupAddressVO.cityDesc" /></td>

			<td height="25" CLASS="formLabel">Pincode:</td>
			<td CLASS="textLabel"><bean:write name="frmPolicyAccountInfoDetail" property="groupAddressVO.pinCode" /></td>
		</tr>
		</table>
	</fieldset>
	</logic:notEqual>
	<table>
		<tr>
			<td></td>
		</tr>
	</table>
	</div>
	
<!-- E N D : Form Fields -->
<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>