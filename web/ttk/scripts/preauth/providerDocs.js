

// JavaScript function for Page Indexing
function doViewProviderDoc(filePath,fileName){
	
	document.forms[1].mode.value="doViewUploadDocs";
	document.forms[1].fileName.value=fileName;	
	document.forms[1].filePath.value=filePath;	
	
	document.forms[1].submit();
}// End of pageIndex()


function onClose()
{
	document.forms[1].mode.value="doClose";	
	document.forms[1].submit();
}//end of onClose()