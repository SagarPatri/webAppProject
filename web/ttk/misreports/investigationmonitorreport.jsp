
<%
/** @ (#) investigationmonitorreport.jsp April 24, 2008
 * Project     : TTK Healthcare Services
 * File        : investigationmonitorreport.jsp
 * Author      : Ajay Kumar
 * Company     : WebEdge Technologies Pvt.Ltd.
 * Date Created: April 24, 2008
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
<script language="javascript" src="/ttk/scripts/misreports/investigationmonitorreport.js"></script>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>
<%
   // boolean viewmode=true;
   boolean viewmode=false;
	
    pageContext.setAttribute("ttkOfficeInID", ReportCache.getCacheObject("ttkOfficeInfo"));
	pageContext.setAttribute("insCompanyID", ReportCache.getCacheObject("insCompany"));
	pageContext.setAttribute("insDoBoID", ReportCache.getCacheObject("insDoBo"));
	pageContext.setAttribute("investAgencyID", ReportCache.getCacheObject("investAgencyDetails"));
	
%>


<!-- S T A R T : Content/Form Area -->
<html:form action="/InvestigatReportsAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td width="51%">Investigation Monitor</td>
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
            <td class="formLabel">Investigator:</td>
            <td><html:select property="investAgencyName" styleClass="selectBox selectBoxMoreLargest" disabled="<%= viewmode %>">
		  	 	<!-- <html:option value="">Select from list</html:option> -->
		  	 	  <html:option value="ALL">All</html:option> 
		  	 	  <html:options collection="investAgencyID" property="cacheId" labelProperty="cacheDesc"/>
            </html:select>
	    </td>
            </tr>
            <tr>
            <td class="formLabel">Agent Code:</td>
        	<td>
        		<html:text property="sAgentCode" styleClass="textBox textBoxMedium" maxlength="250" />
        	</td>
	
            <td class="formLabel">Scheme No:</td>
        	<td>
        		<html:text property="sGroupPolicyNo" styleClass="textBox textBoxMedium" maxlength="250" />
        	</td>
        
        </tr>
        <tr>
       <td class="formLabel">Type:<span class="mandatorySymbol">*</span></td>
             <td>
        		<html:select property="sType" styleClass="selectBox selectBoxMedium" onchange="onChangeStatus()">
        		 <html:option value="">Select from list</html:option> 
        		<!-- <html:option value="ALL">All</html:option> -->
        	    <html:option value='CLM'>Claims</html:option>
			   <html:option value='PAT'>Cashless</html:option>
			  </html:select>
        	</td>
        	<td class="formLabel">Status:</td>
        	<td>
        		<html:select property="sStatus" styleClass="selectBox selectBoxMedium" onchange="onChangeLabel()">
        		<!-- <html:option value="">Select from list</html:option> -->
        		 <html:option value="ALL">All</html:option> 
        			<!--<html:option value="NEW">New</html:option>-->
        		<html:option value="SENT">Sent</html:option>
        		<!--<html:option value="CONTINUE">Continue</html:option>-->
        		<html:option value="GENUINE">Genuine</html:option>
        		<html:option value="FRAUDULENT">Fraudulent</html:option>
        		<html:option value="REPORT NOT RECEIVED">Report not Received</html:option>
        		<!--<html:option value="STOP PREAUTH/CLAIMS">Stop Preauth/Claims</html:option>-->
	           </html:select>
        	</td>
        	
        </tr>
         <tr>
        <td class="formLabel">
        <logic:empty name="frmMISReports" property="sStatus">
			    <label id="lb">Investigation Date(From)</label>:<span class="mandatorySymbol">*</span>
	       </logic:empty> 
	      
	       
	       <logic:equal name="frmMISReports" property="sStatus" value="ALL">
           <label id="lb">Investigation Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
   	       <logic:equal name="frmMISReports" property="sStatus" value="SENT">
           <label id="lb">Investigation Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="GENUINE">
           <label id="lb">Investigation Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="FRAUDULENT">
           <label id="lb">Investigation Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="REPORT NOT RECEIVED">
           <label id="lb">Investigation Date(From)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           </td>
        	<td>
        		<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="InvestigatReportStartDate" ID="InvestigatReportStartDate" HREF="#" onClick="javascript:show_calendar('InvestigatReportStartDate','frmMISReports.sStartDate',document.frmMISReports.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>
        <td class="formLabel">
         <logic:empty name="frmMISReports" property="sStatus">
			    <label id="lab">Investigation Date(To)</label>:<span class="mandatorySymbol">*</span>
	       </logic:empty>  
	       
	       <logic:equal name="frmMISReports" property="sStatus" value="ALL">
           <label id="lab">Investigation Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           
	       <logic:equal name="frmMISReports" property="sStatus" value="GENUINE">">
           <label id="lab">Investigation Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
            <logic:equal name="frmMISReports" property="sStatus" value="SENT">
           <label id="lab">Investigation Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="FRAUDULENT">
           <label id="lab">Investigation Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
           <logic:equal name="frmMISReports" property="sStatus" value="REPORT NOT RECEIVED">
           <label id="lab">Investigation Date(To)</label>:<span class="mandatorySymbol">*</span>
           </logic:equal>
          </td>
        	<td>
        		<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="InvestigatReportEndDate" ID="InvestigatReportEndDate" HREF="#" onClick="javascript:show_calendar('InvestigatReportEndDate','frmMISReports.sEndDate',document.frmMISReports.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>
        	
        </tr>
        <tr>
            <td class="formLabel">Enrollment Id:</td>
        	<td>
        		<html:text property="sEnrolmentId" styleClass="textBox textBoxMedium" maxlength="250" />
        	</td>	
            <td class="formLabel">Corporate/Insurer:</td>
        	<td>
        		<html:text property="sCorInsurer" styleClass="textBox textBoxMedium" maxlength="250" />
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
			   <logic:match name="frmMISReports" property="sType" value="CLM">
			   <option value="IMCIR">Investigation Monitor - Claims Info Report</option>
			   </logic:match>
			   <logic:match name="frmMISReports" property="sType" value="PAT">
			   <option value="IMPAIR">Investigation Monitor - Cashless Info Report</option>
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
    <li class="liPad">All - Investigation Date(Added Date)</li></ul></td>
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