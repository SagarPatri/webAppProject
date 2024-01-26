<%
/** @ (#) ipruheader.jsp 13th Nov 2008
 * Project     : TTK Healthcare Services
 * File        : ipruheader.jsp
 * Author      : Ramakrishna K M
 * Company     : Span Systems Corporation
 * Date Created: 13th Nov 2008
 *
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 *
 */
 %>
<!-- S T A R T : Header -->
<%@ page import="com.ttk.common.TTKCommon,com.ttk.dto.usermanagement.UserSecurityProfile" %>
<%
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
%>
	<div id="header">
		<img src="/ttk/images/logo.gif" alt="IPRU" width="232" height="78">
    	<div id="User">
    		<ul>
        		<li>Member Name: <span><%=userSecurityProfile.getUserName()%></span></li>
				<li>Date: <span><%=userSecurityProfile.getLoginDate()%></span></li>
				<li class="last"><a href="#" onClick="javascript:ipruLogout()" title="Close">Close</a></li>
        	</ul>
		</div>
	</div>
<!-- E N D : Header -->