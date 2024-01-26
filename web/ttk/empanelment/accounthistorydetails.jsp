<%
/**
 * @ (#) accounthistorydetails.jsp 28th Nov 2005
 * Project      : TTK HealthCare Services
 * File         : accounthistorydetails.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 28th Nov 2005
 *
 * @author       :Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<SCRIPT LANGUAGE="JavaScript">
//function OnClose
function onClose()
{
    document.forms[1].mode.value="doClose";
    document.forms[1].child.value="";
    document.forms[1].action = "/AccountsHistoryAction.do";
    document.forms[1].submit();
}//end of onClose()
</SCRIPT>
	<!-- S T A R T : Content/Form Area -->
	<html:form action="/EditAccountsHistoryAction.do" method="post" >
	<!-- S T A R T : Page Title -->
		<logic:empty name="frmAccountsHistoryDetail" property="emplSeqId" >
			<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="51%"> <bean:write name="frmAccountsHistoryDetail" property="caption"/></td>
				</tr>
			</table>
			<!-- E N D : Page Title -->
			<div class="contentArea" id="contentArea">
			<fieldset>
				<legend>Bank Guarantee Details</legend>
				<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
					<tr>
        				<td class="formLabel">Bank Guart. Enhancement: </td>
        				<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="bankGuantReqYN"/></td>
        				<td>&nbsp;</td>
        				<td class="formLabelBold">&nbsp;</td>
      				</tr>
      				<tr>
        				<td width="23%" class="formLabel">Bank Name:</td>
        				<td width="30%" class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="guaranteeBankName"/></td>
        				<td width="17%">Amount (Rs):</td>
        				<td width="30%" class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="amount"/></td>
      				</tr>
      				<tr>
        				<td class="formLabel">Commencement Date: </td>
        				<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="guaranteeCommDate"/></td>
        				<td class="formLabel">Expiry Date: </td>
        				<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="guaranteeExpiryDate"/></td>
      				</tr>
				</table>
			</fieldset>
		</logic:empty>

		<logic:notEmpty name="frmAccountsHistoryDetail" property="emplSeqId" >
			<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td><bean:write name="frmAccountsHistoryDetail" property="caption"/></td>
				</tr>
			</table>
			<!-- E N D : Page Title -->
			<div class="contentArea" id="contentArea">
			<fieldset>
				<legend>Empanelment Fee Details</legend>
				<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td class="formLabel">Empanel. Fee Charged: </td>
		        		<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="emplFeeChrgYn"/></td>
		        		<td class="formLabel">&nbsp;</td>
		        		<td>&nbsp;</td>
					</tr>
					<tr>
		        		<td width="23%" class="formLabel">Type:</td>
		        		<td width="30%" class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="payOrdDesc"/></td>
		        		<td width="17%" class="formLabel">Pay Order No.:</td>
		        		<td width="30%" class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="payOrdNmbr"/></td>
		      	   </tr>
		      	   <tr>
		        		<td class="formLabel">Amount :</td>
		        		<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="amount"/></td>
		        		<td class="formLabel">Received Date:</td>
		        		<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="endDate"/></td>
		      	  </tr>
		      	  <tr>
		        		<td class="formLabel">Bank Name:</td>
		        		<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="bankName"/></td>
		        		<td class="formLabel">Issued Date: </td>
		        		<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="startDate"/></td>
		      	  </tr>
		      	  <tr>
		        		<td class="formLabel">Address 1:</td>
		        		<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="addressVO.address1"/></td>
		        		<td class="formLabel">Address 2:</td>
		        		<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="addressVO.address2"/></td>
		      	  </tr>
		      	  <tr>
		        		<td class="formLabel">Address 3:</td>
		        		<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="addressVO.address3"/></td>
		        		<td class="formLabel">State:</td>
		        		<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="addressVO.stateName"/></td>
		      	  </tr>
		      	  <tr>
		        		<td class="formLabel">City:</td>
		        		<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="addressVO.cityDesc"/></td>
		        		<td class="formLabel">Pincode:</td>
		        		<td class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="addressVO.pinCode"/></td>
		      	  </tr>
		      	  <tr>
		        		<td class="formLabel">Country:</td>
		        		<td colspan="3" class="textLabel"><bean:write name="frmAccountsHistoryDetail" property="addressVO.countryName"/></td>
		      	  </tr>
				</table>
			</fieldset>
		</logic:notEmpty>
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
    <!-- E N D : Buttons -->
    <INPUT TYPE="hidden" NAME="mode" VALUE="">
    <input type="hidden" name="child" value="EditHistory">
    <INPUT TYPE="hidden" NAME="emplSeqId" VALUE="<bean:write name="frmAccountsHistoryDetail" property="emplSeqId" />">
    <INPUT TYPE="hidden" NAME="bankGuantSeqId" VALUE="<bean:write name="frmAccountsHistoryDetail" property="bankGuantSeqId" />">
    </html:form>
	<!-- E N D : Content/Form Area -->
