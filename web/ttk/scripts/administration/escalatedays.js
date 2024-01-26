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
		 		var obj=document.forms[1].elements['remType'];
		 		var obj2=document.forms[1].elements['flag'];
		 		var obj3=document.forms[1].elements['escalateDays'];
		 		//var obj4=document.forms[1].elements['days'];
		 	}
		    if(!JS_SecondSubmit)
			{
			    document.forms[1].mode.value="doSave";
			    if(document.forms[0].sublink.value == "Products")
			    {
			      document.forms[1].action="/UpdateEscalationProdConf.do";
			    }
			    else
			    {
			      document.forms[1].action="/UpdateEscalationPolicyConf.do";
			    }
			    JS_SecondSubmit=true;
			   document.forms[1].submit();
		    
		    
		  
		}//end of if(isValidated()==true)
	}//end of if(!JS_SecondSubmit)
	}
}//end of onSave()

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/EscalationConfiguration.do";
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
	document.forms[1].action="/EscalationConfiguration.do";
	document.forms[1].submit();
}//end of onClose()

/*function onDocumentLoad()
 {
    objform = document.forms[1];
    
   	if(document.forms[1].limitSeqID.length)//if more than one records are there
 	{
 		var obj=document.forms[1].elements['flag'];
 		var obj3=document.forms[1].elements['escalateDays'];
 		//alert("escalateDays length "+obj3.length);
 		//alert("flag length "+obj.length);
 		for(i=0;i<document.forms[1].limitSeqID.length;i++)
  		{
	  		//alert("Flag value" +obj[i].value);
	  		if(obj[i].value == "Y")
	  		{
	  			document.forms[1].checkbox[i].checked=true;
	  			//alert(obj3[i].value);
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
	 		document.forms[1].days.value=document.forms[1].escalateDays.value;
 	}//end of else
 	 
 }//end of onDocumentLoad()
 
 function onCheck()
{
	objform = document.forms[1];
	var obj=document.forms[1].elements['remType'];
	var obj2=document.forms[1].elements['escalateDays'];
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
 			document.forms[1].escalateDays.value="";
 		}
 		document.forms[1].escalateDays.value=document.forms[1].days.value;
 	}
 	
}//end of onCheck()
*/
function isValidated()
 {
 	trimForm(document.forms[1]);
 	var number=/^[0-9]*$/;
 	var obj=document.forms[1].elements['escalateDays'];
 	var obj2=document.forms[1].elements['days'];
 	var obj3=document.forms[1].elements['remType'];
 	if(document.forms[1].limitSeqID.length)//if more than one records are there
 	{
	  for(i=0;i<obj.length;i++)
	  {
		  if(obj2[i].value.length>0)
		  {
		  	if(number.test(document.forms[1].days[i].value)==false)
			 {
				alert('Duration should be a numeric value.');						
				document.forms[1].days[i].select();										
				return false;
			 }//end of if(number.test(obj[i].value)==false)
		  }//end of if
	  }//end of for
	  
	  for(i=0;i<obj3.length;i++)
	  {
		  if(obj3[i].value.length>0)
		  {
		  	if((document.forms[1].remType[i].value =="")&& (document.forms[1].days[i].value =="" || document.forms[1].days[i].value==null))
		  	{
		  		alert('Enter the No of days for the selected Remainders ');						
				document.forms[1].remType[i].focus();										
				return false;
		  	}
		  }//end of if
	  }//end of for
	}//end of if
	  
  else
  {
 		if(number.test(document.forms[1].days.value)==false)
		 {
			alert('Duration should be a numeric value.');						
			document.forms[1].days.select();										
			return false;
		 }//end of if(bigdecimal.test(document.forms[1].tariffRequestedAmt.value)==false)
		 
		
  }//end of else
	return true;
 }//end of isValidated()



function onDocumentLoad()
{
   objform = document.forms[1];
   
  	if(document.forms[1].limitSeqID.length)//if more than one records are there
	{
		var obj=document.forms[1].elements['flag'];
		var obj3=document.forms[1].elements['escalateDays'];
		
		for(i=0;i<document.forms[1].limitSeqID.length;i++)
 		{
	  		
	 		document.forms[1].days[i].value=obj3[i].value;
	 		
 		}//end of for
	}//end of if
	else
	{
	 	
	 		document.forms[1].days.value=document.forms[1].escalateDays.value;
	}//end of else
	 
	 
}//end of onDocumentLoad()

