//javascript used in batchgeneral.jsp of claim flow

function onSave(){	
	 trimForm(document.forms[1]);	
	 if(!JS_SecondSubmit)
     {
		 
			var processType = document.forms[1].processType.value;
			if(processType == "DBL"){
				  var networkType=document.forms[1].claimType.value;
				if(networkType=="CNH"){
					 var paymentTo=document.forms[1].paymentTo.value;
					
					if(paymentTo=="PTN"){
						if(document.forms[1].partnerName.value == ""){
							alert("Please select Partner Name");
							document.forms[1].partnerName.focus();
							return false;
							}
						else{
							
							var status=document.forms[1].batchStatus.value;
							var completedYN=document.forms[1].completedYN.value;
							if(completedYN==="Y"){
								alert("Sorry!!! This Claim Batch Is Completed.If You Want Changes Click Override Button");return;
							}
							if((status==="COMP")&&(completedYN!="Y")){
								if(!confirm("Do You Want Complete The Batch?"))return;
							}
						   document.forms[1].mode.value="doSave";
						   document.forms[1].validateYN.value="N";
						   document.forms[1].action="/SaveClaimBatchGeneralAction.do";
						   JS_SecondSubmit=true;
						   document.forms[1].submit();
							
						}
					}
					else if(paymentTo=="PRV"){
						if(document.forms[1].providerID.value == ""){
							alert("Please select Provider Name");
							document.forms[1].providerID.focus();
							return false;
						}
						else{
							
							var status=document.forms[1].batchStatus.value;
							var completedYN=document.forms[1].completedYN.value;
							if(completedYN==="Y"){
								alert("Sorry!!! This Claim Batch Is Completed.If You Want Changes Click Override Button");return;
							}
							if((status==="COMP")&&(completedYN!="Y")){
								if(!confirm("Do You Want Complete The Batch?"))return;
							}
						   document.forms[1].mode.value="doSave";
						   document.forms[1].validateYN.value="N";
						   document.forms[1].action="/SaveClaimBatchGeneralAction.do";
						   JS_SecondSubmit=true;
						   document.forms[1].submit();
							
						}
					}
					 
				}
				else{
					var status=document.forms[1].batchStatus.value;
					var completedYN=document.forms[1].completedYN.value;
					if(completedYN==="Y"){
						alert("Sorry!!! This Claim Batch Is Completed.If You Want Changes Click Override Button");return;
					}
					if((status==="COMP")&&(completedYN!="Y")){
						if(!confirm("Do You Want Complete The Batch?"))return;
					}
				   document.forms[1].mode.value="doSave";
				   document.forms[1].validateYN.value="N";
				   document.forms[1].action="/SaveClaimBatchGeneralAction.do";
				   JS_SecondSubmit=true;
				   document.forms[1].submit();
					
					
				}
				
			}
			
			else{
				var status=document.forms[1].batchStatus.value;
				var completedYN=document.forms[1].completedYN.value;
				if(completedYN==="Y"){
					alert("Sorry!!! This Claim Batch Is Completed.If You Want Changes Click Override Button");return;
				}
				if((status==="COMP")&&(completedYN!="Y")){
					if(!confirm("Do You Want Complete The Batch?"))return;
				}
			   document.forms[1].mode.value="doSave";
			   document.forms[1].validateYN.value="N";
			   document.forms[1].action="/SaveClaimBatchGeneralAction.do";
			   JS_SecondSubmit=true;
			   document.forms[1].submit();
			}
		
	}//end of if(!JS_SecondSubmit)
	
}//end of onSave()
function deleteClaimDetails(claimSeqID1){	
	 if(!JS_SecondSubmit)
	    {
		 var completedYN=document.forms[1].completedYN.value;
			if(completedYN==="Y"){
				alert("Sorry!!! This Claim Batch Is Completed.If You Want Changes Click Override Button");return;
			}
			if(!confirm("Do You Want Delete Claim Details?"))return;
		   document.forms[2].mode.value="deleteClaimDetails";
		   document.forms[2].claimSeqID.value=claimSeqID1;
		   document.forms[2].child.value="DeleteInvoiceNO";
		   document.forms[2].action="/AddClaimDetailsAction.do";
		   JS_SecondSubmit=true;
		   document.forms[2].submit();
		}//end of if(!JS_SecondSubmit)
}//end of deleteClaimDetails()

function override(){
	if(!confirm("Do You Want Override Batch Details?"))return;
	 if(!JS_SecondSubmit)
    {
	   document.forms[1].mode.value="overrideBatchDetails";
	   document.forms[1].overrideYN.value="Y";
	   document.forms[1].child.value="OverrideBatchDetails";
	   document.forms[1].action="/ClaimBatchGeneralAction.do";
	   JS_SecondSubmit=true;
	   document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
	
}//end of override()
function setProviderType(){
	 var completedYN=document.forms[1].completedYN.value;
		if(completedYN==="Y"){
			alert("Sorry!!! This Claim Batch Is Completed.If You Want Changes Click Override Button");return;
		}
	   document.forms[1].mode.value="addBatch";
	   document.forms[1].action="/ClaimBatchGeneralAction.do?initialize=NO";
	   document.forms[1].submit();
}//end of setProviderType()
function setNetWorkType(){
	 var completedYN=document.forms[1].completedYN.value;
		if(completedYN==="Y"){
			alert("Sorry!!! This Claim Batch Is Completed.If You Want Changes Click Override Button");return;
		}
	   document.forms[1].mode.value="setNetWorkType";
	   document.forms[1].action="/ClaimBatchGeneralAction.do";
	   document.forms[1].submit();
}//end of setNetWorkType()
function setProviderID(){
	  /* document.forms[1].providerLisenceNO.value= document.forms[1].providerID.value;*/
	   
	  document.forms[1].mode.value="setProviderID";
	   document.forms[1].action="/ClaimBatchGeneralAction.do";
	   document.forms[1].submit();
	   
}//end of setProviderID()
function addClaimDetails(){	
	 trimForm(document.forms[2]);
	 if(!JS_SecondSubmit){
		 var completedYN=document.forms[1].completedYN.value;
		 
		 var processType = document.forms[1].processType.value;
		
			if(completedYN==="Y"){
				alert("Sorry!!! This Claim Batch Is Completed.If You Want Changes Click Override Button");return;
			}
	   document.forms[2].batchSeqID.value=document.forms[1].batchSeqID.value;
	   document.forms[2].batchReceiveDate.value=document.forms[1].batchReceiveDate.value;
	   document.forms[2].receivedTime.value=document.forms[1].receivedTime.value;
	   document.forms[2].receiveDay.value=document.forms[1].receiveDay.value;
	   document.forms[2].claimType.value=document.forms[1].claimType.value;	  
	   
	   
	   
	   if(processType=="DBL"){
		   var networkType=document.forms[1].claimType.value;
		   
		 if(networkType=="CNH"){
			 var paymentTo=document.forms[1].paymentTo.value;
			   if(paymentTo=="PTN"){
				   document.forms[2].partnerName.value=document.forms[1].partnerName.value;
			   }
			   else if(paymentTo=="PRV"){
				   document.forms[2].providerName.value=document.forms[1].providerName.value;
				   document.forms[2].providerID.value=document.forms[1].providerID.value;
				   document.forms[2].providerLisenceNO.value=document.forms[1].providerLisenceNO.value;
			   }
			   else{
				   document.forms[2].providerName.value=document.forms[1].providerName.value;
				   document.forms[2].providerID.value=document.forms[1].providerID.value;
				   document.forms[2].providerLisenceNO.value=document.forms[1].providerLisenceNO.value;
			   }
		 } 
		  
	   }
	  
	   else{
		   document.forms[2].providerName.value=document.forms[1].providerName.value;
		   document.forms[2].providerID.value=document.forms[1].providerID.value;
		   document.forms[2].providerLisenceNO.value=document.forms[1].providerLisenceNO.value;
	   }
	   
	   
	  
	  
	   document.forms[2].mode.value="addClaimDetails";
	   document.forms[2].action="/SaveAddClaimDetailsAction.do";
	   JS_SecondSubmit=true;
	   document.forms[2].submit();
	}//end of if(!JS_SecondSubmit)
	
}//end of addClaimDetails()
function selectEnrollmentID()
{
	document.forms[2].mode.value="doSelectEnrollmentID";
	document.forms[2].child.value="EnrollmentList";
	document.forms[2].action="/ClaimBatchGeneralAction.do";
	document.forms[2].submit();
}//selectEnrollmentID()
function editClaimSubmissionDetails(rownum){	
	 if(!JS_SecondSubmit){
		 var completedYN=document.forms[1].completedYN.value;
			if(completedYN==="Y"){
				alert("Sorry!!! This Claim Batch Is Completed.If You Want Changes Click Override Button");return;
			}
	   document.forms[2].rownum.value=rownum;
	   document.forms[2].mode.value="editClaimSubmissionDetails";
	   document.forms[2].action="/AddClaimDetailsAction.do";
	   JS_SecondSubmit=true;
	   document.forms[2].submit();
	}//end of if(!JS_SecondSubmit)
	
}//end of editClaimSubmissionDetails()
function editClaimReSubmissionDetails(rownum){	
	 if(!JS_SecondSubmit){
		 var completedYN=document.forms[1].completedYN.value;
			if(completedYN==="Y"){
				alert("Sorry!!! This Claim Batch Is Completed.If You Want Changes Click Override Button");return;
			}
	   document.forms[2].rownum.value=rownum;
	   document.forms[2].mode.value="editClaimReSubmissionDetails";
	   document.forms[2].action="/AddClaimDetailsAction.do";
	   JS_SecondSubmit=true;
	   document.forms[2].submit();
	}//end of if(!JS_SecondSubmit)
	
}//end of editClaimReSubmissionDetails()
function onOverride()
{
	if(TC_GetChangedElements().length>0)
    {
    	alert("Please save the modified data, before Overriding");
    	return false;
    }//end of if(TC_GetChangedElements().length>0)
	document.forms[1].mode.value="doOverride";
	document.forms[1].action="/OverridePreAuthGeneral.do";
	document.forms[1].submit();
}//end of onOverride()


function changenetwork()
{
	
	var networkType=document.forms[1].netWorkType.value;
	var paymentTo=document.forms[1].paymentTo.value;
	var processType = document.forms[1].processType.value;
	if(processType == "DBL"){
	if(networkType == "Y" && paymentTo == "PRV" ){
	 document.getElementById("providerId").style.display="";
	 document.getElementById("partnerId").style.display="none";
	 document.getElementById("partnerIdLabel").style.display="none";
	}else{
		document.getElementById("providerId").style.display="none";
		 document.getElementById("partnerId").style.display="";
		 document.getElementById("partnerIdLabel").style.display="";

	}
	}
	else if(processType == "GBL"){
		if(networkType == "Y")
			{
			 document.getElementById("providerId").style.display="inline";
		
			}else
		{
			document.getElementById("providerId").style.display="none";

		}
	}
	

}


function eclaimValidation()
{
	if(document.getElementById("modeOfClaim").value == "ECL")
		{
		alert("E-Claims cannot be created manually.");
		document.getElementById("modeOfClaim").value = "";
		document.getElementById("modeOfClaim").focus();
		return false;
		}
}




