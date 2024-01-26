


function onChangeInsCompany()
{
    	document.forms[1].mode.value="doChangeInsCompany";
    	document.forms[1].focusID.value="ttkoffice";
    	document.forms[1].action="/MISFinanceGReportsAction.do";
    	document.forms[1].submit();
}//end of onChangeCity()

function onChangeInsBoDo()
{
	document.forms[1].mode.value="doChangeInsComDoBo";
   	document.forms[1].focusID.value="comDoBo";
	document.forms[1].action="/MISFinanceGReportsAction.do";
	document.forms[1].submit();
}//end of onChangeInsBoDo

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/MISFinanceGReportsAction.do";
	document.forms[1].submit();
}//end of onClose()

function onGenerateReport()
{
       if(document.getElementById("tTKBranchCode").value=="")
		{
			alert("Please Select Vidal Health Branch");
			document.forms[1].tTKBranchCode.focus();
			return false;
		}
		 if(document.getElementById("insCompanyCode").value=="")
		{
			alert("Please Select Healthcare Company");
			document.forms[1].insCompanyCode.focus();
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
		var TTKBranchCode=document.getElementById("tTKBranchCode").value
		var insCompanyCode=document.getElementById("insCompanyCode").value
		var insBoDoCode=document.forms[1].insBoDoCode.value
		var startDate = document.forms[1].sStartDate.value;
		var endDate = document.forms[1].sEndDate.value;
		var selectbranchcode=document.getElementById("tTKBranchCode");
		var ttkbranchname=selectbranchcode.options[selectbranchcode.options.selectedIndex].text;
		var selectinscompcode=document.getElementById("insCompanyCode");
		var inscompname=selectinscompcode.options[selectinscompcode.options.selectedIndex].text;
		var selectinsbodocode=document.forms[1].insBoDoCode;
		var inscompbodo=selectinsbodocode.options[selectinsbodocode.options.selectedIndex].text;
		var selectreportID=document.forms[1].reportID;
		var reportname=selectreportID.options[selectreportID.options.selectedIndex].text;
		var reportlinkname= "Payment Pending Report";
		
         
		var parameterValue;//= new String("|S|");
		document.forms[1].parameterValues.value=parameterValue;
		document.forms[1].mode.value="doGenerateReport";
	    if(document.forms[1].reportType.value=="EXL")
		{
	    	if(document.forms[1].reportID.value=="FGPR")
	     	{
		       var fileName = "reports/misreports/FGPendingReportEXL.jrxml";
	     	}
		}//END OF if(document.forms[1].reportType.value=="EXL")
		if(document.forms[1].reportType.value=="PDF")
		{
			if(document.forms[1].reportID.value=="FGPR")
	     	{
		      var fileName = "reports/misreports/FGPendingReportEXL.jrxml";
	     	}
		}// END OF if(document.forms[1].reportType.value=="PDF")
	
	parameterValue = "|"+TTKBranchCode+"|"+insCompanyCode+"|"+insBoDoCode+"|"+startDate+"|"+endDate+"|";
	var partmeter = "?mode=doGenerateReport&parameter="+parameterValue+"&fileName="+fileName+"&reportID="+document.forms[1].reportID.value+"&reportType="+document.forms[1].reportType.value+"&startDate="+startDate+"&endDate="+endDate+"&Location="+TTKBranchCode+"&reportlinkname="+reportlinkname+"&reportname="+reportname+"&ttkbranchname="+ttkbranchname+"&inscompname="+inscompname+"&inscompbodo="+inscompbodo;
	var openPage = "/MISFinanceGReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateReport
