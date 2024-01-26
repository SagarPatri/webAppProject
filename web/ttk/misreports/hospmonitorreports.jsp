
<%
/** @ (#) hospmonitorreports.jsp July 13, 2007
 * Project     : TTK Healthcare Services
 * File        : hospmonitorreports.jsp
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
<script language="javascript" src="/ttk/scripts/misreports/hospmonitorreports.js"></script>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>
<%
   // boolean viewmode=true;
   boolean viewmode=false;
	
    pageContext.setAttribute("hospitalName", ReportCache.getCacheObject("hospitalNameInfo"));
	
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/HospitalReportsAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>Hospital Monitor</td>
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
       <td class="formLabel">Hospital Name:<span class="mandatorySymbol">*</span></td>
           <td><html:select property="sHospitalName" styleId="hosName" styleClass="selectBox selectBoxMoreLargest" disabled="<%= viewmode %>">
		  	 	  <html:option value="">Select from list</html:option> 
		  	 	   <!--<html:option value="ALL">ALL</html:option>-->
		  	 	  <html:options collection="hospitalName" property="cacheId" labelProperty="cacheDesc"/>
            </html:select>
	    </td>
	    
	   
	</tr>
	<tr>
	 <td class="formLabel">Type:<span class="mandatorySymbol">*</span></td>
             <td>
        		<html:select property="sType" styleClass="selectBox selectBoxMedium" onchange="onChangeStatus()">
        		<html:option value="">Select from list</html:option>
        	    <html:option value='CLM'>Claims Info</html:option>
			   <html:option value='PAT'>Cashless Info</html:option>
			  </html:select>
        	</td>
          <td class="formLabel">Status:<span class="mandatorySymbol">*</span></td>
               <td>
        		<html:select property="sStatus" styleClass="selectBox selectBoxMedium" onchange="onChangeLabel()">
        		<logic:empty name="frmHospitalReports" property="sType">
			    <html:option value="">Select from list</html:option>
			    </logic:empty>
        		<logic:match name="frmHospitalReports" property="sType" value="CLM">
                 <html:option value="ALL">All</html:option>
                 <html:option value='CLOSED'>Closed</html:option>
			     <html:option value='INPROGRESS'>Inprogress</html:option>
			     <html:option value='PAID'>Paid</html:option>
			    <html:option value='PENDING'>Pending for Payment</html:option>
			    <html:option value='REJECTED'>Rejected</html:option>
			    <html:option value='SHORTFALL'>Shortfall</html:option>
			  </logic:match>
              <logic:match name="frmHospitalReports" property="sType" value="PAT">
        		<html:option value="ALL">All</html:option>
        		<html:option value='APPROVED'>Approved</html:option>
			    <html:option value='BILLNOTRECIEVED'>Bill Not Received</html:option>
			    <html:option value='CANCELED'>Cancelled</html:option>
			    <html:option value='INPROGRESS'>Inprogress</html:option>
			    <html:option value='REJECTED'>Rejected</html:option>
			    <html:option value='SHORTFALL'>Shortfall</html:option>
			  </logic:match>
        	</html:select>
        	</td>
</tr>
<tr>
       <td class="formLabel">
         <!-- <logic:empty name="frmHospitalReports" property="sStatus">
			    Start Date:<span class="mandatorySymbol">*</span>
	       </logic:empty> -->
	       <logic:equal name="frmHospitalReports" property="sType" value="">
			    Start Date:<span class="mandatorySymbol">*</span>
	       </logic:equal>
	       
           <!-- Claims Info Lavel -->
  <logic:equal name="frmHospitalReports" property="sType" value="CLM">
  
  		<logic:equal name="frmHospitalReports" property="sStatus" value="ALL">
    			       <label id="lb">Inward Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
         </logic:equal>
          <logic:equal name="frmHospitalReports" property="sStatus" value="CLOSED">
           <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
            <logic:equal name="frmHospitalReports" property="sStatus" value="INPROGRESS">
                           <label id="lb">Inward Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
                   </logic:equal>
                   <logic:equal name="frmHospitalReports" property="sStatus" value="PAID">
           <label id="lb">Cheque Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="PENDING">
           <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="REJECTED">
                             <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
			<logic:equal name="frmHospitalReports" property="sStatus" value="SHORTFALL">
                            <label id="lb">Inward Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>                        
  </logic:equal>

  <!-- PreAuth Info Lavel -->
 <logic:equal name="frmHospitalReports" property="sType" value="PAT">

  	                       <logic:equal name="frmHospitalReports" property="sStatus" value="ALL">
                            <label id="lb">First Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
                       </logic:equal>
                       <logic:equal name="frmHospitalReports" property="sStatus" value="APPROVED">
           <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="BILLNOTRECIEVED">
           <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="CANCELED">
           <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="INPROGRESS">
                            <label id="lb">First Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
                         <logic:equal name="frmHospitalReports" property="sStatus" value="REJECTED">
                               <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
                         </logic:equal>
                         <logic:equal name="frmHospitalReports" property="sStatus" value="SHORTFALL">
                                <label id="lb">First Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
  </logic:equal>
           
         </td>
        	<td>
        		<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="HosMonitorStartDate" ID="HosMonitorStartDate" HREF="#" onClick="javascript:show_calendar('HosMonitorStartDate','frmHospitalReports.sStartDate',document.frmHospitalReports.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>
        <td class="formLabel">
         <!-- <logic:empty name="frmHospitalReports" property="sStatus">
			    End Date:<span class="mandatorySymbol">*</span>
	       </logic:empty> -->
	       
	      <logic:equal name="frmHospitalReports" property="sType" value="">
			    End Date:<span class="mandatorySymbol">*</span>
	       </logic:equal>
	      
	       
           <!-- Claims Info Lavel -->
           <logic:equal name="frmHospitalReports" property="sType" value="CLM">
                       <logic:equal name="frmHospitalReports" property="sStatus" value="ALL">
                          <label id="lab">Inward Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
           
	       <logic:equal name="frmHospitalReports" property="sStatus" value="CLOSED">
           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
                                   <logic:equal name="frmHospitalReports" property="sStatus" value="INPROGRESS">
                               <label id="lab">Inward Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
           
           <logic:equal name="frmHospitalReports" property="sStatus" value="PAID">
           <label id="lab">Cheque Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="PENDING">
           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           
                        <logic:equal name="frmHospitalReports" property="sStatus" value="REJECTED">
                               <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
           
           
                        <logic:equal name="frmHospitalReports" property="sStatus" value="SHORTFALL">
                               <label id="lab">Inward Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
           </logic:equal>
           <!-- Preauth Info Lavel -->
           <logic:equal name="frmHospitalReports" property="sType" value="PAT">
                        <logic:equal name="frmHospitalReports" property="sStatus" value="ALL">
                              <label id="lab">First Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
                         </logic:equal>
           
          <logic:equal name="frmHospitalReports" property="sStatus" value="APPROVED">
           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="BILLNOTRECIEVED">
           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="CANCELED">
           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           
                        <logic:equal name="frmHospitalReports" property="sStatus" value="INPROGRESS">
                                 <label id="lab">First Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
                         </logic:equal>
           
           
                        <logic:equal name="frmHospitalReports" property="sStatus" value="REJECTED">
                                 <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
           
           
                        <logic:equal name="frmHospitalReports" property="sStatus" value="SHORTFALL">
                                <label id="lab">First Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
                         </logic:equal>
           </logic:equal>
           
        </td>
        	<td>
        		<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="HosMonitorEndDate" ID="HosMonitorEndDate" HREF="#" onClick="javascript:show_calendar('HosMonitorEndDate','frmHospitalReports.sEndDate',document.frmHospitalReports.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
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
			   <logic:empty name="frmHospitalReports" property="sType">
			    <option value="">Select from list</option>
			    </logic:empty>
			   <logic:match name="frmHospitalReports" property="sType" value="CLM">
			   <option value="HMRCI">Hospital Monitor - Claims Info Report</option>
			   </logic:match>
			   <logic:match name="frmHospitalReports" property="sType" value="PAT">
			   <option value="HMRPRI">Hospital Monitor - Cashless Info Report</option>
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
    
    <legend>Claims Info - Status And Logic</legend>
    <table class="formContainer" align="left"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">All - Inward Entry Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Closed - Second Entry Date(Decision Date)</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Inprogress - Inward Entry Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Paid - Cheque Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Pending For Payment - Second Entry Date(Decision Date)</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Rejected - Second Entry Date(Decision Date)</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Shortfall - Inward Entry Date</li></ul></td>
    </tr>
    <!-- <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">First Entry Not Done - Inward Entry Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Second Entry Not Done - Inward Entry Date</li></ul></td>
    </tr> -->
    
    
    
    
   </table>
    </fieldset>
    <fieldset>
    
    <legend>Cashless Info - Status And Logic</legend>
    <table class="formContainer" align="left"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">All - First Entry Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Approved - Second Entry Date(Decision Date)</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Bill Not Received - Second Entry Date(Decision Date)</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Cancelled - Second Entry Date(Decision Date)</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Inprogress - First Entry Date</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Rejected - Second Entry Date(Decision Date)</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Shortfall - First Entry Date</li></ul></td>
    </tr>
    </table>
    </fieldset>
<!-- E N D : Help Box -->
</div>
    
    <INPUT TYPE="hidden" NAME="sHospitalName" VALUE="">
    <INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="focusID" value="">
	<html:hidden property="fileName"/> 
    <html:hidden property="parameterValues"/>
    <logic:notEmpty name="frmHospitalReports" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
    </logic:notEmpty>


</html:form>
<!-- E N D : Content/Form Area -->