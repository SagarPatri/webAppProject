


function  selectProviderTypes(aStyleID,aOPMode){
	 
	 var w = 300;
	 var h = 490;
var vConfigValues=document.getElementById(aStyleID).value;
	
	var path="/DynPharmaRuleAction.do?mode=openConfigWindow";
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


function addPharmaDtls(){
        document.forms[0].mode.value="addPharmaDtls";       
		document.forms[0].action="/DynPharmaAddRuleAction.do";
		document.getElementById("addPharmaDtlsBtnID").innerHTML="Please Wait Adding...";
		document.getElementById("addPharmaDtlsBtnID").disabled=true;
		document.forms[0].submit();
}
function deletePharmaDtls(argRowNum){
	if(!confirm("You want delete this configuration?")){
		return;
	}
	    document.forms[0].rowNum.value=argRowNum;
		document.forms[0].mode.value="deletePharmaDtls";
		document.forms[0].action="/DynPharmaRuleAction.do";		
		document.forms[0].submit();
}
function editPharmaConf(argRowNum){
	if(!confirm("You want edit this configuration?")){
		return;
	}
	    document.forms[0].rowNum.value=argRowNum;
		document.forms[0].mode.value="editPharmaConf";
		document.forms[0].action="/DynPharmaRuleAction.do";		
		document.forms[0].submit();
}
function viewPharmaStaticConfDtls(aRowNum,aIdentity){	
		
		var w = 600;
		var h = 200;
		
		var path="/DynPharmaRuleAction.do?mode=viewPharmaStaticConfDtls";
		var params="&rowNum="+aRowNum+"&identifier="+aIdentity;
		
		var openPage =path+params;
		 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=100,resizable=0,menubar=0,width="+w+",height="+h;
		
		 openConfigWindow(openPage,features);
}
