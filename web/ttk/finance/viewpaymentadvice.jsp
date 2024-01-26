<%
/**
 * @ (#) viewpaymentadvice.jsp 30th oct 2006
 * Project      : TTK HealthCare Services
 * File         : viewpaymentadvice.jsp
 * Author       : Arun K.M
 * Company      : Span Systems Corporation
 * Date Created : 30th oct 2006
 *
 * @author       :Arun K.M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon"%>

<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/finance/viewpaymentadvice.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="JavaScript">
	var TC_Disabled = true;
</script>

<html:form action="/ViewPaymentAdviceAction.do">
	<logic:notEmpty name="fileName" scope="request">
		<script language="JavaScript">
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 82;
			var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			 window.open("/ViewPaymentAdviceAction.do?mode=doViewPaymentXL&displayFile=<bean:write name="fileName"/>&reportType=EXL&alternateFileName=<bean:write name="alternateFileName"/>",'PaymentAdvice',features); 
		</script>
	</logic:notEmpty>
		
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td width="57%">List of Claims</td>
    		<td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td nowrap>File Name:<br>
          		<html:text property="sFileName"  styleClass="textBox textBoxMedium" maxlength="60"/>
          	</td>        	
          	<td nowrap>Batch Number:<br>
          		<html:text property="sBatchNum"  styleClass="textBox textBoxMedium" maxlength="20"/>
          	</td>
          	<td nowrap>Claim Settlement No.:<br>
          		<html:text property="sClaimSettelmentNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
          	</td>
   			<td nowrap>Batch Date:<br>
         		<html:text property="sBatchDate" styleClass="textBox textDate" maxlength="10"/>
         		<a name="CalendarObjectempDate" id="CalendarObjectempDate" href="#" onClick="javascript:show_calendar('CalendarObjectempDate','forms[1].sBatchDate',document.forms[1].sBatchDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="sBatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
         		<a href="#" accesskey="s" onClick="javascript:onSearch()" class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        	</td>
        	<td width="100%">&nbsp;</td>
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
        	<td width="73%" align="right">&nbsp;</td>
      	</tr>
      	<ttk:PageLinks name="tableData"/>
    </table>
    <br>
    <!-- E N D : Buttons and Page Counter -->
    
    <input type="hidden" name="rownum" value="">
    <input type="hidden" name="mode" value="">
    <input type="hidden" name="sortId" value="">
    <input type="hidden" name="pageId" value="">
</html:form>