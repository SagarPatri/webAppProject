<!doctype html>

<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@page import="com.ttk.dto.maintenance.GeneralAnnouncementVO"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<html>

<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Vidal</title>
<link href="/ttk/styles/bootstrap.css" media="screen" rel="stylesheet"></link>
<link href="/ttk/styles/all.css" media="screen" rel="stylesheet"></link>
<link href="/ttk/styles/style.css" media="screen" rel="stylesheet"></link>

<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<link href="/ttk/styles/OnlineDefault.css" media="screen" rel="stylesheet"></link>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<style>
	.errorContainer {
    color: #FF0000;
    border: 0px solid #CCCCCC;
    background-color: transparent;
    margin-top: 10px;
    padding: 5px 5px 5px 5px;
    width: 97%;
    text-align: left;
    font-size:18px;
     font-weight: 700;
	}
	.w-101 {
    width: 283px !important;
    height: 40px;
    margin-left: 6%;
}
	
</style>
</head>

<!-- <body onload="setInterval('blinkIt()',500)"> -->
<script type="text/javascript">
function blinkIt() {
if (!document.all) return;
else {
for(var i=0;i<document.all.tags('blink').length;i++){
s=document.all.tags('blink')[i];
s.style.visibility=(s.style.visibility=='visible') ?'hidden':'visible';
}
}
}
</script>

<script type="text/javascript">
function V2Login(OpenPage)
{ 
	var selLogin=document.forms[0].loginType.value;
	var subLogin=document.forms[0].subLoginType.value;
	
	if(selLogin === "OBR")
	{
		//openWebFullScreenModeWithScrollbar(OpenPage);
		openWebFullScreenMode(OpenPage);
	}		
	else{
			openWebFullScreenMode(OpenPage);
	}
	if(selLogin === "PTR")
    {
        if(subLogin === "PRH")
        {
            openWebFullScreenModeWithScrollbar(OpenPage);
        }else{
        
        openWebFullScreenMode(OpenPage);
        }
    }        
    else{
            openWebFullScreenMode(OpenPage);
    }   

}
</script>

<script type="text/javascript">
function onLogin()
{
    trimForm(document.forms[0]);
    var loginTypeID = document.forms[0].loginType.value;
	//added as per IBM
if(loginTypeID =='IBMOCO')
    {
    	loginTypeID ='OCO';
    }
    if(loginTypeID =='IBMOHR')
    {
    	loginTypeID ='OHR';
    }
	//added as per IBM
    
    if(loginTypeID =='OIN' || loginTypeID =='OCO' || loginTypeID =='OHR' || loginTypeID =='OIU' || loginTypeID =='OCI' || loginTypeID =='HOS' || loginTypeID =='OBR'  || loginTypeID =='PTR') {
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
function onExcel()
{
	alert("called");
	document.forms[0].mode.value="getStreamInfo";
	document.forms[0].action = "/CitiDownloadAction.do";
}
function onForgotPassword(){
	var param="";
	var loginType=document.forms[0].loginType.value;
	
	if(loginType==='OIU')
	{
		if(document.forms[0].insUserId.value === ""){
			alert("Please enter Insurance User ID");
			document.forms[0].insUserId.focus();
			return false;
		}//end of if(document.forms[0].insUserId.value == "")
		param		=	"&UserId="+document.forms[0].insUserId.value+"&LoginType="+document.forms[0].loginType.value;
	}
	else if(loginType==='OBR')	{
		if(document.forms[0].broUserId.value === ""){
			alert("Please enter Broker User ID");
			document.forms[0].broUserId.focus();
			return false;
		}//end of if(document.forms[0].broUserId.value == "")
		param		=	"&UserId="+document.forms[0].broUserId.value+"&LoginType="+document.forms[0].loginType.value;
	}
    //Added for Partner Login
    
    else if(loginType==='PTR')    {
        if(document.forms[0].ptrEmpaneId.value === ""){
            alert("Please enter Partner ID");
            document.forms[0].ptrEmpaneId.focus();
            return false;
        }
        if(document.forms[0].ptrUserId.value === ""){
            alert("Please enter User ID");
            document.forms[0].ptrEmpaneId.focus();
            return false;
        }
        if(document.forms[0].ptrUserId.value === ""){
            alert("Please enter User ID");
            document.forms[0].ptrUserId.focus();
            return false;
        }
        //end of if(document.forms[0].broUserId.value == "")
        param        =    "&UserId="+document.forms[0].ptrUserId.value+"&LoginType="+loginType
                            +"&empanelID="+document.forms[0].ptrEmpaneId.value;
    }
	openFullScreenMode("/ForgotPasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
}//end of onForgotPassword()


function onForgotPassword1()
{
	var param3="";
    var param1="";
	var loginType=document.forms[0].loginType.value;
	if(loginType=='HOS')
	{
		/*Empanel Id commented for project X 
		if(document.forms[0].hosEmpaneId.value == "")
		{
		alert("Please enter  Empanellment Number");
		document.forms[0].hosEmpaneId.focus();
		return false;
		} */
		//end of if(document.forms[0].groupId.value == "")
		if(document.forms[0].hosUserId.value == "")
		{
		alert("Please enter User Id.");
		document.forms[0].hosUserId.focus();
		return false;
		}//end of if(document.forms[0].corpPolicyNo.value == "")
		param1="&GroupID="+document.forms[0].hosUserId.value;
		param2="&PolicyNo="+document.forms[0].hosUserId.value;
		param3="&UserId="+document.forms[0].hosUserId.value;
		param4="&LoginType="+document.forms[0].loginType.value;
		
	}//end of if(loginType=='HOS')
	 if(loginType=='PTR')
	    {
	       
	        if(document.forms[0].ptrUserId.value == "")
	        {
	        alert("Please enter User Id.");
	        document.forms[0].ptrUserId.focus();
	        return false;
	        }//end of if(document.forms[0].corpPolicyNo.value == "")
	        param1="&GroupID="+document.forms[0].ptrUserId.value;
	        param2="&PolicyNo="+document.forms[0].ptrUserId.value;
	        param3="&UserId="+document.forms[0].ptrUserId.value;
	        param4="&LoginType="+document.forms[0].loginType.value;
	        
	    }//end of if(loginType=='OCO')	
	var param = param1+param2+param3+param4;
	openFullScreenMode("/ForgotPasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");


}//end of onForgotPassword()


/* function onForgotPassword1()
{
	var param3="";
    var param1="";
	var loginType=document.forms[0].loginType.value;
	if(loginType=='HOS')
	{
		if(document.forms[0].hosEmpaneId.value == "")
		{
			alert("Please enter  Empanellment Number");
			document.forms[0].hosEmpaneId.focus();
			return false;
		}//end of if(document.forms[0].groupId.value == "")
		if(document.forms[0].hosUserId.value == "")
		{
			alert("Please enter User Id.");
			document.forms[0].hosUserId.focus();
			return false;
		}//end of if(document.forms[0].corpPolicyNo.value == "")
		param1="&GroupID="+document.forms[0].hosEmpaneId.value;
		param2="&PolicyNo="+document.forms[0].hosEmpaneId.value;
		param3="&UserId="+document.forms[0].hosUserId.value;
		param4="&LoginType="+document.forms[0].loginType.value;
		
	}//end of if(loginType=='OCO')
	var param = param1+param2+param3+param4;
	openFullScreenMode("/ForgotPasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
}//end of onForgotPassword1()
 */
function onChangePassword()
{
	var loginType=document.forms[0].loginType.value;
	if(loginType=='OCO' || loginType=='IBMOCO')	{
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
	var check=document.forms[0].groupId.value;
	var param1="&GroupID="+document.forms[0].groupId.value;
	if(check=="A0912")
	{
		var param2="&PolicyNo="+document.forms[0].corpPolicyNo1.value;
	}
	else
	{
		var param2="&PolicyNo="+document.forms[0].corpPolicyNo.value;
	}
	var param3="&UserId="+document.forms[0].userId.value;
	var param4 ="&LoginType="+loginType;
	var param = param1+param2+param3+param4;
	if(loginType == "IBMOCO" || check == "C0717" )	{
		openWebFullScreenModeYOO("/OnlineChangePasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}
	else{
	openFullScreenMode("/OnlineChangePasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
         }	
}//end of if(loginType=='OCO' || loginType=='IBMOCO')
	if(loginType=='OHR' || loginType=='IBMOHR' )
	{
		var param1="&HRGroupID="+document.forms[0].hrGroupId.value;
		var param2="&HRUserId="+document.forms[0].hrUserId.value;
		var param3 ="&LoginType="+loginType;
		var param = param1+param2+param3;
              if(loginType == "IBMOHR")
		{
		 openWebFullScreenModeYOO("/OnlineChangePasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		}
		else{
		openFullScreenMode("/OnlineChangePasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	            }
}//end of if(loginType=='OHR' || loginType=='IBMOHR')
if(loginType=='HOS')
{
	//var param1="&HosEmpanelId="+document.forms[0].hosEmpaneId.value;
	var param2="&HosUserId="+document.forms[0].hosUserId.value;
	var param3 ="&LoginType="+loginType;
	//var param = param1+param2+param3;
	var param = param2+param3;
	openFullScreenMode("/OnlineChangePasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
    
}//end of if(loginType=='OHR' || loginType=='IBMOHR')
if(loginType=='PTR')
{
	var param2="&PtrUserId="+document.forms[0].ptrUserId.value;
	var param3 ="&LoginType="+loginType;
	var param = param2+param3;
	openFullScreenMode("/OnlineChangePasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
}//end of if(loginType=='PTR')
	
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
		var param1="&BroUserId="+document.forms[0].broUserId.value;
		
		var param3 ="&LoginType="+loginType;
		var param = param1+param3;
		openFullScreenMode("/OnlineChangePasswordAction.do?mode=doDefault"+param+"",'_self',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
	}//end of if(loginType=='OBR')
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
	  		openFullScreenMode("https://v1ttk.vidalhealthtpa.com/corp_signin.asp?ltype=IndPol",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		}//end of if(loginType=="OIN")
		else if(loginType=="OCO")
		{
	   		openFullScreenMode("https://v1ttk.vidalhealthtpa.com/corp_signin.asp?ltype=CompLog&lsubtype=EmpLog",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		}//end of else if(loginType=="OCO")
		else if(loginType=="OHR")
		{
	  		openFullScreenMode("https://v1ttk.vidalhealthtpa.com/corp_signin.asp?ltype=CompLog",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		}//end of else if(loginType=="OHR")
		else if(loginType=="OIU")
		{
	    	openFullScreenMode("https://v1ttk.vidalhealthtpa.com/signin.asp?ltype=InsLog",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
		}//end of else if(loginType=="OIU")
	}//end of else
}//end of onBlcUser()

function onIBMUser()
{
  openFullScreenMode("https://weblogin.vidalhealthtpa.com/index.htm",'_self',"scrollbars=1,status=0,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
}//end of onIBMUser()

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
	//changes as per IBM CR
	if(loginTypeID =='IBMOCO')
    {
    	loginTypeID ='OCO';
    }
    if(loginTypeID =='IBMOHR')
    {
    	loginTypeID ='OHR';
    }
	//Changes as per IBM CR
    
    if(loginTypeID =='OIN' || loginTypeID =='OCO' || loginTypeID =='OHR' || loginTypeID =='OIU' || loginTypeID =='OCI' || loginTypeID =='HOS' || loginTypeID =='OBR' || loginTypeID =='PTR'){
    	document.forms[0].mode.value="doLogin";
    }//end of if(loginTypeID =='OIN' && loginTypeID =='OCO' && loginTypeID =='OHR' || loginTypeID =='OIU' || loginTypeID =='OCI')
    else{
    	alert("Login Type is Required");
        return false;
    }//end of else
    
	var hosEmpaneId=document.forms[0].hosEmpaneId.value;
	var hosUserId=document.forms[0].hosUserId.value;
	var hosPassword=document.forms[0].hosPassword.value;

 	if(hosEmpaneId==null||hosEmpaneId===""||hosUserId==null||hosUserId===""||hosPassword==null||hosPassword===""){
 		alert("Empanelment ID/User ID/Password Should Not Left Blank.");
 		return false;
	}	
    
	document.forms[0].mybutton.value = 'Please wait..';
	document.forms[0].mybutton.disabled = true;
	submitted = true;
}
function onlineSubmit() {
	if(submitted == true) { return; }
	
	trimForm(document.forms[0]);
    var loginTypeID = document.forms[0].loginType.value;
    
    if(loginTypeID===null||loginTypeID ===""||loginTypeID.length<1){
    	alert("Login Type is Required");
        return ;
    }
	document.forms[0].mybutton.value = 'Please wait..';
	document.forms[0].mybutton.disabled = true;
	submitted = true;
	document.forms[0].mode.value="doLogin";
	document.forms[0].submit();
}

</SCRIPT>

<%
	String sideHeading="";
	String loginType=TTKCommon.checkNull(request.getParameter("loginType"));
	pageContext.setAttribute("loginTypeList",Cache.getCacheObject("onlineAccess"));
%>

<%
session.setAttribute("Login_Type",TTKCommon.checkNull(request.getParameter("loginType")));  //Rishi Sharma	
session.setAttribute("Login_Type",loginType); //Rishi Sharma
request.setAttribute("loginType",loginType); //Rishi Sharma

if("HOS".equals(loginType))sideHeading="Hospital Login";
else if("OIU".equals(loginType))sideHeading="Insurance Login";
else if("OBR".equals(loginType))sideHeading="Broker Login";
else if("PTR".equals(loginType))sideHeading="Partner Login";
/* java.util.ArrayList al =SupportDAOImp.getGeneralAnnouncementsDetails();
GeneralAnnouncementVO generalannounceDetails =null; */
%>


<head>
    <meta charset="utf-8">
    <title>Your Name Here - Welcome</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">      
	<meta name="author" content="Html5TemplatesDreamweaver.com">
        <!-- Remove this Robots Meta Tag, to allow indexing of site -->
	<META NAME="ROBOTS" CONTENT="NOINDEX, NOFOLLOW"> 
    
<link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">

    <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->
    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
</head>

<body>
<html:form action="/OnlineAccessAction.do" onsubmit="return SubmitTheForm();">
<section class="main-header1">
<div style="text-align: left; padding:3%;" >
<br><br>

<!-- <div class="login-block1"> -->



<logic:notEmpty name="SessionExpired" scope="request">
	<script type="text/javascript">
	var loginType='<bean:write name="SessionExpired" scope="request"/>';
	openFullScreenMode("/ttk/onlineaccess.jsp?sessionexpired=true&loginType="+loginType);
	</script>
</logic:notEmpty>
	
<logic:notEmpty name="LogoutSeccess" scope="request">
	<script type="text/javascript">
	var loginType='<bean:write name="loginType" scope="request"/>';
	openFullScreenMode("/ttk/onlineaccess.jsp?Logout=SUCCESS&loginType="+loginType);
	</script>
</logic:notEmpty>
	
<%
	if(request.getParameter("Logout")!=null)
	{
%>
	  <table align="center" class="successContainer"  border="0" cellspacing="0" cellpadding="0">
				<tr>
			    	<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
			    		<bean:message key="logout.success"/>
			    	</td>
			 	</tr>
			</table>
<%
	}//end of if(request.getParameter("Logout")!=null)
%>
	
<html:errors/>

<%
	if(request.getParameter("sessionexpired")!=null)
	{
%>
	  <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/ErrorIcon.gif" alt="Success" title="Success" width="16" height="16" align="absmiddle">&nbsp;<bean:message key="error.onlinesession"/>
	     </td>
	   </tr>
	  </table>
<%
	}//end of if(request.getParameter("sessionexpired")!=null)
%>

<!-- S T A R T : Success Box -->
<logic:notEmpty name="updated" scope="request">	
	  	   	<table align="center" class="successContainer"  border="0" cellspacing="0" cellpadding="0">
				<tr>
			    	<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
			    		<bean:write name="updated" scope="request"/>
			    	</td>
			 	</tr>
			</table>
</logic:notEmpty>


<!-- E N D : Success Box -->

																			<%--Hospital Login --%>
<logic:notEqual  name="Login_Type" value="PTR">  
	<logic:equal name="Login_Type" value="HOS"> 
<br>	
<div style="margin-left: 77px;">
<img src="/ttk/images/vipul_better_care_logo 1.jpg" />
</div>	
<br>

	<div class="login-block1">
		<select name="hosProvType"  class="form-control">
			<option value="IND">Individual</option>
			<option value="GRP">Group</option>
		</select>
	</div> 
  
  <div class="login-block1">
    	<input type="text" name="hosEmpaneId" class="form-control" placeholder="Empanelment ID">
  </div> 
  
	<div class="login-block1">
    	<input type="text" name="hosUserId" class="form-control" class="line-height: 20px;" placeholder="User ID">
  	</div> 
  	
 	 <div class="login-block1">
   	 	<input type="password" name="hosPassword" id="LoginId32" class="form-control" placeholder="Password">
  	</div>
	 <br>
 	 <div class="form-group">
    <!-- 	<button type="submit" style="border-color:#72bf44; background-color:#72bf44;" class="btn btn-primary w-23">Submit</button> -->
    <button type="submit" name="mybutton" class="btn btn-primary w-101">Submit</button>
 	 </div>  <br>
  
  	<!-- <div class="text-center"><a href="#"  onClick="javascript:onForgotPassword1()" >Forgot Password?</a></div> -->
  	<div class="text-left1"><a href="#"  style="color: blue;font-weight: 500;" onClick="javascript:onForgotPassword1()">Forgot Password?</a></div>
 	</logic:equal>
</logic:notEqual>  
    
<%--   
 commented for srilanka project told by manju sir
 
   	<!-- Added for Partner login -->
 <logic:equal name="Login_Type" value="PTR">  
 	<div class="span12">
		<h4 class="sub_heading">Partner  Login</h4>
		<div id="contentOuterSeparator"></div>
		<table width="100%" align="left" valign="middle" border="0" cellspacing="0" cellpadding="0">
			<tr>
              	<td>
              		<table width="50%" align="center"   valign="middle" border="0" cellspacing="0" cellpadding="0">
              	
            	 	<tr>
                	<td class="loginText" width="35%" nowrap>Empanelment No.</td>
             		  <td align="left" width="65%"><html:text property="ptrEmpaneId" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" /></td>
             		</tr> 
            	 	<tr>
                		<td nowrap="nowrap"  class="loginText" width="35%">User Id</td>
                	
              			<td align="left" width="65%"><html:text property="ptrUserId" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="20" /></td>
                   
              		</tr>
              		<tr>
                		 <td nowrap="nowrap" class="loginText" width="35%">Password </td>
              		
                		<td align="left" width="65%"><input name="ptrPassword" type="password" class="textBoxWeblogin textBoxMediumWeblogin" id="LoginId32" maxlength="20"  ></td>
              		
              		</tr>
              		<tr>
						<td height="8">&nbsp;</td>
	        			<td >&nbsp;</td>
              		</tr>
  			  		<tr>
	           			<td class="loginText">&nbsp;</td>
	               		<td align="left">
	               			<input type="submit" name="mybutton" value="Login" class="buttons" onmouseout="this.className='buttons'" onmouseover="this.className='buttons buttonsHover'"/>&nbsp;
	               			<button type="button" name="Button" accesskey="c" class="buttons" onMous
	               			eout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="window.close()"><u>C</u>lose</button>
	               		</td>
	           		</tr>
	           		 <tr>
           				<td height="50" class="loginText" width="112">&nbsp;</td>
           				<td align="left" nowrap="nowrap" class="loginText">
           					<a href="#"  onClick="javascript:onForgotPassword1()" class="events">Forgot Password</a>
           				</td>
			          <td height="50" class="loginText" width="112">&nbsp;</td>
			           <td align="left" nowrap="nowrap" class="loginText">
			            <a href="#"  onClick="javascript:onChangePassword()" class="events">Change Password</a>
			           </td>
          			</tr> 
    				</table>
              	</td>
              
            </tr>
       </table>
	</div>   
                   
</logic:equal> 

	<!-- Added for Broker Login --> 

   <logic:notEqual name="Login_Type" value="PTR">           
    <logic:equal name="Login_Type" value="OBR">
    <div class="span12">
       <h4 class="sub_heading">Broker Login</h4>
     <div id="contentOuterSeparator"></div>
    </div>
		<table width="25%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0">
		      <tr>
                <td nowrap="nowrap" class="loginText" width="125">Broker Code:</td>
                <td align="right" width="170"><html:text property="brokerCompCode"  styleClass="textBoxWeblogin textBoxLargeWeblogin"  maxlength="20" /></td>
              </tr>
               <tr>
                <td nowrap="nowrap" class="loginText" width="125">User Id:</td>
                <td align="right" width="170"><html:text property="broUserId"   styleClass="textBoxWeblogin textBoxLargeWeblogin"  maxlength="20" /></td>
              </tr>
              <tr>
                <td nowrap="nowrap" class="loginText" width="125">Password: </td>
                <td align="right" width="170"><input name="broPassword" type="password"   class="textBoxWeblogin textBoxLargeWeblogin"   maxlength="20"></td>
              </tr>              
              <tr>	           	
	            <td  colspan="2" align="right">	            
	               <button type="button" name="mybutton" accesskey="l"  class="olbtn" onClick="onlineSubmit();"><u>L</u>ogin</button>&nbsp;
	               <button type="button" name="Button" accesskey="c" class="olbtn" onClick="window.close()"><u>C</u>lose</button>
	            </td>
	          </tr>
	          	 <tr>
           				<td height="50" class="loginText" width="112">&nbsp;</td>
           				<td align="left" nowrap="nowrap" class="loginText">
           					<a href="#"  onClick="javascript:onForgotPassword1()" class="events">Forgot Password</a>
           				</td>
			          <td height="50" class="loginText" width="112">&nbsp;</td>
			           <td align="left" nowrap="nowrap" class="loginText">
			            <a href="#"  onClick="javascript:onChangePassword()" class="events">Change Password</a>
			           </td>
          			</tr> 
	    </table>
    </logic:equal>
    </logic:notEqual>

	<!-- Added for Insurance Login --> 

       <logic:notEqual name="Login_Type" value="PTR">   
    <logic:equal name="Login_Type" value="OIU">
    <div class="span12">
		<h4 class="sub_heading">Insurance Login</h4>
		<div id="contentOuterSeparator"></div>
	</div>
		<table width="25%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0">
		      <tr>
                <td nowrap="nowrap" class="loginText" width="125">Ins Company Code:</td>
                <td align="right" width="170"><html:text property="insCompCode" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="20"/></td>
              </tr>
               <tr>
                <td nowrap="nowrap" class="loginText" width="125">User Id:</td>
                <td align="right" width="170"><html:text property="insUserId" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="20"/></td>
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
	               <input type="submit" name="mybutton" value="Login" class="buttons" onmouseout="this.className='buttons'" onClick="SubmitTheForm();"  onmouseover="this.className='buttons buttonsHover'"/>&nbsp;
	               <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="window.close()"><u>C</u>lose</button>
	            </td>
	          </tr>
	          
	    </table>
    <!-- Broker Start kocb -->
	    
	    
	    <table width="30%" align="center" valign="middle" border="0" cellspacing="0" cellpadding="0" id="corporate1forgotpassword" style="">
          <tr>
           <td height="50" class="loginText" width="112" colspan="2">&nbsp;</td>
           <td align="left" nowrap="nowrap" class="loginText" colspan="2">
            <a href="#"  onClick="javascript:onForgotPassword()" class="events">Forgot Password</a>
           </td>
           
          </tr>
  	 </table>
  	 
  	 
    </logic:equal>
    
  </logic:notEqual> 

  --%>
<br>


 </div> 
<div class="home-footer position-relative"><div class="fwhitebg"></div><img src="/ttk/images/home-footer.png" class="img-fluid" /></div>
</section>

<script src="js/jquery-2.2.0.min.js"></script>
<script src="js/bootstrap.min.js"></script>

<input type="hidden" name="mode">
<INPUT TYPE="hidden" NAME="randomNo" VALUE=""><!-- added for koc 1349 -->
<input type="hidden" name="loginType" value="<%=request.getParameter("loginType")%>">
<input type="hidden" name="subLoginType" value="<%=request.getAttribute("subLoginType")%>">
<input type="hidden" name="open" value="<%=TTKCommon.checkNull(request.getParameter("open"))%>">

<logic:notMatch name="frmOnlineAccess" property="firstLoginYN" value="Y">
	<logic:notMatch name="frmOnlineAccess" property="pwdExpiryYN" value="Y">
		<logic:notEmpty name="OpenPage">
		<script language="javascript">
			V2Login("<bean:write name="OpenPage"/>");
		</script>
		</logic:notEmpty>
	</logic:notMatch>
	<logic:match name="frmOnlineAccess" property="pwdExpiryYN" value="Y">
		<script language="javascript">
		var res = confirm("<bean:write name="frmOnlineAccess" property="pwdExpiryMsg"/>");
		if(res == true)
		{
			onChangePassword();
		}//end of if(res == true)
		else{
			V2Login("<bean:write name="OpenPage"/>");
		    }	
		</script>
	</logic:match>
</logic:notMatch>

 
<logic:match name="frmOnlineAccess" property="firstLoginYN" value="Y">
	<logic:notMatch name="frmOnlineAccess" property="pwdExpiryYN" value="Y">
	<logic:equal name="frmOnlineAccess" property="loginExpiryYN" value="Y">
	<script language="javascript">
	//alert("<bean:write name="frmOnlineAccess" property="firstLoginYN"/>");
				var res = confirm("Please change the password for security reasons");
				if(res == true)
				{
					onChangePassword();
				}//end of if(res == true)
		</script>
	</logic:equal>
	<logic:notEqual name="frmOnlineAccess" property="loginExpiryYN" value="Y">
	<script language="javascript">
	
				var res = confirm("Your password has expired.Please change your password");
				if(res == true)
				{
					onChangePassword();
				}//end of if(res == true)
		</script>
	</logic:notEqual>
	
	</logic:notMatch>
	
	<logic:match name="frmOnlineAccess" property="pwdExpiryYN" value="Y">
	<script language="javascript">
	var res = confirm("<bean:write name="frmOnlineAccess" property="pwdExpiryMsg"/>");
				if(res == true)
				{				onChangePassword();
				}//end of if(res == true)
				else{	
					// var loginType=document.forms[0].loginType.value;
					 V2Login("<bean:write name="OpenPage"/>");
				}
		</script>
	</logic:match>
</logic:match>
</html:form>
</body>
</html>