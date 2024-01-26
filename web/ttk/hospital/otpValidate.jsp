<%
/** @ (#) inscompanyinfo.jsp 25th Feb 2008
 * Project     : INTX Services
 * File        : otpValidate.jsp
 * Author      : kishor kumar 
 * Company     : RCS Technologies
 * Date Created: 25th Oct 2014
 *
 * @author 		 : kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,org.apache.struts.action.DynaActionForm" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/hospital/cashlessAddNew.js"></script>
<head>
    <link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">
    <link href="/ttk/scripts/bootstrap/css/custom.css" rel="stylesheet" type="text/css" />
    
</head>
<html:form action="/OnlineCashlessHospActionNew.do" >
<body id="pageBody">
<%
DynaActionForm frmCashlessAdd=(DynaActionForm)request.getSession().getAttribute("frmCashlessAdd");
String blocked	=	(String)request.getAttribute("blocked");
//String[] tobBenefits	=	(String[])request.getAttribute("tobBenefits");
%>
<div class="contentArea" id="contentArea">
<div style="background-image:url('/ttk/images/Insurance/content.png');background-repeat: repeat-x; ">
<div class="container"  style="background:#fff;">

    <div class="divPanel page-content">
        <!--Edit Main Content Area here-->
        <div class="row-fluid" >

<div class="span8">
<!-- <div id="navigateBar">Home > Corporate > Detailed > Claim Details</div> -->
	<div id="contentOuterSeparator"></div>
	<h4 class="sub_heading">Benefits</h4>
	<div id="contentOuterSeparator"></div>
</div>           

       </div>
        <div class="row-fluid" >
        <div style="width: 100%;">
<div class="span12" style="margin:0% 0%">
	<!-- S T A R T : Page Title -->
	<html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
	<!-- <div class="contentArea" id="contentArea"> -->
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
		<%-- <span class="fieldGroupHeader" style="background: green;"> &nbsp;&nbsp;Eligibility : <bean:write name="frmCashlessAdd" property="eligibility"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span> --%>
		 <span class="fieldGroupTabHeader">  &nbsp;&nbsp;Eligibility : <bean:write name="frmCashlessAdd" property="eligibility"/> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	    
	    <%-- <jsp:include page="index_activex.jsp"></jsp:include> --%>
	    </span>
		<%-- 		<div style="font: bold;background-color: green;" >Eligibility : <bean:write name="frmCashlessAdd" property="eligibility"/></div> --%>		
<!-- New Fields -->
		
		<logic:equal name="eligibility" value="NO" >
		<fieldset>
		<legend>Beneficiary Details</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		   
		   
		   <tr>
			        <td width="15%" class="formLabel"><strong>Patient Name</strong></td>
			        <td width="30%">:&nbsp;<bean:write name="frmCashlessAdd" property="memberName"/> </td>
			        <td width="15%" class="formLabel" ><strong>Card Number</strong></td>
			        <td width="40%">:&nbsp;<bean:write name="frmCashlessAdd" property="enrollId"/> </td>
		      	</tr>
		      	 <tr>
		      	 	<td width="15%" class="formLabel"> </td>
		       		<td width="30%"> </td>
			        <td width="15%" class="formLabel"><strong>DHPO ID</strong></td>
			        <td width="40%">:&nbsp;<bean:write name="frmCashlessAdd" property="dhpoMemberId"/> </td>
		      	</tr>
		      	<tr>
		      	<td width="15%" class="formLabel"><strong>Age/DOB</strong></td>
		        <td width="30%">:&nbsp;<bean:write name="frmCashlessAdd" property="age"/></td>
		        <td width="15%" class="formLabel"><strong>Gender</strong></td>
		        <td width="40%">:&nbsp;<bean:write name="frmCashlessAdd" property="gender"/></td>
		        </tr>
		      	<tr>
		      	<td width="25%" class="formLabel"><strong>Insurance Company Name</strong></td>
		        <td width="25%">:&nbsp;<bean:write name="frmCashlessAdd" property="payer"/></td>
		        
		        <td width="25%" class="formLabel"><strong>Emirate ID</strong></td>
		        <td width="25%">:&nbsp;<bean:write name="frmCashlessAdd" property="emirateID"/></td>
		        
		        </tr>
		        
		        
		        
		        <tr>
		      	<td width="25%" class="formLabel"><strong>Policy No</strong></td>
		        <td width="25%">:&nbsp;<bean:write name="frmCashlessAdd" property="policyNum"/></td>
		        
		        <td width="25%" class="formLabel"><strong>Network Type</strong></td>
		        <td width="25%">:&nbsp;<bean:write name="frmCashlessAdd" property="member_network"/></td>
		        
		        </tr>
		   
		   
		   
		   
		   
		    <%-- 	<tr>
			        <td width="15%" class="formLabel" ><strong>Patient Name </strong> </td>
			        <td width="35%">: <bean:write name="frmCashlessAdd" property="memberName"/>
			        </td><td width="15%" class="formLabel" ><strong>Card Number</strong></td>
			        <td width="35%">: <bean:write name="frmCashlessAdd" property="enrollId"/>
			        </td>
		      	</tr>
		      	<tr>
			        <td width="15%" ><strong>Age/DOB</strong> </td>
		      		<td width="35%">: <bean:write name="frmCashlessAdd" property="age"/></td>
		      	</tr> --%>
		</table>        
		</fieldset>
		<fieldset>
		<legend>Reason for Rejection</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		    	<tr>
			        <td width="25%" class="formLabel" ><strong>Reason</strong></td>
			        <td colspan="3"> <bean:write name="frmCashlessAdd" property="reasonForRejection"/>
		      	</tr>
		</table>        
		</fieldset>
		</logic:equal>
		
		<logic:equal name="eligibility" value="YES">
		<fieldset>
		<legend>Beneficiary Details</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    	<tr>
			        <td width="15%" class="formLabel"><strong>Patient Name</strong></td>
			        <td width="30%">:&nbsp;<bean:write name="frmCashlessAdd" property="memberName"/> </td>
			        <td width="15%" class="formLabel" ><strong>Card Number</strong></td>
			        <td width="40%">:&nbsp;<bean:write name="frmCashlessAdd" property="enrollId"/> </td>
		      	</tr>
		      	 <tr>
		      	 	<td width="15%" class="formLabel"> </td>
		       		<td width="30%"> </td>
			        <td width="15%" class="formLabel"><strong>DHPO ID</strong></td>
			        <td width="40%">:&nbsp;<bean:write name="frmCashlessAdd" property="dhpoMemberId"/> </td>
		      	</tr>
		      	<tr>
		      	<td width="15%" class="formLabel"><strong>Age/DOB</strong></td>
		        <td width="30%">:&nbsp;<bean:write name="frmCashlessAdd" property="age"/></td>
		        <td width="15%" class="formLabel"><strong>Gender</strong></td>
		        <td width="40%">:&nbsp;<bean:write name="frmCashlessAdd" property="gender"/></td>
		        </tr>
		      	<tr>
		      	<td width="25%" class="formLabel"><strong>Insurance Company Name</strong></td>
		        <td width="25%">:&nbsp;<bean:write name="frmCashlessAdd" property="payer"/></td>
		        
		        <td width="25%" class="formLabel"><strong>Emirate ID</strong></td>
		        <td width="25%">:&nbsp;<bean:write name="frmCashlessAdd" property="emirateID"/></td>
		        
		        </tr>
		        
		        
		        
		        <tr>
		      	<td width="25%" class="formLabel"><strong>Policy No</strong></td>
		        <td width="25%">:&nbsp;<bean:write name="frmCashlessAdd" property="policyNum"/></td>
		        
		        <td width="25%" class="formLabel"><strong>Network Type</strong></td>
		        <td width="25%">:&nbsp;<bean:write name="frmCashlessAdd" property="member_network"/></td>
		        
		        </tr>
		        
		        
		        
		        
		        
		        
		        
		</table>        
		</fieldset>
		
		<fieldset>
		<legend>Policy Details</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    	<%-- <tr>
			        <td width="15%" class="formLabel" ><strong>Deductible</strong></td>
			        <td width="30%">:&nbsp;<bean:write name="frmCashlessAdd" property="deductable"/>
			        </td><td width="40%" class="formLabel" ><strong>Co Payment : </strong>
			        
					<table></table>			        
			        </td>
			        <td width="15%" >:&nbsp;<bean:write name="frmCashlessAdd" property="coPay"/>
			        </td>
		      	</tr> --%>
		      <tr>
		      		<td class="formLabel" ><strong>Table of Benefits</strong></td>
		      </tr>
		      		<tr>
		      	<logic:equal value="OPTS" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr><td width="25%">Copay </td><td> : ${tobBenefitsForMemElig[0]} </td></tr><tr>
				      				<td width="25%">Deductible </td><td> :  ${tobBenefitsForMemElig[2]} </td>
				      			</tr>
				      			<tr><td width="25%">Pharmaceutical </td><td> :  ${tobBenefitsForMemElig[12]}  </td></tr><tr>
				      				<td width="25%">IP/OP Services </td><td> : ${tobBenefitsForMemElig[11]}</td>
				      			</tr>
				      			<tr><td width="25%">Remarks </td><td> : ${tobBenefitsForMemElig[1]} </td>
				      			</tr>
			      			</table>
			      		</td>
		      	</logic:equal>
		      	
		      	<logic:equal value="IPT" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr><td width="25%">Copay </td><td> :  ${tobBenefitsForMemElig[0]} </td>
				      			</tr>
				      			<tr><td width="25%">Remarks </td><td> :  ${tobBenefitsForMemElig[1]} </td>
				      			</tr>
				      			<tr><td width="25%">IP/OP Services </td><td> :  ${tobBenefitsForMemElig[11]}</td>
				      			</tr>
			      			</table>
			      		</td>
		      	</logic:equal>
		      	
		      	<logic:equal value="MTI" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr><td width="25%">Maternity Copay </td><td> : ${tobBenefitsForMemElig[6]} </td></tr><tr>
				      				<td width="25%">Deductible </td><td> :  ${tobBenefitsForMemElig[2]}</td>
				      			</tr>
				      			<tr><td width="25%">Remarks </td><td> :  ${tobBenefitsForMemElig[1]} </td></tr><tr>
				      				<td width="25%">Pharmaceutical </td><td> :  ${tobBenefitsForMemElig[12]}</td>
				      			</tr>
			      			</table>
			      		</td>
		      	</logic:equal>
		      	
		      	<logic:equal value="OPTC" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr><td width="25%">Optical Copay </td><td> : ${tobBenefitsForMemElig[8]} </td></tr><tr>
				      				<td width="25%">Deductible </td><td> :  ${tobBenefitsForMemElig[2]}</td>
				      			</tr>
				      			<tr><td width="25%">Remarks </td><td> :  ${tobBenefitsForMemElig[1]}</td>
				      			</tr>
			      			</table>
			      		</td>
		      	</logic:equal>
		      	
		      	<logic:equal value="DNTL" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr><td width="25%">Dental Copay </td><td> :  ${tobBenefitsForMemElig[10]} </td></tr><tr>
				      				<td width="25%">Deductible </td><td> :  ${tobBenefitsForMemElig[2]}</td>
				      			</tr>
				      			<tr><td width="25%">Remarks </td><td> :  ${tobBenefitsForMemElig[1]}</td></tr><tr>
				      				<td width="25%">Pharmaceutical </td><td> :  ${tobBenefitsForMemElig[12]}</td>
				      			</tr>
			      			</table>
			      		</td>
		      	</logic:equal>
		      	
		      	<logic:equal value="DAYC" name="benifitTypeVal" scope="session">
			      		<td> 
							<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
				      			<tr><td width="25%">Copay </td><td> :  ${tobBenefitsForMemElig[0]} </td></tr><tr>
				      				<td width="25%">Remarks </td><td> :  ${tobBenefitsForMemElig[1]}</td>
				      			</tr>
				      			<tr><td width="25%">IP/OP Services </td><td> :  ${tobBenefitsForMemElig[11]} </td>
				      			</tr>
			      			</table>
			      		</td>
		      	</logic:equal>
		      	</tr>
		      	
		</table>        
		</fieldset>
		</logic:equal>
		
		<br>
		
		<%-- <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		
				<tr>
		        <td  class="formLabel" >
		        <strong>Pre Approval Limit</strong>
		        <span style="margin-left: 112px;">
		        :&nbsp;<bean:write name="frmCashlessAdd" property="preApprLimit"/>
		        </span>
		        </td>
		         <td></td>
		         </tr>
		          		        
	    </table>		 --%>
		<br>
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
		        <td width="100%" align="center">
		        <logic:equal name="eligibility" value="YES">
					<button type="button" name="Button" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onPrintForms();"><u>P</u>rint Forms</button>&nbsp;&nbsp;&nbsp;
					<logic:equal value="IPT" name="benifitTypeVal" scope="session">
						<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" disabled="disabled" >P<u>r</u>oceed</button>&nbsp;
					</logic:equal>
					<logic:notEqual value="IPT" name="benifitTypeVal" scope="session">
						<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onProceed();" title="Proceed for Online PreAuth Submission">P<u>r</u>oceed</button>&nbsp;
					</logic:notEqual>
				</logic:equal>	
					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;		       
				 </td>
		      	<td width="100%" align="center">
	      	</tr>
	      
	    </table>
	    
		<!-- New Fields Ends -->
		
		
		
		<!--  <fieldset>
			<legend>Validate OTAC</legend>
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    	<tr>
			        <td width="22%" class="formLabel">Enrollment ID :</td>
			        <td width="78%" colspan="3">
			          <bean:write name="frmCashlessAdd" property="enrollId"/>
			        </td>
		      	</tr>
		      	<tr>
			        <td width="22%" class="formLabel">Benefit Type :</td>
			        <td width="78%" colspan="3">
			          <bean:write name="frmCashlessAdd" property="benifitType"/>
			        </td>
		      	</tr>
		      	
		      	<logic:notEqual name="OTP_Done" value="Done">
		      	 <logic:empty name="blocked">
		      	<tr>
			        <td width="22%" class="formLabel">One Time Authorization Code :</td>
			        <td width="78%" colspan="3">
			          <html:text property="otpcode" name="frmCashlessAdd" styleClass="textBox textBoxSmall"/>
			        </td>
		      	</tr>
		      	</logic:empty>
		      	<logic:notEmpty name="blocked">
		      		<tr>
			        	<td width="22%" class="formLabel">One Time Authorization Code :</td>
			        	<td width="78%" colspan="3">
			          		<html:text property="otpcode" name="frmCashlessAdd" styleClass="textBox textBoxSmall" disabled="true"/>
			        	 
			        	 	<a href="#" onClick="javascript:onReGenerateOtp();"><u>R</u>egenerate OTP</a>
			        	 </td>
		      		</tr>
		      	</logic:notEmpty>
		      	</logic:notEqual>
		    </table>
		    
		     <logic:empty name="blocked">
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    	<tr>
			        <td width="78%" colspan="3"><strong>
			          <font color="red"><bean:write name="frmCashlessAdd" property="wrongOtp"/>
			          </font></strong>
			        </td>
		      	</tr>
		    </table>
		    </logic:empty>
		     <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    	<tr>
			        <td width="78%" colspan="3"><strong>
			          <font color="red"><bean:write name="frmCashlessAdd" property="blockedOtp"/>
			          </font></strong>
			        </td>
		      	</tr>
		    </table>
		    
		    <logic:notEmpty name="outdated">
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    	<tr>
			        <td width="78%" colspan="3"><strong>
			          <font color="red"><bean:write name="frmCashlessAdd" property="outdatedOtp"/>
			          </font></strong>
			        </td>
		      	</tr>
		    </table>
		    </logic:notEmpty>
		</fieldset>
		
		<logic:equal name="OTP_Done" value="Done">
		<fieldset>
		<legend> Member Details</legend>
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    <tr>
		        <td width="22%">Member Name</td>
		        <td width="78%" align="left" colspan="3"><bean:write name="frmCashlessAdd" property="memberName"/></td>
	      	</tr>
	      	<tr>
		        <td width="22%">Age</td>
		        <td width="78%" align="left" colspan="3" ><bean:write name="frmCashlessAdd" property="age"/></td>
	      	</tr>
	      	<tr>
		        <td width="22%">Gender</td>
		        <td width="78%"  align="left" colspan="3"><bean:write name="frmCashlessAdd" property="gender"/></td>
	      	</tr>
	      	<tr>
		        <td width="22%">Payer</td>
		        <td width="78%"  align="left" colspan="3"><bean:write name="frmCashlessAdd" property="payer"/></td>
	      	</tr>
		    </table>
		    </fieldset>
		    <br>
		  <fieldset>
		<legend> Member Eligibility</legend>
		  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    <tr>
		        <td width="22%">Eligible</td>
		        <td width="78%"  align="left" colspan="3"><bean:write name="frmCashlessAdd" property="eligibility"/></td>
	      	</tr>
	      	<tr>
		        <td width="22%">Deductible</td>
		        <td width="78%"  align="left" colspan="3"><bean:write name="frmCashlessAdd" property="deductable"/></td>
	      	</tr>
	      	<tr>
		        <td width="22%">Co-Participation</td>
		        <td width="78%" align="left" colspan="3"><bean:write name="frmCashlessAdd" property="coPay"/></td>
	      	</tr>
	      	<tr>
		        <td width="22%">Exclusions</td>
		        <td width="78%" align="left" colspan="3"><bean:write name="frmCashlessAdd" property="exclusions"/></td>
	      	</tr>
		    </table>
		 </fieldset>
		 
		 <fieldset>
		<!-- <legend> Pre-Authorization Required</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    <tr>
		        <td width="22%">Invesigations/Diagnostics</td>
		        <td width="78%"  align="left" colspan="3">Yes</td>
	      	</tr>
	      	<tr>
		        <td width="22%">Pharmacy</td>
		        <td width="78%"  align="left" colspan="3">Yes</td>
	      	</tr>
	      	<tr>
		        <td width="22%">Physiotherapy</td>
		        <td width="78%" align="left" colspan="3">Yes</td>
	      	</tr>
	      	<tr>
		        <td width="22%">Vaccinations</td>
		        <td width="78% align="left" colspan="3">Yes</td>
	      	</tr>
	      	<tr>
		        <td width="22%">General Checkup</td>
		        <td width="78%" align="left" colspan="3">Yes</td>
	      	</tr>
	      	<tr>
		        <td width="22%">Alternative Medicines</td>
		        <td width="78%" align="left" colspan="3">Yes</td>
	      	</tr>
		    </table>
		</fieldset> -->
		    </logic:equal>
	 
		<!-- S T A R T : Buttons -->
	    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	    	
	    	<tr>
	    	<logic:notEqual name="OTP_Done" value="Done">	  
		        <td width="100%" align="center">
		        <logic:empty name="blocked">
<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onConfirmOtp();"><u>C</u>onfirm</button>&nbsp;&nbsp;&nbsp;
</logic:empty>
				 <button type="button" name="Button" accesskey="l" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseOTP();">C<u>l</u>ose</button>&nbsp;		       
				 </td>
	      	</logic:notEqual>
	      	
	      	<logic:equal name="OTP_Done" value="Done">
		      	<td width="100%" align="center">
<!--Removing PreAUth button- as discussed with saravanan  for INTX 
 					<button type="button" name="Button" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onMemberVitals();"><u>P</u>reauth</button>&nbsp;&nbsp;&nbsp; -->					
<%
if(TTKCommon.getActiveSubLink(request).equals("Pre-Authorization")){
%>
 	&nbsp;&nbsp;&nbsp;<button type="button" name="Button" accesskey="l" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseOTP_PreAuth();">C<u>l</u>ose</button>&nbsp;		       
<%}else if(TTKCommon.getActiveSubLink(request).equals("Eligibility Check")){%>
	&nbsp;&nbsp;&nbsp;<button type="button" name="Button" accesskey="l" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseOTP();">C<u>l</u>ose</button>&nbsp;
	<%} %>					
				</td>
			</logic:equal>
	      	</tr>
	    </table>
	
	    
	    
	    <logic:notEmpty name="wrong">
	     <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
			<font color=blue><b>Note :</b>  If OTP Number is entered wrong for 3 times it will be blocked. For further processing of blocked OTP, click on Regenerate OTP</font> 
			</td>
     		
     	</tr>
		</table>
		</logic:notEmpty>
	    <!-- E N D : Buttons -->
	<!-- </div> -->
<input type="hidden" name="enrollId" value="<bean:write name="frmCashlessAdd" property="enrollId"/>">
<input type="hidden" name="eligibilitySedId" value="<bean:write name="frmCashlessAdd" property="eligibilitySedId"/>">



	<input type="hidden" name="mode" value="">
	<INPUT TYPE="hidden" NAME="leftlink">
	<INPUT TYPE="hidden" NAME="sublink">
	<INPUT TYPE="hidden" NAME="tab">
</div></div></div></div></div></div></div></body>
</html:form>