function setValue(idValue){
	var queryIds=document.forms[1].queryIds.value;
	var value1=idValue.value+"|";
	if(idValue.checked){
		queryIds=queryIds.replace(value1,"");
		}else{
			queryIds=queryIds.concat(value1);
			}
	document.forms[1].queryIds.value=queryIds;
}//function setValue(idValue){
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
	//var	queryIds=document.forms[1].queryIds.value;
	//alert(queryIds);
	if(!JS_SecondSubmit){
       //checking the checkboxes checked or entered others  or not	    	
	if(varShortfallSeqID===""||varShortfallSeqID.length<1){

		var flag=true;
		var medicalQueriesChecks=document.forms[1].allQueries;		
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
			
		}else{//if(varShortfallSeqID===""||varShortfallSeqID.length<1)
			//var varStatusTypeID=document.forms[1].statusTypeID.value; 
						
			var flag=true;
			var recievedChecks=document.forms[1].QueriesRecieved;			
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
		document.forms[1].mode.value="doSavePreauthShortFalls";				
	    document.forms[1].action="/PreAuthShortfallsGeneralAction.do";
		JS_SecondSubmit=true;
	  	document.forms[1].submit();
	 }//if(!JS_SecondSubmit)
	}//end of onSave()
	function onGenerateShortFall()
	{
		
			if(document.forms[1].shortFall.value === 'SRT'){
		    	var parameterValue="";
		      	var sfTypeVal=document.getElementById("type").value;
		      	var preauthno = document.getElementById("preAuthNo").value;
		   //   var shortfallNo = document.getElementById("shortfallNo").value;
		      	var shortfallNo = document.forms[1].shortfallNo.value;
		      	var preAuthSeqID = document.getElementById("preAuthSeqID").value;
		      	var preauthStatus = document.getElementById("preauthStatus").value;
		      	var authType = document.forms[1].authType.value;
		  //   	var DMSRefID = document.getElementById("DMSRefID").value;
		      	var DMSRefID = document.forms[1].DMSRefID.value;
			   	//added for Mail-SMS Template for Cigna
		      	var parameter="";		      
		      	
		      	parameterValue="|"+document.forms[1].shortfallSeqID.value+"|"+document.getElementById("type").value+"|";
		      	
		      	if(sfTypeVal == "MDI")
		      	{
		 	  		parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallMedDoc.jrxml&reportID=ShortfallMid&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID+"&preAuthSeqID="+preAuthSeqID+"&preauthStatus="+preauthStatus+"&authType="+authType;
		      	}
		      	else if(sfTypeVal == "INC")
		      	{
		      		parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallInsDoc.jrxml&reportID=ShortfallIns&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
		      	}
		      	else if(sfTypeVal == "INM")
		      	{
		      		parameter = "?mode=doGenerateXmlReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/ShortfallMisDoc.jrxml&reportID=ShortfallINM&preAuthNo="+preauthno+"&shortfallNo="+shortfallNo+"&DMSRefID="+DMSRefID;
		      	}
		 }else if(document.forms[1].shortFall.value == 'DCV'){
		   		var parameterValue="|"+document.forms[1].shortfallSeqID.value+"|";
		   		parameter = "?mode=doGenerateReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/DischargeVoucher.jrxml&reportID=DischargeVoucher";
		   	}else{
		   		parameter="";
			   	}
	      	var openPage = "/ReportsAction.do"+parameter;
	      	var w = screen.availWidth - 10;
	      	var h = screen.availHeight - 49;
	      	var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	      	window.open(openPage,'',features);
	}//end of onGenerateShortFall()

function closePreauthShortfalls(){	
   			document.forms[1].leftlink.value="Pre-Authorization";
	        document.forms[1].sublink.value="Shortfall";
	        document.forms[1].tab.value="Search";
	        document.forms[1].action="/ttkAction.do?mode=doDefault"; 		  
	 	  	document.forms[1].submit();
		}//closeShortfalls()
		
		function setProcess(){
			var flag=false;
			var recievedChecks=document.forms[1].QueriesRecieved;			
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
			    document.forms[1].shortfallClosedYN.value="NO";
			    }else{
			    	 document.forms[1].leftlink.value="Pre-Authorization";
					 document.forms[1].sublink.value="Processing";
					 document.forms[1].tab.value="System Preauth Approval";
			    	document.getElementById("saveBtn").style.display="none";
				    document.getElementById("processBtn").style.display="";
				    document.forms[1].shortfallClosedYN.value="YES";
				    }
			}
		function closeShortfalls(wayPath){
			if(wayPath==="goGeneral"){
				   document.forms[1].reforward.value="close";
				   document.forms[1].mode.value="doGeneral";
				   document.forms[1].action="/PreAuthGeneralAction.do";	
				   document.forms[1].leftlink.value="Pre-Authorization";
				   document.forms[1].sublink.value="Processing";
				   document.forms[1].tab.value="System Preauth Approval";
				   document.forms[1].submit();	
			   }if(wayPath==="goSearch"){
				   document.forms[1].reforward.value="goShortfallSearch";
				   document.forms[1].mode.value="doGeneral";
				   document.forms[1].leftlink.value="Pre-Authorization";
				   document.forms[1].sublink.value="Shortfalls";
				   document.forms[1].tab.value="Search";
				   document.forms[1].action="/PreAuthGeneralAction.do";	
				   document.forms[1].submit();	
			   }					
				}//function closeShortfalls(wayPath){