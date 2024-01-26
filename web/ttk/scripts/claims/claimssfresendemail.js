//java script for the line items screen
//KOC 1179
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/ShortfallResendEmailAction.do?";
	document.forms[1].submit();
}//end of onClose()



function onViewFile(ShortfallNo)
{
	 var ShortfallNoReq=ShortfallNo;
		document.forms[1].mode.value="doViewFile";
		document.forms[1].action="/ShortfallResendEmailAction.do?shortfallnoreq="+ShortfallNoReq+"";
		document.forms[1].submit();
}
// End of edit()

function onResendShortfallEmail(ShortfallType,ShortfallTypeDesc)
{
	document.forms[1].mode.value="doResendShortfallEmail";
	document.forms[1].action="/ShortfallResendEmailAction.do?ShortfallType="+ShortfallType+"&ShortfallTypeDesc="+ShortfallTypeDesc+"";
	document.forms[1].submit();
}

function onResendEmailClose()
{
        document.forms[1].mode.value="doAfterResendEmailClose";
        document.forms[1].child.value="ShortFall Details";
        document.forms[1].action = "/ShortFallDetailsAction.do";
    	document.forms[1].submit();
}