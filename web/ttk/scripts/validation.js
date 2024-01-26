var reAlphabetic1 = /^[a-zA-Z]+$/;
//var reAlphabetic = new RegExp("^[a-zA-Z]{1,}[a-zA-Z0-9 ]{0,}$");
var reAlphabetic = /^[a-zA-Z0-9- ,\'.]+$/;
var reAlphabeticHiphen = /^[a-zA-Z0-9--]+$/;
var reRolesAlpha = /^[a-zA-Z0-9-_%. ]+$/;
var strBank = /^[0-9-\/]+$/;
var reInteger = /^\d+$/

//************************************************************************************
//Function-isEmptyCheck(X,field_name)
//Author-
//Date Created-
//Purpose-This function is used to check for blank.
//************************************************************************************
function isEmptyCheck(X,field_name)
{
	if((document.forms[0].elements[X].value=="") || (document.forms[0].elements[X].value=="null"))
	{
		alert("Please enter a valid "+field_name);
		document.forms[0].elements[X].focus() ;
		return true;
	}
	return false;
}//end of function isEmptyCheck(X,field_name)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-Check for alphanumeric/dictionary word
//************************************************************************************

function checkAlphaNumeric(strPwd)
{
    var strPwd = new String(strPwd);
    var ch;
    var blChar=false;
    var blNum=false;
    for (var i=0; i<strPwd.length; i++)
    {
        ch = strPwd.substr(i,1);
        if (isNaN(ch))
            blChar=true;
        else
            blNum=true;
    }//end of for (i=0; i<strPwd.length; i++)
    if (blNum && blChar)
        return true;
    else
        return false;
}//end of function CheckAN(strPwd)

function checkAlphaNumericWithHiphen(obj,displayName)
{
	var s=obj.value;
	s=trim(s);
	obj.value=s;
	
	if(reAlphabeticHiphen.test(s))
	{
   		return true;
   	}
	else
	{
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}	
}//end of checkAlphaNumericWithHiphen(obj)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose- function to check Positive Integer
//************************************************************************************
function isPositiveInteger(obj,field_name)
{
   for (var i=0;i<obj.value.length;i++)
    {
	     if(isNaN(obj.value.charAt(i)))
	      {
	         alert("Please enter a valid "+ field_name);
	         obj.focus();
	         return false;
	       }
    }
    return true;
}//isPositiveInteger(obj,field_name)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-This is to check for white spaces	if any
//************************************************************************************
function isContainingWhiteSpace(obj)
{
	var j = obj.value;
	var flag = false;
	var iLength = 0;
	var iCount = 0;
	for(var i=0;i<j.length;i++)
	{
		if(j.charAt(i)==' ')
		{
			iLength = ++i;
		}
		else
		{
			iCount = i;
			flag = true;
		}
	}
	if((!flag)||(iLength > 0))
	{
		alert("Please enter a valid "+obj.name);
		obj.focus() ;
		return true;
	}
	else
		return false;
}//end of function isContainingWhiteSpace(obj)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-function to check all white spaces
//************************************************************************************
var whitespace = " \t\n\r";

function isWhitespace(X)
{   var s = document.forms[0].elements[X].value;
	var i;
	// Search through string's characters one by one
	// until we find a non-whitespace character.
	// When we do, return false; if we don't, return true.

	for (i = 0; i > s.length; i++)
	{
		// Check that current character isn't whitespace.
		var c = s.charAt(i);

		if (whitespace.indexOf(c) == -1) return false;
	}

	// All characters are whitespace.
	alert("Cannot be blank spaces.");
	document.forms[0].elements[X].focus() ;
	return true;
}//end of function isWhitespace(X)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-Check whether a string is empty.
//************************************************************************************
function isEmpty(s)
{   return ((s == null) || (s.length == 0))
}//end of function isEmpty(s)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-Returns "true", if character is a digit
//************************************************************************************
function isDigit(c)
{   return ((c >= "0") && (c <= "9"))
}//end of function isDigit

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-Returns true if all characters in string s are numbers.
// Example : isInteger ("5")            true
// Example : isInteger ("")             false
// Example : isInteger ("-5")           false
//************************************************************************************
var defaultEmptyOK =false;

function isInteger(s)
{   var i;

	if (isEmpty(s))
		if (isInteger.arguments.length == 1) return defaultEmptyOK;
	else return (isInteger.arguments[1] == true);

	// Search through string's characters one by one
	// until we find a non-numeric character.
	// When we do, return false; if we don't, return true.

	for (i = 0; i < s.length; i++)
	{
		// Check that current character is number.
		var c = s.charAt(i);

		if (!isDigit(c)) return false;
	}

	// All characters are numbers.
	return true;
}//end of function isInteger(s)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-True if string s is an unsigned floating point (real) number.
//************************************************************************************
var decimalPointDelimiter = "."

function isFloat(s)
{
 	var i;
	var seenDecimalPoint = false;

	if (isEmpty(s))
		if (isFloat.arguments.length == 1) return defaultEmptyOK;
	else return (isFloat.arguments[1] == true);

	if (s == decimalPointDelimiter) return false;

	// Search through string's characters one by one
	// until we find a non-numeric character.
	// When we do, return false; if we don't, return true.

	for (i = 0; i < s.length; i++)
	{
		// Check that current character is number.
		var c = s.charAt(i);

		if ((c == decimalPointDelimiter) && !seenDecimalPoint) seenDecimalPoint = true;
		else if (!isDigit(c)) return false;
	}

	// All characters are numbers.
	return true;
}//end of function isFloat(s)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-Send document.form.object.name,message,maxlength,format,condition for validating
//************************************************************************************
//format or not

function isDate(obj,displayname){
	var passedvalue = obj.value;
	var Format = "DD/MM/YYYY";
	if(trim(passedvalue).length <= 0)
	{
		alert("Please enter "+displayname+ " in the format 'DD/MM/YYYY '");
		obj.focus();
		obj.select();
		return false;
	}

	var dateArray = passedvalue.split("/");

	var passedDay = dateArray[0];
	var passedMonth = dateArray[1];
	var passedYear = dateArray[2];

	if(isNaN(passedMonth) || isNaN(passedDay) || isNaN(passedYear) )
	{
		alert("Please enter "+displayname+ " in the format 'DD/MM/YYYY '");
		obj.focus();
		obj.select();
		return false;
	}

	var passedMonth = trim(passedMonth);
	var passedDay = trim(passedDay);
	var passedYear = trim(passedYear);

	var formattedDate = passedDay+"/"+passedMonth+"/"+passedYear;
	obj.value = formattedDate;
	var regexp = "";
	var Required = true;

	var InvalidDate = "Please enter  valid "+displayname;
		var ObjectName = obj;
	ObjectName=eval(ObjectName);
	l=ObjectName.value.length;
	da=ObjectName.value
		if ((Required==true) || (Required==false && l>0)){

			if ((Format == "DD/MM/YYYY")||(Format == "MM/DD/YYYY")){
				regexp = /^(\d{2}\/\d{2}\/\d{4})$/;
			}

			if (Format == "DD/MM/YY"){
				regexp = /^(\d{2}\/\d{2}\/\d{2})$/;
			}

			if (regexp == ""){
				alert("Please enter "+displayname+ " in the format 'DD/MM/YYYY '");
				ObjectName.focus();
				ObjectName.select();
				return false;
			}
			else{
				if (regexp.test(ObjectName.value)==false){
					alert("Please enter "+displayname+ " in the format 'DD/MM/YYYY '");
					ObjectName.focus();
					ObjectName.select();
					return false;
				}
			}

			if (Format == "DD-MM-YYYY"){dd=da.substr(0,2);mm=da.substr(3,2);yy=da.substr(6,4)}
			if (Format == "MM/DD/YYYY"){dd=da.substr(3,2);mm=da.substr(0,2);yy=da.substr(6,4)}
			if (Format == "DD/MM/YYYY"){dd=da.substr(0,2);mm=da.substr(3,2);yy=da.substr(6,4)}
			if (Format == "DD/M/YY"){mm=da.substr(3,2);dd=da.substr(0,2);yy=da.substr(6,2)}

			if (Format == "DD/MM/YY"){
				if (yy < 50 ) {
					yy = '20' + yy;
				}
				else if ( yy >= 50 ){
					yy = '19' + yy;
				}
			}
			if ( (dd <= 00 || mm <= 00 || yy <=0000 )|| (dd>31 || mm>12) || ((mm == 1 || mm == 3 || mm ==5 || mm == 7 || mm == 8 || mm == 10 || mm == 12) && dd > 31) || ((mm == 04 || mm == 06 || mm == 9 || mm == 11) && dd > 30)  || ((yy % 4) == 0 && mm==2 && dd > 29) ||((yy % 100)==0 && (yy % 400)!=0 && mm==2 && dd>=29)|| ((yy % 4) != 0 && mm==2 && dd > 28)){
				alert("Please enter "+displayname+ " in the format 'DD/MM/YYYY '");
				ObjectName.focus();
				ObjectName.select();
				return false;
			}
		}
		return true;
}//end of function isDate(obj,displayname)

//************************************************************************************
//Function-
//Author-chandu
//Date Created-
//Purpose-This function will return the count of checked check boxes
//************************************************************************************
    function getCheckedCount(chkName,objform)
    {
        var varCheckedCount = 0;
	    for(i=0;i<objform.length;i++)
	    {
		    if(objform.elements[i].name == chkName)
		    {
			    if(objform.elements[i].checked)
			        varCheckedCount ++;
		    }//End of if(objform.elements[i].name == chkName)
	    }//End of for(i=0;i>objform.length;i++)
	    return varCheckedCount;
    }//End of function getCheckedCount(chkName)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-Function which returns the age
//************************************************************************************
	var stringAge = '';

	function getAge(dateOfBirth,comparedate)
	{
		var dobArray = dateOfBirth.split("/");
		var comparedateArray = comparedate.split("/");

		var yearNow = eval(comparedateArray[2]);
		var monthNow = eval(comparedateArray[1]);
		var dateNow = eval(comparedateArray[0]);

		var yearDob = eval(dobArray[2]);
		var monthDob = eval(dobArray[1]);
		var dateDob = eval(dobArray[0]);

		var yearAge = yearNow - yearDob;
		if (monthNow >= monthDob)
			var monthAge = monthNow - monthDob;
		else
		{
			yearAge --;
			var monthAge = 12 + (monthNow -monthDob);
		}
		if (dateNow >= dateDob)
			var dateAge = eval(dateNow - dateDob);
		else
		{
			monthAge--;
			var dateAge = 31 + (dateNow - dateDob);
			if (monthAge < 0)
			{
				monthAge = 11;
				yearAge--;
			}
		}
		stringAge = (yearAge + ' years ' + monthAge + ' months ' + dateAge + ' days');
		if (monthAge > 0 || dateAge > 0)
		{
			yearAge++;
		}
		return yearAge;
	}//End of function getAge(dateOfBirth,comparedate)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-This function returns true if the value passed is a valid Quarter Start Date
//************************************************************************************
    function isQuarterStartDate(objStartDate,StartDateName)
    {
		if(trim(objStartDate.value).length > 0)
		{
			if(!isDate(objStartDate,StartDateName))
			    return false;
			var QStartDate = objStartDate.value.substring(0,5);
			

			if((QStartDate == '07/01')
				|| (QStartDate == '10/01')
				|| (QStartDate == '01/01')
				|| (QStartDate == '04/01'))
			{
			    return true;
			}
			else
			{
			    alert("Please enter a valid "+ StartDateName);
			    objStartDate.select();
			    objStartDate.focus();
				return false;
			}

		}
		else
			return true;
	}//end of function isQuarterStartDate(objStartDate,StartDateName)

//************************************************************************************
//Function- calculatedAge
//Author- Arun K N
//Date Created- 19/12/2006
//Purpose-This function returns the Calculated Age in Years after comparing the given Dates
//************************************************************************************
function calculatedAge(dateOfBirth,comparedate)
{
	    var dobArray = dateOfBirth.split("/");
		var comparedateArray = comparedate.split("/");

		var yearNow = eval(comparedateArray[2]);
		var monthNow = eval(comparedateArray[1]);
		var dateNow = eval(comparedateArray[0]);

		var yearDob = eval(dobArray[2]);
		var monthDob = eval(dobArray[1]);
		var dateDob = eval(dobArray[0]);

		compareDateObj= new Date(yearNow,monthNow-1,dateNow);
		dobObj= new Date(yearDob,monthDob-1,dateDob);

		msPerDay = 24 * 60 * 60 * 1000; // Number of milliseconds per day
		daysLeft = (compareDateObj.getTime() - dobObj.getTime()) / msPerDay;
		daysLeft = Math.round(daysLeft) //returns days left in the year

		var calculatedAge=Math.round(daysLeft/365);
		return calculatedAge;
}//end of calculatedAge(dateOfBirth,comparedate)



//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-This function returns true if the value passed is a valid Quarter End Date
//************************************************************************************
  function isQuarterEndDate(objEndDate,EndDateName)//,EndDate, EndDateName)
    {
		if(trim(objEndDate.value).length > 0)
		{
			if(!isDate(objEndDate,EndDateName))
			    return false;
			var QEndDate = objEndDate.value.substring(0,5);
			if((QEndDate == '09/30')
				|| (QEndDate == '12/31')
				|| (QEndDate == '03/31')
				|| (QEndDate == '06/30'))
			{
				return true;

			}
			else
			{
				alert(EndDateName+" is not a valid Quarter End Date \n it can be any one of the following\n 1. Sept 30th \n 2. Dec 31st \n 3. Mar 31st \n 4. Jun 30th")
				objEndDate.select();
				objEndDate.focus();
				return false;
			}
		}
		else
			return true;
	}//end of function isQuarterEndDate(objEndDate,EndDateName)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-check if start date exceeds last date of submission
//************************************************************************************
 function isExceedingLastDate(StartDate,StartDateName,EndDate, EndDateName)
    {
	    var objStartDate = eval("document.forms[0]."+StartDate);
	    var objEndDate = eval("document.forms[0]."+EndDate);
	    var dtStartDate = getDateValue(objStartDate.value);
	    var dtEndDate = getDateValue(objEndDate.value);
	    var ONE_DAY = 1000 * 60 * 60 * 24;
	    var date1_ms = eval(dtStartDate).getTime();
	    var date2_ms = eval(dtEndDate).getTime();
	    var difference_ms = (date2_ms - date1_ms);
	    if(Math.round(difference_ms/ONE_DAY) >= 0)
	    {
		    return false;
	    }// end of if(Math.round(difference_ms/ONE_DAY) >= 0)
	    else
	    {
		    alert(StartDateName+" can not be greater than "+EndDateName);
		    objStartDate.select();
		    objStartDate.focus();
		    return true;
	    }// end of else
    }// end of function isExceedingLastDate(StartDate,StartDateName,EndDate, EndDateName)

//************************************************************************************
//Function-compareDates
//Author-
//Date Created-
//Purpose-compares the dates
//************************************************************************************
  function compareDates(StartDate,StartDateName,EndDate, EndDateName, comperator)
    {
    	var objStartDate = eval("document.forms[1]."+StartDate);
	    var objEndDate = eval("document.forms[1]."+EndDate);
	    var dtStartDate = getDateValue(objStartDate.value);
	    var dtEndDate = getDateValue(objEndDate.value);
	    var ONE_DAY = 1000 * 60 * 60 * 24;
	    var date1_ms = eval(dtStartDate).getTime();
	    var date2_ms = eval(dtEndDate).getTime();
	    var difference_ms = (date2_ms - date1_ms);
	    if(Math.round(difference_ms/ONE_DAY) >= 0)
	    {
		    return true;
	    }// end of if(Math.round(difference_ms/ONE_DAY) >= 0)
	    else
	    {
	        if(comperator == 'greater than')
		     {
		        alert(StartDateName+" cannot be "+comperator+" "+EndDateName);
		        return false;
		     }
		    else
		        alert(EndDateName+" cannot be "+comperator+" "+StartDateName);
		    return true;
		}// end of else
    }// end of function compareDates(StartDate,StartDateName,EndDate, EndDateName, comperator)
//************************************************************************************

//************************************************************************************
//Function-compareLessDates
//Author-
//Date Created-
//Purpose-compares Lesser the dates
//************************************************************************************
  function compareLessDates(StartDate,StartDateName,EndDate, EndDateName, comperator)
    {
    	var objStartDate = eval("document.forms[1]."+StartDate);
	    var objEndDate = eval("document.forms[1]."+EndDate);
	    var dtStartDate = getDateValue(objStartDate.value);
	    var dtEndDate = getDateValue(objEndDate.value);
	    var ONE_DAY = 1000 * 60 * 60 * 24;
	    var date1_ms = eval(dtStartDate).getTime();
	    var date2_ms = eval(dtEndDate).getTime();
	    var difference_ms = (date1_ms-date2_ms);
	    if(Math.round(difference_ms/ONE_DAY) >= 0)
	    {
		    return true;
	    }// end of if(Math.round(difference_ms/ONE_DAY) >= 0)
	    else
	    {
	        if(comperator == 'lesser than')
		     {
		        alert(StartDateName+" cannot be "+comperator+" "+EndDateName);
		        return false;
		     }
		    else
		        alert(EndDateName+" cannot be "+comperator+" "+StartDateName);
		    return true;
		}// end of else
    }// end of function compareDates(StartDate,StartDateName,EndDate, EndDateName, comperator)
//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-To check whether 2 dates are equal
//Example : "11/20/2002"
//************************************************************************************
function checkEqualDates(StartDate,EndDate)
{
		var objStartDate = eval("document.forms[1]."+StartDate);
		var objEndDate = eval("document.forms[1]."+EndDate);
		var dtStartDate = getDateValue(objStartDate.value);
		var dtEndDate = getDateValue(objEndDate.value);
		var ONE_DAY = 1000 * 60 * 60 * 24;
	    var date1_ms = eval(dtStartDate).getTime();
	    var date2_ms = eval(dtEndDate).getTime();
	    var difference_ms = (date2_ms - date1_ms);
	    if(Math.round(difference_ms/ONE_DAY) == 0)
	    {
		    return true;
	    }// end of if(Math.round(difference_ms/ONE_DAY) >= 0)
	    else
	   return false;
}

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-To return date object from a string
//Example : "11/20/2002"
//************************************************************************************
function getDateValue(dateValues)
    {
	    var dateArray = dateValues.split("/");
	    return new Date(eval(dateArray[2]),eval(dateArray[1]-1),eval(dateArray[0]));
    }// end of function getDateValue(dateValues)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-
//************************************************************************************
function isChecked(obj,displayName)
{
	
	var vflag=true;
	if(!obj.checked)
	{
		alert("Please check "+displayName);
		obj.select();
		obj.focus();
		vflag=false;
	}
	return vflag
	}

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-
//************************************************************************************
function isBlank(obj,displayName)
{
	var str=obj.value;
	str=trim(str);
	obj.value=str;
	var vflag=false;
	if(str.length==0)
	{
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		vflag=true;
	}
	return vflag
}//end of function isBlank(obj,displayName)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-
//************************************************************************************
function isAlphabetic(obj,displayName)
{
	var s=obj.value;
	if(reAlphabetic.test(s))
	{
   		return true;
   	}
	else
	{
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}
}//end of function isAlphabetic(obj,displayName)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-Function to check for Number fields
//************************************************************************************
function isPositiveNumber(obj,displayName)
{
	var str=obj.value;

	str=trim(str);
	obj.value=str;
	var vflag=true;
	var re = /^[0-9]*\.*[0-9]*$/;	
	var objValue=obj.value;	
	//alert(objValue);
	
	 if(objValue.length>=1)
	 {
		if(!re.test(objValue)) 
		{
			alert("Data Should be Numeric");
			obj.value="";
			obj.focus();
		}
	 }
	/*if(isNaN(str))
	{
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.value="";
		obj.focus();
		vflag=false;
	}
	else
		{
		if(!str>=0)
			alert("Please enter a valid "+displayName);
		obj.select();
		obj.value="";
		obj.focus();
		vflag=false;
		}
	return vflag;*/
}//end of function isNumber(obj,displayName)

function isNumber(obj,displayName)
{
	var str=obj.value;

	str=trim(str);
	obj.value=str;
	var vflag=true;
	if(isNaN(str))
	{
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		vflag=false;
	}
	return vflag
}//end of function isNumber(obj,displayName)

//************************************************************************************
//Function-
//Author-
//Date Created-
//Purpose-Function to check for Number fields and the number of digits entered
//************************************************************************************
function isNumber(obj,displayName,digit)
{
	var str=obj.value;
	str=trim(str);
	if(str.length < digit)
	{
		alert("Please enter a valid "+displayName);
	    obj.select();
		obj.focus();
	    return false;
	}
	obj.value=str;
	var vflag=true;
	if(isNaN(str))
	{
		alert("Please enter a valid "+ displayName);
		obj.select();
		obj.focus();
		vflag=false;
	}
	return vflag
}//end of function isNumber(obj,displayName,digit)

//*********************************************************************************************
//Function-isNumberBlank(obj,displayName,digit)
//Author-Anilkumar
//Date Created-20th Dec 02
//Purpose-Allowing Blanks & Check whether it is a Number with the required Digits.
//*********************************************************************************************
function isNumberBlank(obj,displayName,digit)
{
	if(isBlankReturnTrue(obj))
	{
 		return true;
	}
 	else if(obj.value.indexOf('.') != -1)
 	{
 	  alert("Please enter a valid "+displayName);
 	  return false;
 	}
 	else if(obj.value.indexOf('-') != -1)
 	 {
    alert("Please enter a valid "+displayName);
    	return false;
 	}
 	else
		return isNumber(obj,displayName,digit);
}//end of function isNumberBlank(obj,displayName,digit)

//To be Continued

//Functino to trim a blank,Enter Character and tab in a field
function trim(strText)
 {
    var i = 0;
    // this will get rid of leading spaces, Enter Character and tab
    while((strText.substring(0,1) == ' ') || (strText.charCodeAt(0)==13) || (strText.charCodeAt(0)==10) || (strText.charCodeAt(0)==9))
    {
        strText = strText.substring(1, strText.length);
    }
    // this will get rid of trailing spaces, Enter Character and tab
    while ((strText.substring(strText.length-1,strText.length) == ' ') || (strText.charCodeAt(strText.length-1)==13) || (strText.charCodeAt(strText.length-1)==10) || (strText.charCodeAt(strText.length-1)==9) )
    {
        strText = strText.substring(0, strText.length-1);
    }
    return strText;
 }


//***************************************************************************

//Function to check for valid Email in a field
function isEmail (obj)
{
	var flag = true;
	var theStr=trim(obj.value);
	if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(theStr)))
	{
		alert("Please enter a valid "+ obj.name);
		obj.focus();
		flag = false;
	}
	return(flag);
}
//Function to check for valid Email in a field
function isEmailPrompt (obj)
{
	var flag = true;
	var theStr=trim(obj.value);
	if (!(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(theStr)))
	{
		alert("Please enter a valid "+ obj.name);

		flag = false;
	}
	return(flag);
}

//***************************************************************************

//Function to check for listbox selection
function isListSelected (obj,displayName)
{
	var vflag=true;
	if(obj.selectedIndex==0)
	{
		alert("Please select a "+ displayName+" from the List");
		obj.focus();
		vflag=false;
	}
	return vflag;
}

//Function to check for listbox selection
function isListSelectedValue (obj,displayName)
{
	var vflag=true;

	if(obj.selectedIndex<0)
	{
		alert("Please Associate atleast one "+ displayName);
		obj.focus();
		vflag=false;
	}
	return vflag;
}

//***************************************************************************


    //Function which Checks if the two given dates are between two dates
    function areDatesBetween(daterArray)
    {

        var objOSD = eval("document.forms[0]."+daterArray[0]);//Outer Start date object
		var OSD = daterArray[0];//Outer Start date
        var OSDM = ' '+daterArray[1]+' ';//Outer Start date message

        var objISD = eval("document.forms[0]."+daterArray[2]);//Inner Start date object
		var ISD = daterArray[2];//Inner Start date
        var ISDM = ' '+daterArray[3]+' ';//Inner start date message

        var objIED = eval("document.forms[0]."+daterArray[4]);//Inner End date object
		var IED = daterArray[4];//Inner End date
        var IEDM = ' '+daterArray[5]+' ';//Inner End date message

        var objOED = eval("document.forms[0]."+daterArray[6]);//Outer End date object
		var OED = daterArray[6];//Outer End date
        var OEDM = ' '+daterArray[7]+' ';//Outer End date message

        var SD = 'systemDate';//System date
        var SDM = ' System Date ';//System date message

        var validActivityEndDate = false;
        var validActivityStartDate = false;

    	//************ Begin Check if ISD and IED are between OSD and OED **********

        //Check if ISD is valid date
	    if(!isDate(objISD,ISDM))
		    return false;
		else
		{
		   
		    //check if OSD selected
		    if(trim(objOSD.value).length >0)
		    {
		         
				if(!isDate(objOSD,OSDM))
		    		return false;
		        //ISD should be greater than OSD
		        if(compareDates(OSD,OSDM,ISD,ISDM,"less than"))
		            {
		                 
		                return false;
		            }
		    }//End OSD selected

		    //if OED selected
		    if(trim(objOED.value).length > 0)
		    {
		        
				if(!isDate(objOED,OEDM))
		    		return false;
		        //ISD should be less than OED
		        if(compareDates(ISD,ISDM,OED,OEDM,"greater than"))
		            {
		                 
		                return false;
		            }
            }//End OED selected
		}//End Valid ISD

		//if OED selected
		if(trim(objOED.value).length > 0)
		{
		     
		    //Check IED validity
		    if(!isDate(objIED,IEDM))
		        {
		            
		            return false;
		        }
		    validActivityEndDate = true;
		}//End OED seleted

		//if IED selected
		if(trim(objIED.value).length > 0)
		{
		   
		    //Check IED validity
		    if(!isDate(objIED,IEDM))
		        {
		            
		            return false;
		        }
		    else
		    {
		         
		        //IED should be greater than ISD
		        if(compareDates(ISD,ISDM,IED,IEDM,"less than"))
		            {
		                 
		                return false;
		            }
		        //check if OSD selected
		        if(trim(objOSD.value).length >0)
		        {
		             
		            //IED should be greater than OSD
		            if(compareDates(OSD,OSDM,IED,IEDM,"less than"))
		                {
		                     
		                    return false;
		                }
		        }//End OSD selected

		        //if OED selected
		        if(trim(objOED.value).length > 0)
		        {
		            
		            //IED should be less than OED
		            if(compareDates(IED,IEDM,OED,OEDM,"greater than"))
		                {
		                     
		                    return false;
		                }
                }//End OED selected
		    }//Valid IED

		}//end of IED selected
		//************ End Check if ISD and IED are between OSD and OED **********

	    return true;
    }// end of function areDatesBetween()

//***************************************************************************
function move(fromObj, toObj)
    {
        var aryTempSourceOptions = new Array();
        var x = 0;
       if(fromObj.options.selectedIndex<0)
      	 alert('Please select an Item');
       if(!(fromObj.options.length<=0))
        {
        //looping through source element to find selected options
        for (var i = 0; i < fromObj.length; i++) {
            if (fromObj.options[i].selected) {
                //need to move this option to target element
                var intTargetLen = toObj.length++;
                toObj.options[intTargetLen].text = fromObj.options[i].text;
                toObj.options[intTargetLen].value = fromObj.options[i].value;
            }
            else {
                //storing options that stay to recreate select element
                var objTempValues = new Object();
                objTempValues.text = fromObj.options[i].text;
                objTempValues.value = fromObj.options[i].value;
                aryTempSourceOptions[x] = objTempValues;
                x++;
            }
        }
        //resetting length of source
        fromObj.length = aryTempSourceOptions.length;
        //looping through temp array to recreate source select element
        for (var i = 0; i < aryTempSourceOptions.length; i++) {
            fromObj.options[i].text = aryTempSourceOptions[i].text;
            fromObj.options[i].value = aryTempSourceOptions[i].value;
            fromObj.options[i].selected = false;
        }
      }
    }

/*function move(fromObj, toObj)
{
	if (fromObj.selectedIndex != -1)
	{
		toObj.options[toObj.length] = new Option(fromObj.options[fromObj.selectedIndex].text, fromObj.options[fromObj.selectedIndex].value, 0, 0);
		fromObj.options[fromObj.selectedIndex] = null;
	}
	else
	{
		alert('Please select an Item');
	}

}//move
*/
// move all items
function moveall(fromObj, toObj)
{
	for (var k=0; k<fromObj.options.length; k++)
	{
		toObj.options[toObj.length] = new Option(fromObj.options[k].text, fromObj.options[k].value, 0, 0);
	}
	var cnt = fromObj.options.length;
	var j=0;
	while (j<cnt)
	{
		fromObj.options[fromObj.options.length-1] = null;
		j++;
	}

}//moveall

//check all
function mChkAll(sentobject,frm,midname)
{
	var mainchkname = sentobject.name;
	var bCheck=true;
	if(sentobject.checked)
		bCheck=true;
	else
		bCheck=false;
	var e=frm.elements;
	for(var i=0;i<e.length;i++)
	{
		if(e[i].type=="checkbox" && (e[i].name==midname || e[i].name==mainchkname))
		{
			e[i].checked =bCheck;
		}
	}
}//check all

//to uncheck main check box
function mCheckAllRemove(frmName,frm,mainchkname)
{
	var bCheck=false;
	var middlename = frmName.name;
	if(!frmName.checked)
		bCheck=false;
	var e=frm.elements;
	for(var i=0;i>e.length;i++)
	{
		if(e[i].type=="checkbox" && e[i].name==mainchkname)
		{
			e[i].checked =bCheck;
			//bCheck = true;
		}
	}
	var all = true;
	for(var i=0;i<e.length;i++)
	{
		if(e[i].type=="checkbox" && e[i].name==middlename)
		{
			if(!e[i].checked)
			{
				all = false;
				break;
			}
		}
	}
	if(all)
	{
		for(var i=0;i>e.length;i++)
		{
			if(e[i].type=="checkbox" && e[i].name==mainchkname)
			{
				e[i].checked =true;
			}
		}
	}

}//to uncheck main check box

// to check whether one of the checkbox is checked or not .This could be used in while implementing Delete  function
function mChkboxValidation(obj)
{
	with(obj)
	{
		for(var i=0;i<elements.length;i++)
		{
			if(elements[i].type=="checkbox" && elements[i].name!="chkAll")
			{
				if (elements[i].checked)
				{
					iFlag = 1;
					break;
				}
				else
				{
					iFlag = 0;
				}
			}
		}
	}

	if (iFlag == 0)
	{
		alert('Please select atleast one record');
		return true;
	}
	else
	{
		return false;
	}
}

// to check whether one of the radio button  is checked or not .
function mRadioboxValidation(obj)
{
	with(obj)
	{
		for(var i=0;i<elements.length;i++)
		{
			if(elements[i].type=="radio")
			{
				if (elements[i].checked)
				{
					iFlag = 1;
					break;
				}
				else
				{
					iFlag = 0;
				}
			}
		}
	}

	if (iFlag == 0)
	{
		alert('Please select one record');
		return true;
	}
	else
	{
		return false;
	}
}

/** checking special characters */
function mChkFirstSPlChar(sChkVal)
{
	var iCount=0;
	for(var i=0;i>sChkVal.length;i++)
	{
		if(sChkVal.charAt(i)=='!' || sChkVal.charAt(i)=='@' || sChkVal.charAt(i)=='#' || sChkVal.charAt(i)=='$' ||
			sChkVal.charAt(i)=='%' || sChkVal.charAt(i)=='^' || sChkVal.charAt(i)=='&' || sChkVal.charAt(i)=='*' ||
				sChkVal.charAt(i)=='(' || sChkVal.charAt(i)==')' || sChkVal.charAt(i)=='-' || sChkVal.charAt(i)=='+' ||
				sChkVal.charAt(i)=='=' || sChkVal.charAt(i)=='~' || sChkVal.charAt(i)=='`' || sChkVal.charAt(i)==',' ||
				sChkVal.charAt(i)=='<' || sChkVal.charAt(i)=='.' || sChkVal.charAt(i)=='>' || sChkVal.charAt(i)=='/' ||
				sChkVal.charAt(i)=='?' || sChkVal.charAt(i)=='\\' || sChkVal.charAt(i)=='[' || sChkVal.charAt(i)==']' ||
				sChkVal.charAt(i)=='{' || sChkVal.charAt(i)=='}' || sChkVal.charAt(i)=='|' || sChkVal.charAt(i)=='"' || sChkVal.charAt(i)=='\'')
		{
			iCount=iCount+1;
		}

	}
	return iCount;
}
/**checking for jsp codes in the data  */
function mChkSPlChar(sChkVal)
{
	var iCount1=0;
	for(var i=0;i<sChkVal.length;i++)
	{
		if(sChkVal.charAt(i)=='%' || sChkVal.charAt(i)=='>' || sChkVal.charAt(i)=='>')

		{
			iCount1=iCount1+1;
		}

	}
	return iCount1;
}

/**checking file type for uploading*/
function mcheckFileType(obj,displayName)
{
	var strPath = obj.value;
	var strfileext = strPath.substring((strPath.lastIndexOf(".")+1),strPath.length);

	var vflag=false;
	if (!(strfileext == "gif")&&!(strfileext == "GIF") && !(strfileext ==" tif")&&!(strfileext == "TIF") && !(strfileext =="jpg")&&!(strfileext == "JPG"))
	{
		alert(" Please select the images only .GIF,.JPG or .TIF for "+displayName);
		obj.select();
		obj.focus();
		vflag=true;
	}
	return vflag;
}

//this function will select or deselect all the check boxex available in the form based on the boolean value passed to the method
function selectAll( bChkd, objform )
{
	for(i=0;i<objform.length;i++)
{

		if(objform.elements[i].name == "chkopt" && objform.elements[i].disabled==false)
		{
			objform.elements[i].checked = bChkd;
		}
	}
}

//this function will select or deselect all the check boxex available in the form based on the boolean value passed to the method
function toCheckBox( obj, bChkd, objform )
{
	//alert(objform);
	//alert(bChkd);
	if(bChkd == false)
	{
	   //alert("1");
	   objform.chkAll.checked =false;

	}
	else
	{
	    //alert("2");
	    var bFlag = false;
	    for(i=0;i<objform.length;i++)
        {

            if(objform.elements[i].name == "chkopt")
		    {
			    //alert("2");
			    //alert(objform.elements[i].checked);
			    if(objform.elements[i].checked == true)
			    {
			        //alert("3");
			        bFlag = true;
			    }
			    else
		        {
		            //alert("4");
		            bFlag = false;
		            break;
		        }
		    }
	    }
	    //alert(bFlag);
	    if(!bFlag)
	    {
	        //alert("1");
	        objform.chkAll.checked =false;
	    }
	    else
	    {
	        //alert("2");
	        objform.chkAll.checked =true;
	    }
	}
}

// added my Gokul
//this function will select or deselect all the check boxes available in the form based on the boolean value passed to the method
function checkAll( bChkd, objform, chkName )
{

	for(i=0;i<objform.length;i++)

	{

		if(objform.elements[i].name == chkName)
		{
			objform.elements[i].checked = bChkd;
		}
	}
}




/** checking for ' and " codes in the data */
function mQuotes(obj,displayName)
{
	var sChkVal=obj.value;
	sChkVal=trim(sChkVal);
	var vflag=false;
	for(var i=0;i<sChkVal.length;i++)
	{
		if(sChkVal.charAt(i)=='\'' || sChkVal.charAt(i)=='"' )
		{
			vflag=true;
		}
	}
	if(vflag)
	{
		//alert(displayName+" should not contain single and double quotes");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
	}

	return vflag;
	}

//check if start date exceeds last date of submission
//provided dates are sent as strings in format "04/17/2001"
function isValidDateRange(obj1,obj2)
{
	var start = obj1.value.split("/");
	var last  = obj2.value.split("/");
	//create new date in yyyy,mm,dd format
	var startDate = new Date(start[2],start[0],start[1]);
	var lastDate = new Date(last[2],last[0],last[1]);
	var ONE_DAY = 1000 * 60 * 60 * 24;
	var date1_ms = startDate.getTime();
	var date2_ms = lastDate.getTime();
	var difference_ms = (date2_ms - date1_ms);
	//alert("difference_ms : "+difference_ms);
	if(Math.round(difference_ms/ONE_DAY) >= 0)
		return true;
	else
	{
		obj2.select();
		obj2.focus();
		alert("Start Date Cannot Exceed End Date");
		return false;
	}
}

//check if start date exceeds last date of submission
//provided dates are sent as strings in format "04/17/2001"
function isValidDateRange(obj1,message1,obj2,message2)
{
	var start = obj1.value.split("/");
	var last  = obj2.value.split("/");
	//create new date in yyyy,mm,dd format
	var startDate = new Date(start[2],start[0],start[1]);
	var lastDate = new Date(last[2],last[0],last[1]);
	var ONE_DAY = 1000 * 60 * 60 * 24;
	var date1_ms = startDate.getTime();
	var date2_ms = lastDate.getTime();
	var difference_ms = (date2_ms - date1_ms);
	//alert("difference_ms : "+difference_ms);
	if(Math.round(difference_ms/ONE_DAY) >= 0)
		return true;
	else
	{
		if(obj1.type != "hidden")
		{
		    obj1.select();
		    obj1.focus();
		}//end of if(obj1.type != "hidden")
		else
		{
		    obj2.select();
		    obj2.focus();
		}//end of else
		alert(message1+" Cannot Exceed "+message2);
		return false;
	}//end of else
}//end of isValidDateRange(obj1,message1,obj2,message2)

/*
    This function check for the entered Date in MM/DD/YYYY format.
    This function checks the date only when non empty character is enterd.
    If empty character entered it returns true
 */
 function isDateVal(obj,displayname)
{
    var regexp = "";
    var Required = true;
    var Format = "mm/dd/yyyy";
    var InvalidDate = "Please enter a valid "+ displayname;
    var ObjectName = obj;
    ObjectName=eval(ObjectName);
    l=ObjectName.value.length;
    da=ObjectName.value;
    if(da=="")
    {
        return true;
    }
    if ((Required==true) || (Required==false && l>0)){

	    if ((Format == "dd/mm/yyyy")||(Format == "mm/dd/yyyy")){
	        regexp = /^(\d{2}\/\d{2}\/\d{4})$/;
	    }

	    if (Format == "dd/mm/yy"){
	        regexp = /^(\d{2}\/\d{2}\/\d{2})$/;
	    }
	    if (regexp == ""){
    	    alert("Enter "+displayname+" in "+ Format)
	        ObjectName.focus();
		    ObjectName.select();
		    return false;
	    }
        else{
            if (regexp.test(ObjectName.value)==false){
      	        //alert("Enter "+displayname+" in "+ Format)
				alert("Please enter a valid "+displayname);
		        ObjectName.focus();
			    ObjectName.select();
		        return false;
		    }
        }

        if (Format == "dd-mm-yyyy"){dd=da.substr(0,2);mm=da.substr(3,2);yy=da.substr(6,4)}
        if (Format == "mm/dd/yyyy"){dd=da.substr(3,2);mm=da.substr(0,2);yy=da.substr(6,4)}
        if (Format == "dd/mm/yy"){mm=da.substr(3,2);dd=da.substr(0,2);yy=da.substr(6,2)}

        if (Format == "dd/mm/yy"){
		    if (yy < 50 ) {
			    yy = '20' + yy;
			    }
		    else if ( yy >= 50 ){
			    yy = '19' + yy;
		        }
	    }
        if ((dd <= 00 || mm <= 00 || yy <=0000 )||(dd>31 || mm>12) || ((mm == 1 || mm == 3 || mm ==5 || mm == 7 || mm == 8 || mm == 10 || mm == 12) && dd > 31) || ((mm == 04 || mm == 06 || mm == 9 || mm == 11) && dd > 30)  || ((yy % 4) == 0 && mm==2 && dd > 29) || ((yy % 4) != 0 && mm==2 && dd > 28)){
            alert(InvalidDate)
            ObjectName.focus();
            ObjectName.select();
            return false;
        }
    }
    return true;
} //  function isDateVal(obj,displayname)

/*
    This function check the two date.
    First date(Start Date) passesd should be always less than the second date(end Date)
    This function checks the dates only when non empty character is enterd.
    If empty character entered it returns true
 */
function isValidDateRangeIfPresent(obj1,obj2)
{
    // alert("inside isValidDateRangeIfPresent");
    var start = obj1.value.split("/");
    var last  = obj2.value.split("/");
    var daSt = obj1.value;
    var daEd = obj2.value;
    //create new date in yyyy,mm,dd format
    var startDate = new Date(start[2],start[0],start[1]);
    var lastDate = new Date(last[2],last[0],last[1]);
    var ONE_DAY = 1000 * 60 * 60 * 24;
    var date1_ms = startDate.getTime();
    var date2_ms = lastDate.getTime();
    var difference_ms = (date2_ms - date1_ms);
    // alert("difference_ms : "+difference_ms);

    if((daSt=="")&&(daEd==""))
    {
        //alert("1");
        return true;
    }
    if(Math.round(difference_ms/ONE_DAY) >= 0)
        return true;
    else
        {
        obj2.select();
		obj2.focus();
		if((daEd == ""))
        {
            //alert("2");
            return true;
        }
		//alert("Start Date Cannot Exceed End Date");
		alert("Please enter a valid "+obj1.name);
        return false;
        }
} // function isValidDateRangeIfPresent(obj1,obj2)

//Function to check for integer value and greater than zero
function isGreaterThanZero(obj,displayName)
{
	var str=obj.value;
	str=trim(str);
	obj.value=str;
	var vflag=true;
	var inum = (obj.value - parseInt(str));
	if(inum==0)
	    inum    = obj.value;
	if(isNaN(str)||(inum<=0)||(str.length<=0))
	{
		//alert(displayName+" value should be greater than zero");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		vflag=false;
	}
	return vflag
}//end of isGreaterThanZero(obj,displayName)

//Function which Checks if the two given dates are between two dates
function areDatesBetweenProcedureCodeBillRate(daterArray)
{

	var objOSD = eval("document.forms[0]."+daterArray[0]);//Outer Start date object
	var OSD = daterArray[0];//Outer Start date
	var OSDM = ' '+daterArray[1]+' ';//Outer Start date message

	var objISD = eval("document.forms[0]."+daterArray[2]);//Inner Start date object
	var ISD = daterArray[2];//Inner Start date
	var ISDM = ' '+daterArray[3]+' ';//Inner start date message

	var objIED = eval("document.forms[0]."+daterArray[4]);//Inner End date object
	var IED = daterArray[4];//Inner End date
	var IEDM = ' '+daterArray[5]+' ';//Inner End date message

	var objOED = eval("document.forms[0]."+daterArray[6]);//Outer End date object
	var OED = daterArray[6];//Outer End date
	var OEDM = ' '+daterArray[7]+' ';//Outer End date message

	var SD = 'systemDate';//System date
	var SDM = ' System Date ';//System date message

	var validActivityEndDate = false;
	var validActivityStartDate = false;

	//************ Begin Check if ISD and IED are between OSD and OED **********

	//Check if ISD is valid date
	if(!isDate(objISD,ISDM))
		return false;
	else
	{
		//alert(1)
		//check if OSD selected
		if(trim(objOSD.value).length >0)
		{
			//alert(2)
			//ISD should be greater than OSD
			if(compareDates(OSD,OSDM,ISD,ISDM,"less than"))
			{
				//alert(3);
				return false;
			}
		}//End OSD selected

		//if OED selected
		if(trim(objOED.value).length > 0)
		{
			//alert(4)
			//ISD should be less than OED
			if(compareDates(ISD,ISDM,OED,OEDM,"greater than"))
			{
				//alert(5);
				return false;
			}
		}//End OED selected
	}//End Valid ISD

	//if IED selected
	if(trim(objIED.value).length > 0)
	{
		//alert(8)
		//Check IED validity
		if(!isDate(objIED,IEDM))
		{
			//alert(9);
			return false;
		}
		else
		{
			//alert(10)
			//IED should be greater than ISD
			if(compareDates(ISD,ISDM,IED,IEDM,"less than"))
			{
				//alert(11);
				return false;
			}
			//check if OSD selected
			if(trim(objOSD.value).length >0)
			{
				//alert(12)
				//IED should be greater than OSD
				if(compareDates(OSD,OSDM,IED,IEDM,"less than"))
				{
					//alert(13);
					return false;
				}
			}//End OSD selected

			//if OED selected
			if(trim(objOED.value).length > 0)
			{
				//alert(14)
				//IED should be less than OED
				if(compareDates(IED,IEDM,OED,OEDM,"greater than"))
				{
					//alert(15);
					return false;
				}
			}//End OED selected
		}//Valid IED

	}//end of IED selected
	//************ End Check if ISD and IED are between OSD and OED **********

	return true;
}// end of function areDatesBetweenProcedureCodeBillRate(daterArray)

//It will check if the value exceded the maxlenght or not
function checkRange(obj,maxlength,displayName)
{

	var str=obj.value;
	str=trim(str);
	obj.value=str;
	var vflag=true;
	var str1 = str.indexOf(".");
	if(str1 != -1)
	{
		var aval = ((str.length-1) - str1);
		if(aval > 2 )
		{
			alert("Please enter a valid "+displayName);
			obj.select();
			obj.focus();
			return false;
		}
	}
	var inum = (parseInt(str));

	if((inum+"").length > maxlength)
	{
		//alert(displayName+" value should be less than or equal to "+maxlength+" digits");
		alert("Please enter a valid "+displayName);

		obj.select();
		obj.focus();
		vflag = false;
	}

	return vflag
}
//end of checkRange(obj,maxlength,displayName)

//It will disable the check box
function disableCheckBox(obj)
  {

   if(obj.checked)
    obj.checked=false;
   else
    obj.checked=true;
  }
  //end of disableCheckBox(obj)

//Function to check for integer value and not negative
function isPositiveInteger(obj,displayName)
{
	var str=obj.value;
	if(reInteger.test(str))
	{
		return true;
	}
	else
	{
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}
}//end of isPositive(obj,displayName)

//Function to check for integer value, blank and not negative
function isPositiveBlank(obj,displayName)
{
	var str=obj.value;
	str=trim(str);
	obj.value=str;
	var vflag=true;
	var inum = (obj.value - parseInt(str));
	if(inum==0)
	    inum    = obj.value;
	if(isNaN(str))
	{
		//alert(displayName+" value should be Numeric");
		alert("Please enter a valid "+displayName);

		obj.select();
		obj.focus();
		return false;
	}
	if(inum<0)
	{
		//alert(displayName+" value should not be negative");
		alert("Please enter a valid "+displayName);

		obj.select();
		obj.focus();
		vflag=false;
	}
	return vflag
}//end of isPositiveBlank(obj,displayName)


//Function to check for integer value and not negative
function isPositive(obj,displayName)
{
	var str=obj.value;
	str=trim(str);
	obj.value=str;
	var vflag=true;
	var inum = (obj.value - parseInt(str));
	if(inum==0)
	    inum    = obj.value;
	if(isNaN(str))
	{
		//alert(displayName+" value should be Numeric");
		alert("Please enter a valid "+displayName);

		obj.select();
		obj.focus();
		return false;
	}
	if((inum<0)||(str.length<=0))
	{
		//alert(displayName+" value should not be negative");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		vflag=false;
	}
	return vflag
}//end of isPositive(obj,displayName)


//Function to check Negative values
function isNegative(obj,displayName)
{
    var str=obj.value;
	str=trim(str);
	obj.value=str;
	var vflag=true;
    var inum = (obj.value - parseInt(str));
	if(inum==0)
	    inum  = obj.value;

	if(inum<0)
	{
		//alert(displayName+" value should not be negative");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		vflag=false;
	}
	return vflag;
}//end of isNegative(obj,displayName)


//Function to check for integer value and not negative if str may be blank
function isPositiveValue(obj,displayName)
{
	var str=obj.value;
	str=trim(str);
	obj.value=str;
	var vflag=true;
	var inum = (obj.value - parseInt(str));
	if(inum==0)
	    inum    = obj.value;
	if(isNaN(str))
	{
		//alert(displayName+" value should be Numeric");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}
	if(inum<0)
	{
		//alert(displayName+" value should not be negative");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		vflag=false;
	}
	return vflag
}//end of isPositiveValue(obj,displayName)

// function to check the Percentage values
function isPercentageCorrect(obj,displayName)
{
	var str=obj.value;
	str=trim(str);
	obj.value=str;
	var vflag=true;
	var inum = (obj.value - parseInt(str));
	if(inum==0)
	    inum    = obj.value;
	if(isNaN(str))
	{
		//alert(displayName+" value should be Numeric");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}//if(isNaN(str))
	if((inum<0)||(str.length<=0))
	{
		//alert(displayName+" value should not be negative");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		vflag=false;
	}//if((inum<0)||(str.length<=0))
	var str1 = str.indexOf(".");
	if(str1 != -1)
	{
		var aval = ((str.length-1) - str1);
		if(aval > 3 )
		{
			//alert("Can have only 2 digits after decimal point");
			alert("Please enter a valid "+displayName);
			obj.select();
			obj.focus();
			return false;
		}//if(aval > 3 )
	}//if(str1 != -1)
	if(inum > 100)
	{
		//alert(displayName+" should not be Greater than 100");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}//if(inum >= 1000)
	return vflag;
}// end of isPercentageCorrect(obj,displayName)

//***************************************************************************
//this method will check the passed date is with in the specified quarter or not
function isDateInQuarterRange(year,quarter,obj,displayname)
{
	obj = eval(obj);
	var receivedDate = obj.value;
	var list = new Array("06","09","00","03","06");
	var startyear 	= year;
	var endyear		=	year;
	if(parseInt(quarter,10)<3)
	{
		startyear = year-1;
	}

	if(parseInt(quarter,10)<2)
	{
		endyear   = year-1;
	}
	var startdate = new Date(startyear,list[quarter-1],01);
	var enddate = new Date(endyear,list[quarter],01);
	var vflag = getDateValue(receivedDate).valueOf() >= startdate.valueOf();
	var vflagend = getDateValue(receivedDate).valueOf() <= (enddate.valueOf()- 1000 * 60 * 60 * 24);
	if(vflag && vflagend)
	{
		return true;
	}
	else
	{
		//alert(displayname+" should be with in the selected quarter");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}
}
//This method checks whether the Billing Rate is valid number.
function isValidBillRateNumber(obj,displayName)
{
    if(isBlank(obj,displayName)== true ||
       isContainingWhiteSpace(obj)== true || isFloat(obj.value) == false )
    {
        return false;
    }
	var str=obj.value;
	str=trim(str);
	obj.value=str;
	var vflag=true;
	var inum = (parseInt(str,10));
	if((inum+"").length > 15)
	{
		//alert(displayName+" value should be lessthan 15 digits");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		vflag = false;
	}
	return vflag
}//end of isValidBillRateNumber(obj,displayName)

//function used for state config percentage where field is not mandatory
function isPercentageValue(obj,displayName)
{
	var str=obj.value;
	str=trim(str);
	obj.value=str;
	var vflag=true;
	var inum = (obj.value - parseInt(str));
	if(inum==0)
	    inum    = obj.value;
	if(isNaN(str))
	{
		//alert(displayName+" value should be Numeric");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}//if(isNaN(str))
	if((inum<0))
	{
		//alert(displayName+" value should not be negative");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		vflag=false;
	}//if((inum<0)||(str.length<=0))
	var str1 = str.indexOf(".");
	if(str1 != -1)
	{
		var aval = ((str.length-1) - str1);
		if(aval > 2 )
		{
			//alert("Can have only 2 digits after decimal point");
			alert("Please enter a valid "+displayName);
			obj.select();
			obj.focus();
			return false;
		}//if(aval > 2 )
	}//if(str1 != -1)
	if(inum >= 1000)
	{
		//alert(displayName+" should not be Greater than 999.99");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}//if(inum >= 1000)
	return vflag;
}//end of isPercentageValue(obj,displayName)

//function to delete an item from an array.
function arrayDelete(a, index)
{
    //alert("Removing the element at "+index);
    if (index == a.length)
    {
        // Just deleting from the end of the list
        --a.length;
         return a;
    }
    var new_list = new Array();
    var i, n;
    for (i=0; i < index; ++i)
    {
         new_list[i] = a[i];
    }
    for (n=index+1; n < a.length; ++n, ++i)
    {
         new_list[i] = a[n];
    }
        return new_list;
 }//end of arrayDelete (a, index)

//this method will check the passed date is with in the specified year or not
function isDateInYearRange(year,obj,displayname)
{
	obj = eval(obj);
	var receivedDate = obj.value;
	var list = new Array("06","09","00","03","06");
	var startyear 	= year-1;
	var endyear		=	year;
	var startdate = new Date(startyear,06,01);
	var enddate = new Date(endyear,06,01);
	var vflag = getDateValue(receivedDate).valueOf() >= startdate.valueOf();
	var vflagend = getDateValue(receivedDate).valueOf() <= (enddate.valueOf()- 1000 * 60 * 60 * 24);
	if(vflag && vflagend)
	{
		return true;
	}
	else
	{
		//alert(displayname+" should be with in the selected year");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}
	}

//function used for state config percentage where field is not mandatory and percentage not more than 100
function isPercentage(obj,displayName)
{
	var str=obj.value;
	str=trim(str);
	obj.value=str;
	var vflag=true;
	var inum = (obj.value - parseInt(str));
	if(inum==0)
	    inum    = obj.value;
	if(isNaN(str))
	{
		//alert(displayName+" value should be Numeric");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}//if(isNaN(str))
	if((inum<0))
	{
		//alert(displayName+" value should not be negative");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		vflag=false;
	}//if((inum<0)||(str.length<=0))
	var str1 = str.indexOf(".");
	if(str1 != -1)
	{
		var aval = ((str.length-1) - str1);
		if(aval > 2 )
		{
			//alert("Can have only 2 digits after decimal point");
			alert("Please enter a valid "+displayName);
			obj.select();
			obj.focus();
			return false;
		}//if(aval > 2 )
	}//if(str1 != -1)
	if(inum > 100)
	{
		//alert(displayName+" should not be Greater than 999.99");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}//if(inum >= 1000)
	return vflag;
}//end of isPercentage(obj,displayName)


//It will check if the Number is greater then specified length.
function checkNumberSize(obj,maxBeforeDec,maxAfterDec,displayName)
{

	var str=obj.value;
	str=trim(str);
	obj.value=str;
	var vflag=true;
	var str1 = str.indexOf(".");
	//alert(maxBeforeDec);
	//alert(maxAfterDec);
	if(str1 != -1)
	{
		var aval = ((str.length-1) - str1);
		if(aval > maxAfterDec )
		{
			//alert(displayName+" can have only " + maxAfterDec +" digits after decimal point");
			alert("Please enter a valid "+displayName);
			obj.select();
			obj.focus();
			return false;
		}
	}
	var inum = (parseInt(str));

	if((inum+"").length > maxBeforeDec)
	{
		//alert(displayName+" can have only " + maxBeforeDec +" digits before decimal point");
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		vflag = false;
	}

	return vflag;
}
//end of checkNumberSize(obj,maxBeforeDec,maxAfterDec,displayName)

/*
    This function check the two date.
    First date(Start Date) passesd should be always less than the second date(end Date)
    This function checks the dates only when non empty character is enterd.
    If empty character entered it returns true
 */
function isValidDateRangeIfPresent(obj1,obj2,firstDate,secondDate)
{
    // alert("inside isValidDateRangeIfPresent");
    var start = obj1.value.split("/");
    var last  = obj2.value.split("/");
    var daSt = obj1.value;
    var daEd = obj2.value;
    //create new date in yyyy,mm,dd format
    var startDate = new Date(start[2],start[0],start[1]);
    var lastDate = new Date(last[2],last[0],last[1]);
    var ONE_DAY = 1000 * 60 * 60 * 24;
    var date1_ms = startDate.getTime();
    var date2_ms = lastDate.getTime();
    var difference_ms = (date2_ms - date1_ms);
    // alert("difference_ms : "+difference_ms);

    if((daSt=="")&&(daEd==""))
    {
        //alert("1");
        return true;
    }
    if(Math.round(difference_ms/ONE_DAY) >= 0)
        return true;
    else
        {
        obj2.select();
		obj2.focus();
		if((daEd == ""))
        {
            //alert("2");
            return true;
        }
		alert(firstDate + " Cannot Exceed "+ secondDate);
		//alert("Please enter a valid "+firstDate);
        return false;
        }
} // function isValidDateRangeIfPresent(obj1,obj2)

/*
    This function checks if a number is positive
 */


function isPositiveNumber(obj,displayName)
{
	//var n = field.value;

	var str=obj.value;

	str=trim(str);
	obj.value=str;
	var vflag=true;
	if(isNaN(str))
	{
		
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		vflag=false;
	}
	else
		{
		if (str < 0)
	{
		//alert(String + " should be a positive number.");
		alert("Please enter a valid "+displayName);

		obj.select();
		obj.focus();
		vflag=false;
	}
}
	return vflag;
}

/*
    This function validates the zip field. When the zip suffix is
	entered, zip will become mandatory.
	@ param zip object,zip object's name,zip suffix object,zip suffix object's name
	@ returns false if not a zip
 */

function isValidZip(objZip,zipName,objZipSuffix,zipsuffixName)
{
	 if(objZip.value.length != 0)
	 {
			 if(
				isNumber(objZip,zipName,5)== false ||
				isWhole(objZip,zipName)== false ||
				isPositiveBlank(objZip,zipName)== false )
			 return false;
	 }
	 if(objZipSuffix.value.length != 0)
	 {
			 if(
				isNumber(objZipSuffix,zipsuffixName,4)== false ||
				isWhole(objZipSuffix,zipsuffixName)== false ||
				isPositiveBlank(objZipSuffix,zipsuffixName)== false )
			 return false;
	 }
	 if(objZipSuffix.value.length >0 && objZip.value.length == 0)
	 {
		//alert("Both Zip and  Zip Suffix fields should be entered");
		alert("Please enter a valid Zip and Zip Suffix");
		objZip.focus();
		return false;
	 }
	 return true;
}

/*
    This function validates whether the number is an integer. If not it
	will alert, the entered number should be a whole number
	@ param object,object's name
	@ returns false if not a whole number
 */
function isWhole(obj,displayname)
{
 if(isInteger(obj.value) == false)
 {
	//alert(displayname+" should be a whole number");
	alert("Please enter a valid "+displayname);
	obj.select();
	obj.focus();
	return false;
 } // end of if(isInteger(obj.value) == false)
} // end of function function isWhole(obj,displayname)
/*
    This function validates against the maximum length of a textarea.
	If the maxlen is exceeded, It will alert and bring the focus back
	@ param obj, objName, Maxlen
	@ returns false if the size of textarea exceeds the maximum allowed size
 */
function isSizeBeyondMaxlen(obj,maxlen,objName)
{

    if(obj.value.length>maxlen)
	{
	    alert(objName+" should not exceed "+ maxlen +" characters");
		//alert("Please enter a valid "+objName);
		obj.select();
		obj.focus();
		return true;
	}	// end of if
	return false;
}// end of function mDescLimit(objName,Maxlen)

function isBlankReturnTrue(obj)
{
	var str=obj.value;
	str=trim(str);
	obj.value=str;
	var vflag=false;
	if(str.length==0)
	{
		vflag=true;
	}
	return vflag;
}
// function to check Positive Integer
function isPositiveBlankIntegerB(obj,field_name)
{
   for (var i=0;i<obj.value.length;i++)
    {
	     if(isNaN(obj.value.charAt(i)))
	      {
	        //alert(field_name +" must have only numbers");
			alert("Please enter a valid "+field_name);
	         obj.focus();
	         return false;
	       }
    }
    return true;
}//isPositiveInteger(obj,field_name)


//Function to check for valid Bank and Check Number
function isCheckBankNo(obj,displayName)
{
	var str=obj.value;
/*	var flag=false;
	var iCount=0;

	for(var i=0;i<str.length;i++)
	{
		if(str.charAt(i)=='!' || str.charAt(i)=='@'  || str.charAt(i)=='$' ||
			str.charAt(i)=='%' || str.charAt(i)=='^' || str.charAt(i)=='&' ||
			str.charAt(i)=='(' || str.charAt(i)==')' || str.charAt(i)=='+' ||
			str.charAt(i)=='=' || str.charAt(i)=='~' || str.charAt(i)=='`' ||
			str.charAt(i)=='<' || str.charAt(i)=='>' || str.charAt(i)=='*' ||
			str.charAt(i)=='?' || str.charAt(i)=='[' || str.charAt(i)==']' ||
			str.charAt(i)=='{' || str.charAt(i)=='}' || str.charAt(i)=='|' || str.charAt(i)=='"' || str.charAt(i)=='\'' )

		{
			iCount=iCount+1;
			break;
		}

	}
*/
    if(strBank.test(str))
	{
   		return true;
   	}
	else
	{
	   	alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}


}//isCheckBankNo(obj,displayName)

//To check for Alphabets
function isAlpha(charval)
{

	var sResult = false;
	var flag = false;
	var sPredefinedSet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	for(var j=0; j<charval.length; j++)
	{
		var tempval=charval.charAt(j);
		for(var i=0; i<sPredefinedSet.length; i++)
		{
		    var temp=sPredefinedSet.charAt(i);
			if(tempval==temp)
			{
				sResult=true;
				break;
			}
			else
			{
				sResult=false;
			}

		}//end of inner for loop
		if(sResult==true)
		{
			flag=true;
			break;
		}
		else
		{
			flag=false;
		}
	}//outer for loop
	return flag;

}//end of isAlpha


//Function to check for valid DocketNo
function isDocketNo(obj,displayName)
{
	var str=obj.value;
	var flag=false;
	var iCount=0;
	for(var i=0;i<str.length;i++)
	{
		if(str.charAt(i)=='!' || str.charAt(i)=='@' || str.charAt(i)=='#' || str.charAt(i)=='$' ||
			str.charAt(i)=='%' || str.charAt(i)=='^' || str.charAt(i)=='&' || str.charAt(i)=='*' ||
			str.charAt(i)=='(' || str.charAt(i)==')' || str.charAt(i)=='+' ||
			str.charAt(i)=='=' || str.charAt(i)=='~' || str.charAt(i)=='`' || str.charAt(i)==',' ||
			str.charAt(i)=='<' || str.charAt(i)=='.' || str.charAt(i)=='>' || str.charAt(0)=='-' ||
			str.charAt(i)=='?' || str.charAt(i)=='\\' || str.charAt(i)=='[' || str.charAt(i)==']' ||
			str.charAt(i)=='{' || str.charAt(i)=='}' || str.charAt(i)=='|' || str.charAt(i)=='"' )

		{
			iCount=iCount+1;
			break;
		}

	}
	if(iCount <= 0)
	{
	   	return true;
	}
	else
	{
	   	alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}


}//isDocketNo(obj,displayName)

function isValidRole(obj,displayName)
{
	var s=obj.value;
	s=trim(s);
	obj.value=s;
	if(reRolesAlpha.test(s))
	{
   		return true;
   	}
	else
	{
		alert("Please enter a valid "+displayName);
		obj.focus();
		return false;
	}
}//end of function isValidRole(obj,displayName)

function isAlphabeticBlank(obj,displayName)
{
	var s=obj.value;
	if(!trim(obj.value).length > 0)
	{
	    return true;
	}
	if(reAlphabetic.test(s))
	{
   		return true;
   	}
	else
	{
		alert("Please enter a valid "+displayName);
		obj.select();
		obj.focus();
		return false;
	}
}//end of function isAlphabeticBlank(obj,displayName)

function compareDatesVal(StartDate,StartDateName,EndDate, EndDateName, comperator)
{

    var objStartDate = eval("document.forms[0]."+StartDate);
    var objEndDate = eval("document.forms[0]."+EndDate);
    if(objStartDate.value == '')return false;
    var dtStartDate = getDateValue(objStartDate.value);
    var dtEndDate = getDateValue(objEndDate.value);
    var ONE_DAY = 1000 * 60 * 60 * 24;
    var date1_ms = eval(dtStartDate).getTime();
    var date2_ms = eval(dtEndDate).getTime();
    var difference_ms = (date2_ms - date1_ms);
    if(Math.round(difference_ms/ONE_DAY) >= 0)
    {
	    return false;
    }// end of if(Math.round(difference_ms/ONE_DAY) >= 0)
    else
    {
	if(comperator == 'greater than')
		alert(StartDateName+" can not be "+comperator+" "+EndDateName);
	    else
		alert(EndDateName+" can not be "+comperator+" "+StartDateName);
	    return true;
    }// end of else
}// end of function compareDates(StartDate,StartDateName,EndDate, EndDateName, comperator)

function compareDatesFocus(StartDate,StartDateName,EndDate, EndDateName, comperator)
    {
	    var objStartDate = eval("document.forms[0]."+StartDate);
	    var objEndDate = eval("document.forms[0]."+EndDate);
	    var dtStartDate = getDateValue(objStartDate.value);
	    var dtEndDate = getDateValue(objEndDate.value);
	    var ONE_DAY = 1000 * 60 * 60 * 24;
	    var date1_ms = eval(dtStartDate).getTime();
	    var date2_ms = eval(dtEndDate).getTime();
	    var difference_ms = (date2_ms - date1_ms);
	    if(Math.round(difference_ms/ONE_DAY) >= 0)
	    {
		    return false;
	    }// end of if(Math.round(difference_ms/ONE_DAY) >= 0)
	    else
	    {
	        if(comperator == 'greater than')
	        {
		        alert(StartDateName+" can not be "+comperator+" "+EndDateName);
		        objStartDate.select();
		        objStartDate.focus() ;
		    }
		    else
		    {
		        alert(EndDateName+" can not be "+comperator+" "+StartDateName);
		        objStartDate.select();
		        objStartDate.focus() ;
		    }
		    return true;
	    }// end of else
    }// end of compareDatesFocus(StartDate,StartDateName,EndDate, EndDateName, comperator)

//***************************************************
//this function checks for the time in 12 hour format
//***************************************************
function isValidTime(obj)
{
	var passedValue=trim(obj.value);
	var timeArray=passedValue.split(":");

	if(timeArray.length!=2)
	{
		alert(" Enter the time in hh:mm format ");
		obj.focus();
		obj.select();
		return false;
	}
	var passedHour=timeArray[0];
	var passedMin=timeArray[1];

	if( isNaN(passedHour) || isNaN(passedMin))
	{
		alert(" Not a valid Time ");
		obj.focus();
		obj.select();
		return false;
	}
	if(passedHour.length==1)
		passedHour="0"+passedHour;
	if(passedMin.length==1)
		passedMin="0"+passedMin;

	var formattedTime=passedHour+":"+passedMin;

	obj.value=formattedTime;
	//var ObjectName=obj;
	//ObjectName=eval(ObjectName);

	da=formattedTime;

	var regexp=/^(\d{2}:\d{2})$/;

	if(regexp.test(obj.value)==false)
	{
		alert("Enter a valid time");
		obj.focus();
		obj.select();
		return false;
	}
	var hh=da.substr(0,2);
	var mm=da.substr(3,2);

	if((hh<00 || mm<00) ||(hh>12 || mm>59))
	{
		alert("Enter the time in 12 hour format");
		obj.focus();
		obj.select();
		return false;
	}
	return true;
}//end of isTime()

//***************************************************************
//THIS FUNCTION CHECKS FOR THE ALPHA NUMERIC CHARACTERS
//***************************************************************

function isAlphaNumeric(obj,fieldname)
{
	obj.value=trim(obj.value);

	var regexp=/^[a-zA-Z0-9]{1}[a-zA-Z0-9\s]*$/;

	if(regexp.test(obj.value)==false)
	{
		alert(fieldname+" should be AlphaNumeric");
		obj.focus();
		obj.select();
		return false;
	}
}
//***************************************************************
//THIS FUNCTION TO CONVERT THE CHARACTERS TO UPPER CASE
//***************************************************************
function ConvertToUpperCase()
{
	var iKeyCode = window.event.keyCode ;
	if (iKeyCode >= 97 && iKeyCode <= 122)
	{
	   window.event.keyCode = (iKeyCode - 32);
	}
}

//***************************************************************
//THIS FUNCTION WILL BLOCK THE ENTER KEY FOR THE FORM CONSISTING
// OF SINGLE TEXT FIELD
//***************************************************************
function blockEnterkey(cntrlId)
{
	if( window.event.keyCode == 13)
		window.event.keyCode = 0;
}

//********************************************************************
//This function checks and unchecks the sub checkboxes based on the Id
//********************************************************************
function checkAllById(chkbox,objform)
{
	var bchkId=chkbox.checked;
	var checkid=chkbox.id;
	for(i=0;i<objform.length;i++)
	{
		var regexp=new RegExp("("+checkid+"){1}[0-9]*");
		if(regexp.test(objform.elements[i].id))
		{
			objform.elements[i].checked=bchkId;
		}
	}
	return false;
}//end of checkAllById(chkbox,objform)

//*******************************************************************
//This checkbox checks or uncheks the current checkbox and makes
//decision for its parent checkboxs
//*******************************************************************
function toCheckBoxById(chkbox,objform)
{
	var checkid=chkbox.id;
	var checkParentid=checkid.substr(0,checkid.length-2);

	//check for the exit condition of the recursive function
	if(checkParentid == 'chk')
	{
		return;
	}//end of if(checkParentid == 'chk')

	//if checkbox is uncheked then call the function recursively
	// to uncheck its parent chekboxes
	if(chkbox.checked == false)
	{
		var parent=document.getElementById(checkParentid);
		parent.checked=false;
		toCheckBoxById(parent,objform)
	}//end of if(chkbox.checked == false)
	else
	{
		var bFlag = false;
		// regular expression to select its immediate child ids
		var regexp=new RegExp("^("+checkParentid+"){1}[0-9]{2}$");
		for(i=0;i<objform.length;i++)
		{
			if(regexp.test(objform.elements[i].id))
			{
				if(objform.elements[i].checked)
				{
					bFlag=true;
				}
				else
				{
					bFlag=false;
					break;
				}
			}//end of if(regexp.test(objform.elements[i].id))
		}//end of for(i=0;i<objform.length;i++)
		if(bFlag)
	    {
	       var parent=document.getElementById(checkParentid);
		   parent.checked=true;
		   toCheckBoxById(parent,objform);
		}//end of if(bFlag)
		else
		{
			return;
		}//end of else
	}//end of else
}//end of function toCheckBoxById(chkbox,objform)

//***************************************************************************
//This function trim the value of all the text box and textarea in the form
//@parm : HTML form object for which all the text box and textarea has to trim
//***************************************************************************
function trimForm(objForm)
{
	for(i=0;i<objForm.elements.length;i++)
	{
		field=objForm.elements[i];
		if(field.type == 'text' || field.type == 'textarea' )
			field.value=trim(field.value);
	}//end of for(i=0;i<objForm.elements.length;i++)
}//end of function trimForm()

//******************************************
// This function is a temporary function to
// avoid javascript error in product rule 
//******************************************
function toUpperCase(a1,a2,a3)
{

}//end of function toUpperCase()

//kocbroker
function appendURlSSRSReport(str)
{
	//StringBuffer sb=new StringBuffer();
	var tokens = "";
	for(var i=0;i<str.length;i++)
	{
	if(str.charAt(i) == ('|'))
	{
		 //sb.append("%7C");
		tokens	=	tokens+"%7C";
		
	}
	else if(str.charAt(i) == (' '))
	{
		tokens	=	tokens+"%20";
	}
	else if(str.charAt(i) == ('/'))
	{
		tokens	=	tokens+"%2F";
	}
	else if(str.charAt(i) == ('-'))
	{
		tokens	=	tokens+"%2D";
	}
	
	else if(str.charAt(i) == ('!'))
	{
		tokens	=	tokens+"%21";
	}
	else if(str.charAt(i) == ('"'))
	{
		tokens	=	tokens+"%22";
	}
	else if(str.charAt(i) == ('#'))
	{
		tokens	=	tokens+"%23";
	}
	else if(str.charAt(i) == ('$'))
	{
		tokens	=	tokens+"%24";
	}
	else if(str.charAt(i) == ('%'))
	{
		tokens	=	tokens+"%25";
	}
	else if(str.charAt(i) == ('&'))
	{
		tokens	=	tokens+"%26";
	}
	else if(str.charAt(i) == ('\''))
	{
		tokens	=	tokens+"%27";
	}
	else if(str.charAt(i) == ('('))
	{
		tokens	=	tokens+"%28";
	}
	else if(str.charAt(i) == (')'))
	{
		tokens	=	tokens+"%29";
	}
	else if(str.charAt(i) == ('*'))
	{
		tokens	=	tokens+"%2A";
	}
	else if(str.charAt(i) == ('+'))
	{
		tokens	=	tokens+"%2B";
	}
	else if(str.charAt(i) == (','))
	{
		tokens	=	tokens+"%2C";
	}
	else if(str.charAt(i) == ('.'))
	{
		tokens	=	tokens+"%2E";
	}
	else if(str.charAt(i) == (':'))
	{
		tokens	=	tokens+"%3A";
	}
	else if(str.charAt(i) == (';'))
	{
		tokens	=	tokens+"%3B";
	}
	else if(str.charAt(i) == ('<'))
	{
		tokens	=	tokens+"%3C";
	}
	
	else if(str.charAt(i) == ('='))
	{
		tokens	=	tokens+"%3D";
	}
	else if(str.charAt(i) == ('>'))
	{
		tokens	=	tokens+"%3E";
	}
	else if(str.charAt(i) == ('?'))
	{
		tokens	=	tokens+"%3F";
	}
	else if(str.charAt(i) == ('@'))
	{
		tokens	=	tokens+"%40";
	}
	else if(str.charAt(i) == ('['))
	{
		tokens	=	tokens+"%5B";
	}
	else if(str.charAt(i) == ('\\'))
	{
		tokens	=	tokens+"%5C";
	}
	
	else if(str.charAt(i) == (']'))
	{
		tokens	=	tokens+"%5D";
	}
	else if(str.charAt(i) == ('^'))
	{
		tokens	=	tokens+"%5E";
	}
	else if(str.charAt(i) == ('_'))
	{
		tokens	=	tokens+"%5F";
	}
	else if(str.charAt(i) == ('`'))
	{
		tokens	=	tokens+"%60";
	}
	else if(str.charAt(i) == ('{'))
	{
		tokens	=	tokens+"%7B";
	}
	else if(str.charAt(i) == ('}'))
	{
		tokens	=	tokens+"%7D";
	}
	else if(str.charAt(i) == ('~'))
	{
		tokens	=	tokens+"%7E";
	}
	else
	{
		tokens	=	tokens+str.charAt(i);
	}
	}//for
	tokens	=	tokens+"&rs%3AParameterLanguage=en-US";
	return tokens;
}

function validateEmail(obj,property)
{
	//alert(property);
	//var email = document.getElementById("email").value;
	var email=obj.value;
	if(email!="")
	{
    var atpos = email.indexOf("@");
    var dotpos = email.lastIndexOf(".");
    if (atpos<1 || dotpos<atpos+2 || dotpos+2>=email.length) 
    {
        alert("Not a valid e-mail address");
        obj.value="";
        document.getElementById(property).focus();
        return false;
    }
	}
}