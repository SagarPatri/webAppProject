//java script for the card rules screen in the administration of products flow.

//JavaScript onClose() function
function onClose()
{
	if(!TrackChanges()) return false;
	if(document.forms[0].tab.value=='Products')	
	{
		document.forms[1].child.value="";
	}//end of if(document.forms[0].tab.value=='Products')	
    document.forms[1].mode.value="doClose";
    
    document.forms[1].action = "/CardRulesAction.do";
    document.forms[1].submit();
}// End of onClose

//JavaScript onSave() function
function onSave()
{
	for(var i=0;i<document.forms[1].generalTypeId.length;i++)
	{
		if(document.forms[1].generalTypeId[i].value=="")
		{
		alert("Please Select "+document.forms[1].cardRuleDesc[i].value);
		document.forms[1].generalTypeId[i].focus();
		return false;
		}//end of if(document.forms[1].generalTypeId[i].value=="")
	}//end of for
	
	if(!JS_SecondSubmit)
	{
		var productorpolicyFlag = document.getElementById("cardRuleForid").value;
		document.forms[1].mode.value="doSave";
		
		if(productorpolicyFlag=="Policies"){
			document.forms[1].action = "/CardRulesActionForPolicy.do";	
		}else{
	    document.forms[1].action = "/CardRulesAction.do";
		}
	    JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
} // End of onSave

//JavaScript onReset() function
function onReset()
{
	document.forms[1].reset();
}// End of onReset

//JavaScript onSelect() function
function onSelect()
{
    document.forms[1].mode.value="doEnrollTypeChanged";
    document.forms[1].enrollType.value=document.forms[1].enrollTypeCode.value;
    document.forms[1].action = "/CardRulesAction.do";
    document.forms[1].submit();
} // End of onSelect

function onGeneralTypeID(strID)
{
	var selVal = strID.options[strID.selectedIndex].value;
	if(selVal =="SRY")
	{
		document.getElementById("remarks"+strID.id).style.display="";
	}//end of if(selVal =="SRY")
	else
	{
		document.getElementById("remarks"+strID.id).style.display="none";
		document.getElementById("remarks"+strID.id).value="";
	}//end of else
}//end of onGeneralTypeID(strID)
