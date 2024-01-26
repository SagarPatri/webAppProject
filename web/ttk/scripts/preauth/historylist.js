
function doViewHistory(authSeqID1){
	document.forms[1].authSeqID.value=authSeqID1;
	document.forms[1].mode.value = "doViewHistory";		
	document.forms[1].action="/PreAuthHistoryAction.do";
	document.forms[1].submit();
	}

function closeHistory(){
	document.forms[1].mode.value = "doGeneral";	
	document.forms[1].reforward.value = "close";		
	document.forms[1].action="/PreAuthHistoryAction.do";
	document.forms[1].submit();
	}
function changeHistoryMode(){
	
	if(document.forms[1].enhancedPreauth.value == "EnhancedPreauth" && (document.forms[1].historyMode.value == "PAT" || document.forms[1].historyMode.value == "CLM") ){
		document.forms[1].mode.value = "doListEnhancedRelatedPreAuth";	
		document.forms[1].action="/PreAuthHistoryAction.do";
	}else{
		document.forms[1].mode.value = "historyList";	
		document.forms[1].action="/PreAuthHistoryAction.do";
	}
	document.forms[1].submit();
	}