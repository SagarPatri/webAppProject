//set the selected value to the appropriate text box
function onPhoneNoSelect()
{
		var phonedetails=(document.forms[1].contactphone.value);		
		if(phonedetails=="")
		{
			document.forms[1].txtphoneno.value="";
			document.forms[1].txtextension.value="";
			document.forms[1].commCode.options.value="";
			return false;
		}//end of if(phonedetails=="")		
		var phoneArray=phonedetails.split("|");
		var phoneid='';
		if(phoneArray[0]=='')
			phoneid=0;
		else
			 phoneid=phoneArray[0];
		var phonetype=phoneArray[1];
		var phoneno=phoneArray[2];
		var phoneext=phoneArray[3];
		document.forms[1].txtphoneno.value=phoneno;
		document.forms[1].txtextension.value=trim(phoneext);
		for(var i=0;i<document.forms[1].commCode.options.length;i++)
		{
			if(document.forms[1].commCode.options[i].value==phonetype)
				document.forms[1].commCode.options[i].selected=true;
		}// end of for(var i=0;i<document.forms[1].commCode.options.length;i++)		
}//end of onPhoneNoSelect()
//function to delete the phone number
function onDeletePhoneNo()
{
	if(document.forms[1].contactphone.selectedIndex>0)
	{
		if(document.forms[1].contactphone.options[0].selected != true)
		{
			var msg = confirm("Are you sure you want to delete the selected phone number ?");
			if(msg)
			{
				var splitPhone = document.forms[1].contactphone.options[document.forms[1].contactphone.options.selectedIndex].value.split("|");
				if(parseInt(splitPhone[0]) > 0)
				{
					if(document.forms[1].deletephoneid.value == "")
                        document.forms[1].deletephoneid.value = "|" +splitPhone[0]+"|";
                    else
                    	document.forms[1].deletephoneid.value = "|" +document.forms[1].deletephoneid.value+splitPhone[0]+"|"; 
                }//end of if(parseInt(splitPhone[0]) > 0)
                document.forms[1].contactphone.options[document.forms[1].contactphone.options.selectedIndex]=null;    	
				document.forms[1].txtphoneno.value="";
				document.forms[1].txtextension.value="";
				document.forms[1].commCode.options.value="";
           }//end of if(msg)
       }// end of if(document.forms[1].contactphone.options[0].selected != true)
   }//end of if(document.forms[1].contactphone.selectedIndex>0)
   else
    {
        alert("Please select a phone number to delete");
    }//end of else
}// end of onDeletePhoneNo()
//function to validate phone number and phone type
function isPhoneValidate()
{
	if(document.forms[1].commCode.value=='')
	{
		alert("Please select the phone type");
		document.forms[1].commCode.focus();
		return false;
	}// end of if(document.forms[1].commCode.value=='')
	document.forms[1].txtphoneno.value=trim(document.forms[1].txtphoneno.value);
	if(isPositiveInteger(document.forms[1].txtphoneno," Phone Number")==false)
	{
		document.forms[1].txtphoneno.focus();
		return false;
	}// end of if(isPositiveInteger(document.forms[1].txtphoneno," Phone Number")==false)
	document.forms[1].txtextension.value=trim(document.forms[1].txtextension.value);
	if(document.forms[1].txtextension.value.length!=0)
	{
		if(isPositiveInteger(document.forms[1].txtextension,"Extension Number")==false)
			return false;
	}// end of if(document.forms[1].txtextension.value.length!=0) 
	return true;
}//end of isPhoneValidate()
//function to add and edit  the phone number
function onSavePhoneno()
{
	if(isPhoneValidate())
	{
		var phonedetails=(document.forms[1].contactphone.value);
		var phoneArray=phonedetails.split("|");
		var phoneid=0;
		if(phoneArray[0]=='')
			phoneid=0;
		else
		    phoneid=phoneArray[0];
		var phonetype=document.forms[1].commCode.options.value; 
		var phoneno=document.forms[1].txtphoneno.value+"[ "+document.forms[1].txtextension.value+" ]";
		var phonenumber=document.forms[1].txtphoneno.value;
		var phoneext=document.forms[1].txtextension.value;
		//to add phonenumber extension and type	
		for(var i=0; i < document.forms[1].contactphone.options.length; i++)
		{
				//to add phonenumber extension and type
				if(document.forms[1].contactphone.value=="") 
				{
						var phonelist=phoneid+ "|" +phonetype+ "|" +phonenumber+ "| " +phoneext+"|";
						var offset=document.forms[1].contactphone.options.length;
						var option0=new Option(phoneno,phonelist);
						document.forms[1].contactphone.options[offset]=option0;
			            document.forms[1].txtphoneno.value="";
						document.forms[1].txtextension.value="";
						document.forms[1].commCode.options.value="";
						document.forms[1].contactphone.value=phonelist;
						document.forms[1].contactphone.options[0].selected=true;
						return true;
				}// end of if(document.forms[1].contactphone.value=="")
				// to edit phonenumber extension and type
				if(document.forms[1].contactphone.options[i].selected == true)
		        {
		            var phonelist=phoneid+ "|" +phonetype+ "|" +phonenumber+ "| " +phoneext+"|";
					document.forms[1].contactphone.options[i].value=phonelist;
		            document.forms[1].contactphone.options[i].text=phoneno;
		            document.forms[1].contactphone.options[0].selected=true;
					document.forms[1].txtphoneno.value="";
					document.forms[1].txtextension.value="";
					document.forms[1].commCode.options.value="";
					return true;
		        }//end of if(document.forms[1].contactphone.options[i].selected == true)
	    }//end of for(var i=0; i < document.forms[1].contactphone.options.length; i++)		
    }//end of if(isPhoneValidate()) 
}//end of onSavePhoneno()
//function to select all the phone numbers
function onSelectAll()
{
	var phoneno = document.forms[1].contactphone.options;
	if(document.forms[1].contactphone.selectedIndex == 0)
	    document.forms[1].contactphone.options[0].selected=false;
	var strPhone ='';
		for(var i=0;i<phoneno.length;i++)
		{
			if(strPhone=='')
				strPhone =phoneno[i].value;
			else
				strPhone =strPhone+phoneno[i].value;
		}// end of for(var i=0;i<phoneno.length;i++)
		document.forms[1].phoneDetails.value =strPhone;
		if(document.forms[1].phoneDetails.value!="")
		{
			document.forms[1].phoneDetails.value ="|"+strPhone;
		}// end of if(document.forms[1].phoneDetails.value!="")	
}//end of onSelectAll()