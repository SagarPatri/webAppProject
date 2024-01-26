<%
/** @ (#) accenturereportparam.jsp 
 * Project     : TTK Healthcare Services
 * File        : accenturereportparam.jsp
 * Author      : Balaji C R B
 * Company     : Span Systems Corporation
 * Date Created: October 17, 2008
 *
 * @author 		 : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/support/courierreportparam.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<%
	pageContext.setAttribute("sCourierName",Cache.getCacheObject("courierName"));
	pageContext.setAttribute("sOfficeInfo",Cache.getCacheObject("officeInfo"));
	pageContext.setAttribute("sCourierType",Cache.getCacheObject("courierType"));
	pageContext.setAttribute("departmentID",Cache.getCacheObject("departmentID"));
%>
<html:form action="/CourierReportAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td>Courier Report</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Form Fields -->
    
	<fieldset>
		<legend>Report Parameters </legend>
			<table border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
				<tr>
					<td width="22%"  nowrap>Courier Type:<span class="mandatorySymbol">*</span></td>
        		    <td width="29%" class="formLabel">
        		       <html:select property="sCourierType" styleClass="selectBox selectBoxMedium">
	        		  <html:options collection="sCourierType" property="cacheId" labelProperty="cacheDesc"/>
	    		      </html:select>
                 	</td>
				      <td width="22%"  nowrap>Courier Name :<br>
		            	<td width="29%" class="formLabel">
		            		<html:select property="sCourierName" styleClass="selectBox selectBoxMedium" >
			  					<html:option value="">Any</html:option>
			  					<html:optionsCollection name="sCourierName" label="cacheDesc" value="cacheId" />
		           			</html:select>
		            	
                     </td>
				</tr>
				<tr>
				    	   <td width="22%" nowrap class="formLabel">Department: </td>
				           <td width="29%" class="formLabel">
				            <html:select property="sDepartment" styleClass="selectBox selectBoxMedium" >
							        <html:option value="">Select from list</html:option>
				                  		<html:options collection="departmentID" property="cacheId" labelProperty="cacheDesc"/>
				              		</html:select>
				              </td>
				    	
				    	      <td width="22%" nowrap> Branch:</td>
					       <td width="29%" class="formLabel">
					        	<html:select property="sOfficeInfo" styleClass="selectBox selectBoxMedium">
					    			<html:option value="">Any</html:option>
					        		<html:options collection="sOfficeInfo" property="cacheId" labelProperty="cacheDesc"/>
					    		</html:select>
				    	      </td>
				</tr>
				<tr>
					  <td nowrap>Report From:<span class="mandatorySymbol">*</span></td>
				   		<td width="29%" class="formLabel">
				   		<html:text property="sStartDate" styleClass="textBox textDate" maxlength="10"/>
				  		<A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmCourierReportList.sStartDate',document.frmCourierReportList.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
		  			  </td>
					  	<td nowrap>Report To:<span class="mandatorySymbol">*</span><br></td>
					  	<td width="29%" class="formLabel">
					   		<html:text property="sEndDate" styleClass="textBox textDate" maxlength="10"/>
					  		<A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmCourierReportList.sEndDate',document.frmCourierReportList.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="docDispatchDate" width="24" height="17" border="0" align="absmiddle" ></a>
					  	</td>
				       
				</tr>
			</table>
	</fieldset>
	<!-- E N D : Form Fields -->
	<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="center">Report Type 
				<select name="reportType" class="selectBox" id="reporttype">
					<option value="PDF">PDF</option>
					<option value="EXL">EXCEL</option>
				</select> &nbsp;
				<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateCourierReport();"><u>G</u>enerateReport</button>
				&nbsp;
				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
			</td>
		</tr>
	</table>
	<!-- End of Reports List -->
	</div>	

	<input type="hidden" name="mode" value="" />
	<input type="hidden" name="reportID" value="" />
</html:form>