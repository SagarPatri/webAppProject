
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
	    document.forms[1].action ="/ActivityDetailsAction.do?codeFlag=remember";
	    document.forms[1].submit();
		
		}
}//getActivityCodeDetails
function closeActivities() {   
	document.forms[1].mode.value = "doGeneral";
	document.forms[1].reforward.value = "close";
    document.forms[1].action ="/ActivityDetailsAction.do";
    document.forms[1].submit();
}

function resetActivityDetails() {   
	document.forms[1].mode.value = "resetActivityDetails";
    document.forms[1].action ="/ActivityDetailsAction.do";
    document.forms[1].submit();
}

function saveActivityDetails() { 
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
	if(selObj.value!==null&&selObj.value!==""&&selObj.value.length>1){		
	document.forms[1].denialDescription.value = selObj.options[selObj.selectedIndex].text;
	}
    document.forms[1].mode.value = "saveActivityDetails";
    document.forms[1].action ="/SaveActivityDetailsAction.do";
    document.forms[1].submit();
}

function calculateNetAmount(upObj){
	
	var re = /^[0-9]*\.*[0-9]*$/;	
	var objValue=upObj.value;
	var unitPrice=document.forms[1].unitPrice.value;
	var activityTypeId=document.forms[1].activityTypeId.value;
	var activityCodeSeqId=document.forms[1].activityCodeSeqId.value;
	var processType=document.forms[1].processType.value;
	if(upObj.id==="UnitType"){
		if(activityCodeSeqId.length<1||activityTypeId!=5)
		{	
			return ;
		}
		else if(activityCodeSeqId.length>1||activityTypeId==5)
		{	
		var activityCode=document.forms[1].activityCode.value;
		var activityStartDate=document.forms[1].activityStartDate.value;
		var unitType=document.forms[1].unitType.value;
		var providerSeqID=document.forms[1].providerSeqID.value;
		var preAuthSeqID=document.forms[1].preAuthSeqID.value;
		//	var claimSeqID=document.forms[1].claimSeqID.value;
		$(document).ready(function () {
		$.ajax({
			
            url :"/asynchronAction.do?mode=getUnitTypePrice&activityCode="+activityCode+"&activityStartDate="+activityStartDate+"&unitType="+unitType+"&providerSeqID="+providerSeqID+"&type=PAT"+"&seqID="+preAuthSeqID,
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
	}}
		else if(objValue.length>=1)
	{
		if(!re.test(objValue))
		{
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
	
	document.getElementById("approvedQuantity").value=quantity;
	
	quantity=(quantity==null||quantity===""||!re.test(quantity)||quantity==="1")?1:quantity;
		
	unitPrice=(unitPrice==null||unitPrice===""||!re.test(unitPrice))?0:unitPrice;
	
	if(processType=="DBL"){
		var conversionRate  = document.getElementById("conversionRate").value;
		conversionRate=(conversionRate==null||conversionRate===""||!re.test(conversionRate)||conversionRate==="1")?1:conversionRate;
		unitPrice=unitPrice*conversionRate;
		document.forms[1].convertedUnitPrice.value=unitPrice.toFixed(2);
	}
	
	
	document.forms[1].grossAmount.value=parseFloat(parseFloat(unitPrice)*parseFloat(quantity)).toFixed(2);
	
	 var varTariffYN=document.forms[1].tariffYN.value;
	 var overrideYN=document.getElementById("overrideYN");
	 var haad_YN=document.getElementById("haad_YN").value;
	 if(varTariffYN==="Y"&&overrideYN===null){
		 var discountPerUnit=document.forms[1].discountPerUnit.value;
		 var discountAmt=document.forms[1].discountPerUnit.value;
			discountAmt=(discountAmt==null||discountAmt===""||!re.test(discountAmt))?0:discountAmt;
			discountPerUnit=(discountPerUnit==null||discountPerUnit===""||!re.test(discountPerUnit))?0:discountPerUnit;
			if(haad_YN==="Y") document.forms[1].discount.value=parseFloat(parseFloat(discountPerUnit)*parseFloat(quantity)).toFixed(2);
			else document.forms[1].discount.value=parseFloat(parseFloat(discountAmt)*parseFloat(quantity)).toFixed(2);
		 }else{
			 var discountAmt=document.forms[1].discount.value;
			 var discountPerUnit=document.forms[1].discountPerUnit.value;
				discountAmt=(discountAmt==null||discountAmt===""||!re.test(discountAmt))?0:discountAmt;
				discountPerUnit=(discountPerUnit==null||discountPerUnit===""||!re.test(discountPerUnit))?0:discountPerUnit;
				if(haad_YN==="Y") document.forms[1].discount.value=parseFloat(parseFloat(discountPerUnit)*parseFloat(quantity)).toFixed(2);
				else document.forms[1].discount.value=parseFloat(discountAmt).toFixed(2);
			 }	
	
	if(parseFloat(document.forms[1].discount.value)>parseFloat(document.forms[1].grossAmount.value)){		
		alert("Discount Amount Should Not Greater Than Gross Amount");
		document.forms[1].copay.value='0';
	//	document.forms[1].coinsurance.value='0';
		document.forms[1].deductible.value='0';
	//	document.forms[1].outOfPocket.value='0';
		document.forms[1].patientShare.value='0';
		document.forms[1].discount.value='0';
		document.forms[1].discountedGross.value='0';
		document.forms[1].netAmount.value='0';
		document.forms[1].grossAmount.value='0';  
		//document.forms[1].allowedAmount.value=''; 
		//document.forms[1].approvedAmount.value='';
		return ;
		}
	
	/*var additional_Disallowances=document.forms[1].additionalDisallowances.value;
	additional_Disallowances=(additional_Disallowances==null||additional_Disallowances===""||additional_Disallowances==="0")?0:additional_Disallowances;*/
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
	//	document.forms[1].coinsurance.value='0';
		document.forms[1].deductible.value='0';
	//	document.forms[1].outOfPocket.value='0';
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

function selectClinician(){
				  document.forms[1].mode.value="doGeneral";
				  document.forms[1].reforward.value="activityClinicianSearch";
				  document.forms[1].action="/ActivityDetailsAction.do";	
				   document.forms[1].submit();	
	}
function selectActivityCode() {
    document.forms[1].mode.value = "doGeneral";
    document.forms[1].reforward.value="activitySearchList";
    document.forms[1].action ="/ActivityDetailsAction.do";
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
    document.forms[1].action ="/ActivityDetailsAction.do";
    document.forms[1].submit();
}
function deleteDenialDesc(keyVal) {
	document.forms[1].denialCode.value = keyVal;
    document.forms[1].mode.value = "deleteDenialDesc";
    document.forms[1].action ="/ActivityDetailsAction.do";
    document.forms[1].submit();
}
function onChangeServiceType() {   
	document.forms[1].mode.value = "doChangeServiceType";
    document.forms[1].action ="/ActivityDetailsAction.do";
    document.forms[1].submit();
}

function overrideNotAllow() {
	alert("For this denial code you con't override");
	}
function onViewDocument() 
{
	
	var activityId=document.forms[1].activityId.value;
	var preAuthSeqID=document.forms[1].preAuthSeqID.value;
	  var openPage = "/ActivityDetailsAction.do?mode=doViewDocument&preAuthSeqID="+preAuthSeqID+"&activityId="+activityId;
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}

function getInternalCodeDetails(){
document.forms[1].mode.value = "selectInternalCode";
document.forms[1].action ="/ActivityDetailsAction.do";
document.forms[1].submit();
}