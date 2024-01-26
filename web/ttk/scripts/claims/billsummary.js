

function onViewBillDetails()
{
	document.forms[1].child.value="";
	document.forms[1].mode.value="doViewBillDetails";
	document.forms[1].action="/BillSummaryAction.do";
	document.forms[1].submit();
}//end of onViewBillDetails()

function onSave()
{
	trimForm(document.forms[1]);
	calcAilAppAmt();
	setTimeout("onUserSubmit()", 900);   
}//end of onSave()

function calNetAmt()
{  
	var totAppAmt=0;
	regexp=/^([0])*\d{0,10}(\.\d{1,2})?$/;
	var obj=document.forms[1].elements['applyDiscountYN'];
	var obj1=document.forms[1].elements['claimNetAmount'];
	var obj2=document.forms[1].elements['claimBillAmt'];
	var obj3=document.forms[1].elements['discountPercent'];
	var obj4=document.forms[1].elements['hideDiscountYN'];
	var obj5=document.forms[1].elements['claimMaxAmount'];
	
	if(document.forms[1].selectedWardAccGroupSeqID.length)
	{			
		for(i=0,j=0;i<obj.length;i++)
		{			
			if(obj2[i].value=="")
				obj2[i].value="0.00";
		    else
	          	obj2[i].value=parseFloat(obj2[i].value).toFixed(2);
			if(obj5[i].value=="")
				obj5[i].value="0.00";
		    else
	          	obj5[i].value=parseFloat(obj5[i].value).toFixed(2);
			if(!(obj[i].checked))
            {
				obj1[i].value=parseFloat(obj2[i].value).toFixed(2);								
				obj4[i].value='N';				
            }//end of if(!(obj[i].checked))

            if((obj[i].checked))
            {
            	obj1[i].value= parseFloat(((obj2[i].value))-((obj2[i].value)*(obj3[i].value))/100).toFixed(2);            	
            	obj4[i].value='Y';            	
            }//end of if((obj[j].checked))
       }//end of for(i=0;i<obj.length;i++)
    }//end of if(document.forms[1].selectedWardAccGroupSeqID.length)
    else
    {    	
    	if(obj2.value=="")
			obj2.value="0.00";
	    else
          	obj2.value=parseFloat(obj2.value).toFixed(2);
    	if(obj5.value=="")
			obj5.value="0.00";
	    else
          	obj5.value=parseFloat(obj5.value).toFixed(2);
    	if(!(obj.checked))
        {
    		obj1.value=parseFloat(obj2.value).toFixed(2);    		
            obj4.value='N';            
        }//end of if(!(obj.checked))

        if((obj.checked))
        {
        	obj1.value= parseFloat(((obj2.value))-((obj2.value)*(obj3.value))/100).toFixed(2);        	
            obj4.value='Y';
        }//end of if((obj.checked))
    }
    calTotNet();
}//end of function calNetAmt()

function calTotBill()
{
	var totBill=0;
	regexp=/^([0])*\d{0,10}(\.\d{1,2})?$/;
	if(document.forms[1].selectedWardAccGroupSeqID.length)
	{
		var obj2=document.forms[1].elements['claimBillAmt'];
		for(i=0;i<obj2.length;i++)
		{
			if(obj2[i].value!="" && regexp.test(obj2[i].value))
				totBill=totBill+parseFloat(obj2[i].value);
		}
	}
	else
	{
		var obj2=document.forms[1].elements['claimBillAmt'];
		if(obj2.value!="" && regexp.test(obj2.value))
			totBill=totBill+parseFloat(obj2.value);
	}
	document.forms[1].totBillAmt.value=parseFloat(totBill).toFixed(2);
}//end of function calTotBill()

function calTotNet()
{
	var totNet=0;
	regexp=/^([0])*\d{0,10}(\.\d{1,2})?$/;
	if(document.forms[1].selectedWardAccGroupSeqID.length)
	{
		var obj1=document.forms[1].elements['claimNetAmount'];
		for(i=0;i<obj1.length;i++)
		{
			if(obj1[i].value!="" && regexp.test(obj1[i].value))
				totNet=totNet+parseFloat(obj1[i].value);			
		}//end of for
	}//end of if(document.forms[1].selectedWardAccGroupSeqID.length)
	else
	{
		var obj1=document.forms[1].elements['claimNetAmount'];
		if(obj1.value!="" && regexp.test(obj1.value))
			totNet=totNet+parseFloat(obj1.value);
	}
	totNet = Math.round(totNet);
	totNet=totNet.toFixed(2);
	document.forms[1].totNetAmt.value=parseFloat(totNet).toFixed(2);
}//end of function calTotNet()

function calTotMax()
{
	var totMax=0;
	regexp=/^([0])*\d{0,10}(\.\d{1,2})?$/;
	if(document.forms[1].selectedWardAccGroupSeqID.length)
	{
		var obj1=document.forms[1].elements['claimMaxAmount'];
		for(i=0;i<obj1.length;i++)
		{
			if(obj1[i].value!="" && regexp.test(obj1[i].value))
			{
				totMax=totMax+parseFloat(obj1[i].value);
			}
		}
	}
	else
	{
		var obj1=document.forms[1].elements['claimMaxAmount'];
		if(obj1.value!="" && regexp.test(obj1.value))
			totMax=totMax+parseFloat(obj1.value);
	}
	document.forms[1].totMaxAmt.value=parseFloat(totMax).toFixed(2);
}//end of function calTotMax()

function calcAilAppAmt()
{
	var totAilAppAmt;
	regexp=/^([0])*\d{0,10}(\.\d{1,2})?$/;    
	if(document.forms[1].selectedICDPCSSeqID !=null)
	{
		totAilAppAmt =0;
		if(document.forms[1].selectedICDPCSSeqID.length)
		{
			var obj=document.forms[1].elements['approvedAilmentAmt'];
			for(i=0;i<obj.length;i++)
			{
				if(obj[i].value!="" && regexp.test(obj[i].value))
					totAilAppAmt= totAilAppAmt + parseFloat(obj[i].value);
			}
		}
		else
		{
			var obj=document.forms[1].elements['approvedAilmentAmt'];
			if(obj.value!="" && regexp.test(obj.value))
				totAilAppAmt= totAilAppAmt + parseFloat(obj.value);
		}
	}	
    document.forms[1].totSumNetAmt.value=parseFloat(totAilAppAmt).toFixed(2);
}//end of function calcAilAppAmt()

function onUserSubmit()
{
	var regexp=new RegExp("^(approvedAilmentAmt){1}[0-9]{1,}$");    
	var bigdecimal=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.[1-9]{1}\d?$|^\s*$/; 
	var approvedAilmentAmt=0;
	for(i=0;i<document.forms[1].length;i++)
	{
		if(regexp.test(document.forms[1].elements[i].id))
		{
			id=document.forms[1].elements[i].id;				
			ailCnt=id.substr(id.lastIndexOf('t')+1,id.length);								
			if(document.forms[1].elements[i].value.length>0)
			{
				if(bigdecimal.test(document.forms[1].elements[i].value)==false)
				{
					alert('Ailment Approved Amount should be 10 digits followed by 2 decimal places.');						
					document.forms[1].elements[i].select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)					
				approvedAilmentAmt=approvedAilmentAmt + parseFloat(document.forms[1].elements[i].value);
			}//end of if(document.forms[1].elements[i].value.length>0)				
			var regexp1=new RegExp("^(procedureAmt"+ailCnt+"~){1}[0-9]{1,}$");
			var procedureAmount=0;
			for(j=0;j<document.forms[1].length;j++)
			{
				if(regexp1.test(document.forms[1].elements[j].id))
				{
					id1=document.forms[1].elements[j].id;
					//document.forms[1].elements[j].value=trim(document.forms[1].elements[j].value);
						
					if(document.forms[1].elements[j].value.length>0)
					{
						if(bigdecimal.test(document.forms[1].elements[j].value)==false)
						{
							alert('Procedure Approved Amount should be 10 digits followed by 2 decimal places.');						
							document.forms[1].elements[j].select();																		
							return false;
						}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
						procedureAmount+=parseFloat(document.forms[1].elements[j].value);	
					}//end of if(document.forms[1].elements[j].value=="")
				}//end of if(regexp1.test(document.forms[1].elements[j].id))			
			}//end of for(j=0;j<document.forms[1].length;j++)
			if(procedureAmount!= 0 && procedureAmount > document.forms[1].elements[i].value)
			{
				alert('Ailment Amount should be greater than Sum of Procedure Amount.');						
				document.forms[1].elements[i].select();																		
				return false;
			}
		}//end of if(regexp.test(objform.elements[i].id))
	}//end of for(i=0;i<objform.length;i++)
	if(parseFloat(document.forms[1].totNetAmt.value)!=parseFloat(approvedAilmentAmt))
	{
		alert('Total Net Amount. should be equal to Total Ailment Appr. Amt.');
		return false;
	}
	if(!JS_SecondSubmit)
	{
		document.forms[1].mode.value="doSubmit";
		document.forms[1].action="/BillSummaryAction.do";
		JS_SecondSubmit=true
		document.forms[1].submit();
	}//end of if(!JS_SecondSubmit)  
}//end of function onUserSubmit()

function calToBillSplitUp()
{
	var VpreHospitalization = document.forms[1].preHospitalization.value;
	var Vhospitalization = document.forms[1].hospitalization.value;
	var VpostHospitalization = document.forms[1].postHospitalization.value;
	if(VpreHospitalization=="")
		document.forms[1].preHospitalization.value="0.00";
    else
    	document.forms[1].preHospitalization.value=parseFloat(VpreHospitalization).toFixed(2);
	if(Vhospitalization=="")
		document.forms[1].hospitalization.value="0.00";
    else
    	document.forms[1].hospitalization.value=parseFloat(Vhospitalization).toFixed(2);
	if(VpostHospitalization=="")
		document.forms[1].postHospitalization.value="0.00";
    else
    	document.forms[1].postHospitalization.value=parseFloat(VpostHospitalization).toFixed(2);
	
	if(document.forms[1].selectedICDPCSSeqID !=null)
	{
		var objApprovedAilmentAmt=document.forms[1].elements['approvedAilmentAmt'];
		var objMaxAilmentAllowedAmt=document.forms[1].elements['maxAilmentAllowedAmt'];
		if(document.forms[1].selectedICDPCSSeqID.length)
		{		
			for(i=0;i<objApprovedAilmentAmt.length;i++)
			{
				if(objApprovedAilmentAmt[i].value!="" && regexp.test(objApprovedAilmentAmt[i].value))
					objApprovedAilmentAmt[i].value = parseFloat(objApprovedAilmentAmt[i].value).toFixed(2);
				if(objMaxAilmentAllowedAmt[i].value!="")
					objMaxAilmentAllowedAmt[i].value = parseFloat(objMaxAilmentAllowedAmt[i].value).toFixed(2);
				else					
					objMaxAilmentAllowedAmt[i].value = "0.00";
			}
		}
		else
		{		
			if(objApprovedAilmentAmt.value!="" && regexp.test(objApprovedAilmentAmt.value))
				objApprovedAilmentAmt.value = parseFloat(objApprovedAilmentAmt.value).toFixed(2);
			if(objMaxAilmentAllowedAmt.value!="")
				objMaxAilmentAllowedAmt.value = parseFloat(objMaxAilmentAllowedAmt.value).toFixed(2);
			else					
				objMaxAilmentAllowedAmt.value = "0.00";
		}
	}
	
	if(document.forms[1].selectedPCSSeqID !=null)
	{
		var objProcedureAmt=document.forms[1].elements['procedureAmt'];
		
		if(document.forms[1].selectedPCSSeqID.length)
		{		
			for(i=0;i<objProcedureAmt.length;i++)
			{
				if(objProcedureAmt[i].value!="" && regexp.test(objProcedureAmt[i].value))
					objProcedureAmt[i].value = parseFloat(objProcedureAmt[i].value).toFixed(2);
			}
		}
		else
		{	
			if(objProcedureAmt.value!="" && regexp.test(objProcedureAmt.value))
				objProcedureAmt.value = parseFloat(objProcedureAmt.value).toFixed(2);
		}	
	}
}//end of function calToBillSpliUp()