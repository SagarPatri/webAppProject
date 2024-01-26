//java script for the edit batch details screen in the inward entry of batch flow.

function onReset()
{	
if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
	    document.forms[1].mode.value="doReset";
	    document.forms[1].action = "/EditBatchAction.do";    
	    document.forms[1].submit();	
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	else
	{
		document.forms[1].reset();
	}//end of else

}//end of onReset()
function changeOffice()
{
	document.forms[1].mode.value="doBatchChangeOffice";
	document.forms[1].child.value="Insurance Company";
	document.forms[1].action="/EditBatchAction.do";
	document.forms[1].submit();
}//end of changeOffice()
function onSave()
{	
	
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit){
		//document.forms[1].mode.value="doSave";changed this for intX
		document.forms[1].mode.value="doSaveBatchIns";
    	document.forms[1].action = "/SaveBatchAction.do";
    	JS_SecondSubmit=true;
    	document.forms[1].submit();
   }//end of if(!JS_SecondSubmit)
}//end of onSave()
function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value="doClose";
	document.forms[1].action="/PolicyAction.do";
	document.forms[1].submit();
}//end of onClose()


//Ajax code to get Provider details dynamically. for intX
function getInsCode(obj) 
{
	if(document.getElementById("companyName").value == "" || document.getElementById("companyName").value == null )
	{
		document.getElementById("officeCode").value  = "";
		document.getElementById("companyName").focus();
	}	
	var provname	=	obj.value;
	if(!(provname.indexOf("@") == -1))
	{
			var res = provname.split("@");
			
			document.getElementById("companyName").value = res[0];
			var officeCode  = document.getElementById("officeCode").value  = res[1];
			if (window.XMLHttpRequest){ // Non-IE browsers
				 xmlhttp = new XMLHttpRequest();
				 try {
					if (xmlhttp){
						xmlhttp.open("GET",'/EditBatchAction.do?mode=getInsCode&insCode=' +officeCode ,true);
						xmlhttp.onreadystatechange=state_ChangeProvider;
						xmlhttp.send();
					}
				 }catch (e){
					alert(e);
				 }
			   }
		    if (window.ActiveXObject)
		    {
		    	xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		    	if (xmlhttp)
		    	{
		    		xmlhttp.open("GET",'/EditBatchAction.do?mode=getInsCode&insCode=' +officeCode ,true);
		    		xmlhttp.onreadystatechange=state_ChangeProvider;
		    		xmlhttp.send();
		    	}
		    }
	
	 }

function state_ChangeProvider()
	{
		var result,result_arr;
		if (xmlhttp.readyState==4)
		{
			//alert(xmlhttp.status);
			if (xmlhttp.status==200)
			{
					result = xmlhttp.responseText;
					result_arr = result.split(","); 
					document.getElementById("officeCode").value=result_arr[0];
					document.forms[1].hidInsuranceSeqID.value=result_arr[1];
			}
		}
	}
 }