<%
/**
 * @ (#) financeaccountReviewreferencedetail.jsp.jsp 29th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : financeaccountReviewreferencedetail.jsp.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : 29th Sep 2005
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="com.ttk.common.security.Cache" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/financeaccountreferencedetail.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<%
	pageContext.setAttribute("modReason",Cache.getCacheObject("modReason"));
 %>
<!-- S T A R T : Content/Form Area -->
<html:form action="/CustomerBankDetailsAccountList.do" >
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="51%">Reference Details</td>
     <td width="49%" align="right" class="webBoard">&nbsp;</td>
  </tr>
</table>

	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<fieldset>
    <legend>General</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="17%" class="formLabel">Modified Reason: <span class="mandatorySymbol">*</span></td>
        <td width="32%">
        	<html:select property ="modReson" styleClass="selectBox selectBoxMedium">
                 <html:option value="">Select from list</html:option>
                 <html:options collection="modReason" property="cacheId" labelProperty="cacheDesc"/>
          	</html:select>
	    </td>
        <td width="16%">&nbsp; </td>
        <td width="35%">&nbsp;</td>
      </tr>
      <tr>
        <td class="formLabel">Reference Date: <span class="mandatorySymbol">*</span></td>
        <td>
        	<html:text property="refDate" styleClass="textBox textDate" maxlength="10" /><A NAME="CalendarObjectRefDate" ID="CalendarObjectRefDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectRefDate','frmCustomerBankAccountList.elements[\'refDate\']',document.frmCustomerBankAccountList.elements['refDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
        </td>
        <td>Reference No.: </td>
        <td>
        	<html:text property="refNmbr" styleClass="textBox textBoxMedium" maxlength="60" />
        </td>
      </tr>
      <tr>
        <td valign="top" class="formLabel">Remarks: <span class="mandatorySymbol">*</span></td>
        <td colspan="3"><html:textarea property="remarks" styleClass="textBox textAreaLarge"/></td>
      </tr>
    </table>
    </fieldset>
    <!-- E N D : Form Fields -->
     <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
	    <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUserReviewSubmit();"><u>S</u>ave</button>&nbsp;
	    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseReview();"><u>C</u>lose</button>
	</td>
  </tr>
</table>
	<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<html:hidden property="switchHospOrPtr"/>
</html:form>