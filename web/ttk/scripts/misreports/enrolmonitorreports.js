
//contains the javascript functions of enrollmentmonitorreports.jsp of MIS Reports module


function onChangeInsCompany()
{
    	document.forms[1].mode.value="doChangeCompany";
    	document.forms[1].focusID.value="ttkoffice";
    	document.forms[1].action="/EnrolMonitorReportsAction.do";
    	document.forms[1].submit();
}//end of onChangeCity()

function onChangeInsBoDo()
{
	document.forms[1].mode.value="doChangeComDoBo";
   	document.forms[1].focusID.value="comDoBo";
	document.forms[1].action="/EnrolMonitorReportsAction.do";
	document.forms[1].submit();
}
function onChangeStatus()
{
        //alert("onChangeStatus()");
    	document.forms[1].mode.value="doChangeStatus";
    	document.forms[1].action="/EnrolMonitorReportsAction.do";
    	document.forms[1].submit();
}//end of onChangeStatus()

function onChangeLabel()
{
        //alert("onChangeStatus()");
    	document.forms[1].mode.value="doChangeLabel";
    	document.forms[1].action="/EnrolMonitorReportsAction.do";
    	document.forms[1].submit();
}//end of onChangeLabel()

function onClose()
{
	

    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/EnrolMonitorReportsAction.do";
    document.forms[1].submit();
}//end of onClose()
function onGenerateReport()
{
       if(document.getElementById("tTKBranchCode").value=="")
		{
			alert("Please Select Vidal Health Branch");
			document.forms[1].tTKBranchCode.focus();
			return false;
		}//end of if(document.getElementById("tTKBranchCode").value=="")
		 if(document.getElementById("insCompanyCode").value=="")
		{
			alert("Please Select Healthcare Company");
			document.forms[1].insCompanyCode.focus();
			return false;
		}//end of if(document.getElementById("insCompanyCode").value=="")
       if(document.forms[1].sType.value=="")
		{
			alert("Please Select Type");
			document.forms[1].sType.focus();
			return false;
		}//end of if(document.forms[1].sType.value=="")
		if(document.forms[1].sStatus.value=="")
		{
			alert("Please Select Status");
			document.forms[1].sStatus.focus();
			return false;
		}//end of if(document.forms[1].sStatus.value=="")
		
		if(isDate(document.forms[1].sStartDate,"Start Date")==false)
		{
			//alert("Please Enter Start Date");
			document.forms[1].sStartDate.focus();
			return false;
		}//end of if(isDate(document.forms[1].sStartDate,"Start Date")==false)
		
		if(isDate(document.forms[1].sEndDate,"End Date")==false)
		{
			//alert("Please Enter End Date");
			document.forms[1].sEndDate.focus();
			return false;
		}//end of if(isDate(document.forms[1].sEndDate,"End Date")==false)
		
       if(document.forms[1].reportID.value=="")
		{
			alert("Please Select Report Name");
			document.forms[1].reportID.focus();
			return false;
		}//end of if(document.forms[1].reportID.value=="")
		 var TTKBranchCode=document.getElementById("tTKBranchCode").value
		 var insCompanyCode=document.getElementById("insCompanyCode").value
		 var insBoDoCode=document.forms[1].insBoDoCode.value
		 var sAgentCode=document.forms[1].sAgentCode.value
		 var sGroupPolicyNo=document.forms[1].sGroupPolicyNo.value
		 var startDate = document.forms[1].sStartDate.value;
		var endDate = document.forms[1].sEndDate.value;
		var sType=document.forms[1].sType.value
		var sStatus=document.forms[1].sStatus.value
		var eType=document.forms[1].eType.value
		var selectbranchcode=document.getElementById("tTKBranchCode");
    	var ttkbranchname=selectbranchcode.options[selectbranchcode.options.selectedIndex].text;
    	//alert("Type combox Label Text selecttypes=="+ttkbranchname);
    	var selectinscompcode=document.getElementById("insCompanyCode");
    	var inscompname=selectinscompcode.options[selectinscompcode.options.selectedIndex].text;
    	//alert("Type combox Label Text selecttypes=="+inscompname);
    	var selectinsbodocode=document.forms[1].insBoDoCode;
    	var inscompbodo=selectinsbodocode.options[selectinsbodocode.options.selectedIndex].text;
    	//alert("Type combox Label Text selecttypes=="+inscompbodo);
 		var selecttype=document.forms[1].sType;
    	var types=selecttype.options[selecttype.options.selectedIndex].text;
    	//alert("Type combox Label Text selecttypes=="+selecttypes);
    	var selectstatus=document.forms[1].sStatus;
    	var status=selectstatus.options[selectstatus.options.selectedIndex].text;
    	var selectreportID=document.forms[1].reportID;
    	var reportname=selectreportID.options[selectreportID.options.selectedIndex].text;
    	var selectetype=document.forms[1].eType;
    	var enrtype=selectetype.options[selectetype.options.selectedIndex].text;
    	
    	var reportlinkname= "Enrollment Monitor";
    	var startlabelvalue=document.getElementById("lb").innerText;
         
         var endlabelvalue=document.getElementById("lab").innerText;
         
		var parameterValue;//= new String("|S|");
		document.forms[1].parameterValues.value=parameterValue;
		document.forms[1].mode.value="doGenerateReport";
			
	   if(document.forms[1].reportType.value=="EXL")
		{
		    if(document.forms[1].reportID.value=="EMPR")
	     {
		       var fileName = "reports/misreports/EnrollMonitorPolicyEXL.jrxml";
	     }
	     else if(document.forms[1].reportID.value=="EMMR")
	     {
		       var fileName = "reports/misreports/EnrollMonitorMemberEXL.jrxml";
	     }
	}//END OF if(document.forms[1].reportType.value=="EXL")
	if(document.forms[1].reportType.value=="PDF")
	{
		if(document.forms[1].reportID.value=="EMPR")
	     {
		       var fileName = "reports/misreports/EnrollMonitorPolicyEXL.jrxml";
	     }
	     else if(document.forms[1].reportID.value=="EMMR")
	     {
		      var fileName = "reports/misreports/EnrollMonitorMemberEXL.jrxml";
	     }
	}// END OF if(document.forms[1].reportType.value=="PDF")
	
	parameterValue = "|"+TTKBranchCode+"|"+insCompanyCode+"|"+insBoDoCode+"|"+sAgentCode+"|"+eType+"|"+sType+"|"+sStatus+"|"+startDate+"|"+endDate+"|"+sGroupPolicyNo+"|";
	//var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+fileName+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate+"&Location="+TTKBranchCode;
	var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+fileName+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate+"&Location="+TTKBranchCode+"&reportlinkname="+reportlinkname+"&sGroupPolicyNo="+sGroupPolicyNo+"&sAgentCode="+sAgentCode+"&reportname="+reportname+"&types="+types+"&status="+status+"&ttkbranchname="+ttkbranchname+"&inscompname="+inscompname+"&inscompbodo="+inscompbodo+"&enrtype="+enrtype+"&startlabelvalue="+startlabelvalue+"&endlabelvalue="+endlabelvalue;
	var openPage = "/EnrolMonitorReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateReport