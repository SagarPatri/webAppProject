//1352
function onuUploadPdfFiles()
{
	var file=document.getElementById("file").value;
	var fileExt=(file.substring(file.lastIndexOf(".")+1,file.length)).toLowerCase();
	var strRemarks=document.getElementById("remarks").value;
	
	if(fileExt ==""){
		alert("Please select file");
		return false;
	}
	//var strDocumentType=document.getElementById("documentType").value;
	/*if(fileExt!="pdf"){
		alert("Please select PDF file ");
		return false;
	}*/
	if(strRemarks=="")	{
		alert("Please Enter Remarks");
		return false;
       }
	/*if(strDocumentType=="")	{
		alert("Please select type of Document");
		return false;
       }*/
	document.forms[1].mode.value="doSubmitFile";
	document.forms[1].action="/SaveFileUploadUnfreeze.do";
	document.forms[1].submit();
	
}

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doView";
	document.forms[1].action="/PreAuthGeneralAction.do";
	document.forms[1].submit();
}//end of Close()


function UnfreezePreAuth()
{
	document.forms[1].mode.value="doUnfreezePatClm";
	document.forms[1].action="/SaveFileUploadUnfreeze.do";
	document.forms[1].submit();
}


