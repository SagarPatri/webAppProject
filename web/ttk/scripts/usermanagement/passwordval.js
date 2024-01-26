/**
 * @ (#) passwordval.js 4th jan 2008
 * Project      : TTK HealthCare Services
 * File         : passwordval.js
 * Author       : Yogesh S.C
 * Company      : Span Systems Corporation
 * Date Created : 4th jan 2008
 *
 * @author       :Yogesh S.C
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 
//this function is called onclick of save button 
function onSave()
{
		document.forms[1].mode.value="doSave";
	    document.forms[1].action="/UpdateUserPasswordValidityConfig.do";
	    //JS_SecondSubmit=true;
		document.forms[1].submit();
}//end of onSave()

//this function is called onclick of reset button
function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 {
	  	document.forms[1].mode.value="doReset";
	  	document.forms[1].action="/UserPasswordValidityConfig.do";
	  	document.forms[1].submit();	
	 }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 else
	 {
	  	document.forms[1].reset();		  	
	 }//end of else		  
}//end of onReset()

//this function is called onclick of close button
function onClose()
{
	if(!TrackChanges()) return false;
 	document.forms[1].mode.value="doClose";
	document.forms[1].action="/UserPasswordValidityConfig.do";
	document.forms[1].submit();	
}//end of onClose()

function onResetPasswordAll()
{
	var message=confirm('Click here to reset the password for all Vidal Health Users');
	if(message)
	{
		if(!JS_SecondSubmit)
	    {
			document.forms[1].mode.value="doResetPasswordAll";
			document.forms[1].action="/UserPasswordValidityConfig.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit) 	
	}//end of if(message)
}//end of onResetPassword()
