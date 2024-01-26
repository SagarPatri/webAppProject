<%
/** @ (#) leftlinks.jsp 20th Sep 2005
 * Project     : TTK Healthcare Services
 * File        : leftlinks.jsp
 * Author      : Nagaraj D V
 * Company     : Span Systems Corporation
 * Date Created: 20th Sep 2005
 *
 * Modified by   : Arun K N
 * Modified date : 17th May 2007
 * Reason        : For showing the value of displayname attribute for Links and sublinks.
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
	}
%>
<!-- S T A R T : Left Navigation Menu -->
<table width="160" border="0" cellspacing="0" cellpadding="0">
<%
	ArrayList alLinks = new ArrayList();
   	ArrayList alSubLinks = new ArrayList();
	if(spx != null)
	{
	   alLinks = spx.getLeftLinks();
	   alSubLinks = spx.getSubLinks();
	}
	if(alLinks != null && alLinks.size() > 0) 	//check if any links exists
	{
		for(int i=0; i < alLinks.size(); i++)
		{
			Link leftLink= (Link)alLinks.get(i);
			if(spx.isActiveLink(leftLink.getName()))
			{
%>
				<tr>
	  				<td class="currentMenu" ><%=leftLink.getDisplayName()%></td>
				</tr>
<%
				//check if any Sublinks exists for the current link
				if(alSubLinks != null && alSubLinks.size() > 0)
				{
					for(int j=0; j < alSubLinks.size(); j++)
					{
						Link subLink= (Link)alSubLinks.get(j);
						if(spx.isActiveSubLink(subLink.getName()))
						{
		%>
							<tr>
					          <td class="currentSubMenu" ><%=subLink.getDisplayName()%></td>
					        </tr>
		<%
						}
						else
						{
							if(subLink.getName().equalsIgnoreCase("Wellness Login"))
							{
			%>
								<tr>
						          <td class="submenu" >
						       <a href="#" onClick="javascript:wellnessOpen()" >Wellness</a> 
						          </a>
						          </td>
						         </tr>
			<%
							}//end of else
							else{
		%>
							<tr>
					          <td class="submenu" >
					          <a href="#" onClick="javascript:modifyOnlineLinks('SubLink','<%=subLink.getName()%>')" title="<%=subLink.getToolTip()%>"  class="menuLink">
					          	<%=subLink.getDisplayName()%>
					          </a>
					          </td>
					        </tr>
		<%}//end of else
						}//end of else
					}//end of for(int j=0; j < alSubLinks.size(); j++)
				}//end of if(alSubLinks != null && alSubLinks.size() > 0)
			}//end if(spx.isActiveLink(leftLink.getName()))
			else
			{
%>
				<tr>
		          <td class="menu" >
		          	<a href="#" onClick="javascript:modifyOnlineLinks('Link','<%=leftLink.getName()%>')" title="<%=leftLink.getToolTip()%>" class="menuLink">
		          		<%= leftLink.getDisplayName() %>
		          	</a>
		          </td>
		        </tr>
<%
			}//end of else
	}//end of for(int i=0; i < alLinks.size(); i++)
}
%>
  </table> 
  
  
  <!-- E N D : Left Navigation Menu -->
  <form name="leftlinks" METHOD="POST">
  <input type="hidden" name="mode">
  <INPUT TYPE="hidden" NAME="leftlink" VALUE="<%= spx.getActiveLink()%>">
  <INPUT TYPE="hidden" NAME="sublink" VALUE="<%= spx.getActiveSubLink()%>">
  <INPUT TYPE="hidden" NAME="tab" VALUE="<%= spx.getActiveTab()%>">
  </form>