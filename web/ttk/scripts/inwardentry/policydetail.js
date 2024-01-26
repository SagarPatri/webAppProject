//java script for the policy details screen in inwardentry module.


function onGetPreviousPolicyCategory()
{
	var countryCode = document.getElementById("prevPolicyNbr").value ;
	 if(countryCode != "")
		 {
		   document.getElementById("vatTrnLabel").style.display="";
		 }
	 else{
			 document.getElementById("vatTrnLabel").style.display="none";
	 }
	
	   if(document.getElementById("prevPolicyNbr").value!=null && document.getElementById("prevPolicyNbr").value.length>1 && document.getElementById("insuranceSeqID").value!=null)
		   {
           document.forms[1].mode.value="doGetPreviousPolicyCategory";
           document.forms[1].action="/SavePolicyDetailActionTest.do";
		   document.forms[1].submit();
		  
		   }
	 

}

// function to save policy details.
function doSave()
{
	trimForm(document.forms[1]);
	if((document.forms[1].batchType.value == "END")&&(document.forms[1].endorseGenTypeID.value != ""))
	{
		document.forms[1].prevPolicyNbr.value="";		
	}	
	if(!JS_SecondSubmit){
		
		var prevPolicyNbr = document.getElementById("prevPolicyNbr").value; 
		var productChangeYN = document.getElementById("productChangeYNId").value;
		if(prevPolicyNbr !="" && productChangeYN ==""){
			alert("Product Change is Mandatory");
			return;
		}
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/SavePolicyDetailAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
} //end of doSave()

// function to reset the form data.
function doReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/PolicyDetailAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else 
}//end of doReset()



// function to close the policy details screen.
function doClose()
{
	if(!TrackChanges()) return false;
	
	document.forms[1].mode.value="doClose";
	document.forms[1].child.value="";
	document.forms[1].action="/BatchPolicyAction.do";
	document.forms[1].submit();
}//end of doClose()

//function to Change the Policy Type.
function doSelectPolicyType()
{
	document.forms[1].mode.value="doChangePolicyType";
	document.forms[1].action="/PolicyDetailAction.do";
	document.forms[1].submit();
}//end of doSelectPolicyType()

//function to Change the corporate.
function changeCorporate()
{
	document.forms[1].mode.value="doChangeCorporate";
	document.forms[1].child.value="GroupList";
	document.forms[1].action="/PolicyDetailAction.do";
	document.forms[1].submit();
}//end of changeCorporate()

//fuction for showCustEndorsNo
function showCustEndorsNo()
{
	if(document.forms[1].endorseGenTypeID.value=="EIN")
	{
		document.forms[1].endorsementNbr.value="";
		document.forms[1].endorsementNbr.readOnly=false;
		document.forms[1].endorsementNbr.value=document.forms[1].hidEndorsementNbrINS.value;
	}
	else if(document.forms[1].endorseGenTypeID.value=="ETT")
	{
		document.forms[1].endorsementNbr.value="";
		document.forms[1].endorsementNbr.readOnly=true;
		document.forms[1].endorsementNbr.value=document.forms[1].hidEndorsementNbrTTK.value;
	}	
	else
	{
		document.forms[1].endorsementNbr.value="";
		document.forms[1].endorsementNbr.readOnly=true;		
	}
}//function showCustEndorsNo()