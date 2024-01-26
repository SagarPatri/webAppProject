<%
/** @ (#) insleftlinks.jsp 3rd July 2015
 * Project     : TTK Healthcare Services
 * File        : insleftlinks.jsp
 * Author      : Kishor kumar S H
 * Company     : RCS Technologies
 * Date Created: 3rd July 2015
 *
 * Modified by   : Kishor kumar S H
 * Modified date : 3rd July 2015
 * Reason        : For showing the value of displayname attribute for Links and sublinks.
 *
 */
 /*
 width:99%; border:solid 1px #c93300; border-width:1px 1px 1px 0;
 */
 %>

<%@ page import="java.util.*,com.ttk.common.security.* ,com.ttk.dto.usermanagement.UserSecurityProfile" %>
<%@ page import="com.ttk.dto.menu.Link" %>
<head>
	
	<link rel="stylesheet" type="text/css" href="css/payerstyles.css" />
	<script language="javascript" src="/ttk/scripts/payerscript.js"></script>
      
      <style>
body { margin:10px; padding:0; font-family:Arial, Helvetica, sans-serif;}
	#insLoginMenuId   { margin:0; padding:0px; list-style:none; background:url(/ttk/images/horizontal_d_green_classic_bg.png) repeat-x top; height:28px;  }
	#insLoginMenuId li{ display:inline; position:relative; float:left;  }
	#insLoginMenuId li a { display:block; float:left; height:29px; line-height:29px; padding:0 25px; text-decoration:none; color:#000000; font-weight:normal; font-size:12px; border-left:solid 1px #FFFFFF; }
	#insLoginMenuId li a:hover {color:#a9401b; background:#FFFFFF; }
	
	#insLoginMenuId li ul { margin:0; padding:0 11px; line-height:none; position:absolute; top:29px; left:0; border:solid 1px #c93300; border-width:0px 1px 1px 1px; width:180px; display:none; background:#FFFFFF;}
	#insLoginMenuId li:hover ul { display:block}
	#insLoginMenuId li:hover a {color:#a9401b; background:#FFFFFF;} 
	#insLoginMenuId li ul li { display:block;  border-bottom:solid 1px #dbdcd9; width:100%; background:url(/ttk/images/d_horizontal_red_classic_menu_arrow.gif) no-repeat 3px 12px; padding:0 0 0 10px; }
	#insLoginMenuId li ul li:last-child { border-bottom:0px;}
	#insLoginMenuId li ul a { border-width:0px; color:#909090; padding:0 5px 0 0; background-color:transparent;}
	#insLoginMenuId li:hover ul li a { color:#04B4AE}
	#insLoginMenuId li ul li a:hover { color:#4000FF}
	#insLoginMenuId li a:hover { color:#4000FF}
	
	
	
</style>
</head>
<%
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)session.getAttribute("UserSecurityProfile");
	SecurityProfile spx=null;
	if(userSecurityProfile!=null && userSecurityProfile.getSecurityProfile()!=null)
	{
		spx = userSecurityProfile.getSecurityProfile();
	}
%>


<!-- S T A R T : Left Navigation Menu -->

<body>
<ul id="insLoginMenuId">
	<!-- <li><a href="#" title="Corporate"><span>Corporate</span></a>
    	<ul>
        	<li><a href="#">Global View</a></li>
            <li><a href="#">Focused View</a></li>
        </ul>
    </li>
    <li><a href="#" title="Retail"><span>Retail</span></a></li>
    <li><a href="#" title="Reports"><span>Reports</span></a></li>
    <li><a href="#" title="My Profile"><span>My Profile</span></a>
    	<ul>
        	<li><a href="#">Change Password</a></li>
            <li><a href="#">Log Details</a></li>
        </ul>
    </li> -->
    
    
    <!-- Code to Fetch Menu data from DB -->
    <%
	ArrayList alLinks = new ArrayList();
   	ArrayList alSubLinks = new ArrayList();
   	ArrayList alTabLinks = new ArrayList();
	if(spx != null)
	{
	   alLinks = spx.getLeftLinks();
	   alSubLinks = spx.getSubLinks();
	   alTabLinks =	spx.getTabs();
	}
	if(alLinks != null && alLinks.size() > 0) 	//check if any links exists
	{
		for(int i=0; i < alLinks.size(); i++)
		{
			Link leftLink= (Link)alLinks.get(i);
			if(spx.isActiveLink(leftLink.getName()))
			{//links
			%>
				<li><a href="#"><%=leftLink.getDisplayName()%></a>
			<%
			//check if any Sublinks exists for the current link
			if(alSubLinks != null && alSubLinks.size() > 0)
			{
				%><ul><%
				for(int j=0; j < alSubLinks.size(); j++)
				{
					Link subLink= (Link)alSubLinks.get(j);
					
					if(spx.isActiveSubLink(subLink.getName()))
					{//sublinks
						%>
						<li><a href="#" title="<%=subLink.getDisplayName()%>"><%=subLink.getDisplayName()%></a></li>
						<%			
								
					}else{//INACTIVE SUB LINKS
						%>
						<li><a href="#" onClick="javascript:modifyLinks('SubLink','<%=subLink.getName()%>')">
			          		<%= subLink.getDisplayName() %></a>
			         	</li>
					<%
					}
					
				}
				%></ul><%
			}
			}else{//INACTIVE LINKS
				%>
					<li><a href="#" onClick="javascript:modifyLinks('Link','<%=leftLink.getName()%>')">
		          		<%= leftLink.getDisplayName() %></a>
		         	</li>
				<%
			}
			%></li><%
		}
	}
			%>
</ul>

<!-- <div id='cssmenu'>
<ul>
   <li class='active has-sub'><a href='#'><span>Corporate</span></a>
      <ul>
         <li class='has-sub'><a href='#'>Global View</a></li>
         <li class='has-sub'><a href='#'>Focused View</a></li>
      </ul>
   </li>
	  <li><a href='#'><span>Retail</span></a></li>
	  <li><a href='#'><span>Reports</span></a></li>
  <li  class='active has-sub'><a href='#'><span>My Profile</span></a>
  <ul>
       <li class='has-sub'><a href='#'>Change Password</a></li>
  	<li class='has-sub'><a href='#'>Log Details</a></li>
     </ul>
  </li>
</ul>
</div> -->
  <!-- E N D : Left Navigation Menu -->
  <form name="insleftlinks" METHOD="POST" style="display: none;">
  <input type="hidden" name="mode">
  <INPUT TYPE="hidden" NAME="leftlink" VALUE="<%= spx.getActiveLink()%>">
  <INPUT TYPE="hidden" NAME="sublink" VALUE="<%= spx.getActiveSubLink()%>">
  <INPUT TYPE="hidden" NAME="tab" VALUE="<%=spx.getActiveTab()%>">
  </form>
</body>
