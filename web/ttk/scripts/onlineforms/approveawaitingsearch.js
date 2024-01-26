//approveaaitngsearch.js added as per Bajaj Change Rwequest



function onSearch()
{
	if(document.forms[1].sOperator.value!="")
	{
		if(document.forms[1].sClaimRecommendedAmount.value=="")
		{
			alert("Please enter Claim Recommended Amount.");
			return false;
		}
	}
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doSearch";
	//document.forms[1].child.value="";
	document.forms[1].action="/ClaimAwaitingApproveSearch.do";
	document.forms[1].submit();
}//end of onSearch()

function edit(rownum)
{
	var switchType=document.forms[1].switchType.value;
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doView";
	//document.forms[1].child.value="";
	if(switchType=="CLM")
	{
	 document.forms[1].tab.value ="Claims";
	 document.forms[1].action="/UpdateClaimAwaitingApproveSearch.do";
	}
	 
	else if(switchType=="PRE"){
		 document.forms[1].tab.value ="Pre-Auth";
		 document.forms[1].action="/UpdatePreAutAwaitingApproveSearch.do";
		}
	document.forms[1].rownum.value=rownum;
	document.forms[1].submit();
}//end of onClose()


function onBulkApprove()
{
	if(!mChkboxValidation(document.forms[1]))
	{
		trimForm(document.forms[1]);
		var sRemarks=document.forms[1].sRemarks.value;
		if(document.forms[1].sRemarks.value=="")
		{
			var count = checkBoxCount(document.forms[1]);
			if(count>0)
			{
				document.getElementById("multiRemarks").style.display="";
				document.getElementById("reject").style.display="none";
				document.getElementById("reqinf").style.display="none";
				return false;
			}
			else{
				document.getElementById("multiRemarks").style.display="none";
			}
			
		}//if(document.forms[1].sRemarks.value=="")
		if(!JS_SecondSubmit)
		{	
			document.forms[1].mode.value="doSetBulkStatus";
			 document.forms[1].tab.value ="Search";
			//document.forms[1].setStatus.value="APR";
			 document.forms[1].setStatus.value="TRAD";
			document.forms[1].child.value="Approve";
			document.forms[1].action = "/ClaimAwaitingApproveSearch.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}
	}//if(!mChkboxValidation(document.forms[1]))
}//end of onBulkApprove()
//approve,reject,reqinf

function onBulkReject()
{
	if(!mChkboxValidation(document.forms[1]))
	{
		var sRemarks=document.forms[1].sRemarks.value;
		if(document.forms[1].sRemarks.value=="")
		{
			trimForm(document.forms[1]);

			var count = checkBoxCount(document.forms[1]);
			if(count>0)
			{
				document.getElementById("multiRemarks").style.display="";
				document.getElementById("approve").style.display="none";
				document.getElementById("reqinf").style.display="none";
				return false;
			}
			else{
				document.getElementById("multiRemarks").style.display="none";
			}
		}//if(document.forms[1].sRemarks.value=="")
		if(!JS_SecondSubmit)
		{	
			document.forms[1].mode.value="doSetBulkStatus";
			document.forms[1].tab.value ="Search";
			//document.forms[1].setStatus.value="REJ";
			document.forms[1].setStatus.value="TRND";
			document.forms[1].child.value="Reject";
			document.forms[1].action = "/ClaimAwaitingApproveSearch.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}// 	if(!JS_SecondSubmit)
	}//if(!mChkboxValidation(document.forms[1]))
}//end of onBulkReject()


function onRequiredInformation()
{
	if(!mChkboxValidation(document.forms[1]))
	{
		trimForm(document.forms[1]);
		
		var sRemarks=document.forms[1].sRemarks.value;
		if(document.forms[1].sRemarks.value=="")
		{
		var count = checkBoxCount(document.forms[1]);
		if(count>0)
		{
			document.getElementById("multiRemarks").style.display="";
			document.getElementById("approve").style.display="none";
			document.getElementById("reject").style.display="none";
			return false;
		}
		else{
			document.getElementById("multiRemarks").style.display="none";
		}

	   }//if(document.forms[1].sRemarks.value=="")

		if(!JS_SecondSubmit)
		{	
			document.forms[1].mode.value="doSetBulkStatus";
			 document.forms[1].tab.value ="Search";
			document.forms[1].setStatus.value="REQ";
			document.forms[1].child.value="RequiredInformation";
			document.forms[1].action = "/ClaimAwaitingApproveSearch.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}//if(!JS_SecondSubmit)
	}//if(!mChkboxValidation(document.forms[1]))
}//end of onRequiredInformation()



//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].pageId.value=pagenumber;
	document.forms[1].action = "/ClaimAwaitingApproveSearch.do";
	document.forms[1].submit();
}//end of pageIndex(pagenumber)

function toggle(sortid)
{
	document.forms[1].reset();
	document.forms[1].mode.value="doSearch";
	document.forms[1].sortId.value=sortid;
	document.forms[1].action = "/ClaimAwaitingApproveSearch.do";
	document.forms[1].submit();
}//end of toggle(sortid)



function checkBoxCount(obj)
{

	var count=0;
	trimForm(document.forms[1]);
	for(var i=0;i<document.forms[1].elements.length;i++)
	{
		if(document.forms[1].elements[i].type=="checkbox")
		{
			if(document.forms[1].elements[i].checked)
			{
				count++;
			}
		}
	}
	return count;
}


function onSwitch()
{
	document.forms[1].mode.value="doSwitchTo";
	document.forms[1].action="/ClaimAwaitingApproveSearch.do";
	document.forms[1].submit();
}//end of onSwitch()