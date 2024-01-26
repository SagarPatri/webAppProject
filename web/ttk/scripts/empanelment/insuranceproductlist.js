//java script for the insurance product list screen in the empanelment of insurance flow.

function edit(rownum)
{
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EditInsuranceProductAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to sort based on the link selected
//function to display next set of pages
function PressForward()
{ 
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/InsuranceProductAction.do";
    document.forms[1].submit();     
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{ 
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/InsuranceProductAction.do";
    document.forms[1].submit();     
}//end of PressBackWard()

function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/InsuranceProductAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/InsuranceProductAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value="doViewCompanySummary";
	document.forms[1].child.value="";
    document.forms[1].action = "/InsuranceProductAction.do";
    document.forms[1].submit();
}// End of onClose()

function onAddEditInsuranceProduct()
{
    document.forms[1].mode.value = "doAdd";
    document.forms[1].action = "/EditInsuranceProductAction.do";
    document.forms[1].submit();	
}// End onAddEditInsuranceProduct()

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    { 
	var msg = confirm("Are you sure you want to delete the selected Products ?");
	if(msg)
	{
	    document.forms[1].mode.value = "doDeleteList";
	    document.forms[1].action = "/InsuranceProductAction.do";
	    document.forms[1].submit();
	}//end of if(msg)  
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()