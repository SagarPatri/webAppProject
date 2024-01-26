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
		if(!JS_SecondSubmit)
		{
		    document.forms[1].mode.value="doSave"; 
		    document.forms[1].action="/UpdatePolicyDesignConfiguration.do"; 
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


function showHideDetails(flag)

{
	if(flag == "GroupLevel")
	{
		var grouplevel=document.getElementById('groupLevelYN');
		 if (grouplevel.checked  == true ) {
			 document.getElementById("allowedGroupLevel").style.display=""; 
			 document.getElementById("allowedGroupLevel1").style.display=""; 
			 document.getElementById("allowedGroupLevel2").style.display=""; 
			 document.getElementById("allowedGroupLevel3").style.display=""; 
			 document.getElementById("allowedGroupLevel4").style.display=""; 
			 document.getElementById("allowedGroupLevel5").style.display=""; 
			 document.getElementById("allowedGroupLevel6").style.display=""; 
			 document.getElementById("groupLevelYN").value= "Y";
			 document.forms[1].initialWatiingPeriod.value="180";
			 document.forms[1].nonNetworkRemCopayGroup.value="40";
			 document.forms[1].electiveOutsideCover.value="NOTCOV";
			 document.forms[1].electiveOutsideCoverDays.value="0";
			 document.forms[1].transportaionOverseasLimit.value="0";
			 document.forms[1].repatriationRemainsLimit.value="0";
			 document.forms[1].specialistConsultationReferal.value="NOTCOV";
			 document.forms[1].emergencyEvalAML.value="0";
			 document.forms[1].internationalMedicalAssis.value="NOTCOV";
			 document.forms[1].specialistConsultationReferalGroup.value="APPL"; 
			 document.forms[1].perLifeAML.value="0";
			 document.forms[1].outpatientCoverage.value="ICO";
			 document.forms[1].maternityCopayGroup.value="30";
			 document.forms[1].groundTransportaionPerc.value="30";
			 document.forms[1].groundTransportaionNumeric.value="0";
			 document.forms[1].maternityCopayGroupNumeric.value="0";
		 }
		 else{
			 document.getElementById("allowedGroupLevel").style.display="none"; 
			 document.getElementById("allowedGroupLevel1").style.display="none"; 
			 document.getElementById("allowedGroupLevel2").style.display="none";
			 document.getElementById("allowedGroupLevel3").style.display="none";
			 document.getElementById("allowedGroupLevel4").style.display="none"; 
			 document.getElementById("allowedGroupLevel5").style.display="none"; 
			 document.getElementById("allowedGroupLevel6").style.display="none"; 
			 document.getElementById("groupLevelYN").value= "N"; 
			 document.forms[1].initialWatiingPeriod.value="180";
			 document.forms[1].nonNetworkRemCopayGroup.value="40";
			 document.forms[1].electiveOutsideCover.value="NOTCOV";
			 document.forms[1].electiveOutsideCoverDays.value="0";
			 document.forms[1].transportaionOverseasLimit.value="0";
			 document.forms[1].repatriationRemainsLimit.value="0";
			 document.forms[1].specialistConsultationReferal.value="NOTCOV";
			 document.forms[1].emergencyEvalAML.value="0";
			 document.forms[1].internationalMedicalAssis.value="NOTCOV";
			 document.forms[1].specialistConsultationReferalGroup.value="APPL"; 
			 document.forms[1].perLifeAML.value="0";
			 document.forms[1].outpatientCoverage.value="ICO";
			 document.forms[1].maternityCopayGroup.value="30";
			 document.forms[1].groundTransportaionPerc.value="30";
			 document.forms[1].groundTransportaionNumeric.value="0";
			 document.forms[1].maternityCopayGroupNumeric.value="0";
			 
			 
		 	}  
	}
	if(flag == "Inpatient")
	{
		var inpatient=document.getElementById('insPatYN');
		 if (inpatient.checked  == true ) {
			 document.getElementById("allowedInpat").style.display=""; 
			 document.getElementById("allowedInpat1").style.display=""; 
			 document.getElementById("allowedInpat2").style.display=""; 
			 document.getElementById("allowedInpat3").style.display=""; 
			 document.getElementById("allowedInpat4").style.display=""; 
			 document.getElementById("allowedInpat5").style.display=""; 
			// document.getElementById("allowedInpat6").style.display=""; 
			 
			 document.getElementById("insPatYN").value= "Y";

			 document.forms[1].inpatientICUAML.value="0";
			 document.forms[1].annualMaxLimit.value="0";
			 document.forms[1].roomType.value="WRD";
			 document.forms[1].copay.value="30";
			 document.forms[1].companionBenefit.value="NOTCOV";
			 document.forms[1].companionBenefitAMl.value="0";
			 document.forms[1].inpatientCashBenefit.value="NOTCOV";
			 document.forms[1].cashBenefitAML.value="0";
			// document.forms[1].emergencyDental.value="0";
			 //document.forms[1].emergencyMaternity.value="0";
			 document.forms[1].ambulance.value="NOTCOV";
			 document.forms[1].inpatientICU.value="NOTCOV";
		 }
		 else{
			 document.getElementById("allowedInpat").style.display="none";
			 document.getElementById("allowedInpat1").style.display="none"; 
			 document.getElementById("allowedInpat2").style.display="none"; 
			 document.getElementById("allowedInpat3").style.display="none"; 
			 document.getElementById("allowedInpat4").style.display="none"; 
			 document.getElementById("allowedInpat5").style.display="none"; 
			// document.getElementById("allowedInpat6").style.display="none"; 
			 document.getElementById("insPatYN").value= "N"; 
			 document.forms[1].inpatientICUAML.value="0";
			 document.forms[1].annualMaxLimit.value="0";
			 document.forms[1].roomType.value="WRD";
			 document.forms[1].copay.value="30";
			 document.forms[1].companionBenefit.value="NOTCOV";
			 document.forms[1].companionBenefitAMl.value="0";
			 document.forms[1].inpatientCashBenefit.value="NOTCOV";
			 document.forms[1].cashBenefitAML.value="0";
			// document.forms[1].emergencyDental.value="0";
			 //document.forms[1].emergencyMaternity.value="0";
			 document.forms[1].ambulance.value="NOTCOV";
			 document.forms[1].inpatientICU.value="NOTCOV";
		 	}  
	}
	if(flag == "Pharmacy")
	{
		var pharmacy=document.getElementById('pharmacyYN');
		 if (pharmacy.checked  == true ) {
			 document.getElementById("allowedPharmacy").style.display=""; 
			// document.getElementById("allowedPharmacy1").style.display=""; 
			 document.getElementById("allowedPharmacy2").style.display=""; 
			 document.getElementById("allowedPharmacy3").style.display="";
			 document.getElementById("pharmacyYN").value= "Y";
			 document.forms[1].copaypharmacy.value="30";
			 document.forms[1].amlPharmacy.value="0";
			 //document.forms[1].chronicAML.value="0";
			 document.forms[1].preauthLimitVIP.value="0";
			 document.forms[1].nonNetworkRemCopay.value="20";
			 document.forms[1].preauthLimitNonVIP.value="0";
		 }
		 else{
			 document.getElementById("allowedPharmacy").style.display="none";
			// document.getElementById("allowedPharmacy1").style.display="none"; 
			 document.getElementById("allowedPharmacy2").style.display="none"; 
			 document.getElementById("allowedPharmacy3").style.display="none"; 
			 document.getElementById("pharmacyYN").value= "N"; 
			 
			 document.forms[1].copaypharmacy.value="30";
			 document.forms[1].amlPharmacy.value="0";
			 //document.forms[1].chronicAML.value="0";
			 document.forms[1].preauthLimitVIP.value="0";
			 document.forms[1].nonNetworkRemCopay.value="20";
			 document.forms[1].preauthLimitNonVIP.value="0";
			 //document.forms[1].chronicPharmacyCopayPerc.value="0";
			 //document.forms[1].chronicPharmacyCopayNum.value="0";
		 	}  
	}
	
	if(flag == "LabDiagnostics")
	{
		var labdiag=document.getElementById('labdiagYN');
		 if (labdiag.checked  == true ) {
			 document.getElementById("allowedLabDiag").style.display=""; 
			 document.getElementById("allowedLabDiag1").style.display=""; 
			 document.getElementById("allowedLabDiag2").style.display=""; 
			 document.getElementById("allowedLabDiag3").style.display=""; 
			 document.getElementById("allowedLabDiag4").style.display=""; 
			 
			 document.getElementById("labdiagYN").value= "Y";
			 document.forms[1].copayLab.value="30";
			 document.forms[1].nonNetworkRemCopayLab.value="20";
			 document.forms[1].oncologyTest.value="NOTCOV";
			 document.forms[1].oncologyTestAML.value="0";
			 document.forms[1].labsAndDiagnosticsAML.value="0";
			 document.forms[1].preventiveScreeningDiabetics.value="NOTCOV";
			 document.forms[1].preventiveScreenDiabeticsAnnual.value="NOTCOV";
			 document.forms[1].preventiveScreeningDiabeticsAge.value="NOTCOV";
		 }
		 else{
			 document.getElementById("allowedLabDiag").style.display="none"; 
			 document.getElementById("allowedLabDiag1").style.display="none";
			 document.getElementById("allowedLabDiag2").style.display="none"; 
			 document.getElementById("allowedLabDiag3").style.display="none"; 
			 document.getElementById("allowedLabDiag4").style.display="none"; 
			 document.getElementById("labdiagYN").value= "N"; 
			 
			 document.forms[1].copayLab.value="30";
			 document.forms[1].nonNetworkRemCopayLab.value="20";
			 document.forms[1].oncologyTest.value="NOTCOV";
			 document.forms[1].oncologyTestAML.value="0";
			 document.forms[1].labsAndDiagnosticsAML.value="0";
			 document.forms[1].preventiveScreeningDiabetics.value="NOTCOV";
			 document.forms[1].preventiveScreenDiabeticsAnnual.value="NOTCOV";
			 document.forms[1].preventiveScreeningDiabeticsAge.value="NOTCOV";
			 
			  
		 }  
	}
	if(flag == "OPConsultation")
	{
		var opconsult=document.getElementById('opConsultYN');
		 if (opconsult.checked  == true ) {
			 document.getElementById("allowedOPConsult").style.display=""; 
			 document.getElementById("allowedOPConsult1").style.display=""; 
			 document.getElementById("allowedOPConsult2").style.display=""; 
			 //document.getElementById("allowedOPConsult3").style.display=""; 
			// document.getElementById("allowedOPConsult4").style.display=""; 
			// document.getElementById("allowedOPConsult5").style.display=""; 
			 document.getElementById("opConsultYN").value= "Y";
			 document.forms[1].consultationAML.value="0";
			 document.forms[1].opConsultationCopayList.value="30";
			 document.forms[1].specConsultationList.value="30";
			 document.forms[1].specConsultationNum.value="0";
			 document.forms[1].physiotherapySession.value="0";
			 document.forms[1].physiotherapyAMLLimit.value="0"; 
			// document.forms[1].noOfmaternityConsults.value="0";
			// document.forms[1].maternityConsultations.value="0";
			// document.forms[1].maternityConsultationsNum.value="0";
			 //document.forms[1].chronicDiseaseConsults.value="0";
			 //document.forms[1].chronicDiseaseAML.value="0";
			 //document.forms[1].chronicDiseaseCopay.value="0";
			 //document.forms[1].chronicDiseaseDeductible.value="0";
			 document.forms[1].opConsultationCopayListNum.value="0";
		 }
		 else{
			 document.getElementById("allowedOPConsult").style.display="none";
			 document.getElementById("allowedOPConsult1").style.display="none"; 
			 document.getElementById("allowedOPConsult2").style.display="none"; 
			 //document.getElementById("allowedOPConsult3").style.display="none";
			 //document.getElementById("allowedOPConsult4").style.display="none";
			 //document.getElementById("allowedOPConsult5").style.display="none"; 
			 document.getElementById("opConsultYN").value= "N";  
			 
			 document.forms[1].consultationAML.value="0";
			 document.forms[1].opConsultationCopayList.value="30";
			 document.forms[1].specConsultationList.value="30";
			 document.forms[1].specConsultationNum.value="0";
			 document.forms[1].physiotherapySession.value="0";
			 document.forms[1].physiotherapyAMLLimit.value="0"; 
			// document.forms[1].noOfmaternityConsults.value="0";
			// document.forms[1].maternityConsultations.value="0";
			// document.forms[1].maternityConsultationsNum.value="0";
			 //document.forms[1].chronicDiseaseConsults.value="0";
			 //document.forms[1].chronicDiseaseAML.value="0";
			 //document.forms[1].chronicDiseaseCopay.value="0";
			 //document.forms[1].chronicDiseaseDeductible.value="0";
			 document.forms[1].opConsultationCopayListNum.value="0";
			 
			 
		 }  
	}
	
	if(flag == "Maternity")
	{
		var maternity=document.getElementById('maternityYN');
		 if (maternity.checked  == true ) {
			 document.getElementById("allowedMaternity").style.display=""; 
			 document.getElementById("allowedMaternity1").style.display=""; 
			 document.getElementById("allowedMaternity2").style.display=""; 
			 document.getElementById("allowedMaternity3").style.display=""; 
			 document.getElementById("allowedMaternity4").style.display=""; 
			 document.getElementById("maternityYN").value= "Y";
			 document.forms[1].normalDeliveryAML.value="0";
			 document.forms[1].maternityCsectionAML.value="0";
			 document.forms[1].dayCoverage.value="NOTCOV";
			 document.forms[1].maternityComplicationAML.value="0"; 
			 document.forms[1].dayCoverageVaccination.value="NOTCOV"; 
			 document.forms[1].noOfmaternityConsults.value="0";
			 document.forms[1].maternityConsultationsNum.value="0";
			 document.forms[1].emergencyMaternityAML.value="0";
			 document.forms[1].maternityAnteNatalTests.value="DHA";
			 document.forms[1].maternityConsultations.value="30";
		 }
		 else{
			 document.getElementById("allowedMaternity").style.display="none"; 
			 document.getElementById("allowedMaternity1").style.display="none"; 
			 document.getElementById("allowedMaternity2").style.display="none"; 
			 document.getElementById("allowedMaternity3").style.display="none"; 
			 document.getElementById("allowedMaternity4").style.display="none";
			 document.getElementById("maternityYN").value= "N"; 
			 
			 document.forms[1].normalDeliveryAML.value="0";
			 document.forms[1].maternityCsectionAML.value="0";
			 document.forms[1].dayCoverage.value="NOTCOV";
			 document.forms[1].maternityComplicationAML.value="0"; 
			 document.forms[1].dayCoverageVaccination.value="NOTCOV"; 
			 document.forms[1].noOfmaternityConsults.value="0";
			 document.forms[1].maternityConsultationsNum.value="0";
			 document.forms[1].emergencyMaternityAML.value="0";
			 document.forms[1].maternityAnteNatalTests.value="DHA";
			 document.forms[1].maternityConsultations.value="30";
			 
			 
			 
		 }  
	}
	if(flag == "Dental")
	{
		var dental=document.getElementById('dentalYN');
		 if (dental.checked  == true ) {
			 document.getElementById("allowedDental").style.display=""; 
			 document.getElementById("allowedDental1").style.display=""; 
			 document.getElementById("allowedDental2").style.display=""; 
			 document.getElementById("allowedDental3").style.display=""; 
			 document.getElementById("dentalYN").value= "Y";
			 document.forms[1].dentalAML.value="0";
			 document.forms[1].dentalCopay.value="30";
			 document.forms[1].dentalDeductible.value="0";
			 document.forms[1].cleaningAML.value="0";
			 document.forms[1].orthodonticsAML.value="0";
			 document.forms[1].dentalNonNetworkRem.value="20"; 
			 document.forms[1].emergencyDentalAML.value="0"; 
		 }
		 else{
			 document.getElementById("allowedDental").style.display="none"; 
			 document.getElementById("allowedDental1").style.display="none"; 
			 document.getElementById("allowedDental2").style.display="none"; 
			 document.getElementById("allowedDental3").style.display="none"; 
			 document.getElementById("dentalYN").value= "N"; 
			 
			 document.forms[1].dentalAML.value="0";
			 document.forms[1].dentalCopay.value="30";
			 document.forms[1].dentalDeductible.value="0";
			 document.forms[1].cleaningAML.value="0";
			 document.forms[1].orthodonticsAML.value="0";
			 document.forms[1].dentalNonNetworkRem.value="20"; 
			 document.forms[1].emergencyDentalAML.value="0"; 
			 
		 }  
	}
	if(flag == "Chronic")
	{
		var chronic=document.getElementById('chronicYN');
		 if (chronic.checked  == true ) {
			 document.getElementById("allowedChronic").style.display=""; 
			 document.getElementById("allowedChronic1").style.display=""; 
			 document.getElementById("allowedChronic2").style.display=""; 
			 document.getElementById("allowedChronic3").style.display=""; 
			// document.getElementById("allowedChronic4").style.display=""; 
			 document.getElementById("chronicYN").value= "Y";
			 document.forms[1].chronicAMLNum.value="0";
			 document.forms[1].pharmacyAML.value="0";
			 document.forms[1].pharmacyAMLCopay.value="30";
			 document.forms[1].pharmacyAMLAmount.value="0";
			 document.forms[1].chronicLabDiag.value="0";
			 document.forms[1].chronicLabDiagCopay.value="30"; 
			 document.forms[1].chronicLabDiagAmount.value="0";
			 document.forms[1].chronicConsultationAML.value="0";
			 document.forms[1].chronicConsultationCopay.value="30";
			 document.forms[1].chronicConsultation.value="0";
			 document.forms[1].chronicCopayDeductibleAmount.value="0";
			 document.forms[1].chronicCopayDeductibleCopay.value="30";
			 
		 }
		 else{
			 document.getElementById("allowedChronic").style.display="none"; 
			 document.getElementById("allowedChronic1").style.display="none";
			 document.getElementById("allowedChronic2").style.display="none"; 
			 document.getElementById("allowedChronic3").style.display="none"; 
			// document.getElementById("allowedChronic4").style.display="none";
			 document.getElementById("chronicYN").value= "N"; 

			 document.forms[1].chronicAMLNum.value="0";
			 document.forms[1].pharmacyAML.value="0";
			 document.forms[1].pharmacyAMLCopay.value="30";
			 document.forms[1].pharmacyAMLAmount.value="0";
			 document.forms[1].chronicLabDiag.value="0";
			 document.forms[1].chronicLabDiagCopay.value="30"; 
			 document.forms[1].chronicLabDiagAmount.value="0";
			 document.forms[1].chronicConsultationAML.value="0";
			 document.forms[1].chronicConsultationCopay.value="30";
			 document.forms[1].chronicConsultation.value="0";
			 document.forms[1].chronicCopayDeductibleAmount.value="0";
			 document.forms[1].chronicCopayDeductibleCopay.value="30";
			 
		 }  
	}
	if(flag == "PED")
	{
		var ped=document.getElementById('pedYN');
		 if (ped.checked  == true ) {
			 document.getElementById("allowedPED").style.display="";
			// document.getElementById("allowedPED1").style.display="";
			 document.getElementById("pedYN").value= "Y";
			 document.forms[1].pedAML.value="0";
			 document.forms[1].pedCopayDeductible.value="30";
			 document.forms[1].pedDeductible.value="0";
		 }
		 else{
			 document.getElementById("allowedPED").style.display="none";
			 //document.getElementById("allowedPED1").style.display="none";
			 document.getElementById("pedYN").value= "N";
			 
			// document.forms[1].coveredPED.value="NOTCOV";
			 document.forms[1].pedAML.value="0";
			 document.forms[1].pedCopayDeductible.value="30";
			 document.forms[1].pedDeductible.value="0";
			  
		 }  
	}
	if(flag == "Psychiatry")
	{
		var psychiatry=document.getElementById('psychiatryYN');
		 if (psychiatry.checked  == true ) {
			 document.getElementById("allowedPsychiatry").style.display=""; 
			 document.getElementById("allowedPsychiatry1").style.display=""; 
			 document.getElementById("allowedPsychiatry2").style.display=""; 
			 document.getElementById("psychiatryYN").value= "Y";
			 document.forms[1].inpatientAML.value="0";
			 document.forms[1].noofInpatientDays.value="0";
			 document.forms[1].outpatientAML.value="0";
			 document.forms[1].noofOutpatientConsul.value="0";
			 document.forms[1].psychiatryCopayDeduc.value="30";
			 document.forms[1].psychiatryDeductible.value="0";
		 }
		 else{
			 document.getElementById("allowedPsychiatry").style.display="none"; 
			 document.getElementById("allowedPsychiatry1").style.display="none"; 
			 document.getElementById("allowedPsychiatry2").style.display="none"; 
			 document.getElementById("psychiatryYN").value= "N";
			 
			 document.forms[1].inpatientAML.value="0";
			 document.forms[1].noofInpatientDays.value="0";
			 document.forms[1].outpatientAML.value="0";
			 document.forms[1].noofOutpatientConsul.value="0";
			 document.forms[1].psychiatryCopayDeduc.value="30";
			 document.forms[1].psychiatryDeductible.value="0";
		 }  
	}
	
	if(flag == "Optical")
	{
		var optical=document.getElementById('opticalYN');
		 if (optical.checked  == true ) {
			 document.getElementById("allowedOptical").style.display=""; 
			 document.getElementById("allowedOptical1").style.display=""; 
			 document.getElementById("allowedOptical2").style.display="";
			 document.getElementById("opticalYN").value= "Y";
			 document.forms[1].opticalCopay.value="20";
			 document.forms[1].frameContactLensAML.value="0";
			 document.forms[1].opticalConsulCopay.value="30";
			 document.forms[1].opticalConsulAmount.value="0";
			 document.forms[1].opticalNonNetworkRem.value="20"; 
			 document.forms[1].opticalAML.value="0";
			 document.forms[1].emergencyOpticalAML.value="0"; 
		 }
		 else{
			 document.getElementById("allowedOptical").style.display="none";
			 document.getElementById("allowedOptical1").style.display="none"; 
			 document.getElementById("allowedOptical2").style.display="none"; 
			 document.getElementById("opticalYN").value= "N"; 
			 
			 document.forms[1].opticalCopay.value="20";
			 document.forms[1].frameContactLensAML.value="0";
			 document.forms[1].opticalConsulCopay.value="30";
			 document.forms[1].opticalConsulAmount.value="0";
			 document.forms[1].opticalNonNetworkRem.value="20"; 
			 document.forms[1].opticalAML.value="0";
			 document.forms[1].emergencyOpticalAML.value="0"; 
			 
		 }  
	}
	if(flag == "Others")
	{
		var others=document.getElementById('othersYN');
		 if (others.checked  == true ) {
			 document.getElementById("allowedOthers").style.display=""; 
			 document.getElementById("allowedOthers1").style.display=""; 
			 document.getElementById("allowedOthers2").style.display=""; 
			 document.getElementById("allowedOthers3").style.display=""; 
			 document.getElementById("allowedOthers4").style.display=""; 
			 document.getElementById("othersYN").value= "Y";
			 document.forms[1].systemOfMedicine.value="N";
			 document.forms[1].alternativeAML.value="0"; 
			 
			 document.forms[1].osteopathyMedicine.value="N";
			 document.forms[1].homeopathyMedicine.value="N";
			 document.forms[1].ayurvedaMedicine.value="N";
			 document.forms[1].accupunctureMedicine.value="N";
			 document.forms[1].naturopathyMedicine.value="N";
			 document.forms[1].unaniMedicine.value="N";
			 document.forms[1].chineseMedicine.value="N";
			 

		 }
		 else{
			 document.getElementById("allowedOthers").style.display="none"; 
			 document.getElementById("allowedOthers1").style.display="none"; 
			 document.getElementById("allowedOthers2").style.display="none"; 
			 document.getElementById("allowedOthers3").style.display="none"; 
			 document.getElementById("allowedOthers4").style.display="none"; 
			 document.getElementById("othersYN").value= "N";
			 
			 document.forms[1].systemOfMedicine.value="N";
			 document.forms[1].alternativeAML.value="0"; 
			 document.forms[1].osteopathyMedicine.value="N";
			 document.forms[1].homeopathyMedicine.value="N";
			 document.forms[1].ayurvedaMedicine.value="N";
			 document.forms[1].accupunctureMedicine.value="N";
			 document.forms[1].naturopathyMedicine.value="N";
			 document.forms[1].unaniMedicine.value="N";
			 document.forms[1].chineseMedicine.value="N";
			 
		 }  
	}
	
 }

 
function onGenerateFile()
{  
  	    var parameterValue=document.forms[1].groupseqid.value;
		document.forms[1].mode.value="doGenerateQuoteReport";
		document.forms[1].parameter.value=parameterValue; 
		document.forms[1].reportType.value="PDF";
		document.forms[1].action = "/ReportsAction.do";
		document.forms[1].submit();
}



function onClosePlan()
{
	document.forms[1].reset();
    document.forms[1].tab.value ="Income Profile";
    document.forms[1].mode.value = "doIncomeProfile";
    document.forms[1].action = "/InsPricingActionIncome.do";
    document.forms[1].submit();
}



function onGenerate()
 {
	document.forms[1].reset();
 	document.forms[1].mode.value = "doDefaultQuotation";
 	 document.forms[1].tab.value ="Generate Quote";
 	document.forms[1].action = "/GenerateQuotationAction.do";
 	document.forms[1].submit();	
 }



function onCalculateQuote()
{
		trimForm(document.forms[1]); 
		if(!JS_SecondSubmit)
		{
		    document.forms[1].mode.value= "doSaveCalculateQuote"; 
		    document.forms[1].tab.value = "Generate Quote";
		    document.forms[1].action="/UpdateGenerateQuotationAction.do"; 
		    JS_SecondSubmit=true;
		   document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
}


