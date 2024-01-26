<!-- S T A R T : Header -->
<%@ page import="com.ttk.common.TTKCommon,com.ttk.dto.usermanagement.UserSecurityProfile" %>
<%
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
%>

<% 
          if(userSecurityProfile.getLoginType().equals("U")){%>
<div class="container">

        <div class="divPanel topArea notop nobottom">
            <div class="row-fluid">
                <div class="span12">

                    <div id="divLogo" class="pull-left">
                        <a href="index.html" id="divSiteTitle"><!-- <img src="https://www.vidalhealth.ae/sites/all/themes/designmd/logo.png" alt="Home"> -->
                        <img src="/ttk/images/vidal_AE_Logo.PNG" alt="Home"></a><br />
                    </div>
                    
  					<div class="pull-right">
                        <p style="color:#008c8d;margin-top: 10px;">Welcome, <%=userSecurityProfile.getInsCompName() %><br />
                           <!-- Last Login dd/mm/yy @ hh:mm  --> Last Login : <%=userSecurityProfile.getLastLoginDate() %> <br />
                           <!-- Wednesday 09/07/15 01:00pm  -->Current Date : <%=userSecurityProfile.getLoginDate() %> 
                           &nbsp;&nbsp;
           					|&nbsp;&nbsp;
                           <a href="#" onClick="javascript:inslogout()" class="headerLinks" title="Logout" style="color:#008c8d">Logout</a>
                         </p>
                    </div>
                    
                </div>
            </div>
        </div>

    </div> 
    <%
          }else if(userSecurityProfile.getLoginType().equals("HOS")){%>
          <div class="container">

        <div class="divPanel topArea notop nobottom">
            <div class="row-fluid">
                <div class="span12">

                    <div id="divLogo" class="pull-left">
                        <!--<a href="index.html" id="divSiteTitle"> <img src="https://www.vidalhealth.ae/sites/all/themes/designmd/logo.png" alt="Home"> 
                        <img src="/ttk/images/vidal_AE_Logo.PNG" alt="Home"></a><br />-->
                        <img src="/ttk/images/vidal_AE_Logo.PNG" alt="Home"><br />
                    </div>
  					<div class="pull-right">
                        <p style="color:#008c8d;margin-top: 10px;">Welcome, <%=userSecurityProfile.getUserName() %><br />
                           Last Login : <%=userSecurityProfile.getLastLoginDate() %> <br />
                           Current Date : <%=userSecurityProfile.getLoginDate() %> 
                           &nbsp;&nbsp;
           					|&nbsp;&nbsp;
                           <a href="#" onClick="javascript:inslogout()" class="headerLinks" title="Logout" style="color:#008c8d">Logout</a>
                         </p>
                    </div>
                    
                </div>
            </div>
        </div>

    </div> 
          <%} 
          else if(userSecurityProfile.getLoginType().equals("PTR")){%>
         
            <table width="100%" border="0" cellpadding="0" cellspacing="0" style="width:100%; background-color:#FFFFFF;">
  		<tr>
  			<td style="border-bottom:2px solid #000;">&nbsp;&nbsp;&nbsp;&nbsp;<img style="padding-top:2px;" src="/ttk/images/vidal_AE_Logo.PNG" title="Logo" alt="Logo"></td>

			<td align="right" style="border-bottom:2px solid #000;">
						<font size="2px"><strong>	Welcome <%=userSecurityProfile.getUserName() %>,<br /><br /></strong>
							Last Login : <%=userSecurityProfile.getLastLoginDate() %> 
							&nbsp;&nbsp;|&nbsp;&nbsp;
							Current Date : <%=userSecurityProfile.getLoginDate() %> 
							&nbsp;&nbsp;|&nbsp;&nbsp;
							<a href="#" onClick="javascript:inslogout()" class="headerLinks" title="Logout" style="color:#008c8d">Logout </a>
						</font>
			</td>
		</tr>
	</table>
	
          <%}
          
          else{ %>
<table width="100%"  border="0" cellspacing="0" cellpadding="0"><!-- class="header"  -->
  <tr>
    <td width="30%">
    <img src="/ttk/images/Logo_Final.gif" alt="Vidal Healthcare Administrator Services" width="350" height="57">
    
    </td>
   
    <td width="40%"><table width="100%" height="57"  border="0" cellpadding="0" cellspacing="0">
   
        <tr>
          <td align="right" valign="top" class="headerLinks"><a  ondblclick="forTestL();" href="#" class="headerLinks helpCursor" title="Help">Help</a>|&nbsp;<a href="#" onClick="javascript:logout()" class="headerLinks" title="Logout">Logout</a></td>
        </tr>
        <tr>
          <td align="right" valign="bottom" class="headerInfo">User: <span class="headerInfoValue"><%=userSecurityProfile.getUserName()%></span> &nbsp;&nbsp;&nbsp;
          <% 
          if(!userSecurityProfile.getLoginType().equals("B")){%>
          Branch: <span class="headerInfoValue"><%=userSecurityProfile.getBranchName()%></span>
          <%} %>
          	&nbsp;&nbsp;
           	|&nbsp;&nbsp;Date: <span class="headerInfoValue"><%=userSecurityProfile.getLoginDate()%></span>
          </td>
        </tr>
        <tr>
          <td align="right"><span style="font-face:tahoma;font-size:16px;font-weight:bold;color:#A83108;"><%=userSecurityProfile.getGroupName()%></span>
          </td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td colspan="3" class="headerSeparator" nowrap></td>
  </tr>
 <!--  <tr>
    <td colspan="2" class="headerLowerBand" nowrap></td>
  </tr> -->
</table>
<%} %>
<!-- E N D : Header -->