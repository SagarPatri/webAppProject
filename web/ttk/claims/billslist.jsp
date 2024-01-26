<%
/**
 * @ (#) billslist.jsp 21th july 2006
 * Project      : TTK HealthCare Services
 * File         : billslist.jsp
 * Author       : Arun K.M
 * Company      : Span Systems Corporation
 * Date Created : 21th july 2006
 *
 * @author       : Arun K.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.ClaimsWebBoardHelper" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/claims/billslist.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>

<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true;
</script>

<html:form action="/ListofBillsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	    	<td width="57%">List of Bills - <bean:write name="frmListofBills" property="caption"/></td>
	    	<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Bill No.:<br>
            <html:text property="sBillNo" styleClass="textBox textBoxMedium" maxlength="10"/></td>
        <td nowrap>Allowed Amt. (Rs):<br>
            <html:text property="sBillAmount" styleClass="textBox textBoxMedium" maxlength="10"/></td>
        <td nowrap>Bill Date:<br>
        	<html:text property="sBillDate" styleClass="textBox textDate" maxlength="10"/>
        	<A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','forms[1].sBillDate',document.forms[1].sBillDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
        </td>

        <td width="100%" valign="bottom">
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
	    <td width="73%" nowrap>&nbsp;
	    </td>
	    <td align="right" nowrap>
   		<button type="button" name="Button" accesskey="v" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onViewBillsSummary()"><u>V</u>iew Bill Summary</button>&nbsp;
   			<%
   			if(TTKCommon.isDataFound(request,"tableData"))
		    		{
	    	%>
		    			<button type="button" name="Button" accesskey="i" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onInclude();"><u>I</u>nclude</button>&nbsp;
						<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onExclude();"><u>E</u>xclude</button>&nbsp;
			<%
	    			}//end of if(TTKCommon.isDataFound(request,"tableData"))
		    %>

   			<%
	    		if(ClaimsWebBoardHelper.getAmmendmentYN(request).equals("N"))
	    		{
	    			if(TTKCommon.isAuthorized(request,"Add"))
  					{
  			%>
  						<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAdd()"><u>A</u>dd</button>&nbsp;
	    	<%
                	}//end of if(TTKCommon.isAuthorized(request,"Add"))
		    		if(TTKCommon.isDataFound(request,"tableData"))
		    		{
	    	%>
		    <%
			    		if(TTKCommon.isAuthorized(request,"Delete"))
			    		{
	    	%>
	    					<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
	    	<%
	    				}//end of if(TTKCommon.isAuthorized(request,"Delete"))
		    		}//end of if(TTKCommon.isDataFound(request,"tableData"))
		    	}//end of if(ClaimsWebBoardHelper.getAmmendmentYN(request).equals("N"))
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
	<input type="hidden" name="child" value="">
	</html:form>