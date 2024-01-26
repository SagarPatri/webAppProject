// Java Script function for clinicianTypeCopayDetails.js

function onSave(){

		var varCheckBx=document.forms[0].checkedIndex;			
		var varCheckBxArr = varCheckBx.length ? varCheckBx : [varCheckBx]; 
		var status=true;
		var i=0;
		var clinLength=varCheckBxArr.length;
		
		for(i;i<clinLength;i++){
				var rdObj=varCheckBxArr[i];
		if(rdObj.checked){
			status=false;
			var copayAmt=document.getElementById("copay"+i).value;
			var deductAmt=document.getElementById("deduct"+i).value;
			
			if(copayAmt.length<1&&deductAmt.length<1){
				document.getElementById("copay"+i).focus();
				status=true;
				break;
			}
		}
		}

		if(status){
alert("Please select atleast one/Provide the copay or deductable details for selected Clinician");
return;
}
	document.forms[0].submit();	 
}
function checkClinician(chObj){

	if(chObj.checked){
		var varCopay=document.getElementById("copay"+chObj.value);	
		varCopay.readOnly=false;
		varCopay.style.backgroundColor="white";
		
		var varDeduct=document.getElementById("deduct"+chObj.value);	
		varDeduct.readOnly=false;
		varDeduct.style.backgroundColor="white";	
		   
	}else{
		   var varCopay=document.getElementById("copay"+chObj.value);	
			varCopay.readOnly=true;
			varCopay.value="";
			varCopay.style.backgroundColor="#EEEEEE";
			
			var varDeduct=document.getElementById("deduct"+chObj.value);	
			varDeduct.readOnly=true;
			varDeduct.value="";
			varDeduct.style.backgroundColor="#EEEEEE";
	}
}

function closeWindow(){
	    setInterval(function(){ window.self.close(); }, 1100);
}
function checkAllClinicians(chObj){

	var varCheckBx=document.forms[0].checkedIndex;			
	var varCheckBxArr = varCheckBx.length ? varCheckBx : [varCheckBx]; 
	var status=chObj.checked;
	var i=0;
	var clinLength=varCheckBxArr.length;
	
	for(i;i<clinLength;i++){
	var chObj=varCheckBxArr[i];
			chObj.checked=status;
			var varCopay=document.getElementById("copay"+chObj.value);
			varCopay.value="";
			var varDeduct=document.getElementById("deduct"+chObj.value);
			varDeduct.value="";
	if(status){
		varCopay.readOnly=false;
		varCopay.style.backgroundColor="white";
		varDeduct.readOnly=false;
		varDeduct.style.backgroundColor="white";
	}else{
			varCopay.readOnly=true;
			varCopay.style.backgroundColor="#EEEEEE";
			
			varDeduct.readOnly=true;
			varDeduct.style.backgroundColor="#EEEEEE";
	}
	
	}

}