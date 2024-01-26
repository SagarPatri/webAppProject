<%
/**
 * @ (#) rolelist.jsp 28th Dec 2005
 * Project      : TTK HealthCare Services
 * File         : rolelist.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 28th Dec 2005
 *
 * @author       :Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%
	pageContext.setAttribute("tpausers", Cache.getCacheObject("tpaUsers"));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/usermanagement/rolelist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/RolesAction.do"  method="post">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td>List of Roles </td>
		<td width="43%" align="right" class="webBoard">&nbsp;</td>
	</tr>
</table>
<!-- E N D : Page Title -->
<div class="contentArea" id="contentArea">
<!-- S T A R T : Search Box -->
<html:errors/>
<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="bottom" nowrap>Role:<br>
      		<html:text property="sRoleName" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
      	</td>
		<td nowrap>User Type:<br>
			<html:select property="sTpaUsers" styleClass="selectBox selectBoxMedium">
		  	 	  <html:option value="">Any</html:option>
		          <html:optionsCollection name="tpausers" label="cacheDesc" value="cacheId" />
            </html:select>
	    </td>
		<td valign="bottom" nowrap>
        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
		</td>
    	<td width="100%">&nbsp;</td>
	</tr>
</table>
<!-- E N D : Search Box -->
<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData"/>
<!-- E N D : Grid -->
<!-- S T A R T : Buttons and Page Counter -->
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="27%"></td>
        <td width="73%" nowrap align="right">
        	<%
	    		if(TTKCommon.isAuthorized(request,"Add"))
				{
			%>
        		<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddRole()"><u>A</u>dd</button>&nbsp;
        	<%
	    		}
	    		if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
	    		{
	    	%>
        			<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>
        	<%
	    		}// end of if(TTKCommon.isDataFound(request,"tableData"))
	    	%>
        </td>
	</tr>
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
<!-- E N D : Content/Form Area -->	</td>