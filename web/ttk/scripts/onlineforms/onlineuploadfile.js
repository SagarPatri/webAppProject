//1352   
function onuUploadPdfFiles()
{
	var file=document.getElementById("file").value;
	var fileExt=(file.substring(file.lastIndexOf(".")+1,file.length)).toLowerCase();
	var strRemarks=document.getElementById("remarks").value;
	var strDocumentType=document.getElementById("documentType").value;
	if(fileExt!="pdf"){
		alert("Please select PDF file ");
		return false;
	}
	if(strRemarks=="")	{
		alert("Please Enter Remarks");
		return false;
       }
	   else if(strRemarks.length>200)
       {
		alert("Please Enter Remarks less than 200 Characters");
		return false;
	   }
	if(strDocumentType=="")	{
		alert("Please select type of Document");
		return false;
       }
	document.forms[1].mode.value="doSubmitFile";
	document.forms[1].action="/EmployeeFileUpload.do";
	document.forms[1].submit();
	
}

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value = "doClose";
	document.forms[1].action = "/FileUpload.do";
	document.forms[1].submit();	
}//end of onClose() 



function edit(rownum)
{
    document.forms[1].mode.value="doViewFile";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EmployeeUploadFileList.do";
    document.forms[1].submit();
}//end of edit(rownum)


function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/EmployeeUploadFileList.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/EmployeeUploadFileList.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/EmployeeUploadFileList.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/EmployeeUploadFileList.do";
    document.forms[1].submit();
}//end of PressForward()


