<%
/** @ (#) carddetail.jsp Nov 04, 2006
 * Project     : TTK Healthcare Services
 * File        : carddetail.jsp
 * Author      : Krishna K H
 * Company     : Span Systems Corporation
 * Date Created: Nov 04, 2006
 *
 * @author 		 : Krishna K H
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
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/support/courierdetail.js"></script>
<%
  boolean viewmode=true;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }// end of if(TTKCommon.isAuthorized(request,"Edit"))
%>
<script>
function onClose()
{
	document.forms[1].mode.value = "doSearch";
	document.forms[1].child.value = "";
	document.forms[1].action="/CardPrintingAction.do";
	document.forms[1].submit();
}//end of onClose()
</script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/CardPrintingAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>Card Batch Detail</td>
        <td width="43%" align="right" class="webBoard">&nbsp;</td>
      </tr>
    </table>
	<!-- E N D : Page Title -->
  <!-- S T A R T : Form Fields -->
  <div class="contentArea" id="contentArea">
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
  <html:errors/>

<fieldset>
    <legend>Card Fields</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="21%" nowrap class="formLabel"><bean:write name="cardFields" filter="false"/></td>
        <td width="30%" class="textLabel" >&nbsp;</td>
        <td width="21%" nowrap class="formLabel">&nbsp;</td>
        <td width="28%" nowrap class="textLabel">&nbsp;</td>
      </tr>
    </table>
</fieldset>
<fieldset>
	<legend>Others</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="21%">Card Type:</td>
        <td width="30%" class="textLabelBold"><bean:write name="cardType"/></td>
        <td width="21%">&nbsp;</td>
    	<td width="28%">&nbsp;</td>
      </tr>
      <tr>
        <td>Card Template: </td>
        <td class="textLabelBold"><bean:write name="cardTemplate"/></td>
         <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
</fieldset>
	<!-- S T A R T :  Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
		   <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		</td>
	  </tr>
	 </table>
  <!-- E N D : Buttons -->
  <INPUT TYPE="hidden" NAME="mode" VALUE="">
  <INPUT TYPE="hidden" NAME="tab" VALUE="">
  <input type="hidden" name="child" value="">
  </div>
</html:form>
<!-- E N D : Content/Form Area -->