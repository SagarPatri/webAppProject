//this function is called onclick of the save button

 function onSave()
 {	
	 var strSwitchType=document.getElementById("switchtype").value;
	 var eventseqId=document.getElementById("lngEventSeqID").value;
	 if(strSwitchType=="ENM" && eventseqId==4)
	 {
		 alert("Enrollment already completed, so document can be upload through endorsement process.");
		 return ;
	 }
	
	var strSwitchType=document.getElementById("switchtype").value;
	var eventseqId=document.getElementById("lngEventSeqID").value;
	if(strSwitchType=="ENM" && eventseqId==4)
	{
		alert("Enrollment already completed, so document can be upload through endorsement process.");
		return ;
	}
	  if(!JS_SecondSubmit)
     {
	 trimForm(document.forms[1]);
	 document.forms[1].mode.value="doUploadPolicyDocs";
	 document.forms[1].child.value="Policy Doc Uploads Save";
	 document.forms[1].action = "/UploadPolicyDocsSave.do";
	 JS_SecondSubmit=true
	 document.forms[1].submit();
   }//end of if(!JS_SecondSubmit)
 }//end of onSave()


//this function is called onclick of the close button
 function onClose()
 {
     document.forms[1].mode.value="doClosePolicyDocs";
     document.forms[1].action="/UploadPolicyDocs.do";
     document.forms[1].submit();
 }//end of onClose()


//this function is called on click of the link in grid to display the Files Upload
  function editViewFile(moudocseqid)
 {
	 var filename=document.getElementById("a"+moudocseqid).value;
	 document.forms[1].action="/ReportsAction.do?moudocseqid="+moudocseqid+"&filename="+filename;
     document.forms[1].mode.value="doPolicyFileDownload";
     document.forms[1].submit();     
 }//end of edit(rownum)
function editTestNag(rownum)
{
	//document.forms[1].fileName.value = strFileName;
	var openPage = "/ReportsAction.do?mode=doViewCommonForAll&module=policyDocs&rownum="+rownum;
   var w = screen.availWidth - 10;
   var h = screen.availHeight - 49;
   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
   window.open(openPage,'',features);
}//end of edit(rownum)

 function onDeleteIconDisabled()
 {
	 var strSwitchType=document.getElementById("switchtype").value;
	 var eventseqId=document.getElementById("lngEventSeqID").value;
	 if(strSwitchType=="ENM" && eventseqId==4)
		 {
		 alert(" Enrollment already completed, so document can be deleted in endorsement process");
		 return false;
		 }
	 else
		 {
		 alert(" Document already deleted!");
		 return false;
		 }
 }
 function onDeleteIcon(moudocseqid)
 {
	  var deleteRemarks=prompt("Please enter the Delete Remarks and click 'OK' to delete ,\n delete this file.(Minimum 10 characters).If required please save the document before deleting.","");
      var space = deleteRemarks.charCodeAt(0);
	  if(space==32)
	  {
			 alert("Deletion remarks should not start with space.");
			 return;
	  }
	 	 if(deleteRemarks==null||deleteRemarks==="")
		 {
	
		  alert("Reason for Delete Remarks Are Required");
		  return;
	     }else if(deleteRemarks.length<10){
		       alert("Reason for Delete Remarks Should Not be Less Than 10 Characters");
		     return;
	     }
	 	  document.forms[1].mode.value="doPolicyFileDelete";
		  document.forms[1].child.value="Policy Doc Uploads Delete";
	 	  document.forms[1].action="/UploadPolicyDocsDelete.do?moudocseqid="+moudocseqid+"&deleteRemarks="+deleteRemarks;
	      document.forms[1].submit();  
      }
function editTestNag(rownum)
{
	//document.forms[1].fileName.value = strFileName;
	var openPage = "/ReportsAction.do?mode=doViewCommonForAll&module=policyDocs&rownum="+rownum;
   var w = screen.availWidth - 10;
   var h = screen.availHeight - 49;
   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
   window.open(openPage,'',features);
}//end of edit(rownum)

function onClosePolicies()
{
	document.forms[1].mode.value = "doClose";
	document.forms[1].action = "/Configuration.do";
	document.forms[1].submit();	
}

function onClosePreAuthProcessing()
{   
	    document.forms[1].mode.value="doGeneral";
	    document.forms[1].reforward.value="close";
	    document.forms[1].action = "/DiagnosisAction.do";
	    document.forms[1].submit();
	
}


function onCloseClaimProcessing()
{   
	document.forms[1].mode.value="doClose";
    document.forms[1].action = "/ClaimDiagnosisSearchAction.do";
    document.forms[1].submit();
}