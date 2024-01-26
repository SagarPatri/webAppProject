//javascript used in courierdetail.jsp of Support flow

function onReport()
{
	var parameterValue="|"+document.forms[1].courierSeqID.value+"|";
	var partmeter = "?mode=doGenerateReport&reportType=EXL&parameter="+parameterValue+"&fileName=generalreports/CourierDispatchDetails.jrxml&reportID=CourierDtl";
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}//end of onReport()

function getCourierName()
{
	if(document.forms[1].courierCompSeqID.value=="-1")
		document.getElementById("Others").style.display="";
	else
	{
		document.getElementById("Others").style.display="none";
		document.forms[1].otherDesc.value="";
	}
}//end of function showhideCourierName()



function getDocumentName()
{
	if(document.forms[1].rctDocType.value=="OTRS"){
		document.getElementById("DocType").style.display="";
	}
	else
	{
		document.getElementById("DocType").style.display="none";
		document.forms[1].courierDocType.value="";
	}
}//end of function showhideCourierName()
function showhideCourierType(){
	var selObj=document.forms[1].courierTypeID;
	var selVal = selObj.options[selObj.selectedIndex].value;
	if(selVal == 'RCT')
	{
		document.getElementById("Recipt").style.display="";
		document.getElementById("Recipt1").style.display="";
		document.getElementById("Recipt2").style.display="";
		document.getElementById("senderDetails").style.display="";
		//document.getElementById("branch").style.display="";
		document.getElementById("senbranch").style.display="none";
		document.getElementById("Dispatch").style.display="none";
		document.getElementById("Dispatch1").style.display="none";
		document.getElementById("Dispatch2").style.display="none";
		//document.getElementById("dispatchDetails").style.display="none";
	}
	else
	{
		document.getElementById("Recipt").style.display="none";
		document.getElementById("Recipt1").style.display="none";
		document.getElementById("Recipt2").style.display="none";
		document.getElementById("senderDetails").style.display="none";
		//document.getElementById("branch").style.display="none";
		document.getElementById("senbranch").style.display="";
		document.getElementById("Dispatch").style.display="";
		document.getElementById("Dispatch1").style.display="";
		document.getElementById("Dispatch2").style.display="";
	//	document.getElementById("dispatchDetails").style.display="";
	}
}// end of function showhideCourierType()

function showhideContentType()
{
	var selObj=document.forms[1].contentTypeID;
	var selVal = selObj.options[selObj.selectedIndex].value;
	if(selVal == 'HOP')
	{
		document.getElementById("Hospital").style.display="";
		document.getElementById('deldate').style.display = "";
	}
	else
	{
		document.getElementById("Hospital").style.display="none";
		document.getElementById('deldate').style.display = "none";
		document.forms[1].deliveryDate.value="";
		document.forms[1].deliveryTime.value="";
		document.forms[1].proofDeliveryNbr.value="";
	}
	if(selVal == 'ECD')
	{
		document.getElementById("Enrollment").style.display="";
	}
	else
	{
		document.getElementById("Enrollment").style.display="none";
	}

	if(document.forms[1].courierSeqID.value!="")//for enabling and dis-abling the excell down load button
	{
		if(selVal=='ECD')
		{
			document.getElementById("courier").style.display="";
		}//end of if(selVal=='ECD')
		else
		{
			document.getElementById("courier").style.display="none";
		}//end of else if(selVal=='ECD')
	}//end of if(document.forms[1].courierSeqID.value!="")
}// end of function showhideContentType(selObj)

function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
	{
	    document.forms[1].mode.value ="doSave";
	    document.forms[1].action = "/UpdateCourierDetailAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSave()

function onClose()
{
	if(!TrackChanges()) return false;
    document.forms[1].mode.value ="doClose";
    document.forms[1].tab.value ="Search";
    document.forms[1].action = "/CourierDetailAction.do";
    document.forms[1].submit();
}//end of onClose()

function selectHospital()
{
	 document.forms[1].mode.value="doSelectHospital";
	 document.forms[1].action="/CourierDetailAction.do";
	 document.forms[1].submit();
}//end of selectHospital()

function onCardBatch()
{
	document.forms[1].mode.value ="doSelectBatch";
    document.forms[1].action = "/CourierDetailAction.do";
    document.forms[1].submit();
}
function onReset()
{
	showhideCourierType();
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
	document.forms[1].mode.value="doReset";
    document.forms[1].action="/CourierDetailAction.do";
	document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
		showhideContentType();
		showhideCourierType();
		getCourierName();
	}//end of else
}//end of onReset
/*function onAdd(obj)
{
	var paramType =obj;
	alert("paramType::::::::::"+paramType);
    document.forms[1].reset();
    document.forms[1].paramType.value = paramType;
    document.forms[1].rownum.value = "";
 //   document.forms[1].tab.value ="General";
 
    document.forms[1].mode.value = "doAdd";
    alert("paramType::::::::::"+document.forms[1].mode.value);
    document.forms[1].action = "/CourierDetailAction.do";
    document.forms[1].submit();
}//end of onAdd()*/