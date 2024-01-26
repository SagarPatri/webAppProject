//JavaScript for certificatelist.jsp

var selectedPath = "";
function toRadioButton( obj, bChkd, objform ,path)
{
	selectedPath = path;
	document.forms[1].selectButton.style.display=""; 
}//end of function toRadioButton( obj, bChkd, objform )

function onSelect(){
	document.forms[1].mode.value="doSelect";
	document.forms[1].certPath.value= selectedPath;
	document.forms[1].action = "/AssociateCertificatesList.do";
	document.forms[1].submit();
}//end of onSelect()

function onClose(){
	document.forms[1].mode.value="doDefault";
	document.forms[1].action = "/AssociateCertificatesList.do";
	document.forms[1].submit();
}//end of onClose()