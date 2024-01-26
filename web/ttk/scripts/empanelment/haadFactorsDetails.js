function onClose()
{
	if(!TrackChanges()) return false;//end of if(!TrackChanges())
    document.forms[1].mode.value="doClose";
    document.forms[1].action="/UploadMOUCertificatesList.do";
    document.forms[1].submit();
}//end of onClose()

function numZeroValidation(field) {
    var re = /^[1-9]*\.*[0-9]*$/;
    if (!re.test(field.value)  ||  field.value <= 0) {
        alert("Data entered must be Numeric & Greater Then Zero");
		field.focus();
		field.value="";
		return false;
    }
}

function onSaveHaadFactors()
{
	
	networkType	=	document.forms[1].networkType.value;
	if(document.forms[1].haadTarrifStartDt.value=="")
	{
		alert("Please Select Tariff Start Date");
		document.forms[1].haadTarrifStartDt.focus();
		return false;
	}
	
	
	/*// zero validation
	 var factor	=	Number(document.forms[1].factor.value);
	
	 if(factor < 0 || factor == 0 )
	 {
		 alert("Enter Value Must be Greater then 0 ");
		 document.forms[1].factor.value="";
		 document.forms[1].factor.focus();
		 return;
	 }*/
	 
	if(!JS_SecondSubmit){ 
		document.forms[1].mode.value="doModifyMultipleHaadFactors";
		document.forms[1].action="/ModifyHaadFactorsHospitalAction.do?networkType="+networkType;
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}
}//end of onSaveHaadFactors()


																																		
function onUpdateHaadFactors()																						
{
	var haadGroup	=	document.forms[1].haadGroup.value;
	var haadfactor	=	document.forms[1].haadfactor.value;
	var factorflag=false;
	var baseflag=false;
	var discountflag=false;
	
	 // FACTOR
					if(haadfactor==="1")
					 {	
						var res = haadGroup.split("|");
						for(var i=0;i<res.length;i++)
						{
						 	 if(res[i]==="1" || res[i]==="2"||res[i]==="5" || res[i]==="8" || res[i]==="9" || res[i]==="10" )
						 	 {
						 		 factorflag=true;
						 		 alert("FACTOR is not applicable for DRUG,GENERIC DRUG,DENTAL & IR-DRG.");
						 		
								 break;
							 } 
						}
					
						if(factorflag === false) 
						{
							alert("FACTOR is not applicable for DRUG,GENERIC DRUG,DENTAL & IR-DRG.");
							 return;	 
						}
					 
						if(haadfactor==="1" )
						{
							 var factorVal	=	Number(document.forms[1].factorVal.value);
							 if(factorVal < 0 || factorVal == 0 )
							 {
								 alert("FACTOR must be greater than 0.");
								 document.forms[1].factorVal.value="";
								 document.forms[1].factorVal.focus();
								 return;
							}
						 }	 	
					 
					 }
					
		
					
		// BASE RATE			
					if(haadfactor==="2" )
					 {	
							var res = haadGroup.split("|");
						
							for(var i=0;i<res.length;i++)
							{
							
									if(res[i]==="6")
									{
							 		
										if(res.length==1)
										{
							 			 	baseflag=true;
							 			 	break;
										}
										else
										{						 		
											baseflag=true;
											alert("BASE RATE is applicable for IR-DRG only.");
											break;
										}
									
									}
							 }		
							
									if(baseflag === false) 
									{
										alert("BASE RATE is applicable for IR-DRG only.");
										return;	 
									}
							}
							
		// GAP			
					if(haadfactor==="3" )
					 {	
						var res = haadGroup.split("|");
						for(var i=0;i<res.length;i++)
						{
						 	 if(res[i]==="6")
						 	 {
						 		if(res.length==1)
								{
					 			 	baseflag=true;
					 			 	break;
								}
								else
								{						 		
									baseflag=true;
									alert("GAP is applicable for IR-DRG only.");
									break;
								}
						 		
							 } 
						}
						
						if(baseflag === false) 
						{
							alert("GAP is applicable for IR-DRG only.");
							 return;	 
						}
					}			
					
		
					
		// MARGIN			
					if(haadfactor==="4" )
					 {	
						var res = haadGroup.split("|");
						for(var i=0;i<res.length;i++)
						{
						 	 if(res[i]==="6")
						 	 {
						 		
						 		if(res.length==1)
								{
					 			 	baseflag=true;
					 			 	break;
								}
								else
								{						 		
									baseflag=true;
									alert("MARGIN is applicable for IR-DRG only.");
									break;
								}
							 } 
						}
						
						if(baseflag === false) 
						{
							alert("MARGIN is applicable for IR-DRG only.");
							 return;	 
						}
					}			
					
			
		// Discount			
					if(haadfactor==="5")
					 {	
						var res = haadGroup.split("|");
						for(var i=0;i<res.length;i++)
						{
						 	 if(res[i]==="3" || res[i]==="7" )
						 	 {
						 		
						 		if(res.length==1)
								{
						 		    discountflag=true;
					 			 	break;
								}
								else
								{						 		
									discountflag=true;
									alert("DISCOUNT is applicable for GENERIC DRUG and DRUG only.");
									break;
								}
							 } 
						}
						
						if(discountflag === false) 
						{
							alert("DISCOUNT is applicable for GENERIC DRUG and DRUG only.");
							 return;	 
						}
					}			
	
	 
	 
	 	if(!JS_SecondSubmit)
		{ 
	 	    document.forms[1].mode.value="doUpdateHaadFactors";
			document.forms[1].action="/UpdateHaadFactorsHospitalAction.do";
			JS_SecondSubmit=true;
			document.forms[1].submit();
		}
	
}//end of onUpdateHaadFactors()
function onEditValues()
{
	if(!JS_SecondSubmit){ 
		document.forms[1].mode.value="doEditValues";
		document.forms[1].action="/EditHaadFactorsHospitalAction.do?editFlag=Edit";
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}
}//end of onEditValues()

function changeNetworkType()
{
	networkType	=	document.forms[1].networkType.value;
	if(!JS_SecondSubmit){ 
		document.forms[1].mode.value="doChangeNetworkType";
		document.forms[1].action="/EditHaadFactorsHospitalAction.do?networkType="+networkType;
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}
}//end of changeNetworkType()


function changeNetworkTypeYN()
{
  var hosp_seq_id	=	document.getElementById("hosp_seq_id").value;
  var networkTypeYN = document.getElementById("networkTypeYN").value;
  var anetworkType = document.getElementById("anetworkType").value;
  
	if(networkTypeYN==="Y"){
		document.forms[1].eligibleNetworks.readOnly=false;
		document.forms[1].eligibleNetworks.style.backgroundColor="#EEEEEE";
		document.forms[1].eligibleNetworks.style.color="#666666";
		 document.forms[1].eligibleNetworks.value="|CN|GN|SN|EN|PN|ON|RN|CO|"; 
		 document.forms[1].mode.value="doHaadFactorDefault";
	    
	}else{
		document.forms[1].networkTypeYN.value="N";
		document.forms[1].eligibleNetworks.readOnly=true;
		document.forms[1].eligibleNetworks.style.backgroundColor="white";
		document.forms[1].eligibleNetworks.style.color="black"; 
	    document.forms[1].eligibleNetworks.value=""; 
	    document.forms[1].mode.value="doHaadFactorDefault";
    }
	document.forms[1].action="/EditHaadFactorsHospitalAction.do?hosp_seq_id="+hosp_seq_id;
	document.forms[1].submit();
}
