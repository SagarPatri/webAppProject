<%
/**
 * @ (#) onlineemembers.jsp Aug 3rd 2006
 * Project      : TTK HealthCare Services
 * File         : onlinemembers.jsp
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : Aug 3rd 2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.WebBoardHelper,com.ttk.common.TTKCommon,com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/corporatemember.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
function onClose()//kocb 
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDefault";
    document.forms[1].action="/OnlinePoliciesAction.do";
    document.forms[1].submit();
}//end of onClose()
</SCRIPT>
<%
	String strDisplay="display";
	pageContext.setAttribute("ActiveSubLink",TTKCommon.getActiveSubLink(request));
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	// Changes added for CR KOC1183
	 String strActiveLink=TTKCommon.getActiveSubLink(request);
	 //ADDED FOR KOC-1216
	 String EnrollmentNo="";
	 String PolicyOpted="";
	 EnrollmentNo = (String)request.getSession().getAttribute("OnMemActionEnrNo");
	 PolicyOpted = (String)request.getSession().getAttribute("PolicyOpted");
	
	 //ENDED
%>

<script> TC_Disabled = true;</script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/OnlineMemberAction.do" >
<logic:notEmpty name="SelectedPolicy">
	<logic:equal name="SelectedPolicy" property="enrollmentType" value="COR">
		<% strDisplay="hide"; %>
	</logic:equal>
	<logic:equal name="SelectedPolicy" property="enrollmentType" value="NCR">
		<% strDisplay="hide"; %>
	</logic:equal>
</logic:notEmpty>
<logic:empty name="frmMember" property="display">
	<!-- S T A R T : Page Title -->
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		 <tr>
		    <td width="50%">List of Members - <bean:write name="frmMember" property="caption"/>
		    <!-- kocb  
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>B</u>ack</button>
			 -->
		    </td>
		    <%
		    if(userSecurityProfile.getLoginType().equals("E")||userSecurityProfile.getLoginType().equals("I") ||userSecurityProfile.getLoginType().equals("OCI"))
		    {
			if(PolicyOpted.equalsIgnoreCase("Y"))
		    	{
		    %>
		    	<td align="right"><a href="#" onclick="javascript:onECards()"><img src="/ttk/images/E-Cardbold.gif" alt="E-Card" title="E-Card" width="81" height="17" align="absmiddle" border="0" class="icons"></a></td>
		    <%
			}
		    }
		    %>
		 </tr>
		</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
			  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:message name="updated" scope="request"/>
			  </td>
			</tr>
		</table>
	</logic:notEmpty>
	<!-- E N D : Success Box -->
	<html:errors/>
	<logic:notEmpty name="SelectedPolicy">
	<logic:equal name="SelectedPolicy" property="loginType" value="H">

     <table align="center" class="searchContainer rcContainer" border="0" cellspacing="3" cellpadding="3">
      <tr>
        <td nowrap>Enrollment No.:<br>
             <html:text property="sEnrollmentNbr" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" />
        </td>
        <td nowrap>Employee Name:<br>
             <html:text property="sMemberName" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" />
        </td>
        <td nowrap>Employee No.:<br>
             <html:text property="sEmployeeNum" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" />
        </td>
        <td nowrap>Member Name:<br>
             <html:text property="sCorpMemberName" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" />
        </td>
        <td align="left" width="100%" valign="bottom" nowrap>
        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
      </tr>
    </table>
	</logic:equal>
<!--kocb-->	
	<logic:equal name="SelectedPolicy" property="loginType" value="B">

     <table align="center" class="searchContainer rcContainer" border="0" cellspacing="3" cellpadding="3">
      <tr>
        <td nowrap>Enrollment No.:<br>
             <html:text property="sEnrollmentNbr" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" />
        </td>
        <td nowrap>Employee Name:<br>
             <html:text property="sMemberName" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" />
        </td>
        <td nowrap>Employee No.:<br>
             <html:text property="sEmployeeNum" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" />
        </td>
        <td nowrap>Member Name:<br>
             <html:text property="sCorpMemberName" styleClass="textBoxWeblogin textBoxMediumWeblogin" maxlength="60" />
        </td>
        <td align="left" width="100%" valign="bottom" nowrap>
        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
      </tr>
    </table>
   		
	</logic:equal>
	
	</logic:notEmpty>
	

    <!-- S T A R T : Form Fields -->
	<table align="center" class="formContainerWeblogin rcContainer" border="0" cellspacing="3" cellpadding="3" style="margin:0 auto;">
	  <tr>
    	<td height="10" colspan="2"></td>
	  </tr>
	  <tr>
	    <td colspan="2" align="center">
    		<ttk:OnlineTreeComponent name="treeData"/> <br>
	    </td>
	  </tr>
	<%//   Display the Page link only for HR login%>
    <tr>
	    <td height="25" colspan="2" align="right" class="textAlignRight">
			<%if(!userSecurityProfile.getLoginType().equals("B")){//Not Showing for Broker users when Configured from WebConfig
		    	if(TTKCommon.isAuthorized(request,"Add"))
		    	{
	    	%>
				<logic:match name="ActiveSubLink" value="Enrollment">
					<logic:notEqual name="frmMember" property="policyStatus" value="POC">
			    		<logic:equal name="frmMember" property="empaddyn" value="Y">
							<button type="button" name="Button" accesskey="A" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddNewEnrollment();"><u>A</u>dd New Enrollment</button>&nbsp;
						</logic:equal>
					</logic:notEqual>
				</logic:match>
			<%
				}//end of if(TTKCommon.isAuthorized(request,"Add"))
			}
			%>
		</td>
     </tr>
	 <tr>
	   <td class="buttonsContainerGrid">  </td>
	   <td align="right" class="buttonsContainerGrid"> </td>
	 </tr>
	<logic:notEmpty name="SelectedPolicy">
		<logic:equal name="SelectedPolicy" property="loginType" value="H">
				  <ttk:TreePageLinks name="treeData"/>
		 </logic:equal>
		 
		 <logic:equal name="SelectedPolicy" property="loginType" value="B">
				  <ttk:TreePageLinks name="treeData"/>
		 </logic:equal>
		 
	</logic:notEmpty>
	</table>
	
<%//kocnewB
	if(userSecurityProfile.getLoginType().equals("B"))
    {%>
	<table align="center" border="0" cellspacing="0" cellpadding="0">
		 <tr>
		    <td width="50%" align="center">
		    <!-- kocb   -->
				
				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>B</u>ack</button>
			
		    </td>
		 </tr>
		</table>
		<% }
   %>
	<!-- E N D : Form Fields -->

<p>&nbsp;</p>
<ul>
<!-- Changes added for CR KOC1183 -->
	<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<%
		if (userSecurityProfile.getLoginType().equalsIgnoreCase("E") && (strActiveLink.equalsIgnoreCase("Enrollment")))
		{
		%>
	
			<tr>
			<td><b>Note:</b></td>
			</tr>
			
			<tr>
			<td>
			<font color="#0C48A2"> <b><li></b>Please Click on <img src="/ttk/images/AddIcon.gif" alt="Add/Edit/Delete Member(s)" title="Add/Edit/Delete Member(s)" width="16" height="16" border="0">  to add/ edit/ delete members   OR for Additional coverage.</font></td>
			</tr>

			<tr>
			<td>
			<font color="#0C48A2"> <b><li></b>Please Click on Vidal Health ID ( Mentioned in BLACK -Block Letters) for Entering the  Personal Information/
			<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Account information for ECS transaction of Re-imbursement.</font></td>
			</tr>
		
			<%--CR requested as per KOC KOC1248  "Y0063" || "Y040" || "Y0073" --%>
		<%
		if (userSecurityProfile.getGroupID().equalsIgnoreCase("Y040") || userSecurityProfile.getGroupID().equalsIgnoreCase("Y0063") || userSecurityProfile.getGroupID().equalsIgnoreCase("Y0073")){
		%>
			
			<tr>
			<td>
			 <li> <font color="#0C48A2"> 
			 Any new addition (spouse/new born baby) should be enrolled within 30 days from the date of event</font></li>
			<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<li><font color="#0C48A2">For Healthcare Scheme, process and faq, please click on the below link
			</font></li>
			<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://indiahr.corp.yahoo.com/Process/Insurance/GroupHealthInsurance/index.htm" target="_blank"><u>Yahoo-GroupHealthInsurance</u></a>
			</td>
			</tr>
			
           <%
		}
	}
	  %>
			  </table>
		</ul>
			<%
			if (userSecurityProfile.getLoginType().equalsIgnoreCase("H") && (strActiveLink.equalsIgnoreCase("Enrollment"))) {
		%>
		<logic:equal name="frmMember" property="noteCaption" value="shownote">
		<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td><b>Note:</b></td>
		</tr>
		<tr>
			<td>
			<font  color="#0C48A2"> <b><li></b>Please Click on <img src="/ttk/images/AddIcon.gif" alt="Add/Edit/Delete Member(s)" title="Add/Edit/Delete Member(s)" width="16" height="16" border="0">  to add/ edit/ delete members  OR for Additional coverage.</font></td>
			</tr>
			<tr>
			<td>
			<font color="#0C48A2"> <b><li></b>Please Click on Vidal Health ID ( Mentioned in BLACK -Block Letters) for Entering the  Personal Information/<br>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Account information for ECS transaction of Re-imbursement.</font></td>
			</tr>
		</table>
		</logic:equal>
		<%
			}
		if(userSecurityProfile.getLoginType().equalsIgnoreCase("E") && (strActiveLink.equalsIgnoreCase("Enrollment")))
		{
		if(EnrollmentNo.contains("I310-01")||(EnrollmentNo.contains("I310-001"))){%>

			<!--- Added for IBM....17-->
		<table>
		<logic:match name="ActiveSubLink" value="Enrollment">
		 <tr>
			<td>
				<font color="#0C48A2">
				<b><li></b> To OPT OUT please click on <img src="/ttk/images/OPTOUT1.gif" alt="OPT IN\OUT" width="30" height="30" border="0" align="center" >Icon Above.  <br>
				</font>
			</td>
		 </tr>
		 </logic:match>
		</table>
		<!--Ended--->

		<%}
		}
		%>
		<!-- End changes added for CR KOC1183 -->
</div>


<!-- E N D : Buttons --></div>
<!-- E N D : Content/Form Area -->
 	<html:hidden property="rownum"/>
    <html:hidden property="mode" />
    <input type="hidden" name="selectedroot" value="">
    <input type="hidden" name="selectednode" value="">
    <input type="hidden" name="pageId" value="">
    <html:hidden property="sPolicySeqID" />

</logic:empty>

<logic:notEmpty name="frmMember" property="display">
	<html:errors/>
</logic:notEmpty>

</html:form>