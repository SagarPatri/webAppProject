<%
/** @ (#) ttkoffice.jsp 21st Mar 2006
 * Project     : TTK Healthcare Services
 * File        : ttkoffice.jsp
 * Author      : Krishna K H
 * Company     : Span Systems Corporation
 * Date Created: 21st Mar 2006
 * 
 * @author 			Krishna K H
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 *
 */
 %>
 
<%@ page import="com.ttk.common.TTKCommon" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/ttkoffice.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/TTKOfficeAction.do" > 	

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Vidal Health Office Details </td>     
    </tr>
</table>
	<!-- E N D : Page Title --> 

	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<table align="center" class="formContainer rcContainer" border="0" cellspacing="1" cellpadding="0">
	  	<tr>
	   		<td class="fieldGroupHeader">Head Office: </td>
	  	</tr>
	  	<tr>
		    <td>	
				<div class="rcDiv">
				  <table width="350"  align="left" border="0" cellpadding="0" cellspacing="0" class="rcBorder">
			        <tr>
			          <td width="100%" align="left" class="rcText"><bean:write name="frmTTKOffice" property="headOfficeName"/></td>
			          <td align="right" class="rcIcons">
					<% if(TTKCommon.isAuthorized(request,"Add"))
	    				{
	    				%>
			          &nbsp;&nbsp;<a href="#" onClick="javascript:onAddIcon();"><img src="/ttk/images/AddIcon.gif" alt="Add New Zonal Office" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;<img src="/ttk/images/IconSeparator.gif" width="1" height="18" align="absmiddle">&nbsp;&nbsp;
			          <% }
			          	if(TTKCommon.isAuthorized(request,"Edit"))
	    				{
	    				%> 
			          <a href="#" onClick="javascript:onEditIcon();"><img src="/ttk/images/EditIcon.gif" alt="Edit Office" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;<img src="/ttk/images/IconSeparator.gif" width="1" height="18" align="absmiddle">&nbsp;&nbsp;
			          <% } %>
			          <a href="#" onClick="javascript:onClauseIcon();"><img src="/ttk/images/ClauseIcon.gif" alt="Configure Properties" width="16" height="15" border="0" align="absmiddle"></a>&nbsp;</td>
			        </tr>
			      </table>
				</div>
			</td>
	    </tr>
	    <tr>
	    	<td class="fieldGroupHeader">Regional/Branch Offices: </td>
	 	</tr>
	  	<tr>
		    <td colspan="2">
		    		<ttk:TreeComponent name="treeData"/> 
		    </td>
	  	</tr>
	  	<tr>
		    <td colspan="2" height="25" align="right">&nbsp;</td>
	    </tr>
	  	<tr>
		    <td colspan="2" class="buttonsContainerGrid" align="right">&nbsp;</td>
	    </tr>
		   <ttk:TreePageLinks name="treeData"/>
	</table>
	<!-- E N D : Form Fields -->	
    <!-- S T A R T : Buttons -->
    <!-- E N D : Buttons -->
    <p>&nbsp;</p>
	</div>
	<!-- E N D : Content/Form Area -->
	</td> 
  </tr>
</table> 
	<input type="hidden" name="child" value="">	
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="selectedroot" VALUE="">
	<INPUT TYPE="hidden" NAME="selectednode" VALUE="">
</html:form>
<!-- E N D : Main Container Table --> 
