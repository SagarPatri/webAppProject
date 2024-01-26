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
<script type="text/javascript" src="/ttk/scripts/administration/newRuleList.js"></script>

<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true;
//function to call edit screen

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
<td width="27%">

&nbsp;

</td>
<td align="right" nowrap>
<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" style="cursor: pointer;" onClick="javascript:onClose();"><u>C</u>lose</button>
&nbsp;
<%
	if(TTKCommon.isAuthorized(request,"Add"))
	{
%>
	<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" style="cursor: pointer;" onClick="javascript:onDefineRule();">Define <u>R</u>ule</button>
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