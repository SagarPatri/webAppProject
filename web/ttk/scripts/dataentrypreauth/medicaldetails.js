  //This method is called on click of ped icon in Pre-Auth module
  function onPEDIcon()
  {
    document.forms[1].mode.value="doSearchPreauthPED";
    document.forms[1].child.value="Add PED";
    document.forms[1].action="/PreAuthPEDAction.do";
    document.forms[1].submit();
  }// end of onPEDIcon()

  //This method is called on click of ped icon in Claims module
  function onClaimsPEDIcon()
  {
    document.forms[1].mode.value="doSearchPreauthPED";
    document.forms[1].action="/PreAuthPEDAction.do";
    document.forms[1].submit();
  }//end of onClaimsPEDIcon

  function onAdd()
  {
    document.forms[1].mode.value="doAdd";
    document.forms[1].child.value="ICDPCS";
    document.forms[1].action="/DataEntryICDPCSCodingAction.do";
    document.forms[1].submit();
  }//end of onAdd

  function onDocOpinionIcon()
  {
		if(document.forms[1].elements['ailmentVO.specialityDesc'].value=="")
		{
			alert("Please enter the Medical Details before entering the Doctor Opinion");
		}//end of if
		else
		{
			document.forms[1].mode.value="doView";
		    document.forms[1].child.value="Doctor Opinion";
		    document.forms[1].action="/DataEntryDoctorOpinionAction.do";
		    document.forms[1].submit();
	    }//end of else
  }//end of onDocOpinionIcon()
  
  function onAilmentDesc()
  {
    document.forms[1].mode.value="doView";
    document.forms[1].child.value="Ailment Details";
    document.forms[1].action="/DataEntryAilmentDetailsAction.do";
    document.forms[1].submit();
  }//end of onAilmentDesc

  function onDeleteList()
  {
    if(!mChkboxValidation(document.forms[1]))
      {
        var msg = confirm("Are you sure you want to delete the selected record(s)?");
            if(msg)
            {
          		document.forms[1].mode.value="doDeleteList";
          		if(document.forms[0].leftlink.value == 'Coding')
          		document.forms[1].action="/CodingPreMedicalDeleteDetailsAction.do";
          		else
            	document.forms[1].action="/MedicalDeleteDetailsAction.do";
          		document.forms[1].submit();
        	}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
  }//end of onDeleteList

  //function to sort based on the link selected
  function edit(rownum)
  {
      document.forms[1].mode.value="doView";
      document.forms[1].child.value="ICDPCS";
      document.forms[1].rownum.value=rownum;
      document.forms[1].action = "/DataEntryICDPCSCodingAction.do";
      document.forms[1].submit();
  }//end of edit(rownum)
  
  //to generate medical opinion report
   function onGenerateMedicalOpinion()
{
	trimForm(document.forms[1]);
	if(TC_GetChangedElements().length>0)
    {
    	alert("Please save the modified data, before Generate");
    	return false;
    }//end of if(TC_GetChangedElements().length>0)
	var parameterValue = document.forms[1].claimSeqID.value;
	var openPage = "ReportsAction.do?mode=doGenerateMedicalOpinionReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/MedicalOpinionSheet.jrxml&reportID=MedicalOpinion";
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}
  
   