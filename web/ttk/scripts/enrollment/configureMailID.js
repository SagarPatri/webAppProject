function onAdd()
{
	 document.forms[1].action="/AddReciverMailID.do";
	 document.forms[1].mode.value="doAdd";
	 document.forms[1].submit();
}
function onCancel()
{
	//if(!TrackChanges()) return false;
	//document.forms[1].tab.value="DHPO";
    document.forms[1].action="/closeTkakfulEmail.do";
	document.forms[1].submit();
}


function edit(rownum)
{
    document.forms[1].mode.value="doEditReciverMailData";
    //document.forms[1].child.value="";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/AddReciverMailID.do";
    document.forms[1].submit();
}//end of edit(rownum)

function onDeleteIcon(rownum)
{
	document.forms[1].mode.value="doDeleteReciverMail";
    document.forms[1].child.value="Delete";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/MemberUploadAction.do";
    document.forms[1].submit();

}

function toggle(sortid)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearchEmailList";
    document.forms[1].sortId.value=sortid;
    document.forms[1].action = "/MemberUploadAction.do";
    document.forms[1].submit();
}//end of toggle(sortid)

//function to display the selected page
function pageIndex(pagenumber)
{
    document.forms[1].reset();
    document.forms[1].mode.value="doSearchEmailList";
    document.forms[1].pageId.value=pagenumber;
    document.forms[1].action = "/MemberUploadAction.do";
    document.forms[1].submit();
}//end of pageIndex(pagenumber)

function PressBackWard()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doReciverMailListBackward";
    document.forms[1].action = "/MemberUploadAction.do";
    document.forms[1].submit();
}//end of PressBackWard()

//function to display next set of pages
function PressForward()
{
    document.forms[1].reset();
    document.forms[1].mode.value ="doReciverMailListForward";
    document.forms[1].action = "/MemberUploadAction.do";
    document.forms[1].submit();
}//end of PressForward()



function onSearch()
{
   if(!JS_SecondSubmit)
 {
	trimForm(document.forms[1]);
	  
	document.forms[1].mode.value = "doSearchEmailList";
	document.forms[1].action = "/MemberUploadAction.do";
	JS_SecondSubmit=true;
	document.forms[1].submit();
 
 }
 
}//end of onSearch()



function getInsCompany(id1)
{
	var authority=document.getElementById("regulatoryAuthority").value;
	var forwardValue=document.getElementById("forwardValue").value;
	
	var path="/asynchronAction.do?mode=getInsuranceCompany&providerAuthority="+authority+"&forwardValue="+forwardValue;
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
	
	
	}// end of getInsCompany()

