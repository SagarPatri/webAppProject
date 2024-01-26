function onSave()
{
	document.forms[1].mode.value = "doSaveVatConfigureation";
	document.forms[1].action = "/Configuration.do";
	document.forms[1].submit();	
}//end of onClose()




function onClose()
{
	document.forms[1].mode.value = "doClose";
	document.forms[1].action = "/Configuration.do";
	document.forms[1].submit();	
}//end of onClose()