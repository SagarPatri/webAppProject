//javascript file for the Associate Product Screen of the Administration module

function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
	{
	if(isValidated()==true)
		{
			objform = document.forms[1];
			if(document.forms[1].limitSeqID.length)//if more than one records are there
		 	{
		 		var obj=document.forms[1].elements['checkbox'];
		 		var obj2=document.forms[1].elements['flag'];
		 		var obj3=document.forms[1].elements['renewalDays'];
		 		var obj4=document.forms[1].elements['days'];
		 		
		 		for(i=0;i<document.forms[1].limitSeqID.length;i++)
		  		{
		  			if(obj[i].checked)
			  		{
			  			obj2[i].value='Y';
			  		}
			  		else
			 		{
			 			obj2[i].value='N';
			 		}
			 		obj3[i].value=document.forms[1].days[i].value;
		  		}//end of for
		 	}//end of if
		 	else
		 	{
			 	if(document.forms[1].checkbox.checked)
			 	{
			 		document.forms[1].flag.value='Y';
			 	}
			 	else
			 	{
			 		document.forms[1].flag.value='N';
			 	}
			 	document.forms[1].renewalDays.value=document.forms[1].value;
		 	}//end of else
				document.forms[1].mode.value="doSave";
				document.forms[1].action="/UpdateRenewalDaysConf.do";
				JS_SecondSubmit=true
				document.forms[1].submit();
		}//end of if(isValidated()==true)
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/RenewalConfiguration.do";
		document.forms[1].submit();
 	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
 	else
 	{
  		document.forms[1].reset();
  	}//end of else
  	onDocumentLoad();
}//end of Reset()

function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value="doClose";
	//document.forms[1].child.value="";
	document.forms[1].action="/RenewalConfiguration.do";
	document.forms[1].submit();
}//end of onClose()

function onDocumentLoad()
 {
    objform = document.forms[1];
    
   	if(document.forms[1].limitSeqID.length)//if more than one records are there
 	{
 		var obj=document.forms[1].elements['flag'];
 		var obj3=document.forms[1].elements['renewalDays'];
 		
 		for(i=0;i<document.forms[1].limitSeqID.length;i++)
  		{
	  		
	  		if(obj[i].value == "Y")
	  		{
	  			document.forms[1].checkbox[i].checked=true;
	  			document.forms[1].days[i].disabled=false;
	  		}
	  		else
	 		{
	 			document.forms[1].checkbox[i].checked=false;
	 			document.forms[1].days[i].disabled=true;
	 		}
	 		document.forms[1].days[i].value=obj3[i].value;
  		}//end of for
 	}//end of if
 	else
 	{
	 	if(document.forms[1].flag.value =="Y")
	  		{
	  			document.forms[1].checkbox.checked=true;
	  			document.forms[1].days.disabled=false;
	  		}
	  		else
	 		{
	 			document.forms[1].checkbox.checked=false;
	 			document.forms[1].days.disabled=true;
	 		}
	 		document.forms[1].days.value=document.forms[1].renewalDays.value;
 	}//end of else
 	 
 }//end of onDocumentLoad()
 
 function onCheck()
{
	objform = document.forms[1];
	var obj=document.forms[1].elements['checkbox'];
	var obj2=document.forms[1].elements['renewalDays'];
	if(document.forms[1].limitSeqID.length)//if more than one records are there
 	{
		for(i=0;i<document.forms[1].limitSeqID.length;i++)
		{
			if(obj[i].checked)
			{
				document.forms[1].days[i].disabled=false;
			}
		 	else
		 	{
		 		document.forms[1].days[i].disabled=true;
		 		document.forms[1].days[i].value="";
		 		obj2[i].value="";
		 	}
		}//end of for
	 	
 	}
 	else
 	{
 		if(document.forms[1].checkbox.checked)
 		{
 			document.forms[1].days.disabled=false;
 		}
 		else
 		{
 			document.forms[1].days.disabled=true;
 			document.forms[1].days.value="";
 			document.forms[1].renewalDays.value="";
 		}
 		document.forms[1].renewalDays.value=document.forms[1].days.value;
 	}
 	
}//end of onCheck()

function isValidated()
 {
 	trimForm(document.forms[1]);
 	var number=/^[0-9]*$/;
 	var obj=document.forms[1].elements['renewalDays'];
 	var obj2=document.forms[1].elements['days'];
 	var obj3=document.forms[1].elements['checkbox'];
 	if(document.forms[1].limitSeqID.length)//if more than one records are there
 	{
	  for(i=0;i<obj.length;i++)
	  {
		  if(obj2[i].value.length>0)
		  {
		  	if(number.test(document.forms[1].days[i].value)==false)
			 {
				alert('No of days should be a numeric value.');						
				document.forms[1].days[i].select();										
				return false;
			 }//end of if(number.test(obj[i].value)==false)
		  }//end of if
	  }//end of for
	  
	  for(i=0;i<obj3.length;i++)
	  {
		  if(obj3[i].value.length>0)
		  {
		  	if((obj3[i].checked)&& (document.forms[1].days[i].value =="" || document.forms[1].days[i].value==null))
		  	{
		  		alert('Enter the No of days for the selected Enrollment Type');						
				document.forms[1].days[i].focus();										
				return false;
		  	}
		  }//end of if
	  }//end of for
	}//end of if
	  
  else
  {
 		if(number.test(document.forms[1].days.value)==false)
		 {
			alert('No of days should be a numeric value.');						
			document.forms[1].days.select();										
			return false;
		 }//end of if(bigdecimal.test(document.forms[1].tariffRequestedAmt.value)==false)
		 
		 if((document.forms[1].checkbox.checked)&& (document.forms[1].days.value =="" || document.forms[1].days.value==null))
	  	 {
	  		alert('Enter the No of days for the selected checkbox');						
			document.forms[1].days.focus();										
			return false;
	  	 }//end of if
  }//end of else
	return true;
 }//end of isValidated()