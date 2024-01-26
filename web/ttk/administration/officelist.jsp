<%
/** @ (#) officelist.jsp Nov 08th, 2005
 * Project       : TTK Healthcare Services
 * File          : officelist.jsp
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Nov 08th 2005
 * @author 		 : Bhaskar Sandra
 * Modified by   : Lancy A
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import="com.ttk.common.TTKCommon" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/officelist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/OfficeAction.do" >

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td ><bean:write name="frmOffice" property="caption"/></td>
	    <td  align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->

	<html:errors/>
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" nowrap>Company Code:<br>
        <input type="text" name="sOfficeCode" class="textBox textBoxMedium"  maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);" value="<bean:write name="frmOffice" property="sOfficeCode"/>">
        </td>
        <td valign="bottom" width="100%" nowrap align="left">
        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
      </tr>
    </table>
	<!-- E N D : Search Box -->

	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData" />
	<!-- E N D : Grid -->

	<table class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		<ttk:PageLinks name="tableData"/>
	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->