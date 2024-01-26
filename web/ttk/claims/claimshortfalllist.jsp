<%
/** @ (#) claimshortfalllist.jsp 7/30/2015
 * Project     : ProjectX
 * File        : claimshortfalllist.jsp
 * Author      : Nagababu K
 * Company     : RCS
 * Date Created: 7/30/2015
 *
 * @author 		 : Nagababu K
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
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript"  SRC="/ttk/scripts/claims/claimshortfalllist.js"></SCRIPT>

<script>
bAction=false;
var TC_Disabled = true;
</script>
<%
pageContext.setAttribute("sAmount", Cache.getCacheObject("amount"));
pageContext.setAttribute("sSource", Cache.getCacheObject("source"));
pageContext.setAttribute("sStatus", Cache.getCacheObject("preauthStatus"));
pageContext.setAttribute("sPreAuthType", Cache.getCacheObject("preauthType"));
pageContext.setAttribute("claimType", Cache.getCacheObject("claimType"));

HashMap hmWorkflow= ((UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile")).getWorkFlowMap();
ArrayList alWorkFlow=null;

if(hmWorkflow!=null && hmWorkflow.containsKey(new Long(3)))	//to get the workflow of Pre-Auth
{
    alWorkFlow=((WorkflowVO)hmWorkflow.get(new Long(3))).getEventVO();
}

pageContext.setAttribute("listWorkFlow",alWorkFlow);

%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ClaimShortfallsAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">List of Shortfalls</td>
			<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	<tr class="searchContainerWithTab">
	<td nowrap>Shortfall No.:<br>
		   <html:text property="sShortfallNO"  styleClass="textBox textBoxLarge" maxlength="60"/>
        </td>
         <td nowrap>Batch No.:<br>
            	<html:text property="sBatchNO"  styleClass="textBox textBoxLarge" maxlength="60"/>
     </td>
	 <td nowrap>Invoice No.:<br>
		    	<html:text property="sInvoiceNO"  styleClass="textBox textBoxLarge" maxlength="60"/>
	 </td>
	</tr>
		<tr class="searchContainerWithTab">
		 <td nowrap>Claim No.:<br>
		    	<html:text property="sClaimNO"  styleClass="textBox textBoxLarge" maxlength="60"/>
        </td>
		 <td nowrap>Settlement No.:<br>
            	<html:text property="sSettlementNO"  styleClass="textBox textBoxLarge" maxlength="60"/>
        	</td>
		 <td nowrap>Policy No.:<br>
            	<html:text property="sPolicyNumber"  styleClass="textBox textBoxLarge" maxlength="60"/>
        	</td>
    	</tr>
    	<tr class="searchContainerWithTab">
    	<td nowrap> <!-- Enrollment Id/ DHPO --> Member Id:<br>
		    	<html:text property="sEnrollmentId"  styleClass="textBox textBoxLarge" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement)"/>
		    </td>
    	 <td nowrap>Claim Type:<br>
	            <html:select property="sClaimType" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="claimType" label="cacheDesc" value="cacheId" />
            	</html:select>
          	</td>
        	<td nowrap>Status:<br>
	            <html:select property="sStatus" styleClass="selectBox selectBoxMedium">
		  	 		<html:option value="">Any</html:option>
		        	<html:optionsCollection name="sStatus" label="cacheDesc" value="cacheId" />
            	</html:select>
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
	<td></td>
	<td></td>	
	<td><!--button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="addClaim()"><u>A</u>dd</button-->&nbsp;</td>
	</tr>
    	<tr>
    		<td width="27%"></td>
        	<td width="73%" align="right">
        	<%
        	if(TTKCommon.isDataFound(request,"tableData"))
	    	{
	    	%>
        	<!-- 	<button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard()"><u>C</u>opy to Web Board</button>&nbsp; -->
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

