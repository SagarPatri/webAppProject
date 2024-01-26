<%@page import="com.ttk.dto.preauth.ActivityDetailsVO"%>
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

<!DOCTYPE html> 
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Activity Details</title>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
  <script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
<script type="text/javascript">
var JS_Focus_Disabled =true;

function closeBenefitDetails(){
	//alert("fromFlag::"+fromFlag);
	document.forms[1].mode.value="doValidate";
    document.forms[1].action = "/OnlineCashlessPartBackAction.do";   
	JS_SecondSubmit=true;
	document.forms[1].submit();
}

function displayBenefitsBasedOnType(){
	var benefitType = document.getElementById("benefitType").value;
	document.forms[1].mode.value="viewBenefitDetails";
	document.forms[1].action="/OnlineCashlessPartBackAction.do?benefitType4="+benefitType;	 
	document.forms[1].submit();
}

</script>
</head>

<body>

<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
			  <tr>
				    <td width="57%">Benefits Details</td>
					<td  align="right" class="webBoard"></td>
			  </tr>
		</table>
<html:form action="OnlineCashlessPartBackAction.do">
<div class="contentArea" id="contentArea">
<html:errors/>

	
	
		 	

<fieldset>
		<legend> Details of Benefits Utilized</legend>
		<fieldset>
		     <legend>Member/Policy Details</legend>
		     
				 <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		        <td class="formLabel">Alkoot ID:</td> 
					  <td class="textLabel">
					 <input type="text" class="textBox textBoxLarge textBoxDisabled" value="${enrollmentId}">
			         
					  </td>
					  <td class="formLabel">Member Inception Date:</td>
					  <td class="textLabel">
					  	  <input type="text" class="textBox textBoxLarge textBoxDisabled" value="${policyIssueDate}">
					  </td>
					  </tr>
					  <tr>
					  <td class="formLabel">Policy No:</td>
					  <td class="textLabel">
				 <input type="text" class="textBox textBoxLarge textBoxDisabled" value="${policyNumber}">
					  </td>
					   <td class="formLabel">Eligible Networks:</td>
					  <td class="textLabel">
				 <input type="text" class="textBox textBoxLarge textBoxDisabled" value="${productCatTypeID}">
					  </td>
					  </tr>
					  <tr>
					  <td class="formLabel">Policy start Date: </td>
					  <td class="textLabel">
			          <input type="text" class="textBox textBoxLarge textBoxDisabled" value="${effectiveFromDate}">
					  </td>
					  <td class="formLabel">Policy End Date:</td>
					  <td class="textLabel">
					  	 <input type="text" class="textBox textBoxLarge textBoxDisabled" value="${effectiveToDate}">
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
					 <input type="text" class="textBox textBoxLarge textBoxDisabled" value="${sumInsured}">
			         
					  </td>
					  <td class="formLabel">Available Sum Insured:</td>
					  <td class="textLabel">
					  	   <input type="text" class="textBox textBoxLarge textBoxDisabled" value="${availableSumInsured}">
					  </td>
		 </tr>
		 <tr>
		 	<td class="formLabel">Benefit Type:</td>
		 	<td class="textLabel">
		 	<SELECT onchange=displayBenefitsBasedOnType(); id=benefitType class="selectBox selectBoxMedium" >
		 		<OPTION value=""   ${benefitType1 eq '' ? 'selected' : ''} >Select from list</OPTION>
		 		<OPTION value=OPTS ${benefitType1 eq 'OPTS' ? 'selected' : ''}>OUT-PATIENT</OPTION>
		 		<OPTION value=IPT  ${benefitType1 eq 'IPT' ? 'selected' : ''}>IN-PATIENT</OPTION>
		 		<OPTION value=OPTC ${benefitType1 eq 'OPTC' ? 'selected' : ''}>OPTICAL</OPTION>
		 		<OPTION value=DNTL ${benefitType1 eq 'DNTL' ? 'selected' : ''}>DENTAL</OPTION>
		 		<OPTION value=IMTI ${benefitType1 eq 'IMTI' ? 'selected' : ''}>IN-PATIENT MATERNITY</OPTION>
		 		<OPTION value=OMTI ${benefitType1 eq 'OMTI' ? 'selected' : ''}>OUT-PATIENT MATERNITY</OPTION>
		 		<OPTION value=HEAC ${benefitType1 eq 'HEAC' ? 'selected' : ''}>HEALTH CHECK UP</OPTION>
		 	</SELECT>
		 	
			</td>	
		 </tr>
	   </table>	
			</fieldset>
	<fieldset>
	<ttk:HtmlGrid name="tableData"/>
		</fieldset>  
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">	
		 <tr><td class="formLabelBold">Final Remarks:</td></tr>
		<tr>
		<TD class=textLabel colSpan=5>
				<TEXTAREA cols=135 class="textAreaMediumhtDisabled" name=otherRemarks >${otherRemarks}</TEXTAREA></TD>
     	</tr>
		 <tr>	
		 <td align="center">
		  <button type="button" onclick="closeBenefitDetails();" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"><u>C</u>lose</button>&nbsp;			  
		  </td>		  
		 </tr>
		 </table>
		</fieldset>
</div>	 		
<input type="hidden" id="mode" name="mode"/>
<INPUT TYPE="hidden" NAME="reforward" value="">
<input type="hidden" name="memberSeqId" id="memberSeqId" value="${memberSeqId}"/>
<input type="hidden" name="policySeqId" id="policySeqId" value="${policySeqId}"/>
</html:form>
</body>


</html>