//java script for generateindividual.js
function finendyear(sFinancialyear)
{
	var i = 1;
	i = i+ parseInt(sFinancialyear);
 	if(i >0)
	{
		document.forms[1].sFinYearTo.value = i;
  	}//end of if(i >0)
}//end of finendyear(sFinancialyear)

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/CertificateGenAction.do";
	document.forms[1].submit();
}//end of onClose()

function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/GenerateIndividual.do";
    document.forms[1].submit();
}//end of toggle(sortid)
	
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/GenerateIndividual.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/GenerateIndividual.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/GenerateIndividual.do";
    document.forms[1].submit();
}//end of PressBackWard()


function onSearch()
{
	if (!JS_SecondSubmit) {
		trimForm(document.forms[1]);
		if(!(parseInt(document.forms[1].sFinancialyear.value) > 2008 && (document.forms[1].sFinancialyear.value != "" )))
		{
			alert('Please enter a valid Financial Year');
			document.forms[1].sFinancialyear.focus();
			document.forms[1].sFinancialyear.select();
			return false;
		}//end of if(parseInt(document.forms[1].financeYear.value) > 2008)
		else if(document.forms[1].tdsbatchQtr.value == "")
		{
			alert("Please select the Frequency");
			document.forms[1].tdsbatchQtr.focus();
			return false;
		}//end of else if(document.forms[1].tdsbatchQtr.value == "")
		else
		{
			document.forms[1].mode.value = "doSearch";
			document.forms[1].action = "/GenerateIndividual.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
		}//end of else
	}//end of if (!JS_SecondSubmit)	  		
}//end of onSearch()

function onGenerate()
{
	if(!mChkboxValidation(document.forms[1]))
	{
		document.forms[1].mode.value = "doGenerate";
		document.forms[1].action = "/GenerateIndividual.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}
}//end of onGenerate()