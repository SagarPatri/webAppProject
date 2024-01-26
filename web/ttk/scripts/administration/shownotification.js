/** @ (#) inscompanyinfo.js 25th Feb 2008
 * Project     : TTK Healthcare Services
 * File        : shownotification.js
 * Author      : Kishor kumar
 * Company     : Span Systems Corporation
 * Date Created: 09 05 2014
 *
 * @author 		 : Kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 //newDev

function onClickMe()
{
	document.forms[1].mode.value = 'doClickMeAction';
	document.forms[1].action="/HospitalConfigurationAction.do";
	document.forms[1].submit();
	
}

//kocnewhosp1

function showNotification()
{
	document.forms[1].mode.value = 'doShowNotify';
	document.forms[1].action="/HospitalConfigurationAction.do";
	document.forms[1].submit();
	
}
function showUploads()
{
	document.forms[1].mode.value = 'doShowUploads';
	document.forms[1].action="/HospitalConfigurationAction.do";
	document.forms[1].submit();
}
 //this function is called onclick of the save button
function onSave()
{
	if(document.forms[1].hospInfo.value=="")
	{
		alert("Please Enter Hospital Information");
		return false;
	}
	if(!JS_SecondSubmit)
	{
		var str = document.forms[1].hospInfo.value;
		var strIndex = str.indexOf("\n");
		while(strIndex != -1)
		{
			str = str.replace("\n","</br>");
			strIndex = str.indexOf("\n");
		}
		document.forms[1].hospInfo.value=str;
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doSaveNotify";
		//document.forms[1].action = "/ServTaxConfigurationAction.do";
		document.forms[1].action = "/HospitalConfigurationAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

//this function is called onclick of close button
function onClose()
{
	if(!TrackChanges()) return false;
 	document.forms[1].mode.value="doCloseHosp";
	document.forms[1].action="/HospitalConfigurationAction.do";
	document.forms[1].submit();	
}//end of onClose()