<%
/**
 * @ (#) associateprocedurelist.jsp 24th Sep 2005
 * Project      : Vidal Health TPA  Services
 * File         : associateprocedurelist.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 24th Sep 2005
 *
 * @author       :Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon" %>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/associateprocedurelist.js"></script>
<%
	pageContext.setAttribute("ActiveLink",TTKCommon.getActiveLink(request));
%>
	<!-- S T A R T : Content/Form Area -->
	<html:form action="/AssociateProcedureAction" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td><bean:write name="frmSearchAssociate" property="caption"/></td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td nowrap>Procedure Code:<br><input name="procedurecode" type="text" class="textBox textBoxMedium" id="companyname3" value="<bean:write name="frmSearchAssociate" property="procedurecode"/>" maxlength="250"></td>
      	<td nowrap>Procedure Name:<br><input name="procedurename" type="text" value="<bean:write name="frmSearchAssociate" property="procedurename"/>" class="textBox textBoxLarge" id="companyname3" maxlength="250"></td>
        <td valign="bottom" width="100%">
        <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
        <td width="100%">&nbsp;</td>
      </tr>
     </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
    <ttk:HtmlGrid name="tableDataProcedure"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
 	 <tr>
	    <td width="27%"> </td>
	    <td width="73%" nowrap align="right">
		    <%
				   if(TTKCommon.isDataFound(request,"tableDataProcedure"))
				   {
			%>
						<button type="button" name="Button" accesskey="t" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAssociate();">Associa<u>t</u>e</button>&nbsp;
			<%
		    		}// end of if(TTKCommon.isDataFound(request,"tableDataProcedure"))
		    %>
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    </td>
  	</tr>
  	<ttk:PageLinks name="tableDataProcedure"/>
	</table>
	<!-- E N D : Buttons and Page Counter -->
</div>
<!-- E N D : Buttons and Page Counter -->
<input type="hidden" name="child" value="Procedure">
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
<logic:match name="ActiveLink" value="Pre-Authorization">
	<logic:notEmpty name="frmICDPCSCoding" property="frmChanged">
		 	<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</logic:match>
</html:form>