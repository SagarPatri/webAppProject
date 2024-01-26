<%
/** @ (#) historysearch.jsp May 08, 2006
 * Project    	 : TTK Healthcare Services
 * File       	 : historysearch.jsp
 * Author     	 : Raghavendra T M
 * Company    	 : Span Systems Corporation
 * Date Created	 : May 08, 2006
 * @author 		 : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/onlineforms/summarylist.js"></script>
<script>var TC_Disabled = true;</script>
<%
	String strLink=TTKCommon.getActiveLink(request);
	String strTabLink=TTKCommon.getActiveTab(request);
	String ampm[] = {"AM","PM"};
	//String HRMode = "N";
	boolean viewmode=false;
	pageContext.setAttribute("ampm",ampm);
	pageContext.setAttribute("strTabLink",strTabLink);
	pageContext.setAttribute("strLink",strLink);
%>
<logic:notMatch name="strLink" value="Online Forms">
<%	pageContext.setAttribute("claimHistoryType",Cache.getCacheObject("claimHistoryType")); %>
</logic:notMatch>
<logic:match name="strLink" value="Online Forms">
	<logic:notEmpty name="SelectedPolicy" >
		<logic:equal name="SelectedPolicy" property="loginType" value="H">
			<logic:match name="strTabLink" value="Policies">
			<% pageContext.setAttribute("claimHistoryType",Cache.getCacheObject("summaryType"));%>
			</logic:match>
			<logic:match name="strTabLink" value="Members">
			<% pageContext.setAttribute("claimHistoryType",Cache.getCacheObject("claimHistoryType"));%>
			</logic:match>
		</logic:equal>
		<logic:notEqual name="SelectedPolicy" property="loginType" value="H">
			<logic:match name="strTabLink" value="Members">
				<% pageContext.setAttribute("claimHistoryType",Cache.getCacheObject("claimHistoryType"));%>
			</logic:match>
		</logic:notEqual>
	</logic:notEmpty>
</logic:match>
			<!-- S T A R T : Content/Form Area -->
	<!-- S T A R T : Page Title -->
	<html:form action="/OnlineHistoryListAction.do">
	<table align="center" class="<%=TTKCommon.checkNull(PreAuthWebBoardHelper.getShowBandYN(request)).equals("Y") ? "pageTitleHilite" :"pageTitle" %>" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>
    <logic:notMatch name="strLink" value="Online Forms">
    History
	</logic:notMatch>
    <logic:match name="strLink" value="Online Forms">
    Summary
    </logic:match>
    <bean:write property="caption" name="frmHistoryList"/></td>
    <td width="49%" align="right" class="webBoard">&nbsp;
    <%@ include file="/ttk/common/toolbar.jsp" %>
    </td>
  </tr>
</table>
<html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Type:<br>
        <html:select property="PreAuthHistoryTypeID" name="frmHistoryList" styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>">
	          <html:options collection="claimHistoryType" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
		</td>
        <td nowrap>Start Date:<br>
        <html:text property="StartDate" styleClass="textBox textDate"  maxlength="10" />
            <A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmHistoryList.StartDate',document.frmHistoryList.StartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
        <td nowrap>End Date:<br>
        <html:text property="EndDate" styleClass="textBox textDate"  maxlength="10" />
            <A NAME="CalendarObjectPARDate1" ID="CalendarObjectPARDate1" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate1','frmHistoryList.EndDate',document.frmHistoryList.EndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
        <td width="100%" valign="bottom">
	        <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
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
    <td width="27%"> </td>
    <td width="73%" nowrap align="right">&nbsp;
    <logic:match name="strLink" value="Online Forms">
		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseList();"><u>C</u>lose</button>
	</logic:match>
    </td>
  </tr>
      <ttk:PageLinks name="tableData" />
</table>
</div>
	<!-- E N D : Buttons and Page Counter -->

	<!-- E N D : Content/Form Area -->

<!-- E N D : Main Container Table -->
	<input type="hidden" name="mode" value=""/>
	<input type="hidden" name="child" value="">
	<input type="hidden" name="rownum" value=""/>
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<html:hidden property="caption" />
	<html:hidden property="selectedroot" />
	<html:hidden property="selectednode" />

</html:form>