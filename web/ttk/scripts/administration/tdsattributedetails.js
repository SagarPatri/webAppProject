
//java script for tdsattributedetails.jsp
function onSave()
{   
if (!JS_SecondSubmit) {
if (isValidated() == true) {
	trimForm(document.forms[1]);
	//document.forms[1].elements['applRatePerc'].disabled=false;
	document.forms[1].mode.value="doSave";
	document.forms[1].action="/TDSTaxAttributesAction.do";
	document.forms[1].submit();
	}
    }
}//end of onSave()

function isValidated()
{

	if(isDate(document.forms[1].revDateFrom, "Valid From")==false)
	{
		document.forms[1].revDateFrom.focus();
		return false;					
	}// end of if(document.forms[1].revDateFrom.value.length==0)	
	var baseTaxAmt=0.0;
	var tdsCatTypeID=document.forms[1].tdsCatTypeID.value;
	var tdsCatTypeIDsubstr=tdsCatTypeID.substr(0, 2);
	regexp=/^([0])*\d{0,3}(\.\d{1,2})?$/;
	
		var obj=document.forms[1].elements['applRatePerc'];
		
	    for(i=0;i<obj.length-4;i++)
	    {
		    if(obj[i].value!="" && regexp.test(obj[i].value))
		    {  
		     	baseTaxAmt =baseTaxAmt+parseFloat(obj[i].value);
		     }//end of if(obj[i].value!="" && regexp.test(obj[i].value))
		    if(baseTaxAmt == 0)
		    {
		    	if(tdsCatTypeIDsubstr!="TE")
		    	{
		    		alert('Please enter Base Tax');
		    		obj[i].select();
		    		return false;
		    	}//end of if(tdsCatTypeIDsubstr!="TE")  
    	    }// if(baseTaxAmt == 0)
    	    }//end of for
	    
    	for(i=0;i<obj.length;i++)
	    {
	       if(regexp.test(obj[i].value)==false)
	       	{
	       		alert('TDS attributes should be 4 digits followed by 2 Decimal Places.');
	       		obj[i].select();
	       		return false;
	       	}//if(regexp.test(obj[i].value)==false)
	    }//end of for
	return true;
}//end of function isValidated()

function onReset()
{   
 if(typeof(ClientReset)!= 'undefined' && !ClientReset)
 {
  document.forms[1].mode.value="doReset";
  document.forms[1].action="/TDSTaxAttributesAction.do";
  document.forms[1].submit();
 }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
 else
 {
  document.forms[1].reset();
 }//end of else   
}//end of onReset()

function onClose()
{   
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/TDSTaxAttributesAction.do";
	document.forms[1].submit();	 
}//end of onClose()
