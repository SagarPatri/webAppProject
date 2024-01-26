// JAva Script Function for accounthead.jsp
function onClose()
{
	document.forms[1].mode.value="doView";
    document.forms[1].action = "/PreAuthTariffAction.do";
    document.forms[1].submit();
}

function onReset()
{
    document.forms[1].reset();    
}//end of onReset()

function onSubmit()
{
	var selectedWardIDs="|";
	var flag=true;
	for(i=0;i<document.forms[1].chbox.length;i++)
	{
		if(document.forms[1].chbox[i].checked)
		{
			flag=false;
			selectedWardIDs+=document.forms[1].chbox[i].value+"|";			
		}//end of if(document.forms[1].chbox[i].checked)
	}//end of for(i=0;i<document.forms[1].chbox.length;i++)
	
	if(flag)
	{
		alert('Please select atleast one Account Head');
		return false;
	}
	if(!JS_SecondSubmit)
	{
		document.forms[1].selectedWardIds.value=selectedWardIDs;
		document.forms[1].mode.value="doSaveAccountHead";
    	document.forms[1].action = "/PreAuthTariffAction.do";
    	JS_SecondSubmit=true
    	document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)	
}