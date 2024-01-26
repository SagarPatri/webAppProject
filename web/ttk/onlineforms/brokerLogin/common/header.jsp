<!-- S T A R T : Header -->
<%@ page import="com.ttk.common.TTKCommon,com.ttk.dto.usermanagement.UserSecurityProfile" %>
<%
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
%>
<table style="width:100%;border:0;padding:0px;border-spacing: 0px;"><!-- class="header"  -->
  <tr>
    <td width="30%"><img src="/ttk/images/vidal_AE_Logo.PNG" alt="Vidal Healthcare Administrator Services" width="350" height="57"></td>
    <td width="70%">
      <table style="width: 100%;height: 57px;border: 0;padding:0px;border-spacing: 0px">
        <tr>
          <td align="right" valign="top" class="headerLinks"><a href="#" class="headerLinks helpCursor" title="Help">Help</a>|&nbsp;<a href="#" onClick="javascript:brokerLogout()" class="headerLinks" title="Logout">Logout</a></td>
        </tr>
        <tr>
          <td align="left" valign="bottom" class="headerInfo">
          Welcome: <span class="headerInfoValue"><%=userSecurityProfile.getUserName()%></span>&nbsp;&nbsp;
          Company : <span class="headerInfoValue"><%=userSecurityProfile.getGroupName()%></span>&nbsp;&nbsp;
          Last Login : <span class="headerInfoValue"><%=userSecurityProfile.getLastLoginDate()%></span>&nbsp;&nbsp;
          Date : <span class="headerInfoValue"><%=userSecurityProfile.getLoginDate()%></span>
          </td>
        </tr>        
      </table>
      </td>
</tr> 
</table>

<!-- E N D : Header -->	