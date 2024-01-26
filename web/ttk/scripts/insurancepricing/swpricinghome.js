


	function copyToWebBoard()
	{
	    if(!mChkboxValidation(document.forms[1]))
	    {
		    document.forms[1].mode.value = "doCopyToWebBoard";
	   		document.forms[1].action = "/SwInsPricingAction.do";
		    document.forms[1].submit();
	    }//end of if(!mChkboxValidation(document.forms[1]))
	}//end of copyToWebBoard()

function addPricing()
{
	document.forms[1].reset();
    document.forms[1].rownum.value = "";
    document.forms[1].tab.value ="Group Profile";
    document.forms[1].mode.value = "doAdd";
    document.forms[1].action = "/SwInsPricingAction.do";
    document.forms[1].submit();
}

function chkExistCompPrd2(){
	
	var existCompProd1 = document.getElementById('existCompProd1').value;
	var existCompProd2 = document.getElementById('existCompProd2').value;
	
	 document.forms[1].existCompProd2.disabled=false;
	 document.getElementById("existCompProd2").style.backgroundColor ="white";
	if(existCompProd1 == ""){
		
		document.forms[1].existCompProd2.disabled=true;
		document.forms[1].existCompProd2.value="";
		document.getElementById("existCompProd2").style.backgroundColor ="#d9dce0";
	}
}

function enableExistCompPrd2(){
	
	var existCompProd1 = document.getElementById('existCompProd1').value;
	
	 
	if(existCompProd1 != ""){
		
		document.forms[1].existCompProd2.disabled=false;
		 document.getElementById("existCompProd2").style.backgroundColor ="white";
	}else{
		
		document.forms[1].existCompProd2.disabled=true;
		document.forms[1].existCompProd2.value="";
		document.getElementById("existCompProd2").style.backgroundColor ="#d9dce0";
		
	}
}

function ntworkToAddHospCov(){
	
	var networkList = document.getElementById('networkList').value;
	//alert('networkList:'+networkList); 
	
	if(networkList == '21' || networkList == '22'){
		
		$(".spanDIpCpy").removeAttr("style");
		 document.getElementById("dinpatientCopay").disabled=false;
			document.getElementById("dinpatientCopay").className="selectBox selectBoxMedium";
		 	document.getElementById("dinpatientCopay").readOnly="";
		 	 document.getElementById("dinpatientCopay").style.backgroundColor ="white";
		
	}else{
		
		$(".spanDIpCpy").attr("style","display:none");
		 document.getElementById("dinpatientCopay").disabled=true;
			document.getElementById("dinpatientCopay").className="selectBox selectBoxMedium";
			document.getElementById("dinpatientCopay").value="";
		 	document.getElementById("dinpatientCopay").readOnly="true";
		 	 document.getElementById("dinpatientCopay").style.backgroundColor ="#d9dce0";
		 	 
			 $(".spancommentsDirect").attr("style","display:none");
			 document.getElementById("commentsDirect").disabled=true;
				document.getElementById("commentsDirect").className="textBox textBoxMediumPT";
				document.getElementById("commentsDirect").value="";
			 	document.getElementById("commentsDirect").readOnly="true";
			 	 document.getElementById("commentsDirect").style.backgroundColor ="#d9dce0";
		
	}
	
}



function onSave(obj){	
	//alert('onSave'+obj);
	

	

	//document.getElementById("inpatientAreaOfCover").disabled=false;
	
	$('.disabledOption').removeAttr("disabled");
	
	var dateString = document.getElementById('coverStartDate').value;
	var dateString1 = document.getElementById('coverEndDate').value;
	var sDate  = dateString.split("/");
	var eDate  = dateString1.split("/");
	var startDate = new Date(sDate[2] ,sDate[1]-1, sDate[0]);
	var endDate = new Date(eDate[2] ,eDate[1]-1, eDate[0]);
	if( startDate > endDate ){
		
		
		alert("'Coverage end date' should not be earlier than 'Coverage start date'");
		  document.getElementById('coverEndDate').value="";
		  return false;
		
	 }
	
	var existCompProd1 = document.getElementById('existCompProd1').value;
	var existCompProd2 = document.getElementById('existCompProd2').value;
	//alert('existCompProd1:'+existCompProd1);
	//alert('existCompProd2:'+existCompProd2);
	if(existCompProd1 != "" || existCompProd2 != ""){
		if(existCompProd1 == existCompProd2){
			
			alert("Product name cannot be the same as 'Existing comparable product - 1'");
			  return false;
		}
	}

	
	var totalCovedLives = parseFloat(document.getElementById('totalCovedLives').value);
	var totalLivesMaternity = parseFloat(document.getElementById('totalLivesMaternity').value);
	
	if(totalLivesMaternity > totalCovedLives){
		
		  alert("'Total covered lives eligible for maternity' can not be higher than 'total covered lives'");
		  document.forms[1].totalLivesMaternity.value="";
		  return false;
	}
	
	  var maxBenifitList = parseFloat(document.getElementById('maxBenifitList').value);
	  
		var SelmaxBenifitList = document.getElementById("maxBenifitList");
			var MaxBenifitText = SelmaxBenifitList.options[SelmaxBenifitList.selectedIndex].text;
		  
			MaxBenifitText = MaxBenifitText.replace(/,/g,"");
		  
		  var MaxBenifit = parseFloat(MaxBenifitText);
		//  alert('MaxBenifit:'+MaxBenifit);

		  var normalDeliveryLimitOth = parseFloat(document.getElementById('normalDeliveryLimitOth').value);
		  var CSectionLimitOth = parseFloat(document.getElementById('CSectionLimitOth').value);
		  var drugsLimitOth = parseFloat(document.getElementById('drugsLimitOth').value);
		//  alert('SelmaxBenifitList:'+SelmaxBenifitList);
		//  alert('maxBenifitList:'+maxBenifitList+'normalDeliveryLimitOth:'+normalDeliveryLimitOth+'CSectionLimitOth:'+CSectionLimitOth);
		  if(maxBenifitList == 176){
			//  alert('If part:'+maxBenifitList);
			  var maxBeneLimitOth = parseFloat(document.getElementById('maxBeneLimitOth').value);
			  
			  if(drugsLimitOth > maxBeneLimitOth){
					
					alert("'Drugs limit' can not be higher than 'Maximum benefit limit'");
					document.forms[1].drugsLimitOth.value="";
					 return false;
				}
				if(normalDeliveryLimitOth > maxBeneLimitOth){
					
					alert("'Normal delivery limit' should not be higher than 'Maximum benefit limit'");
					document.forms[1].normalDeliveryLimitOth.value="";
					 return false;
				}
				
				if(CSectionLimitOth > maxBeneLimitOth){
					
					alert("'C-section limit' can not be higher than 'Maximum benefit limit'");
					document.forms[1].CSectionLimitOth.value="";
					 return false;
				}
			  
		  }else {
			  
			  if(drugsLimitOth > MaxBenifit){
					
					alert("'Drugs limit' can not be higher than 'Maximum benefit limit'");
					document.forms[1].drugsLimitOth.value="";
					 return false;
				}
			  
				if(normalDeliveryLimitOth > MaxBenifit){
					
					alert("'Normal delivery limit' should not be higher than 'Maximum benefit limit'");
					document.forms[1].normalDeliveryLimitOth.value="";
					 return false;
				}
				
				if(CSectionLimitOth > MaxBenifit){
					
					alert("'C-section limit' can not be higher than 'Maximum benefit limit'");
					document.forms[1].CSectionLimitOth.value="";
					 return false;
				}
			  
		  }
		 
/*	//  var maxBenifitList = document.getElementById('maxBenifitList').value;
	  
	var SelmaxBenifitList = document.getElementById("maxBenifitList");
		var MaxBenifitText = SelmaxBenifitList.options[SelmaxBenifitList.selectedIndex].text;
	  
		MaxBenifitText = MaxBenifitText.replace(/,/g,"");
	  
		var MaxBenifit = MaxBenifitText;
	//  var MaxBenifit = parseFloat(MaxBenifitText);
	//  alert('MaxBenifit:'+MaxBenifit);

	  var normalDeliveryLimitOth =document.getElementById('normalDeliveryLimitOth').value;
	  var CSectionLimitOth = document.getElementById('CSectionLimitOth').value;
	  var drugsLimitOth = document.getElementById('drugsLimitOth').value;
	//  alert('SelmaxBenifitList:'+SelmaxBenifitList);
	//  alert('maxBenifitList:'+maxBenifitList+'normalDeliveryLimitOth:'+normalDeliveryLimitOth+'CSectionLimitOth:'+CSectionLimitOth);
	  if(MaxBenifit == "Others"){
		//  alert('If part:'+maxBenifitList);
		  var maxBeneLimitOth = parseFloat(document.getElementById('maxBeneLimitOth').value);
		  
		  if(drugsLimitOth != ""){
			  
		  
		  if(drugsLimitOth != maxBeneLimitOth){
				
			//	alert("'Drugs limit' can not be higher than 'Maximum benefit limit'");
				 alert("'Drugs limit' should be equal to 'Maximum benefit limit'");
				document.forms[1].drugsLimitOth.value="";
				 return false;
			}
		  }
		  
		  if(normalDeliveryLimitOth != ""){
			  
		  
			if(normalDeliveryLimitOth != maxBeneLimitOth){
				
			//	alert("'Normal delivery limit' should not be higher than 'Maximum benefit limit'");
				 	alert("'Normal delivery limit' should be equal to 'Maximum benefit limit'");
				document.forms[1].normalDeliveryLimitOth.value="";
				 return false;
			}
			
		  }
		  
		  if(CSectionLimitOth != ""){
			  
		  
			
			if(CSectionLimitOth != maxBeneLimitOth){
				
			//	alert("'C-section limit' can not be higher than 'Maximum benefit limit'");
				 alert("'C-section limit' should be equal to 'Maximum benefit limit'");
				document.forms[1].CSectionLimitOth.value="";
				 return false;
			}
			
		  }
		  
	  }else {
		  
		  if(drugsLimitOth != ""){
		  
		  if(drugsLimitOth != MaxBenifit){
				
				// alert("'Drugs limit' can not be higher than 'Maximum benefit limit'");
				 alert("'Drugs limit' should be equal to 'Maximum benefit limit'");
				document.forms[1].drugsLimitOth.value="";
				 return false;
			}
		  
		  }
		  
		  if(normalDeliveryLimitOth != ""){
		  
			if(normalDeliveryLimitOth != MaxBenifit){
				
			//	alert("'Normal delivery limit' should not be higher than 'Maximum benefit limit'");
			 	alert("'Normal delivery limit' should be equal to 'Maximum benefit limit'");
				document.forms[1].normalDeliveryLimitOth.value="";
				 return false;
			}
			
		  }
			
			if(CSectionLimitOth != ""){
			
			if(CSectionLimitOth != MaxBenifit){
				
			//	alert("'C-section limit' can not be higher than 'Maximum benefit limit'");
				 alert("'C-section limit' should be equal to 'Maximum benefit limit'");
				document.forms[1].CSectionLimitOth.value="";
				 return false;
			}
			
			}
		  
	  }*/
	  
			
			var maternityInpatient = document.getElementById('maternityInpatient').value;
			var maternityOutpatient = document.getElementById('maternityOutpatient').value;
			var totalLivesMaternity = document.getElementById('totalLivesMaternity').value;
			


if(maternityInpatient == "Y" || maternityOutpatient == "Y"){
	
	var maternityPricing = document.getElementById('maternityPricing').value;
	var premiumRefundApplicable = document.getElementById('premiumRefundApplicable').value;

	
if(totalLivesMaternity == "" || totalLivesMaternity == undefined){
		
	alert("'Total covered lives eligible for maternity' is required.");
	return false;
	}
			
				/*var totalLivesMaternity = parseFloat(totalLivesMaternity);
				if(totalLivesMaternity <= 0){
					
					alert("'Total covered lives eligible for maternity' should be more than Zero.");
					return false;
					
				}*/
	
if(maternityPricing == "" || maternityPricing == undefined){
	
	alert("'Maternity IP and OP pricing' is required.");
	return false;
		
	}

if(premiumRefundApplicable == "" || premiumRefundApplicable == undefined){
	
	alert("'Premium refund applicable?' is required.");
	return false;
	
}
		
}



var loadingForInpatient = document.getElementById('loadingForInpatient').value;
var commentsInpatient = document.getElementById('commentsInpatient').value;
var loadingForOutpatient = document.getElementById('loadingForOutpatient').value;
var commentsOutpatient = document.getElementById('commentsOutpatient').value;

if(loadingForInpatient != "0" && loadingForInpatient != ""){
	
	if(commentsInpatient == "" || commentsInpatient == undefined){
		
		alert("'Comments (Inpatient area of cover loading)' is required.");
		return false;
	}
	
	
}

if(loadingForOutpatient != "0" && loadingForOutpatient != ""){
	
	if(commentsOutpatient == "" || commentsOutpatient == undefined){
		
		alert("'Comments (Outpatient area of cover loading)' is required.");
		return false;
	}
	
	
}

      var SelincomeGroup = document.getElementById("incomeGroup");
      var incomeGroup = SelincomeGroup.options[SelincomeGroup.selectedIndex].text;
	
	var singlebutton = obj;
	
	var fetchData = document.getElementById('fetchData').value;
	
	if(fetchData == "Y"){
		
		var confYN=confirm("As the inputs in the 'Key Coverages' section have been auto populated, please confirm that you have reviewed the inputs and ok to proceed.");
		
		if(!confYN){
		  return false;
		}
		
		
	}
	
	var outpatientBenefit = document.getElementById('outpatientBenefit').value;
	var networkList = document.getElementById('networkList').value;
	var dinpatientCopay = document.getElementById('dinpatientCopay').value;

	if(outpatientBenefit == "COV" && (networkList == "21" || networkList == "22")){
		
		if(dinpatientCopay == "" ){
			
			alert("'Direct Specialist access (valid only for OP) ' is required.");
			return false;
		}
		
		
	}
	
	var maxChildAgeLimit = document.getElementById('maxChildAgeLimit').value;
	
	var companionCharges = document.getElementById('companionCharges').value;

	if(maxChildAgeLimit != ""){
		
       if(maxChildAgeLimit != "55" ){
	
	    if(companionCharges == ""){
		
		alert("'Companion charges per day limit ' is required.");
		return false;
		
		
	}
			
			
		}	
		
	}
	
	
	if(maternityInpatient == "N" || maternityOutpatient == "N"){
		
		var premiumRefundApplicable = document.getElementById('premiumRefundApplicable').value;
		if(premiumRefundApplicable == "" || premiumRefundApplicable == undefined){
			
			alert("'Premium refund applicable?' is required.");
			return false;
			
		}
	}	
	document.getElementById("dinpatientCopay").disabled=false; // added by govind
	
/*	if(!JS_SecondSubmit)
    {
		if (confirm('Are you sure you want to proceed pricing of the policy category mentioned on the top?')) {*/
			
			if(singlebutton =="saveProceed"){
				document.forms[1].tab.value ="Income Profile";
			    }
			//file
			var attachmentname1 = document.getElementById("attachmentname1").value;
			var attachmentname2 = document.getElementById("attachmentname2").value;
			var attachmentname3 = document.getElementById("attachmentname3").value;
			var attachmentname4 = document.getElementById("attachmentname4").value;
			var attachmentname5 = document.getElementById("attachmentname5").value;
			
			//end file
				   document.forms[1].mode.value="doSave";
				   document.forms[1].action="/SwInsPricingActionAdd.do?singlebutton="+singlebutton+"&attachmentname1="+attachmentname1+"&attachmentname2="+attachmentname2+"&attachmentname3="+attachmentname3+"&attachmentname4="+attachmentname4+"&attachmentname5="+attachmentname5;
				   JS_SecondSubmit=true;
				   document.forms[1].submit();
			
	//	}
		
			// }else{
					//document.getElementById("").focus();
					//return false;
				//} 
		//}//end of if(!JS_SecondSubmit)
//    }


	
}//end of onSave()
// govind validation start
function valPremRefnd()
{
	var premiumRefundApplicable = document.getElementById('premiumRefundApplicable').value;
//	alert('premiumRefundApplicable:'+premiumRefundApplicable);
	if(premiumRefundApplicable == '169'){
		
		alert("A load of 0.3% will be factored in the final premium - any loadings/discount over and above this can be adjusted for in the 'Loadings & Discount' section in the 'Final Premium - Working' tab");
		
	}
	
if(premiumRefundApplicable == '170'){
	
	alert("A load of 0.5% will be factored in the final premium - any loadings/discount over and above this can be adjusted for in the 'Loadings & Discount' section in the 'Final Premium - Working' tab");
		
	}
	

	

}

function warnFutureDate(){
	var dateString = document.getElementById('coverStartDate').value;
	var sDate  = dateString.split("/");
	var myDate = new Date(sDate[2] ,sDate[1]-1, sDate[0]);
	var today = new Date();
	if( myDate < today ){
		if (confirm('Coverage start date entered is in the past, do you want to proceed?')) {
		 document.getElementById('coverStartDate').value = dateString;
		 return true;
		} else {
		  document.getElementById('coverStartDate').value="";
		} 
	 }
}

/*function warnFutureDate(){
	var dateString = document.getElementById('coverStartDate').value;
	var sDate  = dateString.split("/");
	var myDate = new Date(sDate[2] ,sDate[1]-1, sDate[0]);
	var today = new Date();
	if( myDate < today ){
		if (confirm('Coverage start date entered is in the past, do you want to proceed?')) {
		 document.getElementById('coverStartDate').value = dateString;
		} else {
		  document.getElementById('coverStartDate').value="";
		} 
	 }
}*/

function onchangeMaxBenefitType(obj)
{
	
	
     var mblSel=document.getElementById("maxBenifitList").value;
	 if(mblSel == "176"){
		
		 document.getElementById("maxBeneLimitOth").disabled=false;
		// document.getElementById("maxBeneLimitOth").className="textBox textBoxMediumPT textBoxEnabled";
		document.getElementById("maxBeneLimitOth").className="textBox textBoxMediumPT";
	 	document.getElementById("maxBeneLimitOth").readOnly="";
	 	$(".spanmaxBeneLimitOth").removeAttr("style");
	 }
	 else{
		 document.getElementById("maxBeneLimitOth").className="textBox textBoxMediumPT textBoxDisabled";
		 document.getElementById("maxBeneLimitOth").readOnly="true"; 
		 document.getElementById("maxBeneLimitOth").value = "";
		 $(".spanmaxBeneLimitOth").attr("style","display:none");
	 }
	 
	
}

function onchangeDrugsLimit(obj)
{
	var drugSel=document.getElementById("drugsLimit").value;
   //  alert('onchangeDrugsLimit drugSel:'+drugSel);
	 if(drugSel == "177"){
		 $(".spandrugsLimitOth").removeAttr("style");
		 document.getElementById("drugsLimitOth").disabled=false;
		// document.getElementById("drugsLimitOth").className="textBox textBoxMediumPT textBoxEnabled";
		document.getElementById("drugsLimitOth").className="textBox textBoxMediumPT";
	 	document.getElementById("drugsLimitOth").readOnly="";
	 	document.getElementById("drugsLimitOth").style.backgroundColor ="white";
	 }
	 else{
		 $(".spandrugsLimitOth").attr("style","display:none");
		 document.getElementById("drugsLimitOth").className="textBox textBoxMediumPT textBoxDisabled";
		 document.getElementById("drugsLimitOth").readOnly="true"; 
		 document.getElementById("drugsLimitOth").value = "";
		 document.getElementById("drugsLimitOth").style.backgroundColor ="#d9dce0";
	 }
}

function chlAgeLmtForCompEgbl(obj)
{
    var ageSel=document.getElementById("maxChildAgeLimit").value;
    var ip=document.getElementById("inpatientBenefit").value;
	 if(ageSel == "" || ageSel == "55" || ip != "COV"){
		 
		 $(".spancompanionCharges").attr("style","display:none");
			// document.getElementById("companionCharges").className="selectBox selectBoxMedium selectBoxDisabled";
			 document.getElementById("companionCharges").disabled=true;
			 document.getElementById("companionCharges").readOnly="true"; 
			 document.getElementById("companionCharges").value = "";
			 document.getElementById("companionCharges").style.backgroundColor ="#d9dce0";
		
	
	 }
	 else{
	
		 
		 $(".spancompanionCharges").removeAttr("style");
		 document.getElementById("companionCharges").disabled=false;
		document.getElementById("companionCharges").className="selectBox selectBoxMedium";
	 	document.getElementById("companionCharges").readOnly="";
	 	 document.getElementById("companionCharges").style.backgroundColor ="white";
	 }
}

function onChangeIpAreaOfCov()
{
     var ipAreaOfCovVar=document.getElementById("inpatientAreaOfCoverVariations").value;
	 if(ipAreaOfCovVar != "" && ipAreaOfCovVar != undefined){
		
		 $(".spanloadingForIp").removeAttr("style");
		// $(".spancommIp").removeAttr("style");
		 document.getElementById("loadingForInpatient").disabled=false;
		document.getElementById("loadingForInpatient").className="textBox textBoxMediumPT";
	 	document.getElementById("loadingForInpatient").readOnly="";
	 	 document.getElementById("loadingForInpatient").style.backgroundColor ="white";
	 	 
	/* 	document.getElementById("commentsInpatient").disabled=false;
		document.getElementById("commentsInpatient").className="textBox textBoxMediumPT";
	 	document.getElementById("commentsInpatient").readOnly="";
	 	 document.getElementById("commentsInpatient").style.backgroundColor ="white";*/
	 }
	 else {
	   
		 $(".spanloadingForIp").attr("style","display:none");
		 $(".spancommIp").attr("style","display:none");
		 document.getElementById("loadingForInpatient").disabled=true;
		 document.getElementById("loadingForInpatient").readOnly="true"; 
		 document.getElementById("loadingForInpatient").value = "0";
		 document.getElementById("loadingForInpatient").style.backgroundColor ="#d9dce0";
		 
		 document.getElementById("commentsInpatient").disabled=true;
		 document.getElementById("commentsInpatient").readOnly="true"; 
		 document.getElementById("commentsInpatient").value = "";
		 document.getElementById("commentsInpatient").style.backgroundColor ="#d9dce0";
		 
		 
	 } 
	 
}

function onChangeOpAreaOfCov()
{
    var opAreaOfCovVar=document.getElementById("outpatientAreaOfCoverVariations").value;
	 if(opAreaOfCovVar != "" && opAreaOfCovVar != undefined){
		
			$(".spanldForOp").removeAttr("style");
		 document.getElementById("loadingForOutpatient").disabled=false;
		document.getElementById("loadingForOutpatient").className="textBox textBoxMediumPT";
	 	document.getElementById("loadingForOutpatient").readOnly="";
	 	 document.getElementById("loadingForOutpatient").style.backgroundColor ="white";
	 	 
	/* 	$(".spancommOp").removeAttr("style");
		 document.getElementById("commentsOutpatient").disabled=false;
			document.getElementById("commentsOutpatient").className="textBox textBoxMediumPT";
		 	document.getElementById("commentsOutpatient").readOnly="";
		 	 document.getElementById("commentsOutpatient").style.backgroundColor ="white";*/
	 }
	 else{
		 $(".spanldForOp").attr("style","display:none");
		// document.getElementById("companionCharges").className="selectBox selectBoxMedium selectBoxDisabled";
		 document.getElementById("loadingForOutpatient").disabled=true;
		 document.getElementById("loadingForOutpatient").readOnly="true"; 
		 document.getElementById("loadingForOutpatient").value = "0";
		 document.getElementById("loadingForOutpatient").style.backgroundColor ="#d9dce0";
		 
		 	 $(".spancommOp").attr("style","display:none");
			 document.getElementById("commentsOutpatient").disabled=true;
				document.getElementById("commentsOutpatient").className="textBox textBoxMediumPT";
				document.getElementById("commentsOutpatient").value="";
			 	document.getElementById("commentsOutpatient").readOnly="true";
			 	 document.getElementById("commentsOutpatient").style.backgroundColor ="#d9dce0";
	 }
}

function onChngOpAreaCov(){
	
	 var outpatientAreaOfCover=document.getElementById("outpatientAreaOfCover").value;
	 
	 if(outpatientAreaOfCover != "106"){
		 
		
		 document.getElementById("additionalHospitalCoverage").disabled=false;
		document.getElementById("additionalHospitalCoverage").className="textBox textBoxMediumPT";
	 	document.getElementById("additionalHospitalCoverage").readOnly="";
	 	 document.getElementById("additionalHospitalCoverage").style.backgroundColor ="white";
	 }
	 else{
		// document.getElementById("companionCharges").className="selectBox selectBoxMedium selectBoxDisabled";
		 document.getElementById("additionalHospitalCoverage").disabled=true;
		 document.getElementById("additionalHospitalCoverage").readOnly="true"; 
		 document.getElementById("additionalHospitalCoverage").value = "";
		 document.getElementById("additionalHospitalCoverage").style.backgroundColor ="#d9dce0";
		 
		 $(".spanldForAddHosp").attr("style","display:none");
			// document.getElementById("companionCharges").className="selectBox selectBoxMedium selectBoxDisabled";
			 document.getElementById("loadingForAddHospital").disabled=true;
			 document.getElementById("loadingForAddHospital").readOnly="true"; 
			 document.getElementById("loadingForAddHospital").value = "";
			 document.getElementById("loadingForAddHospital").style.backgroundColor ="#d9dce0";
			 
			 $(".spanhosComments").attr("style","display:none");
				// document.getElementById("companionCharges").className="selectBox selectBoxMedium selectBoxDisabled";
				 document.getElementById("hosComments").disabled=true;
				 document.getElementById("hosComments").readOnly="true"; 
				 document.getElementById("hosComments").value = "";
				 document.getElementById("hosComments").style.backgroundColor ="#d9dce0";
	 }
	
}

function onChangeAddHospCov()
{
    var loadingForAddHospital=document.getElementById("loadingForAddHospital").value;
	 if(loadingForAddHospital != "" && loadingForAddHospital != undefined){
		 
		 $(".spanhosComments").removeAttr("style");
		
		 document.getElementById("hosComments").disabled=false;
		document.getElementById("hosComments").className="textBox textBoxMediumPT";
	 	document.getElementById("hosComments").readOnly="";
	 	 document.getElementById("hosComments").style.backgroundColor ="white";
	 }
	 else{
		 $(".spanhosComments").attr("style","display:none");
		// document.getElementById("companionCharges").className="selectBox selectBoxMedium selectBoxDisabled";
		 document.getElementById("hosComments").disabled=true;
		 document.getElementById("hosComments").readOnly="true"; 
		 document.getElementById("hosComments").value = "";
		 document.getElementById("hosComments").style.backgroundColor ="#d9dce0";
	 }
}

function onchangeNmlDlivLmt(obj)
{
     var nmDelSel=document.getElementById("normalDeliveryLimit").value;
	 if(nmDelSel == "178"){
			$(".spanNrmDelLmtOth").removeAttr("style");
		 document.getElementById("normalDeliveryLimitOth").disabled=false; 
		 //document.getElementById("normalDeliveryLimitOth").className="textBox textBoxMediumPT";
		 
		 document.getElementById("normalDeliveryLimitOth").className="textBox textBoxMediumPT3";//bk
	 	 document.getElementById("normalDeliveryLimitOth").readOnly="";
	 	 document.getElementById("normalDeliveryLimitOth").style.backgroundColor ="white";
	 }
	 else{
		 $(".spanNrmDelLmtOth").attr("style","display:none");
		// document.getElementById("normalDeliveryLimitOth").className="textBox textBoxMediumPT textBoxDisabled";
		 
		 document.getElementById("normalDeliveryLimitOth").className="textBox textBoxMediumPT3 textBoxDisabled";//bk
		 document.getElementById("normalDeliveryLimitOth").readOnly="true"; 
		 document.getElementById("normalDeliveryLimitOth").value = "";
		 document.getElementById("normalDeliveryLimitOth").style.backgroundColor ="#d9dce0";
	 }
}

function onChngNmlDlvLmtOth() {
	
/*	var normalDeliveryLimitOth = parseFloat(document.getElementById('normalDeliveryLimitOth').value);
	 if(normalDeliveryLimitOth > 15000){
		
		  alert("'Maximum limit' can not exceed 15,000");
		  document.forms[1].normalDeliveryLimitOth.value="";
		  return false;
	 }*/
	
}

function onChngCsecLmtOth() {
	
/*	var CSectionLimitOth = parseFloat(document.getElementById('CSectionLimitOth').value);
	 if(CSectionLimitOth > 22500){
		
		  alert("'Maximum limit' can not exceed 22,500");
		  document.forms[1].CSectionLimitOth.value="";
		  return false;
	 }*/
	
}

function onchangeCSecLmt(obj)
{
     var cSecSel=document.getElementById("CSectionLimit").value;
	 if(cSecSel == "179"){
			$(".spanCSecLmtOth").removeAttr("style");
		 document.getElementById("CSectionLimitOth").disabled=false;
		//document.getElementById("CSectionLimitOth").className="textBox textBoxMediumPT";
		
		document.getElementById("CSectionLimitOth").className="textBox textBoxMediumPT3";
	 	document.getElementById("CSectionLimitOth").readOnly="";
	 	document.getElementById("CSectionLimitOth").style.backgroundColor ="white";
	 }
	 else{
		 $(".spanCSecLmtOth").attr("style","display:none");
		// document.getElementById("CSectionLimitOth").className="textBox textBoxMediumPT textBoxDisabled";
		 
		 document.getElementById("CSectionLimitOth").className="textBox textBoxMediumPT3 textBoxDisabled";
		 document.getElementById("CSectionLimitOth").readOnly="true"; 
		 document.getElementById("CSectionLimitOth").value = "";
		 document.getElementById("CSectionLimitOth").style.backgroundColor ="#d9dce0";
	 }
}

function onchangeMaxBenefitLimit(obj)
{

	var maxBeneLimitOth = parseFloat(document.getElementById('maxBeneLimitOth').value);
	 if(maxBeneLimitOth > 550000){
		
		  alert("Maximum benefit limit must not exceeds 550,000");
		  document.forms[1].maxBeneLimitOth.value="";
		  return false;
	 }
	
}

function validateTrendPercent()
{

	var trendFactor = parseFloat(document.getElementById('trendFactor').value);
	 if(trendFactor > 11){
		
		  alert("'Trend' can not exceed 11%");
		  document.forms[1].trendFactor.value="";
		  return false;
	 }
	
}

function onchangeLdIpCovVarOnLoad()
{

	var loadingForInpatient = document.getElementById('loadingForInpatient').value;
	

	
	
	if(loadingForInpatient != "" && loadingForInpatient != undefined && loadingForInpatient != "0" ){
		
		  var regexp =/^\d+(?:\.\d{1,1})?$/;

		    if(!regexp.test(loadingForInpatient))
	      {
		     // 	alert(" grossRiskLoadID ");
		        alert(" Only 1 decimal place allowed ");
		        document.getElementById("loadingForInpatient").focus();
		        document.getElementById("loadingForInpatient").value="0";
				return false;
	      }
		
		$(".spancommIp").removeAttr("style");
		
		 document.getElementById("commentsInpatient").disabled=false;
			document.getElementById("commentsInpatient").className="textBox textBoxMediumPT";
		 	document.getElementById("commentsInpatient").readOnly="";
		 	 document.getElementById("commentsInpatient").style.backgroundColor ="white";
		
	}else{
		
		$(".spancommIp").attr("style","display:none");
		 document.getElementById("commentsInpatient").disabled=true;
			document.getElementById("commentsInpatient").className="textBox textBoxMediumPT";
			document.getElementById("commentsInpatient").value="";
		 	document.getElementById("commentsInpatient").readOnly="true";
		 	 document.getElementById("commentsInpatient").style.backgroundColor ="#d9dce0";
		
	}
	

	
}

function onchangeLdIpCovVar()
{
	var loadingForInpatient = parseFloat(document.getElementById('loadingForInpatient').value);
	 if(loadingForInpatient > 50){
		
		  alert("'Loading for Inpatient area of cover' exceed 50%, Do you want to proceed?");
		//  document.getElementById('loadingForInpatient').value = "";
		 
	 }
	 
		var loadingForInpatient = document.getElementById('loadingForInpatient').value;
		

		
		
		if(loadingForInpatient != "" && loadingForInpatient != undefined && loadingForInpatient != "0" ){
			
			  var regexp =/^\d+(?:\.\d{1,1})?$/;

			    if(!regexp.test(loadingForInpatient))
		      {
			     // 	alert(" grossRiskLoadID ");
			        alert(" Only 1 decimal place allowed ");
			        document.getElementById("loadingForInpatient").focus();
			        document.getElementById("loadingForInpatient").value="0";
					return false;
		      }
			
			$(".spancommIp").removeAttr("style");
			
			 document.getElementById("commentsInpatient").disabled=false;
				document.getElementById("commentsInpatient").className="textBox textBoxMediumPT";
			 	document.getElementById("commentsInpatient").readOnly="";
			 	 document.getElementById("commentsInpatient").style.backgroundColor ="white";
			
		}else{
			
			$(".spancommIp").attr("style","display:none");
			 document.getElementById("commentsInpatient").disabled=true;
				document.getElementById("commentsInpatient").className="textBox textBoxMediumPT";
				document.getElementById("commentsInpatient").value="";
			 	document.getElementById("commentsInpatient").readOnly="true";
			 	 document.getElementById("commentsInpatient").style.backgroundColor ="#d9dce0";
			
		}
	
}

function onchangeLdOpCovVarOnLoad()
{
	
	var loadingForOutpatient = document.getElementById('loadingForOutpatient').value;
	


	if(loadingForOutpatient != "" && loadingForOutpatient != undefined && loadingForOutpatient != "0"){
		
		  var regexp =/^\d+(?:\.\d{1,1})?$/;

		    if(!regexp.test(loadingForOutpatient))
	        {
		     // 	alert(" grossRiskLoadID ");
		        alert(" Only 1 decimal place allowed ");
		        document.getElementById("loadingForOutpatient").focus();
		        document.getElementById("loadingForOutpatient").value="0";
				return false;
	        }
		
		$(".spancommOp").removeAttr("style");
		 document.getElementById("commentsOutpatient").disabled=false;
			document.getElementById("commentsOutpatient").className="textBox textBoxMediumPT";
		 	document.getElementById("commentsOutpatient").readOnly="";
		 	 document.getElementById("commentsOutpatient").style.backgroundColor ="white";
	}else{
		
		$(".spancommOp").attr("style","display:none");
		
		 document.getElementById("commentsOutpatient").disabled=true;
			document.getElementById("commentsOutpatient").className="textBox textBoxMediumPT";
			document.getElementById("commentsOutpatient").value="";
		 	document.getElementById("commentsOutpatient").readOnly="true";
		 	 document.getElementById("commentsOutpatient").style.backgroundColor ="#d9dce0";
		
	}
		
}

function onchangeLdOpCovVar(){
	
	var loadingForOutpatient = parseFloat(document.getElementById('loadingForOutpatient').value);
	 loadingForOutpatient = parseFloat(loadingForOutpatient);
	 if(loadingForOutpatient > 50){
		
		  alert("'Loading for Outpatient area of cover' exceed 50%, Do you want to proceed?");
		//  document.getElementById('loadingForOutpatient').value = "";
		 
	 }
	 
		var loadingForOutpatient = document.getElementById('loadingForOutpatient').value;
		


		if(loadingForOutpatient != "" && loadingForOutpatient != undefined && loadingForOutpatient != "0"){
			
			  var regexp =/^\d+(?:\.\d{1,1})?$/;

			    if(!regexp.test(loadingForOutpatient))
		        {
			     // 	alert(" grossRiskLoadID ");
			        alert(" Only 1 decimal place allowed ");
			        document.getElementById("loadingForOutpatient").focus();
			        document.getElementById("loadingForOutpatient").value="0";
					return false;
		        }
			
			$(".spancommOp").removeAttr("style");
			 document.getElementById("commentsOutpatient").disabled=false;
				document.getElementById("commentsOutpatient").className="textBox textBoxMediumPT";
			 	document.getElementById("commentsOutpatient").readOnly="";
			 	 document.getElementById("commentsOutpatient").style.backgroundColor ="white";
		}else{
			
			$(".spancommOp").attr("style","display:none");
			
			 document.getElementById("commentsOutpatient").disabled=true;
				document.getElementById("commentsOutpatient").className="textBox textBoxMediumPT";
				document.getElementById("commentsOutpatient").value="";
			 	document.getElementById("commentsOutpatient").readOnly="true";
			 	 document.getElementById("commentsOutpatient").style.backgroundColor ="#d9dce0";
			
		}
	
}

function validateAntSelLdIp()
{

	var antiSelectionLoadInpatient = parseFloat(document.getElementById('antiSelectionLoadInpatient').value);
	
	 if(antiSelectionLoadInpatient > 100){
			
		  alert("'Anti selection load Inpatient'  can not exceed 100%");
		  document.forms[1].antiSelectionLoadInpatient.value="";
		  return false;
	 }
	 
	 if(antiSelectionLoadInpatient > 50){
			
		  alert("'Anti selection load Inpatient' is greater than 50%");
		  return true;
	 }

	
}

function validateAntSelLdOp()
{

	var antiSelectionLoadOutpatient = parseFloat(document.getElementById('antiSelectionLoadOutpatient').value);
	
	 if(antiSelectionLoadOutpatient > 100){
			
		  alert("'Anti selection load Outpatient'  can not exceed 100%");
		  document.forms[1].antiSelectionLoadOutpatient.value="";
		  return false;
	 }
	
	 if(antiSelectionLoadOutpatient > 50){
			
		  alert("Anti selection load Outpatient' is greater than 50%");
		  return true;
	 }
	
}

function validateMatCovLives()
{

	var totalLivesMaternity = parseFloat(document.getElementById('totalLivesMaternity').value);
	var maternityInpatient = document.getElementById("maternityInpatient").value;
	 if(maternityInpatient == 'Y' && totalLivesMaternity <= 0){
			
		  alert("'Total covered lives eligible for maternity' is required.");
		  document.forms[1].totalLivesMaternity.value="";
		  return false;
	 }
	
}

function onChngNtwrkToOp(){
	
	var networkList = document.getElementById('networkList').value;
	var op = document.getElementById("outpatientBenefit").value;
	
	if((networkList == '21' || networkList == '22') && op == "COV" ){
		
		$(".spanDIpCpy").removeAttr("style");
		 document.getElementById("dinpatientCopay").disabled=false;
			document.getElementById("dinpatientCopay").className="selectBox selectBoxMedium";
		 	document.getElementById("dinpatientCopay").readOnly="";
		 	 document.getElementById("dinpatientCopay").style.backgroundColor ="white";
		
	}else{
		
		$(".spanDIpCpy").attr("style","display:none");
		 document.getElementById("dinpatientCopay").disabled=true;
			document.getElementById("dinpatientCopay").className="selectBox selectBoxMedium";
			document.getElementById("dinpatientCopay").value="";
		 	document.getElementById("dinpatientCopay").readOnly="true";
		 	 document.getElementById("dinpatientCopay").style.backgroundColor ="#d9dce0";
		 	 
			 $(".spancommentsDirect").attr("style","display:none");
			 document.getElementById("commentsDirect").disabled=true;
				document.getElementById("commentsDirect").className="textBox textBoxMediumPT";
				document.getElementById("commentsDirect").value="";
			 	document.getElementById("commentsDirect").readOnly="true";
			 	 document.getElementById("commentsDirect").style.backgroundColor ="#d9dce0";
		
	}
	
}

function onChngNtwrk()
{

	var networkList = document.getElementById('networkList').value;
	//alert('networkList:'+networkList); 
	

	var  myselect1=document.getElementById("outpatientAreaOfCover");
	
	while (myselect1.hasChildNodes()) {   
 	    myselect1.removeChild(myselect1.firstChild);
   }
	 myselect1.options.add(new Option("Select from list",""));
    var  path="/asynchronAction.do?mode=getOutPatientAreaCov&networkList="+networkList;		                 

	 $.ajax({
	     url :path,
	     dataType:"text",
	     success : function(data) {
	    	
	     var res1 = data.split("#");
	     for(var i=0;i<res1.length-1;i++){   	    	    
	     var res2=res1[i].split("@");
	        myselect1.options.add(new Option(res2[1],res2[0]));  	                 
	     }//for()
	     }//function(data)
	 });
	//var additionalHospitalCoverage = parseFloat(document.getElementById('additionalHospitalCoverage').value);
	
	
/*	alert('additionalHospitalCoverage:'+additionalHospitalCoverage);
	
	 if((additionalHospitalCoverage != null || additionalHospitalCoverage != undefined) || (networkList != null || networkList != undefined)){
		 
		 if(networkList == '22' && additionalHospitalCoverage == '71')
			
		  alert("'Dubai Health Authority - Hatta Hospital' and 'Al Ruwais Hospital' are by default covered under Ruby network.");
		  return true;
	 }*/
	
	
	
	
}

function addHsplCov(){
	
	var additionalHospitalCoverage = document.getElementById("additionalHospitalCoverage").value;
	
	if(additionalHospitalCoverage != "" && additionalHospitalCoverage != undefined){
		
		$(".spanldForAddHosp").removeAttr("style");
		
		 document.getElementById("loadingForAddHospital").disabled=false;
			document.getElementById("loadingForAddHospital").className="textBox textBoxMediumPT";
		 	document.getElementById("loadingForAddHospital").readOnly="";
		 	 document.getElementById("loadingForAddHospital").style.backgroundColor ="white";
		
	}else {
		
		 $(".spanldForAddHosp").attr("style","display:none");
		 
		 document.getElementById("loadingForAddHospital").disabled=true;
			document.getElementById("loadingForAddHospital").className="textBox textBoxMediumPT";
			document.getElementById("loadingForAddHospital").value="";
		 	document.getElementById("loadingForAddHospital").readOnly="true";
		 	 document.getElementById("loadingForAddHospital").style.backgroundColor ="#d9dce0";
		 	 
		 	$(".spanhosComments").attr("style","display:none");
		 	 document.getElementById("hosComments").disabled=true;
				document.getElementById("hosComments").className="textBox textBoxMediumPT";
				document.getElementById("hosComments").value="";
			 	document.getElementById("hosComments").readOnly="true";
			 	 document.getElementById("hosComments").style.backgroundColor ="#d9dce0";
	}
}

function compNtwrk(){
	
	var networkList = document.getElementById('networkList').value;
	if(networkList == '22'){
		
		// document.getElementById('networkList').tooltip= "'Dubai Health Authority - Hatta Hospital' and 'Al Ruwais Hospital' are by default covered under Ruby network.";
		 alert("'Dubai Health Authority - Hatta Hospital' and 'Al Ruwais Hospital' are by default covered under Ruby network.");
		
	}
	
}

function onChngDirSpclAccess(){

	var dinpatientCopay = document.getElementById("dinpatientCopay").value;
	
	if(dinpatientCopay == "148"){
		
		$(".spancommentsDirect").removeAttr("style");
		 document.getElementById("commentsDirect").disabled=false;
			document.getElementById("commentsDirect").className="textBox textBoxMediumPT";
		 	document.getElementById("commentsDirect").readOnly="";
		 	 document.getElementById("commentsDirect").style.backgroundColor ="white";
		
	}else {
		
		 $(".spancommentsDirect").attr("style","display:none");
		 document.getElementById("commentsDirect").disabled=true;
			document.getElementById("commentsDirect").className="textBox textBoxMediumPT";
			document.getElementById("commentsDirect").value="";
		 	document.getElementById("commentsDirect").readOnly="true";
		 	 document.getElementById("commentsDirect").style.backgroundColor ="#d9dce0";
	}
}


function enableIncomeData(){
	
	var previousProductName = document.getElementById('previousProductName').value;
	if(previousProductName != ""){
		document.forms[1].incomeGroup.disabled=false;
		document.getElementById('incomeGroup').style.backgroundColor="white";
	}else{
		
		document.forms[1].incomeGroup.value="";
		document.forms[1].incomeGroup.disabled=true;
		document.getElementById('incomeGroup').style.backgroundColor="#d9dce0";
		
	}
	
}

function enableInsCompNmOth(){
	
	var insuranceCompanyName = document.getElementById('insuranceCompanyName').value;
	if(insuranceCompanyName == "OTHERS"){
		
		$(".spanIncCompOth").removeAttr("style");
		 document.getElementById("insuranceCompanyNameOth").disabled=false;
		 	document.getElementById("insuranceCompanyNameOth").readOnly="";
		 	 document.getElementById("insuranceCompanyNameOth").style.backgroundColor ="white";
		
	}else{
		
		 $(".spanIncCompOth").attr("style","display:none");
		 document.getElementById("insuranceCompanyNameOth").disabled=true;
			document.getElementById("insuranceCompanyNameOth").value="";
		 	document.getElementById("insuranceCompanyNameOth").readOnly="true";
		 	 document.getElementById("insuranceCompanyNameOth").style.backgroundColor ="#d9dce0";
		
	
		
	}
	
}

function validateIP()
{

	 var inpatientBenefitSel = document.getElementById("inpatientBenefit");
	  var inpatientBenefitVal = inpatientBenefitSel.options[inpatientBenefitSel.selectedIndex].value;
	if(inpatientBenefitVal == 'NCOV' || inpatientBenefitVal == ""){
		
		//document.forms[1].ipfield.disabled=true;
		//document.getElementById("ipDiv").disabled=true; 
		//document.getElementById("ipfield").disabled=true;
		 $(".spanipAreaCov").attr("style","display:none");
		 $(".spanloadingForIp").attr("style","display:none");
		 $(".spancommIp").attr("style","display:none");
		 $(".spanroomType1").attr("style","display:none");
		 $(".spaninpatientCopay").attr("style","display:none");
		 $(".spanmaxChildAgeLimit").attr("style","display:none");
		 $(".spancompanionCharges").attr("style","display:none");
		 $(".spanipAreaCov1").attr("style","display:none");//bk
		 
		
		 
		document.getElementById("inpatientAreaOfCover").value="";
		document.getElementById("inpatientAreaOfCoverVariations").value="";
		document.getElementById("loadingForInpatient").value="0";
		document.getElementById("commentsInpatient").value="";
		document.getElementById("roomType").value="";
		document.getElementById("inpatientCopay").value="";
		document.getElementById("maxChildAgeLimit").value="";
		document.getElementById("companionCharges").value="";
		
		document.getElementById("inpatientAreaOfCover").disabled=true;
		document.getElementById("inpatientAreaOfCoverVariations").disabled=true;
		document.getElementById("loadingForInpatient").disabled=true;
		document.getElementById("commentsInpatient").disabled=true;
		document.getElementById("roomType").disabled=true;
		document.getElementById("inpatientCopay").disabled=true;
		document.getElementById("maxChildAgeLimit").disabled=true;
		document.getElementById("companionCharges").disabled=true;
		
		//document.getElementById("ipfield").style.backgroundColor="#d9dce0";
		document.getElementById("inpatientAreaOfCover").style.backgroundColor ="#d9dce0";
		document.getElementById("inpatientAreaOfCoverVariations").style.backgroundColor="#d9dce0";
		document.getElementById("loadingForInpatient").style.backgroundColor="#d9dce0";
		document.getElementById("commentsInpatient").style.backgroundColor="#d9dce0";
		document.getElementById("roomType").style.backgroundColor="#d9dce0";
		document.getElementById("inpatientCopay").style.backgroundColor="#d9dce0";
		document.getElementById("maxChildAgeLimit").style.backgroundColor="#d9dce0";
		document.getElementById("companionCharges").style.backgroundColor="#d9dce0";
		
	}
if(inpatientBenefitVal == 'COV'){
		
		//document.forms[1].ipfield.disabled=false;
	//	document.getElementById("ipfield").disabled=false;
	$(".spanipAreaCov").removeAttr("style");
	$(".spanroomType1").removeAttr("style");
	$(".spaninpatientCopay").removeAttr("style");
	$(".spanmaxChildAgeLimit").removeAttr("style");
	
	$(".spanipAreaCov1").removeAttr("style");//bk
	
	document.getElementById("inpatientAreaOfCover").disabled=false;
	document.getElementById("inpatientAreaOfCoverVariations").disabled=false;

	
	
	document.getElementById("roomType").disabled=false;
	document.getElementById("inpatientCopay").disabled=false;
	document.getElementById("maxChildAgeLimit").disabled=false;
	
	
	//	document.getElementById("ipfield").style.backgroundColor="white";
		document.getElementById("inpatientAreaOfCover").style.backgroundColor="white";
		document.getElementById("inpatientAreaOfCoverVariations").style.backgroundColor="white";
		
		document.getElementById("roomType").style.backgroundColor="white";
		document.getElementById("inpatientCopay").style.backgroundColor="white";
		document.getElementById("maxChildAgeLimit").style.backgroundColor="white";
		
		
		var ipArCovVar = document.getElementById("inpatientAreaOfCoverVariations").value;
		if(ipArCovVar != undefined && ipArCovVar != "" ){
			
			$(".spanloadingForIp").removeAttr("style");
			document.getElementById("loadingForInpatient").disabled=false;
			document.getElementById("commentsInpatient").disabled=false;
			document.getElementById("loadingForInpatient").style.backgroundColor="white";
			document.getElementById("commentsInpatient").style.backgroundColor="white";
			
		}
		
		var maxChildAgeLimit = document.getElementById("maxChildAgeLimit").value;
		
		if(maxChildAgeLimit != "" ){
			
			if(maxChildAgeLimit != "55" ){
				
				$(".spancompanionCharges").removeAttr("style");
				document.getElementById("companionCharges").disabled=false;
				document.getElementById("companionCharges").style.backgroundColor="white";
				
			}
			
		}

		
	}
//bk
     //if(inpatientBenefitVal == 'NCOV'){
    	
       // $(".spanipAreaCov1").attr("style","display:none");//bk
    if(inpatientBenefitVal == 'NCOV' || inpatientBenefitVal == ""){
  	  document.forms[1].maternityInpatient.value='N';
  	  document.forms[1].maternityInpatient.disabled=true;
  	  document.getElementById('maternityInpatient').value="";
  	  
  	  var maternityInpatientSel = document.getElementById("maternityInpatient");
	  var maternityInpatientVal = maternityInpatientSel.options[maternityInpatientSel.selectedIndex].value;
	  if(maternityInpatientVal == 'N' || maternityInpatientVal == ""){
		
		$(".spanttlLivMat").attr("style","display:none");
		$(".spanMatIpOp").attr("style","display:none");
		 $(".spanNrmDelLmt").attr("style","display:none");
		 $(".spanCSecLmt").attr("style","display:none");
		 $(".spanMatIpCpy").attr("style","display:none");
		 $(".spanNrmDelLmtOth").attr("style","display:none");
		 $(".spanCSecLmtOth").attr("style","display:none");
		
		document.forms[1].normalDeliveryLimit.disabled=true;
		document.forms[1].CSectionLimit.disabled=true;
		document.forms[1].normalDeliveryLimitOth.disabled=true;
		document.forms[1].CSectionLimitOth.disabled=true;
		document.forms[1].maternityInpatientCopay.disabled=true; 
		
		    document.getElementById('normalDeliveryLimit').value="";
		    document.getElementById('CSectionLimit').value="";
		    document.getElementById('normalDeliveryLimitOth').value="";
		    document.getElementById('CSectionLimitOth').value="";
		    document.getElementById('maternityInpatientCopay').value="";
		    
		    document.getElementById('normalDeliveryLimit').style.backgroundColor="#d9dce0";
		    document.getElementById('CSectionLimit').style.backgroundColor="#d9dce0";
		    document.getElementById('normalDeliveryLimitOth').style.backgroundColor="#d9dce0";
		    document.getElementById('CSectionLimitOth').style.backgroundColor="#d9dce0";
		    document.getElementById('maternityInpatientCopay').style.backgroundColor="#d9dce0";
		    document.getElementById('maternityInpatient').style.backgroundColor="#d9dce0";
	
}
    }else{
    	 document.forms[1].maternityInpatient.disabled=false;
    	 document.getElementById('maternityInpatient').style.backgroundColor="white";
    }
}

function validateOP()
{

	 var outpatientBenefitSel = document.getElementById("outpatientBenefit");
	  var outpatientBenefitVal = outpatientBenefitSel.options[outpatientBenefitSel.selectedIndex].value;
	if(outpatientBenefitVal == 'NCOV' || outpatientBenefitVal == ""){
		
	//	document.getElementById("opDiv").disabled=true;
	//	document.getElementById("opfield").disabled=true;
		 
		 $(".spanopAreaOfCov").attr("style","display:none");
		 $(".spanldForOp").attr("style","display:none");
		 $(".spanconsultCD").attr("style","display:none");
		 $(".spandrugsLimit").attr("style","display:none");
		 $(".spandrugsCopay").attr("style","display:none");
		 $(".spanlaboratoryCD").attr("style","display:none");
		 
		 $(".spanphysiLmt").attr("style","display:none");
		 $(".spanphysiCpy").attr("style","display:none");
		 $(".spanDIpCpy").attr("style","display:none");
		 
		 $(".spanopAreaOfCov1").attr("style","display:none");//bk
		 
		document.getElementById("outpatientAreaOfCover").disabled=true;
		document.getElementById("outpatientAreaOfCoverVariations").disabled=true;
		document.getElementById("loadingForOutpatient").disabled=true;
		document.getElementById("commentsOutpatient").disabled=true;
		document.getElementById("additionalHospitalCoverage").disabled=true;
	//	document.getElementById("hospitalNameOth").value="";
		document.getElementById("hosComments").disabled=true;
		document.getElementById("loadingForAddHospital").disabled=true;
		document.getElementById("consultationCD").disabled=true;
		document.getElementById("drugsLimit").disabled=true;
		document.getElementById("drugsLimitOth").disabled=true;
		document.getElementById("drugsCopay").disabled=true;
		document.getElementById("laboratoryCD").disabled=true;
		document.getElementById("physiotherapyLimit").disabled=true;
		document.getElementById("physiotherapyCopay").disabled=true;
		document.getElementById("dinpatientCopay").disabled=true;
		document.getElementById("commentsDirect").disabled=true;
		
		document.getElementById("outpatientAreaOfCover").value="";
		document.getElementById("outpatientAreaOfCoverVariations").value="";
		document.getElementById("loadingForOutpatient").value="0";
		document.getElementById("commentsOutpatient").value="";
		document.getElementById("additionalHospitalCoverage").value="";
	//	document.getElementById("hospitalNameOth").value="";
		document.getElementById("hosComments").value="";
		document.getElementById("loadingForAddHospital").value="";
		document.getElementById("consultationCD").value="";
		document.getElementById("drugsLimit").value="";
		document.getElementById("drugsLimitOth").value="";
		document.getElementById("drugsCopay").value="";
		document.getElementById("laboratoryCD").value="";
		document.getElementById("physiotherapyLimit").value="";
		document.getElementById("physiotherapyCopay").value="";
		document.getElementById("dinpatientCopay").value="";
		document.getElementById("commentsDirect").value="";
		
	//	document.getElementById("opfield").style.backgroundColor="#d9dce0";
		document.getElementById("outpatientAreaOfCover").style.backgroundColor="#d9dce0";
		document.getElementById("outpatientAreaOfCoverVariations").style.backgroundColor="#d9dce0";
		document.getElementById("loadingForOutpatient").style.backgroundColor="#d9dce0";
		document.getElementById("commentsOutpatient").style.backgroundColor="#d9dce0";
		document.getElementById("additionalHospitalCoverage").style.backgroundColor="#d9dce0";
	//	document.getElementById("hospitalNameOth").style.backgroundColor="#d9dce0";
		document.getElementById("hosComments").style.backgroundColor="#d9dce0";
		document.getElementById("loadingForAddHospital").style.backgroundColor="#d9dce0";
		document.getElementById("consultationCD").style.backgroundColor="#d9dce0";
		document.getElementById("drugsLimit").style.backgroundColor="#d9dce0";
		document.getElementById("drugsLimitOth").style.backgroundColor="#d9dce0";
		document.getElementById("drugsCopay").style.backgroundColor="#d9dce0";
		document.getElementById("laboratoryCD").style.backgroundColor="#d9dce0";
		document.getElementById("physiotherapyLimit").style.backgroundColor="#d9dce0";
		document.getElementById("physiotherapyCopay").style.backgroundColor="#d9dce0";
		document.getElementById("dinpatientCopay").style.backgroundColor="#d9dce0";
		document.getElementById("commentsDirect").style.backgroundColor="#d9dce0";
		
	}
if(outpatientBenefitVal == 'COV'){
		
//	document.getElementById("opDiv").disabled=false;
//	document.getElementById("opfield").disabled=false; spanloadingForIp
	
	$(".spanopAreaOfCov").removeAttr("style");
	$(".spanconsultCD").removeAttr("style");
	$(".spandrugsLimit").removeAttr("style");
	$(".spandrugsCopay").removeAttr("style");
	$(".spanlaboratoryCD").removeAttr("style");
	$(".spanopAreaOfCov1").removeAttr("style");//bk
	
	$(".spanphysiLmt").removeAttr("style");
	$(".spanphysiCpy").removeAttr("style");
	$(".spanDIpCpy").removeAttr("style");
	//document.getElementById("opfield").disabled=false;
	document.getElementById("outpatientAreaOfCover").disabled=false;
	document.getElementById("outpatientAreaOfCoverVariations").disabled=false;
	
	document.getElementById("additionalHospitalCoverage").disabled=false;
	//document.getElementById("hospitalNameOth").style.backgroundColor="white";
//	document.getElementById("hosComments").disabled=false;
//	document.getElementById("loadingForAddHospital").disabled=false;
	document.getElementById("consultationCD").disabled=false;
	document.getElementById("drugsLimit").disabled=false;
//	document.getElementById("drugsLimitOth").disabled=false;
	document.getElementById("drugsCopay").disabled=false;
	document.getElementById("laboratoryCD").disabled=false;
	document.getElementById("physiotherapyLimit").disabled=false;
	document.getElementById("physiotherapyCopay").disabled=false;
	document.getElementById("dinpatientCopay").disabled=false;
//	document.getElementById("commentsDirect").disabled=false;
		
	//	document.getElementById("opfield").style.backgroundColor="white";
		document.getElementById("outpatientAreaOfCover").style.backgroundColor="white";
		document.getElementById("outpatientAreaOfCoverVariations").style.backgroundColor="white";
		
		document.getElementById("additionalHospitalCoverage").style.backgroundColor="white";
		//document.getElementById("hospitalNameOth").style.backgroundColor="white";
	//	document.getElementById("hosComments").style.backgroundColor="white";
		//document.getElementById("loadingForAddHospital").style.backgroundColor="white";
		document.getElementById("consultationCD").style.backgroundColor="white";
		document.getElementById("drugsLimit").style.backgroundColor="white";
		//document.getElementById("drugsLimitOth").style.backgroundColor="white";
		document.getElementById("drugsCopay").style.backgroundColor="white";
		document.getElementById("laboratoryCD").style.backgroundColor="white";
		document.getElementById("physiotherapyLimit").style.backgroundColor="white";
		document.getElementById("physiotherapyCopay").style.backgroundColor="white";
		document.getElementById("dinpatientCopay").style.backgroundColor="white";
	//	document.getElementById("commentsDirect").style.backgroundColor="white";
		
		var opArCovVar = document.getElementById("outpatientAreaOfCoverVariations").value;
		if(opArCovVar != undefined && opArCovVar != "" ){
			
			$(".spanldForOp").removeAttr("style");
			document.getElementById("loadingForOutpatient").disabled=false;
			document.getElementById("commentsOutpatient").disabled=false;
			document.getElementById("loadingForOutpatient").style.backgroundColor="white";
			document.getElementById("commentsOutpatient").style.backgroundColor="white";
			
		}
		
		var additionalHospitalCoverage = document.getElementById("additionalHospitalCoverage").value;
		if(additionalHospitalCoverage != undefined && additionalHospitalCoverage != "" ){
			
			$(".spanldForAddHosp").removeAttr("style");
			document.getElementById("loadingForAddHospital").disabled=false;
			document.getElementById("loadingForAddHospital").style.backgroundColor="white";
			
		}
		
		var loadingForAddHospital = document.getElementById("loadingForAddHospital").value;
		if(loadingForAddHospital != undefined && loadingForAddHospital != "" ){
			
			$(".spanhosComments").removeAttr("style");
			document.getElementById("hosComments").disabled=false;
			document.getElementById("hosComments").style.backgroundColor="white";
			
		}
		
		
		var drugsLimit = document.getElementById("drugsLimit").value;
		if(drugsLimit != undefined && drugsLimit != "" ){
			
			$(".spandrugsLimitOth").removeAttr("style");
			document.getElementById("drugsLimitOth").disabled=false;
			document.getElementById("drugsLimitOth").style.backgroundColor="white";
			
		}
		
		var dinpatientCopay = document.getElementById("dinpatientCopay").value;
		if(dinpatientCopay != undefined && dinpatientCopay != "" ){
			
			$(".spancommentsDirect").removeAttr("style");
			document.getElementById("commentsDirect").disabled=false;
			document.getElementById("commentsDirect").style.backgroundColor="white";
			
		}
		
	}
	//bk
        if(outpatientBenefitVal == 'NCOV'|| outpatientBenefitVal == ""){
        	document.forms[1].maternityOutpatient.value='N';
        	document.getElementById('maternityOutpatient').value="";
        	document.forms[1].maternityOutpatient.disabled=true;	
        	var maternityOutpatientSel = document.getElementById("maternityOutpatient");
      	    var maternityOutpatientVal = maternityOutpatientSel.options[maternityOutpatientSel.selectedIndex].value;
      	    if(maternityOutpatientVal == 'N' || maternityOutpatientVal == ""){
     	
      		$(".spanMatOpVisit").attr("style","display:none");
      		$(".spanMatOpCpy").attr("style","display:none");
      		document.forms[1].maternityOutpatientVisits.disabled=true;
      		document.forms[1].maternityOutpatientCopay.disabled=true;
      		
      		document.getElementById('maternityOutpatientVisits').value="";
      		document.getElementById('maternityOutpatientCopay').value="";
      		
      		document.getElementById('maternityOutpatientVisits').style.backgroundColor="#d9dce0";
      		document.getElementById('maternityOutpatientCopay').style.backgroundColor="#d9dce0";
      		document.getElementById('maternityOutpatient').style.backgroundColor="#d9dce0";
      		
      		   var maternityInpatient = document.getElementById("maternityInpatient").value;
      		    if(maternityInpatient == 'N' || maternityInpatient == ""){
      		    	
      		    	$(".spanttlLivMat").attr("style","display:none");
      				$(".spanMatIpOp").attr("style","display:none");
      		    	
      		    	document.forms[1].totalLivesMaternity.disabled=true;
      		    	 document.getElementById('totalLivesMaternity').value="";
      		    	 document.getElementById('totalLivesMaternity').style.backgroundColor="#d9dce0";
      		    	 
      		    		document.forms[1].maternityPricing.disabled=true;
      			    	 document.getElementById('maternityPricing').value="";
      			    	 document.getElementById('maternityPricing').style.backgroundColor="#d9dce0";
      		    	
      		    }
      		
      	}
        	
        }else{
        	document.forms[1].maternityOutpatient.disabled=false;
       	  document.getElementById('maternityOutpatient').style.backgroundColor="white";
        }
}

function validateMaternityIP()
{

	/* 
    var normalDeliveryLimit = document.getElementById('normalDeliveryLimit');
    var CSectionLimit = document.getElementById('CSectionLimit');
    var maternityInpatientCopay = document.getElementById('maternityInpatientCopay');
   var normalDeliveryLimitLabel = document.getElementById('normalDeliveryLimitLabel');
    var CSectionLimitLabel = document.getElementById('CSectionLimitLabel');
    var maternityInpatientCopayLabel = document.getElementById('maternityInpatientCopayLabel');*/
    
	 var maternityInpatientSel = document.getElementById("maternityInpatient");
	  var maternityInpatientVal = maternityInpatientSel.options[maternityInpatientSel.selectedIndex].value;
	if(maternityInpatientVal == 'N' || maternityInpatientVal == ""){
		
	  
	 /*   normalDeliveryLimit.style.display = "none";
	    CSectionLimit.style.display = "none";
	    maternityInpatientCopay.style.display = "none";
	    
	    normalDeliveryLimitLabel.style.display = "none";
	    CSectionLimitLabel.style.display = "none";
	    maternityInpatientCopayLabel.style.display = "none";*/
		$(".spanttlLivMat").attr("style","display:none");
		$(".spanMatIpOp").attr("style","display:none");
		 $(".spanNrmDelLmt").attr("style","display:none");
		 $(".spanCSecLmt").attr("style","display:none");
		 $(".spanMatIpCpy").attr("style","display:none");
		 $(".spanNrmDelLmtOth").attr("style","display:none");
		 $(".spanCSecLmtOth").attr("style","display:none");
		
		document.forms[1].normalDeliveryLimit.disabled=true;
		document.forms[1].CSectionLimit.disabled=true;
		document.forms[1].normalDeliveryLimitOth.disabled=true;
		document.forms[1].CSectionLimitOth.disabled=true;
		document.forms[1].maternityInpatientCopay.disabled=true; 
		
		  document.getElementById('normalDeliveryLimit').value="";
		    document.getElementById('CSectionLimit').value="";
		    document.getElementById('normalDeliveryLimitOth').value="";
		    document.getElementById('CSectionLimitOth').value="";
		    document.getElementById('maternityInpatientCopay').value="";
		    
		    document.getElementById('normalDeliveryLimit').style.backgroundColor="#d9dce0";
		    document.getElementById('CSectionLimit').style.backgroundColor="#d9dce0";
		    document.getElementById('normalDeliveryLimitOth').style.backgroundColor="#d9dce0";
		    document.getElementById('CSectionLimitOth').style.backgroundColor="#d9dce0";
		    document.getElementById('maternityInpatientCopay').style.backgroundColor="#d9dce0";
		    
		  /*  document.getElementById('normalDeliveryLimitLabel').style.backgroundColor="#d9dce0";
		    document.getElementById('CSectionLimitLabel').style.backgroundColor="#d9dce0";
		    document.getElementById('maternityInpatientCopayLabel').style.backgroundColor="#d9dce0";*/
		    
		    var maternityOutpatient = document.getElementById("maternityOutpatient").value;
		    if(maternityOutpatient == 'N' || maternityOutpatient == ""){
		    	
		    	document.forms[1].totalLivesMaternity.disabled=true;
		    	 document.getElementById('totalLivesMaternity').value="";
		    	 document.getElementById('totalLivesMaternity').style.backgroundColor="#d9dce0";
		    	 
		    		document.forms[1].maternityPricing.disabled=true;
			    	 document.getElementById('maternityPricing').value="";
			    	 document.getElementById('maternityPricing').style.backgroundColor="#d9dce0";
			    	 
			    	 /*$(".spanPRA").attr("style","display:none");
			    		 document.forms[1].premiumRefundApplicable.disabled=true;
				    	 document.getElementById('premiumRefundApplicable').value="";
				    	 document.getElementById('premiumRefundApplicable').style.backgroundColor="#d9dce0";*/ //bk
		    	
		    }
		    
		 
		
	}
if(maternityInpatientVal == 'Y'){
	
/*	normalDeliveryLimit.style.display = "block";
    CSectionLimit.style.display = "block";
    maternityInpatientCopay.style.display = "block";
    
    normalDeliveryLimitLabel.style.display = "block";
    CSectionLimitLabel.style.display = "block";
    maternityInpatientCopayLabel.style.display = "block";*/
	$(".spanttlLivMat").removeAttr("style");
	$(".spanMatIpOp").removeAttr("style");
	$(".spanNrmDelLmt").removeAttr("style");
	$(".spanCSecLmt").removeAttr("style");
	$(".spanMatIpCpy").removeAttr("style");
	$(".spanPRA").removeAttr("style");
	document.forms[1].normalDeliveryLimit.disabled=false;
	document.forms[1].CSectionLimit.disabled=false;
	document.forms[1].maternityInpatientCopay.disabled=false;
	document.forms[1].premiumRefundApplicable.disabled=false;
	
	  document.getElementById('normalDeliveryLimit').style.backgroundColor="white";
	    document.getElementById('CSectionLimit').style.backgroundColor="white";
	    document.getElementById('maternityInpatientCopay').style.backgroundColor="white";
	    document.getElementById('premiumRefundApplicable').style.backgroundColor="white";
	    
	 /*   document.getElementById('normalDeliveryLimitLabel').style.backgroundColor="white";
	    document.getElementById('CSectionLimitLabel').style.backgroundColor="white";
	    document.getElementById('maternityInpatientCopayLabel').style.backgroundColor="white";*/
	    
		document.forms[1].totalLivesMaternity.disabled=false;
	   	 document.getElementById('totalLivesMaternity').style.backgroundColor="white";
	   	 
	 	document.forms[1].maternityPricing.disabled=false;
	   	 document.getElementById('maternityPricing').style.backgroundColor="white";
	    	
	}
   
    
}

function validateMaternityOP()
{

	/*var maternityOutpatientVisits = document.getElementById('maternityOutpatientVisits');
	var maternityOutpatientCopay = document.getElementById('maternityOutpatientCopay');*/
	 var maternityOutpatientSel = document.getElementById("maternityOutpatient");
	  var maternityOutpatientVal = maternityOutpatientSel.options[maternityOutpatientSel.selectedIndex].value;
	   if(maternityOutpatientVal == 'N' || maternityOutpatientVal == ""){
		
		
	/*	maternityOutpatientVisits.style.display = "none";
		maternityOutpatientCopay.style.display = "none";*/
	
		$(".spanMatOpVisit").attr("style","display:none");
		$(".spanMatOpCpy").attr("style","display:none");
		document.forms[1].maternityOutpatientVisits.disabled=true;
		document.forms[1].maternityOutpatientCopay.disabled=true;
		
		document.getElementById('maternityOutpatientVisits').value="";
		document.getElementById('maternityOutpatientCopay').value="";
		
		document.getElementById('maternityOutpatientVisits').style.backgroundColor="#d9dce0";
		document.getElementById('maternityOutpatientCopay').style.backgroundColor="#d9dce0";
		
		/*document.getElementById('maternityOutpatientVisitsLabel').style.backgroundColor="#d9dce0";
		document.getElementById('maternityOutpatientCopayLabel').style.backgroundColor="#d9dce0";*/
		
		   var maternityInpatient = document.getElementById("maternityInpatient").value;
		    if(maternityInpatient == 'N' || maternityInpatient == ""){
		    	
		    	$(".spanttlLivMat").attr("style","display:none");
				$(".spanMatIpOp").attr("style","display:none");
		    	
		    	document.forms[1].totalLivesMaternity.disabled=true;
		    	 document.getElementById('totalLivesMaternity').value="";
		    	 document.getElementById('totalLivesMaternity').style.backgroundColor="#d9dce0";
		    	 
		    		document.forms[1].maternityPricing.disabled=true;
			    	 document.getElementById('maternityPricing').value="";
			    	 document.getElementById('maternityPricing').style.backgroundColor="#d9dce0";
			    	 
			    	 /*$(".spanPRA").attr("style","display:none");
			    		document.forms[1].premiumRefundApplicable.disabled=true;
				    	 document.getElementById('premiumRefundApplicable').value="";
				    	 document.getElementById('premiumRefundApplicable').style.backgroundColor="#d9dce0";*/ //bk
		    	
		    }
		
	}
if(maternityOutpatientVal == 'Y'){
	
	
	/*maternityOutpatientVisits.style.display = "block";
	maternityOutpatientCopay.style.display = "block";*/
		
	$(".spanttlLivMat").removeAttr("style");
	$(".spanMatIpOp").removeAttr("style");
	$(".spanMatOpVisit").removeAttr("style");
	$(".spanMatOpCpy").removeAttr("style");
	$(".spanPRA").removeAttr("style");
		document.forms[1].maternityOutpatientVisits.disabled=false;
		document.forms[1].maternityOutpatientCopay.disabled=false;
		document.forms[1].premiumRefundApplicable.disabled=false;
		
		document.getElementById('maternityOutpatientVisits').style.backgroundColor="white";
		document.getElementById('maternityOutpatientCopay').style.backgroundColor="white";
		document.getElementById('premiumRefundApplicable').style.backgroundColor="white";
		
	/*	document.getElementById('maternityOutpatientVisitsLabel').style.backgroundColor="white";
		document.getElementById('maternityOutpatientCopayLabel').style.backgroundColor="white";*/
		
		document.forms[1].totalLivesMaternity.disabled=false;
   	 document.getElementById('totalLivesMaternity').style.backgroundColor="white";
   	 
 	document.forms[1].maternityPricing.disabled=false;
  	 document.getElementById('maternityPricing').style.backgroundColor="white";
		
	}
	
}

// govind validation end

function onPartialSave(obj){	
	var singlebutton = obj;  
	 trimForm(document.forms[1]);	
	
	// setDescValues();
/*	 if( document.forms[1].renewalYN.value == "Y" && document.getElementById("previousPolicyNo").value == ""){
	 if (confirm('Previous policy number has not been entered. Do you want to proceed?')) {
		   document.getElementById("previousPolicyNo").value = "";
		} else {
			document.getElementById("previousPolicyNo").focus();
		} 
	 }*/
	 
	 
/*	 var trendFactor = parseFloat(document.forms[1].trendFactor.value);
	if(trendFactor > 11){
		alert("Trend should not exceed than 11%");
		document.forms[1].trendFactor.focus();
		return false;
	}*/
	/*if(document.forms[1].groupProfileSeqID.value == 0)
	{
	alert("Please note the pricing reference number");
	}*/
	 
     var SelincomeGroup = document.getElementById("incomeGroup");
     var incomeGroup = SelincomeGroup.options[SelincomeGroup.selectedIndex].text;
	 
	 if(!JS_SecondSubmit)
    {
		 
	   document.forms[1].mode.value="doSave";
	   document.forms[1].action="/SwInsPricingActionPartialAdd.do?singlebutton="+singlebutton;
	   JS_SecondSubmit=true;
	   document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
	
}//end of onSave()

function onIncomeprofile()
{
	if(!JS_SecondSubmit)
	 {
		//document.forms[1].reset();
		
		if(document.forms[1].groupProfileSeqID.value == 0)
			{
				alert('Please save before proceed');											
			return false;
			}
		if(!TrackChanges()) return false; 
	    document.forms[1].rownum.value = "";
	    document.forms[1].tab.value ="Income Profile";
	    document.forms[1].mode.value = "doIncomeProfile";
	    document.forms[1].action = "/SwInsPricingActionIncome.do";
	    document.forms[1].submit();
	    
	 }
}


function onSaveIncome(obj){	
	
	 if(!JS_SecondSubmit){
		
		 var totalLives = parseInt(document.forms[1].totalCovedLives.value);
		 var table1totalLive = parseInt(document.forms[1].table1totalLives.value);
		 
		 var totalLivesmaternity  = parseInt(document.forms[1].totalLivesMaternity.value);
		 var table2totalLive = parseInt(document.forms[1].table2totalLives.value);
		 
		 var table3totalLive = parseInt(document.forms[1].table3totalLives.value);
		 
		 var table4totalLive = parseInt(document.forms[1].table4totalLives.value);
			
			
			if(totalLives!= table1totalLive)	{
				alert("Total covered lives and Total covered lives-Inpatient/Outpatient should be same");
				//field.value = "";
				return false;
			}
			
			if(totalLivesmaternity> table2totalLive)	{
				alert("Total covered lives maternity' should not be higher than the Total covered lives'");
				//field.value = "";
				return false;
			}
			
			if(totalLives!= table3totalLive)	{
				alert("'Covered lives by Income band' should be equal to 'Total covered lives'");
				//field.value = "";
				return false;
			}
			
			if(totalLives!= table4totalLive)	{
				alert("'Covered lives by Nationality' should be equal to 'Total covered lives'");
				//field.value = "";
				return false;
			}
			
	   var singlebutton = obj;
	   if(singlebutton =="saveProceed"){
			document.forms[1].tab.value="Plan design";
		   }
       document.forms[1].mode.value="doSaveIncome";
	   document.forms[1].action="/SwInsPricingActionIncome.do?singlebutton="+singlebutton;
	   JS_SecondSubmit=true;
	   document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
	
}
function showTemplate()
{
	
	   document.forms[1].mode.value="doshowTemplate";
	    document.forms[1].action = "/SwInsPricingActionIncome.do";
	    document.forms[1].submit();
}

function onSaveIncomePartial(obj){	
	var singlebutton = obj;

		 trimForm(document.forms[1]);	
		 if(!JS_SecondSubmit)
	   {
		  if(singlebutton =="saveProceed"){
			document.forms[1].tab.value="Plan design";
		   }
		   document.forms[1].mode.value="doSaveIncome";
		   document.forms[1].action="/SwInsPricingActionIncomePartial.do?singlebutton="+singlebutton;
		   JS_SecondSubmit=true;
		   document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
		
	}


function onCloseIncome()
{
	//alert("onCloseIncome");
	document.forms[1].reset();
    document.forms[1].rownum.value = "";
    document.forms[1].tab.value ="Group Profile";
    document.forms[1].mode.value = "doCloseIncome";
    document.forms[1].action = "/SwInsPricingActionClose.do";
    document.forms[1].submit();
}

function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/SwInsPricingAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

function edit(rownum)
{
    document.forms[1].mode.value="doEditIncome";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="Group Profile";
    document.forms[1].action = "/SwInsPricingAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function fetchScreen1()
{
	//alert("fetchScreen1");
	
    document.forms[1].mode.value="fetchScreen1";
   // document.forms[1].rownum.value=rownum;
    document.forms[1].rownum.value=rownum;
    document.forms[1].rownum.value=rownum;
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="Group Profile";
    document.forms[1].action = "/SwInsPricingAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/SwInsPricingAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/SwInsPricingAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/SwInsPricingAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onSearch()
{
	
	if(!JS_SecondSubmit)
	 {
			document.forms[1].mode.value = "doSearch";
			document.forms[1].action = "/SwInsPricingAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
	 }
	
}//end of onSearch()
 

function onViewPlanDesign()
{
	if(!TrackChanges()) return false; 
	if(!JS_SecondSubmit)
	 {
	document.forms[1].reset();
	document.forms[1].mode.value ="doDefault";
	document.forms[1].tab.value="Plan design";
    document.forms[1].action = "/SwPlanDesignConfigurationAction.do";
    document.forms[1].submit();
	 }
}//end of PressBackWard()


//function onViewDocument(filename)
function onViewDocument()
{
   
	
	var file1 = document.getElementById("attachmentname1").value;
	var pricingrRefNo=document.getElementById("pricingRefno").value;
	
   	//var openPage = "/SwInsPricingAction.do?mode=doViewUploadDocs&filename="+filename;	
   //var fileName1 = document.forms[1].fileName1.value;
	
 	 var openPage = "/SwInsPricingAction.do?mode=doViewUploadDocs1&fileName1="+file1+"&pricingrRefNo="+pricingrRefNo;	
   	var w = screen.availWidth - 10;
  	var h = screen.availHeight - 49;
  	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
  	window.open(openPage,'',features);
}

//file-2
function onViewDocument2()
{
   
	
	var file2 = document.getElementById("attachmentname2").value;
	var pricingrRefNo=document.getElementById("pricingRefno").value;
 	 var openPage = "/SwInsPricingAction.do?mode=doViewUploadDocs2&fileName1="+file2+"&pricingrRefNo="+pricingrRefNo;	 
   	var w = screen.availWidth - 10;
  	var h = screen.availHeight - 49;
  	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
  	window.open(openPage,'',features);
}
//file-3
function onViewDocument3()
{
   
	var file3 = document.getElementById("attachmentname3").value;
	var pricingrRefNo=document.getElementById("pricingRefno").value;
 	 var openPage = "/SwInsPricingAction.do?mode=doViewUploadDocs3&fileName1="+file3+"&pricingrRefNo="+pricingrRefNo;	
   	var w = screen.availWidth - 10;
  	var h = screen.availHeight - 49;
  	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
  	window.open(openPage,'',features);
}

//file-4

function onViewDocument4()
{
   
	var file4 = document.getElementById("attachmentname4").value;
	var pricingrRefNo=document.getElementById("pricingRefno").value;
 	 var openPage = "/SwInsPricingAction.do?mode=doViewUploadDocs4&fileName1="+file4+"&pricingrRefNo="+pricingrRefNo;	
   	var w = screen.availWidth - 10;
  	var h = screen.availHeight - 49;
  	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
  	window.open(openPage,'',features);
}

//file-5
function onViewDocument5()
{
   
	 var file5 = document.getElementById("attachmentname5").value;
	 var pricingrRefNo=document.getElementById("pricingRefno").value;
 	 var openPage = "/SwInsPricingAction.do?mode=doViewUploadDocs5&fileName1="+file5+"&pricingrRefNo="+pricingrRefNo;	
   	var w = screen.availWidth - 10;
  	var h = screen.availHeight - 49;
  	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
  	window.open(openPage,'',features);
}
//function to Change the corporate.
function changeCorporate()
{
/*	if( document.forms[1].renewalYN.value == "N"){
	alert("Client code applicable only for renewal policy ");
	}else{*/
	
	document.forms[1].mode.value="doChangeCorporate";
	document.forms[1].child.value="GroupList";
	document.forms[1].action="/clientDetailAction.do";
	document.forms[1].submit();
	//}
}//end of changeCorporate()


function checkTotalForTable1() {
	
	var totalCoveredLives=document.forms[1].totalCovedLives.value;
	var totalValue=0;
	for(var i=0;1>0;i++){
		
		var liveBoxObj=document.getElementById("colTotCovrLivesID"+i);
		if(liveBoxObj==undefined||liveBoxObj==null){
			break;
		}
		var boxValue=liveBoxObj.value;
		if(boxValue!=null&&boxValue!=""){
			totalValue=parseInt(boxValue)+parseInt(totalValue);
			
			if(parseInt(totalValue) > parseInt(totalCoveredLives)){
				alert("Covered lives by age band  - Inpatient and Outpatient' should be equal to 'Total covered lives'");
				liveBoxObj.value="";
				//document.getElementById("frmSwIncomeProfileID").value="";
				break;return;
			}
			
			document.getElementById("frmSwIncomeProfileID").value=totalValue;
            }			
	    
	}
}
//table-2

function checkTotalForTable2() {	
	var totalValue=0;
	var totalLivesMaternity=document.forms[1].totalLivesMaternity.value;
	for(var i=0;1>0;i++){
		
		var liveBoxObj=document.getElementById("colTotCovrLivesID2"+i);
		
		if(liveBoxObj==undefined||liveBoxObj==null){
			
			break;
		}
		var boxValue=liveBoxObj.value;
		if(boxValue!=null&&boxValue!=""){
			totalValue=parseInt(boxValue)+parseInt(totalValue);
			
			if((parseInt(totalValue)>parseInt(totalLivesMaternity)) || totalLivesMaternity == ""){
				//alert("Total covered lives-Maternity Are Not Matching");
				alert("‘Covered lives by age band – Maternity’ should be equal to ‘Total covered lives eligible for maternity’")
				liveBoxObj.value="";
			//	document.getElementById("frmSwIncomeProfileID2").value="";
				break;return;
			}
			
			document.getElementById("frmSwIncomeProfileID2").value=totalValue;
            }			
	    
	}
}
//table-3
function checkTotalForTable3() {	
	var totalCoveredLives=document.forms[1].totalCovedLives.value;
	var totalValue=0;
	for(var i=0;1>0;i++){
		
		var liveBoxObj=document.getElementById("colTotCovrLivesID3"+i);
		
		if(liveBoxObj==undefined||liveBoxObj==null){
			break;
		}
		var boxValue=liveBoxObj.value;
		if(boxValue!=null&&boxValue!=""){
			totalValue=parseInt(boxValue)+parseInt(totalValue);
			
			if(parseInt(totalValue) > parseInt(totalCoveredLives)){
				alert("'Covered lives by Income band' should be equal to 'Total covered lives'");
				liveBoxObj.value="";
			//	document.getElementById("frmSwIncomeProfileID3").value="";
				break;return;
			}
			
			document.getElementById("frmSwIncomeProfileID3").value=totalValue;
            }			
	    
	}
}

//table-4
function checkTotalForTable4() {
	var totalCoveredLives=document.forms[1].totalCovedLives.value;
	var totalValue=0;
	for(var i=0;1>0;i++){
		
		var liveBoxObj=document.getElementById("colTotCovrLivesID4"+i);
		
		if(liveBoxObj==undefined||liveBoxObj==null){
			break;
		}
		var boxValue=liveBoxObj.value;
		if(boxValue!=null&&boxValue!=""){
			totalValue=parseInt(boxValue)+parseInt(totalValue);
			
			if(parseInt(totalValue)>parseInt(totalCoveredLives)){
				alert("'Covered lives by Nationality' should be equal to 'Total covered lives'");
				liveBoxObj.value="";
			//	document.getElementById("frmSwIncomeProfileID4").value="";
				break;return;
			}
			
			document.getElementById("frmSwIncomeProfileID4").value=totalValue;
            }			
	    
	}
}



function getInsuranceCompanyName(){
	//alert('getInsuranceCompanyName');
	 var providerAuthority= document.getElementById("authorityType").value;
	var  myselect1=document.getElementById("insuranceCompanyName");
	//alert('providerAuthority:'+providerAuthority);
	myselect1.disabled = false;
	if(providerAuthority == 'HMO'){
		
		//alert('HMO plan will be addressed in Phase 2');
		alert('"The tool currently does not address HMO. For HMO renewals, please enter the closestinput options available,feed in the projected claims cost working for the current running policy & finalize the premium by allowing 100% credibility to the current running policy."');
		document.getElementById("authorityType").value = "HMO";
	//	providerAuthority.value="";
		//myselect1.disabled = true;
		return false;
	}
	
	
	while (myselect1.hasChildNodes()) {   
  	    myselect1.removeChild(myselect1.firstChild);
    }
	 myselect1.options.add(new Option("Select from list",""));
     var  path="/asynchronAction.do?mode=getInsuranceCompanyName&providerAuthority="+providerAuthority;		                 

	 $.ajax({
	     url :path,
	     dataType:"text",
	     success : function(data) {
	    	
	     var res1 = data.split("&");
	     for(var i=0;i<res1.length-1;i++){   	    	    
	     var res2=res1[i].split("@");
	        myselect1.options.add(new Option(res2[1],res2[0]));  	                 
	     }//for()
	     }//function(data)
	 });
 	
}//getInsuranceCompany

function validateInsComp(){
	
	 var providerAuthority= document.getElementById("authorityType").value;
	/*	var  myselect1=document.getElementById("insuranceCompanyName");
	//	alert('providerAuthority:'+providerAuthority);
		myselect1.disabled = false;
		if(providerAuthority == 'HMO'){
			myselect1.value="";
			myselect1.disabled = true;*/
	//	}
}

// govind

//getInsuranceCompany Name By  healthAuthority
function getInsuranceCompany(){
	
	 var providerAuthority= document.getElementById("authorityType").value;
	var  myselect1=document.getElementById("insSeqId");
	
	while (myselect1.hasChildNodes()) {   
   	    myselect1.removeChild(myselect1.firstChild);
     }
	 myselect1.options.add(new Option("Select from list",""));
      var  path="/asynchronAction.do?mode=getInsuranceCompany&providerAuthority="+providerAuthority;		                 

 	 $.ajax({
 	     url :path,
 	     dataType:"text",
 	     success : function(data) {
 	    	
 	     var res1 = data.split("&");
 	     for(var i=0;i<res1.length-1;i++){   	    	    
 	     var res2=res1[i].split("@");
 	        myselect1.options.add(new Option(res2[1],res2[0]));  	                 
 	     }//for()
 	     }//function(data)
 	 });
  	
}//getInsuranceCompany


function changeInsCmp()
{
    trimForm(document.forms[1]);
    if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value="changeInsCmpPT";
		document.forms[1].action="/EditProductAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of changeInsCmp()

function showMandatory()
{
	//alert('showMandatory');
	
	var clientType = document.getElementById("renewalYN").value;

		if(clientType == "REN"){
			
			$(".spanportfloLevel").removeAttr("style");
			
		}else {
			
			 $(".spanportfloLevel").attr("style","display:none");
		}
		
	
	
	var maxBenifitList = document.getElementById("maxBenifitList").value;
	
		if(maxBenifitList == "176"){
			
			$(".spanmaxBeneLimitOth").removeAttr("style");
			
		}else {
			
			 $(".spanmaxBeneLimitOth").attr("style","display:none");
		}
		
		var inpatientBenefit = document.getElementById("inpatientBenefit").value;
		
		if(inpatientBenefit == "COV"){
			
			$(".spanipAreaCov").removeAttr("style");
			$(".spanroomType1").removeAttr("style");
			$(".spaninpatientCopay").removeAttr("style");
			$(".spanmaxChildAgeLimit").removeAttr("style");
			$(".spancompanionCharges").removeAttr("style");
			
		}else {
			
			 $(".spanipAreaCov").attr("style","display:none");
			 $(".spanroomType1").attr("style","display:none");
			 $(".spaninpatientCopay").attr("style","display:none");
			 $(".spanmaxChildAgeLimit").attr("style","display:none");
			 $(".spancompanionCharges").attr("style","display:none");
		}
		
	var inpatientAreaOfCoverVariations = document.getElementById("inpatientAreaOfCoverVariations").value;
		
		if(inpatientAreaOfCoverVariations != ""){
			
			$(".spanloadingForIp").removeAttr("style");
			
		}else {
			
			 $(".spanloadingForIp").attr("style","display:none");
		}
		
		var outpatientBenefit = document.getElementById("outpatientBenefit").value;
		
		if(outpatientBenefit == "COV"){
			
			$(".spanopAreaOfCov").removeAttr("style");
			$(".spanconsultCD").removeAttr("style");
			$(".spandrugsLimit").removeAttr("style");
			$(".spandrugsCopay").removeAttr("style");
			$(".spanlaboratoryCD").removeAttr("style");
			
			$(".spanphysiLmt").removeAttr("style");
			$(".spanphysiCpy").removeAttr("style");
			$(".spanDIpCpy").removeAttr("style");
			
		}else {
			
			 $(".spanopAreaOfCov").attr("style","display:none");
			 $(".spanconsultCD").attr("style","display:none");
			 $(".spandrugsLimit").attr("style","display:none");
			 $(".spandrugsCopay").attr("style","display:none");
			 $(".spanlaboratoryCD").attr("style","display:none");
			 
			 $(".spanphysiLmt").attr("style","display:none");
			 $(".spanphysiCpy").attr("style","display:none");
			 $(".spanDIpCpy").attr("style","display:none");
		}
		
	var outpatientAreaOfCoverVariations = document.getElementById("outpatientAreaOfCoverVariations").value;
		
		if(outpatientAreaOfCoverVariations != ""){
			
			$(".spanldForOp").removeAttr("style");
			
		}else {
			
			 $(".spanldForOp").attr("style","display:none");
		}
		
	var loadingForOutpatient = document.getElementById("loadingForOutpatient").value;
		
		if(loadingForOutpatient != ""){
			
			$(".spancommOp").removeAttr("style");
			
		}else {
			
			 $(".spancommOp").attr("style","display:none");
		}
		
	var loadingForInpatient = document.getElementById("loadingForInpatient").value;
		
		if(loadingForInpatient != ""){
			
			$(".spancommIp").removeAttr("style");
			
		}else {
			
			 $(".spancommIp").attr("style","display:none");
		}
		
	var additionalHospitalCoverage = document.getElementById("additionalHospitalCoverage").value;
		
		if(additionalHospitalCoverage != ""){
			
			$(".spanldForAddHosp").removeAttr("style");
			
		}else {
			
			 $(".spanldForAddHosp").attr("style","display:none");
		}
		
	var loadingForAddHospital = document.getElementById("loadingForAddHospital").value;
		
		if(loadingForAddHospital != ""){
			
			$(".spanhosComments").removeAttr("style");
			
		}else {
			
			 $(".spanhosComments").attr("style","display:none");
		}
		
		var drugsLimit = document.getElementById("drugsLimit").value;
		
		if(drugsLimit == "177"){
			
			$(".spandrugsLimitOth").removeAttr("style");
			
		}else {
			
			 $(".spandrugsLimitOth").attr("style","display:none");
		}
		
	var dinpatientCopay = document.getElementById("dinpatientCopay").value;
		
		if(dinpatientCopay == "148"){
			
			$(".spancommentsDirect").removeAttr("style");
			
		}else {
			
			 $(".spancommentsDirect").attr("style","display:none");
		}
		
		var maternityInpatient = document.getElementById("maternityInpatient").value;
		var maternityOutpatient = document.getElementById("maternityOutpatient").value;
		
		if(maternityInpatient == "Y"){
			
			$(".spanttlLivMat").removeAttr("style");
			$(".spanMatIpOp").removeAttr("style");
			
			$(".spanNrmDelLmt").removeAttr("style");
			$(".spanCSecLmt").removeAttr("style");
			$(".spanMatIpCpy").removeAttr("style");
			
		}else {
			
			 $(".spanttlLivMat").attr("style","display:none");
			 $(".spanMatIpOp").attr("style","display:none");
			 
			 $(".spanNrmDelLmt").attr("style","display:none");
			 $(".spanCSecLmt").attr("style","display:none");
			 $(".spanMatIpCpy").attr("style","display:none");
			 
		}
		
	if(maternityOutpatient == "Y"){
			
			$(".spanttlLivMat").removeAttr("style");
			$(".spanMatIpOp").removeAttr("style");
			$(".spanMatOpVisit").removeAttr("style");
			$(".spanMatOpCpy").removeAttr("style");
			
		}else {
			
			 $(".spanttlLivMat").attr("style","display:none");
			 $(".spanMatIpOp").attr("style","display:none");
			 $(".spanMatOpVisit").attr("style","display:none");
			 $(".spanMatOpCpy").attr("style","display:none");
		}
	
	var normalDeliveryLimit = document.getElementById("normalDeliveryLimit").value;
	var CSectionLimit = document.getElementById("CSectionLimit").value;
	
	if(normalDeliveryLimit == "178"){
		
		$(".spanNrmDelLmtOth").removeAttr("style");
		
		
	}else {
		
		 $(".spanNrmDelLmtOth").attr("style","display:none");
	
	}
	
	if(CSectionLimit == "179"){
		
		$(".spanCSecLmtOth").removeAttr("style");
		
		
	}else {
		
		 $(".spanCSecLmtOth").attr("style","display:none");
		
	}  
	
	var insuranceCompanyName = document.getElementById("insuranceCompanyName").value;
	
	if(insuranceCompanyName == "OTHERS"){
		
		$(".spanIncCompOth").removeAttr("style");
		
		
	}else {
		
		 $(".spanIncCompOth").attr("style","display:none");
		
	} 
	
}//end of changeInsCmp()

function saveConfirmation(msg) {
	
	var confYN=confirm(msg);
	
	if(confYN){
	    document.forms[1].mode.value = "confirmationSave";
	    document.forms[1].action = "/SwInsPricingActionAdd.do";
	    document.forms[1].submit();
	}
}




function isNumericOnlyPT(field) {

    var re = /^[0-9]*$/;
    if (!re.test(field.value)) {
    	if(field.id == "maxBeneLimitOth"){
    		
    		alert("MBL must be positive integer!");
    	}
    	
    	else if(field.id == "normalDeliveryLimitOth"){
		
    		alert("Normal delivery limit  must be positive integer!");
    	}
	
    	else if(field.id == "CSectionLimitOth"){
		
    		alert("C - Section limit  must be positive integer!");
    	}
    	else if(field.id == "totalCovedLives"){
		
		alert("Total covered lives  must be positive integer!");
	}
      	else if(field.id == "loadingForInpatient"){
    		
    		alert("Loading for Inpatient area of cover variations  must be positive integer!");
    	}
    	
  	else if(field.id == "loadingForOutpatient"){
    		
    		alert("Loading for Outpatient area of cover variations  must be positive integer!");
    	}
    	
	else if(field.id == "drugsLimitOth"){
		
		alert("Drugs limit  must be positive integer!");
	}
	else if(field.id == "totalLivesMaternity"){
		
		alert("Total covered lives eligible for maternity  must be positive integer!");
	}
	else if(field.id == "totalLivesMaternity"){
		
		alert("Total covered lives eligible for maternity  must be positive integer!");
	}
          
      //  alert("Data entered must be Numeric and Positive Numbers!");
		field.focus();
		field.value="";
		return false;
    }

}

function isNumericPT(field) {
    var re = /^[0-9]*\.*[0-9]*$/;
    if (!re.test(field.value)) {
        alert("Data entered must be Numeric!");
		field.focus();
		field.value="";
		return false;
    }
    
}

function incomeGrpSel(){
	
	var screen1incomeGrp = document.getElementById("incomeGroup").value;
	for(i=0;i<2;i++){
		var incGrp = document.getElementById("incomeGrpId"+i).value;
		
		if(incGrp != screen1incomeGrp){
			
			document.getElementById("colTotCovrLivesID3"+i).disabled=true;
	    	// document.getElementById("colTotCovrLivesID3"+i).value="";
	    	 document.getElementById("colTotCovrLivesID3"+i).style.backgroundColor="#d9dce0";
			
		}
		
	}
	
}

function dateValidate(objDate) {
	var pattern =/^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
    var dateInput1 = objDate.value;
    if (!pattern.test(dateInput1)){
        alert("Date format should be (dd/mm/yyyy)");
        objDate.value="";
        objDate.focus();
        return false;
    }

}

function fetchScreen1()
{
	
	// start
	
	
	var renewalYN = document.getElementById("renewalYN").value;
	var clientcodeId = document.getElementById("clientcodeId").value;
	var clientName = document.getElementById("clientName").value;
	var authorityType = document.getElementById("authorityType").value;
	var administrativeServiceType = document.getElementById("administrativeServiceType").value;
	var insuranceCompanyName = document.getElementById("insuranceCompanyName").value;
	var insuranceCompanyNameOth = document.getElementById("insuranceCompanyNameOth").value;
	var existCompProd1 = document.getElementById("existCompProd1").value;
	var existCompProd2 = document.getElementById("existCompProd2").value;
	var coverStartDate = document.getElementById("coverStartDate").value;
	var coverEndDate = document.getElementById("coverEndDate").value;
	var totalCovedLives = document.getElementById("totalCovedLives").value;
	
	// end
	
	var portVal = document.getElementById("portfloLevel").value;
	var incVal = document.getElementById("incomeGroup").value;
	
	var SelportfloLevel = document.getElementById("portfloLevel");
	var portfloLevel = SelportfloLevel.options[SelportfloLevel.selectedIndex].text;
	
	var previousPolicyNo = document.getElementById("previousPolicyNo").value;
	var previousProductName = document.getElementById("previousProductName").value;
	
	var SelincomeGroup = document.getElementById("incomeGroup");
	var incomeGroup = SelincomeGroup.options[SelincomeGroup.selectedIndex].text;
	
	 document.forms[1].mode.value="fetchScreen1";
    
   
  //  var param2 = "&renewalYN="+renewalYN+"&clientcodeId="+clientcodeId+"&clientName="+clientName+"&authorityType="+authorityType+"&administrativeServiceType="+administrativeServiceType+"&insuranceCompanyName="+insuranceCompanyName"&insuranceCompanyNameOth="+insuranceCompanyNameOth+"&existCompProd1="+existCompProd1+"&existCompProd2="+existCompProd2+"&coverStartDate="+coverStartDate+"&coverEndDate="+coverEndDate+"&totalCovedLives="+totalCovedLives;
    // portfloLevel="+portfloLevel+"&previousPolicyNo="+previousPolicyNo+"&previousProductName="+previousProductName+"&incomeGroup="+incomeGroup+"&portVal="+portVal+"&incVal="+incVal;

    document.forms[1].tab.value="Group Profile";
   /* document.forms[1].action = "/SwInsPricingAction.do?portfloLevel="+portfloLevel+"&previousPolicyNo="+previousPolicyNo+"&previousProductName="+previousProductName+"&incomeGroup="+incomeGroup+"&portVal="+portVal+"&incVal="+incVal+"&renewalYN="+renewalYN+"&clientcodeId="+clientcodeId+"" +
    		"&clientName="+clientName+"&authorityType="+authorityType+"&administrativeServiceType="+administrativeServiceType+"&insuranceCompanyName="+insuranceCompanyName+"&insuranceCompanyNameOth="+insuranceCompanyNameOth+"&existCompProd1="+existCompProd1+"&existCompProd2="+existCompProd2+"&coverStartDate="+coverStartDate+"&coverEndDate="+coverEndDate+"&totalCovedLives="+totalCovedLives; 
    */
    document.forms[1].action = "/SwInsPricingAction.do?portfloLevel="+portfloLevel+"&previousPolicyNo="+previousPolicyNo+"&previousProductName="+previousProductName+"&incomeGroup="+incomeGroup+"&portVal="+portVal+"&incVal="+incVal+"&renewalYN="+renewalYN+"&clientcodeId="+clientcodeId+"" +
	"&authorityType="+authorityType+"&administrativeServiceType="+administrativeServiceType+"&insuranceCompanyName="+insuranceCompanyName+"&insuranceCompanyNameOth="+insuranceCompanyNameOth+"&existCompProd1="+existCompProd1+"&existCompProd2="+existCompProd2+"&coverStartDate="+coverStartDate+"&coverEndDate="+coverEndDate+"&totalCovedLives="+totalCovedLives; 

    document.forms[1].submit();
}


function onUploadMemDetails()
{
	    if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
	    document.forms[1].mode.value = "doUploadMemDetails";
	    document.forms[1].action = "/SwInsPricingActionIncome.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}