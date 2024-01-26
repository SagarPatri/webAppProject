<%
/** @ (#) definerule.jsp 4th Jul 2006
 * Project     : TTK Healthcare Services
 * File        : definerule.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 4th Jul 2006
 *
 * @author 	   : Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.TTKCommon" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%
	pageContext.setAttribute("ActiveSubLink",TTKCommon.getActiveSubLink(request));
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
		viewmode=false;
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>

<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/administration/definerule.js"></script>
<style>
<!--
.escapeNum{
margin-left: -24px;
}
-->
</style>
<SCRIPT type="text/javascript">
	var JS_Focus_Disabled =true;
</SCRIPT>

<!-- S T A R T : Content/Form Area -->
	<html:form action="/EditRuleAction.do">
	<!-- S T A R T : Page Title -->
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td >List of all Applicable Clauses - <bean:write name="frmDefineRules" property="caption"/></td>
		<td align="right" class="webBoard">&nbsp;
			<logic:match name="ActiveSubLink" value="Policies">
				<%@ include file="/ttk/common/toolbar.jsp" %>
			</logic:match>
		</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentAreaHScroll" id="contentArea">
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
	<table align="right" border="0" cellspacing="0" cellpadding="3">
	    <tr><td>
	    	<img src="/ttk/images/ConfigureIcon.gif" width="16" height="16" /> - Some conditions will be defined at the Family or Member level
	        </td>
	    </tr>
    </table>
	<br/>

	<logic:match name="ActiveSubLink" value="Products">
	<fieldset>
	<legend>Rule Date</legend>
		<table align="center" class="formContainer" cellpadding="0" cellspacing="0" border="0" width="100%">
		<tr>
		<logic:empty name="frmDefineRules" property="prodPolicyRuleSeqID">
			<td width="20%" class="formLabel">Rule Valid From <span class="mandatorySymbol">*</span></td>
			<td width="30%">
				<html:text property="fromDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>"/>
				<logic:match name="viewmode" value="false">
					<A NAME="CalendarObjectFrmDate" ID="CalendarObjectFrmDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectFrmDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
				</logic:match>
			</td>
		</logic:empty>
		<logic:notEmpty name="frmDefineRules" property="prodPolicyRuleSeqID">
			<td width="20%" class="formLabel">Rule Valid From </td>
			<td width="30%" class="textLabelBold">
				<bean:write name="frmDefineRules" property="fromDate"/>
			</td>
			<html:hidden name="frmDefineRules" property="fromDate"/>
		</logic:notEmpty>
		<td>&nbsp;</td>
	    <td>&nbsp;</td>
		</tr>
		</table>
	</fieldset>
	</logic:match>
	<table cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td valign="top" >
			<ttk:ClauseDetails flow="ProdPolicyRule"/>
		</td>
	</tr>
	</table>

    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
		    <logic:match name="viewmode" value="false">
				<button type="button" name="Button1" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button2" accesskey="f" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReconfigure();">Recon<u>f</u>igure</button>&nbsp;
				<logic:match name="ActiveSubLink" value="Products">
					<logic:notEmpty name="frmDefineRules" property="prodPolicyRuleSeqID">
						<logic:empty name="frmDefineRules" property="frmChanged">
							<button type="button" name="Button3" accesskey="i" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReviseRule();">Rev<u>i</u>se</button>&nbsp;
						</logic:empty>
					</logic:notEmpty>
		    	</logic:match>
			</logic:match>
			<logic:match name="ActiveSubLink" value="Products">
		    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		    </logic:match>
	    </td>
	  </tr>
	</table>
	<br/><br/>
	<table align="right" border="0" cellspacing="0" cellpadding="3">
	    <tr><td>
	    	<img src="/ttk/images/ConfigureIcon.gif" width="16" height="16" /> - Some conditions will be defined at the Family or Member level
	        </td>
	    </tr>
    </table>
	<br/>

	<!-- E N D : Buttons -->
	</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	
	<logic:match name="ActiveSubLink" value="Products">
		<input type="hidden" name="child" value="Define Rule">
	</logic:match>
	<logic:notMatch name="ActiveSubLink" value="Products">
		<input type="hidden" name="child" value="">
	</logic:notMatch>
	
	<SCRIPT type="text/javascript">
	validateMaternity();
</SCRIPT>
	</html:form>
	<!-- E N D : Content/Form Area -->