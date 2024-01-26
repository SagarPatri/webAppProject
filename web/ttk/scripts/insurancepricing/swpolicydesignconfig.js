//bajaj


function onClose()
 {
	
 	if(!TrackChanges()) return false;
 	document.forms[1].mode.value = "doClose";
 	document.forms[1].action = "/SwInsuranceApproveConfiguration.do";
 	document.forms[1].submit();	
 }//end of onClose() 


function onSave()
{
		trimForm(document.forms[1]); 
		if(!JS_SecondSubmit)
		{
		    document.forms[1].mode.value="doSave"; 
		    document.forms[1].action="/SwPlanDesignConfigurationAction.do"; 
		    JS_SecondSubmit=true;
		   document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
}//end of onSave()

function calculateTable2()
{
		trimForm(document.forms[1]); 
		
		if(!isAllFieldsDisable("ipCol")&&(parseFloat(getCrdbltValues("IP"))>100||parseFloat(getCrdbltValues("IP"))<100)){
			
			alert("Inpatient credibility number should add upto 100%");
			return;
		}else if(!isAllFieldsDisable("opCol")&&(parseFloat(getCrdbltValues("OP"))>100||parseFloat(getCrdbltValues("OP"))<100)){
			alert("Outpatient credibility number should add upto 100%");
			return;
		}else if(!isAllFieldsDisable("matIpCol")&&(parseFloat(getCrdbltValues("MIP"))>100||parseFloat(getCrdbltValues("MIP"))<100)){
			alert("Maternity Inpatient credibility number should add upto 100%");
			return;
		}else if(!isAllFieldsDisable("matOpCol")&&(parseFloat(getCrdbltValues("MOP"))>100||parseFloat(getCrdbltValues("MOP"))<100)){
			alert("Maternity Outpatient credibility number should add upto 100%");
			return;
		}			
		  
		
		if(!JS_SecondSubmit)
		{
		    document.forms[1].mode.value="calculateTable2"; 
		    document.forms[1].action="/SwPlanDesignConfigurationAction.do"; 
		    JS_SecondSubmit=true;
		   document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
}//end of calculateTable2()

function calculateTable3()
{
		if(checkASLAndVL()){
			var commds=trim(document.forms[1].table3Commands.value);
			if(commds==null||commds==""||commds.length==0){
				alert("Comments are required");
				document.forms[1].table3Commands.focus();
				return;
			}
		}
		if(!JS_SecondSubmit)
		{
		    document.forms[1].mode.value="calculateTable3"; 
		    document.forms[1].action="/SwPlanDesignConfigurationAction.do"; 
		    JS_SecondSubmit=true;
		   document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
}//end of calculateTable3()
function checkASLAndVL(){
	return (
				
			isFieldNotEmty('tth1Value1')||
			isFieldNotEmty('tth2Value1')||
			isFieldNotEmty('tth3Value1')||
			isFieldNotEmty('tth4Value1')||
			
			isFieldNotEmty('tth1Value2')||
			isFieldNotEmty('tth2Value2')||
			isFieldNotEmty('tth3Value2')||
			isFieldNotEmty('tth4Value2')
	);
	
	
}
function isFieldNotEmty(pFieldName){
	var sFlag=false;
	var fv=trim(document.forms[1][pFieldName].value);
	if(fv!=null&&fv!=""&&fv.length!=0){
		sFlag=true;
	}
	return sFlag;
}
function onSaveDemography()
{
	var flag ="N";
	/*for(var i=1;i<19;i++)
	{
		var currrentPolicy = document.getElementById("democurrentPolicy["+i+"]").value;
			if (currrentPolicy == "" ) {
			flag = "Y";
		}
	}
	
	if (flag == "Y" ) {
		 if (confirm("Are you sure you want to proceed without entering the current policy details?")){
			 
		 }
		 else{
			 document.getElementById("democurrentPolicy["+i+"]").focus();
			 return false;
		 }
		
	}*/
	
	
	trimForm(document.forms[1]); 
	if(!JS_SecondSubmit)
	{
	    document.forms[1].mode.value="doSaveDemography"; 
	    document.forms[1].action="/SwUpdatePolicyDesignConfiguration.do"; 
	    JS_SecondSubmit=true;
	   document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}

function onSaveCalculate()
{
	
	var totaliterate = document.getElementById("totaliterate").value;
	var prpINPCPM = document.getElementById("prpINPCPM").value;
	var prpOUTCPM = document.getElementById("prpOUTCPM").value;
	var prpMATCPM = document.getElementById("prpMATCPM").value;
	var prpOPTCPM = document.getElementById("prpOPTCPM").value;
	var prpDENTCPM = document.getElementById("prpDENTCPM").value;
	if ( isNaN( prpINPCPM ) || prpINPCPM == "" ) {
		prpINPCPM = 0;
	}
	if ( isNaN( prpOUTCPM ) || prpOUTCPM == "" ) {
		prpOUTCPM = 0;
	}
	if ( isNaN( prpMATCPM ) || prpMATCPM == "" ) {
		prpMATCPM = 0;
	}
	if ( isNaN( prpOPTCPM ) || prpOPTCPM == "" ) {
		prpOPTCPM = 0;
	}
	if ( isNaN( prpDENTCPM ) || prpDENTCPM == "" ) {
		prpDENTCPM = 0;
	}

	
	var increditfirstvalue = 0;
	var outpatientfirstvalue = 0;
	var maternityfirstvalue = 0;
	var opticalfirstvalue = 0;
	var dentalfirstvalue = 0;
			for(var i=0;i<totaliterate;i++)
			{
				var inpatientcrediblty = document.getElementById("inpatientcrediblty["+i+"]").value;
				var outpatientcrediblty = document.getElementById("outpatientcrediblty["+i+"]").value;
				var maternitycrediblty = document.getElementById("maternitycrediblty["+i+"]").value;
				var opticalcrediblty = document.getElementById("opticalcrediblty["+i+"]").value;
				var dentalcrediblty = document.getElementById("dentalcrediblty["+i+"]").value;
				
				if ( isNaN( inpatientcrediblty ) || inpatientcrediblty == "" ) {
					inpatientcrediblty = 0;
				}
				if ( isNaN( outpatientcrediblty ) || outpatientcrediblty == "" ) {
					outpatientcrediblty = 0;
				}
				if ( isNaN( maternitycrediblty ) || maternitycrediblty == "" ) {
					maternitycrediblty = 0;
				}
				if ( isNaN( opticalcrediblty ) || opticalcrediblty == "" ) {
					opticalcrediblty = 0;
				}
				if ( isNaN( dentalcrediblty ) || dentalcrediblty == "" ) {
					dentalcrediblty = 0;
				}
				increditfirstvalue =	 parseFloat(inpatientcrediblty)+parseInt(increditfirstvalue);
				outpatientfirstvalue =	 parseFloat(outpatientcrediblty)+parseInt(outpatientfirstvalue);
				maternityfirstvalue =	 parseFloat(maternitycrediblty)+parseInt(maternityfirstvalue);
				opticalfirstvalue =	 parseFloat(opticalcrediblty)+parseInt(opticalfirstvalue);
				dentalfirstvalue =	 parseFloat(dentalcrediblty)+parseInt(dentalfirstvalue);

			}
			if(prpINPCPM > 0 && increditfirstvalue != 100)
			{
				alert("Inpatient credibility numbers should add up to 100%");
				return false;
			}
			
			if(prpOUTCPM > 0 && outpatientfirstvalue != 100)
			{
				alert("Outpatient credibility numbers should add up to 100%");
				return false;
			}
			
			if(prpMATCPM > 0 && maternityfirstvalue != 100)
			{
				alert("Maternity credibility numbers should add up to 100%");
				return false;
			}
			
			if(prpOPTCPM > 0 && opticalfirstvalue != 100)
			{
				alert("Optical credibility numbers should add up to 100%");
				return false;
			}
			
			if(prpDENTCPM > 0 && dentalfirstvalue != 100)
			{
				alert("Dental credibility numbers should add up to 100%");
				return false;
			}
	
		trimForm(document.forms[1]); 
		if(!JS_SecondSubmit)
		{
		    document.forms[1].mode.value="doSaveCalculate"; 
		    document.forms[1].action="/SwUpdatePolicyDesignConfiguration.do"; 
		    JS_SecondSubmit=true;
		   document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	
}//end of onSave()



function onSaveLoading()
{
		trimForm(document.forms[1]); 
		var fieldvalue =parseFloat(document.getElementById("load_DeductTypePercentage[0]").value);
		var field =document.getElementById("load_DeductTypePercentage[0]").value;
		if(fieldvalue > 20){
			alert("Broking commission should not exceed 20%");
			field.focus();
			return false;
		}
		
		var comments = document.getElementById("loadComments").value;
		if(comments == "")
			{
			alert("Please specify comments");
			document.getElementById("loadComments").focus();
			return false;
			}
	
			
		if(!JS_SecondSubmit)
		{
		    document.forms[1].mode.value="doLoadingCalculate"; 
		    document.forms[1].action="/SwGrossPremiumCalculate.do"; 
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
	var proceedbutton = "N";
    document.forms[1].mode.value = "doIncomeProfile";
    document.forms[1].tab.value ="Income Profile";
    document.forms[1].action = "/SwInsPricingActionIncome.do?proceedbutton="+proceedbutton;
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

function loadingValidation(field)
{
	//alert(load_DeductType);
	//alert(load_DeductType.value);
	if(field.value > 20){
		alert("Loadings Discount Should Not Exceed 20%");
		field.focus();
		field.value="10";
		return false;
	}
		
}

function onViewInputSummary(){
	   
	var partmeter = "?mode=doViewInputSummary&addedBy="+document.forms[1].addedBy.value+"&reportID=mainInputPricing&reportType=PDF";
	   var openPage = "/SwFinalOutputAction.do"+partmeter;
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
	   
	
}

function onViewPricingSummary(){
	   // document.forms[1].reset();
	   
	    document.forms[2].mode.value= "doDefaultQuotation"; 
	    document.forms[2].tab.value ="Generate Quote";
	    document.forms[2].action="/SwGenerateQuotationAction.do"; 
	    document.forms[2].submit();
	
}


function onViewGrosspremium(){
   // document.forms[1].reset();
    document.forms[1].mode.value= "doDefaultGrossPremium"; 
    document.forms[1].tab.value ="Gross Premium";
    document.forms[1].action="/SwGenerateQuotationAction.do"; 
    document.forms[1].submit();

}


function gettotallivesType(obj){
    var flag="SCRN3";
    var policy_no="";
    var tot_lives=obj.value;
    var grp_prof_seq_id=  document.getElementById("groupProfileSeqID").value;
  
    
var  path="/asynchronAction.do?mode=getTotalLivesPricing&flag="+flag+"&policy_no="+policy_no+"&tot_lives="+tot_lives+"&grp_prof_seq_id="+grp_prof_seq_id;
$.ajax({
    url :path,
    dataType:"text",
    success : function(data) {
    var res1 = data;
    if(res1!=""){
      alert(data);
     }
    }//function(data)
});

}

function onCloseGrossPremium(){
	
	var proceedbutton = "N";
	 //   document.forms[1].reset();
	    document.forms[2].mode.value="doDefault"; 
	    document.forms[2].tab.value ="Plan design";
	    document.forms[2].action="/SwPlanDesignConfigurationAction.do?proceedbutton="+proceedbutton; 
	    document.forms[2].submit();
	
}



function onCloseOutput(){
	var proceedbutton = "N";
	if(!JS_SecondSubmit)
	{
	    document.forms[1].mode.value="doDefaultGrossPremium"; 
	    document.forms[1].tab.value ="Gross Premium";
	    document.forms[1].action="/SwFinalOutputAction.do?proceedbutton="+proceedbutton; 
	    JS_SecondSubmit=true;
	   document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}





function onbenefitvalueCheck(objvalue1,credibility){
	
	if(credibility == "inpatient")
		{
			if(document.getElementById("inPatientCPMvalue").value == ""){
				alert("Please enter inpatient current policy experience");
				objvalue1.value = "";
				return false;
			}
		}

	if(credibility == "outpatient")
		{
			if(document.getElementById("outPatientCPMvalue").value == ""){
				alert("Please enter outpatient current policy experience");
				objvalue1.value = "";
				return false;
			}
		}
		
	if(credibility == "maternity")
		{
			if(document.getElementById("maternityCPMvalue").value == ""){
				alert("Please enter maternity current policy experience");
				objvalue1.value = "";
				return false;
			}
		}
	if(credibility == "optical")
		{
			if(document.getElementById("opticalCPMvalue").value == ""){
				alert("Please enter optical current policy experience");
				objvalue1.value = "";
				return false;
			}
		}	
	if(credibility == "dental")
		{
			if(document.getElementById("dentalCPMvalue").value == ""){
				alert("Please enter dental current policy experience");
				objvalue1.value = "";
				return false;
			}
		}
	
	  
	
}

function dateValidate(objDate) {
	var pattern =/^([0-9]{2})\/([0-9]{2})\/([0-9]{4})$/;
    var dateInput1 = objDate.value;
    if (!pattern.test(dateInput1)){
        alert("Date format should be (dd/mm/yyyy)");
        objDate.value="";
        objDate.focus();
        return false;
    }

}


function onProceedProposal(){ 

	if(!JS_SecondSubmit)
	{
	    document.forms[2].mode.value="doDefaultGrossPremium"; 
	    document.forms[2].tab.value ="Gross Premium";
	    document.forms[2].action="/SwGenerateQuotationAction.do"; 
	    JS_SecondSubmit=true;
	   document.forms[2].submit();
	}//end of if(!JS_SecondSubmit)
	
	
}

function setCurrentTab(CTNumb){ 
	 
	document.getElementById("currentTabID").value = CTNumb;		 
}

function isDateIfNotEmpty(dtObj){ 
	if( dtObj.value!=""&& dtObj.value!=null){
	if(!isDate(dtObj,'Date(DD/MM/YYYY)')){
		 dtObj.value="";
		 dtObj.focus();
	 }
	}
}
function isValidNumber(obj){ 
	if(obj!=null&&obj.value!=""){
	if(isNaN(obj.value)){
		alert("Please enter a valid number");
		obj.value="";
		obj.focus();
	}
	}
}

function checkPrcnt200(obj,msg) {
	if(obj.value!=""&&isNaN(obj.value)){
		alert("Please enter a valid number");
		obj.value="";
		obj.focus();
		return;
	}
	if(parseFloat(obj.value)>50 )
	{
		var result = msg.match(/Anti Selection Load/i);
		if(result=='Anti Selection Load' ){
			if(!confirm(msg+" is greater than 50%, Do you want to proceed?"))
			{
				return;		
			}
		}
	}
	if(parseFloat(obj.value)>200){
		alert(msg+" should not exceed more than 200%");
		obj.value="";
		obj.focus();
		return;
	}
}

function chkCrdbPrc(obj,aID) {
	if(obj.value!=""&&isNaN(obj.value)){
		alert("Please enter a valid number");
		obj.value="";
		obj.focus();
		return;
	}
	
	//IP checking
	var tot=getCrdbltValues(aID);
	
	if(parseFloat(tot)>100){
		var strMsg="";
		if(aID=="IP")strMsg="Inpatient";
		else if(aID=="OP")strMsg="Outpatient";
		else if(aID=="MIP")strMsg="Maternity Inpatient";
		else if(aID=="MOP")strMsg="Maternity Outpatient";
		
		alert(strMsg+" credibility number should add upto 100%");
		obj.value="";
		obj.focus();
	}
}

function getCrdbltValues(aID){
	var tot=0;
	if(aID=='IP'){
		 tot+=getRowValues("ipCol");
		var cpIP=document.forms[1].demoCredIp2;
		var ppIP=document.forms[1].ip2Col;
		if(cpIP!=undefined&&cpIP.value!="" && !(isNaN(cpIP.value))){
			tot+=parseFloat(cpIP.value);
		}
		if(ppIP!=undefined&&ppIP.value!="" && !(isNaN(ppIP.value))){
			tot+=parseFloat(ppIP.value);
		}		
	}else if(aID=='OP'){
		 tot+=getRowValues("opCol");
			var cpOP=document.forms[1].demoCredOp2;
			var ppOP=document.forms[1].op2Col;
			if(cpOP!=undefined&&cpOP.value!="" && !(isNaN(cpOP.value))){
				tot+=parseFloat(cpOP.value);
			}
			if(ppOP!=undefined&&ppOP.value!="" && !(isNaN(ppOP.value))){
				tot+=parseFloat(ppOP.value);
			}		
		}else if(aID=='MIP'){
			 tot+=getRowValues("matIpCol");
				var cpMIP=document.forms[1].demoCredMatIp2;
				var ppMIP=document.forms[1].matIp2Col;
				if(cpMIP!=undefined&&cpMIP.value!="" && !(isNaN(cpMIP.value))){
					tot+=parseFloat(cpMIP.value);
				}
				if(ppMIP!=undefined&&ppMIP.value!="" && !(isNaN(ppMIP.value))){
					tot+=parseFloat(ppMIP.value);
				}		
			}  else if(aID=='MOP'){
			 tot+=getRowValues("matOpCol");//demoCredMatOp2 matOp2Col
				var cpMOP=document.forms[1].demoCredMatOp2;
				var ppMOP=document.forms[1].matOp2Col;
				if(cpMOP!=undefined&&cpMOP.value!="" && !(isNaN(cpMOP.value))){
					tot+=parseFloat(cpMOP.value);
				}
				if(ppMOP!=undefined&&ppMOP.value!="" && !(isNaN(ppMOP.value))){
					tot+=parseFloat(ppMOP.value);
				}		
			}
	return tot;
}
function isAllFieldsDisable(fName) {
	var sFlag=true;
	var rowID=0;
	if(fName=="ipCol"){
		var cpIP=document.forms[1].demoCredIp2;
		var ppIP=document.forms[1].ip2Col;
		if((cpIP!=undefined&&cpIP.disabled!=true)||(ppIP!=undefined&&ppIP.disabled!=true)){
			return false;
		}
	}else if(fName=="opCol"){
		var cpOP=document.forms[1].demoCredOp2;
		var ppOP=document.forms[1].op2Col;
		if((cpOP!=undefined&&cpOP.disabled!=true)||(ppOP!=undefined&&ppOP.disabled!=true)){
			return false;
		}
	} else if(fName=="matIpCol"){
		var cpMatIP=document.forms[1].demoCredMatIp2;
		var ppMatIP=document.forms[1].matIp2Col;
		if((cpMatIP!=undefined&&cpMatIP.disabled!=true)||(ppMatIP!=undefined&&ppMatIP.disabled!=true)){
			return false;
		}
	}else if(fName=="matOpCol"){
		var cpMatOP=document.forms[1].demoCredMatOp2;
		var ppMatOP=document.forms[1].matOp2Col;
		if((cpMatOP!=undefined&&cpMatOP.disabled!=true)||(ppMatOP!=undefined&&ppMatOP.disabled!=true)){
			return false;
		}
	}
	
	while(true){
		var field=document.forms[1][fName+rowID];
		if( field!=undefined){
			
			if(!(field.disabled)){
				sFlag=false;
				break;
			}
		}else{
			break;
		}
		rowID++;
	}
	return sFlag;
}

function getRowValues(fName) {
	var tot=0;
	var rowID=0;
	while(true){
		var field=document.forms[1][fName+rowID];
		if( field!=undefined){
			if(field.value!=""&&!(isNaN(field.value))){
			tot+=parseFloat(field.value);
			}
		}else{
			break;
		}
		rowID++;
	}
	return tot;
}
function onBack(){ 

	if(!JS_SecondSubmit)
	{
	    document.forms[2].mode.value="doIncomeProfile"; 
	    document.forms[2].tab.value ="Income Profile";
	    document.forms[2].action="/SwInsPricingActionIncome.do"; 
	    JS_SecondSubmit=true;
	   document.forms[2].submit();
	}//end of if(!JS_SecondSubmit)
	
	
}


