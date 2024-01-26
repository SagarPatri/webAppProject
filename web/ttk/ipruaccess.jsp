<%
/** @ (#) ipruaccess.jsp Nov 13th, 2008
 * Project       : TTK Healthcare Services
 * File          : ipruaccess.jsp
 * Author        : Ramakrishna K M
 * Company       : Span Systems Corporation
 * Date Created  : Nov 13th, 2008
 * @author 		 : Ramakrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<head>
<title>Vidal Healthcare Administrator - Online Access</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
</head>
<script language="javascript">
function onLogin()
{
    trimForm(document.forms[0]);
    var policyNo = document.forms[0].policyNbr.value;
    var ipruURL = document.forms[0].fromUrl.value;
   	if(ipruURL == 'www.iciciprulife.com'){
    	document.forms[0].mode.value="doLogin";
    	document.forms[0].action = "/IpruAccessAction.do";
    	document.forms[0].submit();
    }//end of if(ipruURL == 'www.iciciprulife.com')
    else{
    	alert("Please select the Scheme No. from IPRU");
        return false;
    }//end of else
    
}//end of onLogin()

</script>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<body>
<%
	pageContext.setAttribute("Policy_Nbr",TTKCommon.checkNull(request.getParameter("policyNbr")));
%>
<logic:notEmpty name="OpenPage">
	<script language="javascript">
			openFullScreenMode("<bean:write name="OpenPage"/>");
	</script>
</logic:notEmpty>
   <html:form action="/IpruAccessAction.do" onsubmit="onLogin()">
   <html:errors/>
   <%
	if(request.getParameter("sessionexpired")!=null)
	{
%>
	  <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/ErrorIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message key="error.iprusession"/>
	     </td>
	   </tr>
	  </table>
<%
	}//end of if(request.getParameter("sessionexpired")!=null)
%>	
<input type="hidden" name="mode">
<input type="hidden" name="policyNbr" value="<%=request.getParameter("policyNbr")%>">
<input type="hidden" name="fromUrl" value="<%=request.getParameter("fromUrl")%>">

</html:form>
</body>
