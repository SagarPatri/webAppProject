// javascript for changepassword.jsp
function onSave()
{
	if(!JS_SecondSubmit)
	{
	     document.forms[0].mode.value = "doChangePassword";
     	 document.forms[0].action = "/OnlineSavePasswordAction.do";
      	 JS_SecondSubmit=true;
	     document.forms[0].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()