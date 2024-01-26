//javascript function for ibmadditioncutoffrptparams jsp

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/IBMReportsAction.do";
	document.forms[1].submit();
}//end of onClose()

function onSelectGroup()
{
	document.forms[1].mode.value="doSelectGroup";
	document.forms[1].reportID.value="IBMAdditionMaxRec";
	document.forms[1].action="/IBMReportsAction.do";
	document.forms[1].submit();
}//end of onSelectGroup()

function onGroupChange()
{
	/*document.forms[1].mode.value="doGroupChange";
	document.forms[1].action="/IBMReportsAction.do";
	document.forms[1].submit();*/
}//end of onGroupChange()

function onSelectPolicy()
{
	if(document.forms[1].policySeqID.value!=""){
		document.forms[1].mode.value="doAdditioncutoffMaxRecSelectPolicy";
		document.forms[1].action="/IBMReportsAction.do";
		document.forms[1].submit();
	}//end of if(document.forms[1].policySeqID.value!="")
	else {
		document.forms[1].sReportFrom.value="";
		document.forms[1].sReportTo.value="";
	}//end of else
}//end of onSelectPolicy()

function onGenerateIbmAdditioncutoffMaxRecReport()
{
	var groupID = document.forms[1].groupId.value;
	//var policySeqID = document.forms[1].policySeqID.value;
	var policySeqID = document.forms[1].IBMPolicyNo.value;
	var reportFrom=document.forms[1].sReportFrom.value;
	var reportTo=document.forms[1].sReportTo.value;
	//var reportType = document.forms[1].reportType.value;
	
	if(document.forms[1].groupId.value == "")
	{
		alert('Please select Group ID');
		document.forms[1].groupId.focus();
		return false;
	}//end of if(document.forms[1].groupId.value == "")	
   //if(document.forms[1].policySeqID.value=="")
    //{
    	//alert('Please select Policy No.');
    	//document.forms[1].policySeqID.focus();
    	//return false;
    //}///end of if(document.forms[1].policySeqID.value="")
	/*if(document.forms[1].IBMPolicyNo.value=="")
    {
    	alert('Please select Policy No.');
    	document.forms[1].IBMPolicyNo.focus();
    	return false;
    }//end of if(document.forms[1].IBMPolicyNo.value="")*/
    if(reportFrom=='')
    {
    	alert('Please enter Report From');
    	return false;
    }//end of if(reportFrom=='')
    else if(reportTo==''){
    	alert('Please enter Report To');
    	return false;
    }//end of else
    
   	if(!isDate(document.forms[1].sReportFrom,"Report From"))
   	{
   		document.forms[1].sReportFrom.focus();
   		return false;
   	}
   	if(!isDate(document.forms[1].sReportTo,"Report To"))
   	{
   		document.forms[1].sReportTo.focus();
   		return false;
   	}
    if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))
    {
    	return false;
    }//end of if(!compareDates('sReportFrom','Report From','sReportTo','Report To','greater than'))
   // parameterValue ="|"+policySeqID+"|"+reportFrom+"|"+reportTo+"|";
	//var partmeter = "?mode=doGenerateLoginChildBornRpt&parameter="+parameterValue+"&fileName=reports/ibm/IBMLoginChildBornTOFemaleEmployee.jrxml&reportID=LoginChildBorn&reportType="+reportType
					//+"&sFrom="+reportFrom+"&sTo="+reportTo;
	
	//var partmeter = "?mode=doGenerateAdditioncutoffMaxRecRpt&parameter1="+groupID+"&parameter2="+reportFrom+"&parameter3="+reportTo+"&sFrom="+reportFrom+"&sTo="+reportTo;
    
    //var partmeter = "?mode=doGenerateAdditioncutoffMaxRecRpt&parameter1="+policySeqID+"&parameter2="+reportFrom+"&parameter3="+reportTo+"&sFrom="+reportFrom+"&sTo="+reportTo;
    
    var partmeter = "?mode=doGenerateAdditioncutoffMaxRecRpt&parameter1="+policySeqID+"&parameter2="+reportFrom+"&parameter3="+reportTo+"&reportID=IBMAdditionMaxRec&sFrom="+reportFrom+"&sTo="+reportTo;				
	var openPage = "/IBMReportsAction.do"+partmeter;
	
	//var openPage="NewReport.jsp?groupId="+groupID+"&reportFrom="+reportFrom+"&reportTo="+reportTo+"";
	
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	document.forms[1].mode.value="doGenerateAdditioncutoffMaxRecRpt";
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateIbmLoginChildBornReport


