<%
/** @ (#) changepassword.jsp 20th April 2006
 * Project     : TTK Healthcare Services
 * File        : changepassword.jsp
 * Author      : Krupa J
 * Company     : Span Systems Corporation
 * Date Created: 20th April 2006
 *
 * @author 		 :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon" %>


<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
function onChangePassword()
{
	if(!JS_SecondSubmit)
	{
	    document.forms[1].mode.value="doSave";
		document.forms[1].action = "/SavePswdAction.do";
		JS_SecondSubmit=true	
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
}//end of onChangePassword()

</SCRIPT>
<%
    boolean viewmode=true;
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
        viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
 %>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/ChangePswdAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="1" cellpadding="1">
  <tr>

    <td>Change Password</td>
	<td width="43%" align="right" class="webBoard">&nbsp;</td>
  </tr>
</table>
	<!-- E N D : Page Title -->

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
	
<div class="contentArea" id="contentArea">
<html:errors/>	
<fieldset>
<legend>Password Details </legend>
<table align="center" class="formContainer" border="0" cellspacing="1" cellpadding="1">
  <tr>
    <td width="20%" class="formLabel">Old Password: <span class="mandatorySymbol">*</span></td>
    <td width="80%" class="textLabelBold">
    <html:password property="oldPassword"  styleClass="textBox textBoxMedium" maxlength="20" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
    </td>
    </tr>
  <tr>
    <td class="formLabel">New Password: <span class="mandatorySymbol">*</span></td>
    <td class="textLabel">
      <html:password property="newPassword" styleClass="textBox textBoxMedium" maxlength="20" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
    </td>
    </tr>
  <tr>
    <td class="formLabel">Confirm Password: <span class="mandatorySymbol">*</span></td>
    <td class="textLabel">
    <html:password property="confirmPassword" styleClass="textBox textBoxMedium" maxlength="20" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
    </tr>
    </table>
	</fieldset>

	<!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <%
	    if(TTKCommon.isAuthorized(request,"Edit"))
	    {
				
	%>

    		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onChangePassword()"><u>C</u>hange Password</button>&nbsp;</td>

    <%
    	}// end of if(TTKCommon.isAuthorized(request,"Edit"))
	 %></tr>
</table>
<%--Changes as per kOC 1257 11PP --%>
</br>
       <table align="left"  border="0" cellspacing="0" cellpadding="0">
        <tr align="center">
         <td> 
        <%-- <fieldset>--%> <table align="left" border="0" cellspacing="0" cellpadding="0">
        <tr align="left">
          <td> 
         <font color="#FF0000" size="2"><b>Note</b>
         </font>
         </td>
         </tr>
          <tr align="left">
        <ui><td align="left">
        <font color="#FF0000" size="1">
        <li>Please give atleast one- Uppercase, Lowercase, Numeric, Special Character !@#$%^&*(){}[].</li></br><li>New password should not be less than 8 characters and should not be longer than 16 characters.</li></font>
		</ui></td>
		 </tr>
		  </table>
		  <%-- </fieldset>--%>	
		  </td>
		</tr>
		  </table>
</div>
<!-- E N D : Buttons -->
	<html:hidden property="mode" />

</html:form>
	<!-- E N D : Content/Form Area -->