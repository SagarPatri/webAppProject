//java script for the Investigation screen in the preauth and support of Preauthorization flow.
//for Medical Opinion Sheet Report in Claims 

function showhideDisability(selObj)
{
	 var selVal = selObj.options[selObj.selectedIndex].value;
	 if(selVal=='TRA')
	    {
	    	 document.getElementById("disablity").style.display="";
	    }//end of if(selVal=='TRA')
	    else
	    {
	    	document.getElementById("disablity").style.display="none";
	    }//end of else	
}//end of showhideDisability(selObj)

//on Click of reset button
function onReset()
{
   	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
    	document.forms[1].action = "/AilmentDetailsAction.do";
    	document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
		var selVal = document.forms[1].specialityTypeID.value;
	 	if(selVal=='TRA')
	    {
	    	 document.getElementById("disablity").style.display="";
	    }//end of if(selVal=='TRA')
	    else
	    {
	    	document.getElementById("disablity").style.display="none";
	    }//end of else
	}//end of else
}//end of onReset()

function computeDischargeDate(duration)
{
	trimForm(document.forms[1]);
	if(duration.value !='' && document.forms[1].admissionDate.value !='')
	{
		if(isNaN(duration.value))	//if duration is not in days clear the discharge date
		{
			document.forms[1].probableDischargeDate.value="";
			return false;
		}//end of if(isNaN(duration.value))

		var dateOfAdmission = document.forms[1].admissionDate.value;
		var dateArray = dateOfAdmission.split("/");

		var passedDay = dateArray[0];
		var passedMonth = dateArray[1];
		var passedYear = dateArray[2];

		var  dateStr = passedMonth+"/"+passedDay +"/"+passedYear;
		var myDate=new Date(dateStr);
		myDate.setDate(myDate.getDate() + eval(duration.value));

		var newDt=myDate.getDate();
		var newMon=myDate.getMonth()+1;
		var newYr=myDate.getFullYear();

		if((newDt.toString()).length==1)
			newDt = "0"+newDt;

		if((newMon.toString()).length==1)
			newMon = "0"+newMon;

		document.forms[1].probableDischargeDate.value=newDt+"/"+newMon+"/"+newYr;
	}
	else
	{
		//clear the discharge date if admission date/duarion of Hospitaliztion not availble
		document.forms[1].probableDischargeDate.value="";
	}//end of else
}//end of computeDischargeDate(duration)

//on Click of save button
function onSave()
{
	trimForm(document.forms[1]);
	if(document.forms[0].leftlink.value == "Pre-Authorization")
	{
		if(document.forms[1].previousIllYN.checked)
	    {
	    	document.forms[1].previousIllnessYN.value='Y';
	    }//end of if(document.forms[1].previousIllYN.checked)
	    else
	    {
	   		document.forms[1].previousIllnessYN.value='N';
	    }//end of else
	   //alert('IN Pre-Authorization '+document.forms[1].preInvestReports.value);
	   document.forms[1].investReports.value= document.forms[1].preInvestReports.value;
	   document.forms[1].action = "/SubmitAilmentDetailsAction.do";
    }
    else if((document.forms[0].leftlink.value == "DataEntryClaims")||(document.forms[0].leftlink.value == "DataEntryClaims"))
    {
     document.forms[1].action = "/DataEntrySubmitClaimAilmentDetailsAction.do";
     //alert('IN Claims '+document.forms[1].clmInvestReports.value);
     document.forms[1].investReports.value= document.forms[1].clmInvestReports.value;
	}
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSubmit";
		JS_SecondSubmit=true
    	document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSave()

//on click of SaveDiagnosis
function onSaveDiagnosis()
{
	if(document.forms[1].elements.diagnosisDesc.value=="")
	{
		alert("Please add the Diagnosis");
		return false;
	}
	var len = document.forms[1].elements.diagnosisDesc.value.length;
	if(len>100)
	{
		alert("Diagnosis field should not contain more than 100 characters");
		return false;
	}
	if(document.forms[1].elements.diagnosisType.value=="")
	{
		alert("Please select the AilmentType");
		return false;
	}   
	if(document.forms[1].elements.diagnosisType.value=="PRE" && document.forms[1].elements.diagHospGenTypeId.value=="")
	{
		alert("Please select the Hospitalization Type");
		return false;
	}   
	if(document.forms[1].elements.diagHospGenTypeId.value=="REP")
	{
		if(isNaN(document.forms[1].elements.freqOfVisit.value)==true)
		{
			alert("Frequency of visit schould be always number");
			document.forms[1].elements.freqOfVisit.focus();
			return false;
		}
		if(document.forms[1].elements.freqOfVisit.value=="")
		{
			alert("Please add Frequency of visit details");
			document.forms[1].elements.freqOfVisit.focus();
			return false;
		}
		if(isNaN(document.forms[1].elements.noOfVisits.value)==true)
		{
			alert("No of visit schould be always number");
			document.forms[1].elements.noOfVisits.focus();
			return false;
		}
		if(document.forms[1].elements.noOfVisits.value=="")
		{
			alert("Please add No of visit details");
			document.forms[1].elements.noOfVisits.focus();
			return false;
		}
	}
	if(document.forms[1].elements.diagnosisType.value=="PRE" && document.forms[1].elements.diagTreatmentPlanGenTypeId.value=="")
	{
		alert("Please select the Treatement Plan");
		return false;
	}	
	else
	{
		document.forms[1].mode.value="doSaveDiagnosis";
		document.forms[1].action = "/DataEntrySubmitClaimAilmentDetailsAction.do";
		document.forms[1].submit();	
	}
}

//added for KOC-Decoupling
function showhideHospType()
{
  if(document.getElementById("diagHospGenTypeId")){
	  var selVal=document.forms[1].elements.diagHospGenTypeId.value;
	  if(selVal == 'REP')
	  {
	    document.getElementById("FV").style.display="";
	    document.getElementById("NV").style.display="";
	  }//end of if(selVal == 'REP')
	  else
	  {
	    document.getElementById("FV").style.display="none";
	    document.getElementById("NV").style.display="none";
	  }//end of else
  }
}//end of showhideHospType(selObj)

//added for KOC-Decoupling
function onDeleteIcon(rownum)
{
	var medCompleteYN = document.forms[1].medCompleteYN.value;
	if(medCompleteYN!='Y')
	{
		var msg = confirm("Are you sure you want to delete the selected record?");
	    if(msg)
	    {
	    	document.forms[1].mode.value="doDelete";
	    	document.forms[1].rownum.value=rownum;
	      	document.forms[1].action="/DataEntryAilmentDetailsAction.do";
	    	document.forms[1].submit();
	    }//end of if(msg)	
	}
	
}//end of onDeleteIcon(rownum)

function edit(rownum)
{
	document.forms[1].mode.value="doViewDiagnosis";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action="/DataEntryAilmentDetailsAction.do";
	document.forms[1].submit();	
}

function showhideTariffType()
{
  if(document.getElementById("diagTreatmentPlanGenTypeId")){
	  var selVal=document.forms[1].elements.diagTreatmentPlanGenTypeId.value;
	  if(selVal == 'SUR')
	  {
	    document.getElementById("Tariff").style.display="";
	  }//end of if(selVal == 'SUR')
	  else
	  {
	    document.getElementById("Tariff").style.display="none";
	  }//end of else
	}
}//end of showhideTariffType(selObj)


//this change is not done.....please do it....... after selectPackage,,,
function showhideTariffItem()
{
  if(document.getElementById("tariffGenTypeId")){
	  var selVal= document.forms[1].elements['tariffGenTypeId'].value;
	  if(selVal == 'NPK')
	  {
	    //document.getElementById("procedures").style.display="";
	    document.getElementById("package").style.display="none";
	  }//end of if(selVal == 'NPK')
	  else
	  {
	    document.getElementById("package").style.display="";
	    //document.getElementById("procedures").style.display="none";
	  }//end of else
	}
}// end of showhideTariffItem(selObj)

function onSelectPackage()
{
  document.forms[1].mode.value="doViewPackage";
  document.forms[1].child.value="PackageList";
  document.forms[1].action = "/DataEntryAilmentDetailsAction.do";
  document.forms[1].submit();
}//end of onSelectPackage()


function onClose()
{
	if(!TrackChanges()) return false;
    document.forms[1].mode.value="doDefault";
    document.forms[1].child.value="";
    document.forms[1].action="/DataEntryMedicalDetailsAction.do";
    document.forms[1].submit();
}//end of onClose()

function showHideType()
{
	var specialityTypeID= document.forms[1].specialityTypeID.value;
		 if(specialityTypeID=='SUR')
	    {
	    	 document.getElementById("surgerytype").style.display="";
	    	 
	    }//end of if(specialityTypeID=='SUR')
	    else
	    {
	    	document.getElementById("surgerytype").style.display="none";
	    }//end of else	
	if(specialityTypeID=='MAS')
	 {
		 
		 document.getElementById("maternitytype").style.display="";
	     document.getElementById("lmpdate").style.display="";
		 document.getElementById("noofchildren").style.display="";
		 //added for maternity new
		 document.getElementById("noofbiological").style.display="";
		 document.getElementById("noofadopted").style.display="";
		 document.getElementById("deliveriesNo").style.display="";
		 //added for maternity new
	 }//end of if(specialityTypeID=='MAS')
	else
	 {
		 document.forms[1].elements['maternityTypeID'].value = "";
		 document.forms[1].elements['livingChildrenNumber'].value = "";
		 document.forms[1].elements['lmpDate'].value = "";
		 document.forms[1].elements['childDate'].value = "";
		 document.forms[1].elements['vaccineDate'].value = "";
		 document.getElementById("maternitytype").style.display="none";
	     document.getElementById("lmpdate").style.display="none";
	     document.getElementById("noofchildren").style.display="none";
	     //added for maternity new
	     document.getElementById("noofbiological").style.display="none";
		 document.getElementById("noofadopted").style.display="none";
		 document.getElementById("deliveriesNo").style.display="none";
		 //added for maternity new
	     document.getElementById("childDob").style.display="none";
	     document.getElementById("vaccineDob").style.display="none";
	     
	 }//end of else
}//end of showHideType()


// added for donor expenses

function stopClaim()
{
	if(document.forms[0].leftlink.value == "DataEntryClaims")
	{

		if(document.forms[1].donorExpenses.value=="Y")  
		{
			document.forms[1].donorExpYN.checked=true;
		}
		else
		{
			document.forms[1].donorExpYN.checked=false;
		}
	}
}//end of stopPreAuthClaim()


function showHideType2()

	{
    var a=document.getElementById('donorExpYN');

	 if (a.checked) {
		 document.getElementById("ttkid").style.display="";
		
	 }else{
		
		 document.getElementById("ttkid").style.display="none";
		 document.forms[1].elements['ttkNO'].value = "";
		
	 }
	}
// end added for donor expenses
//koc maternity
function showHideType1()
{
	var maternityTypeID= document.forms[1].maternityTypeID.value;
	if((maternityTypeID=='NBB'))
	    {
		 document.getElementById("childDob").style.display="";
		 document.getElementById("vaccineDob").style.display="";
	    }
	 else{
		 document.forms[1].elements['childDate'].value = "";
		 document.forms[1].elements['vaccineDate'].value = "";
		 document.getElementById("childDob").style.display="none";
		 document.getElementById("vaccineDob").style.display="none";
	 }
}//end of showHideType1()


//added for CR KOC-Decoupling
function onDataEntryPromote()  
{
	   if(!JS_SecondSubmit)
	   {
			document.forms[1].mode.value="doDataEntryPromote";
			document.forms[1].action="/DataEntryAilmentDetailsAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		} 
}

function onDataEntryRevert()  
{
	   if(!JS_SecondSubmit)
	   {
			document.forms[1].mode.value="doDataEntryRevert";
			document.forms[1].action="/DataEntryAilmentDetailsAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		} 
}
//ended