//contains the javascript functions of hospitalreportslist.jsp of MIS Reports module


function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/HospitalReportsAction.do";
	document.forms[1].submit();
}//end of onClose()
function onChangeStatus()
{
       // alert("onChangeStatus()");
    	document.forms[1].mode.value="doChangeStatus";
    	document.forms[1].action="/HospitalReportsAction.do";
    	document.forms[1].submit();
}//end of onChangeStatus()

function onChangeLabel()
{
        //alert("onChangeStatus()");
    	document.forms[1].mode.value="doChangeLabel";
    	document.forms[1].action="/HospitalReportsAction.do";
    	document.forms[1].submit();
}//end of onChangeLabel()

function onGenerateReport()
{

       if(document.getElementById("sHospitalName").value=="")
		{
			alert("Please Select Hospital Name");
			document.forms[1].sHospitalName.focus();
			return false;
		}
		
		if(document.forms[1].sType.value=="")
		{
			alert("Please Select Type");
			document.forms[1].sType.focus();
			return false;
		}
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
   //alert("function onGenerateReport()");
   var sHospitalName=document.getElementById("sHospitalName").value
   var sType=document.forms[1].sType.value
   var sStatus=document.forms[1].sStatus.value
   var startDate = document.forms[1].sStartDate.value;
   var endDate = document.forms[1].sEndDate.value;
   var selecthospitalname=document.getElementById("sHospitalName");
   var hospitalname=selecthospitalname.options[selecthospitalname.options.selectedIndex].text;
   var selectstatus=document.forms[1].sStatus;
   var status=selectstatus.options[selectstatus.options.selectedIndex].text;
   var selecttype=document.forms[1].sType;
   var type=selecttype.options[selecttype.options.selectedIndex].text;
   var selectreportID=document.forms[1].reportID;
   var reportname=selectreportID.options[selectreportID.options.selectedIndex].text;
   //alert("Type combox Label Text selecttypes=="+reportname);
   var reportlinkname= "Hospital Monitor";
   var startlabelvalue=document.getElementById("lb").innerText;
         
         var endlabelvalue=document.getElementById("lab").innerText;
         
    var parameterValue;//= new String("|S|");
    document.forms[1].parameterValues.value=parameterValue;
	document.forms[1].mode.value="doGenerateReport";
	if(document.forms[1].reportType.value=="EXL")
	{
	   if(document.forms[1].reportID.value=="HMRCI")
	     {
		      var fileName = "reports/misreports/HospMonitorClaimsInfoEXL.jrxml";
	     }
	     else if(document.forms[1].reportID.value=="HMRPRI")
	     {
		        var fileName = "reports/misreports/HospMonitorPreauthInfoEXL.jrxml";
	     }
	}//END OF if(document.forms[1].reportType.value=="EXL")
	if(document.forms[1].reportType.value=="PDF")
	{
		if(document.forms[1].reportID.value=="HMRPRI")
	     {
		      var fileName = "reports/misreports/HospMonitorPreauthInfoEXL.jrxml";
	     }
	}// END OF if(document.forms[1].reportType.value=="PDF")
	
	parameterValue = "|"+sHospitalName+"|"+sType+"|"+sStatus+"|"+startDate+"|"+endDate+"|";
	//var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+fileName+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate;
	var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+fileName+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate+"&reportlinkname="+reportlinkname+"&reportname="+reportname+"&type="+type+"&status="+status+"&hospitalname="+hospitalname+"&startlabelvalue="+startlabelvalue+"&endlabelvalue="+endlabelvalue;
	var openPage = "/HospitalReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateReport