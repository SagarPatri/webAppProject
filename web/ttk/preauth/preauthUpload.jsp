<%
/**
 * @ (#) policyDocsUploads.java 31 Dec 2014
 * Project      : TTK HealthCare Services
 * File         :policyDocsUploads.java
 * Author       : Rishi Sharma
 * Company      : RCS Technologies
 * Date Created : 27th June 2018
 *
 * @author       : Rishi Sharma
 * Modified by   : Rishi Sharma
 * Modified date : 27th June 2018
 
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ page import=" com.ttk.common.TTKCommon" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<link rel="stylesheet" type="text/css" href="ttk/styles/style.css" />


<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/enrollment/policyDocsUploads.js"></script>
</head>
<html:form action="/UploadPolicyDocs.do"  method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Policy Documents : <bean:write name="frmPolicyUploads" property="caption"/></td>   
    		</tr>
	</table>
	<html:errors/>
	
	<div style="width: 99%; float: right;">

	
	<fieldset>
		<legend>View Policy Documents</legend>
			<div style="PADDING-BOTTOM: 10px; OVERFLOW-X: scroll; OVERFLOW-Y: scroll; WIDTH: 1150px; HEIGHT: 240px" id=scroll width="50%">
			<ttk:PolicyDocsUploadLogs/>
			</div>
	</fieldset>		
	
<br/>
<br/>
<br/>
<center>
	<% 
	String strSubLink = TTKCommon.getActiveSubLink(request);
	
	String strActiveLink = TTKCommon.getActiveLink(request);

	if("Policies".equals(strSubLink))
	{
	%>
<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClosePolicies();"><u>C</u>lose</button>
</center>
<% 
	}
	else if("Processing".equals(strSubLink) && "Pre-Authorization".equals(strActiveLink))
	{
		%>
		<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClosePreAuthProcessing();"><u>C</u>lose</button>
</center>
		
	<% }
	else if("Processing".equals(strSubLink) && "Claims".equals(strActiveLink))
	{
%>
	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseClaimProcessing();"><u>C</u>lose</button>
</center>
<% 
	}
%>
 <INPUT TYPE="hidden" NAME="reforward" value="">	

  <INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>

</div>
</body>
</html>