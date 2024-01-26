

function onClose(){
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doCloseConf";
	document.forms[1].action="/MOHRuleAction.do";
	document.forms[1].submit();
}//end of onClose()

function addBenefit(oMode){
	enableRequiredFields();
	
	if(!JS_SecondSubmit){	
		document.forms[1].mode.value="addBenefit";	
		if(oMode=='A'){
		document.getElementById("oprsBtnID").innerHTML="Please Wait Adding...";
		}else {
			document.getElementById("oprsBtnID").innerHTML="Please Wait Updating...";
		}
		
		document.forms[1].action="/MOHRuleAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
}//end of addBenefit()

function showHideConditions(obj,sbName,sbID) {

	document.getElementById("RN"+sbID).value=sbID;
	document.getElementById("RV"+sbID).value=obj.value;
	if(obj.value==="3"){
		document.getElementById(sbName).style.display="";
		}else{
			document.getElementById(sbName).style.display="none";
			}
}
var  popupWindow=null;	
function consultConf(argInvsType) {

	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynConsultRuleAction.do?mode=consultConfiguration&benefitID="+benType+"&invsType="+argInvsType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}
function investConf(argInvsType) {

	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynInvestRuleAction.do?mode=investConfiguration&benefitID="+benType+"&invsType="+argInvsType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}
function physioConf(argType) {

	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynPhysioRuleAction.do?mode=physioConfiguration&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

function pharmaConf(argType) {

	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynPharmaRuleAction.do?mode=pharmaConfiguration&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

function checkMainBenefitType(selObj){
	if("3"==selObj.value)document.getElementById("mbdtID").style.display="";
	else document.getElementById("mbdtID").style.display="none";
}
function confProviderCopayDetails(benefType,benefName)
{	
	
	if("IPT"==benefType){
		var condtype=document.getElementById("S1-C10-cond-type");
		if(condtype!=undefined&&condtype.value!='3'){
		alert("Please Select Pay conditionally");
		return;
		}
	}else if("MTI"==benefType){
			var condtype=document.getElementById("S1-C6-cond-type");
			if(condtype!=undefined&&condtype.value!='3'){
			alert("Please Select Pay conditionally");
			return;
			}
		}
	
	
	var openPage="/RuleAction.do?mode=mohConfProviderCopayDetails&benefitType="+benefType+"&benefitName="+benefName;
	 var features = "scrollbars=1,status=1,toolbar=0,top=30,left=150,resizable=0,menubar=0,width=1200,height=550";
	 popupWindow=window.open(openPage,'',features);
	 
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup;  
}//end of confProviderCopayDetails()
function focusPopup() {
	  if(popupWindow && !popupWindow.closed) { popupWindow.focus(); } 
	}


function companConf(argType) {

	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynCompanRuleAction.do?mode=companConfiguration&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}
function ambulanceConf(argType) {

	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynAmbulanceRuleAction.do?mode=ambulanceConfiguration&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus();
	document.onmousedown=focusPopup;
	document.onkeyup=focusPopup;
	document.onmousemove=focusPopup;
}


function onBenefitConfiguration(vBenID,vCT){
	if(vBenID==null||vBenID==''||vBenID.length<1)return;
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

var  popupWindow=null;	
function confClinicianTypeCopay(ynID,confID){	
	 var selBox=document.getElementById(ynID).value;
	 var confIDValue=document.getElementById(confID).value;
	
	 if(selBox!="Y"){
		 alert("Please Select Yes");
		 return;
	 }
	 var openPage="/RuleAction.do?mode=confClinicianTypeCopay&confID="+confID+"&confIDValue="+confIDValue+"&opType=V";
	 var features = "scrollbars=1,status=1,toolbar=0,top=30,left=150,resizable=0,menubar=0,width=1000,height=450";
	 popupWindow=window.open(openPage,'',features);
	 
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup;  
}//end of confProviderCopayDetails()

function focusPopup() {
	  if(popupWindow && !popupWindow.closed) { popupWindow.focus(); } 
	}
function refreshClcnDtls(falgYNID,reFreshID){
	if(document.getElementById(falgYNID).value=='N'){
		document.getElementById(reFreshID).value='';
	}
}
	
// added by govind
function confVaccinationsLimit(argType){
	
	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynVaccinationsRuleAction.do?mode=vaccinConfiguration&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

function altTmtConf(argType){
	
	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynAltTmtRuleAction.do?mode=altTmtConfiguration&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

function dEOTConf(argType){
	
	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynDEOTRuleAction.do?mode=dEOTConfig&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

function eyeCareConf(argType){
	
	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynEyeCareRuleAction.do?mode=eyeCareConfig&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

function gyncolgyConf(argType){
	
	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynGyncolgyRuleAction.do?mode=gyncolgyConfig&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

function minrSrgryConf(argType){
	
	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynMinrSrgryRuleAction.do?mode=minrSrgryConfig&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

function naslCrctionConf(argType){
	
	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynNaslCrctionRuleAction.do?mode=naslCrctionConfig&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

function onclgyConf(argType){
	
	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynOnclgyRuleAction.do?mode=onclgyConfig&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

function ognTrnspltRcptConf(argType){
	
	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynOgnTrnspltRcptRuleAction.do?mode=ognTrnspltRcptConf&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

function psychiatricConf(argType){
	
	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynPsychiatricRuleAction.do?mode=psychiatricConfig&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

function rnlDlsConf(argType){
	
	var benType=document.getElementById("benefitID").value;
	var docSize = getDocumentSize();
	var hg = docSize.height -50;
	var wt = docSize.width -80;
	
	 var openPage = "/DynRnlDlsRuleAction.do?mode=rnlDlsConfig&benefitID="+benType+"&invsType="+argType;
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=30,resizable=0,menubar=0,width="+wt+",height="+hg;
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

// for chronic-covered for in-patient
function showHideFields(covered,waitPeriod,days,pplimit,clplimit,ppCopaylimit,ppDedlimit)				
{	
	 var benfID=document.getElementById("benefitID").value;				
		if(benfID=='IPT'){
	if(document.getElementById(covered).value=='N')
	{
		document.getElementById(waitPeriod).disabled = true;
		document.getElementById(days).disabled = true;
		document.getElementById(pplimit).disabled = true;
		document.getElementById(clplimit).disabled = true;
		document.getElementById(ppCopaylimit).disabled = true;
		document.getElementById(ppDedlimit).disabled = true;
		
		document.getElementById(waitPeriod).value = "";
		document.getElementById(days).value = "";
		document.getElementById(pplimit).value = "";
		document.getElementById(clplimit).value = "";
		document.getElementById(ppCopaylimit).value = "";
		document.getElementById(ppDedlimit).value = "";
		
		
	}
	if(document.getElementById(covered).value=='Y')
	{
		document.getElementById(waitPeriod).disabled = false;
		document.getElementById(days).disabled = false;
		document.getElementById(pplimit).disabled = false;
		document.getElementById(clplimit).disabled = false;
		document.getElementById(ppCopaylimit).disabled = false;
		document.getElementById(ppDedlimit).disabled = false;
		
		
	}
	
	var ccObj=document.getElementById("S1-C7-chronic-covered").value;
	var preExcObj=document.getElementById("S1-C8-Pre-existing").value;
	
	if(ccObj =='Y' || preExcObj=='Y')
		{
		document.getElementById("S1-C6-policy-limit").disabled = false;
		document.getElementById("S1-C6-claim-limit").disabled = false;
		document.getElementById("S1-C6-per-policy-limit").disabled = false;
		document.getElementById("S1-C6-per-claim-limit").disabled = false;
		}
	
	if(ccObj =='N' && preExcObj=='N')
	{
	document.getElementById("S1-C6-policy-limit").disabled = true;
	document.getElementById("S1-C6-claim-limit").disabled = true;
	document.getElementById("S1-C6-per-policy-limit").disabled = true;
	document.getElementById("S1-C6-per-claim-limit").disabled = true;
	}
		
	}
}

// Chronic Condition Covered for Out_patient														
function showHideFieldsOutPt(covered,waitPeriod,days,pplimit,clplimit,ppCopaylimit,ppDedlimit)
{
	var benfID=document.getElementById("benefitID").value;
	if(benfID=='OPTS')
	{
	if(document.getElementById(covered).value=='N')
	{
		document.getElementById(waitPeriod).disabled = true;
		document.getElementById(days).disabled = true;
		document.getElementById(pplimit).disabled = true;
		document.getElementById(clplimit).disabled = true;
		document.getElementById(ppCopaylimit).disabled = true;
		document.getElementById(ppDedlimit).disabled = true;
		
		document.getElementById(waitPeriod).value = "";
		document.getElementById(days).value = "";
		document.getElementById(pplimit).value = "";
		document.getElementById(clplimit).value = "";
		document.getElementById(ppCopaylimit).value = "";
		document.getElementById(ppDedlimit).value = "";
		
		
	}
	if(document.getElementById(covered).value=='Y')
	{
		document.getElementById(waitPeriod).disabled = false;
		document.getElementById(days).disabled = false;
		document.getElementById(pplimit).disabled = false;
		document.getElementById(clplimit).disabled = false;
		document.getElementById(ppCopaylimit).disabled = false;
		document.getElementById(ppDedlimit).disabled = false;
		
		
	}
	
	var ccObj=document.getElementById("S1-C7-chronic-covered").value;
	var preExcObj=document.getElementById("S1-C8-Pre-existing").value;
	
	if(ccObj =='Y' || preExcObj=='Y')
		{
		document.getElementById("S1-C6-policy-limit").disabled = false;
		document.getElementById("S1-C6-claim-limit").disabled = false;
		document.getElementById("S1-C6-per-policy-limit").disabled = false;
		document.getElementById("S1-C6-per-claim-limit").disabled = false;
		}
	
	if(ccObj =='N' && preExcObj=='N')
	{
	document.getElementById("S1-C6-policy-limit").disabled = true;
	document.getElementById("S1-C6-claim-limit").disabled = true;
	document.getElementById("S1-C6-per-policy-limit").disabled = true;
	document.getElementById("S1-C6-per-claim-limit").disabled = true;
	}
	}
}	


function setChronicFields() 		
{
		var benfID=document.getElementById("benefitID").value;
			
		if(benfID=='IPT'||benfID=='OPTS'){
				
				
						var ccObj=document.getElementById("S1-C7-chronic-covered");
						var waitPrObj=document.getElementById("S1-C7-waiting-period");
						var daysObj=document.getElementById("S1-C7-days");
						var plObj=document.getElementById("S1-C7-policy-limit");
						var clObj=document.getElementById("S1-C7-claim-limit");
						var perPlObj=document.getElementById("S1-C7-per-policy-limit");
						var perClObj=document.getElementById("S1-C7-per-claim-limit");
						
						
						var preExcObj=document.getElementById("S1-C8-Pre-existing");
						var waitPrObj2=document.getElementById("S1-C8-waiting-period");
						var daysObj2=document.getElementById("S1-C8-days");
						var plObj2=document.getElementById("S1-C8-policy-limit");
						var clObj2=document.getElementById("S1-C8-claim-limit");
						var perPlObj2=document.getElementById("S1-C8-per-policy-limit");
						var perClObj2=document.getElementById("S1-C8-per-claim-limit");
						
						if(preExcObj!=null&&preExcObj!=undefined)
						{
							if(preExcObj.value=='Y')
							{
								if(perClObj2!=null&&perClObj2!=undefined)
								{
									perClObj2.disabled = false;
								}
							}
							else  if(preExcObj.value=='N')
							{
					   
								if(perClObj2!=null&&perClObj2!=undefined)
								{
									perClObj2.disabled = true;
								}
							}
						}
						
						if(preExcObj!=null&&preExcObj!=undefined)
						{
							if(preExcObj.value=='Y')
							{
								if(perPlObj2!=null&&perPlObj2!=undefined)
								{
									perPlObj2.disabled = false;
								}
							}
							else  if(preExcObj.value=='N')
							{
					   
								if(perPlObj2!=null&&perPlObj2!=undefined)
								{
									perPlObj2.disabled = true;
								}
							}
						}
						
						if(preExcObj!=null&&preExcObj!=undefined)
						{
							if(preExcObj.value=='Y')
							{
								if(clObj2!=null&&clObj2!=undefined)
								{
									clObj2.disabled = false;
								}
							}
							else  if(preExcObj.value=='N')
							{
					   
								if(clObj2!=null&&clObj2!=undefined)
								{
									clObj2.disabled = true;
								}
							}
						}
						
						if(preExcObj!=null&&preExcObj!=undefined)
						{
							if(preExcObj.value=='Y')
							{
								if(plObj2!=null&&plObj2!=undefined)
								{
									plObj2.disabled = false;
								}
							}
							else  if(preExcObj.value=='N')
							{
					   
								if(plObj2!=null&&plObj2!=undefined)
								{
									plObj2.disabled = true;
								}
							}
						}
						
						if(preExcObj!=null&&preExcObj!=undefined)
						{
							if(preExcObj.value=='Y')
							{
								if(daysObj2!=null&&daysObj2!=undefined)
								{
									daysObj2.disabled = false;
								}
							}
							else  if(preExcObj.value=='N')
							{
					   
								if(daysObj2!=null&&daysObj2!=undefined)
								{
									daysObj2.disabled = true;
								}
							}
						}
						
						if(preExcObj!=null&&preExcObj!=undefined)
						{
							if(preExcObj.value=='Y')
							{
								if(waitPrObj2!=null&&waitPrObj2!=undefined)
								{
									waitPrObj2.disabled = false;
								}
							}
							else  if(preExcObj.value=='N')
							{
					   
								if(waitPrObj2!=null&&waitPrObj2!=undefined)
								{
									waitPrObj2.disabled = true;
								}
							}
						}
						
						if(ccObj!=null&&ccObj!=undefined)
						{
							if(ccObj.value=='Y')
							{
								if(waitPrObj!=null&&waitPrObj!=undefined)
								{
									waitPrObj.disabled = false;
								}
							}
							else  if(ccObj.value=='N')
							{
					   
								if(waitPrObj!=null&&waitPrObj!=undefined)
								{
									waitPrObj.disabled = true;
								}
							}
						}
						
						if(ccObj!=null&&ccObj!=undefined)
						{
							if(ccObj.value=='Y')
							{
								if(daysObj!=null&&daysObj!=undefined)
								{
									daysObj.disabled = false;
								}
							}
							else  if(ccObj.value=='N')
							{
					   
								if(daysObj!=null&&daysObj!=undefined)
								{
									daysObj.disabled = true;
								}
							}
						}
						
						if(ccObj!=null&&ccObj!=undefined)
						{
							if(ccObj.value=='Y')
							{
								if(plObj!=null&&plObj!=undefined)
								{
									plObj.disabled = false;
								}
							}
							else  if(ccObj.value=='N')
							{
					   
								if(plObj!=null&&plObj!=undefined)
								{
									plObj.disabled = true;
								}
							}
						}
						
						if(ccObj!=null&&ccObj!=undefined)
						{
							if(ccObj.value=='Y')
							{
								if(clObj!=null&&clObj!=undefined)
								{
									clObj.disabled = false;
								}
							}
							else  if(ccObj.value=='N')
							{
					   
								if(clObj!=null&&clObj!=undefined)
								{
									clObj.disabled = true;
								}
							}
						}
						
						if(ccObj!=null&&ccObj!=undefined)
						{
							if(ccObj.value=='Y')
							{
								if(perPlObj!=null&&perPlObj!=undefined)
								{
									perPlObj.disabled = false;
								}
							}
							else  if(ccObj.value=='N')
							{
					   
								if(perPlObj!=null&&perPlObj!=undefined)
								{
									perPlObj.disabled = true;
								}
							}
						}
						
						if(ccObj!=null&&ccObj!=undefined)
						{
							if(ccObj.value=='Y')
							{
								if(perClObj!=null&&perClObj!=undefined)
								{
									perClObj.disabled = false;
								}
							}
							else  if(ccObj.value=='N')
							{
					   
								if(perClObj!=null&&perClObj!=undefined)
								{
									perClObj.disabled = true;
								}
							}
						}
			}
}

function onLoadUtility() {
	var benfID=document.getElementById("benefitID").value;
	if(benfID=='IPT'||benfID=='OPTS'){
	setChronicFields();
	enablePedChronic();
	}
}

function enableRequiredFields(){									
	var benfID=document.getElementById("benefitID").value;
	if(benfID=='IPT'||benfID=='OPTS'){
	document.getElementById('S1-C7-waiting-period').disabled = false;
	document.getElementById('S1-C7-days').disabled = false;
	document.getElementById('S1-C7-policy-limit').disabled = false;
	document.getElementById('S1-C7-claim-limit').disabled = false;
	document.getElementById('S1-C7-per-policy-limit').disabled = false;
	document.getElementById('S1-C7-per-claim-limit').disabled = false;
	
	
	document.getElementById('S1-C8-waiting-period').disabled = false;
	document.getElementById('S1-C8-days').disabled = false;
	document.getElementById('S1-C8-policy-limit').disabled = false;
	document.getElementById('S1-C8-claim-limit').disabled = false;
	document.getElementById('S1-C8-per-policy-limit').disabled = false;
	document.getElementById('S1-C8-per-claim-limit').disabled = false;
	
	// for PED+CHRONIC
	document.getElementById('S1-C6-policy-limit').disabled = false;
	document.getElementById('S1-C6-claim-limit').disabled = false;
	document.getElementById('S1-C6-per-policy-limit').disabled = false;
	document.getElementById('S1-C6-per-claim-limit').disabled = false;
	}
}
function enablePedChronic()			
{
	var ccObj=document.getElementById("S1-C7-chronic-covered").value;
	var preExcObj=document.getElementById("S1-C8-Pre-existing").value;

	if(ccObj =='Y' || preExcObj=='Y')
		{
		document.getElementById("S1-C6-policy-limit").disabled = false;
		document.getElementById("S1-C6-claim-limit").disabled = false;
		document.getElementById("S1-C6-per-policy-limit").disabled = false;
		document.getElementById("S1-C6-per-claim-limit").disabled = false;
		}

	if(ccObj =='N' && preExcObj=='N')
	{
	document.getElementById("S1-C6-policy-limit").disabled = true;
	document.getElementById("S1-C6-claim-limit").disabled = true;
	document.getElementById("S1-C6-per-policy-limit").disabled = true;
	document.getElementById("S1-C6-per-claim-limit").disabled = true;
	}
}

// percentage validation
function percentageValidation(field) 
{
    var re = /^[0-9]*\.*[0-9]*$/;
    if (!re.test(field.value))
    {
        alert("Data entered must be Numeric!");
		field.focus();
		field.value="";
		return false;
    }
    
    if(Number(document.getElementById("S1-C1-inpt-copay").value)< 0  || Number(document.getElementById("S1-C1-inpt-copay").value)> 100 ) 
    {	
    	alert("Percentage should be less than or equal to 100");
    	field.value="";
    	field.value.focus();
    	return false;
    }
}