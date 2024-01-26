//java script for the hospital general screen in the empanelment of hospital flow
//function to trim the text fields
function toTrim() 
{		
		document.forms[1].docDispDate.value=trim(document.forms[1].docDispDate.value);
  		document.forms[1].mouRcvdDate.value=trim(document.forms[1].mouRcvdDate.value);
  		document.forms[1].mouSignDate.value=trim(document.forms[1].mouSignDate.value);
  		document.forms[1].mouSentDate.value=trim(document.forms[1].mouSentDate.value);
		document.forms[1].tariffRcvdDate.value=trim(document.forms[1].tariffRcvdDate.value);
		document.forms[1].infoRcvdDate.value=trim(document.forms[1].infoRcvdDate.value);				
  		document.forms[1].name.value=trim(document.forms[1].name.value.toUpperCase());
  		document.forms[1].address1.value=trim(document.forms[1].address1.value);
  		document.forms[1].address2.value=trim(document.forms[1].address2.value);
  		document.forms[1].address3.value=trim(document.forms[1].address3.value);
  		document.forms[1].pinCode.value=trim(document.forms[1].pinCode.value);
  		document.forms[1].stdCode.value=trim(document.forms[1].stdCode.value);
  		document.forms[1].creditPeriod.value=trim(document.forms[1].creditPeriod.value);
  		document.forms[1].intPcnt.value=trim(document.forms[1].intPcnt.value);
  		document.forms[1].regNo.value=trim(document.forms[1].regNo.value);
  		document.forms[1].regAuth.value=trim(document.forms[1].regAuth.value);
  		document.forms[1].pan.value=trim(document.forms[1].pan.value);
  		document.forms[1].irdaNumber.value=trim(document.forms[1].irdaNumber.value);  		
		document.forms[1].landmark.value=trim(document.forms[1].landmark.value);
		document.forms[1].emailID.value=trim(document.forms[1].emailID.value);
		document.forms[1].remarks.value=trim(document.forms[1].remarks.value);
		//document.forms[1].intPcnt.value=eval(document.forms[1].intPcnt.value);
}//end of toTrim() 

//function to submit the screen
function onUserSubmit()
{    	
    	if(document.forms[1].intExtApp.checked) 
    		document.forms[1].intExtAppl.value="Y";    		
    	else
    		document.forms[1].intExtAppl.value="N";    		
  		
  		if(document.forms[1].hasInternetConn.checked) 
  			document.forms[1].hasInternetConnc.value="Y";	
  		else
  			document.forms[1].hasInternetConnc.value="N";	  		
  		toTrim();		
    	document.forms[1].mode.value="UpdateHospital";
    	document.forms[1].action="/AddHospitalSearchAction.do";
    	document.forms[1].submit();
}//end of onUserSubmit()
