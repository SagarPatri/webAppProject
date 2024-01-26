<%
/**
 * @ (#) home.jsp Dec 26, 2007
 * Project      : TTK HealthCare Services
 * File         : home.jsp
 * Author       : Raghavendra T M
 * Company      : Span Systems Corporation
 * Date Created : Dec 26, 2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/home.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<%
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
String newEnroll	=	(String)request.getAttribute("NewEnroll")==null?"":(String)request.getAttribute("NewEnroll");
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
    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
</head>
<html:form action="/OnlineHomeAction.do" >
<body id="pageBody">

<div class="contentArea" id="contentArea">
<!-- S T A R T : Content/Form Area -->

<logic:notEmpty name="openurl" scope="request">
<script language="JavaScript">
var w = screen.availWidth - 10;
var h = screen.availHeight - 90;
var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
window.open("<bean:write name="openurl" scope="request"/>",'',features);                                                
</script>                              
                </logic:notEmpty>	
                
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">
<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<!-- <div id="navigateBar">Home > Corporate > Detailed > Claim Details</div> -->
	<div id="contentOuterSeparator"></div>
	<logic:equal name="Login_Type" value="HOS"> 	
	<h4 class="sub_heading">Provider Dashboard</h4>
	<%
	if(userSecurityProfile.getLoginType().equals("PTR")  ) 
	{
	%>
	<h4 class="sub_heading">Partner Dashboard</h4>
	<%} %>
<br>
</logic:equal>
	<div id="contentOuterSeparator"></div>
</div>           

       </div>
        <div class="row-fluid" >
        <div style="width: 100%;">
<div class="span12" style="margin:0% 0%"> 

	<!-- S T A R T : Page Title -->
<%-- <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
   
  <td>Home Page Information</td>
   
    <td>
    <%
    if(userSecurityProfile.getLoginType().equals("E")||userSecurityProfile.getLoginType().equals("I") ) 
    {
    if(!userSecurityProfile.getGroupID().equals("I310"))
    {
    %>
     <td align="right"><a href="#" onclick="javascript:onECards()"><img src="/ttk/images/E-Cardbold.gif" alt="E-Card" title="E-Card" width="81" height="17" align="absmiddle" border="0" class="icons"></a></td>
    <%
    }
    }
    %>
</tr>
</table> --%>
<html:errors/>
<%if(newEnroll!=""){ %>
<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
  <tr>
  <td style="color: red;"><%= newEnroll%></td>
</tr>
</table>
<%} %>
<!-- S T A R T : Success Box -->
	<%
	if(userSecurityProfile.getLoginType().equals("E") && request.getAttribute("pwdalert") !=null ) 
	{
	%>
	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <% out.println(request.getAttribute("pwdalert"));  %>
	        </td>
	      </tr>
	       
   	 </table>
    <%
    }//end of if(userSecurityProfile.getLoginType().equals("E") && request.getAttribute("pwdalert") !=null )
    %>
    <%
	if(userSecurityProfile.getLoginType().equals("E") && request.getAttribute("windowprdalert") !=null ) 
	{
	%>
	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	       <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <% out.println(request.getAttribute("windowprdalert"));  %>
	        </td>
	      </tr>
   	 </table>
    <%
    }//end of if(userSecurityProfile.getLoginType().equals("E") && request.getAttribute("windowprdalert") !=null )
    	
        
    %>
    
     <%
	if(userSecurityProfile.getLoginType().equals("HOS")  ) 
	{
	%>
	
	<!-- <table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
	<tr>
			 <td  width="20%" nowrap>&nbsp;</td>
          	 <td width="20%" nowrap>From Date <br>
	            <INPUT TYPE="text" NAME="sFromDate" class="textBox textDate" VALUE="" maxlength="10"> <A NAME="CalendarObjectFrmDate" ID="CalendarObjectFrmDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectFrmDate','forms[1].sFromDate',document.forms[1].sFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="frmDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
          	
          	 <td  width="20%" nowrap>To Date:<br>
	            <INPUT TYPE="text" NAME="sToDate" class="textBox textDate" VALUE="" maxlength="10"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].sToDate',document.forms[1].sToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="toDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
  	    	<td  width="20%" valign="bottom" nowrap>
        	<a href="#" accesskey="s" onClick="javascript:onDashBoardSearch()" class="search">
	        <img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch
       	 	</a>
    		</td>    
    	
    
    
    	 <td  width="20%" nowrap>&nbsp;</td>
		</tr>
		  </table> -->
	 <ttk:OnlineHospHomeDetails/>
	 <br> 
	 <table width="98%" align="center"  border="0" cellspacing="0" cellpadding="0" >
	
	<tr>
		<td align="center">
			 <button type="button" name="Button2" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onProceed()"><u>P</u>roceed</button>
		</td>
	</tr>

	</table>
	<br>
	 <!-- <table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
    	      <tr>
    	       <td width="25%" nowrap><a href="#" onclick="javascript:onEnrollNewProvider()">Enroll New Provider Facility</a><br>
    	       </td>
    	      </tr>
    	     
    	      
    	      
	   <tr>
	<td>
		<ul  style="margin-bottom:0px; ">
	<li class="liPad"><a href="#" onClick="javascript:onEnrollNewProvider()" >Enroll New Provider Facility</a></li>
	<li class="liPad"><a href="#" onClick="javascript:onIsuranceNewProvider()" >Insurance Details</a></li>
	<li class="liPad"><a href="#" OnClick="javascript:OnProviderFacilities()">Provider Facilities</a></li>
		</ul>
	</td>
	   </tr>
     
    </table> -->
    <%
  
    }//end of if(userSecurityProfile.getLoginType().equals("HOS")
    if(userSecurityProfile.getLoginType().equals("PTR")  ) 
   	{
   	%>	
   	 <ttk:OnlinePartnerHomeDetails/>
   	 <% String limit = (String)request.getSession().getAttribute("limit"); %>
   	 <br> 
   	 <table width="98%" align="center"  border="0" cellspacing="0" cellpadding="0" >
   	
   	<tr>
   		<td align="center">
   			 <button type="button" name="Button2" accesskey="p" class="olbtn" onClick="onProceed1()"><u>P</u>roceed</button>
   		</td>
   	</tr>
   	</table>
   	<table width="98%" align="center"  border="0" cellspacing="0" cellpadding="0" >
   	<tr>
   	</tr>
   		<tr>
   			<td width="20%" class="formLabel">NOTE: Obtaining Approval Is Mandatory For Requested Amount i.e more than limit: <%= limit%></td>
       				<td width="30%" class="textLabelBold">
       				</td>	
   		</tr>
   	</table>
   	<br>
       <%
       }//end of if(userSecurityProfile.getLoginType().equals("HOS")
       
    //kocb 
         else if(userSecurityProfile.getLoginType().equals("B"))
    	{
   %>
    		<table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
    	      <tr>
    	      	
    	        <td width="25%" nowrap>Scheme No.:<br>
    	            <html:text property="sPolicyNumber" name="frmOnlineHome" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
    	        </td>
    	        <td nowrap>Group/Company Id:<br>
    	            	<html:text property="sGroupId" name="frmOnlineHome" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search3" maxlength="60"/>
    	        </td>
    	        <td nowrap>Group/Company Name:<br>
    		        <html:text property="sGroupName" styleClass="textBoxWeblogin textBoxMediumWeblogin" styleId="search4" maxlength="40"/>
    			</td>
    			<!-- 
    			<td width="46%" valign="bottom" nowrap>
    	        <a href="#" accesskey="s" onClick="javascript:SelectCorporate()" class="search">
    		        <img src="/ttk/images/EditIcon.gif" alt="Select Corporate" title="Select Corporate" width="16" height="16" border="0" align="absmiddle">&nbsp;
    	        </a>
    	        </td>  
    	        
    	         -->
    	                
    	    	 
    		    	
    			<td>
    	        <a href="#" accesskey="s" onClick="javascript:onSearch()" class="search">
    		        <img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch
    	        </a>
    	    	</td>
    	    	 
    	    	
    	    	
    			</tr>				
    	    </table>
    	    <!-- div class="contentArea" id="contentArea"--><br>

  
<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
    <!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="27%"></td>
  	    <ttk:PageLinks name="tableData"/>
  	  </tr>
	</table>
<!-- /div>-->


    <% 	}
    	else
    	{
     %>
   	 <ttk:OnlineHomeDetails/>
     
   	 <%}
    //kocbroker
    	if(userSecurityProfile.getLoginType().equals("B"))
    	{  
    %>
    <br>
   	 <table align="left"   border="0" cellspacing="0" cellpadding="0">
            <tr>
            	<td><b>Note :&nbsp;</b></td>
                  <td class="textLabel">To Navigate DashBoard details, please click on the Scheme number.  
                  </td>
            
     </tr>
</table>
   <%} %>	 
</div>


</div>
</div>
</div>
</div>
</div>
</div>


<!--E N D : Content/Form Area -->
<!-- E N D : Main Container Table --> 
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="leftlink" VALUE="">
<INPUT TYPE="hidden" NAME="sublink" VALUE="">
<INPUT TYPE="hidden" NAME="tab" VALUE="">
<INPUT TYPE="hidden" NAME="seqID" VALUE="">
<INPUT TYPE="hidden" NAME="fileName" VALUE="">

<INPUT TYPE="hidden" NAME="rownum" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
<INPUT TYPE="hidden" NAME="tab" VALUE="">
<INPUT TYPE="hidden" NAME="historymode" VALUE="">

</body>
</html:form>

