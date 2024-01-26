//java script for the associate disease screen in products/policies module.
function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value = "doClose";
	document.forms[1].action = "/ClauseDiseaseAction.do";
	document.forms[1].submit();	
}//end of onClose() 

function onSearch()
{
  if(!JS_SecondSubmit)
  {
 	trimForm(document.forms[1]);
	document.forms[1].mode.value = "doSearch";
	document.forms[1].action = "/ClauseDiseaseAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()

//function to associate/exclude
function onAssociateExclude(task)
{
if(!mChkboxValidation(document.forms[1]))
{
	if((document.forms[1].associatedList.value) == (task))
	{
	if(task == 'DBU')
	{
	  alert("Selected disease(s) is already Associated ");
	}//end of if(task == 'DBU')
	else
	{
	  alert("Selected diseases(s) is already Unassociated ");
	}//end of else
	return false;
	}//end of if((document.forms[1].associatedList.value) == (task))
	else
	{
	if(task=='DBU')
	{
		strtask= 'Unassociate';
	}//end of if(task=='DBU')
	else
	{
		strtask= 'Associate';
    }//end of else
	var msg = confirm('Are you sure you want to '+strtask +' selected Disease(s)');
	if(msg){
		if(!JS_SecondSubmit)
      	{
			document.forms[1].mode.value = "doAssociateExclude";
			document.forms[1].action = "/ClauseDiseaseAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();	
		}//end of if(!JS_SecondSubmit)
	}//end of if(msg)
  }//end of else
 }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onAssociateExclude(task)

