

// Java Script function for accountheadinfo.jsp



function checkAllGeolocation(chObj,sfalg){

	var varChbx=document.forms[0].checkedIndex;			
	var varChbxArr = varChbx.length ? varChbx : [varChbx];  
	
	if(chObj.checked){
		var i=0;
		var hoslength=varChbxArr.length;
		for(i;i<hoslength;i++){
			varChbxArr[i].checked=true;
			if(sfalg) checkGeolocation(varChbxArr[i]);	           		
		    }
	}else{
		var i=0;
		var hoslength=varChbxArr.length;
		for(i;i<hoslength;i++){
			varChbxArr[i].checked=false;
			if(sfalg) checkGeolocation(varChbxArr[i]);	           		
		    }	
	}

}


function checkAllCountries(chObj){
	var varChbx=document.forms[0].checkedIndex;			
	var varChbxArr = varChbx.length ? varChbx : [varChbx];  
	var i=0;
	var contrLength=varChbxArr.length;
	
	var flag;
	if(chObj.checked)flag=true;
	else flag=false;
		
		for(i;i<contrLength;i++){
			varChbxArr[i].checked=flag;
			checkCountry(varChbxArr[i]);	           		
		}
}
 function checkCountry(chObj){

	if(chObj.checked){
		var varCopay=document.getElementById("copay"+chObj.value);	
		varCopay.readOnly=false;
		varCopay.style.backgroundColor="white";
		   
		var varFlatamt=document.getElementById("flatamount"+chObj.value);	
		varFlatamt.readOnly=false;
		varFlatamt.style.backgroundColor="white";
		
		var varMinmax=document.getElementById("minmax"+chObj.value);	
		varMinmax.selectedIndex="0";		
		   
	}else{
		
		   var varCopay=document.getElementById("copay"+chObj.value);	
			varCopay.readOnly=true;
			varCopay.value="";
			varCopay.style.backgroundColor="#EEEEEE";
			   
			var varFlatamt=document.getElementById("flatamount"+chObj.value);	
			varFlatamt.readOnly=true;
			varFlatamt.value="";
			varFlatamt.style.backgroundColor="#EEEEEE";
			
			var varMinmax=document.getElementById("minmax"+chObj.value);	
			varMinmax.selectedIndex="0";
	}
}
 
 function checkProvider(chObj){

		if(chObj.checked){
			var varCopay=document.getElementById("copay"+chObj.value);	
			varCopay.readOnly=false;
			varCopay.style.backgroundColor="white";
			   
			var varFlatamt=document.getElementById("flatamount"+chObj.value);	
			varFlatamt.readOnly=false;
			varFlatamt.style.backgroundColor="white";
			
			var varMinmax=document.getElementById("minmax"+chObj.value);	
			varMinmax.selectedIndex="0";		
			   
		}else{
			
			   var varCopay=document.getElementById("copay"+chObj.value);	
				varCopay.readOnly=true;
				varCopay.value="";
				varCopay.style.backgroundColor="#EEEEEE";
				   
				var varFlatamt=document.getElementById("flatamount"+chObj.value);	
				varFlatamt.readOnly=true;
				varFlatamt.value="";
				varFlatamt.style.backgroundColor="#EEEEEE";
				
				var varMinmax=document.getElementById("minmax"+chObj.value);	
				varMinmax.selectedIndex="0";
		}
	}
 
 function checkAllProvider(chObj){
		var varChbx=document.forms[0].checkedIndex;			
		var varChbxArr = varChbx.length ? varChbx : [varChbx];  
		var i=0;
		var provLength=varChbxArr.length;
		
		var flag;
		if(chObj.checked)flag=true;
		else flag=false;
			
			for(i;i<provLength;i++){
				varChbxArr[i].checked=flag;
				checkProvider(varChbxArr[i]);	           		
			}
	}
function checkGeolocation(chObj){

	if(chObj.checked){
		var varCopay=document.getElementById("copay"+chObj.value);	
		varCopay.readOnly=false;
		varCopay.style.backgroundColor="white";
		   
		var varFlatamt=document.getElementById("flatamount"+chObj.value);	
		varFlatamt.readOnly=false;
		varFlatamt.style.backgroundColor="white";
		
		var varMinmax=document.getElementById("minmax"+chObj.value);	
		varMinmax.selectedIndex="0";		
		   
	}else{
		
		   var varCopay=document.getElementById("copay"+chObj.value);	
			varCopay.readOnly=true;
			varCopay.value="";
			varCopay.style.backgroundColor="#EEEEEE";
			   
			var varFlatamt=document.getElementById("flatamount"+chObj.value);	
			varFlatamt.readOnly=true;
			varFlatamt.value="";
			varFlatamt.style.backgroundColor="#EEEEEE";
			
			var varMinmax=document.getElementById("minmax"+chObj.value);	
			varMinmax.selectedIndex="0";
	}
}


function selectGeolocation(){

	
	var varRadioBx=document.forms[0].checkedIndex;			
	var varRadioBxArr = varRadioBx.length ? varRadioBx : [varRadioBx]; 
	
	var i=0;
	var geoLength=varRadioBxArr.length;
	
	for(i;i<geoLength;i++){
			var rdObj=varRadioBxArr[i];
	if(rdObj.checked){
		var varCopay=document.getElementById("copay"+rdObj.value);	
		varCopay.readOnly=false;
		varCopay.style.backgroundColor="white";
		   
		var varFlatamt=document.getElementById("flatamount"+rdObj.value);	
		varFlatamt.readOnly=false;
		varFlatamt.style.backgroundColor="white";
		
		var varMinmax=document.getElementById("minmax"+rdObj.value);	
		varMinmax.selectedIndex="0";		
		   
	}else{
		
		   var varCopay=document.getElementById("copay"+rdObj.value);	
			varCopay.readOnly=true;
			varCopay.value="";
			varCopay.style.backgroundColor="#EEEEEE";
			   
			var varFlatamt=document.getElementById("flatamount"+rdObj.value);	
			varFlatamt.readOnly=true;
			varFlatamt.value="";
			varFlatamt.style.backgroundColor="#EEEEEE";
			
			var varMinmax=document.getElementById("minmax"+rdObj.value);	
			varMinmax.selectedIndex="0";
	}
	}//for(i;i<geoLength;i++){
	
}

function onSave(){
	var strlocCode=document.forms[0].locationType.value;
	if(strlocCode==="GEO"){
	 if(checkGeoLocationsCopay()) {
		 alert("Please select atleast one geographical location/provide the values");
		 return;
	 }
	}else 	if(strlocCode==="CON"){
		 if(checkCountryLocationsCopay()) {
			 alert("Please select atleast one country location/provide the values for selected country");
			 return;
		 }
	}else 	if(strlocCode==="PRO"){
		 if(checkCountryLocationsCopay()) {
			 alert("Please select atleast one provider/provide the values for selected providers");
			 return;
		 }
	}
	document.forms[0].mode.value="doSaveLocationCopay";
	document.forms[0].action="/ProviderCopayDetailsAction.do";	
	document.forms[0].submit();
}
function onAddHospCoayDetails(){
	var strlocCode=document.forms[0].locationType.value;

		 if(checkCountryLocationsCopay()) {
			 alert("Please select atleast one provider/provide the values for selected providers");
			 return;
		 }
	document.forms[0].mode.value="doAddHospCoayDetails";
	document.forms[0].action="/ProviderCopayDetailsAction.do";	
	document.forms[0].submit();
}

function checkGeoLocationsCopay(){
	var varRadioBx=document.forms[0].checkedIndex;			
	var varRadioBxArr = varRadioBx.length ? varRadioBx : [varRadioBx]; 
	var status=true;
	var i=0;
	var geoLength=varRadioBxArr.length;
	
	for(i;i<geoLength;i++){
			var rdObj=varRadioBxArr[i];
	if(rdObj.checked){
		status=false;
		var fAmt=document.getElementById("flatamount"+i).value;
		var copayAmt=document.getElementById("copay"+i).value;
		if(fAmt.length<1&&copayAmt.length<1){
			document.getElementById("copay"+i).focus();
			status=true;
		}
		
		break;
	}
	}
	return status;
}

function checkAllFecilities(chekBox){
	var varCheckBx=document.forms[0].checkedIndex;			
	var varCheckBxArr = varCheckBx.length ? varCheckBx : [varCheckBx]; 
	var status=chekBox.checked?true:false;
    var geoLength=varCheckBxArr.length;
	var i=0;
	
	for(i;i<geoLength;i++)	
	varCheckBxArr[i].checked=status;
			
}

function checkAllEncounters(chekBox){
	var varCheckBx=document.forms[0].checkedIndex;			
	var varCheckBxArr = varCheckBx.length ? varCheckBx : [varCheckBx]; 
	var status=chekBox.checked?true:false;
    var geoLength=varCheckBxArr.length;
	var i=0;
	
	for(i;i<geoLength;i++)	
	varCheckBxArr[i].checked=status;
			
}
function checkCountryLocationsCopay(){
	var varCheckBx=document.forms[0].checkedIndex;			
	var varCheckBxArr = varCheckBx.length ? varCheckBx : [varCheckBx]; 
	var status=true;
	var i=0;
	var geoLength=varCheckBxArr.length;
	
	for(i;i<geoLength;i++){
			var rdObj=varCheckBxArr[i];
	if(rdObj.checked){
		status=false;
		var fAmt=document.getElementById("flatamount"+rdObj.value).value;
		var copayAmt=document.getElementById("copay"+rdObj.value).value;
		if(fAmt.length<1&&copayAmt.length<1){
			document.getElementById("copay"+rdObj.value).focus();
			status=true;
			break;
		}
	}
	}
	return status;
}
function onSearchProviders(){
	document.forms[0].mode.value="doSearchProviders";	
	document.forms[0].action="/ProviderCopayDetailsAction.do?locationType=PRO";	
	document.forms[0].submit();
}

function onClose() {
	document.forms[0].mode.value="doClose";
	document.forms[0].action="/ProviderCopayDetailsAction.do";	
	document.forms[0].submit();
}