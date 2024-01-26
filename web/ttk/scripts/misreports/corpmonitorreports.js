//contains the javascript functions of corpmonitorreports.jsp of MIS Reports module



function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/CorporateReportsAction.do";
	document.forms[1].submit();
}//end of onClose()
function onChangeStatus()
{
        //alert("onChangeStatus()");
    	document.forms[1].mode.value="doChangeStatus";
    	document.forms[1].action="/CorporateReportsAction.do";
    	document.forms[1].submit();
}//end of onChangeStatus()

function onChangeLabel()
{
        //alert("onChangeStatus()");
    	document.forms[1].mode.value="doChangeLabel";
    	document.forms[1].action="/CorporateReportsAction.do";
    	document.forms[1].submit();
}//end of onChangeLabel()

function onGenerateReport()
{

       if(document.forms[1].sPolicyNo.value=="")
		{
			alert("Please Select Scheme No.");
			document.forms[1].sPolicyNo.focus();
			return false;
		}
		if(document.forms[1].sType.value=="")
		{
			alert("Please Select Type");
			document.forms[1].sType.focus();
			return false;
		}
		/*if(document.forms[1].sClaimsType.value=="")
		{
			alert("Please Select Claims Type");
			document.forms[1].sClaimsType.focus();
			return false;
		}*/
		if(document.forms[1].sStatus.value=="")
		{
			alert("Please Select Status");
			document.forms[1].sStatus.focus();
			return false;
		}
		
		if(isDate(document.forms[1].sStartDate,"Start Date")==false)
		{
			//alert("Please Enter Start Date");
			document.forms[1].sStartDate.focus();
			return false;
		}
		
		if(isDate(document.forms[1].sEndDate,"End Date")==false)
		{
			//alert("Please Enter End Date");
			document.forms[1].sEndDate.focus();
			return false;
		}
		
       if(document.forms[1].reportID.value=="")
		{
			alert("Please Select Report Name");
			document.forms[1].reportID.focus();
			return false;
		}
     var sPolicyNo=document.forms[1].sPolicyNo.value
     var sType=document.forms[1].sType.value
    var sClaimsType=document.forms[1].sClaimsType.value
    var sStatus=document.forms[1].sStatus.value
    var startDate = document.forms[1].sStartDate.value;
	var endDate = document.forms[1].sEndDate.value;
	var selectstatus=document.forms[1].sStatus;
    var status=selectstatus.options[selectstatus.options.selectedIndex].text;
    var selecttype=document.forms[1].sType;
    var type=selecttype.options[selecttype.options.selectedIndex].text;
    //alert("Type combox Label Text selecttypes=="+type);
    var selectclaimtype=document.forms[1].sClaimsType;
    var claimtype=selectclaimtype.options[selectclaimtype.options.selectedIndex].text;
    //alert("Type combox Label Text selecttypes=="+claimtype);
    var selectreportID=document.forms[1].reportID;
    var reportname=selectreportID.options[selectreportID.options.selectedIndex].text;
    var reportlinkname= "Corporate Monitor";
	var startlabelvalue=document.getElementById("lb").innerText;
         
         var endlabelvalue=document.getElementById("lab").innerText;
         
	var parameterValue;// = new String("|S|");
    document.forms[1].parameterValues.value=parameterValue;
	document.forms[1].mode.value="doGenerateReport";
	if(document.forms[1].reportType.value=="EXL")
	{
	    if(document.forms[1].reportID.value=="CMREI")
	     {
		       var fileName = "reports/misreports/CorpMonitorEmployeeInfoEXL.jrxml";
	     }
	     else if(document.forms[1].reportID.value=="CMRCI")
	     {
		       var fileName = "reports/misreports/CorpMonitorClaimsInfoEXL.jrxml";
	     }
	     else if(document.forms[1].reportID.value=="CMRPRI")
	     {
		       var fileName = "reports/misreports/CorpMonitorPreauthInfoEXL.jrxml";
	     }
	     else if(document.forms[1].reportID.value=="CMRPOI")
	     {
		       var fileName = "reports/misreports/CorpMonitorPolicyInfoEXL.jrxml";
	     }
	     
	}//END OF if(document.forms[1].reportType.value=="EXL")
	if(document.forms[1].reportType.value=="PDF")
	{
		if(document.forms[1].reportID.value=="CMREI")
	     {
		       var fileName = "reports/misreports/CorporateMonitorEXL.jrxml";
	     }
	}// END OF if(document.forms[1].reportType.value=="PDF")
	
	parameterValue ="|"+sPolicyNo+"|"+sType+"|"+sClaimsType+"|"+sStatus+"|"+startDate+"|"+endDate+"|";
	//var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+fileName+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate;
	var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+fileName+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate+"&reportlinkname="+reportlinkname+"&sPolicyNo="+sPolicyNo+"&type="+type+"&reportname="+reportname+"&claimtype="+claimtype+"&status="+status+"&startlabelvalue="+startlabelvalue+"&endlabelvalue="+endlabelvalue;
	var openPage = "/CorporateReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateReport