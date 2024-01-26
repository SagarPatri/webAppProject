<%
/**
 * @ (#) critical procedurelist.jsp
 * Project       : TTK HealthCare Services
 * File          : procedurelist.jsp
 * Author        : Balaji C R B
 * Company       : Span Systems Corporation
 * Date Created  : Oct 22, 2007
 *
 * @author       : Balaji C R B
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/maintenance/criticalprocedurelist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	bAction = false; //to avoid change in web board in product list screen //to clarify
	var TC_Disabled = true;
</SCRIPT>
<%
	pageContext.setAttribute("daycareProduct", Cache.getCacheObject("daycareProduct"));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/CriticalProcedureListAction.do" >
	<!-- S T A R T : Page Title -->
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
	    		<td><bean:write name="frmSearchProcedure" property="caption"/></td>
			    <td width="43%" align="right" class="webBoard">&nbsp;</td>
		  </tr>
	</table>
<div class="contentArea" id="contentArea">
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Critical Procedure Code:<br>
          <input name="sProcedureCode" type="text" maxlength="250" class="textBox textBoxLarge" id="sProcedureCode" value="<bean:write name="frmSearchProcedure" property="sProcedureCode"/>" onkeypress="javascript:blockEnterkey(event.srcElement);" >
        </td>
         <td nowrap>Critical Procedure Description:<br>
          <input name="sProcedureName" type="text" maxlength="250" class="textBox textBoxLarge" id="sProcedureName" value="<bean:write name="frmSearchProcedure" property="sProcedureName"/>" onkeypress="javascript:blockEnterkey(event.srcElement);" >
        </td>
		<td nowrap>Search In:<br>
	    <html:select property="sDayCareProduct" styleClass="selectBox selectBoxMedium">
		    <html:optionsCollection name="daycareProduct" label="cacheDesc" value="cacheId" />
        </html:select>
        </td>
		<td valign="bottom" nowrap>
		<a href="#" accesskey="s" onClick="javascript:onSearch()" class="search">
	        <img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch
        </a>
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
		    	if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
		    	{
      		%>
      		<logic:match name="frmSearchProcedure" property="sDayCareProduct" value="USC">
      			<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAssociate();"><u>A</u>ssociate</button>&nbsp;
			</logic:match> 
			<logic:match name="frmSearchProcedure" property="sDayCareProduct" value="ATD">
      			<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onExclude();"><u>E</u>xclude</button>&nbsp;
			</logic:match>     			      			
      		<%
      			}//if(TTKCommon.isDataFound(request,"tableData") && TTKCommon.isAuthorized(request,"Delete"))
      		%>
				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;

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