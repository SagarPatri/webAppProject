<%
/** @ (#) clauselist.jsp Jun 29th, 2006
 * Project       : TTK Healthcare Services
 * File          : clauselist.jsp
 * Author        : Unni V Mana
 * Company       : Span Systems Corporation
 * Date Created  : 29th Jun 2006
 * @author 		 : Unni V Mana
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>

<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<SCRIPT LANGUAGE="JavaScript" src="/ttk/scripts/administration/clauselist.js"></SCRIPT>

<!-- S T A R T : Content/Form Area -->
<!-- S T A R T : Page Title -->
	<html:form action="/ClauseListAction.do">
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
	<td >Select applicable Clauses - <bean:write name="frmDefineRules" property="caption"/></td>
	</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<!-- E N D : Success Box -->
	<fieldset>
	<legend>Select applicable Clauses </legend>
		<ttk:ClauseList/>
    </fieldset>
	<!-- E N D : Form Fields -->
	<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  	<tr>
 	<td width="100%" align="center">
 		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onContinue();"><u>C</u>ontinue</button>&nbsp;
 		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>
 	</td>
 	</tr>
	</table>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="Clause List">
	<script> ClientReset=false;TC_PageDataChanged=true;</script> <!-- to give warning to user if he trying to change tab -->
	<!-- E N D : Content/Form Area -->
	</html:form>
