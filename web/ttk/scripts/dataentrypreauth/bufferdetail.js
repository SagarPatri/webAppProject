//javascript used in bufferdetail.jsp of Preauth flow

function showhideApproved()
{
	var selObj=document.forms[1].statusTypeID;
	var selVal = selObj.options[selObj.selectedIndex].value;
	if(selVal == 'BAP')
	{
		document.getElementById("Approved").style.display="";
		document.getElementById("Approved1").style.display="";
	}//end of if(selVal == 'BAP')
	else
	{
		document.getElementById("Approved").style.display="none";
		document.getElementById("Approved1").style.display="none";
		document.forms[1].approvedAmt.value="";
		document.forms[1].approvedBy.value="";
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
    	JS_SecondSubmit=true
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
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
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