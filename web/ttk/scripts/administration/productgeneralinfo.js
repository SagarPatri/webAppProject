function onReset()
{

if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	{getInsuranceCompany
		document.forms[1].mode.value="doReset";
		document.forms[1].action="/EditProductAction.do";
		document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else

}//end of onReset()

function onSave()
{
    trimForm(document.forms[1]);
    if(!JS_SecondSubmit)
    {
    	if(document.forms[1].stopPreAuthsYN.checked)
    		document.forms[1].stopPreAuth.value="Y";
    	else
    		document.forms[1].stopPreAuth.value="N";
    	if(document.forms[1].stopClaimsYN.checked)
    		document.forms[1].stopClaim.value="Y";
    	else
    		document.forms[1].stopClaim.value="N";
			//KOC 1286 for OPD Benefit
    	if(document.forms[1].opdClaimsYN.checked)
    		document.forms[1].opdClaim.value="Y";
    	else
    		document.forms[1].opdClaim.value="N";
    	//KOC 1286 for OPD Benefit
			
			//KOC 1270 for hospital cash benefit
    	
    	if(document.forms[1].cashBenefitsYN.checked)
    	{
    		document.forms[1].cashBenefit.value="Y";
    	}
    	else
    	{
    		document.forms[1].cashBenefit.value="N";
    	}
    	
    	if(document.forms[1].convCashBenefitsYN.checked) 
    	{
    		document.forms[1].convCashBenefit.value="Y";
    	}
    	else
    	{
    		document.forms[1].convCashBenefit.value="N";
    	}
    	
    	//KOC 1270 for hospital cash benefit
    	
    	//added for KOC-1273
    	if(document.forms[1].criticalBenefitYN.checked)
    		document.forms[1].criticalBenefit.value="Y";
    	else
    		document.forms[1].criticalBenefit.value="N";
    	
    	if(document.forms[1].survivalPeriodYN.checked)
    		document.forms[1].survivalPeriod.value="Y";
    	else
    		document.forms[1].survivalPeriod.value="N";
    	
    	
    	
    	
    	var vatPercent = document.forms[1].vatPercent.value;
    	if(vatPercent>100){
    		
    		alert("VAT(%) should not be greater than 100 ");
    		document.getElementById("vatPercent").value="";
    		document.getElementById("vatPercent").focus();
   	    	 return false;
    		
    	}
    	
    	var refundFalg = document.forms[1].refundFalg.value;
    	if(refundFalg == "Y" ){
    		var previousRefundedYN = document.forms[1].previousRefundedYN.value;
    		var refundedYN = document.forms[1].refundedYN.value;
    		if(previousRefundedYN != refundedYN){
    			var overrRemarks=prompt("Enter Modification Remarks","");
    			var space = overrRemarks.charCodeAt(0); 
    			  if(space==32)
                  {
                         alert("Modification Remarks should not start with space.");
                         document.getElementById("refundedYNRemarks").value="";
                         document.getElementById("refundedYNRemarks").focus();
                         return;
                  } 
    			
    			
        		if(overrRemarks==null||overrRemarks===""){
        	
        		 alert("Modification Remarks Are Required");
        		 return;
        	 }else if(overrRemarks.length<20){
        		 alert("Override Remarks Should Not Less Than 20 Characters");
        		 return;
        	 }
        		
        		
        		 document.forms[1].refundedYNRemarks.value=overrRemarks;
        	
    			
    		}
    		else{
    			document.forms[1].mode.value="doSave";
    			document.forms[1].action="/UpdateProductAction.do";
    			JS_SecondSubmit=true;
    			document.forms[1].submit();
    			
    		}
    		
    		}
    	enableRequiredFields();
    	document.forms[1].mode.value="doSave";
		document.forms[1].action="/UpdateProductAction.do";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSave()

function onCopyRules()
{
    trimForm(document.forms[1]);
    if(document.forms[1].insuranceSeqId.value=="")
    {
    	alert('Please select Healthcare Company');
    	document.forms[1].insuranceSeqId.focus();
	    return false;
    }
    if(document.forms[1].productSeqID.value=="")
    {
        alert('Please select Product Name');
    	document.forms[1].productSeqID.focus();
	    return false;
    }
    var msg =  confirm("Are you sure want to copy Product Rules from the selected Product?");
	if (msg == false)
		return false;	
    if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value="doCopyRules";
		document.forms[1].action="/EditProductAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onCopyRules()

function onChangeInsurance()
{
    trimForm(document.forms[1]);
    if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value="doChangeInsurance";
		document.forms[1].action="/EditProductAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onCopyRules()

function onConfiguration()
{
	document.forms[1].mode.value="doConfiguration";
	document.forms[1].action="/EditProductAction.do";
	document.forms[1].submit();
}//end of onConfiguration()

function stopPreAuthClaim()
{
	if(document.forms[1].stopPreAuth.value=="Y")
	    document.forms[1].stopPreAuthsYN.checked=true;
	else
		document.forms[1].stopPreAuthsYN.checked=false;
	if(document.forms[1].stopClaim.value=="Y")
		document.forms[1].stopClaimsYN.checked=true;
	else
		document.forms[1].stopClaimsYN.checked=false;
		//KOC 1286 for OPD Benefit
	if(document.forms[1].opdClaim.value=="Y")
		document.forms[1].opdClaimsYN.checked=true;
	else
		document.forms[1].opdClaimsYN.checked=false;
	//KOC 1286 for OPD Benefit
		
		//KOC 1270 for hospital cash benefit
	
	if(document.forms[1].cashBenefit.value=="Y") 
	{
		document.forms[1].cashBenefitsYN.checked=true;
	}
	else
	{
		document.forms[1].cashBenefitsYN.checked=false;
	}
	
	if(document.forms[1].convCashBenefit.value=="Y")  
	{
		document.forms[1].convCashBenefitsYN.checked=true;
	}
	else
	{
		document.forms[1].convCashBenefitsYN.checked=false;
	}
	//KOC 1270 for hospital cash benefit
}//end of stopPreAuthClaim()
function criticalBenefit()
{
	if(document.forms[1].criticalBenefit.value=="Y")
	    document.forms[1].criticalBenefitYN.checked=true;
	else
		document.forms[1].criticalBenefitYN.checked=false;
}

function survivalPeriod()
{
	if(document.forms[1].survivalPeriod.value=="Y")
		document.forms[1].survivalPeriodYN.checked=true;
	else
		document.forms[1].survivalPeriodYN.checked=false;
	
}
function getProductCode(obj)
{
	var prodname	=	obj.value;
	if (window.ActiveXObject){
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		if (xmlhttp){
			xmlhttp.open("GET",'/EditProductAction.do?mode=getProductCode&prodname=' +prodname ,true);
			xmlhttp.onreadystatechange=state_GetProdCode;
			xmlhttp.send();
		}
	}
	
}

function state_GetProdCode(){
	var result,result_arr;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split(","); 
			document.forms[1].insProductCode.value=result_arr[0];
			
		}
	}
}
function setAuthorityLabel(obj) {
	if(obj.value=="HAAD"){
		document.getElementById("authorityID").innerHTML="Authority Product Reference No:";
	}else{
		document.getElementById("authorityID").innerHTML="Authority Product ID:";
	}
}

function validateCopay(selObj,cId) {
	if(cId==="MT"){
		if(selObj.value==="Y"){
			document.forms[1].maternityCopay.readOnly=false;
			document.forms[1].maternityCopay.style.backgroundColor="white";
			document.forms[1].maternityCopay.style.color="black"; 
		}else{
			document.forms[1].maternityCopay.readOnly=true;
			document.forms[1].maternityCopay.style.backgroundColor="#EEEEEE";
			document.forms[1].maternityCopay.style.color="#666666";
		    document.forms[1].maternityCopay.value=""; 
		}
	}else if(cId==="OT"){
		if(selObj.value==="Y"){
			document.forms[1].opticalCopay.readOnly=false;
			document.forms[1].opticalCopay.style.backgroundColor="white";
			document.forms[1].opticalCopay.style.color="black"; 
		}else{
			document.forms[1].opticalCopay.readOnly=true;
			document.forms[1].opticalCopay.style.backgroundColor="#EEEEEE";
			document.forms[1].opticalCopay.style.color="#666666"; 
			document.forms[1].opticalCopay.value=""; 
		}
	}else if(cId==="DT"){
		if(selObj.value==="Y"){
			document.forms[1].dentalCopay.readOnly=false;
			document.forms[1].dentalCopay.style.backgroundColor="white";
			document.forms[1].dentalCopay.style.color="black"; 
		}else{
			document.forms[1].dentalCopay.readOnly=true;
			document.forms[1].dentalCopay.style.backgroundColor="#EEEEEE";
			document.forms[1].dentalCopay.style.color="#666666"; 
			document.forms[1].dentalCopay.value=""; 
		}
	}
}



function doSelectIns()
{
	      
			document.forms[1].mode.value ="changeInsCmp";
		    document.forms[1].action = "/EditProductAction.do";
		    document.forms[1].submit();
		
}

// getInsuranceCompany Name By  healthAuthority
function getInsuranceCompany(){
	 
	 trimForm(document.forms[1]);
	    if(!JS_SecondSubmit)
	    {
			document.forms[1].mode.value="changeAuthority";
			document.forms[1].action="/EditProductAction.do";
			JS_SecondSubmit=true
			document.forms[1].submit();
		}//end of if(!JS_SecondSubmit)
  	
}//getInsuranceCompany


function changeInsCmp()
{
    trimForm(document.forms[1]);
    if(!JS_SecondSubmit)
    {
		document.forms[1].mode.value="changeInsCmp";
		document.forms[1].action="/EditProductAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of changeInsCmp()


var  popupWindow=null;
function modificationRemarks(tp,lt){
	
	
		  var openPage = "/EditProductAction.do?mode=modificationRemarks";
		  
		   var features = "scrollbars=0,status=0,toolbar=0,top="+tp+",left="+lt+",resizable=0,menubar=no,width=800,height=300";
		   popupWindow=  window.open(openPage,'',features);
		   popupWindow.focus(); 
			  document.onmousedown=focusPopup; 
			  document.onkeyup=focusPopup; 
			  document.onmousemove=focusPopup;
	
}


function isNumaricOnly(upObj){
	
	var re = /^[0-9]*\.*[0-9]*$/;	
	var objValue=upObj.value;
	if(objValue.length>=1){
		if(!re.test(objValue)){
			alert("Please Enter Valid Data");
			upObj.value="";
			upObj.focus();
		}
	}  
	
}


var keybNumberAndAlpha = new keybEdit(' 0123456789abcdefghijklmnopqrstuvwxyz%()&;,./:+-');
function keybEdit(strValid, strMsg) {
    var reWork = new RegExp('[a-z]','gi');		//	Regular expression\
    //	Properties
    if(reWork.test(strValid))
            this.valid = strValid.toLowerCase() + strValid.toUpperCase();
    else
            this.valid = strValid;
    if((strMsg == null) || (typeof(strMsg) == 'undefined'))
            this.message = '';
    else
            this.message = strMsg;
    //	Methods
    this.getValid = keybEditGetValid;
    this.getMessage = keybEditGetMessage;
    function keybEditGetValid() {
            return this.valid.toString();
    }
    function keybEditGetMessage() {
            return this.message;
    }
}

function onRestrictChar(ev, objForm, objKeyb) {
    strWork = objKeyb.getValid();    
    strMsg = '';							// Error message
    blnValidChar = false;					// Valid character flag
    var BACKSPACE = 8;
    var DELETE = 46;
    var TAB = 9;
    // Checking backspace and delete  
    if(ev.keyCode == BACKSPACE || ev.keyCode == DELETE || ev.keyCode == TAB)  {  
        blnValidChar = true; 
    }
    
    if(!blnValidChar) // Part 1: Validate input
            for(var i=0;i < strWork.length;i++)
                    if(ev.keyCode == strWork.charCodeAt(i) ) {
                            blnValidChar = true;
                            break;
                    }
                            // Part 2: Build error message
    if(!blnValidChar) 
    {
    alert('\'Please Enter Valid Characters\'');
                //if(objKeyb.getMessage().toString().length != 0)
                    //		alert('Error: ' + objKeyb.getMessage());
            ev.returnValue = false;		// Clear invalid character
            
            
//                ev.preventDefault();
        
            objForm.focus();						// Set focus
    }
}

function selectPolicySubType()
{
//	alert("before :SMEproductYN->"+document.forms[1].SMEproductYN.value+"|productSubtype->"+document.forms[1].productSubtype.value+"|totalSumInsured->"+document.forms[1].totalSumInsured.value)
	if(document.forms[1].SMEproductYN.value == "Y")		// individual
	{
		document.forms[1].productSubtype.disabled=false;
		document.forms[1].totalSumInsured.disabled=false;
		document.forms[1].totalSumInsured.style="";
	}
	if(document.forms[1].SMEproductYN.value == "" || document.forms[1].SMEproductYN.value == "N")		// corporate
	{
			document.forms[1].productSubtype.value="";
			document.forms[1].totalSumInsured.value="";
			document.forms[1].productSubtype.disabled=true;
			document.forms[1].totalSumInsured.disabled=true;
			document.forms[1].totalSumInsured.style="background-color:#EEEEEE";
	}	
//	alert("after :SMEproductYN->"+document.forms[1].SMEproductYN.value+"|productSubtype->"+document.forms[1].productSubtype.value+"|totalSumInsured->"+document.forms[1].totalSumInsured.value)
}

function enableRequiredFields()
{
	 document.forms[1].productSubtype.disabled=false;
	 document.forms[1].totalSumInsured.disabled=false;
}