<%
/** @ (#) tariffitemlist.jsp 30th July 2005
 * Project     : TTK Healthcare Services
 * File        : tariffitemlist.jsp
 * Author      : Srikanth H.M
 * Company     : Span Systems Corporation
 * Date Created: 30th July 2005
 *
 * @author 	   : Srikanth H M
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/tariffitemlist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	bAction = false; //to avoid change in web board in product list screen
	var TC_Disabled = true;
</SCRIPT>
<%
	pageContext.setAttribute("generalCodePlan", Cache.getCacheObject("generalCodePlan"));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/TarrifItemSearchAction.do" >
	<!-- S T A R T : Page Title -->
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
	    		<td>List of Tariff Items</td>
			    <td width="43%" align="right" class="webBoard">&nbsp;</td>
		  </tr>
	</table>
<div class="contentArea" id="contentArea">
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Name:<br>
          <input name="sName" type="text" maxlength="250" class="textBox textBoxLarge" id="sName" value="<bean:write name="frmSearchTariffItem" property="sName"/>" onkeypress="javascript:blockEnterkey(event.srcElement);" ></td>
		<td nowrap>Type:<br>
	    <html:select property="generalCodePlan" styleClass="selectBox selectBoxMedium">
		  	<html:option value="">Any</html:option>
		    <html:optionsCollection name="generalCodePlan" label="cacheDesc" value="cacheId" />
        </html:select>
        </td>
		<td valign="bottom" nowrap>
		<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
		</td>
        <td width="100%">&nbsp;</td>
      </tr>
     </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
    <ttk:HtmlGrid name="tableData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		 <tr>
		    <td width="27%"></td>
		    <td width="73%" align="right">
		   <%
		    	if(TTKCommon.isAuthorized(request,"Add"))
      				{
      		%>
      			<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddEditUser();"><u>A</u>dd</button>&nbsp;
      		<%
      			}//end of if(TTKCommon.isAuthorized(request,"Add"))
      		%>


		    </td>
	  	</tr>
	  <ttk:PageLinks name="tableData"/>
	</table>
</div>
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<!-- E N D : Buttons and Page Counter -->
</html:form>
<!-- E N D : Content/Form Area -->