<%
/**
 * @ (#) editcircular.jsp 9th Nov 2005
 * Project      : TTK HealthCare Services
 * File         : editcircular.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 9th Nov 2005
 *
 * @author       :Chandrasekaran J
 * Modified by   : Lancy A
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/editcircular.js"></script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
%>
	<!-- S T A R T : Content/Form Area -->
	<html:form action="/EditCircularAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
    	<td width="90%"><bean:write name="frmEditCircular" property="caption"/></td>
    	<td align="right" class="webBoard">
    		<a href="#"><img src="/ttk/images/DocViewIcon.gif" alt="Launch Document Viewer" width="16" height="16" border="0" align="absmiddle"></a>
    	</td>
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
	<fieldset><legend>General</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="15%" class="formLabel">Circular No.: <span class="mandatorySymbol">*</span></td>
	    <td width="85%">
	    	<html:text property="circularNumber" styleClass="textBox textBoxSmall" maxlength="50" disabled="<%= viewmode %>"/>
	    </td>
	  </tr>
	  <tr>
    	<td class="formLabel">Issued Date: <span class="mandatorySymbol">*</span></td>
    	<td>
    		<html:text property="issueDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>"/><A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','forms[1].issueDate',document.forms[1].issueDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
    	</td>
  	  </tr>
	  <tr>
	    <td class="formLabel">Received Date: <span class="mandatorySymbol">*</span></td>
	    <td>
	    	<html:text property="receiveDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>"/><A NAME="calEndDate" ID="calEndDate" HREF="#" onClick="javascript:show_calendar('calEndDate','forms[1].receiveDate',document.forms[1].receiveDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
	    </td>
	  </tr>
  	  <tr>
    	<td class="formLabel">Title: <span class="mandatorySymbol">*</span></td>
    	<td>
    		<html:text property="circlarTitle" styleClass="textBox textBoxLarge" maxlength="250" disabled="<%= viewmode %>"/>
    	</td>
     </tr>
  	 <tr>
    	<td class="formLabel">Description:</td>
    	<td>
    		<html:textarea property="circlarDesc" styleClass="textBox textAreaLong" disabled="<%= viewmode %>" />
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
  <input type="hidden" name="child" value="EditCirculars"> 
  <input type="hidden" name="systemDate" value="<%=TTKCommon.getFormattedDate(TTKCommon.getDate())%>">
  <INPUT TYPE="hidden" NAME="circularSeqId" VALUE="<bean:write name="frmEditCircular" property="circularSeqId" />">
  <INPUT TYPE="hidden" NAME="rownum" VALUE='<%= TTKCommon.checkNull(request.getParameter("rownum"))%>'>
  <INPUT TYPE="hidden" NAME="mode" VALUE="">
  </div>
  </html:form>
