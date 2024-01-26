
//java script for configurationtds.jsp

function onRootAddIcon(strRootIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doAdd";
	document.forms[1].child.value="AddSubCategory";
	document.forms[1].action = "/AddTDSConfigurationAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].submit();     
}//end of onRootAddIcon(strRootIndex)


//function on click of NodeEditIcon
function onNodeEditIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].reset();
	document.forms[1].mode.value ="doViewSubCategory";
	document.forms[1].child.value="AddSubCategory";
	document.forms[1].action = "/AddTDSConfigurationAction.do";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	document.forms[1].submit();     
}//end of onRootEditIcon(strRootIndex)

function onNodeClauseIcon(strRootIndex,strNodeIndex)
{
	document.forms[1].mode.value="doViewTaxPerc";
	document.forms[1].child.value="TDS Tax Configure";
	document.forms[1].selectedroot.value=strRootIndex;
	document.forms[1].selectednode.value=strNodeIndex;
	document.forms[1].action="/TDSTaxConfigurationAction.do";
	document.forms[1].submit();	 
}//end of onNodeClauseIcon(strRootIndex,strNodeIndex)
function OnShowhideClick(strRootIndex)
{   
	document.forms[1].mode.value="doShowHide";
	document.forms[1].selectedroot.value=strRootIndex;
    document.forms[1].action="/TDSConfigurationAction.do";
	document.forms[1].submit();	 
}//end of OnShowhideClick(strRootIndex)
function onClose()
{   
	document.forms[1].mode.value="doClose";
	document.forms[1].action="/ConfigurationAction.do";
	document.forms[1].submit();	 
}//end of onClose()
function hospitalReport()
{	
	var fileName = "generalreports/HospitalWiseReport.jrxml";
	var reportID = "HospitalTDSRpt";
	var partmeter = "?mode=showHospitalReport&fileName="+fileName+"&reportID="+reportID+"&reportType=EXL";
	var openPage = "/TDSTaxConfigurationAction.do"+partmeter;
	var w = screen.availWidth - 10;
	var h = screen.availHeight - 49;
	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=no,width="+w+",height="+h;
	window.open(openPage,'',features);		
}//end of hospitalReport()



