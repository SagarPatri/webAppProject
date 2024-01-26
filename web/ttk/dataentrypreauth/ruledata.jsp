<%
/** @ (#) ruledata.jsp Jul 07th, 2006
 * Project       : TTK Healthcare Services
 * File          : ruledata.jsp
 * Author        : Unni V Mana
 * Company       : Span Systems Corporation
 * Date Created  : 07th Jul 2006
 * @author 		 : Unni V Mana
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/preauth/ruledata.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">

</SCRIPT>
<%
	pageContext.setAttribute("ActiveLink",TTKCommon.getActiveLink(request));
%>
<!-- S T A R T : Content/Form Area -->
<!-- S T A R T : Page Title -->
	<html:form action="/RuleDataAction.do">
	<logic:match name="ActiveLink" value="Pre-Authorization">
		<table align="center" class="<%=TTKCommon.checkNull(PreAuthWebBoardHelper.getShowBandYN(request)).equals("Y")? "pageTitleHilite" :"pageTitle" %>" border="0" cellspacing="0" cellpadding="0">
			<tr>
			<td>Healthcare Check - [ <%=PreAuthWebBoardHelper.getClaimantName(request)%> ]
			<%
			if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim()))){
			%>
			 [ <%=PreAuthWebBoardHelper.getEnrollmentId(request)%> ]
			<%} %> 
			 </td>
			<td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
			</tr>
		</table>
	</logic:match>
	<logic:match name="ActiveLink" value="Claims">
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>Healthcare Check - [ <%=ClaimsWebBoardHelper.getClaimantName(request)%> ]
			<%
			if(ClaimsWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(ClaimsWebBoardHelper.getEnrollmentId(request).trim()))){
			%>
			 [ <%=ClaimsWebBoardHelper.getEnrollmentId(request)%> ]
			<%} %> 
			</td>
			<td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
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
 	<!-- E N D : Success Box -->
	<!-- S T A R T : Form Fields -->

	<ttk:RuleResult/>
	<!-- E N D : Form Fields -->
	<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  	<tr>

    <td width="37%" align="left"><span class="textLabelBold">Legend: </span> <span class="pageTitleInfo">Red</span> - Requested Data&nbsp;&nbsp;&nbsp;<span class="subHeader">Blue</span>&nbsp;- Limits</td>

	<td align="left">
	<%
		if(TTKCommon.isAuthorized(request,"Edit"))
		{
	%>
		<logic:notEmpty name="frmPreAuthRuleData" property="ruleDataSeqID">
			<button type="button" name="InitiateCheck" accesskey="i" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onInitiateCheck();"><u>I</u>nitiate Check</button>&nbsp;
		</logic:notEmpty>
		<button type="button" name="SelectClause" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReconfigure();">Re<u>C</u>onfigure</button>&nbsp;
		<button type="button" name="ModifyData" accesskey="m" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onModifyData();"><u>M</u>odify Data</button>
	<%
		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	%>
    </td>
 	</tr>
	</table>
	<!-- E N D : Buttons -->
	</div>
	<INPUT TYPE="hidden" NAME="mode" VALUE="save">
<!-- Changes KOC1099 -->
	<INPUT TYPE="hidden" NAME="OverrideCompletedYN" VALUE="N">
	<!-- Changes KOC1099 -->
	<html:hidden property="ruleExecutionFlag"/>
</html:form>
