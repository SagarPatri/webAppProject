

//for restring the form fields
function onReset()
{
	document.forms[1].reset();
}//end of onReset()

function onClose(){
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doCloseConfList";
	var strSL="";
	if("Policies"==document.forms[1].currentSubLink.value){
		strSL="?tab=General";
	}
	document.forms[1].action="/MOHRuleAction.do"+strSL;
	document.forms[1].submit();
}//end of onClose()


function onBenefitConfiguration(vBenID,vCT){
	
	var brcompYN=document.forms[1].bnfRuleCompltYN.value;
	
	if((vCT=="R"||vCT=="C")&&brcompYN=="Y"){
		alert("Rules are completed");
		return;
	}
	
	if(isDate(document.forms[1].fromDate,"From Date")==false)
		return false;
	
	
	if(!JS_SecondSubmit){	
		document.forms[1].benefitID.value=vBenID;
		document.forms[1].confType.value=vCT;		
		document.forms[1].mode.value="doBenefitConfiguration";		
		document.forms[1].child.value="AddBenefitRule";
		document.forms[1].action="/MOHRuleAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
}//end of onBenefitConfiguration()

function hmoPolicyReport(){

	if(!JS_SecondSubmit){
		document.getElementById('genReportId').innerHTML="Generating Report Please wait.....";
		document.getElementById('genReportId').disabled="true";
		document.getElementById('genReportId').innerHTML="Generating Report Please wait.....";
		document.getElementById('genReportId').disabled="true";
		document.forms[1].mode.value="hmoPolicyReport";		
		document.forms[1].action="/MOHRuleAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
		
	
}//end of onBenefitConfiguration()


function removeBenefit(vBenID){
	var brcompYN=document.forms[1].bnfRuleCompltYN.value;
	
	if(brcompYN=="Y"){
		alert("Rules are completed");
		return;
	}
	
	if(!confirm("You Want To Remove This Configuration?"))return;
	if(!JS_SecondSubmit){	
		document.forms[1].benefitID.value=vBenID;
		document.forms[1].mode.value="removeBenefit";
		document.forms[1].child.value="RemoveBenefit";
		document.forms[1].action="/MOHRuleAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
}
	function saveAndComlete(){
		
		var brcompYN=document.forms[1].bnfRuleCompltYN.value;
		
		if(brcompYN=="Y"){
			alert("Rules are completed");
			return;
		}
		
		
		if(!JS_SecondSubmit){	
			document.getElementById("saveCompleteBtnID").innerHTML="Please wait Completing...";
			document.forms[1].mode.value="saveAndComplete";
			document.forms[1].action="/MOHRuleAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)	
	}
	
function override(){
		
	if(!confirm("You Want To Overide This Rule?"))return;
		
		if(!JS_SecondSubmit){	
			document.getElementById("overideBtnID").innerHTML="Please wait Overiding...";
			document.forms[1].mode.value="overrideRules";
			document.forms[1].action="/MOHRuleAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)	
	}
	


function generateReport(){
		document.forms[1].mode.value="generateProPolReport";
		document.forms[1].action="/MOHRuleAction.do";
		document.forms[1].submit();
}