<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.struts.action.DynaActionForm"%>
<%@page import="com.ttk.dto.empanelment.LabServiceVO"%>
<%
/** @ (#) prescriptionDetails.jsp 
 * Project     : Vidal Healthcare Services
 * File        : prescriptionDetails.jsp
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
<%@ page import="java.util.Arrays"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/hospital/prescriptionDetails.js"></script>


<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<link href="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.4/jquery.min.js"></script>

	<script src="jquery-1.11.1.min.js"></script>
	
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
	<script src="js/jquery.autocomplete.js"></script>
      
<SCRIPT>
  $(document).ready(function() {
	  var hospSeqId	=	document.getElementById("hospSeqId").value;
    $("#clinicianID").autocomplete("auto.jsp?mode=preAuthProfessions&hospSeqId="+hospSeqId);
});
  
function getClinicainId(obj)
{
  var hospSeqId	=	document.getElementById("hospSeqId").value;
  $(document).ready(function() {
  $("#clinicianID").blur(function(){
    	var ID	=	obj.value;
        $.ajax({
        		url: "/AsynchronousAction.do?mode=getCommonMethod&id="+ID+"&getType=clinicianId&hospSeqId="+hospSeqId, 
        		success: function(result){
      				var res	=	result.split("@");
					document.forms[1].clinicianId.value		=	res[0];
					document.forms[1].speciality.value		=	res[1]; 
					
        		}}); 
   		 });
  });
}

function onChangeAccident(obj){
 $(document).ready(function() {
	  $("#accidentCase").change(function(){
	    	var ID	=	obj.value;
	        if(ID=='Y')
        	{
        		document.getElementById("firNo").style.display	=	"inline";
        	}
	        else{

        		document.getElementById("firNo").style.display	=	"none";
	        }
	   		 });
	  });
}

$(document).ready(function(){
    $("#firNumb").on("focus blur", function(){
    	var	val	=	document.getElementById("firNumb").value;
  	  if(val=='FIR No.')
  		  document.getElementById("firNumb").value="";
  	  else if(val=='')
  		  document.getElementById("firNumb").value='FIR No.';
    });
});


</SCRIPT>

</head>
<%
UserSecurityProfile userSecurityProfile	=	(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
%>

<style type="text/css">
/*----- Tabs -----*/
.tabs {
    width:100%;
    display:inline-block;
}
 
    /*----- Tab Links -----*/
    /* Clearfix */
    .tab-links:after {
        display:block;
        clear:both;
        content:'';
        color:#4c4c4c;
    }
 
    .tab-links li {
        margin:0px 5px;
        float:left;
        list-style:none;
    }
 
        .tab-links a {
            padding:5px 10px;
            display:inline-block;
            border-radius:2px 2px 0px 0px;
            background:#7FB5DA;
            font-size:11px;
            font-weight:600;
            color:#4c4c4c;
            transition:all linear 0.15s;
        }
 
        .tab-links a:hover {
            background:#a7cce5;
            text-decoration:none;
        }
 
    li.active a, li.active a:hover {
        background:#fff;
        color:#4c4c4c;
    }
 
    /*----- Content of Tabs -----*/
    .tab-content {
        padding:1px;
        border-radius:3px;
        box-shadow:-1px 1px 1px rgba(0,0,0,0.15);
        background:#fff;
    }
 
        .tab {
            display:none;
        }
 
        .tab.active {
            display:block;
        }
</style>
<script type="text/javascript">
jQuery(document).ready(function() {
    jQuery('.tabs .tab-links a').on('click', function(e)  {
        var currentAttrValue = jQuery(this).attr('href');
 
        // Show/Hide Tabs
        jQuery('.tabs ' + currentAttrValue).show().siblings().hide();
 
        // Change/remove current tab to active
        jQuery(this).parent('li').addClass('active').siblings().removeClass('active');
 
        e.preventDefault();
    });
});
</script>
<%

pageContext.setAttribute("medicineSystem",Cache.getCacheObject("medicineSystem"));
pageContext.setAttribute("ailmentClaimType",Cache.getCacheObject("ailmentClaimType"));
pageContext.setAttribute("surgeryType",Cache.getCacheObject("surgeryType"));
pageContext.setAttribute("treatmentPlan",Cache.getCacheObject("treatmentPlan"));

DynaActionForm frmOnlinePreAuth	=	(DynaActionForm)request.getSession().getAttribute("frmOnlinePreAuth");
%>
<html:form action="/OnlinePreAuthAction.do" method="post" enctype="multipart/form-data">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td> Pre - Authorization </td>     
    		<td width="43%" align="right" class="webBoard">&nbsp;</td>
  		</tr>
	</table>
	<html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">
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
		
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		     <tr>
		        <td width="15%"><b>Invoice Number :</b></td>
		        <td width="30%" align="left">
		        <bean:write name="frmOnlinePreAuth" property="invoiceNo"/>
		        </td>
	      	</tr>
	      	
	     </table> 	
		<fieldset>
		<legend> Member Details</legend>
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		     <tr>
		        <td width="15%"><b>Vidal Id :</b></td>
		        <td width="30%" align="left"><bean:write name="frmOnlinePreAuth" property="enrollId"/></td>
		        <td width="15%"><b>Card Holder Name :</b></td>
		        <td width="30%" align="left" ><bean:write name="frmOnlinePreAuth" property="insurredName"/></td>
	      	</tr>
	      	<tr>
		        <td width="15%"><b>Age :</b></td>
		        <td width="30%" align="left" ><bean:write name="frmOnlinePreAuth" property="age"/></td>
		        <td width="15%"><b>Gender :</b></td>
		        <td width="30%"  align="left"><bean:write name="frmOnlinePreAuth" property="gender"/></td>
	      	</tr>
	      	<tr>
		        <td width="15%"><b>Payer :</b></td>
		        <td width="30%"  align="left"><bean:write name="frmOnlinePreAuth" property="payer"/></td>
		        <td width="15%"><b>Treatment date :</b></td>
		        <td width="30%"  align="left"><%= new java.util.Date().getTime() %></td>
	      	</tr>
	      	<tr>
		        <td width="15%"><b>Provider Name :</b></td>
		        <td width="30%"  align="left"><bean:write name="frmOnlinePreAuth" property="providerName"/></td>
		        <td width="15%"><b>Intimation No. </b></td>
		        <td width="30%"  align="left"><%= new java.util.Date() %></td>
	      	</tr>
	      	
		    </table>
		    </fieldset>
		    <br>
		 
		 <fieldset>
		<legend> Consultation:</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    <tr>
		        <td width="25%">Presenting Complaints:</td>
		        <td width="25%"  align="left"><html:text property="consultation" name="frmOnlinePreAuth"/></td>
		        <td width="25%">System of Medicine:</td>
		        <td width="25%"  align="left">
		        	<html:select property="systemOfMedicine" onchange="javascript:showNoofVisits();" name="frmOnlinePreAuth">
		            <html:options collection="medicineSystem" property="cacheId" labelProperty="cacheDesc"/>
		      	</html:select>
				</td>
	      	</tr>
	      	<tr>
	    </table>
		</fieldset>
		
		<fieldset>
		<legend> Prescriptions </legend>
<%
ArrayList allabs		= null;
ArrayList alRadios		= null;
ArrayList alSurgeries	= null;
ArrayList alConsumables	= null;
ArrayList alMinors		= null;

HashMap prescriptions	= (HashMap)request.getSession().getAttribute("prescriptions");
if(prescriptions!=null){
	allabs			= (ArrayList)prescriptions.get("LABORATORY");
	alRadios		= (ArrayList)prescriptions.get("RADIOLOGY");
	alSurgeries		= (ArrayList)prescriptions.get("SURGERIES");
	alConsumables	= (ArrayList)prescriptions.get("CONSUMABLES");
	alMinors		= (ArrayList)prescriptions.get("MINORSURGERIES");
}
%>        
		    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
            <tr><td>&nbsp;</td></tr>
		      <tr> <th colspan="5" align="left"> <strong>Pharmacy</strong> <a href="#"><font color="green">Add Item</font> </a></th></tr>
	      	<tr bgcolor="#126354">
		        <td width="20%"><font color="white"><strong>Sl.No.</strong></font></td>
		        <td width="20%"><font color="white"><strong>Activity Code</strong></font></td>
		        <td width="20%"><font color="white"><strong>Short Description</strong></font></td>
		        <td width="20%"><font color="white"><strong>Price</strong></font></td>
		          <td width="20%"><font color="white"><strong>Select</strong></font></td>
	      	</tr>
	      	<%if(allabs!=null)
			{
				for(int i=0;i<allabs.size();i++)
				{
					LabServiceVO labServiceVO	=	(LabServiceVO)allabs.get(i);
					%>
					<tr>
					<td width="20%"><font color="black"><%=(i+1) %></font></td>
			        <td width="20%"><font color="black"><%=labServiceVO.getActivityCode() %></font></td>
			        <td width="20%"><font color="black"><%=labServiceVO.getShortDesc() %></font></td>
			        <td width="20%"><font color="black"><%=labServiceVO.getPrice() %></font></td>
			        <td width="20%"> <html:checkbox property="gender"/> </td>
			        </tr>
					<%
				}
			}else{%>
	      	<tr>
		        <td width="100%" colspan="5" align="center"> <font color="red">No Data</font> </td>
	      	</tr>
	      	<%} %>
		    </table>
		</fieldset>
		
		
	<!-- S T A R T : Buttons -->
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
	        <td width="100%" align="center">
			  <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSavePreAuth();"><u>S</u>ave</button>&nbsp;
        	 
        	  <button type="button" name="Button" accesskey="l" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseOTP();">C<u>l</u>ose</button>&nbsp;		       
	        </td>
      	</tr>
    </table>

	<input type="hidden" name="mode" value="">
	<input type="hidden" name="hospSeqId" value=<%=userSecurityProfile.getHospSeqId() %>> 

</html:form>