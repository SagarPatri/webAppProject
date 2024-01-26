//var JS_donotFocusIDs=["relationTypeID"];
function onSave()     
{  
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
	{
		
		
		/*
		if(document.forms[1].hyperTensCoverYN.checked)
	    {
	    	document.forms[1].hyperTensCoverYN.value='Y';
	    }//end of if(document.forms[1].hyperTensCoverYN.checked)
	    else
	    {
	    	document.forms[1].hyperTensCoverYN.value='N';
	    }//end of else
		if(document.forms[1].diabetesCoverYN.checked)
	    {
	    	document.forms[1].diabetesCoverYN.value='Y';
	    }//end of if(document.forms[1].hyperTensCoverYN.checked)
	    else
	    {
	    	document.forms[1].diabetesCoverYN.value='N';
	    }//end of else
				
  if(document.getElementById("capitaionFlag").value != "1")
		{
		 var re = /^[0-9]*\.*[0-9]*$/;
		 
		 var grossPremiumAmt=document.forms[1].actualPremium.value;
		 grossPremiumAmt=(grossPremiumAmt==null||grossPremiumAmt===""||!re.test(grossPremiumAmt)||grossPremiumAmt==="0")?0:grossPremiumAmt;
		 
		 var insurerMarginAmt=document.forms[1].insurerMarginPER.value;
			 insurerMarginAmt=(insurerMarginAmt==null||insurerMarginAmt===""||!re.test(insurerMarginAmt)||insurerMarginAmt==="0")?0:insurerMarginAmt;
			 
			 var brokerMarginAmt=document.forms[1].brokerMarginPER.value;
			 brokerMarginAmt=(brokerMarginAmt==null||brokerMarginAmt===""||!re.test(brokerMarginAmt)||brokerMarginAmt==="0")?0:brokerMarginAmt;
			 
			 var tapMarginAmt=document.forms[1].tapMarginPER.value;
			 tapMarginAmt=(tapMarginAmt==null||tapMarginAmt===""||!re.test(tapMarginAmt)||tapMarginAmt==="0")?0:tapMarginAmt;
			 
			 var reInsBrkMarginAmt=document.forms[1].reInsBrkMarginPER.value;
			 reInsBrkMarginAmt=(reInsBrkMarginAmt==null||reInsBrkMarginAmt===""||!re.test(reInsBrkMarginAmt)||reInsBrkMarginAmt==="0")?0:reInsBrkMarginAmt;
			 
			 var otherMarginAmt=document.forms[1].otherMarginPER.value;
			 otherMarginAmt=(otherMarginAmt==null||otherMarginAmt===""||!re.test(otherMarginAmt)||otherMarginAmt==="0")?0:otherMarginAmt;
			 
			 // for finding all margins  total
			 var totalMarginAmt = ((parseFloat(grossPremiumAmt)*parseFloat(otherMarginAmt))/100)+((parseFloat(grossPremiumAmt)*parseFloat(reInsBrkMarginAmt))/100)+((parseFloat(grossPremiumAmt)*parseFloat(brokerMarginAmt))/100)+((parseFloat(grossPremiumAmt)*parseFloat(insurerMarginAmt))/100); 
			 
			 
			 totalMarginAmt=(totalMarginAmt==null||totalMarginAmt===""||!re.test(totalMarginAmt)||totalMarginAmt==="0")?0:totalMarginAmt;
			 
			 // for finding Net Premium 
			 var ipNetPremiumAmt = document.forms[1].ipNetPremium.value;
			 ipNetPremiumAmt=(ipNetPremiumAmt==null||ipNetPremiumAmt===""||!re.test(ipNetPremiumAmt)||ipNetPremiumAmt==="0")?0:ipNetPremiumAmt;
			 var totalMarginAmt1=Number(totalMarginAmt)+Number(ipNetPremiumAmt);
			 
			 if(!((Number(totalMarginAmt1) < Number(grossPremiumAmt)) || (Number(totalMarginAmt1) === Number(grossPremiumAmt)))){
				 alert("Actual Premium (Pro-rata Premium) amount should be always greater than the sum of all deductables");
				// document.forms[1].ipNetPremium.focus();
				 //	 document.forms[1].ipNetPremium.value="";
				 return false;      
			 }
			}*/
		
		
		var prodPolicyAuthority = document.forms[1].prodPolicyAuthority.value;
		var officeCode = document.forms[1].officeCode.value;
		if(officeCode=="INS022" && prodPolicyAuthority=="DHA"){
			var insCategoryCode = document.forms[1].insCategoryCode.value;
			if(insCategoryCode==""){
				alert("Please enter Insurance Member Category Code");
				document.getElementById("insCategoryCode").focus();
				 return false;
			}
			
		}
		
		
		var inceptionDate= document.forms[1].inceptionDate.value;
		var memAddedDate= document.forms[1].memAddedDate.value;
		var portedYN= document.forms[1].portedYN.value;
		
		
		var date1 = inceptionDate.split("/");
		var altDate1=date1[1]+"/"+date1[0]+"/"+date1[2];
		altDate1=new Date(altDate1);
		
		var date2 =memAddedDate.split("/");
		var altDate2=date2[1]+"/"+date2[0]+"/"+date2[2];
 		altDate2=new Date(altDate2);
		
 		
 		 var incdate = new Date(altDate1);
  	    var memdate =  new Date(altDate2);
		
  	    
  	 if(portedYN =="Y"){
		 if(memdate > incdate){
			alert("Member First Inception Date should not be  exceed the Date of Inception");
			document.getElementById("memAddedDate").focus();
			 return false;
		}
  	 }
		
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/AddMemberDetailAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

function onCancel()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
    document.forms[1].action="/EditMemberDetailAction.do";
	document.forms[1].submit();
}//end of onCancel

function onReset(strRootIndex)
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
	    document.forms[1].action="/EditMemberDetailAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()

function onSuspendedIcon()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value="doSearch";
	document.forms[1].action="/SuspensionAction.do";
	document.forms[1].submit();
}// end of onSuspendedIcon()

function onDelete()
{
	var msg = confirm("Are you sure you want to delete the selected record ?");
	if(msg)
	{
		document.forms[1].mode.value="doDelete";
		document.forms[1].action="/EditMemberDetailAction.do";
		document.forms[1].submit();
	}
}// end of onDelete()

function onClarificationTypeIDChange()
{
	document.forms[1].exitDate.value="";
	if(document.forms[1].status.value=='POC')	//if policy status is made active
	{
		document.forms[1].exitDate.value=document.forms[1].effectiveDate.value;
	}
	else	//if policy status Active,with suspension or with extension
	{
		document.forms[1].exitDate.value=document.forms[1].policyEndDate.value;
	}
	
	
		var currentpolicyStatusForMember = document.getElementById("currentpolicyStatusForMember").value;
		var changingpolicystatus = document.getElementById("memberpolicystatusid").value;
		
		if(currentpolicyStatusForMember =="POC" || currentpolicyStatusForMember =="PEX" || currentpolicyStatusForMember =="PSU"){
			
			if(changingpolicystatus == "PAR"){
				alert("Awated For Renewal is not Valid Selection");
				document.getElementById("memberpolicystatusid").value=currentpolicyStatusForMember;
				return;
			}
		}
		
	if(currentpolicyStatusForMember =="POA" || currentpolicyStatusForMember =="POC" || currentpolicyStatusForMember =="PEX" || currentpolicyStatusForMember =="PSU" || currentpolicyStatusForMember == "PAR"){
			
			if(changingpolicystatus == "POE" || changingpolicystatus == "POD"){
				alert("Expired/Deleted is not Valid Selection");
				document.getElementById("memberpolicystatusid").value=currentpolicyStatusForMember;
				return;
			}
		}
	
}//end of onClarificationTypeIDChange()


function onRelationshipChange()
{
	var relationID=document.forms[1].relationTypeID.value;
	var genderID=relationID.substring(relationID.indexOf("#")+1,relationID.length);
	relationID=relationID.substring(0,relationID.indexOf("#"));

	/*if(relationID=="NSF")
		{
		document.forms[1].name.value=document.forms[1].hiddenName.value;
	document.forms[1].secondName.value=document.forms[1].hiddensecondName.value;
	document.forms[1].familyName.value=document.forms[1].hiddenfamilyName.value;
		}
	else
		{
		document.forms[1].name.value="";
		}*/
	document.forms[1].relationID.value=genderID;
	document.forms[1].focusID.value="relationTypeID";
	document.forms[1].mode.value="doChangeRelationship";
	document.forms[1].action="/EditMemberDetailAction.do";
	document.forms[1].submit();
}//end of onClarificationTypeIDChange()

function onDateofBirth()
{
	var regexp = /^(\d{2}\/\d{2}\/\d{4})$/;
	document.forms[1].dateOfBirth.value = trim(document.forms[1].dateOfBirth.value);
	var ageObj = document.getElementById("ageid");
	if (regexp.test(document.forms[1].dateOfBirth.value)==true)
	{
		var age = calculatedAge(document.forms[1].dateOfBirth.value,document.forms[1].policyStartDate.value);
		if(!isNaN(age) && age>=0)
			{
			ageObj.value = age;
			}
		else
			ageObj.value = "0";
	}//end of if (regexp.test(document.forms[1].dateOfBirth.value)==true)
	if(document.forms[1].dateOfBirth.value.length>0)
		ageObj.readOnly=true;
	else
		ageObj.readOnly=false;
}//end of onDateofBirth()

//javascript function called to calculate the IBM members age on the date of birth entered
function onIBMDateofBirth()
{
	var policyStartDate = document.forms[1].policyDate.value;
       var dateofJoining = document.forms[1].DOJ.value;
       var dateofMarriage = document.forms[1].DOM.value;
	var dateofBirth     = document.forms[1].dateOfBirth.value;
	var DOBDOM = "";

	if(document.forms[1].relationTypeID.value=="YSP#OTH")//spouse
	{
		DOBDOM=dateofMarriage;
	}
	else if((document.forms[1].relationTypeID.value.match("NC"))||(document.forms[1].relationTypeID.value.match("ND"))||(document.forms[1].relationTypeID.value.match("NS")))
	{
		DOBDOM=dateofBirth;
	}
	var regExp = /(\d{1,2})\/(\d{1,2})\/(\d{2,4})/;
       var maxDate="";
	if(parseInt(policyStartDate.replace(regExp, "$3$2$1")) > parseInt(dateofJoining.replace(regExp, "$3$2$1")))
	{
	   maxDate = policyStartDate;
	}
	else
	{
	  maxDate = dateofJoining;
	}

	if(DOBDOM!="")
	{
		if(parseInt(maxDate.replace(regExp, "$3$2$1")) < parseInt(DOBDOM.replace(regExp, "$3$2$1")))
		{
	   	     maxDate = DOBDOM;
		}

	}
	day1 = maxDate.substring (0, maxDate.indexOf ("/"));
	month1 = maxDate.substring (maxDate.indexOf ("/")+1, maxDate.lastIndexOf ("/"));
	year1 = maxDate.substring (maxDate.lastIndexOf ("/")+1, maxDate.length);

	day2 = dateofBirth.substring (0, dateofBirth.indexOf ("/"));
	month2 = dateofBirth.substring (dateofBirth.indexOf ("/")+1, dateofBirth.lastIndexOf ("/"));
	year2 = dateofBirth.substring (dateofBirth.lastIndexOf ("/")+1, dateofBirth.length);

	date1 = year1+"/"+month1+"/"+day1;
	date2 = year2+"/"+month2+"/"+day2;

	firstDate = Date.parse(date1);
	secondDate= Date.parse(date2);

	msPerDay = 24 * 60 * 60 * 1000;
	dbd = Math.floor((firstDate.valueOf()-secondDate.valueOf())/ msPerDay) + 1;
    var calculatedAge=Math.floor(dbd/365.25);
    if(!isNaN(calculatedAge) && calculatedAge>=0)
    	{
	
	/*if(calculatedAge>65)
		{
	alert("Age should not be greater than 65");
	document.forms[1].age.value= "";
	document.forms[1].dateOfBirth.value="";
	document.forms[1].dateOfBirth.focus();
		}
	else
		{*/
		document.forms[1].age.value = calculatedAge;
		/*}*/
    	}
    else
    	{
	document.forms[1].age.value = "0";
    	}
}

function onChangeCity()
{
    	document.forms[1].mode.value="ChangeCity";
    	document.forms[1].action="/EditMemberDetailAction.do";
    	document.forms[1].submit();
}//end of onChangeCity()


function calcCapNetPremium()
{
	/*var re = /^[0-9]*\.*[0-9]*$/;	
	var objValue=upObj.value;	
	//alert(objValue);
	
	 if(objValue.length>=1)
	 {
		if(!re.test(objValue)) 
		{
			alert("Data Should be Numeric");
			upObj.value="";
			upObj.focus();
		}
	 }*/
	 // for gross premium
	/* var eventCompleteYN=document.getElementById("eventCompleteYN").value;
	
	 if(eventCompleteYN=="N")
		 {*/
	 var medicalPremiumAmt=document.forms[1].medicalPremium.value;
	// medicalPremiumAmt=(medicalPremiumAmt==null||medicalPremiumAmt===""||!re.test(medicalPremiumAmt)||medicalPremiumAmt==="0")?0:medicalPremiumAmt;
	 medicalPremiumAmt=(medicalPremiumAmt==null||medicalPremiumAmt===""||medicalPremiumAmt==="0")?0:medicalPremiumAmt;
	 var maternityPremiumAmt=document.forms[1].maternityPremium.value;
	 maternityPremiumAmt=(maternityPremiumAmt==null||maternityPremiumAmt===""||maternityPremiumAmt==="0")?0:maternityPremiumAmt;
	 
	 
	 var opticalPremiumAmt=document.forms[1].opticalPremium.value;
	 opticalPremiumAmt=(opticalPremiumAmt==null||opticalPremiumAmt===""||opticalPremiumAmt==="0")?0:opticalPremiumAmt;
	 
	 var dentalPremiumAmt=document.forms[1].dentalPremium.value;
	 dentalPremiumAmt=(dentalPremiumAmt==null||dentalPremiumAmt===""||dentalPremiumAmt==="0")?0:dentalPremiumAmt;	 
	
	 var wellnessPremiumAmt=document.forms[1].wellnessPremium.value;
	 wellnessPremiumAmt=(wellnessPremiumAmt==null||wellnessPremiumAmt===""||wellnessPremiumAmt==="0")?0:wellnessPremiumAmt;
	 
	 
	 document.forms[1].grossPremium.value=parseFloat(medicalPremiumAmt)+parseFloat(maternityPremiumAmt)+parseFloat(opticalPremiumAmt)+parseFloat(dentalPremiumAmt)+parseFloat(wellnessPremiumAmt);
	 
	 var grossPremiumAmt=document.forms[1].grossPremium.value;
	 grossPremiumAmt=(grossPremiumAmt==null||grossPremiumAmt===""||grossPremiumAmt==="0")?0:grossPremiumAmt;
	 var memberActivePeriod=document.forms[1].memberActivePeriod.value;
	 memberActivePeriod=(memberActivePeriod==null||memberActivePeriod===""||memberActivePeriod==="0")?0:memberActivePeriod;
	 var actualPremium=grossPremiumAmt*memberActivePeriod/365;
	 document.forms[1].actualPremium.value=actualPremium.toFixed(2);
	 // for all margins 
	 var insurerMarginAmt=document.getElementById("insurerMarginPER").value;//document.forms[1].insurerMargin.value;
	 insurerMarginAmt=(insurerMarginAmt==null||insurerMarginAmt===""||insurerMarginAmt==="0")?0:insurerMarginAmt;
	 var brokerMarginAmt=document.getElementById("brokerMarginPER").value; //document.forms[1].brokerMargin.value;
	 brokerMarginAmt=(brokerMarginAmt==null||brokerMarginAmt===""||brokerMarginAmt==="0")?0:brokerMarginAmt;
	 var tapMarginAmt=document.getElementById("tapMarginPER").value;//document.forms[1].tapMargin.value; tapMarginPER
	 tapMarginAmt=(tapMarginAmt==null||tapMarginAmt===""||tapMarginAmt==="0")?0:tapMarginAmt;
	 
	 var reInsBrkMarginAmt=document.getElementById("reInsBrkMarginPER").value;//document.forms[1].reInsBrkMargin.value;
	 reInsBrkMarginAmt=(reInsBrkMarginAmt==null||reInsBrkMarginAmt===""||reInsBrkMarginAmt==="0")?0:reInsBrkMarginAmt;
	 
	 var otherMarginAmt=document.getElementById("otherMarginPER").value;//document.forms[1].otherMargin.value;
	 otherMarginAmt=(otherMarginAmt==null||otherMarginAmt===""||otherMarginAmt==="0")?0:otherMarginAmt;
	 
	 // for finding all margins  total
	 var totalMarginAmt = ((parseFloat(actualPremium)*parseFloat(otherMarginAmt))/100)+((parseFloat(actualPremium)*parseFloat(reInsBrkMarginAmt))/100)+((parseFloat(actualPremium)*parseFloat(brokerMarginAmt))/100)+((parseFloat(actualPremium)*parseFloat(insurerMarginAmt))/100); 
	 
	 
	 totalMarginAmt=(totalMarginAmt==null||totalMarginAmt===""||totalMarginAmt==="0")?0:totalMarginAmt;
	 
	 // for finding Net Premium 
	 var ipNetPremiumAmt = document.forms[1].ipNetPremium.value;    
	 ipNetPremiumAmt=(ipNetPremiumAmt==null||ipNetPremiumAmt===""||ipNetPremiumAmt==="0")?0:ipNetPremiumAmt;
	 
	 
	  var  totMar_IpNetPre = parseFloat(ipNetPremiumAmt)+parseFloat(totalMarginAmt);
	  var netPremium=(parseFloat(actualPremium)-parseFloat(totMar_IpNetPre)).toFixed(2);
	  if(netPremium<0)
		  {
		  document.forms[1].netPremium.value = '0.0';
		  }
	  else
		  {
	   document.forms[1].netPremium.value = netPremium;
		  }
		/* }*/
	  // calcActualPremium();
}


 function isNegative1()
 {
	 var re = /^[0-9]*\.*[0-9]*$/;
	 
	 var objValue=document.getElementById("minAge").value;
	
	 
	 if(objValue.length>=1)
	 {
		if(!re.test(objValue)) 
		{
			alert(" Age Should be Numeric ");
			document.getElementById("minAge").focus();
		}
	 }

	 if(objValue<0)
		 {
			if(!re.test(objValue)) 
			{
				  alert('Negative Values Not allowed');
				objValue.value="";
				document.getElementById("minAge").focus();
			}
		 }
}

 
 function isNegative2()
 {
	 var re = /^[0-9]*\.*[0-9]*$/;
	 var objValue=document.getElementById("maxAge").value;
	
	 if(objValue.length>=1)
	 {
		if(!re.test(objValue)) 
		{
			alert(" Age Should be Numeric ");
			document.getElementById("maxAge").focus();
		}
	 }
	 if(objValue<0)
	 {
		if(!re.test(objValue)) 
		{
			  alert('Negative Values Not allowed');
			objValue.value="";
			document.getElementById("maxAge").focus();
		}
	 }
 }
 
 function ageCompare(){
	
	 if( !((document.getElementById("minAge").value)==="") && !((document.getElementById("maxAge").value)===""))
	  {
		  if(Number(document.getElementById("minAge").value) > Number(document.getElementById("maxAge").value))
		  { 
		  alert("Max age always Greater Then Min age");
		  document.getElementById("maxAge").value="";  
		  document.getElementById("maxAge").focus();
		 }
		  }
	} 
 function numbervalidation(txt)
 {
	
	 if(txt==="Insurer Margin" && !(Number(document.forms[1].insurerMargin.value)< 100))
	 {
		 alert(txt +" should be less than 100");
		 document.forms[1].insurerMargin.value="";
		 document.forms[1].insurerMargin.focus();
	 }
	 if(txt==="Broker Margin" && !(Number(document.forms[1].brokerMargin.value)< 100))
	 {
		 alert(txt +" should be less than 100");
		 document.forms[1].brokerMargin.value="";
		 document.forms[1].brokerMargin.focus();
	 }
	 if(txt==="TPA Margin" && !(Number(document.forms[1].tapMargin.value)< 100))
	 {
		 alert(txt +" should be less than 100");
		 document.forms[1].tapMargin.value="";
		 document.forms[1].tapMargin.focus();
	 }
	 if(txt==="ReIns.Brk.Margin" && !(Number(document.forms[1].reInsBrkMargin.value)< 100))
	 {
		 alert(txt +" should be less than 100");
		 document.forms[1].reInsBrkMargin.value.value="";
		 document.forms[1].reInsBrkMargin.focus();  
	 }
	 if(txt==="Other Margin" && !(Number(document.forms[1].otherMargin.value)< 100))
	 {
		 alert(txt +" should be less than 100 ");
		 document.forms[1].otherMargin.value="";
		 document.forms[1].otherMargin.focus();
	 }  
	 
 }
 
 function calculatePerAED(obj,idName)
 {
	/* var eventCompleteYN=document.getElementById("eventCompleteYN").value;*/
	/* if(eventCompleteYN=="N")
		 {*/
	 var op;
	   var AED=idName+"AED";
	   var PER=idName+"PER";
	   var displayDiv="display"+idName;
   var displayAEDPER="displayAEDPER"+idName;
   var displayAED="displayAED"+idName;
   var displayPER="displayPER"+idName;
	 var dropselvalue=idName+"AEDPER";
 	var GP= document.getElementById("actualPremium").value;
 	
 var selvalue=document.getElementById(dropselvalue).value;
//alert("selvalue=="+selvalue);
 if(selvalue!=null)
 	{
 	
        if(selvalue=="AED")
     	   {
     	   
     	   document.getElementById(AED).value=document.getElementById(idName).value;
     	 //  alert(document.getElementById(idName).value);
     	   
     		   
     		  if(GP>0) op=document.getElementById(idName).value/GP*100;
     		  else op='0.0';
     	   document.getElementById(PER).value=op.toFixed(2);
     	  document.getElementById(displayAED).style.display = "none";
     	   document.getElementById(displayPER).style.display = "";
     	   if(dropselvalue!="tapMarginAEDPER")
     	  calcCapNetPremium();
     	  // document.getElementById(PER).focus();
     	   }
        else if(selvalue=="PER")
     	   {
     	   /* var op;
     	   var AED=idName+"AED";
     	   var PER=idName+"PER";
     	   var displayAED="displayAED"+idName;
            var displayPER="displayPER"+idName;
     	   var displayDiv="display"+idName;
            var displayAEDPER="displayAEDPER"+idName; */
            if(Number(document.getElementById(idName).value)> 100)
         	   {
         	  alert(idName+" Should be less than 100");
         	  document.getElementById(idName).value="";
         	  document.getElementById(idName).focus();
         	   }
            else
         	   {
     	   document.getElementById(PER).value=document.getElementById(idName).value;
     	  // alert(document.getElementById(idName).value);
     		  op=GP*document.getElementById(idName).value/100;
     	   document.getElementById(AED).value=op.toFixed(2);
     	  document.getElementById(displayPER).style.display = "none";
     	   document.getElementById(displayAED).style.display = "";
     	  if(dropselvalue!="tapMarginAEDPER")
     	  calcCapNetPremium();
     	  // document.getElementById(AED).focus();
         	   }
     	   }
        else{
     	   document.getElementById(displayPER).style.display = "none";
     	   document.getElementById(displayAED).style.display = "none";
        }
 	}
 else
 	{
 	alert("please Enter data");
 	document.getElementById(idName).focus();
 	}
		/* }*/
 }

 function isValiedAge(obj)
 {
 	var str=obj.value;

 	str=trim(str);
 	obj.value=str;
 	if(isNaN(str))
 	{
 		alert("Please enter a valid Age");
 		obj.select();
 		obj.focus();
 	}
 	else if(str>65)
 		{
 		alert("Age should be between 0 to 65");
 		obj.select();
 		obj.focus();
 		}
 }
 
 function calcActiveDays(obj)
 {
 	var dateFrom =document.getElementById("policyStartDate").value;
 	var dateTo=document.getElementById("exitDate").value;
 	var dateCheck = obj.value;
 	
 		var date1 = dateFrom.split("/");
 		var altDate1=date1[1]+"/"+date1[0]+"/"+date1[2];
 		altDate1=new Date(altDate1);
 		var date2 =dateTo.split("/");
 		var altDate2=date2[1]+"/"+date2[0]+"/"+date2[2];
 		altDate2=new Date(altDate2);
 		var altDateCheck =dateCheck.split("/");
 		 altDateCheck=altDateCheck[1]+"/"+altDateCheck[0]+"/"+altDateCheck[2];
 		 altDateCheck=new Date(altDateCheck);
 		
 	    var minDate = new Date(altDate1);
 	    var maxDate =  new Date(altDate2);
 	    
 	   var today = new Date();
 	  var dd = today.getDate();
 	  var mm = today.getMonth()+1; //January is 0!
 	  var yyyy = today.getFullYear();

 	  if(dd<10) {
 	      dd = '0'+dd
 	  } 

 	  if(mm<10) {
 	      mm = '0'+mm
 	  } 

 	    today = mm + '/' + dd + '/' + yyyy;
    	today=new Date(today);
 	
 	   
 	  /* if(altDateCheck > today){
 		   alert("Date of inception should be always lesser than the system date.");
 		  obj.value="";
 		   return false;
 		   
 	   }*/
 	   
 	  if(dateCheck == dateTo){
	 		 alert("Member Policy Active Days="+"0");
	 		 var days = 0;
				document.getElementById("memberActivePeriod").value=days;
				 return false;
	 	}
 	   
 	    
 	    if (altDateCheck >= minDate && altDateCheck <= maxDate )
 	    {
 	    	var timeDiff = Math.abs(altDate2.getTime() - altDateCheck.getTime());
 			var diffDays = Math.ceil(timeDiff / (1000 * 3600 * 24)); 
 			diffDays++;
 			
 			/*if("366"==diffDays){
 				diffDays=diffDays-1;
 			}*/
 			
 		     alert("Member Policy Active Days="+diffDays);
 			document.getElementById("memberActivePeriod").value=diffDays;
 	    }
 	    else
 	    {
 	    	alert("Member Inception Date Should be between the Policy Period");
 			obj.value="";
 	    }
 }

	
 
	function  onMemberTypeChange()
	 {
		
		document.forms[1].mode.value="doMemberTypeChange";
		document.forms[1].action="/EditMemberDetailAction.do";
		document.forms[1].submit();
		
		
		
	 }
 
