

<%
/** @ (#) fgpendingreportparam.jsp
 * Project       : TTK Healthcare Services
 * File          : fgpendingreportparam.jsp
 * Author        : Ajay Kumar
 * Company       : WebEdge Technologies Pvt.Ltd.
 * Date Created  : January 15,2009
 *
 * @author 		 : Ajay Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
 
 <%@ page import="com.ttk.common.TTKCommon,com.ttk.common.misreports.ReportCache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/fgpendingreportparam.js"></script>

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
<html:form action="/MISFinanceGReportsAction.do">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>Payment Pending Report</td>
	</tr>
</table>
<!-- E N D : Page Title -->
<html:errors/>
<div class="contentArea" id="contentArea">
<!-- S T A R T : Search Box -->
   <!-- Start of Parameter grid -->
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
	    		  	 	  <logic:notEmpty name="frmMISFinanceReports"  property="alInsCompanyList">
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
       </tr>
       <tr>
       <td width="22%" class="formLabel">From Date:<span class="mandatorySymbol">*</span></td>
			<td>
        		<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="FutureGeneralFromDate" ID="FutureGeneralFromDate" HREF="#" onClick="javascript:show_calendar('FutureGeneralFromDate','frmMISFinanceReports.sStartDate',document.frmMISFinanceReports.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>					
			
			<td width="22%" class="formLabel">To Date:<span class="mandatorySymbol">*</span></td>
			<td>
        		<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="FutureGeneralToDate" ID="FutureGeneralToDate" HREF="#" onClick="javascript:show_calendar('FutureGeneralToDate','frmMISFinanceReports.sEndDate',document.frmMISFinanceReports.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>
		</tr>
			</table>
	</fieldset>
<!-- End of parameter grid -->
<!-- Start of Report Type - PDF/EXCEL list and generate button -->
<fieldset>
<legend>Report</legend>
<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
       <tr>
        <td width="100%" align="center">
        	Report Name<span class="mandatorySymbol">*</span>
			<select name="reportID" class="selectBox" id="reportid">
			   <!-- <option value="">Select from list</option> -->
			   <option value="FGPR">Payment Pending Report</option>
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
<!-- End of Report Type - PDF/EXCEL list and generate button -->
<!-- End of form fields -->	
    <INPUT TYPE="hidden" NAME="tTKBranchCode" VALUE="">
    <INPUT TYPE="hidden" NAME="insCompanyCode" VALUE="">
   <input type="hidden" name="mode">
    <input type="hidden" name="focusID" value="">
	<html:hidden property="fileName"/> 
    <html:hidden property="parameterValues"/>
    <logic:notEmpty name="frmMISFinanceReports" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
    </logic:notEmpty>
</html:form>
<!-- E N D : Content/Form Area -->
