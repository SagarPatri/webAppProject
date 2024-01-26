<%
/** @ (#) preauthlist.jsp June 04, 2018
 * Project     : TTK Healthcare Services
 * File        : preauthlist.jsp
 * Author      : Deepthi Meesala
 * Company     : RCS
 * Date Created: June 04, 2018
 *
 * @author 		 : Deepthi Meesala
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

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList,com.ttk.common.PreAuthWebBoardHelper"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.dto.administration.WorkflowVO"%>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/partner/partnerpreauthlist.js"></script>

<script>
bAction=false;
var TC_Disabled = true;
</script>

<%
	pageContext.setAttribute("sAmount", Cache.getCacheObject("amount"));
	pageContext.setAttribute("sSource", Cache.getCacheObject("source"));
	pageContext.setAttribute("sStatus", Cache.getCacheObject("preauthStatus"));
	pageContext.setAttribute("sTtkBranch", Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("sAssignedTo", Cache.getCacheObject("assignedTo"));
	pageContext.setAttribute("sPreAuthType", Cache.getCacheObject("preauthType"));
	pageContext.setAttribute("ProviderList",Cache.getCacheObject("ProviderList"));
	pageContext.setAttribute("insuranceCompany", Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("source", Cache.getCacheObject("source"));

	HashMap hmWorkflow= ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getWorkFlowMap();
	ArrayList alWorkFlow=null;

	if(hmWorkflow!=null && hmWorkflow.containsKey(new Long(3)))	//to get the workflow of Pre-Auth
	{
	    alWorkFlow=((WorkflowVO)hmWorkflow.get(new Long(3))).getEventVO();
	}

    pageContext.setAttribute("listWorkFlow",alWorkFlow);

%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/PartnerPreAuthAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td width="57%">List of Pre-Auth</td>
    		<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
    		
    		
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
		<tr class="searchContainerWithTab">
		    <td nowrap>Partner Reference No.:<br>
		    	<html:text property="sPreAuthNumber" name="frmPreAuthList"  styleClass="textBox textBoxLarge" maxlength="60"/>
		    </td>
		    <td nowrap>Partner Name.:<br>
            	<html:text property="sPartnerName" name="frmPreAuthList"  styleClass="textBox textBoxLarge" maxlength="60"  />
        	</td>
		     <td nowrap>Policy No.:<br>
            	<html:text property="sPolicyNumber" name="frmPreAuthList"  styleClass="textBox textBoxLarge" maxlength="60"/>
        	</td>
        	 <td nowrap>Received Date:<br>
	            <html:text property="sRecievedDate" name="frmPreAuthList"  styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].sRecievedDate',document.forms[1].sRecievedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a>
            </td>
        	</tr>
        	<tr>
		    <td nowrap> Member ID/ DHPO Member Id:<br>
		    	<html:text property="sEnrollmentId" name="frmPreAuthList"  styleClass="textBox textBoxLarge" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement)"/>
		    </td>
    		<td nowrap>Vidal Health Branch:<br>
	            <html:select property="sTtkBranch" name="frmPreAuthList" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sTtkBranch" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
          	<td nowrap>Member Name.:<br>
            	<html:text property="sClaimantName" name="frmPreAuthList"  styleClass="textBox textBoxLarge" maxlength="60"  />
        	</td>
        	<td nowrap>Status:<br>
	            <html:select property="sStatus" name="frmPreAuthList" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sStatus" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td> 
    	</tr>
    	<tr>        	
          <td>
	            <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
            </td>
        	
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
      	<tr>
        	<td height="4" colspan="2"></td>
      	</tr>
      	<tr>
        	<td colspan="2">
        	<!-- <span class="textLabelBold">Legend: </span><img src="/ttk/images/HighPriorityIcon.gif" alt="High Priority" width="16" height="16" align="absmiddle">- High Priority&nbsp;&nbsp;&nbsp;<img src="/ttk/images/MediumPriorityIcon.gif" alt="Medium Priority" width="16" height="16" align="absmiddle">&nbsp;- Medium Priority&nbsp;&nbsp;&nbsp;<img src="/ttk/images/LowPriorityIcon.gif" alt="Low Priority" width="16" height="16" align="absmiddle">&nbsp;- Low Priority -->
        	</td>
        </tr>
	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->
