<%
/** @ (#) associateuserlist.jsp Dec 27, 2005
 * Project    	 : TTK Healthcare Services
 * File       	 : associateuserlist.jsp
 * Author     	 : Bhaskar Sandra
 * Company    	 : Span Systems Corporation
 * Date Created	 : Dec 27, 2005
 * @author 		 : Bhaskar Sandra
 * Modified by   : Arun K N
 * Modified date : Mar 21, 2006
 * Reason        : for modifying option in select box from --Select-- to Any
 */
 %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	pageContext.setAttribute("userRolesList",Cache.getCacheObject("TTK"));
	pageContext.setAttribute("ttkBranchList",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("userTypeList",Cache.getCacheObject("associateUsers"));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/usermanagement/associateuserlist.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/AssociateUserAction.do"  method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	  <tr>
  	    <logic:equal name="frmAssociateUser" property="associateUsers" value="UGA">
    		<td>List of Associated Users - [<bean:write name="frmAssociateUser" property="grpName"/>]</td>
        </logic:equal>
  	    <logic:equal name="frmAssociateUser" property="associateUsers" value="UGU">
    		<td>List of Un-Associated Users - [<bean:write name="frmAssociateUser" property="grpName"/>]</td>
    	</logic:equal>
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
            <html:text property="sEmpNo" styleClass="textBox textBoxMedium" maxlength="60"/></td>
        <td width="100%">&nbsp;</td>
      </tr>
      <tr>
        <td valign="bottom" nowrap>Vidal Health Branch:<br>
            <html:select property="officeInfo"  styleClass="selectBox selectBoxMedium">
  				<html:option value="">Any</html:option>
  				<html:options collection="ttkBranchList"  property="cacheId" labelProperty="cacheDesc"/>
		    </html:select>
        </td>
        <td nowrap>Search In:<br>
			<html:select property="associateUsers"  styleClass="selectBox selectBoxMedium">
  				<html:options collection="userTypeList"  property="cacheId" labelProperty="cacheDesc"/>
		    </html:select>
		</td>
        <td valign="bottom" nowrap>
          	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
        <td>&nbsp;</td>
      </tr>
    </table>
	<!-- E N D : Search Box -->

	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="associateUserData" />
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  	  <tr>
  		<td width="27%">&nbsp</td>
    	<td width="73%" nowrap align="right">

	    <% if(TTKCommon.isDataFound(request,"associateUserData")) {
	    if(TTKCommon.isAuthorized(request,"Delete")) {%>
	    <logic:equal name="frmAssociateUser" property="associateUsers" value="UGA">
	    	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onRemove('<bean:write name="frmAssociateUser" property="sEmpNo" />','<bean:write name="frmAssociateUser" property="sName" />')"><u>R</u>emove</button>&nbsp;
	    </logic:equal>
	    <%}//end of if(TTKCommon.isAuthorized(request,"Delete"))
	     if(TTKCommon.isAuthorized(request,"Edit")) {%>
	    <logic:equal name="frmAssociateUser" property="associateUsers" value="UGU">
		    <button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAssociate('<bean:write name="frmAssociateUser" property="sEmpNo" />','<bean:write name="frmAssociateUser" property="sName" />')"><u>A</u>ssociate</button>&nbsp;
	    </logic:equal>
	    <%}//end of if(TTKCommon.isAuthorized(request,"Edit"))}
	     } //end of if(TTKCommon.isDataFound(request,"associateUserData"))%>
    	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>
    	<ttk:PageLinks name="associateUserData"/>
	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->


	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<input type="hidden" name="child" value="Associated Users">
	</html:form>
	<!-- E N D : Content/Form Area -->