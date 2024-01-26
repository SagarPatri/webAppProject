<%
/**
 * @ (#) insurancecompanylist.jsp 02nd Jan 2006
 * Project      : TTK HealthCare Services
 * File         : insurancecompanylist.jsp
 * Author       : Pradeep.R
 * Company      : Span Systems Corporation
 * Date Created : 02nd Jan 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/enrollment/insurancecompanylist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<%
	String strSubLink=TTKCommon.getActiveSubLink(request);
	pageContext.setAttribute("strSubLink",strSubLink);
	pageContext.setAttribute("listTTKBranch",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("listInsuranceCompany",Cache.getCacheObject("insuranceCompany"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ChangeOfficeAction.do" >
	<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Select Insurance Company Branch <logic:match name="strSubLink"  value="Float Account"><bean:write name="frmEnrlInsCompany" property="caption"/></logic:match></td>
  </tr>
</table>
	<!-- E N D : Page Title -->
	<html:errors/>
	<!-- S T A R T : Search Box -->
<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Insurance Company:<br>
          	<html:select property="sInsuranceSeqID" styleClass="selectBox selectBoxMedium" >
				<html:optionsCollection name="listInsuranceCompany" label="cacheDesc" value="cacheId"/>
			</html:select>
		</td>
        <td nowrap>Company Code:<br>
             <html:text property="sCompanyCode" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
        </td>
        <td nowrap>Branch Location:<br>
        	 <html:select property="sTTKBranchCode" styleClass="selectBox selectBoxMedium" >
				<html:option value="">Any</html:option>
				<html:optionsCollection name="listTTKBranch" label="cacheDesc" value="cacheId"/>
			</html:select>
        </td>
        <td valign="bottom" width="100%" nowrap>
	        <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        </td>
      </tr>
</table>
	<!-- E N D : Search Box -->

	<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="insuranceTableData"/>
	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
    <td width="73%" nowrap align="right">
    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
    </td>
  </tr>
  <ttk:PageLinks name="insuranceTableData"/>
</table>

	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->	</td>

<!-- E N D : Main Container Table -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<input type="hidden" name="child" value="Insurance Company">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
</html:form>