<%
/** @ (#) edithospvalidation.jsp 21st Sep 2005
 * Project     : TTK Healthcare Services
 * File        : edithospvalidation.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 21st Sep 2005
 *
 * @author 			Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%
	boolean bVisitDone=true;
	boolean bValidationReq=true;
	boolean viewmode=true;
	String ampm[] = {"AM","PM"};
	String validationReq[] = {"No","Yes"};
	String reqValue[]={"N","Y"};
	
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}// end of if(TTKCommon.isAuthorized(request,"Edit"))
	pageContext.setAttribute("reqValue",reqValue);
	pageContext.setAttribute("ampm",ampm);
	pageContext.setAttribute("validationStatus", Cache.getCacheObject("validationStatus"));
	pageContext.setAttribute("validationReq",validationReq);
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("bVisitDone",new Boolean(bVisitDone));	
%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/edithospvalidation.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/EditHospValidationAction" >
	<!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="51%"><bean:write name="frmEditHospValidation" property="caption"/></td>
        <td>&nbsp;</td>
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
	<logic:match name="frmEditHospValidation" property="visitDone" value="Y">
		<% bVisitDone=false;pageContext.setAttribute("bVisitDone",new Boolean(bVisitDone));	%>
    </logic:match>
    <logic:match name="frmEditHospValidation" property="validationReqd" value="Y">
		<% bValidationReq=false; %>
    </logic:match>
	<fieldset>
    <legend>General</legend>
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="18%" nowrap class="formLabel indentedLabels">Validation Required:</td>
        <td width="37%" valign="bottom" nowrap class="formLabel">

        	<html:select property="validationReqd" name="frmEditHospValidation" styleClass="selectBox" onchange="javascript:isValidationRequried()" disabled="<%=viewmode%>">
        		<html:options name="reqValue" labelName="validationReq"/>
			</html:select>

        </td>
        <td width="15%" nowrap class="formLabel">Marked Date: <span class="mandatorySymbol">*</span></td>
        <td width="30%" valign="bottom" class="formLabel">
        	<html:text property="markedDate"  styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','forms[1].markedDate',document.forms[1].markedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match>
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Validation Visit Done: </td>
        <td valign="bottom" nowrap class="formLabel">
          <html:checkbox property="visitDone" value="Y" onclick="javascript:isVisitDone()" disabled="<%=(viewmode || bValidationReq)%>"/>
        </td>
        <td nowrap class="formLabel">Validated By:<br></td>
        <td valign="bottom" class="formLabel">
	        <html:text property="validatedBy"  styleClass="textBox textBoxLarge" maxlength="250" disabled="<%=(viewmode || bVisitDone) %>"/>
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Validated Date:</td>
        <td valign="bottom" nowrap class="formLabel">
         <table cellpadding="1" cellspacing="0">
          <tr>
           <td><html:text property="validatedDate"  styleClass="textBox textDate" maxlength="10" disabled="<%=(viewmode || bVisitDone) %>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectInvDate" ID="CalendarObjectInvDate" HREF="#" onClick="javascript:showCalendar();return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="vldDate" width="24" height="17" border="0" align="absbottom"></a></logic:match>&nbsp;&nbsp;&nbsp;</td>
           <td><html:text property="validatedTime"  styleClass="textBox textTime"  maxlength="5" disabled="<%=(viewmode || bVisitDone) %>"/>&nbsp;</td>
           <td><html:select property="day" name="frmEditHospValidation" styleClass="selectBox" disabled="<%=(viewmode || bVisitDone) %>">
        		<html:options name="ampm" labelName="ampm"/>
			</html:select></td>
          </tr>
        </table>	
       </td>
        <td nowrap class="formLabel">Report Received:<br></td>
        <td valign="bottom" class="formLabel">
        	<html:checkbox property="reportRcvd" value="Y"  disabled="<%=(viewmode || bVisitDone) %>"/>
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Validation Status: </td>
        <td colspan="3" nowrap class="formLabel">

			<html:select property="validationStatus" styleClass="selectBox selectBoxMedium" disabled="<%=(viewmode || bVisitDone) %>">
		  	 	  <html:option value="">Select from List</html:option>
		          <html:optionsCollection name="validationStatus" label="cacheDesc" value="cacheId" />
        	</html:select>
        </td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Description:</td>
        <td colspan="3" nowrap class="formLabel">
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
		  <%
				if(TTKCommon.isAuthorized(request,"Edit"))
				{
		  %>

				<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onFormSubmit();"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
		  <%
		  		}//end of checking for Edit permission
		  %>
		  <logic:notEmpty name="frmEditHospValidation" property="validationSeqId">
		  <%

		        if(TTKCommon.isAuthorized(request,"Delete"))
				{
	     %>
			      <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>&nbsp;
         <%
			   	}//end of if(TTKCommon.isAuthorized(request,"Delete"))

	     %>
	     </logic:notEmpty>
	     <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCancel();"><u>C</u>lose</button>
		</td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE='<%= TTKCommon.checkNull(request.getParameter("rownum"))%>'>
	<html:hidden property="validationSeqId"/>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="Validation Details">
	<html:hidden property="caption"/>
  	</html:form>
	<!-- E N D : Content/Form Area -->