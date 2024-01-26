<%
/** @ (#) filelist.jsp 
 * Project     : TTK Healthcare Services
 * File        : filelist.jsp
 * Author      : Kishor
 * Company     : RCS Technologies
 * Date Created: May 8,2014
 *
 * @author 		 : Kishor
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
 <%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
 <%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
 <%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
 <SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
 <SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/home.js"></SCRIPT>
 <script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
 <%
 	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
 
 %>

 <html:form action="/OnlineHomeAction.do" >
 <!-- S T A R T : Content/Form Area -->	
 	<!-- S T A R T : Page Title -->
 <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td>Important Information</td> 
     <td>
     <%
     if(userSecurityProfile.getLoginType().equals("E")||userSecurityProfile.getLoginType().equals("I")) 
     {
     %>
     	<!--  <td align="right"><a href="#" onclick="javascript:onECards()"><img src="/ttk/images/E-Cardbold.gif" alt="E-Card" title="E-Card" width="81" height="17" align="absmiddle" border="0" class="icons"></a></td>-->
     <%
     }
     %>
 	</tr>
 </table>
 <div class="contentArea" id="contentArea">
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
 	
 	 <ttk:OnlineHospHomeDetails/>
     <%
   
     }//end of if(userSecurityProfile.getLoginType().equals("E") && request.getAttribute("windowprdalert") !=null )
     else
     {
    %>
    	 <ttk:OnlineHomeDetails/>
    	 <%
     }//end of if(userSecurityProfile.getLoginType().equals("E") && request.getAttribute("windowprdalert") !=null )
    %>
 </div>
 <!--E N D : Content/Form Area -->
 <!-- E N D : Main Container Table --> 
 <INPUT TYPE="hidden" NAME="mode" VALUE="">
 <INPUT TYPE="hidden" NAME="leftlink" VALUE="">
 <INPUT TYPE="hidden" NAME="sublink" VALUE="">
 <INPUT TYPE="hidden" NAME="tab" VALUE="">
 <INPUT TYPE="hidden" NAME="seqID" VALUE="">
 <INPUT TYPE="hidden" NAME="fileName" VALUE="">

 </html:form>
