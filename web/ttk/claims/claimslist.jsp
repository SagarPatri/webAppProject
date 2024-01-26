<%
/** @ (#) claimslist.jsp
 * Project     : TTK Healthcare Services
 * File        : claimslist.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created:
 *
 * @author 		 : Chandrasekaran J
 * Modified by   : Nagababu
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
<SCRIPT type="text/javascript"  SRC="/ttk/scripts/claims/claimslist.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>

<script>
bAction=false;
var TC_Disabled = true;
</script>
<%
pageContext.setAttribute("sAmount", Cache.getCacheObject("amount"));
pageContext.setAttribute("sSource", Cache.getCacheObject("source"));
pageContext.setAttribute("sStatus", Cache.getCacheObject("preauthStatus"));
pageContext.setAttribute("sTtkBranch", Cache.getCacheObject("officeInfo"));
pageContext.setAttribute("sPreAuthType", Cache.getCacheObject("preauthType"));
pageContext.setAttribute("claimType", Cache.getCacheObject("claimType"));
pageContext.setAttribute("ProviderList",Cache.getCacheObject("ProviderList"));
pageContext.setAttribute("insuranceCompany", Cache.getCacheObject("insuranceCompany"));
pageContext.setAttribute("modeOfClaims", Cache.getCacheObject("modeOfClaims"));
pageContext.setAttribute("spreauthCategory", Cache.getCacheObject("spreauthCategory"));
pageContext.setAttribute("submissionCatagory", Cache.getCacheObject("submissionCatagory"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ClaimsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">List of Claims</td>
			<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	<tr class="searchContainerWithTab">
	<td nowrap>Provider Name:<br>
		    <html:select property="sProviderName" name="frmClaimsList"  styleClass="selectBox textBoxLarge">
		    	 <html:option value="">Any</html:option>
  				<html:options collection="ProviderList"  property="cacheId" labelProperty="cacheDesc"/>
		    </html:select>
        </td>
        <td nowrap>Payer Name:<br>
		    <html:select property="sPayerName" name="frmClaimsList"  styleClass="selectBox textBoxLarge">
		    	 <html:option value="">Any</html:option>
  				<html:options collection="insuranceCompany"  property="cacheId" labelProperty="cacheDesc"/>
		    </html:select>
        </td>
        <td nowrap>Mode Of Claim:<br>
	            <html:select property="sModeOfClaim" styleClass="selectBox selectBoxMedium" name="frmClaimsList">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="modeOfClaims" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
<!--         <td nowrap>GlobalNet Member ID:<br>
		    	<html:text property="sGlobalNetMemID" name="frmClaimsList"  styleClass="textBox textBoxLarge" maxlength="250"/>
		    </td> -->
		    
		   <td nowrap>Member ID:<br>
            	<html:text property="dhpoMemberId"  styleClass="textBox textBoxLarge" maxlength="60"/>
     </td>  
		    
		    
		    
        </tr>
        <tr>
        
        <td nowrap>Batch No.:<br>
            	<html:text property="sBatchNO"  styleClass="textBox textBoxLarge" maxlength="60"/>
     </td>  
     
         <td nowrap>Sub Batch No.:<br>
            	<html:text property="subBatchID"  styleClass="textBox textBoxLarge" maxlength="60"/>
     </td>
	 <td nowrap>Invoice No.:<br>
		    	<html:text property="sInvoiceNO"  styleClass="textBox textBoxLarge" maxlength="60"/>
	 </td>
	           <td nowrap>Claim No.:<br>
	           <html:text property="sClaimNO"  styleClass="textBox textBoxLarge" maxlength="60"/>
              </td>
              <td nowrap>Claim Category:<br>
	            <html:select property="spreauthCategory" name="frmClaimsList" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="spreauthCategory" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td> 
             
    
	</tr>
		<tr class="searchContainerWithTab">
              <td nowrap>Claim  Assigned To :<br>
              <html:text property="sSpecifyName"   styleClass="textBox textBoxMedium"/>
              </td>
		 <td nowrap>Settlement No.:<br>
            	<html:text property="sSettlementNO"  styleClass="textBox textBoxLarge" maxlength="60"/>
        	</td>
		 <td nowrap>Policy No.:<br>
            	<html:text property="sPolicyNumber"  styleClass="textBox textBoxLarge" maxlength="60"/>
        	</td>
        	 <td nowrap> Enrollment Id:<br>
		    	<html:text property="sEnrollmentId"  styleClass="textBox textBoxLarge" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement)"/>
		    </td>
		    <td nowrap>Member Name:<br>
		    	<html:text property="sClaimantName"  styleClass="textBox textBoxMedium" maxlength="250"/>
		    </td>
		    
    	</tr>
    	<tr class="searchContainerWithTab">
		    <td nowrap>Civil ID:<br>
		    	<html:text property="sEmiratesID" name="frmClaimsList"  styleClass="textBox textBoxLarge" maxlength="250"/>
		    </td>
    	 <td nowrap>Claim Type:<br>
	            <html:select property="sClaimType" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="claimType" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
        	<td nowrap>Branch Location:<br>
	            <html:select property="sTtkBranch" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sTtkBranch" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
        	<td nowrap>Status:<br>
	            <html:select property="sStatus" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sStatus" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>  
		<td nowrap>Received Date:<br>
	            <html:text property="sRecievedDate"  styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].sRecievedDate',document.forms[1].sRecievedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            
            </td>  
            
            
            
            
            
            
            
        	</tr>
        	
        	<tr class="searchContainerWithTab">
          <td nowrap>Submission Category:<br>
	            <html:select property="sProcessType" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="submissionCatagory" label="cacheDesc" value="cacheId" />
            	</html:select>
            	 <span id="searchID"/>
            	 <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
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
	<td></td>
	<td></td>	
	<td><!--button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="addClaim()"><u>A</u>dd</button-->&nbsp;</td>
	</tr>
    	<tr>
    		<td width="27%"></td>
        	<td width="73%" align="right">
        	<%
        	if(TTKCommon.isDataFound(request,"tableData")){
	    	%>
        		<button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard()"><u>C</u>opy to Web Board</button>&nbsp;
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
	<!-- E N D : Content/Form Area -->