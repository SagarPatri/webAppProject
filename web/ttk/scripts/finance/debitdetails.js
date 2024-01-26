function onSave()
{
	if(!JS_SecondSubmit)
    {
	    trimForm(document.forms[1]);
	    document.forms[1].mode.value="doSave";
	    document.forms[1].action = "/EditDebitNoteAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
	}    
}//end of onSave()

function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
    	document.forms[1].mode.value="doReset";
    	document.forms[1].action = "/SaveDebitNoteAction.do";
    	document.forms[1].submit();
    }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()

function setDebitType()
{
	
	 var debittype= document.getElementById("debitNoteTypeID").value;
	  document.forms[1].mode.value="setDebitType";
	   document.forms[1].action="/SaveDebitNoteAction.do";
	   document.forms[1].submit();
}//end of setNetWorkType()



function onClose()
{
	if(!TrackChanges()) return false;
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/DebitNoteAction.do";
    document.forms[1].submit();
}//end of onClose()

function onAssociateClaims()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDefault";
	document.forms[1].action = "/AssociatedClaimsSearchAction.do";
    document.forms[1].submit();	
}//end of onAssociate()










function onGenerateReport()
{
	// Changes added for Debit Note CR KOC1163
	var parameterValue = "||"+document.forms[1].debitNoteSeqID.value+"|||D|";
	var reporttypeid = document.forms[1].reportTypeID.value;
	if(reporttypeid=="FinPenRpt")
	{
		var parameterValue = "|"+document.forms[1].debitNoteSeqID.value+"|"+document.forms[1].floatSeqID.value+"|";
		var openPage = "ReportsAction.do?mode=doDebitNoteReport&reportType=EXL&reportTypeID="+reporttypeid+"&parameter="+parameterValue;
	}	
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}//end of onGenerateReport()



function onDraftReport()
{
	
	var debitNoteSeqID = document.forms[1].debitNoteSeqID.value;
	var debitNoteTypeID = document.forms[1].debitNoteTypeID.value;
	var reportTypeID = document.forms[1].reportTypeID.value;
	var floatSeqID = document.forms[1].floatSeqID.value;
	
	if(reportTypeID==null||reportTypeID==""){
		
		alert("Please Select ReportType");
		document.forms[1].reportTypeID.focus();
		return false;
	}
	
	else{
		
	var openPage = "ReportsAction.do?mode=doDebitNoteDraftReport&reportType=EXCEL&debitNoteSeqID="+debitNoteSeqID+"&debitNoteTypeID="+debitNoteTypeID+"&reportTypeID="+reportTypeID+"&floatSeqID="+floatSeqID;
	
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	}
	
}




