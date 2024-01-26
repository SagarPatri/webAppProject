<% 
/** 
as Per Hospital Login
@ (#) hosppreauthsearch.jsp March 24, 2104
 * Project     : TTK Healthcare Services
 *  Author       :Satya Moganti
 * Company      : Rcs Technologies
 * Date Created : march 24 ,2014
 *
 * @author       :Satya moganti
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList,com.ttk.common.PreAuthWebBoardHelper"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.dto.administration.WorkflowVO"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/hospital/hospreauthsearchlist.js"></script>
<script>
bAction=false;
var TC_Disabled = true;
</script>
<%
UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
String strActiveSubLink=userSecurityProfile.getSecurityProfile().getActiveSubLink();
pageContext.setAttribute("sStatus", Cache.getCacheObject("preauthStatus"));

%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/OnlinePatSearchHospAction.do">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
	    <td width="57%">List of Cashless</td>
		<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	</tr>
</table>
<!-- E N D : Page Title -->

<div class="contentArea" id="contentArea">
<html:errors/>

<table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
      <tr>
       <td nowrap>Patient Name:<br>
            <html:text property="sPatientName" name="frmPatHospSearch" styleClass="textBoxWeblogin textBoxMediumWeblogin" styleId="search1" maxlength="60"/>
        </td>
      	<td nowrap>Enrollment Id:<br>
            <html:text property="sEnrollmentNumber" name="frmPatHospSearch" styleClass="textBoxWeblogin textBoxMediumWeblogin" styleId="search1" maxlength="60"/>
        </td>
        <td width="25%" nowrap>Scheme No.:<br>
            <html:text property="sPolicyNumber" name="frmPatHospSearch" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
        <td nowrap>Cashless Number:<br>
            	<html:text property="sPreAuthNumber" name="frmPatHospSearch" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search3" maxlength="60"/>
        </td>
        
         </tr>
         <tr>
         
    	<td nowrap>Status:<br>
	            <html:select property="sStatus" name="frmPatHospSearch" styleClass="selectBox selectBoxMedium" styleId="search4">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sStatus" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
          	<td nowrap>Authorization Number:<br>
            	<html:text property="sAuthNumber" name="frmPatHospSearch" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search3" maxlength="60"/>
        </td>
          	 <td nowrap>Date of Admission:<br>
	            <html:text property="sDOA"  styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].sDOA',document.forms[1].sDOA.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
          	
          	 <td nowrap>Cashless Received Date:<br>
	            <html:text property="sDateOfPat"  styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].sDateOfPat',document.forms[1].sDateOfPat.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
            
             </tr>
    	 <tr>
    	 
    	
  	    	<td valign="bottom" nowrap>
        	<a href="#" accesskey="s" onClick="javascript:onSearch()" class="search">
	        <img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch
       	 	</a>
    		</td>    
    		 	
    	
		</tr>
    </table>
   <%--
 

 <table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
      <tr>
      	<td nowrap>Enrollment Id:<br>
            <html:text property="sEnrollmentNumber" name="frmClmHospSearch" styleClass="textBoxWeblogin textBoxMediumWeblogin" styleId="search1" maxlength="60"/>
        </td>
        <td width="25%" nowrap>Scheme No.:<br>
            <html:text property="sPolicyNumber" name="frmClmHospSearch" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
        <td nowrap>Claim Number<br>
            	<html:text property="sClaimNumber" name="frmClmHospSearch" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search3" maxlength="60"/>
        </td>
    	<td nowrap>Status:<br>
	            <html:select property="sStatus" name="frmClmHospSearch" styleClass="selectBox selectBoxMedium" styleId="search4">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sStatus" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
      <td valign="bottom" nowrap>
        <a href="#" accesskey="s" onClick="javascript:onSearch()" class="search">
	        <img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch
        </a>
    	</td>    	
			
    </table>
--%>
    
	<!-- E N D : Search Box -->
<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData"/>
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="27%"></td>
     		<td width="73%" align="right">
   			    <!-- button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:backToPreAuthDashBoard()"><u>P</u>re-Auth DashBoard</button-->
   			    <button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard()"><u>C</u>opy to Web Board</button>&nbsp;
     		    <!-- button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="addPreAuth()"><u>A</u>dd</button-->&nbsp;
     		
     		</td>
     	</tr>
     	<ttk:PageLinks name="tableData"/>
</table>

<!-- Note Added for Hospital Login -->
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
			<font color=blue><b>Note :</b>  To Navigate Cashless details, please search using one or more criteria or leave it all blank and search.</font> 
			</td>
     		
     	</tr>
</table>


<!-- S T A R T : Buttons and Page Counter -->
	</div>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="leftlink" VALUE="">
	<INPUT TYPE="hidden" NAME="sublink" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="child" value="">
	<html:hidden property="showLatest"/>
	
    </html:form>
	<!-- E N D : Content/Form Area -->