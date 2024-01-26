function onDocumentLoad()
 {
    objform = document.forms[1];
    
   	if(document.forms[1].relshipTypeID.length)//if more than one records are there
 	{
 		var obj1=document.forms[1].elements['selectedYN'];
 		var obj2=document.forms[1].elements['windowPeriodResYN'];
 		var obj3=document.forms[1].elements['policyWindowPeriod'];
 		var obj4=document.forms[1].elements['fromDateGenTypeID'];
 		var obj5=document.forms[1].elements['relshipWindowPeriod'];
 		
 		
 		for(i=0;i<document.forms[1].relshipTypeID.length;i++)
  		{
	  		if(obj1[i].value == "Y")
	  		{
	  			document.forms[1].relSelectedYN[i].checked=true;
	  		}//end of if(obj1[i].value == "Y")
	  		else
	 		{
	 			document.forms[1].relSelectedYN[i].checked=false;
	 		}//end of else
	 		if(obj2[i].value == "Y")
	  		{
	  			document.forms[1].relWindowPeriodResYN[i].checked=true;
	  			document.forms[1].relWindowPeriodResYN[i].disabled=false;
	  			document.forms[1].fromDateGenTypeID[i].disabled=false;
	  			document.forms[1].relationWindowPeriod[i].disabled=false;
	  			if(obj5[i].value.length>0)
	 			{
	 				document.forms[1].relationWindowPeriod[i].value=obj5[i].value;
	 			}//end of if(obj5[i].value.length>0)
	 			else
	 			{
	 				document.forms[1].relationWindowPeriod[i].value=obj3[i].value;
	 			}//end of else
	 		}//end of if(obj2[i].value == "Y")
	  		else
	 		{
	 			document.forms[1].relWindowPeriodResYN[i].checked=false;
	 			document.forms[1].relWindowPeriodResYN[i].disabled=true;
	 			document.forms[1].fromDateGenTypeID[i].disabled=true;
	 			document.forms[1].relationWindowPeriod[i].disabled=true;
	 			//document.forms[1].relationWindowPeriod[i].value="";
	 		}
	 		//document.forms[1].relWindowPeriodResYN[i].value=obj2[i].value;
	 	}//end of for
 	}//end of if
 	else
 	{
	 		if(document.forms[1].selectedYN.value =="Y")
	  		{
	  			document.forms[1].relSelectedYN.checked=true;
	  			document.forms[1].relWindowPeriodResYN.disabled=false;
	  		}
	  		else
	 		{
	 			document.forms[1].relSelectedYN.checked=false;
	 			document.forms[1].relWindowPeriodResYN.disabled=true;
	 		}
	 		if(document.forms[1].windowPeriodResYN.value =="Y")
	  		{
	  			document.forms[1].relWindowPeriodResYN.checked=true;
	  			document.forms[1].relWindowPeriodResYN.disabled=false;
	  			document.forms[1].fromDateGenTypeID.disabled=false;
	  			document.forms[1].relationWindowPeriod.disabled=false;
	  			if(document.forms[1].relshipWindowPeriod.value.length >0)
	 			{
	 				document.forms[1].relationWindowPeriod.value=document.forms[1].relshipWindowPeriod.value;
	 			}
	 			else
	 			{
	 				document.forms[1].relationWindowPeriod.value=document.forms[1].policyWindowPeriod.value;
	 			}
	 		}
	  		else
	 		{
	 			document.forms[1].relWindowPeriodResYN.checked=false;
	 			document.forms[1].relWindowPeriodResYN.disabled=true;
	 			document.forms[1].fromDateGenTypeID.disabled=true;
	 			document.forms[1].relationWindowPeriod.disabled=true;
	 			//document.forms[1].relationWindowPeriod.value="";
	 		}
	 		//document.forms[1].relWindowPeriodResYN.value=document.forms[1].windowPeriodResYN.value;
	}//end of else
 	 onCheck();
 }//end of onDocumentLoad()
 
 function onCheck()
{
	objform = document.forms[1];
	var obj1=document.forms[1].elements['relSelectedYN'];
 	var obj2=document.forms[1].elements['relWindowPeriodResYN'];
	if(document.forms[1].relshipTypeID.length)//if more than one records are there
 	{
		for(i=0;i<document.forms[1].relshipTypeID.length;i++)
		{
			if(obj1[i].checked)
			{
				document.forms[1].relWindowPeriodResYN[i].disabled=false;
			}
		 	else
		 	{
		 		document.forms[1].relWindowPeriodResYN[i].disabled=true;
		 		document.forms[1].relWindowPeriodResYN[i].checked=false;
		 		obj2[i].value="N";
		 	}//end of else
		}//end of for(i=0;i<document.forms[1].relshipTypeID.length;i++)
	}//end of if(document.forms[1].relshipTypeID.length)
 	else
 	{
 		if(document.forms[1].relSelectedYN.checked)
 		{
 			document.forms[1].relWindowPeriodResYN.disabled=false;
 		}
 		else
 		{
 			document.forms[1].relWindowPeriodResYN.disabled=true;
 			document.forms[1].relWindowPeriodResYN.checked=false;
 			document.forms[1].windowPeriodResYN.value="N";
 		}//end of else
 		document.forms[1].windowPeriodResYN.value=document.forms[1].relWindowPeriodResYN.value;
 	}//end of else
 	onWindowCheck();
}//end of onCheck()

function onWindowCheck()
{
	objform = document.forms[1];
	var obj1=document.forms[1].elements['relWindowPeriodResYN'];
 	var obj2=document.forms[1].elements['relshipWindowPeriod'];
 	var obj3=document.forms[1].elements['fromDateGenTypeID'];
 	var obj4=document.forms[1].elements['policyWindowPeriod'];
 	
	if(document.forms[1].relshipTypeID.length)//if more than one records are there
 	{
		for(i=0;i<document.forms[1].relshipTypeID.length;i++)
		{
			if(obj1[i].checked)
			{
				document.forms[1].relationWindowPeriod[i].disabled=false;
				if(document.forms[1].relationWindowPeriod[i].value.length==0)
				{
					if(obj2[i].value.length>0)
		 			{
		 				document.forms[1].relationWindowPeriod[i].value=obj2[i].value;
		 			}//end of if(obj5[i].value.length>0)
		 			else
		 			{
		 				document.forms[1].relationWindowPeriod[i].value=obj4[i].value;
		 			}//end of else
	 			}//end of if(document.forms[1].relationWindowPeriod[i].value.length==0)
	 			document.forms[1].fromDateGenTypeID[i].disabled=false;
			}
		 	else
		 	{
		 		document.forms[1].relationWindowPeriod[i].disabled=true;
		 		document.forms[1].relationWindowPeriod[i].value="";
		 		document.forms[1].fromDateGenTypeID[i].disabled=true;
		 		document.forms[1].fromDateGenTypeID[i].value="";
		 	}//end of else
		}//end of for(i=0;i<document.forms[1].relshipTypeID.length;i++)
	}//end of if(document.forms[1].relshipTypeID.length)
 	else
 	{
 		if(document.forms[1].relWindowPeriodResYN.checked)
 		{
 			document.forms[1].relationWindowPeriod.disabled=false;
 			if(document.forms[1].relationWindowPeriod.value.length==0)
			{
 				if(document.forms[1].relshipWindowPeriod.value.length>0)
	 			{
	 				document.forms[1].relationWindowPeriod.value=document.forms[1].relshipWindowPeriod.value;
	 			}//end of if(obj5[i].value.length>0)
	 			else
	 			{
	 				document.forms[1].relationWindowPeriod.value=document.forms[1].policyWindowPeriod.value;
	 			}//end of else
	 		}//end of if(document.forms[1].relationWindowPeriod.value.length==0)
 			document.forms[1].fromDateGenTypeID.disabled=false;
 			document.forms[1].fromDateGenTypeID.value="";
 		}
 		else
 		{
 			document.forms[1].relationWindowPeriod.disabled=true;
		 	document.forms[1].relationWindowPeriod.value="";
		 	document.forms[1].fromDateGenTypeID.disabled=true;
		 	document.forms[1].fromDateGenTypeID.value="";
 		}//end of else
 		document.forms[1].relationWindowPeriod.value=document.forms[1].relshipWindowPeriod.value;
 	}//end of else
}

//javascript file for the Associate Product Screen of the Administration module

function onSave()
{
	trimForm(document.forms[1]);
	
	if(!JS_SecondSubmit)
	{
		if(isValidated()==true)
				{
					if(document.forms[1].relshipTypeID.length)//if more than one records are there
				 	{
				 		var obj1=document.forms[1].elements['selectedYN'];
				 		var obj2=document.forms[1].elements['relSelectedYN'];
				 		
				 		var obj3=document.forms[1].elements['windowPeriodResYN'];
				 		var obj4=document.forms[1].elements['relWindowPeriodResYN'];
				 		
				 		var obj5=document.forms[1].elements['relshipWindowPeriod'];
				 		var obj6=document.forms[1].elements['relationWindowPeriod'];
				 		
				 		for(i=0;i<document.forms[1].relshipTypeID.length;i++)
				  		{
				  			//set the value for selectedYN flag
				  			if(obj2[i].checked)
					  		{
					  			obj1[i].value='Y';
					  		}
					  		else
					 		{
					 			obj1[i].value='N';
					 		}
					 		//set the value for windowPeriodResYN flag
					 		if(obj4[i].checked)
					  		{
					  			obj3[i].value='Y';
					  		}
					  		else
					 		{
					 			obj3[i].value='N';
					 		}
					 		obj5[i].value=obj6[i].value;
					 		document.forms[1].fromDateGenTypeID[i].disabled=false;
				  		}//end of for
				 	}//end of if
				 	else
				 	{
					 	if(document.forms[1].selectedYN.checked)
					 	{
					 		document.forms[1].relSelectedYN.value='Y';
					 	}
					 	else
					 	{
					 		document.forms[1].relSelectedYN.value='N';
					 	}
					 	
					 	if(document.forms[1].windowPeriodResYN.checked)
					 	{
					 		document.forms[1].relWindowPeriodResYN.value='Y';
					 	}
					 	else
					 	{
					 		document.forms[1].relWindowPeriodResYN.value='N';
					 	}
					 	document.forms[1].fromDateGenTypeID.disabled=false;
					 	document.forms[1].relshipWindowPeriod.value=document.forms[1].relationWindowPeriod.value;
				 	}//end of else
				 	
				 	
						document.forms[1].mode.value="doSave";
						document.forms[1].action="/UpdateRelationshipAction.do";
						JS_SecondSubmit=true
						document.forms[1].submit();
				}//end of if(isValidated()==true)
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value="doClose";
	document.forms[1].action="/RelationshipInfoAction.do";
	document.forms[1].submit();
}//end of onClose()

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/RelationshipInfoAction.do";
		document.forms[1].submit();
 	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
 	else
 	{
  		document.forms[1].reset();
  	}//end of else
  	onDocumentLoad();
}//end of Reset()

function isValidated()
 {
 	trimForm(document.forms[1]);
 	var number=/^[0-9]*$/;
 	var obj2=document.forms[1].elements['relationWindowPeriod'];
 	var obj3=document.forms[1].elements['relWindowPeriodResYN'];
 	
 	if(document.forms[1].relshipTypeID.length)//if more than one records are there
 	{
	  for(i=0;i<document.forms[1].relshipTypeID.length;i++)
	  {
			  if(obj2[i].value.length>0)
			  {
			  	if(number.test(document.forms[1].relationWindowPeriod[i].value)==false)
				 {
					alert('Window Period should be a numeric value.');						
					document.forms[1].relationWindowPeriod[i].select();										
					return false;
				 }//end of if(number.test(document.forms[1].relationWindowPeriod[i].value)==false)
				 
				 /*if(obj2[i].value==0)
				 {
				 	alert('Window Period cannot be zero.');						
					document.forms[1].relationWindowPeriod[i].select();										
					return false;
				 }*/
			  }//end of if(obj2[i].value.length>0)
			  
			 if(obj3[i].checked)
		  	 {
				 if(obj2[i].value.length==0)
		  	 	{
			  		alert('Enter the Window Period for the selected Relationship');						
					obj2[i].focus();										
					return false;
				}
				if(document.forms[1].fromDateGenTypeID[i].value==null || document.forms[1].fromDateGenTypeID[i].value=="")
				{
					alert('Select the Window Period From for the selected Relationship');						
					document.forms[1].fromDateGenTypeID[i].focus();										
					return false;
				}
		  	 }//end of if(obj3[i].checked)
	 }//end of for
	}//end of if
	  
  else
  {
  		if(document.forms[1].relationWindowPeriod.value.length>0)
  		{
	 		if(number.test(document.forms[1].relationWindowPeriod.value)==false)
			 {
				alert('Window Period should be a numeric value.');						
				document.forms[1].days.select();										
				return false;
			 }//end of if(bigdecimal.test(document.forms[1].tariffRequestedAmt.value)==false)
			 
			 /*if(document.forms[1].relationWindowPeriod.value==0)
				 {
				 	alert('Window Period cannot be zero.');						
					document.forms[1].relationWindowPeriod.select();										
					return false;
				 }*/
		 }//end of if(document.forms[1].relationWindowPeriod.value.length>0)
		 
		 if(document.forms[1].relWindowPeriodResYN.checked)
		 {
		 	if(document.forms[1].relationWindowPeriod.value.length==0)
		 	{
	 			alert('Enter the Window Period for the selected Relationship');						
				document.forms[1].relationWindowPeriod.focus();										
				return false;
		 	}//end of if(document.forms[1].relationWindowPeriod.value.length==0)
			if(document.forms[1].fromDateGenTypeID.value ==""||document.forms[1].fromDateGenTypeID.value ==null)
			 {
				alert('Select the Window Period From for the selected Relationship');						
				document.forms[1].fromDateGenTypeID[i].focus();										
				return false;
		 	}//end of if(document.forms[1].fromDateGenTypeID.value ==""||document.forms[1].fromDateGenTypeID.value ==null)
		 }//end of if(document.forms[1].relWindowPeriodResYN.checked)
	}//end of else
	return true;
 }//end of isValidated()