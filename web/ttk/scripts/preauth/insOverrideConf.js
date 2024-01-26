
function onSave()
{

	
	/*if(document.forms[1].insDec.value=="Y")
	{
		document.forms[1].insDecYN.checked=true;
	}
	else
	{
		document.forms[1].insDecYN.checked=false;
	}*/


document.forms[1].mode.value="doInsOverrideConfPatClm";
document.forms[1].action="/UpdateFileInsOverrideConf.do";
document.forms[1].submit();
	
}

function onClose()
{
	
	var tab=document.forms[1].tab.value;
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doView";
	
	if((tab=="Authorization") || (tab=="Settlement")){
		document.forms[1].action="/AuthorizationDetailsAction.do";
	}	
	else{
	document.forms[1].action="/PreAuthGeneralAction.do";
	}
	document.forms[1].submit();
}//end of Close()


function onOverrideInsurance()
{
	
	if(document.forms[1].insDec.value=="Y")
		{
			document.forms[1].insDecYN.checked=true;
		}
		else
		{
			document.forms[1].insDecYN.checked=false;
		}
	
	
	document.forms[1].mode.value="doInsOverrideConfPatClm";
	document.forms[1].action="/UpdateFileInsOverrideConf.do";
	document.forms[1].submit();
}



