<%
/**
 * @ (#) policygeneral.jsp July 24, 2007
 * Project      : TTK HealthCare Services
 * File         : policygeneral.jsp
 * Author       : Krupa J	
 * Company      : Span Systems Corporation
 * Date Created : July 24, 2007
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
<%@ page import="com.ttk.common.WebBoardHelper,com.ttk.common.TTKCommon,com.ttk.dto.usermanagement.UserSecurityProfile"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script>
function Close()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/OnlinePolicyDetailsAction.do";
	document.forms[1].submit();
}//end of Close()

function onIECards()
{

	var openPage = "/OnlineMemberAction.do?mode=doIECards";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
	//if(!TrackChanges()) return false;
	//document.forms[1].mode.value="doIECards";
	//document.forms[1].target="_blank";
	//document.forms[1].action="/OnlineMemberAction.do";
	//document.forms[1].submit();
}//end of Close()
</script>
<%
	//String strDisplay="display";
	//pageContext.setAttribute("ActiveSubLink",TTKCommon.getActiveSubLink(request));
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	String PolicyOpted="";
	PolicyOpted = (String)request.getSession().getAttribute("PolicyOpted");

%>
<html:form action="/OnlinePolicyDetailsAction.do" >
<logic:empty name="frmOnlineDetails" property="display">
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="57%">Enrollment Details - <bean:write name="frmOnlineDetails" property="caption"/></td>
	<td width="43%" align="right" class="webBoard">&nbsp;&nbsp;</td>
	<%
		    if(userSecurityProfile.getLoginType().equals("E")) 
		    {
			if(PolicyOpted.equalsIgnoreCase("Y"))
		    	{
		    %>
	<td align="right"><a href="#" onclick="javascript:onIECards()" ><img src="/ttk/images/E-Cardbold.gif" alt="E-Card" title="E-Card" width="81" height="17" align="absmiddle" border="0" class="icons"></a></td>
         <%
		 }
		    }
		    else if(userSecurityProfile.getLoginType().equals("H"))
		    {
		    	%>
		    	<td align="right"><a href="#" onclick="javascript:onIECards()" ><img src="/ttk/images/E-Cardbold.gif" alt="E-Card" title="E-Card" width="81" height="17" align="absmiddle" border="0" class="icons"></a></td>
		             <%
		    }
		    %>
  </tr>
</table>

<div class="contentArea" id="contentArea">
<logic:match name="logintype" value="I">

			<!--<ttk:PolicyDetailHistory/>-->
	</logic:match>
	<ttk:OnlinePolicyHistory/>
	<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    <%if(userSecurityProfile.getLoginType().equals("B")){ %>
	       <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Close()"><u>B</u>ack</button>
	    <% }else{ %>
	    <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Close()"><u>C</u>lose</button>
	    <% } %>
	    </td>
	  </tr>
	</table>
	<!-- E N D : Buttons -->
</div>
</logic:empty>
<logic:notEmpty name="frmOnlineDetails" property="display">
	<html:errors/>
</logic:notEmpty>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="">
<html:hidden property="logintype" />


</html:form>