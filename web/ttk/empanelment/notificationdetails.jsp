<%
/** @ (#) notificationdetails.jsp 15th May 2008
 * Project     : TTK Healthcare Services
 * File        : notificationdetails.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 15th May 2008
 *
 * @author 		 : Chandrasekaran J
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
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/empanelment/notificationdetails.js"></SCRIPT>
<%
	boolean viewmode=true;
 	
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>

	<!-- S T A R T : Content/Form Area -->
	<html:form action="/NotificationDetailsAction.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>Notification Details - <bean:write name="frmNotifiDetails" property="caption"/></td>
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
	<legend>Notification Details</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="13%" align="center">&nbsp;</td>
        <td width="87%">
        	<TABLE WIDTH="75%" BORDER="0" CELLSPACING="0" CELLPADDING="0">
	          	<TR>
	            	<TD colspan="3" height="2"></TD>
	          	</TR>
	          	<TR>
	            	<TD WIDTH="42%" ALIGN="left" CLASS="formLabelBold">List of All Notifications:</TD>
	            	<TD WIDTH="14%" align="left" VALIGN="TOP" CLASS="formLabelBold">&nbsp;</TD>
	            	<TD WIDTH="44%" align="left" VALIGN="TOP" CLASS="formLabelBold">Associated Notifications: <span class="mandatorySymbol">*</span></TD>
	          	</TR>
	          	<TR>
	            	<TD height="5" colspan="3" ALIGN="left"></TD>
	          	</TR>
	          	<TR>
	          		<TD WIDTH="42%" ALIGN="left" VALIGN="TOP">
	          			<html:select  property="unAssociateNotifyList"  size="7" multiple="true" styleClass="generaltext selectBoxLargest" onfocus="doSelect(document.forms[1].selectedNotifyList)" disabled="<%=viewmode%>">
	          				<html:optionsCollection  name="frmNotifiDetails" property="unAssociateNotifyList" value="msgID" label="msgName"/>
	            		</html:select>
	            	</TD>
		            <TD WIDTH="14%" VALIGN="middle" align="center" style="padding-left:20px;padding-right:20px;">
		       		<% 
		       		   if(TTKCommon.isAuthorized(request,"Edit"))
					   {
					%>
		 					<html:button property="button"  onclick="move(document.forms[1].unAssociateNotifyList,document.forms[1].selectedNotifyList);" styleClass="buttons" disabled="<%=viewmode%>">&nbsp;>&nbsp;</html:button><br>
							<br><html:button property="button" onclick="moveall(document.forms[1].unAssociateNotifyList,document.forms[1].selectedNotifyList);" styleClass="buttons" disabled="<%=viewmode%>">>></html:button><br><br>
							<html:button property="button" onclick="move(document.forms[1].selectedNotifyList,document.forms[1].unAssociateNotifyList);" styleClass="buttons" disabled="<%=viewmode%>">&nbsp;<&nbsp;</html:button><br>
							<br><html:button property="button" onclick="moveall(document.forms[1].selectedNotifyList,document.forms[1].unAssociateNotifyList);" styleClass="buttons" disabled="<%=viewmode%>"><<</html:button>
					<%} 
					%>
					</TD>
	          		<TD WIDTH="44%" VALIGN="TOP">
		            	<html:select  property="selectedNotifyList" size="7" multiple="true" styleClass="generaltext selectBoxLargest" onfocus="doSelect(document.forms[1].unAssociateNotifyList)" disabled="<%=viewmode%>">
		            		<html:optionsCollection name="frmNotifiDetails" property="associateNotifyList" value="msgID" label="msgName"/>
		 				</html:select>
	            	</TD>
	          </TR>
          	</TABLE>
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
	<!-- E N D : Buttons and Page Counter -->
	
	<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
	<html:hidden property="caption"/>
	<INPUT TYPE="hidden" NAME="workflowName" value="<bean:write name="frmNotifiDetails" property="workflowName"/>"/>
	
</html:form>