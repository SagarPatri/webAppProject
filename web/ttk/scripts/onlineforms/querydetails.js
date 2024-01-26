
function onSave()
 {
 	if(!JS_SecondSubmit)
    {
 		document.forms[1].mode.value="doSave";
    	document.forms[1].action="/EditQueryDetailsAction.do";
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
 }//end of onSave()
 
 //Caleed onclick of edit link
function edit(rownum)
{
    document.forms[1].mode.value="doViewQueryInfo";
    document.forms[1].rownum.value=rownum;
   	document.forms[1].action="/QueryDetailsAction.do";
    document.forms[1].submit();
}//end of edit(rownum) 

//called onclick of close button
 function onClose()
 {
 	if(!TrackChanges()) return false;
 	document.forms[1].mode.value="doClose";
    document.forms[1].action="/QueryDetailsAction.do";
	document.forms[1].submit();
 }//end of onClose()
 
 //called onclick of close button
 function onAddNewQuery()
 {
 	document.forms[1].mode.value="doNewQuery";
    document.forms[1].action="/QueryDetailsAction.do";
	document.forms[1].submit();
 }//end of onAddNewQuery()
 
 //this function is called onclick of reset button
function onReset()
{
	document.forms[1].mode.value="doReset";
	document.forms[1].action="/QueryDetailsAction.do";
	document.forms[1].submit();
}//end of onReset()

function onFeedBackChange()
{
	var feedBackType= document.forms[1].queryFeedbackTypeID.value;
	if(feedBackType=='OFU')
	{
		document.getElementById("remarks").style.display="";
		document.getElementById("status").style.display="";
		document.getElementById("submit").style.display="";
		document.getElementById("feedbackresp").style.display="";
	}//end of if(feedBackType=='OFU')
	else
	{
		document.getElementById("remarks").style.display="none";
		document.getElementById("status").style.display="none";
		document.getElementById("submit").style.display="none";
		document.getElementById("feedbackresp").style.display="none";
	}//end of else
}//end of onFeedBackChange()