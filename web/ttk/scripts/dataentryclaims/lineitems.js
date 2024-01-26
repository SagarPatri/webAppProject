//java script for the line items screen
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doClose";
	document.forms[1].child.value="";
	document.forms[1].action="/DataEntryLineItemsAction.do";
	document.forms[1].submit();
}//end of onClose()



// to check whether one of the checkbox is checked or not .This could be used in while implementing Delete  function
function gridChkboxValidation(obj)
{
	with(obj)
	{
		for(var i=0;i<elements.length;i++)
		{
			if(elements[i].type=="checkbox")
			{
				if (elements[i].checked)
				{
					if(elements[i].name == "chkopt")
					{
						iFlag = 1;
						break;
					}
					else
					{
						iFlag = 0;
					}
				}
				else
				{
					iFlag = 0;
				}
			}
		}
	}

	if (iFlag == 0)
	{
		alert('Please select atleast one record');
		return true;
	}
	else
	{
		return false;
	}
}


// JavaScript edit() function
function edit(rownum)
{
	document.forms[1].rownum1.value=rownum;
	document.forms[1].mode.value="doView";
	document.forms[1].action="/DataEntryLineItemsAction.do";
	document.forms[1].submit();
}
// End of edit()

//function to sort based on the link selected
function toggle(sortid)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/DataEntryLineItemsAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//added for CR KOC-Decoupling
function onDataEntryPromote()  
{	   
	   var billseqid=document.forms[1].billSeqID.value;
	   var relation=document.getElementById('billSeqID').value;	   
	   if(billseqid==null)
		{
		   alert("Please add the bill details before promoting.");
		}
	   else if(!JS_SecondSubmit)
	   {	 
		   
		   //if(trim(document.forms[1].billNbr.value).length==0)
		   //{
			   //alert(document.forms[1].billNbr.value);
				//alert("Please add the bill details before promoting.");
				//return false;
		   //}//end of if(trim(document.forms[1].billNbr.value).length==0)
		   //else
		   //{
		   		document.forms[1].mode.value="doDataEntryPromote";
		   		document.forms[1].action="/DataEntryLineItemsAction.do";
		   		JS_SecondSubmit=true;
		   		document.forms[1].submit();				
		   //}		   
		} 
}

function onDataEntryRevert()  
{
	   if(!JS_SecondSubmit)
	   {
			document.forms[1].mode.value="doDataEntryRevert";
			document.forms[1].action="/DataEntryLineItemsAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		} 
}
//ended
