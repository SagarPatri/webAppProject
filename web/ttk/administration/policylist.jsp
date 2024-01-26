<%
/**
 * @ (#) policylist.jsp Feb 27 2006
 * Project      : TTK HealthCare Services
 * File         : policylist.jsp
 * Author       : Pradeep R
 * Company      : Span Systems Corporation
 * Date Created : Feb 27 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%
	pageContext.setAttribute("listEnrollmentType",Cache.getCacheObject("enrollTypeCode"));
	pageContext.setAttribute("listInsuranceCompany",Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("listTTKBranch",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("listStatus",Cache.getCacheObject("productStatusCode"));
	pageContext.setAttribute("productAuthority", Cache.getCacheObject("productAuthority")); 

%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/policylist.js"></script>
<SCRIPT LANGUAGE="JavaScript">

var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>
<!-- S T A R T : Content/Form Area -->
<html:form action="/AdminPoliciesAction.do" >
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="57%">List of Policies</td>
	   <td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	</table>
<!-- E N D : Page Title -->
<div class="contentArea" id="contentArea">
<!-- S T A R T : Search Box -->
<html:errors/>

<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
	        <td nowrap>Policy No.:<br>
	     	 	<html:text property="sPolicyNO" styleClass="textBox textBoxMedium" maxlength="60"/>
	     	</td>
		  	<td valign="bottom" nowrap>Enrollment Type:<br>
		  		<html:select property="sEnrollmentType" styleClass="selectBox selectBoxMedium"  disabled="true">
				<html:option value="">Any</html:option>
				<html:optionsCollection name="listEnrollmentType" label="cacheDesc" value="cacheId"/>
				</html:select>
				<html:hidden property="sEnrollmentType"/>
			</td>
		 	 <td nowrap>Insurance Company:<br>
				<html:select property="sInsuranceCompany" styleClass="selectBox selectBoxMedium" >
				<html:option value="">Any</html:option>
				<html:optionsCollection name="listInsuranceCompany" label="cacheDesc" value="cacheId"/>
				</html:select>
			</td>
	        <td valign="bottom" nowrap>Branch Location:<br>
	        	<html:select property="sTTKBranch" styleClass="selectBox selectBoxMedium" >
				<html:option value="">Any</html:option>
				<html:optionsCollection name="listTTKBranch" label="cacheDesc" value="cacheId"/>
				</html:select>
	        </td>
	       <!--  AK -->
	        
	         <td nowrap>Product Name:<br>
	     	 	<html:text property="sPoductName" styleClass="textBox textBoxMedium" maxlength="60"/>
	     	</td>
	     	
	     	 <td nowrap>Product Authority:<br>
			<html:select property="productAuthority" styleClass="selectBox" style="width:160px;">
		  	 	  <html:option value="">Select from the List</html:option>
		          <html:optionsCollection name="productAuthority" label="cacheDesc" value="cacheId" />
            </html:select>
            
	    </td>
	       <!--  EAK
	         -->
	        
			<td width="100%" valign="bottom">&nbsp;</td>
      </tr>
      <tr>
        <td nowrap>Status:<br>
            <html:select property="sStatus" styleClass="selectBox" style="width:160px;">
            <html:option value="">Any</html:option>
            <html:optionsCollection name="listStatus" label="cacheDesc" value="cacheId"/>
            </html:select>
         <td nowrap>Group Id:<br>
			<html:text property="sGroupID" styleClass="textBox textBoxMedium" maxlength="60"/>
		 </td>
		 <td nowrap colspan="2">Group Name:<br>
	  		<html:text property="sGroupName" styleClass="textBox textBoxLarge" maxlength="60"/>
	  		&nbsp;&nbsp;<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
		 </td>
        	<td valign="bottom">&nbsp;</td>
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
			   if(TTKCommon.isDataFound(request,"tableData"))
			   {
		    %>
			   	 <button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard();"><u>C</u>opy to Web Board</button>&nbsp;
			<%
				} // end of if(TTKCommon.isDataFound(request,"tableData"))
			%>

	        </td>
	  	</tr>
	  			<ttk:PageLinks name="tableData"/>

	</table>
<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">

</html:form>
<!-- E N D : Main Container Table -->