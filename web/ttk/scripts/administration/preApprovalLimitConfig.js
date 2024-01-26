

function preApprovalLimit(checkObj) {	
	//var =document.getElementById("preApprovalLimitYN");
	if(checkObj.checked){
       document.getElementById("preAppravalLimtIDVIP").style.display="";
       document.getElementById("preAppravalLimtIDNONVIP").style.display="";
       document.forms[1].preApprovalAmountVIP.value="";
       document.forms[1].preApprovalAmountNONVIP.value="";
		}else{
			document.getElementById("preAppravalLimtIDVIP").style.display="none";
			document.getElementById("preAppravalLimtIDNONVIP").style.display="none";
			document.forms[1].preApprovalAmountVIP.value="";
			document.forms[1].preApprovalAmountNONVIP.value="";
		}
}


function onSave(){
	var checkObj=document.getElementById("preApprovalLimitYN");
	if(checkObj.checked) document.forms[1].preApprovalLimitYN.value="Y";
	else{
		document.forms[1].preApprovalLimitYN.value="N";
		document.forms[1].preApprovalAmountVIP.value="";
		document.forms[1].preApprovalAmountNONVIP.value="";
	}
	var paaVIP=document.forms[1].preApprovalAmountVIP.value;
	var paaNONVIP=document.forms[1].preApprovalAmountNONVIP.value;
	if(checkObj.checked){
		if(!(paaVIP.length>=1||paaNONVIP.length>=1)){
			alert("Please Enter Any One Limited Amount");
			return;
		}	
	}
		
	document.forms[1].mode.value="doSavePreApprovalLimit";
	document.forms[1].action="/PreApprovalLimitConfiguration.do";
	document.forms[1].submit();
}//end of onSave()


function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value = "doClose";
	document.forms[1].action = "/InsuranceApproveConfiguration.do";
	document.forms[1].submit();	
}//end of onClose() 