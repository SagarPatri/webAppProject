//accinfoenrollment.js is called form accinfoenrollment.jsp in AccountInfo flow

//function onClose
function onClose()
{
    document.forms[1].mode.value="doClose";
    document.forms[1].child.value="";
    document.forms[1].action="/AccInfoMemberAction.do";
    document.forms[1].submit();
}//end of onClose()

