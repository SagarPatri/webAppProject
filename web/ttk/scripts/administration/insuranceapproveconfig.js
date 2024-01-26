//bajaj


function onClose()
 {
 	if(!TrackChanges()) return false;
 	document.forms[1].mode.value = "doClose";
 	document.forms[1].action = "/InsuranceApproveConfiguration.do";
 	document.forms[1].submit();	
 }//end of onClose() 
function onSave()
{
		trimForm(document.forms[1]);
		
		var insPatYN=document.getElementById("insPatYN").value;
		var insClmYN=document.getElementById("insClmYN").value;
		var insClmRemYN=document.getElementById("insClmRemYN").value;
		
		
	if((document.forms[1].rejectionYN.value!="N")||(document.forms[1].allowedPatYN.value!="N"))
	{
		var timeHrs = parseInt(document.forms[1].timeInHrs.value);
		var timeMins = parseInt(document.forms[1].timeInMins.value);
		if(!isNaN(timeHrs))
		{
			if(timeHrs>24)
			{
				alert("Please enter valid Hours");
				return false;
			}
		}//end of if(!isNaN(timeHrs))
		if(!isNaN(timeMins))
		{
			if(timeMins>60)
		    {
			alert("Please enter valid Minutes");
			return false;
		    }//end of if
		}// end of if if(!isNaN(timeMins))
	}//end of 	if(document.forms[1].rejectionYN.value!="N")
	
	
	
	
		
	if((document.forms[1].rejectionClmYN.value!="N")||(document.forms[1].allowedClmYN.value!="N"))
	{
		
		var timeInHrsClm = parseInt(document.forms[1].timeInHrsClm.value);
		var timeInMinsClm = parseInt(document.forms[1].timeInMinsClm.value);
		if(!isNaN(timeInHrsClm))
		{
			if(timeInHrsClm>24)
			{
				alert("Please enter valid Hours");
				return false;
			}
		}//end of if(!isNaN(timeHrs))
		if(!isNaN(timeInMinsClm))
		{
			if(timeInMinsClm>60)
		    {
			alert("Please enter valid Minutes");
			return false;
		    }//end of if
		}// end of if if(!isNaN(timeMins))
	}//end of 	if(document.forms[1].rejectionYN.value!="N")
	
	
		
	if((document.forms[1].rejectionClmRemYN.value !="N")||(document.forms[1].allowedClmRemYN.value !="N"))
	{
		var timeInHrsClm = parseInt(document.forms[1].timeInHrsClm.value);
		var timeInMinsClm = parseInt(document.forms[1].timeInMinsClm.value);
		if(!isNaN(timeInHrsClm))
		{
			if(timeInHrsClm>24)
			{
				alert("Please enter valid Hours");
				return false;
			}
		}//end of if(!isNaN(timeHrs))
		if(!isNaN(timeInMinsClm))
		{
			if(timeInMinsClm>60)
		    {
			alert("Please enter valid Minutes");
			return false;
		    }//end of if
		}// end of if if(!isNaN(timeMins))
	}//end of 	if(document.forms[1].rejectionYN.value!="N")
	
       
	if(!JS_SecondSubmit)
		{
		    document.forms[1].mode.value="doSave";
		    if(document.forms[0].sublink.value == "Products")
		    {
		      document.forms[1].action="/UpdateProdInsuranceApproveConfiguration.do";
		    }
		    else
		    {
		      document.forms[1].action="/UpdatePolicyInsuranceApproveConfiguration.do";
		    }
		    JS_SecondSubmit=true;
		   document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
}//end of onSave()

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)	 {
	  document.forms[1].mode.value="doReset";
	  document.forms[1].action="/InsuranceApproveConfiguration.do";
	  document.forms[1].submit();
	 }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 else {
	  document.forms[1].reset();
	 }//end of else   
}//end of onReset()
function showDataPat(){
	var allowedYN=document.forms[1].allowedPatYN.value;
	var rejectionYN=document.forms[1].rejectionYN.value;
	if(allowedYN=="Y")	{
	document.getElementById("insuranceapproveconfigYNpat").style.display="";
	document.forms[1].elements['patOperator'].value = "GT";
	document.forms[1].elements['patApproveAmountLimit'].value = "";
	document.getElementById("notificationYN").style.display="";
    document.getElementById("notificationFlag").value="MAIL";
    document.getElementById("notificationFlag").checked=true;
    document.getElementById("timeInHrsandMin").style.display="";
	//document.getElementById("patApproveAmountLimit").style.display="";
	}// end of if(allowedYN=="Y")
	else if((allowedYN =="N") && (rejectionYN =="N"))
		{
		document.getElementById("notificationYN").style.display="none";
		document.getElementById("notificationFlag").value="";
		document.getElementById("insuranceapproveconfigYNpat").style.display="none";
		document.forms[1].elements['patOperator'].value = "";
		document.forms[1].timeInHrs.value="";
		document.forms[1].timeInMins.value="";
		document.getElementById("timeInHrsandMin").style.display="none";
		}
	else{	
	document.getElementById("insuranceapproveconfigYNpat").style.display="none";
	document.forms[1].elements['patOperator'].value = "";
	document.forms[1].elements['patApproveAmountLimit'].value = "";
	//document.getElementById("insurancerejectionYN").style.display="none";
	   }//end of else if(allowedYN=="Y")
}//end of showData()


function showDataClm(){
	var allowedClmYN=document.forms[1].allowedClmYN.value;
	var rejectionClmYN=document.forms[1].rejectionClmYN.value;
	if(allowedClmYN=="Y")	{
	document.getElementById("insuranceapproveconfigYNclm").style.display="";
	document.forms[1].elements['clmOperator'].value = "GT";
	document.forms[1].elements['clmApproveAmountLimit'].value = "";
	document.getElementById("notificationClmYN").style.display="";
    document.getElementById("notificationClmFlag").value="MAIL";
    document.getElementById("notificationClmFlag").checked=true;
    document.getElementById("timeInHrsandMinClm").style.display="";
	//document.getElementById("insurancerejectionYN").style.display="";
	}// end of if(allowedYN=="Y")
	else if((allowedClmYN =="N") && (rejectionClmYN =="N"))
	{
		document.getElementById("notificationClmYN").style.display="none";
		document.getElementById("notificationClmFlag").value="";
		document.getElementById("insuranceapproveconfigYNclm").style.display="none";
		document.forms[1].elements['clmOperator'].value = "";
		document.forms[1].elements['clmApproveAmountLimit'].value = "";
		document.forms[1].timeInHrsClm.value="";
		document.forms[1].timeInMinsClm.value="";
		document.getElementById("timeInHrsandMinClm").style.display="none";
	}
	else{			
	document.getElementById("insuranceapproveconfigYNclm").style.display="none";
	document.forms[1].elements['clmOperator'].value = "";
	document.forms[1].elements['clmApproveAmountLimit'].value = "";
	   }//end of else if(allowedYN=="Y")
}//end of showData()

function showDataClmRem(){
	var allowedClmRemYN=document.forms[1].allowedClmRemYN.value;
	var rejectionClmRemYN=document.forms[1].rejectionClmRemYN.value;
	if(allowedClmRemYN=="Y")	{
	document.getElementById("insuranceapproveconfigYNclmRem").style.display="";
	document.forms[1].elements['clmOperatorRem'].value = "GT";
	document.forms[1].elements['clmApproveAmountLimitRem'].value = "";
	document.getElementById("notificationClmRemYN").style.display="";
    document.getElementById("notificationClmRemFlag").value="MAIL";
    document.getElementById("notificationClmRemFlag").checked=true;
    document.getElementById("timeInHrsandMinClm").style.display="";
	//document.getElementById("insurancerejectionYN").style.display="";
	}// end of if(allowedYN=="Y")
	else if((allowedClmRemYN =="N") && (rejectionClmRemYN =="N"))
	{
		document.getElementById("notificationClmRemYN").style.display="none";
		document.getElementById("notificationClmRemFlag").value="";
		document.getElementById("insuranceapproveconfigYNclmRem").style.display="none";
		document.forms[1].elements['clmOperatorRem'].value = "";
		document.forms[1].elements['clmApproveAmountLimitRem'].value = "";
		document.forms[1].timeInHrsClm.value="";
		document.forms[1].timeInMinsClm.value="";
		document.getElementById("timeInHrsandMinClm").style.display="none";
	}
	else{			
	document.getElementById("insuranceapproveconfigYNclmRem").style.display="none";
	document.forms[1].elements['clmOperatorRem'].value = "";
	document.forms[1].elements['clmApproveAmountLimitRem'].value = "";
	   }//end of else if(allowedYN=="Y")
}//end of showData()


function showNoticationData(){
	var rejectionYN=document.forms[1].rejectionYN.value;
	var allowedYN=document.forms[1].allowedPatYN.value;
    if(rejectionYN=="Y") {
	    document.getElementById("notificationYN").style.display="";
	    document.getElementById("notificationFlag").value="MAIL";
	    document.getElementById("notificationFlag").checked=true;
	    document.getElementById("timeInHrsandMin").style.display="";
	 }//end if(rejectionYN=="Y")
	else if((allowedYN =="N") && (rejectionYN =="N"))
	{
	document.getElementById("notificationYN").style.display="none";
	document.getElementById("notificationFlag").value="";
	document.forms[1].timeInHrs.value="";
	document.forms[1].timeInMins.value="";
	document.getElementById("timeInHrsandMin").style.display="none";
	}
} //end of showNoticationData()
//denial process
function showNoticationDataClm(){
	var rejectionClmYN=document.forms[1].rejectionClmYN.value;
	var allowedClmYN=document.forms[1].allowedClmYN.value;
	if(rejectionClmYN=="Y") {
	    document.getElementById("notificationClmYN").style.display="";
	    document.getElementById("notificationClmFlag").value="MAIL";
	    document.getElementById("notificationClmFlag").checked=true;
	    document.getElementById("timeInHrsandMinClm").style.display="";
	 }//end if(rejectionYN=="Y")
	else if((allowedClmYN =="N") && (rejectionClmYN =="N"))
	{			
	document.getElementById("notificationClmYN").style.display="none";
	document.getElementById("notificationClmFlag").value="";
	document.forms[1].timeInHrsClm.value="";
	document.forms[1].timeInMinsClm.value="";
	document.getElementById("timeInHrsandMinClm").style.display="none";
		}//end of else if(rejectionClmYN=="Y")
} //end of showNoticationData()

function showNoticationDataClmRem(){
	var rejectionClmRemYN=document.forms[1].rejectionClmRemYN.value;
	var allowedClmRemYN=document.forms[1].allowedClmRemYN.value;
	if(rejectionClmRemYN=="Y") {
	    document.getElementById("notificationClmRemYN").style.display="";
	    document.getElementById("notificationClmRemFlag").value="MAIL";
	    document.getElementById("notificationClmRemFlag").checked=true;
	    document.getElementById("timeInHrsandMinClm").style.display="";
	 }//end if(rejectionYN=="Y")
	else if((allowedClmRemYN =="N") && (rejectionClmRemYN =="N"))	
		{
	document.getElementById("notificationClmRemYN").style.display="none";
	document.getElementById("notificationClmRemFlag").value="";
	document.forms[1].timeInHrsClm.value="";
	document.forms[1].timeInMinsClm.value="";
	document.getElementById("timeInHrsandMinClm").style.display="none";
		}//end of else if(rejectionClmYN=="Y")
} //end of showNoticationData()
//denial process
//added for bajaj enhancement1
function showHidePreauth()

{
var a=document.getElementById('insPatYN');
 if (a.checked  == true ) {
	 document.getElementById("allowedPatYNid").style.display="";	
	 document.getElementById("allowedPatRejYNid").style.display="";
	 document.getElementById("insPatYN").value= "Y";
 }
 else{
	 document.getElementById("allowedPatYNid").style.display="none";
	 document.getElementById("allowedPatRejYNid").style.display="none";
	 document.getElementById("notificationYN").style.display="none";
	 document.getElementById("insuranceapproveconfigYNpat").style.display="none";
	 document.getElementById("timeInHrsandMin").style.display="none";
	 document.getElementById("insPatYN").value= "N";
	 document.forms[1].elements['allowedPatYN'].value = "N";
	 document.forms[1].elements['rejectionYN'].value = "N";
	 document.forms[1].elements['patOperator'].value = "GT";
	 document.forms[1].elements['patApproveAmountLimit'].value = "";
	 document.getElementById("notificationFlag").value="MAIL";
	 document.forms[1].timeInHrs.value="";
	 document.forms[1].timeInMins.value="";

 }
	 	 
 }

function showHideClaim()

{
var a=document.getElementById('insClmYN');
var b=document.getElementById('insClmRemYN');



 if (a.checked  == true ) {
	 document.getElementById("allowedClmYNid").style.display="";	
	 document.getElementById("allowedClmRejYNid").style.display="";	
	 document.getElementById("insClmYN").value = "Y";
 }
 else{
	 if ((a.checked || b.checked) == true ) {
		 document.getElementById("timeInHrsandMinClm").style.display="";
	 }
	 else if ((a.checked && b.checked) == false ) {
		 document.getElementById("timeInHrsandMinClm").style.display="none";
		 document.forms[1].timeInHrsClm.value="";
			document.forms[1].timeInMinsClm.value="";
	 }
	 document.getElementById("allowedClmYNid").style.display="none";
	 document.getElementById("allowedClmRejYNid").style.display="none";
	 document.getElementById("notificationClmYN").style.display="none";
	 document.getElementById("insuranceapproveconfigYNclm").style.display="none";
	 document.getElementById("insClmYN").value = "N";
	 document.forms[1].elements['allowedClmYN'].value = "N";
	 document.forms[1].elements['rejectionClmYN'].value = "N";
	 document.forms[1].elements['clmOperator'].value = "GT";
	 document.forms[1].elements['clmApproveAmountLimit'].value = "";
	 document.getElementById("notificationClmFlag").value="MAIL";
	 
 }
	 	 
 }
function showHideClaimRem()

{
var a=document.getElementById('insClmRemYN');
var b=document.getElementById('insClmYN');

 if (a.checked  == true ) {
	 document.getElementById("allowedClmRemYNid").style.display="";	
	 document.getElementById("allowedClmRejRemYNid").style.display="";	
	 document.getElementById("insClmRemYN").value = "Y";
 }
 else{
	 
	 if ((a.checked || b.checked) == true ) {
		 document.getElementById("timeInHrsandMinClm").style.display="";
	 }
	 else if ((a.checked && b.checked) == false ) {
		 document.getElementById("timeInHrsandMinClm").style.display="none";
		 document.forms[1].timeInHrsClm.value="";
			document.forms[1].timeInMinsClm.value="";
	 }
	
	 document.getElementById("allowedClmRemYNid").style.display="none";
	 document.getElementById("allowedClmRejRemYNid").style.display="none";
	 document.getElementById("notificationClmRemYN").style.display="none";
	 document.getElementById("insuranceapproveconfigYNclmRem").style.display="none";
	 document.forms[1].elements['allowedClmRemYN'].value = "N";
	 document.getElementById("insClmRemYN").value = "N";
	 document.forms[1].elements['rejectionClmRemYN'].value = "N";
	 document.forms[1].elements['clmOperatorRem'].value = "GT";
	 document.forms[1].elements['clmApproveAmountLimitRem'].value = "";
	 document.getElementById("notificationClmRemFlag").value="MAIL";
	
 }
	 	 
 }
/*function showHidePreauth()

{
var a=document.getElementById('insPatYN');
var b=document.getElementById('insClmYN');

 if (a.checked  == true ) {
	 document.getElementById("allowedPatYNid").style.display="";	
 }
 else{
	
	 document.getElementById("allowedPatYNid").style.display="none";
	 document.getElementById("insuranceapproveconfigYNpat").style.display="none";
	 document.forms[1].elements['allowedPatYN'].value = "N";
	 document.forms[1].elements['rejectionYN'].value = "N";
	 document.forms[1].elements['patOperator'].value = "GT";
	 document.forms[1].elements['patApproveAmountLimit'].value = "";
	 
	
 }
 if ((a.checked || b.checked) == true ) {
	 document.forms[1].elements['rejectionYN'].value = "Y";
	 document.forms[1].elements['rejectionValue'].value = "Y";
	 document.getElementById("notificationYN").style.display="";
	 document.getElementById("timeInHrsandMin").style.display="";
	 	 
 }
 
}
function showHideClaim()

{
var a=document.getElementById('insPatYN');
var b=document.getElementById('insClmYN');


if (b.checked  == true ) {
	 document.getElementById("allowedClmYNid").style.display="";	
} else{	
	 document.getElementById("allowedClmYNid").style.display="none";
	 document.getElementById("insuranceapproveconfigYNclm").style.display="none";
	 document.forms[1].elements['allowedClmYN'].value = "N";
	 document.forms[1].elements['rejectionYN'].value = "N";
	 document.forms[1].elements['clmOperator'].value = "GT";
	 document.forms[1].elements['clmApproveAmountLimit'].value = "";
	 	
 }
if ((a.checked || b.checked) == true ) {
	 document.forms[1].elements['rejectionYN'].value = "Y";
	 document.getElementById("notificationYN").style.display="";
	 document.getElementById("timeInHrsandMin").style.display="";
 }
}
*/

function stopPreauth()
{
	if(document.forms[1].insPat.value=="Y")  
		{
			document.forms[1].insPatYN.checked=true;
		}
		else
		{
			document.forms[1].insPatYN.checked=false;
		}
	
}//end of stopPreauth()

function stopClaim()
{
		if(document.forms[1].insClm.value=="Y")  
		{
			document.forms[1].insClmYN.checked=true;
		}
		else
		{
			document.forms[1].insClmYN.checked=false;
		}
	
}//end of stopPreAuthClaim()

//end added for bajaj enhancement1