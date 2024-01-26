//javascript used in tdsacknowledge.jsp of Finance TDS flow

//function to sort based on the link selected
	function onSave()
	{
		if (!JS_SecondSubmit) {
			trimForm(document.forms[1]);
				document.forms[1].mode.value = "doSave";
				document.forms[1].action = "/SaveTDSAckAction.do";
				JS_SecondSubmit = true
				document.forms[1].submit();
		}//end of if (!JS_SecondSubmit)
	}//end of onAdd()
	
	
	function onReset()
	{
		if(typeof(ClientReset)!= 'undefined' && !ClientReset)
		{
			document.forms[1].mode.value="doReset";
		    document.forms[1].action = "/TDSAcknowledgeAction.do";
		    document.forms[1].submit();
		}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
		else
		{
			document.forms[1].reset();		
		}//end of else
	}//end of onAdd()

	function onClose()
	{
		if(!TrackChanges()) return false;
		document.forms[1].finYearTo.value="";
		document.forms[1].mode.value="doClose";
	    document.forms[1].action = "/TDSAcknowledgeAction.do";
	    document.forms[1].submit();
	}//end of onAdd()

	
	