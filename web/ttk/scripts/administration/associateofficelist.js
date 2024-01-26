//JavaScript to Associate office : checked row seq ids are concatenated
//to form pipe separated values
function onAssociate()
{
	
	objform = document.forms[1];
	var offids="|";
	var j=0;
	for(i=0;i<objform.length;i++)
	{
		if( (objform.elements[i].id).indexOf("chkid") != -1)
		{
			if(objform.elements[i].checked)
			{
				j=1;
				offids = offids + objform.elements[i].value +"|";
			}//end of if(objform.elements[i].checked)
		}//if( (objform.elements[i].id).indexOf("chkid") != -1)
	}//end of for(i=0;i<objform.length;i++) 
	if(j==0)
	{		
		offids="";
		alert('Please select atleast one record');		
	}//end of if(j==0)	
	else {	
	var msg = confirm('Are you sure you want to Associate');
	if(msg)
	{		
	document.forms[1].mode.value = "doAssociate";
	document.forms[1].sOffIds.value = offids;	
	document.forms[1].action = "/AssociateOfficeListAction.do";
	document.forms[1].submit();
	}//end of if(msg)
	}//end of else
}
function onClose()
{
	document.forms[1].mode.value = "doClose";
	document.forms[1].action = "/AssociateOfficeListAction.do";
	document.forms[1].submit();	
}	

//to handle check/uncheck 
function oncheck(strId)
{
	var bFlag=true;
	if(strId =='checkall')
		bFlag = document.getElementById("checkall").checked;
	objform = document.forms[1];
	for(i=0;i<objform.length;i++)
	{
		var regexp=new RegExp("^(chkid){1}[0-9]{1,}$");
		//if(regexp.test(objform.elements[i].id))
		if( (objform.elements[i].id).indexOf("chkid") != -1 )
		{
			if(strId =='checkall')
				objform.elements[i].checked = bFlag;
			else if(objform.elements[i].checked != true)
			{
				bFlag=false;
				break;
			}
		}//end of if(regexp.test(objform.elements[i].id))
	}//end of for(i=0;i<objform.length;i++)

 	document.getElementById("checkall").checked = bFlag ;
}

function selectValues(strOffIds)
{
	objform = document.forms[1];
	var isAllSelected = 0;
	//to select the rows whose seq id was there in session value to associate
	for(i=0;i<objform.length;i++)
	{
		if( (objform.elements[i].id).indexOf("chkid") != -1 )
		{
		if(	(strOffIds.indexOf("|"+objform.elements[i].value+"|"))	!= -1 )
				{
					objform.elements[i].checked=true;
				}//end of if(	(strOffIds.indexOf("|"+objform.elements[i].value+"|"))	!= -1 )
		}//end of if( (objform.elements[i].id).indexOf("chkid") != -1 )			
		
	}//	for(i=0;i<objform.length;i++)
	//if all the rows are selected checkall check box should be checked true
	for(i=0;i<objform.length;i++)
	{
		if( ((objform.elements[i].id).indexOf("chkid") != -1) && !(objform.elements[i].checked) )
		{
			isAllSelected = 1;
			break;
		}//end of if( (objform.elements[i].id).indexOf("chkid") != -1 )
	}//end of for(i=0;i<objform.length;i++)	
	if(isAllSelected ==0)
		document.forms[1].checkall.checked = true;
	
}//end of function selectValues(strOffIds)








