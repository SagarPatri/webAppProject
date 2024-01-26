
//Created as per Change request KOC1140(SumInsuredRestriction)
function onClose()
 {
 	if(!TrackChanges()) return false;
 	document.forms[1].mode.value = "doClose";
 	document.forms[1].action = "/SumInsuredConfigurationAction.do";
 	document.forms[1].submit();	
 }//end of onClose() 

function onSave()
{
		trimForm(document.forms[1]);
		if(!JS_SecondSubmit)
		{
		    document.forms[1].mode.value="doSave";
		    if(document.forms[0].sublink.value == "Products")
		    {
		      document.forms[1].action="/UpdateProdSumInsuredRestrictAction.do";
		    }
		    else
		    {
		      document.forms[1].action="/UpdatePolicySumInsuredRestrictAction.do";
		    }
		    JS_SecondSubmit=true
		   document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
}//end of onSave()

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 {
	  document.forms[1].mode.value="doReset";
	  document.forms[1].action="/SumInsuredConfigurationAction.do";
	  document.forms[1].submit();
	 }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 else
	 {
	  document.forms[1].reset();
	 }//end of else   
	
}//end of onReset()
function showHideType()
{   
var suminsuredrestrictionYN=document.forms[1].suminsuredrestrictionYN.value;

 if(suminsuredrestrictionYN !='Y')
    {
    	 document.getElementById("suminsuredconfiguaration").style.display="none";
    	 document.getElementById("suminsuredconfiguarationage").style.display="none";
    	
    }//end of if(copayPercentageYN=='YES')
    else if(suminsuredrestrictionYN=='Y')
    {
    	
    	document.getElementById("suminsuredconfiguaration").style.display="";
    	document.getElementById("suminsuredconfiguarationage").style.display="";
    }//end of else	
  
}

function showHideType1()
{   
var suminsuredrestrictionYN=document.forms[1].suminsuredrestrictionYN.value;

 if(suminsuredrestrictionYN !='Y')
    {
    	 document.getElementById("suminsuredconfiguarationage").style.display="none";
    	
    }//end of if(copayPercentageYN=='YES')
    else if(suminsuredrestrictionYN=='Y')
    {
    	
    	document.getElementById("suminsuredconfiguarationage").style.display="";
    }//end of else	
  
}