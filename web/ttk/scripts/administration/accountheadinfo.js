// Java Script function for accountheadinfo.jsp

function onDocumentLoad()
 {
    objform = document.forms[1];
   	if(document.forms[1].wardTypeID.length)//if more than one records are there
 	{
 		var obj1=document.forms[1].elements['incAcctheadYN'];
 		var obj2=document.forms[1].elements['showAccountHeadYN'];
 		var obj3=document.forms[1].elements['showAcctheadYN'];
 		for(i=0;i<document.forms[1].wardTypeID.length;i++)
  		{
	  		if(obj1[i].value == "Y")
	  		{
	  			document.forms[1].incAccountHeadYN[i].checked=true;
	  			document.forms[1].authGrpSeqID[i].disabled=false;
	  			document.forms[1].showAccountHeadYN[i].disabled=false;
	  			if(obj3[i].value == "Y")
				{
					document.forms[1].showAccountHeadYN[i].checked=true;
				}//end of if(obj3[i].value == "Y")
				else
				{
					document.forms[1].showAccountHeadYN[i].checked=false;
	 			}//end of else
	  		}//end of if(obj1[i].value == "Y")
	  		else
	 		{
	 			document.forms[1].incAccountHeadYN[i].checked=false;
	 			document.forms[1].authGrpSeqID[i].disabled=true;
	  			document.forms[1].showAccountHeadYN[i].disabled=true;
	  			obj3[i].value='N';
	 		}//end of else
	 	}//end of for
 	}//end of if(document.forms[1].wardTypeID.length
 	 onCheck();
 }//end of onDocumentLoad()
 
 function onCheck()
{
	objform = document.forms[1];
	if(document.forms[1].wardTypeID.length)//if more than one records are there
 	{
 		var obj1=document.forms[1].elements['incAccountHeadYN'];
 		var obj2=document.forms[1].elements['showAccountHeadYN'];
		for(i=0;i<document.forms[1].wardTypeID.length;i++)
		{
			if(obj1[i].checked)
			{
				document.forms[1].showAccountHeadYN[i].disabled=false;
				document.forms[1].authGrpSeqID[i].disabled=false;
				if(obj2[i].checked)
				{
					document.forms[1].showAcctheadYN[i].value='Y';
				}//end of if(obj2[i].checked)
				else
				{
					document.forms[1].showAcctheadYN[i].value='N';
				}//end of else
			}//end of if(obj1[i].checked)
		 	else
		 	{
		 		document.forms[1].showAccountHeadYN[i].disabled=true;
				document.forms[1].authGrpSeqID[i].disabled=true;
				document.forms[1].showAcctheadYN[i].value='N';
				document.forms[1].authGrpSeqID[i].value="";
		 		document.forms[1].showAccountHeadYN[i].checked=false;
		 	}//end of else
		}//end of for(i=0;i<document.forms[1].wardTypeID.length;i++)
	}//end of if(document.forms[1].wardTypeID.length)
}//end of onCheck()

function onSave()
{
	trimForm(document.forms[1]);
	if(!JS_SecondSubmit)
	{
		if(isValidated()==true)
		{
			if(document.forms[1].wardTypeID.length)//if more than one records are there
			{
				var obj1=document.forms[1].elements['incAcctheadYN'];
				var obj2=document.forms[1].elements['incAccountHeadYN'];

				var obj3=document.forms[1].elements['showAcctheadYN'];
				var obj4=document.forms[1].elements['showAccountHeadYN'];
				for(i=0;i<document.forms[1].wardTypeID.length;i++)
				{
					if(obj2[i].checked)
					{
						obj1[i].value='Y';
						if(obj4[i].checked)
						{
							obj3[i].value='Y';
						}//end of if(obj4[i].checked)
						else
						{
							obj3[i].value='N';
						}//end of else
					}//end of if(obj2[i].checked)
					else
					{
						obj1[i].value='N';
						obj3[i].value='N';
					}//end of else
					document.forms[1].authGrpSeqID[i].disabled=false;
				}//end of for
			}//end of if
			document.forms[1].mode.value="doSave";
			document.forms[1].action="/AccountHeadInfoAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
		}//end of if(isValidated()==true)	
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

function onReset()
{
	document.forms[1].reset();
  	onDocumentLoad();
}//end of Reset()

function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value="doClose";
	document.forms[1].action="/AccountHeadInfoAction.do";
	document.forms[1].submit();
}//end of onClose()

function isValidated()
 {
 	trimForm(document.forms[1]);
 	var obj1=document.forms[1].elements['incAccountHeadYN'];
 	if(document.forms[1].wardTypeID.length)//if more than one records are there
 	{
	  for(i=0;i<document.forms[1].wardTypeID.length;i++)
	  {
			 if(obj1[i].checked)
		  	 {
				if(document.forms[1].authGrpSeqID[i].value==null || document.forms[1].authGrpSeqID[i].value=="")
				{
					alert('Select the Group Name for the selected Account Head');						
					document.forms[1].authGrpSeqID[i].focus();										
					return false;
				}
		  	 }//end of if(obj1[i].checked)
	 }//end of for
	}//end of if
	return true;
 }//end of isValidated()