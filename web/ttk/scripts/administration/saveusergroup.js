//java script for the User Group screen in the administration of Products flow.

// function to save the selected users groups
function onSave()
{
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/SaveProductUserGroupAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
} //end of onSave()

//function to reset
function onReset()
{
document.forms[1].reset();
}// end of onReset()

// function to clear all fields
function onClearAll()
{
for( var i =0;i<document.forms[1].chbox.length;i++)
 if(document.forms[1].chbox[i].checked==true)
	document.forms[1].chbox[i].checked=false;
}//end of onClearAll()


