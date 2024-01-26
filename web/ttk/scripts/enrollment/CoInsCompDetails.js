//function to display pages based on search criteria
function onSearch()
{
	if(!JS_SecondSubmit)
	 {
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/CoInsCompDetailsAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function edit(rownum)
{
    document.forms[1].mode.value="doViewInsComp";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/CoInsCompDetailsAction.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onAdd(){
	
	if(!JS_SecondSubmit)
	 {
	    document.forms[1].mode.value = "toAddCoInsComp";
	    document.forms[1].action = "/CoInsCompDetailsAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)

}

function onDelete()
{
    if(!mChkboxValidation(document.forms[1])) {
	var msg = confirm("Are you sure you want to delete the selected record(s)?");
		if(msg){
		    document.forms[1].mode.value = "doDeleteList";
		    document.forms[1].action = "/CoInsCompDetailsAction.do";
		    document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

function onCloseAddComp(){

	if(!JS_SecondSubmit)
	 {
		if(!TrackChanges()) return false;
	    document.forms[1].mode.value = "doCloseAddCoInsComp";
	    document.forms[1].action = "/CoInsCompDetailsAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}

function onClose(){

	if(!JS_SecondSubmit)
	 {
	    document.forms[1].mode.value = "doCloseCoInsComp";
	    document.forms[1].action = "/CoInsCompDetailsAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}

function onSave(){
	
	var percentage=parseInt(document.forms[1].coInsPercentage.value);
	if(percentage<0){
		alert("Percentage should be Greater than Zero");
		return false;
	}
	
	if(percentage>100){
		alert("Percentage should be Less than Hundred");
		return false;
	}
	
	if(!JS_SecondSubmit)
	 {
	    document.forms[1].mode.value = "saveCoInsComp";
	    document.forms[1].action = "/CoInsCompDetailsAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}

function getCompanyCode(){
	if(!JS_SecondSubmit)
	 {
	    document.forms[1].mode.value = "toGetCompanyCode";
	    document.forms[1].action = "/CoInsCompDetailsAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
	}