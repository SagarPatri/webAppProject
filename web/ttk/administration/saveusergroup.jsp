<%/**
 * @ (#) saveusergroup.jsp Jan 20, 2006
 * Project       : TTK HealthCare Services
 * File          : saveusergroup.jsp
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Jan 20, 2006
 * @author       : Bhaskar Sandra
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
 <%@ page import="com.ttk.common.TTKCommon"%>
 <%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
 <%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
 <%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%
	String viewmode=" disabled ";
 	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode="";
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	%>
 <SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/administration/saveusergroup.js"></SCRIPT>
 <!-- S T A R T : Content/Form Area -->
	<form action="/ProductUserGroupAction.do" method="post" name="frmProductUserGroup">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
		    <td>User Group [<bean:write name="frmProductUserGroup" property="caption"/>]</td>
			<td  align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	    </tr>
	</table>
	<div class="contentArea" id="contentArea">
	<!-- E N D : Page Title -->
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
	<!-- E N D : Success Box -->
	<html:errors/>
    <!-- S T A R T : Form Fields -->

	<fieldset>
	<legend>User Group</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="20%" class="textLabel">Select TPA Branches to create Special User Group </td>
	  </tr>
	</table>
	<table class="formContainer"  border="0" cellspacing="0" cellpadding="0">

	<bean:define id="zoneNameTmp" name="frmProductUserGroup" property="Zone"/>
	<% int countBr=1; %>
	<logic:iterate id="usergroup" name="frmProductUserGroup" property="usergroup" >
		<logic:equal name="usergroup" property="officeCode" value="THO">
			 	  <tr> <td colspan="6" class="textLabelBold indentedLabels" height="6"></td> </tr>
				  <tr> <td width="23%" class="textLabelBold indentedLabels"><bean:write name="usergroup" property="officeName" /></td>
				  <td width="10%">
				      <INPUT NAME="chbox" TYPE="checkbox" VALUE=<bean:write name="usergroup" property="officeSeqID" /> <logic:notEmpty name="usergroup" property="groupBranchSeqID"> checked </logic:notEmpty> "<%=viewmode%>" >
 				      <INPUT TYPE="hidden" NAME="officeSeqID" VALUE=<bean:write name="usergroup" property="groupBranchSeqID" />>
				    </td>
				    
				  <tr>
		</logic:equal>
		<logic:equal name="usergroup" property="officeCode" value="TZO">
				<% countBr=1; %>
			 	  <tr> <td colspan="6" class="textLabelBold indentedLabels" height="6"></td> </tr>
				  <tr> <td width="24%" class="textLabelBold indentedLabels"><bean:write name="usergroup" property="officeName" /></td>
				  		<td width="10%">
				     		 <INPUT NAME="chbox" TYPE="checkbox" VALUE=<bean:write name="usergroup" property="officeSeqID" /> <logic:notEmpty name="usergroup" property="groupBranchSeqID"> checked </logic:notEmpty> "<%=viewmode%>" >
 				     		 <INPUT TYPE="hidden" NAME="officeSeqID" VALUE=<bean:write name="usergroup" property="groupBranchSeqID" />>
				    	</td>
				  <tr>
		</logic:equal>
		<logic:equal name="usergroup" property="officeCode" value="TBO">
					<td width="23%" class="formLabel indentedLabels"><bean:write name="usergroup" property="officeCode" />/<bean:write name="usergroup" property="officeName" /></td>
				    <td width="10%">
				      <INPUT NAME="chbox" TYPE="checkbox" VALUE=<bean:write name="usergroup" property="officeSeqID" /> <logic:notEmpty name="usergroup" property="groupBranchSeqID"> checked </logic:notEmpty> "<%=viewmode%>" >
 				      <INPUT TYPE="hidden" NAME="officeSeqID" VALUE=<bean:write name="usergroup" property="groupBranchSeqID" />>
				    </td>
				    <% if(countBr++%3==0) {%>
						<tr>
					<% 	}	 %>
		</logic:equal>
	</logic:iterate>
  	</tr>
	</table>
	</fieldset>

	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <logic:notEmpty name="frmProductUserGroup" property="usergroup">
    <%
		if(TTKCommon.isAuthorized(request,"Edit"))
		{
	%>
		    <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
			<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClearAll();"><u>C</u>lear All</button>
    <%
		}
     %>
    </td>
    </logic:notEmpty>
  </tr>
</table>
</div>
	<!-- E N D : Buttons -->
	<input type="hidden" name="mode" value="">
	</form>
	<!-- E N D : Content/Form Area -->
