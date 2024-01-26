//java script for the hospital general screen in the empanelment of hospital flow
//function to submit the screen
function onUserSubmit()
{
  		if(!JS_SecondSubmit)
        {
    		document.forms[1].mode.value="doSave";
    		document.forms[1].action="/AddPartnerSearchAction.do";
    		JS_SecondSubmit=true;
    		document.forms[1].submit();
    	}//end of if(!JS_SecondSubmit)
}//end of onUserSubmit()

function onDiscrepancySubmit()
{
	document.forms[1].mode.value="doDiscrepancy";
   	document.forms[1].action="/GeneralDiscrepancyAction.do";
   	document.forms[1].submit();
}//end of onDiscrepancySubmit()

function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doView";
	   	document.forms[1].action="/AddPartnerSearchAction.do";
	   	document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
		stopPreAuthClaim();
		//setnetworkTypes();
	}//end of else

}//end of onReset()
function onChangeState(obj)
{
	if(obj.value!=""){
    	document.forms[1].mode.value="doChangeState";
    	document.forms[1].focusID.value="state";
    	document.forms[1].action="/EditPartnerSearchAction.do";
    	document.forms[1].submit();
	}
}//end of onChangeCity()

function onConfiguration()
{
	document.forms[1].mode.value="doConfiguration";
	document.forms[1].action="/EditHospitalSearchAction.do";
	document.forms[1].submit();
}//end of onConfiguration()

function stopPreAuthClaim()
{
	if(document.forms[1].stopPreAuth.value=="Y")
	    document.forms[1].stopPreAuthsYN.checked=true;
	if(document.forms[1].stopClaim.value=="Y")
		document.forms[1].stopClaimsYN.checked=true;
}//end of stopPreAuthClaim()

/*
 * coomented after makign masters for Networks
 * function setnetworkTypes()
{
	if(document.forms[1].cnynNet.value=="Y")
	    document.forms[1].CNYN.checked=true;
	if(document.forms[1].gnynNet.value=="Y")
		document.forms[1].GNYN.checked=true;
	if(document.forms[1].srnynNet.value=="Y")
		document.forms[1].SRNYN.checked=true;
	if(document.forms[1].rnynNet.value=="Y")
		document.forms[1].RNYN.checked=true;
	if(document.forms[1].wnynNet.value=="Y")
		document.forms[1].WNYN.checked=true;
	
	if(document.forms[1].primaryNetworkID.value=="GN")
	{
		document.forms[1].CNYN.disabled=true;
	}
	if(document.forms[1].primaryNetworkID.value=="SN")
	{
		document.forms[1].CNYN.disabled=true;
		document.forms[1].GNYN.disabled=true;
	}
	if(document.forms[1].primaryNetworkID.value=="BN")
	{
		document.forms[1].CNYN.disabled=true;
		document.forms[1].GNYN.disabled=true;
		document.forms[1].SRNYN.disabled=true;
	}
	if(document.forms[1].primaryNetworkID.value=="WN")
	{
		document.forms[1].CNYN.disabled=true;
		document.forms[1].GNYN.disabled=true;
		document.forms[1].SRNYN.disabled=true;
		document.forms[1].RNYN.disabled=true;
	}
	
	
	if(document.forms[1].providerReviewYN.value=="Y")
		document.forms[1].providerReview.checked=true;
	
	if(document.forms[1].provgrpHidden.value=="Y")
	{
		document.forms[1].providerYN.checked=true;
		document.getElementById("idprovGrpListId").style.display="none";
	}
	
	if(document.forms[1].sectorType.value=="SPR")
	{
		document.getElementById("idprovSector").style.display="none";
		document.forms[1].sectorNumb.value="";
	}else{
		document.getElementById("idprovSector").style.display="inline";
	}
	
	if(document.forms[1].provGrpId.value=="PGR" || document.forms[1].provGrpId.value=="")
	{
		document.getElementById("idprovGrpListId").style.display="none";
	}else
	{
		document.getElementById("idprovGrpListId").style.display="inline";
	}
	
//	changeNetworks(document.forms[1].primaryNetworkID);
}//end of setnetworkTypes()
*/

function checkHideGroupProvider()
{
	if(document.getElementById("providerYN").checked==true)
	{
		document.getElementById("idprovGrpListId").style.display="none";
		document.forms[1].groupName.style.display	=	"inline";
		document.forms[1].provGrpListId.value="";	
	}else{
		document.getElementById("idprovGrpListId").style.display="inline";
		document.forms[1].groupName.style.display	=	"none";
		document.forms[1].groupName.value	=	'';
	}
}

function onCheckIndGrp(obj)
{
	var indORgrp	=	obj.value;
	if(indORgrp=='GRP')
	{
		document.getElementById("createNew").style.display	=	"inline";
		document.forms[1].createNewGrp.value		=	"";
		document.getElementById("groupProvider").style.display	=	"inline";
		
	}
	else{
		document.getElementById("createNew").style.display	=	"none";
		document.getElementById("grpContacts1").style.display	="none";
		document.getElementById("grpContacts2").style.display	="none";
		document.getElementById("grpContacts3").style.display	="none";

		document.forms[1].groupName.value			=	"";
		document.forms[1].grpContactPerson.value	=	"";
		document.forms[1].grpContactNo.value		=	"";
		document.forms[1].grpContactEmail.value		=	"";
		document.forms[1].grpContactAddress.value	=	"";
		document.getElementById("provGrpListId").selectedIndex=0;
		document.forms[1].createNewGrp.value		=	"";
	}
}

//to create groups
function onCreateGroup()
{
	document.getElementById("grpContacts1").style.display="inline";
	document.getElementById("grpContacts2").style.display="inline";
	document.getElementById("grpContacts3").style.display="inline";
	document.getElementById("groupProvider").style.display="none";
	document.forms[1].createNewGrp.value		=	"NewGrp";
	
}
//Ajax code to get Provider details dynamically.
function getProviderDetails(obj)
{
	//alert("value::"+obj.value);
	var val	=	obj.value;
	val	=	val.split('[');
	
	var valNew	=	val[1].substring(0,val[1].length-1);
	var provName	=	obj.value.split('[');
	if (window.ActiveXObject){
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		if (xmlhttp){
			xmlhttp.open("GET",'/HospitalSearchAction.do?mode=getLicenceNumbers&ProviderId=' +valNew+'&provName=' +provName[0] ,true);
			xmlhttp.onreadystatechange=state_ChangeProvider;
			xmlhttp.send();
		}
	}
	
	//Ajax code removing as we are getting the code in the auto complete box itself
	/*var val	=	obj.value;
	val	=	val.split('[');
	
	var valNew	=	val[1].substring(0,val[1].length-1);
	document.getElementById("irdaNumber").value	=	valNew;*/
}

function state_ChangeProvider(){
	document.getElementById("validHosp").innerHTML	=	'';
	var result,result_arr;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split(","); 
			if(result_arr[0]!=""){
				document.getElementById("irdaNumber").value=result_arr[0];
				document.getElementById("validHosp").innerHTML	=	'Valid Provider';
				document.getElementById("validHosp").style.color = 'green';
			}
			else{
				document.getElementById("validHosp").innerHTML	=	'Invalid Provider';
				document.getElementById("validHosp").style.color = 'red';
				document.getElementById("irdaNumber").value="";
				document.getElementById("hospitalName").value="";
			}
			
		}
	}
}

function getProviderDetailsOnLicence(obj)
{
	var val	=	obj.value;
	if (window.ActiveXObject){
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		if (xmlhttp){
			xmlhttp.open("GET",'/HospitalSearchAction.do?mode=getLicenceNumbers&ProviderId=' +val+'&strIdentifier=GetNameByLicence' ,true);
			xmlhttp.onreadystatechange=state_ChangeLicence;
			xmlhttp.send();
		}
	}
}


function state_ChangeLicence(){
	document.getElementById("validHosp").innerHTML	=	'';
	var result,result_arr;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split(","); 
			if(result_arr[0]!=""){
				document.getElementById("hospitalName").value=result_arr[0];
				document.getElementById("validHosp").innerHTML	=	'Valid Provider';
				document.getElementById("validHosp").style.color = 'green';
			}
			else{
				document.getElementById("validHosp").innerHTML	=	'Invalid Provider';
				document.getElementById("validHosp").style.color = 'red';
				document.getElementById("irdaNumber").value="";
				document.getElementById("hospitalName").value="";
			}
			
		}
	}
}
/*function changeNetworks(obj)
{	
	var networkType	=	obj.value;
	if(networkType=='CN')
	{
		document.getElementById("CNYN").disabled = false;
		document.getElementById("GNYN").disabled = false;
		document.getElementById("SRNYN").disabled = false;
		document.getElementById("RNYN").disabled = false;
		document.getElementById("WNYN").disabled = false;
		
		
		document.getElementById("CNYN").checked=true;
		document.getElementById("RNYN").checked=false;
		document.getElementById("SRNYN").checked=false;
		document.getElementById("GNYN").checked=false;
		document.getElementById("WNYN").checked = false;
	}else if(networkType=='GN')
	{
		document.getElementById("CNYN").disabled = true;
		document.getElementById("GNYN").disabled = false;
		document.getElementById("SRNYN").disabled = false;
		document.getElementById("RNYN").disabled = false;
		document.getElementById("WNYN").disabled = false;

		document.getElementById("CNYN").checked = false;
		document.getElementById("GNYN").checked=true;
		document.getElementById("RNYN").checked=false;
		document.getElementById("SRNYN").checked=false;
		document.getElementById("WNYN").checked = false;

	}else if(networkType=='SN')
	{
		document.getElementById("CNYN").disabled = true;
		document.getElementById("GNYN").disabled = true;
		document.getElementById("SRNYN").disabled = false;
		document.getElementById("RNYN").disabled = false;
		document.getElementById("WNYN").disabled = false;
		
		document.getElementById("CNYN").checked = false;
		document.getElementById("GNYN").checked = false;
		document.getElementById("SRNYN").checked = true;
		document.getElementById("RNYN").checked=false;
		document.getElementById("WNYN").checked = false;
	}else if(networkType=='BN')
	{
		document.getElementById("CNYN").disabled = true;
		document.getElementById("GNYN").disabled = true;
		document.getElementById("SRNYN").disabled = true;
		document.getElementById("RNYN").disabled = false;
		document.getElementById("WNYN").disabled = false;
		
		document.getElementById("CNYN").checked = false;
		document.getElementById("GNYN").checked = false;
		document.getElementById("SRNYN").checked=false;
		document.getElementById("RNYN").checked=true;
		document.getElementById("WNYN").checked = false;

	}else if(networkType=='VN')
	{
		document.getElementById("CNYN").disabled = true;
		document.getElementById("GNYN").disabled = true;
		document.getElementById("SRNYN").disabled = true;
		document.getElementById("RNYN").disabled = true;
		document.getElementById("WNYN").disabled = false;

		document.getElementById("CNYN").checked = false;
		document.getElementById("GNYN").checked = false;
		document.getElementById("RNYN").checked = false;
		document.getElementById("SRNYN").checked = false;
		document.getElementById("WNYN").checked = true;
		
	}

}*/

function onUploadDocs()
{
	document.forms[1].mode.value="doDefault";
	var ptnrSeqId	=	document.forms[1].ptnrSeqId.value;
	//document.forms[1].action="/UploadMOUCertificatesList.do?ptnrSeqId="+ptnrSeqId;
	document.forms[1].submit();
}

function changeMe(obj)
{
	var val	=	obj.value;
	//alert(val);
	if(val=='Phone No')
	{
		document.forms[1].officePhone1.value="";
	}
	if(val=='ISD')
	{
		document.forms[1].isdCode.value="";
	}
	if(val=='STD')
	{
		document.forms[1].stdCode.value="";
	}
}
function getMe(obj)
{
	//alert(obj);
	
	if(obj=='ISD')
	{
		if(document.forms[1].isdCode.value=="")
			document.forms[1].isdCode.value=obj;
	}
	if(obj=='STD')
	{
		if(document.forms[1].stdCode.value=="")
			document.forms[1].stdCode.value=obj;
	}if(obj=='Phone No')
	{
		if(document.forms[1].officePhone1.value=="")
			document.forms[1].officePhone1.value=obj;
	}
	
	
}

function checkProviderGroup(obj)
{
	if(document.getElementById("providerYN").checked==true)
	{
		document.getElementById("tdprovGrpId").style.display="inline";
		//document.getElementById("idprovGrpListId").style.display="inline";
	}
	if(document.getElementById("providerYN").checked==false)
	{
		document.getElementById("tdprovGrpId").style.display="none";
		document.getElementById("idprovGrpListId").style.display="none";
		document.forms[1].provGrpId.value="";
		document.forms[1].provGrpListId.value="";	
	}
}

function changeGroupType(obj)
{
	if(obj.value=="PGR" || obj.value=="")
	{
		document.getElementById("idprovGrpListId").style.display="none";
	}else
	{
		document.getElementById("idprovGrpListId").style.display="inline";
	}
}

function onChangeSector(obj)
{
	var val	=	obj.value;
	if(val=="SGO")
	{
		document.getElementById("idprovSector").style.display="inline";
	}else{
		document.getElementById("idprovSector").style.display="none";
	}
}

function getToolTip(){
    document.forms[1].providerReview.title="Please Review the Hospital Details before selecting";
}

function onChangeRegAuth(){
	document.forms[1].hospitalName.value="";
	document.forms[1].irdaNumber.value="";
	document.forms[1].action="/ChangeRegAuth.do";
	document.forms[1].submit();
}

function onLoadGroup(indOrGrp)
{
	if(indOrGrp=='GRP' && document.forms[1].createNewGrp.value=='NewGrp')
	{
		document.getElementById("grpContacts1").style.display="inline";
		document.getElementById("grpContacts2").style.display="inline";
		document.getElementById("grpContacts3").style.display="inline";
		
	}
	else if(indOrGrp=='GRP' && document.forms[1].createNewGrp.value=='')
	{
		document.getElementById("createNew").style.display="inline";
		
	} 
}

function changeNetworks(obj)
{
	var networkType	=	obj.value;
	var temp	=	0;
	var inputs	=	document.getElementsByName("serviceType");
	for(var i=0;i<inputs.length;i++)
	{
		if(inputs[i].type=="checkbox"){
			inputs[i].disabled	=	false;
			if(networkType==inputs[i].value){
				inputs[i].checked	=	true;
				document.getElementById("hidServiceType"+i).value='Y';
				temp	=	i;
			}
			else{
				inputs[i].checked	=	false;
				document.getElementById("hidServiceType"+i).value='N';
			}
		}
	}
	
	for(var k=0;k<temp;k++)
		document.getElementById("serviceType"+k).disabled=true;
}

function checkNetworkType(obj){
	if(document.getElementById("serviceType"+obj).checked)
		document.getElementById("hidServiceType"+obj).value='Y';
	else
		document.getElementById("hidServiceType"+obj).value='N';
}

function disableHighlevelNet(obj)
{
	var res	=	obj.split(",");
if(document.forms[1].hospSeqId.value!=""){
	for(var a=0;a<obj.length;a++){
		document.getElementById("serviceType"+res[a]).checked	=	true;
		document.getElementById("hidServiceType"+res[a]).value	=	"Y";
	}
}
}

function onHaadFactorDefault()
{
	if(!JS_SecondSubmit)
    {
		var hosp_seq_id	=	document.forms[1].hospSeqId.value;
		if(hosp_seq_id==null || hosp_seq_id=='')
		{
			alert("Please Empanel the Provider First..");
			return false;
		}
		document.forms[1].mode.value="doHaadFactorDefault";
    	document.forms[1].action="/EditHaadFactorsHospitalAction.do?hosp_seq_id="+hosp_seq_id;
    	document.forms[1].submit();
    
    }
}

