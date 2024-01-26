function viewObservs(activityDtlSeqId1) {
	document.forms[1].activityDtlSeqId.value=activityDtlSeqId1;
	document.forms[1].mode.value="viewObservsHistory";
	document.forms[1].action="/PreAuthHistoryAction.do";
}
function viewObservs(activityDtlSeqId1){
	var ss="sdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsds";
	var obrurl="/PreAuthHistoryAction.do?obru="+ss+ss+ss+ss+"&mode=viewObservsHistory&activityDtlSeqId="+activityDtlSeqId1;				
	 popupWindow= window.open(obrurl,"OBSERVS","width=950,height=450,left=200,top=100,toolbar=no,scrollbars=no,status=no"); 
	  popupWindow.focus(); 
	  document.onmousedown=focusPopup; 
	  document.onkeyup=focusPopup; 
	  document.onmousemove=focusPopup; 		
	}
function focusPopup() {
	  if(popupWindow && !popupWindow.closed) { popupWindow.focus(); } 
	}

function Closeflag(){
	if(!JS_SecondSubmit){ 
	  
		var flaghistory=document.getElementById("flaghistory").value;
		//  document.forms[1].mode.value="doGeneral";
		  document.forms[1].reforward.value="memberSearch";
		  document.forms[1].action="/PreAuthGeneralAction.do?mode=doGeneral&flaghistory="+flaghistory;	
		  document.forms[1].submit();
	}
}