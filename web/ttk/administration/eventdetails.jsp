<%
/** @ (#) addeventdetails.jsp 20th Dec 2005
 * Project     : TTK Healthcare Services
 * File        : eventdetails.jsp
 * Author      : Raghavendra T M
 * Company     : Span Systems Corporation
 * Date Created: 20th Dec 2005
 *
 * @author 		 : Raghavendra T M
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import=" com.ttk.common.TTKCommon" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/administration/eventdetails.js"></SCRIPT>
<%
	boolean viewmode=true;
 	boolean viewreadonly=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>

<!-- S T A R T : Content/Form Area -->
	<html:form action="/WorkflowAction.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>Event Details - [<bean:write name="frmEvent" property="caption"/>]</td>
	<td width="43%" align="right" class="webBoard">&nbsp;</td>
      </tr>
    </table>
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
	<html:errors/>
	<fieldset>
    <legend>Event Information</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="formLabel">Event Name: <span class="mandatorySymbol">*</span></td>
        <td class="textLabelBold"><bean:write name="frmEvent" property="eventName"/> </td>
        </tr>
      <tr>
        <td width="13%" class="formLabel" nowrap>Description: <span class="mandatorySymbol">*</span></td>
        <td width="87%" class="formLabel" nowrap>
        <html:textarea property="eventDesc" styleClass="textBox textAreaLong" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        </tr>
    </table>
	</fieldset>
	<fieldset>
    <legend>Review Counts</legend>
    <table class="formContainer" align="center" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="13%" class="formLabel">Simple: <span class="mandatorySymbol">*</span></td>
        <td width="20%" class="formLabel">
        <html:text property="simpleCount"  styleClass="textBox textBoxSmall" styleId="companyname3" maxlength="2" onkeypress="javascript:blockEnterkey(event.srcElement);" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
        <logic:match property='workflowName' name='frmEvent' value='display'>
	        <td width="13%" nowrap class="formLabel">Medium: <span class='mandatorySymbol'>*</span></td>
    	    <td width="20%" nowrap class="formLabel">
	       	<html:text property="mediumCount" styleClass="textBox textBoxSmall" styleId="companyname3" maxlength="2" disabled="<%=viewmode%>" /></td>
    	    <td width="13%" nowrap class="formLabel">Complex: <span class="mandatorySymbol">*</span></td>
        	<td width="21%" nowrap class="formLabel">
	        <html:text property="complexCount" styleClass="textBox textBoxSmall" styleId="companyname3" maxlength="2" disabled="<%=viewmode%>" /></td>
		</logic:match>
        <logic:notMatch property='workflowName' name='frmEvent' value='display'>
	        <td colspan=2>&nbsp;
	        <html:hidden property="mediumCount" />
    	    <html:hidden property="complexCount" />
    	    </td>
        </logic:notMatch>
      </tr>
    </table>
	</fieldset>
	<fieldset>
	<legend>Roles</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="13%" align="center">&nbsp;</td>
        <td width="87%"><TABLE WIDTH="75%" BORDER="0" CELLSPACING="0" CELLPADDING="0">
          <TR>
            <TD colspan="3" height="2"></TD>
          </TR>
          <TR>
            <TD WIDTH="42%" ALIGN="left" CLASS="formLabelBold">List of All Roles:</TD>
            <TD WIDTH="14%" align="left" VALIGN="TOP" CLASS="formLabelBold">&nbsp;</TD>
            <TD WIDTH="44%" align="left" VALIGN="TOP" CLASS="formLabelBold">Associated Roles: <span class="mandatorySymbol">*</span></TD>
          </TR>
          <TR>
            <TD height="5" colspan="3" ALIGN="left"></TD>
          </TR>
          <TR>
          	<TD WIDTH="42%" ALIGN="left" VALIGN="TOP">
          	 <html:select  property="unAssociatedRoles"  size="7" multiple="true" styleClass="generaltext selectBoxLargest" onfocus="doSelect(document.forms[1].selectedRoles)" disabled="<%=viewmode%>">
          	 <html:optionsCollection  name="frmEvent" property="unAssociatedRoles" value="roleSeqID" label="roleName"/>
            </html:select>
             </TD>
            <TD WIDTH="14%" VALIGN="middle" align="center" style="padding-left:20px;padding-right:20px;">
       <%    if(TTKCommon.isAuthorized(request,"Edit"))
	{%>
 <html:button property="button"  onclick="move(document.forms[1].unAssociatedRoles,document.forms[1].selectedRoles);" styleClass="buttons" disabled="<%=viewmode%>">&nbsp;>&nbsp;</html:button><br>
<br><html:button property="button" onclick="moveall(document.forms[1].unAssociatedRoles,document.forms[1].selectedRoles);" styleClass="buttons" disabled="<%=viewmode%>">>></html:button><br><br>
<html:button property="button" onclick="move(document.forms[1].selectedRoles,document.forms[1].unAssociatedRoles);" styleClass="buttons" disabled="<%=viewmode%>">&nbsp;<&nbsp;</html:button><br>
<br><html:button property="button" onclick="moveall(document.forms[1].selectedRoles,document.forms[1].unAssociatedRoles);" styleClass="buttons" disabled="<%=viewmode%>"><<</html:button>
<%} %>
	</TD>
          <TD WIDTH="44%" VALIGN="TOP">
            <html:select  property="selectedRoles" size="7" multiple="true" styleClass="generaltext selectBoxLargest" onfocus="doSelect(document.forms[1].unAssociatedRoles)" disabled="<%=viewmode%>">
             <html:optionsCollection name="frmEvent" property="associatedRoles" value="roleSeqID" label="roleName"/>
 			</html:select>
            </TD>
          </TR>
        </TABLE></td>
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
    	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
    <%
	}// end of if(TTKCommon.isAuthorized(request,"Edit"))
	%>
	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td>
  </tr>
</table>
	<!-- E N D : Buttons -->
		</div>
		<!-- E N D : Content/Form Area -->	</td>
  </tr>
</table>
<!-- E N D : Buttons and Page Counter -->
	<input type="hidden" name="child" value="EventDetails">
	<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
	<html:hidden property="caption"/>
	<INPUT TYPE="hidden" NAME="workflowName" value="<bean:write name="frmEvent" property="workflowName"/>"/>
	<INPUT TYPE="hidden" NAME="eventSeqID" value="<bean:write name="frmEvent" property="eventSeqID"/>"/>
	<INPUT TYPE="hidden" NAME="eventName" value="<bean:write name="frmEvent" property="eventName"/>"/>
</html:form>