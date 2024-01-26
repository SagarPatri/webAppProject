

function closeOldLicense()
{
	document.forms[1].mode.value="closeOldLicense";
	document.forms[1].action="/HospitalOldLicenseAction.do";
	document.forms[1].submit();
}


function onLicenseUpdateSave()
{
	
	var prodiverName = document.getElementById("hospitalName").value;
	var irdaNumber = document.getElementById("irdaNumber").value;
	var sStartDate = document.getElementById("sStartDate").value;
	//var sEndDate = document.getElementById("sEndDate").value;
	
	if(prodiverName=="" || prodiverName==null){
		alert("Please Select Provider Name");
		document.forms[1].hospitalName.focus();
			return false;
	}
	else if(irdaNumber=="" || irdaNumber==null){
		alert("Please Select Health Authority License");
		document.forms[1].irdaNumber.focus();
			return false;
	}
	else if(sStartDate=="" || sStartDate==null){
		alert("Please Select License Effective Date");
		document.forms[1].sStartDate.focus();
			return false;
	}
	/*else if(sEndDate=="" || sEndDate==null){
		alert("Please Select License Expiry Date");
		document.forms[1].sEndDate.focus();
			return false;
	}
	if(compareDates("sStartDate","License Issue Date","sEndDate","License Expiry Date","greater than")==false)
	{
		document.forms[1].sEndDate.value="";
	    return false;
	}
	if(!isDate(document.forms[1].sStartDate,"License Issue Date"))
   	{
   		document.forms[1].sStartDate.focus();
   		return false;
   	}
   	if(!isDate(document.forms[1].sEndDate,"License Expiry Date"))
   	{
   		document.forms[1].sEndDate.focus();
   		return false;
   	}
    if(!compareDates('sStartDate','License Issue Date','sEndDate','License Expiry Date','greater than'))
    {
    	return false;
    }
	*/
	
	
	else{
		
		document.forms[1].mode.value="onLicenseUpdateSave";
		document.forms[1].action="/HospitalOldLicenseAction.do";
		document.forms[1].submit();
		
	}
	
	
}

function edit(rownum)
{
    document.forms[1].reset();
    document.forms[1].mode.value="editOldLicenseUpdate";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="General";
    document.forms[1].action = "/EditHospitalSearchAction.do";
    document.forms[1].submit();
}


function getProviderDetails(obj)
{
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

function onLicenseUpdateReset()
{
	
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
	   	document.forms[1].action="/HospitalOldLicenseAction.do";
	   	document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
		stopPreAuthClaim();
		//setnetworkTypes();
	}//end of else

}

function stopPreAuthClaim()
{
	if(document.forms[1].stopPreAuth.value=="Y")
	    document.forms[1].stopPreAuthsYN.checked=true;
	if(document.forms[1].stopClaim.value=="Y")
		document.forms[1].stopClaimsYN.checked=true;
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




































