<%
/**
 * @ (#) genralreferencedetail.jsp 26th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : genralreferencedetail.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : 26th Sep 2005
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@ page import="com.ttk.common.security.Cache" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/generalreferencedetail.js"></script>
<%
	pageContext.setAttribute("modReason",Cache.getCacheObject("modReason"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/EditHospitalSearchAction.do">
	<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="51%">Reference Details - <bean:write name="frmAddHospital" property="refdetcaption"/></td>
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

        	<html:select property ="hospitalAuditVO.modReson" styleClass="selectBox selectBoxMedium">
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
        	<html:text property="hospitalAuditVO.refDate" styleClass="textBox textDate" maxlength="10" /><A NAME="CalendarObjectRefDate" ID="CalendarObjectRefDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectRefDate','frmAddHospital.elements[\'hospitalAuditVO.refDate\']',frmAddHospital.elements['hospitalAuditVO.refDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
        </td>
        <td>Reference No.: </td>
        <td>
        	<html:text property="hospitalAuditVO.refNmbr" styleClass="textBox textBoxMedium" maxlength="60" />
        </td>
      </tr>
      <tr>
        <td valign="top" class="formLabel">Remarks: <span class="mandatorySymbol">*</span></td>
        <td colspan="3"><html:textarea property="hospitalAuditVO.remarks" styleClass="textBox textAreaLarge"/></td>
      </tr>
    </table>
    </fieldset>
    <!-- E N D : Form Fields -->
     <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
	    <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUserSubmit();"><u>S</u>ave</button>&nbsp;
		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td>
  </tr>
</table>
	<!-- E N D : Buttons -->
	</div>
<script>TC_PageDataChanged=true;</script>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>

