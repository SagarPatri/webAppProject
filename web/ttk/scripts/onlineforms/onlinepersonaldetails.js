/**
 * @ (#) onlinepersonaldetails.js 21st Feb 2008
 * Project      : TTK HealthCare Services
 * File         : onlinepersonaldetails.js
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 21st Feb 2008
 *
 * @author       : Chandrasekaran J
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 */

// this function is called onclick of reset button
function Reset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/OnlinePersonalDetailsAction.do";
		document.forms[1].submit();
 	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
 	else
 	{
  		document.forms[1].reset();
 	}//end of else		
}//end of Reset()

// this function is called onclick of save button
function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/SavePersonalDetailsAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
}//end of onSave()