<%
/**
 * @ (#) addgradinggeneral.jsp 15th Oct 2005
 * Project      : TTK HealthCare Services
 * File         : addgradinggeneral.jsp
 * Author       : Suresh M
 * Company      : Span Systems Corporation
 * Date Created : 15th Oct 2005
 *
 * @author       :
 * Modified by   : Raghavendra T M
 * Modified date : March 10,2006
 * Reason        :
 */
%>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/addgradinggeneral.js"></script>

<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
		{
			viewmode=false;
		}
	pageContext.setAttribute("locationCode",Cache.getCacheObject("locationCode"));
	pageContext.setAttribute("categoryCode",Cache.getCacheObject("categoryCode"));
%>
        <!-- S T A R T : Content/Form Area -->
	<html:form action="/UpdateGradingGeneralAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="51%">General - [<bean:write property="caption" name="frmGradingGeneral"/>]</td>
    <td>&nbsp;</td>
  </tr>
</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
	  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="updated" scope="request"/>
	     </td>
	  </tr>
	 </table>
    </logic:notEmpty>
	<!-- E N D : Success Box -->
	<!-- S T A R T : Form Fields -->
	<fieldset>
	<legend>General</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" class="formLabel">Location: <span class="mandatorySymbol">*</span></td>
        <td width="30%">
			<!-- ttk:SelectBox cacheObject="locationCode" cacheId="location"/-->
		<html:select property="location" name="frmGradingGeneral" styleClass="selectBox"  disabled="<%=viewmode%>">
	       <html:option value="">Select from list</html:option>
	       <html:options collection="locationCode" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
		</td>
        <td class="formLabel" width="20%">&nbsp;</td>
        <td width="30%">&nbsp;</td>
      </tr>
      <tr>
        <td class="formLabel">Category: <span class="mandatorySymbol">*</span></td>
        <td>
			<html:select property="category" name="frmGradingGeneral" styleClass="selectBox"  disabled="<%=viewmode%>">
	      	<html:option value="">Select from list</html:option>
	    	<html:options collection="categoryCode" property="cacheId" labelProperty="cacheDesc"/>
		</html:select>
		</td>
        <td class="formLabel">&nbsp;</td>
        <td>&nbsp;</td>
      </tr>
    </table>
	</fieldset>

	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <%
		if(TTKCommon.isAuthorized(request,"Edit"))
		{
	%>
    	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
    <%
	 	}//end of if
	%>
    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
    	</td>
  </tr>
</table>
</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<html:hidden property="caption"/>
	<input type="hidden" name="child" value="General">
	</html:form>
	<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->

