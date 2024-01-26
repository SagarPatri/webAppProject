// java script function for clausedetails.jsp in preauth

function checkClause()
{
	if(document.forms[1].applicable.length)
	{
		for(var i=0;i<document.forms[1].applicable.length;i++)
		{
			if(document.forms[1].applicable[i].checked)
			{
				return false;
			}//end of if(document.forms[1].applicable[i].checked)
		}//end of for(var i=0;i<document.forms[1].applicable.length;i++)
	}//end of if(document.forms[1].applicable.length)
	else
	{
		if(document.forms[1].applicable.checked)
		{
			return false;
		}//end of if(document.forms[1].applicable.checked)
	}//end of else
	return true;
}//end of checkClause()

function onGenerateReport()
{

	if(TC_GetChangedElements().length>0)
	{
		alert("Please save the modified data, before Generating Letter");
		return false;
	}//end of if(TC_GetChangedElements().length>0)
	if(checkClause())
	{
		alert('Please save data, before Generating Letter');
		return false;
	}//end of if(clauseflag)
	var parameterValue= document.forms[1].seqId.value;
	document.forms[1].mode.value="doGenerateReport";
	var partmeter = "?mode=doGenerateRejectionReport&parameter="+parameterValue+"&reportID=RejectionLetter"+"&reportType=PDF";
	var openPage = "/ReportsAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
	//document.forms[1].submit();
}//end of onGenerateReport()

function onSave()
{
	 trimForm(document.forms[1]);
	 if(checkClause())
	 {
	 	alert('Please select atleast one Clause');
		return false;
	 }
	 if(document.forms[0].leftlink.value=='Claims'){
	 	if(isSizeBeyondMaxlen(document.forms[1].rejFooterInfo,2000,"Footer")==true)
	    {
	    	return false;
	    }//end of if(isSizeBeyondMaxlen(document.forms[1].rejFooterInfo,2000,"Footer")==true)
		if(isSizeBeyondMaxlen(document.forms[1].rejHeaderInfo,2000,"Header")==true)
	    {
	    	return false;
	    }//end of if(isSizeBeyondMaxlen(document.forms[1].rejHeaderInfo,2000,"Header")==true)   
	 	if(document.forms[1].letterTypeID.value=="")
	 	{
	 		alert('Please select Letter to be generated for');
	 		document.forms[1].letterTypeID.focus();
			return false;
	 	}//end of if(document.forms[1].letterTypeID.value=="")
	 }//end of if(document.forms[0].leftlink.value=='Claims')
	 var Id="";
	 var selectedId="";
	 var flag=false;
	 if(document.forms[1].applicable.length)
	 {
		for(var i=0;i<document.forms[1].applicable.length;i++)
		 {
			if(document.forms[1].applicable[i].checked)
			{
				Id+=document.forms[1].applicable[i].value;
				Id+="|";
				flag=true;
			}//end of if(document.forms[1].applicable[i].checked)
		 }//end of for(var i=0;i<document.forms[1].applicable.length;i++)
	 }else
	 {
		 if(document.forms[1].applicable.checked)
		 {
			Id=document.forms[1].applicable.value;
			Id+="|";
			flag=true;
		 }
	 }
	 if(flag)
	 {
		selectedId+="|"+Id;
	 }
	 if(!JS_SecondSubmit)
	 {
	 	document.forms[1].clauseIds.value=selectedId;
		document.forms[1].mode.value="doSaveClause";
		document.forms[1].action="/SaveSelectRejectoinClause.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)	
}//end of onSave()

//function to re-set the screen
function onReset()
{
    document.forms[1].reset();

}//end of onReset()

function onClose()
{
	document.forms[1].mode.value="doCloseClause";
	document.forms[1].action="/SelectRejectoinClause.do";
	document.forms[1].submit();
}