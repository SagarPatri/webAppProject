<%
/**
 * @ (#)  policieslist.jsp July 24,2006
 * Project      : TTK HealthCare Services
 * File         : policieslist.jsp
 * Author       : Harsha Vardhan B N
 * Company      : Span Systems Corporation
 * Date Created : July 24,2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon" %>
<!-- kocb -->
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/onlineforms/policieslist.js"></script>
<html:form action="/OnlinePoliciesAction.do">
	<!-- S T A R T : Page Title -->
<%//kocb
UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
 if(userSecurityProfile.getLoginType().equals("B")){ %>
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="57%">List of Schemes <bean:write name="frmOnlinePolicies" property="caption"/></td>
	  </tr>
	</table>
	
	<!-- kocb -->
	
<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
      <tr>
      	
        <td width="25%" nowrap>Scheme No.:<br>
            <html:text property="sPolicyNumber" name="frmOnlinePolicies" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
        <td nowrap>Group/Company Id:<br>
            	<html:text property="sGroupId" name="frmOnlinePolicies" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search3" maxlength="60"/>
        </td>
        <td nowrap>Group/Company Name:<br>
	        <html:text property="sGroupName" styleClass="textBoxWeblogin textBoxMediumWeblogin" styleId="search4" maxlength="60"/>
		</td>
		<!-- 
		<td width="46%" valign="bottom" nowrap>
        <a href="#" accesskey="s" onClick="javascript:SelectCorporate()" class="search">
	        <img src="/ttk/images/EditIcon.gif" alt="Select Corporate" title="Select Corporate" width="16" height="16" border="0" align="absmiddle">&nbsp;
        </a>
        </td>  
        
         -->
         
	    	
		<td valign="bottom" nowrap>
        <a href="#" accesskey="s" onClick="javascript:onSearch()" class="search">
	        <img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch
        </a>
    	</td>
    	<td></td>
    	
    	
		</tr>				
    </table>
    <%} %>
    
    
	<!-- E N D : Page Title --><br>
<html:errors/>
	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
    <!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="27%"></td>
  	    <ttk:PageLinks name="tableData"/>
  	  </tr>
	</table>
	 <%
	 //kocb 
	 if(userSecurityProfile.getLoginType().equals("B")){ %>
	 <br><table align="left"   border="0" cellspacing="0" cellpadding="0">
            <tr>
            	<td><b>Note :&nbsp;</b></td>
                  <td class="textLabel">To Navigate Details, please search using one or more criteria and click on Scheme number to view the details.  
                  </td>
            
     </tr>
</table>
<%} %>
 
</div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="historymode" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->