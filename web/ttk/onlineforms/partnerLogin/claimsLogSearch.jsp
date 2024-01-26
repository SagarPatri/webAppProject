<%
/** 
 * @ (#) ClaimsLogSearch.jsp May 20 2017
 * Project      : TTK HealthCare Services Dubai
 * File         : ClaimsLogSearch.jsp
 * Author       : 
 * Company      : 
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
	pageContext.setAttribute("benefitType",Cache.getCacheObject("benifitTypes"));
	pageContext.setAttribute("status",Cache.getCacheObject("claimStatus"));
	pageContext.setAttribute("alCountryCode",Cache.getCacheObject("countryCode"));

%>
<head>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/partnerLogin/claimsLogSearch.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>

</head>
<html:form action="/OnlinePartnerClaimsSearchAction.do" >
<div class="contentArea" id="contentArea">
<!-- S T A R T : Content/Form Area -->

	<h4 class="sub_heading">Partner Claims Log Search</h4>
	<br>
<html:errors/>


	<table align="center" class="searchContainer" border="0" cellspacing="3" cellpadding="3" >
	
    	      <tr>
				<td colspan="2" align="left">Date of Treatment : </td>
				<td colspan="3" align="left"> Claim submission date : </td>
				</tr>
    	      <tr>
    	      
    	        <td width="25%" nowrap>From date:<br>
				<html:text property="trtmtFromDate" name="frmOnlineClaimLog" styleClass="textBox textBoxMedium" maxlength="12"/>
 					<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmOnlineClaimLog.trtmtFromDate',document.frmOnlineClaimLog.trtmtFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
  						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
  					</a>
    	        </td>
    	        
    	        <td width="25%" nowrap>To date:<br>
    				<html:text property="trtmtToDate" name="frmOnlineClaimLog" styleClass="textBox textBoxMedium" maxlength="12" />
   				<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmOnlineClaimLog.trtmtToDate',document.frmOnlineClaimLog.trtmtToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
					<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="toDate1" width="24" height="17" border="0" align="absmiddle">
				</a>   
    	        </td>
    	        
    	       
       	        
    	        <td width="25%" nowrap>From date:<br>
				<html:text property="clmFromDate" name="frmOnlineClaimLog" styleClass="textBox textBoxMedium" maxlength="12"/>
 					<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmOnlineClaimLog.clmFromDate',document.frmOnlineClaimLog.clmFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
  						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
  					</a>
    	        </td>
    	        
    	        <td width="25%" nowrap>To date:<br>
    				<html:text property="clmToDate" name="frmOnlineClaimLog" styleClass="textBox textBoxMedium" maxlength="12" />
   				<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmOnlineClaimLog.clmToDate',document.frmOnlineClaimLog.clmToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
					<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="toDate1" width="24" height="17" border="0" align="absmiddle">
				</a>   
    	        </td>
    	        
    	        <td width="25%" nowrap>Patient Name:<br>
      	            <html:text property="patientName" name="frmOnlineClaimLog" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
   	        </td>
    	     </tr>
    	     <tr>
    	     
    	        <td width="25%" nowrap>Status:<br>
         			<html:select property ="status" styleClass="selectBox selectBoxMedium">
	           			<html:option value="">Select from list</html:option>
	           			<html:options collection="status" property="cacheId" labelProperty="cacheDesc"/>
       				</html:select>
       	        </td>
       	        
       	        
    	        <td width="25%" nowrap>Invoice Number:<br>
    	            <html:text property="invoiceNumber" name="frmOnlineClaimLog" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
       	        </td>
       	        
    	        <td width="25%" nowrap>Batch Number:<br>
    	            <html:text property="batchNumber" name="frmOnlineClaimLog" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
    	        </td>
       	        
    	        <td width="25%" nowrap>Vidal Enrollment Id:<br>
    	            <html:text property="patientCardId" name="frmOnlineClaimLog" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
    	        </td>
       	        
    	         <td width="25%" nowrap>Claim Number:<br>
    	            <html:text property="claimNumber" name="frmOnlineClaimLog" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
   	        </td>
    	     </tr>
    	     
    	  <tr>
			    <td width="25%" nowrap>Provider Name:<br>
      	            <html:text property="providerName" name="frmOnlineClaimLog" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
   	            </td>
		      	
			     <td width="25%" nowrap>Provider Country:<br>
			          <html:select property ="country" styleId="country" styleClass="selectBox selectBoxMedium">
			         	 <html:option value="">Select from list</html:option>
                 			<html:options collection="alCountryCode" property="cacheId" labelProperty="cacheDesc"/>
			          </html:select>
			       </td>
		     </tr>
    	         
    	     <tr>
    	          <td  colspan="5" align="right">
   	             &nbsp;&nbsp;&nbsp;&nbsp;<!-- Search: -->
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

