<%
/**
 * @ (#) addgeneraldetail.jsp 24st Apr 2012
 * Project      : TTK HealthCare Services
 * File         : cashlessfeedbackscreen.jsp
 * Author       : Manohar
 * Company      : RCS
 * Date Created : 24st Apr 2012
 *
 * @author       : Manohar
 * Modified by   : 
 * Modified date : 
 * Reason        :
 */
 
 /**
  * This jsp is added For CR KOC1168 Feedback Form 
  * 
  */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.dto.onlineforms.FeedbackCashlessVO"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile" %>

<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="JavaScript" src="/ttk/scripts/onlineforms/cashlessfeedbackscreen.js"></script>
<%
	UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	
%>
<body onLoad="document.forms[1].elements['insuredName'].focus();">
<html:form action="/ChangeCashlessFBAction.do" method="post">
<!-- S T A R T : Page Title -->
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td>Cashless Feedback Screen
              </td>
            </tr>
        </table>
	<!-- E N D : Page Title -->
<html:errors/>
<div class="contentArea" id="contentArea">
<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
        <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success" width="16" height="16" align="absmiddle">&nbsp;
                    <bean:message name="updated" scope="request"/>
              </td>
            </tr>
        </table>
    </logic:notEmpty>
<!-- E N D : Success Box -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="width:100%; background-color:#FFFFFF;">
  		<tr>
  		<td align="left"><img src="/ttk/images/TTLLogo.gif" alt="Logo" title="Logo" width="250" height="40"></td></tr>
  		<tr><td class="formLabel" align="center"><b>Vidal Healthcare Services Pvt. LIMITED</b></td></tr>
  		<tr><td class="formLabel" align="center"><b>    CORPORATE FEEDBACK FORM</b></td></tr>
		<tr><td class="formLabel" align="left">Dear Customer,</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="formLabel" align="left">Thank you for choosing us as Healthcare Administrator. It is our pleasure to service you. To improve our customer satisfaction, we would like to know from you how we are doing so far. </td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td class="formLabel" align="center"><b>CASHLESS CLAIM</b></td></tr>
	</table>
	<fieldset>
	<legend>Personal Details</legend>
	<table>
		<tr><td class="formLabel">Name of the Employee:</td><td colspan="1" nowrap="nowrap"><input type="text" name="insuredName"  class="textBox textBoxMedium"  value="<%=userSecurityProfile.getUserName()%>" style="background-color: #EEEEEE;"  maxlength="60" /></td></tr>
	    <tr><td class="formLabel">Employee Id:</td><td colspan="1" nowrap="nowrap"><html:text property="employeeId" styleClass="textBox textBoxMedium"  value="<%=userSecurityProfile.getUSER_ID()%>" style="background-color: #EEEEEE;"  readonly="true" maxlength="60" /></td></tr>
	      <tr><td class="formLabel" nowrap>Admission Date:</td><td colspan="1" nowrap="nowrap"><html:text property="sAdmissionDate" styleClass="textBox textBoxSmall"  style="background-color: #EEEEEE;" style="height:16px;" style="background-color: #EEEEEE;" readonly="true" maxlength="20"/>
		 <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','forms[1].sAdmissionDate',document.forms[1].sAdmissionDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
		 <img src="ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="admDate" width="24" height="17" border="0" align="absmiddle"></a></td>
	    </tr>			        	
	</table>
	</fieldset>
	<logic:iterate id="FeedbackCashlessVO" name="frmChangeCashlessFBForm" property="alQuestionList" indexId="index">
	<fieldset>
	<legend><bean:write name="FeedbackCashlessVO" property="qustname"/></legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">	
	<logic:iterate id="FeedbackCashlessVO" name="FeedbackCashlessVO"  property="feedBack" indexId="index1">	
	<tr>	
	<td>
	<html:radio  property="<%=\"alQuestionList[\"+index+\"]\"%>" value="answerSeqID" idName="FeedbackCashlessVO">	
	<bean:write name="FeedbackCashlessVO" property="answerName" /></html:radio>
	</td>
	</tr>	
	</logic:iterate>
	
	</table>
	</fieldset>
	</logic:iterate>
    
      <fieldset>
    <legend>According to you what are the top priorities to fix the issue </legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	<tr>
        <td class="formLabel" height="25">Remarks: </td>
            <td colspan="1" nowrap="nowrap">
              <textarea id="remarks" name="remarks" class="textBox textAreaLong"></textarea>
            </td>
      </tr>
	</table>
    </fieldset>
    <fieldset>
    <legend>What was that you were really happy about! </legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	<tr>
        <td class="formLabel" height="25">Remarks: </td>
            <td colspan="1" nowrap="nowrap">
              <textarea id="remarks1" name="remarks1" class="textBox textAreaLong"></textarea>
            </td>   
      </tr>
	</table>
    </fieldset>
     
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
    			<button type="button" name="Button1" accesskey="S" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button2" accesskey="R" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
		  	 	<button type="button" name="Button3" accesskey="C" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>  		 		
				
 				</td>
  			</tr>  		
		</table>
    </div>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>
</body>