<%
/**
 * @ (#) changepassword.jsp jan 08, 2016
 * Project      : TTK HealthCare Services
 * File         : changepassword.jsp
 * Author       : Nagababu K
 * Company      : RCS
 * Date Created : jan 08, 2016
 *
 * @author       : Nagababu K
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon" %>
<script type="text/javascript" src="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/onlineforms/brokerLogin/changepassword.js"></script>
<!-- S T A R T : Content/Form Area -->
<head>
</head>
	<html:form action="/UpdateBrokerPasswordChangeAction.do">
	
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
<html:errors/>
<div id="sideHeading">Password Change</div>	
<fieldset>
<legend>Password Details </legend>
<table class="formContainer" style="margin-left: 30%; border: 0; padding: 0;border-collapse: collapse;">
  <tr>
    <td width="20%" class="formLabel">Old Password: <span class="mandatorySymbol">*</span></td>
    <td width="80%" class="textLabelBold">
    <html:password property="oldPassword"  styleClass="textBox textBoxMedium" maxlength="20"/>
    </td>
    </tr>
  <tr>
    <td class="formLabel">New Password: <span class="mandatorySymbol">*</span></td>
    <td class="textLabel">
      <html:password property="newPassword" styleClass="textBox textBoxMedium" maxlength="20"/>
    </td>
    </tr>
  <tr>
    <td class="formLabel">Confirm Password: <span class="mandatorySymbol">*</span></td>
    <td class="textLabel">
    <html:password property="confirmPassword" styleClass="textBox textBoxMedium" maxlength="20"/></td>
    </tr>
    <tr>
    <td colspan="2">&nbsp;</td>
    </tr>
    <tr>	
    <td></td>           	
        <td>	            
           <button type="button" name="mybutton" accesskey="c"  class="olbtnLarge" onClick="onChangePassword();"><span class="accessLetter">C</span>hange Password</button>
        </td>
	</tr>  
    </table>
	</fieldset>
<br>
	<table>
	<tr>
	<td style="color: red;">Note*</td>
	</tr>	
	<tr>
	<td>
	<ul>
	<li style="background-color: yellow;">* Please give atleast one- Uppercase, Lowercase, Numeric, Special Character &#33;&#64;&#36;&#38;%^*(){}[].</li>
	<li style="background-color: yellow;">* New password should not be less than 8 characters and should not be longer than 16 characters.</li>
	</ul>
	</td>
	</tr>
	</table>
	<table style="margin-left: 45%;">
	<tr>
	<td><img title="Back" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" onclick="onBack();" href="#" title="Back">Back</a></td>
	</tr>
	</table>
	<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
</html:form>
<!-- E N D : Content/Form Area -->	

	<!-- E N D : Content/Form Area -->