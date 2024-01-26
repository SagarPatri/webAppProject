//java script for the other policy list screen in enrollment flow

function onCompare()
{ 
	 var obj=document.forms[1].elements['chkopt'];
	 var val="";
	 count=0;
 		
	 if(document.forms[1].chkopt.length) 
	 {
	 	for(i=0;i<obj.length;i++)
	 	{
	 		if(obj[i].checked)
	 		{
	 			count=count+1;
	 			val=val+'&amp;'+'chkopt='+obj[i].value;
	 			
	 		}//end of if(obj[i].checked)
	 		
	 	}//end of for(i=0;i<=obj.length;i++)
	 }//end of if(document.forms[1].chkopt.length) 
	 else
	 {
	 	if(document.forms[1].chkopt.checked)
	 	{
	 		count=count+1;
	 		val=val+'&amp;'+'chkopt='+document.forms[1].chkopt.value;
	 	}//end of if(document.forms[1].chkopt.checked)
	 }//end of else
 	 if(count==0)
	 {
 		alert('Please select atleast one policy to compare');
	 }//end of if(count==0)
	 
	else if(count>2)
 	{	
 		alert('Please select not more than 2 Policies to Compare');	
	}//end of else if(count>2)
	else
	{	
		openWindow("/ComparePolicyAction.do?mode=Compare"+val,"1000%", "500%");
	}//end of else
	
}//end of onCompare()

function onAssociate()
{   
	var obj=document.forms[1].elements['chkopt'];
 	count=0;
 		
	 if(document.forms[1].chkopt.length) 
	 {
	 	for(i=0;i<obj.length;i++)
	 	{
	 	
	 		if(obj[i].checked)
	 		{
	 			count=count+1;
	 			
	 		}//end of if(obj[i].checked)
	 		
	 	}//end of for(i=0;i<=obj.length;i++)
	 }//end of if(document.forms[i].chkopt.length
	 else
	 {	
	 	if(document.forms[1].chkopt.checked)
	 	{
	 		count=count+1;
	 		
	 	}//end of if(document.forms[1].chkopt.checked)
	 }//end of else
 	 
 	 if(count==0)
	 {
 		alert('Please select atleast one Scheme ');
	 
	 }//end of if(count==0)
	 
	else if(count>1)
 	{	
 		alert('Please select only 1 Scheme ');	
	}//end of else if(count>1)
	else
	{
    	document.forms[1].mode.value="Associate";
    	document.forms[1].action = "/OtherPolicyAction.do";
    	document.forms[1].submit();
	}//end of else
	
}//end of onAssociate()


function onUnAssociate()
{   
	var obj=document.forms[1].elements['chkopt'];
 	count=0;
 		
	 if(document.forms[1].chkopt.length) 
	 {
	 	for(i=0;i<obj.length;i++)
	 	{
	 	
	 		if(obj[i].checked)
	 		{
	 			count=count+1;
	 			
	 		}//end of if(obj[i].checked)
	 		
	 	}//end of for(i=0;i<=obj.length;i++)
	 }//end of if(document.forms[i].chkopt.length
	 else
	 {	
	 	if(document.forms[1].chkopt.checked)
	 	{
	 		count=count+1;
	 		
	 	}//end of if(document.forms[1].chkopt.checked)
	 }//end of else
 	 
 	 if(count==0)
	 {
 		alert('Please select atleast one Scheme ');
	 
	 }//end of if(count==0)
	 
	else if(count>1)
 	{	
 		alert('Please select only 1 Scheme ');	
	}//end of else if(count>1)
	else
	{
    	document.forms[1].mode.value="Un-associate";
    	document.forms[1].action = "/OtherPolicyAction.do";
    	document.forms[1].submit();
	}//end of else
	
}//end of onUnAssociate()


function onClose()
{    
	if(!TrackChanges()) return false;

    document.forms[1].mode.value="Close";
    document.forms[1].action = "/OtherPolicyAction.do";
    document.forms[1].submit();
}//end of onClose()



