<% 
/**
 * @ (#) otherpolicylist.jsp 01st Feb 2006
 * Project      : TTK HealthCare Services
 * File         : otherpolicylist.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : 01st Feb 2006  
 *
 * @author       :Krupa J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import=" com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/childwindow.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/enrollment/otherpolicylist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>

<!-- S T A R T : Content/Form Area -->	
	<html:form action="/OtherPolicyAction.do">
	<!-- S T A R T : Page Title -->
	<logic:match name="frmOtherPolicy" property="switchType" value="ENM">
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
	<logic:match name="frmOtherPolicy" property="switchType" value="END">
		<table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
	</logic:match>
  	<tr>
    <td width="100%">Other Policies List - <bean:write name="frmOtherPolicy" property="caption" /></td> 
    </tr>
	</table>
	<!-- E N D : Page Title --> 
	
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
	<html:errors/>
	
    <!-- S T A R T : Form Fields -->
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="10" colspan="2" class="formLabel"></td>
      </tr>
      <tr>
        <td width="13%" class="formLabel">&nbsp;Member Name:</td>
        <td width="86%" class="textLabelBold"><bean:write name="frmOtherPolicy" property="name" /></td>
      </tr>
    </table>
	<br>
	
		<div class="scrollableGrid" style="height:450px">
		<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="otherPolicyData" className="gridWithCheckBox zeroMargin"/> 
		<!-- E N D : Grid -->
		</div>
	
	<!-- S T A R T : Buttons -->
		<table class="buttonsSavetolistGrid" border="0" cellspacing="0" cellpadding="0">
      	<tr>
			<%
	    		if(TTKCommon.isDataFound(request,"otherPolicyData"))
	    		{
	    	%>
	
        	<td width="75%" align="left" nowrap class="formLabel">Select Policies (not more than 2) to compare them with the current Policy.</td>
        	<td width="25%" align="right" nowrap>
		    	<button type="button" name="Button2" accesskey="m" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCompare()">Co<u>m</u>pare</button>&nbsp;
		    	<button type="button" name="Button22" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAssociate()"><u>A</u>ssociate</button>&nbsp;
		    	<button type="button" name="Button22" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUnAssociate()"><u>U</u>n-associate</button>&nbsp;
        	</td>
        	<%
	    		}//end of if(TTKCommon.isDataFound(request,"otherPolicyData"))
	    	%>	
	    	<td width="25%" align="right" nowrap>
	    		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
        		
      	</td>
      	</tr>
      </table>
      
    <!-- E N D : Buttons -->

	<!-- E N D : Form Fields -->
    

<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<INPUT TYPE="hidden" NAME="mode" VALUE="">

</html:form>
	
