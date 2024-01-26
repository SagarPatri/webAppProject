<%
/** @ (#) benefitlist.jsp
 * Project     : TTK Healthcare Services
 * File        : benefitlist.jsp
 * Author      : Balaji C R B
 * Company     : Span Infotech India Pvt. Ltd
 * Date Created: July 02,2008
 *
 * @author 		 : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/calendar/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/claims/benefitlist.js"></SCRIPT>
<script>
	bAction=false;
	var TC_Disabled = true;
</script>

<%
	pageContext.setAttribute("CaseStatus", Cache.getCacheObject("caseStatus"));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/BenefitListAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">List of Benefits</td>
			<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<logic:notEmpty name="claimcreated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
			  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:message name="claimcreated" scope="request"/>
			  </td>
			</tr>
		</table>
	</logic:notEmpty>
	<!-- E N D : Success Box -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
		<tr class="searchContainerWithTab">
			<td nowrap>Parent Claim No.:<br>
            	<html:text property="sClaimNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td> 
        	<td width="25%" nowrap>Parent Claim Settlement No.:<br>
            	<html:text property="sClaimSettelmentNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
			<td nowrap>Case Status:<br>
				<html:select property="sCaseStatus" styleClass="selectBox selectBoxMedium">		  	 		
		        	<html:optionsCollection name="CaseStatus" label="cacheDesc" value="cacheId" />
            	</html:select>
			</td>					   	
        	
			 <td width="17%" nowrap>&nbsp;</td>
        	 <td width="16%" nowrap>&nbsp;</td>
        	 <td width="10%" nowrap>&nbsp;</td>   	
      	</tr> 
		<tr class="searchContainerWithTab">
			<td nowrap class="formLabel">Start Date:<br><html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>&nbsp;
				<A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','frmBenefitList.sStartDate',document.frmBenefitList.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></A>
 			</td>
			<td width="25%" nowrap class="formLabel">End Date:<br><html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>&nbsp;
				<A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','frmBenefitList.sEndDate',document.frmBenefitList.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
		    </td>
			<td valign="bottom" nowrap>
	        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
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
        	if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"SpecialPermission"))
	    	{
	    	%>
	    	<logic:equal name="frmBenefitList" property="sCaseStatus" value="CPE">
	    		<button type="button" id="appbutton" name="Button" accesskey="a" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:onApprove()"><u>A</u>pprove</button>&nbsp;
	    		<button type="button" id="rejbutton" name="Button" accesskey="r" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:onReject()"><u>R</u>eject</button>&nbsp;
        	</logic:equal>
        	<%
        	}//end of if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"SpecialPermission"))
        	%>
        	</td>
      	</tr>
      	<ttk:PageLinks name="tableData"/>
	</table>
	</div>
	<logic:equal name="frmBenefitList" property="sCaseStatus" value="CPE">
	<script>
		document.getElementById('chkAll').style.display='none';
	</script>
	</logic:equal>
	<!-- E N D : Buttons and Page Counter -->
	
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<html:hidden property="sApproveRejectFlag"/>
</html:form>
	<!-- E N D : Content/Form Area -->