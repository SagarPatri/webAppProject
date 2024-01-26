<%
/** @ (#) pcslist.jsp
 * Project     		: TTK Healthcare Services
 * File        		: pcslist.jsp
 * Author      		: Balaji C R B
 * Company     		: Span Systems Corporation
 * Date Created		: August 21,2008
 *
 * @author 	   		:
 * Modified by   	:
 * Modified date 	:
 * Reason        	:
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/maintenance/pcslist.js"></script>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/PCSListAction">
		<!-- S T A R T : Page Title -->
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	      	<td>List of Procedure</td>
	     </tr>
		</table>
		<!-- E N D : Page Title -->
		<div class="contentArea" id="contentArea">
		<html:errors/>
		<!-- S T A R T : Search Box -->
		<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      	<tr>
        <td nowrap>Procedure Code:<br><html:text property="sPCSCode" styleClass="textBox textBoxMedium" maxlength="10" /></td>
        <td nowrap>Procedure Description:<br>
          <html:text property="sPCSName" styleClass="textBox textBoxLarge" maxlength="60"/></td>
		<td valign="bottom" nowrap>
			<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
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
	    <td width="27%"> </td>
	    <td width="73%" nowrap align="right">
	    <%
	       if(TTKCommon.isAuthorized(request,"Add"))
	       {
    	%>
		    <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd();"><u>A</u>dd</button>
		<%
			}//end of if(TTKCommon.isAuthorized(request,"Add"))
		%>    
	    </td>
	  	</tr>
		<ttk:PageLinks name="tableData"/>
	</table>
		<!-- E N D : Buttons and Page Counter -->
		</div>	
		<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">			
	</html:form>
<!-- E N D : Content/Form Area -->
