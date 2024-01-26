<% 
/** 
as Per Hospital Login
@ (#) hospclaimsearch.jsp 
 * Project     : TTK Healthcare Services
 * File        : hospclaimsearch.jsp
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
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList,com.ttk.common.HospClaimsWebBoardHelper"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/hospital/cashlessSearch.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>

<script>
bAction=false;
var TC_Disabled = true;
</script>
<%
UserSecurityProfile userSecurityProfile = ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile"));
String strActiveSubLink=userSecurityProfile.getSecurityProfile().getActiveSubLink();

%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/OnlineCashlessHospAction.do">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td width="57%">List of Cashless</td>
		<td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	</tr>
</table>
<!-- E N D : Page Title -->

<div class="contentArea" id="contentArea">
<html:errors/>
 <table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
      <tr>
      	<td nowrap>Cashless Number<br>
            	<html:text property="sCashlessNumber" name="frmCashlessAdd" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search3" maxlength="60"/>
        </td>
        <td nowrap>Authorization Number:<br>
            	<html:text property="sAuthNumber" name="frmCashlessAdd" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search3" maxlength="60"/>
     	   </td>
     	   <td nowrap>Member Name:<br>
            	<html:text property="sMemberName" name="frmCashlessAdd" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search3" maxlength="60"/>
     	   </td>
      	<td nowrap>Enrollment Id:<br>
            <html:text property="sEnrollmentNumber" name="frmCashlessAdd" styleClass="textBoxWeblogin textBoxMediumWeblogin" styleId="search1" maxlength="60"/>
        </td>
        
        
        </tr>
      
          	<tr>
          	<td nowrap>OTP Request:<br>
	            <html:select property="sOtpReq" name="frmCashlessAdd" styleClass="selectBox selectBoxMedium" styleId="search4">
		  	 		<html:option value="">Select from List</html:option>
		        	<html:option value="Y">Yes</html:option>
		        	<html:option value="N">No</html:option>
            	</html:select>
          	</td>
          	<td nowrap>Bills Pending:<br>
	            <html:select property="sBillsPending" name="frmCashlessAdd" styleClass="selectBox selectBoxMedium" styleId="search4">
		  	 		<html:option value="">Select from List</html:option>
		        	<html:option value="Y">Yes</html:option>
		        	<html:option value="N">No</html:option>
            	</html:select>
          	</td>
          	 <td nowrap>Start Date:<br>
	            <html:text property="sStartDate"  styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectDOA" ID="CalendarObjectDOA" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].sStartDate',document.forms[1].sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
             <td nowrap>End Date:<br>
	            <html:text property="sEndDate"  styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectDOA" ID="CalendarObjectDOA" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].sEndDate',document.forms[1].sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
  	    	  
    	 </tr>
    	 <tr>
    	 
            
            <td valign="bottom" nowrap>
        	<a href="#" accesskey="s" onClick="javascript:onSearchOtp()" class="search">
	        <img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch
       	 	</a>
    		</td>  
    	 </tr>
    </table>
    
	<!-- E N D : Search Box -->
<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData"/>
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="27%"></td>
     		<td width="73%" align="right">
   			    <button type="button" name="Button" accesskey="a" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:onAdd()"><u>A</u>dd</button>&nbsp;
     			
     		</td>
     	</tr>
     	<ttk:PageLinks name="tableData"/>
</table>


<!-- Note Added for Hospital Login -->

<!-- E N D :  Grid -->
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
	
	
	

	
    </html:form>
	<!-- E N D : Content/Form Area -->