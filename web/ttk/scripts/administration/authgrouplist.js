function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doDefault";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/AuthGroupConfiguration.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doViewAuthGroup";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="General";
    document.forms[1].action = "/EditAuthGroupAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doDefault";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/AuthGroupConfiguration.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
	
function onAddGroup()
{
	document.forms[1].mode.value="onAddGroup";
	document.forms[1].action="/EditAuthGroupAction.do";
	document.forms[1].submit();
}//end of onAddGroup()

function onClose()
{   
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/AuthGroupConfiguration.do";
    document.forms[1].submit();
}//end of onClose()