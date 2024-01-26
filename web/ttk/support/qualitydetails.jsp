<%
/**
 * @ (#) qualitydetails.jsp 10th May 2006
 * Project      : TTK HealthCare Services
 * File         : qualitydetails.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 10th May 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/support/qualitydetails.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	pageContext.setAttribute("QualityStatusID",Cache.getCacheObject("qualityStatus"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/QualityDetailsAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td width="51%">Quality Details</td>
    	<td width="49%" align="right" class="webBoard">&nbsp;</td>
	</tr>
</table>
<!-- E N D : Page Title -->

<!-- S T A R T : Form Fields -->
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

	<fieldset>
    <legend>General</legend>
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="18%"  class="formLabel indentedLabels">Type:</td>
        <td width="35%" class="textLabelBold"><bean:write name="frmQtyDetails" property="typeDesc"/></td>
        <td width="25%" class="formLabel" >Cashless / Claim No.:</td>
        <td width="22%" class="textLabelBold"><bean:write name="frmQtyDetails" property="preAuthClaimNo"/></td>
      </tr>
      <tr>
        <td  nowrap class="formLabel indentedLabels">Marked Date / Time:</td>
        <td  nowrap class="textLabel"><bean:write name="frmQtyDetails" property="documentDate"/></td>
        <td  nowrap class="formLabel">Vidal Health Branch:</td>
        <td  class="textLabel"><bean:write name="frmQtyDetails" property="officeName"/></td>
      </tr>
    </table>
	</fieldset>

	<fieldset>
    <legend>QC Details</legend>
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="18%" class="formLabel indentedLabels">Date: <span class="mandatorySymbol">*</span></td>
        <td width="35%" class="textLabel">
	        <html:text property="completedDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
			<A NAME="calDate" ID="calDate" HREF="#" onClick="javascript:show_calendar('calDate','frmQtyDetails.completedDate',document.frmQtyDetails.completedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
        </td>
        <td width="25%"  class="formLabel ">Status: <span class="mandatorySymbol">*</span></td>
        <td width="22%"  class="textLabel">
			<html:select property="statusTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
				<html:option value="">Select from list</html:option>
				<html:options collection="QualityStatusID"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select>
          </td>
      </tr>
      <tr>
	  	<td nowrap class="formLabel indentedLabels">Remarks:</td>
        <td colspan="3" nowrap class="formLabel">
      		<html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
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
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Reset();"><u>R</u>eset</button>&nbsp;
			    <%
				    }// end of if(TTKCommon.isAuthorized(request,"Edit"))
				%>
				    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Close();"><u>C</u>lose</button>
			</td>
		</tr>
	</table>
<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
<INPUT TYPE="hidden" NAME="tab" VALUE=""/>
<html:hidden property="seqID"/>
<html:hidden property="typeDesc"/>
<html:hidden property="preAuthClaimNo"/>
<html:hidden property="documentDate"/>
<html:hidden property="officeName"/>
</html:form>
<!-- E N D : Content/Form Area -->