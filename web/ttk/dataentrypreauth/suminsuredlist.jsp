<%
/** @ (#) suminsuredlist.jsp May 12, 2006
 * Project     : TTK Healthcare Services
 * File        : suminsuredlist.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: May 12, 2006
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.PreAuthWebBoardHelper"%>
<SCRIPT LANGUAGE="JavaScript">
function onClose()
{
	document.forms[1].mode.value ="doClose";
	document.forms[1].child.value="";
    document.forms[1].action = "/SumInsuredEnhanceAction.do";
    document.forms[1].submit();
}//end of onClose()
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/SumInsuredEnhanceAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
		    <td><bean:write name="frmSumInsuredList" property="caption"/></td>
	  	</tr>
	</table>
	<!-- E N D : Page Title -->
    <!-- S T A R T : Grid -->
    	<ttk:HtmlGrid name="tableDataSumInsured"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	    <tr>
	    	<td width="27%"></td>
		    <td width="73%" align="right">
		    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		    </td>
	    </tr>
	</table>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="SumInsuredList">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->