
<%
/** @ (#) addPharmacy.jsp 
 * Project     : Vidal Healthcare Services
 * File        : addPharmacy.jsp
 * Author      : kishor kumar 
 * Company     : RCS Technologies
 * Date Created: 05/09/2015
 *
 * @author 		 : kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
<%@ page import="java.util.ArrayList,com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon" %>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@ page import="java.util.Arrays,org.apache.struts.action.DynaActionForm"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/hospital/addPharmacy.js"></script>

<head>
	<link rel="stylesheet" type="text/css" href="ttk/styles/style.css" />
	<link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
	<link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
	<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
	<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>
<SCRIPT>
  $(document).ready(function() {
    $("#professionalId").autocomplete("auto.jsp?mode=profession");
	});  
  
  $(document).ready(function() {
	  $("#pharmacyDescSearch").autocomplete("/AsynchronousAction.do?mode=getCommonMethod&getType=PharmacyDaetails");
});
  
</SCRIPT>

</head>
<%
UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
ArrayList alPharmacies=(ArrayList)request.getSession().getAttribute("alPharmacies");
%>
<!-- <body onload="checkCursor()"> -->
<html:form action="/OnlinePharmacyAction.do">
<body id="pageBody">
<div class="contentArea" id="contentArea">

			<!-- S T A R T : Content/Form Area -->
			<div
				style="background-image: url('/ttk/images/Insurance/content.png'); background-repeat: repeat-x;">
				<div class="container" style="background: #fff;">

					<div class="divPanel page-content">
						<!--Edit Main Content Area here-->
						<div class="row-fluid">

							<!-- S T A R T : Page Title -->
							<div class="span8">
								<h4 class="sub_heading">Pharmacy </h4>
							</div>

						</div>
						<div class="row-fluid">
							<div style="width: 100%;">
								<div class="span12" style="margin: 0% 0%">
	<!-- S T A R T : Page Title -->
	<html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
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
		
		
		<fieldset>
		<legend> Add Pharmacy</legend>
		
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      			
      			<tr>
        			<td width="20%" class="formLabel">Pharmacy Search: </td>
		        	<td width="30%" align="left" colspan="3"> <html:text property="pharmacyDescSearch" styleId="pharmacyDescSearch" styleClass="textBox textBoxLargest" /><!-- onblur="getPharamaDetails(this)" --> </td>
        		</tr>
        		<tr>
        			<td width="20%" class="formLabel">Code: </td>
		        	<td width="30%" align="left"> <html:text property="pharmacyCode" styleClass="textBox textBoxMedium"/> </td>
        			<td width="20%" class="formLabel">Description: </td>
		        	<td width="30%" align="left"> <html:text property="pharmaDesc" styleClass="textBox textBoxMedium"/> </td>
        		</tr>
        		
        		<tr>
        			<td width="20%" class="formLabel">Strength: </td>
		        	<td width="30%" align="left"> <html:text property="strength" styleClass="textBox textBoxMedium"/> </td>
        			<td width="20%" class="formLabel">Quantity: </td>
		        	<td width="30%" align="left"> <html:text property="quanity" styleClass="textBox textBoxTiny"/> </td>
        		</tr>
        		
        		<tr>
        			<td width="20%" class="formLabel">Dosage Form: </td>
		        	<td width="30%" align="left"> <html:text property="dosageForm" styleClass="textBox textBoxMedium"/> </td>
        			<td width="20%" class="formLabel">Dosage: </td>
		        	<td width="30%" align="left"> <html:text property="dosage" styleClass="textBox textBoxMedium"/> </td>
        		</tr>
        		
        		<tr>
        			<td width="20%" class="formLabel">Frequency in a day: </td>
		        	<td width="30%" align="left"> <html:text property="freqInADay" styleClass="textBox textBoxTiny" onblur="calcQty()"/> </td>
        			<td width="20%" class="formLabel">No of Days: </td>
		        	<td width="30%" align="left"> <html:text property="noOfDays" styleClass="textBox textBoxTiny" onblur="calcQty()"/> </td>
        		</tr>
        		
        		<tr>
        			<td width="20%" class="formLabel">Unit Price: </td>
		        	<td width="30%" align="left"> <html:text property="unitPrice" styleClass="textBox textBoxTiny"/> </td>
        			<td width="20%" class="formLabel">Route of *****: </td>
		        	<td width="30%" align="left"> <html:text property="routeOfMediation" styleClass="textBox textBoxMedium"/> </td>
        		</tr>
        		
        </table>
		    </fieldset>
		    <br>
		    
		    		<!-- S T A R T : Buttons -->
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
	        <td width="100%" align="center">
        	  <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSavePharmacy();"><u>S</u>ave</button>&nbsp;&nbsp;&nbsp;&nbsp;
        	  
	        </td>
      	</tr>
    </table>
    <!--  E N D -->
    
		 	<br>
		<table class="formContainer" align="center" border="1" cellspacing="0" cellpadding="0">
		      <tr> <th colspan="12" align="center"> <strong>Pharmacy</strong> 
		      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		      		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		      		OverAll Discount<input type="text" name="overAllDisc" id="overAllDisc" class="textBoxTiny" />%
		      		<input type="button" name="Apply" value="Apply" onclick="ApplyDisc()" size="3" maxlength="3"> </th>
		      </tr>
	      	<tr bgcolor="#04B4AE">
		        <td width="5%"><font color="black"><strong>Sl.No.</strong></font></td>
		        <td width="10%"><font color="black"><strong>Select</strong></font></td>
		        <td width="10%"><font color="black"><strong>Activity Code</strong></font></td>
		        <td width="20%"><font color="black"><strong>Service Name</strong></font></td>
		        <td width="7%"><font color="black"><strong>Unit Price</strong></font></td>
		        <td width="7%"><font color="black"><strong>Quantity</strong></font></td>
		        <td width="7%"><font color="black"><strong>Gross</strong></font></td>
		        <td width="7%"><font color="black"><strong>Disc. Amount</strong></font></td>
		        <td width="7%"><font color="black"><strong>Disc. Gross</strong></font></td>
		        <td width="10%"><font color="black"><strong>Patient Share</strong></font></td>
		        <td width="10%"><font color="black"><strong>Net Amount</strong></font></td>
	      	</tr>
	      </table>
	
		


	<input type="hidden" name="mode" value="">
	</div></div></div></div></div></div></div></body>
</html:form>
</body>