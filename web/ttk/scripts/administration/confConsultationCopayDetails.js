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
			var copayAmtperclm=document.getElementById("copayperclm"+i).value;
			var deductAmtperclm=document.getElementById("deductperclm"+i).value;
			if(copayAmtperclm.length<1&&deductAmtperclm.length<1){
				document.getElementById("copayperclm"+i).focus();
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
		var varCopayperclm=document.getElementById("copayperclm"+chObj.value);	
		varCopayperclm.readOnly=false;
		varCopayperclm.style.backgroundColor="white";
		
		var varDeductperclm=document.getElementById("deductperclm"+chObj.value);	
		varDeductperclm.readOnly=false;
		varDeductperclm.style.backgroundColor="white";	
		   
	}else{
		   var varCopayperclm=document.getElementById("copayperclm"+chObj.value);	
			varCopayperclm.readOnly=true;
			varCopayperclm.value="";
			varCopayperclm.style.backgroundColor="#EEEEEE";
			
			var varDeductperclm=document.getElementById("deductperclm"+chObj.value);	
			varDeductperclm.readOnly=true;
			varDeductperclm.value="";
			varDeductperclm.style.backgroundColor="#EEEEEE";
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
	var consultLength=varCheckBxArr.length;
	for(i;i<consultLength;i++){
	var chObj=varCheckBxArr[i];
			chObj.checked=status;
			var varCopayperclm=document.getElementById("copayperclm"+chObj.value);
			varCopayperclm.value="";
		/*	var varDiscountcopayperclm=document.getElementById("discountcopayperclm"+chObj.value);
			varDiscountcopayperclm.value="";*/
			var varDeductperclm=document.getElementById("deductperclm"+chObj.value);
			varDeductperclm.value="";
		/*	var varMinmaxperclm=document.getElementById("minmaxperclm"+chObj.value);
			varMinmaxperclm.value="";*/
			/*var varCopayperpolicy=document.getElementById("copayperpolicy"+chObj.value);
			varCopayperpolicy.value="";
			var varDiscountcopayperpolicy=document.getElementById("discountcopayperpolicy"+chObj.value);
			varDiscountcopayperpolicy.value="";
			var varDeductperpolicy=document.getElementById("deductperpolicy"+chObj.value);
			varDeductperpolicy.value="";
			var varMinmaxperpolicy=document.getElementById("minmaxperpolicy"+chObj.value);
			varMinmaxperpolicy.value="";*/
			
	if(status){
	
		varCopayperclm.readOnly=false;
		varCopayperclm.style.backgroundColor="white";
		
		/*varDiscountcopayperclm.readOnly=false;
		varDiscountcopayperclm.style.backgroundColor="white";
		*/
		varDeductperclm.readOnly=false;
		varDeductperclm.style.backgroundColor="white";
		
	/*	varMinmaxperclm.readOnly=false;
		varMinmaxperclm.style.backgroundColor="white";
		*/
	/*	varCopayperpolicy.readOnly=false;
		varCopayperpolicy.style.backgroundColor="white";
		
		
		varDiscountcopayperpolicy.readOnly=false;
		varDiscountcopayperpolicy.style.backgroundColor="white";
		
		varDeductperpolicy.readOnly=false;
		varDeductperpolicy.style.backgroundColor="white";
		
		varMinmaxperpolicy.readOnly=false;
		varMinmaxperpolicy.style.backgroundColor="white";*/
		
	}else{
		
		
		varCopayperclm.readOnly=true;
		varCopayperclm.style.backgroundColor="#EEEEEE";
		
		/*varDiscountcopayperclm.readOnly=true;
		varDiscountcopayperclm.style.backgroundColor="#EEEEEE";*/
		
		varDeductperclm.readOnly=true;
		varDeductperclm.style.backgroundColor="#EEEEEE";
		
		/*varMinmaxperclm.readOnly=true;
		varMinmaxperclm.style.backgroundColor="#EEEEEE";
		*/
	/*	varDiscountcopayperpolicy.readOnly=true;
		varDiscountcopayperpolicy.style.backgroundColor="#EEEEEE";
		
		varDeductperpolicy.readOnly=true;
		varDeductperpolicy.style.backgroundColor="#EEEEEE";
		
		varMinmaxperpolicy.readOnly=true;
		varMinmaxperpolicy.style.backgroundColor="#EEEEEE";*/
	}
	
	}

}