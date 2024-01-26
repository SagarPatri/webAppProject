<%
/** @ (#) coveragelist.jsp 9th Jul 2006
 * Project     : TTK Healthcare Services
 * File        : coveragelist.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 9th Jul 2006
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
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
function onReset()
{
	document.forms[1].reset();
}
function onSave()
{
	document.forms[1].mode.value="doSave";
	document.forms[1].action = "/ContinueRuleDataAction.do";
	document.forms[1].submit();
}
</script>
<%
	pageContext.setAttribute("ActiveLink",TTKCommon.getActiveLink(request));
%>
<html:form action="/CoverageListAction.do" >
	<!-- S T A R T : Page Title -->
	<logic:match name="ActiveLink" value="Pre-Authorization">
	<table align="center" class="<%=TTKCommon.checkNull(PreAuthWebBoardHelper.getShowBandYN(request)).equals("Y")? "pageTitleHilite" :"pageTitle" %>" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td >List of Clauses - [ <%=PreAuthWebBoardHelper.getClaimantName(request)%> ] [ <%=PreAuthWebBoardHelper.getWebBoardDesc(request)%> ]
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
		    <td >List of Clauses - [ <%=ClaimsWebBoardHelper.getClaimantName(request)%> ] [ <%=ClaimsWebBoardHelper.getWebBoardDesc(request)%> ]
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
	<!-- S T A R T : Form Fields -->
	<ttk:CoverageList/>
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr><td width="100%" align="center">
	   	<%
			if(TTKCommon.isAuthorized(request,"Edit"))
			{
		%>
	    		<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
	    		<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>
	    <%
			}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		%>
	  	  </td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</html:form>
	<!-- E N D : Content/Form Area -->