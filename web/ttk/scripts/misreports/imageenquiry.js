
//imageenquiry.js is called from the imageenquiry.jsp in Image Enquiry flow

function onSearch()
{
    
	if(!JS_SecondSubmit)
	 {
		trimForm(document.forms[1]);
		//var searchFlag=false;
		//var regexp=new RegExp("^(search){1}[0-9]{1,}$");
		var policyNbr = document.forms[1].policyNbr.value;
		var enrollmentNbr= document.forms[1].enrollmentNbr.value;
     	var enrollmentId = document.forms[1].enrollmentID.value;
		var memberName = document.forms[1].memberName.value;
 		var corporateName = document.forms[1].corporateName.value;
 		var groupId = document.forms[1].groupId.value;
		var pType = document.getElementById("policyType").value;
		
		/*if((pType!='Select') && ( policyNbr !="" || enrollmentNbr !="" || enrollmentId !="" || memberName !="" || corporateName !="" || groupId !=""))
		{
			searchFlag = true;
		}*/
		if((pType=="") && ( policyNbr =="" && enrollmentNbr =="" && enrollmentId =="" && memberName =="" && corporateName =="" && groupId ==""))
		{
			alert("Please select Scheme Type and atleast one of the remaining search criteria");
			return false;
		}
		
		else if((pType=="") && ( policyNbr !="" || enrollmentNbr !="" || enrollmentId !="" || memberName !="" || corporateName !="" || groupId !=""))
		{
			alert("Please select Scheme Type");
			return false;
		}
		
		else if((pType!="") && ( policyNbr =="" && enrollmentNbr =="" && enrollmentId =="" && memberName =="" && corporateName =="" && groupId ==""))
		{
			alert("Please enter any one from :- Scheme No, Enrollment No, Enrollment ID, Member Name, Corp. Name, Group Id ");
			return false;
		}
		/*for(i=0;i<document.forms[1].length;i++)
		{
			//if(regexp.test(document.forms[1].elements[i].id) && document.forms[1].elements[i].value!="")
			if(document.forms[1].elements[i].value!="")
			{
				searchFlag=true;
				break;
			}
		}*/
		/*if(searchFlag==false)
		{
			alert("Please enter atleast one search criteria");
			return false;
		}*/
		document.forms[1].mode.value = "doSearch";
	    document.forms[1].action = "/ImageEnquiryAction.do";
		JS_SecondSubmit=true
	    document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)
}//end of onSearch()