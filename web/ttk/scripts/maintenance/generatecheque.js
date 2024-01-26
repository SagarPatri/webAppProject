//java script for generatecheque.jsp
function getBatchNo()
{
	document.forms[1].mode.value="doBatchNumber";
	document.forms[1].action="/ReportsAction.do";
	document.forms[1].submit();
}//end of getBatchNo()

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/GenerateChequeAction.do";
	document.forms[1].submit();
}//end of onClose()

function onPrintCheque(){
	if(document.forms[1].batchNo.value=="")
	{
		alert('Please Enter Batch Number');
        document.forms[1].batchNo.focus();
	}//end of if(document.forms[1].batchNo.value=="")
	else{
		document.forms[1].mode.value="doPrintCheque";
		document.forms[1].action="/GenerateChequeAction.do";
		document.forms[1].submit();
	}//end of else
}//end of onPrintCheque()