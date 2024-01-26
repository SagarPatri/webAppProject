//javascript function for claimspendingrptparams jsp

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/IOBReportsAction.do";
	document.forms[1].submit();
}//end of onClose()

function onBatchNumber()
{
	document.forms[1].mode.value="doSelectBatch";
	document.forms[1].reportID.value="IOBBatRpt";
	document.forms[1].action="/IOBReportsAction.do";
	document.forms[1].submit();
}//end of onBatchNumber()

function onSelectGroup()
{
	document.forms[1].mode.value="doSelectGroup";
	document.forms[1].reportID.value="IOBBatRpt";
	document.forms[1].action="/IOBReportsAction.do";
	document.forms[1].submit();
}//end of onBatchNumber()

function onChangeBatch()
{
	selObj = document.forms[1].selectBatch;
    var selVal = selObj.options[selObj.selectedIndex].value;
    if(selVal=='SBI')
    {
    	document.getElementById('batch').style.display = "";
    }//end of if(selVal=='SBI')
    else
    {
    	document.getElementById('batch').style.display = "none";
    	document.forms[1].batchNo.value="";
    }//end of else
}//end of onChangeBatch

function onLoadBatch()
{
	selObj = document.forms[1].selectBatch;
    var selVal = selObj.options[selObj.selectedIndex].value;
    if(selVal=='SBI')
    {
    	document.getElementById('batch').style.display = "";
    }//end of if(selVal=='SBI')
    else
    {
    	document.getElementById('batch').style.display = "none";
    }//end of else
}//end of onLoadBatch()

function onGenerateIOBReport()
{
	if(document.forms[1].groupId.value == "")
	{
		alert('Please Enter Group ID');
		document.forms[1].groupId.focus();
		return false;
	}//end of if(document.forms[1].groupId.value == "")
	if(document.forms[1].groupId.value.length != 0)
    {
    	if(isAlphaNumeric(document.forms[1].groupId,"Group ID")==false)
    	{
    		return false;
    	}//end of if(isAlphaNumeric(document.forms[1].groupId,"Group ID")==false)
    }//end of if(document.forms[1].groupId.value.length != 0)
	selObj = document.forms[1].selectBatch;
    var selVal = selObj.options[selObj.selectedIndex].value;
    if(selVal == 'SBI')
    {
    	if(document.forms[1].batchNo.value == "")
		{
			alert('Please Select Batch No.');
			return false;
		}//end of if(document.forms[1].groupId.value == "")
    }//end of if(selVal == 'SBI')
	document.forms[1].mode.value="doGenerateIOBRpt";
	var groupID = document.forms[1].groupId.value;
	var batchNo = document.forms[1].batchNo.value;
	var reportType = document.forms[1].reportType.value;
	parameterValue ="|S|"+groupID+"|"+batchNo+"|";
	var partmeter = "?mode=doGenerateIOBRpt&parameter="+parameterValue+"&fileName=reports/enrollment/BOIReport.jrxml&reportID=IOBBatRpt&reportType="+reportType;
	var openPage = "/IOBReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateReport
