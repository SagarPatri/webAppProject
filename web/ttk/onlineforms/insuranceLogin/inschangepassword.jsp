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
		document.forms[1].action = "/InsSavePswdAction.do";
		JS_SecondSubmit=true;
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
	<html:form action="/ChangePswdAction.do">
	
	<body id="pageBody">
                  
<div class="contentArea" id="contentArea">
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">


<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<!-- <div id="navigateBar">Profile</div>  -->
<div id="contentOuterSeparator"></div>
<h4 class="sub_heading">Change Password</h4>
<%-- <html:errors/> --%>
<div id="contentOuterSeparator"></div>
</div>


	<!-- S T A R T : Page Title -->
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

    		<!-- <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onChangePassword()"><u>C</u>hange Password</button>&nbsp;</td> -->
    		
<div class="span12" align="center" style="margin-left:0%;">
<div style="width: 70%;">

<div>
<br>
<p style="margin-bottom: 0px; margin-left: 16%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle" height="12" width="12"> &nbsp;&nbsp;&nbsp;
 <a href="#" onclick="javascript:onChangePassword()" accesskey="c"> <u>C</u>hange Password </a></p>
</div>
  
</div>
</div>

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
<!-- E N D : Buttons -->
	<html:hidden property="mode" />

</div>
</div>
</div>
</div>
</div>

<script src="/ttk/scripts/bootstrap/css/jquery.min.js" type="text/javascript"></script> 
<script src="/ttk/scripts/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>

</body>
</html:form>
	<!-- E N D : Content/Form Area -->