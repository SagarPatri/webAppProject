//approveaaitngsearch.js added as per Bajaj Change Rwequest

//as per Bajaj

/*function onApprove()
{f
	if(!TrackChanges()) return false;
    document.forms[1].mode.value="doApprove";
	document.forms[1].child.value="";
    document.forms[1].action="/ClaimAwaitingApproveSearch.do";
    document.forms[1].submit();
}//end of onClose()
*/

function onSave()
{
	var claimApprovlaStatus=document.forms[1].insApprovalStatus.value;
	var insurerRemarks=document.forms[1].insurerRemarks.value;
	if(claimApprovlaStatus=="")
	{
		alert("Please select Insurer's Decision");
		return false;
	}
	
	if(insurerRemarks=="")
	{
		alert("Please enter Insurer remarks");
		return false;
	}
	if(claimApprovlaStatus!="")
	{
	var childVlaue="";
	if(claimApprovlaStatus=="APR")
		childVlaue="Approve";
	else if(claimApprovlaStatus=="REJ")
		childVlaue="Reject";
	else if(claimApprovlaStatus=="REQ")
	childVlaue="RequiredInformation";
	}
	else{
		alert("Please select Approval Status");
		return false;
	}
	  document.forms[1].mode.value="doSave";
	document.forms[1].child.value=childVlaue;
    document.forms[1].action="/UpdateClaimAwaitingApproveSearch.do";
    document.forms[1].submit();
}//end of onClose()


function onReject()
{
	if(!TrackChanges()) return false;
    document.forms[1].mode.value="doReject";
	//document.forms[1].child.value="";
    document.forms[1].action="/ClaimAwaitingApproveSearch.do";
    document.forms[1].submit();
}//end of onReject()


function onGenerateReport()
{
  if(TC_GetChangedElements().length>0)
    {
      alert("Please save the modified data, before Generating Letter");
      return false;
    }//end of if(TC_GetChangedElements().length>0)
    var strTab=document.forms[0].tab.value;
  
  var parameterValue=document.forms[1].seqID.value;
  var enrollNumber=document.forms[1].enrollNumber.value;
  var policyNo=document.forms[1].policyNo.value;
  var vidalhealthttkid=document.forms[1].vidalttkId.value;
  
  if(strTab=="Claims")
  {
	  parameter = "?mode=doGenerateAppRejClmReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ClaimsApprovalForm.jrxml&reportID=ClaimApproveRejectForm&enrollNumber="+enrollNumber+"&policyNo="+policyNo+"&vidalttkId="+vidalhealthttkid;
   }
  else if(strTab=="Pre-Auth")
  {
  parameter = "?mode=doGenerateAppRejPatReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/PreAuthApprovalForm.jrxml&reportID=PatApproveRejectForm&enrollNumber="+enrollNumber+"&policyNo="+policyNo+"&vidalttkId="+vidalhealthttkid;
  }
var openPage = "/ReportsAction.do"+parameter;
  var w = screen.availWidth - 10;
  var h = screen.availHeight - 49;
  var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
  window.open(openPage,'',features);
}//end of onReport()



function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value = "doDefault";
	document.forms[1].tab.value ="Search";
	document.forms[1].action = "/ClaimAwaitingApproveSearch.do";
	document.forms[1].submit();	
}//end of onClose() 


function onDocumentViewer(documentviewer)
{
       
       //var URL="ttkpro/getquery.html?"+documentviewer;
       //window.open(documentviewer);
       //added for KOC-1257
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
}	
