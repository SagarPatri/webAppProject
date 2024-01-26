//java script for the contacts screen in the empanelment of hospital flow.
function Add()
{
	document.forms[1].rownum.value="";
	document.forms[1].child.value="ContactDetails";
	document.forms[1].mode.value="doAdd";
    document.forms[1].action = "/EditUserContact.do";
    document.forms[1].submit();
}//end of add block

function edit(rownum)
{
    document.forms[1].mode.value="doViewContacts";
    document.forms[1].child.value="ContactDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EditUserContact.do";
    document.forms[1].submit();
}//end of edit(rownum)


function onContactTypeChange()
{
	document.forms[1].mode.value="doContactTypeChange";
	document.forms[1].action="/HospitalContactAction.do";
	document.forms[1].submit();
}//end of onContactChanged()

function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/HospitalContactAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/HospitalContactAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/HospitalContactAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
//function to display next set of pages
function PressForward()
{
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/HospitalContactAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onSearch()
{
	if(!JS_SecondSubmit)
	 {
	    if(typeof(document.forms[1].applUser) != 'undefined' && document.forms[1].applUser.value !=null)
	    {
		    if(document.forms[1].applUser.checked){
		    	document.forms[1].applUser1.value='Y';
		    }//end of if(document.forms[1].applUser.checked)
		    else{
		    	document.forms[1].applUser1.value='N';
		    }//end of else	
	    }//end of if(typeof(document.forms[1].applUser) != 'undefined' && document.forms[1].applUser.value !=null)
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/HospitalContactAction.do";
	    JS_SecondSubmit=true;
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onActivateInactivate(status)
{
    if(!mChkboxValidation(document.forms[1]))
    {
		var msg = confirm("Are you sure you want to "+status+" the selected contact(s)?");
		if(msg)
		{
			if(status=="Inactivate")
				document.forms[1].activeInactive.value="N";
			else
				document.forms[1].activeInactive.value="Y";
			document.forms[1].mode.value = "doActivateInactivate";
			document.forms[1].action = "/HospitalContactAction.do";
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
}//end of onActivateInactivate()

function onClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value = "doCompanySummary";
	document.forms[1].child.value="";
	document.forms[1].action = "/HospitalContactAction.do";
    document.forms[1].submit();
}//end of onClose

//kocb
function onBrokerClose()
{
	if(!TrackChanges()) return false;

	document.forms[1].mode.value = "doBrokerSummary";
	document.forms[1].child.value="";
	document.forms[1].action = "/HospitalContactAction.do";
    document.forms[1].submit();
}//end of onClose

function onContactClose()
{
	document.forms[1].mode.value = "doClose";
	document.forms[1].child.value="";
	document.forms[1].action = "/GroupListAction.do";
    document.forms[1].submit();
}//end of onContactClose()

function onBankContactClose()
{
	document.forms[1].tab.value="Search";
	document.forms[1].mode.value = "doClose";
	document.forms[1].action = "/BankTreeListAction.do";
    document.forms[1].submit();
}//end of onBankContactClose()

//int x
function AddLicense()
{
	document.forms[1].rownum.value="";
	document.forms[1].child.value="ContactDetails";
	document.forms[1].mode.value="doAddLicense";
    document.forms[1].action = "/EditProfessionContact.do?flag=License";
    document.forms[1].submit();
}


function edit2(rownum)
{
    document.forms[1].mode.value="doViewProfessionals";
    document.forms[1].child.value="ContactDetails";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/EditProfessionContact.do?rownum="+rownum;
    document.forms[1].submit();
}//end of edit2(rownum)

function onSwitch()
{
	document.forms[1].mode.value="doSwitchTo";
    document.forms[1].child.value="ContactDetails";
	document.forms[1].action="/HospitalContactSearchAction.do";
	document.forms[1].submit();
}

function onUploadProfessionals()
{
	var hospEmpnlNo	=	document.forms[1].hospEmpnlNo.value;
	var hospSeqId	=	document.forms[1].hospSeqId.value;
	var userSeqId	=	document.forms[1].userSeqId.value;
	var userName	=	document.forms[1].userName.value;
	var ProfessionalSoftCopyURL	=	document.forms[1].ProfessionalSoftCopyURL.value;
	window.open(ProfessionalSoftCopyURL+"?hospSid="+hospSeqId+"&uploadedBy="+userSeqId+"&empanelmentId="+hospEmpnlNo+"&userName="+userName,'',"scrollbars=1,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=0,width=800,height=350");
}



function doDownloadclinicianContact(downloadtype)									
{
	var partmeter = "?mode=doDownloadProviderContact"+"&downloadtype="+downloadtype;

	var openPage = "/HospitalContactAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 99;
	var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	window.open(openPage,'',features);
}
