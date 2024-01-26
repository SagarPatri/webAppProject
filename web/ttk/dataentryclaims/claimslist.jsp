<%
/** @ (#) claimslist.jsp
 * Project     : TTK Healthcare Services
 * File        : claimslist.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created:
 *
 * @author 		 : Chandrasekaran J
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

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.dto.administration.WorkflowVO"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/dataentryclaims/claimslist.js"></SCRIPT>
<script>
bAction=false;
var TC_Disabled = true;
</script>

<%

	pageContext.setAttribute("sMode", Cache.getCacheObject("claimMode"));
	pageContext.setAttribute("sStatus", Cache.getCacheObject("claimStatus"));
	pageContext.setAttribute("sTtkBranch", Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("sAssignedTo", Cache.getCacheObject("assignedTo"));
	pageContext.setAttribute("sClaimType", Cache.getCacheObject("claimType"));
	HashMap hmWorkflow= ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getWorkFlowMap();
	ArrayList alWorkFlow=null;
	if(hmWorkflow!=null && hmWorkflow.containsKey(new Long(4)))	//to get the workflow of claims
	{
	    alWorkFlow=((WorkflowVO)hmWorkflow.get(new Long(4))).getEventVO();
	}
    pageContext.setAttribute("listWorkFlow",alWorkFlow);
    String strRoleName  = String.valueOf(((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getRoleName());
    %>
<!-- S T A R T : Content/Form Area -->
<html:form action="/DataEntryClaimsAction.do">
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
			<td nowrap>Inward No.:<br>
				<html:text property="sInwardNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
			</td>
			<td nowrap> Claim No.:<br>
            	<html:text property="sClaimNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
            </td>
        	<td nowrap> Claim Type:<br>
            	<html:select property="sClaimType" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sClaimType" label="cacheDesc" value="cacheId" />
            	</html:select>
        	</td>
        	<td valign="bottom" nowrap>Claim Settlement No.:<br>
            	<html:text property="sClaimSettelmentNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	<td nowrap>Claim File No.:<br>
            	<html:text property="sClaimFileNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
		</tr>
		<tr class="searchContainerWithTab">
        	<td nowrap>Member Name :<br>
            	<html:text property="sClaimantName"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	<td nowrap>Enrollment ID:<br>
            	<html:text property="sEnrollmentId"  styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);"/>
        	</td>
        	<td nowrap>Scheme No.:<br>
            	<html:text property="sPolicyNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	<td nowrap>Corporate Name:<br>
            	<html:text property="sCorporateName"  styleClass="textBox textBoxMedium" maxlength="120"/>
        	</td>
        	<td nowrap>Employee No.:<br>
            	<html:text property="sEmployeeNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
            </td>
      	</tr>
      	<tr class="searchContainerWithTab">
        	<td nowrap>Employee Name:<br>
            	<html:text property="sEmployeeName"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	<td nowrap>Scheme Holder Name:<br>
            	<html:text property="sPolicyHolderName"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	<td nowrap>Assigned To:<br>
            	<html:select property="sAssignedTo" styleClass="selectBox selectBoxMedium">
		        	<html:optionsCollection name="sAssignedTo" label="cacheDesc" value="cacheId" />
            	</html:select>
       	 	</td>
        	<td nowrap>If Others:<br>
            	<html:text property="sSpecifyName"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	<td nowrap="nowrap">Status:<br>
            	 <html:select property="sStatus" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sStatus" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
      </tr>
      <tr class="searchContainerWithTab">
        	<td nowrap>Mode:<br>
            	 <html:select property="sMode" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sMode" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
        	<td nowrap>Vidal Healthcare Branch:<br>
            	<html:select property="sTtkBranch" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sTtkBranch" label="cacheDesc" value="cacheId" />
            	</html:select>
        	</td>
        	<td nowrap="nowrap">Workflow:<br>
            	<html:select property="sWorkFlow" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<logic:notEmpty name="listWorkFlow">
		        		<html:optionsCollection name="listWorkFlow" label="eventName" value="eventSeqID" />
		        	</logic:notEmpty>
            	</html:select>
        	</td>
        	<td nowrap="nowrap">Scheme Name:<br>
            	<html:text property="sSchemeName"  styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);"/>
        	</td>
        	<td width="100%" nowrap>&nbsp;</td>        	
      	</tr>      	
      	<tr class="searchContainerWithTab">
		     <%--added as per bajaj  --%>
            <td nowrap>Insurer Status.:<br>
            	<html:select  property="sInsurerAppStatus" styleClass="selectBox selectBoxMedium">
            	                  <html:option value="">Any</html:option>
				                 <html:option value="INP">In-Progress</html:option>
				                  <html:option value="APR">Approved</html:option>
					              <html:option value="REJ">Rejected</html:option>
					              <html:option value="REQ">Required Information</html:option>
				  </html:select>     
				  </td>
        	<td nowrap="nowrap">Certificate No.:<br>
            	<html:text property="sCertificateNumber"  styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);"/>
        	</td>
        	<td valign="bottom" nowrap>
	        	<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
        	</td>
        	<td width="100%" nowrap>&nbsp;</td>
        	<td width="100%" nowrap>&nbsp;</td>
        	<td width="100%" nowrap>&nbsp;</td>
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
	<input type="hidden" name="Role" value="<%=strRoleName%>"></input>
</html:form>
	<!-- E N D : Content/Form Area -->