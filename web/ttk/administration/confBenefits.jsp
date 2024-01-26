<%
/** @ (#) confBenefits.jsp 4th Jul 2018
 * Project     : TTK Healthcare Services
 * File        : confBenefits.jsp
 * Author      : Nagababu K
 * Company     :Vidalhealthcare
 * Date Created: 4th Jul 2018
 *
 * @author 	   : Nagababu K
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
<script type="text/javascript" src="/ttk/scripts/administration/confBenefits.js"></script>
<SCRIPT type="text/javascript">
	var JS_Focus_Disabled =true;

</SCRIPT>
<style>
li.nostyle {
    list-style-type: none;
}
.SBMoreMedium{
height: 18px;
width: 160px;
color: green;
font-size: 12px;
}
</style>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/MOHRuleAction.do" method="POST">
	<!-- S T A R T : Page Title -->
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td  >List of all Applicable Clauses - <bean:write name="frmDefineRules" property="caption"/></td>
		<td align="right" class="webBoard">
		<ttk:MOHBenefitsWebBoards/>		
		</td>
		<td align="right" class="webBoard">		
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
	
	<br/>	
	<ttk:RuleBenefitsConf/>
		   <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
		       <logic:notEqual value="V" parameter="confType">
		       <logic:equal value="C" parameter="confType">
		       <%if(TTKCommon.isAuthorized(request, "SaveBenefit") ){%>
				<button type="button" id="oprsBtnID" name="Button1" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:addBenefit('A');"><u>A</u>dd</button>&nbsp;
			<%} %>
			  </logic:equal>
			  <logic:equal value="R" parameter="confType">
			   <%if(TTKCommon.isAuthorized(request, "SaveBenefit") ){%>
				<button type="button" id="oprsBtnID" name="Button1" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:addBenefit('U');"><u>U</u>pdate</button>&nbsp;
			 <%} %>
			  </logic:equal>
			  </logic:notEqual>
		   <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>
	
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
	<input type="hidden" name="confType" value="${param.confType}">
	
	<script type="text/javascript">  
 document.forms[1].setAttribute( "autocomplete", "off" );      
  </script>	
  <html:hidden name="frmDefineRules" property="fromDate"/>
  <input type="hidden" name="bnfRuleCompltYN" value="<bean:write name="frmDefineRules" property="benefitRuleCompletedYN"/>"/>
	</html:form>
<script type="text/javascript">
	onLoadUtility();
	
</script>
	<!-- E N D : Content/Form Area -->