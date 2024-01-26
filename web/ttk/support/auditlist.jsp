<%
/**
 * @ (#) auditlist.jsp 11th May 2006
 * Project      : TTK HealthCare Services
 * File         : auditlist.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : 11th May 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/support/auditlist.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<%
	pageContext.setAttribute("officeInfo",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("qualityStatus",Cache.getCacheObject("qualityStatus"));
	pageContext.setAttribute("investType",Cache.getCacheObject("investType"));
%>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/AuditSearchAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>List of QC Audits</td>
	<td width="43%" align="right" class="webBoard">&nbsp;</td>
  </tr>
</table>
	<!-- E N D : Page Title -->
<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Type:<br>
            <html:select property="investType" styleClass="selectBox selectBoxMedium">
	    		<html:options collection="investType" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
		</td>
        <td nowrap>Cashless / Claim No.:<br>
            <html:text property="preAuthClaimNo" styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
        <td nowrap>Marked Date:<br>
            <html:text property="markedDate" styleClass="textBox textDate" maxlength="10"/>
            <A NAME="CalendarObjectempDate11" ID="CalendarObjectempDate11" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate11','forms[1].markedDate',document.forms[1].markedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="markedDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
        </td>
        </tr>
      <tr>
      	<td nowrap>Vidal Health Branch:<br>
   			 	<html:select property="officeInfo" styleClass="selectBox selectBoxMedium">
    			<html:option value="">Any</html:option>
        		<html:options collection="officeInfo" property="cacheId" labelProperty="cacheDesc"/>
    			</html:select>
		</td>

        <td valign="bottom" nowrap>Status:<br>
	            <html:select property="qualityStatus" styleClass="selectBox selectBoxMedium">
	    		<html:option value="">Any</html:option>
	        	<html:options collection="qualityStatus" property="cacheId" labelProperty="cacheDesc"/>
				</html:select>
        </td>
        <td valign="bottom" nowrap>
         <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        <td nowrap>&nbsp;</td>
        <td width="100%" nowrap>&nbsp;</td>
        </tr>
    </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <ttk:PageLinks name="tableData"/>
      </tr>
    </table>
    </div>
	<!-- E N D : Buttons and Page Counter -->

	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	</html:form>
	<!-- E N D : Content/Form Area -->