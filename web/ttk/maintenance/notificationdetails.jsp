<%
/** @ (#) notificationdetails.jsp July 28th, 2008
 * Project     : TTK Healthcare Services
 * File        : notificationdetails.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: July 28th, 2008
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ page import="com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/maintenance/notificationdetails.js"></script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
%>
<html:form action="/EditNotificationAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>Notification - <bean:write name="frmNotifyDetails" property="caption"/>&nbsp;&nbsp;
			&nbsp;&nbsp;
			<logic:equal name="frmNotifyDetails" property="custConfigAllowed" value="Y">
			<a href="#" onClick="onConfigurationInfo()"><img src="/ttk/images/EditIcon.gif" alt="Configuration Information" width="16" height="16" border="0" align="absmiddle"></a>
			</logic:equal>
			</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
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
	<!-- S T A R T : Form Fields -->
	<fieldset>
		<legend>Notification Information</legend>
			<table border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
				<tr>
					<td width="15%" nowrap class="formLabel">Notification ID:</td>
					<td width="85%" class="textLabel">
						<strong><bean:write name="frmNotifyDetails" property="msgName"/></strong>
					</td>
				</tr>
				<tr>
					<td width="15%" nowrap class="formLabel">Notification Category:</td>
					<td width="85%" class="textLabel">
						<strong><bean:write name="frmNotifyDetails" property="notifCategory"/></strong>
					</td>
				</tr>
				<tr>
					<td width="15%" nowrap class="formLabel">Notification  Description:</td>
					<td width="85%" class="textLabel">
						<strong><bean:write name="frmNotifyDetails" property="notificationDesc"/></strong>
					</td>
				</tr>
				<logic:equal name="frmNotifyDetails" property="sendEmail" value="Y">
					<tr>
						<td width="15%" nowrap class="formLabel">Email Title:</td>
						<td width="85%">
							<html:textarea property="msgTitle" styleClass="textBox textAreaLong" style="height:50px;" disabled="<%= viewmode %>" />
						</td>
					</tr>
					<tr>
						<td width="15%" nowrap class="formLabel">Email Content:</td>
						<td width="85%">
							<html:textarea property="emailDesc" styleId="emailDesc" styleClass="textBox textAreaLong" style="height:50px;" disabled="<%= viewmode %>" />
						</td>
					</tr>
				</logic:equal>
				<logic:equal name="frmNotifyDetails" property="sendSms" value="Y">
					<tr>
						<td width="15%" nowrap class="formLabel">SMS Content:</td>
						<td width="85%">
							<html:textarea property="SMSDesc" styleClass="textBox textAreaLong" style="height:50px;" disabled="<%= viewmode %>" />
						</td>
					</tr>
				</logic:equal>
				<tr>
					<td width="15%" nowrap class="formLabel">Applicable Insurance Company:</td>
					<td width="85%">
						<html:hidden property="applicableInsCompany" styleClass="textBox textAreaLong" style="height:50px;" styleId="applicableInsCompany"/>
						<a href="#" onClick="openInsList('applicableInsCompany','ApplicableInsCompany')" style="display: inline;" id="anetworkType"><img src="/ttk/images/EditIcon.gif" alt="Select Insurance company" width="16" height="16" border="0" align="absmiddle"></a>
					</td>
				</tr>
				<logic:equal name="frmNotifyDetails" property="sendEmail" value="Y">
					<logic:equal name="frmNotifyDetails" property="custConfigAllowed" value="N">
						<tr>
							<td width="15%" nowrap class="formLabel">Primary/Level1 Email ID:</td>
							<td width="85%">
								<html:textarea property="primaryMailID" styleClass="textBox textAreaLong" disabled="<%= viewmode %>" />
							</td>
						</tr>
						<tr>
							<td width="15%" nowrap class="formLabel">Secondary Email ID:</td>
							<td width="85%">
								<html:textarea property="secondaryMailID" styleClass="textBox textAreaLong" disabled="<%= viewmode %>" />
							</td>
						</tr>
					</logic:equal>
				</logic:equal>
				<logic:equal name="frmNotifyDetails" property="configParam" value="Y">
					<tr>
						<td nowrap width="15%" class="formLabel">Config Param1:</td>
						<td width="85%">
							<html:text property="configParam1" maxlength="9" styleClass="textBox textBoxMedium" disabled="<%= viewmode %>"/>
						</td>
					</tr>
					<logic:equal name="frmNotifyDetails" property="showMultiLevelEmail" value="Y">
						<tr>
							<td width="15%" nowrap class="formLabel">Level2 Email ID:</td>
							<td width="85%">
								<html:textarea property="level2EmailID" styleClass="textBox textAreaLong" disabled="<%= viewmode %>" />
							</td>
						</tr>
					</logic:equal>
					<tr>
						<td width="15%" nowrap class="formLabel">Config Param2:</td>
						<td width="85%">
							<html:text property="configParam2" maxlength="9" styleClass="textBox textBoxMedium" disabled="<%= viewmode %>"/>
						</td>
					</tr>
					<logic:equal name="frmNotifyDetails" property="showMultiLevelEmail" value="Y">
						<tr>
							<td width="15%" nowrap class="formLabel">Level3 Email ID:</td>
							<td width="85%">
								<html:textarea property="level3EmailID" styleClass="textBox textAreaLong" disabled="<%= viewmode %>" />
							</td>
						</tr>
					</logic:equal>
					<tr>
						<td width="15%" nowrap class="formLabel">Config Param3:</td>
						<td width="85%">
							<html:text property="configParam3" maxlength="9" styleClass="textBox textBoxMedium" disabled="<%= viewmode %>"/>
						</td>
					</tr>
				</logic:equal>
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
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" id="notiSaveBtnID" onClick="javascript:onSubmit();"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	      <%
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		 %>
	      <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
     	  <button type="button" name="Button" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onPreview('<bean:write name="frmNotifyDetails" property="msgName"/>');"><u>P</u>review</button>
      </td>
    </tr>
  </table>
  <!-- E N D :  Buttons -->
  </div>
	<input type="hidden" name="mode" value="" />
	<INPUT TYPE="hidden" NAME="rownum" VALUE='<%= TTKCommon.checkNull(request.getParameter("rownum"))%>'>
	<INPUT TYPE="hidden" NAME="Entry" VALUE="">
	 <input type="hidden" name="emailDesc"  id="emailDesc" value='<%= request.getSession().getAttribute("emailDesc")%> '>
</html:form>