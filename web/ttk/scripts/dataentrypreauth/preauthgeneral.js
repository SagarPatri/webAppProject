//javascript used in preauthgeneral.jsp of Preauth flow

function clearAuthorization() {
	if (!document.forms[1].authNbr.value == '') {
		document.forms[1].mode.value = "doClearAuthorization";
		document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
		document.forms[1].submit();
	}// end of if(!document.forms[1].authNbr.value=='')
}// end of clearAuthorization()

function onPrevHospitalization() {
	if (document.forms[1].authNbr.value == '') {
		document.forms[1].mode.value = "doPrevHospitalization";
		document.forms[1].focusID.value = "PrevHospId";
		document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
		document.forms[1].submit();
	}// end of if(document.forms[1].authNbr.value=='')
}// end of onPrevHospitalization()

function selectEnrollmentID() {
	if (trim(document.forms[1].elements['claimantDetailsVO.enrollmentID'].value).length > 0) {
		var message = confirm('You are fetching new data. Do you want to continue?');
		if (message) {
			document.forms[1].mode.value = "doSelectEnrollmentID";
			document.forms[1].child.value = "EnrollmentList";
			document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
			document.forms[1].submit();
		}// end of if(message)
	} else {
		document.forms[1].mode.value = "doSelectEnrollmentID";
		document.forms[1].child.value = "EnrollmentList";
		document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
		document.forms[1].submit();
	}// end of else
}// end of selectEnrollmentID()

function clearEnrollmentID() {
	document.forms[1].mode.value = "doClearEnrollmentID";
	document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
	document.forms[1].submit();
}// end of clearEnrollmentID()

function selectCorporate() {
	document.forms[1].mode.value = "doSelectCorporate";
	document.forms[1].child.value = "GroupList";
	document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
	document.forms[1].submit();
}// end of selectCorporate()

function selectPolicy() {
	if (trim(document.forms[1].elements['claimantDetailsVO.policyNbr'].value).length > 0) {
		var message = confirm('You are fetching new data. Do you want to continue?');
		if (message) {
			document.forms[1].mode.value = "doSelectPolicy";
			document.forms[1].child.value = "PolicyList";
			document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
			document.forms[1].submit();
		}// end of if(message)
	} else {
		document.forms[1].mode.value = "doSelectPolicy";
		document.forms[1].child.value = "PolicyList";
		document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
		document.forms[1].submit();
	}// end of else
}// end of selectPolicy()

function clearPolicy() {
	document.forms[1].mode.value = "doClearInsurance";
	document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
	document.forms[1].submit();
}// end of clearPolicy()

function selectInsurance() {
	document.forms[1].mode.value = "doSelectInsurance";
	document.forms[1].child.value = "Insurance Company";
	document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
	document.forms[1].submit();
}// end of selectInsurance()

function selectHospital() {
	document.forms[1].mode.value = "doSelectHospital";
	document.forms[1].child.value = "HospitalList";
	document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
	document.forms[1].submit();
}// end of selectHospital()

function onClearHospital() {
	document.forms[1].mode.value = "doClearHospital";
	document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
	document.forms[1].submit();
}// end of onClearHospital()

function selectAuthorization() {
	document.forms[1].mode.value = "doSelectAuthorization";
	document.forms[1].child.value = "SelectAuthorization";
	document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
	document.forms[1].submit();
}// end of selectHospital()

function onEnhancementAmount() {
	document.forms[1].mode.value = "doEnhancementAmount";
	document.forms[1].child.value = "SumInsuredList";
	document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
	document.forms[1].submit();
}// end of onEnhancementAmount()

function showhideadditionalinfo(selObj) {
	var selVal = selObj.options[selObj.selectedIndex].value;
	var dagreeObj = document.getElementById("additionalinfo");
	dagreeObj.style.display = "none";
	if (selVal == 'MAN') {
		dagreeObj.style.display = "";
	}// end of if(selVal == 'MAN')
}// end of showhideadditionalinfo(selObj)

function onUserSubmit() {

	trimForm(document.forms[1]);
	if (document.forms[0].leftlink.value == "DataEntryClaims") {
		if (document.forms[1].elements['claimDetailVO.claimSubTypeID'].value == "CSD") {
			document.forms[1].elements['preAuthHospitalVO.hospSeqId'].value = "";
			document.forms[1].elements['preAuthHospitalVO.hospitalName'].value = "";
			document.forms[1].elements['preAuthHospitalVO.address1'].value = "";
			document.forms[1].elements['preAuthHospitalVO.address2'].value = "";
			document.forms[1].elements['preAuthHospitalVO.address3'].value = "";
			document.forms[1].elements['preAuthHospitalVO.stateName'].value = "";
			document.forms[1].elements['preAuthHospitalVO.cityDesc'].value = "";
			document.forms[1].elements['preAuthHospitalVO.pincode'].value = "";
			document.forms[1].elements['preAuthHospitalVO.countryName'].value = "";
			if (!document.forms[1].elements['claimDetailVO.claimSubTypeID'].value == "CSD") {
				document.forms[1].elements['preAuthHospitalVO.emailID'].value = "";
				document.forms[1].elements['preAuthHospitalVO.phoneNbr1'].value = "";
				document.forms[1].elements['preAuthHospitalVO.phoneNbr2'].value = "";
				document.forms[1].elements['preAuthHospitalVO.faxNbr'].value = "";
			}
			document.forms[1].elements['preAuthHospitalVO.hospRemarks'].value = "";
			// added as per KOC 1285
			var doctorCertificateYN = document.forms[1].elements['claimDetailVO.doctorCertificateYN'];
			if (doctorCertificateYN.checked != true) {
				document.forms[1].doctorCertificateYN.value = "N";
			}// end of If
			else {
				document.forms[1].doctorCertificateYN.value = "Y";
			}// end of else
			// added as per KOC 1285
		}// end of
			// if(document.forms[1].elements['claimDetailVO.claimSubTypeID'].value=="CSD")
	}// end of if(document.forms[0].leftlink.value=="Claims")
	if (!JS_SecondSubmit) {

		document.forms[1].mode.value = "doSave";
		document.forms[1].action = "/DataEntryUpdatePreAuthGeneral.do";
		JS_SecondSubmit = true;
		document.forms[1].submit();
	}// end of if(!JS_SecondSubmit)
}// end of onUserSubmit()

function onReset() {
	if (typeof (ClientReset) != 'undefined' && !ClientReset) {
		document.forms[1].mode.value = "doReset";
		document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
		document.forms[1].submit();
	}// end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else {
		document.forms[1].reset();
		// showhideCorporateDet(document.forms[1].elements['claimantDetailsVO.policyTypeID']);
	}// end of else
}// end of onReset()

function showhideCorporateDet(selObj) {
	var selVal = selObj.options[selObj.selectedIndex].value;

	if (document.forms[1].flowType.value == "PRE") {
		var addInfo = document.getElementById("additionalinfo");
		var empNoLabel = document.getElementById("empNoLabel");
		var empNoField = document.getElementById("empNoField");
		var empName = document.getElementById("empName");
		var schemeName = document.getElementById("schemeName");

		if (selVal == 'COR') {
			document.getElementById("corporate").style.display = "";
			// document.forms[1].elements['claimantDetailsVO.policyHolderName'].value="";
			// document.forms[1].elements['claimantDetailsVO.policyHolderName'].disabled
			// = true;
			document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly = true;
			document.forms[1].elements['claimantDetailsVO.policyHolderName'].className = "textBox textBoxLarge textBoxDisabled";
			if (document.forms[1].preAuthTypeID.value == "MAN") {

				addInfo.style.display = "";
				empNoLabel.style.display = "";
				empNoField.style.display = "";
				empName.style.display = "";
				// document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly
				// = false;
			}// end of if(document.forms[1].preAuthTypeID.value=="MAN")
			// document.forms[1].elements['additionalDetailVO.insScheme'].value="";
			// document.forms[1].elements['additionalDetailVO.certificateNo'].value="";
			schemeName.style.display = "none";
		}// end of if(selVal == 'COR')

		else if (selVal == 'NCR'
				&& document.forms[1].preAuthTypeID.value == "MAN") {
			document.getElementById("corporate").style.display = "";
			addInfo.style.display = "";
			schemeName.style.display = "";
			// document.forms[1].elements['additionalDetailVO.employeeName'].value="";
			// document.forms[1].elements['additionalDetailVO.joiningDate'].value="";
			// document.forms[1].elements['additionalDetailVO.employeeNbr'].value="";
			empNoLabel.style.display = "none";
			empNoField.style.display = "none";
			empName.style.display = "none";
		}// end of

		else {
			document.getElementById("corporate").style.display = "none";
			document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly = false;
			document.forms[1].elements['claimantDetailsVO.policyHolderName'].className = "textBox textBoxLarge";
			// document.forms[1].elements['additionalDetailVO.insScheme'].value="";
			// document.forms[1].elements['additionalDetailVO.certificateNo'].value="";
			addInfo.style.display = "none";
			empNoLabel.style.display = "none";
			empNoField.style.display = "none";
			empName.style.display = "none";
			schemeName.style.display = "none";
		}// end of else
	}// end of if(document.forms[1].flowType.value=="PRE")
	else if (document.forms[1].flowType.value == "CLM") {
		if (selVal == 'COR') {
			document.getElementById("corporate").style.display = "";
			// document.forms[1].elements['claimantDetailsVO.policyHolderName'].value="";
			document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly = true;
			document.forms[1].elements['claimantDetailsVO.policyHolderName'].className = "textBox textBoxLarge textBoxDisabled";
		} else {
			// document.getElementById("corporate").style.display="none";
			document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly = false;
			document.forms[1].elements['claimantDetailsVO.policyHolderName'].className = "textBox textBoxLarge";
		}// end of else if(document.forms[1].flowType.value=="CLM")
	}// end of else

	document.forms[1].mode.value = "doChangePolicyType";
	document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
	document.forms[1].submit();
}// end of showhideCorporateDet(selObj)

// on Click of review button
function onReview() {
	// trimForm(document.forms[1]);
	// if(!TrackChanges()) return false;
	if (TC_GetChangedElements().length > 0) {
		alert("Please save the modified data, before Review");
		return false;
	}// end of if(TC_GetChangedElements().length>0)
	if (!JS_SecondSubmit) {
		document.forms[1].mode.value = "doReviewInfo";
		document.forms[1].action = "/DataEntryUpdatePreAuthGeneral.do";
		JS_SecondSubmit = true
		document.forms[1].submit();
	}// end of if(!JS_SecondSubmit)

}// end of onReview()

function onRevert() {
	if (TC_GetChangedElements().length > 0) {
		alert("Please save the modified data, before Promote");
		return false;
	}// end of if(TC_GetChangedElements().length>0)
	var message;
	if (document.forms[0].leftlink.value == 'Pre-Authorization') {
		message = confirm('Cashless will be moved to the next level and if you do not have privileges, you may not see this Cashless.');
	}// end of if(document.forms[0].leftlink.value=='Pre-Authorization')
	else {
		message = confirm('Claim will be moved to the next level and if you do not have privileges, you may not see this Claim.');
	}// end of else if(document.forms[0].leftlink.value=='Pre-Authorization')
	if (message) {
		if (!JS_SecondSubmit) {
			document.forms[1].mode.value = "doRevert";
			document.forms[1].action = "/DataEntryUpdatePreAuthGeneral.do";
			JS_SecondSubmit = true
			document.forms[1].submit();
		}
	}// end of if(!JS_SecondSubmit)

}// end of onRevert()

// on Click of promote button
function onPromote() {
	// trimForm(document.forms[1]);
	// if(!TrackChanges()) return false;
	if (TC_GetChangedElements().length > 0) {
		alert("Please save the modified data, before Promote");
		return false;
	}// end of if(TC_GetChangedElements().length>0)
	var message;
	if (document.forms[0].leftlink.value == 'Pre-Authorization') {
		message = confirm('Cashless will be moved to the next level and if you do not have privileges, you may not see this Cashless.');
	}// end of if(document.forms[0].leftlink.value=='Pre-Authorization')
	else {
		message = confirm('Claim will be moved to the next level and if you do not have privileges, you may not see this Claim.');
	}// end of else if(document.forms[0].leftlink.value=='Pre-Authorization')
	if (message) {
		if (!JS_SecondSubmit) {
			document.forms[1].mode.value = "doReviewInfo";
			document.forms[1].mode.value = "doDataEntryReviewInfo";
			document.forms[1].action = "/DataEntryUpdatePreAuthGeneral.do";
			JS_SecondSubmit = true
			document.forms[1].submit();
		}// end of if(!JS_SecondSubmit)
	}// end of if(message)
}// end of onPromote()

// added for CR KOC-Decoupling
function onDataEntryPromote() {
	// trimForm(document.forms[1]);
	// if(!TrackChanges()) return false;
	if (TC_GetChangedElements().length > 0) {
		alert("Please save the modified data, before Promote");
		return false;
	}// end of if(TC_GetChangedElements().length>0)
	var message;
	if (document.forms[0].leftlink.value == 'Pre-Authorization') {
		message = confirm('Cashless will be moved to the next level and if you do not have privileges, you may not see this Cashless.');
	}// end of if(document.forms[0].leftlink.value=='Pre-Authorization')
	else {
		message = confirm('Claim will be moved to the next level and if you do not have privileges, you may not see this Claim.');
	}// end of else if(document.forms[0].leftlink.value=='Pre-Authorization')
	if (message) {
		if (!JS_SecondSubmit) {
			// document.forms[1].mode.value="doReviewInfo";
			document.forms[1].mode.value = "doDataEntryReviewInfo";
			document.forms[1].action = "/DataEntryUpdatePreAuthGeneral.do";
			JS_SecondSubmit = true
			document.forms[1].submit();
		}// end of if(!JS_SecondSubmit)
	}// end of if(message)
}// end of onPromote()

// ended

// added for CR KOC-Decoupling

function onDataEntryRevert() {
	if (TC_GetChangedElements().length > 0) {
		alert("Please save the modified data, before Promote");
		return false;
	}// end of if(TC_GetChangedElements().length>0)
	var message;
	if (document.forms[0].leftlink.value == 'Pre-Authorization') {
		message = confirm('Cashless will be moved to the next level and if you do not have privileges, you may not see this Cashless.');
	}// end of if(document.forms[0].leftlink.value=='Pre-Authorization')
	else {
		message = confirm('Claim will be moved to the next level and if you do not have privileges, you may not see this Claim.');
	}// end of else if(document.forms[0].leftlink.value=='Pre-Authorization')
	if (message) {
		if (!JS_SecondSubmit) {
			document.forms[1].mode.value = "doDataEntryRevert";
			document.forms[1].action = "/DataEntryUpdatePreAuthGeneral.do";
			JS_SecondSubmit = true
			document.forms[1].submit();
		}
	}// end of if(!JS_SecondSubmit)

}// end of onRevert()

function onDiscrepancySubmit() {
	if (!TrackChanges())
		return false;
	document.forms[1].mode.value = "doDiscrepancies";
	document.forms[1].child.value = "Discrepancy";
	document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
	document.forms[1].submit();
}// end of function onDiscrepancySubmit()

function getPreauthReferenceNo(refNo) {
	document.forms[1].DMSRefID.value = refNo;
}// end of getPreauthReferenceNo(refNo)

function showhideClaimSubType(selObj) {

	var selVal = selObj.options[selObj.selectedIndex].value;
	var dagreeObj = document.getElementById("hospitalinfo");
	// Changes as per KOC1216C Change Request

	if (selVal != 'CSD') {
		document.getElementById('labelchange').innerHTML = "Admission Date/Time:<span class=\"mandatorySymbol\">*</span>";
		document.getElementById('labelchange1').innerHTML = "Discharge Date/Time:<span class=\"mandatorySymbol\">*</span>";
		document.getElementById("domicilaryinfo").style.display = "none";
		document.getElementById("domicilaryinfocheckBox").style.display = "none";
	} else {
		document.getElementById('labelchange').innerHTML = "Treatment Commencement Date/Time:<span class=\"mandatorySymbol\">*</span>";
		document.getElementById('labelchange1').innerHTML = "Treatment Completion Date/Time:<span class=\"mandatorySymbol\">*</span>";
		document.getElementById("domicilaryinfo").style.display = "";
		document.getElementById("domicilaryinfocheckBox").style.display = "";
	}
	// Changes as per KOC1216C Change Request
	if (selVal == 'OPD') {
		document.forms[1].elements['prevHospClaimSeqID'].disabled = true;
	} else {
		document.forms[1].elements['prevHospClaimSeqID'].disabled = false;
	}
	// Changes as per KOC1216C Change Request
	if (selVal == 'CSD') {
		document.getElementById("hospitalinfo").style.display = "none";
	}// end of if(selVal == 'CSD')
	else {
		document.getElementById("hospitalinfo").style.display = "";
	}// end of else if(selVal == 'CSD')
	if (selVal == 'HCU') {
		document.getElementById("hospitalizationdetail").style.display = "none";
	}// end of if(selVal == 'CSD')
	else {
		document.forms[1].elements['prevHospClaimSeqID'].value = "";
		document.forms[1].elements['clmAdmissionDate'].value = "";
		document.forms[1].elements['clmAdmissionTime'].value = "";
		document.forms[1].elements['dischargeDate'].value = "";
		document.forms[1].elements['dischargeTime'].value = "";

		document.getElementById("hospitalizationdetail").style.display = "";
	}// end of else if(selVal == 'CSD')

}// end of showhideClaimSubType(selObj)

function onOverride() {
	if (TC_GetChangedElements().length > 0) {
		alert("Please save the modified data, before Overriding");
		return false;
	}// end of if(TC_GetChangedElements().length>0)
	document.forms[1].mode.value = "doOverride";
	document.forms[1].action = "/OverridePreAuthGeneral.do";
	document.forms[1].submit();
}// end of onOverride()

function reassignEnrID() {
	if (TC_GetChangedElements().length > 0) {
		alert("Please save the modified data, before Re-associating");
		return false;
	}// end of if(TC_GetChangedElements().length>0)
	document.forms[1].mode.value = "doReassociateEnrollID";
	document.forms[1].action = "/DataEntryPreAuthGeneralAction.do";
	document.forms[1].submit();
}// end of reassignEnrID()

function onDocumentLoad() {
	selObj = document.forms[1].elements['claimantDetailsVO.policyTypeID']
	selVal = selObj.options[selObj.selectedIndex].value;
	var addInfo = document.getElementById("additionalinfo");
	var empNoLabel = document.getElementById("empNoLabel");
	var empNoField = document.getElementById("empNoField");
	var empName = document.getElementById("empName");
	var schemeName = document.getElementById("schemeName");

	if (selVal == 'COR') {
		document.getElementById("corporate").style.display = "";
		document.forms[1].elements['claimantDetailsVO.policyHolderName'].value = "";
		document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly = true;
		document.forms[1].elements['claimantDetailsVO.policyHolderName'].className = "textBox textBoxLarge textBoxDisabled";
		if (document.forms[1].preAuthTypeID.value == "MAN") {
			addInfo.style.display = "";
			empNoLabel.style.display = "";
			empNoField.style.display = "";
			empName.style.display = "";
		}// end of if(document.forms[1].preAuthTypeID.value=="MAN")
		schemeName.style.display = "none";
	}// end of if(selVal == 'COR')
	else if (selVal == 'NCR' && document.forms[1].preAuthTypeID.value == "MAN") {
		document.getElementById("corporate").style.display = "";
		addInfo.style.display = "";
		schemeName.style.display = "";
		empNoLabel.style.display = "none";
		empNoField.style.display = "none";
		empName.style.display = "none";
	}// end of
	else {
		document.getElementById("corporate").style.display = "none";
		document.forms[1].elements['claimantDetailsVO.policyHolderName'].readOnly = false;
		document.forms[1].elements['claimantDetailsVO.policyHolderName'].className = "textBox textBoxLarge";
		addInfo.style.display = "none";
		schemeName.style.display = "none";
		empNoLabel.style.display = "none";
		empNoField.style.display = "none";
		empName.style.display = "none";
	}// end of else
}// end of onDocumentLoad()
// KOC 1285 Change Request
function onDoctorCertificate() {
	var doctorCertificateYN = document.forms[1].elements['claimDetailVO.doctorCertificateYN'];
	if (doctorCertificateYN.checked != true) {
		document.forms[1].doctorCertificateYN.value = "N";
	} else {
		document.forms[1].doctorCertificateYN.value = "Y";
	}

}