//java script for the tariff details screen in the Administration of hospital flow.

function onSearch()
{
	
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
	    document.forms[1].mode.value = "doSearchTariffItems";
	    document.forms[1].action = "/ShowSearchModifyTariffItems.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function UpdateDiscount()
{
	/*
	 * S T A R T
	 * THIS CODE COMMENTED, REMOVING THE MULTIPLE SELECTION OF PRICEREFNUMB AND TARIFS
	 * var commonClass	=	document.forms[1].elements["commonClass"];
	var ids			=	"";
	var resArray	=	new Array();
	for(var k=0;k<commonClass.length;k++)
	{
		if((commonClass[k].type	==	"text") && commonClass[k].value!="")
			ids	=	ids+"@"+commonClass[k].value+"#";
		
		else if(commonClass[k].type	==	"checkbox")
			if(commonClass[k].checked)
				ids	=	ids+"|"+commonClass[k].value+"|";
	}
	resArray.push(ids);
	document.forms[1].selectedIds.value	=	ids;
	E N D HERE
	*/
	
	if(document.forms[1].tariffTypeSearch.value=="")
	{
		alert("Please select Type of Tariff");
		document.forms[1].tariffTypeSearch.focus();
		return false;
	}
	
	/*
	 * select atleast one record  S T A R T
	 */
	var elements	=	document.forms[1].elements;
	var iFlag		=	"";
	for(var i=0;i<elements.length;i++)
	{
		if(elements[i].name == "chkopt")
		{
			if (elements[i].checked)
			{
				iFlag = 1;
				break;
			}//end of if (objform.elements[i].checked)
			else
			{
				iFlag = 0;
			}//end of else
		}//end of if(objform.elements[i].name == "chkopt")
	}//end of for(var i=0;i<elements.length;i++)
	if (iFlag == 0)
	{
		alert('Please select atleast one record');
		return false;
	}//end of if (iFlag == 0)
	/*
	 * select atleast one record  E N D S
	 */
	if(document.forms[1].discAmount.value=="")
	{
		alert("Please Enter Discount");
		document.forms[1].discAmount.focus();
		return false;
	}
	
	if(isPercentageCorrect(document.forms[1].discAmount,"Discount")==false)
	  	 return false;
	
	if(document.forms[1].discAmount.value>100 || document.forms[1].discAmount.value<0)
	{
		alert("Discount Percentage must be between 0 and 100%");
		document.forms[1].discAmount.focus();
		return false;
	}
	if(!JS_SecondSubmit)
	 {
	    trimForm(document.forms[1]);
	    document.forms[1].mode.value = "doModifyTariffItems";
	    document.forms[1].action = "/ModifyTariffItems.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}

//function to check only positive numbers
function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : evt.keyCode;
   alert(charCode);
   if (charCode != 46 && charCode > 31 && (charCode < 48 || charCode > 57))
      return false;
   return true;
}

function validateDiscount()
{
    /*var pattern = /^-?[0-9]+(.[0-9]{1,2})?$/;
    var text = document.forms[1].discAmount.value;
    if (text.match(pattern)==null)
    {
		alert('Please Enter only Numbers');
		document.forms[1].discAmount.value	=	"";
    }*/
	
}
function onBack()
{
	document.forms[1].mode.value="doDefault";
    document.forms[1].tab.value="Search";
    document.forms[1].action = "/TarrifSearchAction.do";
    document.forms[1].submit();
}

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearchTariffItems";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/ShowTariffItems.do";
    document.forms[1].submit();
}//end of toggle(sortid)
//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearchTariffItems";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/ShowTariffItems.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)
//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/ShowTariffItems.do";
    document.forms[1].submit();
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/ShowTariffItems.do";
    document.forms[1].submit();
}//end of PressForward()
//function to display pages based on search criteria