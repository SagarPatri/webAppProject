<%
/**
 * @ (#) preAuthLogSearch.jsp 
 * Project      : TTK HealthCare Services Dubai
 * File         : preAuthLogSearch.jsp(Partner)
 * Author       : Vishwabandhu Kumar
 * Company      : RCS Technologies
 * Date Created : 
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.common.security.Cache"%>
<%
	pageContext.setAttribute("benefitType",Cache.getCacheObject("main4benifitTypes"));
	pageContext.setAttribute("status",Cache.getCacheObject("claimStatus"));
	pageContext.setAttribute("alCountryCode",Cache.getCacheObject("countryCode"));

%>
<head>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/partnerLogin/preAuthLogSearch.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
</head>
<html:form action="/OnlinePartnerPreAuthAction.do" >
<div class="contentArea" id="contentArea">

			<!-- S T A R T : Content/Form Area -->
	<h4 class="sub_heading">Partner Pre-Approval Log Search</h4>
	<br>
	<html:errors/>


	<table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3">
    	      <tr>
    	      
    	      <td width="25%" nowrap>Pre-Approval No:<br>
    	            <html:text property="preAuthNumber" name="frmOnlinePreAuthLog" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
    	        </td>
    	        
    	        <td width="25%" nowrap>From date:<br>
				<html:text property="fromDate" name="frmOnlinePreAuthLog" styleClass="textBox textBoxMedium" maxlength="12"/>
 					<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmOnlinePreAuthLog.fromDate',document.frmOnlinePreAuthLog.fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
  						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
  					</a>
			 	</td>
    	        
    	        <td width="25%" nowrap>To date:<br>
    				<html:text property="toDate" name="frmOnlinePreAuthLog" styleClass="textBox textBoxMedium" maxlength="12" />
   				<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmOnlinePreAuthLog.toDate',document.frmOnlinePreAuthLog.toDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
					<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="toDate1" width="24" height="17" border="0" align="absmiddle">
				</a>   
    	        </td>
    	        
    	        <td width="25%" nowrap>Patient Name:<br>
      	            <html:text property="patientName" name="frmOnlinePreAuthLog" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
  			 </td>
    	     </tr>
    	     <tr>
    	        <td width="25%" nowrap>Authorization Number:<br>
    	            <html:text property="approvalNumber" name="frmOnlinePreAuthLog" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
    	        </td>
    	        
    	        <td width="25%" nowrap>Provider's Name:<br>
      	            <html:text property="providerName" name="frmOnlinePreAuthLog" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
   	            </td>
    	        
    	        <td width="25%" nowrap>Vidal Enrollment Id:<br>
    	            <html:text property="patientCardId" name="frmOnlinePreAuthLog" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
    	        </td>
    	        
    	     <td width="25%" nowrap>Benefit Type:<br>
       			<html:select property ="benefitType" styleClass="selectBox selectBoxMedium">
           			<html:option value="">Select from list</html:option>
           			<html:options collection="benefitType" property="cacheId" labelProperty="cacheDesc"/>
       			</html:select>
   	        </td>
    	     </tr>
    	     
    	     <tr>
    	        <td width="25%" nowrap>Status:<br>
         			<html:select property ="status" styleClass="selectBox selectBoxMedium">
	           			<html:option value="">Select from list</html:option>
	           			<html:options collection="status" property="cacheId" labelProperty="cacheDesc"/>
       				</html:select>
       	        </td>
       	        
   	        <td width="25%" nowrap>Reference No.:<br> 
				<html:text property="preAuthRefNo" name="frmOnlinePreAuthLog" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
			</td>
  	        <td width="25%" nowrap>Emirate ID:<br>
	            <html:text property="emirateID" name="frmOnlinePreAuthLog" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
  	        </td>
  	        
  	          <td width="25%" nowrap>Provider Country:<br>
			          <html:select property ="country" styleId="country" styleClass="selectBox selectBoxMedium">
			         	 <html:option value="">Select from list</html:option>
                 			<html:options collection="alCountryCode" property="cacheId" labelProperty="cacheDesc"/>
			          </html:select>
			       </td>
  	        
    	          </tr>
    	          
    	      <tr>
  	        <td width="25%" nowrap colspan="4" align="right"><!-- Search:<br> -->
		 		<button type="button" name="Button2" accesskey="s" class="olbtnSmall" onClick="onSearch()"><u>S</u>earch</button>
			</td>
    	     </tr>
   </table>
   
   <br>
   <!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		    <ttk:PageLinks name="tableData"/>
	</table>

<!--E N D : Content/Form Area -->

<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="rownum" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
</div>
</html:form>

