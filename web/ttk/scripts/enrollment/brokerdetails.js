function onSearch(){
	//alert("on search()");
	    	document.forms[1].mode.value = "doSearch";
	    	document.forms[1].action = "/enrBrokerDetailAction.do";
	    	document.forms[1].submit();
}//end of activityCodeSearch()

function onBrokerUserSearch(){
	       //alert("on BrokerUserSearch()");
	    	document.forms[1].mode.value = "doBrokerUserSearch";
	    	document.forms[1].action = "/enrBrokerDetailAction.do";
	    	document.forms[1].submit();
}//end of activityCodeSearch()

function edit(rownum){
	document.forms[1].mode.value = "doViewBrokers";
    document.forms[1].rownum.value=rownum;
	document.forms[1].action = "/enrBrokerDetailAction.do";
	document.forms[1].submit();
}
function onBrokerClose(){
	       //  alert("onBrokerClose()");
	       // document.forms[1].tab.value="Broker Details";
	    	document.forms[1].mode.value = "doClose";
	    	document.forms[1].action = "/enrBrokerDetailAction.do";
	    	document.forms[1].submit();
}//end of activityCodeSearch()

   function onBrokerUserAssociate(rownum){
	   
	        if(!mChkboxValidation(document.forms[1]))
	   	    {
	   		var msg = confirm("Are you sure you want to Save the selected record(s)?");
	   		if(msg)
	   			{
	   		document.forms[1].rownum.value=rownum;	
	    	document.forms[1].mode.value = "doAssosicateBrokersUser";
	    	document.forms[1].action = "/enrBrokerDetailAction.do";
	    	document.forms[1].submit();
	   		}// end of if(msg)
	   	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of activityCodeSearch()
   
  /* function onAdd(rownum)
   {
   	    if(!mChkboxValidation(document.forms[1]))
   	    {
   		var msg = confirm("Are you sure you want to Add the selected record(s)?");
   		if(msg)
   			{
   		document.forms[1].rownum.value=rownum;
   		document.forms[1].mode.value="doViewAssociatedCorp";
   		document.forms[1].action="/enrBrokerDetailAction.do";
   		document.forms[1].submit();
   			
   			}// end of if(msg)
   		}//end of if(!mChkboxValidation(document.forms[1]))
   	    
   }//end of onAdd()
*/


function PressBackWard()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/enrBrokerDetailAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/enrBrokerDetailAction.do";
    document.forms[1].submit();
}//end of PressForward()
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/enrBrokerDetailAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function onClose()
{
		document.forms[1].mode.value="doCloseBroker";
		document.forms[1].action="/enrBrokerDetailAction.do";
		document.forms[1].submit();
	
}//end of onAssociateCorp()

function onCloseBroker()
{
	document.forms[1].mode.value="doClosePremiumConfiguration";
	document.forms[1].action="/Configuration.do";
	document.forms[1].submit();
}//end of onClose()

function onBrokerUserDisAssociate(rownum)
{
    if(!mChkboxValidation(document.forms[1]))
	    {
		
	document.forms[1].rownum.value=rownum;	
	document.forms[1].mode.value = "doDisAssosicateBrokersUser";
	document.forms[1].action = "/enrBrokerDetailAction.do";
	document.forms[1].submit();
		
	}
}// end of onBrokerUserDisAssociate()

function onAdd(rownum)
{
	
	    if(!mChkboxValidation1(document.forms[1]))  
	    {
		
		document.forms[1].rownum.value=rownum;
		document.forms[1].mode.value="doViewAssociatedCorp";
		document.forms[1].action="/enrBrokerDetailAction.do";
		document.forms[1].submit();
			
		
		}//end of if(!mChkboxValidation(document.forms[1]))
	    
}//end of onAdd()

function mChkboxValidation1(objform)
{
	var iFlag=0;
	with(objform)
	{
		for(var i=0;i<elements.length;i++)
		{
			if(objform.elements[i].name == "chkopt")
			{
				if (objform.elements[i].checked)
				{
					iFlag = iFlag + 1;
		
				}//end of if (objform.elements[i].checked)
			
			}//end of if(objform.elements[i].name == "chkopt")
		}//end of for(var i=0;i<elements.length;i++)
	}//end of with(objform)
	
	if (iFlag == 0)
	{
		alert('Please select atleast one record');
		return true;
	}
	var strData=getBrokerListStatus();
	
	
	if(strData!=null&&strData!=""&&strData!="null"&&strData.length>0){
		if((iFlag>0&&parseInt(strData)>0)||(parseInt(strData)==0 &&iFlag>1)){
			if(confirm("A policy can have only one broker associated with it. Click OK to associate multiple brokers to this policy?"))
				return false;
				else
				return true;
		}
	}
	
	
}//end of function mChkboxValidation(objform)

function getBrokerListStatus() {
	var xhttp = new XMLHttpRequest();
	
	  xhttp.open("GET", "/enrBrokerDetailAction.do?mode=getBrokerListStatus", false);
	  xhttp.send();
	  
	  return xhttp.responseText;
}