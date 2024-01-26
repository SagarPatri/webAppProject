//This is javascript file for callcenterinfolist.jsp

function onChangeType()
{
	document.forms[1].mode.value="doChangeSearchType";
	document.forms[1].action="/CallCenterInfoAction.do";
	document.forms[1].submit();
}

//function to close the Callcenter details screen.
function onClose()
{
	document.forms[1].child.value = "";
	document.forms[1].mode.value = "doClose";
	document.forms[1].action = "/CallCenterSearchAction.do";
    document.forms[1].submit();
}//end of Close()

//function to get the state change
/*function onStateChange()
{
	document.forms[1].mode.value="ChangeCity";
	document.forms[1].action="/CallCenterInfoAction.do";
	document.forms[1].submit();
}//end of onStateChange()*/

function onSearch()
{
	if(!JS_SecondSubmit)
 	{
		JS_SecondSubmit=true;
		trimForm(document.forms[1]);
		document.forms[1].mode.value ="doSearch";
		document.forms[1].action = "/CallCenterInfoAction.do";
	    document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of function onSearch()

function edit(rownum)
{
	document.forms[1].child.value = "EnquiryDetails";
	document.forms[1].rownum.value =rownum;
	document.forms[1].mode.value ="doEnquiryDetail";
	document.forms[1].action = "/CallCenterInfoAction.do";
    document.forms[1].submit();
}
//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/CallCenterInfoAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/CallCenterInfoAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display next set of pages
function PressForward()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/CallCenterInfoAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{   document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/CallCenterInfoAction.do";
    document.forms[1].submit();
}//end of PressBackWard()