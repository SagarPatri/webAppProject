

<%
/** @ (#) enrolmonitorreports.jsp May 18, 2007
 * Project     : TTK Healthcare Services
 * File        : enrolmonitorreports.jsp
 * Author      : Ajay Kumar
 * Company     : WebEdge Technologies Pvt.Ltd.
 * Date Created: 
 *
 * @author 		 : Ajay Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.misreports.ReportCache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/enrolmonitorreports.js"></script>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>
<%
   // boolean viewmode=true;
   boolean viewmode=false;
	
    pageContext.setAttribute("ttkOfficeInID", ReportCache.getCacheObject("ttkOfficeInfo"));
	pageContext.setAttribute("insCompanyID", ReportCache.getCacheObject("insCompany"));
	pageContext.setAttribute("insDoBoID", ReportCache.getCacheObject("insDoBo"));
	
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/EnrolMonitorReportsAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td width="51%">Enrollment Monitor</td>
    	</tr>
</table>
<!-- E N D : Page Title -->
<!-- S T A R T : Search Box -->
<html:errors/>
<div class="contentArea" id="contentArea">

<fieldset>
<legend>Report Parameters</legend>
<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="formLabel">Vidal Health Branch:<span class="mandatorySymbol">*</span></td>
		<td><html:select property="tTKBranchCode" styleId="ttkoffice" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>" onchange="onChangeInsCompany()">
		  	 	<html:option value="">Select from list</html:option>
		  	 	 <!-- <html:option value="ALL">ALL</html:option> -->
		  	 	  <html:options collection="ttkOfficeInID" property="cacheId" labelProperty="cacheDesc"/>
            </html:select>
	    </td>
	    <td class="formLabel">Healthcare Company:</td>
	     <td><html:select property="insCompanyCode" styleId="comDoBo" styleClass="selectBox selectBoxLarge" disabled="<%= viewmode %>" onchange="onChangeInsBoDo()">
	    		  	 	<%-- <html:option value="">Select from list</html:option> --%>
	    		  	 	  <html:option value="ALL">All</html:option>
	    		  	 	  <logic:notEmpty name="frmMISReports"  property="alInsCompanyList">
		  					<html:optionsCollection property="alInsCompanyList" label="cacheDesc" value="cacheId"/>
		  				</logic:notEmpty>
	    		          <!-- <html:optionsCollection property="alInsCompanyList" label="cacheDesc" value="cacheId" /> -->
	                </html:select>
	    </td>
	    </tr>
	    <tr>
       
        <td class="formLabel">Do/Bo:</td>
        <td>
        <html:select property="insBoDoCode" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
		  	 	  <!-- <html:option value="">Select from list</html:option> -->
		  	 	  <html:option value="ALL">All</html:option>
		          <html:optionsCollection property="alDoBoList" label="cacheDesc" value="cacheId" />
            </html:select>
            </td>
            <td class="formLabel">Type:<span class="mandatorySymbol">*</span></td>
             <td>
        		<html:select property="sType" styleClass="selectBox selectBoxMedium" onchange="onChangeStatus()">
        		
        		<html:option value="">Select from list</html:option> 
        		<!-- <html:option value="ALL">All</html:option> -->
        	    <html:option value='MEM'>Member Level</html:option>
			   <html:option value='POL'> Scheme Level</html:option>
        	</html:select>
        	</td>
	     </tr>
        <tr>
       <td class="formLabel">Enrollment Type:</td>
             <td>
        		<html:select property="eType" styleClass="selectBox selectBoxMedium">
        		<!-- <html:option value="">Select from list</html:option> -->
        		<html:option value="ALL">All</html:option>
        	    <html:option value='COR'>Corporate</html:option>
			   <html:option value='IND'>Individuals</html:option>
			   <html:option value='ING'>Individuals as Corporate</html:option>
			   <html:option value='NCR'>Non Corporate</html:option>
			   </html:select>
        	</td>
        	<td class="formLabel">Status:</td>
        	<td>
        	<html:select property="sStatus" styleClass="selectBox selectBoxLarge" onchange="onChangeLabel()">
        	<logic:empty name="frmMISReports" property="sType">
			    <option value="">Select from list</option>
			    </logic:empty>
        	    <logic:match name="frmMISReports" property="sType" value="POL">
        		<!-- <html:option value="">Select from list</html:option> -->
        		<html:option value="ALL">All</html:option> 
        		<html:option value="CON">Confirmation Done</html:option>
        		<html:option value="CND">Confirmation Not Done</html:option>
        	    <html:option value="FEN">First Entry Not Done</html:option>
        	    <html:option value="PCN">Scheme Cancelled / Suspended</html:option>
        	    <html:option value="PRW">Scheme Due For Renewal</html:option>
        	    <html:option value="IIN">Inward Entry Incomplete</html:option> 
        	    </logic:match>
			   
			   <logic:match name="frmMISReports" property="sType" value="MEM">
			   <!--<html:option value="">Select from list</html:option> -->
        		<html:option value="ALL">All</html:option> 
        		<html:option value="PRN">Cards printed</html:option>
        		<html:option value="PEN">Cards Pending</html:option>
        	    <html:option value="DIS">Cards Dispatched</html:option>
        	    <html:option value="CNP">Cards Not Dispatched</html:option>
        	    <html:option value="CRE">Cards Reprinted</html:option>
        	    <html:option value="DDT">Delivery Details</html:option>
        	    <html:option value="ENA">Endorsement Details</html:option>
        	    <html:option value="PHS">Photo Scanned</html:option>
	           <html:option value="PHN">Photo Not Scanned</html:option>
	           </logic:match>
			   </html:select>
        	</td>
        	
        </tr>
         <tr>
        <td class="formLabel">
           <!-- <logic:empty name="frmMISReports" property="sStatus">
			    Start Date:<span class="mandatorySymbol">*</span>
	       </logic:empty> -->
	      <logic:equal name="frmMISReports" property="sType" value="">
			      Start Date:<span class="mandatorySymbol">*</span>
	       </logic:equal>
	       <!-- Ploicy Lavel -->
	       <logic:equal name="frmMISReports" property="sType" value="POL">
				       <logic:match name="frmMISReports" property="sStatus" value="ALL">
			           <label id="lb">Batch Added Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="CON">
			           <label id="lb">Confirmation Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="CND">
			           <label id="lb">Scheme Added Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="FEN">
			           <label id="lb">Scheme Added Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="PCN">
			           <label id="lb">Scheme Added Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="PRW">
			           <label id="lb">Scheme Expiry Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="IIN">
			           <label id="lb">Batch Added Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			</logic:equal>           
           <!-- Member Lavel -->
           <logic:equal name="frmMISReports" property="sType" value="MEM">
			           <logic:match name="frmMISReports" property="sStatus" value="ALL">
			           <label id="lb">Batch Added Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="PRN">
			           <label id="lb">Cards Print Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="PEN">
			           <label id="lb">As On Date</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="DIS">
			           <label id="lb">Card Dispatched Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="CNP">
			           <label id="lb">Card Print Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="CRE">
			           <label id="lb">Card Print Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="DDT">
			           <label id="lb">Delivery Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="ENA">
			           <label id="lb">Endorsement Added Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="PHS">
			           <label id="lb">Member Added Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="PHN">
			           <label id="lb">Member Added Date(From)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			  </logic:equal>
           </td>
        	<td>
        		<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="EnrolReportStartDate" ID="EnrolReportStartDate" HREF="#" onClick="javascript:show_calendar('EnrolReportStartDate','frmMISReports.sStartDate',document.frmMISReports.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>
        <td class="formLabel">
             <!-- <logic:empty name="frmMISReports" property="sStatus">
			    End Date:<span class="mandatorySymbol">*</span>
	       </logic:empty> -->
	       <logic:equal name="frmMISReports" property="sType" value="">
			      End Date:<span class="mandatorySymbol">*</span>
	       </logic:equal>
	       <!-- Policy Lavel -->
	       <logic:equal name="frmMISReports" property="sType" value="POL">
				       <logic:match name="frmMISReports" property="sStatus" value="ALL">
			           <label id="lab">Batch Added Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="CON">
			           <label id="lab">Confirmation Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			            <logic:match name="frmMISReports" property="sStatus" value="CND">
			           <label id="lab">Scheme Added Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="FEN">
			           <label id="lab">Scheme Added Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="PCN">
			           <label id="lab">Scheme Added Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="PRW">
			           <label id="lab">Scheme Expiry Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="IIN">
			           <label id="lab">Batch Added Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			 </logic:equal>          
           <!-- Member Lavel -->
           <logic:equal name="frmMISReports" property="sType" value="MEM">
			           <logic:match name="frmMISReports" property="sStatus" value="ALL">
			           <label id="lab">Batch Added Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			          <logic:match name="frmMISReports" property="sStatus" value="PRN">
			           <label id="lab">Cards Print Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="PEN">
			           <label id="lab">As On Date</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="DIS">
			           <label id="lab">Card Dispatched Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="CNP">
			           <label id="lab">Card Print Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="CRE">
			           <label id="lab">Card Print Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="DDT">
			           <label id="lab">Delivery Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="ENA">
			           <label id="lab">Endorsement Added Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="PHS">
			           <label id="lab">Member Added Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			           <logic:match name="frmMISReports" property="sStatus" value="PHN">
			           <label id="lab">Member Added Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:match>
			</logic:equal>           
           </td>
        	<td>
        		<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="EnrolReportEndDate" ID="EnrolReportEndDate" HREF="#" onClick="javascript:show_calendar('EnrolReportEndDate','frmMISReports.sEndDate',document.frmMISReports.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>
        	
        </tr>
        <tr>
         <td class="formLabel">Scheme No:</td>
        	<td>
        		<html:text property="sGroupPolicyNo" styleClass="textBox textBoxMedium" maxlength="250" />
        	</td>
        	<td class="formLabel">Agent Code:</td>
        	<td>
        		<html:text property="sAgentCode" styleClass="textBox textBoxMedium" maxlength="250" />
        	</td>
        </tr>
       
</table>
</fieldset>
<fieldset>
<legend>Report</legend>
<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
       <tr>
        <td width="100%" align="center">
        	Report Name<span class="mandatorySymbol">*</span>
			<select name="reportID" class="selectBox" id="reportid">
			<logic:empty name="frmMISReports" property="sType">
			    <option value="">Select from list</option>
			    </logic:empty>
			    <logic:match name="frmMISReports" property="sType" value="MEM">
			  <option value="EMMR">Enrollment Monitor - Member Report</option>
			   </logic:match>
			   <logic:match name="frmMISReports" property="sType" value="POL">
			   <option value="EMPR">Enrollment Monitor - Scheme Report</option>
			   </logic:match>
			   </select>
			   </td>
		   </tr>
</table>
</fieldset>
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="center">
        	Report Type
			<select name="reportType" class="selectBox" id="reporttype">
			    <!-- <option value="PDF">PDF</option> -->
		        <option value="EXL">EXCEL</option>
		   </select>
		   &nbsp;
           <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerate Report</button>&nbsp;
           <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
       	</td>
      </tr>
    </table>
<!-- E N D : Search Box -->
<!-- S T A R T : Help Box -->
<fieldset>
    
    <legend>Status And Logic</legend>
    <table class="formContainer" align="left"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">All - Batch Added Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Cards Pending - As On Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Cards printed - Cards Print Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Cards Dispatched - Card Dispatched Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Cards Not Dispatched - Card Print Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Confirmation Not Done - Scheme Added Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Confirmation Done - Confirmation Date</li></ul></td>
    </tr><tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Cards Reprinted - Card Print Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Delivery Details - Delivery Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Endorsement Details - Endorsement Added Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">First Entry Not Done - Scheme Added Date</li></ul></td>
    </tr>
     <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Inward Entry Incomplete - Batch Added Date</li></ul></td>
    </tr> 
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Scheme Due For Renewal - Scheme Expiry Date</li></ul></td>
    </tr> 
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Scheme Cancelled / Suspended - Scheme Added Date</li></ul></td>
    </tr> 
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Photo Scanned - Member Added Date</li></ul></td>
    </tr> 
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Photo Not Scanned - Member Added Date</li></ul></td>
    </tr> 
   </table>
    </fieldset>
    
<!-- E N D : Help Box -->
</div>
    <!-- <INPUT TYPE="hidden" NAME="reportID" VALUE=""> -->
    <INPUT TYPE="hidden" NAME="tTKBranchCode" VALUE="">
    <INPUT TYPE="hidden" NAME="insCompanyCode" VALUE="">
    <!-- <INPUT TYPE="hidden" NAME="insDoBo" VALUE=""> -->

    <INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="focusID" value="">
	<html:hidden property="fileName"/> 
    <html:hidden property="parameterValues"/>
    <logic:notEmpty name="frmMISReports" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
    </logic:notEmpty>
	
</html:form>
<!-- E N D : Content/Form Area -->