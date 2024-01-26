<%
/**
 * @ (#) enrollmentlist.jsp 4th May 2006
 * Project      : TTK HealthCare Services
 * File         : enrollmentlist.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : 4th May 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/inwardentry/claimlist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var TC_Disabled = true;
</SCRIPT>
<%
	pageContext.setAttribute("gender", Cache.getCacheObject("gender"));
	pageContext.setAttribute("listInsuranceCompany",Cache.getCacheObject("insuranceCompany"));
%>
<html:form action="/EnrollmentListAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td width="57%">Select Enrollment</td>
    		<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td nowrap>Beneficiary Name:<br>
            <html:text property="name" styleId="search1" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement)" maxlength="60"/>
       	</td>
      	<td width="21%" nowrap>Enrollment Id:<br>
            <html:text property="enrollmentID" styleId="search2" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement)" maxlength="60"/>
        </td>
     <!-- <%--	<td width="21%" nowrap>Policy Name:<br>
        	<html:text property="schemeName" styleId="search3" styleClass="textBox textBoxMedium" maxlength="60"/>
		</td>--%>-->
        <td width="10%" nowrap>Claim No.:<br>
        	<html:text property="claimNbr" styleId="search4" styleClass="textBox textBoxMedium" maxlength="60"/>
		</td>
<!-- <%--
        <td nowrap>Gender:<br>
            <span class="textLabel">
            	<html:select styleId="search5" property="genderTypeID" styleClass="selectBox selectBoxSmall">
            		<html:option value="">Any</html:option>
		      		<html:optionsCollection name="gender" label="cacheDesc" value="cacheId" />
				</html:select>
          	</span>
       	</td>--%>-->
        <td width="46%" valign="bottom" nowrap>
        <a href="#" class="search" accesskey="s" onClick="onSearch()" ><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
      </tr>
   <!-- <%--   <tr>
        <td nowrap>Policy No.:<br>
            <html:text property="policyNbr" styleId="search6" styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
        <td nowrap>Healthcare Company:<br>
            <html:select property="sInsuranceSeqID" styleId="search7" styleClass="selectBox selectBoxMedium" >
				 <html:option value="">Any</html:option>
				<html:optionsCollection name="listInsuranceCompany" label="cacheDesc" value="cacheId"/>
			</html:select>
		</td>
		</td>
        <td nowrap>Employee No.:<br>
            <html:text property="sEmployeeNo" styleId="search8" styleClass="textBox textBoxMedium" maxlength="60"/></td>
        <td nowrap>Corp. Name:<br>
            <html:text property="corpName" styleId="search9" styleClass="textBox textBoxMedium" maxlength="60"/>
        </td>
        <td nowrap>Show Latest:<br>
			<input name="showLatestYN" type="checkbox" value="Y" checked>
		</td>
        <td width="46%" valign="bottom" nowrap>
        <a href="#" class="search" accesskey="s" onClick="onSearch()" ><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
      </tr>--%>-->
    </table>
	<!-- E N D : Search Box -->
    <!-- S T A R T : Grid -->
    <ttk:HtmlGrid name="tableData"/>
    <!-- E N D : Grid -->
    <!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>&nbsp;</td>
    	<td width="73%" nowrap align="right">
    		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>
    	</td>
  	</tr>
  	<ttk:PageLinks name="tableData"/>
    </table>
</div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<input type="hidden" name="child" value="ClaimList">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<html:hidden property="showLatest"/>

	</html:form>
	<!-- E N D : Content/Form Area -->