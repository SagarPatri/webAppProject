
//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doSelectPCS";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/SearchPCSAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/SearchPCSAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/SearchPCSAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/SearchPCSAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/PCSListAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

function onClose()
{
    document.forms[1].mode.value = "doClose";
    document.forms[1].action = "/SearchPCSAction.do";
    document.forms[1].submit();
}//end of onAdd()

function onSearch()
{
if(!JS_SecondSubmit)
 {
    document.forms[1].mode.value = "doSearch";
    document.forms[1].action = "/SearchPCSAction.do";
	JS_SecondSubmit=true
    document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()