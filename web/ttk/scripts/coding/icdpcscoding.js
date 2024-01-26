function edit(rownum)
{
    document.forms[1].mode.value="doView";
    document.forms[1].focusID.value="asscProcedure";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/PreauthCodingAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function assocdProcedures()
{
  trimForm(document.forms[1]);
  document.forms[1].mode.value="doViewAssociateProcedure";
  document.forms[1].child.value="";
  document.forms[1].action="/ICDPCSCodingAction.do";
  document.forms[1].submit();
}//end of assocdProcedures()

function onAssociate()
{
	getPCSDescription();
	document.forms[1].mode.value="doViewAssociateProcedure";
	if(trim(document.forms[1].procedurecode.value) !=''){
		document.forms[1].focusID.value="procedurecode";
		document.forms[1].action="/PreauthICDCodeAction.do";
		document.forms[1].submit();
	}//end of if(document.forms[1].procedurecode.value !='')
	else
    {
        alert("Please enter a PCS code to associate");
    }//end of else
}//end of onAssociate()

function onSave()
{
   trimForm(document.forms[1]);
   var strAsscCode="";
   if(document.frmICDPCSPolicy.asscProcedure!=null)
   {
     if(document.frmICDPCSPolicy.asscProcedure.length>1)
    {
      strAsscCode="|";
      for(i=1;i<document.frmICDPCSPolicy.asscProcedure.length;i++)
      {
        strAsscCode+=document.frmICDPCSPolicy.asscProcedure.options[i].value;
        strAsscCode+="|";
      }//end of for
    }//end of if(document.frmICDPCSCoding.asscProcedure.length>1)
  }// end of if(document.frmICDPCSCoding.asscProcedure!=null)
  document.forms[1].selectedProcedureCode.value=strAsscCode;
  if(!JS_SecondSubmit){

  	document.forms[1].mode.value="doSave";
  	document.forms[1].action="/PreauthICDCodingAction.do";
  	JS_SecondSubmit=true
  	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSave()

function onReset()
{
	document.forms[1].mode.value="doReset";
	document.forms[1].action="/PreauthCodingAction.do";
	document.forms[1].submit();
}//end of onReset()

function getNewICDCode()
{
	document.forms[1].mode.value="doNewICDCode";
	document.forms[1].focusID.value="sICDCode";
	document.forms[1].action="/PreauthCodingAction.do";
	document.forms[1].submit();
}//end of getNewICDCode()

function deleteAsscProc()
{
 	if(document.frmICDPCSPolicy.asscProcedure.selectedIndex >0)
    {
        if(document.frmICDPCSPolicy.asscProcedure.options[0].selected != true)
        {
            var msg = confirm("Are you sure you want to delete the selected procedure ?");
            if(msg)
            {
               ClientReset=false;
                var splitProcedure = document.frmICDPCSPolicy.asscProcedure.options[document.frmICDPCSPolicy.asscProcedure.options.selectedIndex].value.split("|");
                if(document.frmICDPCSPolicy.deleteSeqId.value == "")
                {
                    document.frmICDPCSPolicy.deleteSeqId.value = "|"+splitProcedure[0]+"|";
                 }
                else
                {
                    document.frmICDPCSPolicy.deleteSeqId.value = document.frmICDPCSPolicy.deleteSeqId.value+splitProcedure[0]+"|";
                }
                document.frmICDPCSPolicy.asscProcedure.options[document.frmICDPCSPolicy.asscProcedure.options.selectedIndex]=null;
                document.forms[1].mode.value="doRemoveProcedure";
                document.forms[1].focusID.value="procedurecode";
                document.forms[1].action = "/PreauthICDCodeAction.do";
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
   document.forms[1].focusID.value="procedurecode";
   document.forms[1].action = "/ICDPCSCodingAction.do";
   document.forms[1].submit();
}//end of onICDIconClick()

function onDeleteIcon(rownum)
{
  var msg = confirm("Are you sure you want to delete the selected record?");
    if(msg)
    {
    	document.forms[1].mode.value="doDelete";
    	document.forms[1].rownum.value=rownum;
      	document.forms[1].action="/PreauthICDCodeAction.do";
    	document.forms[1].submit();
  }//end of if(msg)
}//end of onDeleteIcon(rownum)

function getDescription()
{
	trimForm(document.forms[1]);
	document.forms[1].sICDCode.value=trim(document.forms[1].sICDCode.value);
   if(trim(document.forms[1].sICDCode.value) !=''){
   		document.forms[1].focusID.value="procedurecode";
   		document.forms[1].mode.value="doGetDescription";
   		document.forms[1].action = "/PreauthICDCodingAction.do";
   		document.forms[1].submit();
   }//end of if
}//end of getDescription()

function getPCSDescription()
{
	document.forms[1].procedurecode.value=trim(document.forms[1].procedurecode.value);
   if(document.forms[1].procedurecode.value !=''){
   		document.forms[1].focusID.value="asscProcedure";
    	document.forms[1].mode.value="doGetPCSDescription";
   		document.forms[1].action = "/PreauthICDCodeAction.do";
   	    document.forms[1].submit();
   }//end of if(trim(document.forms[1].procedurecode.value)!='')
}//end of getPCSDescription()