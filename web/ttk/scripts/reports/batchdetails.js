//javascript functions for batchdetails jsp

function onSearch()
{
	
	
	var numericvalue=/^[0-9]*$/;
	      if(document.forms[1].batchNumber.value.length!=0)
	      {
	    	  if((numericvalue.test(document.forms[1].batchNumber.value)==false))
	  		{
	  			alert("Batch Number should be a numeric value");
	  			document.forms[1].batchNumber.focus();
	  			return false;
	  		
  		}//end of  if((numericvalue.test(batchNumber)==false))
  		
  		}//end of if(document.forms[1].batchNumber.value.length!=0)
	 


  if(!document.forms[1].floatAccNumber.value=="")
     {
        if(document.forms[1].batchNumber.value=="" && document.forms[1].startDate.value=="")
          {
            alert('Please Enter Batch Number or Start Date');
             document.forms[1].batchNumber.focus();
          }//end of if(document.forms[1].batchNumber.value=="" && document.forms[1].startDate.value=="")
     }//end of  if(!document.forms[1].floatAccNumber.value=="")
   else
     {
        alert('Please Enter Float Account Number');
        document.forms[1].floatAccNumber.focus();
     }//end of else 
      
   
      
      
   if((!document.forms[1].floatAccNumber.value=="" && !document.forms[1].batchNumber.value=="")||(!document.forms[1].floatAccNumber.value=="" && !document.forms[1].startDate.value==""))
     {
          var startDate = document.forms[1].startDate.value;
		  var endDate = document.forms[1].endDate.value;
	      var NumElements=document.forms[1].elements.length;
		for(n=0; n<NumElements;n++)
		{
			if(document.forms[1].elements[n].type=="text")
			{
				 if(document.forms[1].elements[n].className=="textBox textDate")
				 {
				 	if(trim(document.forms[1].elements[n].value).length>0)
					{
						if(isDate(document.forms[1].elements[n],"Date")==false)
						{
							document.forms[1].elements[n].focus();
							return false;
						}//end of if(isDate(document.forms[1].elements[n],"Date")==false)
					}//end of if(trim(document.forms[1].elements[n].value).length>0)
				 }//end of if(document.forms[1].elements[n].className=="textBox textDate")
			}//end of if(document.forms[1].elements[n].type=="text")
		}//end of for(n=0; n<NumElements;n++)
          trimForm(document.forms[1]);
          document.forms[1].mode.value="doSearch";
	      document.forms[1].action.value="/BatchDetailsAction.do";
    	  document.forms[1].submit(); 
	 }//end of if((!document.forms[1].floatAccNumber.value=="" && !document.forms[1].batchNumber.value=="")||(!document.forms[1].floatAccNumber.value=="" && !document.forms[1].startDate.value==""))
}//end of onSearch()			

function onClose()
  {
     document.forms[1].mode.value="doClose";
	 document.forms[1].action.value="/BatchDetailsAction.do";
	 document.forms[1].submit(); 
  }//end of close
  
  
function toggle(sortid)
  {
	  document.forms[1].reset();
      document.forms[1].mode.value="doSearch";
      document.forms[1].sortId.value=sortid;
      document.forms[1].action = "/BatchDetailsAction.do";
      document.forms[1].submit();
   }//end of toggle(sortid)
   
function edit(rownum)
   {
      document.forms[1].mode.value="doViewBatchNumber";
	  document.forms[1].rownum.value=rownum;
	  document.forms[1].action ="/BatchDetailsAction.do";
	  document.forms[1].submit();
   }//end of edit(rownum)
   
//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/BatchDetailsAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/BatchDetailsAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/BatchDetailsAction.do";
    document.forms[1].submit();
}//end of PressBackWard()   