/**
 * This js is added For CR KOC1168 Feedback Form 
 * 
 */ 
function onSave()
{
  //Start Modification as per multiple browser CR
  var objForm=document.forms[1];
   //end Modification as per multiple browser CR
    for(i=0;i<objForm.elements.length;i++)
    {
          field=objForm.elements[i];
          if(field.type == 'textarea' )
          { 
                field.value=trim(field.value);
                if(field.value.length>1800)
                {
                	field.select();
                	field.focus();
                	alert("Remarks entered should not exceed 1800 Characters.")
                	return false;
                }//end of if (field.value.length>1500)      
          }//end of if(field.type == 'textarea' )
    }//end for
	document.forms[1].mode.value="doSave";	
    document.forms[1].action="/SaveChangeCashlessFBAction.do";
	document.forms[1].submit();
}//end of onSave() 
function onReset()
{
	document.forms[1].reset();
}//end of onReset()
function onClose()
{
	document.forms[1].mode.value="doClose";	
    document.forms[1].action="/ChangeCashlessFBAction.do";
	document.forms[1].submit();
}//end of onClose()
 
 