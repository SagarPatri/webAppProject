

// Java Script function for accountheadinfo.jsp


function openGeoLocationsList(){
	  
		//openListInMiddle("geoLocations","Geographical Locations","/ProviderCopayDetailsAction.do?mode=doSelectGeoLocations&locationType=GL","350","490");
}

function onSelectLocations(locType){
	document.forms[0].mode.value="doSelectLocations";
	document.forms[0].locationType.value=locType;
	if(locType==="CON"){
		var geoLoc=document.forms[0].geoLocation.value;
		if(geoLoc===""||geoLoc.length<1){
			alert("Please select geographical location");
			return;
			}
	  }else if(locType==="PRO"){
			
			var conConf=document.forms[0].countryIDs.value;
			var pType=document.forms[0].providerType.value;
			var pfType=document.forms[0].providerFacilities.value;
			var netwType=document.forms[0].networkYN.value;
			if(netwType=="N"||netwType==="N"){
				  alert("Sorry there are no non-network providers to select");
				  return;
			  }
			
			if(conConf==null||conConf===""||conConf.length<1){
				  alert("Please configure contries");
				  return;
			  }else if(pType==null||pType===""||pType.length<1){
				  alert("Please Select Provider Type");
				  return;
			  }else if(pfType==null||pfType===""||pfType.length<1){
				  alert("Please Select Provider Facility Type");
				  return;
			  }
			  
	}
	document.forms[0].action="/ProviderCopayDetailsAction.do";	
	document.forms[0].submit();
}


function onSelectProviderFacilities(){
	  
	openListInMiddle("providerFacilities","Provider Facility Type","/ProviderCopayDetailsAction.do?mode=doSelectProviderFacilities","350","490");
}

function onChangeClaimType()	{
	document.forms[0].mode.value="doChangeClaimType";
	document.forms[0].action="/ProviderCopayDetailsAction.do";	
	document.forms[0].submit();
}



function onAddCondition(){

	if(!JS_SecondSubmit){
		
		var claimTypeSelBx=document.forms[0].claimType;
		document.forms[0].claimTypeDesc.value=claimTypeSelBx[claimTypeSelBx.selectedIndex].text;
		
		var networkYNSelBx=document.forms[0].networkYN;
		document.forms[0].networkYNDesc.value=networkYNSelBx[networkYNSelBx.selectedIndex].text;
		
		var providerTypeSelBx=document.forms[0].providerType;
		document.forms[0].providerTypeDesc.value=providerTypeSelBx[providerTypeSelBx.selectedIndex].text;
		
		
		document.forms[0].mode.value="doAddCondition";
		document.forms[0].action="/SaveProviderCopayDetailsAction.do";	
		JS_SecondSubmit=true;
		document.forms[0].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

function onReset(){
	document.forms[0].mode.value="doReset";
	document.forms[0].action="/ProviderCopayDetailsAction.do";	
	document.forms[0].submit();
  	
}//end of Reset()

function onEditCondition(strRownum){
	if(confirm("Do you Want Edit This Condtion")){
	document.forms[0].mode.value="doEditCondition";
	document.forms[0].rownum.value=strRownum;	
	document.forms[0].action="/ProviderCopayDetailsAction.do";	
	document.forms[0].submit();
	}
  	
}//end of onEditCondition()
function onDeleteCondition(strRownum){
	if(confirm("Do you Want Delete This Condtion")){
	document.forms[0].mode.value="doDeleteCondition";
	document.forms[0].rownum.value=strRownum;	
	document.forms[0].action="/ProviderCopayDetailsAction.do";	
	document.forms[0].submit();
	}
  	
}//end of onDeleteCondition()

function onViewDetails(strViewModeType,strRownum){
	var strWidth=550;
	var strHeight=350;
	if(strViewModeType==="PFT"){
		strWidth=300;
		strHeight=350;
	}else if(strViewModeType==="GEO"){
		strWidth=500;
		strHeight=350;
	}else if(strViewModeType==="CON"){
		strWidth=550;
		strHeight=350;
	}else 	if(strViewModeType==="PRO"){
		strWidth=850;
		strHeight=550;
	}
	 var openPage = "/ProviderCopayDetailsAction.do?mode=doViewConfDetails&viewModeType="+strViewModeType+"&rownum="+strRownum;
	
	 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=400,resizable=0,menubar=0,width="+strWidth+",height="+strHeight;
	 
	 window.open(openPage,'',features);
}//end of onViewDetails()
