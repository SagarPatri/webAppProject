// This is javascript file for the icdlist.jsp

//function to call edit screen
function edit(rownum)
{
    document.forms[1].mode.value="doSelectICD";
    if(document.forms[0].leftlink.value=="Enrollment")
	{
		document.forms[1].child.value="";
	}
	else
	{
		document.forms[1].child.value="";
	}
    document.forms[1].rownum.value=rownum;

    // Added for ICD Screen implementation by UNNI V MANA

    if(document.forms[0].leftlink.value=="Maintenance" && document.forms[0].sublink.value=="ICD Code")
    {
    	if(document.forms[1].screen.value=="masterlist")
    	{
    		document.forms[1].mastericd.value=rownum;
    		document.forms[1].action = "/ICDAction.do?mode=doAdd";
    	}
    	else
    	{
    		document.forms[1].action = "/ICDAction.do?mode=doView";
    	}
    }
    else{
    	document.forms[1].action = "/ICDListAction.do";
    }
    // End of Addition

    document.forms[1].submit();
}//end of edit(rownum)

//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/ICDListAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/ICDListAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/ICDListAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/ICDListAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

function onSearch()
{
 if(!JS_SecondSubmit)
 {
    trimForm(document.forms[1]);
    document.forms[1].mode.value = "doSearch";
    document.forms[1].action = "/ICDListAction.do";
	JS_SecondSubmit=true;
    document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].rownum.value="";
	document.forms[1].mode.value = "doClose";
	if(document.forms[0].leftlink.value=="Enrollment")
	{
		document.forms[1].child.value="";
	}
	else
	{
		if(document.forms[1].screen.value=="masterlist")
		{
			document.forms[1].mode.value = "doClose";
		}
		else
		{
			document.forms[1].screen.value="close";
		}
		document.forms[1].child.value="";
	}
	if(document.forms[1].screen.value=="masterlist")
	{
		document.forms[1].action = "/ICDAction.do";
	}
	else
	{
   		document.forms[1].action = "/ICDListAction.do";
   	}
	document.forms[1].submit();
}//end of onClose()

// Added for ICD Code screen implementation :UNNI V MANA on 14-05-2008
function onAdd()
{
	document.forms[1].mode.value="doAdd";
	document.forms[1].action="/ICDAction.do";
	document.forms[1].submit();
}

// Added for ICD Code screen implementation :UNNI V MANA on 14-05-2008
function onSave()
{
	document.forms[1].mode.value="doSave";
	document.forms[1].action="/ICDAddAction.do";
	document.forms[1].submit();
}

function SetState(obj_checkbox)
{
	if(obj_checkbox.checked)
    {
	   //obj_text.disabled = false;
	   //obj_text.className = "textBox textBoxSmall";
	 //  document.getElementById(id).style.display="";

    }
    else
    {
	   //obj_text.disabled = true;
	   //obj_text.className = "textBox textBoxSmallDisabled";
	  // document.getElementById(id).style.display="none";
     }

    }

function displayICDScreen()
{
	document.forms[1].screen.value="masterlist";
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/ICDListAction.do";
	document.forms[1].submit();
}

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
	document.forms[1].mode.value="doReset";
	document.forms[1].action="/ICDEditAction.do";
	document.forms[1].submit();
	}
	else
	{
	document.forms[1].reset();
	}
}//end of onReset()

// End of Addition
