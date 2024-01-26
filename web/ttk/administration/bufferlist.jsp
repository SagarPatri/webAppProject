<%
/**
 * @ (#) bufferlist.jsp 19th Jun 2006
 * Project      : Vidal Health TPA Services
 * File         : bufferlist.jsp
 * Author       : Pradeep R
 * Company      : Span Systems Corporation
 * Date Created : 19th Jun 2006
 *
 * @author       :Pradeep R
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon" %>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/bufferlist.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/BufferAction" >
<!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td>List of Buffer Amount </td>
      <td align="right">
				<a href="#" onClick="onLevelConfiguration()">
					<img src="/ttk/images/EditIcon.gif" alt="Configuration List" width="16"	height="16" border="0" align="absmiddle">
				</a>
			</td>
      <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
    </tr>
  </table>
<!-- E N D : Page Title -->

<div class="contentArea" id="contentArea"><br>
<html:errors/>


<!-- S T A R T : Grid -->
  <ttk:HtmlGrid name="tableData"/>
<!-- E N D : Grid -->

 <!-- S T A R T : Buttons -->
<table  align="right" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td> </td>
    <td nowrap align="right">
      <span class="fieldGroupHeader">Total Buffer Amt. (OMR): <bean:write name="frmBufferAmount" property="totAmount" />
      </span>&nbsp;
      <%
      if(TTKCommon.isAuthorized(request,"Add"))
      {
      %>
        <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd();"><u>A</u>dd</button>
      <%
       }
      %>
   </td>
  </tr>
  <ttk:PageLinks name="tableData"/>
</table>
</div>
  <input type="hidden" name="child" value="">
  <INPUT TYPE="hidden" NAME="rownum" VALUE="">
  <INPUT TYPE="hidden" NAME="mode" VALUE="">
  <INPUT TYPE="hidden" NAME="sortId" VALUE="">
  <INPUT TYPE="hidden" NAME="pageId" VALUE="">
</html:form>
<!-- E N D : Buttons -->



