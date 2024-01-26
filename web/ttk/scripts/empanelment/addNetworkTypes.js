
function onAdd()
{
	document.forms[1].mode.value="doSaveNetworkType";
   	document.forms[1].action="/HospitalNetworkSaveAction.do";
   	document.forms[1].submit();
}//end of onDiscrepancySubmit()

function onEdit()
{
	document.forms[1].mode.value="doEditNetworkType";
   	document.forms[1].action="/HospitalNetworkEditAction.do";
   	document.forms[1].submit();

}//end of onReset()
function onUserSubmit()
{
	var networkOrder	=	document.getElementsByName("networkOrder");
	for(var k=0;k<networkOrder.length;k++)
	{
		if(networkOrder[k].value==""){
			alert("Please Enter Network Order");
			return false;
		}
	}
	document.forms[1].mode.value="doModifyNetworkType";
   	document.forms[1].action="/HospitalNetworkEditAction.do";
   	document.forms[1].submit();

}//end of onReset()



