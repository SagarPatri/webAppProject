<%
/** @ (#) intimationsearch.jsp March 14, 2008
 * Project    	 : TTK Healthcare Services
 * File       	 : intimationsearch.jsp
 * Author     	 : Balakrishna E
 * Company    	 : Span Systems Corporation
 * Date Created	 : March 14, 2008
 * @author 		 : Balakrishna E
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
<script language="javascript" src="/ttk/scripts/support/intimation.js"></script>
<script>var TC_Disabled = true;</script>
<%
	String strLink=TTKCommon.getActiveLink(request);
	boolean viewmode=false;	 
	pageContext.setAttribute("intimationStatus",Cache.getCacheObject("intimationStatus"));
	
%>
	<!-- S T A R T : Content/Form Area -->
	<html:form action="/IntimationAction.do" >
	<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>List of Intimations</td>
	<td width="43%" align="right" class="webBoard">&nbsp;</td>
  </tr>
</table>
  <html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Status:<br>
            <html:select property="StatusTypeID" styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>">
		       	<html:option value="">Any</html:option>	 
		  		<html:options collection="intimationStatus" property="cacheId" labelProperty="cacheDesc"/>     
			</html:select>
		</td>
        <td nowrap>Scheme No.:<br>
           <html:text property="PolicyNo" styleClass="textBox textBoxMedium" maxlength="60" />
        </td>
        <td valign="bottom" nowrap>Enrollment ID:<br>
        <html:text property="EnrollmentID" styleClass="textBox textBoxMedium" maxlength="60" />
		</td>
    </tr>
	<tr>
		<td valign="bottom" nowrap>Intimation Received From:<br>
            <html:text property="ReceivedAfter" styleClass="textBox textDate"  maxlength="10" />
            <A NAME="CalendarObjectReceivedAfter" ID="CalendarObjectReceivedAfter" HREF="#" onClick="javascript:show_calendar('CalendarObjectReceivedAfter','forms[1].ReceivedAfter',document.forms[1].ReceivedAfter.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
        </td>
		<td valign="bottom" nowrap>Intimation Received To:<br>
            <html:text property="RespondedDate" styleClass="textBox textDate"  maxlength="10" />
            <A NAME="CalendarObjectRespondedDate" ID="CalendarObjectRespondedDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectRespondedDate','forms[1].RespondedDate',document.forms[1].RespondedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
        </td>
	    <td valign="bottom">
	    <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
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