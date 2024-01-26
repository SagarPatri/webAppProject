//get the emirate id description
function getEmirateIdDescription(){  
    var name2 = document.forms[1].emirateId.value;	
 var path="/asynchronAction.do?mode=getEmirateIdDescription&eId="+name2;
  
 $.ajax({
     url :path,
     dataType:"HTML",
     success : function(data) {               
         document.forms[1].emirateId.title=data;
     }
 });	
}

//get the isd and std codes
function getIsdOrStd(path,id1){
	$.ajax({
	     url :path,
	     dataType:"text",
	     success : function(data) {
		     if(!(data==null||data=="")){
		     if(id1==="isd"){
		         document.getElementById("isdCode1").value=data;
		         document.getElementById("isdCode1").readOnly=true;
		         document.getElementById("isdCode2").value=data;
		         document.getElementById("isdCode2").readOnly=true;		         
		         document.getElementById("isdCode3").value=data;
		         document.getElementById("isdCode3").readOnly=true;		         
		         document.getElementById("isdCode4").value=data;
		         document.getElementById("isdCode4").readOnly=true;
		         
		     }else{
		    	 document.getElementById("stdCode1").value=data;
		    	 document.getElementById("stdCode1").readOnly=true;
		         document.getElementById("stdCode2").value=data;
		         document.getElementById("stdCode2").readOnly=true;
		         document.getElementById("stdCode3").value=data;
		         document.getElementById("stdCode3").readOnly=true;
			     } 
	     }              
	     }
	 });	
}

//get the states and cities
function getStates(name1,id1){
	
	 var myselect1=document.getElementById("cityCode");
	 while (myselect1.hasChildNodes()) {   
    	    myselect1.removeChild(myselect1.firstChild);
      }
	 myselect1.options.add(new Option("Select from list",""));	 
		
    document.getElementById("stdCode1").value="STD";
    document.getElementById("stdCode2").value="STD";
    document.getElementById("stdCode3").value="STD";
    document.getElementById("stdCode1").readOnly=false;
    document.getElementById("stdCode2").readOnly=false;
    document.getElementById("stdCode3").readOnly=false;
   
    var path;
    if(name1==="state"){
        var countryId= document.getElementById("cnCode").value;
         path="/asynchronAction.do?mode=getStates&sorc=states&countryId="+countryId;
         getIsdOrStd("/asynchronAction.do?mode=getIsdOrStd&iors=ISD&countryId="+countryId,"isd");        
    }
    else{
    	var stateId= document.getElementById("stateCode").value;
         path="/asynchronAction.do?mode=getAreas&sorc=cities&stateId="+stateId;
         getIsdOrStd("/asynchronAction.do?mode=getIsdOrStd&iors=STD&stateId="+stateId,"std");        
    }

    $(document).ready(function(){ 
   	 $.ajax({
   	     url :path,
   	     dataType:"text",
   	     success : function(data) {
   	   	    
   	      var myselect2=document.getElementById(id1);

   	      while (myselect2.hasChildNodes()) {   
   	    	    myselect2.removeChild(myselect2.firstChild);
   	      }
   	   myselect2.options.add(new Option("Select from list",""));             
   	     var res1 = data.split("&");
   	     
   	     for(var i=0;i<res1.length;i++){   	    	    
   	     var res2=res1[i].split("@");
   	        myselect2.options.add(new Option(res2[1],res2[0]));  	                 
   	     }
  	      
   	     }
   	 });

   	});	

}