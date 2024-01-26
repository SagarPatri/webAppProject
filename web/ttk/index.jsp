<!doctype html>

<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.dto.usermanagement.UserSecurityProfile" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
%>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<SCRIPT type="text/javascript">
function onLogin()
{
    document.forms[0].mode.value="doLogin";
}//end of onLogin()

function onChangePassword(strUserID)
{
	var param="&UserId="+strUserID;
	openFullScreenMode("/ChangepasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
}//end of onChangePassword()
</SCRIPT>

<SCRIPT type="text/javascript">
var submitted = false;
function SubmitTheForm() {
	//logoutBtnID
	  var lgbtn=document.getElementById("logoutBtnID");
       if(lgbtn!=null&&lgbtn.value=='Logout') {
    	   onUserLogout();
    	   return;
           }
	var userid=document.forms[0].userid.value;
	var password=document.forms[0].password.value;

 	if(userid==null||password==null||userid===""||password===""||userid.length<1||password.length<1){
 		alert("User Id Or Password Should Not Left");
 		return false;
	}
		
if(submitted == true) { return; }

document.forms[0].mode.value="doLogin";
//document.forms[0].submit();
document.forms[0].mybutton.value = 'Please wait..';
document.forms[0].mybutton.disabled = true;
submitted = true;
}

function onUserLogout()
{
	
	var userID=document.forms[0].userid;
	var pwd=document.forms[0].password;
	if(userID.value==null||userID.value==""){
       alert("Please Enter User ID");
       userID.focus();
       return;
		}
	if(pwd.value==null||pwd.value==""){
	       alert("Please Enter Password");
	       pwd.focus();
	       return;
			}
    document.forms[0].mode.value="doUserLogout";
    document.forms[0].submit();
    
}//end of onUserLogout()
</SCRIPT>

<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Vidal</title>
<link href="/ttk/styles/bootstrap.css" media="screen" rel="stylesheet"></link>
<link href="/ttk/styles/all.css" media="screen" rel="stylesheet"></link>
<link href="/ttk/styles/style.css" media="screen" rel="stylesheet"></link>
</head>

<body>
<section class="main-header">
<html:errors/>
<logic:notEmpty name="errorMsg" scope="request">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="errorMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>

			<!-- S T A R T : Success Box -->
			<logic:notEmpty name="updated" scope="request">
				<table align="center" class="successContainer" style="display:"
					border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/SuccessIcon.gif" alt="Success"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="updated" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>

<%
	if(request.getParameter("sessionexpired")!=null)
	{
%>
	  <section class="main-header">
	  <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/ErrorIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message key="error.session"/>
	     </td>
	   </tr>
	  </table>
<%
	}//end of if(request.getParameter("sessionexpired")!=null)
%>


<div class="login-block text-center" style="background-color:white;">
<div class="mt-4 mb-3 pb-4"><img src="/ttk/images/vipul_better_care_logo 1.jpg" /></div>
<html:form action="/LoginAction.do" onsubmit="return SubmitTheForm();">
<div class="form-group">
    <input name="userid" type="text" class="form-control" placeholder="User ID">
  </div>
  <div class="form-group">
    <input name="password" type="password" class="form-control" placeholder="Password">
  </div>
  <div class="form-group">
   <!--  <button type="submit" class="btn btn-primary w-100">Submit</button> -->
   <logic:notEmpty name="UALO" scope="request">
                     <!--   <input type="submit" name="mybutton" id="logoutBtnID" value="Logout" class="buttons" onmouseout="this.className='buttons'" onmouseover="this.className='buttons buttonsHover'" /> -->
                  <button type="submit" id="logoutBtnID" class="btn btn-primary w-100">Logout</button>   
                      &nbsp;
                      </logic:notEmpty>
                       <logic:empty name="UALO" scope="request">
                    <!--   <input type="submit" name="mybutton" id="loginBtnID" value="Login" class="buttons" onmouseout="this.className='buttons'" onmouseover="this.className='buttons buttonsHover'" /> -->
                    <button type="submit" id="loginBtnID" class="btn btn-primary w-100">Submit</button>
                     </logic:empty>
  </div>
  
  
<input type="hidden" name="mode">
<input type="hidden" name="open" value="<%=TTKCommon.checkNull(request.getParameter("open"))%>">
<logic:notMatch name="frmLogin" property="firstLoginYN" value="Y">
<!-- Changes Added for Password Policy CR KOC 1235 -->
<logic:notMatch name="frmLogin" property="pwdExpiryYN" value="Y">
<!-- End changes for Password Policy CR KOC 1235 -->
<logic:notEmpty name="OpenPage">
	<script type="text/javascript">
			openFullScreenMode("<bean:write name="OpenPage"/>");
	</script>
	</logic:notEmpty>
</logic:notMatch>
<!-- Changes Added for Password Policy CR KOC 1235 -->
<logic:match name="frmLogin" property="pwdExpiryYN" value="Y">
<script type="text/javascript">
			var res = confirm("<bean:write name="frmLogin" property="pwdExpiryMsg"/>");
			if(res == true)
			{
				onChangePassword("<bean:write name="frmLogin" property="userid"/>");
			}//end of if(res == true)
			else//end of else(res == false)
			{
				openFullScreenMode("<bean:write name="OpenPage"/>");
			}
	</script>
</logic:match>
<!-- End changes for Password Policy CR KOC 1235 -->
</logic:notMatch>
<logic:match name="frmLogin" property="firstLoginYN" value="Y">
<!-- Changes Added for Password Policy CR KOC 1235 -->
<logic:notMatch name="frmLogin" property="pwdExpiryYN" value="Y">
<script type="text/javascript">
			var res = confirm("Please Change the Password for Security Reasons.");
			if(res == true)
			{
				onChangePassword("<bean:write name="frmLogin" property="userid"/>");
			}//end of if(res == true)
	</script>
</logic:notMatch>
<logic:match name="frmLogin" property="pwdExpiryYN" value="Y">
<script type="text/javascript">
			var res = confirm("<bean:write name="frmLogin" property="pwdExpiryMsg"/>");
			if(res == true)
			{
				onChangePassword("<bean:write name="frmLogin" property="userid"/>");
			}//end of if(res == true)
			else
			{
				openFullScreenMode("<bean:write name="OpenPage"/>");
			}//end of else(res == false)
	</script>
</logic:match>
<!-- End changes for Password Policy CR KOC 1235 -->
</logic:match>  
  
  
  
  
  
</html:form>
</div>


<div class="home-footer position-relative"><div class="fwhitebg"></div><img src="/ttk/images/home-footer.png" class="img-fluid" /></div>
</section>


<script src="js/jquery-2.2.0.min.js"></script>
<script src="js/bootstrap.min.js"></script>
</body>
</html>
