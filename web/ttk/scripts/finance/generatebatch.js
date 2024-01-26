//Java Script file for generatebatch.jsp

function finendyear(financeYear)
{
	var i = 1;
	i = i+ parseInt(financeYear);
 	if(i >0)
	{
		document.forms[1].finYearTo.value = i;
  	}//end of if(i >1)
}//end of finendyear(financeYear)

function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action.value="/GenerateBatchAction.do";
	document.forms[1].submit();
}//end of onClose

//function for sorting.
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doGenerateBatch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/CertificateGenAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the details.
function onReassociate(rownum)
{
	var msg=confirm("Are you sure you want to Regenerate the Batch?")
	if(msg){
		document.forms[1].mode.value="doRegenerateBatch";
		document.forms[1].rownum.value=rownum;
		document.forms[1].action="/CertificateGenAction.do";
		document.forms[1].submit();
	}//end of if(msg)
}//end of onReassociate(rownum)

function onGenerate()
{
	if(!JS_SecondSubmit)
	
	{
	 var dt= new Date();
	 var year= dt.getYear();
	 if(!((parseInt(document.forms[1].financeYear.value) <= year) && (parseInt(document.forms[1].financeYear.value)>2008) && (document.forms[1].financeYear.value != "") ))
		{
		  alert('Please enter a valid Financial Year');
		  document.forms[1].financeYear.focus();
		  document.forms[1].financeYear.select();
		  return false;
		}//end of if(!((parseInt(document.forms[1].financeYear.value) <= year) && (parseInt(document.forms[1].financeYear.value)>2008) && (document.forms[1].financeYear.value != "") ))
		else if(document.forms[1].tdsbatchQtr.value == "")
		{
		  alert("Please select the Frequency");
		  document.forms[1].tdsbatchQtr.focus();
		  return false;
		}//end of else if(document.forms[1].tdsbatchQtr.value == "")
		else
		{
		  document.forms[1].mode.value="doGenerateBatch";
		  document.forms[1].action.value="/UpdateGenerateBatchAction.do";
		  JS_SecondSubmit=true
		  document.forms[1].submit();
		}//end of else
	}//end of if(!JS_SecondSubmit)	
}//end of onGenerate
