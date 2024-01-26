

function dynamicList(aIdentifier,aDynaType,aDynaParamID)
{
	 document.getElementById("dynaTypeId").value=aDynaType;
	 document.getElementById("dynaParam").value=document.getElementById(aDynaParamID).value;
	openList(aIdentifier,'DynamicValues');
	
}//end of dynamicList()

function  selectInvestTypes(aStyleID,aOPMode){
	 
	 var w = 300;
	 var h = 490;
var vConfigValues=document.getElementById(aStyleID).value;
var vConfigValues=document.getElementById(aStyleID).value;
           var invSeqID=document.forms[0].investgationSeqID.value;
           var boxType="CH";
	if(invSeqID.length>1)boxType="RD";
	
	var path="/DynInvestRuleAction.do?mode=openConfigWindow";
	var params="&windoType=Inv&boxType="+boxType+"&windowTitle=Invest Type List&configValues="+vConfigValues+"&displayID="+aStyleID+"&OPMode="+aOPMode;
	
	var openPage =path+params;
	 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=200,resizable=0,menubar=0,width="+w+",height="+h;
	
	 openConfigWindow(openPage,features);
}

function  selectProviderTypes(aStyleID,aOPMode){
	 
	 var w = 300;
	 var h = 490;
var vConfigValues=document.getElementById(aStyleID).value;
	
	var path="/DynInvestRuleAction.do?mode=openConfigWindow";
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


function addInvestDtls(){
        document.forms[0].mode.value="addInvestDtls";
       // var invSelObj=document.forms[0].investTypeName;investgationSeqID
       // document.forms[0].investTypeLabel.value=invSelObj.options[invSelObj.selectedIndex].text;
		document.forms[0].action="/DynInvestRuleAddAction.do";
		document.getElementById("addConsDtlsBtnID").innerHTML="Please Wait Adding...";
		document.getElementById("addConsDtlsBtnID").disabled=true;
		
		document.forms[0].submit();
}
function deleteInvestDtls(argRowNum){
	if(!confirm("You want delete this configuration?")){
		return;
	}
	    document.forms[0].rowNum.value=argRowNum;
		document.forms[0].mode.value="deleteInvestDtls";
		document.forms[0].action="/DynInvestRuleAction.do";		
		document.forms[0].submit();
}
function editInvestConf(argRowNum){
	if(!confirm("You want edit this configuration?")){
		return;
	}
	    document.forms[0].rowNum.value=argRowNum;
		document.forms[0].mode.value="editInvestConf";
		document.forms[0].action="/DynInvestRuleAction.do";		
		document.forms[0].submit();
}
function viewInvestStaticConfDtls(aRowNum,aIdentity){	
		
		var w = 600;
		var h = 200;
		
		var path="/DynInvestRuleAction.do?mode=viewInvestStaticConfDtls";
		var params="&rowNum="+aRowNum+"&identifier="+aIdentity;
		
		var openPage =path+params;
		 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=100,resizable=0,menubar=0,width="+w+",height="+h;
		
		 openConfigWindow(openPage,features);
}
