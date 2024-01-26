//java script for the policy list screen in inward entry module

function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/BatchPolicyAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/BatchPolicyAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/BatchPolicyAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/BatchPolicyAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to call batch complete
function onBatchComplete(batchType)
{
	if(batchType=='INW')
	{
		var msg = confirm("Are you sure you want to complete the Inward entry ?");
	}//end of if(batchType=='INW')
	else if(batchType=='BAT')
	{
		var msg = confirm("Are you sure you want to complete the Batch ?");
	}//end of else if(batchType=='BAT')
	if(msg)
	{
    	document.forms[1].mode.value="doBatchCompletePolicy";
    	document.forms[1].batchType.value=batchType;
    	document.forms[1].action = "/BatchCompleteAction.do";
    	document.forms[1].submit();
    }// end of if(msg)
}//end of onBatchComplete()

//function to delete
function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected record ?");
		if(msg)
		{
			document.forms[1].mode.value = "doDeleteList";
			document.forms[1].action = "/BatchPolicyAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function onAddPolicy()
{
    document.forms[1].mode.value="doAdd";
    document.forms[1].child.value="PolicyDetail";
    document.forms[1].action = "/PolicyDetailAction.do";
    document.forms[1].submit();
}//end of onAddPolicy()

//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doViewBatchPolicy";
    document.forms[1].child.value="PolicyDetail";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/PolicyDetailAction.do";
    document.forms[1].submit();
}//end of edit(rownum)



function uploadPolicies(){    
	document.forms[1].mode.value ="uploadPolicies";
    document.forms[1].action = "/BatchPolicyAction.do";
    
    var elem = document.createElement('div');
    elem.style.cssText = 'position:absolute;width:1000px;height:1000px;opacity:0.3;z-index:100;background:green;';
    document.body.appendChild(elem);
    
    document.forms[1].submit();
}//end of uploadPolicies()

function downloadBulkPolicyLog(){                               
	document.forms[1].mode.value ="downloadBulkPolicyLog";
    document.forms[1].action = "/BatchPolicyAction.do";
    document.forms[1].submit();
}//end of downloadBulkPolicyLog()
function downloadSampleFormat(){                               
	document.forms[1].mode.value ="downloadSampleFormat";
    document.forms[1].action = "/BatchPolicyAction.do";
    document.forms[1].submit();
}//end of downloadSampleFormat()

