function dynamicList(aIdentifier,aDynaType,aDynaParamID)
{
	 document.getElementById("dynaTypeId").value=aDynaType;
	 document.getElementById("dynaParam").value=document.getElementById(aDynaParamID).value;
	openList(aIdentifier,'DynamicValues');
	
}//end of dynamicList()

function onChangeClaimTypeRnlDls()	{
	document.forms[0].mode.value="doChangeClaimTypeRnlDls";
	document.forms[0].action="/DynRnlDlsRuleAction.do";	
	document.forms[0].submit();
}

function  selectEncounterTypes(aStyleID,aOPMode){ // RND
	 var w = 300;
	 var h = 490;
var vConfigValues=document.getElementById(aStyleID).value;
	
	var path="/DynRnlDlsRuleAction.do?mode=openConfigWindow";
	var params="&windoType=Enc&spType=RND&boxType=CH&windowTitle=Encounter List&configValues="+vConfigValues+"&displayID="+aStyleID+"&OPMode="+aOPMode;
	
	var openPage =path+params;
	 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=200,resizable=0,menubar=0,width="+w+",height="+h;
	
	 openConfigWindow(openPage,features);
}

function openGeoLocation(aStyleID,aOPMode){
	 document.getElementById("countryID").value="";
	 document.getElementById("emiratesID").value="";	 	
	 
	 var w = 300;
	 var h = 490;
	 
	var vConfigValues=document.getElementById(aStyleID).value;
	
	var path="/DynRnlDlsRuleAction.do?mode=openConfigWindow";
	var params="&windoType=Geo&boxType=RD&windowTitle=Geographical Locatoions&configValues="+vConfigValues+"&displayID="+aStyleID+"&OPMode="+aOPMode;
	
	
	var openPage =path+params;
	 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=100,resizable=0,menubar=0,width="+w+",height="+h;
	
	 openConfigWindow(openPage,features);
}//end of openGeoLocation()

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
	
	var path="/DynRnlDlsRuleAction.do?mode=openConfigWindow";
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
	
	var path="/DynRnlDlsRuleAction.do?mode=openConfigWindow";
	var params="&windoType=Emr&boxType=CH&windowTitle=Emirate List&configValues="+vConfigValues+"&displayID="+aStyleID+"&OPMode="+aOPMode;
	
	var openPage =path+params;
	 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=100,resizable=0,menubar=0,width="+w+",height="+h;
	
	 openConfigWindow(openPage,features);
}


function  selectProviderTypes(aStyleID,aOPMode){
	 var w = 300;
	 var h = 490;
var vConfigValues=document.getElementById(aStyleID).value;
	
	var path="/DynRnlDlsRuleAction.do?mode=openConfigWindow";
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

function addRnlDlsDtls(){
	var vCountryID=document.getElementById("countryID").value;
	if(vCountryID.indexOf("175")!=-1){
		var vEmrs=document.getElementById("emiratesID").value;
		if(vEmrs==''||vEmrs==null||vEmrs.length<1){
		alert("Please select  Emirates");
		return;
		}
	}
		document.forms[0].mode.value="addRnlDlsDtls";
		document.forms[0].action="/DynRnlDlsAddRuleAction.do";
		document.getElementById("addRnlDlsDtlsBtnID").innerHTML="Please Wait Adding...";
		document.getElementById("addRnlDlsDtlsBtnID").disabled=true;
		document.forms[0].submit();
}

function editRnlDlsConf(argRowNum){
	if(!confirm("You want edit this configuration?")){
		return;
	}
	    document.forms[0].rowNum.value=argRowNum;
		document.forms[0].mode.value="editRnlDlsConf";
		document.forms[0].action="/DynRnlDlsRuleAction.do";		
		document.forms[0].submit();
}

function viewRnlDlsStaticConfDtls(aRowNum,aIdentity){	
	var w = 600;
	 var h = 200;
	
	var path="/DynRnlDlsRuleAction.do?mode=viewRnlDlsStaticConfDtls";
	var params="&rowNum="+aRowNum+"&identifier="+aIdentity;
	
	var openPage =path+params;
	 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=100,resizable=0,menubar=0,width="+w+",height="+h;
	
	 openConfigWindow(openPage,features);
}

function deleteRnlDlsDtls(argRowNum){
	if(!confirm("You want delete this configuration?")){
		return;
	}
	    document.forms[0].rowNum.value=argRowNum;
		document.forms[0].mode.value="deleteRnlDlsDtls";
		document.forms[0].action="/DynRnlDlsRuleAction.do";		
		document.forms[0].submit();
}