<%
/** @ (#) ruleverification.jsp 12th Aug 2006
 * Project     : TTK Healthcare Services
 * File        : ruleverification.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 12th Aug 2006
 *
 * @author 	   : Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.TTKCommon" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<script language="javascript">
function onClose()
{
	document.forms[1].mode.value="CloseVerifyRule";
	document.forms[1].action="/RuleVerification.do";
	document.forms[1].submit();
}//end of function onClose()
</script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/RuleVerification.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td >Rule verification screen - <bean:write name="frmDefineRules" property="caption"/></td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
		<ttk:RuleVerification/>
	<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		</td>
	  </tr>
	</table>
	</div>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>