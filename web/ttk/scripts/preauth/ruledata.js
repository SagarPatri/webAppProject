//This is javascript file for ruledata.jsp used
// in PreAuth-Claims flow.
function onModifyData()
{
	document.forms[1].mode.value="doModifyRuleData";
	document.forms[1].action = "/ModifyRuleDataAction.do";
	document.forms[1].submit();
}

function onInitiateCheck()
{
	document.forms[1].mode.value="doInitiateCheck";
	document.forms[1].action = "/InitiateCheckAction.do";
	document.forms[1].submit();
}
//Changes as per request 1099
function updateRemarks()
{
	var successCount=0;
	var failCount=0;
	var fieldCount=0;
	var objForm=document.forms[1].elements;
	for(i=0;i<objForm.elements.length;i++)
	{
		field=objForm.elements[i];
		
		if(field.type == 'textarea' )
		{  
			fieldCount++;
			field.value=trim(field.value);

			
             if(field.value.length>150)
             {
	           field.readOnly=false;
	           field.select();
	           field.focus();
	           failCount++;

	           alert("Remarks Length Should Be Less Than 150 Characters.")
	           return false;
              }//end of if (field.value.length>150)
			if(field.value.length<20 || field.value==" ")
			{ 
				
				field.readOnly=false;
				field.select();
				field.focus();
				failCount++;

				alert("Remarks Length Should be Greater Than 20 Characters.")
				return false;
			}  //end of if field.value.length<20   
			else{
				successCount++;
				
			}//end ofelse field.value.length<20 


		}//end of if(field.type == 'textarea' )
		

	}//end of for loop

	if(failCount>0)
	{
		
		alert("Please Enter Remarks For "+failCount+" Failed Rules");
	}//end of if(fail >0)
	else if(successCount<fieldCount){
		alert("You should Enter Remaining "+(fieldCount-successCount)+" Remarks Fields");
	}//end of else if(success<fieldCount)
	else{
		if(fieldCount==successCount)
		{
			document.forms[1].mode.value="doSaveRemarks";
			document.forms[1].action = "/InitiateCheckAction.do";
			document.forms[1].OverrideCompletedYN.value="Y";
			document.forms[1].submit();
		}//end of if(fieldCount==success)
		
	}//end of else
}// end of updateRemarks()


function onOverride()
{
var focusTextArea=null;
var	fieldCount=0;
	var objForm=document.forms[1].elements;
	for(i=0;i<objForm.elements.length;i++)
	{
		field=objForm.elements[i];
		
		if(field.type == 'textarea')
		{
			fieldCount++;
		   	field.readOnly=false;
		   	if(fieldCount==1)
			   {
				focusTextArea=field;
			   }
			
			//continue;
		}//end of if(field.type != "textarea")
		//else{
		
			
		//}//end of else field.type != "textarea"
	}//end of for loop
	focusTextArea.select();
	focusTextArea.focus();
	
}//end of Override()
function onReconfigure()
{
	trimForm(document.forms[1]);
	if(document.forms[1].ruleExecutionFlag.value==2)	//CHECK FOR INITATE CHECK FLAG
	{
		var msg = confirm("Rules has been already executed. Choosing Reconfigure will override the Rules?");
		if(msg)
		{
			document.forms[1].mode.value="doReconfigure";
			document.forms[1].action="/CoverageListAction.do";
			document.forms[1].submit();
		}//end of if(msg)
		else
		{
			return false;
		}//end of else
	}
	else
	{
		document.forms[1].mode.value="doReconfigure";
		document.forms[1].action="/CoverageListAction.do";
		document.forms[1].submit();
	}
}