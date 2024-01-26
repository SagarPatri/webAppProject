/**
 * @ (#) onlineProvider.js 09th Jan 2007 Project : TTK Healthcare Services File :
 *   onlineProvider.js Author : Kishor kumar Company : RCS Technologies Date
 *   Created: 08th Jan 2015
 * 
 * @author : Kishor kumar Modified by : Modified date : Reason :
 * 
 */
function onChangeState(obj) {
	if (obj.value != "") {
		document.forms[1].mode.value = "doChangeState";
		// document.forms[1].focusID.value="state";
		document.forms[1].action = "/NewEnrollAction.do";
		document.forms[1].submit();
	}
}// end of onChangeCity()

// function to submit the screen
function onNewEnrolSubmit() {
	if (!JS_SecondSubmit) {
		trimForm(document.forms[1]);
		document.forms[1].mode.value = "doSave";
		document.forms[1].action = "/NewEnrollGeneralSaveAction.do";
		JS_SecondSubmit = true;
		document.forms[1].submit();
	}// end of if(!JS_SecondSubmit)
}// end of onNewEnrolSubmit()

function onSaveAndNext() {
	/*if (document.getElementById("officePhone1").value == "Phone No1")
		document.getElementById("officePhone1").value = "";*/

	if (document.getElementById("officePhone2").value == "Phone No2")
		document.getElementById("officePhone2").value = "";

	if (!JS_SecondSubmit) {
		trimForm(document.forms[1]);
		document.forms[1].mode.value = "doSave";
		document.forms[1].action = "/NewEnrollGeneralSaveAction.do";
		JS_SecondSubmit = true;
		document.forms[1].submit();
	}// end of if(!JS_SecondSubmit)
}

function onUserSubmit() {
	if (!JS_SecondSubmit) {
		trimForm(document.forms[1]);
		document.forms[1].mode.value = "doSaveUsers";
		document.forms[1].action = "/NewEnrollContactSaveAction.do";
		JS_SecondSubmit = true;
		document.forms[1].submit();
	}// end of if(!JS_SecondSubmit)
}

function onServices() {
	document.forms[1].mode.value = "doViewServiceList";
	document.forms[1].child.value = "General";
	document.forms[1].action = "/HospitalGradingAction.do";
	document.forms[1].submit();
}// end of onStatusChanged()

function onClose() {
	document.forms[1].mode.value = "doClose";
	document.forms[1].action = "/NewEnrollAction.do";
	document.forms[1].submit();
}
function changeMe(obj) {
	var val = obj.value;
	if (val == 'Phone No1') {
		document.forms[1].officePhone1.value = "";
	}
	if (val == 'Phone No2') {
		document.forms[1].officePhone2.value = "";
	}
	if (val == 'Phone No') {
		document.forms[1].officePhone1.value = "";
	}
	if (val == 'ISD') {
		document.forms[1].isdCode.value = "";
	}
	if (val == 'STD') {
		document.forms[1].stdCode.value = "";
	}
}
function getMe(obj) {
	// alert(obj);

	if (obj == 'ISD') {
		if (document.forms[1].isdCode.value == "")
			document.forms[1].isdCode.value = obj;
	}
	if (obj == 'STD') {
		if (document.forms[1].stdCode.value == "")
			document.forms[1].stdCode.value = obj;
	}
	if (obj == 'Phone No1') {
		if (document.forms[1].officePhone1.value == "")
			document.forms[1].officePhone1.value = obj;
	}
	if (obj == 'Phone No2') {
		if (document.forms[1].officePhone2.value == "")
			document.forms[1].officePhone2.value = obj;
	}
	if (obj == 'Phone No') {
		if (document.forms[1].officePhone1.value == "")
			document.forms[1].officePhone1.value = obj;
	}

}

$(function() {
	$('#website').click(function() {
	var txt = $('#txturl').val();
	var re = /(http(s)?:\\)?([\w-]+\.)+[\w-]+[.com|.in|.org]+(\[\?%&=]*)?/
	if (re.test(txt)) {
	alert('Valid URL');
	}
	else {
	alert('Please Enter Valid URL');
	return false;
	}
	});
	});
