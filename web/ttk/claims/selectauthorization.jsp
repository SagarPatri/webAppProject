<%
/**
 * @ (#)  selectauthorization.jsp July 17,2006
 * Project      : TTK HealthCare Services
 * File         : selectauthorization.jsp
 * Author       : Harsha Vardhan B N
 * Company      : Span Systems Corporation
 * Date Created : July 17,2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<%
	pageContext.setAttribute("preauthType",Cache.getCacheObject("preauthType"));
	pageContext.setAttribute("officeInfo",Cache.getCacheObject("officeInfo"));
%>

<SCRIPT  type="text/javascript" src="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/claims/selectauthorization.js"></script>
<SCRIPT type="text/javascript">
var TC_Disabled = true;
</script>

<!-- E N D : Tab Navigation -->
	<!-- S T A R T : Content/Form Area -->
	<html:form action="/SelectAuthorizationAction.do" method="post" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
        	<td>Select Authorization <bean:write name="frmSelectAuth" property="caption"/></td>
  		</tr>
	</table>
<!-- E N D : Page Title -->
  <div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td nowrap>Authorization No.:<br>
	        <html:text property="sAuthNo"  styleClass="textBox textBoxMedium" maxlength="60"/>
   	 	</td>
        <td nowrap>Hospital Name:<br>
		    <html:text property="sHospitalName"  styleClass="textBox textBoxMedium" maxlength="60"/>
	    </td>
   	 	
	  </tr>
	  <tr>
   	 	<td nowrap>Start Date:<br>
      		<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
            <A NAME="CalendarObjectauthDate11" ID="CalendarObjectauthDate11" HREF="#" onClick="javascript:show_calendar('CalendarObjectauthDate11','forms[1].sStartDate',document.forms[1].sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="sStartDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
		</td>
		<td nowrap>End Date:<br>
	  		<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
            <A NAME="CalendarObjectauthDate12" ID="CalendarObjectauthDate12" HREF="#" onClick="javascript:show_calendar('CalendarObjectauthDate12','forms[1].sEndDate',document.forms[1].sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="sEndDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
	 	</td>
		<td valign="bottom" nowrap>Vidal Health Branch:<br>
	        	<html:select property="sTtkBranch" styleClass="selectBox selectBoxMedium" >
				<html:option value="">Any</html:option>
				<html:optionsCollection name="officeInfo" label="cacheDesc" value="cacheId"/>
				</html:select>
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
	    <td width="100%" nowrap align="right" colspan="2" >
	   	  <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseList();"><u>C</u>lose</button>
	    </td>
	  </tr>
  	 <ttk:PageLinks name="tableData"/>
	</table>
  </div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="child" VALUE="SelectAuthorization">
	<html:hidden property="caption"/>
</html:form>
<!-- E N D : Main Container Table -->