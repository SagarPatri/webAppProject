<%
/** @ (#) onlineaccountinfo.jsp March 24, 2008
 * Project     : TTK Healthcare Services
 * File        : onlineaccountinfo.jsp
 * Author      : Balakrishna E
 * Company     : Span Systems Corporation
 * Date Created: March 24, 2008
 *
 * @author 		 : Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/onlineforms/onlineaccpolicylist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true;
</SCRIPT>

<%
UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	         
		        String strUserID = userSecurityProfile.getLoginType();
			 
 %>	
 
<!-- S T A R T : Content/Form Area -->
<html:form action="/OnlineAccountInfoAction.do">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>List of Enrollments</td>
		<td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	</tr>
</table>
<!-- E N D : Page Title -->

<div class="contentArea" id="contentArea">
<html:errors/>

<table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
      <tr>
      	<td nowrap>Enrollment Id:<br>
            <html:text property="sEnrollmentNumber" styleClass="textBoxWeblogin textBoxMediumWeblogin" styleId="search1" maxlength="60"/>
        </td>
        <td width="25%" nowrap>Policy No.:<br>
            <html:text property="sPolicyNumber" name="frmOnlineAccountInfo" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
        <td nowrap>Group Id:<br>
            	<html:text property="sGroupId" name="frmOnlineAccountInfo" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search3" maxlength="60"/>
        </td>
        <td nowrap>Group Name:<br>
	        <html:text property="sGroupName" styleClass="textBoxWeblogin textBoxMediumWeblogin" styleId="search4" maxlength="60"/>
		</td>
		<td width="46%" valign="bottom" nowrap>
        <a href="#" accesskey="s" onClick="javascript:SelectCorporate()" class="search">
	        <img src="/ttk/images/EditIcon.gif" alt="Select Corporate" title="Select Corporate" width="16" height="16" border="0" align="absmiddle">&nbsp;
        </a>
        </td>        
    	</tr>
	    <tr> 
	    <td nowrap>Member Name:<br>
	        <html:text property="memberName" styleClass="textBoxWeblogin textBoxMediumWeblogin" styleId="search5" maxlength="60"/>
		</td>   	
    	<td nowrap>Show Latest:<br>
			<input name="showLatestYN" type="checkbox" value="Y" checked>
		</td>		
		<td valign="bottom" nowrap>
        <a href="#" accesskey="s" onClick="javascript:onSearch()" class="search">
	        <img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch
        </a>
    	</td>
    	<td></td>
    	
    	
		</tr>				
    </table>
	<!-- E N D : Search Box -->
<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData"/>
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
<ttk:PageLinks name="tableData"/>
</table>

<!-- E N D :  Grid -->
<logic:notEmpty name="frmOnlineAccountInfo" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
<!-- S T A R T : Buttons and Page Counter -->
	</div>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="child" value="">
	<html:hidden property="showLatest"/>
	<INPUT TYPE="hidden" NAME="strUserID" VALUE="<%=strUserID%>"><!-- added for broker -->
	
    </html:form>
	<!-- E N D : Content/Form Area -->