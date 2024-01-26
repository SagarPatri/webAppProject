<%
/** @ (#) changepassword.jsp Feb 08th, 2011
 * Project       : TTK Healthcare Services
 * File          : changepassword.jsp
 * Author        : Manikanta Kumar G G
 * Company       : Span Systems Corporation
 * Date Created  : Feb 08th, 2011
 
 * @author 	 	 : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
 <%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<head>
<title>Vidal Healthcare Administrator - Change Password</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/common/changepassword.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var JS_SecondSubmit=false;
</SCRIPT>
</head>
<SCRIPT LANGUAGE="JavaScript">
function disableCtrlModifer(evt)
{
	var disabled = {a:0, x:0,w:0, n:0, F4:0, j:0};
	var ctrlMod = (window.event)? window.event.ctrlKey : evt.ctrlKey;
	var key = (window.event)? window.event.keyCode : evt.which;
	key = String.fromCharCode(key).toLowerCase();
	return (ctrlMod && (key in disabled))? false : true;
}//end of disableCtrlModifer(evt)
</SCRIPT>
<body class="loginBodyBg" style="background-color:#edf4ff" onkeypress="return disableCtrlModifer(event);" onkeydown="return disableCtrlModifer(event);">
<html:form action="/ChangepasswordAction.do" method="post">
<table width="100%"  border="0" cellspacing="0" class="header" cellpadding="0">
  <tr>
    <td width="36%">
    
                <img src="/ttk/images/VINS.gif" width="175" height="25" alt="Vidal Health Administrator Services">
                <div style="font-size: 8px;padding: 1px 1px 1px 14px;font-family: Arial, Helvetica, sans-serif;color:#4d4d4d;">(Vidal Insurance Network Global Services)</div>
    </td>
  </tr>
  <tr>
    <td colspan="2" class="headerSeparator" nowrap></td>
  </tr>
  <tr>
    <td colspan="2" class="headerLowerBand" nowrap></td>
  </tr>
</table>
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Change Password</td>
	  </tr>
	</table>
 <html:errors/>
 
  <logic:notEmpty name="updated" scope="request">
   <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
         <bean:message name="updated" scope="request"/>
       </td>
     </tr>
   </table>
  </logic:notEmpty>
  		<table width="30%" align="center"  border="0" cellspacing="0" cellpadding="0" id="corporate1" style="">
  		<tr>
		 <td width="125" nowrap>&nbsp;</td>
		 <td width="170" align="right">&nbsp;</td>
		</tr>
		<tr>
		 <td class="loginText">&nbsp;</td>
		 <td align="right">&nbsp;</td>
		</tr>
		<tr>
		 <td  class="loginText">&nbsp;</td>
		 <td  align="right">&nbsp;</td>
	    </tr>
  		<tr>
  		<td nowrap="nowrap"  class="loginText" width="175">User Id:</td>
           <td align="left" width="170"><html:text name="frmChangepassword" property="userId" styleClass="textBoxWeblogin textBoxWebloginDisabled" style="width:150px;" maxlength="20" readonly="true"  /></td>
  		</tr>
  		<tr>
         <td class="loginText" width="175" nowrap>Old Password:</td>
         <td align="left" width="170"><html:password name="frmChangepassword" property="oldPassword" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="60"/></td>
        </tr>
        <tr>
         <td nowrap="nowrap" class="loginText" width="175">New Password:</td>
         <td align="left" width="150"><html:password name="frmChangepassword" property="newPassword"  styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60"/></td>
        </tr>
        <tr>
         <td nowrap="nowrap"  class="loginText" width="175">Confirm Password:</td>
         <td align="left" width="150"><html:password name="frmChangepassword" property="confirmPassword" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" /></td>
        </tr>  
  		</table>
  		<table width="35%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0" id="loginButton" style="margin-top: 30px;" >
    	<tr>
    	 <logic:empty name="updated" scope="request">
         <td class="loginText" width="600">&nbsp;</td>
         </logic:empty>
         <logic:notEmpty name="updated" scope="request">
	          <td class="loginText" width="223">&nbsp;</td>
         </logic:notEmpty>
         <logic:empty name="updated" scope="request">
         <td align="right">
           <button type="button" name="Button" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()">Change <u>P</u>assword</button>&nbsp;
         </td>
         <td class="loginText" width="250">&nbsp;</td>
         </logic:empty>
         <td >
           <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
         </td>
     	</tr>        
   </table>
   <input type="hidden" name="mode">
</html:form>
</body>