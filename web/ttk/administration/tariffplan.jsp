<%
/** @ (#) tariffplan.jsp 28th Sep 2005
 * Project     : TTK Healthcare Services
 * File        : tariffplan.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 28th Sep 2005
 *
 * @author 			Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/tariffplan.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/TariffPlanAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>List of Plans</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Search Box -->
	
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Name:<br>
          <input name="sName" type="text" class="textBox textBoxLarge" id="companyname3" value="<bean:write name="frmTariffPlan" property="sName"/>" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"></td>
		<td valign="bottom" nowrap>
		<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
        <td width="100%">&nbsp;</td>
      </tr>
     </table>
	<!-- E N D : Search Box -->

	<!-- S T A R T : Grid -->
	    <ttk:HtmlGrid name="tableData" />
    <!-- E N D : Grid -->

	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	<tr>
    <td width="27%">&nbsp; </td>
    <td width="73%" nowrap align="right">
    	<%
	    	if(TTKCommon.isAuthorized(request,"Add"))
	    	{
	    %>
	    	<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddTariffPlan();"><u>A</u>dd</button>&nbsp;
	    <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	    	if(TTKCommon.isAuthorized(request,"Delete")&& TTKCommon.isDataFound(request,"tableData"))
	    	{
	    %>
    		<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>&nbsp
    	<%
	    	}//end of if(TTKCommon.isAuthorized(request,"Delete")&& TTKCommon.isDataFound(request,"tableData"))
	    %>
    </td>
  </tr>
		<ttk:PageLinks name="tableData"/>
</table>
</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	</html:form>
	<!-- E N D : Content/Form Area -->
