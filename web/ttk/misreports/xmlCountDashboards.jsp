<%
/** @ (#) xmlCountDashboards.jsp
 * Project     : TTK Healthcare Services
 * Company     : RCS Technologies
 * Date Created: NOV 17,2017
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
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/xmlCountDashboards.js"></script>



<SCRIPT type="text/javascript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<SCRIPT type="text/javascript">
var submitted = false;

function SubmitTheForm() {

var sStartDate = document.forms[1].sStartDate.value;
var xmlDashboards = document.forms[1].xmlDashboards.value;
if(sStartDate==""||sStartDate==null){
	alert("Please select Fromdate");
	return false;
}
else if(xmlDashboards==""||xmlDashboards==null){
	alert("Please select Dashboards");
return false;
}
else{
		
if(submitted == true) { return; }

document.forms[1].mode.value="doGenerateReport";
//document.forms[0].submit();
document.forms[1].mybutton.value = 'Please wait..';
document.forms[1].mybutton.disabled = true;
submitted = true;
}
}

</SCRIPT>

<%

String fromDate = request.getParameter("sStartDate");
String toDate = request.getParameter("sEndDate");

if(toDate==""||toDate==null){
	Date dNow = new Date( );
    SimpleDateFormat ft = new SimpleDateFormat ("dd/MM/yyyy");
     toDate=ft.format(dNow);
}

%>


<html:errors/>
<html:form action="/XmlCountDashboardsAction.do" onsubmit="return SubmitTheForm();">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td>XML Count Dashboards</td>
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
  
		
	<td nowrap>From Date:<span class="mandatorySymbol">*</span>
		 <html:text property="sStartDate" styleClass="textBox textDate" />
		 <A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmXMLCountDashboardReports.sStartDate',document.frmXMLCountDashboardReports.sStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
		 <img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>	
	
    	<td nowrap>To Date: 
		 <html:text property="sEndDate" styleClass="textBox textDate" />
		 <A NAME="CalendarObjectempDate1" ID="CalendarObjectempDate1" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate1','frmXMLCountDashboardReports.sEndDate',document.frmXMLCountDashboardReports.sEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
		 <img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>	
		

	             <td class="formLabel">Dashboards:<span class="mandatorySymbol">*</span>
		
        		<html:text property="xmlDashboards" styleId="xmlDashboardsID" styleClass="textBox textBoxMedium" maxlength="250" />
        		
        		
        		<a href="#" onClick="openList('xmlDashboardsID','XMLDashboards');" style="display: inline;" id="xmldashboarts"><img src="/ttk/images/EditIcon.gif" alt="Select Dashboard" width="16" height="16" border="0" align="absmiddle"></a>
        		
<!--         		<a href="#" onClick="openXMLDashboards('xmlDashboards','XMLDashboards')" style="display: inline;" id="xmldashboarts"><img src="/ttk/images/EditIcon.gif" alt="Select Dashboard" width="16" height="16" border="0" align="absmiddle"></a> -->
        		
        	</td>
	
	
	</tr>
	
	
	<tr></tr>
	
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
		
		<!-- <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerate</button> -->
		<input type="submit" name="mybutton" value="Generate" class="buttons" onmouseout="this.className='buttons'" onmouseover="this.className='buttons buttonsHover'" />
			&nbsp;
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>
		</td>
	</tr>
    </table>
		
		<logic:match value="ECXD"  name="frmXMLCountDashboardReports"   property="xmlDashboards">
		
	<fieldset>
	 <legend>e-Claim XML Downloading and Bifurcation Dashboard </legend>
	<table  border="1" cellspacing="0" cellpadding="0" class="gridWithCheckBox" style="margin-top: 15PX">
                    <tr>
                        <td  valign="top" class="gridHeader" align="center">DASHBOARD PARAMETERS</td>
                        <td  valign="top" class="gridHeader" align="center">&nbsp;&nbsp;TOTAL&nbsp;&nbsp;</td>
                         <td  valign="top" class="gridHeader" align="center">&nbsp;&nbsp;DHPO&nbsp;&nbsp;</td>
                         <td  valign="top" class="gridHeader" align="center">&nbsp;&nbsp;SHAFAFIYA&nbsp;&nbsp;</td>
                   </tr>
                   <logic:notEmpty name="frmECXD" scope="request">
                  <tr CLASS="gridOddRow">
                 <td  width="55%" class="headLabel">XML Batch files downloaded from Portals (DHPO/Shafafiya)    </td>
                  <td width="15%" align="center" class="valueLabel"><bean:write property="total1" name="frmECXD" scope="request"/></td>
                   <td width="15%" align="center" class="valueLabel"><bean:write property="dhpo1" name="frmECXD" scope="request"/></td>
                    <td width="15%" align="center" class="valueLabel"><bean:write property="shafafiya1" name="frmECXD" scope="request"/></td>
                  </tr>

                   <tr CLASS="gridEvenRow">
                  <td  width="55%" class="headLabel">Claims Downloaded from Portals (DHPO/Shafafiya)</td>
                  <td width="15%" align="center" class="valueLabel"><bean:write property="total2" name="frmECXD" scope="request"/></td>
                   <td width="15%" align="center" class="valueLabel"><bean:write property="dhpo2" name="frmECXD" scope="request"/></td>
                    <td width="15%" align="center" class="valueLabel"><bean:write property="shafafiya2" name="frmECXD" scope="request"/></td>
                 
                   </tr>
                    <tr CLASS="gridOddRow">
                  <td   width="55%"  class="headLabel">Claims Bifurcated to VINGS application</td>
                  <td width="15%" align="center" align="center" class="valueLabel"><bean:write property="total3" name="frmECXD" scope="request"/></td>
                   <td width="15%" align="center" class="valueLabel"><bean:write property="dhpo3" name="frmECXD" scope="request"/></td>
                    <td width="15%" align="center" class="valueLabel"><bean:write property="shafafiya3" name="frmECXD" scope="request"/></td>
                   </tr >
                    <tr CLASS="gridEvenRow">
                  <td   width="55%"   class="headLabel">Claims Bifurcated to Smart Health application</td>
                  <td width="15%" align="center" class="valueLabel"><bean:write property="total4" name="frmECXD" scope="request"/></td>
                   <td width="15%" align="center" class="valueLabel"><bean:write property="dhpo4" name="frmECXD" scope="request"/></td>
                    <td width="15%" align="center" class="valueLabel"><bean:write property="shafafiya4" name="frmECXD" scope="request"/></td>
                   </tr>
                     <tr CLASS="gridOddRow">
                  <!-- <td   width="55%"  class="headLabel">Claims pending for bifurcation for the specified date interval</td> -->
                  <td   width="55%"  class="headLabel">Claims pending for bifurcation from <%=fromDate%> to <%=toDate%></td>
                  <td width="15%" align="center" class="valueLabel"><bean:write property="total5" name="frmECXD" scope="request"/></td>
                   <td width="15%" align="center" class="valueLabel"><bean:write property="dhpo5" name="frmECXD" scope="request"/></td>
                    <td width="15%" align="center" class="valueLabel"><bean:write property="shafafiya5" name="frmECXD" scope="request"/></td>
                   </tr>
                    <tr CLASS="gridEvenRow">
                  <td    width="55%" class="headLabel">Claims pending for bifurcation as on date(From Application beginning date to today date)</td>
                  <td width="15%" align="center" class="valueLabel"><bean:write property="total6" name="frmECXD" scope="request"/></td>
                   <td width="15%" align="center" class="valueLabel"><bean:write property="dhpo6" name="frmECXD" scope="request"/></td>
                    <td width="15%" align="center" class="valueLabel"><bean:write property="shafafiya6" name="frmECXD" scope="request"/></td>
                   </tr>
                   
                    <tr CLASS="gridOddRow">
                  <td    width="55%" class="headLabel">Claims successfully uploaded into VINGS</td>
                  <td width="15%" align="center" class="valueLabel"><bean:write property="eclaimVingsTotal1" name="frmECXD" scope="request"/></td>
                   <td width="15%" align="center" class="valueLabel"><bean:write property="eclaimVingsDHPO1" name="frmECXD" scope="request"/></td>
                    <td width="15%" align="center" class="valueLabel"><bean:write property="eclaimVingsShafafiya1" name="frmECXD" scope="request"/></td>
                   </tr>
                   <tr CLASS="gridEvenRow">
                  <td    width="55%" class="headLabel"> Claims pending for upload into VINGS from <%=fromDate%> to <%=toDate%></td>
                  <td width="15%" align="center" class="valueLabel"><bean:write property="eclaimVingsTotal2" name="frmECXD" scope="request"/></td>
                   <td width="15%" align="center" class="valueLabel"><bean:write property="eclaimVingsDHPO2" name="frmECXD" scope="request"/></td>
                    <td width="15%" align="center" class="valueLabel"><bean:write property="eclaimVingsShafafiya2" name="frmECXD" scope="request"/></td>
                   </tr>
                     <tr CLASS="gridOddRow">
                  <td    width="55%" class="headLabel"> Claims pending for upload into VINGS as on date(From Application beginning date to today date)</td>
                  <td width="15%" align="center" class="valueLabel"><bean:write property="eclaimVingsTotal3" name="frmECXD" scope="request"/></td>
                   <td width="15%" align="center" class="valueLabel"><bean:write property="eclaimVingsDHPO3" name="frmECXD" scope="request"/></td>
                    <td width="15%" align="center" class="valueLabel"><bean:write property="eclaimVingsShafafiya3" name="frmECXD" scope="request"/></td>
                   </tr>
                   
</logic:notEmpty>

    </table>
	
	</fieldset>
	</logic:match>
		
    <br><br>
    
    <logic:match value="EPXD"  name="frmXMLCountDashboardReports"   property="xmlDashboards">
	<fieldset>
	 <legend>e-Preauthorization XML Downloading and Bifurcation </legend>
	 <table  border="1" cellspacing="0" cellpadding="0" class="gridWithCheckBox" style="margin-top: 15PX">
                    <tr>
                        <td  valign="top" class="gridHeader" align="center">DASHBOARD PARAMETERS</td>
                        <td  valign="top" class="gridHeader" align="center">&nbsp;&nbsp;TOTAL&nbsp;&nbsp;</td>
                       
                   </tr>
                   <logic:notEmpty name="frmEPXD" scope="request">
                  <tr CLASS="gridOddRow">

                 <td  width="60%" class="headLabel">Downloaded from DHPO web Portal </td>
                  <td width="40%" align="center" class="valueLabel"><bean:write property="preauthDownloadTotal1" name="frmEPXD" scope="request"/></td>
                   
                  </tr>

                   <tr CLASS="gridEvenRow">
                  <td  width="60%" class="headLabel">Bifurcated to VINGS application</td>
                  <td width="40%" align="center" class="valueLabel"><bean:write property="preauthDownloadTotal2" name="frmEPXD" scope="request"/></td>
                   
                 
                   </tr>
                    <tr CLASS="gridOddRow">
                  <td   width="55%"  class="headLabel">Bifurcated to Smart Health application</td>
                  <td width="40%" align="center" class="valueLabel"><bean:write property="preauthDownloadTotal3" name="frmEPXD" scope="request"/></td>
                  
                   </tr >
                    <tr CLASS="gridEvenRow">
                  <td   width="60%"  class="headLabel">Pending for bifurcation from <%=fromDate%> to <%=toDate%></td>
                  <td width="40%" align="center" class="valueLabel"><bean:write property="preauthDownloadTotal4" name="frmEPXD" scope="request"/></td>
                   
                   </tr>
                     <tr CLASS="gridOddRow">
                  <td   width="60%"  class="headLabel">Pending for bifurcation as on date(From Application beginning date to today date)</td>
                  <td width="40%" align="center" class="valueLabel"><bean:write property="preauthDownloadTotal5" name="frmEPXD" scope="request"/></td>
                  
                   </tr>
                   
                      <tr CLASS="gridEvenRow">
                  <td   width="60%"  class="headLabel">Successfully uploaded into VINGS</td>
                  <td width="40%" align="center" class="valueLabel"><bean:write property="ePreauthVingsTotal1" name="frmEPXD" scope="request"/></td>
                  
                   </tr>
                    <tr CLASS="gridOddRow">
                  <td   width="60%"  class="headLabel">Pending for upload into VINGS from <%=fromDate%> to <%=toDate%></td>
                  <td width="40%" align="center" class="valueLabel"><bean:write property="ePreauthVingsTotal2" name="frmEPXD" scope="request"/></td>
                  
                   </tr>
                    <tr CLASS="gridEvenRow">
                  <td   width="60%"  class="headLabel">Pending for upload into VINGS as on date(From Application beginning date to today date)</td>
                  <td width="40%" align="center" class="valueLabel"><bean:write property="ePreauthVingsTotal3" name="frmEPXD" scope="request"/></td>
                  
                   </tr>
                 
</logic:notEmpty>

    </table>
	</fieldset>
	</logic:match>
     <br><br>
    
     <logic:match value="EPXU"  name="frmXMLCountDashboardReports"   property="xmlDashboards">
	<fieldset>
	 <legend>e-Preauthorization XML Processing and Uploading</legend>
	 
	  <table  border="1" cellspacing="0" cellpadding="0" class="gridWithCheckBox" style="margin-top: 15PX">
                    <tr>
                        <td  valign="top" class="gridHeader" align="center">DASHBOARD PARAMETERS</td>
                        <td  valign="top" class="gridHeader" align="center">&nbsp;&nbsp;TOTAL&nbsp;&nbsp;</td>
                       
                   </tr>
                   <logic:notEmpty name="frmEPXU" scope="request">
                  <tr CLASS="gridOddRow">

                 <td  width="60%" class="headLabel">e-preauths processed (Approved/Rejected) in VINGS </td>
                  <td width="40%" align="center" class="valueLabel"><bean:write property="preauthUploadTotal1" name="frmEPXU" scope="request"/></td>
                  
                  </tr>

                   <tr CLASS="gridEvenRow">
                  <td  width="60%" class="headLabel">e-preauths uploaded to DHPO portal</td>
                  <td width="40%" align="center" class="valueLabel"><bean:write property="preauthUploadTotal2" name="frmEPXU" scope="request"/></td>
                   
                 
                   </tr>
                    <tr CLASS="gridOddRow">
                  <td   width="60%"  class="headLabel">Pending for upload from <%=fromDate%> to <%=toDate%></td>
                  <td width="40%" align="center" class="valueLabel"><bean:write property="preauthUploadTotal3" name="frmEPXU" scope="request"/></td>
                  
                   </tr >
                    <tr CLASS="gridEvenRow">
                  <td   width="60%"  class="headLabel">Pending for upload as on date(From Application beginning date to today date)</td>
                  <td width="40%" align="center" class="valueLabel"><bean:write property="preauthUploadTotal4" name="frmEPXU" scope="request"/></td>
                  
                   </tr>
                 
</logic:notEmpty>

    </table>
	 
	</fieldset>
	</logic:match>
     <br><br>
    
    
     <logic:match value="RAGU"  name="frmXMLCountDashboardReports"   property="xmlDashboards">
	<fieldset>
	 <legend>Remittance Advice Generation and Uploading</legend>
	 
	<table  border="1" cellspacing="0" cellpadding="0" class="gridWithCheckBox" style="margin-top: 15PX">
                    <tr>
                        <td  valign="top" class="gridHeader" align="center">DASHBOARD PARAMETERS</td>
                        <td  valign="top" class="gridHeader" align="center">&nbsp;&nbsp;TOTAL&nbsp;&nbsp;</td>
                         <td  valign="top" class="gridHeader" align="center">&nbsp;&nbsp;DHPO&nbsp;&nbsp;</td>
                         <td  valign="top" class="gridHeader" align="center">&nbsp;&nbsp;SHAFAFIYA&nbsp;&nbsp;</td>
                   </tr>
                   <logic:notEmpty name="frmRAGU" scope="request">
                  <tr CLASS="gridOddRow">

                 <td  width="55%" class="headLabel">Remittance Advice generated in VINGS </td>
                  <td width="15%" align="center" class="valueLabel"><bean:write property="remittanceUploadTotal1" name="frmRAGU" scope="request"/></td>
                   <td width="15%" align="center" class="valueLabel"><bean:write property="remittanceUploadDhpo1" name="frmRAGU" scope="request"/></td>
                    <td width="15%" align="center" class="valueLabel"><bean:write property="remittanceUploadShafafiya1" name="frmRAGU" scope="request"/></td>
                  </tr>

                   <tr CLASS="gridEvenRow">
                  <td  width="55%" class="headLabel">Remittance Advice uploaded to portals (DHPO/Shafafiya)</td>
                  <td width="15%" align="center" class="valueLabel"><bean:write property="remittanceUploadTotal2" name="frmRAGU" scope="request"/></td>
                   <td width="15%" align="center" class="valueLabel"><bean:write property="remittanceUploadDhpo2" name="frmRAGU" scope="request"/></td>
                    <td width="15%" align="center" class="valueLabel"><bean:write property="remittanceUploadShafafiya2" name="frmRAGU" scope="request"/></td>
                 
                   </tr>
                    <tr CLASS="gridOddRow">
                  <td   width="55%"  class="headLabel">Pending for upload from <%=fromDate%> to <%=toDate%></td>
                  <td width="15%" align="center" class="valueLabel"><bean:write property="remittanceUploadTotal3" name="frmRAGU" scope="request"/></td>
                   <td width="15%" align="center" class="valueLabel"><bean:write property="remittanceUploadDhpo3" name="frmRAGU" scope="request"/></td>
                    <td width="15%" align="center" class="valueLabel"><bean:write property="remittanceUploadShafafiya3" name="frmRAGU" scope="request"/></td>
                   </tr >
                    <tr CLASS="gridEvenRow">
                  <td   width="55%"  class="headLabel">Pending for upload as on date(From Application beginning date to today date)</td>
                  <td width="15%" align="center" class="valueLabel"><bean:write property="remittanceUploadTotal4" name="frmRAGU" scope="request"/></td>
                   <td width="15%" align="center" class="valueLabel"><bean:write property="remittanceUploadDhpo4" name="frmRAGU" scope="request"/></td>
                    <td width="15%" align="center" class="valueLabel"><bean:write property="remittanceUploadShafafiya4" name="frmRAGU" scope="request"/></td>
                   </tr>
                   
</logic:notEmpty>
    </table>
	</fieldset>
	</logic:match>
     <br><br>
    
      <logic:match value="MREU"  name="frmXMLCountDashboardReports"   property="xmlDashboards">
	<fieldset>
	 <legend>Member Register XML Endorsements and Uploads</legend>
	

			<table  border="1" cellspacing="0" cellpadding="0" class="gridWithCheckBox" style="margin-top: 15PX">
                    <tr>
                        <td  valign="top" class="gridHeader" align="center">INSURANCE COMPANY</td>
                        <td  valign="top" class="gridHeader" align="center">DASHBOARD PARAMETERS</td>
                        <td  valign="top" class="gridHeader" align="center">&nbsp;&nbsp;TOTAL&nbsp;&nbsp;</td>
                         
                   </tr>
                   <logic:notEmpty name="frmMREU" scope="request">
                  <tr CLASS="gridOddRow">

             <td  width="30%" align="center" class="headLabel">All Insurance Companies </td>
           
            <td  width="30%" class="headLabel" rowSpan="0" colSpan="0">
            <table border="1" cellspacing="0" cellpadding="0" style="margin-top: 0PX ; margin-right: 0px;">
           
                <tr CLASS="gridOddRow">
                      <td  width="40%"   style="margin-right: 20PX;"  class="headLabel">Endorsements (Addition/Deletion/Modification)</td>
                    
                 </tr>
                 <tr CLASS="gridEvenRow">
                    <td   width="40%"  style="margin-right: 20PX;" class="headLabel">Uploaded to DHPO web portal</td>
                </tr>
                <tr CLASS="gridOddRow">
                     <td   width="40%"  style="margin-right: 20PX;" class="headLabel">Pending for upload from <%=fromDate%> to <%=toDate%></td>
                </tr >
                 <tr CLASS="gridEvenRow">
                     <td  width="40%"  style="margin-right: 20PX;"  class="headLabel">Pending for upload as on date(From Application beginning date to today date)</td>
                </tr>
           </table>
           </td>
           
           <td  width="30%" class="headLabel" >
            <table  border="1" cellspacing="0" cellpadding="0" class="gridWithCheckBox" style="margin-top: 0PX ; margin-right: 0PX;">
           
                <tr CLASS="gridOddRow">
                      <td  width="30%" align="center" class="valueLabel"><bean:write property="allInsuranceTotal1" name="frmMREU" scope="request"/></td>
                    
                 </tr>
                 <tr CLASS="gridEvenRow">
                  <td  width="30%" align="center" class="valueLabel"><bean:write property="allInsuranceTotal2" name="frmMREU" scope="request"/></td>
                </tr>
                <tr CLASS="gridOddRow">
                    <td  width="30%" align="center" class="valueLabel"><bean:write property="allInsuranceTotal3" name="frmMREU" scope="request"/></td>
                </tr >
                 <tr CLASS="gridEvenRow">
                  <td  width="30%" align="center" class="valueLabel"><bean:write property="allInsuranceTotal4" name="frmMREU" scope="request"/></td>
                </tr>
           </table>
           </td>    
           </tr>

                   <tr CLASS="gridEvenRow">
                  <td  width="30%" align="center" class="headLabel">Oriental Insurance</td>
                  
                 <td  width="30%" class="headLabel" rowSpan="0" colSpan="0">
           <table  border="1" cellspacing="0" cellpadding="0"  style="margin-top: 0PX ; margin-right: 0PX;">
           
           
            <tr CLASS="gridOddRow">
                      <td   width="40%" class="headLabel">Endorsements (Addition/Deletion/Modification)</td>
                </tr>
                 <tr CLASS="gridEvenRow">
                    <td  width="40%"  class="headLabel">Uploaded to DHPO web portal</td>
                </tr>
                <tr CLASS="gridOddRow">
                     <td  width="40%" class="headLabel">Pending for upload from <%=fromDate%> to <%=toDate%></td>
                </tr >
                 <tr CLASS="gridEvenRow">
                     <td  width="40%"  class="headLabel">Pending for upload as on date(From Application beginning date to today date)</td>
                </tr>
           </table>
           </td>
           
            <td  width="30%" class="headLabel" >
           <table  border="1" cellspacing="0" cellpadding="0" class="gridWithCheckBox" style="margin-top: 0PX; margin-right: 0PX;">
           
           
            <tr CLASS="gridOddRow">
                    <td  width="30%" align="center" class="valueLabel"><bean:write property="orientalInsTotal1" name="frmMREU" scope="request"/></td>
                </tr>
                 <tr CLASS="gridEvenRow">
                  <td  width="30%" align="center" class="valueLabel"><bean:write property="orientalInsTotal2" name="frmMREU" scope="request"/></td>
                </tr>
                <tr CLASS="gridOddRow">
                    <td  width="30%" align="center" class="valueLabel"><bean:write property="orientalInsTotal3" name="frmMREU" scope="request"/></td>
                </tr >
                 <tr CLASS="gridEvenRow">
                  <td  width="30%" align="center" class="valueLabel"><bean:write property="orientalInsTotal4" name="frmMREU" scope="request"/></td>
                </tr>
           </table>
           </td>
                </tr>
                   
                    <tr CLASS="gridOddRow" >
                  <td   width="30%" align="center"  class="headLabel">ASCANA Insurance</td>
                  
                  <td  width="30%" class="headLabel" rowSpan="0" colSpan="0">
             <table  border="1" cellspacing="0" cellpadding="0"  style="margin-top: 0PX; margin-right: 0PX;">
           
           
             <tr CLASS="gridOddRow">
                      <td  width="40%" class="headLabel">Endorsements (Addition/Deletion/Modification)</td>
                </tr>
                 <tr CLASS="gridEvenRow">
                    <td  width="40%"  class="headLabel">Uploaded to DHPO web portal</td>
                </tr>
                <tr CLASS="gridOddRow">
                     <td  width="40%"  class="headLabel">Pending for upload from <%=fromDate%> to <%=toDate%></td>
                </tr >
                 <tr CLASS="gridEvenRow">
                     <td  width="40%" class="headLabel">Pending for upload as on date(From Application beginning date to today date)</td>
                </tr>
           </table>
           </td>
           
           
            <td  width="30%" class="headLabel" >
             <table  border="1" cellspacing="0" cellpadding="0" class="gridWithCheckBox" style="margin-top: 0PX; margin-right: 0PX;">
           
           
             <tr CLASS="gridOddRow">
                    <td  width="30%" align="center" class="valueLabel"><bean:write property="ascanaInsTotal1" name="frmMREU" scope="request"/></td>
                </tr>
                 <tr CLASS="gridEvenRow">
                  <td  width="30%" align="center" class="valueLabel"><bean:write property="ascanaInsTotal2" name="frmMREU" scope="request"/></td>
                </tr>
                <tr CLASS="gridOddRow">
                    <td  width="30%" align="center" class="valueLabel"><bean:write property="ascanaInsTotal3" name="frmMREU" scope="request"/></td>
                </tr >
                 <tr CLASS="gridEvenRow">
                  <td   width="30%" align="center" class="valueLabel"><bean:write property="ascanaInsTotal4" name="frmMREU" scope="request"/></td>
                </tr>
           </table>
           </td>
                 
            </tr >
                   
</logic:notEmpty>
    </table>
	</fieldset>
	</logic:match>
     <br><br>
    
      <logic:match value="ASPS"  name="frmXMLCountDashboardReports"   property="xmlDashboards">
	<fieldset>
	 <legend>Ageing Summary of all the pending status XMLs</legend>
   
   <table  border="1" cellspacing="0" cellpadding="0" class="gridWithCheckBox" style="margin-top: 15PX">
                    <tr>
                        <td  valign="top" class="gridHeader" align="center">DASHBOARD PARAMETERS</td>
                        <td  valign="top" class="gridHeader" align="center">&nbsp;&nbsp;TOTAL&nbsp;&nbsp;</td>
                         <td  valign="top" class="gridHeader" align="center">0-1 days<br>(0-24 Hrs)</td>
                          <td  valign="top" class="gridHeader" align="center">1-2 days<br>(24.01-48 Hrs)</td>
                           <td  valign="top" class="gridHeader" align="center">2-4 days<br>(48.01-96 Hrs)</td>
                            <td  valign="top" class="gridHeader" align="center">Above 4 days<br>(Above 96.01 Hrs)</td>
                   </tr>
                   
                   <logic:notEmpty name="frmEclaimASPS" scope="request">
                  <tr CLASS="gridOddRow">

                 <td  width="50%" class="headLabel">DHPO e-claims pending for bifurcation</td>
                  <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummarytotal1" name="frmEclaimASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary24hrs1" name="frmEclaimASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary48hrs1" name="frmEclaimASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary96hrs1" name="frmEclaimASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummaryAbove96hrs1" name="frmEclaimASPS" scope="request"/></td>
                  </tr>

                   <tr CLASS="gridEvenRow"  style="border-bottom:5px solid black;border-width: thin;">
                  <td  width="50%" class="headLabel">Shafafiya e-claims pending for bifurcation</td>
                  <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummarytotal2" name="frmEclaimASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary24hrs2" name="frmEclaimASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary48hrs2" name="frmEclaimASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary96hrs2" name="frmEclaimASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummaryAbove96hrs2" name="frmEclaimASPS" scope="request"/></td>
                 
                   </tr>
                  </logic:notEmpty>
                   
                    <logic:notEmpty name="frmEclaimVingsASPS" scope="request">
                    <tr CLASS="gridOddRow" >
                  <td   width="50%"  class="headLabel">DHPO e-claims pending for upload into VINGS</td>
                  <td width="10%" align="center" class="valueLabel"><bean:write property="ageingVingsDhpoTotal1" name="frmEclaimVingsASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingVingsDhpo24hrs" name="frmEclaimVingsASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingVingsDhpo48hrs" name="frmEclaimVingsASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingVingsDhpo96hrs" name="frmEclaimVingsASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingVingsDhpoAbove96hrs" name="frmEclaimVingsASPS" scope="request"/></td>
                   </tr >
                    <tr CLASS="gridEvenRow"  style="border-bottom:5px solid black; border-width: thin;">
                  <td   width="50%"  class="headLabel">Shafafiya e-calims pending for upload into VINGS</td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingVingsShafafiyaTotal1" name="frmEclaimVingsASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingVingsShafafiya24hrs" name="frmEclaimVingsASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingVingsShafafiya48hrs" name="frmEclaimVingsASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingVingsShafafiya96hrs" name="frmEclaimVingsASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingVingsShafafiyaAbove96hrs" name="frmEclaimVingsASPS" scope="request"/></td>
                   </tr>
                     </logic:notEmpty>
                   
                    <logic:notEmpty name="frmEpreauthSPS" scope="request">
                    <tr CLASS="gridOddRow" >
                  <td   width="50%"  class="headLabel">DHPO e-preauths pending for bifurcation</td>
                  <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummarytotal3" name="frmEpreauthSPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary24hrs3" name="frmEpreauthSPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary48hrs3" name="frmEpreauthSPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary96hrs3" name="frmEpreauthSPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummaryAbove96hrs3" name="frmEpreauthSPS" scope="request"/></td>
                   </tr >
                    <tr CLASS="gridEvenRow"  style="border-bottom:5px solid black; border-width: thin;">
                  <td   width="50%"  class="headLabel">DHPO e-preauths pending for upload into VINGS</td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummarytotal4" name="frmEpreauthSPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary24hrs4" name="frmEpreauthSPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary48hrs4" name="frmEpreauthSPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary96hrs4" name="frmEpreauthSPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummaryAbove96hrs4" name="frmEpreauthSPS" scope="request"/></td>
                   </tr>
                   
                    <!-- deepthi6 -->
                    <tr CLASS="gridOddRow"  style="border-bottom:5px solid black; border-width: thin;">
                  <td   width="50%"  class="headLabel">E-preauths pending for upload into DHPO web portal</td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="webPortalEperauthDhpoTotal" name="frmEpreauthSPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="webPortalEperauthDhpo24hrs" name="frmEpreauthSPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="webPortalEperauthDhpo48hrs" name="frmEpreauthSPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="webPortalEperauthDhpo96hrs" name="frmEpreauthSPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="webPortalEperauthDhpoAbove96hrs" name="frmEpreauthSPS" scope="request"/></td>
                   </tr>
                   
                     </logic:notEmpty>
                   
                   
                    <logic:notEmpty name="frmRemittanceASPS" scope="request">
                   <tr CLASS="gridEvenRow">

                 <td  width="50%" class="headLabel">DHPO Remittance Advice pending for uploads</td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummarytotal5" name="frmRemittanceASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary24hrs5" name="frmRemittanceASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary48hrs5" name="frmRemittanceASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary96hrs5" name="frmRemittanceASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummaryAbove96hrs5" name="frmRemittanceASPS" scope="request"/></td>
                  </tr>

                   <tr CLASS="gridOddRow"   style="border-bottom:5px solid black;border-width: thin;">
                  <td  width="50%" class="headLabel">Shafafiya Remittance Advice pending for uploads</td>
                  <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummarytotal6" name="frmRemittanceASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary24hrs6" name="frmRemittanceASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary48hrs6" name="frmRemittanceASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary96hrs6" name="frmRemittanceASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummaryAbove96hrs6" name="frmRemittanceASPS" scope="request"/></td>
                 
                   </tr>
                    </logic:notEmpty>
                   
                   <logic:notEmpty name="frmMemberASPS" scope="request">
                    <tr CLASS="gridEvenRow">
                  <td   width="50%"  class="headLabel">Member Upload pending for Oriental Insurance</td>
                  <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummarytotal7" name="frmMemberASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary24hrs7" name="frmMemberASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary48hrs7" name="frmMemberASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary96hrs7" name="frmMemberASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummaryAbove96hrs7" name="frmMemberASPS" scope="request"/></td>
                   </tr >
                    <tr CLASS="gridOddRow"  style="border-bottom:5px solid black;border-width: thin;">
                  <td   width="50%"  class="headLabel">Member Upload pending for ASCANA Insurance</td>
                  <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummarytotal8" name="frmMemberASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary24hrs8" name="frmMemberASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary48hrs8" name="frmMemberASPS" scope="request"/></td>
                   <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummary96hrs8" name="frmMemberASPS" scope="request"/></td>
                    <td width="10%" align="center" class="valueLabel"><bean:write property="ageingsummaryAbove96hrs8" name="frmMemberASPS" scope="request"/></td>
                   </tr>
                    </logic:notEmpty>
    </table>
	</fieldset>
	</logic:match>
    <br><br><br><br><br><br><br><br><br><br>
		</div>
		
	<!-- End of Report Type - PDF/EXCEL list and generate button -->
<!-- End of form fields -->	
<input type="hidden" name="mode">
</html:form>