<%
/** @ (#) onlinereportparameters.jsp
 * Project     : TTK Healthcare Services
 * File        : onlinereportparameters.jsp
 * Author      : Balaji C R B
 * Company     : Span Systems Corporation
 * Date Created: March 8,2008
 *
 * @author 		 : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.common.TTKPropertiesReader" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript">
function onLoadJSP(){
	var linkerportId	=	document.forms[1].reportID.value;
	if(linkerportId=='OnlinePreAuthBroRpt')
	{
		document.forms[1].reportList.selectedIndex=0;
	}
	else if(linkerportId=='PreAuthBroSummary')
	{
		document.forms[1].reportList.selectedIndex=1;
	}
	else if(linkerportId=='ClmRegBroSummary')
	{
		document.forms[1].reportList.selectedIndex=2;
	}else if(linkerportId=='ClmRegBroDetail')
	{
		document.forms[1].reportList.selectedIndex=3;
	}else if(linkerportId=='ListBroEmpDepPeriod')
	{
		document.forms[1].reportList.selectedIndex=4;
	}
}
function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/OnlineReportsAction.do";
	document.forms[1].submit();
}//end of onClose()

  /*On select IDs kocb */
function onReportChange()
{
	if(document.forms[1].reportList.value=="ReportOnlinePreAuthBroRpt")
	{
		//alert("online--ReportOnlinePreAuthBroRpt");
	 
	 
	document.forms[1].reportID.value="ReportOnlinePreAuthBroRpt";
	document.forms[1].reportName.value="Pre-Authorization - Detail";
	document.forms[1].mode.value="doReportsDetail";
    document.forms[1].action.value="/OnlineReportsAction.do";
	document.forms[1].submit();
		
	
	}
	if(document.forms[1].reportList.value=="ReportPreAuthBroSummary")
	{
		//alert("online--ReportPreAuthBroSummary");
		document.forms[1].reportID.value="ReportPreAuthBroSummary";
		document.forms[1].reportName.value="Pre-Authorization - Summary";
		document.forms[1].mode.value="doReportsDetail";
	    document.forms[1].action.value="/OnlineReportsAction.do";
		document.forms[1].submit();
		
	
	}
	if(document.forms[1].reportList.value=="ReportClmRegBroSummary")
	{
		//alert("online--ReportClmRegBroSummary");
		document.forms[1].reportID.value="ReportClmRegBroSummary";
		document.forms[1].reportName.value="Claim Register - Summary";
		document.forms[1].mode.value="doReportsDetail";
	    document.forms[1].action.value="/OnlineReportsAction.do";
		document.forms[1].submit();
		
	
	}
	if(document.forms[1].reportList.value=="ReportClmRegBroDetail")
	{
		//alert("online--ReportClmRegBroDetail");
		document.forms[1].reportID.value="ReportClmRegBroDetail";
		document.forms[1].reportName.value="Claim Register - Detail";
		document.forms[1].mode.value="doReportsDetail";
	    document.forms[1].action.value="/OnlineReportsAction.do";
		document.forms[1].submit();
		
	
	}
	//added new for kocbroker
	if(document.forms[1].reportList.value=="ReportListBroEmpDepPeriod")
	{
		//alert("online--ReportListBroEmpDepPeriod");
		document.forms[1].reportID.value="ReportListBroEmpDepPeriod";
		document.forms[1].reportName.value="List of Employees and Dependents (Added/Cancelled) for a Period";
		document.forms[1].mode.value="doReportsDetail";
	    document.forms[1].action.value="/OnlineReportsAction.do";
		document.forms[1].submit();
		
	
	}
	if(document.forms[1].reportList.value=="" && document.forms[1].reportID.value!="")
	{
		alert("Please select Report List");
		document.forms[1].reportList.focus();
		return false;
	}
		
}
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
	var userType	=	document.forms[1].userType.value;
	
	
	var objFromDate	=	"";
	var objToDate	=	"";
	
	for(i=0;i<document.forms[1].length;i++)
	{
		/*On select IDs kocbroker  */
		if(userType=='B'){
			if((document.getElementById("mandatory0").value=="") && (document.getElementById("mandatory1").value==""))
			{
				alert("Please enter Company Name or Select Scheme");
				//document.forms[1].elements[i].focus();
				return false;
			}
			if(document.forms[1].elements[i].id=="mandatory2" && document.forms[1].elements[i].value=="")  
			{
				alert("Please Select Received From Date.");
				document.forms[1].elements[i].focus();
				return false;
			}
			if(document.forms[1].elements[i].id=="mandatory3" && document.forms[1].elements[i].value=="")  
			{
				alert("Please Select Received To Date.");
				document.forms[1].elements[i].focus();
				return false;
			}
			
			 
			
		}
		else
		{
		if(regexp.test(document.forms[1].elements[i].id) && document.forms[1].elements[i].value=="")
			{
				alert("Please enter the mandatory fields");
				document.forms[1].elements[i].focus();
				return false;
			}//end of if(regexp.test(document.forms[1].elements[i].id) && document.forms[1].elements[i].value=="")
		}
	}//end of for(i=0;i<document.forms[1].length;i++)

	var NumElements=document.forms[1].elements.length;
	var parameterValue = new String("|");
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
			if(document.forms[1].elements[n].name!="reportType" && document.forms[1].elements[n].name!="reportList" )
			{
				parameterValue+= document.forms[1].elements[n].value;
				parameterValue+="|";
			}//end of if(document.forms[1].elements[n].name!="reportType")
		}//end of if(document.forms[1].elements[n].type=="select-one")
	}//end of for(n=0; n<NumElements;n++)

	//var userType	=	document.forms[1].userType.value;
	//variables declared for SSRS Broker reports kocbroker
	var ServerIp	=	document.forms[1].ServerIp.value;
	var w = screen.availWidth - 10;
    var h = screen.availHeight - 90;
    var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	document.forms[1].parameterValues.value=parameterValue;
	
	if(userType=='B'){
		
	 if(document.forms[1].reportList.value == "ReportOnlinePreAuthBroRpt"){
		 //alert("Nethra--ReportOnlinePreAuthBroRpt");
		objFromDate	=	document.forms[1].PreAuth_Received_From.value;
		objToDate	=	document.forms[1].PreAuth_Received_To.value;
		
		
			//alert("currentDate>>>"+currentDate+"----objToDate>>>"+objToDate);
	//Added by Kishor, To Avoid selection of to Date beyond Current Date.
	//For TO date CAlculating
	var date  = objToDate.substring(0, 2);
    var month = objToDate.substring(3, 5);
    var year  = objToDate.substring(6, 10);
    objToDate = new Date(year, month - 1, date);
    
    //For Calculating From Date
    date  = objFromDate.substring(0, 2);
    month = objFromDate.substring(3, 5);
    year  = objFromDate.substring(6, 10);
    objFromDate = new Date(year, month - 1, date);
    
    var currentDate = new Date();
	currentDate = new Date(currentDate.toDateString());
		    
		    if (objFromDate > currentDate) {
		        alert("From Date Should not be greater than Current Date ");
		        document.forms[1].PreAuth_Received_From.focus();
		        return false;
		    }
		    if (objToDate > currentDate) {
		        alert("To Date Should not be greater than Current Date ");
		        document.forms[1].PreAuth_Received_To.focus();
		        return false;
		    }
			
		if(objFromDate!="" ){
              if(compareDates("PreAuth_Received_From","Pre Auth Received From Date","PreAuth_Received_To","Pre Auth Received To Date","greater than")==false)
                     return false;
              }
		var preAuthFromDate = document.forms[1].PreAuth_Received_From.value;
		var preAuthToDate = document.forms[1].PreAuth_Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName=onlinereports/broker/PreAuthRptBroPDF.jrxml"+"&xlsFileName=onlinereports/broker/PreAuthRptBroXLS.jrxml"+"&reportID="+document.forms[1].reportList.value+"&reportType="+document.forms[1].reportType.value+"&preAuthFromDate="+preAuthFromDate+"&preAuthToDate="+preAuthToDate;
			//alert("partmeter:::"+partmeter);
	}//end of else if(document.forms[1].reportID.value == "ListEmpDepTill")
	else if(document.forms[1].reportList.value == "ReportPreAuthBroSummary"){
		// alert("Nethra --ReportPreAuthBroSummary");
		objFromDate	=	document.forms[1].PreAuth_Received_From.value;
		objToDate	=	document.forms[1].PreAuth_Received_To.value;
			//Added by Kishor, To Avoid selection of to Date beyond Current Date.
	var date  = objToDate.substring(0, 2);
    var month = objToDate.substring(3, 5);
    var year  = objToDate.substring(6, 10);
    var objToDate = new Date(year, month - 1, date);
    
  //For Calculating From Date
    date  = objFromDate.substring(0, 2);
    month = objFromDate.substring(3, 5);
    year  = objFromDate.substring(6, 10);
    objFromDate = new Date(year, month - 1, date);
    
    
    var currentDate = new Date();
	currentDate = new Date(currentDate.toDateString());
	    if (objFromDate > currentDate) {
	        alert("From Date Should not be greater than Current Date ");
	        document.forms[1].PreAuth_Received_From.focus();
	        return false;
	    }
	    if (objToDate > currentDate) {
	        alert("To Date Should not be greater than Current Date ");
	        document.forms[1].PreAuth_Received_To.focus();
	        return false;
	    }
		if(objFromDate!="" ){
	    	  if(compareDates("PreAuth_Received_From","Pre Auth Received From Date","PreAuth_Received_To","Pre Auth Received To Date","greater than")==false)
                     return false;
              }
		var preAuthFromDate = document.forms[1].PreAuth_Received_From.value;
		var preAuthToDate = document.forms[1].PreAuth_Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName=onlinereports/broker/PreAuthSummaryBroPDF.jrxml"+"&xlsFileName=onlinereports/broker/PreAuthRptBroXLS.jrxml"+"&reportID="+document.forms[1].reportList.value+"&reportType="+document.forms[1].reportType.value+"&preAuthFromDate="+preAuthFromDate+"&preAuthToDate="+preAuthToDate;
			//alert("partmeter:::"+partmeter);
	}//end of else if(document.forms[1].reportID.value == "ListEmpDepTill")
	else if(document.forms[1].reportList.value == "ReportClmRegBroSummary"){
		// alert("Nethra--ReportClmRegBroSummary");
		objFromDate	=	document.forms[1].Claims_Received_From.value;
		objToDate	=	document.forms[1].Claims_Received_To.value;
			//Added by Kishor, To Avoid selection of to Date beyond Current Date.
	var date  = objToDate.substring(0, 2);
    var month = objToDate.substring(3, 5);
    var year  = objToDate.substring(6, 10);
    var objToDate = new Date(year, month - 1, date);
    
  //For Calculating From Date
    date  = objFromDate.substring(0, 2);
    month = objFromDate.substring(3, 5);
    year  = objFromDate.substring(6, 10);
    objFromDate = new Date(year, month - 1, date);
    
    var currentDate = new Date();
	currentDate = new Date(currentDate.toDateString());
	    if (objFromDate > currentDate) {
	        alert("From Date Should not be greater than Current Date ");
	        document.forms[1].Claims_Received_From.focus();
	        return false;
	    }
	    if (objToDate > currentDate) {
	        alert("To Date Should not be greater than Current Date ");
	        document.forms[1].Claims_Received_To.focus();
	        return false
	    }
		if(objFromDate!="" ){
              if(compareDates("Claims_Received_From","Claims Received From Date","Claims_Received_To","Claims Received To Date","greater than")==false)
                     return false;
              }
		 var clmFromDate = document.forms[1].Claims_Received_From.value;
			var clmToDate = document.forms[1].Claims_Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName=onlinereports/broker/ClaimRegSummaryBroPDF.jrxml"+"&xlsFileName=onlinereports/broker/ClaimRegSummaryBroXLS.jrxml"+"&reportID="+document.forms[1].reportList.value+"&reportType="+document.forms[1].reportType.value+"&clmFromDate="+clmFromDate+"&clmToDate="+clmToDate;
		//	alert("partmeter:::"+partmeter);
	}//end of else if(document.forms[1].reportID.value == "ListEmpDepTill")
	else if(document.forms[1].reportList.value == "ReportClmRegBroDetail"){
		objFromDate	=	document.forms[1].Claims_Received_From.value;
		objToDate	=	document.forms[1].Claims_Received_To.value;
			//Added by Kishor, To Avoid selection of to Date beyond Current Date.
	var date  = objToDate.substring(0, 2);
    var month = objToDate.substring(3, 5);
    var year  = objToDate.substring(6, 10);
    var objToDate = new Date(year, month - 1, date);
    
  //For Calculating From Date
    date  = objFromDate.substring(0, 2);
    month = objFromDate.substring(3, 5);
    year  = objFromDate.substring(6, 10);
    objFromDate = new Date(year, month - 1, date);
    
    var currentDate = new Date();
	currentDate = new Date(currentDate.toDateString());
	    if (objFromDate > currentDate) {
	        alert("From Date Should not be greater than Current Date ");
	        document.forms[1].Claims_Received_From.focus();
	        return false;
	    }
	    if (objToDate > currentDate) {
	        alert("To Date Should not be greater than Current Date ");
	        document.forms[1].Claims_Received_To.focus();
	        return false;
	    }
		if(objFromDate!="" ){
			if(compareDates("Claims_Received_From","Claims Received From Date","Claims_Received_To","Claims Received To Date","greater than")==false)
                     return false;
              }
		// alert("Nethra--ReportClmRegBroDetail");
		 var clmFromDate = document.forms[1].Claims_Received_From.value;
			var clmToDate = document.forms[1].Claims_Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName=onlinereports/broker/ClaimsRegDetailBroPDF.jrxml"+"&xlsFileName=onlinereports/broker/ClaimsRegDetailBroXLS.jrxml"+"&reportID="+document.forms[1].reportList.value+"&reportType="+document.forms[1].reportType.value+"&clmFromDate="+clmFromDate+"&clmToDate="+clmToDate;
			//alert("partmeter:::"+partmeter);
	}//end of else if(document.forms[1].reportID.value == "ListEmpDepTill")



	//HyperLink IDs kocbroker
	else if(document.forms[1].reportID.value == "OnlinePreAuthBroRpt"){
		//alert("parameter==="+parameterValue);
		objFromDate	=	document.forms[1].PreAuth_Received_From.value;
		objToDate	=	document.forms[1].PreAuth_Received_To.value;
			//Added by Kishor, To Avoid selection of to Date beyond Current Date.
	var date  = objToDate.substring(0, 2);
    var month = objToDate.substring(3, 5);
    var year  = objToDate.substring(6, 10);
    var objToDate = new Date(year, month - 1, date);
    
  //For Calculating From Date
    date  = objFromDate.substring(0, 2);
    month = objFromDate.substring(3, 5);
    year  = objFromDate.substring(6, 10);
    objFromDate = new Date(year, month - 1, date);
    
    var currentDate = new Date();
	currentDate = new Date(currentDate.toDateString());
	    if (objFromDate > currentDate) {
	        alert("From Date Should not be greater than Current Date ");
	        document.forms[1].PreAuth_Received_From.focus();
	        return false;
	    }
	    if (objToDate > currentDate) {
	        alert("To Date Should not be greater than Current Date ");
	        document.forms[1].PreAuth_Received_To.focus();
	        return false;
	    }
		if(objFromDate!="" ){
			if(compareDates("PreAuth_Received_From","Pre Auth Received From Date","PreAuth_Received_To","Pre Auth Received To Date","greater than")==false)
                     return false;
              }
		var preAuthFromDate = document.forms[1].PreAuth_Received_From.value;
		var preAuthToDate = document.forms[1].PreAuth_Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&preAuthFromDate="+preAuthFromDate+"&preAuthToDate="+preAuthToDate;
		//	alert("partmeter::"+partmeter);
	}//end of else if(document.forms[1].reportID.value == "ListEmpDepTill")
	else if(document.forms[1].reportID.value == "PreAuthBroSummary"){
		objFromDate	=	document.forms[1].PreAuth_Received_From.value;
		objToDate	=	document.forms[1].PreAuth_Received_To.value;
			//Added by Kishor, To Avoid selection of to Date beyond Current Date.
	var date  = objToDate.substring(0, 2);
    var month = objToDate.substring(3, 5);
    var year  = objToDate.substring(6, 10);
    var objToDate = new Date(year, month - 1, date);
    
  //For Calculating From Date
    date  = objFromDate.substring(0, 2);
    month = objFromDate.substring(3, 5);
    year  = objFromDate.substring(6, 10);
    objFromDate = new Date(year, month - 1, date);
    
    
    var currentDate = new Date();
	currentDate = new Date(currentDate.toDateString());
	    if (objFromDate > currentDate) {
	        alert("From Date Should not be greater than Current Date ");
	        document.forms[1].PreAuth_Received_From.focus();
	        return false;
	    }
	    if (objToDate > currentDate) {
	        alert("To Date Should not be greater than Current Date ");
	        document.forms[1].PreAuth_Received_To.focus();
	        return false;
	    }
		if(objFromDate!="" ){
			if(compareDates("PreAuth_Received_From","Pre Auth Received From Date","PreAuth_Received_To","Pre Auth Received To Date","greater than")==false)
                     return false;
              }
		var preAuthFromDate = document.forms[1].PreAuth_Received_From.value;
		var preAuthToDate = document.forms[1].PreAuth_Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&preAuthFromDate="+preAuthFromDate+"&preAuthToDate="+preAuthToDate;
	
	}
	else if(document.forms[1].reportID.value == "ClmRegBroSummary"){
		objFromDate	=	document.forms[1].Claims_Received_From.value;
		objToDate	=	document.forms[1].Claims_Received_To.value;
			//Added by Kishor, To Avoid selection of to Date beyond Current Date.
	var date  = objToDate.substring(0, 2);
    var month = objToDate.substring(3, 5);
    var year  = objToDate.substring(6, 10);
    var objToDate = new Date(year, month - 1, date);
    
  //For Calculating From Date
    date  = objFromDate.substring(0, 2);
    month = objFromDate.substring(3, 5);
    year  = objFromDate.substring(6, 10);
    objFromDate = new Date(year, month - 1, date);
    
    
    var currentDate = new Date();
	currentDate = new Date(currentDate.toDateString());
	    if (objFromDate > currentDate) {
	        alert("From Date Should not be greater than Current Date ");
	        document.forms[1].Claims_Received_From.focus;
	        return false;
	    }
	    if (objToDate > currentDate) {
	        alert("To Date Should not be greater than Current Date ");
	        document.forms[1].Claims_Received_To.focus;
	        return false;
	    }
		if(objFromDate!="" ){
			if(compareDates("Claims_Received_From","Claims Received From Date","Claims_Received_To","Claims Received To Date","greater than")==false)
                     return false;
              }
		var clmFromDate = document.forms[1].Claims_Received_From.value;
		var clmToDate = document.forms[1].Claims_Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&clmFromDate="+clmFromDate+"&clmToDate="+clmToDate;
	
	}//end of else if(document.forms[1].reportID.value == "ListEmpDepTill")

	else if(document.forms[1].reportID.value == "ClmRegBroDetail"){
		objFromDate	=	document.forms[1].Claims_Received_From.value;
		objToDate	=	document.forms[1].Claims_Received_To.value;
			//Added by Kishor, To Avoid selection of to Date beyond Current Date.
	var date  = objToDate.substring(0, 2);
    var month = objToDate.substring(3, 5);
    var year  = objToDate.substring(6, 10);
    var objToDate = new Date(year, month - 1, date);
    
  //For Calculating From Date
    date  = objFromDate.substring(0, 2);
    month = objFromDate.substring(3, 5);
    year  = objFromDate.substring(6, 10);
    objFromDate = new Date(year, month - 1, date);
    
    var currentDate = new Date();
	currentDate = new Date(currentDate.toDateString());
	    if (objFromDate > currentDate) {
	        alert("From Date Should not be greater than Current Date ");
	        document.forms[1].Claims_Received_From.focus();
	        return false;
	    }
	    if (objToDate > currentDate) {
	        alert("To Date Should not be greater than Current Date ");
	        document.forms[1].Claims_Received_To.focus();
	        return false;
	    }
		if(objFromDate!="" ){
			if(compareDates("Claims_Received_From","Claims Received From Date","Claims_Received_To","Claims Received To Date","greater than")==false)
                     return false;
              }
		var clmFromDate = document.forms[1].Claims_Received_From.value;
		var clmToDate = document.forms[1].Claims_Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&clmFromDate="+clmFromDate+"&clmToDate="+clmToDate;
	
	}
	//added new for kocbroker
	else if(document.forms[1].reportID.value == "ListBroEmpDepPeriod"){
		objFromDate	=	document.forms[1].Received_From.value;
		objToDate	=	document.forms[1].Received_To.value;
			//Added by Kishor, To Avoid selection of to Date beyond Current Date.
	var date  = objToDate.substring(0, 2);
    var month = objToDate.substring(3, 5);
    var year  = objToDate.substring(6, 10);
    var objToDate = new Date(year, month - 1, date);
    
  //For Calculating From Date
    date  = objFromDate.substring(0, 2);
    month = objFromDate.substring(3, 5);
    year  = objFromDate.substring(6, 10);
    objFromDate = new Date(year, month - 1, date);
    
    var currentDate = new Date();
	currentDate = new Date(currentDate.toDateString());
	    if (objFromDate > currentDate) {
	        alert("From Date Should not be greater than Current Date ");
	        document.forms[1].Received_From.focus();
	        return false;
	    }
	    if (objToDate > currentDate) {
	        alert("To Date Should not be greater than Current Date ");
	        document.forms[1].Received_To.focus();
	        return false;
	    }
		if(objFromDate!="" ){
            if(compareDates("Received_From"," Received From Date","Received_To"," Received To Date","greater than")==false)
                   return false;
            }
		var fromDate = document.forms[1].Received_From.value;
		var toDate = document.forms[1].Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&fromDate="+fromDate+"&toDate="+toDate;
	
	}
	else if(document.forms[1].reportID.value == "ReportListBroEmpDepPeriod"){
		objFromDate	=	document.forms[1].Received_From.value;
		objToDate	=	document.forms[1].Received_To.value;
			//Added by Kishor, To Avoid selection of to Date beyond Current Date.
	var date  = objToDate.substring(0, 2);
    var month = objToDate.substring(3, 5);
    var year  = objToDate.substring(6, 10);
    var objToDate = new Date(year, month - 1, date);
    
  //For Calculating From Date
    date  = objFromDate.substring(0, 2);
    month = objFromDate.substring(3, 5);
    year  = objFromDate.substring(6, 10);
    objFromDate = new Date(year, month - 1, date);
    
    var currentDate = new Date();
	currentDate = new Date(currentDate.toDateString());
	    if (objFromDate > currentDate) {
	        alert("From Date Should not be greater than Current Date ");
	        document.forms[1].Received_From.focus();
	        return false;
	    }
	    if (objToDate > currentDate) {
	        alert("To Date Should not be greater than Current Date ");
	        document.forms[1].Received_To.focus();
	        return false;
	    }
		if(objFromDate!="" ){
			if(compareDates("Received_From"," Received From Date","Received_To"," Received To Date","greater than")==false)
                     return false;
              }
		var fromDate = document.forms[1].Received_From.value;
		var toDate = document.forms[1].Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName=onlinereports/broker/EmpDependentperPeriod.jrxml"+"&xlsFileName=onlinereports/hr/EmpDependentListPerXLS.jrxml"+"&reportID="+document.forms[1].reportList.value+"&reportType="+document.forms[1].reportType.value+"&fromDate="+fromDate+"&toDate="+toDate;
		
	
	}
	
	}else{//end If Only Broker
	
	
	if(document.forms[1].reportID.value == "GrpEnrollRpt")
	{
	   document.forms[1].mode.value="doGenerateEnrolReport";
	}//end of if(document.forms[1].reportID.value != "GrpEnrollRpt")
	else if(document.forms[1].reportID.value == "GrpPreAuthRpt")
	{
		document.forms[1].mode.value="doGenerateGrpPreAuthRpt";
	}//end of else if(document.forms[1].reportID.value == "GrpPreAuthRpt")
	else
	{
		document.forms[1].mode.value="doGenerateReport";
	}//end of else
	if(document.forms[1].reportID.value == "ListEmpDepPeriod")
	{
		var fromDate = document.forms[1].From_Date.value;
		var toDate = document.forms[1].To_Date.value;
	    var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&fromDate="+fromDate+"&toDate="+toDate;
	}//end of if(document.forms[1].reportID.value == "ListEmpDepPeriod")
	// Summary CR
	else if(document.forms[1].reportID.value == "OnlineSummaryRpt")
	{
		var fromDate = document.forms[1].From_Date.value;
		var toDate = document.forms[1].To_Date.value;
	    var partmeter = "?mode=doGenerateSummaryReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&fromDate="+fromDate+"&toDate="+toDate;
	}//end of if(document.forms[1].reportID.value == "OnlineSummaryRpt")
	else if(document.forms[1].reportID.value == "ListEmpDepTill"){
		var toDate = document.forms[1].To_Date.value;
		var strArray = new Array();
		strArray = parameterValue.split("|");
		parameterValue="|"+strArray[1]+"||"+strArray[2]+"|";
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&fromDate="+fromDate+"&toDate="+toDate;
	}//end of else if(document.forms[1].reportID.value == "ListEmpDepTill")
	else if(document.forms[1].reportID.value == "ClmRegd"){
		var clmRegFromDate = document.forms[1].Claims_Registered_From.value;
		var clmRegToDate = document.forms[1].Claims_Registered_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&clmRegFromDate="+clmRegFromDate+"&clmRegToDate="+clmRegToDate;		
	}//end of else if(document.forms[1].reportID.value == "ListEmpDepTill")
	else if(document.forms[1].reportID.value == "OnlinePreAuthRpt"){
		var preAuthFromDate = document.forms[1].PreAuth_Received_From.value;
		var preAuthToDate = document.forms[1].PreAuth_Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&preAuthFromDate="+preAuthFromDate+"&preAuthToDate="+preAuthToDate;		
	}//end of else if(document.forms[1].reportID.value == "ListEmpDepTill")
	else if(document.forms[1].reportID.value == "ClmRegSummary" || document.forms[1].reportID.value == "ClmRegDetail"){
		var clmFromDate = document.forms[1].Claims_Received_From.value;
		var clmToDate = document.forms[1].Claims_Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&clmFromDate="+clmFromDate+"&clmToDate="+clmToDate;
	}//end of else if(document.forms[1].reportID.value == "ListEmpDepTill")
	//as per Hospital Login
		else if((document.forms[1].reportID.value == "HospitalOnlineSummaryRpt") || (document.forms[1].reportID.value == "HospitalClmRegd") || (document.forms[1].reportID.value == "HospitalBillsPend")){
		var hospseqid=document.forms[1].hospseqid.value;
		parameterValue=parameterValue;					
		var fromDate = document.forms[1].From_Date.value;
		var toDate = document.forms[1].To_Date.value;
	    var partmeter = "?mode=doGenerateHospitalReports&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&fromDate="+fromDate+"&toDate="+toDate;
	}//end of if(document.forms[1].reportID.value == "HospitalOnlineSummaryRpt")	

	

	/*else if(document.forms[1].reportID.value == "HospitalClmRegd"){
		var hospSeqid = document.forms[1].hospseqid.value;
		parameterValue="|"+hospSeqid+"|"+parameterValue;
		var clmFromDate = document.forms[1].Claims_Received_From.value;
		var clmToDate = document.forms[1].Claims_Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&clmFromDate="+clmFromDate+"&clmToDate="+clmToDate;		
	}//end of else if(document.forms[1].reportID.value == "HospitalClmRegd")*/
	//as per Hospital Login
	//KOC 1339 for mail
	
	else if(document.forms[1].reportID.value == "ClaimsIntimations")
	{
		var fromDate = document.forms[1].From_Date.value;
		var toDate = document.forms[1].To_Date.value;
	    var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&fromDate="+fromDate+"&toDate="+toDate;
	}//end of if(document.forms[1].reportID.value == "ClaimsIntimations")
	

//On select IDs kocb
	
	
	//KOC 1339 for mail
	else if(document.forms[1].reportID.value == "GrpEnrollRpt")
	{
		var reportFromDate = document.forms[1].Report_From.value;
		var reportToDate = document.forms[1].Report_To.value;
		var strArray = new Array();
		strArray = parameterValue.split("|");
		var parametervalue=strArray[1];
		var partmeter = "?mode=doGenerateEnrolReport&parameter="+parametervalue+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&reportFromDate="+reportFromDate+"&reportToDate="+reportToDate;
	}//end of else if(document.forms[1].reportID.value == "GrpEnrollRpt")
	else if(document.forms[1].reportID.value == "GrpPreAuthRpt")
	{
		var reportFromDate = document.forms[1].Report_From.value;
		var reportToDate = document.forms[1].Report_To.value;
		var strArray = new Array();
		strArray = parameterValue.split("|");
		var parametervalue=strArray[1];
		var partmeter = "?mode=doGenerateGrpPreAuthRpt&parameter="+parametervalue+"&sStatus="+document.forms[1].Status.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&reportFromDate="+reportFromDate+"&reportToDate="+reportToDate;
	}//end of else if(document.forms[1].reportID.value == "GrpPreAuthRpt")
	else if(document.forms[1].reportID.value == "GrpOnlineTATRpt")
	{
		var reportFromDate = document.forms[1].Report_From.value;
		var reportToDate = document.forms[1].Report_To.value;
		var strArray = new Array();
		strArray = parameterValue.split("|");
		var parametervalue=strArray[1];
		var partmeter = "?mode=doGenerateGrpOnlineTATRpt&parameter="+parametervalue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&sFeedBackType="+document.forms[1].FeedBack_Type.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&reportFromDate="+reportFromDate+"&reportToDate="+reportToDate;
	}//end of else if(document.forms[1].reportID.value == "GrpOnlineTATRpt")
		//Changes as per KOC 1274A
	else if(document.forms[1].reportID.value == "AppRejClaimsList")
	{
		var reportFromDate = document.forms[1].Report_From.value;
		var reportToDate = document.forms[1].Report_To.value;
		
		var pdfFileName=document.forms[1].pdfFileName.value;
		var xlsFileName=document.forms[1].xlsFileName.value;
		var xlsFileName=document.forms[1].xlsFileName.value;
		var reportID=document.forms[1].reportID.value;
		var reportType=document.forms[1].reportType.value;
		
		var strArray = new Array();
		strArray = parameterValue.split("|");
		var parametervalue=strArray[1];
		var partmeter = "?mode=doGenerateReport&parameter="+parametervalue+"&pdfFileName="+pdfFileName+"&xlsFileName="+xlsFileName+"&reportID="+reportID+"&reportType="+reportType+"&reportFromDate="+reportFromDate+"&reportToDate="+reportToDate;
	}//end of else if(document.forms[1].reportID.value == "AppRejClaimsList")
	else if(document.forms[1].reportID.value == "AppRejPreAuthList")
	{
		var reportFromDate = document.forms[1].Report_From.value;
		var reportToDate = document.forms[1].Report_To.value;

		var pdfFileName=document.forms[1].pdfFileName.value;
		var xlsFileName=document.forms[1].xlsFileName.value;
		var xlsFileName=document.forms[1].xlsFileName.value;
		var reportID=document.forms[1].reportID.value;
		var reportType=document.forms[1].reportType.value;
		
		var strArray = new Array();
		strArray = parameterValue.split("|");
		var parametervalue=strArray[1];
		var partmeter = "?mode=doGenerateReport&parameter="+parametervalue+"&pdfFileName="+pdfFileName+"&xlsFileName="+xlsFileName+"&reportID="+reportID+"&reportType="+reportType+"&reportFromDate="+reportFromDate+"&reportToDate="+reportToDate;
	}//end of else if(document.forms[1].reportID.value == "AppRejPreAuthList")//Changes as per KOC 1274A
	//KOC 1353 for payment report	
	else if(document.forms[1].reportID.value == "INSPaymentReport")
	{
		
		var payFromDate = document.forms[1].Received_From.value;
		var payToDate = document.forms[1].Received_To.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&csvFileName="+document.forms[1].csvFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&payFromDate="+payFromDate+"&payToDate="+payToDate;		
	}
	else if(document.forms[1].reportID.value == "PreauthReport")// KOCPreauthreport
	{
		
		var reportFromDate = document.forms[1].Report_From.value;
		var reportToDate = document.forms[1].Report_To.value;
		
		var pdfFileName=document.forms[1].pdfFileName.value;
		var xlsFileName=document.forms[1].xlsFileName.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+pdfFileName+"&xlsFileName="+xlsFileName+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&reportFromDate="+reportFromDate+"&reportToDate="+reportToDate;		
	}
	else if(document.forms[1].reportID.value == "ClaimReport")//KOCPreauthreport
	{
		
		var reportFromDate = document.forms[1].Report_From.value;
		var reportToDate = document.forms[1].Report_To.value;
		
		var pdfFileName=document.forms[1].pdfFileName.value;
		var xlsFileName=document.forms[1].xlsFileName.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+pdfFileName+"&xlsFileName="+xlsFileName+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&reportFromDate="+reportFromDate+"&reportToDate="+reportToDate;		
	}
	else if(document.forms[1].reportID.value == "PolicyReport")//KOCPreauthreport
	{
		
		var reportFromDate = document.forms[1].Report_From.value;
		var reportToDate = document.forms[1].Report_To.value;
		
		var pdfFileName=document.forms[1].pdfFileName.value;
		var xlsFileName=document.forms[1].xlsFileName.value;
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+pdfFileName+"&xlsFileName="+xlsFileName+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&reportFromDate="+reportFromDate+"&reportToDate="+reportToDate;		
	}
	//KOC 1353 for payment report
	else {
		var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&pdfFileName="+document.forms[1].pdfFileName.value+"&xlsFileName="+document.forms[1].xlsFileName.value+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value;
	}//end of else
}//For Other Users

		 
	var openPage = "/OnlineReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateReport()



</script>
<%
UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
String ServerIp = TTKPropertiesReader.getPropertyValue("SsrsServerIpBrokerReports");          
 
%>
<html:form action="/OnlineReportsAction.do">
<input type="hidden" name="userType" value="<%=userSecurityProfile.getLoginType()%>"/>
<input type="hidden" name="ServerIp" value="<%=ServerIp%>"/>
<!-- S T A R T : Page Title -->
<%if(!userSecurityProfile.getLoginType().equals("B")){ %>
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><%=TTKCommon.getActiveSubLink(request)%> Reports - <bean:write name="frmOnlineReportList" property="reportName"/></td>
  </tr>
</table>
<% } %>
<!-- End of: Page Title -->
<div class="contentArea" id="contentArea">
<!-- Start of Reports List -->
<!-- On select IDs kocb -->
	
			 <%if(userSecurityProfile.getLoginType().equals("B")){ %>
			 <logic:notMatch name="frmOnlineReportList" property="reportListID" value="OnlineSummary">
			 <br><br>
		<table align="center" class="tablePad"  border="0" cellspacing="0" cellpadding="0" >
	      <tr>
				<td width="50%"   class="textLabelBold" align="right">Report List:</td>
		        <td width="50%"  align="left">
			        <html:select property="reportList" styleClass="specialDropDown" styleId="reportlist"  onchange="javascript:onReportChange()">
						  <!--<html:option value="">Select from List</html:option>-->
						  
						<html:option value="ReportOnlinePreAuthBroRpt">Cashless - Detail</html:option>
			    <html:option value="ReportPreAuthBroSummary">Cashless - Summary</html:option>
			    <html:option value="ReportClmRegBroSummary">Claim Register - Summary</html:option>
			    <html:option value="ReportClmRegBroDetail">Claim Register - Detail</html:option>
			    <html:option value="ReportListBroEmpDepPeriod">List of Employees and Dependents (Added/Cancelled) for a Period</html:option>
					</html:select>
				</td>
		  </tr>
	    </table>
	 </logic:notMatch>
			
			<%} %>

	<ttk:OnlineReportParameter/>
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="center">
        	Report Type
			<select name="reportType" class="selectBoxWeblogin" id="reporttype">
			<logic:notMatch name="frmOnlineReportList" property="reportID" value="PolicyReport">
			<logic:notMatch name="frmOnlineReportList" property="reportID" value="ClaimReport">
			 <logic:notMatch name="frmOnlineReportList" property="reportID" value="PreauthReport">
			  <logic:notMatch name="frmOnlineReportList" property="reportID" value="INSPaymentReport"><!-- KOC 1353 for payment report -->
				<logic:notMatch name="frmOnlineReportList" property="reportID" value="GrpEnrollRpt">			
			   <!-- Summary CR KOC1224-->
					<logic:notMatch name="frmOnlineReportList" property="reportID" value="OnlineSummaryRpt">
			    		<option value="PDF">PDF</option>
					</logic:notMatch>
				</logic:notMatch>
		        <% if(!userSecurityProfile.getLoginType().equals("B")){ %>
		        <option value="EXL">EXCEL</option>
		      <%} %>
		     </logic:notMatch>
		    </logic:notMatch>
		   </logic:notMatch>
		   </logic:notMatch>
		        <!-- KOC 1353 for payment report KOCPreauthreport -->
		        <logic:match name="frmOnlineReportList" property="reportID" value="INSPaymentReport">
		        	<option value="TXT">TXT</option>
		        </logic:match>
				<%--Hospital login --%>
				<logic:match name="frmOnlineReportList" property="reportID" value="HospitalOnlineSummaryRpt">
			   				 <option value="PDF">PDF</option>
				</logic:match>
				<%--Hospital login --%>
		        <logic:match name="frmOnlineReportList" property="reportID" value="PreauthReport">
		        	<option value="EXL">EXCEL</option>
		        </logic:match>
		        <logic:match name="frmOnlineReportList" property="reportID" value="ClaimReport">
		        	<option value="EXL">EXCEL</option>
		        </logic:match>
		        <logic:match name="frmOnlineReportList" property="reportID" value="PolicyReport">
		        	<option value="EXL">EXCEL</option>
		        </logic:match>
		   </select>
		   &nbsp;
           <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateReport();"><u>G</u>enerate Report</button>&nbsp;
<% if(userSecurityProfile.getLoginType().equals("B")){ %>
           <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>B</u>ack</button>
<%}else{ %>
           <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
<% } %>



       	</td>
      </tr>
   <%  
   //kocbroker
   if(userSecurityProfile.getLoginType().equals("B")){ %>
      <tr>
    <td align="center" class="textLabel"><span class="mandatorySymbol">NOTE : Please enter Company/Group Name or Select Scheme</span></td>
    </tr>
    <%} %>
    </table>
<!-- End of Reports List -->
</div>
<html:hidden property="pdfFileName"/>
<html:hidden property="xlsFileName"/>
<html:hidden property="csvFileName"/> <!-- KOC 1353 for payment report -->
<html:hidden property="reportID"/>
<html:hidden property="reportListID"/>
<html:hidden property="reportName"/>
<html:hidden property="parameterValues"/>

<input type="hidden" name="reportIdFromLink" id="reportIdFromLink" value="">

<input type="hidden" name="mode" value=""/>
	<script language="javascript">
	onLoadJSP();
	</script>
</html:form>