//javascript for preauthclmdetailrpt.jsp

function onSelectPolicy()
{
	if(document.forms[1].policySeqID.value ==""){
		document.forms[1].sReportFrom.value="";
		document.forms[1].sReportTo.value="";
	}//end of if(document.forms[1].policySeqID.value =="")
	
}//end of onSelectPolicy()

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/PreAuthClmReportAction.do";
	document.forms[1].submit();
}//end of onClose()

function onSelectGroup()
{
	document.forms[1].mode.value="doSelectGroup";
	document.forms[1].reportID.value="PreAuthClmDetail";
	document.forms[1].action="/PreAuthClmReportAction.do";
	document.forms[1].submit();
}//end of onSelectGroup()


function onGeneratePreAuthClmReport()
{
	var groupID = document.forms[1].groupId.value;
	var policySeqID = document.forms[1].policySeqID.value;
	var reportFrom=document.forms[1].sReportFrom.value;
	var reportTo=document.forms[1].sReportTo.value;
	var reportType = document.forms[1].reportType.value;
	
	if(document.forms[1].groupId.value == "")
	{
		alert('Please select Group ID');
		document.forms[1].groupId.focus();
		return false;
	}//end of if(document.forms[1].groupId.value == "")	
    if(document.forms[1].policySeqID.value=="")
    {
    	alert('Please select Scheme No.');
    	document.forms[1].policySeqID.focus();
    	return false;
    }//end of if(document.forms[1].policySeqID.value="")
    
    if((document.forms[1].sReportFrom.value != "")  && (!isDate(document.forms[1].sReportFrom,"Report From")))
   	{
   		document.forms[1].sReportFrom.focus();
   		return false;
   	}//end of if(!isDate(document.forms[1].sReportFrom,"Report From"))
    if((document.forms[1].sReportTo.value != "")  && (!isDate(document.forms[1].sReportTo,"Report To")))
   	{
   		document.forms[1].sReportTo.focus();
   		return false;
   	}//end of if(!isDate(document.forms[1].sReportTo,"Report To"))
   	if((document.forms[1].sReportFrom.value != "") && (document.forms[1].sReportTo.value != ""))
   	{
   		if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))
   		{
   			return false;
   		}//end of if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))
   	}//end of if(document.forms[1].sReportFrom.value !="" && document.forms[1].sReportTo.value !="" )
   	
   	parameterValue ="|"+policySeqID+"|"+reportFrom+"|"+reportTo+"|";
	var partmeter = "?mode=doGeneratePreAuthClmRpt&parameter="+parameterValue+"&fileName=reports/preauth/PreauthClmReport.jrxml&reportID=PreAuthClmDetail&reportType="+reportType
					+"&sFrom="+reportFrom+"&sTo="+reportTo;
	var openPage = "/PreAuthClmReportAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	document.forms[1].mode.value="doGeneratePreAuthClmRpt";
	window.open(openPage,'',features);
}//end of onGeneratePreAuthClmReport()