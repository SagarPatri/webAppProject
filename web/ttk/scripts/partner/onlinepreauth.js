//as per intX 
var xhttp	=null;
function edit(rownum)
{
	 //document.forms[1].leftlink.value ="Claims";
	document.forms[1].tab.value ="Details";
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;   
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onBack(){
	if(!JS_SecondSubmit){
		var fromFlag	=	document.forms[1].fromFlag.value;
		//alert("fromFlag::"+fromFlag);
		if("preAuth"==fromFlag){
			document.forms[1].mode.value="doDefaultPreAuth";
			document.forms[1].action = "/OnlinePartnerPreAuthAction.do";   
		}
		else if("preAuthEnhance"==fromFlag){
			document.forms[1].mode.value="doSearchPreAuth";
			document.forms[1].action = "/PartnerSearchPreAuthLogsAction.do";   
		}
		else{
		document.forms[1].mode.value="doValidate";
	    document.forms[1].action = "/OnlineCashlessPartBackAction.do";   
		} 
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}
}


function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onSearch()
{
   if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	document.forms[1].mode.value = "doSearch";
	document.forms[1].action = "/OnlineClmSearchHospAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()


function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/OnlineClmSearchHospAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()

function onValidateVidalId()
{
	document.forms[1].mode.value = "doValidateEnrollId";
	document.forms[1].action = "/OnlinePreAuthEnrollValidate.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
}//end of onValidateVidalId()

function onUserSubmit()
{
	document.forms[1].tab.value ="Pre-Authorization";
	document.forms[1].sublink.value ="Pre-Authorization";
	document.forms[1].leftlink.value ="Approval Claims";
	document.forms[1].mode.value = "doSaveGeneral";
	document.forms[1].action = "/OnlinePreAuthAction.do";
	document.forms[1].submit();
}

function onAddDiags(obj)
{
	if(document.forms[1].icdCode.value=="")
	{
		alert("Please Enter Diagnosys Code First");
		document.forms[1].icdCode.focus();
		return false;
	}else if(document.forms[1].icdDesc.value=="")
	{
		alert("Please Enter Diagnosys Description");
		document.forms[1].icdDesc.focus();
		return false;
	}
	document.forms[1].mode.value = "doSaveDiags";
	document.forms[1].action = "/OnlinePreAuthAction.do?focusId="+obj;
	document.forms[1].submit();
}


function deleteDiagnosisDetails(rownum){
 if(confirm("Are You Sure You Want To Delete Diagnosis Details!")){
if(!JS_SecondSubmit){	
	document.forms[1].rownum.value=rownum;
   document.forms[1].mode.value="deleteDiagnosisDetails";
   document.forms[1].action="/OnlinePreAuthAction.do";	
   JS_SecondSubmit=true;	   
   document.forms[1].submit();
 }	
 }	
}

function deleteDrugDetails(rownum){
 if(confirm("Are You Sure You Want To Delete Drug Details!")){
if(!JS_SecondSubmit){	
	document.forms[1].rownum.value=rownum;
   document.forms[1].mode.value="deleteDrugDetails";
   document.forms[1].action="/OnlinePreAuthAction.do";	
   JS_SecondSubmit=true;	   
   document.forms[1].submit();
 }	
 }	
}

function addDiagnosisDetails(){
	if(!JS_SecondSubmit){
	document.forms[1].mode.value="doAddDiagnosisDetails";
    document.forms[1].action = "/OnlinePreAuthAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}

function addActivityDetails(){
	if(!JS_SecondSubmit){
	document.forms[1].mode.value="doAddActivityDetails";
    document.forms[1].action = "/OnlinePreAuthAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}

function addDrugDetails(){
	if(!JS_SecondSubmit){
	document.forms[1].mode.value="doAddDrugDetails";
    document.forms[1].action = "/OnlinePreAuthAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}

function onSaveActivities(obj){
	if(document.forms[1].activityCode.value=="")
	{
		alert("Please Enter Activity Code First");
		document.forms[1].activityCode.focus();
		return false;
	}else if(document.forms[1].activityCodeDesc.value=="")
	{
		alert("Please Enter Activity Description");
		document.forms[1].activityCodeDesc.focus();
		return false;
	}else if(document.forms[1].activityQuantity.value=="")
	{
		alert("Please Enter Quantity");
		document.forms[1].activityQuantity.focus();
		return false;
	}
	
	if(!JS_SecondSubmit){
	document.forms[1].mode.value="doSaveActivities";
    document.forms[1].action = "/OnlinePreAuthAction.do?focusId="+obj;
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}
function onSaveDrugs(){
	if(!JS_SecondSubmit){
		if(document.forms[1].drugCode.value=="")
		{
			alert("Please Enter Drug Code First");
			document.forms[1].drugCode.focus();
			return false;
		}else if(document.forms[1].drugDesc.value=="")
		{
			alert("Please Enter Drug Description");
			document.forms[1].drugDesc.focus();
			return false;
		}else if(document.forms[1].drugdays.value=="")
		{
			alert("Please Enter Days");
			document.forms[1].drugdays.focus();
			return false;
		}else if(document.forms[1].drugUnit.value=="")
		{
			alert("Please Enter Drug Unit");
			document.forms[1].drugUnit.focus();
			return false;
		}else if(document.forms[1].drugquantity.value=="")
		{
			alert("Please Enter Drug Quantity");
			document.forms[1].drugquantity.focus();
			return false;
		}
		
	document.forms[1].mode.value="doSaveDrugs";
    document.forms[1].action = "/OnlinePreAuthAction.do?focusId=drugDesc";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}
function deleteActivityDetails(rownum){
	 if(confirm("Are You Sure You Want To Delete Activity Details!")){
	if(!JS_SecondSubmit){	
		document.forms[1].rownum.value=rownum;
	   document.forms[1].mode.value="deleteActivityDetails";
	   document.forms[1].action="/OnlinePreAuthAction.do";	
	   JS_SecondSubmit=true;	   
	   document.forms[1].submit();
	 }	
	 }	
	}
function onSaveOnlinePreAuth(){
	if(!JS_SecondSubmit){
	document.forms[1].mode.value="doSaveOnlinePreAuth";
    document.forms[1].action = "/OnlinePreAuthAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}
function onSavePartialPreAuth(){
	if(!JS_SecondSubmit){
		
	document.forms[1].mode.value="doSavePartialPreAuth";
    document.forms[1].action = "/OnlinePartnerPreAuthProceedAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}
function onSavePreAuthEnhance(){
	if(!JS_SecondSubmit){
	document.forms[1].mode.value="doSavePartialPreAuth";
    document.forms[1].action = "/OnlinePartnerPreAuthEnhanceAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}


function onSubmitOnlinePreAuth(){
	if(!JS_SecondSubmit){
		document.forms[1].mode.value="doSubmitOnlinePreAuth";
	    document.forms[1].action = "/OnlinePreAuthAction.do";    
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}
}

function calcQtyOnPosology()
{
	//var	posology	=	document.forms[1].posology.value;
	var	days		=	document.forms[1].drugdays.value;
	var	drugUnit	=	document.forms[1].drugUnit.value;
	var	qty			=	"";
	if(days!='' && !isNaN(days))
		qty	=	parseInt(days);//parseInt(posology)*parseInt(days);//Removed Posology calculation as per QATAR
	var	gran		=	document.forms[1].gran.value;
	if("PKG"==drugUnit)
		result	=	Math.ceil(qty/gran);
	else
		result	=	qty;
	document.forms[1].drugquantity.value	=	result;
}

function onEditOnlinePreAuth(){
	if(!JS_SecondSubmit){
		document.forms[1].mode.value="doEditOnlinePreAuth";
	    document.forms[1].action = "/OnlinePreAuthAction.do";    
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}
}

function onExit()
{
	document.forms[1].tab.value ="Check Eligibility";
	document.forms[1].sublink.value ="Check Eligibility";
	document.forms[1].leftlink.value ="Partner Approval Claims";
	document.forms[1].mode.value="doExit";
	document.forms[1].action="/OnlinePartnerPreAuthProceedAction.do";
	document.forms[1].submit();
}

function selectClinician(){
  document.forms[1].mode.value="doDefaultClinician";
  document.forms[1].action="/OnlinePreAuthClinicianAction.do";	
  document.forms[1].submit();	
}
var req = "";
function validateClinician(obj){
	if(obj.value!=""){
		if (window.XMLHttpRequest)
		{ // Non-IE browsers
			req = new XMLHttpRequest();
			req.onreadystatechange = state_ClinicianDetails;
		    try {
		    	req.open("POST","/OnlinePreAuthClinicianAction.do?mode=doValidateClinician&clinicianId="+obj.value,true);
			} catch (e)
			{
				alert(e);
			}
			req.send(null);
		} 
		else if (window.ActiveXObject)
		{ // IE
			req = new ActiveXObject("Microsoft.XMLHTTP");
		    if (req)
		    {
		    	req.onreadystatechange = state_ClinicianDetails;
		        req.open("GET", "/OnlinePreAuthClinicianAction.do?mode=doValidateClinician&clinicianId="+obj.value,true);
		        req.send(null);
			}
		}
	/*if (window.ActiveXObject){
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		if (xmlhttp){
			xmlhttp.open("GET",'/OnlinePreAuthClinicianAction.do?mode=doValidateClinician&clinicianId='+obj.value,true);
			xmlhttp.onreadystatechange=state_ClinicianDetails;
			xmlhttp.send();
		}
	}*/
	 /* document.forms[1].mode.value="doValidateClinician";
	  document.forms[1].action="/OnlinePreAuthClinicianAction.do";	
	  document.forms[1].submit();	*/
	}
}
function state_ClinicianDetails(){
	var result,result_arr;
	if (req.readyState == 4){
		//alert(xmlhttp.status);
	if (req.status == 200){
				result = req.responseText;
			result_arr = result.split(","); 
			if(result_arr[0]!=""){
				document.forms[1].clinicianName.value=result_arr[1];
				document.forms[1].speciality.value=result_arr[2];
				document.forms[1].consultation.value=result_arr[3];
			}
			else{
				alert("Treating Doctor is not associated with the Provider and we will not process the Preapproval " +
						"request until the doctor is associated. Please send the details of Treating doctor to " +
						document.forms[1].hospMailId.value+".");
				document.forms[1].clinicianName.value="";
				document.forms[1].speciality.value="";
				document.forms[1].consultation.value="";
			}
			
		}
	}
}

function callFocusObj(){
	//var focusObj	=	document.forms[1].focusObj.value;
	//if(focusObj.value!=""){
		//document.getElementById("drugUnit").scrollIntoView(true);
		//document.getElementById("drugUnit").focus();
		//document.forms[1].focusObj.scrollIntoView(true);
		//document.getElementById(focusObj).focus();
		//window.scrollTo(0,1000);
	//}
}

function showUplodedFile(rownum)
{
	
	var openPage = "/OnlineReportsAction.do?mode=doViewCommonForAll&module=preAuthorizationUploadedFile&rownum="+rownum;
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}

function onUploadDocs()
{
	var fromFlag	=	document.forms[1].fromFlag.value;
	document.forms[1].mode.value="doDefaultUploadDocs";
	var lPreAuthIntSeqId	=	document.forms[1].lPreAuthIntSeqId.value;
	document.forms[1].action="/UploadMOUCertificatesList.do?lPreAuthIntSeqId="+lPreAuthIntSeqId+"&preAuthNoYN="+document.forms[1].preAuthNoYN.value+"&fromFlag="+fromFlag;
	document.forms[1].submit();
}


////Diag Code
function onDiagCodeSearch(searchMode) {
	  document.getElementById("icdDesc").value="";
	  onSearchData(searchMode,'onDiagCodeSearch','icdCode','icdDivID','/ProviderAsynchronousSearch.do?mode=doDiagDetailsSearch&icdMode=CODE','onSelectIcdCode');
}

function onSelectIcdCode(param1){
	  var path="/ProviderAsynchronousSearch.do?mode=getIcdDetails&icdMode=DESC&icdSeqID="+param1;
		 var varIcdCodeDec= getSearchData(path);
		 document.getElementById("diagCodeSeqID").value=param1;
		 document.getElementById("icdDesc").value=varIcdCodeDec; 
	  }


//Diag Desc
function onDiagDescSearch(searchMode) {
	  document.getElementById("icdCode").value="";
	  onSearchData(searchMode,'onDiagDescSearch','icdDesc','icdDescDivID','/ProviderAsynchronousSearch.do?mode=doDiagDetailsSearch&icdMode=DESC','onSelectIcdDesc');
}

function onSelectIcdDesc(param1){
	  var path="/ProviderAsynchronousSearch.do?mode=getIcdDetails&icdMode=CODE&icdSeqID="+param1;
		 var varIcdCodeDec= getSearchData(path);
		 document.getElementById("diagCodeSeqID").value=param1;
		 document.getElementById("icdCode").value=varIcdCodeDec; 
	  }


//Activity COde
function onActivityCodeSearch(searchMode) {
	  document.getElementById("activityCodeDesc").value="";
	  onSearchData(searchMode,'onActivityCodeSearch','activityCode','activityCodeDivID','/ProviderAsynchronousSearch.do?mode=doActivityCodeSearch&icdMode=CODE','onSelectActivityCode');
}

function onSelectActivityCode(param1){
	//alert("param1::"+param1);
  var path="/ProviderAsynchronousSearch.do?mode=getIcdDetails&searchFlag=getActDesc&icdMode=DESC&icdSeqID="+param1;
	// var varIcdCodeDec= getSearchData(path);
	 var strArr	=	param1.split("--");
	 var toothYN	=	"";
	 document.getElementById("activityCodeDesc").value=strArr[0]; 
	 document.getElementById("internalCode").value=strArr[1]; 
	 toothYN	=	strArr[2]; 
	 if('Y'===toothYN)	{
		 document.getElementById("toothReqYN").style.display="inline";
	 }
	 else{
		 document.getElementById("toothReqYN").style.display="none";
		 document.getElementById("toothNo").value="";
	 }
  }
//Activity Desc
function onActivityDescSearch(searchMode) {
	  document.getElementById("activityCode").value="";
	  onSearchData(searchMode,'onActivityDescSearch','activityCodeDesc','activityDescDivID','/ProviderAsynchronousSearch.do?mode=doActivityCodeSearch&icdMode=DESC','onSelectActivityDesc');
}

function onSelectActivityDesc(param1){
	  var path="/ProviderAsynchronousSearch.do?mode=getIcdDetails&searchFlag=getActCode&icdMode=CODE&icdSeqID="+param1;
		 //var varIcdCodeDec= getSearchData(path);
		 var strArr	=	param1.split("--");
		 var toothYN	=	"";
		 //document.getElementById("diagCodeSeqID").value=param1;
		 document.getElementById("activityCode").value=strArr[0];
		 document.getElementById("internalCode").value=strArr[1]; 
		 toothYN	=	strArr[2]; 
		 alert("toothYN::"+toothYN);
		 if('Y'===toothYN)	{
			 document.getElementById("toothReqYN").style.display="inline";
		 }
		 else{
			 document.getElementById("toothReqYN").style.display="none";
			 document.getElementById("toothNo").value="";
		 }
	  }
//Internal COde
function onInternalCodeSearch(searchMode) {
	  document.getElementById("activityCode").value="";
	  document.getElementById("activityCodeDesc").value="";
	  var hospSeqID	=	document.getElementById("hospSeqID").value;
	  onSearchData(searchMode,'onInternalCodeSearch','internalCode','internalCodeDivID','/ProviderAsynchronousSearch.do?mode=doActivityCodeSearch&icdMode=INTERNALCODE&hospSeqId='+hospSeqID,'onSelectInternalCode');
}

function onSelectInternalCode(param1){
	  //var path="/ProviderAsynchronousSearch.do?mode=getIcdDetails&searchFlag=getInternalDesc&icdMode=DESC&icdSeqID="+param1;
		 var strArr	=	param1.split("--");
		 document.getElementById("activityCode").value=strArr[0];
		 document.getElementById("activityCodeDesc").value=strArr[1]; 
	  }
//-----------------------------

//Drug COde
function onDrugCodeSearch(searchMode) {
	  document.getElementById("drugDesc").value="";
	  onSearchData(searchMode,'onDrugCodeSearch','drugCode','drugCodeDivID','/ProviderAsynchronousSearch.do?mode=doDrugCodeSearch&icdMode=CODE','onSelectDrugCode');
}

function onSelectDrugCode(param1){
	  var path="/ProviderAsynchronousSearch.do?mode=getIcdDetails&searchFlag=getDrugDesc&icdMode=DESC&icdSeqID="+param1;
		 var varIcdCodeDec= getSearchData(path);
		 document.getElementById("drugDesc").value=varIcdCodeDec; 
	  }
//Activity Desc
function onDrugDescSearch(searchMode) {
	  document.getElementById("drugCode").value="";
	  onSearchData(searchMode,'onDrugDescSearch','drugDesc','drugDescDivID','/ProviderAsynchronousSearch.do?mode=doDrugCodeSearch&icdMode=DESC','onSelectDrugDesc');
}

function onSelectDrugDesc(param1){
	  var path="/ProviderAsynchronousSearch.do?mode=getIcdDetails&searchFlag=getDrugCode&icdMode=CODE&icdSeqID="+param1;
		 var varIcdCodeDec= getSearchData(path);
		 //document.getElementById("diagCodeSeqID").value=param1;
		 document.getElementById("drugCode").value=varIcdCodeDec; 
	  }

//----------------------------
//Clicnician Search
function onClinicianSearch(searchMode) {
	//document.getElementById("clinicianName").value="";
	  onSearchData(searchMode,'onClinicianSearch','clinicianId','clinicianDivID','/ProviderAsynchronousSearch.do?mode=doClinicianSearch','onSelectClinicianID');
}
function onSelectClinicianID(param1) {
	var strArr	=	param1.split("--");
	  document.getElementById("clinicianName").value=strArr[0];
	  document.forms[1].speciality.value=strArr[1];
}

function onClinicianNameSearch(searchMode) {
	  //document.getElementById("clinicianId").value="";
	  onSearchData(searchMode,'onClinicianNameSearch','clinicianName','clinicianNameDivID','/ProviderAsynchronousSearch.do?mode=doClinicianNameSearch','onSelectClinicianName');
}
function onSelectClinicianName(param1) {
	var strArr	=	param1.split("--");
	  document.getElementById("clinicianId").value=strArr[0];
	  document.forms[1].speciality.value=strArr[1];
}

function onViewShortfall(obj)
{
	document.forms[1].mode.value="doViewShortfall";
   	document.forms[1].action="/ViewPartnerAuthorizationShortfallDetails.do?shortfallSeqId="+obj;
   	document.forms[1].submit();
}
function changeTreatment(obj,obj1){
	  if((obj.value==='1' || obj.value==='3') && (document.getElementById("benefitTemp").value=="DNTL" || document.getElementById("benefitTemp").value=="Dental"))
		  document.getElementById("orthoDiv").style.display="inline";
	  else
		  document.getElementById("orthoDiv").style.display="none";
	  
	  if(obj1=='Y')	{
			 document.getElementById("toothReqYN").style.display="inline";
		 }
		 else{
			 document.getElementById("toothReqYN").style.display="none";
			 document.getElementById("toothNo").value="";
		 }
	  
}

function calcRoomRent(){
	if(!isNaN(document.getElementById("roomRent").value) && !isNaN(document.getElementById("roomRentDays").value)
			&& document.getElementById("roomRent").value!='' && document.getElementById("roomRentDays").value!='')
		document.getElementById("roomRentCharges").value	=	parseFloat(document.getElementById("roomRent").value)*parseFloat(document.getElementById("roomRentDays").value);
}

function calcIcuRent(){
	if(!isNaN(document.getElementById("icuRent").value) && !isNaN(document.getElementById("icuRentDays").value)
			&& document.getElementById("icuRent").value!='' && document.getElementById("icuRentDays").value!='')
		document.getElementById("icuCharges").value	=	parseFloat(document.getElementById("icuRent").value)*parseFloat(document.getElementById("icuRentDays").value);
}