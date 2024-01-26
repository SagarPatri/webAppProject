<% 
/**
 * @ (#) personaldetails.jsp 20th Apr 2006
 * Project      : TTK HealthCare Services
 * File         : personaldetails.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 20th Apr 2006
 *
 * @author       : Lancy A
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import=" com.ttk.common.TTKCommon" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/onlineforms/insuranceLogin/inspersonaldetails.js"></script>
<%
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");		
	boolean viewmode=true;
	//kocb
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		if(userSecurityProfile.getLoginType().equals("B"))
		{
			viewmode=true;
		}
		else
		{
			viewmode=false;
		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	}
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
<html:form action="/PersonalDetailsAction.do">


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
<h4 class="sub_heading">Insurance Profile</h4>
<%-- <html:errors/> --%>
<div id="contentOuterSeparator"></div>
</div>



<!-- S T A R T : Page Title -->

<!-- E N D : Page Title --> 		

<!-- S T A R T : Form Fields -->
	<html:errors/>		
	
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
	
	<fieldset>
	<legend>Personal Information</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="17%" class="formLabel">Name: </td>
        <td width="37%" class="textLabelBold"><bean:write property="name" name="frmPersonalDetails"/></td>
        <td width="17%">&nbsp;</td>
        <td width="29%">&nbsp;</td>
      </tr>
      <tr>
        <td>Designation: </td>
        <td class="textLabelBold"><bean:write property="designationDesc" name="frmPersonalDetails"/></td>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td class="formLabel">Primary Email ID: <span class="mandatorySymbol">*</span></td>
        <td>
        	<html:text property="primaryEmailID" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
		</td>
        <td class="formLabel">Alternate Email ID:</td>
        <td>
        	<html:text property="secondaryEmailID" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>        	
        </td>
      </tr>
      <tr>
        <td class="formLabel">Office Phone 1:</td>
        <td>
        	<html:text property="phoneNbr1" styleClass="textBox textBoxMedium" maxlength="25" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>        	
        </td>
        <td class="formLabel">Office Phone 2:</td>
        <td>
        	<html:text property="phoneNbr2" styleClass="textBox textBoxMedium" maxlength="25" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>        	        
        </td>
      </tr>
      <tr>
        <td class="formLabel">Home Phone:</td>
        <td>
	        <html:text property="residencePhoneNbr" styleClass="textBox textBoxMedium" maxlength="25" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>        	        
        </td>
        <td class="formLabel">Mobile:</td>
        <td>
			<html:text property="mobileNbr" styleClass="textBox textBoxMedium" maxlength="15" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>        	                
		</td>
      </tr>      
    </table>
	</fieldset>
<!-- E N D : Form Fields -->




<!-- S T A R T : Buttons -->



<div class="span12" align="center" style="margin-left:0%;">
<div style="width: 70%;">

<div>
<br>
<p style="margin-bottom: 0px; margin-left: 16%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle" height="12" width="12"> &nbsp;&nbsp;&nbsp;
 <a href="#" onclick="javascript:onSave()" > Save </a></p>
 <p style="margin-bottom: 0px; margin-left: 9%; margin-top: -2%;"><img class="hexagon_small" src="/ttk/images/Insurance/small_hexagon_1.png" alt="" align="middle" height="12" width="12"> &nbsp;&nbsp;&nbsp;
 <a href="#" onclick="javascript:Reset()" > Reset </a></p>
</div>
  
</div>
</div>

	<%-- <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td width="100%" align="center">
	       	    <%
    				if(TTKCommon.isAuthorized(request,"Edit")) 
					{
    					if(!userSecurityProfile.getLoginType().equals("B"))
    					{
			    %>

	        			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;

			    		<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Reset()"><u>R</u>eset</button>&nbsp;
	    		<%
    					}//end if Broker
    				} 
	    		%>
			</td>
		</tr>
	</table> --%>
<!-- E N D : Buttons -->
<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
<html:hidden property="name"/>
<html:hidden property="designationDesc"/>

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
