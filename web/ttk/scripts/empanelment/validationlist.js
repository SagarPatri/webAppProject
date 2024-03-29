//java script for the validation details screen in the empanelment of hospital flow.

//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doView";
    document.forms[1].child.value="Validation Details";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EditHospValidationAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/HospitalValidationAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display next set of pages
function PressForward()
{ 
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/HospitalValidationAction.do";
    document.forms[1].submit();     
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{ 
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/HospitalValidationAction.do";
    document.forms[1].submit();     
}//end of PressBackWard()

function onAddValidation()
{
    document.forms[1].rownum.value="";
    document.forms[1].mode.value = "doAdd";
    document.forms[1].child.value = "Validation Details";
    document.forms[1].action = "/EditHospValidationAction.do";
    document.forms[1].submit();
}//end of onAddEditUser()

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    { 
	var msg = confirm("Are you sure you want to delete the selected Validations ?");
	if(msg)
	{
	    document.forms[1].mode.value = "doDeleteList";
	    document.forms[1].action = "/HospitalValidationAction.do";
	    document.forms[1].submit();
	}//end of if(msg)  
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()