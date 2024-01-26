/** @ (#) addenrollment.js 09th Jan 2007
 * Project     : TTK Healthcare Services
 * File        : addenrollment.js
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 09th Jan 2007
 *
 * @author 		 : Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */

//this function is called onclick of save button
/*function onSave()
{
	if(!JS_SecondSubmit)
    {
		trimForm(document.forms[1]);
		document.getElementById("memname").value=document.forms[1].name.value;
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/UpdateAddEnrollmentAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of	if(!JS_SecondSubmit)
}//end of onSave()*/

<!---Modified Save for IBM....19-->
function onSave()
{
	if(!JS_SecondSubmit)
    {
			   
	   if(((document.forms[1].validEmailPhYN!= 'undefined') && (document.forms[1].validEmailPhYN != null)  && (document.forms[1].validEmailPhYN != ''))
	   || ((document.forms[1].stopOPtInYN!= 'undefined') && (document.forms[1].stopOPtInYN != null)  && (document.forms[1].stopOPtInYN != '')))
	   {
			//alert("top");
		  
		if(document.forms[1].validEmailPhYN.checked)
		{
			if(document.forms[1].EmailId2.value == "")
	  		{
			alert('Please Enter Personal EmailID');
			document.forms[1].EmailId2.focus();
			return false;
		   }
	 		if(document.forms[1].MobileNo.value == "")
			{
			alert('Please Enter Mobile No');
			document.forms[1].MobileNo.focus();
			return false;
			} 		
			
		   document.forms[1].EmailPh.value="Y";		  
			
		}
		else
		{
			document.forms[1].EmailPh.value="N";		
		}
////////////////////
		if(document.forms[1].stopOPtInYN.checked)
		{			
				document.forms[1].OPT.value="N";			
		
		}		
		else 
		{				
		document.forms[1].OPT.value="Y";
		trimForm(document.forms[1]);	
		}
	
		}
		  //alert(document.forms[1].EmailPh.value);		   
		 //alert(document.forms[1].OPT.value);				
		//no else final conditions
		    document.forms[1].mode.value="doSave";
			document.forms[1].action="/UpdateAddEnrollmentAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

<!--Ended---->
//this function is called onclick of close button
function onClose()
{
	if(!TrackChanges()) return false;
    //document.forms[1].mode.value="doClose";
	document.forms[1].mode.value="doDefault";
    document.forms[1].child.value="";
    //document.forms[1].action="/AddEnrollmentAction.do";
    document.forms[1].action="/OnlineMemberAction.do";
    document.forms[1].submit();
}//end of onClose()

function onHRClose()
{
	if(!TrackChanges()) return false;
    document.forms[1].mode.value="doClose";
	document.forms[1].child.value="";
    document.forms[1].action="/AddEnrollmentAction.do";
    document.forms[1].submit();
}//end of onClose()

//this function is called onclick of reset button
function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  	{
    	document.forms[1].mode.value="doReset";
    	document.forms[1].action="/AddEnrollmentAction.do";
    	document.forms[1].submit();
  	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
  else
  {
    document.forms[1].reset();
    if(typeof(validEmailPhYN)!='undefined')
    {	
        if(document.forms[1].EmailPh.value=="Y")//overwritten by Praveen Nov8.2012
        document.forms[1].validEmailPhYN.checked=true;
        else
        document.forms[1].validEmailPhYN.checked=false;
    }    
	
  }//end of else
}//end of onReset()
function onSend()
{
	document.forms[1].mode.value="doSend";
	document.forms[1].action="/UpdateAddEnrollmentAction.do";
	document.forms[1].submit();		
}//end of onSend()

<!--Added for IBM....19.1-->

//koc1216 added by Rekha 13.07.2012
//------------------------

function stopOPTYN()
{
	//if(document.forms[1].OPT.value=="Y")
	if(document.forms[1].OPT.value=="N")//overwritten by Praveen Nov8.2012
	    document.forms[1].stopOPtInYN.checked=true;
	else
		
	    document.forms[1].stopOPtInYN.checked=false;
	
}//end of stopOPTYN()

function EmailMobPhVal()
{
	//if(document.forms[1].OPT.value=="Y")
	if(document.forms[1].EmailPh.value=="Y")//overwritten by Praveen Nov8.2012
	    document.forms[1].validEmailPhYN.checked=true;
	else
		
	    document.forms[1].validEmailPhYN.checked=false;
	
}//end of EmailMobPhVal()

//koc1216 end added by Rekha 13.07.2012
<!--Ended---->