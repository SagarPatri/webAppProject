/**
 * 
 */


function doViewHistoryPAT(authSeqID1){

	 window.opener.document.forms[1].mode.value = "doViewHistorypatclm";
		//alert(window.opener.document.getElementById("mode").value);
		 window.opener.document.forms[1].action = "/PreAuthHistoryAction.do?mode=doViewHistorypatclm&authSeqID="+authSeqID1+"&flag="+"P"+"";
		 window.opener.document.forms[1].submit();
	 	self.close();
	}



function doViewHistoryCLM(authSeqID1){

	 window.opener.document.forms[1].mode.value = "doViewHistorypatclm";
		//alert(window.opener.document.getElementById("mode").value);
		 window.opener.document.forms[1].action = "/PreAuthHistoryAction.do?mode=doViewHistorypatclm&authSeqID="+authSeqID1+"&flag="+"C"+"";
		 window.opener.document.forms[1].submit();
	 	self.close();
	}
/*function closeHistory(){
	document.forms[1].mode.value = "doGeneral";	
	document.forms[1].reforward.value = "close";		
	document.forms[1].action="/PreAuthHistoryAction.do";
	document.forms[1].submit();
	}
function changeHistoryMode(){
	document.forms[1].mode.value = "historyList";	
	document.forms[1].action="/PreAuthHistoryAction.do";
	document.forms[1].submit();
	}*/