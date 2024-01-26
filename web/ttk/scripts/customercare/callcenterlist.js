//java script for the call center list screen in the customer care flow.

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/CallCenterSearchAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="/CallCenterSearchAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/CallCenterSearchAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/CallCenterSearchAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

/*function isValidated()
{
		//checks if Start Date is entered
		if(document.forms[1].startDate.value.length!=0)
		{
			if(isDate(document.forms[1].startDate,"Start Date")==false)
				return false;
				document.forms[1].startDate.focus();
		}// end of if(document.forms[1].startDate.value.length!=0)

		//checks if End Date is entered
		if(document.forms[1].endDate.value.length!=0)
		{
			if(isDate(document.forms[1].endDate,"End Date")==false)
				return false;
				document.forms[1].endDate.focus();
		}// end of if(document.forms[1].endDate.value.length!=0)

		//checks if both dates entered
		if(document.forms[1].startDate.value.length!=0 && document.forms[1].endDate.value.length!=0)
		{
			if(compareDates("startDate","Start Date","endDate","End Date","greater than")==false)
				return false;
		}// end of if(document.forms[1].startDate.value.length!=0 && document.forms[1].endDate.value.length!=0)
		return true;
}//end of isValidated()

*/
// function to search
function onSearch()
{
   if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	document.forms[1].mode.value = "doSearch";
	document.forms[1].action = "/CallCenterSearchAction.do";
	JS_SecondSubmit=true;
    document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

// JavaScript edit() function
function edit(rownum)
{
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=1,menubar=1,width=1010,height=600";
	window.open("/CallCenterSearchAction.do?mode=doLogDetails&rownum="+rownum,'', features);
}// End of edit()

// JavaScript edit2() function
function edit2(rownum)
{
	document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="Call Details";
    document.forms[1].mode.value="doViewCallCenter";
    document.forms[1].action = "/CallCenterDetailsAction.do";
    document.forms[1].submit();
}// End of edit2()

function onAdd()
{
	document.forms[1].tab.value="Call Details";
	document.forms[1].mode.value="doAdd";
    document.forms[1].action = "/CallCenterDetailsAction.do";
    document.forms[1].submit();
}//end of onAdd()

//function to delete
function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected record(s)?");
		if(msg)
		{
			document.forms[1].mode.value = "DeleteList";
			document.forms[1].action = "/CallCenterSearchAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function changeCallerFields()
{
	document.forms[1].mode.value="doChangeLogType";
	document.forms[1].action="/CallCenterSearchAction.do";
	document.forms[1].submit();
}//end of changeCallerFields()

function SelectEnrollment(closeFlag)
{	
	document.forms[1].child.value="EnrollmentList";
	document.forms[1].mode.value="doSelectEnrollment";
	document.forms[1].action="/CallCenterSearchAction.do?closeFlag="+closeFlag;
	document.forms[1].submit();
}//end of SelectEnrollment()

function SelectCorporate()
{
	document.forms[1].child.value="GroupList";
	document.forms[1].mode.value="doSelectCorporate";
	document.forms[1].action="/CallCenterSearchAction.do";
	document.forms[1].submit();
}//end of SelectCorporate()

function SelectHospital()
{
	document.forms[1].child.value="HospitalList";
	document.forms[1].mode.value="doSelectHospital";
	document.forms[1].action="/CallCenterSearchAction.do";
	document.forms[1].submit();
}//end of SelectHospital()

function SelectInsurance()
{
	document.forms[1].child.value="Insurance Company";
	document.forms[1].mode.value="doSelectInsurance";
	document.forms[1].action="/CallCenterSearchAction.do";
	document.forms[1].submit();
}//end of SelectInsurance()

function onUserIcon(rownum)
{
	document.forms[1].child.value="Assign";
	document.forms[1].mode.value="doAssignTo";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action="/AssignAction.do";
	document.forms[1].submit();
}//end of onUserIcon(rownum)

function ClearEnrollment()
{
	document.forms[1].mode.value="doClearEnrollment";
	document.forms[1].action="/CallCenterSearchAction.do";
	document.forms[1].submit();
}//end of ClearEnrollment()

function ClearCorporate()
{
	document.forms[1].mode.value="doClearCorporate";
	document.forms[1].action="/CallCenterSearchAction.do";
	document.forms[1].submit();
}//end of ClearCorporate()

function ClearHospital()
{
	document.forms[1].mode.value="doClearHospital";
	document.forms[1].action="/CallCenterSearchAction.do";
	document.forms[1].submit();
}//end of ClearHospital()

function ClearInsurance()
{
	document.forms[1].mode.value="doClearInsurance";
	document.forms[1].action="/CallCenterSearchAction.do";
	document.forms[1].submit();
}//end of ClearInsurance()

function onViewDetail()
{
	document.forms[1].child.value="CallCenterInfoList";
	document.forms[1].mode.value="doViewCallDetail";
	document.forms[1].action="/CallCenterSearchAction.do";
	document.forms[1].submit();
}