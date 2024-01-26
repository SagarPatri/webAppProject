<%
/** @ (#) reportparameters.jsp July 4, 2006
 * Project     : TTK Healthcare Services
 * File        : reportparameters.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: Feb 2, 2006
 *
 * @author 		 : Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript">
function onClose()                 
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/ReportsAction.do";
	document.forms[1].submit();
}//end of onClose()

function toUpperCase(value,id,name)
{
	var NumElements=document.forms[1].elements.length;
	for(var i=0;i<NumElements;i++)
		{
		       if(document.forms[1].elements[i].name==name)
			{
			document.forms[1].elements[i].value=value.toUpperCase();
			}
		}//end if  for(int i=0;i<NumElements;i++)
}//end of function toUpperCase()
function onGenerateReport()
{
	trimForm(document.forms[1]);
	var regexp=new RegExp("^(mandatory){1}[0-9]{1,}$");
	for(i=0;i<document.forms[1].length;i++)
	{
		if(regexp.test(document.forms[1].elements[i].id) && document.forms[1].elements[i].value=="")
		{
			alert("Please enter the mandatory fields");
			document.forms[1].elements[i].focus();
			return false;
		}
	}

	var NumElements=document.forms[1].elements.length;
	var parameterValue= new String("|S|");
	//Shortfall CR KOC1179 Claim Shortfall Summary Rpt
	if(document.forms[1].reportID.value == "PenCaseRpt" || document.forms[1].reportID.value == "CCSRpt" || document.forms[1].reportID.value == "SoftCopyUploadError" || document.forms[1].reportID.value == "ClaimShortfallSummary")
		var parameterValue= new String("|");				
	else
		var parameterValue= new String("|S|");
	for(n=0; n<NumElements;n++)
	{
		if(document.forms[1].elements[n].type=="text")
		{
			 if(document.forms[1].elements[n].className=="textBox textDate")
			 {
			 	if(trim(document.forms[1].elements[n].value).length>0)
				{
					if(isDate(document.forms[1].elements[n],"Date")==false)
					{
						document.forms[1].elements[n].focus();
						return false;
					}//end of if(isDate(document.forms[1].elements[n],"Date")==false)
				}//end of if(trim(document.forms[1].elements[n].value).length>0)
			 }//end of if(document.forms[1].elements[n].className=="textBox textDate")
			 else {
			 	toUpperCase(document.forms[1].elements[n].value,document.forms[1].elements[n].id,document.forms[1].elements[n].name)
			 }//end of else
			 parameterValue+= document.forms[1].elements[n].value;
			 parameterValue+="|";
		}//end of if(document.forms[1].elements[n].type=="text")

		if(document.forms[1].elements[n].type=="select-one")
		{
			if(document.forms[1].elements[n].name!="reportType")
			{
				parameterValue+= document.forms[1].elements[n].value;
				parameterValue+="|";
			}//end of if(document.forms[1].elements[n].name!="reportType")
		}//end of if(document.forms[1].elements[n].type=="select-one")
	}//end of for(n=0; n<NumElements;n++)


	document.forms[1].parameterValues.value=parameterValue;
	document.forms[1].mode.value="doGenerateReport";
	if(document.forms[1].reportID.value == "FinBanRec")
	{
		var month = document.forms[1].Month.value;
		var year = document.forms[1].Year.value;
		var partmeter = "?mode=doGenerateFinReport&parameter="+parameterValue+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&month="+month+"&year="+year;
	}//end of if(document.forms[1].reportID.value == "FinBanRec")
	else if(document.forms[1].reportID.value == "EnrDaiBOI")
	{
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&policyNumber="+document.forms[1].Policy_Number.value;
	}//end of else if(document.forms[1].reportID.value == "EnrDaiBOI")
	else if(document.forms[1].reportID.value == "EndrImpactReport")
	{
			var startDate = document.forms[1].Start_Date.value;
			var endDate = document.forms[1].End_Date.value;
	    	var partmeter = "?mode=doGenerateImpactReport&parameter="+parameterValue+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate;
	}//end of else if(document.forms[1].reportID.value == "EndrImpactReport")
	else if(document.forms[1].reportID.value == "FinDetRpt")
	{
			var startDate = document.forms[1].Start_Date.value;
			var endDate = document.forms[1].End_Date.value;
			var partmeter;
			if(document.forms[1].Select_Type.value == "CAC")
			{
				partmeter ="?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+document.forms[1].fileName.value+"&reportID=FinDetEFTRpt&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate+"&selectRptType="+document.forms[1].Select_Type.value;
			}//end of if(document.forms[1].Report_Type.value == "CAC")
			else
			{
				partmeter ="?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate+"&selectRptType=";
			}//end of else
	}//end of else if(document.forms[1].reportID.value == "FinDetRpt")	
	else if(document.forms[1].reportID.value == "CitiSoftCpyUpl" || document.forms[1].reportID.value == "CitiSoftCpyCncl" || document.forms[1].reportID.value == "CitiSoftCpyNtCncl")
	{
		if(document.forms[1].Batch_Number.value == "" && document.forms[1].Control_Number.value == "")
		{
			alert("Please enter Batch Number or Control Number");
			document.forms[1].Batch_Number.focus();
			return false;
		}//end of if(document.forms[1].Batch_Number.value == "" && document.forms[1].Control_Number.value == "")
		partmeter ="?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value;
	}//end of else if(document.forms[1].reportID.value == "CitiSoftCpyUpl" || document.forms[1].reportID.value == "CitiSoftCpyCncl" || document.forms[1].reportID.value == "CitiSoftCpyNtCncl")
	else if(document.forms[1].reportID.value == "TDSRemittanceRpt" || document.forms[1].reportID.value == "MonthlyRemitRpt")
	{
		partmeter ="?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value;
	}//end of else if(document.forms[1].reportID.value == "TDSRemittanceRpt")
	else if(document.forms[1].reportID.value == "CitiFinDetRpt")
	{
			var startDate = document.forms[1].Start_Date.value;
			var endDate = document.forms[1].End_Date.value;
			var partmeter ="?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate;
	}//end of else if(document.forms[1].reportID.value == "CitiFinDetRpt")
	else if(document.forms[1].reportID.value == "FurGenRpt")
	{
			var startDate = document.forms[1].Start_Date.value;
			var endDate = document.forms[1].End_Date.value;
			var partmeter ="?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate;
	}//end of else if(document.forms[1].reportID.value == "FurGenRpt")	
	else if(document.forms[1].reportID.value == "EmergencyUniSampo")
	{
			var startDate = document.forms[1].Start_Date.value;
			var endDate = document.forms[1].End_Date.value;
			var partmeter ="?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate;
	}//end of else if(document.forms[1].reportID.value == "EmergencyUniSampo")	
	else if(document.forms[1].reportID.value == "SoftCopyUploadError")
	{
		var status = document.forms[1].Status.value;
		var partmeter ="?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&status="+status;
	}//end of else if(document.forms[1].reportID.value == "SoftCopyUploadError")
	else{
	var startDate = document.forms[1].Start_Date.value;
	var endDate = document.forms[1].End_Date.value;
	var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+document.forms[1].fileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate;
	}//end of else
	
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onSelectLink(filename)
</SCRIPT>

<html:form action="/ReportsAction.do">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><%=TTKCommon.getActiveSubLink(request)%> Reports - <bean:write name="frmReportList" property="reportName"/></td>
  </tr>
</table>
<!-- End of: Page Title -->
<div class="contentArea" id="contentArea">
<!-- Start of Reports List -->
	<ttk:ReportParameter/>
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="center">
        	Report Type
			<select name="reportType" class="selectBox" id="reporttype">
			<logic:notMatch name="frmReportList" property="reportID" value="UniSampoPenRpt">
			<logic:notMatch name="frmReportList" property="reportID" value="SoftCopyUploadError">
			  <!--Shortfall CR KOC1179 Claim Shortfall Summary Rpt -->
			<logic:notMatch name="frmReportList" property="reportID" value="ClaimShortfallSummary">
			     <option value="PDF">PDF</option>
			</logic:notMatch>
			<!--End Shortfall CR KOC1179 Claim Shortfall Summary Rpt -->
			</logic:notMatch>
			</logic:notMatch>
			<option value="EXL">EXCEL</option>
		   </select>
		   &nbsp;
		   <% if((request.getParameter("reportID").equals("UniSampoPenRpt")) || (request.getParameter("reportID").equals("SoftCopyUploadError")))  
		     {
		   %>
		    <button type="button" name="Button"  class="buttons" onMoseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();">Generate Report</button>&nbsp;
           <%
           	  }//end of if((request.getParameter("reportID").equals("UniSampoPenRpt")) || (request.getParameter("reportID").equals("SoftCopyUploadError")))  
               else
           	  {
           	%>
           	    <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerate Report</button>&nbsp;
		    <%
		      }//end of else
		    %>
           <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
       	</td>
      </tr>
    </table>
<!-- End of Reports List -->
</div>
<html:hidden property="fileName"/>
<html:hidden property="reportID"/>
<html:hidden property="reportName"/>
<html:hidden property="selectRptType" value=""/>
<html:hidden property="parameterValues"/>
<input type="hidden" name="mode" value=""/>
</html:form>