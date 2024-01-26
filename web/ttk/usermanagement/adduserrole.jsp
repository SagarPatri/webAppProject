<%
/** @ (#) adduserrole.jsp 10th Jan 2006
 * Project     : TTK Healthcare Services
 * File        : adduserrole.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 10th Jan 2006
 *
 * @author 		  Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
	pageContext.setAttribute("listUserType",Cache.getCacheObject("tpaUsers"));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" SRC="/ttk/scripts/usermanagement/adduserrole.js"></script>

<!-- S T A R T : Content/Form Area -->
	<html:form action="/AddEditRolesAction.do" method="POST">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <logic:notEmpty name="frmUserRoleDetails" property="roleSeqID">
	    	<td>Role Details - Edit </td>
	    </logic:notEmpty>
	    <logic:empty name="frmUserRoleDetails" property="roleSeqID">
	    	<td>Role Details - Add </td>
	    </logic:empty>
		<td width="43%" align="right" class="webBoard">&nbsp;</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors />

	<!-- S T A R T : Success Box -->
	 <logic:notEmpty name="updated" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="updated" scope="request"/>
	     </td>
	   </tr>
	  </table>
	 </logic:notEmpty>

	<!-- S T A R T : Form Fields -->
	<fieldset>
    <legend>Role Information</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="18%" class="formLabel">Role: <span class="mandatorySymbol">*</span></td>
        <td class="formLabel"><html:text property="roleName" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/></td>
        </tr>
      <tr>
        <td class="formLabel">User Type: <span class="mandatorySymbol">*</span></td>
        <td>
	        <html:select property="userType" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
				<html:option value="">Select from list</html:option>
				<html:options collection="listUserType" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Description: </td>
        <td><html:textarea property="roleDesc" styleClass="textBox textAreaLong" disabled="<%= viewmode %>"/></td>
      </tr>
    </table>
	</fieldset>
	<table align="center" class="formContainer rcContainer" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <td class="fieldGroupHeader">&nbsp;</td>
        <td align="right" class="fieldGroupHeader">&nbsp;</td>
      </tr>
      <tr>
        <td class="fieldGroupHeader">Permissions & Access Levels</td>
        <td align="right" class="fieldGroupHeader">
        <%
    		if(TTKCommon.isAuthorized(request,"Edit"))
			{
		%>
        	<a href="#" class="fieldGroupHeader" onClick="checkAll(true,document.forms[1],'chkPermissions')">Check All</a> | <a href="#" class="fieldGroupHeader" onClick="checkAll(false,document.forms[1],'chkPermissions')">Clear All</a>
        <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	    %>
	    </td>
      </tr>
      <tr>
        <td colspan="2">
        	<ttk:RoleTree/>
        </td>
      </tr>
	</table>
	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
    	<%
    		if(TTKCommon.isAuthorized(request,"Edit"))
			{
		%>
	    	<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
	    	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
	    <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	    %>

	   <logic:notEmpty name="frmUserRoleDetails" property="roleSeqID">
    	<%
			if(TTKCommon.isAuthorized(request,"Delete"))
			{
		%>
			<button type="button" name="Button2" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onDelete()"><u>D</u>elete</button>&nbsp;
		<%
	    	}//end of if(TTKCommon.isAuthorized(request,"Delete"))
	    %>
	    </logic:notEmpty>
	    	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<input type="hidden" name="mode">
	<html:hidden property="roleSeqID"/>
	<input type="hidden" name="child" value="Role Details">
	<INPUT TYPE="hidden" NAME="rownum" VALUE='<%=TTKCommon.checkNull(request.getParameter("rownum"))%>'>
	</html:form>
	<!-- E N D : Content/Form Area -->