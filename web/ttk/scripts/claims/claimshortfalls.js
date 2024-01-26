
	function appendCheckBoxIds(elObj){
		
		var shortFallQueryIds=document.getElementById("shortFallQueryIds").value;   
		if(elObj.checked){			
			shortFallQueryIds=shortFallQueryIds.replace(elObj.id+"|","");
		}else{
			shortFallQueryIds =shortFallQueryIds+elObj.id+"|";
		}
		document.getElementById("shortFallQueryIds").value=shortFallQueryIds;			
		}
	
	function onSave(){
		
	var	varShortfallSeqID=document.forms[1].shortfallSeqID.value;	
	if(!JS_SecondSubmit){
       //checking the checkboxes checked or entered others  or not	    	
	if(varShortfallSeqID===""||varShortfallSeqID.length<1){

		var flag=true;
		var medicalQueriesChecks=document.forms[1].MedicalQueries;			
		var OtherQueries=document.getElementById("OtherQueries").value;		
		for(var i=0;i<medicalQueriesChecks.length;i++){
            if(medicalQueriesChecks[i].checked){
            	flag=false;break;
                }			
		    }
		
		if((flag)&&(OtherQueries===""||OtherQueries.length<10)){
            alert("Select Shortfall Queries Or Enter Other Queries");
            return ;
			}
			
		}else{ 
						
			var flag=true;
			var recievedChecks=document.forms[1].MedicalQueriesRecieved;			
			var recievedCheckBoxes = recievedChecks.length ? recievedChecks : [recievedChecks];
			for(var i=0;i<recievedCheckBoxes.length;i++){
	            if(recievedCheckBoxes[i].checked&&!recievedCheckBoxes[i].disabled){
	            	flag=false;break;
	                }			
			    }
		    
		   if(flag){
	            alert("Select Shortfall Queries Which Are Recieved");
	            return ;	            
				 }/*else{
					 if(!confirm("If You Want Re-Send Shortfalls Details"))return;					
					  } */
			}
		document.forms[1].mode.value="doSaveClaimShortFalls";				
	    document.forms[1].action="/ClaimShortfallsGeneralAction.do";
		JS_SecondSubmit=true;
	  	document.forms[1].submit();
	 }//if(!JS_SecondSubmit)
	}//end of onSave()
	function onGenerateShortFall(){
	    	
			if(document.forms[1].shortFall.value == 'SRT'){
		    	var parameterValue="";
		      	var sfTypeVal=document.getElementById("type").value;
		      	var claimNo = document.getElementById("claimNo").value;
		      	var shortfallNo = document.forms[1].shortfallNo.value;
			   	var DMSRefID = document.forms[1].DMSRefID.value;
			   	var authType = document.forms[1].authType.value;
			   	var claimSeqID = document.forms[1].claimSeqID.value;
			   	
		      	var parameter="";		      
		      	
		      	parameterValue="|"+document.forms[1].shortfallSeqID.value+"|"+document.getElementById("type").value+"|";
		      	if(sfTypeVal == "MDI")
		      	{
		 	  		parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallMedDoc.jrxml&reportID=ShortfallMid&claimNo="+claimNo+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID+"&authType="+authType+"&claimSeqID="+claimSeqID;
		      	}
		      	else if(sfTypeVal == "INC")
		      	{
		      		parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallInsDoc.jrxml&reportID=ShortfallIns&preAuthNo="+claimNo+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
		      	}
		      	else if(sfTypeVal == "INM")
		      	{
		      		parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallMisDoc.jrxml&reportID=ShortfallINM&preAuthNo="+claimNo+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
		      	}
		      	

		   	}//end of if(document.forms[0].leftlink.value == "Pre-Authorization")
			else if(document.forms[1].shortFall.value == 'DCV')
		   	{
		   		var parameterValue="|"+document.forms[1].shortfallSeqID.value+"|";
		   		parameter = "?mode=doGenerateReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/DischargeVoucher.jrxml&reportID=DischargeVoucher";
		   	}//end of else if(document.forms[0].leftlink.value == "Claims")
	      	var openPage = "/ReportsAction.do"+parameter;
	      	var w = screen.availWidth - 10;
	      	var h = screen.availHeight - 49;
	      	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	      	window.open(openPage,'',features);
		
		
	}//end of onGenerateShortFall()
		
		function setProcess(){
			var flag=false;
			var recievedChecks=document.forms[1].MedicalQueriesRecieved;			
			var recievedCheckBoxes = recievedChecks.length ? recievedChecks : [recievedChecks];
			for(var i=0;i<recievedCheckBoxes.length;i++){
	            if(!recievedCheckBoxes[i].checked){
	            	flag=true;break;
	                }			
			    }
		    if(flag){
			    document.getElementById("saveBtn").style.display="";
			    document.getElementById("processBtn").style.display="none";
			    document.forms[1].leftlink.value="";
			    document.forms[1].sublink.value="";
			    document.forms[1].tab.value="";
			    }else{
			    	document.getElementById("saveBtn").style.display="none";
				    document.getElementById("processBtn").style.display="";
				    document.forms[1].leftlink.value="Claims";
				    document.forms[1].sublink.value="Processing";
				    document.forms[1].tab.value="General";
				    }
			}
		
		function closeShortfalls(wayPath){
			if(wayPath==="goGeneral"){
				   document.forms[1].reforward.value="close";
				   document.forms[1].mode.value="doGeneral";
				   document.forms[1].action="/ClaimGeneralAction.do";	
				   document.forms[1].submit();	
			   }if(wayPath==="goSearch"){
				   document.forms[1].reforward.value="goShortfallSearch";
				   document.forms[1].mode.value="doGeneral";
				   document.forms[1].leftlink.value="Claims";
				   document.forms[1].sublink.value="Shortfalls";
				   document.forms[1].tab.value="Search";
				   document.forms[1].action="/ClaimGeneralAction.do";	
				   document.forms[1].submit();	
			   }					
				}