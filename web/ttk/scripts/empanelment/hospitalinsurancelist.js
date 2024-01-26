//Insurance_corporate_wise_hosp_network

function onClose()
{
	document.forms[1].mode.value="doHospitalClose";
	document.forms[1].action="/HospitalInsuranceList.do";
	document.forms[1].submit();
}//end of onClose()

function onSearch()
{
	document.forms[1].mode.value="doSelectHospital";
	document.forms[1].action="/HospitalInsuranceList.do";
	document.forms[1].submit();
}//end of onSelectGroup()

function onAssociate()
{
	if(getCheckedCount('chkopt',document.forms[1])> 0)
    {
	document.forms[1].mode.value="doAssociate";
	document.forms[1].action="/AssHospitalInsuranceList.do";
	document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
    else
    {
    	alert('Please select atleast one record');
		return true;
	}
}//end of onSelectGroup()

function onExclude()
{
	if(getCheckedCount('chkopt',document.forms[1])> 0)
    {
	document.forms[1].mode.value="doExclude";
	document.forms[1].action="/AssHospitalInsuranceList.do";
	document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
    else
    {
    	alert('Please select atleast one record');
		return true;
	}
}//end of onSelectGroup()

function pageIndex(pagenumber)
{
    document.forms[1].mode.value="doSelectHospital";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/HospitalInsuranceList.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/HospitalInsuranceList.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/HospitalInsuranceList.do";
    document.forms[1].submit();
}//end of PressForward()
