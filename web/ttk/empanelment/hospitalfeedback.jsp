<%
/**
 * @ (#) hospitalfeedback.jsp 20th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : hospitalfeedback.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 20th Sep 2005
 *
 * @author       :Chandrasekaran J
 * Modified by   :Kishor kumar S H
 * Modified date : 01-01-2015
 * Reason        :Adding status related info for intX
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/empanelment/feedback.js"></script>
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
 <html:form action="/HospitalFeedbackAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td> Status and Feedback Details</td>
	     <td  align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	
	 <html:errors />
	 <!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
			  		<td>
			  			<img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:message name="updated" scope="request"/>
			  		</td>
				</tr>
			</table>
		</logic:notEmpty>
		<!-- E N D : Success Box -->
	 
	<!-- S T A R T : Search Box -->
	
	
	
	<!-- intx -->
	
	<fieldset>
	<legend>Status</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		  <tr>
	        <td width="22%" class="formLabel">Status: <span class="mandatorySymbol">*</span></td>
	        <td width="78%" colspan="3">

			<html:select property="emplStatusTypeId" styleClass="selectBox selectBoxLarge" disabled="<%=viewmode%>" onchange="javascript:onStatusChanged()" >
				<html:option value="">Select from list</html:option>
				<html:options collection="empanelstatus" property="cacheId" labelProperty="cacheDesc"/>
			</html:select>

			<logic:notMatch name="frmSearchFeedback" property="emplStatusTypeId" value="EMP">
		        <logic:notEmpty name="alReasonInfo">
					<html:select property="subStatusCode" styleClass="selectBox" disabled="<%=viewmode%>">
						<html:options collection="alReasonInfo" property="cacheId" labelProperty="cacheDesc"/>
					</html:select>
				</logic:notEmpty>
			</logic:notMatch>
	        </td>
	      </tr>
		  <tr>
		    <td class="formLabel">Remarks:  <span class="mandatorySymbol">*</span></td>
		    <td colspan="3">
		    <html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>" readonly="<%=viewmode%>"></html:textarea>
		    </td>
		    </tr>
	      <tr>
	      </table>
	  	<logic:match name="frmSearchFeedback" property="emplStatusTypeId" value="EMP">
			<table align="center" class="formContainer" id="empanelvalidity" style="" border="0" cellspacing="0" cellpadding="0">
		      
		      
		      <!-- tr><td width="22%">Stop Cashless / Claims</td>
		      	  <td width="11%" class="formLabel">
                <select name="stopCashless" id="stopCashless" class="selectBox" onchange="javascript:changeCashless(this)">
	                 <option value="-">--Select--</option>
	                 <option value="P">Cashless</option>
	                 <option value="C">Claim</option>
	                 <option value="B">Both</option>
                 </select>
                 </td>
                 
                 
                 <td colspan="2" id="stopCash" style="display:none">Stop Date :
		        <html:text property="stopDate" name="frmSearchFeedback" styleClass="textBox textDate" styleId="companyname232" maxlength="10"/><A NAME="CalendarObjectstopDate" ID="CalendarObjectstopDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectstopDate','forms[1].stopDate',document.forms[1].stopDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="stopDate" width="24" height="17" border="0" align="absmiddle"></a>
		        </td>
		        
		      </tr>
		      <tr id="stopCashRemarks" style="display:none">
		      <td>Reason : </td>
		      <td colspan="3">  
		      <html:textarea property="stopremarks" styleClass="textBox textAreaLong"></html:textarea>
		      </td>
		      </tr-->
		      
		      <tr>
		        <td colspan="4" class="formLabelBold">Empanelment Validity</td>
		      </tr>
		      <tr>
		        <td width="22%" class="formLabel">Start Date:  <span class="mandatorySymbol">*</span></td>
		        <td width="20%">
		        <html:text property="fromDate" name="frmSearchFeedback" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
		        <td width="12%" class="formLabel">Period (yrs): </td>
                <td width="11%" class="formLabel">
                <%-- <select name="drpYears" id="drpYears" class="selectBox" onchange="javascript:changeDate()" <%=disable%>> --%>
                     <select name="drpYears" id="drpYears" class="selectBox" onchange="javascript:changeDate()">
	                 <option value="">Any</option>
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
                 
                <td width="12%" class="formLabel">End Date:</td>
		        <td width="27%">
		        <html:text property="toDate" name="frmSearchFeedback" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"  onblur="endDateValidation();"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].toDate',document.forms[1].toDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
		      </tr>
		      <tr>
			    <td colspan="4" class="textLabel">Please complete Contacts, Accounts and Grading sections before Empanelment</td>
       	      </tr>
			  <tr>
		        <td class="formLabel"></td>
		        <td class="formLabelBold"><bean:write name="frmSearchFeedback" property="dateOfNotification"/></td>
		        <td class="formLabel">&nbsp;</td>
		        <td>&nbsp;</td>
		      </tr>
		    </table>
	    </logic:match>

	    <logic:match name="frmSearchFeedback" property="emplStatusTypeId" value="DFC">
		    <table align="center" class="formContainer" id="disempaneldate" style="" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		      <tr>
		        <td width="22%" class="formLabel">Dis-empanelment Date:  <span class="mandatorySymbol">*</span></td>
		        <td width="31%">
		        <html:text property="fromDate" name="frmSearchFeedback" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
		        <td width="15%" class="formLabel">&nbsp;</td>
		        <td width="32%">&nbsp;</td>
		      </tr>
		    </table>
		</logic:match>
		
		<logic:match name="frmSearchFeedback" property="emplStatusTypeId" value="DEM">
		    <table align="center" class="formContainer" id="disempaneldate" style="" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		      <tr>
		        <td width="22%" class="formLabel">Dis-empanelment Date:  <span class="mandatorySymbol">*</span></td>
		        <td width="31%">
		        <html:text property="fromDate" name="frmSearchFeedback" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
		        <td width="15%" class="formLabel">&nbsp;</td>
		        <td width="32%">&nbsp;</td>
		      </tr>
		    </table>
		</logic:match>
		<logic:match name="frmSearchFeedback" property="emplStatusTypeId" value="DNC">
		    <table align="center" class="formContainer" id="disempaneldate" style="" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		      <tr>
		        <td width="22%" class="formLabel">Dis-empanelment Date:  <span class="mandatorySymbol">*</span></td>
		        <td width="31%">
		        <html:text property="fromDate" name="frmSearchFeedback" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
		        <td width="15%" class="formLabel">&nbsp;</td>
		        <td width="32%">&nbsp;</td>
		      </tr>
		    </table>
		</logic:match>
		<logic:match name="frmSearchFeedback" property="emplStatusTypeId" value="DPW">
		    <table align="center" class="formContainer" id="disempaneldate" style="" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		      <tr>
		        <td width="22%" class="formLabel">Dis-empanelment Date:  <span class="mandatorySymbol">*</span></td>
		        <td width="31%">
		        <html:text property="fromDate" name="frmSearchFeedback" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
		        <td width="15%" class="formLabel">&nbsp;</td>
		        <td width="32%">&nbsp;</td>
		      </tr>
		    </table>
		</logic:match>
		<logic:match name="frmSearchFeedback" property="emplStatusTypeId" value="DOR">
		    <table align="center" class="formContainer" id="disempaneldate" style="" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		      <tr>
		        <td width="22%" class="formLabel">Dis-empanelment Date:  <span class="mandatorySymbol">*</span></td>
		        <td width="31%">
		        <html:text property="fromDate" name="frmSearchFeedback" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
		        <td width="15%" class="formLabel">&nbsp;</td>
		        <td width="32%">&nbsp;</td>
		      </tr>
		    </table>
		</logic:match>
		<logic:match name="frmSearchFeedback" property="emplStatusTypeId" value="TDE">
		    <table align="center" class="formContainer" id="disempaneldate" style="" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		      <tr>
		        <td width="22%" class="formLabel">Dis-empanelment Date:  <span class="mandatorySymbol">*</span></td>
		        <td width="31%">
		        <html:text property="fromDate" name="frmSearchFeedback" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
		        <td width="15%" class="formLabel">&nbsp;</td>
		        <td width="32%">&nbsp;</td>
		      </tr>
		    </table>
		</logic:match>
		<logic:match name="frmSearchFeedback" property="emplStatusTypeId" value="TDO">
		    <table align="center" class="formContainer" id="disempaneldate" style="" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		      <tr>
		        <td width="22%" class="formLabel">Dis-empanelment Date:  <span class="mandatorySymbol">*</span></td>
		        <td width="31%">
		        <html:text property="fromDate" name="frmSearchFeedback" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
		        <td width="15%" class="formLabel">&nbsp;</td>
		        <td width="32%">&nbsp;</td>
		      </tr>
		    </table>
		</logic:match>
		<logic:match name="frmSearchFeedback" property="emplStatusTypeId" value="CLS">
			<table align="center" class="formContainer" id="closedate" style="" border="0" cellspacing="0" cellpadding="0">
		      <tr>
		      <tr>
		        <td width="22%" class="formLabel">Closed Date:  <span class="mandatorySymbol">*</span></td>
		        <td width="31%">
		        <html:text property="fromDate" name="frmSearchFeedback" styleClass="textBox textDate" styleId="companyname232" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><logic:match name="viewmode" value="false"><A NAME="CalendarObjectToDate" ID="CalendarObjectToDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectToDate','forms[1].fromDate',document.forms[1].fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></logic:match></td>
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
	    <bean:define id="emplStatusTypeID"  name="frmSearchFeedback" property="emplStatusTypeId"/>
	    <bean:define id="gradeTypeID"  name="frmSearchFeedback" property="gradeTypeId"/>
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
	&nbsp;
	&nbsp;
	&nbsp;
	<fieldset>
	<legend>Feedback</legend>
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td nowrap>Start Date:<br>
			<html:text property="startdate"  styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectStartDate" ID="CalendarObjectStartDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectStartDate','forms[1].startdate',document.forms[1].startdate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
	    <td nowrap>End Date:<br>
		   <html:text property="enddate"  styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectEndDate" ID="CalendarObjectEndDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectEndDate','forms[1].enddate',document.forms[1].enddate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
		<td width="100%" valign="bottom">
			<a href="#" accesskey="s" onClick="javascript:onSearch()"   class="search"><img src="/ttk/images/SearchIcon.gif" alt="Search" title="Search" width="16" height="16" border="0" align="absmiddle">&nbsp;<u>S</u>earch</a>
		</td>
	  </tr>
	</table>
	<!-- E N D : Search Box -->
	<!-- S T A R T : Grid -->
    <ttk:HtmlGrid name="tableData"/>

	</fieldset>
	<!-- E N D : Grid -->
	<!-- S T A R T :Buttons and  Page Counter -->
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="27%"></td>
	    <td width="73%" align="right">
	    	<% if(TTKCommon.isAuthorized(request,"Add"))
      			{
          	%>
        	  		<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddEditFeedback();"><u>A</u>dd</button>&nbsp;
      		<%
      			}// end of if(TTKCommon.isAuthorized(request,"Add"))
      			if(TTKCommon.isAuthorized(request,"Delete") && TTKCommon.isDataFound(request,"tableData"))
      			{
      		%>
	          		<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>&nbsp;
      		<%
      			}// end of if(TTKCommon.isAuthorized(request,"Delete") && TTKCommon.isDataFound(request,"tableData"))
      		%>
	    </td>
	  </tr>
  		<ttk:PageLinks name="tableData"/>
	</table>
	</div>
	<!-- E N D : Buttons and Page Counter -->
	
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="empanelDate" VALUE="">
    <INPUT TYPE="hidden" NAME="disEmpanelDate" VALUE="">
    <INPUT TYPE="hidden" NAME="closeDate" VALUE="">
    <input type="hidden" name="empanelSeqId" value="<bean:write name="frmSearchFeedback" property="empanelSeqId"/>">
    <html:hidden property="emplStatusTypeId" styleId="HidEmplStatusTypeId"/>
    <html:hidden property="tpaRegdDate"/>
    <logic:notEmpty name="frmSearchFeedback" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
	
	
	
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="sortId" VALUE="">
<INPUT TYPE="hidden" NAME="pageId" VALUE="">
<input type="hidden" name="child" value="">
</html:form>
<!-- E N D : Content/Form Area -->
