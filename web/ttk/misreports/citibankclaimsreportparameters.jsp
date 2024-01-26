<%
/** @ (#) citibankclaimsreportparameters.jsp
 * Project     : TTK Healthcare Services
 * File        : citibankclaimsreportparameters.jsp
 * Author      : Balaji C R B
 * Company     : Span Infotech India Pvt. Ltd
 * Date Created: July 11,2008
 *
 * @author 		 : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.misreports.ReportCache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/citibankclaimsreportparameters.js"></script>

<%
	boolean viewmode=false;
	pageContext.setAttribute("listTTKBranch", ReportCache.getCacheObject("officeInfo"));
	pageContext.setAttribute("listPolicyType", ReportCache.getCacheObject("enrollTypeCode"));
	pageContext.setAttribute("baos",request.getAttribute("boas"));
%>

<html:form action="/CitibankClaimsReportAction.do">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>Citibank Claims Report Parameters</td>
	</tr>
</table>
<!-- E N D : Page Title -->
<html:errors/>
<!-- Start of form fields -->
	<!-- Start of Parameter grid -->
	<div class="contentArea" id="contentArea">
	<table border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">         
		<tr>
			<td width="21%" class="formLabel">&nbsp;&nbsp;&nbsp;Report Type</td>
			<td width="28%" class="formLabel">
				<html:select property="sReportType" styleId="select" styleClass="selectBox selectBoxMedium" >
            		<html:option value="Claims Intimation"/>
	            	<html:option value="Claims Rejection"/>
	            	<html:option value="Claims Settlement"/>
               </html:select>
			</td>
			<td  width="22%" class="formLabel">&nbsp;</td>
			<td  class="formLabel">&nbsp;</td>					
		</tr>
    </table>
	
    <fieldset>
		<legend>Report Parameters </legend>
			<table border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
				<tr>
					<td nowrap>Start Date&nbsp;<span class="mandatorySymbol">*</span></td>
					<td>
						<html:text property="sStartDate"	styleClass="textBox textDate" maxlength="10" /> 
						<a NAME="CalendarObjectstartDate" ID="CalendarObjectstartDate" HREF="#"	onClick="javascript:show_calendar('CalendarObjectstartDate','forms[1].sStartDate',document.forms[1].sStartDate.value,'',event,148,178);return false;"	onMouseOver="window.status='Calendar';return true;"	onMouseOut="window.status='';return true;">
						<img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="startDate" width="24" height="17" border="0" align="absmiddle"></a>
					</td>
					<td nowrap>End Date</td>
					<td>
						<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10" />
						<a NAME="CalendarObjectendDate" ID="CalendarObjectendDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectendDate','forms[1].sEndDate',document.forms[1].sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
						<img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="endDate" width="24" height="17" border="0" align="absmiddle"></a>
					</td>
				</tr>
				<tr>
					<td width="22%" class="formLabel">Vidal Health Branch  &nbsp;<span class="mandatorySymbol">*</span></td>
					<td width="29%" class="formLabel">
						<html:select property="tTKBranchCode" styleId="select" styleClass="selectBox selectBoxMedium" 
									 onchange="onChangeInsComp()">
							<html:option value="">Select from list</html:option>
							<html:options collection="listTTKBranch" property="cacheId"	labelProperty="cacheDesc" />
						</html:select>
					</td>
					<td width="22%" class="formLabel">Healthcare Company &nbsp;<span class="mandatorySymbol">*</span></td>
						<td width="29%" class="formLabel">						
						<html:select property="sInsCompany" styleId="comDoBo" styleClass="selectBox selectBoxLarge" 
	    								disabled="<%= viewmode %>" >
 		  	 				<html:option value="">All</html:option>
								<logic:notEmpty name="frmCitiClaimsRpt"  property="alInsCompanyList">
				  	 				<html:optionsCollection property="alInsCompanyList" label="cacheDesc" value="cacheId"/>
								</logic:notEmpty>
             			</html:select>
					</td>					
				</tr>
				<tr>
					<td width="22%" class="formLabel">Scheme Type  &nbsp;<span class="mandatorySymbol">*</span></td>
					<td width="29%" class="formLabel">
						<html:select property="sPolicyType" styleId="select" styleClass="selectBox selectBoxMedium">
							<html:option value="">Select from list</html:option>
							<html:options collection="listPolicyType" property="cacheId" labelProperty="cacheDesc" />
						</html:select>
					</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>					
				</tr>
			</table>
	</fieldset>
	
	<!-- End of parameter grid -->
	<!-- Start of Report Type - PDF/EXCEL list and generate button -->
		<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="center">Report Type 
				<html:select property="sReportFormat"  styleClass="selectBox">
					<html:option value="TXT"/>
					<html:option value="EXCEL"/>
				</html:select>
				&nbsp;				
				<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateCitibankReport();"><u>G</u>enerateReport</button>
				&nbsp;
				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
			</td>
		</tr>
	    </table>
		</div>
	    <html:hidden property="openReport"/>
	    <!-- to open the report with ByteArrayOutputStream object-->
	    <logic:equal name="frmCitiClaimsRpt" property="openReport" value="yes">
	    	<script language="JavaScript">
				 openReport();
			</script>	
	    </logic:equal>			
	<!-- End of Report Type - PDF/EXCEL list and generate button -->
<!-- End of form fields -->	
<input type="hidden" name="mode">

</html:form>



