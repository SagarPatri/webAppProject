<%
/**
 * @ (#) claimshortfalldaysconfig.jsp Dec 05, 2012
 * Project      : TTK HealthCare Services
 * File         : claimshortfalldaysconfig.jsp
 * Author       : Manohar
 * Company      : RCS
 * Date Created : Dec 05, 2012
 *
 * @author       : Manohar
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/claimshortfalldaysconfig.js"></script>


<!-- S T A R T : Content/Form Area -->
<html:form action="/ShortfallDetailsConfigurationAction.do" method="post">
	<!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td>Shortfall Days Details - <bean:write name="frmClaimShortfallDaysConfig" property="caption"/></td>
      	<td align="right"></td>
        <td align="right" ></td>
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
	<fieldset>
	<legend>Shortfall Days Configuration</legend>
	<table class="formContainer" border="0" cellspacing="0" align="center" cellpadding="0">
		<%-- Added newly by satya  1179--%>
	<tr>
	<td width="20%" nowrap class="formLabel">Intimation</td>
	<td width="5%"> <html:text property="intimationReqDays" styleClass="textBox textBoxVerySmall" maxlength="2"/> </td>
	<td width="65%" class="formLabel">days.</td>
	</tr>
	
	<%-- Added newly by satya --%>
	<tr>
	<td width="20%" nowrap class="formLabel">Claim Documents Submission</td>
	<td width="5%"> <html:text property="claimSubmissionDays" styleClass="textBox textBoxVerySmall" maxlength="2"/> </td>
	<td width="65%" class="formLabel">days.</td>
	</tr>
	<%-- shortfall phase1 --%>
	<tr>
	<td width="20%" nowrap class="formLabel">Post Hospitalization Claim Submission</td>
	<td width="5%"> <html:text property="postHospitalizationDays" styleClass="textBox textBoxVerySmall" maxlength="2"/> </td>
	<td width="65%" class="formLabel">days.</td>
	</tr>
	<%-- shortfall phase1 --%>
	<tr>
	<td width="30%"  nowrap class="formLabel">Remainder </td>
	<td width="5%"> <html:text property="remainderReqDays" styleClass="textBox textBoxVerySmall" maxlength="2"/> </td>
	<td width="65%" class="formLabel">days after shortfall letter.</td>
	</tr>
    <tr>
	<td width="30%"  nowrap class="formLabel">Closure Notice </td>
	<td width="5%"><html:text property="closureNoticeDays" styleClass="textBox textBoxVerySmall" maxlength="2"/></td>
	<td width="65%" class="formLabel">days after reminder letter.</td>
    </tr>
    <tr>
	<td width="30%"  nowrap class="formLabel">Approval for Claim Closure </td>
	<td width="5%"><html:text property="apprClosureNoticeDays" styleClass="textBox textBoxVerySmall" maxlength="2"/> </td>
	<td width="65%" class="formLabel">days after closure notice.</td>
	</tr>
    <tr>
	<td width="30%"  nowrap class="formLabel">Claim Closure </td>
	<td width="5%"> <html:text property="claimClosureDays" styleClass="textBox textBoxVerySmall" maxlength="2"/> </td>
	<td width="65%" class="formLabel">days after approval for claim closure.</td>
	</tr>
	<tr>
	<td width="30%"  nowrap class="formLabel">Regret </td>
	<td width="5%"> <html:text property="regretLetterDays" styleClass="textBox textBoxVerySmall" maxlength="2"/></td>
	<td width="65%" class="formLabel">days after rejection for claim re-open.</td>
	</tr>
    </table>
	</fieldset>

  <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">  	
	    <%
	       if(TTKCommon.isAuthorized(request,"Edit"))
	       {
    	%>    
	       	<button type="button" name="Button"  accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
	       	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	    <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		%>
	       	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		</td>
	  </tr>
	</table>

	<!-- E N D : Buttons -->
<!-- E N D : Form Fields -->
<input type="hidden" name="mode">
<html:hidden property="shortfallConfigSeqID"/>
<html:hidden property="caption" />
</div>
</html:form>