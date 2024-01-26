//java script for the Suspension list screen in enrollment flow
	function toggle(sortid)
	{
		document.forms[1].reset();
	    document.forms[1].mode.value="doSearch";
	    document.forms[1].sortId.value=sortid;
	    document.forms[1].action = "/SuspensionAction.do";
	    document.forms[1].submit();
	}//end of toggle(sortid)
	function isValidated()
	{
		if(isDate(document.forms[1].startDate,"From Date")==false)
		{
			return false;
		}// end of if(isDate(document.forms[1].startDate,"From Date")==false)
		if(isDate(document.forms[1].endDate,"To Date")==false)
		{
			return false;
		}// end of if(isDate(document.forms[1].endDate,"To Date")==false)
		if(compareDates("startDate","From Date","endDate","To Date",'greater than')==false)
		{
			return false;
		}// end of if(compareDates("startDate","From Date","endDate","To Date",'greater than')==false)
		return true;
	}// end of isValidated()
	function onSave()
	{
		if(isValidated())
		{
			if(!JS_SecondSubmit)
			{
			    document.forms[1].mode.value="doSave";
			    document.forms[1].action = "/SuspensionAction.do";
			    JS_SecondSubmit=true
			    document.forms[1].submit();
			}
	    }//end of if(isValidated())
	}//end of onSave()
	function edit(rownum)
	{
	    document.forms[1].mode.value="doViewSuspension";
	    document.forms[1].rownum.value=rownum;
	    document.forms[1].action = "/SuspensionAction.do";
	    document.forms[1].submit();
	}//end of edit(rownum)
	function onDelete()
	{
	    if(!mChkboxValidation(document.forms[1]))
	    {
		var msg = confirm("Are you sure you want to delete the selected Suspension List ?");
		if(msg)
		{
		    document.forms[1].mode.value = "doDeleteList";
		    document.forms[1].action = "/SuspensionAction.do";
		    document.forms[1].submit();
		}//end of if(msg)
	    }//end of if(!mChkboxValidation(document.forms[1]))
	}//end of onDelete()
	function onClose()
	{
		if(!TrackChanges()) return false;

		document.forms[1].mode.value="doReset";
		document.forms[1].action = "/EditMemberDetailAction.do";
		document.forms[1].submit();
	}//end of onClose()
	
	// Function to calculate_days_months.
	function calculate_days_months(){
	    if(document.forms[1].startDate.value != '' &&  isDate(document.forms[1].startDate,"From Date")== true)
	    {
	    if(document.getElementById("drOfPeriod") && document.getElementById("drOfPeriod").value != '' && isNumeric(document.getElementById("drOfPeriod")))
	    {
		var selVal = document.forms[1].drOfPeriod.value;
		var FromDate = document.forms[1].startDate.value;
		var dateArray = FromDate.split("/");
		
		var passedDay = dateArray[0];
		var passedMonth = dateArray[1];
		var passedYear = dateArray[2];
	
		var  dateStr = passedMonth+"/"+passedDay +"/"+passedYear;
		var myDate=new Date(dateStr);
		
		if(document.forms[1].drOfPeriodType.value == "d")
			// add Days
			myDate.setDate(myDate.getDate()+eval(selVal));
		else
			// add Months
			myDate.setMonth(myDate.getMonth()+eval(selVal));
	
		var yrStr =eval(myDate.getFullYear());
		var newMonth = getActualMonth(myDate);
		var newDate = getDate(myDate.getDate())+"/"+newMonth+"/"+yrStr;
		document.forms[1].endDate.value=newDate;
		}
		}// end of  if(document.forms[1].startDate.value != '')
	}//Edn of Function to calculate_days_months	
	
	function getDate(theDate)
    {
    var dateCtr="";
    var thedate="";
    thedate=theDate+"";
    if(thedate.length >= 2)
    {
     dateCtr = theDate ;
    }
    else
    {
    dateCtr = "0"+theDate;
	}
    return dateCtr;
    }
/*  Added by Unni */
	function getActualMonth(theDate)
	{
		var monthCtr="";
	if(eval(theDate.getMonth())==0) 
			monthCtr="01";
	else if(eval(theDate.getMonth())==1) 
			monthCtr="02";
	else if(eval(theDate.getMonth())==2) 
			monthCtr="03";
	else if(eval(theDate.getMonth())==3) 
			monthCtr="04";
	else if(eval(theDate.getMonth())==4) //feb
			monthCtr="05";
	else if(eval(theDate.getMonth())==5) //feb
			monthCtr="06";
	else if(eval(theDate.getMonth())==6) //feb
			monthCtr="07";
	else if(eval(theDate.getMonth())==7) //feb
			monthCtr="08";
	else if(eval(theDate.getMonth())==8) //feb
			monthCtr="09";
	else if(eval(theDate.getMonth())==9) //feb
			monthCtr="10";
	else if(eval(theDate.getMonth())==10) //feb
			monthCtr="11";
	else if(eval(theDate.getMonth())==11) //feb
			monthCtr="12";
		return monthCtr;
		}
/* End of Addition */

	function isNumeric(field) {
         var re = /^[0-9][0-9]*$/;
         if (!re.test(field.value)) {
            alert("Period should be a Numeric value");
 			field.focus();
 			field.select();
 			return false;
         }
         return true;
    }