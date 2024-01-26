/*
		 * This java script is added for cr koc 1103
		 * added eft
		 */
//declare the Ids of the form fields seperated by comma which should not be focused when document is loaded
var JS_donotFocusIDs=["switchType"];
function Close()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].tab.value="Search";
	document.forms[1].action="/CustomerBankDetails.do";
	document.forms[1].submit();
}//end of Close()

function onSearch()
{
	 //alert("called");
  if(!JS_SecondSubmit)
 { 
	  //alert("called1");
	trimForm(document.forms[1]);
	//alert("called2");
    var searchFlag=false;
   var switchty=document.forms[1].switchType.value;
  // alert(switchty);
  
  
   
   // alert("after reading all var");
    if(switchty == "POLC")
    {
    	//alert("called end");
    	  var policynum=document.forms[1].sPolicyNumber.value;
    	    var enrollnum=document.forms[1].sEnrollNumber.value;
    	    var insurename=document.forms[1].sInsuredName.value;
    	    var ttkbranch=document.forms[1].sTtkBranch.value;
    	    
      //  alert("in switch end");
    if((policynum == "")&&(enrollnum == "")&&(insurename == ""))
    {
       alert("Please enter atleast one search criteria");
       document.forms[1].sPolicyNumber.focus();
        return false;
    }
    if((policynum != "")&&(policynum.length < 8)) 
	{
    	//|| (policynum == "")) && (enrollnum != "") && (insurename != "")
		alert("Please enter atleast 8 characters of Scheme No.");
		document.forms[1].sPolicyNumber.focus();
		return false;
	}
    if(insurename != "")
    {
    if((policynum == "") && (enrollnum == ""))
    {
    	alert("Please enter  Scheme No. or Enrollment No");
    	document.forms[1].sPolicyNumber.focus();
    	return false;
    }
    }
    }
    
 

    if(switchty == "CLAM")
       {
    	//alert("called enm");
       	var clmnmr=document.forms[1].sClaimNumber.value;
         //  alert("claim number"+clmnmr);
          var claimsettlmentnumber=document.forms[1].sClaimSettmentNumber.value;
         // alert("claim sett nmr"+claimsettlmentnumber);
          var claimentname=document.forms[1].sClaimentName.value;
          // alert("claiment name"+claimentname);
       	
       	if(((clmnmr == "") && (claimsettlmentnumber == "")) && (claimentname == ""))
           {
               alert("Please enter atleast one search criteria");
               document.forms[1].sClaimNumber.focus();
               return false;
           } 
       	if(claimentname != "")
        {
        if((clmnmr == "") && (claimsettlmentnumber == ""))
        {
        	alert("Please enter  Cliam No. or claim Settelment No");
        	document.forms[1].sClaimNumber.focus();
        	return false;
        }
        }
       }//end clm
   
    if(switchty == "HOSP")
    {
 	   
    	var empnalNO=document.forms[1].sEmpanelmentNo.value;
     
       var hospitalname=document.forms[1].sHospitalName.value;
      
    	
    	if((empnalNO == "") && (hospitalname == "")) 
        {
            alert("Please enter atleast one search criteria");
            document.forms[1].sEmpanelmentNo.focus();
            return false;
        } 
    	
    }//end hosp
    
	//alert("alert5");
	document.forms[1].mode.value = "doSearch";
    document.forms[1].action = "/CustomerBankDetailsSearch.do";
    JS_SecondSubmit=true;
    document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()
  function edit(rownum)
  {
	  
	  var switchty=document.forms[1].switchType.value;
	  var from	=	document.forms[1].from.value;
  	if(document.forms[0].sublink.value=="Cust. Bank Details")
  	{
  		document.forms[1].tab.value="Policy Bank Details";
  	}
  	if(switchty =='POLC' || switchty == "CLAM" )
  	{
  	document.forms[1].rownum.value=rownum;
  	document.forms[1].mode.value="doViewBankAccount";
  	document.forms[1].action="/CustomerBankDetailsSearch.do";
  	document.forms[1].submit();
  	}
  	else if(switchty =='HOSP' )
  	{
  	document.forms[1].rownum.value=rownum;
  	if(from=="from")
		document.forms[1].tab.value="Account Validation";
	else
		document.forms[1].tab.value="Hospital Bank Details";
  	document.forms[1].mode.value="doViewHospBankAccount";
  	document.forms[1].action="/CustomerBankDetailsSearch.do?from="+from;
  	document.forms[1].submit();
  	}
  	
  	
  }//end of edit
//function to be called onClick of third link in Html Grid
  function edit2(rownum)
  {
	  
	  	if(document.forms[0].sublink.value=="Cust. Bank Details")
	  	{
	  		document.forms[1].tab.value="Member Bank Details";
	  	}
	  	document.forms[1].rownum.value=rownum;
	  	document.forms[1].mode.value="doMemberAccount";
	  	document.forms[1].action="/CustomerBankDetailsSearch.do";
	  	document.forms[1].submit();
  }//end of edit(rownum)
  
  function onAddBankAccount()
  {
      document.forms[1].reset();
      document.forms[1].rownum.value = "";
      document.forms[1].mode.value = "doAdd";
      document.forms[1].tab.value="Bank Details";
      document.forms[1].action = "/BankAcctGeneralActionTest.do";
      document.forms[1].submit();
  }//end of ()
  function pageIndex(pagenumber)
  {
  	document.forms[1].reset();
      document.forms[1].mode.value="doSearch";
      document.forms[1].pageId.value=pagenumber;
      document.forms[1].action = "/CustomerBankDetailsSearch.do";
      document.forms[1].submit();
  }//end of pageIndex(pagenumber)
  function PressForward()
  {
  	document.forms[1].reset();
      document.forms[1].mode.value ="doForward";
      document.forms[1].action = "/CustomerBankDetailsSearch.do";
      document.forms[1].submit();
  }//end of PressForward()

  //function to display previous set of pages
  function PressBackWard()
  {
  	document.forms[1].reset();
      document.forms[1].mode.value ="doBackward";
      document.forms[1].action = "/CustomerBankDetailsSearch.do";
      document.forms[1].submit();
  }//end of PressBackWard()
  function toggle(sortid)
  {
  	document.forms[1].reset();
      document.forms[1].mode.value="doSearch";
      document.forms[1].sortId.value=sortid;
      document.forms[1].action = "/CustomerBankDetailsSearch.do";
      document.forms[1].submit();
  }//end of toggle(sortid)
  function onSwitch()
  {
  	document.forms[1].mode.value="doSwitchTo";
  	document.forms[1].action="/CustomerBankDetailsSearch.do";
  	document.forms[1].submit();
  }//end of onSwitch()
  
  function changePolicyType()
  {
  	document.forms[1].mode.value="doChangePolicyType";
  	document.forms[1].action="/CustomerBankDetailsSearch.do";
  	document.forms[1].submit();
  }//end of changeCallerFields()

//function to enable or disable the fields
	function enableField(obj)
	{
		if(obj.value == "COR")
		{
		document.forms[1].sGroupId.disabled=false;
		}
		else
		{
			document.forms[1].sGroupId.disabled=true;
			
		}
		
			    
			
	}//end of function enableField(obj)
	
	
	function copyToWebBoard()
	{
	    if(!mChkboxValidation(document.forms[1]))
	    {
		    document.forms[1].mode.value = "doCopyToWebBoard";
	   		document.forms[1].action = "/CustomerBankDetailsSearch.do";
		    document.forms[1].submit();
	    }//end of if(!mChkboxValidation(document.forms[1]))
	}//end of copyToWebBoard()
