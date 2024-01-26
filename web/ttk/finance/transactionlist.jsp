<%
/**
 * @ (#) transactionlist.jsp 09th June 2006
 * Project      : TTK HealthCare Services
 * File         : transactionlist.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 09th June 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/transactionlist.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script>var TC_Disabled = true;</script>

<%
	pageContext.setAttribute("TypeID",Cache.getCacheObject("transType"));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/TransactionSearchAction.do">

<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	    	<td width="57%">Transactions</td>
	    	<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		</tr>
	</table>
<!-- E N D : Page Title -->

<div class="contentArea" id="contentArea">
<html:errors/>

<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
	   	<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
		    	<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
		    		<bean:message name="updated" scope="request"/>
		    	</td>
		 	</tr>
		</table>
	</logic:notEmpty>
<!-- E N D : Success Box -->

<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
    	<tr>
	  		<td nowrap>Transaction No.:<br>
	  			<html:text property="transactionNbr" styleClass="textBox textBoxMedium" maxlength="10"/>
	  		</td>
        	<td nowrap>Type:<br>
        		<html:select property="transactionType"  styleClass="selectBox selectBoxMedium">
					<html:option value="">Any</html:option>
	  				<html:options collection="TypeID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
          	</td>
        	<td nowrap>From:<br><html:text property="startDate" styleClass="textBox textDate" maxlength="10"/>
				<A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','frmTransSearch.startDate',document.frmTransSearch.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
        	</td>
        	<td nowrap>To:<br><html:text property="endDate" styleClass="textBox textDate" maxlength="10"/>
	        	<A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','frmTransSearch.endDate',document.frmTransSearch.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
        	</td>
	  		<td valign="bottom" nowrap width="100%"><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
      	</tr>
	</table>
<!-- E N D : Search Box -->

<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData"/>
<!-- E N D : Grid -->

<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td height="28" nowrap class="fieldGroupHeader">Bank Balance :<br>
            <html:text property="bankBalance" styleClass="textBox textBoxMedium textBoxDisabled" disabled="true"/></td>

        	<td align="right">
	        	<%
					if(TTKCommon.isAuthorized(request,"Add"))
					{
				%>
					<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAdd()"><u>A</u>dd</button>&nbsp;
	        	<%
		        	}// end of if(TTKCommon.isAuthorized(request,"Add"))
		        	if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Add"))
					{
				%>
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReverse()"><u>R</u>everse</button>&nbsp;

				<%
		        	}// end of if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Add"))
		        %>
        	</td>
      	</tr>
      	<ttk:PageLinks name="tableData"/>
	</table>
<!-- E N D : Buttons and Page Counter -->
</div>
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
<input type="hidden" name="child" value="">
</html:form>
<!-- E N D : Content/Form Area -->