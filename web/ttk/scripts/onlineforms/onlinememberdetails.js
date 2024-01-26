/**
 * @ (#) onlinememberdetails.js Jan 14th,2008
 * Project       : TTK HealthCare Services
 * File          : onlinememberdetails.js
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : Jan 14th,2008
 * @author       : Krupa J
 * Modified by   : Balaji C R B
 * Modified date :14 October,2008
 * Reason        :
 */

//javascript function called on click of the hyperlink to edit a record
function edit(rownum)
{
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/OnlineMemberDetailsAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//javascript function called to close the memberdetails screen
function onClose()
{
	document.forms[1].mode.value="doClose";
    document.forms[1].action = "/OnlineMemberDetailsAction.do";
    document.forms[1].submit();
}//end of onClose()

//javascript function called to save a record
/*function onSave()
{
	trimForm(document.forms[1]);
	if(onCheckSumInsured()){
		if(!JS_SecondSubmit)
    	{
    	  document.forms[1].mode.value="doSave";
		  document.forms[1].action = "/UpdateMemberDetailsAction.do";
		  if((checkObject(document.forms[1].dateOfBirth)) && (checkObject(document.forms[1].age)))
		  {
		  	onDateofBirth();
		  }//end of if(checkDOB(document.forms[1].dateOfBirth))
		  if(checkObject(document.forms[1].age))
		  {
			document.forms[1].age.disabled = false;
		  }//end of if(checkObject(document.forms[1].age)
		  if(checkObject(document.forms[1].totalSumInsured))
		  {
			document.forms[1].totalSumInsured.disabled = false;
		  }//end of  if(checkObject(document.forms[1].totalSumInsured))
		  JS_SecondSubmit=true;
		  document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	}//end of if(onCheckSumInsured())
}*///end of onSave()

function onSave()
{
	trimForm(document.forms[1]);
	if(onCheckSumInsured()){
	if(!JS_SecondSubmit)
    	{
		  var Enr = document.forms[1].enrollmentNbr.value;
          var Relation = document.forms[1].relationTypeID.value;
		  var age = document.forms[1].age.value;
		  if((Enr.match("I310-01"))||(Enr.match("I310-001")))
		  {

			  if((age>18)&&(!(Relation.match("YSP#OTH")))&&(!(Relation.match("NSF#OTH"))))
			  {
				  document.getElementById('Accept').disabled=false;
				  if(document.getElementById('Accept').checked==false)
				  {
					  alert("Please select the Declaration");
					  return false;
				  }
				  else
				  {
					  var msg = confirm("I certify that my son/daughter is financially dependent on me and not married.");
					  if(msg)
					  {
						  document.forms[1].mode.value="doSave";
						  document.forms[1].action = "/UpdateMemberDetailsAction.do";
						  if((checkObject(document.forms[1].dateOfBirth)) && (checkObject(document.forms[1].age)))
						  {
						  	//onDateofBirth();
						  	onIBMDateofBirth();
						  }//end of if(checkDOB(document.forms[1].dateOfBirth))
						  if(checkObject(document.forms[1].age))
						  {
							document.forms[1].age.disabled = false;
						  }//end of if(checkObject(document.forms[1].age)
						  if(checkObject(document.forms[1].totalSumInsured))
						  {
							document.forms[1].totalSumInsured.disabled = false;
						  }//end of  if(checkObject(document.forms[1].totalSumInsured))
						  JS_SecondSubmit=true;
						  document.forms[1].submit();

					  }
					  else
					  {
						  document.getElementById('Accept').checked=false;
						  return false;
					  }
				  }
			  }
			  else
			  {
				document.forms[1].mode.value="doSave";
				document.forms[1].action = "/UpdateMemberDetailsAction.do";
				if((checkObject(document.forms[1].dateOfBirth)) && (checkObject(document.forms[1].age)))
				{
					//onDateofBirth();
					onIBMDateofBirth();
				 }//end of if(checkDOB(document.forms[1].dateOfBirth))
				if(checkObject(document.forms[1].age))
				{
					document.forms[1].age.disabled = false;
				 }//end of if(checkObject(document.forms[1].age)
				if(checkObject(document.forms[1].totalSumInsured))
				{
					document.forms[1].totalSumInsured.disabled = false;
				 }//end of  if(checkObject(document.forms[1].totalSumInsured))
				 JS_SecondSubmit=true;
				 document.forms[1].submit();

			 }


		  }
		  else
		  {
    	  	  document.forms[1].mode.value="doSave";
		  document.forms[1].action = "/UpdateMemberDetailsAction.do";
		  if((checkObject(document.forms[1].dateOfBirth)) && (checkObject(document.forms[1].age)))
		  {
		  	//onDateofBirth();
		  	if(Enr.match("I310"))
			{
			   onIBMDateofBirth();
			}
			else
			{
			  onDateofBirth();
			}
		  }//end of if(checkDOB(document.forms[1].dateOfBirth))
		  if(checkObject(document.forms[1].age))
		  {
			document.forms[1].age.disabled = false;
		  }//end of if(checkObject(document.forms[1].age)
		  if(checkObject(document.forms[1].totalSumInsured))
		  {
			document.forms[1].totalSumInsured.disabled = false;
		  }//end of  if(checkObject(document.forms[1].totalSumInsured))
		  JS_SecondSubmit=true;
		  document.forms[1].submit();
		  }//end of else
		}//end of if(!JS_SecondSubmit)
	}//end of if(onCheckSumInsured())
}//end of onSave()

function ageDeclaration()
{
	if(document.forms[1].ageDeclaration.value=="Y")
		document.getElementById('Accept').checked=true;
	else

		document.getElementById('Accept').checked=false;

}//end of ageDeclaration()

function checkObject(objToTest)
{
	if (objToTest == null || objToTest == undefined)
	{
		return false;
	}//end of if (objToTest == null || objToTest == undefined)
	return true;
}//end of checkDOB(objToTest)

//javascript function called on click of the Sum insured icon in the grid
function onRatesIcon(rownum)
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/AdditionalSumInsuredDetailsAction.do";
    document.forms[1].submit();
}//end of onRatesIcon()

//javascript function called on click of the family sum insured icon
function onRatesIconFamily()
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].action = "/AdditionalSumInsuredDetailsAction.do";
    document.forms[1].submit();
}//end of onRatesIconFamily()

//javascript function called to calculate the age based on the date of birth entered
function onDateofBirth()
{
	var regexp = /^(\d{2}\/\d{2}\/\d{4})$/;
	document.forms[1].dateOfBirth.value = trim(document.forms[1].dateOfBirth.value);
	if (regexp.test(document.forms[1].dateOfBirth.value)==true)
	{
		if(checkObject(document.forms[1].age))
		{
			var age = calculatedAge(document.forms[1].dateOfBirth.value,document.forms[1].loginDate.value);
			if(!isNaN(age) && age>=0)
				document.forms[1].age.value = age;
			else
				document.forms[1].age.value = "0";
				
		 }//end of if(checkObject(document.forms[1].age))
	}//end of if (regexp.test(document.forms[1].dateOfBirth.value)==true)
	else
	{
		//alert("In else block");
		if(document.forms[1].dateOfBirth.value.length==0 && document.forms[1].ageStatus.value=="SHOW")
		{
			document.forms[1].age.value = "";
		}//end of if
	}//end of else

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
	document.forms[1].age.value = calculatedAge;
    else
	document.forms[1].age.value = "0";

}
//javascript function called on change of relationship to load the gender value
function onRelationshipChange()
{
	 if((checkObject(document.forms[1].dateOfBirth)) && (checkObject(document.forms[1].age)))
	 {
		//onDateofBirth();
		var Enr = document.forms[1].enrollmentNbr.value;
		if(Enr.match("I310"))
		{
		  onIBMDateofBirth();
		}
		else
		{
		   onDateofBirth();
		}
			document.forms[1].age.disabled = false;
	 }//end of if(checkDOB(document.forms[1].dateOfBirth))
	 var relationID=document.forms[1].relationTypeID.value;
	 var genderID=relationID.substring(relationID.indexOf("#")+1,relationID.length);
	 relationID=relationID.substring(0,relationID.indexOf("#"));
	 document.forms[1].relationID.value=genderID;
	 document.forms[1].focusID.value="relationTypeID";
	 document.forms[1].mode.value="doChangeRelationship";
	 document.forms[1].action="/OnlineMemberDetailsAction.do";
	 document.forms[1].planAmt.value=0;
	 document.forms[1].prodPlanSeqID.value='';
	 if(document.forms[1].totalSumInsured!=undefined)
     {
    	document.forms[1].totalSumInsured.value=0;
	 }
	 document.forms[1].dateOfBirth.value='';
	 document.forms[1].age.value = '';
	 document.forms[1].submit();
}//end of onRelationshipChange()

//javascript function called on click of the delete icon in the grid to delete a record
/*function onDeleteIcon(rownum)
{
	var msg = confirm("Are you sure you want to delete the selected record ?");
	if(msg)
	{
		document.forms[1].mode.value="doDelete";
		document.forms[1].rownum.value=rownum;
		document.forms[1].action = "/DeleteMemberDetailsAction.do";
	    document.forms[1].submit();
    }//end of if(msg)
}//end of onDeleteIcon(rownum)*/

//javascript function called on click of the delete icon in the grid to delete a record

//Modified for Oracle alert Message on Deletion of Records
function onDeleteIcon(rownum)
{
	var enrollmentNo = document.forms[1].enrollmentNbr.value;
	if(!enrollmentNo.match("O0099"))
	{
		
		var msg = confirm("Are you sure you want to delete the selected record ?");
		if(msg)
		{
			document.forms[1].mode.value="doDelete";
			document.forms[1].rownum.value=rownum;
			document.forms[1].action = "/DeleteMemberDetailsAction.do";
		    document.forms[1].submit();
	    }//end of if(msg)
		
	}
	else
	{
		
		document.forms[1].mode.value="doDelete";
		document.forms[1].rownum.value=rownum;
		document.forms[1].action = "/DeleteMemberDetailsAction.do";
	    document.forms[1].submit();
	}

	
}//end of onDeleteIcon(rownum)


//Added for Oracle alert Message on Deletion of Records
function checkDelete(rownum,mode)
{
	document.forms[1].action="/DeleteMemberDetailsAction.do?mode=doconfirmDelete&rownum="+rownum;
	//document.forms[1].mode.value=mode;
	//document.forms[1].rownum.value=rownum;
	//document.forms[1].action = "/DeleteMemberDetailsAction.do";
    document.forms[1].submit();	
	
}

function onReset(strRootIndex)
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		var regexp=/^[0-9]*$/;
		if(regexp.test(document.forms[1].age.value)== false)
		{
			document.forms[1].age.value = '0';
		}//end of if(regexp.test(document.forms[1].age.value)== false)
		document.forms[1].mode.value="doReset";
		if(document.getElementById('totsumins') !=null)
		{
			document.forms[1].totalSumInsured.value="0";
		}//end of if(document.getElementById('totsumins') !=null)
	    document.forms[1].action="/OnlineMemberDetailsAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
		var Enr = document.forms[1].enrollmentNbr.value;
		if((Enr.match("I310-01"))||(Enr.match("I310-001")))
		{
			if(document.forms[1].ageDeclaration.value=="Y")
			document.getElementById('Accept').checked=true;
			else
			document.getElementById('Accept').checked=false;
		}

	}//end of else
}//end of onReset()

//called on click of Sum Insured Icon in Member Details Information
function onRatesIconDetails()
{
	trimForm(document.forms[1]);
	var age = document.forms[1].age.value;
    var dateOfBirth=document.forms[1].dateOfBirth.value;
	if(onCheckSumInsured())
	{
				
		if((dateOfBirth!='') || (age!=''))
		{
		document.forms[1].age.disabled=false;
		document.forms[1].mode.value="doListPlan";
		document.forms[1].action="/OnlineMemberDetailsAction.do";
		document.forms[1].submit();
		}
		else
		{
		if(document.forms[1].age.disabled)
			{
			if(document.forms[1].sumInsuredStatus.value =='SHOW')
			{
			 document.forms[1].totalSumInsured.disabled =true;
			}//end of if(document.forms[1].sumInsuredStatus.value =='SHOW')
			 alert('Please enter Date of Birth');
			 document.forms[1].dateOfBirth.focus();
			}//end of if(document.forms[1].age.disabled)
			else
			{
			if(document.forms[1].sumInsuredStatus.value =='SHOW')
			{
				 document.forms[1].totalSumInsured.disabled =true;
			}//end of if(document.forms[1].sumInsuredStatus.value =='SHOW')
			 alert('Please enter Age');
			 document.forms[1].age.focus();
			}//end of else
		}//end of else
	}//end of if(onCheckSumInsured())
}//end  of onRatesIconDetails()

//check for Sum Insured should not be alphanumeric
//and should be greater than selected plan amount
function onCheckSumInsured()
{
	if(document.forms[1].totalSumInsured!=undefined)
	{
	var planAmt = document.forms[1].planAmt.value;
	var totalSumInsured = document.forms[1].totalSumInsured.value;

	if(checkObject(document.forms[1].totalSumInsured))
	{
		document.forms[1].totalSumInsured.disabled=false;
	}//end of if(checkobject(document.forms[1].totalSumInsured))
	if(isNaN(totalSumInsured))
	{
			alert('Sum Insured should be numeric');
			document.forms[1].totalSumInsured.focus();
			return false;
	}//end of if(isNaN(totalSumInsured))
	if(totalSumInsured=='')
	{
		alert('Please enter Sum Insured');
		document.forms[1].totalSumInsured.focus();
		return false;
	}//end of if(totalSumInsured=='')
	if(totalSumInsured<planAmt)
	{
		alert('Sum Insured can not be less than selected Plan Amount');
		document.forms[1].totalSumInsured.focus();
		return false;
	}//end of if(totalSumInsured<planAmt)
	}//end of if(document.forms[1].totalSumInsured!=undefined)
	return true;
}//end of onCheckSumInsured()
//changes IBM Issue
function onChangeAge()
{

	if ((document.forms[1].memberSeqID.value=='') || (document.forms[1].memberSeqID.value==null))
		{
	if(document.forms[1].totalSumInsured!=undefined){
		var planAmt = document.forms[1].planAmt.value;
	var totalSumInsured = document.forms[1].totalSumInsured.value;
	if(totalSumInsured>=planAmt)
	{
		totalSumInsured=totalSumInsured-planAmt;
		document.forms[1].totalSumInsured.value=totalSumInsured;
	}//end of if(totalSumInsured>=planAmt)
	document.forms[1].planAmt.value=0;
	document.forms[1].prodPlanSeqID.value='';
	//fix weblogin
	document.forms[1].totalSumInsured.value=0;
	}//end of if(document.forms[1].totalSumInsured!='undefined')
		}

}
//changes IBM Issue



