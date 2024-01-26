// JAva Script Function for preauthReferralAdd.jsp
function addPreAuthReferral()
{
	document.forms[1].mode.value="doAdd";
	document.forms[1].tab.value="Generate Letter";
    document.forms[1].action = "/PreAuthReferralEditAction.do";
    document.forms[1].submit();
}

function onReset()
{
    document.forms[1].reset();    
}//end of onReset()

function onSearch()
{
	if(!JS_SecondSubmit)
	 {
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/PreAuthReferralAction.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onEditIcon(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="Generate Letter";
    document.forms[1].action = "/PreAuthReferralAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/PreAuthReferralAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)
//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/PreAuthReferralAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/PreAuthReferralAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/PreAuthReferralAction.do";
    document.forms[1].submit();
}//end of PressForward()

function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
    	//document.forms[1].reset();
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/PreAuthReferralAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()