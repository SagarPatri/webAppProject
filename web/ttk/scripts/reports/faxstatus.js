
function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/FaxStatusReportAction.do";
	document.forms[1].submit();
}//end of onClose()
function onGenerateReport()
{
	if(isValidated())
	{	    
	    trimForm(document.forms[1]);
	    var faxStatus = document.forms[1].faxStatus.value;						
	    var generatedBy = document.forms[1].generatedBy.value;		
	    var startDate = document.forms[1].startDate.value;		
	    var endDate = document.forms[1].endDate.value;			    				
		var parameter = "?mode=doGenerateFaxStatusReport&faxStatus="+faxStatus+"&generatedBy="+generatedBy+"&endDate="+endDate+"&startDate="+startDate;
		var openPage = "/FaxStatusReportAction.do"+parameter;
		var w = screen.availWidth - 10;
		var h = screen.availHeight - 99;
		var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
		window.open(openPage,'',features);		
	}//end of if(isValidated())
}//end of function onGenerateReport

function isValidated()
{
        if(document.forms[1].generatedBy.value.length==0)
		{
     			alert("Generated By is Required");
				return false;
		}// end of if(document.forms[1].exitDate.value.length!=0)

		if(document.forms[1].startDate.value.length!=0)
		{
			if(isDate(document.forms[1].startDate,"Start Date")==false)
				return false;
		}// end of if(document.forms[1].receivedAfter.value.length!=0)
		else
		{			
			    alert('Start Date is Required');
			    return false;		    
		}//end of else
		//checks if Date of Exit is entered
		if(document.forms[1].endDate.value.length!=0)
		{
			if(isDate(document.forms[1].endDate,"End Date")==false)
				return false;
		}// end of if(document.forms[1].exitDate.value.length!=0)
				return true;
}//end of isValidated()