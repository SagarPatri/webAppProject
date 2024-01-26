<%
/**
 * @ (#) billdetails.jsp 21th july 2006
 * Project      : TTK HealthCare Services
 * File         : billdetails.jsp
 * Author       : Arun K.M
 * Company      : Span Systems Corporation
 * Date Created : 21th july 2006
 *
 * @author       : Arun K.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/claims/billdetails.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>

<SCRIPT LANGUAGE="JavaScript">
bAction = false;
var TC_Disabled = true;
</script>

<html:form action="/AddEditBillDetailsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Bill Details - <bean:write name="frmClaimBillDetails" property="caption"/></td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
    <!-- S T A R T : Form Fields -->
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
	<!-- E N D : Success Box -->
 	<fieldset>

	<legend>Bill Details</legend>
	<table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
       <tr>
         <td width="19%" nowrap class="formLabel">Bill No: <span class="mandatorySymbol">*</span></td>
         <td width="31%" nowrap class="formLabel"><html:text property="billNbr" styleClass="textBox textBoxMedium" maxlength="10"/></td>
         <td width="19%" nowrap class="formLabel">&nbsp;</td>
         <td width="31%" nowrap class="formLabel">&nbsp;</td>
       </tr>
       <tr>
         <td nowrap class="formLabel">Bill Date: <span class="mandatorySymbol">*</span></td>
         <td nowrap class="formLabel">
	         <html:text property="billDate" styleClass="textBox textDate" maxlength="60"/>&nbsp;<a name="CalendarObjectClDate" id="CalendarObjectClDate" href="#" onClick="javascript:show_calendar('CalendarObjectClDate','forms[1].billDate',document.forms[1].billDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a>
         </td>
         <td nowrap class="formLabel">Bill Type: </td>
         <td nowrap class="textLabelBold"><bean:write name="frmClaimBillDetails" property="billTypeDesc"/></td>
       </tr>
       <tr>
         <td nowrap class="formLabel">Bill Issued By: </td>
         <td nowrap class="formLabel"><html:text property="billIssuedBy" styleClass="textBox textBoxMedium" maxlength="60"/></td>
         <td nowrap class="formLabel">Bills with Prescription:</td>
         <td nowrap class="formLabel"><html:checkbox  name="frmClaimBillDetails" property="billWithPrescription" value="Y"/>
         </td>
       </tr>

       <tr>
         <td nowrap class="formLabel"> Requested Amt. (Rs): </td>
         <td nowrap class="textLabelBold"><bean:write name="frmClaimBillDetails" property="lineItemVO.requestedAmt"/></td>
         <td nowrap class="formLabel">Allowed Amt. (Rs):</td>
         <td nowrap class="textLabelBold"><bean:write name="frmClaimBillDetails" property="lineItemVO.allowedAmt"/></td>
       </tr>
       <tr>
         <td nowrap class="formLabel">Disallowed Amt. (Rs):</td>
         <td nowrap class="textLabelBold"><bean:write name="frmClaimBillDetails" property="lineItemVO.disAllowedAmt"/></td>
         <td nowrap class="formLabel">Bill Included: </td>
         <td nowrap class="formLabel">
        	<html:checkbox property="billIncludedYN" value="Y" disabled="true"/>
         </td>
       </tr>
    </table>
	</fieldset>

	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    <logic:notMatch name="frmClaimBillDetails" property="ammendmentYN" value="Y">
		    <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
		    <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
		</logic:notMatch>
		    <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>
	<!-- E N D : Buttons -->
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" name="billWithPrescription" value=""/>
	<html:hidden property="ammendmentYN"/>
	<input type="hidden" name="child" value="Bill Details">
	</html:form>