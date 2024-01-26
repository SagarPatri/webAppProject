/** @ (#) notificationdetails.js 15th May 2008
 * Project     : TTK Healthcare Services
 * File        : notificationdetails.js
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 15th May 2008
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 
 //function close
function onClose()
{
	if(!TrackChanges()) return false;

 	document.forms[1].mode.value="doClose";
 	document.forms[1].action="/GroupListAction.do";
 	document.forms[1].submit();
}//end of function onClose()
function onReset()
{
	document.forms[1].unAssociateNotifyList.selectedIndex=-1;
    document.forms[1].mode.value="doReset";
    document.forms[1].action = "/NotificationDetailsAction.do";
    document.forms[1].submit();
  // 	if(!TrackChanges()) return false;

	/*document.forms[1].mode.value="doReset";
 	document.forms[1].action="/SaveNotificationDetailsAction.do";
 	document.forms[1].submit();*/
}//end of onReset()
function onSave()
{
	trimForm(document.forms[1]);
	document.forms[1].unAssociateNotifyList.selectedIndex=-1;
	if(!(document.frmNotifiDetails.selectedNotifyList.length<1))
	{
		for(var j=0;j<document.frmNotifiDetails.selectedNotifyList.length;j++)
		{
		 document.frmNotifiDetails.selectedNotifyList[j].selected=true;
		}
	if(!JS_SecondSubmit)
	{	
	    document.forms[1].mode.value="doSave";
	    document.forms[1].action = "/SaveNotificationDetailsAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
    return true;
    }//end of if(!(document.frmEvent.selectedRoles.length<1))
    else
     isListSelectedValue(document.frmNotifiDetails.selectedNotifyList,"Notification");
    return false;
}//end of onSave()
function doSelect(fieldName)
{
	fieldName.selectedIndex=-1;
}