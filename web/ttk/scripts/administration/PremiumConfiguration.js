/*
function onSaveAsPlus()
{
	
	  var capitationtype= document.getElementById("capitationtype").value;
	 
	var capitationYN= document.getElementById("capitationYN").value;
	
	
	
	  var ipNetPremium= document.getElementById("ipNetPremium").value;
		 
		var opNetPremium= document.getElementById("opNetPremium").value;
		var medicalPremium= document.getElementById("medicalPremium").value;
		
		
	if(capitationYN != "1" && capitationtype==""){
		
		alert("Please Select Asplus(OP/IP&OP)");
		document.getElementById("capitationtype").focus();
		 return false;
	} 
	else if(medicalPremium ==""){
		
		alert("Please Select Medical Premium)");
		document.getElementById("medicalPremium").focus();
		 return false;
	} 
	else if(ipNetPremium=="" && opNetPremium==""){
		alert("Please Select either IP Net Premium or OP Net Premium");
		document.getElementById("ipNetPremium").focus();
		 return false;
	}

	
	 if(!((document.getElementById("maritalStatus").value)==="") && !((document.getElementById("relation").value)==="") && !((document.getElementById("maritalStatus").value)===""))
		 {
	 document.forms[1].mode.value="doSavePremiumConfigurarion";
	  document.forms[1].action="/Configuration.do";
	   JS_SecondSubmit=true;
	  document.forms[1].submit();
		 }
	 else
		 {
		 alert("Fields should not be Empty");
		 }
}



*/


function onSaveAsPlus()
{
	var updateRemarks;
	//var capitationtype= document.getElementById("capitationtype").value;
	//var capitationYN= document.getElementById("capitationYN").value;
	  var ipNetPremium= document.getElementById("ipNetPremium").value;
		 
		var opNetPremium= document.getElementById("opNetPremium").value;
		var medicalPremium= document.getElementById("medicalPremium").value;
		var healthAuthority= document.getElementById("healthAuthority").value;
	
	
		
		 var ipNetPremiumAmt = document.forms[1].ipNetPremium.value;
		 ipNetPremiumAmt=(ipNetPremiumAmt==null||ipNetPremiumAmt===""||ipNetPremiumAmt==="0")?0:ipNetPremiumAmt;
		 var opNetPremiumAmt = document.forms[1].opNetPremium.value;
		 opNetPremiumAmt=(opNetPremiumAmt==null||opNetPremiumAmt===""||opNetPremiumAmt==="0")?0:opNetPremiumAmt;
		 var netPremium = document.forms[1].netPremium.value;
		 var ipOpSum=Number(ipNetPremiumAmt)+Number(opNetPremiumAmt);
		 
		 var grossPremiumAmt = document.forms[1].grossPremium1.value;
		 grossPremiumAmt=(grossPremiumAmt==null||grossPremiumAmt===""||grossPremiumAmt==="0")?0:grossPremiumAmt;
	
		 
		 var insurerMarginAED = document.forms[1].insurerMarginAED.value;
		 insurerMarginAED=(insurerMarginAED==null||insurerMarginAED===""||insurerMarginAED==="0")?0:insurerMarginAED;
		 var brokerMarginAED = document.forms[1].brokerMarginAED.value;
		 brokerMarginAED=(brokerMarginAED==null||brokerMarginAED===""||brokerMarginAED==="0")?0:brokerMarginAED;
		 var reInsBrkMarginAED = document.forms[1].reInsBrkMarginAED.value;
		 reInsBrkMarginAED=(reInsBrkMarginAED==null||reInsBrkMarginAED===""||reInsBrkMarginAED==="0")?0:reInsBrkMarginAED;
		 var otherMarginAED = document.forms[1].otherMarginAED.value;
		 otherMarginAED=(otherMarginAED==null||otherMarginAED===""||otherMarginAED==="0")?0:otherMarginAED;
		 
		 var totalAmt=Number(insurerMarginAED)+Number(brokerMarginAED)+Number(reInsBrkMarginAED)+Number(otherMarginAED);
		 totalAmt=(totalAmt==null||totalAmt===""||totalAmt==="0")?0:totalAmt;

		 
	var salaryBand = document.getElementById("salaryBand").value;
	if(salaryBand=="" || salaryBand==='')
	{
		alert("Salary Band should not be empty");
		document.forms[1].salaryBand.focus();
		return false;
	}
	var minAge = document.getElementById("minAge").value;
	if(minAge=="" || minAge==='')
	{
		alert("Minimum Age should not be empty");
		document.forms[1].minAge.focus();
		return false;
	}
	
	var maxAge = document.getElementById("maxAge").value;
	if(maxAge=="" || maxAge==='')
	{
		alert("Maximum Age should not be empty");
		document.forms[1].maxAge.focus();
		return false;
	}	
	/*if(capitationYN != "1" && capitationtype=="")
	{
		alert("Please Select Asplus(OP/IP&OP)");
		document.getElementById("capitationtype").focus();
		return false;
	}*/
	if(medicalPremium ==""){
		
		alert("Please Select Medical Premium)");
		document.getElementById("medicalPremium").focus();
		 return false;
	} 
	 if(ipNetPremium=="" && opNetPremium==""){
		alert("Please Select either IP Net Premium or OP Net Premium");
		document.getElementById("ipNetPremium").focus();
		 return false;
	}
	 
	 if(totalAmt>grossPremiumAmt){
			alert("Sum of all margins should not be greater than the Gross Premium");
			 return false;   
		}
	 
	 
		
	 if(ipOpSum>netPremium){
		alert("Sum of IP Net Premium and OP Net Premium should not be greater than the Net Premium");
		 return false;   
	}
	 if(healthAuthority=='DHA'){
		 var tpaFeeType= document.forms[1].tpaFeeType.value;
		 var tpaFee= document.forms[1].tpaFee.value;
		 if(tpaFee=="" || tpaFee==='')
			{
				alert("TPA FEE should not be empty");
				document.forms[1].tpaFee.focus();
				return false;
			}
		 if(tpaFeeType=="" || tpaFeeType==='')
			{
				alert("TPA Fee Type should not be empty");
				document.forms[1].tpaFeeType.focus();
				return false;
			} 
	 }
	if(document.getElementById("updateRemarks")==null)
	{
		updateRemarks="";
	}
	else
	{
		updateRemarks = document.getElementById("updateRemarks").value;
		if(updateRemarks=="" || updateRemarks==='')
		{
			 alert("updateRemarks should not be empty");
			 document.forms[1].updateRemarks.focus();
			 return false;
		}
	}
	if(!((document.getElementById("maritalStatus").value)==="") && !((document.getElementById("relation").value)==="") && !((document.getElementById("maritalStatus").value)===""))
	{
		document.forms[1].mode.value="doSavePremiumConfigurarion";
		document.forms[1].action="/Configuration.do";
	  	document.forms[1].submit();
	}
	else
	{
		 alert("Fields should not be Empty");
	 }
}






function onSaveASO()
{
	var updateRemarks;
	var salaryBand = document.getElementById("salaryBand").value;
	var medicalPremium= document.getElementById("medicalPremium").value;
	var tpaFeeType= document.forms[1].tpaFeeType.value;
	var tpaFee= document.forms[1].tpaFee.value;
	if(salaryBand=="" || salaryBand==='')
	{
		alert("Salary Band should not be empty");
		document.forms[1].salaryBand.focus();
		return false;
	}

	var minAge = document.getElementById("minAge").value;
	if(minAge=="" || minAge==='')
	{
		alert("Minimum Age should not be empty");
		document.forms[1].minAge.focus();
		return false;
	}
	
	var maxAge = document.getElementById("maxAge").value;
	if(maxAge=="" || maxAge==='')
	{
		alert("Maximum Age should not be empty");
		document.forms[1].maxAge.focus();
		return false;
	}
	
	if(document.getElementById("updateRemarks")==null)
	{
		updateRemarks="";
	}
	else
	{
		updateRemarks = document.getElementById("updateRemarks").value;
		updateRemarks =jQuery.trim(updateRemarks);
		if(updateRemarks=="" || updateRemarks==='' || updateRemarks.length ==0)
		{
			alert("updateRemarks should not be empty");
			document.forms[1].updateRemarks.focus();
			return false;
		}
		
	}
		
	if(medicalPremium ==""){
			
			alert("Please Select Medical Premium)");
			document.getElementById("medicalPremium").focus();
			 return false;
		} 
	 if(tpaFee=="" || tpaFee==='')
		{
			alert("TPA FEE should not be empty");
			document.forms[1].tpaFee.focus();
			return false;
		}
	 if(tpaFeeType=="" || tpaFeeType==='')
		{
			alert("TPA Fee Type should not be empty");
			document.forms[1].tpaFeeType.focus();
			return false;
		}
	if(!((document.getElementById("maritalStatus").value)==="") && !((document.getElementById("relation").value)==="") && !((document.getElementById("maritalStatus").value)===""))
	{
		document.forms[1].mode.value="doSavePremiumConfigurarion";
		document.forms[1].action="/Configuration.do";
		document.forms[1].submit();
	}
	 else
	{
		alert("Fields should not be Empty");
	}
}




























function calcCapNetPremium()
{
	/*var re = /^[0-9]*\.*[0-9]*$/;	
	var objValue=upObj.value;	
	//alert(objValue);
	
	 if(objValue.length>=1)
	 {
		if(!re.test(objValue)) 
		{
			alert("Data Should be Numeric");
			upObj.value="";
			upObj.focus();
		}
	 }*/
	 // for gross premium
	/* var eventCompleteYN=document.getElementById("eventCompleteYN").value;
	
	 if(eventCompleteYN=="N")
		 {*/
	 var medicalPremiumAmt=document.forms[1].medicalPremium.value;
	// medicalPremiumAmt=(medicalPremiumAmt==null||medicalPremiumAmt===""||!re.test(medicalPremiumAmt)||medicalPremiumAmt==="0")?0:medicalPremiumAmt;
	 medicalPremiumAmt=(medicalPremiumAmt==null||medicalPremiumAmt===""||medicalPremiumAmt==="0")?0:medicalPremiumAmt;
	 var maternityPremiumAmt=document.forms[1].maternityPremium.value;
	 maternityPremiumAmt=(maternityPremiumAmt==null||maternityPremiumAmt===""||maternityPremiumAmt==="0")?0:maternityPremiumAmt;
	 
	 
	 var opticalPremiumAmt=document.forms[1].opticalPremium.value;
	 opticalPremiumAmt=(opticalPremiumAmt==null||opticalPremiumAmt===""||opticalPremiumAmt==="0")?0:opticalPremiumAmt;
	 
	 var dentalPremiumAmt=document.forms[1].dentalPremium.value;
	 dentalPremiumAmt=(dentalPremiumAmt==null||dentalPremiumAmt===""||dentalPremiumAmt==="0")?0:dentalPremiumAmt;	 
	
	 var wellnessPremiumAmt=document.forms[1].wellnessPremium.value;
	 wellnessPremiumAmt=(wellnessPremiumAmt==null||wellnessPremiumAmt===""||wellnessPremiumAmt==="0")?0:wellnessPremiumAmt;
	 
	 
	 document.forms[1].grossPremium1.value=parseFloat(medicalPremiumAmt)+parseFloat(maternityPremiumAmt)+parseFloat(opticalPremiumAmt)+parseFloat(dentalPremiumAmt)+parseFloat(wellnessPremiumAmt);
	 
	 var grossPremium1=document.forms[1].grossPremium1.value;
	 grossPremium1=(grossPremium1==null||grossPremium1===""||grossPremium1==="0")?0:grossPremium1;
	// var memberActivePeriod=document.forms[1].memberActivePeriod.value;
	// memberActivePeriod=(memberActivePeriod==null||memberActivePeriod===""||memberActivePeriod==="0")?0:memberActivePeriod;
	// var actualPremium=grossPremiumAmt*memberActivePeriod/365;
	// document.forms[1].actualPremium.value=actualPremium.toFixed(2);
	 // for all margins 
	 var insurerMarginAmt=document.getElementById("insurerMarginPER").value;//document.forms[1].insurerMargin.value;
	 insurerMarginAmt=(insurerMarginAmt==null||insurerMarginAmt===""||insurerMarginAmt==="0")?0:insurerMarginAmt;
	 
	 var brokerMarginAmt=document.getElementById("brokerMarginPER").value; //document.forms[1].brokerMargin.value;
	 brokerMarginAmt=(brokerMarginAmt==null||brokerMarginAmt===""||brokerMarginAmt==="0")?0:brokerMarginAmt;
	 
	// var tapMarginAmt=document.getElementById("tapMarginPER").value;//document.forms[1].tapMargin.value; tapMarginPER
	// tapMarginAmt=(tapMarginAmt==null||tapMarginAmt===""||tapMarginAmt==="0")?0:tapMarginAmt;
	 if(document.getElementById("reInsBrkMarginPER")!=null){
	 var reInsBrkMarginAmt=document.getElementById("reInsBrkMarginPER").value;//document.forms[1].reInsBrkMargin.value;
	 reInsBrkMarginAmt=(reInsBrkMarginAmt==null||reInsBrkMarginAmt===""||reInsBrkMarginAmt==="0")?0:reInsBrkMarginAmt;
	 }
	 var otherMarginAmt=document.getElementById("otherMarginPER").value;//document.forms[1].otherMargin.value;
	 otherMarginAmt=(otherMarginAmt==null||otherMarginAmt===""||otherMarginAmt==="0")?0:otherMarginAmt;
	 // for finding all margins  total
	 if(document.getElementById("reInsBrkMarginPER")!=null){
	 var totalMarginAmt = ((parseFloat(grossPremium1)*parseFloat(otherMarginAmt))/100)+((parseFloat(grossPremium1)*parseFloat(reInsBrkMarginAmt))/100)+((parseFloat(grossPremium1)*parseFloat(brokerMarginAmt))/100)+((parseFloat(grossPremium1)*parseFloat(insurerMarginAmt))/100); 
	totalMarginAmt=(totalMarginAmt==null||totalMarginAmt===""||totalMarginAmt==="0")?0:totalMarginAmt;
	 }else{
		 var totalMarginAmt = ((parseFloat(grossPremium1)*parseFloat(otherMarginAmt))/100)+((parseFloat(grossPremium1)*parseFloat(brokerMarginAmt))/100)+((parseFloat(grossPremium1)*parseFloat(insurerMarginAmt))/100); 
			totalMarginAmt=(totalMarginAmt==null||totalMarginAmt===""||totalMarginAmt==="0")?0:totalMarginAmt; 
	 }
	 // for finding Net Premium 
	// var ipNetPremiumAmt = document.forms[1].ipNetPremium.value;    
	// ipNetPremiumAmt=(ipNetPremiumAmt==null||ipNetPremiumAmt===""||ipNetPremiumAmt==="0")?0:ipNetPremiumAmt;
	 
	 
	//  var  totMar_IpNetPre = parseFloat(ipNetPremiumAmt)+parseFloat(totalMarginAmt);
	 
	  // var sumofLoadings = parseFloat(insurerMarginAmt)+parseFloat(brokerMarginAmt)+parseFloat(reInsBrkMarginAmt)+parseFloat(otherMarginAmt);
	 
	
	if(document.forms[1].netPremium != null){
	 document.forms[1].netPremium.value=(parseFloat(grossPremium1)-parseFloat(totalMarginAmt)).toFixed(2);
	 var netPremium=document.forms[1].netPremium.value;
	 netPremium=(netPremium==null||netPremium===""||netPremium==="0")?0:netPremium;
	 // var netPremium=document.forms[1].netPremium.value;
	  if(netPremium<0)
		  {
		  document.forms[1].netPremium.value = '0.0';
		  }
	  else
		  {
	   document.forms[1].netPremium.value = netPremium;
		  }
	  
	  
	}  
	  
	  
		/* }*/
	  // calcActualPremium();
}


function calculatePerAED(obj,idName,flag)
{
	/* var eventCompleteYN=document.getElementById("eventCompleteYN").value;*/
	/* if(eventCompleteYN=="N")
		 {*/
	 var op;
	   var AED=idName+"AED";
	   var PER=idName+"PER";
	   var displayDiv="display"+idName;
	   
  var displayAEDPER="displayAEDPER"+idName;
  var displayAED="displayAED"+idName;
  var displayPER="displayPER"+idName;
	 var dropselvalue=idName+"AEDPER";
	var GP= document.getElementById("grossPremium1").value;
	var healthAuthority= document.getElementById("healthAuthority").value;
	var asoflagvalue = document.getElementById("capitationYNid").value;
	var selvalue;
	if(dropselvalue=='medicalPremiumAEDPER'){
		selvalue="PER";
	}else{
	    selvalue=document.getElementById(dropselvalue).value;
	}
if(selvalue!=null)
	{
	
       if(selvalue=="AED")
    	   {
    	   if(document.getElementById(AED)!=null)
    	   document.getElementById(AED).value=document.getElementById(idName).value;
    		  if(GP>0) op=document.getElementById(idName).value/GP*100;
    		  else op='0.0';
    		  if(document.getElementById(PER)!=null)
    	   document.getElementById(PER).value=op.toFixed(2);
    		  if(document.getElementById(displayAED)!=null)
    	  document.getElementById(displayAED).style.display = "none";
    		  if(document.getElementById(displayPER)!=null)
    	   document.getElementById(displayPER).style.display = "";
    	   if(dropselvalue!="tapMarginAEDPER")
    	  calcCapNetPremium();
    	   if(healthAuthority=='DHA' && asoflagvalue!=1)
    	   calculateTpaActualFee(selvalue);
    	   else if (asoflagvalue==1)
     		  calculateTpaActualFeeForASO(selvalue,flag);
    	  // document.getElementById(PER).focus();
    	   }
       else if(selvalue=="PER")
    	   {
    	   /* var op;
    	   var AED=idName+"AED";
    	   var PER=idName+"PER";
    	   var displayAED="displayAED"+idName;
           var displayPER="displayPER"+idName;
    	   var displayDiv="display"+idName;
           var displayAEDPER="displayAEDPER"+idName; */
           if(Number(document.getElementById(idName).value)> 100)
        	   {
        	  alert(idName+" Should be less than 100");
        	  document.getElementById(idName).value="";
        	  document.getElementById(idName).focus();
        	   }
           else
        	   {
        	   if(document.getElementById(PER)!=null)
    	   document.getElementById(PER).value=document.getElementById(idName).value;
    	  // alert(document.getElementById(idName).value);
    		  op=GP*document.getElementById(idName).value/100;
    		  if(document.getElementById(AED)!=null)
    	   document.getElementById(AED).value=op.toFixed(2);
    		  if(document.getElementById(displayPER)!=null)  
    	  document.getElementById(displayPER).style.display = "none";
    		  if(document.getElementById(displayAED)!=null) 
    	   document.getElementById(displayAED).style.display = "";
    	  if(dropselvalue!="tapMarginAEDPER")
    	  calcCapNetPremium();
    	  if(healthAuthority=='DHA' && asoflagvalue!=1)
    	  calculateTpaActualFee(selvalue);
    	  else if (asoflagvalue==1)
    		  calculateTpaActualFeeForASO(selvalue,flag);
    	  // document.getElementById(AED).focus();
        	   }
    	   }
       else{
    	   document.getElementById(displayPER).style.display = "none";
    	   document.getElementById(displayAED).style.display = "none";
       }
	}
else
	{
	alert("please Enter data");
	document.getElementById(idName).focus();
	}
	
		/* }*/
}




function onClose()
{
	/*if(!TrackChanges()) return false;
	document.forms[1].mode.value="doConfigureProductPremium";
	document.forms[1].action="/Configuration.do";
	document.forms[1].submit();*/
	document.forms[1].mode.value = "doClosePremiumDetails";
	document.forms[1].action = "/Configuration.do";
	document.forms[1].submit();	 
}




function isNumaricOnly(upObj){
	
	var re = /^[0-9]*\.*[0-9]*$/;	
	var objValue=upObj.value;
	if(objValue.length>=1){
		if(!re.test(objValue)){
			alert("Please Enter Numbers only");
			upObj.value="";
			upObj.focus();
		}
	}  
	
}

function calculateActualFee(element){
	var tpafee=document.getElementById('tpa_fee_id').value;
	var tpa_fee_aedper=document.getElementById('tpa_fee_aedper_id').value;
	/*if(''==elementid){
		
	}else if(''==elementid){
		
	}*/
	if (tpa_fee_aedper == "PER") {
		if (Number(tpafee) > 100) {
			alert("TPA Fee Should be less than 100");
			document.getElementById('tpa_fee_id').value = '';
			document.getElementById('tpa_fee_id').focus();
		} 
	}
	calculateTpaActualFee(tpa_fee_aedper);
}

function calculateTpaActualFee(tpa_fee_aedper){
	var grossPremium1=document.getElementById('grossPremium1').value;
	var tpafee=document.getElementById('tpa_fee_id').value;

	var actualTpaFee=0.0;
	if(''!=tpafee){
		if ('PER'==tpa_fee_aedper) {
			actualTpaFee=tpafee*grossPremium1/100;
			document.getElementById('tpa_fee_select_id').innerHTML='';
			document.getElementById('tpa_fee_select_id').innerHTML='[AED]';
		}else{
			actualTpaFee=(tpafee/grossPremium1)*100;
			document.getElementById('tpa_fee_select_id').innerHTML='';
			document.getElementById('tpa_fee_select_id').innerHTML='%';
		}
	}
	document.getElementById('tpa_actual_fee_id').value=actualTpaFee.toFixed(2);
}

function calculateTpaActualFeeForASO(tpa_fee_aedper,dropselvalue){
	var grossPremium1=document.getElementById('grossPremium1').value;
	var tpafee=document.getElementById('tpa_fee_id').value;
	var actualTpaFee=0.0;
	if(dropselvalue==undefined){
		if(''!=tpafee){
			if ('PER'==tpa_fee_aedper) {
				actualTpaFee=tpafee*grossPremium1/100;
			}else{
				actualTpaFee=(tpafee/grossPremium1)*100;
			}
		  }
		document.getElementById('tpa_actual_fee_id').value=actualTpaFee.toFixed(2);
	}else if(dropselvalue=='tpaFeeFlag'){
		if(''!=tpafee){
			if ('PER'==tpa_fee_aedper) {
				actualTpaFee=tpafee*grossPremium1/100;
				document.getElementById('tpa_fee_select_id').innerHTML='';
				document.getElementById('tpa_fee_select_id').innerHTML='[AED]';
			}else{
				actualTpaFee=(tpafee/grossPremium1)*100;
				document.getElementById('tpa_fee_select_id').innerHTML='';
				document.getElementById('tpa_fee_select_id').innerHTML='%';
			}
		}
		document.getElementById('tpa_actual_fee_id').value=actualTpaFee.toFixed(2);
	}
	
}
