//javascript used in dailytransferlist.jsp of Finance->TDS Configuration

//functin to search
function onSearch()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		if(isValidated()==true)
		{
			document.forms[1].mode.value = "doSearch";
	    	document.forms[1].action = "/DailyTransferAction.do";
	    	JS_SecondSubmit=true
	    	document.forms[1].submit();
		}//end of if(isValidated()==true)
	}//end of if(!JS_SecondSubmit)
}//end of onSearch()

//function to validate fields for search
function isValidated()
{
	//checks if DV Received Date is entered
	if(document.forms[1].sStartDate.value.length!=0)
	{
		if(isDate(document.forms[1].sStartDate,"Start Date")==false)
		{
			document.forms[1].sStartDate.focus();
			return false;			
		}//end of if(isDate(document.forms[1].sStartDate,"Start Date")==false)
	}// end of if(document.forms[1].sStartDate.value.length!=0)	
	if(document.forms[1].sEndDate.value.length!=0)
	{
		if(isDate(document.forms[1].sEndDate,"End Date")==false)
		{
			document.forms[1].sEndDate.focus();
			return false;			
		}//end of if(isDate(document.forms[1].sEndDate,"End Date")==false)
	}// end of if(document.forms[1].sEndDate.value.length!=0)
	if(document.forms[1].sStartDate.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
	{
		if(compareDates("sStartDate","Start Date","sEndDate","End Date","greater than")==false)
		return false;
	}// end of if(document.forms[1].startDate.value.length!=0 && document.forms[1].endDate.value.length!=0)
	if(document.forms[1].sDailyRemitStatusID.value=="TDSC")
	{
		if(document.forms[1].sStartDate.value.length==0)
		{
			alert("Please enter the Start Date.");
			document.forms[1].sStartDate.focus();
			return false;
		}//end of if(document.forms[1].sStartDate.value.length==0)	
	}//end of if(document.forms[1].sDailyRemitStatusID.value=="TDSC")	
	return true;
}//end of function isValidated()

//function to Generate Daily Transfer
function generateDailyTransfer()
{
	if(!mChkboxValidation(document.forms[1]))
    {
		document.forms[1].mode.value = "doGenerateDailyTransfer";
		document.forms[1].action = "/DailyTransferAction.do";	
		document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of function generateDailyTransfer()

//function for sorting.
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/DailyTransferAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/DailyTransferAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/DailyTransferAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/DailyTransferAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//this function is for checkbox validation
function mChkboxValidation(objform)
{
	with(objform)
	{
		for(var i=0;i<elements.length;i++)
		{
			if(objform.elements[i].name == "chkopt")
			{
				if (objform.elements[i].checked)
				{
					iFlag = 1;
					break;
				}//end of if (objform.elements[i].checked)
				else
				{
					iFlag = 0;
				}//end of else
			}//end of if(objform.elements[i].name == "chkopt")
		}//end of for(var i=0;i<elements.length;i++)
	}//end of with(objform)
	if (iFlag == 0)
	{
		alert('Please select atleast one record');
		return true;
	}//end of if (iFlag == 0)
	else
	{
		return false;
	}//end of else
}//end of function mChkboxValidation(objform)

function dailyReport()
{	
	var reportID = document.forms[1].sDailyRemitStatusID.value;
	var insuranceCompany = document.forms[1].sInsuranceCompanyID.value;
	var startDate = document.forms[1].sStartDate.value;
	var endDate = document.forms[1].sEndDate.value;
	var fileName = "";
	var partmeter = "";
	var openPage = "";
	if(reportID == "TDSP")
	{
		fileName = "reports/finance/DailyTransferPending.jrxml";
		reportID = "DailyTransferPending";
		openPage = "/DailyTransferActionPending.do";
	}//end of if(reportID == "TDSP") 
	else if(reportID == "TDSC")
	{
		fileName = "reports/finance/DailyTransferComplete.jrxml";
		reportID = "DailyTransferComplete";
		openPage = "/DailyTransferActionComplete.do";
	}//end of else if(reportID == "TDSC")
	else
	{
		fileName = "generalreports/EmptyReprot.jrxml";
		openPage = "/DailyTransferAction.do";
	}//end of else
	partmeter = "?mode=showDailyReport&fileName="+fileName+"&reportID="+reportID+"&insuranceCompany="+insuranceCompany+"&startDate="+startDate+"&endDate="+endDate+"&reportType=EXL";
	openPage = openPage+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=no,width="+w+",height="+h;
	window.open(openPage,'',features);			
}//end of hospitalReport()