<%
/**
 * @ (#) predailytransferlist.jsp 17th February 2010
 * Project      : TTK HealthCare Services
 * File         : predailytransferlist.jsp
 * Author       : Balakrishna E
 * Company      : Span Systems Corporation
 * Date Created : 17th February 2010
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/predailytransferlist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true;	
</SCRIPT>
<%	
	pageContext.setAttribute("tdsInsuranceInfo", Cache.getCacheObject("tdsInsuranceInfo"));		
	pageContext.setAttribute("tdsClaimsInclExcl", Cache.getCacheObject("tdsClaimsInclExcl"));
%>
<html:form action="/PreDailyTransferAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
 		<tr>
   		<logic:notMatch name="frmPreDailyTransfer" property="tdsClaimsInclExcl" value="IEE" > 		
   			<td>List of TDS Claims</td>   			
   		</logic:notMatch>
   		<logic:match name="frmPreDailyTransfer" property="tdsClaimsInclExcl" value="IEE" > 		
			<td>List of Excluded TDS Claims</td>   			
   		</logic:match>  			
 		</tr>
	</table>
	<!-- E N D : Page Title -->	
	<div class="contentArea" id="contentArea">
		<html:errors/>		
		<!-- S T A R T : Search Box -->
		<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td nowrap>Insurance Company:<br>				
					<html:select property="sInsuranceCompanyID" styleId="select10" styleClass="selectBox selectBoxLargest">              			
              			<html:optionsCollection name="tdsInsuranceInfo" value="cacheId" label="cacheDesc"/>              
              		</html:select>
				</td>			
				<td nowrap>Claim Settlement No.:<br>				
					<html:text property="sClaimSettleNo" styleClass="textBox textBoxMedium" />
				</td>	
				<td nowrap>Action:<br>				
					<html:select property="tdsClaimsInclExcl" styleId="select10" styleClass="selectBox selectBoxMedium">              			
              			<html:optionsCollection name="tdsClaimsInclExcl" value="cacheId" label="cacheDesc"/>              
              		</html:select>
				</td>
			</tr>
			<tr>
				<td nowrap>Start Date:<br>				
					<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10" />
					<a name="sStartDate" id="sStartDate" href="#" onClick="javascript:show_calendar('sStartDate','forms[1].sStartDate',document.forms[1].sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
				</td>				
				<td nowrap>End Date:<br>
					<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10" />
					<a name="sEndDate" id="sEndDate" href="#" onClick="javascript:show_calendar('sEndDate','forms[1].sEndDate',document.forms[1].sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
				</td>
				<td nowrap align="left" valign="bottom" width="100%">
       				<a href="#" accesskey="s" onClick="javascript:onSearch()" class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
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
    			<td width="27%">&nbsp;</td>
        		<td width="73%" align="right">
        		<%
				if(TTKCommon.isDataFound(request,"tableData"))
		     		{
		     	%>
		     	<logic:match name="frmPreDailyTransfer" property="tdsClaimsInclExcl" value="IEE" >
        			<button type="button" name="Button" accesskey="I" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:include()"><u>I</u>nclude</button>&nbsp;
        		</logic:match>
        		<logic:match name="frmPreDailyTransfer" property="tdsClaimsInclExcl" value="IEI">
        			<button type="button" name="Button" accesskey="E" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:exclude()"><u>E</u>xclude</button>&nbsp;
        		</logic:match>	
        		<%	     		
    			}				
        		%>        	
        		&nbsp;</td>
      		</tr>
      		<ttk:PageLinks name="tableData"/>
		</table>		
	<!-- E N D : Buttons and Page Counter -->
	</div>

    <!-- E N D : Buttons and Page Counter -->
    <input type="hidden" name="rownum" value="">
	<input type="hidden" name="mode" value="">
	<input type="hidden" name="sortId" value="">
	<input type="hidden" name="pageId" value="">
	<INPUT TYPE="hidden" NAME="insCompID" VALUE="<bean:write name="frmPreDailyTransfer" property="sInsuranceCompanyID"/>">
	<INPUT TYPE="hidden" NAME="claimSetNo" VALUE="<bean:write name="frmPreDailyTransfer" property="sClaimSettleNo"/>">	
	<INPUT TYPE="hidden" NAME="startDate" VALUE="<bean:write name="frmPreDailyTransfer" property="sStartDate"/>">
	<INPUT TYPE="hidden" NAME="endDate" VALUE="<bean:write name="frmPreDailyTransfer" property="sEndDate"/>">
</html:form>
