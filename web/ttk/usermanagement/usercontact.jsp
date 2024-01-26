<%
/**
 * @ (#) usercontact.jsp 10th Jan 2006
 * Project      : TTK HealthCare Services
 * File         : usercontact.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : 10th Jan 2006
 *
 * @author       :
 * Modified by   :Kishor 
 * Modified date :06 jan 2015
 * Reason        :New java implemented for professionals
 */
%>

<%@ page import="com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/usermanagement/usercontact.js"></script>


<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
</head>
<html:form action="/EditUserContact.do" >

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><bean:write name="frmUserContact" property="caption" /></td>
			<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
  	</table>
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
	
	    <ttk:PersonalInformation/>
	    <ttk:UserAccess/>
	    <ttk:AdditionalInformation/>
	   
	  
	    <!-- <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	    <tr><td><div id="applet-container"/></td></tr>
	    </table>  -->
   	<!-- S T A R T : Buttons -->
   	
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    <%
	     if(TTKCommon.isAuthorized(request,"Edit"))
    		{
	    %>
			    <%
			    	if(TTKCommon.isAuthorized(request,"ResetPassword"))
		    		{
			    %>
				    	<logic:notEmpty name="frmUserContact" property="contactSeqID">
					    	<button type="button" name="Button2" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onResetPassword();">Reset <u>P</u>assword</button>&nbsp;
						</logic:notEmpty >
			    <%
			    	}
			    %>
		    	<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
		    	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Reset();"><u>R</u>eset</button>&nbsp;
	    <%
			}
		%>
	    	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Close();"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>
	
	<!-- E N D : Buttons -->
	<html:hidden property="mode"/>
 </div>
 <input type="hidden" name="child" value="ContactDetails">
 <input type="hidden" name="flag" value="">
</html:form>
