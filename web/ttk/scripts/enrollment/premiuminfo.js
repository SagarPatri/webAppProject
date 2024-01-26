//**************************************************************************
//This function is called for the onchange event of memberPolicy Type for
//policy sub type Floater+NonFloater.
//This will enable and disable other fields for the record based on the value
//***************************************************************************
function onFloatNonFloatChange(obj,index)
{
	var val=obj.value;
	if(document.forms[1].memberSeqID.length)	//if more than one members exists
	{
		if(val =='PFL')	//if member policy sub type is floater
		{
			if(document.forms[1].totalSumInsured[index].value!="" && parseFloat(document.forms[1].totalSumInsured[index].value)>0)
			{
				alert("Sum Insured is already present");
				document.getElementById("memberPolicyType"+index).value="PNF";
				return false;
			}
			document.forms[1].premiumPaid[index].readOnly=true;
			document.forms[1].premiumPaid[index].value="";
			document.forms[1].sumInsured[index].value="";
			document.forms[1].totalSumInsured[index].value="";
			document.forms[1].premiumPaid[index].className='textBoxDisabled textBoxSmall';
			document.getElementById("premiumimg"+index).style.display = "none";
		}
		else	//if member policy sub type is non floater
		{
			document.forms[1].premiumPaid[index].readOnly=false;
			document.forms[1].premiumPaid[index].className='textBox textBoxSmall';
			document.getElementById("premiumimg"+index).style.display = "";
		}
	}
	else			//if only one member exists for the policy
	{
		if(val =='PFL')
		{
			if(document.forms[1].totalSumInsured.value!="" && parseFloat(document.forms[1].totalSumInsured.value)>0)
			{
				alert("Sum Insured is already present");
				document.getElementById("memberPolicyType"+index).value="PNF";
				return false;
			}
			document.forms[1].premiumPaid.readOnly=true;
			document.forms[1].premiumPaid.value="";
			document.forms[1].sumInsured.value="";
			document.forms[1].totalSumInsured.value="";
			document.forms[1].premiumPaid.className='textBoxDisabled textBoxSmall';
			document.getElementById("premiumimg"+index).style.display = "none";
		}
		else
		{
			document.forms[1].premiumPaid.readOnly=false;
			document.forms[1].premiumPaid.className='textBox textBoxSmall';
			document.getElementById("premiumimg"+index).style.display = "";
		}
	}
	enableFloaterPremium();	//to enable or disable floater premium amount field
}

//**************************************************************************
//This function is called for the onchange event of memberPolicy Type for
//policy sub type Floater+FloaterRestriction.
//This will enable and disable other fields for the record based on the value
//***************************************************************************
function onFloatRestrictChange(obj,index)
{
	var val=obj.value;
	if(document.forms[1].memberSeqID.length)	//if more than one members exists
	{
		if(val =='PFL')
		{
			if(document.forms[1].totalSumInsured[index].value!="" && parseFloat(document.forms[1].totalSumInsured[index].value)>0)
			{
				alert("Sum Insured is already present");
				document.getElementById("memberPolicyType"+index).value="PFR";
				return false;
			}
			document.forms[1].premiumPaid[index].readOnly=true;
			document.forms[1].premiumPaid[index].value="";
			document.forms[1].sumInsured[index].value="";
			document.forms[1].totalSumInsured[index].value="";
			document.forms[1].premiumPaid[index].className='textBoxDisabled textBoxSmall';
			document.getElementById("premiumimg"+index).style.display = "none";
		}
		else
		{
			document.forms[1].premiumPaid[index].readOnly=false;
			document.forms[1].premiumPaid[index].className='textBox textBoxSmall';
			document.getElementById("premiumimg"+index).style.display = "";
		}
	}
	else			//if only one member exists for the policy
	{
		if(val =='PFL')
		{
			if(document.forms[1].totalSumInsured.value!="" && parseFloat(document.forms[1].totalSumInsured.value)>0)
			{
				alert("Sum Insured is already present");
				document.getElementById("memberPolicyType"+index).value.value="PFR";
				return false;
			}
			document.forms[1].premiumPaid.readOnly=true;
			document.forms[1].premiumPaid.value="";
			document.forms[1].sumInsured.value="";
			document.forms[1].totalSumInsured.value="";
			document.forms[1].premiumPaid.className='textBoxDisabled textBoxSmall';
			document.getElementById("premiumimg"+index).style.display = "none";
		}
		else
		{
			document.forms[1].premiumPaid.readOnly=false;
			document.forms[1].premiumPaid.className='textBox textBoxSmall';
			document.getElementById("premiumimg"+index).style.display = "";
		}
	}
	enableFloaterPremium();	//to enable or disable floater premium amount field
}

//**********************************************************************
//This function enables and disables the floater premium amount field
//for policy subtype floater+nonfloater and floater with restriction
//based on member policy sub type
//**********************************************************************
function enableFloaterPremium()
{
	var blnFloaterEnabled=false;
	if(document.forms[1].memberSeqID.length)	//if more than one members exists
	{
		for(i=0;i<document.forms[1].memberSeqID.length;i++)
		{
			if(document.getElementById("memberPolicyType"+i).value=="PFL" && document.forms[1].cancelYN[i].value!="Y" )	//checks for atleast one member with policysubtype floater
			{
				blnFloaterEnabled=true;
				break;		//found break the loop
			}
		}
	}
	else			//if only one member exists for the policy
	{
		if(document.getElementById("memberPolicyType0").value=="PFL" && document.forms[1].cancelYN.value!="Y")
			blnFloaterEnabled=true;
	}
	if(!blnFloaterEnabled)		//if none of the active members are having member policy subtype as floater
	{
		document.forms[1].floaterPremium.readOnly=true;
		document.forms[1].floaterPremium.value="";
		document.forms[1].floaterPremium.className='textBoxDisabled textBoxSmall';
	}
	else
	{
		document.forms[1].floaterPremium.readOnly=false;
		document.forms[1].floaterPremium.className='textBox textBoxSmall';
	}
}//end of enableFloaterPremium()

//****************************************************************************
//This function is called when page is loaded to disable and enable different
//Fields in form based on member policy subtype.
//It also shows/hides sum insured image based on the member policy subtype
//****************************************************************************
function onDocumentLoad()
{
	var policysubtype=document.forms[1].policySubTypeId.value;
	if(policysubtype=='PFN' || policysubtype=='PFR')
	{
		enableFloaterPremium();		//to enable or disable floater premium amount field
	}
	if(document.forms[1].memberSeqID.length)	//if more than one members exists
	{

		for(i=0;i<document.forms[1].memberSeqID.length;i++)
		{
			if(document.getElementById("memberPolicyType"+i).value=="PFL")	//checks for member with policysubtype floater
			{
				document.forms[1].premiumPaid[i].readOnly=true;
				document.forms[1].premiumPaid[i].className='textBoxDisabled textBoxSmall';
				if(document.forms[1].cancelYN[i].value!="Y" && (policysubtype=='PFN' || policysubtype=='PFR'))
				{
					document.getElementById("premiumimg"+i).style.display = "none";		//to hide the sum insured image
				}
			}
		}
	}
	else
	{
		if(document.getElementById("memberPolicyType0").value=="PFL")	//checks for member with policy subtype floater
		{
			document.forms[1].premiumPaid.readOnly=true;
			document.forms[1].premiumPaid.className='textBoxDisabled textBoxSmall';
			if(document.forms[1].cancelYN.value!="Y" && (policysubtype=='PFN' || policysubtype=='PFR'))
			{
				document.getElementById("premiumimg0").style.display = "none";	//to hide the sum insured image
			}
		}
	}
}//end of onDocumentLoad()

//**************************************************************************
//This function is called when suminsured icon is clicked to calculate the
//sum insured for the selected member
//***************************************************************************
function onCalcSumInsured(index)
{
	trimForm(document.forms[1]);
	var memberPolicyType=document.getElementById("memberPolicyType"+index);
	if(document.forms[1].memberSeqID.length)		//if more than one members exists for policy
	{
		if(memberPolicyType.value != 'PFL')
		{
			document.forms[1].selectedmemberSeqID.value=document.forms[1].memberSeqID[index].value;
			document.forms[1].selectedmemberName.value=document.forms[1].name[index].value;
			document.forms[1].mode.value="doViewBonusInfo";
			document.forms[1].child.value="Bonus";
			document.forms[1].action="/PremiumInfoAction.do";
			document.forms[1].submit();
		}
	}
	else		//if only one member exists for the policy
	{
		if(memberPolicyType.value != 'PFL')
		{
			document.forms[1].selectedmemberSeqID.value=document.forms[1].memberSeqID.value;
			document.forms[1].selectedmemberName.value=document.forms[1].name.value;
			document.forms[1].mode.value="doViewBonusInfo";
			document.forms[1].child.value="Bonus";
			document.forms[1].action="/PremiumInfoAction.do";
			document.forms[1].submit();
		}
	}
}

//************************************************************************
//This function is called when save button is clicked to save preium info
// for the policy
//************************************************************************
function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
    {
	//added as perr KOC 1284 Change Request
	if(document.forms[1].selectregionYN.value !="N" && document.forms[1].selectregionYN.value !="")
		{
			if(document.forms[1].selectregion.value=="")
			{
			alert("Please select Premium Region");
			return false;
			}
		}
		document.forms[1].mode.value="doSubmitPremiumInfo";
		document.forms[1].action="/SubmitPremiumInfoAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

//This function is called when close button is pressed to go to members list screen
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/MemberAction.do";
	document.forms[1].submit();
}

//This function is called when reset button is pressed
function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/PremiumInfoAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}

//*****************************************************************
//This function is called when Floater sum insured icon is pressed
//to calculate the floater sum insured for the members whose policy
// Sub type is floater
//*****************************************************************
function onCalcFloaterSumInsured()
{
	trimForm(document.forms[1]);
	var blnFltSumInsured = false;
	var floaterSum=document.forms[1].floaterSumInsured.value;

	if(document.forms[1].memberSeqID.length)		//if more than one member exists for the policy
	{
		for(i=0;i<document.forms[1].memberSeqID.length;i++)
		{
			if(document.getElementById("memberPolicyType"+i).value == 'PFL' && document.forms[1].cancelYN[i].value!="Y")
			{
				blnFltSumInsured=true;
				break;
			}
		}
	}
	else			//if only one member exists for the policy
	{
		if(document.getElementById("memberPolicyType0").value == 'PFL' && document.forms[1].cancelYN.value!="Y")
		{
			blnFltSumInsured=true;
		}
	}
	if(blnFltSumInsured)	//allowed to calculate floater Sum insured
	{
		document.forms[1].mode.value="doViewBonusInfo";
		document.forms[1].child.value="Bonus";
		document.forms[1].action="/PremiumInfoAction.do";
		document.forms[1].submit();
	}
	else if(parseFloat(floaterSum)>0)	//allowed to calculate the floater sum if he wants
	{
		document.forms[1].mode.value="doViewBonusInfo";
		document.forms[1].child.value="Bonus";
		document.forms[1].action="/PremiumInfoAction.do";
		document.forms[1].submit();
	}
}
//***********************************************************************
//This function calculates the total family premium Automatically when
// each premiumPaid field of member losts the focus.
//User can Over ride the clculated Premium Paid
//***********************************************************************
function calcFamilyPremium()
{
	var familyPremium = 0;
	var policysubtype=document.forms[1].policySubTypeId.value;
	regexp=/^([0])*\d{0,10}(\.\d{1,2})?$/;
	if(policysubtype == 'PNF')			// for policy-sub type NonFloater
	{
		if(document.forms[1].memberSeqID.length)
		{
			var obj=document.forms[1].elements['premiumPaid'];
			for(i=0;i<obj.length;i++)
			{
				if(document.forms[1].cancelYN[i].value!="Y" && obj[i].value!="" && regexp.test(obj[i].value))
				familyPremium= familyPremium + parseFloat(obj[i].value);
			}
		}
		else
		{
			if(document.forms[1].cancelYN.value!="Y" && document.forms[1].premiumPaid.value!="" && regexp.test(document.forms[1].premiumPaid.value))
				familyPremium= familyPremium + parseFloat(document.forms[1].premiumPaid.value);
		}
		document.forms[1].totalFlyPremium.value=familyPremium;
	}
	else if(policysubtype == 'PFR' || policysubtype == 'PFN') //for Floater+NonFloater and Floater+FloaterRestriction
	{
		var fltPremiumAmt=document.forms[1].floaterPremium.value;
		if(document.forms[1].memberSeqID.length)
		{
			var obj1=document.forms[1].elements['premiumPaid'];
			for(i=0;i<obj1.length;i++)
			{
				if(document.forms[1].cancelYN[i].value!="Y" && document.getElementById("memberPolicyType"+i).value!='PFL' && obj1[i].value!="" &&  regexp.test(obj1[i].value))
				familyPremium= familyPremium + parseFloat(obj1[i].value);
			}
		}
		else
		{
			if(document.forms[1].cancelYN.value!="Y" && document.getElementById("memberPolicyType0").value != 'PFL' && document.forms[1].premiumPaid.value!="" && regexp.test(document.forms[1].premiumPaid.value)==true)
			{
				familyPremium= familyPremium + parseFloat(document.forms[1].premiumPaid.value);
			}
		}
		if(fltPremiumAmt!="" && regexp.test(fltPremiumAmt) && parseFloat(fltPremiumAmt) > 0)
			familyPremium= familyPremium + parseFloat(fltPremiumAmt);
		document.forms[1].totalFlyPremium.value=familyPremium;
	}
}
