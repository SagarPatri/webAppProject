<%
/**
 * @ (#) banklist.jsp 10th June 2006
 * Project      : TTK HealthCare Services
 * File         : banklist.jsp
 * Author       : Arun K.M
 * Company      : Span Systems Corporation
 * Date Created : 10th June 2006
 *
 * @author       : Arun K.M
 * Modified by   : Arun K.M
 * Modified date :07 july 2006
 * Reason        :for modification of screen
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/banklist.js"></script>

<%


	pageContext.setAttribute("officeType",Cache.getCacheObject("officeType"));
	pageContext.setAttribute("cityType",Cache.getCacheObject("cityCode"));

%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/BankListAction.do">

<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
			<td>Select Bank</td>
		</tr>
	</table>
<!-- E N D : Page Title -->
<html:errors/>

<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td nowrap>Bank Name:<br>
	        	<html:text property="sBankName" styleClass="textBox textBoxMedium" maxlength="60" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
      		</td>
			<td nowrap>Office Type:<br>
		  		<html:select property="sOfficeType"  styleClass="selectBox selectBoxMedium">
	  				<html:options collection="officeType" property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
            </td>
	  		<td nowrap>Area:<br>
	  			<html:select property="sCity"  styleClass="selectBox selectBoxMedium">
					<html:option value="">Any</html:option>
	  				<html:options collection="cityType" property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
            </td>
	              <td width="100%" valign="bottom"><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
	        <td valign="bottom" nowrap width="100%">&nbsp;</td>
        </tr>
    </table>
<!-- E N D : Search Box -->

<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="tableData"/>
<!-- E N D : Grid -->

<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td width="100%" nowrap align="right" colspan="2" >
			    <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>
        	</td>
      	</tr>
      	<ttk:PageLinks name="tableData"/>
	</table>
<!-- E N D : Buttons and Page Counter -->

<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
<INPUT TYPE="hidden" NAME="tab" VALUE="">
<input type="hidden" name="child" value="BankList">
</html:form>
<!-- E N D : Content/Form Area -->