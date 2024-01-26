//as per intX 
var xhttp	=null;
function edit(rownum)
{
	 //document.forms[1].leftlink.value ="Claims";
	document.forms[1].tab.value ="Details";
    document.forms[1].mode.value="doView";
    document.forms[1].rownum.value=rownum;   
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of edit(rownum)



function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearch";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

//function to display previous set of pages
function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doBackward";
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doForward";
    document.forms[1].action = "/OnlineClmSearchHospAction.do";
    document.forms[1].submit();
}//end of PressForward()

function onSearch()
{
   if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	document.forms[1].mode.value = "doSearch";
	document.forms[1].action = "/OnlineClmSearchHospAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
  }//end of if(!JS_SecondSubmit)
}//end of onSearch()


function copyToWebBoard()
{
    if(!mChkboxValidation(document.forms[1]))
    {
	    document.forms[1].mode.value = "doCopyToWebBoard";
   		document.forms[1].action = "/OnlineClmSearchHospAction.do";
	    document.forms[1].submit();
    }//end of if(!mChkboxValidation(document.forms[1]))
}//end of copyToWebBoard()

function onValidateVidalId()
{
	document.forms[1].mode.value = "doValidateEnrollId";
	document.forms[1].action = "/OnlinePreAuthEnrollValidate.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
}//end of onValidateVidalId()

function onUserSubmit()
{
	document.forms[1].tab.value ="Pre-Authorization";
	document.forms[1].sublink.value ="Pre-Authorization";
	document.forms[1].leftlink.value ="Approval Claims";
	document.forms[1].mode.value = "doSaveGeneral";
	document.forms[1].action = "/OnlinePreAuthAction.do";
	document.forms[1].submit();
}

function onAddDiags(obj)
{
	if(document.forms[1].icdCode.value=="")
	{
		alert("Please Enter Diagnosis Code First");
		document.forms[1].icdCode.focus();
		return false;
	}else if(document.forms[1].icdDesc.value=="")
	{
		alert("Please Enter Diagnosis Description");
		document.forms[1].icdDesc.focus();
		return false;
	}
	document.forms[1].mode.value = "doSaveDiags";
	document.forms[1].action = "/OnlinePreAuthAction.do?focusObj="+obj;
	document.forms[1].submit();
}


function deleteDiagnosisDetails(rownum){
 if(confirm("Are You Sure You Want To Delete Diagnosis Details!")){
if(!JS_SecondSubmit){	
	document.forms[1].rownum.value=rownum;
   document.forms[1].mode.value="deleteDiagnosisDetails";
   document.forms[1].action="/OnlinePreAuthAction.do";	
   JS_SecondSubmit=true;	   
   document.forms[1].submit();
 }	
 }	
}

function deleteDrugDetails(rownum){
 if(confirm("Are You Sure You Want To Delete Drug Details!")){
if(!JS_SecondSubmit){	
	document.forms[1].rownum.value=rownum;
   document.forms[1].mode.value="deleteDrugDetails";
   document.forms[1].action="/OnlinePreAuthAction.do";	
   JS_SecondSubmit=true;	   
   document.forms[1].submit();
 }	
 }	
}

function addDiagnosisDetails(){
	if(!JS_SecondSubmit){
	document.forms[1].mode.value="doAddDiagnosisDetails";
    document.forms[1].action = "/OnlinePreAuthAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}

function addActivityDetails(){
	if(!JS_SecondSubmit){
	document.forms[1].mode.value="doAddActivityDetails";
    document.forms[1].action = "/OnlinePreAuthAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}

function addDrugDetails(){
	if(!JS_SecondSubmit){
	document.forms[1].mode.value="doAddDrugDetails";
    document.forms[1].action = "/OnlinePreAuthAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}

function onSaveActivities(){
	if(document.forms[1].activityCode.value=="")
	{
		alert("Please Enter Activity Code First");
		document.forms[1].activityCode.focus();
		return false;
	}else if(document.forms[1].activityCodeDesc.value=="")
	{
		alert("Please Enter Activity Description");
		document.forms[1].activityCodeDesc.focus();
		return false;
	}else if(document.forms[1].activityQuantity.value=="")
	{
		alert("Please Enter Quantity");
		document.forms[1].activityQuantity.focus();
		return false;
	}
	
	if(!JS_SecondSubmit){
	document.forms[1].mode.value="doSaveActivities";
    document.forms[1].action = "/OnlinePreAuthAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}
function onSaveDrugs(){
	if(!JS_SecondSubmit){
		if(document.forms[1].drugCode.value=="")
		{
			alert("Please Enter Drug Code First");
			document.forms[1].drugCode.focus();
			return false;
		}else if(document.forms[1].drugDesc.value=="")
		{
			alert("Please Enter Drug Description");
			document.forms[1].drugDesc.focus();
			return false;
		}else if(document.forms[1].drugdays.value=="")
		{
			alert("Please Enter Days");
			document.forms[1].drugdays.focus();
			return false;
		}else if(document.forms[1].drugUnit.value=="")
		{
			alert("Please Enter Drug Unit");
			document.forms[1].drugUnit.focus();
			return false;
		}else if(document.forms[1].drugquantity.value=="")
		{
			alert("Please Enter Drug Quantity");
			document.forms[1].drugquantity.focus();
			return false;
		}
		
	document.forms[1].mode.value="doSaveDrugs";
    document.forms[1].action = "/OnlinePreAuthAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}
function deleteActivityDetails(rownum){
	 if(confirm("Are You Sure You Want To Delete Activity Details!")){
	if(!JS_SecondSubmit){	
		document.forms[1].rownum.value=rownum;
	   document.forms[1].mode.value="deleteActivityDetails";
	   document.forms[1].action="/OnlinePreAuthAction.do";	
	   JS_SecondSubmit=true;	   
	   document.forms[1].submit();
	 }	
	 }	
	}
function onSaveOnlinePreAuth(){
	if(!JS_SecondSubmit){
	document.forms[1].mode.value="doSaveOnlinePreAuth";
    document.forms[1].action = "/OnlinePreAuthAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}
function onSavePartialPreAuth(){
	if(!JS_SecondSubmit){
	document.forms[1].mode.value="doSavePartialPreAuth";
    document.forms[1].action = "/OnlinePreAuthAction.do";    
	JS_SecondSubmit=true;
	document.forms[1].submit();
	 }
	}

function onSubmitOnlinePreAuth(){
	if(!JS_SecondSubmit){
		document.forms[1].mode.value="doSubmitOnlinePreAuth";
	    document.forms[1].action = "/OnlinePreAuthAction.do";    
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}
}

function calcQtyOnPosology()
{
	var	posology	=	document.forms[1].posology.value;
	var	days		=	document.forms[1].drugdays.value;
	var	drugUnit	=	document.forms[1].drugUnit.value;
	var	qty			=	parseInt(posology)*parseInt(days);
	var	gran		=	document.forms[1].gran.value;
	if("PKG"==drugUnit)
		result	=	Math.ceil(qty/gran);
	else
		result	=	qty;
	document.forms[1].drugquantity.value	=	result;
}

function onEditOnlinePreAuth(){
	if(!JS_SecondSubmit){
		document.forms[1].mode.value="doEditOnlinePreAuth";
	    document.forms[1].action = "/OnlinePreAuthAction.do";    
		JS_SecondSubmit=true;
		document.forms[1].submit();
	}
}

function onExit()
{
	document.forms[1].tab.value ="Check Eligibility";
	document.forms[1].sublink.value ="Check Eligibility";
	document.forms[1].leftlink.value ="Approval Claims";
	
	document.forms[1].mode.value="doDefault";
	document.forms[1].action="/OnlineCashlessHospAction.do";
	document.forms[1].submit();
}

function selectClinician(){
  document.forms[1].mode.value="doDefaultClinician";
  document.forms[1].action="/OnlinePreAuthClinicianAction.do";	
  document.forms[1].submit();	
}

function validateClinician(obj){
	if(obj.value!=""){
	if (window.ActiveXObject){
		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
		if (xmlhttp){
			xmlhttp.open("GET",'/OnlinePreAuthClinicianAction.do?mode=doValidateClinician&clinicianId='+obj.value,true);
			xmlhttp.onreadystatechange=state_ClinicianDetails;
			xmlhttp.send();
		}
	}
	 /* document.forms[1].mode.value="doValidateClinician";
	  document.forms[1].action="/OnlinePreAuthClinicianAction.do";	
	  document.forms[1].submit();	*/
	}
}
function state_ClinicianDetails(){
	var result,result_arr;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split(","); 
			if(result_arr[0]!=""){
				document.forms[1].clinicianName.value=result_arr[1];
				document.forms[1].speciality.value=result_arr[2];
				document.forms[1].consultation.value=result_arr[3];
			}
			else{
				alert("Clinician is not associated with the provider” and we will not process the claim until the clinician gets associated. However we are processing the preauth on your request.");
				document.forms[1].clinicianName.value="";
				document.forms[1].speciality.value="";
				document.forms[1].consultation.value="";
			}
			
		}
	}
}

function onProviderSearch(sObj) {
	alert("sObj.value::"+sObj.value);
	
    //document.getElementById("icdDesc").value="";
    var spDiv;
    spDiv=document.getElementById("spDivID");

    spDiv.innerHTML="";
    spDiv.style.display="none";
    if(sObj.value!==null&&sObj.value!==""){

        //document.forms[1].insuSeqID.value="";
    	if(xhttp==null){
    	if (window.XMLHttpRequest) {
    	    xhttp = new XMLHttpRequest();
    	    } else {
    	    // code for IE6, IE5
    	    xhttp = new ActiveXObject("Microsoft.XMLHTTP");
    	}
    	}
    	
//if(xhttp!=null)xhttp = new XMLHttpRequest();

    var path="/OnlinePreAuthAction.do?mode=doProviderSearch&providerName="+sObj.value;


  xhttp.open("GET", path, false);
  xhttp.send();
  var sData=xhttp.responseText;
  //sData=sData.trim();
  if(sData!==null&&sData!==""&&sData.length>1){
      spDiv.style.display="";


      var arrData=sData.split("|");

      if(arrData.length>0){
          var scDiv;

          for(var i=0;i<arrData.length-1;i++){

              scDiv = document.createElement('div'),

          scDiv.className = 'scDivClass';

              var  idData=arrData[i].split("#");

              var  providerSeqId=idData[0].split("@");
        // var funName="setProviderID('"+idData[0]+"','"+idData[1]+"');";
scDiv.id=idData[1]+"|"+providerSeqId[0]+"|"+providerSeqId[1];
            scDiv.innerText=idData[1];
            var myfunction=function(){setProviderID(this);};

            //alert("idData[1]"+idData[1]);
          // scDiv.setAttribute("onclick",funName);
            scDiv.onclick=myfunction;

           spDiv.appendChild(scDiv);

          }//for(var i=0;i<arrData.length-1;i++){
        }// if(arrData.length>0){
      else{
          document.getElementById("spDivID").style.display="none";
        }
}//if(sData!==null&&sData!==""&&sData.length>1){
     else {
      document.getElementById("spDivID").style.display="none";
      }
  }//if(sObj.value!==null&&sObj.value!==""){
  }//function onInsSearch(sObj) { 




function onProviderSearch10(sObj){
	 document.getElementById("icdDesc").value="";
    if(sObj.value!==null&&sObj.value!==""){
		if (window.XMLHttpRequest) {
    	    xmlhttp= new XMLHttpRequest();
    	    } else if (window.ActiveXObject){
    	    // code for IE6, IE5
    	    xmlhttp= new ActiveXObject("Microsoft.XMLHTTP");
    	}
		
		if (xmlhttp){
			xmlhttp.open("GET",'/OnlinePreAuthClinicianAction.do?mode=doIcdSearch&providerName='+sObj.value+'&type=Code',true);
			xmlhttp.onreadystatechange=state_ClinicianDetails10;
			xmlhttp.send();
		}
	}
	}
function state_ClinicianDetails10(){
	 var spDiv;
	    spDiv=document.getElementById("spDivID");

	    spDiv.innerHTML="";
	    spDiv.style.display="none";
	var result;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split(","); 
			if(result!==null&&result!==""&&result.length>1){
			      spDiv.style.display="";


			      var arrData=result.split("|");

			      if(arrData.length>0){
			          var scDiv;

			          for(var i=0;i<arrData.length-1;i++){

			              scDiv = document.createElement('div'),

			          scDiv.className = 'scDivClass';

			              var  idData=arrData[i].split("#");

			             // var  providerSeqId=idData[0].split("@");
			scDiv.id=idData[0]+"|"+idData[1];
			            scDiv.innerText=idData[0];
			            var myfunction=function(){setProviderID(this,'Code');};

			            scDiv.onclick=myfunction;

			           spDiv.appendChild(scDiv);

			          }//for(var i=0;i<arrData.length-1;i++){
			        }// if(arrData.length>0){
			      else{
			          document.getElementById("spDivID").style.display="none";
			        }
}
			else{
			      document.getElementById("spDivID").style.display="none";
			      }
			
		}
	}
}



function setProviderID(obj,type){
	var spDiv;
    var name=obj.id;
    var arrdata=name.split("|");
    if(type=="Code"){
       document.getElementById("icdDesc").value=arrdata[1];
       document.getElementById("icdCode").value=arrdata[0];
       spDiv=document.getElementById("spDivID");
    }else if(type=="Desc"){
        document.getElementById("icdDesc").value=arrdata[0];
        document.getElementById("icdCode").value=arrdata[1];
        spDiv=document.getElementById("icdDescDivID");
     }else if(type=="activityCode"){
         document.getElementById("activityCodeDesc").value=arrdata[1];
         document.getElementById("activityCode").value=arrdata[0];
         spDiv=document.getElementById("activityCodeDivID");
      }else if(type=="activityDesc"){
          document.getElementById("activityCodeDesc").value=arrdata[0];
          document.getElementById("activityCode").value=arrdata[1];
          spDiv=document.getElementById("activityDescDivID");
       }else if(type=="drugCode"){
    	   var	arrDrugCode	=	arrdata[1].split("@@");
    	   document.getElementById("drugDesc").value=arrdata[1];
    	   document.getElementById("gran").value=arrDrugCode[1];//to get Granular UNit for posology calculation
           document.getElementById("drugCode").value=arrdata[0];
           spDiv=document.getElementById("drugCodeDivID");
        }else if(type=="drugDesc"){
        	 document.getElementById("drugDesc").value=arrdata[0];
             document.getElementById("drugCode").value=arrdata[1];
             spDiv=document.getElementById("drugDescDivID");
        }
    
       //document.getElementById("providerSeqId").value=arrdata[1];

       spDiv.innerHTML="";
       spDiv.style.display="none";
   } 



//ICD DESC
function onIcdDescSearch(sObj){
	document.getElementById("icdCode").value="";
    if(sObj.value!==null&&sObj.value!==""){
		if (window.XMLHttpRequest) {
    	    xmlhttp= new XMLHttpRequest();
    	    } else if (window.ActiveXObject){
    	    // code for IE6, IE5
    	    xmlhttp= new ActiveXObject("Microsoft.XMLHTTP");
    	}
		if (xmlhttp){
			xmlhttp.open("GET",'/OnlinePreAuthClinicianAction.do?mode=doIcdSearch&providerName='+sObj.value+'&type=Desc',true);
			xmlhttp.onreadystatechange=state_IcdDescSearch;
			xmlhttp.send();
		}
	}
	}
function state_IcdDescSearch(){
	 var spDiv;
	    spDiv=document.getElementById("icdDescDivID");

	    spDiv.innerHTML="";
	    spDiv.style.display="none";
	var result;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split(","); 
			if(result!==null&&result!==""&&result.length>1){
			      spDiv.style.display="";


			      var arrData=result.split("|");

			      if(arrData.length>0){
			          var scDiv;

			          for(var i=0;i<arrData.length-1;i++){

			              scDiv = document.createElement('div'),

			          scDiv.className = 'scDivClass';

			              var  idData=arrData[i].split("#");

			             // var  providerSeqId=idData[0].split("@");
			scDiv.id=idData[0]+"|"+idData[1];
			            scDiv.innerText=idData[0];
			            var myfunction=function(){setProviderID(this,'Desc');};

			            scDiv.onclick=myfunction;

			           spDiv.appendChild(scDiv);

			          }//for(var i=0;i<arrData.length-1;i++){
			        }// if(arrData.length>0){
			      else{
			          document.getElementById("icdDescDivID").style.display="none";
			        }
}
			else{
			      document.getElementById("icdDescDivID").style.display="none";
			      }
			
		}
	}
}



//ACTIVITY CODE

function onActivityCodeSearch(sObj){
	document.getElementById("activityCodeDesc").value="";
    if(sObj.value!==null&&sObj.value!==""){
		if (window.XMLHttpRequest) {
    	    xmlhttp= new XMLHttpRequest();
    	    } else if (window.ActiveXObject){
    	    // code for IE6, IE5
    	    xmlhttp= new ActiveXObject("Microsoft.XMLHTTP");
    	}
		if (xmlhttp){
			xmlhttp.open("GET",'/OnlinePreAuthClinicianAction.do?mode=doIcdSearch&providerName='+sObj.value+'&type=activityCode',true);
			xmlhttp.onreadystatechange=state_ActivityCodeSearch;
			xmlhttp.send();
		}
	}
	}
function state_ActivityCodeSearch(){
	 var spDiv;
	    spDiv=document.getElementById("activityCodeDivID");

	    spDiv.innerHTML="";
	    spDiv.style.display="none";
	var result;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split(","); 
			if(result!==null&&result!==""&&result.length>1){
			      spDiv.style.display="";


			      var arrData=result.split("|");

			      if(arrData.length>0){
			          var scDiv;

			          for(var i=0;i<arrData.length-1;i++){

			              scDiv = document.createElement('div'),

			          scDiv.className = 'scDivClass';

			              var  idData=arrData[i].split("#");

			             // var  providerSeqId=idData[0].split("@");
			scDiv.id=idData[0]+"|"+idData[1];
			            scDiv.innerText=idData[0];
			            var myfunction=function(){setProviderID(this,'activityCode');};

			            scDiv.onclick=myfunction;

			           spDiv.appendChild(scDiv);

			          }//for(var i=0;i<arrData.length-1;i++){
			        }// if(arrData.length>0){
			      else{
			          document.getElementById("activityCodeDivID").style.display="none";
			        }
}
			else{
			      document.getElementById("activityCodeDivID").style.display="none";
			      }
			
		}
	}
}


//ACTIVITY DESC

function onActivityDescSearch(sObj){
	document.getElementById("activityCode").value="";
   if(sObj.value!==null&&sObj.value!==""){
		if (window.XMLHttpRequest) {
    	    xmlhttp= new XMLHttpRequest();
    	    } else if (window.ActiveXObject){
    	    // code for IE6, IE5
    	    xmlhttp= new ActiveXObject("Microsoft.XMLHTTP");
    	}
		if (xmlhttp){
			xmlhttp.open("GET",'/OnlinePreAuthClinicianAction.do?mode=doIcdSearch&providerName='+sObj.value+'&type=activityDesc',true);
			xmlhttp.onreadystatechange=state_ActivityDescSearch;
			xmlhttp.send();
		}
	}
	}
function state_ActivityDescSearch(){
	 var spDiv;
	    spDiv=document.getElementById("activityDescDivID");

	    spDiv.innerHTML="";
	    spDiv.style.display="none";
	var result;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split(","); 
			if(result!==null&&result!==""&&result.length>1){
			      spDiv.style.display="";


			      var arrData=result.split("|");

			      if(arrData.length>0){
			          var scDiv;

			          for(var i=0;i<arrData.length-1;i++){

			              scDiv = document.createElement('div'),

			          scDiv.className = 'scDivClass';

			              var  idData=arrData[i].split("#");

			             // var  providerSeqId=idData[0].split("@");
			scDiv.id=idData[0]+"|"+idData[1];
			            scDiv.innerText=idData[0];
			            var myfunction=function(){setProviderID(this,'activityDesc');};

			            scDiv.onclick=myfunction;

			           spDiv.appendChild(scDiv);

			          }//for(var i=0;i<arrData.length-1;i++){
			        }// if(arrData.length>0){
			      else{
			          document.getElementById("activityDescDivID").style.display="none";
			        }
}
			else{
			      document.getElementById("activityDescDivID").style.display="none";
			      }
			
		}
	}
}


//DRUG CODE

function onDrugCodeSearch(sObj){
	// document.getElementById("drugDesc").value="";
    if(sObj.value!==null&&sObj.value!==""){
		if (window.XMLHttpRequest) {
    	    xmlhttp= new XMLHttpRequest();
    	    } else if (window.ActiveXObject){
    	    // code for IE6, IE5
    	    xmlhttp= new ActiveXObject("Microsoft.XMLHTTP");
    	}
		if (xmlhttp){
			xmlhttp.open("GET",'/OnlinePreAuthClinicianAction.do?mode=doIcdSearch&providerName='+sObj.value+'&type=drugCode',true);
			xmlhttp.onreadystatechange=state_DrugCodeSearch;
			xmlhttp.send();
		}
	}
	}
function state_DrugCodeSearch(){
	 var spDiv;
	    spDiv=document.getElementById("drugCodeDivID");

	    spDiv.innerHTML="";
	    spDiv.style.display="none";
	var result;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split(","); 
			if(result!==null&&result!==""&&result.length>1){
			      spDiv.style.display="";


			      var arrData=result.split("|");

			      if(arrData.length>0){
			          var scDiv;

			          for(var i=0;i<arrData.length-1;i++){

			              scDiv = document.createElement('div'),

			              scDiv.className = 'scDivClass';

			              var  idData=arrData[i].split("#");

			             // var  providerSeqId=idData[0].split("@");
			            scDiv.id=idData[0]+"|"+idData[1];
			            scDiv.innerText=idData[0];
			            var myfunction=function(){setProviderID(this,'drugCode');};

			            scDiv.onclick=myfunction;

			           spDiv.appendChild(scDiv);

			          }//for(var i=0;i<arrData.length-1;i++){
			        }// if(arrData.length>0){
			      else{
			          document.getElementById("drugCodeDivID").style.display="none";
			        }
			}
			else{
			      document.getElementById("drugCodeDivID").style.display="none";
			      }
			
		}
	}
}
//DRUG DESC

function onDrugDescSearch(sObj){
	// document.getElementById("drugCode").value="";
    if(sObj.value!==null&&sObj.value!==""){
		if (window.XMLHttpRequest) {
    	    xmlhttp= new XMLHttpRequest();
    	    } else if (window.ActiveXObject){
    	    // code for IE6, IE5
    	    xmlhttp= new ActiveXObject("Microsoft.XMLHTTP");
    	}
		if (xmlhttp){
			xmlhttp.open("GET",'/OnlinePreAuthClinicianAction.do?mode=doIcdSearch&providerName='+sObj.value+'&type=drugDesc',true);
			xmlhttp.onreadystatechange=state_DrugDescSearch;
			xmlhttp.send();
		}
	}
	}
function state_DrugDescSearch(){
	 var spDiv;
	    spDiv=document.getElementById("drugDescDivID");

	    spDiv.innerHTML="";
	    spDiv.style.display="none";
	var result;
	if (xmlhttp.readyState==4){
		//alert(xmlhttp.status);
	if (xmlhttp.status==200){
				result = xmlhttp.responseText;
			result_arr = result.split(","); 
			if(result!==null&&result!==""&&result.length>1){
			      spDiv.style.display="";


			      var arrData=result.split("|");

			      if(arrData.length>0){
			          var scDiv;

			          for(var i=0;i<arrData.length-1;i++){

			              scDiv = document.createElement('div'),

			              scDiv.className = 'scDivClass';

			              var  idData=arrData[i].split("#");

			             // var  providerSeqId=idData[0].split("@");
			            scDiv.id=idData[0]+"|"+idData[1];
			            scDiv.innerText=idData[0];
			            var myfunction=function(){setProviderID(this,'drugDesc');};

			            scDiv.onclick=myfunction;

			           spDiv.appendChild(scDiv);

			          }//for(var i=0;i<arrData.length-1;i++){
			        }// if(arrData.length>0){
			      else{
			          document.getElementById("drugDescDivID").style.display="none";
			        }
			}
			else{
			      document.getElementById("drugDescDivID").style.display="none";
			      }
			
		}
	}
}

function callFocusObj(fieldID){
	var focusObj=document.getElementById(fieldID);
	//var focusVal	=	focusObj.value;
	if(focusObj !=null&&focusObj!=undefined)
		focusObj.focus();
}

function showUplodedFile(rownum)
{
	
	var openPage = "/OnlineReportsAction.do?mode=doViewCommonForAll&module=preAuthorizationUploadedFile&rownum="+rownum;
	   var w = screen.availWidth - 10;
	   var h = screen.availHeight - 49;
	   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
	   window.open(openPage,'',features);
}

function onUploadDocs()
{
	document.forms[1].mode.value="doDefaultUploadDocs";
	var lPreAuthIntSeqId	=	document.forms[1].lPreAuthIntSeqId.value;
	document.forms[1].action="/UploadMOUCertificatesList.do?lPreAuthIntSeqId="+lPreAuthIntSeqId+"&preAuthNoYN="+document.forms[1].preAuthNoYN.value;
	document.forms[1].submit();
}

var  popupWindow=null;	
function addProviderObserv(rownum,type)
{
		var preAuthSeqId=document.forms[1].lPreAuthIntSeqId.value;
		
		var obrurl="/ProviderObservAction.do?mode=providerObservWindow&preAuthSeqId="+preAuthSeqId+"&activityRowNum="+rownum+"&type="+type;			
	 	popupWindow= window.open(obrurl,"OBSERVS","width=950,height=450,left=200,top=100,toolbar=no,scrollbars=yes,status=no"); 
	 	popupWindow.focus(); 
	 	document.onmousedown=focusPopup; 
	 	document.onkeyup=focusPopup; 
	 	document.onmousemove=focusPopup;
}


function focusPopup() {
	  if(popupWindow && !popupWindow.closed) { popupWindow.focus(); } 
	}
