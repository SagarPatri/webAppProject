<%
/** @ (#) packagelist.jsp 5th May 2006
 * Project     : TTK Healthcare Services
 * File        : packagelist.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 5th May 2006
 *
 * @author 	   : Arun K N
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
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/dataentrypreauth/packagelist.js"></script>



<!-- S T A R T : Content/Form Area -->
	<html:form action="/DataEntryPackageListAction.do" >
	<!-- S T A R T : Page Title -->
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td><bean:write name="frmPackageSearch" property="caption"/></td>
		  </tr>
		</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Name:<br>
          <html:text property="sPackageName" styleClass="textBox textBoxLarge" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/></td>
		<td valign="bottom" nowrap>
			<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
		</td>
        <td width="100%">&nbsp;</td>
      </tr>
     </table>
	<!-- E N D : Search Box -->

	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="packageListData"/>
	<!-- E N D : Grid -->

	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="27%"></td>
	    <td width="73%" nowrap align="right">
	    	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
	    </td>
	  </tr>
	  <ttk:PageLinks name="packageListData"/>
	</table>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="PackageList">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<logic:notEmpty name="frmICDPCSCoding" property="frmChanged">
	 	<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>

	</html:form>
	<!-- E N D : Content/Form Area -->