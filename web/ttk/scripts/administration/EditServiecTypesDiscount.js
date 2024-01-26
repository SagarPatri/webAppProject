//java script for the tariff details screen in the Administration of hospital flow.

function onSearch()
{
	
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
	    document.forms[1].mode.value = "doSearchTariffItems";
	    document.forms[1].action = "/ShowSearchModifyTariffItems.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onNextServiceTypes()
{
	
	if(document.forms[1].tariffType.value=="")
	{
		alert("Please select Type of Tariff");
		document.forms[1].tariffType.focus();
		return false;
	}
	
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
	    document.forms[1].mode.value = "doNextServiceTypes";
	    document.forms[1].action = "/ModifyServiceTypes.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}
function onUpdateServiceTypes()
{
	
	if(document.forms[1].tariffType.value=="")
	{
		alert("Please select Type of Tariff");
		document.forms[1].tariffType.focus();
		return false;
	}
	
	if(!JS_SecondSubmit)
	 {
			trimForm(document.forms[1]);
		    document.forms[1].mode.value = "doModifySaveServiceTypes";
		    document.forms[1].action = "/ModifySaveServiceTypes.do";
			JS_SecondSubmit=true;
		    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}


function onUpdateSaveServiceTypes()
{
	/*var inputs = document.getElementsByTagName('input');
	{		
		for(var i = 0; i < inputs.length; i++) {
			
		   if(inputs[i].value=='' && inputs[i].type=='text' && (inputs[i].name=='cnId' || inputs[i].name=='gnId' || 
				   inputs[i].name=='snId' || inputs[i].name=='bnId' || inputs[i].name=='wnId'))
		   {
			   alert("Field cannot be empty in Column:"+inputs[i].name);
			   return false;
		   }
		}
	}*/
	
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
	    document.forms[1].mode.value = "doModifySaveServiceTypes";
	    document.forms[1].action = "/ModifySaveServiceTypes.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}

function onChangeTariffType(obj)
{
	var tariffType	=	obj.value;
	if(tariffType=='HOSP')
	{
		document.getElementById("apayerCode").style.display="inline";
		document.getElementById("aproviderCode").style.display="inline";
		document.getElementById("anetworkType").style.display="inline";
		document.getElementById("acorpCode").style.display="none";
		document.getElementById("INDGRP").style.display="inline";
		
	}
	else if(tariffType=='INS')
	{
		document.getElementById("apayerCode").style.display="inline";
		document.getElementById("aproviderCode").style.display="none";
		document.getElementById("anetworkType").style.display="inline";
		document.getElementById("acorpCode").style.display="none";

		document.getElementById("indOrGrp").selectedIndex=0;
		document.getElementById("grpProviderName").selectedIndex=0;
		document.getElementById("INDGRP").style.display="none";
		document.getElementById("GrpProviderNameTR").style.display="none";
		
	}else if(tariffType=='COR')
	{
		document.getElementById("apayerCode").style.display="inline";
		document.getElementById("aproviderCode").style.display="none";
		document.getElementById("anetworkType").style.display="inline";
		document.getElementById("acorpCode").style.display="inline";

		document.getElementById("indOrGrp").selectedIndex=0;
		document.getElementById("grpProviderName").selectedIndex=0;
		document.getElementById("INDGRP").style.display="none";
		document.getElementById("GrpProviderNameTR").style.display="none";
	}else if(tariffType=='TPA')
	{
		document.getElementById("apayerCode").style.display="none";
		document.getElementById("aproviderCode").style.display="none";
		document.getElementById("anetworkType").style.display="inline";
		document.getElementById("acorpCode").style.display="none";

		document.getElementById("indOrGrp").selectedIndex=0;
		document.getElementById("grpProviderName").selectedIndex=0;
		document.getElementById("INDGRP").style.display="none";
		document.getElementById("GrpProviderNameTR").style.display="none";
	}else if(tariffType==''){
		document.getElementById("indOrGrp").selectedIndex=0;
		document.getElementById("grpProviderName").selectedIndex=0;
		document.getElementById("INDGRP").style.display="none";
		document.getElementById("GrpProviderNameTR").style.display="none";
	}
}
//function to change Individual or Group 
function onChangeIndOrGrp(obj)
{
	var value	=	obj.value;
	if(value=='GRP')
	{
		document.getElementById("GrpProviderNameTR").style.display="inline";
		document.forms[1].providerCode.value="";
		document.forms[1].grpProviderCode.value="";
		//for provider selections
		document.getElementById("provCheckBox").style.display="inline";
		document.getElementById("provRadio").style.display="none";
		//for network sections
		document.getElementById("networkRadio").style.display="none";
		document.getElementById("networkCheck").style.display="inline";

		document.getElementById("networkRadio").value="";
		document.getElementById("networkCheck").value="";
		
	}
	//if(value=='IND')
	else
	{
		document.getElementById("GrpProviderNameTR").style.display="none";
		document.forms[1].providerCode.value="";
		document.forms[1].grpProviderCode.value="";
		//for provider selections
		document.getElementById("provCheckBox").style.display="none";
		document.getElementById("provRadio").style.display="inline";
		//for network sections
		document.getElementById("networkRadio").style.display="inline";
		document.getElementById("networkCheck").style.display="none";

		document.getElementById("networkRadio").value="";
		document.getElementById("networkCheck").value="";
	}
}

function onChangeIndOrGrpOnLoad(obj)
{
	var value	=	obj.value;
	if(value=='GRP')
	{
		document.getElementById("GrpProviderNameTR").style.display="inline";
		//for provider selections
		document.getElementById("provCheckBox").style.display="inline";
		document.getElementById("provRadio").style.display="none";
		//for network sections
		document.getElementById("networkRadio").style.display="none";
		document.getElementById("networkCheck").style.display="inline";
	}
	//if(value=='IND')
	else
	{
		document.getElementById("GrpProviderNameTR").style.display="none";
		//for provider selections
		document.getElementById("provCheckBox").style.display="none";
		document.getElementById("provRadio").style.display="inline";
		//for network sections
		document.getElementById("networkRadio").style.display="inline";
		document.getElementById("networkCheck").style.display="none";
	}
}
//function to check only positive numbers
function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   alert(charCode);
   if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
      return false;
   return true;
}
//function to make providers list null when change group names
function changeGroup()
{
	document.forms[1].providerCode.value="";
	document.forms[1].grpProviderCode.value="";
}
function onBackServiceTypes()
{
	document.forms[1].mode.value="doDefault";
    document.forms[1].tab.value="Search";
    document.forms[1].action = "/TarrifSearchAction.do";
    document.forms[1].submit();
}

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearchTariffItems";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/ShowTariffItems.do";
    document.forms[1].submit();
}//end of toggle(sortid)
//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearchTariffItems";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/ShowTariffItems.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/ShowTariffItems.do";
    document.forms[1].submit();
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/ShowTariffItems.do";
    document.forms[1].submit();
}//end of PressForward()
//function to display pages based on search criteria