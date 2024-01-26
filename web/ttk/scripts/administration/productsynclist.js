//java script for the edit productsync list screen in the administration of products flow.
var popupWindow=null;
var myWindow;
function save()
{	
	objform = document.forms[0];
	var checkid="checkid";
	var strCheckedVal="";
	var chkoptt=document.forms[0].chkoptt.value
	var controlname=  document.forms[0].strControlName.value;
	var showRadio=document.forms[0].showRadio.value;
	var strTypeId	=	document.forms[0].strTypeId.value;
	if(showRadio=='Y')  //If showing radio box
	{
		for(var i=0;i<objform.length;i++)
		{
			var regexp=new RegExp("^(radid){1}[0-9]{1,}$"); 
			if(regexp.test(objform.elements[i].id))
			{
				if(objform.elements[i].checked == true)
				{
					strCheckedVal=objform.elements[i].value;
					break;
				}//end of if(objform.elements[i].checked == true)
			}//end of if(regexp.test(objform.elements[i].id))
		}//end of for(i=0;i<objform.length;i++)
		
		if(strCheckedVal=='')
		{
			alert('Select atleast one record');
			return false;
		}//end of if(strCheckedVal=='')
	}//end of if(showRadio=='Y')
	else
	{
		if("Pages to be Synchronized"==strTypeId)
		{var chkdYN	=	"";
		for(i=0;i<objform.length;i++)
		{
			var regexp=new RegExp("("+checkid+"){1}[0-9]*");
			if(regexp.test(objform.elements[i].id))
			{
				if(objform.elements[i].checked == true) 
					chkdYN	=	"Y";
				else	
					chkdYN	=	"N";
					strCheckedVal=strCheckedVal+objform.elements[i].value+"|"+chkdYN+"||";
			}//end of if(regexp.test(objform.elements[i].id))
				
		}//end of for(i=0;i<objform.length;i++)
		strCheckedVal	=	"||"+strCheckedVal;
		if(strCheckedVal=="||") // if no check box is selected
			strCheckedVal="";
		else 				 // remove the last '|'
			strCheckedVal=strCheckedVal.substring(0,strCheckedVal.lastIndexOf('||'));
		}
		else{
			for(i=0;i<objform.length;i++)
			{
				var regexp=new RegExp("("+checkid+"){1}[0-9]*");
				if(regexp.test(objform.elements[i].id))
				{
					if(objform.elements[i].checked == true)
						strCheckedVal=strCheckedVal+objform.elements[i].value+"|";
				}//end of if(regexp.test(objform.elements[i].id))
			}//end of for(i=0;i<objform.length;i++)
			if(strCheckedVal=="|") // if no check box is selected
				strCheckedVal="";
			else 				 // remove the last '|'
				strCheckedVal=strCheckedVal.substring(0,strCheckedVal.lastIndexOf('|'));
		}
	}//end of else
/* 	if (window.opener && !window.opener.closed)
			window.opener.document.getElementById(controlname).value = strCheckedVal;	 */
	/*  if(strCheckedVal!=null)
		 {
		    document.getElementById("mode").value = "doAddProductSync";
			document.forms[1].action = "/ProductListSyncAction.do?mode=doAddProductSync&strControlName="+strCheckedVal+"&chkopt="+chkopt;";
	    	document.forms[1].submit();
		 }*/
	 
	//myWindow.close();
 if(strCheckedVal!=null)
	 {
	 window.opener.document.forms[1].mode.value = "doAddProductSync";
	//alert(window.opener.document.getElementById("mode").value);
	 window.opener.document.forms[1].action = "/ProductListSyncAction.do?mode=doAddProductSync&strControlName="+strCheckedVal+"&chkoptt="+chkoptt;
	 window.opener.document.forms[1].submit();
 	self.close();
	 /*var openPage = "/ProductListSyncAction.do?mode=doAddProductSync&strControlName="+strCheckedVal+"&chkopt="+chkopt;
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	  
	   window.open(openPage,'',features);
	
	   self.close();*/
	 }

}//end of save()

function checkPreSelect(iCount)
{
	var iTotalChecked = 0;
	var showRadio=document.forms[0].showRadio.value;
	var controlId=document.forms[0].strControlName.value;
	var strTypeId	=	document.forms[0].strTypeId.value;
	if(showRadio=='Y')		//for showing radio box
	{
		var selVal= window.opener.document.getElementById(controlId).value;
		for(var i=0;i<iCount;i++)
		{
			objCheck = document.getElementById("radid"+i);
			if(objCheck.value == selVal)
			{
				objCheck.checked= true;
				break;
			}//end of if(objCheck.value == selVal)
		}//end of for(i=0;i<objform.length;i++)
	}//end of if(showRadio=='Y')
	else	//for showing check box
	{
		if(strTypeId=="Pages to be Synchronized"){
		
		var txtBoxVals	=	document.forms[0].txtBoxVals.value;
		var txtBoxValsArr	=	txtBoxVals.split("\|");
		var l	=	3;
		for(var k=0;k<txtBoxValsArr.length;k++)
		{
			/* if(txtBoxValsArr[k+1]=="Y")
			{ */
				objCheck = document.getElementById("checkid"+k);
				//if(isSelectedForEmpnl(txtBoxValsArr[k+1])==1)
				if(txtBoxValsArr[l]=="Y")
				{
					objCheck.checked = true;
					iTotalChecked++;
				}//end of if(strPreSelected.indexOf(objCheck.value)>=0)
			//}
				l	=	l+3;
		}
			
		/* for(i=0;i<iCount;i++)
		{
			objCheck = document.getElementById("checkid"+i);
			if(isSelected(objCheck.value)==1)
			{
				objCheck.checked = true;
				iTotalChecked++;
			}//end of if(strPreSelected.indexOf(objCheck.value)>=0)
		}//end of for(i=0;i<iCount;i++)
		 */}
		else{
			for(i=0;i<iCount;i++)
			{
				objCheck = document.getElementById("checkid"+i);
				if(isSelected(objCheck.value)==1)
				{
					objCheck.checked = true;
					iTotalChecked++;
				}//end of if(strPreSelected.indexOf(objCheck.value)>=0)
			}//end of for(i=0;i<iCount;i++)}
		}
		if(iTotalChecked==iCount)
			document.getElementById("checkall").checked = true;
	}//end of else
}//end of checkPreSelect(iCount)

function isSelected(strId)
{
	var controlname = document.forms[0].strControlName.value;
	var strPreSelected = window.opener.document.getElementById(controlname).value;
	var temp = new Array();
	temp = strPreSelected.split('|');
	for(var j=0;j<temp.length;j++)
		if(temp[j]==strId)
		  return 1;
	return 0;
}//end of isSelected(strId)

function isSelectedForEmpnl(strId)
{
	var controlname = document.forms[0].strControlName.value;
	var strPreSelected = window.opener.document.getElementById(controlname).value;
	var temp = new Array();
	temp = strPreSelected.split('|');
	//for(j=1;j<temp.length;j=(j+1))
		alert("strId::"+strId+"::index::"+index+"temp[index]::"+temp[index]);
		if(temp[index]==strId)
		  return 1;
	return 0;
}//end of isSelected(strId)

function oncheck(strId)
{
	var i	=	0;
	var bFlag=true;
	if(strId =='checkall')
		bFlag = document.getElementById("checkall").checked;
	var checkid="checkid";
	objform = document.forms[0];
	for(i=0;i<objform.length;i++)
	{
		var regexp=new RegExp("("+checkid+"){1}[0-9]*");
		if(regexp.test(objform.elements[i].id))
		{
			if(strId =='checkall')
				objform.elements[i].checked = bFlag;
			else if(objform.elements[i].checked != true)
			{
				bFlag=false;
				break;
			}//end of else if(objform.elements[i].checked != true)
		}//end of if(regexp.test(objform.elements[i].id))
	}//end of for(i=0;i<objform.length;i++)
 	document.getElementById("checkall").checked = bFlag ;
}//end of oncheck(strId)
//function to sort based on the link selected
function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/ProductListSyncAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/ProductListSyncAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/ProductListSyncAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/ProductListSyncAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

/*function onSynchronize()
{
    if(!mChkboxValidation(document.forms[1]))
    {
    	var msg = confirm("Do you want to Synchronize the rules for the selected Schemes?");
    	if(msg)
	    {
	    	document.forms[1].mode.value = "doAddProductSync";
   			document.forms[1].action = "/ProductListSyncAction.do";
	    	document.forms[1].submit();
	    }//end of if(msg)	
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onSynchronize()
*/


function onSynchronizeAll()
{
	 	var msg = confirm("Do you want to Synchronize the rules for all the Schemes?");
    	
    	if(msg)
	    {
	    	document.forms[1].mode.value = "doAddProductSyncAll";
   			document.forms[1].action = "/ProductListSyncAction.do";
	    	document.forms[1].submit();
	    }//end of if(msg)	
    
}


function openPopUpWindow(strControlName,strTypeID)
{
	var w = 350;
	var h = 250;
	var strControlValue="";
	strControlValue =strControlName;

	var allVals = [];
	if(mChkboxValidation(document.forms[1]))
      {
		return false;
      }
	else
		{
	var len= document.forms[1].chkopt.length;
	    if(len===undefined)
	    	len=1;
	
	if(len==1){
		
		allVals[0] = 0;
		allVals[0] =  "|"+allVals[0] ;
	}
	
	else{
		if(len != 0)
		{
			for(var i=0;i<len;i++)
			{
			
					/*if(i == 0)
					{
						allVals[0] = 0;

					}
					else*/  if(i >= 0)
						{
						
					if(document.forms[1].chkopt[i].checked)
					{ 

						if(allVals[0]!=null)
						{
							allVals[0] = allVals[0] +"|"+ document.forms[1].chkopt[i].value;
						}
						else{
							allVals[0] = "|"+ document.forms[1].chkopt[i].value;
							
						}

					}
				}

			}

		}
		
	}
	
	
	
	
	
	
	
	

	

	var openPage = "/ttk/administration/synchronizeformat.jsp?chkoptt="+allVals+"&typeId="+strTypeID+"&controlName="+strControlName+"&controlVal="+strControlValue+"&showRadio=N";
	var features = "scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;

	
	myWindow=window.open(openPage,'',features); 
/*	var timer = setInterval(function () {
	    if (win.closed) {
	      
	        clearInterval(timer);
	        window.parent.close(); // Refresh the parent page
	    }
	}, 1000);
	*/
//	window.parent.close();
	/*popupWindow.focus(); 
	document.onmousedown=focusPopup; 
	document.onkeyup=focusPopup; 
	document.onmousemove=focusPopup;  */
		}
}//end of openList(strControlName,strTypeID)

function focusPopup() {
	  if(popupWindow && !popupWindow.closed) { popupWindow.focus(); } 
	
	}
//denial process
function onInsurerSynchronize()
{
    if(!mChkboxValidation(document.forms[1]))
    {
    	var msg = confirm("Are you sure want to Synchronize the insurer denial process for the selected Schemes?");
    	if(msg)
	    {
	    	document.forms[1].mode.value = "doAddInsProductSync";
   			document.forms[1].action = "/ProductListSyncAction.do";
	    	document.forms[1].submit();
	    }//end of if(msg)	
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of onInsurerSynchronize()
//denial process
function onSearch()
{
if(!JS_SecondSubmit)
 {
    trimForm(document.forms[1]);
	    if(isValidated())
	    {
	    	document.forms[1].sPolicyNbr.value=trim(document.forms[1].sPolicyNbr.value);
	    	document.forms[1].sCorporateName.value=trim(document.forms[1].sCorporateName.value);
	    	document.forms[1].sValidFrom.value=trim(document.forms[1].sValidFrom.value);
	    	document.forms[1].sValidDate.value=trim(document.forms[1].sValidDate.value);
    		document.forms[1].mode.value = "doSearch";
    		document.forms[1].action = "/ProductListSyncAction.do";
			JS_SecondSubmit=true
    		document.forms[1].submit();
    	}	
 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onClose()
{
    document.forms[1].mode.value = "doClose";
    document.forms[1].action = "/ProductListAction.do";
    document.forms[1].submit();
}//end of ()

function isValidated()
{
	var strValidFrom = document.forms[1].sValidFrom;	
	var strValidDate = document.forms[1].sValidDate;	
		//checks if Valid From is entered
		if(strValidFrom.value.length!=0)
		{
			if(isDate(strValidFrom,"Valid From")==false)
				return false;
				strValidFrom.focus();
		}// end of if(strValidFrom.value.length!=0)
		//checks if Valid Date is entered
		if(strValidDate.value.length!=0)
		{
			if(isDate(strValidDate,"Valid To")==false)
				return false;
				strValidDate.focus();
		}// end of if(strValidDate.value.length!=0)
		//checks if both dates entered
		if(strValidFrom.value.length!=0 && strValidDate.value.length!=0)
		{
			if(compareDates("sValidFrom","Valid From","sValidDate","Valid To","greater than")==false)
				return false;
		}// end of if(strValidFrom.value.length!=0 && strValidDate.value.length!=0)
		return true;
}//end of isValidated()