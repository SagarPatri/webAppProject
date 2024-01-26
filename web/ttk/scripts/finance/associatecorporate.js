//java script for the associate corporate group screen in the finance flow.

function changeCorporate()
{
	document.forms[1].mode.value="doCorporateChange";
	document.forms[1].child.value="GroupList";
	document.forms[1].action="/FloatAccAssocAction.do";
	document.forms[1].submit();
}//end of changeCorporate()

function clearCorporate()
{
	if(document.forms[1].groupName.value!="")
	{
		document.forms[1].mode.value="doClearCorp";
		document.forms[1].action="/FloatAccAssocAction.do";
		document.forms[1].submit();	
	}
	//document.forms[1].groupName.value="";
	//document.forms[1].groupID.value="";
	//document.forms[1].groupRegnSeqID.value="";	
	//TC_PageDataChanged=false;
	//document.getElementById("clearassociate").style.display="";
	//document.getElementById("newassociate").style.display="none";
}//end of clearCorporate

function onAssociateCorp()
{
	if(document.forms[1].groupRegnSeqID.value=="")
	{		
		alert("Select the Corporate Name.");
	}
	else
	{
		document.forms[1].mode.value="doAssociateCorp";
		document.forms[1].action="/FloatAccAssocAction.do";
		document.forms[1].submit();
	}
}//end of onAssociateCorp

function onClose()
{
	if(!TrackChanges())
		return false;
	document.forms[1].mode.value="doViewFloatAccount";
    document.forms[1].rownum.value=document.forms[1].floatAcctSeqID.value;
    document.forms[1].action = "/FloatAccAssocAction.do";
    document.forms[1].submit();
}//end of onClose

function onDeleteIcon(rownum)
{
	var msg = confirm("Are you sure you want to delete the record?");
	if(msg)
	{
		document.forms[1].mode.value="doDeleteAssociatedCorp";
		document.forms[1].rownum.value=rownum;	
		document.forms[1].action="/FloatAccAssocAction.do";
		document.forms[1].submit();
	}
}//end of onAddIcon()