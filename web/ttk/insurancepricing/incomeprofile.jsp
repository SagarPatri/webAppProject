<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

	<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
    <script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="/ttk/scripts/insurancepricing/pricinghome.js"></script>	
     <script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script> 
     <SCRIPT LANGUAGE="JavaScript">
	bAction = false; //to avoid change in web board in product list screen //to clarify
	var TC_Disabled = true;
</SCRIPT>
<%

	int iRowCount = 0;
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/InsPricingActionIncome.do" >

	<!-- S T A R T : Page Title -->
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
	    		<td><bean:write name="frmIncomeProfile" property="caption"/></td>
			    <td width="43%" align="right" class="webBoard">&nbsp;</td>
		  </tr>
	</table>
	
	<logic:notEmpty name="updated" scope="request">
  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
         <bean:message name="updated" scope="request"/>
     </td>
   </tr>
  </table>
 </logic:notEmpty>
 
<div class="contentArea" id="contentArea">
	<!-- E N D : Page Title -->
	<!-- S T A R T : Search Box -->
	<html:errors/>
	
	<logic:match name="frmIncomeProfile" property="Message" value="N">
	
	<table align="center" class="gridWithPricing" border="0" cellspacing="1" cellpadding="0">
	<tr>
		<td width="45%" ID="listsubheader" CLASS="gridHeader">Description&nbsp;</td>
		<td width="40%" ID="listsubheader" CLASS="gridHeader">Code&nbsp;</td>
		<td width="15%" ID="listsubheader" CLASS="gridHeader">% Value&nbsp;</td>
		</tr>
	<logic:notEmpty name="frmIncomeProfile" property="profileIncomeList">
		<logic:iterate id="item" name="frmIncomeProfile" property="profileIncomeList">
		   <%if(iRowCount%2==0) { %>
				<tr class="gridOddRow">
			<%
			  } else { %>
  				<tr class="gridEvenRow">
  			<%
			  } %>
			  	<td width="45%" class="textLabelBold"><bean:write name="item" property="profileGroupValue1" /></td>
			  	<td width="40%" class="formLabel"><bean:write name="item" property="profileValue1" /></td>
			  	<td width="15%" class="textLabelBold"> 
			  	<input type="text"  name="profilePercentage"   value="<bean:write name="item" property="profilePercentage1"/>" class="textBox textBoxVerySmall" maxlength="3" onkeyup="isNumeric(this);"></td>
				<%-- <td width="5%" align="center">
				
				<input type="checkbox" id="checkid<%=iRowCount%>" onclick="javascript:oncheck('');" name="chkopt" value="<bean:write name="item" property="procedureID" />">
								
				</td> --%>
				
			</tr>
			<INPUT TYPE="hidden" NAME="profileID" value="<bean:write property='profileID1' name='item'/>"> 
			<INPUT TYPE="hidden" NAME="profileGroup" value="<bean:write property='profileGroup1' name='item'/>"> 
			<INPUT TYPE="hidden" NAME="profileGroupValue" value="<bean:write property='profileGroupValue1' name='item'/>"> 
			<INPUT TYPE="hidden" NAME="profileValue" value="<bean:write property='profileValue1' name='item'/>"> 
			<INPUT TYPE="hidden" NAME="transProfileSeqID" value="<bean:write property='transProfileSeqID1' name='item'/>"> 
			<%-- <INPUT TYPE="hidden" NAME="profilePercentage" value="<bean:write property='profilePercentage1' name='item'/>">  --%>

			<%iRowCount++;%>
		</logic:iterate>
	</logic:notEmpty>
	<logic:empty name="frmIncomeProfile" property="profileIncomeList">
		<tr><td>No Data found</td></tr>
	</logic:empty>
	  
	</table>
	

	<!-- E N D : Grid -->
	<!-- S T A R T : Buttons and Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		 <tr>
		    
		     <td colspan="3" align="center">
		
		        		
      	
      			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveIncome();"><u>S</u>ave</button>&nbsp;
      			<button type="button" name="Button" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onViewPlanDesign();"><u>P</u>lan Design >></button>&nbsp;
				<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseIncome();"><u>B</u>ack</button>&nbsp;

		    </td>
	  	</tr>

	</table>
	</logic:match>
</div>
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="DayCareProduct" VALUE="">
	<!-- E N D : Buttons and Page Counter -->
</html:form>
<!-- E N D : Content/Form Area -->