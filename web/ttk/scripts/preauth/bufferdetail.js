//javascript used in bufferdetail.jsp of Preauth flow

function showhideApproved()
{
	var selObj=document.forms[1].statusTypeID;
	var selVal = selObj.options[selObj.selectedIndex].value;
	//var HrAppYN=document.forms[1].hrapproval;
	var strHrAppYN=document.getElementById("hrapproval").value;

	if(selVal == 'BAP')
	{
		if(strHrAppYN =='Y')
		{
		document.getElementById("Approved").style.display="";
		document.getElementById("Approved1").style.display="";
		document.getElementById("fileId").style.display="";//added for hyundai buffer
		
		}
		else{
			document.getElementById("Approved").style.display="";
			document.getElementById("Approved1").style.display="";
			document.getElementById("fileId").style.display="none";//added for hyundai buffer
			document.forms[1].file.value="";//added for hyundai buffer
		}
		
	}//end of if(selVal == 'BAP')
	
	else
		{
			document.getElementById("Approved").style.display="none";
			document.getElementById("Approved1").style.display="none";
			document.getElementById("fileId").style.display="none";//added for hyundai buffer
			
			document.forms[1].approvedAmt.value="";
			document.forms[1].approvedBy.value="";
			document.forms[1].file.value="";//added for hyundai buffer
		}//end of else
		
	
	
	
	if(selVal == 'BRJ')
	{
		document.getElementById("Rejected").style.display="";
		
	}//end of if(selVal == 'BRJ')
	else
	{
		document.getElementById("Rejected").style.display="none";
		
		document.forms[1].rejectedBy.value="";
	}//end of else
}// end of function showhideApproved()

function onSave()
{
	if(document.forms[1].statusTypeID.value=='BSR' && document.forms[1].requestedDate.value=='')
	{
		alert('Please send the request');
		return false;
	}//end of if(document.forms[1].statusTypeID.value=='BSR' && document.forms[1].requestedDate.value=='')
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
	{
    	document.forms[1].mode.value ="doSave";
    	document.forms[1].action = "/UpdateBufferDetailsAction.do";
    	JS_SecondSubmit=true;
    	document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSave()

function onSendRequest()
{
	trimForm(document.forms[1]);
    document.forms[1].mode.value ="doSendRequest";
    document.forms[1].action = "/UpdateBufferDetailsAction.do";
    document.forms[1].submit();
}//end of onSendRequest()

function onReset()
{
	var ClaimType= document.forms[1].claimType.value;
	  if(ClaimType=="NRML")
		{
		var BufferType= document.forms[1].bufferType.value;
		}
	  if(ClaimType=="CRTL")
		{
		var BufferType= document.forms[1].bufferType1.value;
		}
	  
	
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].patclmClaimType.value=ClaimType;
		document.forms[1].patclmBufferType.value=BufferType;
    	document.forms[1].action="/BufferDetailsAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
		showhideApproved();
	}//end of else
}//end of onReset

function onClose()
{
    document.forms[1].mode.value ="doClose";
    document.forms[1].action = "/BufferDetailsAction.do";
    document.forms[1].submit();
}//end of onClose()


function showhideBufferType()
{
	
	
	var ClaimType= trim(document.forms[1].claimType.value);
	if(ClaimType ==""){
		alert("Please select Claim Type");
		return false;
	}
	if(ClaimType=="NRML")
		{
		document.getElementById("normalID").style.display="";
		document.getElementById("criticalID").style.display="none";
		document.getElementById("criticalID").value="";
		document.forms[1].bufferType1.value="";
		
		}
	else if(ClaimType=="CRTL"){
		document.getElementById("criticalID").style.display="";
		document.getElementById("normalID").style.display="none";
	    document.getElementById("normalID").value="";
	    document.forms[1].bufferType.value="";
	}
	else
	{
		document.getElementById("normalID").style.display="none";
		document.getElementById("criticalID").style.display="none";

		document.getElementById("normalID").VALUE="";
		document.getElementById("criticalID").VALUE="";
		document.forms[1].bufferType.value="";
		document.forms[1].bufferType1.value="";
	}//added for maternity
}	

function ChangeType(obj)

{
	var ClaimType= document.forms[1].claimType.value;	
	var BufferType= obj.value;
	if(ClaimType ==""){
		alert("Please select Claim Type");
		return false;
	}
	
	if(BufferType ==""){
		alert("Please select Buffer Type");
		return false;
	}
	     document.forms[1].mode.value="doAdd";
	  //  document.forms[1].child.value="Buffer Details";
	    document.forms[1].patclmClaimType.value=ClaimType;
	    document.forms[1].patclmBufferType.value=BufferType;
	    document.forms[1].action = "/BufferDetailsAction.do";
	    document.forms[1].submit();
}



function onuUploadPdfFiles()
{
	var file=document.getElementById("file").value;
	var fileExt=(file.substring(file.lastIndexOf(".")+1,file.length)).toLowerCase();
	var strRemarks=document.getElementById("bufferRemarks").value;
	
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


/*function onDocumentViewerHyundaiBuffer(documentviewer)
{
	  var w = screen.availWidth - 10;
	  var h = screen.availHeight - 49;
	  var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
	if (window.ActiveXObject)  // IE
	    {

	window.open(documentviewer,'',features,target="_blank");
	    }
	else
	{
	 alert("Please login with Internet Explorer6.0 and above for DMS");
	// return false;
	}
}	*/


function onDocumentViewerHyundaiBuffer(fileName)
{
	 document.forms[1].mode.value="doViewFile";
    document.forms[1].fileExistYN.value=fileName;
    //document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/BufferDetailsAction.do";
    document.forms[1].submit();
}//end of edit(rownum) 
