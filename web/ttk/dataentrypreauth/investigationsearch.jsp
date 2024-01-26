<%
/** @ (#) investigation.jsp May 04, 2006
 * Project    	 : TTK Healthcare Services
 * File       	 : investigation.jsp
 * Author     	 : Raghavendra T M
 * Company    	 : Span Systems Corporation
 * Date Created	 : May 04, 2006
 * @author 		 : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/preauth/investigation.js"></script>
<script>var TC_Disabled = true;</script>
<%
	String strLink=TTKCommon.getActiveLink(request);
	String ampm[] = {"AM","PM"};
	boolean viewmode=false;
	//if(((TTKCommon.isAuthorized(request,"Delete"))))
	//{
		//viewmode=false;
	//}
	pageContext.setAttribute("ampm",ampm);
	pageContext.setAttribute("strLink",strLink);
	pageContext.setAttribute("agencyType",Cache.getCacheObject("agencyType"));
	pageContext.setAttribute("investStatus",Cache.getCacheObject("investStatus"));
	pageContext.setAttribute("officeinfo",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("investType",Cache.getCacheObject("investType"));
%>
	<!-- S T A R T : Content/Form Area -->
	<html:form action="/InvestigationListAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>List of Investigations</td>
	<td width="43%" align="right" class="webBoard">&nbsp;</td>
  </tr>
</table>
  <html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Investigation No.:<br>
            <html:text property="InvestigationNo" styleClass="textBox textBoxMedium"  maxlength="60" /></td>
	    <td nowrap>Marked Date:<br>
            <html:text property="MarkedDate" styleClass="textBox textDate"  maxlength="10" />
            <A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].MarkedDate',document.forms[1].MarkedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
		</td>
        <td nowrap>Investigation Agency:<br>
            <span class="textLabel">
        <html:select property="InvestAgencyTypeID" styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>">
	       <html:option value="">Any</html:option>
	       <html:options collection="agencyType" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
		</span></td>
        <td valign="bottom" nowrap>Status:<br>
        <html:select property="StatusTypeID" styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>">
	       <html:option value="">Any</html:option>
	       <html:options collection="investStatus" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
		</td>
		  </tr>
      <tr>
        <td nowrap>Type:<br>
        <html:select property="TypeDesc" styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>">
	       <html:options collection="investType" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
		</td>
        <td nowrap>Cashless / Claim No.:<br>
            <html:text property="PreAuthClaimNo" styleClass="textBox textBoxMedium"  maxlength="60"  disabled="<%=viewmode%>" />
			</td>
        <td nowrap>Vidal Healthcare Branch:<br>
         <html:select property="OfficeSeqID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
			<html:option value="">Any</html:option>
			<html:optionsCollection name="officeinfo" label="cacheDesc" value="cacheId" />
		  </html:select></td>
        <td width="100%" valign="bottom" nowrap>
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
        <td width="27%"><a href="#"></td>
        <td width="73%" align="right">&nbsp;
		</td>
		</tr>
   <ttk:PageLinks name="tableData" />
    </table>
    </div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
<!-- E N D : Main Container Table -->
</html:form>