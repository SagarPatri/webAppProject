//java script for the hospital search screen in the empanelment of hospital flow.

//function to get the substatus change
function onStatusChanged()
{
	document.forms[1].mode.value="doStatusChange";
	document.forms[1].action="/HospitalAction.do";
	document.forms[1].submit();
}//end of onStatusChanged()
//function to call edit screen
function edit(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="General";
    document.forms[1].action = "/EditHospitalSearchAction.do";
    document.forms[1].submit();
}//end of edit(rownum)
//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/HospitalAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)
//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/HospitalAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/HospitalSearchAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/HospitalSearchAction.do";
    document.forms[1].submit();
}//end of PressForward()
//function to display pages based on search criteria
function onSearch()
{
	if(!JS_SecondSubmit)
	 {
	    document.forms[1].sEmpanelmentNo.value=trim(document.forms[1].sEmpanelmentNo.value);
	    document.forms[1].sHospitalName.value=trim(document.forms[1].sHospitalName.value);
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/HospitalSearchAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()
//function to add hospital
function onAddHospital()
{
	document.forms[1].reset();
    document.forms[1].rownum.value = "";
    document.forms[1].mode.value = "doAdd";
    document.forms[1].tab.value="General";
    document.forms[1].action = "/EditHospitalSearchAction.do";
    document.forms[1].submit();
}//end of onAddHospital()
//copy the select hospitl to webboard
function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
    	//document.forms[1].reset();
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/HospitalAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()

function OnDownloadProviderDetails()									
{
	var partmeter = "?mode=doGenerateProviderTariff";
	var openPage = "/HospitalSearchAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}
