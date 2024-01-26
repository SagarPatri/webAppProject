//java script for the invoice details screen in the finance flow.

function Reset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/InvoiceGeneralAction.do";
		document.forms[1].submit();	
 	}
 	else
 	{
 		document.forms[1].reset(); 			
 	}
}//end of Reset()

function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/UpdateInvoiceAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

// function to close the invoice details screen.
function Close()
{
	if(!TrackChanges()) return false;	
	document.forms[1].mode.value="doClose";
	document.forms[1].tab.value ="Search";
	document.forms[1].action="/InvoiceGeneralAction.do";
	document.forms[1].submit();
}//end of Close()

function onListInvoices()
{
 document.forms[1].mode.value="doDefault";
 document.forms[1].action="/AssociatePolicyAction.do";
 document.forms[1].submit();
}//end of onListInvoices()

//to generate TPACommission Report
function onGenerateTPARpt(startDate,endDate,seqID)
{
	var partmeter = "?mode=doGenerateTPACommissionRpt"+"&reportID=TPACommissionRpt&reportType=EXL"
					+"&startDate="+startDate+"&endDate="+endDate+"&seqID="+seqID;
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);	
	Close();
}
