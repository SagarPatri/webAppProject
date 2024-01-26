<%
/** @ (#) revenueReport.jsp
 * Project     : TTK Healthcare Services
 * File        : revenueReport.jsp
 * Author      : Lohith.M
 * Company     : RCS Technologies
 * Date Created: January 5,2017
 * @author 		 :Lohith.M
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%
	pageContext.setAttribute("insuranceCompany", Cache.getCacheObject("insuranceCompany"));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/revenueReport.js"></script>

<%
	pageContext.setAttribute("alInsCompanyList", Cache.getCacheObject("InsComp"));
	pageContext.setAttribute("alProductList", Cache.getCacheObject("productList"));
	pageContext.setAttribute("alCorporateList", Cache.getCacheObject("CorporateList"));
	pageContext.setAttribute("alDataType", Cache.getCacheObject("DataType"));
%>


<html:form action="/MISFinanceReportsAction.do">
<table class="pageTitle">
<tr>
<td>
${reportname}
</td>
</tr>
</table>
<div class="contentArea" id="contentArea">
<fieldset> 
	 <legend>${reptype}</legend>
  <table border="0" align="center" cellpadding="0" cellspacing="0" class="searchContainer">
 	 <tr>
	<td> </td>
	</tr>
	<tr>
		 <td align="left" nowrap>Insurance Company Name:<br>
	               <html:select property="insCompanyName" styleId="insCompanyName" styleClass="selectBox selectBoxMedium">
	    		  	 	<html:option value="">Select from list</html:option>
	    		  	 	<html:optionsCollection name="alInsCompanyList" label="cacheDesc" value="cacheId"/>
	                </html:select>
	    </td>
	    
	    <td align="left" nowrap>Product/Category Name:<br>
			<html:select property="productName" styleId="productName" styleClass="selectBox selectBoxMedium">
				    		  	 	  <html:option value="">Select From List</html:option>
					  				  <html:optionsCollection name="alProductList" label="cacheDesc" value="cacheId"/>
			</html:select>
		</td>
		
		 <td align="left" nowrap>Policy No:<br>
			<html:text property="policyNo" styleId="policyNo" styleClass="textBox textBoxMedium"/>
		</td>
		<td align="left" nowrap>Corporate Name:<br>
			<html:select property="corpName" styleId="corpName" styleClass="selectBox selectBoxMedium">
				    		  	 	  <html:option value="">Select From List</html:option>
					  				  <html:optionsCollection name="alCorporateList" label="cacheDesc" value="cacheId"/>
			</html:select>
		</td>
	</tr>

	<tr>
			<td align="left" nowrap>Data Type:<br>
			               <html:select property="dataType" styleId="dataType" styleClass="selectBox selectBoxMedium">
			    		  	 	<html:option value="">Any</html:option>
			    		  	 	<html:optionsCollection name="alDataType" label="cacheDesc" value="cacheId"/>
			                </html:select>
			</td>
		 	<td align="left">(Member Added)From Date:<br>
				<html:text property="sChequeFromDate" styleClass="textBox textDate" maxlength="10" value=""/>
				<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmMISFinanceReports.sChequeFromDate',document.frmMISFinanceReports.sChequeFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
					<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
				</a>
		    </td>
		    <td align="left">(Member Added)To Date:<br>
			<html:text property="sChequeToDate" styleClass="textBox textDate" maxlength="10" value=""/>
				<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmMISFinanceReports.sChequeToDate',document.frmMISFinanceReports.sChequeToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
					<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
				</a>
		    </td>
 	</tr>
</table>


<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
<tr>
<td width="100%" align="center">

<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onclick="javascript:onGenerateRevenueReport();"><u>G</u>enerate Report</button> &nbsp;



<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
</td>
</tr>
</table>
</fieldset>
</div>
<input type="hidden" name="mode">
</html:form>
