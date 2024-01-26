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
<script type="text/javascript" src="/ttk/scripts/insurancepricing/swpricinghome.js"></script>

<script>
bAction=false;
var TC_Disabled = true;
</script>


<!-- S T A R T : Content/Form Area -->
<html:form action="/SwInsPricingAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		  <tr>
	   <td width="57%">Home-Pricing Tool</td>
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<logic:match name="frmSwPricingHome" property="Message" value="N">

	<!-- <table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0"> -->
		

		 <p>This tool runs a set of rules to generate premium quotations for group health business written in the Dubai region. </p>
        	
		      	
<!-- 	</table> -->
	
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="22%">Pricing Reference No:<br>
	     <html:text name="frmSwPricingHome" property="pricingRefno" styleClass="textBox textBoxLarge" maxlength="250"/>
	     </td>
	     
	     <td width="22%">Policy Number:<br>
	     <html:text name="frmSwPricingHome" property="previousPolicyNo" styleClass="textBox textBoxLarge" maxlength="250"/>
	     </td>
	  
	    <td width="22%">Client Name:<br>
	     <html:text name="frmSwPricingHome" property="clientCode" styleClass="textBox textBoxLarge" maxlength="250"/>
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
     		
     		<td width="100%" align="right">
     		<%
  		 	if(TTKCommon.isDataFound(request,"tableData"))
	   		{
	 		%>
	    		<button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard();"><u>C</u>opy to Web Board</button>&nbsp;
	  		 <%
			}//end of if(TTKCommon.isDataFound(request,"tableData"))
     		
     		%>
     		</td>
     		</tr>
     		<tr>
     		<td width="100%" align="center">
			<%	     		
            	if(TTKCommon.isAuthorized(request,"Add"))
		    	{
	    	%>
	            <p style="color:blue">Click here to start a fresh pricing&nbsp;<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="addPricing()"><u>S</u>tart</button></p>&nbsp;
	            
	            <%
	        	}
            %>
     		</td>
     		
     		
     	</tr>
   
	</table>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		
<tr><td colspan="6">
		 Note : To review/edit existing pricing, please enter pricing reference number in the search tab.
     </td></tr>   	
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
