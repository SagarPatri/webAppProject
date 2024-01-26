<%
/** @ (#) checkEligibilityLog.jsp
 * Project     : TTK Healthcare Services
 * Company     : RCS Technologies
 * Date Created: OCT 26,2018
 * @author 		 : Deepthi Meesala
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ page import = "java.io.*,java.util.*" %>
<%@ page import = "javax.servlet.*,java.text.*" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.misreports.ReportCache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<link rel="stylesheet" type="text/css" href="css/style.css" />
	<link rel="stylesheet" type="text/css" href="css/autoComplete.css" />
	<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
	<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/checkEligibilityLog.js"></script>



<SCRIPT type="text/javascript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<SCRIPT type="text/javascript">

<%
   // boolean viewmode=true;
   boolean viewmode=false;
	
   // pageContext.setAttribute("hospitalName", ReportCache.getCacheObject("hospitalNameInfo"));
    
    
    pageContext.setAttribute("inscmpnyNameList", ReportCache.getCacheObject("inscmpnyNameList"));
    
    pageContext.setAttribute("sStatus", Cache.getCacheObject("preauthStatus"));
    pageContext.setAttribute("alCorporateList", Cache.getCacheObject("CorporateList"));
%>

</SCRIPT>




<html:errors/>
<html:form action="/CheckEligibilityReport.do" onsubmit="return SubmitTheForm();">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>Member Eligibility checked from provider login</td>
	</tr>
</table>
<!-- E N D : Page Title -->

<!-- Start of form fields -->
	<!-- Start of Parameter grid -->
	<div class="contentArea" id="contentArea">
	<fieldset>
	 <legend>Report Parameters </legend>
	  <table border="0" align="center" cellpadding="0" cellspacing="0" class="searchContainer">
	<tr></tr>
	
	<tr></tr>
	
        
    <tr>
  
		
	<td class="formLabel"> Authority: 
			   					<html:select property ="payerCodes" styleId="payerCodes" styleClass="selectBox selectBoxMedium" onchange="getProviderList()">
									<html:option value="CMA">CMA</html:option>
									<%-- <html:option value="">Select from list</html:option> 
									<html:option value="DHA">DHA</html:option>
					     			<html:option value="HAAD">HAAD</html:option>
					     			<html:option value="MOH">MOH</html:option> --%>
								</html:select>
							</td>



 <td class="formLabel">Provider Name:
          
           
           <html:select property="sHospitalName" styleId="hosName" styleClass="selectBox selectBoxMoreLargest" >
		  	 	  <html:option value="">Select from list
		  	 	  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	 	   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	 	    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	 	     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	 	     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	 	     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  	 	     </html:option> 
		  	  <logic:notEmpty name="frmMISReports" property="alInsuranceCompany">
  							<html:optionsCollection name="frmMISReports" property="alInsuranceCompany" label="cacheDesc" value="cacheId" />
  							</logic:notEmpty>
		  	   
            </html:select>
	    </td>
	
	
	 <td class="formLabel">Enrollment ID:
        		   <html:text property="sEnrolmentId" styleId="sEnrolmentId" styleClass="textBox textBoxMedium" maxlength="250" />
        	</td>
	
	</tr>
	
	<tr>
	
 <td class="formLabel">Insurance Company Name:
          
           
           <html:select property="insCompanyName" styleId="hosName" styleClass="selectBox selectBoxMoreLargest" >
		  	 	  <html:option value="">Select from list </html:option> 
					<html:options collection="inscmpnyNameList" property="cacheId" labelProperty="cacheDesc"/>
						<%-- 	<logic:notEmpty name="frmMISReports" property="alInsuranceCompany">
  							<html:optionsCollection name="frmMISReports" property="alInsuranceCompany" label="cacheDesc" value="cacheId" />
  							</logic:notEmpty> --%>	
			
            </html:select>
	    </td>
	
	
	<td class="formLabel">From Date: <span class="mandatorySymbol">*</span>
	    			
           <html:text property="chkFromDate" styleClass="textBox textDate" maxlength="10" />
           <a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmMISReports.chkFromDate',document.frmMISReports.chkFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
	    						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
	    					</a>
           
           </td>
           

         
           <td class="formLabel">To Date: <span class="mandatorySymbol">*</span>
	    			
           <html:text property="chkToDate" styleClass="textBox textDate" maxlength="10" />
           <a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmMISReports.chkToDate',document.frmMISReports.chkToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
	    						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" title="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
	    					</a>
           
           </td>  
	
	
	
	</tr>
	
	<tr></tr>
	
	<tr></tr>
	
	<tr></tr>
	
	</table>
	
	</fieldset>
	
	<!-- End of parameter grid -->
	<!-- Start of Report Type - PDF/EXCEL list and generate button -->
	<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%" align="center">
		
		<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerate</button> 
		<!-- <input type="submit" name="mybutton" value="Generate" class="buttons" onmouseout="this.className='buttons'" onmouseover="this.className='buttons buttonsHover'" /> -->
			&nbsp;
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
			
		</td>
	</tr>
    </table>
		
		
		</div>
		
	<!-- End of Report Type - PDF/EXCEL list and generate button -->
<!-- End of form fields -->	
<input type="hidden" name="mode">
<input type="hidden" name="parameterValues">
<input type="hidden" name="forwardValue" value="mailTrigger">
 <html:hidden property="letterPath" name="frmMISReports" />

</html:form>