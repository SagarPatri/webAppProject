//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/NotificationListAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/NotificationListAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function is called onclick of the hyperlink in the grid
function edit(rownum)
{
    document.forms[1].mode.value="doViewNotification";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EditNotificationAction.do";
    document.forms[1].submit();
}//end of edit(rownum)