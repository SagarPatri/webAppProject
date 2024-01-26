/**
 * @ (#) cancelenroll.js jan 18th 2008
 * Project      : TTK HealthCare Services
 * File         : cancelenroll.js
 * Author       : Yogesh S.C
 * Company      : Span Systems Corporation
 * Date Created : jan 18th 2008
 *
 * @author       : Yogesh S.C
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 
//this function is called on click of save button
function onSave()
{
	if(!JS_SecondSubmit)
	{		
			trimForm(document.forms[1]);
			document.forms[1].mode.value="doSaveEnrollCancel";
			document.forms[1].action="/SaveCancelEnrollmentAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();		
	}//end of if(!JS_SecondSubmit)	
}//end of onSave()

//this function is called on click of reset button
function onReset(strRootIndex)
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doResetEnrollCancel";
	    document.forms[1].action="/ResetCancelEnrollmentAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
		onStopPatClmProcess();
	}//end of else
}//end of onReset(strRootIndex)

//this function is called on click of close button
function onClose()
{
    document.forms[1].mode.value="doCloseEnrollCancel";
    document.forms[1].child.value="";
	document.forms[1].action="/ResetCancelEnrollmentAction.do";
	document.forms[1].submit();
}//end of onClose()

//this function is called on load of the jsp
function onStopPatClmProcess()
{
	if(document.getElementById('stopPatClmProcessYN').checked)
	{
		document.getElementById('receivedAfter').disabled = false;
		document.getElementById('calStartDate').style.display = '';
	}//end of if(document.getElementById('stopPatClmProcessYN').checked)
	else
	{
		document.getElementById('receivedAfter').disabled = true;
		document.getElementById('receivedAfter').value = '';
		document.getElementById('calStartDate').style.display = 'none';
	}//end of else
}//end of onStopPatClmProcess()