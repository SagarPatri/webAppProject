/*
		 * This java script is added for cr koc 1216B
		 * 
		 */

/*function Close()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].tab.value="Search";
	document.forms[1].action="/EnrollBufferAction.do";
	document.forms[1].submit();
}//end of Close()
*/
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/MaintainEnrollAction.do";
	document.forms[1].submit();
}//end of onClose()


function onSearch()
{
	
      if(!JS_SecondSubmit)
       { 
      trimForm(document.forms[1]);
      var searchFlag=false;
     	
    	  var policynum=document.forms[1].sPolicyNumber.value;
    	    var enrollnum=document.forms[1].sEnrollNumber.value;
    	    var enrollID=document.forms[1].sEnrollmentId.value;
    	   // var insurename=document.forms[1].sInsuredName.value;
    	    //var ttkbranch=document.forms[1].sTtkBranch.value;
    	    
        if((policynum != "")&&(policynum.length < 8)) 
	{
    
		alert("Please enter atleast 8 characters of Scheme No.");
		document.forms[1].sPolicyNumber.focus();
		return false;
	}
    
    if((policynum == ""))
    {
    	alert("Please enter  Scheme No.");
    	document.forms[1].sPolicyNumber.focus();
    	return false;
  
    }
   
	
	document.forms[1].mode.value = "doSearch";
    document.forms[1].action = "/EnrollBufferSearchAction.do";
    JS_SecondSubmit=true;
    document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()
  function edit(rownum)
  {
	    	
  	document.forms[1].rownum.value=rownum;
  	//document.forms[1].tab.value="";
  	document.forms[1].mode.value="doDefault";
  	document.forms[1].action="/EnrollBufferAction.do";
  	document.forms[1].submit();
  
  	
  	
  }//end of edit
//function to be called onClick of third link in Html Grid
   function pageIndex(pagenumber)
  {
  	document.forms[1].reset();
      document.forms[1].mode.value="doSearch";
      document.forms[1].pageId.value=pagenumber;
      document.forms[1].action = "/EnrollBufferSearchAction.do";
      document.forms[1].submit();
  }//end of pageIndex(pagenumber)
  function PressForward()
  {
  	document.forms[1].reset();
      document.forms[1].mode.value ="doForward";
      document.forms[1].action = "/EnrollBufferSearchAction.do";
      document.forms[1].submit();
  }//end of PressForward()

  //function to display previous set of pages
  function PressBackWard()
  {
  	document.forms[1].reset();
      document.forms[1].mode.value ="doBackward";
      document.forms[1].action = "/EnrollBufferSearchAction.do";
      document.forms[1].submit();
  }//end of PressBackWard()
  function toggle(sortid)
  {
  	document.forms[1].reset();
      document.forms[1].mode.value="doSearch";
      document.forms[1].sortId.value=sortid;
      document.forms[1].action = "/EnrollBufferSearchAction.do";
      document.forms[1].submit();
  }//end of toggle(sortid)

  

	/*
	function copyToWebBoard()
	{
	    if(!mChkboxValidation(document.forms[1]))
	    {
		    document.forms[1].mode.value = "doCopyToWebBoard";
	   		document.forms[1].action = "/EnrollBufferSearchAction.do";
		    document.forms[1].submit();
	    }//end of if(!mChkboxValidation(document.forms[1]))
	}//end of copyToWebBoard()
*/
	
	
	
	function SelectGroup()
	{
		//document.forms[1].child.value="GroupList";
		document.forms[1].mode.value="doSelectGroup";
		document.forms[1].action="/EnrollBufferSearchAction.do";
		document.forms[1].submit();
	}//end of SelectGroup()

	function ClearCorporate()
	{
		document.forms[1].mode.value="doClearCorporate";
		document.forms[1].action="/EnrollBufferSearchAction.do";
		document.forms[1].submit();
	}//end of ClearCorporate()

	function ChangePolicyType()
	{
		document.forms[1].mode.value="doChangePolicyType";
		document.forms[1].action="/EnrollBufferSearchAction.do";
		document.forms[1].submit();
	}//end of ChangePolicyType()