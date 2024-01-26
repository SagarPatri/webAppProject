
function onSave()
 {
 	if(!JS_SecondSubmit)
    {
 		document.forms[1].mode.value="doSave";
    	document.forms[1].action="/EditOnlineAssistQueryAction.do";
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
 }//end of onSave()
 
 //Caleed onclick of edit link
function edit(rownum)
{
    document.forms[1].mode.value="doViewQueryInfo";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value ="General";
   	document.forms[1].action="/OnlineAssistQueryAction.do";
    document.forms[1].submit();
}//end of edit(rownum) 

//called onclick of close button
 function onClose()
 {
 	if(!TrackChanges()) return false;
 	document.forms[1].mode.value="doClose";
 	document.forms[1].tab.value ="Search";
    document.forms[1].action="/OnlineAssistQueryAction.do";
	document.forms[1].submit();
 }//end of onClose()
 
 //called onclick of close button
 function onAddNewQuery()
 {
 	document.forms[1].mode.value="doNewQuery";
    document.forms[1].action="/OnlineAssistQueryAction.do";
	document.forms[1].submit();
 }//end of onAddNewQuery()
 
 //this function is called onclick of reset button
function onReset()
{
   	document.forms[1].mode.value="doReset";
   	document.forms[1].action="/OnlineAssistQueryAction.do";
   	document.forms[1].submit();
}//end of onReset()