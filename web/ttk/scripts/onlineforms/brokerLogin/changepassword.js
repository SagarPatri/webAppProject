function onChangePassword(){
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit){
		document.forms[1].mode.value="doSaveChangePassward";
		document.forms[1].action="/UpdateBrokerPasswordChangeAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
}//end of onSave()

function onBack() {
	document.forms[1].mode.value="goBack";
	document.forms[1].action="/BrokerPasswordChangeAction.do";
	document.forms[1].submit();	
}