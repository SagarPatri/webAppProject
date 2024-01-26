<%
/**
 * @ (#) editinsfeedback.jsp 12th Nov 2005
 * Project      : TTK HealthCare Services
 * File         : editinsfeedback.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 12th Nov 2005
 *
 * @author       :Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon, com.ttk.common.security.Cache" %>
<%

	pageContext.setAttribute("commCode",Cache.getCacheObject("commCode"));
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	pageContext.setAttribute("viewmode",new Boolean(viewmode));		
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/editinsfeedback.js"></script>

	<!-- S T A R T : Content/Form Area -->
	<html:form action="/EditInsuranceFeedbackAction.do" method="post" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
        <td><bean:write name="frmEditInsFeedback" property="caption"/></td>
        <td align="right" class="webBoard"></td>
      </tr>
    </table>
    <!-- E N D : Page Title -->
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
    <div class="contentArea" id="contentArea">
    <fieldset><legend>Feedback Details</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="formLabel">Received Date: <span class="mandatorySymbol">*</span></td>
        <td width="32%">
        	<html:text property="recievedDate" styleClass="textBox textBoxSmall" maxlength="10" onkeypress="javascript:blockEnterkey(event.srcElement);" disabled="<%= viewmode %>"/><logic:match name="viewmode" value="false"><A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','forms[1].recievedDate',document.forms[1].recievedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a></logic:match>
        </td>
		<td width="22%">Communication Mode: </td>
		<td width="24%">

			<html:select property="commType" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
		  		<html:option value="">Select from list</html:option>
		        <html:optionsCollection name="commCode" label="cacheDesc" value="cacheId" />
           	</html:select>
        </td>
     </tr>
     <tr>
       <td width="22%" class="formLabel">Description: <span class="mandatorySymbol">*</span></td>
       <td colspan="3">
       		<html:textarea property="description" styleClass="textBox textAreaLong" disabled="<%= viewmode %>"/>
       </td>
     </tr>
   </table>
   </fieldset>
   <!-- E N D : Form Fields -->
   <!-- S T A R T :  Buttons -->
   <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="100%" align="center">
      	<%
		   		if(TTKCommon.isAuthorized(request,"Edit"))
				{
    	 %>
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSubmit();"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	     <%
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		 %>
	      <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
      </td>
    </tr>
  </table>
  <!-- E N D :  Buttons -->
  <input type="hidden" name="systemDate" value="<%=TTKCommon.getFormattedDate(TTKCommon.getDate())%>">
  <INPUT TYPE="hidden" NAME="feedbackID" VALUE="<bean:write name="frmEditInsFeedback" property="feedbackID"/>">
  <INPUT TYPE="hidden" NAME="rownum" VALUE='<%= TTKCommon.checkNull(request.getParameter("rownum"))%>'>
  <INPUT TYPE="hidden" NAME="mode" VALUE="">
  <input type="hidden" name="child" value="FeedbackDetail">
  <html:hidden property="caption"/>
   </div>
  </html:form>
  <!-- E N D : Content/Form Area -->
