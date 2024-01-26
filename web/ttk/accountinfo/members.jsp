<%
/**
 * @ (#) members.jsp July 26th, 2007
 * Project      : Vidal Health TPA Services
 * File         : members.jsp
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : July 26th, 2007
 *
 * @author       : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/accountinfo/member.js"></SCRIPT>
<script> TC_Disabled = true;</script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/AccInfoMemberAction.do" >

<logic:empty name="frmAccMember" property="display">

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td width="50%">List of Members - <bean:write name="frmAccMember" property="caption"/></td>
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
    	<td height="10" colspan="2"></td>
	  </tr>
	  <tr>
	    <td colspan="2">
    		<ttk:TreeComponent name="treeData"/> <br>
	    </td>
	  </tr>
	  <tr>
		<td align="right" colspan="2" class="buttonsContainerGrid">
		</td>
	  </tr>
	</table>
	<!-- E N D : Form Fields -->
<p>&nbsp;</p>
</div>

<!-- E N D : Buttons --></div>

	<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->
	<html:hidden property="rownum"/>
 	<html:hidden property="mode" />
 	<input type="hidden" name="child" value="">
    <input type="hidden" name="selectedroot" value="">
    <input type="hidden" name="selectednode" value="">
    <input type="hidden" name="pageId" value="">
	<input type="hidden" name="sPolicySeqID" value="7">
</logic:empty>

<logic:notEmpty name="frmAccMember" property="display">
	<html:errors/>
</logic:notEmpty>
</html:form>