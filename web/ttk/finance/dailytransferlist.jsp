<%
/**
 * @ (#) dailytransferlist.jsp 6th August 2009
 * Project      : TTK HealthCare Services
 * File         : dailytransferlist.jsp
 * Author       : R.Navin Kumar
 * Company      : Span Systems Corporation
 * Date Created : 6th August 2009
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/dailytransferlist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true;	
</SCRIPT>
<%	
	pageContext.setAttribute("tdsInsuranceInfo", Cache.getCacheObject("tdsInsuranceInfo"));		
	pageContext.setAttribute("tdsRemittanceStatus", Cache.getCacheObject("tdsRemittanceStatus"));
%>
<html:form action="/DailyTransferAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
 		<tr>
   			<td>List of Claims</td>   			
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
				<td nowrap>Status:<br>				
					<html:select property="sDailyRemitStatusID" styleId="select10" styleClass="selectBox selectBoxMedium">              			
              			<html:optionsCollection name="tdsRemittanceStatus" value="cacheId" label="cacheDesc"/>              
              		</html:select>
				</td>
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
				if(TTKCommon.isAuthorized(request,"Generate Report"))
    			{
				%>
        			<button type="button" name="Button" accesskey="R" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:dailyReport()">Daily Transfer <u>R</u>eport</button>&nbsp;
        		<%
    			}
        		%>
        		<%
				if(TTKCommon.isAuthorized(request,"Generate Daily Transfer"))
    			{
					if(TTKCommon.isDataFound(request,"tableData"))
		     		{
						if("TDSP".equals(request.getParameter("sDailyRemitStatusID").toString()))
						{
    			%>        		
        			<button type="button" name="Button" accesskey="G" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:generateDailyTransfer()"><u>G</u>enerate Daily Transfer</button>&nbsp;
        		<%
						}
		     		}
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
</html:form>
