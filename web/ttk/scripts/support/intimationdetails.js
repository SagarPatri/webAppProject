/** @ (#) intimationdetails.js 15th Mar 2008
 * Project     : TTK Healthcare Services
 * File        : intimationdetails.js
 * Author      : Krupa J
 * Company     : Span Systems Corporation
 * Date Created: 15th Mar 2008
 *
 * @author 		 : Krupa J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

//on Click of reset button
function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
    	document.forms[1].mode.value="doReset";
    	document.forms[1].action = "/EditIntimationDetailsAction.do";
    	document.forms[1].submit();
    }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()

//on Click of save button
function onSave()
{
	if(!JS_SecondSubmit)
    {
		trimForm(document.forms[1]);
		document.forms[1].action = "/SaveIntimationDetailsAction.do";
		document.forms[1].mode.value="doSave";
		JS_SecondSubmit=true
    	document.forms[1].submit();
  	}//end of if(!JS_SecondSubmit)
}//end of onSave()

//on Click of close button
function onClose()
{
		if(!TrackChanges()) return false;
		document.forms[1].mode.value="doClose";
		document.forms[1].tab.value ="Search";
		document.forms[1].action="/EditIntimationDetailsAction.do";
		document.forms[1].submit();
}//end of onClose()