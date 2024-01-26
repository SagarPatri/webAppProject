	//java script for the groupdetail.jsp screen in the Group Registration of Empanelment flow.

	//function save
	function onSave()
	{
		if(document.getElementById("phoneNbr2").value=='Phone No2')
			document.getElementById("phoneNbr2").value='';
	  if(!JS_SecondSubmit)
      {	
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doSave";
	    document.forms[1].action="/GroupRegistrationSaveAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
 	  }//end of if(!JS_SecondSubmit)	
	}//end of onSave()

	//function close
	function onClose()
	{
		if(!TrackChanges()) return false;

	 	document.forms[1].mode.value="doClose";
	 	document.forms[1].child.value="";
	 	document.forms[1].action="/GroupListAction.do";
	 	document.forms[1].submit();
	}//end of function onClose()

	//function onReset
	function onReset()
	{
		if(typeof(ClientReset)!= 'undefined' && !ClientReset)
		{
				document.forms[1].mode.value ="doReset";
				document.forms[1].action = "/GroupRegistrationAction.do";
				document.forms[1].submit();

		}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
		else
		{
			document.forms[1].reset();
			onchangeGroup();
		}//end of else
	}//end of onReset()

	//function to delete
	function onDelete()
	{
	 	var msg = confirm("Are you sure you want to delete the record ?");
		if(msg)
		{
			document.forms[1].mode.value="doDelete";
			document.forms[1].action="/GroupListAction.do";
			document.forms[1].submit();
		}//end of if(msg)
	}//end of onDelete()

	function onChangeState(obj)		
	{
		if(obj.value!="")
	    {
    	document.forms[1].mode.value="doChangeState";
    	document.forms[1].focusID.value="state";
    	document.forms[1].action="/GroupRegistrationAction.do";
    	document.forms[1].submit();
	    }
	}//end of onChangeState()
	
	function selectAccntManager()		
	{
    	document.forms[1].mode.value="doSelectManager";
    	document.forms[1].action="/GroupRegistrationAction.do";
    	document.forms[1].submit();
	}//end of selectAccntManager()
	
	function clearAccntManager()
	{
		document.forms[1].mode.value="doClearManager";
    	document.forms[1].action="/GroupRegistrationAction.do";
    	document.forms[1].submit();
	}//end of clearAccntManager()
	function onchangeGroup()
	{
		selObj = document.forms[1].groupGenTypeID;
		if(selObj!=null)
		{
		    var selVal = selObj.options[selObj.selectedIndex].value;
			if(selVal == 'CRP')
			{
				document.getElementById('corporate').style.display = "";
				
			}//end of if(selVal == 'CRP')
			else
			{
				document.forms[1].ccEmailId.value="";
				document.forms[1].notiEmailId.value="";
				document.forms[1].notiTypeId.value="NIG";
				document.getElementById('corporate').style.display = "none";
			}//end of else
		}//end of if(selObj!=null)
		// added for koc 1346
		if(document.forms[1].ppn.value=="Y")
		{
			document.forms[1].ppnYN.checked=true;
		}
		else
		{
			document.forms[1].ppnYN.checked=false;
		}
		//end  added for koc 1346
	}// end of onchangeGroup()
	