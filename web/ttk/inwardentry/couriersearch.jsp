<%
/**
 * @ (#) couriersearch.jsp 26th July 2006
 * Project      : TTK HealthCare Services
 * File         : couriersearch.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 26th July 2006
 *
 * @author       :Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/inwardentry/couriersearch.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>

 <%
	pageContext.setAttribute("courierName", Cache.getCacheObject("courierName"));
 %>
<html:form action="/CourierListAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>Select Courier</td>
			<td width="43%" align="right" class="webBoard">&nbsp;</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td nowrap>Courier No.:<br>
				<html:text property="sCourierNbr" styleClass="textBox textBoxMedium"/>
			</td>
			 <td nowrap class="formLabel">Consignment No.: <br>
              <html:text property="sdocketPODNbr" styleClass="textBox textBoxMedium" maxlength="60" />
               </td>
			<td nowrap>Courier Name :<br>
				<html:select property="sCourierName" styleClass="selectBox selectBoxMedium">
					<html:option value="">Any</html:option>
            		<html:optionsCollection name="courierName" label="cacheDesc" value="cacheId"/>
          		</html:select>
			</td>
			
			<td nowrap>Start Date:<br>
				<html:text property="sStartDate" styleClass="textBox textDate"/>&nbsp;
				<A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','frmCourierList.sStartDate',document.frmCourierList.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
			</td>
			<td nowrap>End Date:<br>
				<html:text property="sEndDate" styleClass="textBox textDate"/>&nbsp;
				<A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','frmCourierList.sEndDate',document.frmCourierList.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
			</td>
			<td width="100%" valign="bottom" nowrap>
				<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
			</td>
		</tr>
	</table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableDataCourier"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="27%"> </td>
	   		<td width="73%" align="right">
		   		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>&nbsp;
	   		</td>
	   		<ttk:PageLinks name="tableDataCourier"/>
		</tr>
	</table>
	<!-- E N D : Buttons and Page Counter -->
	<input type="hidden" name="child" value="Select Courier">
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>