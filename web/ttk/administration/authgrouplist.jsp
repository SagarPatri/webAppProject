<%
/** @ (#) authgrouplist.jsp AUG 12, 2008
 * Project       : Vidal Health TPA  Services
 * File          : authgrouplist.jsp
 * Author        : Sendhil Kumar V
 * Company       : Span Systems Corporation
 * Date Created  : AUG 12, 2008
 *
 * @author 	     : Sendhil Kumar V
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon" %>

<script language="javascript" src="/ttk/scripts/administration/authgrouplist.js"></script>

<!-- S T A R T : Content/Form Area -->	
	<html:form action="/AuthGroupConfiguration.do"> 	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Auth Group Configuration - <bean:write name="frmAuthGroupList" property="caption"/> </td>
	  </tr>
	</table>
	<!-- E N D : Page Title --> 
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
    
    <!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="73%" nowrap>&nbsp;</td>
	    <td align="right" nowrap>
	    <%
	    	if(TTKCommon.isAuthorized(request,"Add"))
	    	{
    	%>
   			<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddGroup();"><u>A</u>dd</button>&nbsp;
        <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Add"))
		%>
            <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
	    </td>
	  </tr>
		<ttk:PageLinks name="tableData"/>
 	</table>
	</div>
    <!-- E N D : Buttons and Page Counter -->

    <INPUT TYPE="hidden" NAME="sortId" VALUE="">
   	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
    <INPUT TYPE="hidden" NAME="mode" VALUE="">
   	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
   	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	</html:form>
<!-- E N D : Content/Form Area -->