<%
/** @ (#) claimssearch.jsp
 * Project       : TTK Healthcare Services
 * File          : claimssearch.jsp
 * Author        : Balaji C R B
 * Company       : Span Systems Corporation
 * Date Created  :Aug 29,2007
 *
 * @author 		 : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/coding/codingclaimslist.js"></SCRIPT>
<script>
bAction=false;
var TC_Disabled = true;
</script>

<%

	pageContext.setAttribute("sTtkBranch", Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("sAssignedTo", Cache.getCacheObject("assignedTo"));
	pageContext.setAttribute("sinceWhen", Cache.getCacheObject("sinceWhen"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/CodingClaimsSearchAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">List of Claims</td>
			<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
		<tr class="searchContainerWithTab">
			<td nowrap> Claim No.:<br>
            	<html:text property="sClaimNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
            </td>
        	<td nowrap>Enrollment ID:<br>
            	<html:text property="sEnrollmentId"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	
        	<td nowrap>Vidal Health Branch:<br>
            	<html:select property="sTtkBranch" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sTtkBranch" label="cacheDesc" value="cacheId" />
            	</html:select>
        	</td>
		</tr>
		<tr class="searchContainerWithTab">
			<td nowrap>Assigned To:<br>
            	<html:select property="sAssignedTo" styleClass="selectBox selectBoxMedium">
		        	<html:optionsCollection name="sAssignedTo" label="cacheDesc" value="cacheId" />
            	</html:select>
       	 	</td>
    		<td nowrap>Since When:<br>
	            <html:select property="sinceWhen" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sinceWhen" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
    		<td valign="bottom"><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
    		<td width="100%">&nbsp;</td>
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
        	if(TTKCommon.isDataFound(request,"tableData"))
	    	{
	    	%>
        		<button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard()"><u>C</u>opy to Web Board</button>&nbsp;
        	<%
        	}
        	%>
        	</td>
      	</tr>
      	<ttk:PageLinks name="tableData"/>
	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>
	<!-- E N D : Content/Form Area -->