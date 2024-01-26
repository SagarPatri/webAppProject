//java script for the tariff details screen in the empanelment of hospital flow.
function onBack()
{
	document.forms[1].mode.value="doDefaultEmpnlTariff";
    document.forms[1].action = "/TariffActionEmpanelment.do";
    document.forms[1].submit();
}

function onSaveTariffItem()
{
	/*if(isPositiveInteger(document.forms[1].discAmount,"Discount")==false)
	  	 return false;*/
	if(document.forms[1].grossAmount.value=='')
	{
		alert("Please Enter Gross Amount");
		return false;
	}
	if(document.forms[1].discPercentage.value=='' || document.forms[1].discAmount.value=='')
	{
		alert("Either Discount Percent or Amount Should Enter");
		return false;
	}
	
	if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value="doSaveTariffItem";
	    document.forms[1].action = "/SaveTarrifActionEmpanelment.do";
		JS_SecondSubmit=true;
	    document.forms[1].submit();
    }
}

function calcDiscount(obj)
{
	var val	=	obj.value;
	if(document.forms[1].grossAmount.value=='')
	{
		alert("Please Enter Gross Amount");
		return false;
	}
	var discAmount	=	(val/100)*document.forms[1].grossAmount.value;
	document.forms[1].discAmount.value	=	discAmount;
}

function calcDiscountOnAmt(obj)
{
	var val	=	obj.value;
	if(document.forms[1].grossAmount.value=='')
	{
		alert("Please Enter Gross Amount");
		return false;
	}
	document.forms[1].discPercentage.value	=	(val*100)/document.forms[1].grossAmount.value;
}


//end date validation
function endDateValidation()   
{
	var startDate =document.getElementById("startDate").value;    	
    var endDate=document.getElementById("endDate").value;				
    
    if( !((document.getElementById("startDate").value)==="") && !((document.getElementById("endDate").value)===""))
   	{
        var sdate = startDate.split("/");
      	var altsdate=sdate[1]+"/"+sdate[0]+"/"+sdate[2];
        altsdate=new Date(altsdate);
        
        var edate =endDate.split("/");
        var altedate=edate[1]+"/"+edate[0]+"/"+edate[2];
        altedate=new Date(altedate);
        
        var Startdate = new Date(altsdate);
        var EndDate =  new Date(altedate);
        
        if(EndDate < Startdate)
       	 {
       	 	alert("End Date should be greater than or equal to Start Date");
       		document.getElementById("endDate").value="";
       		return ;
       	 }
   	} 
}												
