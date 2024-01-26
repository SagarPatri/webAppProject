<%
/** @ (#) mohdefinerules.jsp 4th Jul 2006
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
<script type="text/javascript" src="/ttk/scripts/administration/benefitList.js"></script>
<SCRIPT type="text/javascript">
	var JS_Focus_Disabled =true;
</SCRIPT>

<!-- S T A R T : Content/Form Area gv List of all Applicable Clauses -gv-->
	<html:form action="/MOHRuleAction.do" >
	<!-- S T A R T : Page Title -->
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td >List of all  - <bean:write name="frmDefineRules" property="caption"/></td>
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
	 <logic:notEmpty name="displayMsg" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
					  <bean:write name="displayMsg" scope="request"/>
				  </td>
				</tr>
			</table>
	</logic:notEmpty>
 	<!-- E N D : Success Box -->

	<!-- S T A R T : Form Fields -->
	

	
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
			<td width="20%" class="formLabel">Rule Valid From</td>
			<td width="30%" class="textLabelBold">
				<bean:write name="frmDefineRules" property="fromDate"/>
			</td>
			<html:hidden name="frmDefineRules" property="fromDate"/>
		</logic:notEmpty>
		
		</tr>
		</table>
			<ttk:RuleBenefitsDisplay/>
		   <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
		    <logic:notEmpty name="frmDefineRules" property="prodPolicyRuleSeqID">
		       <%if(TTKCommon.isAuthorized(request, "SaveAndCompleteMOHRule")){ %>
				<button type="button" id="saveCompleteBtnID" name="Button1" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:saveAndComlete();"><u>S</u>aveAndComplete</button>&nbsp;
			  <%} %>
			</logic:notEmpty>
			 <logic:equal name="frmDefineRules" property="benefitRuleCompletedYN" value="Y">
			  <%if(TTKCommon.isAuthorized(request, "OverrideMOHRule")){ %>
				<button type="button" id="overideBtnID" name="Button1" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:override();"><u>O</u>verride</button>&nbsp;
			 <%} %>
			</logic:equal>
			
		    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		        
		              <%if(TTKCommon.getActiveSubLink(request).equals("Policies")){ %>
				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:hmoPolicyReport();">
				<div id="genReportId">GENERATE EXCEL REPORT</div>				
				</button>
			 <%} %>
	    </td>
	  </tr>
	</table>
	</fieldset>
	

	<table  cellpadding="0" cellspacing="0" border="0" width="100%">
	<tr>
		<td valign="top" >
			
		</td>
	</tr>
	</table>


	<!-- E N D : Buttons -->
	</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	
	<input type="hidden" name="child" value="">
	<input type="hidden" name="benefitID"/>
	<input type="hidden" name="confType"/>
	<input type="hidden" name="currentSubLink" value="<%=TTKCommon.getActiveSubLink(request)%>"/>
	
	<input type="hidden" name="bnfRuleCompltYN" value="<bean:write name="frmDefineRules" property="benefitRuleCompletedYN"/>"/>
	<script type="text/javascript">  
 document.forms[1].setAttribute( "autocomplete", "off" );      
  </script>	
  <logic:equal value="Y" name="GenRepYN" scope="request">
  <script type="text/javascript">  
           generateReport();    
  </script>
  </logic:equal>
	</html:form>
	
	
	<!-- E N D : Content/Form Area -->