//javascript used in preauthlist.jsp of Preauth flow

function onEnhancedIcon(rownum)
{
    document.forms[1].mode.value="doViewEnhancedPreauth";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value ="General";
    document.forms[1].action = "/PreAuthAction.do";
    document.forms[1].submit();
}//end of onEnhancedIcon(rownum)

function edit(rownum)
{
    document.forms[1].mode.value="doViewPreauth";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value ="General";
    document.forms[1].action = "/PreAuthAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/PreAuthAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/PreAuthAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/PreAuthAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/PreAuthAction.do";
    document.forms[1].submit();
}//end of PressForward()

function isValidated()
{
		//checks if received Date is entered
		if(document.forms[1].sRecievedDate.value.length!=0)
		{
			if(isDate(document.forms[1].sRecievedDate,"Received Date")==false)
				return false;
		}// end of if(document.forms[1].sRecievedDate.value.length!=0)
		return true;
}//end of isValidated()

function addPreAuth()
{
	document.forms[1].reset();
    document.forms[1].rownum.value = "";
    document.forms[1].tab.value ="General";
    document.forms[1].mode.value = "doAdd";
    document.forms[1].action = "/PreAuthGeneralAction.do";
    document.forms[1].submit();
}//end of function addPreAuth()

function onSearch()
{
	if(!JS_SecondSubmit)
	 {
	    if(isValidated())
		{
			trimForm(document.forms[1]);
	    	document.forms[1].mode.value = "doSearch";
	    	document.forms[1].action = "/PreAuthAction.do";
			JS_SecondSubmit=true
	    	document.forms[1].submit();
	    }// end of if(isValidated())
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected records ?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/DeletePreAuthAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/PreAuthAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()

function onUserIcon(rownum)
{
	document.forms[1].mode.value="doAssign";
	document.forms[1].child.value="Assign";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action="/AssignToAction.do";
	document.forms[1].submit();
}//end of onUserIcon(rownum)