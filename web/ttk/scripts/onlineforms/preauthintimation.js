
/** @ (#) preauthintimation.js 11th Mar 2008
 * Project     : TTK Healthcare Services
 * File        : preauthintimation.js
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 11th Mar 2008
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doDefault";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/PreAuthIntimationAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/PreAuthIntimationAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/PreAuthIntimationAction.do";
    document.forms[1].submit();
}//end of PressForward()
function onECards()
{
   var openPage = "/OnlineMemberAction.do?mode=doECards";
   var w = screen.availWidth - 10;
   var h = screen.availHeight - 49;
   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
   window.open(openPage,'',features);
}//end of function onECards()

//Caleed onclick of add button
function onAdd()
{
	document.forms[1].mode.value="doAdd";
	if(document.forms[0].sublink.value == "Pre-Auth" || document.forms[0].sublink.value == "Pre-Auth Intimation")
    {
    	document.forms[1].action="/EditPreAuthIntimationAction.do";
    }//end of if(document.forms[0].sublink.value == "Pre-Auth")
    else if(document.forms[0].sublink.value == "Claims" || document.forms[0].sublink.value == "Claims Intimation")
    {
    	document.forms[1].action="/EditClaimsIntimationAction.do";
    }//end of else if(document.forms[0].sublink.value == "Pre-Auth")	
	document.forms[1].submit();
}//end of onAdd()

//Caleed onclick of edit link
function edit(rownum)
{
    document.forms[1].mode.value="doViewIntimationDetails";
    document.forms[1].rownum.value=rownum;
    if(document.forms[0].sublink.value == "Pre-Auth" || document.forms[0].sublink.value == "Pre-Auth Intimation")
    {
    	document.forms[1].action="/EditPreAuthIntimationAction.do";
    }//end of if(document.forms[0].sublink.value == "Pre-Auth")
    else if(document.forms[0].sublink.value == "Claims" || document.forms[0].sublink.value == "Claims Intimation")
    {
    	document.forms[1].action="/EditClaimsIntimationAction.do";
    }//end of else if(document.forms[0].sublink.value == "Pre-Auth")	
    document.forms[1].submit();
}//end of edit(rownum)

//this function is called onclick of the delete button
function onDelete()
{
	if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected Intimation Details ?");
		if(msg)
		{
		    document.forms[1].mode.value = "doDelete";
		    document.forms[1].action = "/PreAuthIntimationAction.do";
		    document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

//Caleed onclick of close button
function onClose()
{
    document.forms[1].mode.value="doCloseList";
    document.forms[1].action = "/PreAuthIntimationAction.do";
    document.forms[1].submit();
}//end of onClose()

//this function will select or deselect all the check boxex available in the form based on the boolean value passed to the method
function selectAll( bChkd, objform )
{
	for(i=0;i<objform.length;i++)
 	{
  		if(objform.elements[i].name == "chkopt" )
  		{
   			if(!objform.elements[i].disabled)
   			{
    			objform.elements[i].checked = bChkd;
   			}//end of if(!objform.elements[i].disabled)
  		}//end of if(objform.elements[i].name == "chkopt" )
 	}//end of for(i=0;i<objform.length;i++) 
}//end of selectAll( bChkd, objform )

//this function is called onload of the jsp
function hideDeleteButton()
{
	var objform=document.forms[1];
	var blIsThereUnsuppressedRecord = false ;
	for(i=0;i<objform.length;i++)
	{
		if(objform.elements[i].name == "chkopt" )
		{
			if(!objform.elements[i].disabled)
			{
    			blIsThereUnsuppressedRecord = true;
   			}//end of if(!objform.elements[i].disabled)
		}//end of if(objform.elements[i].name == "chkopt" )
	}//end of for(i=0;i<objform.length;i++)
 	if(!blIsThereUnsuppressedRecord)
  {
    document.getElementById('chkAll').checked=false;
    document.getElementById('chkAll').disabled=true;
    if(typeof(document.forms[1].deleteButton)!='undefined')
     document.getElementById('deleteButton').style.display="none";
  }//end of if(blIsThereUnsuppressedRecord)
 	
}//end of function hideDeleteButton()