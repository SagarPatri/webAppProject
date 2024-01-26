<%
/** @ (#) pcsdetail.jsp Aug 25th, 2008
 * Project     : TTK Healthcare Services
 * File        : pcsdetail.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: Aug 25th, 2008
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
<script language="javascript" src="/ttk/scripts/maintenance/pcsdetail.js"></script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
%>
<html:form action="/EditPCSListAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>
			Procedure Code -<bean:write name="frmAddPCSCode" property="caption"/>
			</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
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
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<fieldset>
		<legend>Procedure Code Information</legend>
			<table border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
				<tr>
					<td width="25%" class="formLabel">Procedure Code: <span class="mandatorySymbol">*</span></td>
					<td width="65%">
					<logic:empty name="frmAddPCSCode" property="procedureID">
						<html:text property="procedureCode" maxlength="60" styleClass="textBox textBoxSmall" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);"  disabled="<%= viewmode %>"  />
					</logic:empty>	
					<logic:notEmpty name="frmAddPCSCode" property="procedureID">
						<html:text property="procedureCode" maxlength="60" styleClass="textBox textBoxSmallDisabled" readonly="true"  disabled="<%= viewmode %>"  />
					</logic:notEmpty>
					</td>
				</tr>
				<tr>
					<td class="formLabel">Procedure Description: <span class="mandatorySymbol">*</span></td>
					<td>
						<html:textarea property="procedureDescription" styleClass="textBox textAreaLong" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" disabled="<%= viewmode %>" />
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">Short  Description:</td>
					<td>
						<html:textarea property="shortDesc" styleClass="textBox textAreaLong" disabled="<%= viewmode %>" />
					</td>
				</tr>
				<tr>
					<td nowrap class="formLabel">Master Procedure Code:</td>
					<td>
        				<html:text property="masterProcCode" maxlength="60" styleClass="textBox textBoxSmallDisabled" disabled="<%=viewmode%>" readonly="true"/>&nbsp;
		           		<a href="#" onclick="onSelectPCS()" size="10"><img src="/ttk/images/ICDIcon.gif" alt="Select Master Procedure List" width="16" height="16" border="0" align="absmiddle"></a>
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
	<input type="hidden" name="mode" value="" />
	<INPUT TYPE="hidden" NAME="rownum" VALUE='<%= TTKCommon.checkNull(request.getParameter("rownum"))%>'>
	</div>
	<logic:notEmpty name="frmAddPCSCode" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>	
</html:form>