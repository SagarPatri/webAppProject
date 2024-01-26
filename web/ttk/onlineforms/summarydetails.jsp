<%
/**
 * @ (#) summarydetails.jsp July 19, 2007
 * Project      : TTK HealthCare Services
 * File         : summarydetails.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : July 19, 2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/childwindow.js"></script>
<script language="javascript" src="/ttk/scripts/onlineforms/summarydetails.js"></script>
<html:form action="/OnlineHistoryAction.do" >
	<%
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
		if(TTKCommon.getActiveSubLink(request).equals("Pre-Auth"))
			 {
	%>
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="57%">Cashless Details - <bean:write name="frmHistoryDetail" property="caption"/></td>
			<td width="43%" align="right" class="webBoard">&nbsp;&nbsp;</td>
		  </tr>
		</table>
	<%
			 }
	%>
	<%
		if(TTKCommon.getActiveSubLink(request).equals("Claims"))
			 {
	%>
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="57%">Claims Details  <bean:write name="frmHistoryDetail" property="caption"/></td>
			<td width="43%" align="right" class="webBoard">&nbsp;&nbsp;</td>
		  </tr>
		</table>
	<%
			 }
	%>
<div class="contentArea" id="contentArea">

	<%
		if(TTKCommon.getActiveSubLink(request).equals("Pre-Auth"))
			 {
	%>
		<ttk:OnlinePreAuthHistory/>
	<%
			 }
	%>
	<%
		if(TTKCommon.getActiveSubLink(request).equals("Claims"))
			 {
	%>
	<ttk:OnlineClaimHistory/>
	<%
			 }
	%>
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    <%//kocb 
	    if(userSecurityProfile.getLoginType().equals("B")){  %>
	    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>B</u>ack</button>
	    <% }else{ %>
	    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    	<% } %>
	    </td>
	  </tr>
	</table>
	<!-- E N D : Buttons -->
</div>

<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="Details">
</html:form>
