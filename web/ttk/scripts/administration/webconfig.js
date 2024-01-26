/**
 * @ (#) webconfig.js 4th jan 2008
 * Project      : TTK HealthCare Services
 * File         : webconfig.js
 * Author       : Yogesh S.C
 * Company      : Span Systems Corporation
 * Date Created : 4th jan 2008
 *
 * @author       :Yogesh S.C
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 
//this function is called onclick of save button 
function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
	{
		if(document.forms[1].sendMailGenTypeID.value=='WMN')
		{
			document.forms[1].mailGenTypeID.value="";
		}//end of if(!document.forms[1].sendMailGenTypeID.value=='WMN')		
		document.forms[1].mode.value="doSave";
	    document.forms[1].action="/WebConfigSaveAction.do";
	    JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

//this function is called onclick of reset button
function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 {
	  	document.forms[1].mode.value="doReset";
	  	document.forms[1].action="/WebConfigAction.do";
	  	document.forms[1].submit();	
	 }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 else
	 {
	  	document.forms[1].reset();		  	
	 }//end of else		  
}//end of onReset()

//this function is called onclick of close button
function onClose()
{
	if(!TrackChanges()) return false;
 	document.forms[1].mode.value="doClose";
	document.forms[1].action="/WebConfigAction.do";
	document.forms[1].submit();	
}//end of onClose()	


function hideMailGenType()
{
 if(document.forms[1].sendMailGenTypeID.value=='WMY')
 {
  document.getElementById('mailGentypeID').style.visibility="";
  document.getElementById('familyDetails').style.visibility="";
 }//end of  if(document.forms[1].sendMailGenTypeID.value=='WMY')
 else {
  document.getElementById('mailGentypeID').style.visibility="hidden";
  document.getElementById('familyDetails').style.visibility="hidden";
 }//end of else  
}//end of hideMailGenType()

 //this function is called oncChange of From feild
function onChangeFrom()
{
	var reporFrom=document.forms[1].reportFrom.value;
	var temp1='30';
	if(reporFrom != null && reporFrom != '' && reporFrom > '0')
	{
		var totalValue=(parseInt(reporFrom)+parseInt(temp1))-1;
		var reporTo=totalValue-30;
		if(reporTo == '0')
		{
			document.forms[1].reportTo.value='';
		}//end of if(reporTo == '0')
		else
		{
			document.forms[1].reportTo.value=reporTo;
		}//end of else	
	}//end of if(reporFrom != null && reporFrom != '' && reporFrom > '0')
}//end of onChangeFrom()

function hideRating()
{
	if(document.getElementById('onlineAssTypeID').value=='OAA')
	{
	 document.getElementById('rating').style.display="";
	}//end of if(document.getElementById('onlineAssTypeID').value=='OAA')
	else
	{
	 document.getElementById('rating').style.display="none";
	}//end of else
}//end of hideRating()
//Start Modification as perKOC 1159 Aravind Change request
function hidemembers()
{
	if(document.getElementById('relshipCombintnTypeID').value!='RCA')
	{
	 document.getElementById('noOfMembers').style.display="none";
	  document.getElementById('noOf').style.display="none";
	}//end of if(document.getElementById('').value=='')
	else
	{
	 document.getElementById('noOfMembers').style.display="";
	
	}//end of else
	
}//end of hidemembers()
function hidememberslist()// koc note change
{	
	var relation=document.getElementById('relshipCombintnTypeID').value;
	var nomember=document.forms[1].noOfMembers.value;
	//document.getElementsByName('noOfMembers').value;
	
	if(relation =='RCA' && nomember!='2')//document.getElementById('noOfMembers').value
	{

	 document.getElementById('noOf').style.display="none";

	}//end of if(document.getElementById('').value=='')
	else
	{

	 document.getElementById('noOf').style.display="";	
	}//end of else
	
}//end of hidemembers1() 

//End Modification as perKOC 1159 Aravind Change request