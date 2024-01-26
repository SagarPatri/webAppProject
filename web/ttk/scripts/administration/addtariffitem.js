//java script for the add tariff item screen in the administration of tariff plan flow.

//function to delete selected associated procedure
function deleteAsscProc()
{	
    if(document.frmTariffItem.asscProcedure.selectedIndex >0)
    {
        if(document.frmTariffItem.asscProcedure.options[0].selected != true)
        {  
            var msg = confirm("Are you sure you want to delete the selected procedure ?");
            if(msg)
            {
                ClientReset=false;
                var splitProcedure = document.frmTariffItem.asscProcedure.options[document.frmTariffItem.asscProcedure.options.selectedIndex].value.split("|");
                if(document.frmTariffItem.deleteSeqId.value == "")
                    {
                        document.frmTariffItem.deleteSeqId.value = "|"+splitProcedure[0]+"|";						
                     }
                    else
                    {
                        document.frmTariffItem.deleteSeqId.value = document.frmTariffItem.deleteSeqId.value+splitProcedure[0]+"|";                        
                    }
                document.frmTariffItem.asscProcedure.options[document.frmTariffItem.asscProcedure.options.selectedIndex]=null;      
            }//end of if(msg)
        }//end of if(document.frmTariffItem.asscProcedure.options[0].selected != true) 
    }//end of if(document.frmTariffItem.asscProcedure.selectedIndex < 0 )
    else
    {
        alert("Please select a associated procedure to delete");
    }//end of else
}//end of deleteAsscProc()
function onAddEditTariffItem()
{
    document.forms[1].mode.value = "UpdateTariffItem";
    document.forms[1].action = "/EditUserAction.do";
    document.forms[1].submit();
}//end of onAddEditTariffItem()
//function to cancel the screen
function onCancel()
{
    document.forms[1].mode.value="doClose";  
    document.forms[1].child.value="";  
    document.forms[1].action = "/EditTarrifItemAction.do";
    document.forms[1].submit();
}//end of onCancel()
function onReset()
{
 
 if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
	   document.forms[1].mode.value="doReset";
	   document.forms[1].action = "/EditTarrifItemAction.do";
	   document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()
function onTypeChanged()
{
	 document.forms[1].mode.value = "doTypeChanged";
	 document.forms[1].action = "/EditTarrifItemAction.do";
	 document.forms[1].submit();	
}//end of onTypeChanged()
function onSave()
{
   var strAsscCode="";
   if(document.frmTariffItem.asscProcedure!=null)
   {
		   if(document.frmTariffItem.asscProcedure.length>1)
			{
				strAsscCode="|";
				for(i=1;i<document.frmTariffItem.asscProcedure.length;i++)
				{
					strAsscCode+=document.frmTariffItem.asscProcedure.options[i].value;
					strAsscCode+="|";
				}		 
			}
		}   
   document.forms[1].tariffItemName.value=trim(document.forms[1].tariffItemName.value.toUpperCase());
   document.forms[1].tariffItemDescription.value=trim(document.forms[1].tariffItemDescription.value);
   document.forms[1].selectedProcedureCode.value=strAsscCode
   if(!JS_SecondSubmit)
   {
   		document.forms[1].mode.value = "doSave";
   		document.forms[1].action = "/SaveTarrifItemAction.do";
   		JS_SecondSubmit=true
   		document.forms[1].submit();
   	}//end of if(!JS_SecondSubmit)	
}//end of onSave()
function onAssociate()
{
   document.forms[1].mode.value = "doAssociate";
   document.forms[1].child.value="Procedure";
   document.forms[1].action = "/EditTarrifItemAction.do";
   document.forms[1].submit();   
}//end of onAssociate()
function showHidePackageType(selObj)
{
	var selVal = selObj.options[selObj.selectedIndex].value;	
	var chkobj = document.getElementById("chkMedicalPackageYn");
	var dagreeObj = document.getElementById("NonPackage");
	dagreeObj.style.display="none";		
	if(selVal == 'NPK')
	{
		dagreeObj.style.display="";	
		showHideProcedures(chkobj);
	}
	else
	{
		listPro.style.display="";
		document.forms[1].medicalPackageYn.value="N";
		chkobj.checked=false;
	}
}
function showHideProcedures(obj_checkbox)
{
     if(obj_checkbox.checked)
       { 
       	   listPro.style.display="none";
	       document.forms[1].medicalPackageYn.value="Y";
       }
       else
       {
       	listPro.style.display="";       	
       	document.forms[1].medicalPackageYn.value="N";
       }
}//end of changeState(obj_checkbox)