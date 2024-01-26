<%
/** @ (#) doboclaimsdetailreportparam.jsp
 * Project     : TTK Healthcare Services
 * File        : doboclaimsdetailreportparam.jsp
 * Author      : Balaji C R B
 * Company     : Span Infotech India Pvt. Ltd
 * Date Created: August 5,2008
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
<script language="javascript" src="/ttk/scripts/misreports/doboclaimsdetailreportparam.js"></script>

<%
	pageContext.setAttribute("alTTKOfficeInID", ReportCache.getCacheObject("ttkOfficeInfo"));	
%>

<html:form action="/MISFinanceReportsAction.do">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>DoBo Claims Detail Report Parameters</td>
	</tr>
</table>
<!-- E N D : Page Title -->
<html:errors/>
<!-- Start of form fields -->
	<!-- Start of Parameter grid -->
	<div class="contentArea" id="contentArea">
	<fieldset>
	 <legend>Report Parameters </legend>
	  <table border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
		<tr>
		 <td class="formLabel">Vidal Health Branch:<span class="mandatorySymbol">*</span></td>
		 <td>
    	   <html:select property="tTKBranchCode" styleId="ttkoffice" styleClass="selectBox selectBoxMedium" onchange="onChangeBranch()">
	    	 	<html:option value="">Select from list</html:option>
	            <html:options collection="alTTKOfficeInID" property="cacheId" labelProperty="cacheDesc"/>
		    </html:select>
          </td>
		 <td nowrap>Healthcare Company:</td>
		  <td>
		  <html:select property="insCompanyCode" styleId="comCode" styleClass="selectBox selectBoxLarge" onchange="onChangeInsCompany()">
	       <html:option value="ALL">All</html:option>
	       <logic:notEmpty name="frmMISFinanceReports"  property="alInsCompanyList">
		  	 <html:optionsCollection property="alInsCompanyList" label="cacheDesc" value="cacheId"/>
		   </logic:notEmpty>
          </html:select>
	     </td>
		</tr>
		<tr>
			<td class="formLabel">Do/Bo:</td>
	        <td>
    	    <html:select property="insBoDoCode" styleId="comDoBo" styleClass="selectBox selectBoxMedium" >
		  	 	  <html:option value="ALL">All</html:option>
		  	 	  <logic:notEmpty name="frmMISFinanceReports"  property="alDoBoList">
		          	<html:optionsCollection property="alDoBoList" label="cacheDesc" value="cacheDesc" />
		          </logic:notEmpty>
            </html:select>
            </td>
			<td width="22%" class="formLabel">Cheque From Date:<span class="mandatorySymbol">*</span></td>
			<td>
        		<html:text property="sChequeFromDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="FinanceChequeFromDate" ID="FinanceChequeFromDate" HREF="#" onClick="javascript:show_calendar('FinanceChequeFromDate','frmMISFinanceReports.sChequeFromDate',document.frmMISFinanceReports.sChequeFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" width="24" height="17" border="0" align="absmiddle" ></a>
        	</td>					
			</tr>
			<tr>
			<td width="22%" class="formLabel">Cheque To Date:<span class="mandatorySymbol">*</span></td>
			<td>
        		<html:text property="sChequeToDate" styleClass="textBox textDate" maxlength="10"/>
        		<A NAME="FinanceChequeToDate" ID="FinanceChequeToDate" HREF="#" onClick="javascript:show_calendar('FinanceChequeToDate','frmMISFinanceReports.sChequeToDate',document.frmMISFinanceReports.sChequeToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" width="24" height="17" border="0" align="absmiddle" ></a>
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
				<html:select property="reportFormat"  styleClass="selectBox">
					<html:option value="PDF"/>
					<html:option value="EXCEL"/>
				</html:select>
				&nbsp;				
				<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerateReport</button>
				&nbsp;
				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
			</td>
		</tr>
	    </table>
		</div>
		<html:hidden property="openReport"/>
	    <!-- to open the report with ByteArrayOutputStream object-->
	    <logic:equal name="frmMISFinanceReports" property="openReport" value="yes">
	    	<script language="JavaScript">
				 openReport();
			</script>
	    </logic:equal>
	<!-- End of Report Type - PDF/EXCEL list and generate button -->
<!-- End of form fields -->	
<input type="hidden" name="mode">
</html:form>