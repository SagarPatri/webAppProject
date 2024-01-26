<%@page import="java.util.ArrayList"%>
<%
/**
 * @ (#) chequeWiseReport.jsp Nov 27 2015 
 * Project      : TTK HealthCare Services Dubai
 * File         : chequeWiseReport.jsp
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : Nov 27 2015
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.common.security.Cache"%>
<head>
<meta charset="utf-8">
    <link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
    
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/onlineforms/providerLogin/chequewisereport.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
</head>
<html:form action="/OnlineFinanceAction.do" >
<%
ArrayList<String> strChequeDetails	=	(ArrayList<String>)request.getSession().getAttribute("strChequeDetails");
%>
<body id="pageBody">

<div class="contentArea" id="contentArea">
<!-- S T A R T : Content/Form Area -->
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">
<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<!-- <div id="navigateBar">Home > Corporate > Detailed > Claim Details</div> -->
	<div id="contentOuterSeparator"></div>
	<h4 class="sub_heading">Cheque-Wise Report</h4>
<html:errors/>
	<div id="contentOuterSeparator"></div>
</div>           

       </div>
        <div class="row-fluid" >
        <div style="width: 100%;">
<div class="span12" style="margin:0% 0%"> 

<table style="width: 75%; height: auto;" align="center"
class="searchContainer" border="0" cellspacing="3" cellpadding="3">
											<tr>
    	      
			<td width="25%" nowrap align="left">Cheque Number:<br>
			       <html:text property="chequeNumber" name="frmOnlineFinanceDashBoard" styleClass="textBoxWeblogin textBoxMediumWeblogin"  styleId="search2" maxlength="60"/>
			</td>
    	        
  	        <td width="25%" nowrap colspan="4" align="center" >
		 		<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSearch()"><u>S</u>earch</button>
			</td>
			
     </tr>
   </table>
   
   <br>
   
   <!-- <table style="background: #e3e3e3;border-radius:10px; width:50%; height:auto;" align="center" cellspacing="3" cellpadding="3" > -->
   <table style="border-radius: 10px; width: 75%; height: auto;"
										class="table table-striped" align="center" cellspacing="3"
										cellpadding="3"> 
   
	   <tr> <td align="left">Cheque/EFT Number : </td> 	<td align="left"> <c:out value="${strChequeDetails[0]}"/>  </td> </tr>
	   <tr> <td align="left">Cheque/EFT Amount : </td> 	<td align="left"> <c:out value="${strChequeDetails[2]}"/> </td> </tr>
	   <tr> <td align="left">Cheque/EFT Date : </td>	<td align="left"> <c:out value="${strChequeDetails[1]}"/> </td> </tr>
	   <tr> <td align="left">Payer name : </td>		<td align="left"> <c:out value="${strChequeDetails[3]}"/> </td> </tr>
	   <tr> <td align="left">Treatment Date : </td>	<td align="left"> <c:out value="${strChequeDetails[4]}"/> </td> </tr>
	   <tr> <td align="left">Benefit Type : </td>	<td align="left"> <c:out value="${strChequeDetails[5]}"/> </td> </tr>
	   <tr> <td align="left">Batch Number : </td>	<td align="left"> <c:out value="${strChequeDetails[6]}"/> </td> </tr>
	   <tr> <td align="left">Invoice Number : </td>	<td align="left"> <c:out value="${strChequeDetails[7]}"/> </td> </tr>
	   <tr> <td align="left">Claim Received Date : 	</td>	<td align="left"> <c:out value="${strChequeDetails[8]}"/> </td> </tr>
	   <tr> <td align="left">Patient Name : </td>	<td align="left"> <c:out value="${strChequeDetails[9]}"/> </td> </tr>
	   <tr> <td align="left">Claimed Amount : </td>	<td align="left"> <c:out value="${strChequeDetails[10]}"/> </td> </tr>
	   <tr> <td align="left">Discount : </td>		<td align="left"> <c:out value="${strChequeDetails[11]}"/> </td> </tr>
	   <tr> <td align="left">Deductible : </td>		<td align="left"> <c:out value="${strChequeDetails[12]}"/> </td> </tr>
	   <tr> <td align="left">Co-Pay : </td> 		<td align="left"> <c:out value="${strChequeDetails[13]}"/> </td> </tr>
	   <tr> <td align="left">Net Claimed Amount : 	</td>	<td align="left"> <c:out value="${strChequeDetails[14]}"/> </td> </tr>
	   <tr> <td align="left">Total Amount Approved :</td>	<td align="left"> <c:out value="${strChequeDetails[15]}"/> </td> </tr>
	   <tr> <td align="left">Reason for difference in Amount : </td>	<td align="left"> <c:out value="${strChequeDetails[16]}"/> </td> </tr>
	   <tr> <td align="left">Remarks : </td>		<td align="left"> <c:out value="${strChequeDetails[17]}"/> </td> </tr>
   </table>
   
   
   <!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
		    		<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDownload();"><u>D</u>ownload</button>&nbsp;
<!-- 					<button type="button" name="Button2" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onPrint();"><u>P</u>rint</button>&nbsp; -->
 				</td>
  			</tr>  		
		</table>
		<!-- E N D : Buttons -->
		
</div>

</div>
</div>
</div>
 <br><br><br>
</div>
</div>
</div>

<!--E N D : Content/Form Area -->
<INPUT TYPE="hidden" NAME="mode" VALUE="">
</body>
</html:form>

