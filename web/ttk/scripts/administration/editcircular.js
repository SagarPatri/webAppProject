//java script for the edit circular details screen in the administration of products flow.

function onSubmit()
{
	document.forms[1].issueDate.value=trim(document.forms[1].issueDate.value);
	document.forms[1].receiveDate.value=trim(document.forms[1].receiveDate.value);
	document.forms[1].circularNumber.value=trim(document.forms[1].circularNumber.value);
	document.forms[1].circlarTitle.value=trim(document.forms[1].circlarTitle.value);
	document.forms[1].circlarDesc.value=trim(document.forms[1].circlarDesc.value);
	if(!JS_SecondSubmit) 
	{
    	document.forms[1].mode.value="doSave";
    	document.forms[1].action="/UpdateCircularAction.do";
    	JS_SecondSubmit=true
    	document.forms[1].submit();  
    }//end of if(!JS_SecondSubmit)	  
}//end of onSubmit()

function onClose()
{
	if(!TrackChanges()) return false;

    document.forms[1].mode.value="doClose";
    document.forms[1].child.value="Circulars List";
    document.forms[1].action = "/CircularAction.do";
    document.forms[1].submit();
}//end of onClose()

function onReset()
{	
    if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
	    document.forms[1].mode.value="doViewCirculars";
	    document.forms[1].action = "/EditCircularAction.do";
	    document.forms[1].submit();	
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else

}//end of onReset()