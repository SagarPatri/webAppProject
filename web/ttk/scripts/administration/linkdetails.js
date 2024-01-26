/** @ (#) linkdetails.js 21st Dec 2007
 * Project     : TTK Healthcare Services
 * File        : linkdetails.js
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 21st Dec 2007
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
*/
 
//This function is called onload of the jsp 
function  onLoadLinks()
{
	selObj = document.forms[1].linkTypeID;
    var selVal = selObj.options[selObj.selectedIndex].value;
	if(selVal == 'WLG')
	{
		document.getElementById('path').style.display = "";
		document.getElementById('browseImg').style.display = "none";
		document.getElementById('ordNo').style.display = "none";
		document.getElementById('report').style.display = "none";
		document.forms[1].orderNumber.value="";
		document.forms[1].reportID.value="WRA";
		document.forms[1].path.disabled=false;
	}//end of if(selVal == 'WLG')
	if(selVal == 'WLC')
	{
		document.getElementById('path').style.display = "";
		document.getElementById('browseImg').style.display = "none";
		document.getElementById('ordNo').style.display = "none";
		document.getElementById('report').style.display = "none";
		document.forms[1].orderNumber.value="";
		document.forms[1].reportID.value="WRA";
		document.forms[1].path.disabled=false;
	}//end of if(selVal == 'WLC')	
	if(selVal == 'WLL')
	{
		document.getElementById('path').style.display = "";
		document.getElementById('browseImg').style.display = "";		
		document.getElementById('ordNo').style.display = "";
		document.getElementById('report').style.display = "none";
		document.forms[1].reportID.value="WRA";
		document.forms[1].path.disabled=true;
	}//end of if(selVal == 'WLL')	
	if(selVal == 'WLS')
	{
		document.getElementById('path').style.display = "none";
		document.getElementById('ordNo').style.display = "";
		document.getElementById('report').style.display = "";
	}//end of if(selVal == 'WLS')
	if(selVal == 'WCT')
	{
		document.getElementById('path').style.display = "none";
		document.getElementById('browseImg').style.display = "none";
		document.getElementById('ordNo').style.display = "none";
		document.getElementById('report').style.display = "none";
		document.forms[1].orderNumber.value="";
		document.forms[1].reportID.value="WRA";
	}//end ofif(selVal == 'WCT')
	// Changes Done For CR KOC1168 Feedback Form 
	if(selVal == 'WFB')
	{
		
		document.getElementById('path').style.display = "none";
		document.getElementById('browseImg').style.display = "none";
		document.getElementById('ordNo').style.display = "";
		document.getElementById('report').style.display = "none";
		document.forms[1].orderNumber.value="";
	//	document.forms[1].reportID.value="WRA";
	}//end of if(selVal == 'WFB') End changes done for CR KOC1168 Feedback Form 
}//end of onLoadLinks()

//this function is called on change event of Link type
function onchangeLink()
{
	selObj = document.forms[1].linkTypeID;
    var selVal = selObj.options[selObj.selectedIndex].value;
	if(selVal == 'WLG')
	{
		document.forms[1].path.value="";
		document.getElementById('path').style.display = "";
		document.getElementById('browseImg').style.display = "none";
		document.getElementById('ordNo').style.display = "none";
		document.getElementById('report').style.display = "none";
		document.forms[1].orderNumber.value="";
		document.forms[1].reportID.value="WRA";
		document.forms[1].path.disabled=false;
	}//end of if(selVal == 'WLG')
	if(selVal == 'WLC')
	{
		document.forms[1].path.value="";
		document.getElementById('path').style.display = "";
		document.getElementById('browseImg').style.display = "none";
		document.getElementById('ordNo').style.display = "none";
		document.getElementById('report').style.display = "none";
		document.forms[1].orderNumber.value="";
		document.forms[1].reportID.value="WRA";
		document.forms[1].path.disabled=false;
	}//end of if(selVal == 'WLC')	
	if(selVal == 'WLL')
	{
		document.forms[1].path.value="";
		document.getElementById('path').style.display = "";
		document.getElementById('browseImg').style.display = "";		
		document.getElementById('ordNo').style.display = "";
		document.getElementById('report').style.display = "none";
		document.forms[1].reportID.value="WRA";
		document.forms[1].path.disabled=true;
	}//end of if(selVal == 'WLL')	
	if(selVal == 'WLS')
	{
		document.getElementById('path').style.display = "none";
		document.getElementById('ordNo').style.display = "";
		document.getElementById('report').style.display = "";
	}//end of if(selVal == 'WLS')
	if(selVal == 'WCT')
	{
		document.getElementById('path').style.display = "none";
		document.getElementById('browseImg').style.display = "none";
		document.getElementById('ordNo').style.display = "none";
		document.getElementById('report').style.display = "none";
		document.forms[1].orderNumber.value="";
		document.forms[1].reportID.value="WRA";
	}//end ofif(selVal == 'WCT')	
	// Changes Done For CR KOC1168 Feedback Form 
	if(selVal == 'WFB')
	{
		
		document.getElementById('path').style.display = "none";
		document.getElementById('browseImg').style.display = "none";
		document.getElementById('ordNo').style.display = "";
		document.getElementById('report').style.display = "none";
		document.forms[1].orderNumber.value="";
	//	document.forms[1].reportID.value="WRA";
	}//end of if(selVal == 'WFB') End changes done for CR KOC1168 Feedback Form 

}// end of onchangeLink()

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doDefault";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/LinkDetailsAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//this function is called onclick of the save button
function onSave()
{
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		document.forms[1].path.disabled=false;
		document.forms[1].mode.value="doSave";
		document.forms[1].action = "/UpdateLinkDetailsAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

//this function is called onclick of the link in grid to display the records
function edit(rownum)
{
    document.forms[1].mode.value="doViewLinkDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/LinkDetailsAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

//this function is called onclick of the delete button
function onDelete()
{
	if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected Link Information ?");
		if(msg)
		{
		    document.forms[1].mode.value = "doDelete";
		    document.forms[1].action = "/DeleteLinkDetailsAction.do";
		    document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

//this function is called onclick of the close button
function onClose()
{
	if(!TrackChanges()) return false;
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/LinkDetailsAction.do";
    document.forms[1].submit();
}//end of onClose()

//this function is called onclick of the browse icon
function onBrowse()
{
	
	document.forms[1].mode.value="doBrowse";
    document.forms[1].action = "/LinkDetailsAction.do";
    document.forms[1].submit();	
}//end of function onBrowse()

//this function is for checkbox validation
function mChkboxValidation(objform)
{
	with(objform)
	{
		for(var i=0;i<elements.length;i++)
		{
			if(objform.elements[i].name == "chkopt")
			{
				if (objform.elements[i].checked)
				{
					iFlag = 1;
					break;
				}//end of if (objform.elements[i].checked)
				else
				{
					iFlag = 0;
				}//end of else
			}//end of if(objform.elements[i].name == "chkopt")
		}//end of for(var i=0;i<elements.length;i++)
	}//end of with(objform)
	if (iFlag == 0)
	{
		alert('Please select atleast one record');
		return true;
	}//end of if (iFlag == 0)
	else
	{
		return false;
	}//end of else
}//end of function mChkboxValidation(objform)

//this function is called onclick of the reset button
function onReset()
{
	document.forms[1].mode.value="doReset";
   	document.forms[1].action="/LinkDetailsAction.do";
    document.forms[1].submit();
}//end of onReset()



