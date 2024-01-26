//java script for the tariff details screen in the empanelment of hospital flow.

function onSearch()
{
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/TariffAction.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()
//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/TariffAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/TariffAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/TariffAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/TariffAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onHistoryIcon(rownum)
{
	document.forms[1].reset();
	document.forms[1].rownum.value=rownum;
	document.forms[1].mode.value ="doViewRevisionHistory";
	document.forms[1].child.value="RevisePlan";
    document.forms[1].action = "/TariffAction.do";
	document.forms[1].submit();
}//end of function onHistoryIcon(rownum)

//for INTX
function onUploadTariff()
{
	/*var priceRefId	=	0;
	var tariffType	=	document.forms[1].tariffType.value;
	if(tariffType=='HOSP')
	{
		priceRefId	=	document.forms[1].payerCode.value+"-"+document.forms[1].providerCode.value+"-"+document.forms[1].networkType.value;
	}
	else if(tariffType=='INS')
	{
		priceRefId	=	document.forms[1].payerCode.value+"-"+document.forms[1].networkType.value;

	}else if(tariffType=='COR')
	{
		priceRefId	=	document.forms[1].payerCode.value+"-"+document.forms[1].corpCode.value+"-"+document.forms[1].networkType.value;
	}*/
	if(!JS_SecondSubmit)
	 {
		document.forms[1].uploadButton.value = 'Uploading...';
		document.forms[1].uploadButton.disabled = true;
		
	    trimForm(document.forms[1]);
	    document.forms[1].mode.value = "doUploadTariff";
	   // document.forms[1].action = "/TariffUploadsAction.do?priceRefId="+priceRefId;
	    document.forms[1].action = "/TariffUploadsAction.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}

function onChangeTariffType(obj)
{
	var tariffType	=	obj.value;
	//making all values to null
	/*document.forms[1].corpCode.value='';
	document.forms[1].payerCode.value='';
	document.forms[1].providerCode.value='';
	document.forms[1].networkType.value='';*/
	
	
	if(tariffType=='HOSP')
	{
		/*document.forms[1].corpCode.disabled=true;
		document.forms[1].payerCode.disabled=false;
		document.forms[1].providerCode.disabled=false;
		document.forms[1].networkType.disabled=false;*/
		document.getElementById("apayerCode").style.display="inline";
		document.getElementById("aproviderCode").style.display="inline";
		document.getElementById("anetworkType").style.display="inline";
		document.getElementById("acorpCode").style.display="none";
		document.getElementById("INDGRP").style.display="inline";
		
		document.getElementById("providerNetworkTD").value	=	"";
		document.getElementById("payerNetworkTD").value		=	"";
		document.getElementById("providerNetworkTD").style.display="inline";
		document.getElementById("payerNetworkTD").style.display="none";
		
	}
	else if(tariffType=='INS')
	{
		/*document.forms[1].providerCode.disabled=true;
		document.forms[1].corpCode.disabled=true;
		document.forms[1].payerCode.disabled=false;
		document.forms[1].networkType.disabled=false;*/

		document.getElementById("apayerCode").style.display="inline";
		document.getElementById("aproviderCode").style.display="none";
		document.getElementById("anetworkType").style.display="inline";
		document.getElementById("acorpCode").style.display="none";
		

		document.getElementById("indOrGrp").selectedIndex=0;
		document.getElementById("grpProviderName").selectedIndex=0;
		document.getElementById("INDGRP").style.display="none";
		document.getElementById("GrpProviderNameTR").style.display="none";
		
		document.getElementById("providerNetworkTD").value	=	"";
		document.getElementById("payerNetworkTD").value		=	"";
		document.getElementById("providerNetworkTD").style.display="none";
		document.getElementById("payerNetworkTD").style.display="inline";
		
	}else if(tariffType=='COR')
	{
		/*document.forms[1].providerCode.disabled=true;
		
		document.forms[1].payerCode.disabled=false;
		document.forms[1].networkType.disabled=false;
		document.forms[1].corpCode.disabled=false;*/
		
		document.getElementById("apayerCode").style.display="inline";
		document.getElementById("aproviderCode").style.display="none";
		document.getElementById("anetworkType").style.display="inline";
		document.getElementById("acorpCode").style.display="inline";

		document.getElementById("indOrGrp").selectedIndex=0;
		document.getElementById("grpProviderName").selectedIndex=0;
		document.getElementById("INDGRP").style.display="none";
		document.getElementById("GrpProviderNameTR").style.display="none";
		
		document.getElementById("providerNetworkTD").value	=	"";
		document.getElementById("payerNetworkTD").value		=	"";
		document.getElementById("providerNetworkTD").style.display="none";
		document.getElementById("payerNetworkTD").style.display="inline";
		
		
	}else if(tariffType=='TPA')
	{
		/*document.forms[1].providerCode.disabled=true;
		
		document.forms[1].payerCode.disabled=true;
		document.forms[1].networkType.disabled=true;
		document.forms[1].corpCode.disabled=false;*/

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
	}
	//if(value=='IND')
	else
	{
		document.getElementById("GrpProviderNameTR").style.display="none";
		document.forms[1].providerCode.value="";
	}
}
//function to make providers list null when change group names
function changeGroup()
{
	document.forms[1].providerCode.value="";
}

function tariffUploadFormat()
{
	
	var openPage = "/ReportsAction.do?mode=doViewCommonForAll&module=professionDocs&fileName=";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}

function showErrorLog(obj)
{
	var openPage = "/ReportsAction.do?mode=doViewCommonForAll&module=tariffUploadLogs&fileName=obj";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}

function onBackTariff()
{
    document.forms[1].mode.value = "doDefault";
    document.forms[1].action = "/TarrifSearchAction.do";
    document.forms[1].tab.value= "Search";
    document.forms[1].submit();
}
//function to select multiple corporates
function onCorporate()
{
    document.forms[1].mode.value ="doCorporateDefault";
    document.forms[1].action = "/TariffSelectCorporatesAction.do";
    document.forms[1].submit();
}