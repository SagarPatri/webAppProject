


function  selectProviderTypes(aStyleID,aOPMode){
	 
	 var w = 300;
	 var h = 490;
var vConfigValues=document.getElementById(aStyleID).value;
	
	var path="/DynPhysioRuleAction.do?mode=openConfigWindow";
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


function addPhysioDtls(){
        document.forms[0].mode.value="addPhysioDtls";       
		document.forms[0].action="/DynPhysioAddRuleAction.do";
		document.getElementById("addPhysioDtlsBtnID").innerHTML="Please Wait Adding...";
		document.getElementById("addPhysioDtlsBtnID").disabled=true;
		document.forms[0].submit();
}
function deletePhysioDtls(argRowNum){
	if(!confirm("You want delete this configuration?")){
		return;
	}
	    document.forms[0].rowNum.value=argRowNum;
		document.forms[0].mode.value="deletePhysioDtls";
		document.forms[0].action="/DynPhysioRuleAction.do";		
		document.forms[0].submit();
}
function editPhysioConf(argRowNum){
	if(!confirm("You want edit this configuration?")){
		return;
	}
	    document.forms[0].rowNum.value=argRowNum;
		document.forms[0].mode.value="editPhysioConf";
		document.forms[0].action="/DynPhysioRuleAction.do";		
		document.forms[0].submit();
}
function viewPhysiotStaticConfDtls(aRowNum,aIdentity){	
		
		var w = 600;
		var h = 200;
		
		var path="/DynPhysioRuleAction.do?mode=viewPhysiotStaticConfDtls";
		var params="&rowNum="+aRowNum+"&identifier="+aIdentity;
		
		var openPage =path+params;
		 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=100,resizable=0,menubar=0,width="+w+",height="+h;
		
		 openConfigWindow(openPage,features);
}
