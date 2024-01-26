//java script for the clauses screen in the administration of products and policies flow.

function onSave()
{
  	trimForm(document.forms[1]);
  	if(!JS_SecondSubmit)
  	{
	    document.forms[1].mode.value="doSave";
	    document.forms[1].action = "/UpdateClauseAction.do";
	    JS_SecondSubmit=true
	    document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSave()

function edit(rownum)
{
    document.forms[1].mode.value="doViewClause";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/ClauseAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onReset()
{
    if(typeof(ClientReset)!= 'undefined' && !ClientReset)
    {
        document.forms[1].mode.value="doReset";
        document.forms[1].action="/ClauseAction.do";
        document.forms[1].submit();
    }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
    else
    {
        document.forms[1].reset();
    }//end of else if(typeof(ClientReset)!= 'undefined' && !ClientReset)
}//end of onReset()

function onClose()
{
	if(!TrackChanges()) return false;
    document.forms[1].mode.value="doClose";
    document.forms[1].action = "/ClauseAction.do";
    document.forms[1].submit();
}// End of onClose

function onDelete()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	var msg = confirm("Are you sure you want to delete the selected Clause(s) ?");
	if(msg)
	{
	    document.forms[1].mode.value = "doDelete";
	    document.forms[1].action = "/DelClauseAction.do";
	    document.forms[1].submit();
	}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()


function onICDIcon(rownum)
{
	document.forms[1].mode.value="doDefault";
	document.forms[1].rownum.value=rownum;
 	document.forms[1].action="/ClauseDiseaseAction.do";
 	document.forms[1].submit();
}//end of onICDIcon(rownum)
//added as per SHORTFALL CR 1179
function showhideSubClause(selObj){
	 var selVal = selObj.options[selObj.selectedIndex].value;
	 if(selVal=='SRT')
	 {
		 document.getElementById("policyprodClauseSubtype").style.display="";
	 }
	 else{
		 document.getElementById("policyprodClauseSubtype").style.display="none";
		 //document.forms[1].clauseFor.value="OTH";
	 }
	
}//added as per SHORTFALL CR 1179
