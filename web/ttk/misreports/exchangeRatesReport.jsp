<%
/**
 * @ (#) exchangeRatesReport.jsp 4th May 2018
 * Project      : TTK HealthCare Services
 * File         : exchangeRatesReport.jsp
 * Author       : Deepthi Meesala
 * Company      : RCS
 * Date Created : 4th May 2018
        
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/misreports/exchangeRatesReport.js"></SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
bAction = false;
var TC_Disabled = true;
</SCRIPT>
<%
	pageContext.setAttribute("countryCode", Cache.getCacheObject("countryCode"));//added for intX
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/MISFinanceReportsListAction.do"  method="post"   enctype="multipart/form-data">

    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Exchange Rates</td>
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	  </table>
	  <!-- E N D : Page Title -->
	  
	 <div class="contentArea" id="contentArea">
	 <html:errors />
	 <logic:notEmpty name="errorMsg" scope="request">
				<table align="center" class="errorContainer" border="0"
					cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/ErrorIcon.gif" alt="Error"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="errorMsg" scope="request" /></td>
					</tr>
				</table>
			</logic:notEmpty>
	 
	 
	<fieldset>
	 <legend>Report Parameters </legend>
	  <table border="0" align="center" cellpadding="0" cellspacing="0" class="searchContainer">
	  
	  <tr></tr> <tr></tr>	 <tr></tr>	
	  
	  
	  
	  	<tr>
						<td nowrap>Date:<span class="mandatorySymbol">*</span>
						<html:text property="exchangeratesDate" styleId="exchangeratesDate" styleClass="textBox textDate" />
						
			  			<A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','forms[1].exchangeratesDate',document.forms[1].exchangeratesDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="dvReceivedDate" width="24" height="17" border="0" align="absmiddle" ></a>
						
						</td>
						
						
						<td></td><td></td><td></td>
					<td nowrap>Country:<span class="mandatorySymbol">*</span>
   					<html:select property ="countryCode" styleClass="selectBox selectBoxMedium">
  					<html:option value="">Any</html:option>
          			<html:options collection="countryCode" property="cacheId" labelProperty="cacheDesc"/>
   					</html:select>
       				</td>	
						<td></td>
						
   					<td valign="bottom" width="100%" align="left">
    				<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
    				 </td>	
						
					</tr>
					
				 <tr></tr>	 <tr></tr>	 <tr></tr>	
					
	  
	  </table>
	  
	    <div>
	  
	  <ttk:HtmlGrid name="tableData"/> 
	  </div>
	 
	
   
     <br><br> 
	  
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
   <ttk:PageLinks name="tableData"/>
      <tr>
        <td width="27%"> </td>
        <td width="73%" nowrap align="center">
       <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateExchangeRatesReport();"><u>G</u>enerateReport</button>

		   &nbsp; &nbsp; &nbsp; &nbsp;
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
        </td>
        </tr>
        </table>	  
	  
	  
	  
	   </fieldset> 
	  
	  </div> 
	  
	
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	 <INPUT type="hidden" name="mode" value="doSearcExchangRates">
	
	 <html:hidden property="letterPath" name="frmMISFinanceReports" />
	
	
	
	
</html:form>
<!-- E N D : Main Container Table -->