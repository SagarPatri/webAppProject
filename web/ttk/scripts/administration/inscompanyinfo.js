/** @ (#) inscompanyinfo.js 25th Feb 2008
 * Project     : TTK Healthcare Services
 * File        : inscompanyinfo.js
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 25th Feb 2008
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 
 //this function is called onclick of the save button
function onSave()
{
	if(!JS_SecondSubmit)
	{
		var str = document.forms[1].insInfo.value;
		var strIndex = str.indexOf("\n");
		while(strIndex != -1)
		{
			str = str.replace("\n","</br>");
			strIndex = str.indexOf("\n");
		}
		document.forms[1].insInfo.value=str;
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doSave";
		document.forms[1].action = "/SaveInsCompanyInfoAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

//this function is called onclick of close button
function onClose()
{
	if(!TrackChanges()) return false;
 	document.forms[1].mode.value="doClose";
	document.forms[1].action="/InsCompanyInfoAction.do";
	document.forms[1].submit();	
}//end of onClose()