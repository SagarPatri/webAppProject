

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.misreports.ReportCache,java.util.ArrayList" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/claimsoutstandingliability.js"></script>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>
<%
	boolean viewmode=false;
    pageContext.setAttribute("officeInfo", ReportCache.getCacheObject("officeInfo"));
	pageContext.setAttribute("insuranceCompany", ReportCache.getCacheObject("insHO"));
	pageContext.setAttribute("enrollTypeCodeID", ReportCache.getCacheObject("enrollTypeCode"));                              
	pageContext.setAttribute("liabilityStatusID", ReportCache.getCacheObject("liabilityStatus"));	
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/MISClaimsLiabiltyAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td width="51%">Claims - Outstanding Liability for Claims </td>
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
		<tr>
		<td class="formLabel">Vidal Health Branch<span class="mandatorySymbol">&nbsp;*</span></td>
		<td>
			<html:select property="tTKBranchCode" styleId="ttkoffice" styleClass="selectBox selectBoxMedium" 
														disabled="<%= viewmode %>" onchange="onChangeInsCompany()">
		  		<html:option value="">Select from list</html:option>
			  	<html:options collection="officeInfo" property="cacheId" labelProperty="cacheDesc"/>
            </html:select>
	    </td>
	    <td class="formLabel">Healthcare Company</td>
	    <td>
	    	<html:select property="insCompanyCode" styleId="comDoBo" styleClass="selectBox selectBoxLarge" 
	    								disabled="<%= viewmode %>" onchange="onChangeInsBoDo()">
 		  	 <html:option value="">All</html:option>
					<logic:notEmpty name="frmMISReports"  property="alInsCompanyList">
				  	 <html:optionsCollection property="alInsCompanyList" label="cacheDesc" value="cacheId"/>
					</logic:notEmpty>
             </html:select>
	    </td>
	 <tr>
       
        <td class="formLabel">Healthcare Company Code</td>
        <td>
	        <html:select property="insBoDoCode" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
		  	 	  <html:option value="">All</html:option>
		          <html:optionsCollection property="alDoBoList" label="cacheDesc" value="cacheDesc" />
            </html:select>	
        </td>
             <td class="formLabel">Scheme Type</td>
	     <td><html:select property="policyType" styleId="enrollTypeCode" styleClass="selectBox selectBoxLarge">
   		  	 	  <html:option value="">All</html:option>
	    		  	<html:options collection="enrollTypeCodeID" property="cacheId" labelProperty="cacheDesc"/>
               </html:select>
	    </td>
	    
        </tr>
        <tr> 
        	<td class="formLabel">Status</td>
        	<td>
        		<html:select property="sStatus" styleClass="selectBox selectBoxLarge">
        		<html:option value="">All</html:option>
	    		<html:options collection="liabilityStatusID" property="cacheId" labelProperty="cacheDesc"/>
	    		</html:select>
        	</td>
        	<td class="formLabel">As on Date</td>
        	<td>
        		<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="ClaimsReportEndDate" ID="ClaimsReportEndDate" HREF="#" onClick="javascript:show_calendar('ClaimsReportEndDate','frmMISReports.sEndDate',document.frmMISReports.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>
        </tr>              
</table>
</fieldset>
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="center">
        	Report Type
			<select name="reportType" class="selectBox" id="reporttype">
			    <option value="PDF">PDF</option>
		        <option value="EXL">EXCEL</option>
		   </select>
		   &nbsp;
           <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateLiability();"><u>G</u>enerate Report</button>&nbsp;
           <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
       	</td>
      </tr>
    </table>
<!-- E N D : Search Box -->

</div>
    <!--<INPUT TYPE="hidden" NAME="reportID" VALUE="">-->
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