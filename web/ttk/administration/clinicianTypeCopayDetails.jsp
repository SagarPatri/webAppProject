<!DOCTYPE html>
<html>
<%/**
 * @ (#) clinicianTypeCopayDetails.jsp June 28th 2017
 * Project       :Vidal Health TPA Services
 * File          : clinicianTypeCopayDetails.jsp
 * Author        : Nagababu K
 * Company       : Span Systems Corporation
 * Date Created  : June 28th 2017
 * @author       : Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList"%>
<head>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/administration/clinicianTypeCopayDetails.js"></script>

<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<script type="text/javascript">
<!--//-->

</script>

</head>
<body>
<!-- S T A R T : Content/Form Area -->	
	<html:form action="/RuleAction.do"> 	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Clinician Copay Configuration</td>
	  </tr>
	</table>
	<!-- E N D : Page Title --> 	
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Success Box -->
		<logic:notEmpty name="successMsg" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:write name="successMsg" scope="request"/>
				  </td>
				</tr>
			</table>
		</logic:notEmpty>
	<!-- E N D : Success Box -->	
	<!-- S T A R T : Form Fields -->
	
 <fieldset><legend>Clinician List</legend>
 
 <table border="0" align="center" cellpadding="0" cellspacing="0" class="gridWithCheckBox" style='width:50%;height:auto;'">



<%=request.getAttribute("tableContent") %>

</table>
	</fieldset>
	
	<!-- E N D : Form Fields -->
	<!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <%
        if(TTKCommon.isAuthorized(request,"Edit")&&!"V".equals(request.getParameter("OPMode")))
        {
    %>
    
    <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
	
	<%
		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	%>
	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:window.self.close();"><u>C</u>lose</button>
	</td>
  </tr>
</table>



<logic:notEmpty name="confIDValue" scope="session">
<logic:notEmpty name="confID" scope="session">
 
<script type="text/javascript">
window.opener.document.getElementById('<bean:write name="confID"/>').value='<bean:write name="confIDValue"/>';

</script>

</logic:notEmpty>
</logic:notEmpty>
<logic:notEmpty name="closeWindow" scope="request">
<script type="text/javascript">
closeWindow();
</script>
</logic:notEmpty>
<INPUT TYPE="hidden" NAME="mode" VALUE="confClinicianTypeCopay">
<input type="hidden" name="child" value="">
<input type="hidden" name="opType" value="S">
	<!-- E N D : Buttons -->
</div>
</html:form>
</body>
</html>