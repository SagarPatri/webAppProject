<%
/** @ (#) forgotpassword.jsp Mar 15th, 2010
 * Project       : TTK Healthcare Services
 * File          : forgotpassword.jsp
 * Author        : Balakrishna E
 * Company       : Span Systems Corporation
 * Date Created  : Mar 15th, 2010
 
 * @author 	 	 : Balakrishna E
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<head>
<title>Vidal Healthcare Administrator - Online Access - Forgot Password</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
</head>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<SCRIPT LANGUAGE="JavaScript">

function onClose()
{
var loginType=document.forms[0].loginType.value;
//changes as per IBM CR
	if(loginType == "IBMOCO")
	{
		window.open("/ttk/onlineaccess.jsp?loginType=IBMOCO",'_parent',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		
	}else if(loginType == "OBR"){
		window.open("/ttk/onlineaccess.jsp?loginType=OBR",'_parent',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}
	else if(loginType == "HOS")//as per Hospital Login 
	{
		window.open("/ttk/onlineaccess.jsp?loginType=HOS",'_parent',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		
	}//end of else if(loginType == "HOS") as per Hospital Login 	
	else if(loginType == "PTR")//as per Partner Login 
	{
		window.open("/ttk/onlineaccess.jsp?loginType=PTR",'_parent',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		
	}//end of else if(loginType == "PTR") as per Partner Login 	

	else{
		window.open("/ttk/onlineaccess.jsp?loginType=OCO",'_parent',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}	
}
function onSubmit()
{var loginType=document.forms[0].loginType.value;
if(loginType == "OCO")
{
	if(document.forms[0].groupId.value == "")
	{
		 alert("Please enter Group ID");
		 document.forms[0].groupId.focus();
		 return false;
	}//end if(document.forms[0].groupId.value == "")
	if(document.forms[0].corpPolicyNo.value == "")
	{
		 alert("Please enter Scheme No.");
		 document.forms[0].corpPolicyNo.focus();
		 return false;
	}//end if(document.forms[0].corpPolicyNo.value == "")
	if(document.forms[0].userId.value == "")
	{
		 alert("Please enter User ID");
		 document.forms[0].userId.focus();
		 return false;
	}//end if(document.forms[0].userId.value == "")
}
if(loginType == "IBMOCO")
{
	
	if(document.forms[0].userId.value == "")
	{
		 alert("Please enter User ID");
		 document.forms[0].userId.focus();
		 return false;
	}//end if(document.forms[0].userId.value == "")
	if(document.forms[0].dateOfJoining.value == "")
	{
		 alert("Please enter DateOfJoin");
		 document.forms[0].dateOfJoining.focus();
		 return false;
	}//end if(document.forms[0].dateOfJoining.value == "")
	if(document.forms[0].dateOfBirth.value == "")
	{
		 alert("Please enter DateOfBirth");
		 document.forms[0].dateOfBirth.focus();
		 return false;
	}//end if(document.forms[0].dateOfBirth.value == "")
/*if(isDate(document.forms[0].dateOfBirth,"Date Of Joining")==false)
	{
		document.forms[0].dateOfBirth.focus();
		return false;
	}//end of if(isDate(document.forms[1].billDate,"Bill Date")==false)
	if(isDate(document.forms[0].dateOfJoining,"Date of Join")==false)
	{
		document.forms[0].dateOfJoining.focus();
		return false;
	}//end of if(isDate(document.forms[1].billDate,"Bill Date")==false)*/
	
}
	
	document.forms[0].mode.value="doSave";
	document.forms[0].action="/SavePasswordAction.do";
	document.forms[0].submit();
}//end of onSubmit();
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


<html:form action="/ForgotPasswordAction.do" method="post">

<table width="100%" border="0" cellpadding="0" cellspacing="0" style="width:100%; background-color:#FFFFFF;">
  		<tr>
  			<td style="border-bottom:2px solid #000;"><img style="padding-top:15px;" src="/ttk/images/TTLLogo.gif" alt="Logo" width="502" height="74"></td>
			<td style="border-bottom:2px solid #000;"></td>
			<td align="right" style="border-bottom:2px solid #000;"><img src="/ttk/images/ttkimage.gif" alt="Logoimage" width="297" height="76"></td>
		</tr>
		</table>
		<logic:notEmpty name="display" scope="request">
		  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
		   <tr>
		     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
				 Password communicated through Email.
			</td>
		   </tr>
		  </table>
 		 </logic:notEmpty>
 		 
 		 
 		 <logic:notEmpty name="forgotPass" scope="request">
		  <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
		   <tr>
		     <td><img src="/ttk/images/Error.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
				 Please enter correct Group ID/Scheme Number/User ID.
                                 
			</td>
		   </tr>
		  </table>
 		 </logic:notEmpty>
 		 <logic:notEmpty name="forgotPassibm" scope="request">
		  <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
		   <tr>
		     <td><img src="/ttk/images/Error.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
				 Please enter correct Date Of Birth/Date Of Join/User ID.
			</td>
		   </tr>
		  </table>
 		 </logic:notEmpty>
 		 <%--   Changes as per Hopsital Login --%>
 		<logic:notEmpty name="forgotPassHos" scope="request">
		  <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
		   <tr>
		     <td><img src="/ttk/images/Error.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
				<!--  Please enter correct Empanelment Number / User Id -->
				Please enter correct User Id
			</td>
		   </tr>
		  </table>
 		 </logic:notEmpty>	 
 		 
 		 
 		 <html:errors/>
<logic:equal name="frmForgotPassword" property="loginType" value="IBMOCO">
		<table width="30%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0" id="corporate1" style="">
	          
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
                <td class="loginText" width="125" nowrap>Employee Id:</td>
                <td align="left" width="170"><html:text name="frmForgotPassword" property="userId" styleClass="textBoxWeblogin textBoxWebloginDisabled" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60" readonly="true"/></td>
              </tr>
              <tr>
                <td nowrap="nowrap" class="loginText" width="125">Date Of Birth.:</td>
                <td align="left" width="170"><html:text property="dateOfBirth"  styleClass="textBoxWeblogin" maxlength="10" /></td><td><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[0].dateOfBirth',document.forms[0].dateOfBirth.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            
            </td>
              </tr>
              <tr>
                <td nowrap="nowrap"  class="loginText" width="125">Date Of Joining:</td>
                <td align="left" width="170"><html:text  property="dateOfJoining" styleClass="textBoxWeblogin " maxlength="10" /></td><td><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[0].dateOfJoining',document.forms[0].dateOfJoining.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
              </tr>   
               <tr>
               
                <td align="left" width="170"><html:hidden name="frmForgotPassword" property="groupId" styleClass="textBoxWeblogin textBoxWebloginDisabled" onkeypress="ConvertToUpperCase(event.srcElement);"/></td>
              </tr>
              <tr>
              
                <td align="left" width="170"><html:hidden name="frmForgotPassword" property="corpPolicyNo"  styleClass="textBoxWeblogin textBoxWebloginDisabled" /></td>
              </tr>
              
              
              <tr>
		<td height="8">&nbsp;</td>
	        <td >&nbsp;</td>
	      </tr>
              	  
    </table>
   </logic:equal>
  
   <logic:equal name="frmForgotPassword" property="loginType" value="OCO">
  
		<table width="30%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0" id="corporate1" style="">
	          
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
                <td class="loginText" width="125" nowrap>Group Id:</td>
                <td align="left" width="170"><html:text name="frmForgotPassword" property="groupId" styleClass="textBoxWeblogin textBoxWebloginDisabled" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60" readonly="true"/></td>
              </tr>
              <tr>
                <td nowrap="nowrap" class="loginText" width="125">Scheme No. :</td>
                <td align="left" width="170"><html:text name="frmForgotPassword" property="corpPolicyNo"  styleClass="textBoxWeblogin textBoxWebloginDisabled" maxlength="60" readonly="true"/></td>
              </tr>
              <tr>
                <td nowrap="nowrap"  class="loginText" width="125">User Id:</td>
                <td align="left" width="170"><html:text name="frmForgotPassword" property="userId" styleClass="textBoxWeblogin textBoxWebloginDisabled" maxlength="20" readonly="true"/></td>
              </tr>     
              <tr>
		<td height="8">&nbsp;</td>
	        <td >&nbsp;</td>
	      </tr>
              	  
    </table>
 </logic:equal>
  
 <!-- kocb -->
 <logic:notEmpty name="forgotPassbro" scope="request">
		  <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
		   <tr>
		     <td><img src="/ttk/images/Error.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
				 Please enter correct User ID.
			</td>
		   </tr>
		  </table>
 		 </logic:notEmpty>
 		 
 <logic:equal name="frmForgotPassword" property="loginType" value="OBR">

		<table width="30%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0" style="">
	          
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
                <td nowrap="nowrap" class="loginText" width="125">User Type. :</td>
                <td align="left" width="170">Broker</td>
              </tr>
              <tr>
                <td nowrap="nowrap"  class="loginText" width="125">User Id:</td>
                <td align="left" width="170"><html:text name="frmForgotPassword" property="userId" styleClass="textBoxWeblogin textBoxWebloginDisabled" maxlength="20" readonly="true"/></td>
              </tr>     
              
		<td height="8">&nbsp;</td>
	        <td >&nbsp;</td>
	      </tr>
              	  
    </table>
 </logic:equal>
  
<%--   Changes as per Hopsital Login --%>
 <logic:equal name="frmForgotPassword" property="loginType" value="HOS">
		<table width="30%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0" id="corporate1" style="">
	          
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
	          <%-- <tr>
                <td class="loginText" width="125" nowrap>Emapnelment Id:</td>
                <td align="left" width="170"><html:text name="frmForgotPassword" property="groupId" styleClass="textBoxWeblogin textBoxWebloginDisabled" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60" readonly="true"/></td>
              </tr> --%>
               <tr>
                <td nowrap="nowrap" class="loginText" width="125">User Type. :</td>
                <td align="left" width="170">Provider</td>
              </tr>
              <tr>
                <td nowrap="nowrap"  class="loginText" width="125">User Id:</td>
                <td align="left" width="170"><html:text name="frmForgotPassword" property="userId" styleClass="textBoxWeblogin textBoxWebloginDisabled" maxlength="20" readonly="true"/></td>
              </tr>    
        		<tr> 
               <td height="8">&nbsp;</td>
               		<td align="left" width="170"><html:hidden name="frmForgotPassword" property="corpPolicyNo"  styleClass="textBoxWeblogin textBoxWebloginDisabled" /></td>
               									<html:hidden name="frmForgotPassword" property="groupId"  styleClass="textBoxWeblogin textBoxWebloginDisabled" /></td>
               </tr>
              <tr>
				<td height="8">&nbsp;</td>
	        	<td >&nbsp;</td>
	      </tr>
              	  
    </table>
 </logic:equal>
 <%--   Changes as per Hopsital Login --%>
 
  <%--   Changes as per Partner Login --%>
 <logic:equal name="frmForgotPassword" property="loginType" value="PTR">
		<table width="30%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0" id="corporate1" style="">
	          
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
                <td nowrap="nowrap" class="loginText" width="125">User Type. :</td>
                <td align="left" width="170">Partner</td>
              </tr>
              <tr>
                <td nowrap="nowrap"  class="loginText" width="125">User Id:</td>
                <td align="left" width="170"><html:text name="frmForgotPassword" property="userId" styleClass="textBoxWeblogin textBoxWebloginDisabled" maxlength="20" readonly="true"/></td>
              </tr>    
        		<tr> 
               <td height="8">&nbsp;</td>
               		<td align="left" width="170"><html:hidden name="frmForgotPassword" property="corpPolicyNo"  styleClass="textBoxWeblogin textBoxWebloginDisabled" />
               		<html:hidden name="frmForgotPassword" property="groupId"  styleClass="textBoxWeblogin textBoxWebloginDisabled" /></td>
               </tr>
              <tr>
				<td height="8">&nbsp;</td>
	        	<td >&nbsp;</td>
	      </tr>
              	  
    </table>
 </logic:equal>

    <table width="30%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0" id="loginButton" style="">
    <tr>
               	<logic:empty name="display" scope="request">
               	<td class="loginText" width="125">&nbsp;</td>
                   <td align="left">
                   <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSubmit()"><u>S</u>ubmit</button>&nbsp;
                   </logic:empty>
                   <logic:notEmpty name="display" scope="request">
                   <td class="loginText" width="175">&nbsp;</td>
                   <td align="center">
                   </logic:notEmpty>
                   <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
                   </td>
               </tr>        
     </table>	
<input type="hidden" name="mode">
<input type="hidden" name="loginType" value="<bean:write name="frmForgotPassword" property="loginType"/>"/>
</html:form>
</body>
