
function showhideTariffItem()
{
  if(document.getElementById("tariffItemVO.tariffItemType")){
	  var selVal= document.forms[1].elements['tariffItemVO.tariffItemType'].value;
	  if(selVal == 'NPK')
	  {
	    //document.getElementById("procedures").style.display="";
	    document.getElementById("package").style.display="none";
	  }//end of if(selVal == 'NPK')
	  else
	  {
	    document.getElementById("package").style.display="";
	    //document.getElementById("procedures").style.display="none";
	  }//end of else
	}
}// end of showhideTariffItem(selObj)

function showhideHospType()
{
  if(document.getElementById("hospitalTypeID")){
	  var selVal=document.forms[1].elements.hospitalTypeID.value;
	  if(selVal == 'REP')
	  {
	    document.getElementById("FV").style.display="";
	    document.getElementById("NV").style.display="";
	  }//end of if(selVal == 'REP')
	  else
	  {
	    document.getElementById("FV").style.display="none";
	    document.getElementById("NV").style.display="none";
	  }//end of else
  }
}//end of showhideHospType(selObj)

function showhideTariffType()
{
  if(document.getElementById("treatmentPlanTypeID")){
	  var selVal=document.forms[1].elements.treatmentPlanTypeID.value;
	  if(selVal == 'SUR')
	  {
	    document.getElementById("Tariff").style.display="";
	  }//end of if(selVal == 'SUR')
	  else
	  {
	    document.getElementById("Tariff").style.display="none";
	  }//end of else
	}
}//end of showhideTariffType(selObj)

//funtion onSave
function onSave()
{
   trimForm(document.forms[1]);
   var strAsscCode="";
   if(document.frmICDPCSCoding.asscProcedure!=null)
   {
     if(document.frmICDPCSCoding.asscProcedure.length>1)
    {
      strAsscCode="|";
      for(i=1;i<document.frmICDPCSCoding.asscProcedure.length;i++)
      {
        strAsscCode+=document.frmICDPCSCoding.asscProcedure.options[i].value;
        strAsscCode+="|";
      }//end of for
    }//end of if(document.frmICDPCSCoding.asscProcedure.length>1)
  }// end of if(document.frmICDPCSCoding.asscProcedure!=null)
  document.forms[1].selectedProcedureCode.value=strAsscCode;
  if(!JS_SecondSubmit){
  	document.forms[1].mode.value="doSave";
  	if(document.forms[0].leftlink.value == 'Coding')
  	document.forms[1].action="/SaveCodeICDPCSCodingAction.do";
  	else
	document.forms[1].action="/SaveICDPCSCodingAction.do";  	
  	JS_SecondSubmit=true
  	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)	
}//end of onSave()

function onClose()
{
  if(!TrackChanges()) return false;
  document.forms[1].mode.value="doClose";
  document.forms[1].child.value="";
  document.forms[1].action="/ICDPCSCodingAction.do";
  document.forms[1].submit();
}//end of onClose()

function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
    {
      document.forms[1].mode.value="doReset";
       document.forms[1].action = "/ICDPCSCodingAction.do";
      document.forms[1].submit();
    }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
    else
    {
        document.forms[1].reset();
        showhideHospType();
        showhideTariffType();
        showhideTariffItem();
     }//end of else
}//end of onReset()

function geticdcode()
{
  document.forms[1].mode.value="doGetICDCode";
  document.forms[1].child.value="ICD List";
  document.forms[1].action = "/ICDPCSCodingAction.do";
  document.forms[1].submit();
}//end of geticdcode

function onSelectPackage()
{
  document.forms[1].mode.value="doViewPackage";
  document.forms[1].child.value="PackageList";
  document.forms[1].action = "/ICDPCSCodingAction.do";
  document.forms[1].submit();
}//end of onSelectPackage()

function assocdProcedures()
{
  trimForm(document.forms[1]);
  document.forms[1].mode.value="doViewAssociateProcedure";
  document.forms[1].child.value="Procedure";
  document.forms[1].action="/ICDPCSCodingAction.do";
  document.forms[1].submit();
}//end of assocdProcedures()

//function to delete selected associated procedure
function deleteAsscProc()
{
    if(document.frmICDPCSCoding.asscProcedure.selectedIndex >0)
    {
        if(document.frmICDPCSCoding.asscProcedure.options[0].selected != true)
        {
            var msg = confirm("Are you sure you want to delete the selected procedure ?");
            if(msg)
            {
               ClientReset=false;
                var splitProcedure = document.frmICDPCSCoding.asscProcedure.options[document.frmICDPCSCoding.asscProcedure.options.selectedIndex].value.split("|");
                if(document.frmICDPCSCoding.deleteSeqId.value == "")
                {
                    document.frmICDPCSCoding.deleteSeqId.value = "|"+splitProcedure[0]+"|";
                 }
                else
                {
                    document.frmICDPCSCoding.deleteSeqId.value = document.frmICDPCSCoding.deleteSeqId.value+splitProcedure[0]+"|";
                }
                document.frmICDPCSCoding.asscProcedure.options[document.frmICDPCSCoding.asscProcedure.options.selectedIndex]=null;
               document.forms[1].mode.value="doRemoveProcedure";
        document.forms[1].action = "/ICDPCSCodingAction.do";
           document.forms[1].submit();
            }//end of if(msg)
        }//end of if(document.frmICDPCSCoding.asscProcedure.options[0].selected != true)
    }//end of if(document.frmICDPCSCoding.asscProcedure.selectedIndex < 0 )
    else
    {
        alert("Please select a associated procedure to delete");
    }//end of else

}//end of deleteAsscProc()

//function on clicking the icon
function onICDIconClick()
{
   document.forms[1].mode.value="doViewICDList";
   document.forms[1].action = "/ICDPCSCodingAction.do";
   document.forms[1].submit();
}//end of onICDIconClick()

function onDelete()
{
  var msg = confirm("Are you sure you want to delete the selected record?");
    if(msg)
    {
    	document.forms[1].mode.value="doDelete";
      	document.forms[1].action="/MedicalDetailsAction.do";
    	document.forms[1].submit();
  }//end of if(msg)
}//end of onDelete