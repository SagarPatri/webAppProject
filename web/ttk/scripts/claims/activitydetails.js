function getActivityCodeDetails(){
	if( document.forms[1].sCategory.value==="" ||document.forms[1].sCategory.value==null)
	{
		alert("Please Select Category");
	}
	else
		{
	var pattern =/^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
	
	var varActivityCode=document.forms[1].activityCode.value;
	var varActivityStartDate=document.forms[1].activityStartDate.value;
	if(varActivityCode==null||varActivityCode===""){
        alert("Enter Activity Code");
        return false;
		}else  if (varActivityStartDate == null || varActivityStartDate === "" || !pattern.test(varActivityStartDate)){
	        alert("Date format should be (dd/mm/yyyy)");
	        return false;
	    }
	
    //if(!confirm("You Want Validate ActivityCode !"))return;
	document.forms[1].mode.value = "selectActivityCode";
    document.forms[1].action ="/ClaimActivityDetailsAction.do?codeFlag=remember";
    document.forms[1].submit();
		}
}//getActivityCodeDetails
function closeActivities() {   
	document.forms[1].mode.value = "doGeneral";
	document.forms[1].reforward.value = "close";
    document.forms[1].action ="/ClaimActivityDetailsAction.do";
    document.forms[1].submit();
}
function resetActivityDetails() {   
	document.forms[1].mode.value = "resetActivityDetails";
    document.forms[1].action ="/ClaimActivityDetailsAction.do";
    document.forms[1].submit();
}

function saveActivityDetails() {   
	if(!JS_SecondSubmit){
		
		var quantity=document.forms[1].quantity.value;
		var approvedQuantity=document.forms[1].approvedQuantity.value;
		if(parseFloat(approvedQuantity) > parseFloat(quantity))
		{
			alert("Approved Quantity should be less then or equal to Quantity.");
			document.forms[1].approvedQuantity.value="";
			document.forms[1].approvedQuantity.focus();
			return false;
		}
		
		var selObj=document.forms[1].denialCode;	
		if(selObj.value!=null&&selObj.value!=''&&selObj.value.length>0)
		document.forms[1].denialDescription.value = selObj.options[selObj.selectedIndex].text;
			
		
		var claimType = document.forms[1].claimType.value;
		var processType = document.forms[1].processType.value;
		
		
			if(processType !="DBL" && claimType !="CTM"){
			
			var vatPercent=document.forms[1].vatPercent.value;
			
		 	if(vatPercent>100){
		 		
		 		alert("Activity VAT(%) should not be greater than 100 ");
		 		document.getElementById("vatPercent").value="";
		 		document.getElementById("vatAed").value="";
		 		document.getElementById("vatAddedReqAmnt").value="";
		 		document.getElementById("vatPercent").focus();
			    	 return false;
		 	} 
		
		 	var appliedVatPercent=document.forms[1].appliedVatPercent.value;
			
			 	if(appliedVatPercent>100){
			 		
			 		alert("Applied VAT(%) should not be greater than 100 ");
			 		document.getElementById("appliedVatPercent").value="";
			 		document.getElementById("appliedVatPercent").focus();
				    	 return false;
			 	}
		}
		
    document.forms[1].mode.value = "saveActivityDetails";
    document.forms[1].action ="/SaveClaimActivityDetails.do";
    JS_SecondSubmit=true;
    document.forms[1].submit();
	}
}

function calculateNetAmount(upObj){
	
	var re = /^[0-9]*\.*[0-9]*$/;	
	var objValue=upObj.value;	
	var unitPrice=document.forms[1].unitPrice.value;
	var activityTypeId=document.forms[1].activityTypeId.value;
	var activityCodeSeqId=document.forms[1].activityCodeSeqId.value;
	if(upObj.id==="UnitType"){
		
		if(activityCodeSeqId.length<1||activityTypeId!=5)return;
		var activityCode=document.forms[1].activityCode.value;
		var activityStartDate=document.forms[1].activityStartDate.value;
		var unitType=document.forms[1].unitType.value;
		var providerSeqID=document.forms[1].providerSeqID.value;
		var claimSeqID=document.forms[1].claimSeqID.value;
		$(document).ready(function () {
			$.ajax({
            url :"/asynchronAction.do?mode=getUnitTypePrice&activityCode="+activityCode+"&activityStartDate="+activityStartDate+"&unitType="+unitType+"&providerSeqID="+providerSeqID+"&type=CLM"+"&seqID="+claimSeqID,
            dataType:"text",
			type:"POST",
			async:false,
		    context:document.body,
            success : function(responseData) {            	
            	var rsData=responseData;
            	var rsDatas=rsData.split("@");            	
            	document.forms[1].unitPrice.value=parseFloat(rsDatas[0]).toFixed(2);
            	document.forms[1].discountPerUnit.value=parseFloat(rsDatas[1]).toFixed(2);            	
            	unitPrice=document.forms[1].unitPrice.value;
              }//success data
		 });//$.ajax(	
		});
	}else if(objValue.length>=1){
		if(!re.test(objValue)){
			alert("Please Enter Valid Data");
			upObj.value="";
			upObj.focus();
		}
	}  
	//document.forms[1].approvedAmount.value="";
	//document.forms[1].allowedAmount.value="";
	
	 var copayAmt=document.forms[1].copay.value;
//	var coinsuranceAmt=document.forms[1].coinsurance.value;
	var deductibleAmt=document.forms[1].deductible.value;
//	var outOfPocketAmt=document.forms[1].outOfPocket.value;
	var quantity=document.forms[1].quantity.value;
	
	
	/*var additionalDisallowances=document.forms[1].additionalDisallowances.value;
	additionalDisallowances=(additionalDisallowances==null||additionalDisallowances===""||!re.test(additionalDisallowances))?0:additionalDisallowances;*/
	
	document.getElementById("approvedQuantity").value=quantity;
	
	quantity=(quantity==null||quantity===""||!re.test(quantity)||quantity==="1")?1:quantity;
	
	unitPrice=(unitPrice==null||unitPrice===""||!re.test(unitPrice))?0:unitPrice;
	//unitPrice=unitPrice*document.getElementById("conversionRate").value;
	//document.forms[1].convertedUnitPrice.value=unitPrice;
	
	var conversionRate  = document.getElementById("conversionRate").value;
	
	conversionRate=(conversionRate==null||conversionRate===""||!re.test(conversionRate)||conversionRate==="1")?1:conversionRate;
	unitPrice=unitPrice*conversionRate;
	document.forms[1].convertedUnitPrice.value=unitPrice.toFixed(2);
	
	document.forms[1].grossAmount.value=parseFloat(parseFloat(unitPrice)*parseFloat(quantity)).toFixed(2);
	 var haad_YN=document.getElementById("haad_YN").value;
	 var varTariffYN=document.forms[1].tariffYN.value;
	 var overrideYN=document.getElementById("overrideYN");

	 if(varTariffYN==="Y"&&overrideYN===null){
		 var discountPerUnit=document.forms[1].discountPerUnit.value;
		 var discountAmt=document.forms[1].discountPerUnit.value;
			discountAmt=(discountAmt==null||discountAmt===""||!re.test(discountAmt))?0:discountAmt;
			discountPerUnit=(discountPerUnit==null||discountPerUnit===""||!re.test(discountPerUnit))?0:discountPerUnit;
			if(haad_YN==="Y") document.forms[1].discount.value=parseFloat(parseFloat(discountPerUnit)*parseFloat(quantity)).toFixed(2);
			else document.forms[1].discount.value=parseFloat(parseFloat(discountAmt)*parseFloat(quantity)).toFixed(2);
		 }else{
			 var discountPerUnit=document.forms[1].discountPerUnit.value;
			 var discountAmt=document.forms[1].discount.value;
				discountAmt=(discountAmt==null||discountAmt===""||!re.test(discountAmt))?0:discountAmt;
				discountPerUnit=(discountPerUnit==null||discountPerUnit===""||!re.test(discountPerUnit))?0:discountPerUnit;
				if(haad_YN==="Y") document.forms[1].discount.value=parseFloat(parseFloat(discountPerUnit)*parseFloat(quantity)).toFixed(2);
				else document.forms[1].discount.value=parseFloat(discountAmt).toFixed(2);
			 }	
	
	if(parseFloat(document.forms[1].discount.value)>parseFloat(document.forms[1].grossAmount.value)){		
		alert("Discount Amount Should Not Greater Than Gross Amount");
		document.forms[1].copay.value='0';
//		document.forms[1].coinsurance.value='0';
		document.forms[1].deductible.value='0';
//		document.forms[1].outOfPocket.value='0';
		document.forms[1].patientShare.value='0';
		document.forms[1].discount.value='0';
		document.forms[1].discountedGross.value='0';
		document.forms[1].netAmount.value='0';
		document.forms[1].grossAmount.value='0';  
		//document.forms[1].allowedAmount.value=''; 
		//document.forms[1].approvedAmount.value='';
		return ;
		}
	
	/*var enableucr=document.forms[1].enableucr.value;
	if(enableucr=="Y")
	{
		additionalDisallowances=0;
	}*/
	
	// Calculating discountedGross amount.
	document.forms[1].discountedGross.value=parseFloat(parseFloat(document.forms[1].grossAmount.value)-parseFloat(document.forms[1].discount.value).toFixed(3));
		
	copayAmt=(copayAmt==null||copayAmt===""||!re.test(copayAmt))?0:copayAmt;
//	coinsuranceAmt=(coinsuranceAmt==null||coinsuranceAmt===""||!re.test(coinsuranceAmt))?0:coinsuranceAmt;
	deductibleAmt=(deductibleAmt==null||deductibleAmt===""||!re.test(deductibleAmt))?0:deductibleAmt;
//	outOfPocketAmt=(outOfPocketAmt==null||outOfPocketAmt===""||!re.test(outOfPocketAmt))?0:outOfPocketAmt;
	
//	var patientShareAmt=parseFloat(copayAmt)+parseFloat(coinsuranceAmt)+parseFloat(deductibleAmt)+parseFloat(outOfPocketAmt);
	var patientShareAmt=parseFloat(copayAmt)+parseFloat(deductibleAmt);
	var discountedGrossAmt=document.forms[1].discountedGross.value;//=parseFloat(grossAmt)-parseFloat(discountAmt);
	if(parseFloat(patientShareAmt)>parseFloat(discountedGrossAmt)){
		 alert("PatientShare Amount Should Not Greater Than DiscountedGross Amount");
		document.forms[1].copay.value='0';
//		document.forms[1].coinsurance.value='0';
		document.forms[1].deductible.value='0';
//		document.forms[1].outOfPocket.value='0';
		document.forms[1].patientShare.value='0';
		document.forms[1].discount.value='0';
		document.forms[1].discountedGross.value='0';
		document.forms[1].netAmount.value='0';
		document.forms[1].grossAmount.value='0';  
		//document.forms[1].allowedAmount.value=''; 
		//document.forms[1].approvedAmount.value='';           
		}else{
			document.forms[1].patientShare.value=parseFloat(patientShareAmt).toFixed(2);
			document.forms[1].netAmount.value=parseFloat(parseFloat(discountedGrossAmt)-parseFloat(patientShareAmt)).toFixed(2);
			}		
}
function setApprovedQuantity(){	
	document.getElementById("approvedQuantity").value=document.getElementById("quantity").value;
}
function selectActivityCode() {   
    document.forms[1].mode.value = "doGeneral";
    document.forms[1].reforward.value="activitySearchList";
    document.forms[1].action ="/ClaimActivityDetailsAction.do";
    document.forms[1].submit();
}
function selectClinician(){
				  document.forms[1].mode.value="doGeneral";
				  document.forms[1].reforward.value="activityClinicianSearch";
				  document.forms[1].action="/ClaimActivityDetailsAction.do";	
				   document.forms[1].submit();	
	}
function addDenialDesc() {
	var selObj=document.forms[1].denialCode;
	 var denialCode=selObj.value;
	 if(denialCode===null||denialCode===""||denialCode==""||denialCode.length<1){
		 alert("Please Select Denial Description");
		 return;
	 }	
	document.forms[1].denialDescription.value = selObj.options[selObj.selectedIndex].text;
    document.forms[1].mode.value = "addDenialDesc";
    document.forms[1].action ="/ClaimActivityDetailsAction.do";
    document.forms[1].submit();
}
function deleteDenialDesc(keyVal) {
	document.forms[1].denialCode.value = keyVal;
    document.forms[1].mode.value = "deleteDenialDesc";
    document.forms[1].action ="/ClaimActivityDetailsAction.do";
    document.forms[1].submit();
}
function overrideNotAllow() {
alert("For this denial code you con't override");
}

function conProReqAmt()
{
	//var providerRequestedAmt;
	var claimType = document.forms[1].claimType.value;
	var processType = document.forms[1].processType.value;
	
	
	
	
	var providerRequestedAmt=document.forms[1].providerRequestedAmt.value;
	convertedProviderReqAmt=providerRequestedAmt*document.getElementById("conversionRate").value;
	document.forms[1].convertedProviderReqAmt.value=convertedProviderReqAmt;
	 
	 var vataedamt = document.getElementById("vatAed").value;
	
	var sumResult =  Number(convertedProviderReqAmt)+Number(vataedamt);
	
	 document.forms[1].vatAddedReqAmnt.value = sumResult;
	
	
	
	
	
	/*
	if(processType=="DBL" && claimType=="CTM"){
		
		providerRequestedAmt=document.forms[1].vatAddedReqAmnt.value;
		document.forms[1].providerRequestedAmt.value = providerRequestedAmt;
		convertedProviderReqAmt=providerRequestedAmt*document.getElementById("conversionRate").value;
		document.forms[1].convertedProviderReqAmt.value=convertedProviderReqAmt;
		 
		
	}
	else{
		
		providerRequestedAmt=document.forms[1].providerRequestedAmt.value;
		convertedProviderReqAmt=providerRequestedAmt*document.getElementById("conversionRate").value;
		document.forms[1].convertedProviderReqAmt.value=convertedProviderReqAmt;
		 
		 var vataedamt = document.getElementById("vatAed").value;
		
		var sumResult =  Number(convertedProviderReqAmt)+Number(vataedamt);
		
		 document.forms[1].vatAddedReqAmnt.value = sumResult;
	}
	*/
	
	
	
	
	
	
}


function caluculateActivityVatAED(){
	 var vatPercent=document.forms[1].vatPercent.value;
	 var convertedProviderReqAmt=document.forms[1].convertedProviderReqAmt.value;
	 
	 var providerRequestedAmt=document.forms[1].providerRequestedAmt.value;
	 
	 var vatAedstr=document.forms[1].vatAed.value;
	 
	 if(convertedProviderReqAmt>0){
		 
		var vataed =( (convertedProviderReqAmt) *( vatPercent)) /100;
		 
		 
		 if(vataed<0)
		  {
			  document.forms[1].vatAed.value = '0.0';
		  }
		  else
		  {
			  document.forms[1].vatAed.value = vataed.toFixed(2);
			
			  
			  document.forms[1].vatAddedReqAmnt.value = ( Number(convertedProviderReqAmt)+Number(vataed)).toFixed(2);
			  
		  }
	 }
	 else if(providerRequestedAmt>0){
		 var vataedAmt =( (providerRequestedAmt) *( vatPercent)) /100;
		
		 if(vataedAmt<0 || vataedAmt==0)
		  {
			  document.forms[1].vatAed.value = '0.0';
		  }
		  else
		  {
			  document.forms[1].vatAed.value = vataedAmt.toFixed(2);
			
			  
			  document.forms[1].vatAddedReqAmnt.value = ( Number(providerRequestedAmt)+Number(vataedAmt)).toFixed(2);
			  
		  }
		 
	 }
	 else{
		  document.forms[1].vatAddedReqAmnt.value = '0.0';
		 
	 }
}
	 
	 
	 function caluculateActivityVatPER(){
		 var vatPercent=document.forms[1].vatPercent.value;
		 var convertedProviderReqAmt=document.forms[1].convertedProviderReqAmt.value;
		 var vatAedstr=document.forms[1].vatAed.value; 
		 var providerRequestedAmt=document.forms[1].providerRequestedAmt.value;
		 
		 if(convertedProviderReqAmt>0){
			 
			 var vatPER =vatAedstr/convertedProviderReqAmt*100;
			 
			 if(vatPER<0)
			  {
				  document.forms[1].vatPercent.value = '0.0';
			  }
			  else
			  {
				  document.forms[1].vatPercent.value = vatPER.toFixed(2);
				  
				  document.forms[1].vatAddedReqAmnt.value = ( Number(convertedProviderReqAmt)+Number(vatAedstr) ).toFixed(2);
				  
			  }
			 
		 }
		 else if(providerRequestedAmt>0){
			 var vatPERAmt =vatAedstr/providerRequestedAmt*100;
			 
			// var vataedAmt =( (providerRequestedAmt) *( vatPercent)) /100;
			 if(vatPERAmt<0 || vatPERAmt==0)
			  {
				  document.forms[1].vatPercent.value = '0.0';
			  }
			  else
			  {
				  document.forms[1].vatPercent.value = vatPERAmt.toFixed(2);
				  
				  document.forms[1].vatAddedReqAmnt.value = (Number(providerRequestedAmt)+Number(vatAedstr) ).toFixed(2);
				  
			  }
			 
		 }
		 else{
			  document.forms[1].vatAddedReqAmnt.value = '0.0';
			 
		 }
		 
	 }
	 
	 function getInternalCodeDetails(){
		 document.forms[1].mode.value = "selectInternalCode";
		 document.forms[1].action ="/ClaimActivityDetailsAction.do";
		 document.forms[1].submit();
		 }