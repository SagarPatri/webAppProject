//javascript used in custconfigmsg.jsp of Policies module
function isMessageConfigure()
 {
	 var configYN = document.getElementById("configYN");
	 if(configYN.checked)
	 {
		 document.getElementById("messageid").style.display= "";
		 document.getElementById("messageid1").style.display= "";
	 }//end of if(configYN.checked)
	 else
	 {
		 document.getElementById("messageid").style.display= "none";
		 document.getElementById("messageid1").style.display= "none";
	 }// end of else
 }//end of isMessageConfigure()
 
 
function onClose()
 {
 	if(!TrackChanges()) return false;
 	document.forms[1].mode.value = "doClose";
 	document.forms[1].action = "/CustomConfigMessageAction.do";
 	document.forms[1].submit();	
 }//end of onClose() 

function onSave()
{
		trimForm(document.forms[1]);
		if(!JS_SecondSubmit)
		{
			if(document.forms[1].configYN.checked)
			{
	    		document.forms[1].config.value="Y";
	    		document.getElementById("messageid").style.display= "";
		        document.getElementById("messageid1").style.display= "";
			}//end of if(document.forms[1].configYN.checked)
	    	else
	    	{
	    		document.forms[1].config.value="N";
	    		document.getElementById("messageid").style.display= "none";
		        document.getElementById("messageid1").style.display= "none";
		        document.forms[1].message.value ="";
	    	}//end of else
			document.forms[1].mode.value="doSave";
		    document.forms[1].action="/SaveConfigMsgAction.do";
		    JS_SecondSubmit=true
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
}//end of onSave()
function config()
{
	if(document.forms[1].config.value=="Y")
	{
	    document.forms[1].configYN.checked=true;
	    document.getElementById("messageid").style.display= "";
		document.getElementById("messageid1").style.display= "";
	}//end of if(document.forms[1].config.value=="Y")
	else
	{
		document.forms[1].configYN.checked=false;
		document.getElementById("messageid").style.display= "none";
		document.getElementById("messageid1").style.display= "none";
	}//end of else
}//end of config()

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 {
	  document.forms[1].mode.value="doReset";
	  document.forms[1].action="/CustomConfigMessageAction.do";
	  document.forms[1].submit();
	 }//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	 else
	 {
	  document.forms[1].reset();
	  isMessageConfigure();
	  config();
	 }//end of else   
	
}//end of onReset()
