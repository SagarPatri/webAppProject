//java script for the clause details screen in the empanelment of hospital flow.

//function to check/un-check the checkboxes
function SetState(obj_checkbox, counter, clauseNumber)
{  
	if(obj_checkbox.checked)
	{
		document.forms[1].Description[counter].disabled = false;
		document.forms[1].nonapplicable[counter].value = "";
		document.forms[1].applicable[counter].value = clauseNumber;
	}//end of if
	else
	{
		document.forms[1].Description[counter].disabled = true;
		document.forms[1].nonapplicable[counter].value = clauseNumber;
		document.forms[1].applicable[counter].value = "";
	}//end of else	
}//end of SetState(obj_checkbox, counter, clauseNumber)

//function to navigate to the article detail screen
function onSubmit()
{
 if(!JS_SecondSubmit)
 {	  
    for(var i=0;i<document.forms[1].Description.length;i++)
	{
		 document.forms[1].Description[i].value=trim(document.forms[1].Description[i].value);
		if(document.forms[1].chkApplicable[i].checked)
		{
			if(trim(document.forms[1].Description[i].value).length==0)
			{
				alert('Please provide Clause Description');
				document.forms[1].Description[i].focus();
				return false				
			}//end of if(trim(document.forms[1].Description[i].value).length==0)
		}//end of if(document.forms[1].chkApplicable[i].checked)
	}//end of for    
    document.forms[1].mode.value="doSave";
    document.forms[1].action = "/MOUAction.do";
    JS_SecondSubmit=true
	document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)	
}//end of onSubmit()

//function to re-set the screen
function onReset()
{
    document.forms[1].reset();
    
    for(var i=0; i < document.forms[1].chkApplicable.length; i++)
    {
    	if(document.forms[1].chkApplicable[i].checked){
    	    document.forms[1].Description[i].disabled = false;
    	}//end of if(document.forms[1].chkApplicable[i].checked)    
    	else{
    	    document.forms[1].Description[i].disabled = true;
    	}//end of else    
    }//end of for
}//end of onReset()