<%
/**
 * @ (#)  bankguaranteedetails.jsp June 24, 2006
 * Project      : TTK HealthCare Services
 * File         : bankguaranteedetails.jsp
 * Author       : Harsha Vardhan B N
 * Company      : Span Systems Corporation
 * Date Created : June 24, 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/bankguaranteedetails.js"></script>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<%
    boolean viewmode=true;
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
        viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
    pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>

	<script src="jquery-1.11.1.min.js"></script>
	
    <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8/jquery-ui.min.js"></script>
    <script src="http://code.jquery.com/jquery-1.8.2.js"></script>
	<script src="js/jquery.autocomplete.js"></script>
<SCRIPT>
$(document).ready(function() {
    $("#bankName").autocomplete("auto.jsp?mode=bankName");
    
});  

function getBankNames(obj)
{
	document.getElementById("validBankName").innerHTML	=	'';
  var bankName	=	obj.value;
  $(document).ready(function() {
  //$("#actInNameOf").blur(function(){
    	var ID	=	obj.value;
        $.ajax({
        		url: "/AsynchronousAction.do?mode=getCommonMethod&id="+bankName+"&getType=bankName", 
        		success: function(result){
      				//var res	=	result.split("@");
      				if(result==="valid" || result.length==5){
      					document.getElementById("validBankName").innerHTML	=	'Valid Banker Name';
      					document.getElementById("validBankName").style.color = 'green';
      				}
      				else{
      					document.getElementById("validBankName").innerHTML	=	'Invalid Banker Name';
      					document.getElementById("validBankName").style.color = 'red';
      					obj.value='';
      				}
					
        		}}); 
   		 //});
  });
}
</SCRIPT>

</head>
<!-- S T A R T : Content/Form Area -->
<html:form action="/BankGuaranteeDetailAction.do" >

<!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="57%">Bank Guarantee Details <bean:write name="frmBankGuaranteeDetail" property="caption" /></td>
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
    <!-- S T A R T : Form Fields -->
    <fieldset>
            <legend>Bank Guarantee Details</legend>
            <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
                   <tr>
                      <td width="23%" class="formLabel">Bank Guarantee Amt. (AED): <span class="mandatorySymbol">*</span></td>
                      <td width="32%">
                        <html:text property="banGuarAmt" styleClass="textBox textBoxSmall" disabled="<%=viewmode%>" readonly="<%=viewmode%>" maxlength="60"/>
                      </td>
                      <td width="20%" class="formLabel">Banker Name: <span class="mandatorySymbol">*</span></td>
                      <td width="30%">
                      <div id="validBankName"> </div> 
                        <html:text property="bankerName" styleId="bankName" styleClass="textBox textBoxLarge" disabled="<%=viewmode%>" readonly="<%=viewmode%>" maxlength="250" onblur="getBankNames(this)"/>
                      </td>
                   </tr>
                   
                   <tr>
        			  <td class="formLabel">Effective Date: <span class="mandatorySymbol">*</span></td>
        			  <td>
       					<html:text property="fromDate" name="frmBankGuaranteeDetail" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				<logic:match name="viewmode" value="false">
        				<A NAME="CalendarObjectFromDate" ID="CalendarObjectFromDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectFromDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="fromDate" width="24" height="17" border="0" align="absmiddle"></a>
        				</logic:match>
        			  </td>
        			  <td class="formLabel">Expiry Date:<span class="mandatorySymbol">*</span> </td>
        			  <td>
        				<html:text property="toDate" name="frmBankGuaranteeDetail" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				<logic:match name="viewmode" value="false">
        				<A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].toDate',document.forms[1].toDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="toDate" width="24" height="17" border="0" align="absmiddle"></a>
        				</logic:match>
        			  </td>
      			   </tr>
      			   <tr>
        			  <td class="formLabel">Issue Date: <span class="mandatorySymbol">*</span></td>
        			  <td>
       					<html:text property="issueDate" name="frmBankGuaranteeDetail" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				<logic:match name="viewmode" value="false">
        				<A NAME="CalendarObjectIssueDate" ID="CalendarObjectIssueDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectIssueDate','forms[1].issueDate',document.forms[1].issueDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="fromDate" width="24" height="17" border="0" align="absmiddle"></a>
        				</logic:match>
        			  </td>
        			  
        			   <td width="20%" class="formLabel">BG No: <span class="mandatorySymbol">*</span></td>
                      <td width="30%">
                        <html:text property="bankGuaranteeNo" styleClass="textBox textBoxMedium" disabled="<%=viewmode%>" readonly="<%=viewmode%>" maxlength="60"/>
                      </td>
      			   </tr>
      			   <tr>
        			  <td width="20%" class="formLabel">BG Type: <span class="mandatorySymbol">*</span></td>
                      <td width="30%">
                      	<logic:equal value="NEW" property="bgType" name="frmBankGuaranteeDetail">
                      	<html:select property="bgType">
                      		<html:option value="NEW">--New--</html:option>
                      		<html:option value="REN">--Renewal--</html:option>
                      	</html:select>
                      </logic:equal>
                      
                      	<logic:equal value="REN" property="bgType" name="frmBankGuaranteeDetail">
                      	<html:select property="bgType">
                      		<html:option value="REN">--Renewal--</html:option>
                      	</html:select>
                      	</logic:equal>
                      	
                      </td>
        			    <td width="20%" class="formLabel">BG Status: <span class="mandatorySymbol">*</span></td>
                      <td width="30%">
                      	<html:select property="bgStatus">
                      		<html:option value="ACT">--Active--</html:option>
                      		<html:option value="EXP">--Expired--</html:option>
                      	</html:select>
                      </td>
      			   </tr>
      			   
                   <tr>
        			  <td class="formLabel">BG Handover Date: </td>
        			  <td>
       					<html:text property="bgHandOverDate" name="frmBankGuaranteeDetail" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				<logic:match name="viewmode" value="false">
        				<A NAME="CalendarObjectBgHandOverDate" ID="CalendarObjectBgHandOverDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectBgHandOverDate','forms[1].bgHandOverDate',document.forms[1].bgHandOverDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="fromDate" width="24" height="17" border="0" align="absmiddle"></a>
        				</logic:match>
        			  </td>
        			  <td class="formLabel">BG Return Date: </td>
        			  <td>
        				<html:text property="bgReturnDate" name="frmBankGuaranteeDetail" styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        				<logic:match name="viewmode" value="false">
        				<A NAME="CalendarObjectBgReturnDate" ID="CalendarObjectBgReturnDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectBgReturnDate','forms[1].bgReturnDate',document.forms[1].bgReturnDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="toDate" width="24" height="17" border="0" align="absmiddle"></a>
        				</logic:match>
        			  </td>
      			   </tr>
      			
      			<tr>
		        <td width="20%" class="formLabel">Remarks: <span class="mandatorySymbol">*</span></td>
		        	<td width="79%" colspan="3">
		            <html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
		        	</td>
       			 </tr>
      		</table>
    </fieldset>
    <!-- E N D : Form Fields -->

        <!-- S T A R T : Buttons -->
        <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="100%" align="center">
                  <logic:match name="viewmode" value="false">
                    <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
                    <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
                  </logic:match>
                    <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>
                </td>
             </tr>
        </table>
        
        <br>
        <ttk:BankRenewHistory/>
    	<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<html:hidden property="caption"/>
<html:hidden property="guaranteeSeqID"/>
<input type="hidden" name="child" value="Bank Guarantee Details">
</html:form>
