var xhttp = new XMLHttpRequest();
function onClose()
  {
	  document.forms[1].mode.value="doClose";
  	document.forms[1].action = "/AddReciverMailID.do";
  	JS_SecondSubmit=true;
  	document.forms[1].submit();
  }
  
  
  function onReset()
  {
	  document.forms[1].mode.value="doReset";
    	document.forms[1].action = "/AddReciverMailID.do";
    	JS_SecondSubmit=true;
    	document.forms[1].submit();
  }
  
  function onSave()
  {	
  	trimForm(document.forms[1]);
  	if(!JS_SecondSubmit){
  		//document.forms[1].mode.value="doSave";changed this for intX
  		document.forms[1].mode.value="doSaveReciverMails";
      	document.forms[1].action = "/SaveReciverMailID.do";
      	JS_SecondSubmit=true;
      	document.forms[1].submit();
     }//end of if(!JS_SecondSubmit)
  }//end of onSave()
  
  function getInsCode(obj)
  {
	  
	  var authority =document.forms[1].regulatoryAuthority.value;
	  alert(authority);
	  if(authority!==""){
		  alert("please select Regulatory Authority");
		  return;
	  }
  	var provname	=	obj.value;
  	if (window.ActiveXObject){
  		xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
  		if (xmlhttp){
  			xmlhttp.open("GET",'/asynchronAction.do?mode=getInsCodeWithAuthority&InsName='+provname+"&authority="+authority ,true);
  			xmlhttp.onreadystatechange=state_ChangeProvider;
  			xmlhttp.send();
  		}
  	}
  	
  }
  
  function state_ChangeProvider(){
		var result,result_arr;
		if (xmlhttp.readyState==4){
			//alert(xmlhttp.status);
		if (xmlhttp.status==200){
					result = xmlhttp.responseText;
				result_arr = result.split(","); 
				
				document.getElementById("officeCode").value=result_arr[0];
				//document.forms[1].hidInsuranceSeqID.value=result_arr[1];
				
			}
		}
	}
  function ConvertToUpperCase(charObj)
  {
      charObj.value=charObj.value.toUpperCase();
  } 
  
  function setInsName(obj){
 	 
 	 var name=obj.id;
 	 var arrdata=name.split("|");
 	    document.getElementById("insuranceCode").value=arrdata[2];
 	    document.getElementById("insuranceName").value=arrdata[1];
 	    var spDiv=document.getElementById("spDivID");
 	    spDiv.innerHTML="";
 	    spDiv.style.display="none";
 	}
  
  
  function onInsuranceSearch(sObj) {
	
		var authority=document.getElementById("authority").value;
		if(authority==null&&authority=="")
			{
			alert("please select Regulatory Authority");
			return
			}
	    var spDiv;
	    spDiv=document.getElementById("spDivID");
	    
	    spDiv.innerHTML="";
	    spDiv.style.display="none";
	   
	    if(sObj.value!==null&&sObj.value!==""){

	if(xhttp!=null)xhttp = new XMLHttpRequest();
	var path="/asynchronAction.do?mode=getInsCodeWithAuthority&InsName="+sObj.value+"&authority="+authority;


	  xhttp.open("POST", path, false);
	  xhttp.send();
	  var sData=xhttp.responseText;

	  if(sData!==null&&sData!==""&&sData.length>1){
	      spDiv.style.display="";
	     
	     // console.log(sData);
	      
	      var arrData=sData.split("#");

	      if(arrData.length>0){
	          var scDiv;

	          for(var i=0;i<arrData.length-1;i++){

	              scDiv = document.createElement('div'),

	          scDiv.className = 'scDivClass';
	            
	              var  idData=arrData[i].split("|");
	           
	        // var funName="setProviderID('"+idData[0]+"','"+idData[1]+"');";   
	              scDiv.id=idData[0]+"|"+idData[1]+"|"+idData[2];
	            scDiv.innerText=idData[1]; 
	            var myfunction=function(){setInsName(this);};
	            
	            //alert("idData[1]"+idData[1]);
	          // scDiv.setAttribute("onclick",funName);
	            scDiv.onclick=myfunction;

	           spDiv.appendChild(scDiv);
	       
	           spDiv.style.position="absolute";
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

