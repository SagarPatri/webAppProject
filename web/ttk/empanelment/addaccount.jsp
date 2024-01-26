<%
/**
 * @ (#) addaccount.jsp 28th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : addaccount.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : 28th Sep 2005
 *
 * @author       :
 * Modified by   : kishor kumar S H
 * Modified date : Dec 27 2014
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList" %>
<%
	boolean viewmode=true;
	boolean viewmodePO=true;
	
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))	
	pageContext.setAttribute("stateCode", Cache.getCacheObject("stateCode"));
	pageContext.setAttribute("cityCode", Cache.getCacheObject("cityCode"));
	pageContext.setAttribute("countryCode", Cache.getCacheObject("countryCode"));
	pageContext.setAttribute("chequeCode",Cache.getCacheObject("chequeCode"));
	pageContext.setAttribute("payOrderType",Cache.getCacheObject("payOrderType"));
	pageContext.setAttribute("viewmode",new Boolean(viewmode));	
	pageContext.setAttribute("viewmodePO",new Boolean(viewmodePO));	
%>
<logic:match name="frmAccounts" property="hidpoEmpFeeCharged" value="N">
	<% viewmodePO=false; pageContext.setAttribute("viewmodePO",new Boolean(viewmodePO)); %>
</logic:match>


<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/accounts.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>

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
    $("#bankName").autocomplete("auto.jsp?mode=bankNameStad");
}); 
function getClinicainId(obj)
{
	document.getElementById("validHosp").innerHTML	=	'';
  var accountName	=	document.getElementById("actInNameOf").value;
  var HospitalSeqId	=	document.forms[1].HospitalSeqId.value;
  $(document).ready(function() {
  //$("#actInNameOf").blur(function(){
    	var ID	=	obj.value;
        $.ajax({
        		url: "/AsynchronousAction.do?mode=getCommonMethod&id="+accountName+"&getType=accountName&HospitalSeqId="+HospitalSeqId, 
        		success: function(result){
      				//var res	=	result.split("@");
      				if(result==="valid" || result.length==5){
      					document.getElementById("validHosp").innerHTML	=	'The Provider Trade Name and Account Name is matching';
      					document.getElementById("validHosp").style.color = 'green';
      				}
      				else{
      					document.getElementById("validHosp").innerHTML	=	'The Provider Trade Name and Account Name is not matching';
      					document.getElementById("validHosp").style.color = 'red';
      				}
					
        		}}); 
   		 //});
  });
}

</SCRIPT>

</head>
<%
//intX
ArrayList alaccounts=	(ArrayList)request.getSession().getAttribute("alaccounts");



String emplNum		=	(String)request.getSession().getAttribute("emplNum")==null?"":(String)request.getSession().getAttribute("emplNum");
boolean readMode	=	false;
if(!emplNum.equals(""))
	readMode	=	true;
Long HospitalSeqId= new Long(TTKCommon.getWebBoardId(request));//get the web board id
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/AccountsAction.do" >
<!-- S T A R T : Page Title -->
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="51%">Account Details - <bean:write name="frmAccounts" property="caption"/></td>
	        <td align="right" class="webBoard">
				<a href="#" onClick="javascript:onAccountHistory()"><img src="ttk/images/HistoryIcon.gif" alt="View History" width="16" height="16" border="0" align="absmiddle" class="icons"></a>&nbsp;&nbsp;<img src="ttk/images/IconSeparator.gif" width="1" height="15" align="absmiddle" class="icons">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %>
		    </td>
	      </tr>
	    </table>

	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
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
	
	
	<!-- projectX Starts-->
	<fieldset>
	
		 <logic:empty name="frmAccounts" property="partnerOrProvider">
	<legend>Provider Bank Account Details</legend>

	</logic:empty>
	
	
    <!-- <legend>Account Details</legend> -->
    
    <logic:notEmpty name="frmAccounts" property="partnerOrProvider">
	
	<legend>Partner Bank Account Details</legend>
	</logic:notEmpty>
	
	<logic:notEmpty name="alaccounts"> 
	
    	<table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
     	 <tr class="borderBottom">
     	 
        	<td width="20%" class="textLabelBold" nowrap align="left">Bank Name </td>
        	<td width="20%" class="textLabelBold" nowrap align="left">Account Number</td>
        	<td width="20%" class="textLabelBold" nowrap align="left">IBAN Number   </td>
        	<td width="20%" class="textLabelBold" nowrap align="left">Start Date </td>
        	<td width="20%" class="textLabelBold" nowrap align="left">End Date </td>
        	
        </tr>
        
        
        <!-- Iterate Account Details for particular Hospital -->
        <logic:iterate id="accountDetailVO" name="alaccounts" indexId="i">
		 <tr class="<%=(i.intValue()%2)==0? "gridOddRow":"gridEvenRow"%>">	
				<td><bean:write name="accountDetailVO" property="bankName"/></td>	
				<td><bean:write name="accountDetailVO" property="accountNumber"/></td>	
				<td><bean:write name="accountDetailVO" property="bankIfsc"/></td>	
				<td><bean:write name="accountDetailVO" property="startDate"/></td>	
				<td><bean:write name="accountDetailVO" property="endDate"/></td>	
			</tr>
		</logic:iterate>
		
		
		<!-- below code removed as it is showing one line records for Hospital -->
        <!-- tr>	
		      <td> <bean:write name="frmAccounts" property="bankName"/> </td>
		      <td> <bean:write name="frmAccounts" property="accountNumber"/> </td>
		      <td> <bean:write name="frmAccounts" property="bankIfsc"/> </td>
		      <td> <bean:write name="frmAccounts" property="startDate"/> </td>
		      <td> <bean:write name="frmAccounts" property="endDate"/> </td>
		    
      	</tr-->
      	</table>
	
	</logic:notEmpty>
	
	
		
		<logic:empty name="alaccounts">
		<table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="5" align="left"><font color="red"> <strong> Account details not available</strong> </font>	</td>
			</tr>
		</table>
		</logic:empty>
	</fieldset>
	<br/>
	<!-- projectX Ends-->
	
	
	<logic:notEmpty name="frmAccounts" property="partnerOrProvider">
	
	<fieldset><legend>Partner Bank Account Details</legend>
	</logic:notEmpty>
	<logic:empty name="frmAccounts" property="partnerOrProvider">
	<fieldset><legend>Provider Bank Account Details</legend>
	</logic:empty>
	<!-- S T A R T : Form Fields -->
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	  <logic:notEmpty name="frmAccounts" property="bankName">
	    <td width="20%" class="formLabel">Bank Name: <span class="mandatorySymbol">*</span></td>
	    <td width="33%"><html:text property="bankName" styleId="bankName" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="ConvertToUpperCase(event.srcElement);" disabled="<%=viewmode%>" readonly="<%=readMode %>"/>
	    </td>
	   </logic:notEmpty>
	   <logic:empty name="frmAccounts" property="bankName">
	    <td width="20%" class="formLabel">Bank Name: <span class="mandatorySymbol">*</span></td>
	    <td width="33%"><html:text property="bankName" styleId="bankName" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="ConvertToUpperCase(event.srcElement);" style="background-color: #EEEEEE;" disabled="<%=viewmode%>" readonly="<%=readMode %>" />
	    </td>
	   </logic:empty>
	  
	  <logic:notEmpty name="frmAccounts" property="accountNumber">
	    <td class="formLabel">Account No.:<span class="mandatorySymbol">*</span></td>
	    <td><html:text property="accountNumber" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>" readonly="<%=readMode %>"/>
	 	</td>
	 </logic:notEmpty>
	 <logic:empty name="frmAccounts" property="accountNumber">
	    <td class="formLabel">Account No.:<span class="mandatorySymbol">*</span></td>
	    <td><html:text property="accountNumber" styleClass="textBox textBoxMedium" maxlength="60" style="background-color: #EEEEEE;"  disabled="<%=viewmode%>" readonly="<%=readMode %>" value="NA"/>
	 	</td>
	 </logic:empty>
	 </tr>
	 <logic:notEmpty name="frmAccounts" property="actInNameOf">
	 <tr>
	   <logic:empty name="frmAccounts" property="partnerOrProvider" >
	    <td colspan="3"> <div id="validHosp"> </div> </td> </tr>
	    <td class="formLabel">Account Name:<span class="mandatorySymbol">*</span></td>
	    <td><html:text property="actInNameOf" styleId="actInNameOf" styleClass="textBox textBoxLarge" maxlength="250" disabled="<%=viewmode%>" readonly="<%=readMode %>"/>
		</td>
	   </logic:empty>
		<logic:notEmpty  name="frmAccounts" property="partnerOrProvider">
		  <td colspan="3"> <div id="validHosp"> </div> </td> </tr>
	    <td class="formLabel">Account Name:<span class="mandatorySymbol">*</span></td>
	    <td><html:text property="actInNameOf" styleId="actInNameOf" styleClass="textBox textBoxLarge" maxlength="250" disabled="<%=viewmode%>" readonly="<%=readMode %>"/>
		</td>
		</logic:notEmpty>
	</logic:notEmpty>
	<logic:empty name="frmAccounts" property="actInNameOf">
		 <tr>
		  <logic:empty name="frmAccounts" property="partnerOrProvider">
		 <td colspan="3"> <div id="validHosp"> </div> </td> </tr>
	    <td class="formLabel">Account Name:<span class="mandatorySymbol">*</span></td>
	    <td><html:text property="actInNameOf" styleId="actInNameOf" styleClass="textBox textBoxLarge" maxlength="250" style="background-color: #EEEEEE;"  disabled="<%=viewmode%>" value="NA" />
		</td>
		</logic:empty>
		<logic:notEmpty  name="frmAccounts" property="partnerOrProvider">
		<td colspan="3"> <div id="validHosp"> </div> </td> </tr>
	    <td class="formLabel">Account Name:<span class="mandatorySymbol">*</span></td>
	    <td><html:text property="actInNameOf" styleId="actInNameOf" styleClass="textBox textBoxLarge" maxlength="250" style="background-color: #EEEEEE;"  disabled="<%=viewmode%>" value="NA" />
		</td>
		</logic:notEmpty>
	</logic:empty>
	  </tr>
	  
	  <!-- adding IBAN and SWIFT code for intX -->
	  
	 <tr>
	 
	 
	 <logic:empty name="frmAccounts" property="partnerOrProvider">
	  <logic:notEmpty name="frmAccounts" property="ibanNumber">
	    <td class="formLabel">IBAN No.:<span class="mandatorySymbol">*</span></td>
	    <td><html:text property="ibanNumber" styleClass="textBox textBoxMedium" maxlength="23" disabled="<%=viewmode%>"  readonly="<%=readMode %>"/>
	 	</td>
	 </logic:notEmpty>
	 <logic:empty name="frmAccounts" property="ibanNumber">
	    <td class="formLabel">IBAN No.:<span class="mandatorySymbol">*</span></td>
	    <td><html:text property="ibanNumber" styleClass="textBox textBoxMedium" maxlength="23" style="background-color: #EEEEEE;"  disabled="<%=viewmode%>" readonly="<%=readMode %>" value="NA"/>
	 	</td>
	 </logic:empty>
	 <logic:notEmpty name="frmAccounts" property="swiftCode">
	    <td class="formLabel">Swift Code:</td>
	    <td><html:text property="swiftCode" styleClass="textBox textBoxLarge" maxlength="250" disabled="<%=viewmode%>" readonly="<%=readMode %>"/>
		</td>
	</logic:notEmpty>
	<logic:empty name="frmAccounts" property="swiftCode">
	    <td class="formLabel">Swift Code:</td>
	    <td><html:text property="swiftCode" styleClass="textBox textBoxLarge" maxlength="250" style="background-color: #EEEEEE;"  disabled="<%=viewmode%>" readonly="<%=readMode %>" value="NA"/>
		</td>
	</logic:empty>
	</logic:empty>
	
	
	<logic:notEmpty name="frmAccounts" property="partnerOrProvider">
	<logic:notEmpty name="frmAccounts" property="ibanNumber">
	    <td class="formLabel">IBAN No.:</td>
	    <td><html:text property="ibanNumber" styleClass="textBox textBoxMedium" maxlength="23" disabled="<%=viewmode%>"  readonly="<%=readMode %>"/>
	 	</td>
	 </logic:notEmpty>
	 <logic:empty name="frmAccounts" property="ibanNumber">
	    <td class="formLabel">IBAN No.:</td>
	    <td><html:text property="ibanNumber" styleClass="textBox textBoxMedium" maxlength="23" style="background-color: #EEEEEE;"  disabled="<%=viewmode%>" readonly="<%=readMode %>" value="NA"/>
	 	</td>
	 </logic:empty>
	 <logic:notEmpty name="frmAccounts" property="swiftCode">
	    <td class="formLabel">Swift Code:<span class="mandatorySymbol">*</span></td>
	    <td><html:text property="swiftCode" styleClass="textBox textBoxLarge" maxlength="250" disabled="<%=viewmode%>" readonly="<%=readMode %>"/>
		</td>
	</logic:notEmpty>
	<logic:empty name="frmAccounts" property="swiftCode">
	    <td class="formLabel">Swift Code:<span class="mandatorySymbol">*</span></td>
	    <td><html:text property="swiftCode" styleClass="textBox textBoxLarge" maxlength="250" style="background-color: #EEEEEE;"  disabled="<%=viewmode%>" readonly="<%=readMode %>" value="NA"/>
		</td>
	</logic:empty>
	
	</logic:notEmpty>
	 </tr>
	 <tr>
	<td></td><td></td>
	<logic:notEmpty name="frmAccounts" property="partnerOrProvider">
	
	 <logic:notEmpty name="frmAccounts" property="routeCode">
	    <td class="formLabel">Route Code:<span class="mandatorySymbol">*</span></td>
	    <td><html:text property="routeCode" styleClass="textBox textBoxLarge" maxlength="250" disabled="<%=viewmode%>" readonly="<%=readMode %>" />
		</td>
	</logic:notEmpty>
	<logic:empty name="frmAccounts" property="routeCode">
	    <td class="formLabel">Route Code:<span class="mandatorySymbol">*</span></td>
	    <td><html:text property="routeCode" styleClass="textBox textBoxLarge" maxlength="250" style="background-color: #EEEEEE;"  disabled="<%=viewmode%>" readonly="<%=readMode %>" value="NA" />
		</td>
	</logic:empty>
	</logic:notEmpty>
	 </tr>
	 
	  <!-- E N D -->
	  
	  <tr>
                <td class="formLabel"> Start Date<span class="mandatorySymbol">*</span> : </td>
                 <td>
        				<html:text property="startDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=readMode%>"/>
        				
        					<a name="CalendarObjectStartDate" id="CalendarObjectStartDate" href="#" onClick="javascript:show_calendar('CalendarObjectStartDate','frmAccounts.startDate',document.frmAccounts.startDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>
        				
        			</td>
        			<td class="formLabel"> End Date: </td>
                 <td>
        				<html:text property="endDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=readMode%>" onblur="endDateValidation();" />
        				
        					<a name="CalendarObjectEndDate" id="CalendarObjectEndDate" href="#" onClick="javascript:show_calendar('CalendarObjectEndDate','frmAccounts.endDate',document.frmAccounts.endDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
        						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
        					</a>
        				
        			</td>
      </tr>
                 
                 
	  <tr>
	      <td colspan="4" height="5"></td>
	      </tr>
	      <tr>
	        <td colspan="4" class="formLabelBold">Bank Branch Details </td>
	      </tr>
		  <tr>
	        <td colspan="4" height="2"></td>
	      </tr>
	  <tr>
	  <tr>
	  <logic:notEmpty name="frmAccounts" property="branchName">
	    <td class="formLabel">Branch Name: <span class="mandatorySymbol">*</span></td>
	    <td><html:text property="branchName" styleClass="textBox textBoxLarge" onkeypress='ConvertToUpperCase(event.srcElement);' maxlength="250" disabled="<%=viewmode%>" readonly="<%=readMode %>"/></td>
	    </logic:notEmpty>
	    <logic:empty name="frmAccounts" property="branchName">
	    <td class="formLabel">Branch Name: <span class="mandatorySymbol">*</span></td>
	    <td><html:text property="branchName" styleClass="textBox textBoxLarge" onkeypress='ConvertToUpperCase(event.srcElement);' maxlength="250" style="background-color: #EEEEEE;"  disabled="<%=viewmode%>" readonly="<%=readMode %>" value="NA"/></td>
	    </logic:empty>
	    <td>&nbsp;</td>
	    <td>&nbsp;</td>
	  </tr>
	  <logic:notEmpty name="frmAccounts" property="bankAddressVO.address1">
        <td width="20%" class="formLabel">Address 1: <!-- <span class="mandatorySymbol">*</span> --></td>
        <td width="33%"><html:text property="bankAddressVO.address1" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>" readonly="<%=readMode %>"/></td>
        </logic:notEmpty>
        <logic:empty name="frmAccounts" property="bankAddressVO.address1">
        <td width="20%" class="formLabel">Address 1: <!-- <span class="mandatorySymbol">*</span> --></td>
        <td width="33%"><html:text property="bankAddressVO.address1" styleClass="textBox textBoxMedium" maxlength="250" style="background-color: #EEEEEE;"  disabled="<%=viewmode%>" readonly="<%=readMode %>" value="NA"/></td>
        </logic:empty>
        <td width="17%" class="formLabel">Address 2:</td>
        <td width="30%"><html:text property="bankAddressVO.address2" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>" readonly="<%=readMode %>"/></td>
      </tr>
	  <tr>
	   <td class="formLabel">Address 3: </td>
 	    <td><html:text property="bankAddressVO.address3" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>" readonly="<%=readMode %>"/></td>
	    <td class="formLabel">Governorate/State: <!-- <span class="mandatorySymbol">*</span> --></td>
	    <td>
			<html:select property ="bankAddressVO.stateCode" styleId="state1"  styleClass="selectBox selectBoxMedium" onchange="onChangeState('state1')" disabled="<%=viewmode%>">
                 <html:option value="">Select from list</html:option>
                 <html:options collection="stateCode" property="cacheId" labelProperty="cacheDesc"/>
    	   	</html:select>
	   </td>
	  </tr>
	  <tr>
	   <td class="formLabel">Area: <!-- <span class="mandatorySymbol">*</span> --></td>
        <td>
         	<html:select property ="bankAddressVO.cityCode" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                 <html:option value="">Select from list</html:option>
                 <html:optionsCollection property="alCityList" label="cacheDesc" value="cacheId"/>
          	</html:select>
        </td>
        <td class="formLabel">PO Box:<!-- <span class="mandatorySymbol">*</span> --></td>
        <td><html:text property="bankAddressVO.pinCode" styleClass="textBox textBoxSmall" maxlength="10" disabled="<%=viewmode%>"/></td>
      </tr>
      <tr>
        <td class="formLabel">Country: <!-- <span class="mandatorySymbol">*</span> --></td>
        <td colspan="3">
        	<html:select property ="bankAddressVO.countryCode" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
        		<html:option value="">--Select from list--</html:option>
                 <html:options collection="countryCode" property="cacheId" labelProperty="cacheDesc"/>
          </html:select>
        </td>
      </tr>
      <tr>
        <td colspan="4" height="5"></td>
      </tr>
      <tr>
      	<logic:empty name="frmAccounts" property="partnerOrProvider">
	 <td colspan="4" class="formLabelBold">Provider Management Details </td>
	</logic:empty>
	<logic:notEmpty name="frmAccounts" property="partnerOrProvider">
	 <td colspan="4" class="formLabelBold">Partner Management Details </td>
	</logic:notEmpty>
        </tr>
	  <tr>
        <td colspan="4" height="2"></td>
      </tr>
	  <tr>
        <td class="formLabel">Management Name:</td>
        <td><html:text property="managementName" styleClass="textBox textBoxLarge" maxlength="250" disabled="<%=viewmode%>" onkeypress="ConvertToUpperCase(event.srcElement);"/></td>
        <td class="formLabel">Issue Cheques To:</td>
           <td>
           <html:select property="issueChqTo" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
           		<html:option value="">--Select from list--</html:option>
                 <html:options collection="chequeCode" property="cacheId" labelProperty="cacheDesc"/>
           </html:select>
           </td>
         </tr>
         
     <!-- intX Finance Review-->
     <tr>
        <td colspan="4" class="formLabelBold">Finance Reviewed Details </td>
     </tr>
     <tr>
        <td colspan="4" height="2"></td>
      </tr>
	  <tr>
        <td class="formLabel">Finance Reviewed:</td>
        <td><bean:write name="frmAccounts" property="reviewedYN"/>
        <td class="formLabel">Reviewed By:</td>
        <td><bean:write name="frmAccounts" property="reviewedBy"/>
        </td>
     </tr>
     <tr>
        <td class="formLabel">Reviewed Date:</td>
        <td><bean:write name="frmAccounts" property="reviewedDate"/>
     </tr>
         
</table>
</fieldset>


<fieldset>
	<legend>Pay Order Details</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" class="formLabel">Empanel. Fee Charged: </td>
        <td width="33%"><html:checkbox property="emplFeeChrgYn" styleId="emplFeeChrgYn" value="Y" disabled="<%=viewmode%>" onclick="javascript:isEmpanelCharged('false')"/></td>
		</td>
        <td width="17%" class="formLabel">&nbsp;</td>
        <td width="30%">&nbsp;</td>
      </tr>
      <tr>
        <td width="20%" class="formLabel">Type:</td>
        <td width="33%">
         <html:select property="payOrdType" styleClass="selectBox selectBoxMedium" disabled="<%=(viewmode||!viewmodePO)%>">
		         <html:option value="">Select from list</html:option>
                 <html:options collection="payOrderType" property="cacheId" labelProperty="cacheDesc"/>
           </html:select>
         </td>
        <td width="17%" class="formLabel">Pay Order No.:</td>
        <td width="30%"><html:text property="payOrdNmbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=(viewmode||!viewmodePO)%>" />
		</td>
      </tr>
      <tr>
        <td class="formLabel">Amount :</td>
        <td><html:text property="payOrdAmountWord" styleClass="textBox textBoxSmall" maxlength="50" disabled="<%=(viewmode||!viewmodePO)%>" value=""/>
		</td>
        <td class="formLabel">Received Date:</td>
        <td><html:text property="payOrdRcvdDate" styleClass="textBox textDate" maxlength="10" disabled="<%=(viewmode||!viewmodePO)%>"/>
        <logic:match name="viewmode" value="false">
        <A NAME="CalendarObjectempDate1" ID="CalendarObjectempDate1" HREF="#" onClick="javascript:showCalendar();return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
      </tr>
      <tr>
        <td class="formLabel">Bank Name:</td>
        <td><html:text property="payOrdBankName" styleClass="textBox textBoxLarge" maxlength="250" disabled="<%=(viewmode||!viewmodePO)%>"/>
        </td>
        <td class="formLabel">Issued Date:</td>
       <td><html:text property="chkIssuedDate" styleClass="textBox textDate" maxlength="10" disabled="<%=(viewmode||!viewmodePO)%>"/>
           <logic:match name="viewmode" value="false"><A NAME="CalendarObjectempDate2" ID="CalendarObjectempDate2" HREF="#" onClick="javascript:showCalendar1();return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
      </tr>
      <tr>
	        <td width="20%" class="formLabel">Address 1: </td>
	        <td width="33%"><html:text property="payOrdBankAddressVO.address1" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=(viewmode||!viewmodePO)%>"/></td>
	        <td width="17%" class="formLabel">Address 2:</td>
	        <td width="30%"><html:text property="payOrdBankAddressVO.address2" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=(viewmode||!viewmodePO)%>"/></td>
      <tr>
	      <td class="formLabel">Address 3: </td>
	 	  <td><html:text property="payOrdBankAddressVO.address3" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=(viewmode||!viewmodePO)%>"/></td>
		  <td class="formLabel">Governorate/State: </td>
		  <td>
				<html:select property ="payOrdBankAddressVO.stateCode" styleId="state2" styleClass="selectBox selectBoxMedium" onchange="onChangeState('state2')" disabled="<%=(viewmode||!viewmodePO)%>" >
	                 <html:option value="">Select from list</html:option>
	                 <html:options collection="stateCode" property="cacheId" labelProperty="cacheDesc"/>
	    	   	</html:select>
		  </td>
      </tr>
      <tr>
	      <td class="formLabel">City: </td>
    	    <td>
        	 	<html:select property ="payOrdBankAddressVO.cityCode" styleClass="selectBox selectBoxMedium" disabled="<%=(viewmode||!viewmodePO)%>">
            	     <html:option value="">Select from list</html:option>
                	 <html:optionsCollection property="alCityCode" label="cacheDesc" value="cacheId"/>
	          	</html:select>
    	    </td>
	        <td class="formLabel">PO Box:</td>
    	    <td><html:text property="payOrdBankAddressVO.pinCode" styleClass="textBox textBoxSmall" maxlength="10" disabled="<%=(viewmode||!viewmodePO)%>"/></td>
      </tr>
      <tr>
        <td class="formLabel">Country: </td>
        <td colspan="3">
        <logic:notMatch name="frmAccounts" property="hidpoEmpFeeCharged" value="Y">
	        <html:select property="payOrdBankAddressVO.countryCode" styleClass="selectBox selectBoxMedium" disabled="true">
	        <html:option value="">--Select from list--</html:option>
	        	<html:options collection="countryCode" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
  	    </logic:notMatch>
  	    <logic:match name="frmAccounts" property="hidpoEmpFeeCharged" value="Y">
  	    	<html:select property="payOrdBankAddressVO.countryCode" styleClass="selectBox selectBoxMedium" disabled="<%=(viewmode||!viewmodePO)%>">
  	    	<html:option value="">--Select from list--</html:option>
	        	<html:options collection="countryCode" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>
  	    </logic:match>
        </td>
      </tr>
    </table>
	</fieldset>
	<fieldset>
	<legend>Bank Guarantee Details</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	   <tr>
        <td class="formLabel">Bank Guarantee:</td>
        <td><html:checkbox property="bankGuantReqYN" value="Y" disabled="<%=viewmode%>"/>
        </td>
        <td>&nbsp;</td>
        <td class="formLabelBold">&nbsp;</td>
      </tr>
      <tr>
        <td width="20%" class="formLabel">Bank Name:</td>
        <td width="33%" class="formLabelBold"><bean:write name="frmAccounts" property="guaranteeBankName"/>
        </td>
        <td width="17%"><span class="formLabel">Amount :</span></td>
        <td width="30%" class="formLabelBold"><bean:write name="frmAccounts" property="guaranteeAmountWord"/> </td>
      </tr>
      <tr>
        <td class="formLabel">Commencement Date: </td>
        <td class="formLabelBold"><bean:write name="frmAccounts" property="guaranteeCommDate"/></td>
        <td class="formLabel">Expiry Date: </td>
        <td class="formLabelBold"><bean:write name="frmAccounts" property="guaranteeExpiryDate"/></td>
      </tr>
    </table>
	</fieldset>
	
	
	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	     <%
			if(TTKCommon.isAuthorized(request,"Edit"))
			{
		%>
		    	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUserSubmit();"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>
		<%
		    }
	 	 %>
	 	 </td>
	  </tr>
	</table>
</div>






<!-- E N D : Buttons -->
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="">
<INPUT TYPE="hidden" NAME="hidpoEmpFeeCharged" VALUE="">
<INPUT TYPE="hidden" NAME="bankGuar" VALUE="">
<html:hidden property="tpaRegdDate"/>
<html:hidden property="partnerOrProvider"/>
<input type="hidden" name="focusID" value="">
<INPUT TYPE="hidden" NAME="emplFeeChrgYn" VALUE="">
<input type="hidden" name="HospitalSeqId" value="<%= HospitalSeqId%>"/>
<INPUT TYPE="hidden" NAME="bankGuantReqYN" VALUE="">
<logic:notEmpty name="frmAccounts" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
</html:form>
