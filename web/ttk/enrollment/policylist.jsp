<%
/** @ (#) policylist.jsp Feb 2, 2006
 * Project     : TTK Healthcare Services
 * File        : policylist.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: Feb 2, 2006
 * 
 * @author 		 : Arun K N
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/enrollment/policylist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
bAction = false; //to avoid change in web board in product list screen
var TC_Disabled = true;
</SCRIPT>
<%
	pageContext.setAttribute("listSwitchType",Cache.getCacheObject("enrollment"));
%>
<!-- S T A R T : Content/Form Area -->	
	<html:form action="/PolicyAction.do"> 
	<!-- S T A R T : Page Title -->
	<logic:match name="frmPolicyList" property="switchType" value="ENM">
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmPolicyList" property="switchType" value="END">
	<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	  <tr>
	    <td><bean:write name="frmPolicyList" property="caption"/></td>     
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Search Box -->
	<table align="center" class="tablePad"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="10%" nowrap class="textLabelBold">Switch to:</td>
        <td width="90%">
	        <html:select property="switchType" styleClass="specialDropDown" styleId="switchType" onchange="javascript:onSwitch()">
				<html:options collection="listSwitchType" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
		</td>
      </tr>   
    </table>
    <ttk:PolicySearchBox/>
    <!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="policyListData"/> 
	<!-- E N D : Grid --> 
    <!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="27%">&nbsp;</td>
        <td width="73%" align="right">
		<%
        	if(TTKCommon.isDataFound(request,"policyListData"))
	    	{
	    %>
	    	<button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard()"><u>C</u>opy to Web Board</button>&nbsp;
        <%
        	}
        	if(TTKCommon.isDataFound(request,"policyListData") && TTKCommon.isAuthorized(request,"Delete"))
	    	{
	    %>
			<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>
        <%
        	}
        %>
        </td>
      </tr>
      <ttk:PageLinks name="policyListData"/>
    </table>
    </div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="sublink" VALUE="<%=TTKCommon.getActiveSubLink(request)%>">
	</html:form>	
	<!-- E N D : Content/Form Area -->
