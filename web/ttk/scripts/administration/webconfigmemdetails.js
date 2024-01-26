function onSave(objform)
{
   var isAllSelected = true;
	for(i=0;i<objform.length;i++)
	 {
	  if(objform.elements[i].name == 'fieldStatusGenTypeID')
		    {
			    if(objform.elements[i].value=="")
			    {
			        isAllSelected = false;
			        alert('Please select '+ objform.elements[i-2].value);
			        break;
				}//end of if(objform.elements[i].value=="")
		    }//end of if(objform.elements[i].name == 'fieldStatusGenTypeID')
	}//end of for(i=0;i<objform.length;i++)
	    
	if(isAllSelected)
	{
		if(!JS_SecondSubmit)
		{
			document.forms[1].mode.value="doSave";
			document.forms[1].action = "/SaveWebConfigMemDetailsAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	}//end of if(isAllSelected)
}//end of onSave()

function onClose()
{
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/WebConfigMemDetailsAction.do";
    document.forms[1].submit();
}//end of onClose()

function onReset()
{
	document.forms[1].mode.value="doReset";
	document.forms[1].action="/WebConfigMemDetailsAction.do";
	document.forms[1].submit();	
 	
}//end of Reset()