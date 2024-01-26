//functions for claimslist screen of claims flow.

function edit(rownum)
{
	var Role = document.forms[1].Role.value;
	if(Role=="ROLE 1")
	{
		document.forms[1].mode.value="doView";
	    document.forms[1].rownum.value=rownum;
	    document.forms[1].tab.value ="General";
	    
	}
	else if(Role=="ROLE 2")
	{
		document.forms[1].mode.value="doMedicalView";
	    document.forms[1].rownum.value=rownum;
	    document.forms[1].tab.value ="Medical";
	}
	else if(Role=="ROLE 3")
	{
		document.forms[1].mode.value="doBillsView";
	    document.forms[1].rownum.value=rownum;
	    document.forms[1].tab.value ="Bills";	
	}
	else
	{
		document.forms[1].mode.value="doView";
	    document.forms[1].rownum.value=rownum;
	    document.forms[1].tab.value ="General";
	}
	document.forms[1].action = "/DataEntryClaimsAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/DataEntryClaimsAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/DataEntryClaimsAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/DataEntryClaimsAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/DataEntryClaimsAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onSearch()
{
   if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	document.forms[1].mode.value = "doSearch";
	document.forms[1].action = "/DataEntryClaimsAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/DataEntryClaimsAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected records ?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/DeleteClaimsAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function onUserIcon(rownum)
{
	alert("you don't have sufficient privilage to perform this task.");
	//document.forms[1].mode.value="doAssign";
	//document.forms[1].rownum.value=rownum;
	//document.forms[1].action="/AssignToAction.do";
	//document.forms[1].submit();
}//end of onUserIcon(rownum)