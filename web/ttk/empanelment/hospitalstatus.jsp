<%
/** @ (#) hospitalstatus.jsp 27th Sep 2005
 * Project     : TTK Healthcare Services
 * File        : hospitalstatus.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 27th Sep 2005
 *
 * @author 		 : Arun K N
 * Modified by   : Raghavendra T M
 * Modified date : March 10,2006
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/status.js"></script>
<SCRIPT LANGUAGE="JavaScript">
	var JS_Focus_Disabled =true;
</SCRIPT>
<%
	boolean viewmode=true;
	String disable="disabled";
	//String drpYears[] = {"1","2","3","4","5","6","7","8","9","10"};
	if(TTKCommon.isAuthorized(request,"Edit"))
		{
			viewmode=false;
			disable="";
		}
	pageContext.setAttribute("empanelstatus",Cache.getCacheObject("empanelStatusCode"));
	pageContext.setAttribute("viewmode",new Boolean(viewmode));	
	//pageContext.setAttribute("drpYears",drpYears);
%>
	<!-- S T A R T : Content/Form Area -->
	<!--<form name="frmHospitalStatus" action="/HospitalStatusAction.do" method="post"> -->
	<html:form action="/HospitalStatusAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Status Details</td>
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
    <html:errors />

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
	<legend>Status</legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		  <tr>
	        <td width="22%" class="formLabel">Status: <span class="mandatorySymbol">*</span></td>
	        <td width="78%" colspan="3">

			<html:select property="emplStatusTypeId" styleClass="selectBox" disabled="<%=viewmode%>" onchange="javascript:onStatusChanged()" >
				<html:option value="">Select from list</html:option>
				<html:options collection="empanelstatus" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>

			<logic:notMatch name="frmHospitalStatus" property="emplStatusTypeId" value="EMP">
		        <logic:notEmpty name="alReasonInfo">
					<html:select property="subStatusCode" styleClass="selectBox" disabled="<%=viewmode%>">
						<html:options collection="alReasonInfo" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
				</logic:notEmpty>
			</logic:notMatch>
	        </td>
	      </tr>
		  <tr>
		    <td class="formLabel">Remarks:</td>
		    <td colspan="3">
		    <html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>" readonly="<%=viewmode%>"></html:textarea>
		    </td>
		    </tr>
	      <tr>
	      </table>
	  	<logic:match name="frmHospitalStatus" property="emplStatusTypeId" value="EMP">
			<table align="center" class="formContainer" id="empanelvalidity" style="" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		      <tr>
		        <td colspan="4" class="formLabelBold">Empanelment Validity</td>
		      </tr>
		      <tr>
		        <td width="22%" class="formLabel">Start Date:  <span class="mandatorySymbol">*</span></td>
		        <td width="20%">
		        <html:text property="fromDate" name="frmHospitalStatus" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
		        <td width="12%" class="formLabel">Period (yrs): </td>
                <td width="11%" class="formLabel">
                <select name="drpYears" id="drpYears" class="selectBox" onchange="javascript:changeDate()" <%=disable%>>
	                 <option value=""></option>
	                 <option value="">--</option>
	                 <option value="1">1</option>
	                 <option value="2">2</option>
	                 <option value="3">3</option>
	                 <option value="4">4</option>
	                 <option value="5">5</option>
	                 <option value="6">6</option>
	                 <option value="7">7</option>
	                 <option value="8">8</option>
	                 <option value="9">9</option>
	                 <option value="10">10</option>
                 </select>
                 </td>
                <td width="12%" class="formLabel">End Date:  <span class="mandatorySymbol">*</span></td>
		        <td width="27%">
		        <html:text property="toDate" name="frmHospitalStatus" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].toDate',document.forms[1].toDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
		      </tr>
		      <tr>
			    <td colspan="4" class="textLabel">Provider to be graded and tariffed before empanelment</td>
       	      </tr>
			  <tr>
		        <td class="formLabel"></td>
		        <td class="formLabelBold"><bean:write name="frmHospitalStatus" property="dateOfNotification"/></td>
		        <td class="formLabel">&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		    </table>
	    </logic:match>

	    <logic:match name="frmHospitalStatus" property="emplStatusTypeId" value="DIS">
		    <table align="center" class="formContainer" id="disempaneldate" style="" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		      <tr>
		        <td width="22%" class="formLabel">Dis-empanelment Date:  <span class="mandatorySymbol">*</span></td>
		        <td width="31%">
		        <html:text property="fromDate" name="frmHospitalStatus" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
		        <td width="15%" class="formLabel">&nbsp;</td>
		        <td width="32%">&nbsp;</td>
		      </tr>
		    </table>
		</logic:match>

		<logic:match name="frmHospitalStatus" property="emplStatusTypeId" value="CLS">
			<table align="center" class="formContainer" id="closedate" style="" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		      <tr>
		        <td width="22%" class="formLabel">Closed Date:  <span class="mandatorySymbol">*</span></td>
		        <td width="31%">
		        <html:text property="fromDate" name="frmHospitalStatus" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
		        <td width="15%" class="formLabel">&nbsp;</td>
		        <td width="32%">&nbsp;</td>
		      </tr>
		    </table>
	    </logic:match>
	</fieldset>
	<!-- E N D : Form Fields -->

    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    <bean:define id="emplStatusTypeID"  name="frmHospitalStatus" property="emplStatusTypeId"/>
	    <bean:define id="gradeTypeID"  name="frmHospitalStatus" property="gradeTypeId"/>
	    <%
	    	if(String.valueOf(emplStatusTypeID).equals("EMP") && String.valueOf(gradeTypeID).equals(""));
			else if(TTKCommon.isAuthorized(request,"Edit"))
			{
		%>
		   	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSaveStatus();"><u>S</u>ave</button>&nbsp;
			<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	    <%
	    	}//end of else if(TTKCommon.isAuthorized(request,"Edit"))
	    %>
	    </td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="empanelDate" VALUE="">
    <INPUT TYPE="hidden" NAME="disEmpanelDate" VALUE="">
    <INPUT TYPE="hidden" NAME="closeDate" VALUE="">
    <input type="hidden" name="empanelSeqId" value="<bean:write name="frmHospitalStatus" property="empanelSeqId"/>">
    <html:hidden property="emplStatusTypeId" styleId="HidEmplStatusTypeId"/>
    <html:hidden property="tpaRegdDate"/>
    <logic:notEmpty name="frmHospitalStatus" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
	</html:form>
	<!-- E N D : Content/Form Area -->