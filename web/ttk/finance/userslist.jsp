<%
/** @ (#) userslist.jsp Jun 10, 2006
 * Project    	 : TTK Healthcare Services
 * File       	 : userslist.jsp
 * Author     	 : Raghavendra T M
 * Company    	 : Span Systems Corporation
 * Date Created	 : Jun 10, 2006
 * @author 		 : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import=" com.ttk.common.TTKCommon" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/userslist.js"></script>
<script>var TC_Disabled = true;</script>
<%
	String strLink=TTKCommon.getActiveLink(request);
	pageContext.setAttribute("strLink",strLink);
%>
	<!-- S T A R T : Content/Form Area -->
	<html:form action="/UsersListAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>List of Users  <bean:write name="frmUsersList" property="caption"/></td>
  </tr>
</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="15%" nowrap>User Name:<br>
        	<html:text property="UserName" styleClass="textBox textBoxMedium" maxlength="60"/>
           </td>
        <td width="15%" nowrap>User ID:<br>
        <html:text property="UserID" styleClass="textBox textBoxMedium" maxlength="60"/>	          </td>
        <td valign="bottom" width="70%" nowrap><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
      </tr>
     </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableDataListOfUsers"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="27%"><a href="#"></td>
        <td width="73%" align="right">
        <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>
		</td>
		</tr>
   <ttk:PageLinks name="tableDataListOfUsers" />
    </table>
    </div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="child" value="Users List">
<!-- E N D : Main Container Table -->
</html:form>