//1352   
function onUploadFiles()
{
	var file=document.getElementById("file").value;
	var fileExt=(file.substring(file.lastIndexOf(".")+1,file.length)).toLowerCase();
	var strRemarks=document.getElementById("remarks").value;
	var strDocumentType=document.getElementById("documentType").value;
	//vidalId,policyNumber
	var strVidalId=document.getElementById("vidalId").value;
	var strPolicyNumber=document.getElementById("policyNumber").value;
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
	
	if(document.forms[0].sublink.value=="Documents")
	{
		if(strVidalId=="")	{
			alert("Please Enter Vidal Id");
			return false;
	       }
	}
	document.forms[1].mode.value="doHospitalUpload";
	document.forms[1].action="/InsCompFileUpload.do";
	document.forms[1].submit();
	
}

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value = "doClose";
	document.forms[1].action = "/OnlineInsCompFileUploadAction.do";
	document.forms[1].submit();	
}//end of onClose() 



function edit(rownum)
{
    document.forms[1].mode.value="doViewFile";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/OnlineInsCompFileUploadAction.do";
    document.forms[1].submit();
}//end of edit(rownum)


function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/OnlineInsCompFileUploadAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/OnlineInsCompFileUploadAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/OnlineInsCompFileUploadAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/OnlineInsCompFileUploadAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onViewDocFiles()
{
	 document.forms[1].reset();
	    document.forms[1].mode.value ="";
	    document.forms[1].action = "/OnlineInsCompFileUploadAction.do";
	    document.forms[1].submit();
}
