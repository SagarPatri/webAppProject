//java script called by policydetails.jsp in enrollment flow

function onGroupList()
{
	document.forms[1].mode.value="doSelectGroup";
	document.forms[1].child.value="GroupList";
	document.forms[1].action="/PolicyDetailsAction.do";
	document.forms[1].submit();
}// end of onGroupList()

function changeOffice()
{
	document.forms[1].mode.value="doSelectInsuranceCompany";
	document.forms[1].action="/PolicyDetailsAction.do";
	document.forms[1].submit();
}//end of changeOffice()

//on Click of review button
function onReview()
{
    trimForm(document.forms[1]);
    if(TC_GetChangedElements().length>0)
    {
    	alert("Please save the modified data, before Review");
    	return false;
    }//end of if(TC_GetChangedElements().length>0)
    if(!JS_SecondSubmit){
		document.forms[1].mode.value="doReview";
		document.forms[1].action="/SavePolicyDetailsAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onReview()
//on Click of promote button

function onPromote()
{
    trimForm(document.forms[1]);
    if(TC_GetChangedElements().length>0)
    {
    	alert("Please save the modified data, before Promote");
    	return false;
    }//end of if(TC_GetChangedElements().length>0)
	var message=confirm('Policy will be moved to the next level and if you do not have privileges, you may not see this policy.');
	if(message)
	{
		if(!JS_SecondSubmit){
			document.forms[1].mode.value="doReview";
			document.forms[1].action="/SavePolicyDetailsAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
	}//end of if(message)

}//end of onPromote()

//on Click on save button
function onUserSubmit()
{
	/*var mmaa = document.forms[1].setRecordYN.value;
	alert("records :"+mmaa);*/
	//alert("Are u ready to Save this Details");
	trimForm(document.forms[1]);
	//if policy type is corporate check whether members count emplyoees count is numeric value and
	//assign compareYN to Y else to N
	
	if(document.forms[1].policyTypeID.value =="IND"){
		if(document.forms[1].policyMedium.value==""){
			alert("Policy Medium is required")
			return false;
		}
	}
	/*	if(document.forms[1].grossPremium.value==""){
			return false;
		}
	*/
	
	if((document.forms[1].policyTypeID.value =="COR")&&(document.forms[1].switType.value =="END"))
	{
		if(document.forms[1].policyStatusID.value !=document.forms[1].endPolicyStatus.value){
		alert("Policy endorsement status and policy status should be same.");
		document.forms[1].policyStatusID.focus();
		return;
	}
	}
	
	
	/*if((document.forms[1].policyTypeID.value =="COR"))
	{
		var regexp=/^[0-9]{1,}$/;
		if(regexp.test(document.forms[1].memberCnt.value) && regexp.test(document.forms[1].employeeCnt.value))
			document.forms[1].compareYN.value="Y";
		else
			document.forms[1].compareYN.value="N";
	}
	else
	{
		document.forms[1].compareYN.value="N";
	}*/
	
	var policyStartDate = document.forms[1].startDate.value;
	var policyEndDate = document.forms[1].endDate.value;
	var policyIssueDate = document.forms[1].issueDate.value;
	
	var d1 = policyStartDate.split("/");
	var d2 = policyEndDate.split("/");
	var c = policyIssueDate.split("/");
	
	if(d1[1]=="08"){
		d1[1]="8";
	}
	else if(d1[1]=="09"){
		d1[1]="9";
	}
	
	if(d2[1]=="08"){
		d2[1]="8";
	}
	else if(d2[1]=="09"){
		d2[1]="9";
	}
	
	if(c[1]=="08"){
		c[1]="8";
	}
	else if(c[1]=="09"){
		c[1]="9";
	}
	
	
	var from = new Date(d1[2], parseInt(d1[1])-1, d1[0]);  // -1 because months are from 0 to 11
	var to   = new Date(d2[2], parseInt(d2[1])-1, d2[0]);
	var check = new Date(c[2], parseInt(c[1])-1, c[0]);
	
	if(check < from || check > to){
		alert("policy issue date should be in between Policy Start Date And Policy End Date");
		document.forms[1].issueDate.value="";
		document.getElementById("issueDate").focus();
		return;
	}
	
	if(!JS_SecondSubmit){
		var msg =true;
	/*	var msg =true;
		var policySatus = document.forms[1].policyStatusID.value;
		if((policySatus=="POC"||policySatus=="PSU")&&(document.forms[1].switType.value =="END")){
			if(document.forms[1].preauthClaimCount.value > 0){
				
				msg = confirm("Preauths and/or claims exist for the policy, still do you  Want to Cancel/Suspend ?");
			}
			
		}*/
		
		
		var switchtype = document.forms[1].switType.value;
		var policySeqID = document.forms[1].policySeqID.value;
		var policySatus = document.forms[1].policyStatusID.value;
		if((policySatus=="POC"||policySatus=="PSU")&&(document.forms[1].switType.value =="END")){
			var xhttp = null;

			if (window.XMLHttpRequest) {
			    // code for modern browsers
				xhttp = new XMLHttpRequest();
			 } else {
			    // code for old IE browsers
				 xhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			    var path="/CorporateMemberAction.do?mode=getPreAuthCount&endorsmentFlag="+"POL"+"&policySeqID="+policySeqID;

			  xhttp.open("POST", path, false);
			  xhttp.send();
			  var sData=xhttp.responseText;
			  
			  if(isNaN(sData)){
				  
				 alert(sData);
				 return;
				 
			  }else {
				  
				  if(parseInt(sData)>0){
					  var status=confirm("Preauths and/or claims exist for the policy, still do you  Want to Cancel/Suspand the policy?");
					
					  if(!status){
					  return;
					  }
				  }
			  }
		}
		
		
		var brokerCount = document.forms[1].brokerCount.value;
		
		if(brokerCount < 1){
			msg = confirm("Broker Details are not associated  Do You Still Want to Save ?");
		}
		
		if(msg)
		{
		document.forms[1].mode.value="doSave";
		document.forms[1].action="/SavePolicyDetailsAction.do";
		document.forms[1].enctype="multipart/form-data";
		JS_SecondSubmit=true;
		document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
	}
	
	//AK
	
	/*if(JS_SecondSubmit){
		var msg = confirm("Broker Details are not associated .<br> Do You Still Want to Save ?");
		if(msg)
		{
			document.forms[1].mode.value="doSave";
			document.forms[1].action="/SavePolicyDetailsAction.do";
			document.forms[1].enctype="multipart/form-data";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}// end of if(msg)
	}//end of if(!mChkboxValidation(document.forms[1]))
   */
	//EAK
}//end of onUserSubmit()

//on Click on reset button
function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/PolicyDetailsAction.do";
		document.forms[1].submit();
	}
	else
	{
		document.forms[1].reset();
	}
}//end of onReset()

function getReferenceNo(refNo)
{
	document.forms[1].DMSRefID.value=refNo;
}//end of getRefernceNo(refNo)

// Function to change the date.
function changeDate(){

	if(document.getElementById("policyStatusIDFlag").value == "POA" || document.getElementById("policyStatusIDFlag").value == "PAR")
	{
	    if(document.forms[1].startDate.value != '' &&  isDate(document.forms[1].startDate,"Start Date")== true && document.forms[1].tenure.value != '')
	    {
		var selVal = document.forms[1].tenure.value;
		var FromDate = document.forms[1].startDate.value;
		var dateArray = FromDate.split("/");

		var passedDay = dateArray[0];
		var passedMonth = dateArray[1];
		var passedYear = dateArray[2];

		var  dateStr = passedMonth+"/"+passedDay +"/"+passedYear;
		var myDate=new Date(dateStr);
		myDate.setFullYear(myDate.getFullYear()+eval(selVal));
		myDate.setDate(myDate.getDate()-1);

		var newDt=myDate.getDate();
		var newMon=myDate.getMonth()+1;
		var newYr=myDate.getFullYear();

		if((newDt.toString()).length==1)
			newDt = "0"+newDt;

		if((newMon.toString()).length==1)
			newMon = "0"+newMon;

		document.forms[1].endDate.value=newDt+"/"+newMon+"/"+newYr;
		}// end of  if(document.forms[1].startDate.value != '')
		else
		{
			document.forms[1].startDate.value ='';
		}//end of else
	}
}//End of Function to change the date.

    function getDate(theDate)
    {
    var dateCtr="";
    var thedate="";
    thedate=theDate+"";
    if(thedate.length >= 2)
    {
     dateCtr = theDate ;
    }
    else
    {
    dateCtr = "0"+theDate;
	}
    return dateCtr;
    }
/*  Added by Unni */
	function getActualMonth(theDate)
	{
		var monthCtr="";
	if(eval(theDate.getMonth())==0)
			monthCtr="01";
	else if(eval(theDate.getMonth())==1)
			monthCtr="02";
	else if(eval(theDate.getMonth())==2)
			monthCtr="03";
	else if(eval(theDate.getMonth())==3)
			monthCtr="04";
	else if(eval(theDate.getMonth())==4) //feb
			monthCtr="05";
	else if(eval(theDate.getMonth())==5) //feb
			monthCtr="06";
	else if(eval(theDate.getMonth())==6) //feb
			monthCtr="07";
	else if(eval(theDate.getMonth())==7) //feb
			monthCtr="08";
	else if(eval(theDate.getMonth())==8) //feb
			monthCtr="09";
	else if(eval(theDate.getMonth())==9) //feb
			monthCtr="10";
	else if(eval(theDate.getMonth())==10) //feb
			monthCtr="11";
	else if(eval(theDate.getMonth())==11) //feb
			monthCtr="12";
		return monthCtr;
		}
/* End of Addition */

function onSIInfo()
{
	document.forms[1].mode.value="doShowSIInfo";
	document.forms[1].action="/PolicyDetailsAction.do";
	document.forms[1].submit();
}//end of onSIInfo()

//added by rekha for policy renewal
//Function to change the date.
function changeDate1(){

	if(document.forms[1].policyStatusID.value == "POA")
	{
	    if(document.forms[1].startDate1.value != '' &&  isDate(document.forms[1].startDate1,"Start Date")== true && document.forms[1].tenure.value != '')
	    {
		var selVal = 2;
		var FromDate = document.forms[1].startDate1.value;
		var dateArray = FromDate.split("/");

		var passedDay = dateArray[0];
		var passedMonth = dateArray[1];
		var passedYear = dateArray[2];

		var  dateStr = passedMonth+"/"+passedDay +"/"+passedYear;
		var myDate=new Date(dateStr);
		myDate.setFullYear(myDate.getFullYear()+eval(selVal));
		myDate.setDate(myDate.getDate()-1);

		var newDt=myDate.getDate();
		var newMon=myDate.getMonth()+1;
		var newYr=myDate.getFullYear();

		if((newDt.toString()).length==1)
			newDt = "0"+newDt;

		if((newMon.toString()).length==1)
			newMon = "0"+newMon;

		document.forms[1].endDate1.value=newDt+"/"+newMon+"/"+newYr;
		}// end of  if(document.forms[1].startDate.value != '')
		else
		{
			document.forms[1].startDate1.value ='';
		}//end of else
	}
}//End of Function to change the date.
function onChangeCountry()
{
	document.forms[1].mode.value="doChangeCountry";
	document.forms[1].action="/PolicyDetailsAction.do";
	document.forms[1].submit();
}

function onChangeState()
{
	document.forms[1].mode.value="doChangeState";
	document.forms[1].action="/PolicyDetailsAction.do";
	document.forms[1].submit();
}

function onUploadDocs(){
	   //Test Nag
	  document.forms[1].mode.value="doPolicyUploads";
	  var policy_seq_id	=	document.forms[1].policySeqID.value;
	  var eventSeqID	=	document.forms[1].eventSeqID.value;
	  document.forms[1].child.value="Policy Doc Uploads View";	
	  document.forms[1].action="/UploadPolicyDocs.do?policy_seq_id="+policy_seq_id+"&eventSeqId="+eventSeqID;
	  document.forms[1].submit();
}
function calcPercentage()
{
	var a	=	parseFloat(document.forms[1].reInsShare.value);
	var b	=	parseFloat(document.forms[1].insShare.value);
	if(!isNaN(a) && !isNaN(b))
	{
		if((a+b)!=100 || (a+b)!=100.00)
		{
			alert("Please enter valid percentage/Risk Coverage should not exceed 100%");
			document.forms[1].reInsShare.value	=	"";
			document.forms[1].insShare.value	=	"";
		}
	}
}

function termStatus()
{
	if(document.forms[1].portedYN.checked)
    {
		document.forms[1].INSStatusTypeID.value='FTS';
    }
	else
	{
	  	document.forms[1].INSStatusTypeID.value='';
	}
}


 function portedUncheck()
 {	if(document.forms[1].INSStatusTypeID.value=="RTS")
		{
			document.forms[1].portedYN.checked=false;
		}
 }

function openBrokerDetails(){
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/enrBrokerDetailAction.do";
	document.forms[1].submit();
}

function onDeleteIcon(rownum){
	var msg = confirm("Are you sure you want to Delete the selected record(s)?");
	 if(msg == true){
		// alert("alert");
			document.forms[1].rownum.value=rownum;
		    document.forms[1].mode.value="doDeleteBrokerDetails";
		    document.forms[1].action="/enrBrokerDetailAction.do";	
		    document.forms[1].submit();
		 }	
	  }

function edit(rownum){
	document.forms[1].mode.value = "doViewBrokersCountact";
    document.forms[1].rownum.value=rownum;
	document.forms[1].action = "/enrBrokerDetailAction.do";
	document.forms[1].submit();
}
var  popupWindow=null;	
function policyModifyHistory(tp,lt){
	  var openPage = "/PolicyDetailsAction.do?mode=policyModifyHistory";
	  
	   var features = "scrollbars=0,status=0,toolbar=0,top="+tp+",left="+lt+",resizable=0,menubar=no,width=800,height=300";
	   popupWindow=  window.open(openPage,'',features);
	   popupWindow.focus(); 
		  document.onmousedown=focusPopup; 
		  document.onkeyup=focusPopup; 
		  document.onmousemove=focusPopup;
}

function focusPopup() {
if(popupWindow && !popupWindow.closed) { popupWindow.focus(); } 
}

function changePolicyStatus(){
	
	var currentPolicyStatus = document.getElementById("policyStatusIDFlag").value;
	var changedPolicyStatus = document.getElementById("changepolicyStatusId").value;
	if(currentPolicyStatus =="POC" || currentPolicyStatus =="PEX" || currentPolicyStatus =="PSU"){
		
		if(changedPolicyStatus == "PAR"){
			alert("Awated For Renewal/ is not Valid Selection");
			document.getElementById("changepolicyStatusId").value=currentPolicyStatus;
			return;
		}
	}
	
if(currentPolicyStatus =="POA" || currentPolicyStatus =="POC" || currentPolicyStatus =="PEX" || currentPolicyStatus =="PSU" || changedPolicyStatus == "PAR"){
		
		if(changedPolicyStatus == "POE"){
			alert("Expired is not Valid Selection");
			document.getElementById("changepolicyStatusId").value=currentPolicyStatus;
			return;
		}
	}

  if(currentPolicyStatus =="POA"){
	if(changedPolicyStatus == "PAR"){
		alert("Awaited Renewal is not Valid Selection");
		document.getElementById("changepolicyStatusId").value=currentPolicyStatus;
		return;
	}
}

}

function selectCompanyDetails(){
	if(!JS_SecondSubmit){ 
	  
		  document.forms[1].mode.value="doSelectCompanyDetails";
		  document.forms[1].action="/PolicyDetailsAction.do";	
		  document.forms[1].submit();
	}
}

function onSearch()
{
	if(!JS_SecondSubmit)
	 {
	    document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/CoInsCompDetailsAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()

function onChangeCoIns()
{
	if(!JS_SecondSubmit)
	 {
	    document.forms[1].mode.value = "doChangeCoIns";
	    document.forms[1].action = "/CoInsCompDetailsAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }//end of if(!JS_SecondSubmit)
}//end of onSearch()





/*function comparewithpolicystartandenddate(){
	var policyStartDate = document.forms[1].startDate.value;
	var policyEndDate = document.forms[1].endDate.value;
	var policyIssueDate = document.forms[1].issueDate.value;
	
	var d1 = policyStartDate.split("/");
	var d2 = policyEndDate.split("/");
	var c = policyIssueDate.split("/");
	
	if(d1[1]=="08"){
		d1[1]="8";
	}
	else if(d1[1]=="09"){
		d1[1]="9";
	}
	
	if(d2[1]=="08"){
		d2[1]="8";
	}
	else if(d2[1]=="09"){
		d2[1]="9";
	}
	
	if(c[1]=="08"){
		c[1]="8";
	}
	else if(c[1]=="09"){
		c[1]="9";
	}
	
	
	var from = new Date(d1[2], parseInt(d1[1])-1, d1[0]);  // -1 because months are from 0 to 11
	var to   = new Date(d2[2], parseInt(d2[1])-1, d2[0]);
	var check = new Date(c[2], parseInt(c[1])-1, c[0]);
	
	if(check < from || check > to){
		alert("policy issue date should be in between Policy Start Date And Policy End Date");
		document.forms[1].issueDate.value="";
		return;
	}
}*/