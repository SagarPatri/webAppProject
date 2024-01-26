<% 
/**
 * @ (#) icdcodelist.jsp 09th Feb 2006
 * Project      : TTK HealthCare Services
 * File         : icdcodelist.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 09th Feb 2006  
 *
 * @author       : Lancy A
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<script language="javascript" src="/ttk/scripts/enrollment/icdcodelist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>

<!-- S T A R T : Content/Form Area -->	
	<html:form action="/ICDCodeAction.do"  method="post">

	<!-- S T A R T : Page Title -->		
		<logic:match name="frmICDCode" property="switchType" value="ENM">		
			<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		</logic:match>
		<logic:match name="frmICDCode" property="switchType" value="END">
			<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
		</logic:match>
		<tr>
    		<td width="100%"><bean:write name="frmICDCode" property="caption"/></td>   
    		<td align="right">&nbsp;&nbsp;&nbsp;</td>      
    	</tr>
		</table>
	<!-- E N D : Page Title --> 

	<html:errors/>
	
	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="icdCodeData"/> 
	<!-- E N D : Grid -->	

	<!-- S T A R T : Buttons -->
		<br>	
		<table class="buttonsContainerGrid" border="0" cellspacing="0" cellpadding="0" align="center">
	  		<tr>
	    		<td width="27%" align="left"></td>
	    		<td width="73%" align="right">
					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>&nbsp;
	    		</td>
			</tr>
		</table>	
	<!-- E N D : Buttons -->

	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	</html:form>
<!-- E N D : Content/Form Area -->