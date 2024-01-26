//java script for the Cheque printing details screen in the finance flow.

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/ChequeDetailsAction.do";
		document.forms[1].submit();
 	}
 	else
 	{
 		document.forms[1].reset();
 		selObj = document.forms[1].statusTypeID;
 		if(typeof(document.forms[1].chngid) != 'undefined')
			document.forms[1].chngid.value=selObj.options[selObj.selectedIndex].text+" Date :";
 	}
}//end of Reset()

function isValidated()
{
		if(typeof(document.forms[1].clearedDate)!= 'undefined')
		{
			document.forms[1].statusTypeID.disabled=false;
			if(trim(document.forms[1].clearedDate.value).length==0)
			{
				var selectedObj = document.forms[1].statusTypeID;
				var selectedVal = selectedObj.options[selObj.selectedIndex].value;
				if(selectedVal!="")
				{
					if(selectedVal=="CSC")
					{
						alert("Please enter the Cleared Date");
					}
					if(selectedVal=="CSI")
					{
						alert("Please enter the Issued Date");
					}
					if(selectedVal=="CSR")
					{
						alert("Please enter the Re-Issued Date");
					}
					if(selectedVal=="CSV")
					{
						alert("Please enter the Void Date");
					}
					if(selectedVal=="CSS")
					{
						alert("Please enter the Stale Date");
					}
						document.forms[1].clearedDate.focus();
			     		document.forms[1].clearedDate.select();
						return false;
				}
			}
		}
		return true;
}// end of isValidated()
function onSave()
{
	trimForm(document.forms[1]);
	if(isValidated())
	{
		if(!JS_SecondSubmit)
		{
			document.forms[1].mode.value="doSave";
			document.forms[1].action="/SaveChequeDetails.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	}//end of if(isValidated())
}//end of onSave()

// function to close the transaction details screen.
function onCancel()
{
	if(!TrackChanges()) return false;
	if(document.forms[0].sublink.value == "Cheque Information")
	{
		document.forms[1].tab.value="Search";
		document.forms[1].action="/ChequeSearchAction.do";
	}//end of if(document.forms[0].sublink.value == "Cheque Information")
	else
	{
		document.forms[1].action="/ChequeDetailsAction.do";
	}//end of else
	document.forms[1].mode.value="doClose";
	document.forms[1].submit();
}//end of Close()