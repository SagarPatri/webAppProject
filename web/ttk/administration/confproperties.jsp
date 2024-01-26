<%
/** @ (#) confproperties.jsp 21th Mar 2006
 * Project     : TTK Healthcare Services
 * File        : confproperties.jsp
 * Author      : Pradeep R
 * Company     : Span Systems Corporation
 * Date Created: 21th Mar 2006
 *
 * @author 		 : Pradeep R
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/confproperties.js"></script>
<%
  boolean viewmode=true;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }//end of if(TTKCommon.isAuthorized(request,"Edit"))
  pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ConfProperties.do" >
<!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td>Configure Properties - <bean:write name="frmConfProperties" property="caption" /></td>
    </tr>
  </table>
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
 <!-- E N D : Success Box -->
<!-- S T A R T : Form Fields -->

<fieldset>
  <legend>Cashless Details</legend>
  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="30%" class="formLabel">Cashless Processing:</td>
        <td width="35%">
          <html:checkbox onclick="SetState(this,this.form.paLimit)" property="paAllowedYN" value="Y" disabled="<%=viewmode%>" /></td>
        <td width="18%">&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
         <td class="formLabel">Cashless Processing Approval Limit (Rs):</td>
          <td>
            <logic:match name="frmConfProperties" property="paAllowedYN" value="Y">
               <html:text name="frmConfProperties" property="paLimit" maxlength="13" styleClass="textBox textBoxSmall" disabled="<%=viewmode%>" />
           </logic:match>
           <logic:notMatch name="frmConfProperties" property="paAllowedYN" value="Y">
               <html:text name="frmConfProperties" property="paLimit" maxlength="13" styleClass="textBox textBoxSmallDisabled" readonly="true" />
           </logic:notMatch>
     </td>
        <td class="formLabel">&nbsp;</td>
        <td>&nbsp;</td>
   </tr>
   </table>
</fieldset>

<fieldset>
<legend>Claim Processing Details</legend>
  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="30%" class="formLabel">Claim Processing: </td>
        <td width="35%">
          <html:checkbox onclick="SetState(this,this.form.claimLimit)" property="claimAllowedYN" value="Y" disabled="<%=viewmode%>" /></td>
        <td width="18%">&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
    <tr>
         <td class="formLabel">Claim Processing Approval Limit (Rs):</td>
          <td>
            <logic:match name="frmConfProperties" property="claimAllowedYN" value="Y">
              <html:text name="frmConfProperties" property="claimLimit" maxlength="13" styleClass="textBox textBoxSmall" disabled="<%=viewmode%>" />
            </logic:match>
            <logic:notMatch name="frmConfProperties" property="claimAllowedYN" value="Y">
              <html:text name="frmConfProperties" property="claimLimit" maxlength="13" styleClass="textBox textBoxSmallDisabled" readonly="true" />
            </logic:notMatch>
          </td>
          <td class="formLabel">&nbsp;</td>
          <td>&nbsp;</td>
   </tr>
</table>
</fieldset>

<fieldset>
<legend>Card Printing Details</legend>
  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="30%" class="formLabel">Card Printing: </td>
        <td width="35%">
          <html:checkbox property="cardPrintAllowedYN" value="Y" disabled="<%=viewmode%>"  /></td>
        <td width="18%">&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
</table>
</fieldset>
<fieldset><legend>Enrollment Processing Details</legend>
  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="30%" class="formLabel">Enrollment Processing: </td>
        <td width="35%">
          <html:checkbox property="enrolProcessYN" value="Y" disabled="<%=viewmode%>"  /></td>
        <td width="18%">&nbsp;</td>
        <td>&nbsp;</td>
    </tr>
</table>
</fieldset>
  <!-- E N D : Form Fields -->
  <!-- S T A R T :  Buttons -->
  <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
      <logic:match name="viewmode" value="false">
        <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
      </logic:match>
      <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td>
  </tr>
</table>
<!-- E N D : Buttons and Page Counter -->
<!-- E N D : Content/Form Area -->

<!-- E N D : Main Container Table -->
<input type="hidden" name="child" value="Configure Properties">	
<html:hidden property="officeSequenceID" />
<html:hidden property="selectedRoot" />
<html:hidden property="selectedNode" />
<html:hidden property="caption" />
<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>