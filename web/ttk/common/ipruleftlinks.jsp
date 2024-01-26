<%
/** @ (#) ipruleftlinks.jsp 13th Nov 2008
 * Project     : TTK Healthcare Services
 * File        : ipruleftlinks.jsp
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

<%@ page import="java.util.*,com.ttk.common.security.* ,com.ttk.dto.usermanagement.UserSecurityProfile" %>
<%@ page import="com.ttk.dto.menu.Link" %>
<%
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)session.getAttribute("UserSecurityProfile");
	SecurityProfile spx=null;
	if(userSecurityProfile!=null && userSecurityProfile.getSecurityProfile()!=null)
	{
		spx = userSecurityProfile.getSecurityProfile();
	}//end of if(userSecurityProfile!=null && userSecurityProfile.getSecurityProfile()!=null)
%>
<!-- S T A R T : Left Navigation Menu -->
<div id="LeftNav">
	<%
		ArrayList alLinks = new ArrayList();
   		//ArrayList alSubLinks = new ArrayList();
		if(spx != null)
		{
	   		alLinks = spx.getLeftLinks();
	  		//alSubLinks = spx.getSubLinks();
		}//end of if(spx != null)
		if(alLinks != null && alLinks.size() > 0) 	//check if any links exists
		{
			for(int i=0; i < alLinks.size(); i++)
			{
				Link leftLink= (Link)alLinks.get(i);
				if(spx.isActiveLink(leftLink.getName()))
				{
				%>
					<ul>
	  					<li><a href="#"><%=leftLink.getDisplayName()%></a></li>
					</ul>

				<%
						
				}//end if(spx.isActiveLink(leftLink.getName()))
				else
				{
				%>
					<ul>
		          		<li>
		          			<a href="#" onClick="javascript:modifyIpruLinks('Link','<%=leftLink.getName()%>')" title="<%=leftLink.getToolTip()%>">
		          			<%= leftLink.getDisplayName() %>
		          		</a>
		          		</li>
		        	</ul>
				<%
				}//end of else
			}//end of for(int i=0; i < alLinks.size(); i++)
		}//end of if(alLinks != null && alLinks.size() > 0)
		%>
</div> 
    
  <!-- E N D : Left Navigation Menu -->
  <form name="leftlinks" METHOD="POST">
  <input type="hidden" name="mode">
  <INPUT TYPE="hidden" NAME="leftlink" VALUE="<%= spx.getActiveLink()%>">
</form>