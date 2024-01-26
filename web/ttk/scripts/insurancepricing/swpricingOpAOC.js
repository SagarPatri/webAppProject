function configureNetwork(val){
	//alert('aStyleID:'+aStyleID);
	alert('val:'+val);
	 var w = 300;
	 var h = 490;
	
	// var size=document.getElementById(aStyleID).value;
	 
	// var NI=document.getElementById("networkIndex").value;
	 var vConfigValues=document.getElementById("networkID"+val).value;
		
	var path="/SwInsPricingOpAreaOfCoverAction.do?mode=openConfigWindow";
	var params="&windoType=NTWK&boxType=CH&windowTitle=Network List&configValues="+vConfigValues+"&displayID=networkID"+val; // 
	
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


function openGeoCountryList(aStyleID,aOPMode,aGeoLocID){
/*	var vGeoLocID=document.getElementById("geoLocationID").value;
	if("S"==aOPMode){
	document.getElementById("emiratesID").value="";		
	if(vGeoLocID==null||vGeoLocID==""||vGeoLocID.length<1){
		alert("Please select geographical location");
		return;
	}
	}
	if("V"==aOPMode){
		vGeoLocID=aGeoLocID;
	}*/
	 var w = 300;
	 var h = 490;
var vConfigValues=document.getElementById(aStyleID).value;
	
	var path="/DynAltTmtRuleAction.do?mode=openConfigWindow";
	var params="&windoType=Con&boxType=CH&windowTitle=Country List&configValues="+vConfigValues+"&displayID="+aStyleID+"&dynaParam="+vGeoLocID+"&OPMode="+aOPMode;
	
	var openPage =path+params;
	 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=200,resizable=0,menubar=0,width="+w+",height="+h;
	
	 openConfigWindow(openPage,features);
}

