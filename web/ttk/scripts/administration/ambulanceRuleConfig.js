

function dynamicList(aIdentifier,aDynaType,aDynaParamID)
{
	 document.getElementById("dynaTypeId").value=aDynaType;
	 document.getElementById("dynaParam").value=document.getElementById(aDynaParamID).value;
	openList(aIdentifier,'DynamicValues');
	
}//end of dynamicList()

function openGeoLocation(aStyleID,aOPMode){
	var clearFieslds="emiratesID|countryID";	
	 
	 var w = 300;
	 var h = 490;
	 
	var vConfigValues=document.getElementById(aStyleID).value;
	
	var path="/DynAmbulanceRuleAction.do?mode=openConfigWindow";
	var params="&windoType=Geo&boxType=RD&windowTitle=Geographical Locatoions&configValues="+vConfigValues+"&displayID="+aStyleID+"&OPMode="+aOPMode+"&clearFieldIDs="+clearFieslds;
	
	
	var openPage =path+params;
	 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=100,resizable=0,menubar=0,width="+w+",height="+h;
	
	 openConfigWindow(openPage,features);
}//end of openGeoLocation()


function  selectHospTypes(aStyleID,aOPMode){
	 
	 var w = 300;
	 var h = 490;
var vConfigValues=document.getElementById(aStyleID).value;
	
	var path="/DynAmbulanceRuleAction.do?mode=openConfigWindow";
	var params="&windoType=Hos&boxType=CH&windowTitle=Provider Type List&configValues="+vConfigValues+"&displayID="+aStyleID+"&OPMode="+aOPMode;
	
	var openPage =path+params;
	 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=200,resizable=0,menubar=0,width="+w+",height="+h;
	
	 openConfigWindow(openPage,features);
}
var  popupWindow=null;	
function openConfigWindow(openPage,features){
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}


function  selectProviderTypes(aStyleID,aOPMode){
	 
	 var w = 300;
	 var h = 490;
var vConfigValues=document.getElementById(aStyleID).value;
	
	var path="/DynAmbulanceRuleAction.do?mode=openConfigWindow";
	var params="&windoType=Pro&boxType=CH&windowTitle=Provider Type List&configValues="+vConfigValues+"&displayID="+aStyleID+"&OPMode="+aOPMode;
	
	var openPage =path+params;
	 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=200,resizable=0,menubar=0,width="+w+",height="+h;
	
	 openConfigWindow(openPage,features);
}
var  popupWindow=null;	
function openConfigWindow(openPage,features){
	 popupWindow= window.open(openPage,'',features);
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup; 
}

function  selectEncounterTypes(aStyleID,aOPMode){
	 
	 var w = 300;
	 var h = 490;
var vConfigValues=document.getElementById(aStyleID).value;
	
	var path="/DynAmbulanceRuleAction.do?mode=openConfigWindow";
	var params="&windoType=Enc&boxType=CH&windowTitle=Encounter List&configValues="+vConfigValues+"&displayID="+aStyleID+"&OPMode="+aOPMode;
	
	var openPage =path+params;
	 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=200,resizable=0,menubar=0,width="+w+",height="+h;
	
	 openConfigWindow(openPage,features);
}

function openGeoCountryList(aStyleID,aOPMode,aGeoLocID){
	var vGeoLocID=document.getElementById("geoLocationID").value;
	if("S"==aOPMode){
	document.getElementById("emiratesID").value="";		
	if(vGeoLocID==null||vGeoLocID==""||vGeoLocID.length<1){
		alert("Please select geographical location");
		return;
	}
	}
	if("V"==aOPMode){
		vGeoLocID=aGeoLocID;
	}
	 var w = 300;
	 var h = 490;
var vConfigValues=document.getElementById(aStyleID).value;
	
	var path="/DynAmbulanceRuleAction.do?mode=openConfigWindow";
	var params="&windoType=Con&boxType=CH&windowTitle=Country List&configValues="+vConfigValues+"&displayID="+aStyleID+"&dynaParam="+vGeoLocID+"&OPMode="+aOPMode;
	
	var openPage =path+params;
	 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=200,resizable=0,menubar=0,width="+w+",height="+h;
	
	 openConfigWindow(openPage,features);
}
function selectEmirates(aStyleID,aOPMode){
	if("S"==aOPMode){
	var vCountryID=document.getElementById("countryID").value;
	if(vCountryID.indexOf("175")==-1){
		alert("Please select United Arab Emirates");
		return;
	}
	}
	 var w = 300;
	 var h = 490;
var vConfigValues=document.getElementById(aStyleID).value;
	
	var path="/DynAmbulanceRuleAction.do?mode=openConfigWindow";
	var params="&windoType=Emr&boxType=CH&windowTitle=Emirate List&configValues="+vConfigValues+"&displayID="+aStyleID+"&OPMode="+aOPMode;
	
	var openPage =path+params;
	 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=100,resizable=0,menubar=0,width="+w+",height="+h;
	
	 openConfigWindow(openPage,features);
}


function confClinicianTypeCopay(ynID,confID,aOPMode){	
	
	 var confIDValue=document.getElementById(confID).value;
	if("S"==aOPMode){
		 var selBox=document.getElementById(ynID).value;
	 if(selBox!="Y"){
		 alert("Please Select Yes");
		 return;
	 }
	}
	 var openPage="/RuleAction.do?mode=confClinicianTypeCopay&confID="+confID+"&confIDValue="+confIDValue+"&opType=V&OPMode="+aOPMode;
	 var features = "scrollbars=1,status=1,toolbar=0,top=30,left=100,resizable=0,menubar=0,width=1000,height=450";
	 popupWindow=window.open(openPage,'',features);
	 
	 popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup;  
}//end of confProviderCopayDetails()
function addAmbulanceDtls(){
	var vCountryID=document.getElementById("countryID").value;
	if(vCountryID.indexOf("175")!=-1){
		var vEmrs=document.getElementById("emiratesID").value;
		if(vEmrs==''||vEmrs==null||vEmrs.length<1){
		alert("Please select  Emirates");
		return;
		}
	}
		document.forms[0].mode.value="addAmbulanceDtls";
		document.forms[0].action="/DynAmbulanceAddRuleAction.do";
		document.getElementById("addConsDtlsBtnID").innerHTML="Please Wait Adding...";
		document.getElementById("addConsDtlsBtnID").disabled=true;
		document.forms[0].submit();
}
function deleteAmbulanceDtls(argRowNum){
	if(!confirm("You want delete this configuration?")){
		return;
	}
	    document.forms[0].rowNum.value=argRowNum;
		document.forms[0].mode.value="deleteAmbulanceDtls";
		document.forms[0].action="/DynAmbulanceRuleAction.do";		
		document.forms[0].submit();
}
function editAmbulanceConf(argRowNum){
	if(!confirm("You want edit this configuration?")){
		return;
	}
	    document.forms[0].rowNum.value=argRowNum;
		document.forms[0].mode.value="editAmbulanceConf";
		document.forms[0].action="/DynAmbulanceRuleAction.do";		
		document.forms[0].submit();
}
function viewAmbulanceStaticConfDtls(aRowNum,aIdentity){	
		
		var w = 600;
		 var h = 200;
		
		var path="/DynAmbulanceRuleAction.do?mode=viewAmbulanceStaticConfDtls";
		var params="&rowNum="+aRowNum+"&identifier="+aIdentity;
		
		var openPage =path+params;
		 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=100,resizable=0,menubar=0,width="+w+",height="+h;
		
		 openConfigWindow(openPage,features);
}
function checkAgeRange(){
	var ageTo= document.forms[0].ageRangeTo.value;
	var ageFromObj=document.forms[0].ageRangeFrom;
	var ageFrom= ageFromObj.value;
	
	if(ageTo!=null&&ageTo!=''&&ageFrom!=null&&ageFrom!=''){
		if(parseInt(ageFrom)>parseInt(ageTo)){
			alert("From age should not greater than To age");
			ageFromObj.value='';
			ageFromObj.focus();
		}
	}
	
	
}