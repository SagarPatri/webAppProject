<%
/** @ (#) accountsmanagerlist.jsp April 9th 2008
 * Project    	 : TTK Healthcare Services
 * File       	 : accountsmanagerlist.jsp
 * Author     	 : Krupa J
 * Company    	 : Span Systems Corporation
 * Date Created	 : April 9th 2008
 * @author 		 : Krupa J
 * Modified by   :
 * Modified date : 
 * Reason        : 
 */
 %>
<%@ page import="com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	pageContext.setAttribute("userRolesList",Cache.getCacheObject("TTK"));
	pageContext.setAttribute("ttkBranchList",Cache.getCacheObject("officeInfo"));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/empanelment/accountsmanagerlist.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/AssociateAcntManagerAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	  <tr>
  	   <td>List of Users </td>
       <td width="43%" align="right" class="webBoard">&nbsp;</td>
  	 </tr>
	</table>
	<html:errors />
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Name:<br>
            <html:text property="sName" styleClass="textBox textBoxMedium" maxlength="60"/></td>
        <td valign="bottom" nowrap>Role:<br>
        	<html:select property="userRoles"  styleClass="selectBox selectBoxMedium">
  				<html:option value="">Any</html:option>
  				<html:options collection="userRolesList"  property="cacheId" labelProperty="cacheDesc"/>
		    </html:select>
		</td>
        <td valign="bottom" nowrap>Employee No.:<br>
            <html:text property="sEmpNo" styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
       
      	<td valign="bottom" nowrap>Vidal Health Branch:<br>
            <html:select property="officeInfo"  styleClass="selectBox selectBoxMedium">
  				<html:option value="">Any</html:option>
  				<html:options collection="ttkBranchList"  property="cacheId" labelProperty="cacheDesc"/>
		    </html:select>
        </td>
        
        <td valign="bottom" nowrap>
          	<a href="#" accesskey="s" onClick="javascript:onSearch()" class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
        <td>&nbsp;</td>
      </tr>
    </table>
	<!-- E N D : Search Box -->

	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData" />
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  	  <tr>
  		<td width="27%">&nbsp</td>
    	<td width="73%" nowrap align="right">

	    <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>
    	<ttk:PageLinks name="tableData"/>
	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->


	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<input type="hidden" name="child" value="">
	</html:form>
	<!-- E N D : Content/Form Area -->