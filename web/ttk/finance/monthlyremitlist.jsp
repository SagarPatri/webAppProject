<%
/**
 * @ (#) monthlyremitlist.jsp 10th August 2009
 * Project      : TTK HealthCare Services
 * File         : monthlyremitlist.jsp
 * Author       : R.Navin Kumar
 * Company      : Span Systems Corporation
 * Date Created : 10th August 2009
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
<script language="javascript" src="/ttk/scripts/finance/monthlyremitlist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true;	
</SCRIPT>
<%	
	pageContext.setAttribute("tdsInsuranceInfo", Cache.getCacheObject("tdsInsuranceInfo"));
	pageContext.setAttribute("durationMonths", Cache.getCacheObject("durationMonths"));			
%>
<html:form action="/MonthlyRemitAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
 		<tr>
   			<td>List of Monthly Remittance Completed</td>   			
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
              			<html:option value="">Any</html:option>
              			<html:optionsCollection name="tdsInsuranceInfo" value="cacheId" label="cacheDesc"/>              
              		</html:select>
				</td>				
				<td nowrap>Year:<br>
					<html:text property="sYear" styleClass="textBox textDate" maxlength="4"/>					
				</td>
				<td nowrap>Month:<br>
					<html:select property="sMonthID" styleId="select10" styleClass="selectBox selectBoxSmall">              			
              			<html:optionsCollection name="durationMonths" value="cacheId" label="cacheDesc"/>              
              		</html:select>
				</td>				
				<td valign="bottom" nowrap align="left" width="100%">
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
        			if(TTKCommon.isAuthorized(request,"Generate Monthly Remittance"))
        			{
    				%>
        			<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:generateMonthlyRemit()"><u>G</u>enerate Monthly Remittance</button>&nbsp;
        			<%
        			}
        			%>
        		</td>
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
	<input type="hidden" name="frmEdit" value="">
</html:form>
