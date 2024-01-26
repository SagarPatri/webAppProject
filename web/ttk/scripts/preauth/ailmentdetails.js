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
       
		if(document.forms[1].cancerMedicalReview.checked)
    	{
    		document.forms[1].cancerMedicalReview.value = 'Y';
    	}
		else
		{
			document.forms[1].cancerMedicalReview.value = 'N';
		}
		
       document.forms[1].investReports.value= document.forms[1].preInvestReports.value;
	   document.forms[1].action = "/SubmitAilmentDetailsAction.do";
    }
    else if(document.forms[0].leftlink.value == "Claims")
    {
    	if(document.forms[1].cancerMedicalReview.checked)
    	{
    		document.forms[1].cancerMedicalReview.value = 'Y';
    	}
    	else
		{
			document.forms[1].cancerMedicalReview.value = 'N';
		}
    	
    	document.forms[1].action = "/SubmitClaimAilmentDetailsAction.do";
    	//alert('IN Claims '+document.forms[1].clmInvestReports.value);
    	document.forms[1].investReports.value= document.forms[1].clmInvestReports.value;
    
		//physiotherapy cr 1320
		if(document.forms[1].physiotherapyYN.checked)
	    {
	    	document.forms[1].physiotherapyChargeYN.value='Y';
	    }//end of if(document.forms[1].previousIllYN.checked)
	    else
	    {
	   		document.forms[1].physiotherapyChargeYN.value='N';
	    }//end of else
		//physiotherapy cr 1320
		//physiotherapy cr 1307
		if(document.getElementById("nuOfVisits").value=="0")
		{
			alert("Please Enter No. of Visits should be greater than 0");
			document.getElementById("nuOfVisits").focus();
			return false;
		}//end of if(document.forms[1].nuOfVisits.value=="")
		//physiotherapy cr 1307
    	
     document.forms[1].action = "/SubmitClaimAilmentDetailsAction.do";
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
		document.forms[1].action = "/SaveDiagnosisClaimsAilmentDetailsAction.do";
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
		var msg = confirm("Are you sure you want to delete the selected record?");
	    if(msg)
	    {
	    	document.forms[1].mode.value="doDelete";
	    	document.forms[1].rownum.value=rownum;
	      	document.forms[1].action="/AilmentDetailsAction.do";
	    	document.forms[1].submit();
	    }//end of if(msg)	
	
	
}//end of onDeleteIcon(rownum)
//added for KOC-Decoupling
function edit(rownum)
{
	document.forms[1].mode.value="doViewDiagnosis";
	document.forms[1].rownum.value=rownum;
	document.forms[1].action="/AilmentDetailsAction.do";
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
function onClose()
{
	if(!TrackChanges()) return false;
    document.forms[1].mode.value="doDefault";
    document.forms[1].child.value="";
    document.forms[1].action="/MedicalDetailsAction.do";
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
	if(document.forms[0].leftlink.value == "Claims")
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
//physiotherapy cr 1320
function showhidePhysioApply()
{
  	  if(document.forms[1].physiotherapyYN.checked)
	  {
	    document.getElementById("PHYS").style.display="";
	  }//(document.forms[1].physiotherapyYN.checked)
	  else
	  {
	    document.getElementById("PHYS").style.display="none";
		document.forms[1].physioApplicableID.value="";
	  }//end of else
}//end of showhidePhysioApply(selObj)
//physiotherapy cr 1320
//physiotherapy cr 1307
function showNoofVisits()
{
	var claimSubTypeID= document.forms[1].claimSubTypeID.value;
	var medicineSystemTypeID= document.forms[1].medicineSystemTypeID.value;
	if(claimSubTypeID=='OPD')
	{
		if(medicineSystemTypeID=='SAH' || medicineSystemTypeID=='ACU' || medicineSystemTypeID=='CHI' || medicineSystemTypeID=='OST')
		{
			 document.getElementById("visits").style.display="";
		     document.getElementById("nuOfVisits").value="";
		}//end of if(medicineSystemTypeID=='SAH')
		else
		{
		     document.getElementById("visits").style.display="none";
		     document.getElementById("nuOfVisits").value="";
		}//end of else
	}
	else
	{
		document.getElementById("visits").style.display="none";
		document.getElementById("nuOfVisits").value="";
	}
}//end of showNoofVisits()
//physiotherapy cr 1307