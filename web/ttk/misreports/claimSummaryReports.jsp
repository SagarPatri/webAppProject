<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.misreports.ReportCache" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/claimSummaryReports.js"></script>
<script language="javascript"> 
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>


<%
   // boolean viewmode=true;
   boolean viewmode=false;
	
    pageContext.setAttribute("hospitalName", ReportCache.getCacheObject("hospitalNameInfo"));
    pageContext.setAttribute("sStatus", ReportCache.getCacheObject("preauthStatus"));
	
%>





<!-- S T A R T : Content/Form Area -->
<html:form action="/HospitalReportsAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>Claims Summary Report Monitor</td>
	    </tr>
</table>

<html:errors/>


<div class="contentArea" id="contentArea">
<fieldset>
<legend>Report Parameters</legend>
<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
<tr>
       <td class="formLabel">Hospital Name:<span class="mandatorySymbol">*</span></td>
           <td>
           
           <html:select property="sHospitalName" styleId="hosName" styleClass="selectBox selectBoxMoreLargest" disabled="<%= viewmode %>">
		  	 	  <html:option value="">Select from list</html:option> 
		  	 	  <html:option value="ALL">ALL</html:option>
		  	 	  <html:options collection="hospitalName" property="cacheId" labelProperty="cacheDesc"/> 	 
            </html:select>
	    </td>
	</tr>
	
	
	<tr>
	
	
	  <td class="formLabel">ClaimType:</td>
             <td>
        		<html:select property="sType" styleClass="selectBox selectBoxMedium" onchange="onChangeStatus()">
        		<html:option value="">Select from list</html:option>
        	    <html:option value='CTM'>Member</html:option>
			   <html:option value='CNH'>Network</html:option>
			  </html:select>
        	</td>

	 <td class="formLabel">Status:</td>
               <td>
               <html:select property="sStatus"
											 styleClass="selectBox selectBoxMedium">
											<html:option value="">Select from list</html:option>
											 <html:option value="ALL">ALL</html:option>
											<html:optionsCollection name="sStatus"
												label="cacheDesc" value="cacheId" />
										</html:select>
          	</td>
       
</tr>
<tr>
       <td class="formLabel">
         <!-- <logic:empty name="frmHospitalReports" property="sStatus">
			    Start Date:<span class="mandatorySymbol">*</span>
	       </logic:empty> -->
	       <logic:equal name="frmHospitalReports" property="sType" value="">
			    <label id="lb">Start Date</label>:<span class="mandatorySymbol">*</span>
	       </logic:equal>
	       
           <!-- Claims Info Lavel -->
  <logic:equal name="frmHospitalReports" property="sType" value="CTM">
  
  		<logic:equal name="frmHospitalReports" property="sStatus" value="ALL">
    			       <label id="lb">Start Date</label>:<span class="mandatorySymbol">*</span>
         </logic:equal>
          <logic:equal name="frmHospitalReports" property="sStatus" value="REQUIRED">
           <label id="lb">Start Date</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
            <logic:equal name="frmHospitalReports" property="sStatus" value="INPROGRESS">
                           <label id="lb">Start Date</label>:<span class="mandatorySymbol">*</span>
                   </logic:equal>
                   <logic:equal name="frmHospitalReports" property="sStatus" value="APPROVED">
           <label id="lb">Start Date</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="REJECTED">
           <label id="lb">Start Date</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="CLOSED">
                             <label id="lb">Start Date</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
		                    
  </logic:equal>

  <!-- PreAuth Info Lavel -->
 <logic:equal name="frmHospitalReports" property="sType" value="CNH">

  	                       <logic:equal name="frmHospitalReports" property="sStatus" value="ALL">
                            <label id="lb">Start Date</label>:<span class="mandatorySymbol">*</span>
                       </logic:equal>
                       <logic:equal name="frmHospitalReports" property="sStatus" value="REQUIRED">
           <label id="lb">Start Date</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="INPROGRESS">
           <label id="lb">Start Date</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="APPROVED">
           <label id="lb">Start Date</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="REJECTED">
                            <label id="lb">Start Date</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
                         <logic:equal name="frmHospitalReports" property="sStatus" value="CLOSED">
                               <label id="lb">Start Date</label>:<span class="mandatorySymbol">*</span>
                         </logic:equal>
                         
  </logic:equal>
           
         </td>
        	<td>
        		<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="HosMonitorStartDate" ID="HosMonitorStartDate" HREF="#" onClick="javascript:show_calendar('HosMonitorStartDate','frmHospitalReports.sStartDate',document.frmHospitalReports.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>
        <td class="formLabel">
        
	       
	      <logic:equal name="frmHospitalReports" property="sType" value="">
			    <label id="lab">End Date</label>:<span class="mandatorySymbol">*</span>
	       </logic:equal>
	      
	       
           <!-- Claims Info Lavel -->
           <logic:equal name="frmHospitalReports" property="sType" value="CTM">
                       <logic:equal name="frmHospitalReports" property="sStatus" value="ALL">
                          <label id="lab">End Date</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
           
	       <logic:equal name="frmHospitalReports" property="sStatus" value="REQUIRED">
           <label id="lab">End Date</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
                                   <logic:equal name="frmHospitalReports" property="sStatus" value="INPROGRESS">
                               <label id="lab">End Date</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
           
           <logic:equal name="frmHospitalReports" property="sStatus" value="APPROVED">
           <label id="lab">End Date</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="REJECTED">
           <label id="lab">End Date</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           
                        <logic:equal name="frmHospitalReports" property="sStatus" value="CLOSED">
                               <label id="lab">End Date</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
            
           </logic:equal>
           <!-- Preauth Info Lavel -->
           <logic:equal name="frmHospitalReports" property="sType" value="CNH">
                        <logic:equal name="frmHospitalReports" property="sStatus" value="ALL">
                              <label id="lab">End Date</label>:<span class="mandatorySymbol">*</span>
                         </logic:equal>
           
          <logic:equal name="frmHospitalReports" property="sStatus" value="REQUIRED">
           <label id="lab">End Date</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="INPROGRESS">
           <label id="lab">End Date</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmHospitalReports" property="sStatus" value="APPROVED">
           <label id="lab">End Date</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           
                        <logic:equal name="frmHospitalReports" property="sStatus" value="REJECTED">
                                 <label id="lab">End Date</label>:<span class="mandatorySymbol">*</span>
                         </logic:equal>
           
           
                        <logic:equal name="frmHospitalReports" property="sStatus" value="CLOSED">
                                 <label id="lab">End Date</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
           
           </logic:equal>
           
        </td>
        	<td>
        		<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="HosMonitorEndDate" ID="HosMonitorEndDate" HREF="#" onClick="javascript:show_calendar('HosMonitorEndDate','frmHospitalReports.sEndDate',document.frmHospitalReports.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>
</tr>

<tr>

 		<td class="formLabel">Invoice No:</td>
		<td>
        		<html:text property="invoiceNo" styleClass="textBox textBoxMedium" maxlength="250" />
        	</td>
        	
        	<td class="formLabel">Claim No:</td>
        	<td>
        		<html:text property="claimNo" styleClass="textBox textBoxMedium" maxlength="250" />
        	</td>

</tr>

 <tr>
         
        	<td class="formLabel">Batch No:</td>
        	<td>
        		<html:text property="batchNo" styleClass="textBox textBoxMedium" maxlength="250" />
        	</td>
        </tr>


</table>
</fieldset>
<fieldset>
<legend>Report</legend>
<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">

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
 
</fieldset>

<!-- E N D : Search Box -->

</div>

 <INPUT TYPE="hidden" NAME="sHospitalName" VALUE="">
    <INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="focusID" value="">
	<html:hidden property="fileName"/> 
    <html:hidden property="parameterValues"/>
    
    
    <html:hidden property="letterPath" name="frmHospitalReports" />
    
    <logic:notEmpty name="frmHospitalReports" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
    </logic:notEmpty>

</html:form>
