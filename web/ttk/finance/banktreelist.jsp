<%
/**
 * @ (#) treebanklist.jsp 26th June 2006
 * Project      : TTK HealthCare Services
 * File         : treebanklist.jsp
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : 26th June 2006
 *
 * @author       : Raghavendra T M
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
	pageContext.setAttribute("city", Cache.getCacheObject("cityCode"));
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/finance/banktreelist.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
      <!-- S T A R T : Content/Form Area -->
      <html:form action="/BankTreeListAction.do" >
        <!-- S T A R T : Page Title -->
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="50%">List of Banks</td>
            <td width="50%" align="right" class="webBoard">&nbsp;</td>
          </tr>
        </table>
        <!-- E N D : Page Title -->
        <div class="contentArea" id="contentArea">
	      <html:errors/>
          <!-- S T A R T : Form Fields -->
          <table align="center" class="searchContainer rcContainer" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td nowrap>Bank Name:<br>
              <html:text property="BankName" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
				</td>
				<td valign="bottom" nowrap width="100%"><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
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
			    	<button type="button" name="Button2" accesskey="a" class="buttons addNewGroupbtn" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="OnAddNewGroup()"><u>A</u>dd Bank</button>&nbsp;
		      <%
					}
			  %>
			</td>
          </tr>
          <ttk:TreePageLinks name="treeData"/>
          </table>
          <!-- E N D : Form Fields -->
          <p>&nbsp;</p>
        </div>
      <!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->
 	<html:hidden property="rownum"/>
    <html:hidden property="mode" />
	<input type="hidden" name="tab" value="">
    <input type="hidden" name="selectedroot" value="">
    <input type="hidden" name="selectednode" value="">
    <input type="hidden" name="pageId" value="">
</html:form>