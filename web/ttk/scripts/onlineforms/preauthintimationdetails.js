/** @ (#) preauthintimationdetails.js 12th Mar 2008
 * Project     : TTK Healthcare Services
 * File        : preauthintimationdetails.js
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 12th Mar 2008
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 
 //called onclick of selesct hospital icon
 function onSelectHospital(hospitalIndex)
 {
 	document.forms[1].hospitalvalue.value=hospitalIndex;
 	document.forms[1].mode.value="doSelectHospital";
    document.forms[1].action="/EditPreAuthIntimationAction.do";
	document.forms[1].submit();
 }//end of onSelectHospital(hospitalIndex)
 
 //called onclick of selesct Cancel  icon
 function onCancelHospital(hospitalIndex)
 {
 	document.forms[1].hospitalvalue.value=hospitalIndex;
 	document.forms[1].mode.value="doDeleteHospital";
    document.forms[1].action="/EditPreAuthIntimationAction.do";
	document.forms[1].submit();
 }//end of onSelectHospital(hospitalIndex)
 
 //called onclick of save button
 function onSave()
 {
 	if(!JS_SecondSubmit)
    {
 		document.forms[1].mode.value="doSave";
    	document.forms[1].action="/SavePreAuthIntimationAction.do";
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
 }//end of onSave()
 
 //called onclick of close button
 function onClose()
 {
 	if(!TrackChanges()) return false;
 	document.forms[1].mode.value="doClose";
    document.forms[1].action="/EditPreAuthIntimationAction.do";
	document.forms[1].submit();
 }//end of onSave()
 //this function is called onclick of reset button
function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  	{
    	document.forms[1].mode.value="doReset";
    	document.forms[1].action="/EditPreAuthIntimationAction.do";
    	document.forms[1].submit();
  	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  else
  {
    document.forms[1].reset();
  }//end of else
}//end of onReset()