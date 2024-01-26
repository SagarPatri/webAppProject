<%
/** @ (#) claimsreqamtsearch.jsp
 * Project     : TTK Healthcare Services
 * File        : claimsreqamtsearch.jsp
 * Author      : Balakrishna E
 * Company     : Span Systems Corporation
 * Date Created: 9th,August 2010
 *
 * @author 		 :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/maintenance/claimreqamtsearch.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/ClmReqAmtChangesAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>List of Claims</td>
	  </tr>
	</table>
	<div class="contentArea" id="contentArea">
	<!-- E N D : Page Title --> 		
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td align="left" nowrap>Claim No.: <span class="mandatorySymbol">*</span><br>
            <html:text property="sClaimNumber" styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
        <td valign="bottom" width="100%" nowrap align="left"><br>	  		
			<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
		   </td>
      </tr>
    </table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
	 <ttk:HtmlGrid name="tableData" />
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  	<tr>
    <td width="73%" nowrap align="right">
    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
    </tr>
	</table>
		
</div>
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
</html:form>
