//javascript file for the Associate Product Screen of the Administration module

function onDocumentLoad()
{
	isChecked(document.getElementById('H'));
	isChecked(document.getElementById('R'));
	isChecked(document.getElementById('D'));
	isChecked(document.getElementById('B'));
	isChecked(document.getElementById('I'));
	isChecked(document.getElementById('G'));
	isChecked(document.getElementById('C'));
	isChecked(document.getElementById('N'));
}//end of onDocumentLoad()

function onAssociateOffice(strOfficeType)
{
	document.forms[1].strOfficeType.value=strOfficeType;
	document.forms[1].mode.value="doAssociateOffice";
	document.forms[1].action="/AssociatedProductAction.do";
	document.forms[1].submit();
}//end of onAssociateOffice(strOfficeType)

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
  		document.forms[1].mode.value="doReset";
		document.forms[1].action="/AssociatedProductAction.do";
		document.forms[1].submit();
 	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
 	else
 	{
  		document.forms[1].reset();
  		onDocumentLoad();
 	}//end of else
}//end of Reset()

 function onAssociateExecute()
 {
 	var message;
	message=confirm('Are you sure you want to Associate the Product?');
	if(message)
	{
		if(!JS_SecondSubmit)
        {
		 	trimForm(document.forms[1]);
		 	document.forms[1].mode.value="doAssociateExecute";
			document.forms[1].action="/ExecuteAssociateProductAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	}//end of if(message)
 }//end of onAssociateExecute()
 
 function isChecked(objElement)
{
	var id=objElement.id;
	var blnChecked= objElement.checked;
	
	switch (id)
	{
		case "H":
					if(blnChecked)
					{
						document.forms[1].headOfficeEffDate.disabled=false;
						document.getElementById('headDate').style.visibility='visible';
					}//end of if(blnChecked)
					else
					{
						document.forms[1].headOfficeEffDate.disabled=true;
						document.forms[1].headOfficeEffDate.value="";
						document.getElementById('headDate').style.visibility='hidden';
					}//end of else
					break
		case "R":
					if(blnChecked)
					{
						document.forms[1].regOfficeEffDate.disabled=false;
						document.getElementById('regImg').style.visibility='visible';
						document.getElementById('regDate').style.visibility='visible';
					}//end of if(blnChecked)
					else
					{
						document.forms[1].regOfficeEffDate.disabled=true;
						document.forms[1].regOfficeEffDate.value="";
						document.forms[1].regOfficeSeqIds.value="";
						document.getElementById('regImg').style.visibility='hidden';
						document.getElementById('regDate').style.visibility='hidden';
					}//end of else
					break
		case "D":
					if(blnChecked)
					{
						document.forms[1].divOfficeEffDate.disabled=false;
						document.getElementById('divImg').style.visibility='visible';
						document.getElementById('divDate').style.visibility='visible';
					}//end of if(blnChecked)
					else
					{
						document.forms[1].divOfficeEffDate.disabled=true;
						document.forms[1].divOfficeEffDate.value="";
						document.forms[1].divOfficeSeqIds.value="";
						document.getElementById('divImg').style.visibility='hidden';
						document.getElementById('divDate').style.visibility='hidden';
					}//end of else
					break
		case "B":
					if(blnChecked)
					{
						document.forms[1].branchOfficeEffDate.disabled=false;
						document.getElementById('brchImg').style.visibility='visible';
						document.getElementById('brchDate').style.visibility='visible';
					}//end of if(blnChecked)
					else
					{
						document.forms[1].branchOfficeEffDate.disabled=true;
						document.forms[1].branchOfficeEffDate.value="";
						document.forms[1].branchOfficeSeqIds.value="";
						document.getElementById('brchImg').style.visibility='hidden';
						document.getElementById('brchDate').style.visibility='hidden';
					}//end of else
					break
		case "I":
					if(blnChecked)
					{
						document.forms[1].indServiceCharges.disabled=false;
					}//end of if(blnChecked)
					else
					{
						document.forms[1].indServiceCharges.disabled=true;
						document.forms[1].indServiceCharges.value="";
					}//end of else
					break
		case "G":
					if(blnChecked)
					{
						document.forms[1].ingServiceCharges.disabled=false;
					}//end of if(blnChecked)
					else
					{
						document.forms[1].ingServiceCharges.disabled=true;
						document.forms[1].ingServiceCharges.value="";
					}//end of else
					break
		case "C":
					if(blnChecked)
					{
						document.forms[1].corServiceCharges.disabled=false;
					}//end of if(blnChecked)
					else
					{
						document.forms[1].corServiceCharges.disabled=true;
						document.forms[1].corServiceCharges.value="";
					}//end of else
					break
		case "N":
					if(blnChecked)
					{
						document.forms[1].ncrServiceCharges.disabled=false;
					}//end of if(blnChecked)
					else
					{
						document.forms[1].ncrServiceCharges.disabled=true;
						document.forms[1].ncrServiceCharges.value="";
					}//end of else
					break
	}//end of switch
}//end of isChecked(objElement)