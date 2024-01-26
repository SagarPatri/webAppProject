function addconfigurationDetails(){
	 document.forms[1].mode.value = "doAdd";
	    //document.forms[1].tab.value="General";
	    document.forms[1].action = "/Configuration.do";
	    document.forms[1].submit();
}

function addconfigurationDetailsASO(asoFromDateOld,asoToDateOld)
{
	
	var healthauthority =document.getElementById("authority_type").value;
	var asoFromDate = document.getElementById("newAsoFromDate").value;
	var asoToDate = document.getElementById("newAsoToDate").value;
	
	var a = asoFromDateOld;
	var b = asoToDateOld;
	
	if(asoFromDateOld=="" || asoFromDateOld==null){

		alert("Please Add a new Premium effective period");
		return false;
		
	}
	
	
	if(asoFromDate=="" && asoToDate==""){

		alert("Please Add a new Premium effective period");
		document.forms[1].newAsoFromDate.focus();
		return false;
		
	}
	
	
	 if(a != asoFromDate) {
		alert("Please Save the data before adding rule.");
		return false;
	} 
	 if(b != asoToDate) {
		alert("Please Save the data before adding rule.");
		return false;
	} 
	
		 document.forms[1].mode.value = "doAdd";
		   document.forms[1].capitationYN.value="1";
		   document.forms[1].forward.value="EDIT";
		   document.forms[1].action = "/Configuration.do?healthauthority="+healthauthority;
		    document.forms[1].submit();
		
	
}
	
function addconfigurationDetailsASPlus(asPlusFromDateOld,asPlusToDateOld)
{
	var healthauthority =document.getElementById("authority_type").value;
	var asoFromDate = document.getElementById("newAsPlusFromDate").value;
	var asoToDate = document.getElementById("newAsPlusToDate").value;
	
	
	var a = asPlusFromDateOld;
	var b = asPlusToDateOld;
	
	
	
	
	
	if(asPlusFromDateOld=="" || asPlusFromDateOld==null){

		alert("Please Add a new Premium effective period");
		return false;
		
	}
	
	if(asoFromDate=="" && asoToDate==""){

		alert("Please Add a new Premium effective period");
		document.forms[1].newAsPlusFromDate.focus();
		return false;
		
	}
	
	if(asoFromDate==""){
		alert("Please select Premium Effective From Date");
		document.forms[1].newAsPlusFromDate.focus();
		return false;
	}
	
	 if(a != asoFromDate) {
		alert("Please Save the data before adding rule.");
		return false;
	} 
	 if(b != asoToDate) {
		alert("Please Save the data before adding rule.");
		return false;
	} 
	
		  document.forms[1].capitationYN.value="2";
		  document.forms[1].forward.value="EDIT";
			    //document.forms[1].tab.value="General";
			     document.forms[1].mode.value = "doAdd";
			     document.forms[1].action = "/Configuration.do?healthauthority="+healthauthority;
			    document.forms[1].submit();
}


function addSchemeConfigurationDetailsASO()
{
	var healthauthority =document.getElementById("authority_type").value;
	  document.forms[1].capitationYN.value="1";
		 // document.forms[1].capitationYN.value="3";
		    //document.forms[1].tab.value="General";
	         document.forms[1].forward.value="ADD";
		     document.forms[1].mode.value = "doAdd";
		     document.forms[1].action = "/Configuration.do?healthauthority="+healthauthority;
		    document.forms[1].submit();	
	
}


function addSchemeConfigurationDetailsASPlus()
{
	var healthauthority =document.getElementById("authority_type").value;
	  document.forms[1].capitationYN.value="2";
		 // document.forms[1].capitationYN.value="3";
		    //document.forms[1].tab.value="General";
		     document.forms[1].mode.value = "doAdd";
		     document.forms[1].action = "/Configuration.do?healthauthority="+healthauthority;
		    document.forms[1].submit();	
	
}



function addNewConfigurationDetailsASO()
{
	
	var asoFromDate = document.getElementById("asoFromDate").value;
	var asoToDate = document.getElementById("asoToDate").value;
	
	
	if(asoFromDate==""){
		alert("Please select Premium Effective From Date");
		document.forms[1].asoFromDate.focus();
		return false;
	}
	
	
	if(asoFromDate!="" && asoToDate !=""){
		
		if(compareLessDates("asoToDate","Premium Effective To Date","asoFromDate","Premium Effective From Date","lesser than")==false)
			{
				document.forms[1].asoToDate.value="";
			    return false;
			}
			
		else{
			 document.forms[1].mode.value = "doAdd";
			   document.forms[1].capitationYN.value="1";
			   
			   document.forms[1].forward.value="ADD";
			   
			    //document.forms[1].tab.value="General";
			    document.forms[1].action = "/Configuration.do";
			    document.forms[1].submit();
			}
		}
	
	
		 document.forms[1].mode.value = "doAdd";
		   document.forms[1].capitationYN.value="1";
		   
		   document.forms[1].forward.value="ADD";
		   
		    //document.forms[1].tab.value="General";
		    document.forms[1].action = "/Configuration.do";
		    document.forms[1].submit();
		
}
	
function addNewConfigurationDetailsASPlus()
{
	
	var asoFromDate = document.getElementById("asPlusFromDate").value;
	
	var asoToDate = document.getElementById("asPlusToDate").value;
	
	if(asoFromDate==""){
		alert("Please select Premium Effective From Date");
		document.forms[1].asPlusFromDate.focus();
		return false;
	}
	
	
	if(asoFromDate!="" && asoToDate !=""){
		
		if(compareLessDates("asPlusToDate","Premium Effective To Date","asPlusFromDate","Premium Effective From Date","lesser than")==false)
			{
				document.forms[1].asoToDate.value="";
			    return false;
			}
		else{
			  document.forms[1].capitationYN.value="2";
			  document.forms[1].forward.value="ADD";
				    //document.forms[1].tab.value="General";
				     document.forms[1].mode.value = "doAdd";
				    document.forms[1].action = "/Configuration.do";
				    document.forms[1].submit();
		
		}
		}
		
		  document.forms[1].capitationYN.value="2";
		  document.forms[1].forward.value="ADD";
			    //document.forms[1].tab.value="General";
			     document.forms[1].mode.value = "doAdd";
			    document.forms[1].action = "/Configuration.do";
			    document.forms[1].submit();
		
}
/*
function onClose()
{
	document.forms[1].mode.value = "doClosePremiumDetails";
	document.forms[1].action = "/Configuration.do";
	document.forms[1].submit();	
}
*/

function onPremiumPeriodClose()
{
	document.forms[1].mode.value= "doConfigureProductPremium";
	document.forms[1].action="/Configuration.do";
	document.forms[1].submit();
}


function onSaveAso()
{
	
	
	var asoFromDate = document.forms[1].newAsoFromDate.value;
	  
	 var asoToDate = document.forms[1].newAsoToDate.value;
	
	
	 if(asoFromDate==""){
		
				alert("Premium Effective From Date should not be empty.");
				document.forms[1].newAsoFromDate.focus();
				return false;
		 
	 }
	 
	 
	 
	if(asoFromDate!="" && asoToDate !=""){
		
	if(compareLessDates("newAsoToDate","Premium Effective To Date","newAsoFromDate","Premium Effective From Date","lesser than")==false)
		{
			document.forms[1].newAsPlusToDate.value="";
		    return false;
		}
		
		
		
		
		else{
			
			 document.forms[1].mode.value = "doSavePremiumConfig";
			   document.forms[1].capitationYN.value="1";
			    //document.forms[1].tab.value="General";
			  //  document.forms[1].action = "/Configuration.do";
			   
			   document.forms[1].action = "/UpdatePeriodConfiguration.do";
			   
			    document.forms[1].submit();
			
		}
		
	}
	else{
		 document.forms[1].mode.value = "doSavePremiumConfig";
		   document.forms[1].capitationYN.value="1";
		    //document.forms[1].tab.value="General";
		  //  document.forms[1].action = "/Configuration.do";
		   
		   document.forms[1].action = "/UpdatePeriodConfiguration.do";
		   
		    document.forms[1].submit();
		
	}
	

}
	
function onSaveAsPlus()
{
		
		var asoFromDate = document.forms[1].newAsPlusFromDate.value;
		  
		 var asoToDate = document.forms[1].newAsPlusToDate.value;
		
		 
		 if(asoFromDate==""){
				
				alert("Premium Effective From Date should not be empty");
				document.forms[1].newAsPlusFromDate.focus();
				return false;
		 
	 }
		
		if(asoFromDate!="" && asoToDate !=""){
			
		if(compareLessDates("newAsPlusToDate","Premium Effective To Date","newAsPlusFromDate","Premium Effective From Date","lesser than")==false)
			{
				document.forms[1].newAsPlusToDate.value="";
			    return false;
			}
		else{
			  document.forms[1].capitationYN.value="2";
				 // document.forms[1].capitationYN.value="3";
				    //document.forms[1].tab.value="General";
				     document.forms[1].mode.value = "doSavePremiumConfig";
				    document.forms[1].action = "/Configuration.do";
				    document.forms[1].submit();
			
		}
			
		}	
		else{
			
			  document.forms[1].capitationYN.value="2";
				 // document.forms[1].capitationYN.value="3";
				    //document.forms[1].tab.value="General";
				     document.forms[1].mode.value = "doSavePremiumConfig";
				    document.forms[1].action = "/Configuration.do";
				    document.forms[1].submit();
		}
		
		
		
		
	
}






function addNewPremiumPeriodAso()
{
	
	/*var asoFromDate = document.getElementById("asPlusFromDate").value;
	var asoToDate = document.getElementById("asPlusToDate").value;
	
	if(asoFromDate=="" && asoToDate==""){
		alert("Please select Premium Effective From Date or Premium Effective To Date");
		document.forms[1].asPlusFromDate.focus();
		return false;
	}*/
	
	             document.forms[1].capitationYN.value="1";
			     document.forms[1].mode.value = "doAddNewPremium";
			     document.forms[1].action = "/Configuration.do";
			     document.forms[1].submit();
	
	
	
}




function addNewPremiumPeriodAsPlus()
{
	 document.forms[1].capitationYN.value="2";
     document.forms[1].mode.value = "doAddNewPremium";
     document.forms[1].action = "/Configuration.do";
     document.forms[1].submit();
	
}





function onChangeEffectivePeriodAso()
{
	 document.forms[1].capitationYN.value="1";
     document.forms[1].mode.value = "doConfigureProductPremium";
     document.forms[1].action = "/Configuration.do";
     document.forms[1].submit();
	
}


function onChangeEffectivePeriodAsplus()
{
	 document.forms[1].capitationYN.value="2";
     document.forms[1].mode.value = "doConfigureProductPremium";
     document.forms[1].action = "/Configuration.do";
     document.forms[1].submit();
	
}



function onEffectivePeriodDeleteASO(asoFromDateOld,asoToDateOld){
var msg = confirm("Do you want to Delete Premium Effective Period?");
		if (msg) {
			
			
			var asoFromDate =asoFromDateOld;
			var asoToDate =asoToDateOld;
			document.forms[1].capitationYN.value="1";
			
			document.forms[1].mode.value = "doDeletePremium";
			//document.forms[1].child.value="";
			document.forms[1].premiumConfigSeqId.value = "";
			 document.forms[1].action = "/Configuration.do?asoFromDate="+asoFromDate+"&asoToDate="+asoToDate;
		//	document.forms[1].action = "/Configuration.do";
			document.forms[1].submit();
		}


}


function onEffectivePeriodDeleteASPLUS(asPlusFromDateOld,asPlusToDateOld){
	var msg = confirm("Do you want to Delete Premium Effective Period?");
			if (msg) {
				
				document.forms[1].capitationYN.value="2";
				var asplusFromDate =asPlusFromDateOld;
				var asplusToDate =asPlusToDateOld;
				
				document.forms[1].mode.value = "doDeletePremium";
				//document.forms[1].child.value="";
				document.forms[1].premiumConfigSeqId.value = "";
				//document.forms[1].action = "/Configuration.do";
				 document.forms[1].action = "/Configuration.do?asplusFromDate="+asplusFromDate+"&asplusToDate="+asplusToDate;
				document.forms[1].submit();
			}


	}





