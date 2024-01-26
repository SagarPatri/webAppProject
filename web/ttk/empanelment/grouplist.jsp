<%
/**
 * @ (#) groupdetail.jsp 16th Jan 2006
 * Project      : TTK HealthCare Services
 * File         : groupdetail.jsp
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : 12th Jan 2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%
	pageContext.setAttribute("officeinfo", Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("groupType", Cache.getCacheObject("groupType"));
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/empanelment/grouplist.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/GroupListAction.do" >

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="50%">List of Groups <bean:write name="frmGroupList" property="grpCaption"/>  </td>
    <td width="50%" align="right" class="webBoard">&nbsp;</td>
    </tr>
</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">

	<html:errors/>

    <!-- S T A R T : Form Fields -->
	<table align="center" class="tablePad rcContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="10%" nowrap class="textLabelBold">Group Type:</td>
        <td width="90%">
        <html:select property="groupType" styleClass="specialDropDown">
		  	  <html:optionsCollection name="groupType" label="cacheDesc" value="cacheId" />
        </html:select>
        </td>
      </tr>
    </table>
	<table align="center" class="searchContainer rcContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Group Id:<br>
            <html:text property="groupId" styleClass="textBox textBoxMedium" maxlength="250"/></td>
        <td nowrap>Group Name:<br>
            <html:text property="sGroupName" styleClass="textBox textBoxMedium" maxlength="120"/></td>
        <td valign="bottom" nowrap>Branch Location:<br>
            <html:select property="officeInfo" styleClass="selectBox selectBoxMedium">
		  	 	  <html:option value="">Any</html:option>
		          <html:optionsCollection name="officeinfo" label="cacheDesc" value="cacheId" />
            </html:select>
          </td>
        <td valign="bottom" nowrap width="100%">
	        <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
      </tr>
      </table>
	<table align="center" class="formContainer rcContainer" border="0" cellspacing="1" cellpadding="0">
	  <tr>
    	<td height="10" colspan="2"></td>
	  </tr>
	  <tr>
    	<td colspan="2">
    		<ttk:TreeComponent name="treeData"/>
	    </td>
	  </tr>
	  <tr>
	    <td height="25" colspan="2" align="right">&nbsp;</td>
	  </tr>
	  <tr>
	  	<td class="buttonsContainerGrid">&nbsp;</td>
	    <td align="right" class="buttonsContainerGrid">
	    <%
			if(TTKCommon.isAuthorized(request,"Add"))
			{
		%>
	    	<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:OnAddNewGroup();"><u>A</u>dd New Group</button>&nbsp;
    	 <%
			}
		%>&nbsp;
	    </td>
	  </tr>

	  <ttk:TreePageLinks name="treeData"/>

	  </table>
	<!-- E N D : Form Fields -->
<p>&nbsp;</p>
</div>


<!-- E N D : Buttons --></div>

	<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->
	<input type="hidden" name="child" value="">
 	<html:hidden property="rownum"/>
    <html:hidden property="mode" />
    <input type="hidden" name="selectedroot" value="">
    <input type="hidden" name="selectednode" value="">
    <input type="hidden" name="pageId" value="">
    <html:hidden property="grpCaption" />
</body>
</html:form>