// JAva Script Function for preauthReferralList.jsp
/*function getProviderDetails(obj)
{
	var val	=	obj.value;
	document.forms[1].mode.value="getProviderDetails";
    document.forms[1].action = "/PreAuthReferralEditAction.do?licenseId="+val;
    document.forms[1].submit();
}*/

function onReset()
{
    document.forms[1].reset();    
}//end of onReset()

function onSaveGeneralLetter(){
	document.forms[1].mode.value="doReferralGeneralSave";
    document.forms[1].action = "/PreAuthReferralEditSaveAction.do";
    document.forms[1].submit();
}
function onBack(){
	document.forms[1].mode.value="doDefault";
	document.forms[1].tab.value="Search";
    document.forms[1].action = "/PreAuthReferralEditAction.do";
    document.forms[1].submit();
}

//Ajax code to get Provider details dynamically.
function getMemberDetails(obj)
{
	var val	=	obj.value;
	if (window.ActiveXObject){
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		if (xmlhttp){
			xmlhttp.open("GET",'/PreAuthReferralEditAction.do?mode=getMemberDetails&memberId=' +val ,true);
			xmlhttp.onreadystatechange=state_ChangeProvider;
			xmlhttp.send();
		}
	}
}

function state_ChangeProvider(){
	//document.getElementById("memOrPatName").innerHTML	=	'';
	var result,result_arr;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split(","); 
			if(result_arr[0]!=""){
				document.forms[1].memOrPatName.value			=	result_arr[0];
				document.forms[1].patCompName.value				=	result_arr[1];
				//document.getElementById("memOrPatName").innerHTML	=	result_arr[0];
				document.getElementById("validMember").innerHTML	=	'Valid Member';
				document.getElementById("validMember").style.color 	=	'green';
			}
			else{
				document.getElementById("validMember").innerHTML	=	'Invalid Member';
				document.getElementById("validMember").style.color 	=	'red';
				document.forms[1].memOrPatName.value="";
				document.forms[1].patCompName.value="";
				document.forms[1].memberId.value="";
			}
		}
	}
}

function onGenerateLetterUpdateStatus(){
	document.forms[1].mode.value="doGenerateReferralUpadteStatus";
   	document.forms[1].action="/PreAuthReferralEditAction.do";
   	document.forms[1].submit();	
}

function onGenerateLetter(){
	/*alert("onGenerateLetter");
	document.forms[1].mode.value="doGenerateReferral";
   	document.forms[1].action="/PreAuthReferralEditAction.do";
   	document.forms[1].submit();*/
    var openPage = "/PreAuthReferralEditAction.do?mode=doGenerateReferral";
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features); 
}

function onSend(){
	var letterGenYN	=	document.forms[1].letterGenYN.value;
	if("N"==letterGenYN){
		alert("Please Generate Letter First!!!");
		return false;
	}
	if(!JS_SecondSubmit)
	 {
	    document.forms[1].mode.value = "doSendPreAuthReferral";
	    document.forms[1].action = "/PreAuthReferralEditAction.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}
function checkSize(obj1,obj2)
{
	len	=	obj1.value.length;
	if("COT"==obj2){
		if(len>150){
			alert("Content of Area cannot be Morethan 500 Characters");
			obj1.blur();
			return false;
		}
	}else if("OTR"==obj2){
		if(len>500){
			alert("Other Messages cannot be Morethan 500 Characters");
			obj1.blur();
			return false;
		}
	}
}




//Ajax code to get Provider details dynamically.
function getProviderDetails(obj)
{
	var val	=	obj.value;
	if (window.ActiveXObject){
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		if (xmlhttp){
			xmlhttp.open("GET",'/PreAuthReferralEditAction.do?mode=getProviderDetails&licenseId=' +val ,true);
			xmlhttp.onreadystatechange=state_ChangeProvider1;
			xmlhttp.send();
		}
	}
}

function state_ChangeProvider1(){
	var result,result_arr;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split("@@"); 
			if(result_arr[0]!="" && result_arr[0]!=null && result_arr[0]!="null"){
				 document.forms[1].providerName.value	=	result_arr[0];
				 document.forms[1].address.value		=	result_arr[1];
    			 document.forms[1].contactNo.value		=	result_arr[2];
    			 document.forms[1].emailId.value		=	result_arr[3];
    			 document.forms[1].providerId.value		=	result_arr[4];
			}
			else{
				/*document.getElementById("validMember").innerHTML	=	'Invalid Member';
				document.getElementById("validMember").style.color 	=	'red';
				document.forms[1].memOrPatName.value="";
				document.forms[1].patCompName.value="";*/
			}
		}
	}
}

function onViewLetter(obj){
	document.forms[1].mode.value="doViewCommonForAll";
    document.forms[1].action = "/ReportsAction.do?mode=doViewCommonForAll&module=preAuthReferrrals&fileName="+obj;
    document.forms[1].submit();
}