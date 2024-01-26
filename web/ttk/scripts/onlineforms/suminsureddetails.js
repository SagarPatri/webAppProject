// to check whether one of the checkbox is checked or not
function mChkboxValidation(obj)
{
 	var iFlag ="";
	with(obj)
	{
		for(var i=0;i<elements.length;i++)
		{
			if(elements[i].name=="chkopt")
			{
				if (elements[i].checked)
				{
					iFlag = 1;
					break;
				}
				else
				{
					iFlag = 0;
				}
			}
		}
	}

	if (iFlag == 0)
	{
		alert('Please select atleast one record');
		return true;
	}
	else
	{
		return false;
	}
}//end of mChkboxValidation(obj)

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doDefault";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/AdditionalSumInsuredDetailsAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to Save the details
/*function onSave()
{	if(!mChkboxValidation(document.forms[1]))
	{
	if(document.getElementById('chkAccept').checked==true){
	document.forms[1].mode.value = "doSave";
	document.forms[1].action = "/SaveAdditionalSumInsuredDetailsAction.do";
	document.forms[1].submit();
	}
	else {
		alert('Please select Declaration');
	}
	}//end of if(!mChkboxValidation(document.forms[1])
	
}//end of onSave()*/

//function to Save the details
//Modified for Oracle check box Declaration 
function onSave()
{	if(!mChkboxValidation(document.forms[1]))
	{
	var group = document.forms[1].group.value; 
	
		if(group=="O0099" || group=="H0324" || group=="A0951")
		{
			
			document.forms[1].mode.value="doSave";
			document.forms[1].action="/AdditionalSumInsuredDetailsAction.do";
			document.forms[1].submit();
		}
			else
			{
				
				if(document.getElementById('chkAccept').checked==true)
				{
					document.forms[1].mode.value = "doSave";
					document.forms[1].action = "/SaveAdditionalSumInsuredDetailsAction.do";
					document.forms[1].submit();
				}
				else {
				alert('Please select Declaration');
				}		
			}	
	
	}//end of if(!mChkboxValidation(document.forms[1])
	
}//end of onSave()

// Change added for KOC1227C
//function to Save the details
function onSaveAcct()
{	if(!mChkboxValidation(document.forms[1]))
	{
		if((document.getElementById('chkpredeductopt1').checked==true || document.getElementById('chkpredeductopt2').checked==true))
		{
			document.forms[1].mode.value = "doSave";
			document.forms[1].action = "/SaveAdditionalSumInsuredDetailsAction.do";
			document.forms[1].submit();
		}
		else {
			alert('Please select Declaration');
		}
	}//end of if(!mChkboxValidation(document.forms[1])
	
}//end of onSaveAcct()


/*function onSelect()
{
	if(!mChkboxValidation(document.forms[1]))
	{
		if(document.getElementById('chkAccept').checked==true){
		document.forms[1].mode.value="doSelect";
		document.forms[1].action="/AdditionalSumInsuredDetailsAction.do";
		document.forms[1].submit();
		}
		else {
			alert('Please select Declaration');
		}
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onSelect()*/


////Modified for Oracle check box Declaration
function onSelect()
{
	if(!mChkboxValidation(document.forms[1]))
	{
		var group = document.forms[1].group.value; 
		
		if(group=="O0099")
		{
			
			document.forms[1].mode.value="doSelect";
			document.forms[1].action="/AdditionalSumInsuredDetailsAction.do";
			document.forms[1].submit();
		}
		else 
		{
			
			if(document.getElementById('chkAccept').checked==true)
			{
				document.forms[1].mode.value="doSelect";
				document.forms[1].action="/AdditionalSumInsuredDetailsAction.do";
				document.forms[1].submit();
			}
			else {
				alert('Please select Declaration');
			}
		}
		
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onSelect()

// Change added for KOC1227C
//function to Select the details
function onSelectAcct()
{
	if(!mChkboxValidation(document.forms[1]))
	{
		if((document.getElementById('chkpredeductopt1').checked==true || document.getElementById('chkpredeductopt2').checked==true))
		{
			document.forms[1].mode.value = "doSelect";
			document.forms[1].action = "/AdditionalSumInsuredDetailsAction.do";
			document.forms[1].submit();
		}
		else {
			alert('Please select Declaration');
		}
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onSelectAcct()

// Change added for KOC1227C
//function to uncheck the checkbox
function uncheckSecond(obj)
{
	if (obj.checked == true)             
	{
		document.getElementById("chkpredeductopt2").checked = false; 
	} 
}//end of uncheckSecond()

//function to uncheck the checkbox
function uncheckFirst(obj)
{
	if (obj.checked == true)             
	{
		document.getElementById("chkpredeductopt1").checked = false; 
	} 
}//end of uncheckFirst()

//function to close and move to previous screen
function onClose()
{
	document.forms[1].mode.value="doClose";
	document.forms[1].action = "/AdditionalSumInsuredDetailsAction.do";
	document.forms[1].submit();
	
}//end of onClose()



//this function will select or deselect all the check boxex available in the form 
//based on the boolean value passed to the method
function selectAll( bChkd, objform )
{
	//Overridden. Nothing needed to implement for this.
}

//this is to hide check box to check all the records
//and called each time when page gets loaded
function onHideSelectAllBox()
{
	document.getElementById('chkAll').style.display="none";
	var obj = document.forms[1];
	var iFlag ="";
	for(var i=0;i<obj.elements.length;i++)
		{
			if(obj.elements[i].name=="chkopt")
			{
				if (obj.elements[i].checked)
				{
					iFlag = 1;
					break;
				}
				else
				{
					iFlag = 0;
				}
			}
		}
	if(iFlag==1)
	{
		//in Sum Insured Plan Select screen Clear Button is not there
		//so this should not be manipulated
		if(document.getElementById('clearButton')!=null){
			document.getElementById('clearButton').style.display="";
		}//end of if(document.getElementById('clearButton')!=null)
	}//end of if(iFlag==1)	
}//end of onHideSelectAllBox()

function onClearPlan()
{
	document.forms[1].mode.value="doClearAdditionalSum";
	document.forms[1].action = "/ClearAdditionalSumInsuredDetailsAction.do";
	document.forms[1].submit();
}//end of onDeleteAddSumInsured()















