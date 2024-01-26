<%
/** @ (#) notificationlist.jsp 28th Jul 2008
 * Project     		: TTK Healthcare Services
 * File        		: notificationlist.jsp
 * Author      		: Sendhil Kumar V
 * Company     		: Span Systems Corporation
 * Date Created		: 28th Jul 2008
 *
 * @author 				Sendhil Kumar V 
 * Modified by   	:
 * Modified date 	:
 * Reason        	:
 *
 */
 %>
 
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<script language="javascript" src="/ttk/scripts/maintenance/notificationlist.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/NotificationListAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>List of Notifications</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Grid -->
	    <ttk:HtmlGrid name="tableData" />
    <!-- E N D : Grid -->
    <!-- S T A R T : Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		<ttk:PageLinks name="tableData"/>
	</table>	
</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	</html:form>
	<!-- E N D : Content/Form Area -->
 