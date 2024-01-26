<%
/** @ (#) preauthReferralList.jsp 08 Dec 2016
 * Project     : ProjectX
 * File        : preauthReferralList.jsp
 * Author      : Kishor Kumar S H
 * Company     : RCS
 * Date Created: 08 Dec 2016
 *
 * @author 		 : Kishor Kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.dto.administration.WorkflowVO"%>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript"  SRC="/ttk/scripts/preauth/preauthReferralList.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>

<script>
bAction=false;
var TC_Disabled = true;
</script>
<%
	pageContext.setAttribute("insuranceCompany", Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("sTtkBranch",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("ProviderList",Cache.getCacheObject("ProviderList"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/PreAuthReferralAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">List of Referrals</td>
			<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	<tr class="searchContainerWithTab">
	<td nowrap>Referral ID:<br>
		   <html:text property="referralID"  styleClass="textBox textBoxLarge" maxlength="60"/>
        </td>
         <td nowrap>TPA Member ID:<br>
            	<html:text property="vidalMemberID"  styleClass="textBox textBoxLarge" maxlength="60"/>
     </td>
<!-- <td nowrap>GLobalNet Member ID:<br>
		    	<html:text property="globalNetID"  styleClass="textBox textBoxLarge" maxlength="60"/>
	 </td> -->
	</tr>
		<tr class="searchContainerWithTab">
		 <td nowrap>Patient/Member Name:<br>
		    	<html:text property="patOrMemName"  styleClass="textBox textBoxLarge" maxlength="60"/>
        </td>
		 <td nowrap>Member Company Name:<br>
            	<html:text property="memCompName"  styleClass="textBox textBoxLarge" maxlength="60"/>
        	</td>
		 <td nowrap>Policy No.:<br>
            	<html:text property="sPolicyNumber"  styleClass="textBox textBoxLarge" maxlength="60"/>
        	</td>
    	</tr>
    	<tr class="searchContainerWithTab">
      <td nowrap>Branch Location:<br>
           <html:select property="vidalBranch" styleClass="selectBox selectBoxMedium" styleId="search13" >
		<!-- 	<html:option value="">Any</html:option> -->
			<html:optionsCollection name="sTtkBranch" label="cacheDesc" value="cacheId"/>
		</html:select>
		</td>
			
    	 <td nowrap>Status:<br>
	            <html:select property="status" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:option value="REF">Referred</html:option>
		        	<html:option value="INP">In-Progress</html:option>
            	</html:select>
          	</td>
          <td nowrap>Provider Name:<br>
			      <html:select name="frmPreAuthReferral" property="providerName" styleClass="selectBox selectBoxLarge">
						  <html:option value="">Select from list</html:option>
						<html:optionsCollection name="ProviderList" label="cacheDesc" value="cacheId" />
				  </html:select>
     	 </td>
        	</tr>  	
        	
    	<tr class="searchContainerWithTab">
    	<td nowrap> Payer Name:<br>
    	<html:select property="payerName" styleClass="selectBox selectBoxMedium" styleId="search12">
	  	 	  <html:option value="">Any</html:option>
	          <html:optionsCollection name="insuranceCompany" label="cacheDesc" value="cacheId" />
         </html:select>
		    </td>
          <td nowrap>Letter Sent Date:<br>
            	From 
					<html:text property="ltrFromDt" styleClass="textBox textDate" maxlength="10" />
					<a name="CalendarObjectempDate" id="CalendarObjectempDate" href="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmPreAuthReferral.ltrFromDt',document.frmPreAuthReferral.ltrFromDt.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
   						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
   					</a>
   					&nbsp;&nbsp;&nbsp;
   				To
					<html:text property="ltrToDt" styleClass="textBox textDate" maxlength="10" />
					<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmPreAuthReferral.ltrToDt',document.frmPreAuthReferral.ltrToDt.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
   						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate1" width="24" height="17" border="0" align="absmiddle">
   					</a>
            	
        	</td> 	
        	
        	<td valign="bottom" nowrap>
        	<a href="#" accesskey="s" onClick="javascript:onSearch()" class="search">
	        <img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch
       	 	</a>
    		</td> 
    		
        	</tr>  	        	
	</table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
     	<tr>
     		<td width="27%"></td>
     		<td width="73%" align="right">
     		<%
	     		if(TTKCommon.isDataFound(request,"tableData"))
		    	{
	    	%>
 				    <button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard()"><u>C</u>opy to Web Board</button>&nbsp;
     		<%
	        	}
            	if(TTKCommon.isAuthorized(request,"Add"))
		    	{
	    	%>
	            <button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="addPreAuthReferral()"><u>A</u>dd</button>&nbsp;
     		<%
     			}
        		if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
	    		{
	    	%>
     			<!-- <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button> -->
     		<%
        		}
        	%>
     		</td>
     	</tr>
     	<ttk:PageLinks name="tableData"/>
	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>

