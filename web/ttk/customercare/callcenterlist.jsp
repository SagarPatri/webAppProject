<%
/**
 * @ (#) callcenterlist.jsp 28th July 2006
 * Project      : TTK HealthCare Services
 * File         : callcenterlist.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 28th July 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/childwindow.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/customercare/callcenterlist.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script>var TC_Disabled = true;
//declare the Ids of the form fields seperated by comma which should not be focused when document is loaded
var JS_donotFocusIDs=["callertype"];
</script>

<%
	pageContext.setAttribute("CallerTypeID",Cache.getCacheObject("callerType"));
	pageContext.setAttribute("CategoryID",Cache.getCacheObject("callCategory"));
	pageContext.setAttribute("TTKBranchID",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("StatusID",Cache.getCacheObject("callStatus"));
	pageContext.setAttribute("sinceWhenCall",Cache.getCacheObject("sinceWhenCall"));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/CallCenterSearchAction.do">

<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	    	<td width="57%">List of Calls</td>
		    <td width="43%" align="right" class="webBoard"><a href="#" onClick="javascript:onViewDetail();"><img src="/ttk/images/ViewDetails.gif" alt="View Detail" width="16" height="16" border="0" align="absmiddle" class="icons"></a></td>
		</tr>
	</table>
<!-- E N D : Page Title -->

<div class="contentArea" id="contentArea">
<html:errors/>

<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
      	<tr class="searchContainerWithTab">
        	<td nowrap>Caller Type:<br>
        		<html:select property="callertype"  styleId="callertype" styleClass="selectBox selectBoxMedium" onchange="changeCallerFields();">
	  				<html:options collection="CallerTypeID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
            </td>

        	<td width="40%" valign="bottom" nowrap>

				<logic:match name="frmCallCenterList" property="callertype" value="CTP">
			    	Enrollment Id: <span class="subHeader"><bean:write name="frmCallCenterList" property="id"/></span>
		    	</logic:match>

				<logic:match name="frmCallCenterList" property="callertype" value="CTC">
			    	Corporate Name: <span class="subHeader"><bean:write name="frmCallCenterList" property="id"/></span>
				</logic:match>

		    	<logic:match name="frmCallCenterList" property="callertype" value="CNC">
			    	Empanelment Number: <span class="subHeader"><bean:write name="frmCallCenterList" property="id"/></span>
				</logic:match>

		    	<logic:match name="frmCallCenterList" property="callertype" value="CIC">
			    	Insurance Company: <span class="subHeader"><bean:write name="frmCallCenterList" property="id"/></span>
				</logic:match>

		    	<logic:match name="frmCallCenterList" property="callertype" value="CTB">
			    	Caller Name:<br><html:text property="Name" styleClass="textBox textBoxMedium" maxlength="60"/>
				</logic:match>

		    	<logic:match name="frmCallCenterList" property="callertype" value="CTA">
			    	Caller Name:<br><html:text property="Name" styleClass="textBox textBoxMedium" maxlength="10"/>
				</logic:match>
		    </td>
        	<td width="60%" valign="bottom" nowrap>

				<logic:match name="frmCallCenterList" property="callertype" value="CTP">
			    	Name:  <span class="subHeader"><bean:write name="frmCallCenterList" property="name"/></span>&nbsp;&nbsp;
			    	<a href="#" onClick="SelectEnrollment('searchTab')"><img src="/ttk/images/EditIcon.gif" alt="Select Enrollment Id" border="0" align="absmiddle"></a>&nbsp;&nbsp;
			    	<a href="#" onClick="ClearEnrollment()"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Enrollment Id" width="16" height="16" border="0" align="absmiddle"></a>
				</logic:match>

		    	<logic:match name="frmCallCenterList" property="callertype" value="CTC">
			    	Group Id:  <span class="subHeader"><bean:write name="frmCallCenterList" property="name"/></span>&nbsp;&nbsp;
			    	<a href="#" onClick="SelectCorporate()"><img src="/ttk/images/EditIcon.gif" alt="Select Corporate Company" border="0" align="absmiddle"></a>&nbsp;&nbsp;
	          		<a href="#" onClick="ClearCorporate()"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Corporate Company" width="16" height="16" border="0" align="absmiddle"></a>
				</logic:match>

		    	<logic:match name="frmCallCenterList" property="callertype" value="CNC">
			    	Hospital Name:  <span class="subHeader"><bean:write name="frmCallCenterList" property="name"/></span>&nbsp;&nbsp;
			    	<a href="#" onClick="SelectHospital()"><img src="/ttk/images/EditIcon.gif" alt="Select Empanelment Number" border="0" align="absmiddle"></a>&nbsp;&nbsp;
	          		<a href="#" onClick="ClearHospital()"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Empanelment Number" width="16" height="16" border="0" align="absmiddle"></a>
				</logic:match>

		    	<logic:match name="frmCallCenterList" property="callertype" value="CIC">
			    	Company Code:  <span class="subHeader"><bean:write name="frmCallCenterList" property="name"/></span>&nbsp;&nbsp;
			    	<a href="#" onClick="SelectInsurance()"><img src="/ttk/images/EditIcon.gif" alt="Select Company" border="0" align="absmiddle"></a>&nbsp;&nbsp;
	          		<a href="#" onClick="ClearInsurance()"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Company" width="16" height="16" border="0" align="absmiddle"></a>
				</logic:match>
        	</td>
    	</tr>
    </table>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
    	<tr class="searchContainerWithTab">
	  		<td nowrap>Log Number:<br>
	  			<html:text property="logNbr" styleClass="textBox textBoxMedium" maxlength="60"/>
	  		</td>
            <td nowrap>Policy Number: <br>
          		<html:text property="policyno" styleClass="textBox textBoxMedium" maxlength="60"/>
          	</td>
          	<td nowrap>Category:<br>
        		<html:select property="category"  styleClass="selectBox selectBoxMedium">
					<html:option value="">Any</html:option>
	  				<html:options collection="CategoryID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
          	</td>
          	<td nowrap>VIPUL Better Care Health Branch:<br>
        		<html:select property="ttkBranch"  styleClass="selectBox selectBoxMedium">
					<html:option value="">Any</html:option>
	  				<html:options collection="TTKBranchID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
          	</td>
          	<td nowrap>Since When:<br>
        		<html:select property="sinceWhenCall"  styleClass="selectBox selectBoxMedium">
					<html:option value="">Any</html:option>
	  				<html:options collection="sinceWhenCall"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
          	</td>
		</tr>
      	<tr class="searchContainerWithTab">
        	
          	<td valign="bottom" nowrap>Status:<br>
	          	<span class="textLabel">
        		<html:select property="status"  styleClass="selectBox selectBoxMedium">
					<html:option value="">Any</html:option>
	  				<html:options collection="StatusID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select></span>
          	</td>
          	<td nowrap="nowrap">Agent Name:<br>
            	<html:text property="sSchemeName"  styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);"/>
        	</td>
        	
        	
        	<logic:match name="frmCallCenterList" property="callertype" value="CTP">
        		<td nowrap>Claim Intimation / Log No.:<br>
            	<html:text property="sClaimIntLogNo"  styleClass="textBox textBoxMedium" maxlength="120"/>
        		</td>
        	</logic:match>
        	
        	<td valign="bottom" nowrap>Call Back:<br>
	          	<span class="textLabel">
        		<html:select property="callBack"  styleClass="selectBox selectBoxMedium">
					<html:option value="">All</html:option>
					<html:option value="Y">Call Back</html:option>
					<html:option value="N">Without Call Back</html:option>
		    	</html:select></span>
          	</td>
          	
        	 <!--KOC FOR Grievance cigna-->
          	<td valign="bottom" nowrap><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
          	
          	
        	<%-- <td nowrap="nowrap">Certificate No.:<br>
            	<html:text property="sCertificateNumber"  styleClass="textBox textBoxMedium" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);"/>
        	</td>
        	
        	<td nowrap="nowrap">CreditCard No.:<br>
            	<html:text property="sCreditCardNumber"  styleClass="textBox textBoxMedium" maxlength="60"/>
        	</td>
        	 <!--KOC FOR Grievance cigna-->
        	 <logic:match name="frmCallCenterList" property="callertype" value="CTP">
        	 <td nowrap>Scheme Log No.:<br>
            	<html:text property="sPolicyLogNo"  styleClass="textBox textBoxMedium" maxlength="120"/>
        	</td>
        	</logic:match> --%>
        	
        	</tr>
        	<tr>
        	<%-- <logic:match name="frmCallCenterList" property="callertype" value="CTP">
        		<td nowrap>Claim Intimation / Log No.:<br>
            	<html:text property="sClaimIntLogNo"  styleClass="textBox textBoxMedium" maxlength="120"/>
        	</td>
        	</logic:match>
        	 <!--KOC FOR Grievance cigna-->
          	<td valign="bottom" nowrap><a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
		 --%></tr>
	</table>
<!-- E N D : Search Box -->

<!-- S T A R T : Grid -->
<ttk:HtmlGrid name="callcentertableData"/>
<!-- E N D : Grid -->

<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td width="27%"></td>
        	<td width="73%" align="right">
	        	<%
					//if(TTKCommon.isAuthorized(request,"Add"))
					//{
				%>
						<button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAdd()"><u>A</u>dd</button>
	        	<%
		        	//}// end of if(TTKCommon.isAuthorized(request,"Add"))
		        %>
        	</td>
      	</tr>
      	<ttk:PageLinks name="callcentertableData"/>
	</table>
<!-- E N D : Buttons and Page Counter -->
</div>
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<INPUT TYPE="hidden" NAME="enrollmentID" VALUE=''>
<INPUT TYPE="hidden" NAME="policyGrpSeqID" VALUE=''>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
<INPUT TYPE="hidden" NAME="tab" VALUE="">
<html:hidden property="fraudCall"/>
<html:hidden property="id"/>
<input type="hidden" name="child" value="">
</html:form>
<!-- E N D : Content/Form Area -->
