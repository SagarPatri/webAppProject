
<%
/** @ (#) addConsumables.jsp 
 * Project     : Vidal Healthcare Services
 * File        : addConsumables.jsp
 * Author      : kishor kumar 
 * Company     : RCS Technologies
 * Date Created: 05/03/2015
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
<script language="javascript" src="/ttk/scripts/hospital/addConsumables.js"></script>

<head>
	<link rel="stylesheet" type="text/css" href="ttk/styles/style.css" />
	<link href="/ttk/scripts/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="/ttk/scripts/bootstrap/css/bootstrap-responsive.min.css" rel="stylesheet">	
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
	<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>
<SCRIPT>
  $(document).ready(function() {
    $("#professionalId").autocomplete("auto.jsp?mode=profession");
	});  
  
  $(document).ready(function() {
	  $("#consumableDesc").autocomplete("/AsynchronousAction.do?mode=getCommonMethod&getType=addConsumablesForPreAuth");
});
  
</SCRIPT>

</head>
<%
UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
ArrayList alConsumables=(ArrayList)request.getSession().getAttribute("alConsumables");
%>
<html:form action="/OnlineConsumablesAction.do">

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
								<h4 class="sub_heading">Consumables</h4>
							</div>

						</div>
						<div class="row-fluid">
							<div style="width: 100%;">
								<div class="span12" style="margin: 0% 0%">
	<!-- S T A R T : Page Title -->
	<!-- <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td> Consumables </td>     
    		<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table> -->
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
		<legend> Add Consumable</legend>
		    <table align="center" class="formContainer"  border="1" cellspacing="0" cellpadding="0">
		<tr>
		        <td width="15%"><b>Consumable Search :</b></td>
		        <td width="30%" align="left" colspan="6"> <html:text property="consumableDesc" styleId="consumableDesc" styleClass="textBox textBoxLargest"/> </td>
      	</tr>
      	<tr bgcolor="#04B4AE">
		        <td width="7%"><font color="black"><strong>Unit Price</strong></font></td>
		        <td width="7%"><font color="black"><strong>Quantity</strong></font></td>
		        <td width="7%"><font color="black"><strong>Gross</strong></font></td>
		        <td width="7%"><font color="black"><strong>Discount</strong></font></td>
		        <td width="7%"><font color="black"><strong>Discounted Gross</strong></font></td>
		        <td width="10%"><font color="black"><strong>Patient Share</strong></font></td>
		        <td width="10%"><font color="black"><strong>Net Amount</strong></font></td>
      	</tr>
      	<tr>
      			<td>  <html:text property="addUnitPrice" styleClass="textBoxTiny" onblur="onCalcGross()" onkeyup="isNumeric(this)"/> 	</td>
				<td>  <html:text property="addQuantity" styleClass="textBoxTiny" onblur="onCalcGross()" onkeyup="isNumeric(this)"/> 	</td>
				<td>  <html:text property="addGross" styleClass="textBoxTiny" readonly="true"/> 		</td>
				<td>  <html:text property="addDiscount" styleClass="textBoxTiny" onblur="onCalcDiscGross(this)" onkeyup="isNumeric(this)"/> 	</td>
				<td>  <html:text property="addDiscountedGross" styleClass="textBoxTiny" readonly="true"/> </td>
				<td>  <html:text property="addPatientShare" styleClass="textBoxTiny" onblur="onCalcPatientShare(this)" onkeyup="isNumeric(this)"/> </td>
				<td>  <html:text property="addNetAmount" styleClass="textBoxTiny" readonly="true"/> 	</td>
		      	<td>
					 <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddConsumables();"><u>A</u>dd</button>&nbsp;
	       		</td>
      	</tr>
		    </table>
		    </fieldset>
		    <br>
		 	<br>
		<table class="formContainer" align="center" border="1" cellspacing="0" cellpadding="0">
		      <tr> <th colspan="12" align="center"> <strong>Consumables</strong> 
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
	      <logic:notEmpty name="alConsumables">
	      	<logic:iterate id="LabServiceVO" name="alConsumables" indexId="i">
		 	<tr class="<%=(i.intValue()%2)==0? "gridOddRow":"gridEvenRow"%>">	
		 	
		 		<td align="right">  <%= (i+1) %> 															 </td>
		 		<td>  <html:checkbox property="consOpt"></html:checkbox> 					 </td>
				<td>  <bean:write name="LabServiceVO" property="activityCode"/>				 </td>		
				<td>  <bean:write name="LabServiceVO" property="serviceName"/>			   	 </td>
				<td>  <input type="text" name="unitPrice" class="textBoxTiny" id="unitPrice<%=i %>" onblur="onUnitPrice(this,<%=i %>)" onkeyup="isNumeric(this)" value="<bean:write name="LabServiceVO" property="unitPrice"/> "/> </td>
				<td>  <input type="text" name="quantity" class="textBoxTiny" id="quantity<%=i %>" onblur="onQuantity(this,<%=i %>)" onkeyup="isNumeric(this)" value="<bean:write name="LabServiceVO" property="quantity"/>"  />  </td>
				<td>  <input type="text" name="gross" class="textBoxTiny" id="gross<%=i %>" readonly="readonly" value="<bean:write name="LabServiceVO" property="gross"/>" /> </td>
				<td>  <input type="text" name="discount" class="textBoxTiny" id="discount<%=i %>" onblur="onDiscount(this,<%=i %>)" onkeyup="isNumeric(this)" value="<bean:write name="LabServiceVO" property="discount"/>" /> </td>
				<td>  <input type="text" name="discountedGross" class="textBoxTiny" id="discountedGross<%=i %>" onblur="onDiscountedGross(this,<%=i %>)" readonly="readonly" value="<bean:write name="LabServiceVO" property="discGross"/>" /> </td>
				<td>  <input type="text" name="patientShare" class="textBoxTiny" id="patientShare<%=i %>" onblur="onPatientShare(this,<%=i %>)" onkeyup="isNumeric(this)" value="<bean:write name="LabServiceVO" property="patientShare"/>" /> </td>
				<td>  <input type="text" name="netAmount" class="textBoxTiny" id="netAmount<%=i %>" readonly="readonly" value="<bean:write name="LabServiceVO" property="netAmount"/>" /> </td>
					
			</tr>
			</logic:iterate>
		</logic:notEmpty>
		<logic:empty name="alConsumables">
	      	<tr>
		        <td width="100%" colspan="12" align="center"> <font color="red">No Data</font> </td>
	      	</tr>
		</table>	
		</logic:empty>	 
		
		<!-- S T A R T : Buttons -->
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
	        <td width="100%" align="center">
        	  <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseConsumables();"><u>C</u>lose</button>&nbsp;&nbsp;&nbsp;&nbsp;
        	  
        	  <button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onNextConsumables();"><u>N</u>ext</button>&nbsp;		       
	        </td>
      	</tr>
    </table>
    <!--  E N S  -->

	<input type="hidden" name="mode" value="">
	</div></div></div></div></div></div></div></body>
</html:form>