// Java Script function for copaymentcharges.jsp
function onSave()
{
	document.forms[1].mode.value="doSave";
	document.forms[1].action="/CopaymentSaveAction.do";
	document.forms[1].submit();
}//end of onSave()

function onClose()
{
  document.forms[1].mode.value="doClose";  
  document.forms[1].action = "/AdminHospitalsAction.do";
  document.forms[1].submit();
}//end of onClose()

function onReset()
{
    document.forms[1].reset(); 
}//end of onReset()