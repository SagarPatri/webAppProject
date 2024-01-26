<%
/** @ (#) claimreqamtchange.jsp
 * Project     : TTK Healthcare Services
 * File        : claimreqamtchange.jsp
 * Author      : Balakrishan Erram
 * Company     : Span Systems Corporation
 * Date Created: 12th Aug 2010
 *
 * @author 		 :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
 
 <%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/maintenance/claimreqamtchange.js"></script>
 
<!-- S T A R T : Content/Form Area -->
	<html:form action="/ClmReqAmtChngDtlsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Request Amt. Change - [ <bean:write property="claimNbr" name="frmClaimReqAmtDetails"/> ] </td>
  </tr>
</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
<div class="contentArea" id="contentArea">
<html:errors/>
<logic:notEmpty name="updated" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="updated" scope="request"/>
	     </td>
	  </tr>
	 </table>
</logic:notEmpty>
	<fieldset>
	<legend>Claim Details</legend>
<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td width="22%" height="20" class="formLabel">Claim No.:</td>
    <td width="30%" class="formLabelBold">
    <bean:write property="claimNbr" name="frmClaimReqAmtDetails"/>
    </td>
    <td width="20%" class="formLabel">Enrollment Id:</td>
    <td width="28%" class="textLabel">
    <bean:write property="enrollmentID" name="frmClaimReqAmtDetails"/>
    </td>
  </tr>
  <tr>
   <td class="formLabel">Scheme No.:</td>
   <td class="textLabel">
    <bean:write property="policyNbr" name="frmClaimReqAmtDetails"/>
    </td>
    <td class="formLabel">Requested Amt. (Rs):</td>
    <td class="textLabel">
    <bean:write property="preClmReqAmt" name="frmClaimReqAmtDetails"/>
    </td>
  </tr>
  <tr>
        <td height="20" nowrap class="formLabel">Member Name:</td>
        <td class="textLabel">
        <bean:write property="claimantName" name="frmClaimReqAmtDetails"/>
        </td>
        </tr>
</table>
</fieldset>
	<fieldset><legend>New Details</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	<logic:empty name="flag" scope="request">
 
  <tr>
    <td width="22%" nowrap class="formLabel">New Requested Amt. (Rs): <span class="mandatorySymbol">*</span></td>
    <td width="78%" nowrap class="textLabel">
    <html:text property="newClmReqAmt" styleClass="textBox textBoxMedium" /></td>
	</tr>
  <tr>
        <td width="22%" nowrap class="formLabel">Remarks: <span class="mandatorySymbol">*</span></td>
        <td width="78%" nowrap class="textLabel"><html:textarea property="remarks" styleClass="textBox textAreaLongHt" /></td>
        </tr>
  </logic:empty>
  
  <logic:notEmpty name="flag" scope="request">
  
  <tr>
    <td width="22%" nowrap class="formLabel">New Requested Amt. (Rs): <span class="mandatorySymbol">*</span></td>
    <td width="78%" nowrap class="textLabel">
    <html:text property="newClmReqAmt" styleClass="textBox textBoxMedium" disabled="true" /></td>
	</tr>
  <tr>
        <td width="22%" nowrap class="formLabel">Remarks: <span class="mandatorySymbol">*</span></td>
        <td width="78%" nowrap class="textLabel"><html:textarea property="remarks" styleClass="textBox textAreaLongHt" disabled="true" /></td>
        </tr>
  
  </logic:notEmpty>
  
</table>
</fieldset>

	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	    <td width="100%" align="center">
	     <logic:empty name="flag" scope="request">
	    <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
	    <button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	    </logic:empty>
	    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
	    </tr>
	</table>
</div>
<!-- E N D : Buttons -->
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<html:hidden property="claimSeqID"/>
<INPUT TYPE="hidden" NAME="claimNumber" VALUE='<bean:write property="claimNbr" name="frmClaimReqAmtDetails"/>'>
</html:form>