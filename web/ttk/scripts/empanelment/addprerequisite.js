//java script for the hospital general screen in the empanelment of hospital flow
//function to submit the screen
function onUserSubmit()
{
  		if(!JS_SecondSubmit)
        {
  			trimForm(document.forms[1]);
  			if(document.forms[1].stopPreAuthsYN.checked)
  	    		document.forms[1].stopPreAuth.value="Y";
  	    	else
  	    		document.forms[1].stopPreAuth.value="N";
  	    	if(document.forms[1].stopClaimsYN.checked)
  	    		document.forms[1].stopClaim.value="Y";
  	    	else
  	    		document.forms[1].stopClaim.value="N";
  	   //<!-- added for RoomRenttariff -->
  			
  	    	/*if(document.forms[1].gipsaPpn.value=="Y")
  			{
  				document.forms[1].gipsaPpnYN.checked=true;
  			}
  			else
  			{
  				document.forms[1].gipsaPpnYN.checked=false;
  			}*/
  			//end  <!-- added for RoomRenttariff -->
  	    	document.forms[1].categoryID.disabled=false;
    		document.forms[1].mode.value="doSave";
    		document.forms[1].action="/AddHospitalSearchAction.do";
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
		document.forms[1].mode.value="doReset";
	   	document.forms[1].action="/EditHospitalSearchAction.do";
	   	document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
		stopPreAuthClaim();
	}//end of else

}//end of onReset()

function onChangeState()
{
    	document.forms[1].mode.value="doChangeState";
    	document.forms[1].focusID.value="state";
    	document.forms[1].action="/EditHospitalSearchAction.do";
    	document.forms[1].submit();
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



//Ajax code to get Provider details dynamically.
function getProviderDetails(obj)
{
	//alert("value::"+obj.value);
	var provname	=	obj.value;
	var id	=	provname.substring(provname.indexOf("@")+1,provname.length);
	//alert("id::"+id);
	document.getElementById("irdaNumber").value	=	id;
	
	/*if (window.ActiveXObject){
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		if (xmlhttp){
			xmlhttp.open("GET",'/AddHospitalSearchAction.do?mode=getProviderDetails&ProviderId=' +id ,true);
			xmlhttp.onreadystatechange=state_ChangeProvider;
			xmlhttp.send();
		}
	}*/
	
}

/*function state_ChangeProvider(){
	var result,result_arr;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split(","); 
			//document.getElementById("sProviderName").value=result_arr[0];
			alert(result_arr[0]);
			alert(result_arr[1]);
			alert(result_arr[2]);
			document.getElementById("irdaNumber").value=result_arr[1];
			//document.getElementById("sEmirate").value=result_arr[2];
			
		}
	}
}*/



function changeNetworks(obj)
{	
	//alert(obj.value);
	var networkType	=	obj.value;
	 
	if(networkType=='DCN')
	{
		document.getElementById("CNYN").disabled = false;
		document.getElementById("GNYN").disabled = false;
		document.getElementById("SRNYN").disabled = false;
		document.getElementById("RNYN").disabled = false;
	}else if(networkType=='DGEN')
	{
		document.getElementById("CNYN").checked = false;
		
		document.getElementById("CNYN").disabled = true;
		document.getElementById("GNYN").disabled = false;
		document.getElementById("SRNYN").disabled = false;
		document.getElementById("RNYN").disabled = false;
	}else if(networkType=='DSRN')
	{
		document.getElementById("CNYN").checked = false;
		document.getElementById("GNYN").checked = false;
		
		document.getElementById("CNYN").disabled = true;
		document.getElementById("GNYN").disabled = true;
		document.getElementById("SRNYN").disabled = false;
		document.getElementById("RNYN").disabled = false;

	}else if(networkType=='DRN')
	{
		document.getElementById("CNYN").checked = false;
		document.getElementById("GNYN").checked = false;
		document.getElementById("SRNYN").checked = false;
		
		document.getElementById("CNYN").disabled = true;
		document.getElementById("GNYN").disabled = true;
		document.getElementById("SRNYN").disabled = true;
		document.getElementById("RNYN").disabled = false;
	}

	
}

function onUploadDocs()
{
	document.forms[1].mode.value="doDefault";
	var hosp_seq_id	=	document.forms[1].hospSeqId.value;
	document.forms[1].action="/UploadMOUCertificatesList.do?hosp_seq_id="+hosp_seq_id;
	document.forms[1].submit();
}

function getLicenceNumbers(obj)
{
	var provName	=	obj.value;
	//alert("prviderId::"+prviderId);
	if (window.ActiveXObject){
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		if (xmlhttp){
			xmlhttp.open("GET",'/HospitalSearchAction.do?mode=getLicenceNoForPreEmp&provName=' +provName ,true);
			//xmlhttp.open("GET",'/AsynchronousAction.do?mode=getCommonMethod&getType=LicenceNoForPreEmp&id=' +prviderId ,true);
			xmlhttp.onreadystatechange=state_ChangeProvider;
			xmlhttp.send();
		}
	}
	
}

function state_ChangeProvider(){
	var result,result_arr;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split(","); 
			document.getElementById("licenceNo").value=result_arr[0];
		}
	}
}

function onGenerate()

	{trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value="doGenerateIds";
		document.forms[1].action="/PreRequisiteHospitalAddAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
    }
}
function onSendMail()
{
	document.forms[1].mode.value="doSendMail";
	document.forms[1].action="/PreRequisiteHospitalAction.do";
	document.forms[1].submit();
}

function onProviderDashBoard()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value	="doProviderDashBoard";
		document.forms[1].action		="/ProvidereDashBoardAction.do";
		JS_SecondSubmit					=true;
		document.forms[1].submit();
    }
}

function onpreEmpanelment()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value	="doPreRequisiteNew";
		document.forms[1].action		="/PreRequisiteHospitalAction.do";
		JS_SecondSubmit					=true;
		document.forms[1].submit();
    }
}
function onBack()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value	="doPreRequisite";
		document.forms[1].action		="/PreRequisiteHospitalAction.do";
		JS_SecondSubmit					=true;
		document.forms[1].submit();
    }
}