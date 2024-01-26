//contains the javascript functions of member.jsp
function onNodeCompareIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="Policy";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/OtherPolicyAction.do";
	document.forms[1].submit();
}

function onRootDependentsIcon(strRootIndex)
{
	document.forms[1].mode.value="doSearch";
	document.forms[1].child.value="Renew Members";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/RenewMemberAction.do";
	document.forms[1].submit();
}//end of onRootDependentsIcon(strRootIndex)

function onRootDomiciliaryIcon(strRootIndex)
{
	document.forms[1].mode.value="doViewDomiciliary";
	document.forms[1].child.value="Domiciliary Treatment";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/DomiciliaryAction.do";
	document.forms[1].submit();
}//end of onRootDependentsIcon(strRootIndex)

function onRootPolicyIcon(strRootIndex)
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].action="/EnrollmentRuleDataAction.do";
	document.forms[1].submit();
}//end of onRootPolicyIcon(strRootIndex)

function onRootPhotoIcon(strRootIndex)
{
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/CorporateMemberAction.do";
	document.forms[1].submit();
}//end of onRootPhotoIcon(strRootIndex)

function onRootCardIcon(strRootIndex)
{
	document.forms[1].mode.value="doApproveCard";
	document.forms[1].selectedroot.value=strRootIndex;
	var policy_status =	 document.getElementById("policystatus").value;
	if(policy_status == "FTS")
		document.forms[1].action="/CorporateMemberAction.do";
	else if(policy_status == "RTS")
		document.forms[1].action="/CorporateRenewMemberAction.do"; 

   
	document.forms[1].submit();
}//end of onRootCardIcon(strRootIndex)

function onRootRatesIcon(strRootIndex)
{
	document.forms[1].mode.value="doViewPremiumInfo";
	document.forms[1].child.value="Premium Information";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/PremiumInfoAction.do";
	document.forms[1].submit();
}//end of onRootRatesIcon(strRootIndex)

function onRootAddIcon(strRootIndex)
{
	document.forms[1].mode.value="doAddMember";
	document.forms[1].child.value="AddEditMember";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].action="/CorporateMemberAction.do";
	document.forms[1].submit();
}//end of onRootAddIcon(strRootIndex)

function onRootDeleteIcon(strRootIndex)
{
	var msg = confirm("Are you sure you want to delete the selected record ?");
	if(msg)
	{
		var overrRemarks=prompt("Enter Deletion Remarks","");
		
		
		var space = overrRemarks.charCodeAt(0); 
		 if(space==32)
          {
                 alert("Deletion Remarks should not start with space.");
                 document.getElementById("deletionRemarks").value="";
                 document.getElementById("deletionRemarks").focus();
                 return;
          } 
		
		
		if(overrRemarks==null||overrRemarks===""){
	
		 alert("Deletion Remarks Are Required");
		 return;
	 }else if(overrRemarks.length<20){
		 alert("Deletion Remarks Should Not Less Than 20 Characters");
		 return;
	 }
		 document.forms[1].deletionRemarks.value=overrRemarks;
		 
	} 
	
	document.forms[1].mode.value="doDelete";
	document.forms[1].selectedroot.value=strRootIndex;
	var policy_status =	 document.getElementById("policystatus").value;
	if(policy_status == "FTS")
		document.forms[1].action="/CorporateMemberAction.do";
	else if(policy_status == "RTS")
		document.forms[1].action="/CorporateRenewMemberAction.do"; 

	document.forms[1].submit();
		
}//end of onRootDeleteIcon(strRootIndex)

function onRootCancelledIcon(strRootIndex)
{
	
	
	
	var switchtype = document.forms[1].switType.value;
	
	if(switchtype=="END"){
		var xhttp = null;

		if (window.XMLHttpRequest) {
		    // code for modern browsers
			xhttp = new XMLHttpRequest();
		 } else {
		    // code for old IE browsers
			 xhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		    var path="/CorporateMemberAction.do?mode=getPreAuthCount&selectedroot="+strRootIndex;

		  xhttp.open("POST", path, false);
		  xhttp.send();
		  var sData=xhttp.responseText;
		  
		  if(isNaN(sData)){
			  
			 alert(sData);
			 return;
			 
		  }else {
			  
			  if(parseInt(sData)>0){
				  var status=confirm("Preauths and/or claims exist for the policy, still do you  Want to Cancel the member ?");
				  if(status)
					{
						var overrRemarks=prompt("Enter Cancellation Remarks","");
						
						var space = overrRemarks.charCodeAt(0); 
						 if(space==32)
		                  {
		                         alert("Cancellation Remarks should not start with space.");
		                         document.getElementById("cancellationRemarks").value="";
		                         document.getElementById("cancellationRemarks").focus();
		                         return;
		                  } 
						
						
						
						if(overrRemarks==null||overrRemarks===""){
					
						 alert("Cancellation Remarks Are Required");
						 return;
					 }else if(overrRemarks.length<20){
						 alert("Cancellation Remarks Should Not Less Than 20 Characters");
						 return;
					 }
						 document.forms[1].cancellationRemarks.value=overrRemarks;
					
					}//end of if(msg)
				  
				  
				  
				  
				  if(!status){
				  return;
				  }
			  }
		  }
	}
	
	//else{
		
		var msg = confirm("Are you sure you want to cancel the selected record ?");
		if(msg)
		{
			document.forms[1].mode.value="doCancel";
			document.forms[1].selectedroot.value=strRootIndex;
		    document.forms[1].action="/CorporateMemberAction.do";
			document.forms[1].submit();
		}//end of if(msg)
		
	//}
	
}//end of onRootCancelledIcon(strRootIndex)



function onNodeCancelledIcon(strRootIndex,strNodeIndex)
{

	var switchtype = document.forms[1].switType.value;
	if(switchtype=="END"){
		var xhttp = null;

		if (window.XMLHttpRequest) {
		    // code for modern browsers
			xhttp = new XMLHttpRequest();
		 } else {
		    // code for old IE browsers
			 xhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		    var path="/CorporateMemberAction.do?mode=getPreAuthCount&selectedroot="+strRootIndex+"&selectednode="+strNodeIndex;

		  xhttp.open("POST", path, false);
		  xhttp.send();
		  var sData=xhttp.responseText;
		  
		  if(isNaN(sData)){
			  
			 alert(sData);
			 return;
			 
		  }else {
			  
			  if(parseInt(sData)>0){
				  var status=confirm("Preauths and/or claims exist for the policy, still do you  Want to Cancel the member ?");
				  if(status)
					{
						var overrRemarks=prompt("Enter Cancellation Remarks","");
						
						
						var space = overrRemarks.charCodeAt(0); 
						 if(space==32)
		                  {
		                         alert("Cancellation Remarks should not start with space.");
		                         document.getElementById("cancellationRemarks").value="";
		                         document.getElementById("cancellationRemarks").focus();
		                         return;
		                  } 
						
						
						if(overrRemarks==null||overrRemarks===""){
					
						 alert("Cancellation Remarks Are Required");
						 return;
					 }else if(overrRemarks.length<20){
						 alert("Cancellation Remarks Should Not Less Than 20 Characters");
						 return;
					 }
						 document.forms[1].cancellationRemarks.value=overrRemarks;
					
					}//end of if(msg)
				  
				  if(!status){
				  return;
				  }
			  }
		  }
	}	
	
	
	var msg = confirm("Are you sure you want to cancel the selected record ?");
	if(msg)
	{
		document.forms[1].mode.value="doCancel";
		document.forms[1].selectedroot.value=strRootIndex;
		document.forms[1].selectednode.value=strNodeIndex;
	    document.forms[1].action="/CorporateMemberAction.do";
		document.forms[1].submit();
	}//end of if(msg)
}//end of onNodeCancelledIcon(strRootIndex,strNodeIndex)

function onNodePolicyIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].child.value="Member Rules";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/MemberRuleDataAction.do";
	document.forms[1].submit();
}//end of onNodePolicyIcon(strRootIndex,strNodeIndex)

function onNodeBenefitsIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="viewBenefitDetails";
	//document.forms[1].child.value="Add PED";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/PolicyBenefitsAction.do";
	document.forms[1].submit();
}//end of onNodePedIcon(strRootIndex,strNodeIndex)

function onNodePedIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="doSearchPED";
	document.forms[1].child.value="Add PED";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
    document.forms[1].action="/AddPEDAction.do";
	document.forms[1].submit();
}//end of onNodePedIcon(strRootIndex,strNodeIndex)

function onNodeCardIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="doApproveCard";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	var policy_status =	 document.getElementById("policystatus").value;
	if(policy_status == "FTS")
		document.forms[1].action="/CorporateMemberAction.do";
	else if(policy_status == "RTS")
		document.forms[1].action="/CorporateRenewMemberAction.do"; 

	document.forms[1].submit();
}//end of onNodeCardIcon(strRootIndex,strNodeIndex)

function onNodeEditIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="doViewMember";
	document.forms[1].child.value="AddEditMember";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	var policy_status =	 document.getElementById("policystatus").value;
	if(policy_status == "FTS")
		document.forms[1].action="/CorporateMemberAction.do";
	else if(policy_status == "RTS")
		document.forms[1].action="/CorporateRenewMemberAction.do"; 
	document.forms[1].submit();
}//end of onNodeEditIcon(strRootIndex,strNodeIndex)

function onNodeDeleteIcon(strRootIndex,strNodeIndex)
{
	var msg = confirm("Are you sure you want to delete the selected record ?");
	if(msg)
	{
		var overrRemarks=prompt("Enter Deletion Remarks","");
		
		
		var space = overrRemarks.charCodeAt(0); 
		 if(space==32)
          {
                 alert("Deletion Remarks should not start with space.");
                 document.getElementById("deletionRemarks").value="";
                 document.getElementById("deletionRemarks").focus();
                 return;
          } 
		
		
		if(overrRemarks==null||overrRemarks===""){
	
		 alert("Deletion Remarks Are Required");
		 return;
	 }else if(overrRemarks.length<20){
		 alert("Deletion Remarks Should Not Less Than 20 Characters");
		 return;
	 }
		 document.forms[1].deletionRemarks.value=overrRemarks;
		 
	} 
	
		document.forms[1].mode.value="doDelete";
		document.forms[1].selectedroot.value=strRootIndex;
		document.forms[1].selectednode.value=strNodeIndex;
		var policy_status =	 document.getElementById("policystatus").value;
		if(policy_status == "FTS")
			document.forms[1].action="/CorporateMemberAction.do";
		else if(policy_status == "RTS")
			document.forms[1].action="/CorporateRenewMemberAction.do";  
		document.forms[1].submit();
	
}//end of onNodeDeleteIcon(strRootIndex,strNodeIndex)
function onWebboardSelect()
{
	document.forms[1].mode.value="webboardselect";
	document.forms[1].action="/CorporateMemberAction.do";
	document.forms[1].submit();
}//end of onWebboardSelect()
//function Search
function onSearch()
{
	if(!JS_SecondSubmit)
	 {
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doSearch";
		document.forms[1].selectedroot.value="";
	    document.forms[1].action="/CorporateMemberAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function approveCard()
{
	document.forms[1].mode.value="doApproveCard";
	document.forms[1].selectedroot.value="";
	var policy_status =	 document.getElementById("policystatus").value;
	if(policy_status == "FTS")
		document.forms[1].action="/CorporateMemberAction.do";
	else if(policy_status == "RTS")
		document.forms[1].action="/CorporateRenewMemberAction.do"; 
	
	document.forms[1].submit();
}//end of approveCard()

function addNewEnrollment()
{
	document.forms[1].mode.value="doAdd";
	document.forms[1].child.value="Employee Details";
	document.forms[1].selectedroot.value="";
    document.forms[1].action="/EnrollmentAction.do";
	document.forms[1].submit();
}//end of addNewEnrollment()

function editRoot(strRootIndex)
{
	document.forms[1].mode.value="doViewEnrollment";
	document.forms[1].child.value="Employee Details";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/EnrollmentAction.do";
	document.forms[1].submit();
}//end of editRoot(strRootIndex)

function OnShowhideClick(strRootIndex)
{
	document.forms[1].mode.value="doShowHide";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/CorporateMemberAction.do";
	document.forms[1].submit();
}//end of addNewEnrollment()

function onCancel(strRootIndex)
{
	document.forms[1].mode.value="Cancel";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/CorporateMemberAction.do";
	document.forms[1].submit();
}//end of onCancel

//function on click of pageIndex
function pageIndex(strPageIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].selectedroot.value="";
	document.forms[1].pageId.value=strPageIndex;
    document.forms[1].action="/CorporateMemberAction.do";
	document.forms[1].submit();
}//end of pageIndex()

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/CorporateMemberAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/CorporateMemberAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onRootUncheckedIcon(strRootIndex)
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doValidateRule";
    document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action = "/CorporateMemberAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onRootPassedIcon(strRootIndex)
{
	
}//end of onRootPassedIcon(strRootIndex)


function onRootFailedIcon(strRootIndex)
{
	
	document.forms[1].reset();
    document.forms[1].mode.value ="doValidateRule";
    document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action = "/MemberAction.do";
    document.forms[1].submit();
}//end of onRootFailedIcon(strRootIndex)

function onShowError()
{
	document.forms[1].mode.value ="doDefault";
    document.forms[1].action = "/ShowValidationError.do";
    document.forms[1].submit();
}//end of onShowError()

function onClearRules()
{
	document.forms[1].mode.value ="doClearRules";
    document.forms[1].action = "/MemberAction.do";
    document.forms[1].submit();
}//end of onClearRules()

function onRootChangePassword(strRootIndex)
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/ChangePasswordAction.do";
	document.forms[1].submit();
}//end of onRootHistoryIcon(strRootIndex)

//added for koc 1278 and 1270
function onRootChangePlanIcon(strRootIndex)

{
	document.forms[1].mode.value="doViewChangePlan";
	document.forms[1].child.value="Change Plan";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/ChangePlanAction.do";
	document.forms[1].submit();
}//end of onRootDependentsIcon(strRootIndex)
//added for koc 1278 and 1270

function onUploadProfessionals()
{
	var userSeqId	=	document.forms[1].userSeqId.value;
	var EnrollmentUploadURL	=	document.forms[1].EnrollmentUploadURL.value;
	window.open(EnrollmentUploadURL+"?hospSid="+hospSeqId+"&uploadedBy="+userSeqId+"&empanelmentId="+hospEmpnlNo,'',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
}






function onRootAuditLogsIcon(strRootIndex)
{
	var reforward = "groupMember";
	document.forms[1].mode.value="onAuditLogsIcon";
	document.forms[1].child.value="AuditLogsIcon";
	document.forms[1].selectedroot.value=strRootIndex;
	  document.forms[1].action = "/CorporateMemberAction.do?reforward="+reforward;
	document.forms[1].submit();
}//end of onRootDependentsIcon(strRootIndex)



function onNodeAuditLogsIcon(strRootIndex,strNodeIndex)
{
	var reforward = "dependentMember";
	document.forms[1].mode.value="onAuditLogsIcon";
	document.forms[1].child.value="AuditLogsIcon";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	 document.forms[1].action = "/CorporateMemberAction.do?reforward="+reforward;
	document.forms[1].submit();
}//end of onNodeAuditLogsIcon(strRootIndex)















function softcopyUpload()
{
	
	var EnrollmentSoftUploadURL	=	document.forms[1].EnrollmentSoftUploadURL.value;
	var userSeqId	=	document.forms[1].userSeqId.value;
	var switchtype = document.forms[1].switType.value;
	var policy_num = document.forms[1].policy_num.value;
	var productTyp_num = document.forms[1].productTyp_num.value;
	var insComp_num = document.forms[1].insComp_num.value;
	var grpId_num = document.forms[1].grpId_num.value;
	var capitation_type = document.forms[1].capitation_type.value;
	var  policy_category= document.forms[1].policy_category.value;
	var EnrollBatchSeqID="";
	if(document.forms[1].EnrollBatchSeqID != null){
		EnrollBatchSeqID = document.forms[1].EnrollBatchSeqID.value;
	}
	
 var param = { 'loginType' : 'Enrollment', 'userId': userSeqId ,'password' :'m','policy_num': policy_num,'productTyp_num':productTyp_num,'insComp_num':insComp_num,'grpId_num':grpId_num,'capitation_type':capitation_type,'policy_category':policy_category,'EnrollBatchSeqID' :EnrollBatchSeqID,'switchtype' :switchtype};
 OpenWindowWithPost(EnrollmentSoftUploadURL, "width=1000, height=600, left=100, top=100, resizable=yes, scrollbars=yes", "NewFile", param);
}
 

function OpenWindowWithPost(url, windowoption, name, params)
{
 var form = document.createElement("form");
 form.setAttribute("method", "post");
 form.setAttribute("action", url);
 form.setAttribute("target", name);
 for (var i in params)
 {
   if (params.hasOwnProperty(i))
   {
     var input = document.createElement('input');
     input.type = 'hidden';
     input.name = i;
     input.value = params[i];
     form.appendChild(input);
   }
 }
 document.body.appendChild(form);
// window.open("", name, windowoption);
 form.submit();
 document.body.removeChild(form);
}














