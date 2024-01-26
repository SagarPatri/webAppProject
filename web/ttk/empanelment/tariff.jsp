<%
/**
 * @ (#) tariff.jsp 22nd Oct 2005
 * Project      : TTK HealthCare Services
 * File         : tariff.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : 22nd Oct 2005
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/tariff.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/TariffAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td>Tariff Details</td>
		    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Search Box -->
	<table class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" nowrap>Associated To:<br>
			<html:select property="associatedTo" styleClass="selectBox selectBoxMedium">
				<html:option value="">Any</html:option>
				<logic:notEmpty name="alAssociatedTo">
		        	<html:optionsCollection name="alAssociatedTo" label="cacheDesc" value="cacheId" />
	        	</logic:notEmpty>
			</html:select>
		</td>
        <td align="left" nowrap>Product / Scheme No.:<br>
            <html:text property="proPolNo" styleClass="textBox textBoxLarge" onkeypress="javascript:blockEnterkey(event.srcElement);"/></td>
        <td width="100%" align="left" valign="bottom">
	        <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
      </tr>
    </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
    	<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons -->
	<table class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		<ttk:PageLinks name="tableData"/>
	</table>
	<!-- E N D : Buttons -->
	<!-- E N D : Form Fields -->
   </div>
    <INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
 </html:form>