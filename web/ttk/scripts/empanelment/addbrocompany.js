//java script for the insurance company screen in the empanelment of insurance flow.

function onSave()
{
	
	var startDate =document.getElementById("empanelmentDate").value;
    var endDate=document.getElementById("regDate").value;
    
    if( !((document.getElementById("empanelmentDate").value)==="") && !((document.getElementById("regDate").value)===""))
   	{
        var sdate = startDate.split("/");
      	var altsdate=sdate[1]+"/"+sdate[0]+"/"+sdate[2];
        altsdate=new Date(altsdate);
        
        var edate =endDate.split("/");
        var altedate=edate[1]+"/"+edate[0]+"/"+edate[2];
        altedate=new Date(altedate);
        
        var Startdate = new Date(altsdate);
        var EndDate =  new Date(altedate);
        
        if(EndDate > Startdate)
       	 {
       	 	alert("Request Received Date should be less than or equal to System Added Date.");
       		document.getElementById("regDate").value="";
       		document.getElementById("regDate").focus();
       		return ;
       	 }
   	} 
	
	if(document.getElementById("phoneNbr1").value=="Phone No1")
		document.getElementById("phoneNbr1").value	=	"";
	if(document.getElementById("phoneNbr2").value=="Phone No2")
		document.getElementById("phoneNbr2").value	=	"";
	
 if(!JS_SecondSubmit)
 {	
	trimForm(document.forms[1]);
	document.forms[1].mode.value="doSave";
	document.forms[1].action="/UpdateBroCompanyAction.do";
	JS_SecondSubmit=true
	document.forms[1].submit();
 }//end of if(!JS_SecondSubmit)	
}//end of onSave()

function onReset()
{
	if(typeof(ClientReset)!= 'undefined' && !ClientReset) 
	{
		if(document.forms[1].insuranceSeqID.value=="")
			document.forms[1].mode.value="doAdd";
		else
			document.forms[1].mode.value="doView";
			document.forms[1].action="/AddEditBroCompanyAction.do";
			document.forms[1].submit();
	}//end of if(typeof(ClientReset)!= 'undefined' && !ClientReset)
	else
	{
		document.forms[1].reset();
	}//end of else
}//end of onReset()

function onClose()
{
	if(!TrackChanges()) return false;

	if(document.forms[1].officeType.value == "IHO" && document.forms[1].insuranceSeqID.value=="")
	{
		document.forms[1].mode.value="doClose";
		document.forms[1].child.value="";
		document.forms[1].tab.value="Search";
		document.forms[1].action="/EmpBrokerAction.do";
		document.forms[1].submit();
	}//end of if(document.forms[1].officeType.value == "IHO" && document.forms[1].insuranceSeqID.value=="")
	else
	{
		document.forms[1].mode.value="doViewCompanySummary";
		document.forms[1].child.value="";
		document.forms[1].action="/BrokerDetailAction.do";
		document.forms[1].submit();
	}//end of else
}//end of onClose()

function onChangeState()
{
	// var brokerAuthority= document.getElementById("regAuthority").value;
	//var resss=res1;
		//var  myselect12=document.getElementById("companyName").value;
    	document.forms[1].mode.value="doChangeState";
    	document.forms[1].focusID.value="state";
    	document.forms[1].action="/AddEditBroCompanyAction.do";
    	document.forms[1].submit();
    	//res1=resss;
    	/*var  myselect1=document.getElementById("companyName");
		while (myselect1.hasChildNodes()) {   
	   	    myselect1.removeChild(myselect1.firstChild);
	     }*/
		//alert(myselect1+"--"+brokerAuthority);
		/* myselect1.options.add(new Option("Select hg from list",""));
		 myselect1.options.add(new Option("Select hg frffffffffom list",""));
    	alert("onChangeState===="+res1);
    	for(var i=0;i<res1.length-1;i++){   	    	    
	 	     var res2=res1[i].split("@");
	 	        myselect1.options.add(new Option(res2[1],res2[0])); 
	 	     }//for()
    	 myselect1.options.add(new Option("Selsdect hg from list",""));
    	myselect1.value=myselect12;*/
    	//getBrokerNames(myselect1,brokerAuthority);
}//end of onChangeState()

function getMe(obj)
{
	//alert(obj);
	if(obj=='Phone No1')
	{
		if(document.getElementById("phoneNbr1").value=="")
			document.getElementById("phoneNbr1").value=obj;
		
	}else if(obj=='Phone No2')
	{
		if(document.getElementById("phoneNbr2").value=="")
			document.getElementById("phoneNbr2").value=obj;
	}
}

function changeMe(obj)
{
	var val	=	obj.value;
	//alert(val);
	if(val=='Phone No1')
	{
		document.getElementById("phoneNbr1").value="";
	}
	if(val=='Phone No2')
	{
		document.getElementById("phoneNbr2").value="";
	}
}


function getBrokerNames(selectedvalue,brokerNames)
{
	
	 var brokerAuthority=document.getElementById("regAuthority").value;
		var  myselect1=document.getElementById("companyName");
		while (myselect1.hasChildNodes()) {   
	   	    myselect1.removeChild(myselect1.firstChild);
	     }
		 myselect1.options.add(new Option("Select from list",""));
		 
	      var  path="/AsynchronousAction.do?mode=getBrokerNames&brokerAuthority="+brokerAuthority;		                 

	 	 $.ajax({
	 		 cache:false,
	 	     url :path,
	 	     dataType:"text",
	 	     success : function(data) {
	 	      res1 = data.split("&");
	 	     for(var i=0;i<res1.length-1;i++){   	    	    
	 	     var res2=res1[i].split("@");
	 	        myselect1.options.add(new Option(res2[1],res2[0])); 
	 	        
	 	     }//for()
	 	     },//function(data)
	 	 error:function(res){
	 		 alert(err);
	 	 }
	 	 });
	
}


function OnSelectInput(){
	var  brokerCode=document.getElementById("brokerName").value;
	 document.forms[1].companyCode.value=brokerCode;
	
}











function onChangeBrokerAuth(){
	document.forms[1].companyName.value="";
	document.forms[1].companyCode.value="";
	document.forms[1].action="/ChangeBrokerAuth.do";
	document.forms[1].submit();

}

/*function endDateValidation()
{

	alert("inside endDateValidation");
	var startDate =document.getElementById("empanelmentDate").value;
    var endDate=document.getElementById("regDate").value;
    
    if( !((document.getElementById("empanelmentDate").value)==="") && !((document.getElementById("regDate").value)===""))
   	{
        var sdate = startDate.split("/");
      	var altsdate=sdate[1]+"/"+sdate[0]+"/"+sdate[2];
        altsdate=new Date(altsdate);
        
        var edate =endDate.split("/");
        var altedate=edate[1]+"/"+edate[0]+"/"+edate[2];
        altedate=new Date(altedate);
        
        var Startdate = new Date(altsdate);
        var EndDate =  new Date(altedate);
        
        if(EndDate > Startdate)
       	 {
       	 	alert("Request Received Date should be less than or equal to System Added Date.");
       		document.getElementById("regDate").value="";
       		document.getElementById("regDate").focus();
       		return ;
       	 }
   	} 
}*/



function changeBrokerName()
{
	
	document.getElementById("brokerName").value=document.getElementById("companyCode").value;
}