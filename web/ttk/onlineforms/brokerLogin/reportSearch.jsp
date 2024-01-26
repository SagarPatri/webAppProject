<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<head>
<style type="text/css">

table#textTable{
margin-left: 20px;
width: 500px;
display: inline;
}
table#textTable,table#textTable tr td{
border: 1px solid white;
background-color: #F0F0F0;
border-collapse: collapse;
}

table#textTable tr td{
text-align:left;
font-size: 18px;
padding: 20px;
}
#hexagonLinks{
display: inline;
float: right;
list-style: none;
}
#onlineTableTD{
text-align: left;
font-size: 14px;
}
#onlineSearchEnterTable{
border: none;
padding: 0;
}
#onlineTableTR{
padding: 4px;
}
</style>
<script type="text/javascript" src="/ttk/scripts/validation.js"></script>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/onlineforms/brokerLogin/reportSearch.js"></script>
</head>
<html:form action="/BrokerReportsAction.do" >
<html:errors/>
<div id="sideHeading"><bean:write name="frmBroReports" property="reportLabel"/></div>
<br><br>
<div style="border: 1px solid gray;border-radius: 20px;padding: 10px 20px 10px 20px;background-color: #F8F8F8;width: 63%;">
	<table id="onlineSearchEnterTable">
	<logic:notMatch value="BroTATFSINDIVIDUAL" property="reportID" name="frmBroReports">
	<tr id="onlineTableTR">
	  <td id="onlineTableTD">Corporate Name:</td>
	  <td id="onlineTableTD">Policy Number:</td>
	  <td id="onlineTableTD">Policy Period:</td>	 
	 </tr>
	 <tr>	 
	  <td>
	  <html:select property="corporateName" name="frmBroReports" styleClass="selectBoxWebLoginSize1" onchange="getPolicyNumberList();">
	  <html:option value="">-----ALL-----</html:option>
	  <html:optionsCollection name="corporateNameList" label="cacheDesc" value="cacheId" />
	  </html:select>	  
	  </td>
	  <td>
	  <html:select property="policyNumber" name="frmBroReports" styleClass="selectBoxWebLoginSize1" onchange="getPolicyPeriodList();">
	   <html:option value="">-----ALL-----</html:option>
	  <html:optionsCollection name="policyNumberList" label="cacheDesc" value="cacheId" />
	  </html:select>
	  </td>
	  <td>
	  <html:select property="policyPeriod" name="frmBroReports" styleClass="selectBoxWebLoginSize1">
	   <html:option value="">-----ALL-----</html:option>
	  <html:optionsCollection name="policyPeriodList" label="cacheDesc" value="cacheId" />
	  </html:select>	  
	  </td>
	  </tr>
	</logic:notMatch>
	<logic:match value="BroTATFSINDIVIDUAL" property="reportID" name="frmBroReports">
	<tr id="onlineTableTR">	  
	  <td id="onlineTableTD">Policy Number:</td>
	  <td id="onlineTableTD">Policy Period:</td>
	  <td id="onlineTableTD"></td>	 
	 </tr>
	 <tr> 
	  <td>
	  <html:select property="policyNumber" name="frmBroReports" styleClass="selectBoxWebLoginSize1" onchange="getPolicyPeriodList();">
	   <html:option value="">-----ALL-----</html:option>
	  <html:optionsCollection name="policyNumberList" label="cacheDesc" value="cacheId" />
	  </html:select>
	  </td>
	  <td>
	  <html:select property="policyPeriod" name="frmBroReports" styleClass="selectBoxWebLoginSize1">
	   <html:option value="">-----ALL-----</html:option>
	  <html:optionsCollection name="policyPeriodList" label="cacheDesc" value="cacheId" />
	  </html:select>	  
	  </td>
	  <td></td>
	  </tr>
	</logic:match>
	 <tr id="onlineTableTR">	 
	 <logic:match value="BroTechnicalResultReport" property="reportID" name="frmBroReports"> 
	 <td id="onlineTableTD">IBANNumber:</td>
	 </logic:match>
	  <logic:notMatch value="TechnicalResultReport" property="reportID" name="frmBroReports">
	 <!-- <td id="onlineTableTD">Member ID:</td> -->
	 </logic:notMatch>
	  <td id="onlineTableTD">From Date:<span class="mandatorySymbol">*</span></td>
	  <td id="onlineTableTD">To Date:<span class="mandatorySymbol">*</span></td>	 
	 </tr>
	 <tr>
	 <logic:match value="BroTechnicalResultReport" property="reportID" name="frmBroReports">
	 <td><html:text property="IBANNumber" name="frmBroReports" styleClass="textBox textBoxMedLarge"/></td>
	 </logic:match>
	  <logic:notMatch value="BroTechnicalResultReport" property="reportID" name="frmBroReports">
	<%-- <td><html:text property="memberID" name="frmBroReports" styleClass="textBox textBoxMedLarge"/></td> --%> 
	 </logic:notMatch>
	  
	  
	  <td>
	  <table cellspacing="0" cellpadding="0">
    	<tr>
    		<td><html:text name="frmBroReports" property="fromDate" styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmBroReports.fromDate',document.frmBroReports.fromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;</td>
    	</tr>
     </table>
	  </td>
	  <td>
	  <table cellspacing="0" cellpadding="0">
    	<tr>
    		<td><html:text name="frmBroReports" property="toDate" styleClass="textBox textDate" maxlength="10"/><A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmBroReports.toDate',document.frmBroReports.toDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;</td>
    	<td>    	
    	<table>
	  <tr>
	  <td>
	  <img title="Generate Reports" src="/ttk/images/broker/small_hexagon.png" height="18" width="16">
	  </td>
	  <td id="onlineTableTD"><a title="Generate Reports" accesskey="G" id="imgAncSmall" href="#" onclick="javascript:onGenerateReports();"><span class="accessLetter">G</span>enerate Report</a></td>
	  </tr>
	  </table>
    	</td>
    	</tr>
     </table>
	  </td>
	</table>
</div>
<br>
	<table align="center">
	<tr>
	<td><img title="Back" src="/ttk/images/broker/small_hexagon.png" height="48" width="46"></td>
	<td><a id="imgAncMedium" onclick="onBack('Home');" href="#" title="Back">Back</a></td>
	</tr>
	</table>
	
<input type="hidden" name="mode">
<input type="hidden" name="child" value="">
<input type="hidden" name="corporateNameText" value="">
<html:hidden property="reportID"/>
<html:hidden property="reportLabel"/>
</html:form>
