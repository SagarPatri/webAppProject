//java script for the revision history screen in the empanelment of hospital flow.

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/HospitalTariffRevisePlanAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)
// JavaScript function for Page Indexing
function pageIndex(pagenumber)
{
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action="HospitalTariffRevisePlanAction.do";
	document.forms[1].submit();
}// End of pageIndex()

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/HospitalTariffRevisePlanAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/HospitalTariffRevisePlanAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

// funciton to edit
function edit(rownum)
{
	document.forms[1].mode.value="doViewPlanPackage";
	document.forms[1].child.value="PlanPackage";
	document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/HospitalTariffRevisePlanAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

// function to add new plan
function onAdd()
{
	document.forms[1].mode.value="doAdd";
	document.forms[1].reviseMode.value="add";
	document.forms[1].child.value="Add/Edit Tariff Plan";
	document.forms[1].action="/HospitalTariffRevisePlanAction.do";
	document.forms[1].submit();
}//end of onAdd()

// function to search
function onSearch()
{
if(!JS_SecondSubmit)
 {
 if(isValidated())
     {
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/HospitalTariffRevisePlanAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
    }
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

// function to go to previous screen
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doSearch";
	document.forms[1].child.value="";
    document.forms[1].action = "/TariffAction.do";
    document.forms[1].submit();
}//end of onClose()

//function to validate date fields
function isValidated()
{
	document.forms[1].sStartDate.value=trim(document.forms[1].sStartDate.value);
	document.forms[1].sEndDate.value=trim(document.forms[1].sEndDate.value);
	if(document.forms[1].sStartDate.value.length!=0)
	{
		//checks for the validation of Start Date
		if(isDate(document.forms[1].sStartDate,"Start Date")==false)
			return false;
	}

	if(document.forms[1].sEndDate.value.length!=0)
	{
		//checks for the validation of end date
		if(isDate(document.forms[1].sEndDate,"End Date")==false)
			return false;
	}
	if(document.forms[1].sStartDate.value.length!=0 && document.forms[1].sEndDate.value.length!=0)
	{
		//checks if both the dates are entered
		if(compareDates("sStartDate","Start Date","sEndDate","End Date",'greater than')==false)
			return false;
	}
	return true;
}//end of isValidated()

// function of edit2()
function edit2(rownum)
{
	document.forms[1].rownum.value=rownum;
	document.forms[1].mode.value="doViewRevisePlan";
	document.forms[1].reviseMode.value="edit";
	document.forms[1].action="/HospitalTariffRevisePlanAction.do";
	document.forms[1].submit();
}//end of edit2(rownum)