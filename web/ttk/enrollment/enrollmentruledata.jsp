<%
/** @ (#) enrollmentruledata.jsp Apr 25th, 2006
 * Project       : TTK Healthcare Services
 * File          : enrollmentruledata.jsp
 * Author        : Unni V Mana
 * Company       : Span Systems Corporation
 * Date Created  : 25th Apr 2006
 * @author 		 : Unni V Mana
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import="com.ttk.common.TTKCommon" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/enrollment/enrollmentruledata.js"></SCRIPT>
	<SCRIPT LANGUAGE="JavaScript">
		var JS_Focus_Disabled =true;
	</script>

	<!-- S T A R T : Content/Form Area -->
	<!-- S T A R T : Page Title -->
	<html:form action="/EnrollmentRuleDataAction.do">
	<logic:match name="frmPolicyList" property="switchType" value="ENM">
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmPolicyList" property="switchType" value="END">
	<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	  <tr>
	    <td>Family Rules - [<bean:write name="frmEnrollmentRule" property="caption"/>]</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentAreaHScroll" id="contentArea">
	<html:errors/>
	<logic:empty name="display">
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
 	<!-- Added Rule Engine Enhancement code : 27/02/2008 -->
	<ttk:BusinessErrors name="BUSINESS_ERRORS" scope="request" />
	<!--  End of Addition -->
	<ttk:ClauseDetails flow="FamilyRule"/>
	<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    <%
		    if(TTKCommon.isAuthorized(request,"Edit"))
			{
		%>
	    	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose('')"><u>C</u>lose</button>&nbsp;
	    <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	    %>
	    </td>
	  </tr>
	</table>
	</logic:empty>
	<logic:notEmpty name="display">
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="100%" align="center">
    			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose('error')"><u>C</u>lose</button>&nbsp;
		    </td>
		  </tr>
		</table>
	</logic:notEmpty>
	</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="Family Rules">
	</html:form>
	<!-- E N D : Content/Form Area -->