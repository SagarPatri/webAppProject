
//Function on clicking calculate button

function onCalculate()
{
    trimForm(document.forms[1]);
    //calcReqAmt();
    //calcAppAmt();
    //calcMaxAmt();
    //calcAilAppAmt();
    document.forms[1].mode.value="doCalculate";
    document.forms[1].action = "/TariffSubmitAction.do";
    document.forms[1].submit();
}//end of onCalculate()

//function on clicking save button
function onSave()
{

      trimForm(document.forms[1]);
      calcReqAmt();
      calcAppAmt();
      calcMaxAmt();
      calcAilAppAmt();
      if(!JS_SecondSubmit){
      	document.forms[1].mode.value="doSubmit";
      	document.forms[1].action ="TariffSubmitAction.do";
      	JS_SecondSubmit=true
      	document.forms[1].submit();
      }//end of if(!JS_SecondSubmit)	
}//end of onSave()

//function Reset


function showHideButton()
{
  document.getElementById("SV").style.display="none";
}//end of showHideButton

function calcReqAmt()
{
  var totReqAmt=0;
  regexp=/^([0])*\d{0,10}(\.\d{1,2})?$/;
  if(document.forms[1].selectedTariffSeqID !=null)
  {
    if(document.forms[1].selectedTariffSeqID.length)
    {
      var obj=document.forms[1].elements['tariffRequestedAmt'];
      for(i=0;i<obj.length;i++)
      {
        if(obj[i].value!="" && regexp.test(obj[i].value))
        totReqAmt= totReqAmt + parseFloat(obj[i].value);
      }//end of for
    }//end of if(document.forms[1].selectedTariffSeqID.length)
   }//end of  if(document.forms[1].selectedTariffSeqID !=null)
    if( document.forms[1].requestedPkgAmt.value!="" &&  regexp.test(document.forms[1].requestedPkgAmt.value))
    {
      totReqAmt=totReqAmt + parseFloat(document.forms[1].requestedPkgAmt.value);
	}//end of if( document.forms[1].requestedPkgAmt.value!="" &&  regexp.test(document.forms[1].requestedPkgAmt.value))
	document.forms[1].totReqAmt.value=totReqAmt;
    document.forms[1].totHidReqAmt.value=totReqAmt;
}//end of calcReqAmt()

function calcAppAmt()
{
   var totAppAmt=0;
  regexp=/^([0])*\d{0,10}(\.\d{1,2})?$/;
  if(document.forms[1].selectedTariffSeqID !=null)
  {
    if(document.forms[1].selectedTariffSeqID.length)
    {
      var obj=document.forms[1].elements['tariffApprovedAmt'];
      for(i=0;i<obj.length;i++)
      {
        if(obj[i].value!="" && regexp.test(obj[i].value))
        totAppAmt= totAppAmt + parseFloat(obj[i].value);
        //totAppAmt = Math.round(totAppAmt);
      }//end of for
    }//end of if(document.forms[1].selectedTariffSeqID.length)
   }//end of  if(document.forms[1].selectedTariffSeqID !=null)
    if( document.forms[1].approvedPkgAmt.value!="" &&  regexp.test(document.forms[1].approvedPkgAmt.value))
    {
      totAppAmt=totAppAmt + parseFloat(document.forms[1].approvedPkgAmt.value);
    }//end of if( document.forms[1].approvedPkgAmt.value!="" &&  regexp.test(document.forms[1].approvedPkgAmt.value))
   totAppAmt = Math.round(totAppAmt); 
   document.forms[1].totApprAmt.value=totAppAmt;
   document.forms[1].totHidApprAmt.value=totAppAmt;
}//end of calcAppAmt()

function calcMaxAmt()
{
  var totMaxAmt=0;
  regexp=/^([0])*\d{0,10}(\.\d{1,2})?$/;
  if(document.forms[1].selectedTariffSeqID !=null)
  {
    if(document.forms[1].selectedTariffSeqID.length)
    {
      var obj=document.forms[1].elements['tariffMaxAmtAllowed'];
      for(i=0;i<obj.length;i++)
      {
        if(obj[i].value!="" && regexp.test(obj[i].value))
        totMaxAmt= totMaxAmt + parseFloat(obj[i].value);
      }//end of for
    }//end of if(document.forms[1].selectedTariffSeqID.length)
   }//end of  if(document.forms[1].selectedTariffSeqID !=null)
   if( document.forms[1].maxPkgAmtAllowed.value!="" &&  regexp.test(document.forms[1].maxPkgAmtAllowed.value))
    {
      totMaxAmt=totMaxAmt + parseFloat(document.forms[1].maxPkgAmtAllowed.value);
    }//end of if( document.forms[1].maxPkgAmtAllowed.value!="" &&  regexp.test(document.forms[1].maxPkgAmtAllowed.value))
    document.forms[1].totMaxAmt.value=totMaxAmt;
    document.forms[1].totHidMaxAmt.value=totMaxAmt;
}//end of calcMaxAmt()

function calcAilAppAmt()
{
  var totAilAppAmt=0;
  regexp=/^([0])*\d{0,10}(\.\d{1,2})?$/;
  if(document.forms[1].selectedICDPCSSeqID !=null)
  {
    if(document.forms[1].selectedICDPCSSeqID.length >= 0)
    {
      var obj=document.forms[1].elements['approvedAilmentAmt'];
      for(i=0;i<obj.length;i++)
      {
        if(obj[i].value!="" && regexp.test(obj[i].value))
        totAilAppAmt= totAilAppAmt + parseFloat(obj[i].value);
       
      }//end of for
    }//end of if(document.forms[1].selectedICDPCSSeqID.length >= 0)
    else
    {
      var obj=document.forms[1].elements['approvedAilmentAmt'];

      if(obj.value!="" && regexp.test(obj.value))
      totAilAppAmt= totAilAppAmt + parseFloat(obj.value);
    }//end of else
  }//end of if(document.forms[1].selectedICDPCSSeqID !=null)
    document.forms[1].totHidAilAppAmt.value=totAilAppAmt;
}//end of calcAilAppAmt()


