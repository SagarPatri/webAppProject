function onSave(){
	document.forms[1].mode.value="doSaveCapitationCategory";
	document.forms[1].action="/CapitationCategoryAction.do";
	document.forms[1].submit();
}//end of onSave()


function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value = "doClose";
	document.forms[1].action = "/InsuranceApproveConfiguration.do";
	document.forms[1].submit();	
}//end of onClose() 