//java script for the discrepancy screen in the preauth flow.

function Reset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/DataEntryDiscrepancyAction.do";
		document.forms[1].submit();
 	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
 	else
 	{
 		document.forms[1].reset();
 	}//end of else
}//end of Reset()

function onResolve1()
{
	toCheck();
	document.forms[1].mode.value="doResolveDiscrepancy";
	document.forms[1].action="/DataEntryDiscrepancyAction.do";
	document.forms[1].submit();
}//end of onResolve()

// function to close the discrepancy screen.
function Close()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doView";
	document.forms[1].child.value="";
	document.forms[1].action="/DataEntryPreAuthGeneralAction.do";
	document.forms[1].submit();
}//end of Close()

function onResolve()
{
	if(!getCheckBox())
	{
		alert("Please check all the checkboxes to resolve discrepancy");
		return false;
	}//end of if(!getCheckBox())
	document.forms[1].mode.value="doResolveDiscrepancy";
	document.forms[1].action="/DataEntryDiscrepancyAction.do";
	document.forms[1].submit();
}//end of onResolve()

function getCheckBox()
{
	var NumElements=document.forms[1].elements.length;
	var chkStatus=false;
	var nCheckBox=0;
	var allChecked=false;
	for(n=0; n<NumElements;n++)
	{
		if(document.forms[1].elements[n].type=="checkbox")
		{
			nCheckBox++;
			var tt =document.forms[1].elements[n].name;
			if(document.forms[1].elements[n].checked==true)
			{
				allChecked=true;
			}//end of if(document.forms[1].elements[n].checked==true)
			else
			{
				allChecked=false;
				break;
			}//end of else
		}//end of if(document.forms[1].elements[n].type=="checkbox")
	}//end of for
	//There are no checkboxes to be checked, so simply return true;
	if(nCheckBox <=0) allChecked=true;
	return allChecked;
}//end of getCheckBox