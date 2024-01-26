<%
/** @ (#) changepassword.jsp Oct 11th, 2010
 * Project       : TTK Healthcare Services
 * File          : changepassword.jsp
 * Author        : Manikanta Kumar G G
 * Company       : Span Systems Corporation
 * Date Created  : Oct 11th, 2010
 
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
<title>Vidal Health Healthcare Administrator - Online Access - Change Password</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/onlineforms/changepassword.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var JS_SecondSubmit=false;
</SCRIPT>
</head>
<SCRIPT LANGUAGE="JavaScript">
function onClose(strLoginType)
{
	if(strLoginType == 'OCO')
	{
		window.open("/ttk/onlineaccess.jsp?loginType=OCO",'_parent',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		
	}//end of if(strLoginType == 'OCO')
	if(strLoginType == 'OHR')
	{
		window.open("/ttk/onlineaccess.jsp?loginType=OHR",'_parent',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}//end of if(strLoginType == 'OHR')

	//kocb
	if(strLoginType == 'OBR')
	{
		window.open("/ttk/onlineaccess.jsp?loginType=OBR",'_parent',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}//end of if(strLoginType == 'OHR')
	if(strLoginType == 'OIU')
	{
		window.open("/ttk/onlineaccess.jsp?loginType=OIU",'_parent',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}//end of if(strLoginType == 'OIU')
	//ADDED AS PER KOC 11PP 1257
	if(strLoginType == 'IBMOHR')
	{
		window.open("/ttk/onlineaccess.jsp?loginType=IBMOHR",'_parent',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}//end of if(strLoginType == 'IBMOHR'))
	if(strLoginType == 'IBMOCO')
	{
		window.open("/ttk/onlineaccess.jsp?loginType=IBMOCO",'_parent',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}//end of if(strLoginType == 'IBMOCO')
//ADDED AS PER KOC 11PP 1257
//added as per Hospitl Login
	if(strLoginType == 'HOS')
	{
		window.open("/ttk/onlineaccess.jsp?loginType=HOS",'_parent',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}//end of if(strLoginType == 'HOS')
	//added as per Hospitl Login
	

}//end of onClose(strLoginType)
function disableCtrlModifer(evt)
{
	var disabled = {a:0, x:0,w:0, n:0, F4:0, j:0};
	var ctrlMod = (window.event)? window.event.ctrlKey : evt.ctrlKey;
	var key = (window.event)? window.event.keyCode : evt.which;
	key = String.fromCharCode(key).toLowerCase();
	return (ctrlMod && (key in disabled))? false : true;
}//end of disableCtrlModifer(evt)

</SCRIPT>

<body class="loginBodyBg" style="background-color:#FFFFFF" onkeypress="return disableCtrlModifer(event);" onkeydown="return disableCtrlModifer(event);">
<html:form action="/OnlineChangePasswordAction.do" method="post">
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="width:100%; background-color:#FFFFFF;">
  		<tr>
  			<td style="border-bottom:2px solid #000;"><img style="padding-top:15px;" src="/ttk/images/TTLLogo.gif" alt="Logo" title="Logo" width="502" height="74"></td>
			<td style="border-bottom:2px solid #000;"></td>
			<td align="right" style="border-bottom:2px solid #000;"><img src="/ttk/images/ttkimage.gif" alt="Logoimage" title="Logoimage" width="297" height="76"></td>
		</tr>
		</table>
 		 <html:errors/>
	   <logic:notEmpty name="updated" scope="request">
	    <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success" width="16" height="16" align="absmiddle">&nbsp;
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
			 <logic:equal name="frmOnlineChangePassword" property="loginType" value="OCO">
                <td class="loginText" width="175" nowrap>Group Id:</td>
                <td align="left" width="170"><html:text name="frmOnlineChangePassword" property="groupId" styleClass="textBoxWeblogin textBoxWebloginDisabled" style="width:150px;"  maxlength="60" readonly="true" /></td>
                </logic:equal>
		<logic:notEqual name="frmOnlineChangePassword" property="loginType" value="OCO">
                 <logic:equal name="frmOnlineChangePassword" property="loginType" value="IBMOCO">
                    <html:hidden name="frmOnlineChangePassword" property="groupId" styleClass="textBoxWeblogin textBoxWebloginDisabled" style="width:150px;"/>
           	  </logic:equal>
           	  <%--added as per Hospital Login 
           	   <logic:equal name="frmOnlineChangePassword" property="loginType" value="HOS">
           	     <td class="loginText" width="175" nowrap>Empanelment Id:</td>
                    <td align="left" width="170"><bean:write name="frmOnlineChangePassword" property="hosEmpaneId"/>                    
                    <html:hidden name="frmOnlineChangePassword" property="hosEmpaneId" styleClass="textBoxWeblogin textBoxWebloginDisabled" style="width:150px;"/></td>
           	  </logic:equal>--%>
           	   <%--added as per Hospital Login --%>
                </logic:notEqual>
                
                      <%--//Added as per KOC 11pp 1257 --%> 
       		  </tr>
              <tr>
	      <logic:notEqual name="frmOnlineChangePassword" property="loginType" value="OCO">
                <logic:equal name="frmOnlineChangePassword" property="loginType" value="IBMOCO">
                  <html:hidden name="frmOnlineChangePassword" property="corpPolicyNo"  styleClass="textBoxWeblogin textBoxWebloginDisabled" style="width:150px;" />
                </logic:equal>
              </logic:notEqual>
              <logic:equal name="frmOnlineChangePassword" property="loginType" value="OCO">
                <td nowrap="nowrap" class="loginText" width="175">Scheme No. :</td>
                <td align="left" width="170"><html:text name="frmOnlineChangePassword" property="corpPolicyNo"  styleClass="textBoxWeblogin textBoxWebloginDisabled" style="width:150px;" maxlength="60" readonly="true" /></td>
              </logic:equal>
              </tr>
              <tr>
                <logic:match name="frmOnlineChangePassword" property="loginType" value="OCO">
                <td nowrap="nowrap"  class="loginText" width="175">User Id:</td>
                <td align="left" width="170"><html:text name="frmOnlineChangePassword" property="userId" styleClass="textBoxWeblogin textBoxWebloginDisabled" style="width:150px;" maxlength="20" readonly="true"  /></td>
                </logic:match>
                  <logic:notMatch name="frmOnlineChangePassword" property="loginType" value="OCO">
                <logic:equal name="frmOnlineChangePassword" property="loginType" value="OHR">
                <td nowrap="nowrap"  class="loginText" width="175">User Id:</td>
                <td align="left" width="170"><html:text name="frmOnlineChangePassword" property="hrUserId" styleClass="textBoxWeblogin textBoxWebloginDisabled" style="width:150px;" maxlength="20" readonly="true" /></td>
                </logic:equal>
                  <%-- //Added as per KOC 11pp--%>
                 <logic:equal name="frmOnlineChangePassword" property="loginType" value="IBMOCO">
                <td nowrap="nowrap"  class="loginText" width="175">User Id:</td>
                <td align="left" width="170"><html:text name="frmOnlineChangePassword" property="userId" styleClass="textBoxWeblogin textBoxWebloginDisabled" style="width:150px;" maxlength="20" readonly="true"  /></td>
                </logic:equal>
               
                 <logic:equal name="frmOnlineChangePassword" property="loginType" value="IBMOHR">
                <td nowrap="nowrap"  class="loginText" width="175">User Id:</td>
                <td align="left" width="170"><html:text name="frmOnlineChangePassword" property="hrUserId" styleClass="textBoxWeblogin textBoxWebloginDisabled" style="width:150px;" maxlength="20" readonly="true" /></td>
                </logic:equal>
                 <%-- Added as per KOC 11pp --%>
                
                <logic:equal name="frmOnlineChangePassword" property="loginType" value="OIU">
                <td nowrap="nowrap"  class="loginText" width="175">User Id:</td>
                <td align="left" width="170"><html:text name="frmOnlineChangePassword" property="insUserId" styleClass="textBoxWeblogin textBoxWebloginDisabled" style="width:150px;" maxlength="20" readonly="true" /></td>
                </logic:equal>

                <%--Hospital Login --%>
                  <logic:equal name="frmOnlineChangePassword" property="loginType" value="HOS">
                  <td nowrap="nowrap"  class="loginText" width="175">User Id:</td>
                <td align="left" width="170"><bean:write name="frmOnlineChangePassword" property="hosUserId"/>  
                <html:hidden name="frmOnlineChangePassword" property="hosUserId" styleClass="textBoxWeblogin textBoxWebloginDisabled" /></td>
                </logic:equal>

                
                <!--  kocb-->
				<logic:equal name="frmOnlineChangePassword" property="loginType" value="OBR">
                <td nowrap="nowrap"  class="loginText" width="175">User Id:</td>
                <td align="left" width="170"><html:text name="frmOnlineChangePassword" property="broUserId" styleClass="textBoxWeblogin textBoxWebloginDisabled" style="width:150px;" maxlength="20" readonly="true" /></td>
                </logic:equal>
                </logic:notMatch>
              </tr>     
	          <tr>
                <td class="loginText" width="175" nowrap>Old Password:</td>
                <td align="left" width="170"><html:password name="frmOnlineChangePassword" property="oldPassword" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="60"/></td>
              </tr>
              <tr>
                <td nowrap="nowrap" class="loginText" width="175">New Password:</td>
                <td align="left" width="150"><html:password name="frmOnlineChangePassword" property="newPassword"  styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60"/></td>
              </tr>
              <tr>
                <td nowrap="nowrap"  class="loginText" width="175">Confirm Password:</td>
                <td align="left" width="150"><html:password name="frmOnlineChangePassword" property="confirmPassword" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" /></td>
              </tr>     
              <tr>
			<td height="8">&nbsp;</td>
	        <td >&nbsp;</td>
	      </tr>
    </table>
    <table width="328px" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0" id="loginButton" style="">
    	<tr>
    	 <logic:notEmpty name="updated" scope="request">
		      <td class="loginText" width="100px">&nbsp;</td>
         </logic:notEmpty>
         <td class="loginText" width="50px">&nbsp;</td>
           <td align="right">
           <logic:empty name="updated" scope="request">
           <button type="button" name="Button" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()">Change <u>P</u>assword</button>&nbsp;
           </logic:empty>
           <logic:empty name="updated" scope="request">
           <td class="loginText">&nbsp;</td>
           </logic:empty>
           <td align="center">
           <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose('<bean:write name= "frmOnlineChangePassword" property="loginType" />')"><u>C</u>lose</button>
           </td>
         </td>
     	</tr>        
   </table>	
<%--Changes as per kOC 1257 11PP --%>
  </br>
       <table align="center"  border="0" cellspacing="0" cellpadding="0">
        <tr align="center">
         <td> 
        <fieldset> <table align="left" border="0" cellspacing="0" cellpadding="0">
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
		  </fieldset>	
		  </td>
		</tr>
      </table>
<input type="hidden" name="mode">
<input type="hidden" name="loginType" value="<bean:write name= "frmOnlineChangePassword" property="loginType" />">
</html:form>
</body>
