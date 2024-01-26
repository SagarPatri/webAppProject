
<%
/** @ (#) preauthorizationreports.jsp July 25, 2007
 * Project     : TTK Healthcare Services
 * File        : preauthorizationreports.jsp
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
<script language="javascript" src="/ttk/scripts/misreports/preauthorizationreports.js"></script>
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
<html:form action="/PreAuthReportsAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>Cashless Monitor</td>
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
	    		  	 	   <!-- <html:option value="">Select from list</html:option>-->
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
            <td class="formLabel">Agent Code:</td>
        	<td>
        		<html:text property="sAgentCode" styleClass="textBox textBoxMedium" maxlength="250" />
        	</td>
	    </td>
        </tr>
        <tr>
         <td class="formLabel">Scheme No:</td>
        	<td>
        		<html:text property="sGroupPolicyNo" styleClass="textBox textBoxMedium" maxlength="250" />
        	</td>
        	<td class="formLabel">Status:<span class="mandatorySymbol">*</span></td>
        	<td>
        		<html:select property="sStatus" styleClass="selectBox selectBoxMedium" onchange="onChangeLabel()">
        		<html:option value="">Select from list</html:option>
        		<html:option value="ALL">All</html:option>
        		 <html:option value='APPROVED'>Approved</html:option>
			     <html:option value='BILLNOTRECIEVED'>Bill Not Received</html:option>
			     <html:option value='CANCELED'>Cancelled</html:option>
			     <html:option value='INPROGRESS'>Inprogress</html:option>
			     <html:option value='REJECTED'>Rejected</html:option>
			    <html:option value='SHORTFALL'>Shortfall</html:option>
			  </html:select>
        	</td>
        	
        </tr>
        <tr>
        <td class="formLabel">
        <logic:equal name="frmMISReports" property="sStatus" value="">
			      Start Date:<span class="mandatorySymbol">*</span>
	       </logic:equal>
	      
	       
	       <logic:equal name="frmMISReports" property="sStatus" value="ALL">
           <label id="lb">First Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
   	       <logic:equal name="frmMISReports" property="sStatus" value="APPROVED">
           <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="BILLNOTRECIEVED">
           <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="CANCELED">
           <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="INPROGRESS">
           <label id="lb">First Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="REJECTED">
           <label id="lb">Second Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="SHORTFALL">
           <label id="lb">First Entry Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
        </td>
        	<td>
        		<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="PreauthReportStartDate" ID="PreauthReportStartDate" HREF="#" onClick="javascript:show_calendar('PreauthReportStartDate','frmMISReports.sStartDate',document.frmMISReports.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>
        <td class="formLabel">
        <logic:equal name="frmMISReports" property="sStatus" value="">
			      End Date:<span class="mandatorySymbol">*</span>
	       </logic:equal>
	       
	       <logic:equal name="frmMISReports" property="sStatus" value="ALL">
           <label id="lab">First Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           
	       <logic:equal name="frmMISReports" property="sStatus" value="APPROVED">
           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
            <logic:equal name="frmMISReports" property="sStatus" value="BILLNOTRECIEVED">
           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="CANCELED">
           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="INPROGRESS">
           <label id="lab">First Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="REJECTED">
           <label id="lab">Second Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="SHORTFALL">
           <label id="lab">First Entry Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           
        </td>
        	<td>
        		<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="PreauthReportEndDate" ID="PreauthReportEndDate" HREF="#" onClick="javascript:show_calendar('PreauthReportEndDate','frmMISReports.sEndDate',document.frmMISReports.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
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
			   <!-- <option value="">Select from list</option> -->
			   <option value="PAPRI">Cashless Monitor Report</option>
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
    <INPUT TYPE="hidden" NAME="tTKBranchCode" VALUE="">
    <INPUT TYPE="hidden" NAME="insCompanyCode" VALUE="">
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