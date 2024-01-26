/** @ (#) linkdetails.js 21st Dec 2007
 * Project     : TTK Healthcare Services
 * File        : linkdetails.js
 * Author      : Kishor kumar
 * Company     : RCS
 * Date Created: 14/05/2014
 *
 * @author 		 : Kishor kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 *
*/
 

//this function is called onclick of the save button
function onSave()
{
	
	if(document.forms[1].configLinkDesc.value=="")
	{
		alert("Please Enter Information Description.");
		document.forms[1].configLinkDesc.focus();
		return false;
	}
	else if(document.forms[1].orderNumber.value=="" || document.forms[1].orderNumber.value==0)
	{
		
		alert("Please Enter Priority.");
		document.forms[1].orderNumber.focus();
		
		return false;
	}
	/*else if(document.forms[1].orderNumber.value!="")
	{
		var code=window.event.keyCode;
		//alert(code);
		if(code<48 || code>57)
		{	alert("Enter only Numbers");
		document.forms[1].orderNumber.value="";
		document.forms[1].orderNumber.focus();
			return false;
		}
		
	}*/
	else if(document.forms[1].file.value=="")
	{
		alert("Please Select File.");
		document.forms[1].file.focus();
		return false;
	}
	if(!JS_SecondSubmit)
	{
		trimForm(document.forms[1]);
		//document.forms[1].path.disabled=false;
		document.forms[1].mode.value="doSaveUploads";
		//document.forms[1].action = "/LogAction.do";
		document.forms[1].action = "/HospitalConfigurationAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

function onClose()
{
	if(!TrackChanges()) return false;
    document.forms[1].mode.value="doCloseHosp";
    document.forms[1].action = "/HospitalConfigurationAction.do";
    document.forms[1].submit();
}//end of onClose()

//this function is called onclick of the browse icon
function onBrowse()
{
	
	document.forms[1].mode.value="doBrowse";
    document.forms[1].action = "/HospitalConfigurationAction.do";
    document.forms[1].submit();	
}//end of function onBrowse()

//this function is called onclick of the delete button
function onDelete()
{
	if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to delete the selected Link Information ?");
		if(msg)
		{
		    document.forms[1].mode.value = "doDelete";
		    document.forms[1].action = "/HospitalConfigurationAction.do";
		    document.forms[1].submit();
		}//end of if(msg)
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onDelete()

 
function restrictLetters(event)
{
	/*evt = (evt) ? evt : event;
    var charCode = (evt.charCode) ? evt.charCode : ((evt.keyCode) ? evt.keyCode :
       ((evt.which) ? evt.which : 0));
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
       alert("Enter numerals only in this field.");
       return false;
      }
       return true;*/
	
	var code=window.event.keyCode;
	alert(code)
	if(code<48 || code>57)
	{//alert("Enter only Numbers");
	//document.forms[1].orderNumber.value="";
	return false;
	}
	if(code==39 || code==34){
		window.event.keyCode=96;

	return true;
	}
	
	
	
}


function isPositiveInteger(obj,field_name)
{
	//alert(obj.value)
   for (var i=0;i<obj.value.length;i++)
    {
	     if(isNaN(obj.value.charAt(i)) || obj.value.charAt(i)==" ")
	      {
	         //alert(field_name +" must have only numbers");
			 alert(field_name+" Should be in Numbers");
			 //obj.value.charAt(i)=="";
			 obj.value="";
			 obj.focus();
	         return false;
	       }
    }
    return true;
}//isPositiveInteger(obj,field_name)