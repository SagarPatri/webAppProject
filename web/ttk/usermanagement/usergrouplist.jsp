<%
/**
 * @ (#) hospsearch.jsp 28th Dec 2005
 * Project      : TTK HealthCare Services
 * File         : usergrouplist.jsp
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : 28th Dec 2005
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon, com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<%
	pageContext.setAttribute("officeinfo", Cache.getCacheObject("officeInfo"));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/usermanagement/usergrouplist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<!-- name="frmSearchUserGroup" -->
<html:form action="/UserGroupAction.do">
 	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>List of User Groups </td>
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
  	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td nowrap>User Group:<br>
    	  		<html:text property="susergroup" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
      		</td>
	  		<td nowrap>Vidal Health Branch:<br>
		  	 	<html:select property="listofficeInfo" styleClass="selectBox selectBoxMedium">
		  	 	  <html:option value="">Any</html:option>
		          <html:optionsCollection name="officeinfo" label="cacheDesc" value="cacheId" />
            	</html:select>
      		</td>
      		<td valign="bottom" width="100%" align="left">
    			<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
   			</td>
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
		    <td width="73%" align="right">

			<%
	    		if(TTKCommon.isAuthorized(request,"Edit"))
				{
		    %>
				  <button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddUserGroup()"><u>A</u>dd</button>&nbsp;
			<%
	    		}
		    %>

			<%
	    		if(TTKCommon.isAuthorized(request,"Delete"))
				{
					if(TTKCommon.isDataFound(request,"tableData"))
					{
		    %>
			    	<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
			<%
	    			}
	    		}
		    %>
			</td>
	  	</tr>
			<ttk:PageLinks name="tableData"/>
	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="child" value="">
</html:form>
<!-- E N D : Main Container Table -->