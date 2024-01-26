<%
/** @ (#) rulelist.jsp 4th Jul 2006
 * Project     : TTK Healthcare Services
 * File        : rulelist.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 4th Jul 2006
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
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true;
//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doViewRule";
    document.forms[1].rownum.value=rownum;
    document.forms[1].child.value="Define Rule";
    document.forms[1].action = "/RuleAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onDefineRule()
{
	document.forms[1].mode.value="doAdd";
	document.forms[1].child.value="Clause List";
	document.forms[1].action="/EditRuleAction.do";
	document.forms[1].submit();
}
</script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/RuleAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Product Rules - <bean:write name="frmRules" property="caption"/></td>
    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
  </tr>
</table>
<!-- E N D : Page Title -->
<div class="contentArea" id="contentArea">
<html:errors/>
<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData"/>
<!-- E N D : Grid -->
<!-- S T A R T : Buttons and Page Counter -->
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="27%">&nbsp;</td>
<td align="right" nowrap>
<%
	if(TTKCommon.isAuthorized(request,"Add"))
	{
%>
	<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDefineRule();"><u>D</u>efine Rule</button>
<%	} %>
</td>
</tr>
</table>
</div>
<!-- E N D : Buttons and Page Counter -->
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<input type="hidden" name="child" value="">
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->