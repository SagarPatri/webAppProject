//java script called by addbonus.jsp in enrollment flow
function planChange()
{
	if(!document.forms[1].planTypeValue.options[document.forms[1].planTypeValue.selectedIndex].value=="")
	{
		var plan=document.forms[1].planTypeValue.options[document.forms[1].planTypeValue.selectedIndex].value;
		var planArray=plan.split("#");
		document.forms[1].sumInsured.value=planArray[1];
		document.forms[1].productPlanSeqID.value=planArray[0];
		document.forms[1].sumInsured.readOnly=true;
	}//end of if(!document.forms[1].planTypeValue.options[document.forms[1].planTypeValue.selectedIndex].value=="")
	else
	{
		document.forms[1].sumInsured.value="";
		document.forms[1].productPlanSeqID.value="";
		document.forms[1].sumInsured.readOnly=false;
		document.forms[1].bonusAmt.value="";
	}//end of else
}

function onSave()
{
	onInterest();
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/SaveBonusAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}
}//end of onSave()

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/AddBonusAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()
function onInterest()
{

	regexp=/^([0])*\d{0,12}(\.\d{1,2})?$/;
	regexp1=/^([0])*\d{0,2}(\.\d{1,2})?$/;

	if(document.forms[1].type.options[document.forms[1].type.selectedIndex].value==1)
	{
		if(trim(document.forms[1].sumInsured.value).length>0&&trim(document.forms[1].hidbonamt.value).length>0)
		{
			if(regexp.test(document.forms[1].sumInsured.value)==true&&regexp1.test(document.forms[1].hidbonamt.value)==true)
			{

				var sum=trim(document.forms[1].sumInsured.value);
				var int=trim(document.forms[1].hidbonamt.value);
				var bonamt=(sum*int)/100;
				bonamt=bonamt.toFixed(2);
				document.forms[1].bonus.value=trim(document.forms[1].hidbonamt.value);
				//document.forms[1].hidBonusAmt.value=bonamt;
				document.forms[1].bonusAmt.value=bonamt;

			}//end of if(regexp.test(document.forms[1].sumInsured.value)==true&&regexp1.test(document.forms[1].hidbonamt.value)==true)
			else
			{
				document.forms[1].bonus.value=trim(document.forms[1].hidbonamt.value);
			}//end else if(regexp.test(document.forms[1].sumInsured.value)==true&&regexp1.test(document.forms[1].hidbonamt.value)==true)
		}//end of if(trim(document.forms[1].sumInsured.value).length>0&&trim(document.forms[1].hidbonamt.value).length>0)
		else if(trim(document.forms[1].hidbonamt.value).length>0)
		{
			document.forms[1].bonus.value=trim(document.forms[1].hidbonamt.value);
		}//end of else if(trim(document.forms[1].hidbonamt.value).length>0)
		else
		{
			document.forms[1].bonusAmt.value="";
			//document.forms[1].hidbonamt.value="";
			document.forms[1].bonus.value="";
		}//end of else if(trim(document.forms[1].sumInsured.value).length>0&&trim(document.forms[1].hidbonamt.value).length>0)
	}//end of if(document.forms[1].type.options[document.forms[1].type.selectedIndex].value==1)
	else if(document.forms[1].type.options[document.forms[1].type.selectedIndex].value==2)
	{


		if(trim(document.forms[1].hidbonamt.value).length>0&&regexp.test(document.forms[1].hidbonamt.value)==true)
		{
			//document.forms[1].hidBonusAmt.value=trim(document.forms[1].bonus.value);
			document.forms[1].bonusAmt.value=trim(document.forms[1].hidbonamt.value);
			document.forms[1].bonus.value="";
		}else
		{
			document.forms[1].bonusAmt.value=trim(document.forms[1].hidbonamt.value);
		}//end of if(trim(document.forms[1].hidbonamt.value).length>0&&regexp.test(document.forms[1].hidbonamt.value)==true)

	}//end of else if(document.forms[1].type.options[document.forms[1].type.selectedIndex].value==2)
	else if(document.forms[1].type.options[document.forms[1].type.selectedIndex].value==0)
	{
			document.forms[1].bonus.value="";
			document.forms[1].bonusAmt.value="";
	}//end of else if(document.forms[1].type.options[document.forms[1].type.selectedIndex].value==0)
}//end of onInterest()


function showhideBonus(selObj)
{
	var selVal = selObj.options[selObj.selectedIndex].value;
	if(selVal == '2'){
	   document.forms[1].hiddenvalue.value=document.getElementById("currencyId").value;
	   document.forms[1].bonus.value="";
	   document.forms[1].bonusAmt.value="";
	   document.forms[1].hidbonamt.value="";
	   document.forms[1].hidbonamt.readOnly=false;
	   }//end of 	if(selVal == '2')
	else if(selVal == '1'){
	   document.forms[1].hiddenvalue.value="%";
	   document.forms[1].bonusAmt.value="";
	   document.forms[1].hidbonamt.value="";
	   document.forms[1].hidbonamt.readOnly=false;
	   }//end of else if(selVal == '1')
	   else if(selVal == '0'){
	   document.forms[1].hiddenvalue.value="";
	   document.forms[1].bonus.value="";
	   document.forms[1].bonusAmt.value="";
	   document.forms[1].hidbonamt.readOnly=true;
	   document.forms[1].hidbonamt.value="";
	   }//end of else if(selVal == '0')
}//end of showhideBonus(selObj)

function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value="doSearch";
	document.forms[1].action="/BonusAction.do";
	document.forms[1].submit();
}//end of onClose()