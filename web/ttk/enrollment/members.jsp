<%
/**
 * @ (#) members.jsp Feb 2nd 2006
 * Project      : TTK HealthCare Services
 * File         : members.jsp
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : Feb 2nd 2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/enrollment/member.js"></SCRIPT>
<script> TC_Disabled = true;</script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/MemberAction.do" >
<%! String policy_status = null; %>

<%  policy_status =(String)request.getSession().getAttribute("policy_status");
  
%>
<logic:empty name="frmMember" property="display">

	<!-- S T A R T : Page Title -->
	<logic:match name="frmPolicyList" property="switchType" value="ENM">
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmPolicyList" property="switchType" value="END">
	<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<tr>
    <td width="50%">List of Members - <bean:write name="frmMember" property="caption"/></td>
    <td width="50%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
    </tr>
</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">

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
	<table align="center" class="formContainer rcContainer" border="0" cellspacing="1" cellpadding="0">
	  <tr>
    	<td>
    		<ttk:TreeComponent name="treeData"/>
	    </td>
	  </tr>
	    <tr>
		<td align="right" colspan="2" class="buttonsContainerGrid">
		<%
			if(TTKCommon.isAuthorized(request,"DefineRule"))
			{
		%>
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClearRules();"><u>C</u>lear Rules</button>&nbsp;
		<%
			}
		%>
			<button type="button" name="Button" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onShowError();">Show <u>V</u>alidation Error</button>
		</td>
	  </tr>
	</table>
	<!-- E N D : Form Fields -->
<p>&nbsp;</p>
</div>


<!-- E N D : Buttons --></div>

	<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->
 	<html:hidden property="mode" />
 	<input type="hidden" name="child" value="">
    <input type="hidden" name="selectedroot" value="">
    <input type="hidden" name="selectednode" value="">
    <input type="hidden" name="pageId" value="">

</logic:empty>

<logic:notEmpty name="frmMember" property="display">
	<html:errors/>
</logic:notEmpty>
</html:form>