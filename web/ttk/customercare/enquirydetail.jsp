<%
/**
 * @ (#) enquirydetail.jsp 14th Nov 2006
 * Project      : TTK HealthCare Services
 * File         : enquirydetail.jsp
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : 14th Nov 2006
 *
 * @author       :	Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<script language="javascript">
function onClose()
{
	document.forms[1].child.value="CallCenterInfoList";
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/CallCenterInfoAction.do";
	document.forms[1].submit();
}//end of onClose()
</script>

<!-- S T A R T : Content/Form Area -->
	<html:form action="/EnquiryDetailAction">
	<logic:match name="frmCallCenterInfo" property="sType" value="HOS">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td> Hospital Details </td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
		<fieldset>
		<legend>Hospital Details</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td class="formLabel">Hospital Name:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="name"/></td>
	        <td class="formLabel">&nbsp;</td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td width="23%" class="formLabel">Address1:</td>
	        <td width="30%" class="textLabel"><bean:write name="frmEnquiryDetail" property="address1"/></td>
	        <td width="17%" class="formLabel">Address 2:</td>
	        <td width="30%" class="textLabel"><bean:write name="frmEnquiryDetail" property="address2"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">Address 3:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="address3"/></td>
	        <td class="formLabel">State:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="stateName"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">City:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="cityDesc"/></td>
	        <td class="formLabel">Pincode: </td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="pinCode"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">Country:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="countryName"/></td>
	        <td class="formLabel">STD Code:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="stdCode"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">Landmark, if any:</td>
	        <td colspan="3" class="textLabel"><bean:write name="frmEnquiryDetail" property="landmark"/></td>
	        </tr>
	      <tr>
	        <td class="formLabel">Office Phone1:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="officePhone1"/></td>
	        <td class="formLabel">Office Phone2:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="officePhone2"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">Fax:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="fax1"/></td>
	        <td class="formLabel">Email-ID:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="emailID"/></td>
	      </tr>
	    </table>
		</fieldset>
	</logic:match>
	<logic:match name="frmCallCenterInfo" property="sType" value="INS">
		<!-- S T A R T : Page Title -->
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td>Healthcare Details</td>
		    <td  align="right" class="webBoard">&nbsp;</td>
		  </tr>
		</table>
		<!-- E N D : Page Title -->
		<div class="contentArea" id="contentArea">
		<fieldset>
		<legend>Healthcare Details</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td class="formLabel">Healthcare Company:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="name"/></td>
	        <td class="formLabel">&nbsp;</td>
	        <td>&nbsp;</td>
	      </tr>
	      <tr>
	        <td width="20%" class="formLabel">Company Code:</td>
	        <td width="30%" class="textLabel"><bean:write name="frmEnquiryDetail" property="shortName"/></td>
	        <td width="20%" class="formLabel">Company Abbreviation:</td>
	        <td width="30%" class="textLabel"><bean:write name="frmEnquiryDetail" property="abbrCode"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">Address 1:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="address1"/></td>
	        <td class="formLabel">Address 2:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="address2"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">Address 3:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="address3"/></td>
	        <td class="formLabel">State:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="stateName"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">City:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="cityDesc"/></td>
	        <td class="formLabel">Pin Code:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="pinCode"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">Country:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="countryName"/></td>
	        <td class="formLabel">&nbsp;</td>
	        <td class="textLabel">&nbsp;</td>
	      </tr>
	    </table>
	    </fieldset>
	</logic:match>
	<logic:match name="frmCallCenterInfo" property="sType" value="TTK">
		<!-- S T A R T : Page Title -->
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td>Vidal Health Office  Details</td>
		  </tr>
		</table>
		<!-- E N D : Page Title -->
		<div class="contentArea" id="contentArea">
		<fieldset>
		<legend>Vidal Health Office  Details</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td class="formLabel">Office Type: </td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="officeType"/></td>
	        <td class="formLabel">Report To: </td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="reportTo"/></td>
	      </tr>
	      <tr>
	        <td width="20%" class="formLabel">Office Name:</td>
	        <td width="30%" class="textLabel"><bean:write name="frmEnquiryDetail" property="name"/></td>
	        <td width="20%" class="formLabel">Office Code:</td>
	        <td width="30%" class="textLabel"><bean:write name="frmEnquiryDetail" property="abbrCode"/></td>
	      </tr>
		  <tr>
	        <td width="20%" class="formLabel">Active:</td>
	        <td width="30%" class="textLabel"><bean:write name="frmEnquiryDetail" property="activeYN"/></td>
	        <td width="20%" class="formLabel">&nbsp;</td>
	        <td width="30%" class="textLabel">&nbsp;</td>
	      </tr>
	      <tr>
	        <td class="formLabel">Address 1:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="address1"/></td>
	        <td class="formLabel">Address 2:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="address2"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">Address 3:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="address3"/></td>
	        <td class="formLabel">State:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="stateName"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">City:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="cityDesc"/></td>
	        <td class="formLabel">Pin Code:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="pinCode"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">Country:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="countryName"/></td>
	        <td class="formLabel">STD Code: </td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="stdCode"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">Primary Email-Id: </td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="emailID"/></td>
	        <td class="formLabel">Alternate Email-Id: </td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="alternateEmailID"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">Office Phone 1: </td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="officePhone1"/></td>
	        <td class="formLabel">Office Phone 2: </td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="officePhone2"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">Fax:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="fax1"/></td>
	        <td class="formLabel">Alternate Fax: </td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="alternateFax"/></td>
	      </tr>
	      <tr>
	        <td class="formLabel">Toll Free No.:</td>
	        <td class="textLabel"><bean:write name="frmEnquiryDetail" property="tollFreeNbr"/></td>
	        <td class="formLabel">&nbsp;</td>
	        <td class="textLabel">&nbsp;</td>
	      </tr>
	    </table>
		</fieldset>
	</logic:match>
	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>
	</div>
	 <!-- E N D : Buttons and Page Counter -->
	 <input type="hidden" name="mode" value="">
 	 <input type="hidden" name="child" value="EnquiryDetails">

    </html:form>
	<!-- E N D : Content/Form Area -->