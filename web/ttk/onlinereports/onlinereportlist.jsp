<%
/** @ (#) policylist.jsp
 * Project     : TTK Healthcare Services
 * File        : onlinereportlist.jsp
 * Author      : Balaji C R B
 * Company     : Span Systems Corporation
 * Date Created: March 8, 2008
 *
 * @author 		 : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.dto.usermanagement.UserSecurityProfile" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>

<SCRIPT LANGUAGE="JavaScript">
bAction = false; //to avoid change in web board in product list screen
var TC_Disabled = true;
function onSelectLink(pdffilename,xlsfilename,csvfilename,reportID,reportName)//KOC 1353 for payment report
{
	document.forms[1].pdfFileName.value=pdffilename;
	document.forms[1].xlsFileName.value=xlsfilename;
	document.forms[1].csvFileName.value=csvfilename;//KOC 1353 for payment report	
	document.forms[1].reportID.value=reportID;
	document.forms[1].reportName.value=reportName;
	document.forms[1].mode.value="doReportsDetail";
    document.forms[1].action.value="/OnlineReportsAction.do";
	document.forms[1].submit();
}//end of onSelectLink(filename,reportID,reportName))
</SCRIPT>
<html:form action="/OnlineReportsAction.do">
<%
UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
%>
<!-- S T A R T : Page Title -->
<%
if(!userSecurityProfile.getLoginType().equals("B")){
%>
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><%=TTKCommon.getActiveSubLink(request)%> Reports </td>
  </tr>
</table>
  <%} %>
<!-- End of: Page Title -->
<div class="contentArea" id="contentArea">
<!-- Start of Reports List -->
	<ttk:WebLoginReportsLink/>
<!-- End of Reports List -->
<%
	 	
	 	if(userSecurityProfile.getGroupID().equalsIgnoreCase("A0912"))
	 	{
	 		 	
		    %>
<fieldset>
    	<legend>Customized Links For Accenture</legend>
	    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td >
	        <html:link href="ftp://vidalhealthtpa.com/accenture/MIS" target="_blank">MIS </html:link><br>
	        </td>
	      </tr>
		  <tr>
	        <td >
	        <html:link href="ftp://vidalhealthtpa.com/accenture/MOM" target="_blank">MOM </html:link><br>
	        </td>
	      </tr>
	    </table>
	   	</fieldset>
	   	<%
		    }
	 %>
</div>

<html:hidden property="pdfFileName"/>
<html:hidden property="xlsFileName"/>
<html:hidden property="csvFileName"/><!-- csvfilename KOC 1353 for KOC 1353 for payment report -->
<html:hidden property="reportID"/>
<html:hidden property="reportName"/>
<input type="hidden" name="mode" value=""/>
</html:form>



