
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
/**
 * @ (#) brokerdetails.jsp 25/01/2018
 * Project      : TTK HealthCare Services
 * File         : brokerdetails.jsp
 * Author       : Aravind Kumar
 * Company      : RCS Technologies
 * Date Created : 25/01/2018
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import=" com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	pageContext.setAttribute("regAuthority", Cache.getCacheObject("regAuthority"));
	pageContext.setAttribute("officeInfo",Cache.getCacheObject("officeInfo"));

%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/enrollment/brokerdetails.js"></script>
<html:form action="/enrBrokerDetailAction.do" method="post">  
<body>
<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Broker Selection</td>
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	</table>
<!-- E N D : Page Title -->
<div class="contentArea" id="contentArea">
<!-- S T A R T : Search Box -->
<html:errors/>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	  <td nowrap>Broker Code:<br>
	     <html:text property="sCompanyCode" name="frmBrokerDetails" styleClass="textBox textBoxLarge" maxlength="150" />
	     </td>
	    <td nowrap>Broker Name:<br>
	     <html:text property="sCompanyName" name="frmBrokerDetails" styleClass="textBox textBoxLarge" maxlength="250"/>
	     </td>
	     <td nowrap>Broker Authority:<br>
			<html:select property="regAuthority" name="frmBrokerDetails" styleClass="selectBox" style="width:160px;">
		  	 	  <html:option value="">Any</html:option>
		          <html:optionsCollection name="regAuthority" label="cacheDesc" value="cacheId" />
            </html:select>
	    </td>
	    <td nowrap>Vidal Heath Branch:<br>
			<html:select property="officeInfo" name="frmBrokerDetails" styleClass="selectBox" style="width:160px;">
		  	 	  <html:option value="">Any</html:option>
		          <html:optionsCollection name="officeInfo" label="cacheDesc" value="cacheDesc" />
            </html:select>
	    </td>
	    <td width="100%" valign="bottom">
	    	<a href="#" accesskey="s" onClick="javascript:onSearch()"class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
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
	    <td width="73%" align="right">
    			<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd();"><u>A</u>dd</button>&nbsp;
    			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
      </td>
		</tr>	
		<ttk:PageLinks name="tableData"/>
 	</table>
 	</div>
<!-- E N D : Buttons and Page Counter -->

<INPUT TYPE="hidden" NAME="rownum" VALUE="">
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>
<!-- E N D : Main Container Table -->
</body>
</html>