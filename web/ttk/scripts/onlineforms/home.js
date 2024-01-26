/**
 * @ (#) home.js Dec 26, 2007
 * Project      : TTK HealthCare Services
 * File         : home.js
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : Dec 26, 2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */

//On select IDs kocb

function edit(rownum)
{
	 
	document.forms[1].rownum.value=rownum;
	//var partmeter = "?mode=doGenerateUrl&rownum="+document.forms[1].rownum.value;
	var openPage = "/OnlineHomeAction.do?mode=doGenerateUrl&rownum="+document.forms[1].rownum.value;
	//var openPage = "/OnlineHomeAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=no,width="+w+",height="+h;
	window.open(openPage,'',features);
	
	
	
    /*document.forms[1].mode.value="doGenerateUrl";
    document.forms[1].rownum.value=rownum;
    document.forms[1].tab.value="Home";
    document.forms[1].action = "/OnlineHomeAction.do";
    document.forms[1].submit();*/
}//end of edit(rownum)


// Function for gives the selected policy information in the page
function onpolicychange()
{
	
		document.forms[1].seqID.value=document.forms[1].policySelect.value;
		document.forms[1].mode.value="doSelectPolicy";
		document.forms[1].action="/OnlineHomeAction.do";
		document.forms[1].submit();
	
}//end of function onpolicychange()

function onECards()
{
var openPage = "/OnlineMemberAction.do?mode=doECards";
   var w = screen.availWidth - 10;
   var h = screen.availHeight - 49;
   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
   window.open(openPage,'',features);	
}//end of function onECards()

// Function for opening of documents which are stored in a proxy server
function openDoc(strFileName)
{
	document.forms[1].fileName.value = strFileName;
	var openPage = "/ReportsAction.do?mode=doWebdoc&fileName="+document.forms[1].fileName.value+"&module=online";
   var w = screen.availWidth - 10;
   var h = screen.availHeight - 49;
   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
   window.open(openPage,'',features);
}//end of function openDoc(strFileName)

// Function for generating jasper report which contains the hospital information
function openHospDoc(strConCat)
{
	var features = "scrollbars=0,status=0,toolbar=0,top=0,left=0,resizable=1,menubar=0,width=950,height=700";
	window.open("/ReportsAction.do?mode=doGenerateWebHospXL&reportID=GenWebHospXLs&parameter="+strConCat+"&reportType=pdf&fileName="+document.forms[1].fileName.value,'', features);  
}//end of function openHospDoc(strConCat)

//Functions added for CR KOC1168 Feedback Form 
function onCashless()
{
	document.forms[1].mode.value = 'doDefault';
	document.forms[1].action="/ChangeCashlessFBAction.do?claimType=CNH";
	document.forms[1].submit();
}//end of OnCashless()
//Functions added for CR KOC1168 Feedback Form 
function OnMReimbursement()
{	
	document.forms[1].mode.value = 'doDefault';
	document.forms[1].action="/ChangeMReimbursementFBAction.do?claimType=CTM";
	document.forms[1].submit();
}//end of OnMReimburesement()

//sPatStartDate,sPatEndDate,sClmStartDate,sClmEndDate
function onDashBoardPreAuths(obj){
	var startDate=document.forms[1].sPatStartDate.value;
	var endDate=document.forms[1].sPatEndDate.value;
	document.forms[1].tab.value="Search";	
	document.forms[1].sublink.value="Cashless Status";
	document.forms[1].leftlink.value="Hospital Information";
	document.forms[1].mode.value='doDashBoardSearch';
	document.forms[1].action="/OnlinePatSearchHospAction.do?sStatus="+obj;//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();
}//end of onDashBoardPreAuth()

function onDashBoardClaims(obj)
{
	
	var startDate=document.forms[1].sClmStartDate.value;
	var endDate=document.forms[1].sClmEndDate.value;
	document.forms[1].tab.value="Search";
	document.forms[1].sublink.value="Claims Status";
	document.forms[1].leftlink.value="Hospital Information";
	document.forms[1].mode.value='doDashBoardSearch';
	document.forms[1].action="/OnlineClmSearchHospAction.do?sStatus="+obj;//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();
}//end of onDashBoardPreAuth()



function onDashBoardSearch()
{	
	//document.forms[1].tab.value="Claims";	
	document.forms[1].mode.value='doDashBoardData';
	document.forms[1].action="/OnlineClmSearchHospAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();
}//end of onDashBoardPreAuth()

function onDashBoardClaimRefresh()
{	
	//kocnewhosp1
	/*var objFromDate = document.getElementById("sFromDate").value;
	var objToDate = document.getElementById("sToDate").value;*/
	
	var objFromDate = "";
	var objToDate 	= "";
	
	document.forms[1].tab.value="Claims DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";
	document.forms[1].mode.value='doDashBoardData';
	document.forms[1].action="/OnlineClmSearchHospAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();
}//end of onDashBoardClaimRefresh()


//Added For Partner Login

function onPartnerDashBoardClaimRefresh()
{	
	//kocnewhosp1
	/*var objFromDate = document.getElementById("sFromDate").value;
	var objToDate = document.getElementById("sToDate").value;*/
	
	var objFromDate = "";
	var objToDate 	= "";
	
	document.forms[1].tab.value="Claims DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Partner Information";
	document.forms[1].mode.value='doDashBoardData';
	document.forms[1].action="/OnlineClmSearchPtnrAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();
}//end of onDashBoardClaimRefresh()


function onDashBoardClaimSearch()
{	
	//kocnewhosp1
	var objFromDate = document.forms[1].sFromDate.value;
	var objToDate = document.forms[1].value;
	if(isValidated())
	{
	if(objFromDate=="")
	{
		alert("Please Select From Date");
		return false;
	}
	if(objToDate=="")
	{
		alert("Please Select To Date");
		return false;
	}
	
	if(objFromDate!="" || objToDate!=""){
		if(compareDates("sFromDate","From Date","sToDate","To Date","greater than")==false)
			return false;
		}
	
	/*var date1 = new Date(objFromDate);
	var date2 = new Date(objToDate);
	  
	var date3 = new Date();
	var date4 = date3.getMonth() + "/" + date3.getDay() + "/" + date3.getYear();
	var currentDate = new Date(date4);
	//alert("date1>>>"+date1+"date2>>>"+date2+"--date3>>>>"+date3+"---date4>>>"+date4+"---currentDate>>"+currentDate);
	 
	    if(date1 > date2)
	    {
	        alert("From date should be Less than or Equal to To date");
	        return false; 
	    }*/
	    
/*	document.forms[1].tab.value="Claims DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value='doDashBoardData';
	document.forms[1].action="/OnlineClmSearchHospAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();
	}
}//end of onDashBoardClaimSearch()


//Added for Partner Login

function onPartnerDashBoardClaimSearch()
{	
	//kocnewhosp1
	var objFromDate = document.forms[1].sFromDate.value;
	var objToDate = document.forms[1].value;
	if(isValidated())
	{
	if(objFromDate=="")
	{
		alert("Please Select From Date");
		return false;
	}
	if(objToDate=="")
	{
		alert("Please Select To Date");
		return false;
	}
	
	if(objFromDate!="" || objToDate!=""){
		if(compareDates("sFromDate","From Date","sToDate","To Date","greater than")==false)
			return false;
		}
	
	/*var date1 = new Date(objFromDate);
	var date2 = new Date(objToDate);
	  
	var date3 = new Date();
	var date4 = date3.getMonth() + "/" + date3.getDay() + "/" + date3.getYear();
	var currentDate = new Date(date4);
	//alert("date1>>>"+date1+"date2>>>"+date2+"--date3>>>>"+date3+"---date4>>>"+date4+"---currentDate>>"+currentDate);
	 
	    if(date1 > date2)
	    {
	        alert("From date should be Less than or Equal to To date");
	        return false; 
	    }*/
	    
/*	document.forms[1].tab.value="Claims DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value='doDashBoardData';
	document.forms[1].action="/OnlineClmSearchPtnrAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();
	}
}//end of onPartnerDashBoardClaimSearch()


function GetDateDiff(startDate, endDate)
{
    return endDate.getDate() - startDate.getDate();
}

function isValidated()
{
	if(trim(document.forms[1].sFromDate.value).length>0)
	{
		if(isDate(document.forms[1].sFromDate,"From Date")==false)
		{
			document.forms[1].sFromDate.focus();
			return false;
		}//end of if
	}//From Date
	
	if(trim(document.forms[1].sToDate.value).length>0)
	{
		if(isDate(document.forms[1].sToDate,"To Date")==false)
		{
			document.forms[1].sToDate.focus();
			return false;
		}//end of if
	}//From Date
	
	return true;
}//end of isValidated()

function onDashBoardPreauthSearch()
{	
	//kocnewhosp1
	var objFromDate = document.forms[1].sFromDate.value;
	var objToDate = document.forms[1].sToDate.value;
	if(isValidated())
	{
	if(objFromDate=="")
	{
		alert("Please Select From Date");
		return false;
	}
	if(objToDate=="")
	{
		alert("Please Select To Date");
		return false;
	}

	// alert("objFromDate>>"+objFromDate+"---objToDate>>"+objToDate);
	if(objFromDate!="" || objToDate!=""){
	if(compareDates("sFromDate","From Date","sToDate","To Date","greater than")==false)
		return false;
	}
	
	
	/*var date1 = new Date(objFromDate);
	var date2 = new Date(objToDate);
	  
	var date3 = new Date();
	var date4 = date3.getMonth() + "/" + date3.getDay() + "/" + date3.getYear();
	var currentDate = new Date(date4);
	alert("date1>>>"+date1+"---date2>>>"+date2+"--date3>>>>"+date3+"---date4>>>"+date4+"---currentDate>>"+currentDate);
	alert(date1.getTime());
	 
	    if(date1.getTime() > date2.getTime())
	    {
	        alert("From date should be Less than or Equal to To date");
	        return false; 
	    }
	        else if(date1 > currentDate)
	    {
	        alert("From Date should be less than current date");
	        return false; 
	    }
	               else if(date2 > currentDate) 
	               {
	                   alert("To Date should be less than current date");
	        return false; 
	                }*/
	    
	    

	/*document.forms[1].tab.value="Cashless DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value='doDashBoardData';
	document.forms[1].action="/OnlineClmSearchHospAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();
	}
}//end of onDashBoardPreAuth()



//Added For Partner Login
function onPartnerDashBoardPreauthSearch()
{	
	//kocnewhosp1
	var objFromDate = document.forms[1].sFromDate.value;
	var objToDate = document.forms[1].sToDate.value;
	if(isValidated())
	{
	if(objFromDate=="")
	{
		alert("Please Select From Date");
		return false;
	}
	if(objToDate=="")
	{
		alert("Please Select To Date");
		return false;
	}

	// alert("objFromDate>>"+objFromDate+"---objToDate>>"+objToDate);
	if(objFromDate!="" || objToDate!=""){
	if(compareDates("sFromDate","From Date","sToDate","To Date","greater than")==false)
		return false;
	}
	
	
	/*var date1 = new Date(objFromDate);
	var date2 = new Date(objToDate);
	  
	var date3 = new Date();
	var date4 = date3.getMonth() + "/" + date3.getDay() + "/" + date3.getYear();
	var currentDate = new Date(date4);
	alert("date1>>>"+date1+"---date2>>>"+date2+"--date3>>>>"+date3+"---date4>>>"+date4+"---currentDate>>"+currentDate);
	alert(date1.getTime());
	 
	    if(date1.getTime() > date2.getTime())
	    {
	        alert("From date should be Less than or Equal to To date");
	        return false; 
	    }
	        else if(date1 > currentDate)
	    {
	        alert("From Date should be less than current date");
	        return false; 
	    }
	               else if(date2 > currentDate) 
	               {
	                   alert("To Date should be less than current date");
	        return false; 
	                }*/
	    
	    

	/*document.forms[1].tab.value="Cashless DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value='doDashBoardData';
	document.forms[1].action="/OnlineClmSearchPtnrAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();
	}
}//end of onPartnerDashBoardPreauthSearch()


function onDashBoardPreauthRefresh()
{	
	//kocnewhosp1
	/*var objFromDate = document.getElementById("sFromDate").value;
	var objToDate = document.getElementById("sToDate").value;*/
	
	var objFromDate = "";
	var objToDate 	= "";
	
	/*document.forms[1].tab.value="Cashless DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value="doDashBoardData";
	document.forms[1].action="/OnlineClmSearchHospAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();
}//end of onDashBoardPreauthRefresh()



//Added for Partner Login

function onPartnerDashBoardPreauthRefresh()
{	
	//kocnewhosp1
	/*var objFromDate = document.getElementById("sFromDate").value;
	var objToDate = document.getElementById("sToDate").value;*/
	
	var objFromDate = "";
	var objToDate 	= "";
	
	/*document.forms[1].tab.value="Cashless DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value="doDashBoardData";
	document.forms[1].action="/OnlineClmSearchPtnrAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();
}//end of onDashBoardPreauthRefresh()



//kocnewhosp1
function onDashBoardPreauthSearchPrevious(){
	
	/*document.forms[1].tab.value="Cashless DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value='doDashBoardDataPrev';
	document.forms[1].action="/OnlineClmSearchHospAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();

}


//Added for Partner Login
function onPartnerDashBoardPreauthSearchPrevious(){
	//alert(JS_SecondSubmit);
	 
	/*document.forms[1].tab.value="Cashless DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value='doDashBoardDataPrev';
	document.forms[1].action="/OnlineClmSearchPtnrAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();

}


function onDashBoardClaimSearchPrevious(){
	
	/*document.forms[1].tab.value="Claims DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value='doDashBoardDataPrev';
	document.forms[1].action="/OnlineClmSearchHospAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();

}


//Added for Partner Login
function onPartnerDashBoardClaimSearchPrevious(){
	
	/*document.forms[1].tab.value="Claims DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value='doDashBoardDataPrev';
	document.forms[1].action="/OnlineClmSearchPtnrAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();

}



function onDashBoardPreauthSearchNext(){
	
	/*document.forms[1].tab.value="Cashless DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value='doDashBoardDataNext';
	document.forms[1].action="/OnlineClmSearchHospAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();

}


//Added for Partner Login
function onPartnerDashBoardPreauthSearchNext(){
	
	/*document.forms[1].tab.value="Cashless DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value='doDashBoardDataNext';
	document.forms[1].action="/OnlineClmSearchPtnrAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();

}



function onDashBoardClaimSearchNext(){
	
	/*document.forms[1].tab.value="Claims DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value='doDashBoardDataNext';
	document.forms[1].action="/OnlineClmSearchHospAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();

}


//Added for Partner Login
function onPartnerDashBoardClaimSearchNext(){
	
	/*document.forms[1].tab.value="Claims DashBoard";	
	document.forms[1].sublink.value="Home";
	document.forms[1].leftlink.value="Hospital Information";*/
	document.forms[1].mode.value='doDashBoardDataNext';
	document.forms[1].action="/OnlineClmSearchPtnrAction.do";//+"&sStartDate="+startDate+"&sEndDate"+endDate;
	document.forms[1].submit();

}

//kocb
function onSearch()
{
	if(!JS_SecondSubmit)
	 {
		trimForm(document.forms[1]);
		document.forms[1].mode.value = "doSearch";
		document.forms[1].action = "/OnlineHomeAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }
}

function toggle(sortid)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/OnlineHomeAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
	document.forms[1].reset();
    document.forms[1].mode.value="doDefault";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/OnlineHomeAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages

//function to display next set of pages
function PressForward()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/OnlineHomeAction.do";
    document.forms[1].submit();
}//end of PressForward()

//function to display previous set of pages
function PressBackWard()
{
	document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/OnlineHomeAction.do";
    document.forms[1].submit();
}//end of PressBackWard()
//End of Functions added for CR KOC1168 Feedback Form 

//function to take the control to ENter hospital general screen
function onEnrollNewProvider()
{
	if(!JS_SecondSubmit)
	 {
		trimForm(document.forms[1]);
		document.forms[1].mode.value = "doEnrollNewProvider";
		document.forms[1].action = "/NewEnrollAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }
}
function onIsuranceNewProvider()
{
	if(!JS_SecondSubmit)
	 {
		trimForm(document.forms[1]);
		document.forms[1].mode.value = "doInsuranceNewProvider";
		document.forms[1].action = "/NewEnrollAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	 }
}

function OnProviderFacilities()
{
	document.forms[1].mode.value="doViewServicesList";
	document.forms[1].action="/NewEnrolGradingAction.do";
	document.forms[1].submit();
}

//for provider login
function onProceed()
{
	document.forms[1].tab.value="Check Eligibility";	
	document.forms[1].sublink.value="Approval Claims";
	document.forms[1].leftlink.value="Approval Claims";
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/OnlineProviderAction.do";
	document.forms[1].submit();
}

//for partner login
function onProceed1()
{
	document.forms[1].tab.value="Check Eligibility";	
	document.forms[1].sublink.value="Partner Approval Claims";
	document.forms[1].leftlink.value="Partner Approval Claims";
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/OnlinePartnerAction.do";
	document.forms[1].submit();
}

function onHRProceed()
{    
	
	document.forms[1].tab.value="Dashboard";	
    document.forms[1].sublink.value="Enrollment";
    document.forms[1].leftlink.value="Online Information";
    
	 document.forms[1].mode.value="doHRProceed";
	document.forms[1].action="/OnlineHomeAction.do";	
	document.forms[1].submit();
	
}