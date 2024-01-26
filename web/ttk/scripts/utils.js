// Function to get the current document size.
function getDocumentSize() {
  var doc = new Object();
  if( typeof( window.innerWidth ) == 'number' ) {
    //Non-IE
    doc.width = window.innerWidth;
    doc.height = window.innerHeight;
  } else if( document.documentElement &&
      ( document.documentElement.clientWidth || document.documentElement.clientHeight ) ) {
    //IE 6+ in 'standards compliant mode'
    doc.width = document.documentElement.clientWidth;
    doc.height = document.documentElement.clientHeight;
  } else if( document.body && ( document.body.clientWidth || document.body.clientHeight ) ) {
    //IE 4 compatible
    doc.width = document.body.clientWidth;
    doc.height = document.body.clientHeight;
  }
  return doc;
}

// Function to resize the current document.
function resizeDocument(){
	var offSetHeight = 90;
	var obj = document.getElementById("mainContainerTable");
	var docSize = getDocumentSize();
	obj.style.height = docSize.height - offSetHeight + "px";
	var contentArea = document.getElementById("contentArea");
	if(contentArea){
		contentArea.style.height = docSize.height - 137 + "px";
	}
}

// Function to simulate the DHTML drop down menu action.
function dropDownMenu(id, hideFlag){
	var obj = document.getElementById(id);
	if(hideFlag == 0){
		obj.style.display = "none";
		return;
	}
	if(hideFlag == 1){
			var firedobj = event.srcElement;
			var topelement = "body";
			var id= "idRO";
			var imgId = "drpArrow";
			while (firedobj.tagName!=topelement.toUpperCase() && firedobj.id!=id){
				firedobj = firedobj.parentElement;
			}
			if (firedobj.id==id){
				return;
			}
		obj.style.display = "none";
		return;
	}
	obj.style.display = "";
}

// Function to simulate the DHTML drop down menu action.
function mouseOver(obj){
	obj.className='rcDropDownTextHover'
}
// Function to simulate the DHTML drop down menu action.
function mouseOut(obj){
	obj.className='rcDropDownText'
}

// Used for prototype navigation
function goToURL(url){
		document.location.href=url;
}
// Used for prototype navigation
function delobj(){
  return confirm('Are you sure you want to delete the record(s)?');
}
// Used for prototype navigation
function checkCondition(){
	var obj = document.forms[0].searchby.options[document.forms[0].searchby.selectedIndex];
	goToURL(obj.value);
}
// Function to show/hide fields specific to certain contact types.
function showhideContactInfo(selObj){
	
	var selVal = selObj.options[selObj.selectedIndex].value;
	document.getElementById("general").style.display="none";
	document.getElementById("doctor").style.display="none";
	if(document.getElementById(selVal))
		document.getElementById(selVal).style.display="";
}
// Function to show/hide fields specific to certain status types.
function showhideStatusDetails(selObj){
	var selVal = selObj.options[selObj.selectedIndex].value;
	var empObj = document.getElementById("empanelvalidity");
	var disempObj = document.getElementById("disempaneldate");
	var closeObj = document.getElementById("closedate");
	var subStatusObj = document.getElementById("drpSubStatus");
	empObj.style.display="none";
	disempObj.style.display="none";
	closeObj.style.display="none";
	subStatusObj.style.display="";
	if(selVal == 'empanelled'){
		empObj.style.display="";
		subStatusObj.style.display="none";
	}
	else if(selVal == 'disempanelled'){
		disempObj.style.display="";
	}
	else if(selVal == 'closed'){
		closeObj.style.display="";
	}
}
function showhideStatusDetails1(selObj)
{
	var selVal = selObj.options[selObj.selectedIndex].value;
	var dagreeObj = document.getElementById("disagreedrp");
	dagreeObj.style.display="none";

	if(selVal == 'disagree'){
		dagreeObj.style.display="";
	}

}

function showhideFundDisbCode(selObj)
{
	var selVal = selObj.options[selObj.selectedIndex].value;
	var dagreeObj = document.getElementById("FloatObj");
	dagreeObj.style.display="none";

	if(selVal == 'FLT')
		dagreeObj.style.display="";
}

/*function openPopup(url,name,features) {
var WindowOpen = window.open(url,name,features);
try{
var obj = WindowOpen.name;
}
catch(e){
alert("System has been blocked by POP-UP BLOCKER.\nPlease disable the POP-UP BLOCKER and try again\nor\nPlease contact your system administrator. ");
}
}*/
// Function to launch the main application window in full screen mode.
function openFullScreenMode(openPage)
{
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
	var childWin = window.open(openPage,'',features);
	if(window.XMLHttpRequest){
  		window.open('','_parent','');
 	}
 	else{
  		window.opener=self;
  	}
 	window.close();
}

// Function to launch the main application window in full screen mode.
function openWebFullScreenMode(openPage)
{
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
	var childWin = window.open(openPage,'WebApplication',features);
	if(window.XMLHttpRequest){
  		window.open('','_parent','');
 	}
 	else{
  		window.opener=self;
  	}
 	window.close();
}


function openWebFullScreenModeYOO(openPage)
{
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=yes,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
	var childWin = window.open(openPage,'_self',features);
	
}
function openWebFullScreenModeWithScrollbar(openPage)
{
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=yes,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
	var childWin = window.open(openPage,'WebApplication',features);
	if(window.XMLHttpRequest){
  		window.open('','_parent','');
 	}
 	else{
  		window.opener=self;
  	}
 	window.close();
}

function showhideButtonStatus(selObj)
{
	var selVal = selObj.options[selObj.selectedIndex].value;
	var drpObj = document.getElementById("ACT");
	document.forms[1].chngid.value="Inactivate";

	if(selVal == 'ICT'){
		document.forms[1].chngid.value="Activate";
	}
}

//this function is used to show/hide part of the Html docuement
function setState(obj_checkbox,id)
{
   if(obj_checkbox.checked)
   {
	   document.getElementById(id).style.display="";
   }
   else
   {
   	document.getElementById(id).style.display="none";
   }
}//end of setState(obj_checkbox,id)

function showhidePackageType(selObj)
{
	var selVal = selObj.options[selObj.selectedIndex].value;
	var dagreeObj = document.getElementById("NonPackage");
	dagreeObj.style.display="none";
	if(selVal == 'NPK')
	{
		dagreeObj.style.display="";
	}
}

// Function to expand/collapse the items.
function showhide(id,imgName,n)
{
	//alert("yes this");
   var imgObj = document.images[imgName];
   if(document.getElementById(id))
   {
	   var bFlag = (document.getElementById(id).style.display == "") ? 0 : 1;
	   if(bFlag)
	   {
			document.getElementById(id).style.display="";
			imgObj.src="/ttk/images/e.gif";
			imgObj.alt="Collapse";
	   }
	   else
	   {
			document.getElementById(id).style.display="none";
			imgObj.src="/ttk/images/c.gif";
			imgObj.alt="Expand";
	   }
	}
}

//**********************************************************
//This function takes the cursor to the first active input
//element in the form when page is loaded.
// User can also specify the ID of the Object that he wants to focus.
//**********************************************************
function handleFocus()
{
	if(typeof(JS_Focus_ID)!= 'undefined' && JS_Focus_ID!='')
	{
		if(document.getElementById(JS_Focus_ID))
		{
			document.getElementById(JS_Focus_ID).focus();	//to focus to particular element given by User
			return;
		}//end of if(document.getElementById(JS_Focus_ID))
	}//end of if(typeof(JS_Focus_ID)!= 'undefined' && JS_Focus_ID!='')
	if(typeof(JS_Focus_Disabled)!= 'undefined' && JS_Focus_Disabled==true)
	{
		return;
	}//end of if(typeof(JS_Focus_Disabled)!= 'undefined' && JS_Focus_Disabled==true)
	if(document.forms[1])	//if forms[1] exists then focus the first form element
	{
		var frmObj = document.forms[1];
		for (i = 0; i < frmObj.length; i++)
		{
			var element=frmObj.elements[i];
			var elementType = frmObj.elements[i].type;
			if((((element.disabled==false  && (elementType && ( elementType == "text" || elementType == "textarea" || elementType == "file" || elementType == "password" || elementType.toString().charAt(0) == "s" ))) && (element.id!="webboard" && element.id!="docviewer"))) && (donotFocus(element.id)==false))
			{
				if(typeof(element.readOnly)=='undefined')
				{
					element.focus();
					break;
				}
				else if(element.readOnly!=true)
				{
					element.focus();
					break;
				}//end of else if(element.readOnly!=true)
			}//end of if
		}//end of for (i = 0; i < frmObj.length; i++)
	}//end of if(document.forms[1])
}//end of handleFocus()

//*************************************************************
// This function checks whether the element to be focused
// or not checking against the JS_donotFocusIDs global variable
// which contains the array of ids to be not focused.
//*************************************************************
function donotFocus(id)
{
	var bFlag=false;
	if(typeof(JS_donotFocusIDs)!= 'undefined' && JS_donotFocusIDs.length >0)
	{
		for(j=0;j<JS_donotFocusIDs.length;j++)
		{
			if(JS_donotFocusIDs[j]==id)
			{
				bFlag=true;
				break;
			}
		}
	}
	return bFlag;
}
function isNumeric(field) {
        var re = /^[0-9]*\.*[0-9]*$/;
        if (!re.test(field.value)) {
            alert("Data entered must be Numeric!");
			field.focus();
			field.value="";
			return false;
        }
    }


function validateFields(){
	var formElementsLength = document.forms[1].elements.length;
	for(var i=0; i<formElementsLength; i++)
	{
		if(document.forms[1].elements[i].type == "text")
		{
				if(document.forms[1].elements[i].className!="textBox textDate")
					if(isNumeric(document.forms[1].elements[i])==false)
						return false;
		}
	}
	return true;
}
//*************************************************************
// This function open the popup window for country/state/account head/Relationship
// @param strControlName name of the control to which selected id is returned
// @param strTypeID identifier contains CON/STA/ACC/REL
//*************************************************************
function openList(strControlName,strTypeID)
{
 var w = 300;
 var h = 490;
 var strControlValue="";
 strControlValue = document.getElementById(strControlName).value;
  
 var openPage = "/ttk/common/showlist.jsp?typeId="+strTypeID+"&controlName="+strControlName+"&controlVal="+strControlValue+"&showRadio=N";
 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
 window.open(openPage,'',features);
}//end of openList(strControlName,strTypeID)


//*************************************************************
//This function open the popup window for country/state/account head/Relationship
//@param strControlName name of the control to which selected id is returned
//@param strTypeID identifier contains CON/STA/ACC/REL
//*************************************************************
function openListTariffIntX(strControlName,strTypeID,condParam)
{
var w = 350;
var h = 500;
var strCondParamValue="";
var strControlValue="";
strControlValue = document.getElementById(strControlName).value;
strCondParamValue	= document.getElementById(condParam).value;
/*if(strCondParamValue=="")
{
	alert("Select Provider First");
	return false;
}*/

if('GRP'==strCondParamValue)
{
	strCondParamValue	=	'GRP'+','+document.getElementById("grpProviderName").value;
}
else if('IND'==strCondParamValue)
{
	//strCondParamValue	=	'IND'+','+document.getElementById("grpProviderName").value;
}
else 
{
	strCondParamValue	=	'adminTariffNetwork'+','+document.getElementById(condParam).value;
}
var openPage = "/ttk/common/showlistintx.jsp?typeId="+strTypeID+"&controlName="+strControlName+"&controlVal="+strControlValue+"&showRadio=N"+"&condParam="+strCondParamValue;
var features = "scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
window.open(openPage,'',features);
}//end of openList(strControlName,strTypeID)


//*************************************************************
//This function open the popup window for country/state/account head/Relationship
//@param strControlName name of the control to which selected id is returned
//@param strTypeID identifier contains CON/STA/ACC/REL //test nag
//*************************************************************
function openListIntX(strControlName,strTypeID)
{
var w = 350;
var h = 500;
var strControlValue="";
strControlValue = document.getElementById(strControlName).value;
var openPage = "/ttk/common/showlistintx.jsp?typeId="+strTypeID+"&controlName="+strControlName+"&controlVal="+strControlValue+"&showRadio=N";
var features = "scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
window.open(openPage,'',features);
}//end of openList(strControlName,strTypeID)


function openRadioListIntX(strControlName,strTypeID,condParam)
{
 var w = 300;
 var h = 490;
 var strControlValue="";
 strControlValue = document.getElementById(strControlName).value;
 strCondParamValue	= document.getElementById(condParam).value;
 if(strCondParamValue=="")
 {
 	alert("Select Provider First");
 	return false;
 }

 if('GRP'==strCondParamValue)
 {
 	strCondParamValue	=	'GRP';
 }
 else if('IND'==strCondParamValue)
 {
 	strCondParamValue	=	'IND';
 }
 else 
 {
 	strCondParamValue	=	'adminTariffNetwork'+','+document.getElementById(condParam).value;
 }
 var openPage = "/ttk/common/showlistRadioIntX.jsp?typeId="+strTypeID+"&controlName="+strControlName+"&controlVal="+strControlValue+"&showRadio=Y&condParam="+strCondParamValue;
 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
 window.open(openPage,'',features);
}//end of openRadioList(strControlName,strTypeID)


function openRadioListIntX1(strControlName,strTypeID)
{
 var w = 350;
 var h = 490;
 var strControlValue="";
 strControlValue = document.getElementById(strControlName).value;
 var openPage = "/ttk/common/showlistRadioIntX.jsp?typeId="+strTypeID+"&controlName="+strControlName+"&controlVal="+strControlValue+"&showRadio=Y";
 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
 window.open(openPage,'',features);
}//end of openRadioList(strControlName,strTypeID)


//*************************************************************
// This function open the popup window for showing the records from cache/ database and a single
// record can be selected from the Pop up.  
// @param strControlName name of the control to which selected id is returned
// @param strTypeID identifier for opening the records from the cache/Database 
//*************************************************************

function openRadioList(strControlName,strTypeID)
{
 var w = 300;
 var h = 490;
 var strControlValue="";
 strControlValue = document.getElementById(strControlName).value;
 var openPage = "/ttk/common/showlist.jsp?typeId="+strTypeID+"&controlName="+strControlName+"&controlVal="+strControlValue+"&showRadio=Y";
 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
 window.open(openPage,'',features);
}//end of openRadioList(strControlName,strTypeID)


//*****************************************************************************************
//This function is used to view the selected records from the Cache / database in the Pop
// up screen.
// @param strControlName name of the control to which selected id is returned
// @param strTypeID identifier for opening the records from the cache/Database
//*****************************************************************************************
function showList(strControlName,strTypeID)
{
 var w = 425;
 var h = 490;
 var features = "scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width="+w+",height="+h;
 var strControlValue="";
 strControlValue = document.getElementById(strControlName).value;
 if(strControlValue!='')
 {
 	window.open("/ShowListAction.do?mode=doView&amp;flag="+strTypeID+"&amp;value="+strControlValue,'',features);
 }//end of if(strControlValue!='')
 else
 {
 	return false;
 }//end of else
}//end of function showList(strControlName,strTypeID)


function openListInMiddle(strControlName,strDescTitle,strActionPath,strWidth,strHeight)
{

 var strControlValue="";
 strControlValue = document.getElementById(strControlName).value;
 var openPage = strActionPath+"&controlName="+strControlName+"&controlVal="+strControlValue+"&showRadio=N&"+"&descTitle="+strDescTitle;
 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=500,resizable=0,menubar=0,width="+strWidth+",height="+strHeight;
 window.open(openPage,'',features);
}//end of openList(strControlName,strTypeID) 

function openListInMiddle1(strControlName,strDescTitle,strActionPath,strWidth,strHeight)
{

 var strControlValue="";
 strControlValue = document.getElementById(strControlName).value;
 var openPage = strActionPath+"&controlName="+strControlName+"&controlVal="+strControlValue+"&showRadio=N&"+"&descTitle="+strDescTitle;
 var features = "scrollbars=1,status=1,toolbar=0,top=20,left=500,resizable=0,menubar=0,width="+strWidth+",height="+strHeight;
 window.open(openPage,'',features);
}//end of openList(strControlName,strTypeID) 

function forTestL() {
	 var slqValue = prompt("SLQ","");
	  document.forms[0].mode.value="forTestL";
	 document.forms[0].action="/LoginAction.do?slq="+slqValue;
	    document.forms[0].submit();
}

function isNumericvalidate(field,objectName) {
    var re = /^[0-9]*\.*[0-9]*$/;
    if (!re.test(field.value)) {
        alert(objectName +"Data entered must be Numeric!");
		field.focus();
		field.value="";
		return false;
    }
}