<!--Insurance_corporate_wise_hosp_network -->
<%
/** @ (#) hospitalinsurancelist.jsp Nov 08th, 2005
 * Project      : TTK Healthcare Services
 * File         : hospitallist.jsp
 * Author       : Bhaskar Sandra
 * Company      : Span Systems Corporation
 * Date Created : Nov 08th, 2005
 * @author 		: Bhaskar Sandra
 * Modified by  :
 * Modified date:
 * Reason       :
 */
 %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	pageContext.setAttribute("sCityCode", Cache.getCacheObject("cityCode"));
	pageContext.setAttribute("sOfficeInfo", Cache.getCacheObject("stateCode"));
	pageContext.setAttribute("associateCode", Cache.getCacheObject("associateHosCode"));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/hospitalinsurancelist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/HospitalInsuranceList.do" method="post" >

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td >List of Contacts <bean:write name="frmHospitalList" property="caption"/></td>
	  </tr>
	</table>
	
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
 <!-- S T A R T : Success Box -->
 <logic:notEmpty name="updated" scope="request">
  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
         <bean:message name="updated" scope="request"/>
     </td>
   </tr>
  </table>
 </logic:notEmpty>
 <!-- E N D : Success Box -->
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td nowrap>Empanelment&nbsp;No.:<br><html:text  name="frmHospitalList" property="empanelmentNO" styleClass="textBox textBoxMedium" maxlength="60"/></td>
        <td nowrap>Area:<br>
	        <html:select name="frmHospitalList" property="cityCode" styleClass="selectBox selectBoxMedium">
		  	 	  <html:option value="">Any</html:option>
		          <html:optionsCollection name="sCityCode" label="cacheDesc" value="cacheId" />
            </html:select>
        </td>
        <td nowrap>Provider&nbsp;Name:<br><html:text name="frmHospitalList" property="hospName" styleClass="textBox textBoxMedium" maxlength="250" /></td>
      </tr>
      <tr>
        <td nowrap>Emirate/State:<br>
	        <html:select name="frmHospitalList" property="officeInfo" styleClass="selectBox selectBoxMedium">
		  	 	  <html:option value="">Any</html:option>
		          <html:optionsCollection name="sOfficeInfo" label="cacheDesc" value="cacheId" />
            </html:select>
        </td>
	    <td nowrap>Search In:<br>
		    <html:select name="frmHospitalList" property="associateCode" styleClass="selectBox selectBoxMedium">
		          <html:optionsCollection name="associateCode" label="cacheDesc" value="cacheId" />
            </html:select>
        </td>
	    <td width="100%" valign="bottom">
	    	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
		</td>
      </tr>
    </table>
	<!-- E N D : Search Box -->

	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData" />
	<!-- E N D : Grid -->

	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
  	  <tr>
	    <td width="27%" align="left"></td>
	    <td width="73%" nowrap align="right">		
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>&nbsp;
					<logic:match name="frmHospitalList" property="associateCode" value="EML">
					<button type="button" name="Button" accesskey="t" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAssociate();">Associa<u>t</u>e</button>&nbsp;
					</logic:match>
					<logic:match name="frmHospitalList" property="associateCode" value="ASL">
					
					<button type="button" name="Button" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onExclude();"><u>E</u>xclude</button>&nbsp;
					</logic:match>
		
    	</td>
    	<ttk:PageLinks name="tableData"/>
  	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<input type="hidden" name="child" value="">    
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
</html:form>

<!-- E N D : Content/Form Area -->