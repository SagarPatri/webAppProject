var  popupWindow=null;	

function getStates(){
	 var countryId= document.getElementById("providerCountry").value;
	 
	 var myselect1=document.getElementById("providerEmirate");
	 while (myselect1.hasChildNodes()) {   
   	    myselect1.removeChild(myselect1.firstChild);
     }
	 var myselect2=document.getElementById("providerArea");
	 while (myselect2.hasChildNodes()) {   
   	    myselect2.removeChild(myselect2.firstChild);
     }
	 myselect1.options.add(new Option("Select from list",""));
	 myselect2.options.add(new Option("Select from list",""));
  
       var  path="/asynchronAction.do?mode=getStates&countryId="+countryId;		                 

  	 $.ajax({
  	     url :path,
  	     dataType:"text",
  	     success : function(data) {
  	     var res1 = data.split("&");
  	     for(var i=0;i<res1.length-1;i++){   	    	    
  	     var res2=res1[i].split("@");
  	        myselect1.options.add(new Option(res2[1],res2[0]));  	                 
  	     }//for()
  	     }//function(data)
  	 });
 
}//getStates

function getAreas(){
	 
		 var stateId= document.getElementById("providerEmirate").value;
	 var myselect1=document.getElementById("providerArea");
	 while (myselect1.hasChildNodes()) {   
  	    myselect1.removeChild(myselect1.firstChild);
    }
	 myselect1.options.add(new Option("Select from list",""));
 
      var  path="/asynchronAction.do?mode=getAreas&stateId="+stateId;		                 

 	 $.ajax({
 	     url :path,
 	     dataType:"text",
 	     success : function(data) {
 	     var res1 = data.split("&");
 	     for(var i=0;i<res1.length-1;i++){   	    	    
 	     var res2=res1[i].split("@");
 	        myselect1.options.add(new Option(res2[1],res2[0]));  	                 
 	     }//for()
 	     }//function(data)
 	 });

}//getAreas
function getClinicianDetails(){
   	document.getElementById('clinicianId').value='';
   	document.getElementById('clinicianResult2').innerHTML='';			
	 var clinicianName=document.getElementById("clinicianName").value;
	 if(clinicianName==null||clinicianName===""||clinicianName.length<1){
		 document.getElementById('clinicianResult1').style.color='red';
    	document.getElementById('clinicianResult2').innerHTML='Enter Clinician Name';                
	 }
	 else{
		 if(clinicianName.length>5){
		$.ajax({
            url :"/asynchronAction.do?mode=getClinicianDetails&clinicianName="+clinicianName,
            dataType:"text",
			type:"POST",
            success : function(data) {
            var dataArray=data.split("@");
               var rstatus=dataArray[0];
               if(rstatus==="EO"||rstatus.length==2){//EO i.e Exception Occured
               	document.getElementById('clinicianResult1').style.color='red';
               	document.getElementById('clinicianResult2').innerHTML='There is some problem while performing operations on the database.<br>Please report this problem to the administrator.';
                 }else if(rstatus==="CNE"||rstatus.length==3){//PNE i.e Clinician Not Exist 
               	//document.getElementById('clinicianResult1').style.color='red';
                 	//document.getElementById('clinicianResult2').innerHTML='Your Given Provider Name Incorrect';
                  }else if(rstatus==="SUCC"||rstatus.length==4){//Success
               	   document.getElementById('clinicianResult1').style.color='green';
                     document.getElementById('clinicianResult2').innerHTML='Valid';
               	// var clinicianName=dataArray[1];
               	 var clinicianId=dataArray[2];
               	document.getElementById('clinicianId').value=clinicianId;                       
                  }//if("SUCC")
            }//success data
		});//$.ajax(
		 }//if
	}//else
}//getClinicianDetails

function getProviderDetails(){
	document.getElementById('providerSeqId').value='';
   	document.getElementById('providerId').value='';
   	document.getElementById('providerResult2').innerHTML='';			
	 var providerName=document.getElementById("providerName").value;
	 if(providerName==null||providerName===""||providerName.length<1){
		 document.getElementById('providerResult1').style.color='red';
    	document.getElementById('providerResult2').innerHTML='Enter Provider Id';                
	 }else{
		if(providerName.length>5){					
		$.ajax({
            url :"/asynchronAction.do?mode=getProviderDetails&providerName="+providerName,
            dataType:"text",
			type:"POST",
            success : function(data) {
            	 var dataArray=data.split("@");
               var rstatus=dataArray[0];
               if(rstatus==="EO"||rstatus.length==2){//EO i.e Exception Occured
               	document.getElementById('providerResult1').style.color='red';
               	document.getElementById('providerResult2').innerHTML='There is some problem while performing operations on the database.<br>Please report this problem to the administrator.';
                 }else if(rstatus==="PNE"||rstatus.length==3){//PNE i.e Priveder Not Exist 
               	document.getElementById('providerResult1').style.color='red';
                 document.getElementById('providerResult2').innerHTML='Your Given Provider Name Incorrect';
                  }else if(rstatus==="SUCC"||rstatus.length==4){//Success
               	   document.getElementById('providerResult1').style.color='green';
                     document.getElementById('providerResult2').innerHTML='Valid';
               	 var providerSeqId=dataArray[1];
               	 //var providerName=dataArray[2];
               	 var providerLicenNum=dataArray[3];
               	document.getElementById('providerSeqId').value=providerSeqId;
               	document.getElementById('providerId').value=providerLicenNum;
               //	document.getElementById('providerName').value=providerName;
               
                  }//if("SUCC")
              }//success data
		 });//$.ajax(
		}//if
	}//else
}//getProviderDetails

function focusPopup() {
	  if(popupWindow && !popupWindow.closed) { popupWindow.focus(); } 
	}
function openOTPWindow(){  
	//clear data	 
	document.getElementById('memberIdResult2').innerHTML='';
	document.getElementById('memberSeqID').value='';
	document.getElementById('patientName').value='';
	document.getElementById('memberAge').value='';                                	
	document.getElementById('emirateId').value='';
	document.getElementById('payerId').value='';
	document.getElementById('insSeqId').value='';
	document.getElementById('payerName').value='';
	document.getElementById('policySeqId').value='';
	document.getElementById('patientGender').value='';
	document.getElementById('policyNumber').value='';
	document.getElementById('corporateName').value='';
	document.getElementById('availableSumInsured').value='';
	document.getElementById('sumInsured').value='';
	document.getElementById('policyStartDate').value='';
	document.getElementById('policyEndDate').value='';
	document.getElementById('nationality').value='';
	document.getElementById('productName').value='';
	document.getElementById('payerAuthority').value='';
	document.getElementById('vipYorN').value='';
	
	document.getElementById('preMemInceptionDt').value='';
	
var	preauthMode=document.getElementById("preauthMode").value;
var memberId=document.getElementById("memberId").value;
if(memberId==null||memberId==""||memberId.length<1){
	 document.getElementById('memberIdResult1').style.color='red';
	document.getElementById('memberIdResult2').innerHTML='Enter Member Id';
}else{
	//member validation 
	$.ajax({
      url :"/asynchronAction.do?mode=getMemberDetails&memberId="+memberId,
      dataType:"text",
	  type:"POST",
      success : function(data1) {       
    
   var dataArray1=data1.split("@");
   var rstatus1=dataArray1[0];
   if(rstatus1==="EO"||rstatus1.length==2){
   	document.getElementById('memberIdResult1').style.color='red';
   	document.getElementById('memberIdResult2').innerHTML='There is some problem while performing operations on the database.<br>Please report this problem to the administrator.';             
       
        }else if(rstatus1==="MNE"||rstatus1.length==3){
          document.getElementById('memberIdResult1').style.color='red';
         	document.getElementById('memberIdResult2').innerHTML='Member Id Not Valid';               	                            
	       }else if(rstatus1==="SUCC"||rstatus1.length==4) {
	     // if(preauthMode=='EML1'||preauthMode==='EML1'||preauthMode=='FAX1'||preauthMode==='FAX1'||){
		  if(preauthMode ==='DHP'){       
		  	                         //dataArray1                   
		                          	   document.getElementById('memberIdResult1').style.color='green';
		                                document.getElementById('memberIdResult2').innerHTML='Member Id Valid';
		                          	 var memberSeqId=dataArray1[1];
		                          	 var memberName=dataArray1[2];
		                          	 var memberAge=dataArray1[3];
		                          	 var emirateId=dataArray1[4];
		                          	 var payerId=dataArray1[5];
		                          	 var insSeqId=dataArray1[6];
		                          	 var insCompName=dataArray1[7];
		                          	 var policySeqId=dataArray1[8];
		                          	 var patientGender=dataArray1[9];
		                          	var policyNumber=dataArray1[10];
		                          	var corporateName=dataArray1[11];
		                          	var policyStartDate=dataArray1[12];
		                          	var policyEndDate=dataArray1[13];
		                          	var nationality=dataArray1[14];
		                          	var sumInsured=dataArray1[15];
		                          	var availableSumInsured=dataArray1[16];
		                          	var vipyn=dataArray1[17];
		                          	
		                        	var pat_mem_insp_date=dataArray1[18];
		                          	
		                          	
		                          	
		                          	document.getElementById('memberSeqID').value=memberSeqId;
		                          	document.getElementById('patientName').value=memberName;
		                          	document.getElementById('memberAge').value=memberAge;                                	
		                          	document.getElementById('emirateId').value=emirateId;
		                          	document.getElementById('payerId').value=payerId;
		                          	document.getElementById('insSeqId').value=insSeqId;
		                          	document.getElementById('payerName').value=insCompName;
		                          	document.getElementById('policySeqId').value=policySeqId;
		                          	document.getElementById('patientGender').value=patientGender;
		                          	document.getElementById('policyNumber').value=policyNumber;
		                          	document.getElementById('corporateName').value=corporateName;
		                          	document.getElementById('policyStartDate').value=policyStartDate;
		                          	document.getElementById('policyEndDate').value=policyEndDate;
		                          	document.getElementById('nationality').value=nationality;
		                          	document.getElementById('sumInsured').value=sumInsured;
		                          	document.getElementById('availableSumInsured').value=availableSumInsured;
		                          	document.getElementById('vipYorN').value=vipyn;
		                       	document.getElementById('preMemInceptionDt').value=pat_mem_insp_date;
		                        	
		                     
          }else{
        	 
        	  popupWindow= window.open("/OtpViewPage.do?memberId="+memberId,"OTP","width=500,height=200,left=300,top=200,toolbar=no,scrollbars=no,status=no"); 
     		 
   		  popupWindow.focus(); 
   		  document.onmousedown=focusPopup; 
   		  document.onkeyup=focusPopup; 
   		  document.onmousemove=focusPopup;         	  
            }//else preauthMode
		     
	       }
}//function(data)
});//$.ajax(
		 }//else        
	}//openOTPWindow()
function getIcdCodeDetails(){
	document.getElementById('icdCodeSeqId').value='';//refresh
  	document.getElementById('diagnosisDescription').value='';//refresh
  	 var diagSeqId=document.forms[1].diagSeqId.value;
  //	document.getElementById('primaryAilment').checked=false;//refresh
  var icdCode=document.getElementById("icdCode").value;
  var authType=document.getElementById("authType").value;
  var seqId="";
  if(icdCode==null||icdCode===""||icdCode.length<1){
	  document.getElementById('icdResult1').style.color='red';
    	document.getElementById('icdResult2').innerHTML='Enter ICD Code';
    	return ;
  }
  if(authType=="PAT"||authType==="PAT"){
	  var preAuthSeqID=document.forms[1].preAuthSeqID.value;
	  if(preAuthSeqID==null||preAuthSeqID===""||preAuthSeqID.length<1){
		  document.getElementById('icdResult1').style.color='red';
	      document.getElementById('icdResult2').innerHTML='Save Pre-Auth Details';
	      return ;
	    }
	  seqId=preAuthSeqID;
  }else if(authType=="CLM"||authType==="CLM"){
	  var claimNo=document.forms[1].claimNo.value;
	  var claimSeqID=document.forms[1].claimSeqID.value;
	  if(claimNo==null||claimNo===""||claimNo.length<1){
		  document.getElementById('icdResult1').style.color='red';
	    	document.getElementById('icdResult2').innerHTML='Save Claim Details';
	    	return ;
	  }
	  seqId=claimSeqID;
  }
	$.ajax({
        url :"/asynchronAction.do?mode=getIcdCodeDetails&authType="+authType+"&icdCode="+icdCode+"&seqID="+seqId,
        dataType:"text",
		 type:"POST",
        success : function(data){
			  //alert(data);
			  var dataArray=data.split("@");
              var rstatus=dataArray[0];
              if(rstatus==="EO"||rstatus.length==2){
              	document.getElementById('icdResult1').style.color='red';
              	document.getElementById('icdResult2').innerHTML='There is some problem while performing operations on the database.<br>Please report this problem to the administrator.';
                }else if(rstatus==="INE"||rstatus.length==3){
              	document.getElementById('icdResult1').style.color='red';
                	document.getElementById('icdResult2').innerHTML='Not Valid';
                 }else if(rstatus==="SUCC"||rstatus.length==4){
              	   document.getElementById('icdResult1').style.color='green';
                   document.getElementById('icdResult2').innerHTML='Valid';
              	 var icdCodeSeqId=dataArray[1];
              	 var icdDescription=dataArray[2];
              	var primary=dataArray[3];
              	document.getElementById('icdCodeSeqId').value=icdCodeSeqId;
              	document.getElementById('diagnosisDescription').value=icdDescription;
              	if(diagSeqId==null||diagSeqId===""||diagSeqId.length<1){
              		document.getElementById('primaryAilment').checked=(primary==null||primary===""||primary==="YES")?false:true;
              	}
              	}//if("SUCC")
        }
	});
 
}//getIcdCodeDetails


function addObservs(activityDtlSeqId1){
	var ss="sdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsdsds";
	var authType=document.forms[1].authType.value;
	var pstatussi="0";
	if(authType==="PAT") pstatussi=document.forms[1].preAuthSeqID.value;
	else if(authType==="CLM")pstatussi=document.forms[1].claimSeqID.value;
	
	var obrurl="/ObservationAction.do?obru="+ss+ss+ss+ss+"&mode=observWindow&activityDtlSeqId="+activityDtlSeqId1+"&pstatussi="+pstatussi+"&authType="+authType;				
	 popupWindow= window.open(obrurl,"OBSERVS","width=950,height=450,left=200,top=100,toolbar=no,scrollbars=no,status=no"); 
	  popupWindow.focus(); 
	  document.onmousedown=focusPopup; 
	  document.onkeyup=focusPopup; 
	  document.onmousemove=focusPopup; 		
	}

function setGPLA(type){
	var re = /^[0-9]*\.*[0-9]*$/;	

	var gravidaObj=document.forms[1].gravida;
	var gravida=gravidaObj.value;
	var paraObj=document.forms[1].para;
	var para=paraObj.value;
	var liveObj=document.forms[1].live;
	var live=liveObj.value;
	var abortionObj=document.forms[1].abortion;
	var abortion=abortionObj.value;
	
	gravida=(gravida==null||gravida===""||!re.test(gravida))?0:gravida;
	para=(para==null||para===""||!re.test(para))?0:para;
	live=(gravida==null||gravida===""||!re.test(live))?0:live;
	abortion=(abortion==null||abortion===""||!re.test(abortion))?0:abortion;
	if(type==="GR"){
	      abortionObj.value="";
	   }else if(type==="PA"){
		   abortionObj.value="";
	if(parseInt(para)>parseInt(gravida)){
      alert("Para Should Not Greater Than Gravida");
      gravidaObj.value='';
      liveObj.value='';
      paraObj.value='';
      abortionObj.value="";
	 }
	}else if(type==="LI"){
		abortionObj.value="";
		if(parseInt(live)>parseInt(para)){
              alert("Live Should Not Greater Than Para");
              gravidaObj.value='';
              liveObj.value='';
              paraObj.value='';
              abortionObj.value="";
		}
	 }else if(type==="AB"){
		 var calVal=parseInt(gravida)-(parseInt(para)+1);
		 if(parseInt(abortion)!=parseInt(calVal)){
	              alert("Abortion Values Are Not Valid");
	              gravidaObj.value='';
	              liveObj.value='';
	              paraObj.value='';
	              abortionObj.value="";
		 }
	  }
	}