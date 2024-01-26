//java script for the bank details screen

function Reset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/BankDetailsAction.do";
		document.forms[1].submit();
 	}
 	else
 	{
  		document.forms[1].reset();
 	}
}//end of Reset()

function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/SaveBankDetailsAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

// function to close the bank details screen.
function Close()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].tab.value="Search";
	document.forms[1].action="/BankTreeListAction.do";
	document.forms[1].submit();
}//end of Close()

function onChangeCity()
{
	document.forms[1].mode.value="doStateChange";
	document.forms[1].focusID.value="state";
	document.forms[1].action="/BankDetailsAction.do";
	document.forms[1].submit();
}//end of onChangeCity()

function onChangeCountry()
{
	document.forms[1].mode.value="doChangeCountry";
	document.forms[1].focusID.value="state";
	document.forms[1].action="/BankDetailsAction.do";
	document.forms[1].submit();
}//end of onChangeCountry()

function showBankFile(obj)
{
	var openPage = "/ReportsAction.do?mode=doViewCommonForAll&module=financeBanks&fileName="+obj;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}



function changeMe(obj)
{
	var val	=	obj.value;
	//alert(val);
	if(val=='Phone No')
	{
		document.forms[1].officePhone1.value="";
	}
	if(val=='ISD')
	{
		document.forms[1].isdCode.value="";
	}
	if(val=='STD')
	{
		document.forms[1].stdCode.value="";
	}
}
function getMe(obj)
{
	//alert(obj);
	
	if(obj=='ISD')
	{
		if(document.forms[1].isdCode.value=="")
			document.forms[1].isdCode.value=obj;
	}
	if(obj=='STD')
	{
		if(document.forms[1].stdCode.value=="")
			document.forms[1].stdCode.value=obj;
	}if(obj=='Phone No')
	{
		if(document.forms[1].officePhone1.value=="")
			document.forms[1].officePhone1.value=obj;
	}
	
	
}
