<%
/**
 * @ (#) onlinepolicygrouplist.jsp 6th April 2008
 * Project      : TTK HealthCare Services
 * File         : onlinepolicygrouplist.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created :6th April 2008
 *
 * @author       :Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%
	pageContext.setAttribute("ttkBranchList",Cache.getCacheObject("officeInfo"));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/onlineforms/onlinepolicygrouplist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/PolicyGroupAction.do"  method="post">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>Select Group Name </td>
	</tr>
</table>
<!-- E N D : Page Title -->
<!-- S T A R T : Search Box -->
<table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
	<tr>
		<td nowrap>Group Id:<br>
			<html:text property="sGroupId" name="frmPolicyGroupList" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
		<td nowrap>Group Name:<br>
			<html:text property="sGroupName" name="frmPolicyGroupList" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
		</td>
        <td nowrap>Vidal Health Branch:<br>
        	<html:select property="officeInfo"  styleClass="selectBoxWeblogin selectBoxMediumWeblogin" style="width:220px;">
  				<html:option value="">Any</html:option>
  				<html:options collection="ttkBranchList"  property="cacheId" labelProperty="cacheDesc"/>
		    </html:select>
      	</td>
      	<td valign="bottom" width="100%" nowrap>
	      	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
   		</td>
	</tr>
</table>
<!-- E N D : Search Box -->
<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableDataGroupName"/>
<!-- E N D : Grid -->
<!-- S T A R T : Buttons and Page Counter -->
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="27%"></td>
		<td width="73%" nowrap align="right">
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
		</td>
	</tr>
	<ttk:PageLinks name="tableDataGroupName"/>
</table>
<!-- E N D : Buttons and Page Counter -->
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<input type="hidden" name="child" value="GroupList">
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->