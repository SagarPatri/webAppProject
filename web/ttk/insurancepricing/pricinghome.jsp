<%
/** @ (#) pricinghome.jsp DEC 29, 2016
 * Project     : Vidal Health TPA
 * File        : pricinghome.jsp
 * Author      : 
 * Company     : Vidal Health TPA
 * Date Created: DEC 29, 2016
 *
 * @author 		 : 
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
<script type="text/javascript" src="/ttk/scripts/insurancepricing/pricinghome.js"></script>

<script>
bAction=false;
var TC_Disabled = true;
</script>


<!-- S T A R T : Content/Form Area -->
<html:form action="/PreAuthAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
    		<td width="57%">Home-Insurance Pricing</td>
    		
  		</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<logic:match name="frmPricingHome" property="Message" value="N">

	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
		

		 <p>This tool runs a set of rules to generate premium quotations for group health insurance schemes in the UAE market. </p>
        	
		      	
	</table>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="22%">Group Name:<br>
	     <html:text name="frmPricingHome" property="groupName" styleClass="textBox textBoxLarge" maxlength="250"/>
	     </td>
	<td>
	    <a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a></td>
	
	  </tr>
	</table>
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableData"/>
<!-- E N D : Grid -->

	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
     	<tr>
     		
     		<td width="100%" align="center">
     		<%
	     		
            	if(TTKCommon.isAuthorized(request,"Add"))
		    	{
	    	%>
	            <p style="color:blue">For New Group Click Here <button type="button" name="Button2" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="addPricing()"><u>A</u>dd</button></p>&nbsp;
	            
	            <%
	        	}
            %>
	            
     		</td>
     	</tr>
     <ttk:PageLinks name="tableData"/>
	</table>
	
	</logic:match>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->
