
<%
/** @ (#) corpmonitorreports.jsp July 13, 2007
 * Project     : TTK Healthcare Services
 * File        : corpmonitorreports.jsp
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
<%@ page import="com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/corpmonitorreports.js"></script>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/CorporateReportsAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>Corporate Monitor</td>
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
<td class="formLabel">Scheme No.:<span class="mandatorySymbol">*</span></td>
<td><html:text property="sPolicyNo" styleClass="textBox textBoxMedium" maxlength="250" /></td>
<td class="formLabel">Type:<span class="mandatorySymbol">*</span></td>
             <td>
        		<html:select property="sType" styleClass="selectBox selectBoxMedium" onchange="onChangeStatus()">
        		<html:option value="">Select from list</html:option>
        		<html:option value='CLM'>Claims Info</html:option>
			    <html:option value='EMP'>Employee Info</html:option>
			    <html:option value='PE'>Scheme Info</html:option>
			    <html:option value='PAT'>Cashless Info</html:option>
			  </html:select>
        	</td>
</tr>
<tr>
<td class="formLabel">Claims Type:</td>
             <td>
               
        		<logic:match name="frmCorporateReports" property="sType" value="CLM">
        		       <html:select property="sClaimsType" styleClass="selectBox selectBoxMedium" disabled="false">
			        		<!-- <html:option value="">Select from list</html:option> -->
			        		<html:option value="ALL">All</html:option> 
			        	    <html:option value='MEM'>Member</html:option>
						    <html:option value='NET'>Network</html:option>
			          </html:select>
			   </logic:match>
			   <logic:match name="frmCorporateReports" property="sType" value="PAT">
					   <html:select property="sClaimsType" styleClass="selectBox selectBoxMedium" disabled="true">
		        		<html:option value="">Select from list</html:option>
		        		</html:select>
			   </logic:match>
			   <logic:match name="frmCorporateReports" property="sType" value="EMP">
					   <html:select property="sClaimsType" styleClass="selectBox selectBoxMedium" disabled="true">
		        		<html:option value="">Select from list</html:option>
		        		</html:select>
			   </logic:match>
		       <logic:match name="frmCorporateReports" property="sType" value="PE">
					   <html:select property="sClaimsType" styleClass="selectBox selectBoxMedium" disabled="true">
		        		<html:option value="">Select from list</html:option>
		        		</html:select>
			   </logic:match> 
			  <logic:match name="frmCorporateReports" property="sType" value="REN">
					   <html:select property="sClaimsType" styleClass="selectBox selectBoxMedium" disabled="true">
		        		<html:option value="">Select from list</html:option>
		        		</html:select>
			   </logic:match>
        	</td>

<td class="formLabel">Status:<span class="mandatorySymbol">*</span></td>
             <td>
        		<html:select property="sStatus" styleClass="selectBox selectBoxMedium" onchange="onChangeLabel()">
				        <logic:empty name="frmCorporateReports" property="sType">
							   <html:option value="">Select from list</html:option>
					     </logic:empty>
					    <logic:match name="frmCorporateReports" property="sType" value="CLM">
				                 <html:option value="ALL">All</html:option>
				                 <html:option value='CLOSED'>Closed</html:option>
							     <html:option value='INPROGRESS'>Inprogress</html:option>
							     <html:option value='PAID'>Paid</html:option>
							     <html:option value='PENDING'>Pending for Payment</html:option>
							     <html:option value='REJECTED'>Rejected</html:option>
							     <html:option value='SHORTFALL'>Shortfall</html:option>
					  </logic:match>
		              <logic:match name="frmCorporateReports" property="sType" value="EMP">
			                 <html:option value="ALL">All</html:option>
			                 <html:option value="ACTIVE">Active</html:option>
			                 <html:option value='CANCELED'>Cancelled</html:option>
		              </logic:match>
		              <logic:match name="frmCorporateReports" property="sType" value="PAT">
				        		<html:option value="ALL">All</html:option>
				        		<html:option value='APPROVED'>Approved</html:option>
							    <html:option value='BILLNOTRECIEVED'>Bill Not Received</html:option>
							    <html:option value='CANCELED'>Cancelled</html:option>
							    <html:option value='INPROGRESS'>Inprogress</html:option>
							    <html:option value='REJECTED'>Rejected</html:option>
				        	    <html:option value='SHORTFALL'>Shortfall</html:option>
					  </logic:match>
					  <logic:match name="frmCorporateReports" property="sType" value="PE">
			        		<html:option value="ALL">All</html:option>
			        	    <html:option value='CANCELED'>Cancelled</html:option>
					  </logic:match>
			  </html:select>
        	</td>
</tr>
<tr>
<td class="formLabel">
          <logic:equal name="frmCorporateReports" property="sType" value="">
			      Start Date:<span class="mandatorySymbol">*</span>
	       </logic:equal>
	       <!-- Claims Info Lavel -->
          <logic:equal name="frmCorporateReports" property="sType" value="CLM">
  
		  		<logic:equal name="frmCorporateReports" property="sStatus" value="ALL">
		    	 <label id="lb">Inward Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
		         </logic:equal>
		          <logic:equal name="frmCorporateReports" property="sStatus" value="CLOSED">
		           <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
		           </logic:equal>
		            <logic:equal name="frmCorporateReports" property="sStatus" value="INPROGRESS">
		             <label id="lb">Inward Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
		            </logic:equal>
		            <logic:equal name="frmCorporateReports" property="sStatus" value="PAID">
		           <label id="lb">Cheque Date(From)</label>:<span class="mandatorySymbol">*</span>
		           </logic:equal>
		           <logic:equal name="frmCorporateReports" property="sStatus" value="PENDING">
		           <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
		           </logic:equal>
		           <logic:equal name="frmCorporateReports" property="sStatus" value="REJECTED">
		            <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
		             </logic:equal>
					<logic:equal name="frmCorporateReports" property="sStatus" value="SHORTFALL">
		             <label id="lb">Inward Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
		             </logic:equal>                        
  </logic:equal>
  <!-- Employee Info Lavel -->
  <logic:equal name="frmCorporateReports" property="sType" value="EMP">
  
		  		<logic:equal name="frmCorporateReports" property="sStatus" value="ALL">
		           <label id="lb">Member Added Date(From)</label>:<span class="mandatorySymbol">*</span>
		        </logic:equal>
		         <logic:equal name="frmCorporateReports" property="sStatus" value="ACTIVE">
		          <label id="lb">Member Added Date(From)</label>:<span class="mandatorySymbol">*</span>
		         </logic:equal>
		          <logic:equal name="frmCorporateReports" property="sStatus" value="CANCELED">
		           <label id="lb">Member Added Date(From)</label>:<span class="mandatorySymbol">*</span>
		           </logic:equal>
  </logic:equal>
  <!-- Policy Info Lavel -->
  <logic:equal name="frmCorporateReports" property="sType" value="PE">
  
		  		<logic:equal name="frmCorporateReports" property="sStatus" value="ALL">
		    			       <label id="lb">Endorsements Added Date(From)</label>:<span class="mandatorySymbol">*</span>
		         </logic:equal>
		          <logic:equal name="frmCorporateReports" property="sStatus" value="CANCELED">
		           <label id="lb">Endorsements Added Date(From)</label>:<span class="mandatorySymbol">*</span>
		           </logic:equal>
  </logic:equal>
  <!-- Preauth Info Lavel -->
  <logic:equal name="frmCorporateReports" property="sType" value="PAT">

           <logic:equal name="frmCorporateReports" property="sStatus" value="ALL">
                 <label id="lb">First Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
            </logic:equal>
            <logic:equal name="frmCorporateReports" property="sStatus" value="APPROVED">
                <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
            </logic:equal>
           <logic:equal name="frmCorporateReports" property="sStatus" value="BILLNOTRECIEVED">
           <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmCorporateReports" property="sStatus" value="CANCELED">
           <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmCorporateReports" property="sStatus" value="INPROGRESS">
               <label id="lb">First Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
            <logic:equal name="frmCorporateReports" property="sStatus" value="REJECTED">
                  <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
            </logic:equal>
            <logic:equal name="frmCorporateReports" property="sStatus" value="SHORTFALL">
                   <label id="lb">First Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
  </logic:equal>
</td>
        	<td>
        		<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="CorMonitorStartDate" ID="CorMonitorStartDate" HREF="#" onClick="javascript:show_calendar('CorMonitorStartDate','frmCorporateReports.sStartDate',document.frmCorporateReports.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>
        <td class="formLabel">
                <logic:equal name="frmCorporateReports" property="sType" value="">
			           End Date:<span class="mandatorySymbol">*</span>
	           </logic:equal>
	           
	            <!-- Claims Info Lavel -->
           <logic:equal name="frmCorporateReports" property="sType" value="CLM">
			           <logic:equal name="frmCorporateReports" property="sStatus" value="ALL">
			              <label id="lab">Inward Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
			            </logic:equal>
			           
				       <logic:equal name="frmCorporateReports" property="sStatus" value="CLOSED">
			           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:equal>
			            <logic:equal name="frmCorporateReports" property="sStatus" value="INPROGRESS">
			                          <label id="lab">Inward Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
			            </logic:equal>
			           
			           <logic:equal name="frmCorporateReports" property="sStatus" value="PAID">
			           <label id="lab">Cheque Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:equal>
			           <logic:equal name="frmCorporateReports" property="sStatus" value="PENDING">
			           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:equal>
			           
			          <logic:equal name="frmCorporateReports" property="sStatus" value="REJECTED">
			                 <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
			          </logic:equal>
			
			           
			         <logic:equal name="frmCorporateReports" property="sStatus" value="SHORTFALL">
			                <label id="lab">Inward Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
			         </logic:equal>
           </logic:equal>
           <!-- Employee Info Lavel -->
           <logic:equal name="frmCorporateReports" property="sType" value="EMP">
			           <logic:equal name="frmCorporateReports" property="sStatus" value="ALL">
			              <label id="lab">Member Added Date(To)</label>:<span class="mandatorySymbol">*</span>
			            </logic:equal>
			           <logic:equal name="frmCorporateReports" property="sStatus" value="ACTIVE">
		                <label id="lAb">Member Added Date(To)</label>:<span class="mandatorySymbol">*</span>
		               </logic:equal>
				       <logic:equal name="frmCorporateReports" property="sStatus" value="CANCELED">
			           <label id="lab">Member Added Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:equal>
		 </logic:equal>
		 <!-- Policy Info Lavel -->
		 <logic:equal name="frmCorporateReports" property="sType" value="PE">
			           <logic:equal name="frmCorporateReports" property="sStatus" value="ALL">
			              <label id="lab">Endorsements Added Date(To)</label>:<span class="mandatorySymbol">*</span>
			            </logic:equal>
			           
				       <logic:equal name="frmCorporateReports" property="sStatus" value="CANCELED">
			           <label id="lab">Endorsements Added Date(To)</label>:<span class="mandatorySymbol">*</span>
			           </logic:equal>
		 </logic:equal>
		 <!-- Preauth Info Lavel -->
		 <logic:equal name="frmCorporateReports" property="sType" value="PAT">
                        <logic:equal name="frmCorporateReports" property="sStatus" value="ALL">
                              <label id="lab">First Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
                         </logic:equal>
           
          <logic:equal name="frmCorporateReports" property="sStatus" value="APPROVED">
           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmCorporateReports" property="sStatus" value="BILLNOTRECIEVED">
           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmCorporateReports" property="sStatus" value="CANCELED">
           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           
                        <logic:equal name="frmCorporateReports" property="sStatus" value="INPROGRESS">
                                 <label id="lab">First Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
                         </logic:equal>
           
           
                        <logic:equal name="frmCorporateReports" property="sStatus" value="REJECTED">
                                 <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
                        </logic:equal>
           
           
                        <logic:equal name="frmCorporateReports" property="sStatus" value="SHORTFALL">
                                <label id="lab">First Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
                         </logic:equal>
           </logic:equal>
        </td>
        	<td>
        		<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="CorMonitorEndDate" ID="CorMonitorEndDate" HREF="#" onClick="javascript:show_calendar('CorMonitorEndDate','frmCorporateReports.sEndDate',document.frmCorporateReports.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
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
			   <logic:empty name="frmCorporateReports" property="sType">
			    <option value="">Select from list</option>
			    </logic:empty>
			   <logic:match name="frmCorporateReports" property="sType" value="EMP">
			   <option value="CMREI">Corporate Monitor - Employee Info Report</option>
			   </logic:match>
			   <logic:match name="frmCorporateReports" property="sType" value="CLM">
			   <option value="CMRCI">Corporate Monitor - Claims Info Report</option>
			   </logic:match>
			   <logic:match name="frmCorporateReports" property="sType" value="PAT">
			   <option value="CMRPRI">Corporate Monitor - Cashless Info Report</option>
			   </logic:match>
			   <logic:match name="frmCorporateReports" property="sType" value="PE">
			   <option value="CMRPOI">Corporate Monitor - Scheme Info Report</option>
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
    
    <legend>Employee Info - Status And Logic</legend>
    <table class="formContainer" align="left"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">All - Member Added Date (All Means Consider Active And Cancelled Employee)</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Active - Member Added Date (Active Means Consider Only Active Employee)</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Cancelled - Member Added Date (Cancelled Means Consider Only Cancelled Employee)</li></ul></td>
    </tr>
    </table>
    </fieldset>
    <fieldset>
    
    <legend>Scheme And Endorsements Info - Status And Logic</legend>
    <table class="formContainer" align="left"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">All - Endorsements Added Date (All Means Consider Only Active Scheme)</li></ul></td>
    </tr>
    <tr>
    <td class="formLabel"><ul class="liBotMargin">
    <li class="liPad">Cancelled - Endorsements Added Date (Cancelled Means Consider Only Cancelled Scheme)</li></ul></td>
    </tr>
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
    
    <INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="focusID" value="">
	<html:hidden property="fileName"/> 
    <html:hidden property="parameterValues"/>
    <logic:notEmpty name="frmCorporateReports" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
    </logic:notEmpty>
</html:form>
<!-- E N D : Content/Form Area -->