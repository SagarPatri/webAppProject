<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@page import="com.ttk.dto.preauth.PreAuthDetailVO"%>
<%@page import="com.ttk.dto.preauth.ActivityDetailsVO"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="java.util.ArrayList"%>
<%@page import="org.dom4j.Document"%>
<%@page import="org.dom4j.Node"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<html:form action="/ActivityDetailsAction.do" >
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activity Details</title>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
  <script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
var JS_Focus_Disabled =true;
function closeBenefitDetails() {   
	//document.getElementById("mode").value = "doGeneral";
	document.forms[1].mode.value="doGeneral";
	document.forms[1].reforward.value = "close";
    document.forms[1].action ="/ActivityDetailsAction.do";
    document.forms[1].submit();
}

function displayBenefitsBasedOnType(){
	var benefitType1 = document.getElementById("benefitType").value;
	//alert("Benefit type is : "+ benefitType1);
	document.forms[1].mode.value="viewBenefitDetails";
	document.forms[1].action="/ActivityDetailsAction.do?benefitTypes="+benefitType1;	 
	document.forms[1].submit();
}


</script>

<%
pageContext.setAttribute("benefitTypes",
		Cache.getCacheObject("benefitTypes"));
%>
<body>

<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				    <td width="57%">Benefits Details</td>
					<td  align="right" class="webBoard"></td>
			  </tr>
		</table>
		<form>
<div class="contentArea" id="contentArea">
<html:errors/>

	
	
		 	

<fieldset>
		<legend> Details of Benefits Utilized</legend>
		<fieldset>
		     <legend>Member/Policy Details</legend>
		     
				 <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		        <td class="formLabel">Member ID:</td> 
					  <td class="textLabel">
					<html:text  name="frmPreAuthGeneral" styleClass="textBox textBoxLarge textBoxDisabled" property ="memberId" />
				 
					  </td>
					  <td class="formLabel">Member InsceptionDate:</td>
					  <td class="textLabel">
					  	   	  <html:text  name="frmPreAuthGeneral" styleClass="textBox textBoxLarge textBoxDisabled" property="preMemInceptionDt"/>
						  </td>
					  </tr>
					  <tr>
					  <td class="formLabel">Policy No:</td>
					  <td class="textLabel">
				 <html:text styleClass="textBox textBoxLarge textBoxDisabled" name="frmPreAuthGeneral" property="policyNumber"/>
					 	  </td>
					  <td class="formLabel">Policy End Date:</td>
					  <td class="textLabel">
					  	 <html:text styleClass="textBox textBoxLarge textBoxDisabled" name="frmPreAuthGeneral" property="policyEndDate"/>
						  </td>
					  </tr>
					  <tr>
					  <td class="formLabel">Policy start Date: </td>
					  <td class="textLabel">
			          <html:text styleClass="textBox textBoxLarge textBoxDisabled" name="frmPreAuthGeneral" property="policyStartDate"/>
  						</td>
					  <td class="formLabel"> </td>
					  <td class="textLabel">
					  	  
					  </td>
		 </tr>
	   </table>	
					  </fieldset>
		  <fieldset>
		   <legend>Benefit Details</legend>
		  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		        <td class="formLabel">Sum Insured:</td>
					  <td class="textLabel">
					  <html:text styleClass="textBox textBoxLarge textBoxDisabled" name="frmPreAuthGeneral" property="sumInsured"/>
  
					  </td>
					  <td class="formLabel">Available Sum Insured:</td>
					  <td class="textLabel">
					  <html:text styleClass="textBox textBoxLarge textBoxDisabled" name="frmPreAuthGeneral" property="availableSumInsured"/>
					 </td>
		 </tr>
		  <tr>
		 	<td class="formLabel">Benefit Type:</td>
		 	<td class="textLabel">
		 	<html:select property="benefitType" onchange="displayBenefitsBasedOnType()"    styleId="benefitType" styleClass="selectBox selectBoxMedium" >
				<html:option value="ALL">ALL</html:option>
				<html:optionsCollection name="benefitTypes" label="cacheDesc" value="cacheId" />
		 	</html:select>
		 	
			</td>	
			 <td class="formLabel">Utilized Sum Insured:</td>
			<td class="textLabel">
				<html:text styleClass="textBox textBoxLarge textBoxDisabled" name="frmPreAuthGeneral" property="utilizeSuminsured" value="${utilizeSuminsured}"/>
			</td>
		 </tr>
		 
		 
	   </table>	
			</fieldset>
	<fieldset>
	<ttk:HtmlGrid name="tableData"/>
	 <table border="0" align="center" cellpadding="0" class="gridWithCheckBox" cellspacing="0" >
	<%-- <tr>
<td class=gridOddRow width="10%" align=left>&nbsp;Other Remarks</td>
<td class=generalcontent colSpan=16 align=left>&nbsp;&nbsp;${otherRemarks}</td>
	</tr> --%>
	</table>
		</fieldset>  
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">	
		 <tr>	
		 <td align="center">
		  <button type="button" onclick="closeBenefitDetails();" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"><u>C</u>lose</button>&nbsp;			  
		  </td>		  
		 </tr>
		</fieldset>
</div>	 		
<html:hidden styleId="mode" property="mode" name="frmPreAuthGeneral"/>
<html:hidden property="reforward" name="frmPreAuthGeneral" value=""/>
</form>  
</body>


</html:form>