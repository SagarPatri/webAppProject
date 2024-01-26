<%
/** @ (#) tariffdetail.jsp 26th Jun 2006
 * Project     : TTK Healthcare Services
 * File        : tariffdetail.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 26th Jun 2006
 *
 * @author 	   : Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/preauth/modifyruledata.js"></SCRIPT>
<%
	pageContext.setAttribute("ActiveLink",TTKCommon.getActiveLink(request));
%>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/ModifyRuleDataAction.do" >
	<!-- S T A R T : Page Title -->
	<logic:match name="ActiveLink" value="Pre-Authorization">
		<table align="center" class="<%=TTKCommon.checkNull(PreAuthWebBoardHelper.getShowBandYN(request)).equals("Y")? "pageTitleHilite" :"pageTitle" %>" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td>Modify Rule Data - [ <%=PreAuthWebBoardHelper.getClaimantName(request)%> ] [ <%=PreAuthWebBoardHelper.getWebBoardDesc(request)%> ]
		    <%
			if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim()))){
			%>
			 [ <%=PreAuthWebBoardHelper.getEnrollmentId(request)%> ]
			<%} %>
		    </td>
		  </tr>
		</table>
	</logic:match>
	<logic:match name="ActiveLink" value="Claims">
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td >Modify Rule Data - [ <%=ClaimsWebBoardHelper.getClaimantName(request)%> ] [ <%=ClaimsWebBoardHelper.getWebBoardDesc(request)%> ]
		    <%
			if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim()))){
			%>
			 [ <%=ClaimsWebBoardHelper.getEnrollmentId(request)%> ]
			<%} %>
		    </td>
		  </tr>
		</table>
	</logic:match>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
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
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td valign="top" >
			<ttk:RuleData/>
		</td>
	</tr>
	</table>
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
		<%
			if(TTKCommon.isAuthorized(request,"Edit"))
			{
		%>
			<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
	 	    <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>
		<%
			}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		%>
		 </td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	</html:form>
	<!-- E N D : Content/Form Area -->
