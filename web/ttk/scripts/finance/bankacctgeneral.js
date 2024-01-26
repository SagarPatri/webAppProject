//java script called by bankacctgeneral.jsp in finance flow
function onUserSubmit()
{
     if(!JS_SecondSubmit)
     {
     	trimForm(document.forms[1]);
     	document.forms[1].mode.value="doSave";
     	document.forms[1].action="/SaveBankAcctGeneralAction.do";
     	JS_SecondSubmit=true
     	document.forms[1].submit();
     }//end of if(!JS_SecondSubmit)	
}//end of onUserSubmit()

function showhidestatus()
{
	var selObj = document.forms[1].statusTypeID;
	var selVal = selObj.options[selObj.selectedIndex].value;
	if(selVal=="ASC")
	{
		document.getElementById("clsDate").style.display="";
		document.getElementById("closedDate").style.display="";
	}
	else
	{
		document.getElementById("clsDate").style.display="none";
		document.getElementById("closedDate").style.display="none";
		document.forms[1].closedDate.value="";
	}
}//end of showhidestatusdate(selObj)
function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
    {
        document.forms[1].mode.value="doReset";
        document.forms[1].action="/BankAcctGeneralAction.do";
        document.forms[1].submit();
    }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
    else
    {
        document.forms[1].reset();
    	if(document.forms[1].statusTypeID.disabled != true )
    	{
    		showhidestatus();
    	}
    }//end of else if(typeof(ClientReset)!= 'undefined' && !ClientReset)
}//end of onReset()

function selectBank()
{
     document.forms[1].mode.value="doViewBank";
     document.forms[1].child.value="BankList";
     document.forms[1].action="/BankAcctGeneralAction.do";
     document.forms[1].submit();
}//end of onUserSubmit()
