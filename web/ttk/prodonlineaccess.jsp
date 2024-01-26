<%
/** @ (#) onlineaccess.jsp Sep 25th, 2007
 * Project       : TTK Healthcare Services
 * File          : onlineaccess.jsp
 * Author        : Chandrasekaran J
 * Company       : Span Systems Corporation
 * Date Created  : Sep 25th, 2007
 * @author 		 : Chandrasekaran J
 * Modified by   : Ramakrishna K M
 * Modified date : 
 * Reason        : Insurance Login
 */
 %>
<head>
<title>Vidal Healthcare Administrator - Online Access</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
</head>
<script language="javascript">
function onLogin()
{
    trimForm(document.forms[0]);
    var loginTypeID = document.forms[0].loginType.value;
    if(loginTypeID =='OIN' || loginTypeID =='OCO' || loginTypeID =='OHR' || loginTypeID =='OIU' || loginTypeID =='OCI'){
    	document.forms[0].mode.value="doLogin";
    }//end of if(loginTypeID =='OIN' && loginTypeID =='OCO' && loginTypeID =='OHR' || loginTypeID =='OIU' || loginTypeID =='OCI')
    else{
    	alert("Login Type is Required");
        return false;
    }//end of else
}//end of onLogin()

function onClose()
{
   	document.forms[0].logmode.value="Close";
    document.forms[0].action = "/CloseOnlineAccessAction.do";
    document.forms[0].submit();
    
}//end of onClose()
function onForgotPassword()
{
	var param1="&GroupID="+document.forms[0].groupId.value;
	var param2="&PolicyNo="+document.forms[0].corpPolicyNo.value;
	var param3="&UserId="+document.forms[0].userId.value;	
	var param = param1+param2+param3;
	openFullScreenMode("/ForgotPasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
}//end of onForgotPassword()
function onChangePassword()
{
	var loginType=document.forms[0].loginType.value;
	if(loginType=='OCO')
	{
	if(document.forms[0].groupId.value == "")
	{
		alert("Please enter GroupId");
		document.forms[0].groupId.focus();
		return false;
	}//end of if(document.forms[0].groupId.value == "")
	if(document.forms[0].corpPolicyNo.value == "")
	{
		alert("Please enter PolicyNo.");
		document.forms[0].corpPolicyNo.focus();
		return false;
	}//end of if(document.forms[0].corpPolicyNo.value == "")
	if(document.forms[0].userId.value == "")
	{
		alert("Please enter UserID");
		document.forms[0].userId.focus();
		return false;
	}//end of if(document.forms[0].userId.value == "")
	var param1="&GroupID="+document.forms[0].groupId.value;
	var param2="&PolicyNo="+document.forms[0].corpPolicyNo.value;
	var param3="&UserId="+document.forms[0].userId.value;
	var param4 ="&LoginType="+loginType;	
	var param = param1+param2+param3+param4;
	openFullScreenMode("/OnlineChangePasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}//end of if(loginType=='OCO')
	if(loginType=='OHR')
	{
		var param1="&HRGroupID="+document.forms[0].hrGroupId.value;
		var param2="&HRUserId="+document.forms[0].hrUserId.value;
		var param3 ="&LoginType="+loginType;	
		var param = param1+param2+param3;
		openFullScreenMode("/OnlineChangePasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}//end of if(loginType=='OHR')
	if(loginType=='OIU')
	{
		var param1="&InsCompCode="+document.forms[0].insCompCode.value;
		var param2="&InsUserId="+document.forms[0].insUserId.value;
		var param3 ="&LoginType="+loginType;	
		var param = param1+param2+param3;
		openFullScreenMode("/OnlineChangePasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}//end of if(loginType=='OIU')

		//kocb
	if(loginType=='OBR')
	{
		
		var param2="&BroUserId="+document.forms[0].broUserId.value;
		var param3 ="&LoginType="+loginType;	
		var param = param2+param3;
		openFullScreenMode("/OnlineChangePasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}//end of if(loginType=='OIU')
}//end of onChangePassword()

//For Production the below code should be uncommented
function onBlcUser()
{
	var loginType=document.forms[0].loginType.value;
	if(document.forms[0].loginType.value=="")
	{
		alert("Please select Login Type");
		return false;
	}//end of if(document.forms[0].loginType.value=="")
	else
	{
		if(loginType=="OIN")
		{
	  		openFullScreenMode("https://www.vidalhealthtpa.com/corp_signin.asp?ltype=IndPol",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		}//end of if(loginType=="OIN")
		else if(loginType=="OCO")
		{
	   		openFullScreenMode("https://www.vidalhealthtpa.com/corp_signin.asp?ltype=CompLog&lsubtype=EmpLog",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		}//end of else if(loginType=="OCO")
		else if(loginType=="OHR")
		{
	  		openFullScreenMode("https://www.vidalhealthtpa.com/corp_signin.asp?ltype=CompLog",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		}//end of else if(loginType=="OHR")
		else if(loginType=="OIU")
		{
	    	openFullScreenMode("https://www.vidalhealthtpa.com/corp_signin.asp?ltype=InsLog",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		}//end of else if(loginType=="OIU")
	}//end of else
}//end of onBlcUser()

function onIBMUser()
{
  openFullScreenMode("https://weblogin.vidalhealthtpa.com/index.htm",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
}//end of onIBMUser()

//For Test Server
/*function onBlcUser()
{
	var loginType=document.forms[0].loginType.value;
	if(document.forms[0].loginType.value=="")
	{
		alert("Please select Login Type");
		return false;
	}//end of if(document.forms[0].loginType.value=="")
	else
	{
		if(loginType=="OIN")
		{
	  		openFullScreenMode("http://10.1.10.62/ttk/corp_signin.asp?ltype=IndPol",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		}//end of if(loginType=="OIN")
		else if(loginType=="OCO")
		{
	   		openFullScreenMode("http://10.1.10.62/ttk/corp_signin.asp?ltype=CompLog",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		}//end of else if(loginType=="OCO")
		else if(loginType=="OHR")
		{
	  		openFullScreenMode("http://10.1.10.62/ttk/corp_signin.asp?ltype=CompLog",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		}//end of else if(loginType=="OHR")
		else if(loginType=="OIU")
		{
	    	openFullScreenMode("http://10.1.10.62/ttk/corp_signin.asp?ltype=InsLog",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		}//end of else if(loginType=="OIU")

	}//end of else
}//end of onBlcUser()

function onIBMUser()
{
  openFullScreenMode("http://10.1.10.62/ibmweb/",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
}//end of onIBMUser()*/

function onDisable(val)
{
	if(val=='Y')
	{
		document.getElementById("enrollmentId").disabled=true;
		document.getElementById("enrollmentId").className="textBoxWeblogin textBoxMediumWeblogin textBoxWebloginDisabled";
		document.getElementById("indPolicyNo").disabled=false;
		document.getElementById("indPolicyNo").className="textBoxWeblogin textBoxMediumWeblogin";
		document.getElementById("enrollmentId").value="";
	}//end of if(val=='Y')
	else
	{
		document.getElementById("indPolicyNo").disabled=true;
		document.getElementById("indPolicyNo").className="textBoxWeblogin textBoxMediumWeblogin textBoxWebloginDisabled";
		document.getElementById("enrollmentId").disabled=false;
		document.getElementById("enrollmentId").className="textBoxWeblogin textBoxMediumWeblogin";
		document.getElementById("indPolicyNo").value="";
	}//end of else
}//end of onDisable(val)

</script>
<SCRIPT LANGUAGE="JavaScript">
var submitted = false;
function SubmitTheForm() {
	if(submitted == true) { return; }
	//document.forms[0].mode.value="doLogin";
	//document.forms[0].submit();
	trimForm(document.forms[0]);
    var loginTypeID = document.forms[0].loginType.value;
    if(loginTypeID =='OIN' || loginTypeID =='OCO' || loginTypeID =='OHR' || loginTypeID =='OIU' || loginTypeID =='OCI'){
    	document.forms[0].mode.value="doLogin";
    }//end of if(loginTypeID =='OIN' && loginTypeID =='OCO' && loginTypeID =='OHR' || loginTypeID =='OIU' || loginTypeID =='OCI')
    else{
    	alert("Login Type is Required");
        return false;
    }//end of else
	document.forms[0].mybutton.value = 'Please wait..';
	document.forms[0].mybutton.disabled = true;
	submitted = true;
}
</SCRIPT>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%
	pageContext.setAttribute("loginTypeList",Cache.getCacheObject("onlineAccess"));
%>
<body class="loginBodyBg" style="background-color:#FFFFFF">

<%
	pageContext.setAttribute("Login_Type",TTKCommon.checkNull(request.getParameter("loginType")));
%>	

<html:form action="/OnlineAccessAction.do" onsubmit="SubmitTheForm()">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="width:100%; background-color:#FFFFFF;">
  		<tr>
  			<td style="border-bottom:2px solid #000;"><img style="padding-top:15px;" src="/ttk/images/TTLLogo.gif" alt="Logo" width="502" height="74"></td>
			<td style="border-bottom:2px solid #000;"></td>
			<td align="right" style="border-bottom:2px solid #000;"><img src="/ttk/images/ttkimage.gif" alt="Logoimage" width="297" height="76"></td>
		</tr>
	</table>
	<html:errors/>
	
	<%
	if(request.getParameter("sessionexpired")!=null)
	{
%>
	  <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/ErrorIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message key="error.onlinesession"/>
	     </td>
	   </tr>
	  </table>
<%
	}//end of if(request.getParameter("sessionexpired")!=null)
%>	
	
	<table width="25%"  border="0" cellspacing="0" cellpadding="0" align="center" valign="middle" >
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
	</table>
	<logic:equal name="Login_Type" value="OIN">
	    <table width="25%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0">
	    	<%
				if(request.getAttribute("tmp")==null)
				{
			 %>
			 <tr>
			 <td class="loginText">
			 <INPUT TYPE="radio"  NAME="temp" VALUE="Y" CHECKED onclick="onDisable('Y')" >Scheme No.
			 </td>
			 <td class="loginText">
			 <INPUT TYPE="radio" NAME="temp" VALUE="N" onclick="onDisable('N')" >Enrollment Id
			 </td>
			 </tr>
			 <%
				}//end of if(request.getAttribute("tmp")==null)
			 %>
			 <%
				if(request.getAttribute("tmp")!=null && request.getAttribute("tmp").equals("enrerr"))
				{
			 %>
			 <tr>
			 <td class="loginText">
			 <INPUT TYPE="radio"  NAME="temp" VALUE="Y"  onclick="onDisable('Y')" >Scheme No.
			 </td>
			 <td class="loginText">
			 <INPUT TYPE="radio" NAME="temp" VALUE="N" checked="checked" onclick="onDisable('N')" >Enrollment Id
			 </td>
			 </tr>
			 <%
				}//end of if(request.getAttribute("tmp")!=null && request.getAttribute("tmp").equals("enrerr"))
			 %>
			 <%
				if(request.getAttribute("tmp")!=null && request.getAttribute("tmp").equals("polerr"))
				{
			 %>
			  <tr>
			 <td class="loginText">
			 <INPUT TYPE="radio"  NAME="temp" VALUE="Y" CHECKED onclick="onDisable('Y')" >Scheme No.
			 </td>
			 <td class="loginText">
			 <INPUT TYPE="radio" NAME="temp" VALUE="N" onclick="onDisable('N')" >Enrollment Id
			 </td>
			 </tr>
			 <%
				}//end of if(request.getAttribute("tmp")!=null && request.getAttribute("tmp").equals("polerr"))
			 %>
			 <%
				if(request.getAttribute("tmp")==null)
				{
			 %>
			 <tr>
               <td class="loginText" width="125" nowrap >Scheme No. :</td>
               <td align="left" width="170">
               	<html:text property="indPolicyNo"  styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="60"/>
               </td>
             </tr>
             <tr>
               <td nowrap class="loginText" width="125"> Enrollment Id:</td>
               <td align="left" width="125">
               	<html:text property="enrollmentId"  styleClass="textBoxWeblogin textBoxMediumWeblogin textBoxWebloginDisabled" disabled="true" maxlength="60"/>
               </td>
             </tr>
             <%
				}//end of if(request.getAttribute("tmp")==null)
             %>
			 </tr>
			 <%
				if(request.getAttribute("tmp")!=null && request.getAttribute("tmp").equals("enrerr"))
				{
			 %>
             <tr>
               <td class="loginText" width="125" nowrap >Scheme No. :</td>
               <td align="left" width="170">
               	<html:text property="indPolicyNo"  styleClass="textBoxWeblogin textBoxMediumWeblogin textBoxWebloginDisabled" disabled="true" maxlength="60"/>
               </td>
             </tr>
             <tr>
               <td nowrap class="loginText" width="125"> Enrollment Id:</td>
               <td align="left" width="125">
               	<html:text property="enrollmentId"  styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="60"/>
               </td>
             </tr>
             <%
				}//end of if(request.getAttribute("tmp")!=null && request.getAttribute("tmp").equals("enrerr"))
             %>
             <%
				if(request.getAttribute("tmp")!=null && request.getAttribute("tmp").equals("polerr"))
				{
			 %>
			 <tr>
               <td class="loginText" width="125" nowrap >Scheme No. :</td>
               <td align="left" width="170">
               	<html:text property="indPolicyNo"  styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="60"/>
               </td>
             </tr>
             <tr>
               <td nowrap class="loginText" width="125"> Enrollment Id:</td>
               <td align="left" width="125">
               	<html:text property="enrollmentId"  styleClass="textBoxWeblogin textBoxMediumWeblogin textBoxWebloginDisabled" disabled="true" maxlength="60"/>
               </td>
             </tr>
             <%
				}//end of if(request.getAttribute("tmp")!=null && request.getAttribute("tmp").equals("polerr"))
             %>
             <tr>
              	<td height="8">&nbsp;</td>
                <td >&nbsp;</td>
  			  </tr>
  			  <tr>
	           		<td class="loginText">&nbsp;</td>
	               <td align="right">
	               <input type="submit" name="mybutton" value="Login" class="buttons" onmouseout="this.className='buttons'" onmouseover="this.className='buttons buttonsHover'"/>&nbsp;
	               <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="window.close()"><u>C</u>lose</button>
	               </td>
	           </tr>
         </table>
    </logic:equal>     
	<logic:equal name="Login_Type" value="OCO">
		<table width="25%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="loginText" width="125" nowrap>Group Id:</td>
                <td align="right" width="170"><html:text property="groupId" styleClass="textBoxWeblogin textBoxMediumWeblogin" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60"/></td>
                <td></td>
              </tr>
              <tr>
                <td nowrap="nowrap" class="loginText" width="125">Scheme No. :</td>
                <td align="right" width="170"><html:text property="corpPolicyNo"  styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60"/></td>
                <td align="center" class="loginText" width="50" nowrap><a href="#" onClick=window.open("/ttk/common/showpolicylist.jsp?GroupID="+document.forms[0].groupId.value+"",'',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350")><img src="/ttk/images/ViewDetails.gif" alt="View Policies" width="16" height="16" border="0"></a></td>
              </tr>
              <tr>
                <td nowrap="nowrap"  class="loginText" width="125">User Id:</td>
                <td align="right" width="170"><html:text property="userId" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="20" /></td>
				<td></td>              
              </tr>
              <tr>
                <td nowrap="nowrap" class="loginText" width="125">Password: </td>
                <td align="right" width="170"><input name="password" type="password" class="textBoxWeblogin textBoxMediumWeblogin" id="LoginId32" maxlength="20"></td>
                <td></td>
              </tr>
              <tr>
				<td height="8">&nbsp;</td>
	        	<td >&nbsp;</td>
              </tr>
  			  <tr>
	           		<td class="loginText">&nbsp;</td>
	               <td align="left">
	               <input type="submit" name="mybutton" value="Login" class="buttons" onmouseout="this.className='buttons'" onmouseover="this.className='buttons buttonsHover'"/>&nbsp;
	               <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="window.close()"><u>C</u>lose</button>
	               </td>
	           </tr>
    	</table>
    	 <table width="30%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0" id="corporate1forgotpassword" style="">
          <tr>
           <td height="50" class="loginText" width="112">&nbsp;</td>
           <td align="left" nowrap="nowrap" class="loginText">
            <a href="#"  onClick="javascript:onForgotPassword()" class="events">Forgot Password</a>
           <td>
           <td height="50" class="loginText" width="112">&nbsp;</td>
           <td align="left" nowrap="nowrap" class="loginText">
            <a href="#"  onClick="javascript:onChangePassword()" class="events">Change Password</a>
           <td>
          </tr>
  	 </table>
    </logic:equal>
    <logic:equal name="Login_Type" value="OHR">
		<table width="25%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="loginText" width="125" nowrap>Group Id:</td>
                <td align="right" width="170"><html:text property="hrGroupId" styleClass="textBoxWeblogin textBoxMediumWeblogin"  onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60"/></td>
              </tr>
              <tr>
                <td nowrap="nowrap" class="loginText" width="125">User Id:</td>
                <td align="right" width="170"><html:text property="hrUserId" styleClass="textBoxWeblogin textBoxMediumWeblogin"   maxlength="20" /></td>
              </tr>
              <tr>
                <td nowrap="nowrap" class="loginText" width="125">Password: </td>
                <td align="right" width="170"><input name="hrPassword" type="password" class="textBoxWeblogin textBoxMediumWeblogin"  id="LoginId32" maxlength="20"></td>
              </tr>
              <tr>
	           		<td class="loginText">&nbsp;</td>
	               <td align="left">
	               <input type="submit" name="mybutton" value="Login" class="buttons" onmouseout="this.className='buttons'" onmouseover="this.className='buttons buttonsHover'"/>&nbsp;
	               <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="window.close()"><u>C</u>lose</button>
	               </td>
	           </tr>
        </table>
    </logic:equal>
    <logic:equal name="Login_Type" value="OIU">
		<table width="25%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0">
		      <tr>
                <td nowrap="nowrap" class="loginText" width="125">Ins Company Code:</td>
                <td align="right" width="170"><html:text property="insCompCode" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="20" /></td>
              </tr>
               <tr>
                <td nowrap="nowrap" class="loginText" width="125">User Id:</td>
                <td align="right" width="170"><html:text property="insUserId" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="20" /></td>
              </tr>
              <tr>
                <td nowrap="nowrap" class="loginText" width="125">Password: </td>
                <td align="right" width="170"><input name="insPassword" type="password" class="textBoxWeblogin textBoxMediumWeblogin"  id="LoginId32" maxlength="20"></td>
              </tr>
              <tr>
              	<td height="8">&nbsp;</td>
                <td >&nbsp;</td>
  			  </tr>
              <tr>
	           	<td class="loginText">&nbsp;</td>
	            <td align="left">
	               <input type="submit" name="mybutton" value="Login" class="buttons" onmouseout="this.className='buttons'" onmouseover="this.className='buttons buttonsHover'"/>&nbsp;
	               <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="window.close()"><u>C</u>lose</button>
	            </td>
	          </tr>
	    </table>
    </logic:equal>
    <logic:equal name="Login_Type" value="OCI">
		<table width="25%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td class="loginText" width="125" nowrap>Certificate No.:</td>
                <td align="right" width="170"><html:text property="cityCertificateNbr" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="60" /></td>
              </tr>
              <tr>
                <td nowrap="nowrap" class="loginText" width="125">Scheme Name:</td>
                <td align="right" width="170"><html:text property="citySchemeName" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="60" /></td>
              </tr>
              <tr>
                <td nowrap="nowrap" class="loginText" width="125">Enrollment Id: </td>
                <td align="right" width="170"><html:text property="cityEnrollmentId"  styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60"/>
              </tr>
              <tr>
	           		<td class="loginText">&nbsp;</td>
	               <td align="left">
	               <input type="submit" name="mybutton" value="Login" class="buttons" onmouseout="this.className='buttons'" onmouseover="this.className='buttons buttonsHover'"/>&nbsp;
	               <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="window.close()"><u>C</u>lose</button>
	               </td>
	           </tr>
        </table>
    </logic:equal>
    <logic:notEmpty name="Login_Type" >
    <logic:notEqual name="Login_Type" value="OCI">         
    	<table width="30%" align="center"  border="0" cellspacing="0" cellpadding="0" id="blcuser" style="margin-top:40px;">
    	       <tr>
    	         <td align="center" nowrap="nowrap" class="loginText">
    	        	 <B>For BLC Users,&nbsp;Please&nbsp;</B><a href="#"  onClick="javascript:onBlcUser()" class="events"><u>Click Here</u></a>
    	         </td>
    	       </tr>
	   </table>
	</logic:notEqual>
	</logic:notEmpty>
	<logic:equal name="Login_Type" value="OHR">         
    	<table width="30%" align="center"  border="0" cellspacing="0" cellpadding="0" id="ibmuser" style="margin-top:10px;">
    	       <tr>
    	         <td align="center" nowrap="nowrap" class="loginText">
    	        	 <B>For IBM Users,&nbsp;Please&nbsp;</B><a href="#"  onClick="javascript:onIBMUser()" class="events"><u>Click Here</u></a>
    	         </td>
    	       </tr>
	   </table>
	</logic:equal>
	<logic:equal name="Login_Type" value="OCO">         
    	<table width="30%" align="center"  border="0" cellspacing="0" cellpadding="0" id="ibmuser" style="margin-top:10px;">
    	       <tr>
    	         <td align="center" nowrap="nowrap" class="loginText">
    	        	 <B>For IBM Users,&nbsp;Please&nbsp;</B><a href="#"  onClick="javascript:onIBMUser()" class="events"><u>Click Here</u></a>
    	         </td>
    	       </tr>
	   </table>
	</logic:equal>
	
<input type="hidden" name="mode">
<input type="hidden" name="loginType" value="<%=request.getParameter("loginType")%>">
<input type="hidden" name="open" value="<%=TTKCommon.checkNull(request.getParameter("open"))%>">
<logic:notMatch name="frmOnlineAccess" property="firstLoginYN" value="Y">
<logic:notMatch name="frmOnlineAccess" property="pwdExpiryYN" value="Y">
<logic:notEmpty name="OpenPage">
	<script language="javascript">
			openWebFullScreenMode("<bean:write name="OpenPage"/>");
	</script>
	</logic:notEmpty>
</logic:notMatch>
<logic:match name="frmOnlineAccess" property="pwdExpiryYN" value="Y">
<script language="javascript">
			var res = confirm("Your Password has been Expired.Please Change Password");
			if(res == true)
			{
				onChangePassword();
			}//end of if(res == true)
	</script>
</logic:match>
</logic:notMatch>
<logic:match name="frmOnlineAccess" property="firstLoginYN" value="Y">
<logic:notMatch name="frmOnlineAccess" property="pwdExpiryYN" value="Y">
<script language="javascript">
			var res = confirm("Please Change the Password for Security Reasons");
			if(res == true)
			{
				onChangePassword();
			}//end of if(res == true)
	</script>
</logic:notMatch>
<logic:match name="frmOnlineAccess" property="pwdExpiryYN" value="Y">
<script language="javascript">
			var res = confirm("Your Password has been Expired.Please Change Password");
			if(res == true)
			{
				onChangePassword();
			}//end of if(res == true)
	</script>
</logic:match>
</logic:match>
</html:form>
</body>
