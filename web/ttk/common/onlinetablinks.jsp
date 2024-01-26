<%
/** @ (#) tablinks.jsp 20th Sep 2005
 * Project     : TTK Healthcare Services
 * File        : tablinks.jsp
 * Author      : Nagaraj D V
 * Company     : Span Systems Corporation
 * Date Created: 20th Sep 2005
 *
 * Modified by   : Arun K N
 * Modified date : 17th May 2007
 * Reason        : to show value of displayname attribute for tab links
 *
 */
 %>

<%@ page import="java.util.*,com.ttk.common.TTKCommon,com.ttk.common.security.*,com.ttk.dto.menu.Link" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,org.apache.struts.action.DynaActionForm" %>
<%
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)session.getAttribute("UserSecurityProfile");
	SecurityProfile spx=null;
	if(userSecurityProfile!=null && userSecurityProfile.getSecurityProfile()!=null)
	{
		spx = userSecurityProfile.getSecurityProfile();
	}
	ArrayList alTabs = new ArrayList();

	if(spx != null)
	   alTabs = spx.getTabs();

%>
<!-- S T A R T : Tab Navigation -->
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
      <tr>
      <%
    	if(alTabs!=null && alTabs.size()>0)
    	{
        	String strTab="";
        	String strSwitchType="";
        	String strActiveLink=spx.getActiveLink();
        	DynaActionForm generalForm=(DynaActionForm)session.getAttribute("frmPolicyList");

        	if(generalForm !=null)
        	{
        		strSwitchType=TTKCommon.checkNull((String)generalForm.get("switchType"));
	        	strSwitchType=strSwitchType.equals("")? "ENM":strSwitchType;
	        }
        	if(strActiveLink.equals("Enrollment"))
        	{
        		for(int i=0; i < alTabs.size(); i++)
	        	{
	        		Link tabLink= (Link)alTabs.get(i);
	        		strTab=tabLink.getName();
	        		if(spx.isActiveTab(tabLink.getName()))
	        		{
	    %>
 		       			<td nowrap class="currentTab"><%= tabLink.getDisplayName() %></td>
        <%
	        		}//end of if(spx.isActiveTab(tabLink.getName()))
	        		else if(!(strSwitchType.equals("ENM") && (strTab.equals("Endorsement")||strTab.equals("Alert"))))
        			{
	    %>
        				<td nowrap class="tab">
        				<a href="#" onClick="javascript:modifyOnlineLinks('Tab','<%=tabLink.getName()%>')" class="tabLink">
        					<%=tabLink.getDisplayName()%>
        				</a>
        				</td>
        <%
	        		}
	        	}//end of for(int i=0; i < alTabs.size(); i++)
        	}//end of if(strActiveLink.equals("Enrollment"))
        	else
        	{
	        	for(int i=0; i < alTabs.size(); i++)
	        	{
	        		Link tabLink= (Link)alTabs.get(i);
	        		if(spx.isActiveTab(tabLink.getName()))
	        		{
        %>
	        			<td nowrap class="currentTab"><%= tabLink.getDisplayName()%></td>
        <%
	        		}//end of if(spx.isActiveTab(tabLink.getName()))
	        		else
	        		{
        %>
        				<td nowrap class="tab">
    					<a href="#" onClick="javascript:modifyOnlineLinks('Tab','<%= tabLink.getName() %>')" class="tabLink">
    						<%= tabLink.getDisplayName() %>
    					</a>
        				</td>
        <%
		        	}
		        }//end of for(int i=0; i < alTabs.size(); i++)
        	}//end of else
        %>
        <%
        	if(strActiveLink.equals("Enrollment"))
        	{
        		if(strSwitchType.equals("ENM"))
        		{
        %>
 		       		<td width="100%" align="right" class="tab blankTab" nowrap>Enrollment View&nbsp;</td>
        <%
        		}//end of if(strSwitchType.equals("ENM"))
        		else if(strSwitchType.equals("END"))
        		{
        %>
 		       		<td width="100%" align="right" class="tab blankTab" nowrap>Endorsement View&nbsp;</td>
        <%
        		}//end of else if(strSwitchType.equals("END"))
        	}//end of if(strActiveLink.equals("Enrollment"))
        	else
        	{
        %>
	        	<td width="100%" class="tab blankTab" nowrap>&nbsp;</td>
        <%
	    	}//end of else
        }//end of if(alTabs!=null && alTabs.size()>0)
	 %>
      </tr>
    </table>
	<!-- E N D : Tab Navigation -->