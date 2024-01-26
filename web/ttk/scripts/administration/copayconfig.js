function onClose()
 {
 	if(!TrackChanges()) return false;
 	document.forms[1].mode.value = "doClose";
 	document.forms[1].action = "/CopayConfigurationAction.do";
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
		      document.forms[1].action="/UpdateProdCopayAmtAction.do";
		    }
		    else
		    {
		      document.forms[1].action="/UpdatePolicyCopayAmtAction.do";
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
	  document.forms[1].action="/CopayConfigurationAction.do";
	  document.forms[1].submit();
	 }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 else
	 {
	  document.forms[1].reset();
	 }//end of else   
	
}//end of onReset()
//Changes as Per KOC 1142(Copay Resriction)
function showHideType()
{
    var copaypercentageYN=document.forms[1].copaypercentageYN.value;
    if(copaypercentageYN !='Y')
       {
	 document.getElementById("copaypercentage").style.display="none";
       }//end of if(copayPercentageYN=='YES')
    else if(copaypercentageYN =='Y')
       {
    	document.getElementById("copaypercentage").style.display="";
       }//end of else	
  
}//end showHideType