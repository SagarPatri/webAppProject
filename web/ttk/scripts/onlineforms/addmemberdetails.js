//contains the javascript functions of addmemberdetails.jsp
function onSave()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/AddOnlineMemberAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
}//end of onSave()
function onCancel()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
    document.forms[1].action="/EditOnlineMemberAction.do";
	document.forms[1].submit();
}//end of onCancel
function onReset(strRootIndex)
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
	    document.forms[1].action="/EditOnlineMemberAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset

function onDelete()
{
	var msg = confirm("Are you sure you want to delete the selected record ?");
	if(msg)
	{
		document.forms[1].mode.value="doDelete";
		document.forms[1].action="/AddOnlineMemberAction.do";
		document.forms[1].submit();
	}
}// end of onDelete()
function onClarificationTypeIDChange()
{
	document.forms[1].exitDate.value="";
	if(document.forms[1].status.value=='POC')	//if policy status is made active
	{
		document.forms[1].exitDate.value=document.forms[1].effectiveDate.value;
	}
	else	//if policy status Active,with suspension or with extension
	{
		document.forms[1].exitDate.value=document.forms[1].policyEndDate.value;
	}
}//end of onClarificationTypeIDChange()


function onRelationshipChange()
{
	var relationID=document.forms[1].relationTypeID.value;
	var genderID=relationID.substring(relationID.indexOf("#")+1,relationID.length);
	relationID=relationID.substring(0,relationID.indexOf("#"));

	if(relationID=="NSF")
		document.forms[1].name.value=document.forms[1].hiddenName.value;
	else
		document.forms[1].name.value="";

	document.forms[1].relationID.value=genderID;

	document.forms[1].mode.value="doChangeRelationship";
	document.forms[1].action="/EditOnlineMemberAction.do";
	document.forms[1].submit();
}//end of onClarificationTypeIDChange()

function onChangeCity()
{
    	document.forms[1].mode.value="doChangeCity";
    	document.forms[1].action="/AddOnlineMemberAction.do";
    	document.forms[1].submit();
}//end of onChangeCity()