

	function onClose()
	{
		document.forms[1].mode.value="doClose";
		document.forms[1].child.value="";
   		document.forms[1].action="/BufferAction.do";
		document.forms[1].submit();	
	}//end of onClose
	
	function onSave()
	{
		
		
		if(!JS_SecondSubmit)
		{
			document.forms[1].adminExistAlert.value="";
			document.forms[1].mode.value="doSave";
	   		document.forms[1].action="/BufferDetailSaveAction.do";
	   		JS_SecondSubmit=true;
			document.forms[1].submit();	
		}//end of if(!JS_SecondSubmit)
	}//end of onSave
	
	function onReset()
	{
		if(typeof(ClientReset)!= 'undefined' && !ClientReset)
		 {
			document.forms[1].mode.value="doReset";
	   		document.forms[1].action="/BufferDetailAction.do";
			document.forms[1].submit();	
		}
		else
		{
			document.forms[1].reset();
		}
	}//end of onReset
 