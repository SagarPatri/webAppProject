//This function is called when close button is clicked to go to members list screen

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/DomiciliaryAction.do";
	document.forms[1].submit();
}

//This function is called when Save button is clicked
function onSave()
{
	if(!JS_SecondSubmit)
	{  
			//added for Policy Deductable - KOC-1277
		objform = document.forms[1];
		var policyDeductableTypeId = document.getElementById("policyDeductableTypeId").value;
		
		var totalFlyDeductableLimit = document.getElementById("totalFlyDeductableLimit").value;
		if(document.forms[1].memberSeqID.length)//if more than one records are there
	 	{
	 		var obj=document.forms[1].elements['checkbox'];
	 		
	 		var obj2=document.forms[1].elements['memberDeductableYN'];
	 		 		
	 		for(i=0;i<document.forms[1].memberSeqID.length;i++)
	  		{
	  			
		  		if(obj[i].checked)
		  		{
		  			
		  			obj2[i].value='Y';
		  		}
		  		else
		 		{
		  			obj2[i].value='N';
		 		}
		 		
	  		}//end of for
	 	}//end of if
  			
	 	if(document.forms[1].checkbox.checked)
		{
		 	document.forms[1].memberDeductableYN.value='Y';
		}
		else
		{
		 	document.forms[1].memberDeductableYN.value='N';
		}
		 //document.forms[1].renewalDays.value=document.forms[1].value;
	 	
	    //end of Policy Deductable
	 	if(policyDeductableTypeId=="PNF")
	 	{
	 		addOverallDeductableAmount();
	 		//addOverAllFamily();
	 	}
	 	if(policyDeductableTypeId=="PFL")
	 	{
	 		if((totalFlyDeductableLimit>0)&&(document.getElementById("overallFamilyCheckYN").checked==false))
	 		{
	 			alert("Please check the checkbox before Saving Record");
	 		
	 		}
	 		else
	 		{
	 			document.forms[1].mode.value="doSubmit";
	 		 	document.forms[1].action="/DomiciliarySubmit.do";
	 		 	JS_SecondSubmit=true;
	 		 	document.forms[1].submit();
	 		}
	 	}
		else
	 	{
			document.forms[1].mode.value="doSubmit";
			document.forms[1].action="/DomiciliarySubmit.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
		}
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

	function onDocumentLoad()
{
	objform = document.forms[1];
    
   	if(document.forms[1].memberSeqID.length)//if more than one records are there
 	{
 		var obj=document.forms[1].elements['memberDeductableYN'];
 		
 		//var obj3=document.forms[1].elements['memberDeductable'];
 		for(i=0;i<document.forms[1].memberSeqID.length;i++)
  		{
	  		if(obj[i].value == "Y")
	  		{
	  			document.forms[1].checkbox[i].checked=true;
	  			//document.forms[1].policyDeductableLimit[i].disabled=false;
	  			//document.forms[1].policyDeductablePercLimit[i].disabled=false;
	  			document.forms[1].policyDeductableLimit[i].readOnly=false;//changed
	 			//document.forms[1].policyDeductablePercLimit[i].readOnly=false;//changed
	  		}
	  		else
	 		{
	  			document.forms[1].checkbox[i].checked=false;
	 			document.forms[1].policyDeductableLimit[i].readOnly=true;//changed
	 			//document.forms[1].policyDeductablePercLimit[i].readOnly=true;//changed
	 		}
	 		//document.forms[1].days[i].value=obj3[i].value;
  		}//end of for
 	}//end of if
 	if(document.forms[1].memberDeductableYN.value =="Y")
	{
	 	document.forms[1].checkbox.checked=true;
	  	//document.forms[1].overallFamilyCheckYN.readOnly=false;
	  	document.forms[1].policyDeductableLimit.readOnly=false;//changed
		//document.forms[1].policyDeductablePercLimit.readOnly=false;//changed
		
	 }
	 else
	 {
	  	document.forms[1].checkbox.checked=false;
	 	//document.forms[1].overallFamilyCheckYN.readOnly=true;
	 	document.forms[1].policyDeductableLimit.readOnly=true;//changed
		//document.forms[1].policyDeductablePercLimit.readOnly=true;//changed
		
	 }
 	 	 	 
 }//end of onDocumentLoad()

//This function is called when Reset button is clicked
function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/DomiciliaryAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else
}

//This function is called on change of Domiciliary Type
function onDomicilaryTypeChange()
{
	document.forms[1].mode.value="doViewDomiciliary";
	document.forms[1].action="/DomiciliaryAction.do";
	document.forms[1].submit();
}

function addOverAllFamily()
{
    trimForm(document.forms[1]);
	var total=0;
	regexp=/^([0])*\d{0,10}(\.\d{1,2})?$/;
	var obj=document.forms[1].elements['domiciliaryLimit'];
	if(obj.length > 0)
	{
		for(i=0;i<obj.length;i++)
	    {
	      if(obj[i].value!="" && regexp.test(obj[i].value))
	      {
	       	total= total + parseFloat(trim(obj[i].value));
	      }
	    }//end of for
	}
	else 
	{
		total = document.forms[1].elements['domiciliaryLimit'].value;
	}		      
    document.forms[1].totalFlyLimit.value=total;
}// end of addOverAllFamily
//added for Policy Deductable -KOC-1277
function addOverallDeductableAmount()
{
    trimForm(document.forms[1]);
	var total=0;
	regexp=/^([0])*\d{0,10}(\.\d{1,2})?$/;
	var obj=document.forms[1].elements['policyDeductableLimit'];
	if(obj.length > 0)
	{
		for(i=0;i<obj.length;i++)
	    {
	      if(obj[i].value!="" && regexp.test(obj[i].value))
	      {
	       	total= total + parseFloat(trim(obj[i].value));
	      }
	    }//end of for
	}
	else 
	{
		total = document.forms[1].elements['policyDeductableLimit'].value;
	}		      
    document.forms[1].totalFlyDeductableLimit.value=total;
    //document.forms[1].totalFlyDeductablePercLimit.value="";
}// end of addOverallDeductableAmount

function onCheck()
{
	objform = document.forms[1];
	var obj=document.forms[1].elements['checkbox'];
	var policyDeductableTypeId = document.getElementById("policyDeductableTypeId").value;
	if(document.forms[1].memberSeqID.length)//if more than one records are there
 	{
		for(i=0;i<document.forms[1].memberSeqID.length;i++)
		{
			if(obj[i].checked)
			{
				if(policyDeductableTypeId=="PNF")
				{
					//document.forms[1].policyDeductableLimit[i].disabled =false;
					//document.forms[1].policyDeductablePercLimit[i].disabled =false;
					document.forms[1].policyDeductableLimit[i].readOnly=false;//changed
			 		//document.forms[1].policyDeductablePercLimit[i].readOnly=false;//changed
			 	
					
				}
			}
			else
		 	{
				if(policyDeductableTypeId=="PNF")
				{
					document.forms[1].policyDeductableLimit[i].readOnly=true;//changed
			 		//document.forms[1].policyDeductablePercLimit[i].readOnly=true;//changed
			 		document.forms[1].policyDeductableLimit[i].value="";
			 		//document.forms[1].policyDeductablePercLimit[i].value="";
				}
				
		 		
		 	}
		}		
 	}//end of if
	
	if(document.forms[1].checkbox.checked)
 	{
		document.forms[1].policyDeductableLimit.readOnly=false;//changed
 		//document.forms[1].policyDeductablePercLimit.readOnly=false;//changed
 	
 	}
 	else
 	{
 		document.forms[1].policyDeductableLimit.readOnly=true;//changed
 		//document.forms[1].policyDeductablePercLimit.readOnly=true;//changed
 		
 	} 
	
	
}//end of onCheck