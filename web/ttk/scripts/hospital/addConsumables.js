//as per intX 

function onAddConsumables()
{
	 //document.forms[1].leftlink.value ="Claims";
    document.forms[1].mode.value="doConsumablesAdd";
    document.forms[1].action = "/OnlineConsumablesAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onUnitPrice(obj,iVal)
{
	var qty	=	document.getElementById("quantity"+iVal).value;
		
	document.getElementById("gross"+iVal).value = obj.value*qty;
}

function onQuantity(obj,iVal)
{
	alert("obj.value::"+obj.value);
	var unitPrice	=	document.getElementById("unitPrice"+iVal).value;
	
	document.getElementById("gross"+iVal).value = obj.value*unitPrice;
}

function ApplyDisc()
{
	var discs	=	document.getElementById("overAllDisc").value;
	//discs	=	(discs/100);
	if(discs=="")
	{
		alert("Please Enter OverAll Discount.");
		document.getElementById("overAllDisc").focus();
		return false;
	}
	var grossNames	=	document.getElementsByName("gross");
	for(var i=1;i<=grossNames.length;i++)
	{
		
		document.getElementById("discountedGross"+(i-1)).value	=	document.getElementById("gross"+(i-1)).value-(document.getElementById("gross"+(i-1)).value * (discs/100));
		document.getElementById("discount"+(i-1)).value			=	document.getElementById("gross"+(i-1)).value * (discs/100);
	}
}

function onCalcGross()
{
	var unitPrice	=	document.forms[1].addUnitPrice.value;
	var quantity	=	document.forms[1].addQuantity.value;
	//alert("unitPrice::"+unitPrice+"---quantity::"+quantity);
	document.forms[1].addGross.value	=	unitPrice*quantity;
}
function onCalcDiscGross(obj)
{
	var gross	=	document.forms[1].addGross.value;
	document.forms[1].addDiscountedGross.value	=	gross-(obj.value/100)*gross;
}
function onCalcPatientShare(obj)
{
	var discountedGross	=	document.forms[1].addDiscountedGross.value;
	document.forms[1].addNetAmount.value	=	discountedGross-obj.value;
}


function onCloseConsumables()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].mode.value="doCloseLabs";
		document.forms[1].action = "/AddConsumablesToPrescriptionAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}

function onNextConsumables()
{
	document.forms[1].mode.value="doNextConsumables";
	document.forms[1].action = "/AddConsumablesToPrescriptionAction.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
}