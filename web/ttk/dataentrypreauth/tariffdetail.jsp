<%
/** @ (#) tariffdetail.jsp 31st May 2006
 * Project     : TTK Healthcare Services
 * File        : tariffdetail.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 31st May 2006
 *
 * @author 	     : Arun K N
 * Modified by   : Pradeep R
 * Modified date : 4th July 2006
 * Reason        : To implement the changes based on prototype change(redesign).
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/preauth/tariffdetail.js"></SCRIPT>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper" %>
<script>

function onReset()
{
    document.forms[1].reset();    
}//end of onReset()
//added for maternity
function onAccountHeader()
{
	trimForm(document.forms[1]);
    var  numericValue=/^[0-9]*$/;
    var  bigdecimal=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.\[1-9]{1}\d?$|^\s*$/;   
	if(document.forms[1].wardTypeID !=null && document.forms[1].wardTypeID.length)//if more than one records are there
    {
    	var objCaptureNbrOfDaysYN=document.forms[1].elements['captureNbrOfDaysYN'];
    	var objRoomTypeID=document.forms[1].elements['roomTypeID'];
    	var objVaccineTypeID=document.forms[1].elements['vaccineTypeID'];
    	var objDaysOfStay=document.forms[1].elements['daysOfStay'];
    	var objTariffRequestedAmt=document.forms[1].elements['tariffRequestedAmt'];
    	var objTariffApprovedAmt=document.forms[1].elements['tariffApprovedAmt'];
    	var objTariffMaxAmtAllowed=document.forms[1].elements['tariffMaxAmtAllowed'];    	
    	    	
    	
    	for(i=0;i<document.forms[1].wardTypeID.length;i++)
    	{
    		
    		if(objCaptureNbrOfDaysYN[i].value == 'Y')
     		{
     			if(objDaysOfStay[i].value.length>0)
	     		{	     			
	     			if(numericValue.test(objDaysOfStay[i].value)==false)
					{
						alert('No.of days should be a numeric value.');						
						objDaysOfStay[i].select();										
						return false;
					}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
	     		}//end of if(objDaysOfStay[i].value.length>0)		     	
     		}//end of if(objCaptureNbrOfDaysYN[i].value=='Y' && objRoomTypeID[i].value=="")      		   
     			
     		if(objTariffRequestedAmt[i].value.length>0)
     		{
	     		if(bigdecimal.test(objTariffRequestedAmt[i].value)==false)
				{
					alert('Req. Amt. (Rs) should be 10 digits followed by 2 decimal places.');						
					objTariffRequestedAmt[i].select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)	
			}//end of if(objTariffRequestedAmt[i].value.length>0)
					
			
			if(objTariffApprovedAmt[i].value.length>0)
     		{
				if(bigdecimal.test(objTariffApprovedAmt[i].value)==false)
				{
					alert('Appr. Amt. (Rs) should be 10 digits followed by 2 decimal places.');						
					objTariffApprovedAmt[i].select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
			}//end of if(objTariffApprovedAmt[i].value.length>0)
    	}//end of for(i=0;i<objCaptureNbrOfDaysYN.length;i++)
    }//end of if(document.forms[1].wardTypeID !=null && document.forms[1].wardTypeID.length)
    else
    {
    	//if only one record found
    	
    	if(document.forms[1].captureNbrOfDaysYN!=null && document.forms[1].captureNbrOfDaysYN.value=='Y')
     	{
   			if(document.forms[1].daysOfStay.value.length>0)
     		{	     			
     			if(numericValue.test(document.forms[1].daysOfStay.value)==false)
				{
					alert('No.of Days should be a numeric value.');						
					document.forms[1].daysOfStay.select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
     		}//end of if(document.forms[1].daysOfStay.value.length>0)
     		
     		if(document.forms[1].tariffRequestedAmt.value.length>0)
     		{     		
	     		if(bigdecimal.test(document.forms[1].tariffRequestedAmt.value)==false)
				{
					alert('Req. Amt. (Rs) should be 10 digits followed by 2 decimal places.');						
					document.forms[1].tariffRequestedAmt.select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].tariffRequestedAmt.value)==false)
			}//end of if(document.forms[1].tariffRequestedAmt.value.length>0)
			
			if(document.forms[1].tariffApprovedAmt.value.length>0)
     		{
				if(bigdecimal.test(document.forms[1].tariffApprovedAmt.value)==false)
				{
					alert('Appr. Amt. (Rs) should be 10 digits followed by 2 decimal places.');						
					document.forms[1].tariffApprovedAmt.select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
			}//end of if(objTariffApprovedAmt[i].value.length>0)     		
     	}//end of if(objCaptureNbrOfDaysYN[i].value=='Y' && objRoomTypeID[i].value=="")    
    }
    
    /**
    *	Fucntion to check whether the enterd data is proper in the ailement block
    */
     var regexp=new RegExp("^(approvedAilmentAmt){1}[0-9]{1,}$");      
	 var totAilmentAmt=0;
  	 for(i=0;i<document.forms[1].length;i++)
	 {
			if(regexp.test(document.forms[1].elements[i].id))
			{
				id=document.forms[1].elements[i].id;				
				ailCnt=id.substr(id.lastIndexOf('t')+1,id.length);				
				document.forms[1].elements[i].value=trim(document.forms[1].elements[i].value);
				if(document.forms[1].elements[i].value.length>0)
				{
					if(bigdecimal.test(document.forms[1].elements[i].value)==false)
					{
						alert('Ailment Approved Amount should be 10 digits followed by 2 decimal places.');						
						document.forms[1].elements[i].select();										
						return false;
					}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)					
					//totAilmentAmt=totAilmentAmt + parseFloat(document.forms[1].elements[i].value);					
				}//end of if(document.forms[1].elements[i].value.length>0)				
				var regexp1=new RegExp("^(procedureAmt"+ailCnt+"~){1}[0-9]{1,}$");
				//var procedureAmount=0;				
				var procedureAmount=0;
				for(j=0;j<document.forms[1].length;j++)
				{
					if(regexp1.test(document.forms[1].elements[j].id))
					{
						id1=document.forms[1].elements[j].id;
						document.forms[1].elements[j].value=trim(document.forms[1].elements[j].value);
						
						if(document.forms[1].elements[j].value.length>0)
						{
							if(bigdecimal.test(document.forms[1].elements[j].value)==false)
							{
								alert('Procedure Approved Amount should be 10 digits followed by 2 decimal places.');						
								document.forms[1].elements[j].select();																		
								return false;
							}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)								
						}//end of if(document.forms[1].elements[j].value=="")
					}//end of if(regexp1.test(document.forms[1].elements[j].id))					
				}//end of for(j=0;j<document.forms[1].length;j++)				
			}//end of if(regexp.test(objform.elements[i].id))
		}//end of for(i=0;i<objform.length;i++)			
	document.forms[1].mode.value="doSubmitAccountHead";
    document.forms[1].action = "/TariffSubmitAction.do";
    document.forms[1].submit();
}   

function onSubmitCalculate()
{
	setTimeout("onSubmitCalculate1()", 900);
}

function onSubmitCalculate1()
{
    trimForm(document.forms[1]);
    //calcTotalReqAmt();
    //calcTotalApprAmt();
    var  numericValue=/^[0-9]*$/;
    var  bigdecimal=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.\[1-9]{1}\d?$|^\s*$/;   
    
    var totReqAmt=0;
    
    /**
    *	Check requested amount matche with sum of account head requested amount.
    */
    if(document.forms[1].wardTypeID !=null && document.forms[1].wardTypeID.length)//if more than one records are there
    {
    	for(i=0;i<document.forms[1].wardTypeID.length;i++)
    	{
    		var obj=document.forms[1].elements['tariffRequestedAmt'];
		    for(i=0;i<obj.length;i++)
		    {
		       if(obj[i].value!="" && bigdecimal.test(obj[i].value))
		       totReqAmt= totReqAmt + parseFloat(obj[i].value);
		    }//end of for
    	}//end of for(i=0;i<document.forms[1].wardTypeID.length;i++)	    	
    	
    }else
    {
    	if(document.forms[1].tariffRequestedAmt!=null && document.forms[1].tariffRequestedAmt.value!="" && bigdecimal.test(document.forms[1].tariffRequestedAmt.value))
	    	totReqAmt= totReqAmt + parseFloat(document.forms[1].tariffRequestedAmt.value);
    }//end of else    
    
    
    if(totReqAmt!=document.forms[1].reqAmount.value)
    {
    	alert('Total Req. Amt. (Rs) should be equal to Requested  Amt. (Rs).');    	
    	return false;
    }
    
   /**
    *	Validate for the proper data in the form.    
    *	For any of the account head Room Type/No.of Days/Req. Amt. (Rs) than make that particular account head
    *	as mandatory 
    */
        
    if(document.forms[1].wardTypeID !=null && document.forms[1].wardTypeID.length)//if more than one records are there
    {
    	var objCaptureNbrOfDaysYN=document.forms[1].elements['captureNbrOfDaysYN'];
    	var objRoomTypeID=document.forms[1].elements['roomTypeID'];
    	var objVaccineTypeID=document.forms[1].elements['vaccineTypeID'];
    	var objDaysOfStay=document.forms[1].elements['daysOfStay'];
    	var objTariffRequestedAmt=document.forms[1].elements['tariffRequestedAmt'];
    	var objTariffApprovedAmt=document.forms[1].elements['tariffApprovedAmt'];
    	var objTariffMaxAmtAllowed=document.forms[1].elements['tariffMaxAmtAllowed'];
    	var objwardTypeID=document.forms[1].elements['wardTypeID'];//added for maternity    	
    	    	
    	
    	for(i=0;i<document.forms[1].wardTypeID.length;i++)
    	{
    		var checkFlag=false;  
    		
    		if(objCaptureNbrOfDaysYN[i].value == 'Y')
     		{
     			if(objRoomTypeID[i].value!="" || objDaysOfStay[i].value.length>0  || objTariffRequestedAmt[i].value.length>0 )
     			{
     				checkFlag=true;     				
     			}
     			if(checkFlag)
     			{
	     			if(objRoomTypeID[i].value=="")
	     			{
		     			alert('Please select Room Type.');     			
	    	 			objRoomTypeID[i].focus();
	     				return false; 
	     			}//end of if(objRoomTypeID[i].value=="")     		
		     	
		     		if(objDaysOfStay[i].value=="")
		     		{
		     			alert('Please enter No.of Days.');	     			
		     			objDaysOfStay[i].focus();
		     			return false; 
		     		}//end of if(objDaysOfStay[i].value=="")
		     		if(objDaysOfStay[i].value.length>0)
		     		{	     			
		     			if(numericValue.test(objDaysOfStay[i].value)==false)
						{
							alert('No.of Days should be a numeric value.');						
							objDaysOfStay[i].select();										
							return false;
						}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
		     		}
		     	}
     		}//end of if(objCaptureNbrOfDaysYN[i].value=='Y' && objRoomTypeID[i].value=="") 
     		else if(objCaptureNbrOfDaysYN[i].value == 'N')
         		{
         		if((objwardTypeID[i].value == 'VCE'))
         		{
         			if(objVaccineTypeID[i].value!=""  || objTariffRequestedAmt[i].value.length>0 )
         			{
         				checkFlag=true;     				
         			}
         			if(checkFlag)
         			{
    	     			if(objVaccineTypeID[i].value=="")
    	     			{
    		     			alert('Please select Vaccination Type.');     			
    		     			objVaccineTypeID[i].focus();
    	     				return false; 
    	     			}//end of if(objRoomTypeID[i].value=="")     	

             		}
         		}  
         		}   		   


     		else
     		{
     			if(objTariffRequestedAmt[i].value.length>0)
     			{
     				checkFlag=true;     				
     			}     		
     		}
     		
	     	if(checkFlag)
     		{	
	     		if(objTariffRequestedAmt[i].value.length==0)
	     		{
	     			alert('Please enter Req. Amt. (Rs).');						
					objTariffRequestedAmt[i].select();										
					return false;
	     		}
	     		if(bigdecimal.test(objTariffRequestedAmt[i].value)==false)
				{
					alert('Req. Amt. (Rs) should be 10 digits followed by 2 decimal places.');						
					objTariffRequestedAmt[i].select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)			
			}
			if(objTariffApprovedAmt[i].value.length>0)
     		{
				if(bigdecimal.test(objTariffApprovedAmt[i].value)==false)
				{
					alert('Appr. Amt. (Rs) should be 10 digits followed by 2 decimal places.');						
					objTariffApprovedAmt[i].select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
			}//end of if(objTariffApprovedAmt[i].value.length>0)
    	}//end of for(i=0;i<objCaptureNbrOfDaysYN.length;i++)
    }//end of if(document.forms[1].wardTypeID !=null && document.forms[1].wardTypeID.length)
    else
    {
    	//if only one record found
    	if(document.forms[1].captureNbrOfDaysYN.value=='Y')
     	{
   			if(document.forms[1].roomTypeID.value=="")
   			{
    			alert('Please select Room Type.');     			
  	 			document.forms[1].roomTypeID.focus();
   				return false; 
   			}//end of if(objRoomTypeID[i].value=="")     		
     		if(document.forms[1].daysOfStay.value=="")
     		{
     			alert('Please enter No.of Days.');	     			
     			document.forms[1].daysOfStay.focus();
     			return false; 
     		}//end of if(objDaysOfStay[i].value=="")
     		if(document.forms[1].daysOfStay.value.length>0)
     		{	     			
     			if(numericValue.test(document.forms[1].daysOfStay.value)==false)
				{
					alert('No. of Days should be a numeric value.');						
					document.forms[1].daysOfStay.select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
     		}//end of if(document.forms[1].daysOfStay.value.length>0)
     		
     		if(document.forms[1].tariffRequestedAmt.value.length==0)
     		{
     			alert('Please enter Req. Amt. (Rs).');						
				document.forms[1].tariffRequestedAmt.select();										
				return false;
     		}//end of if(document.forms[1].tariffRequestedAmt.value.length==0)
     		if(bigdecimal.test(document.forms[1].tariffRequestedAmt.value)==false)
			{
				alert('Req. Amt. (Rs) should be 10 digits followed by 2 decimal places.');						
				document.forms[1].tariffRequestedAmt.select();										
				return false;
			}//end of if(bigdecimal.test(document.forms[1].tariffRequestedAmt.value)==false)
			
			if(document.forms[1].tariffApprovedAmt.value.length>0)
     		{
				if(bigdecimal.test(document.forms[1].tariffApprovedAmt.value)==false)
				{
					alert('Appr. Amt. (Rs) should be 10 digits followed by 2 decimal places.');						
					document.forms[1].tariffApprovedAmt.select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
			}//end of if(objTariffApprovedAmt[i].value.length>0)     		
     	}//end of if(objCaptureNbrOfDaysYN[i].value=='Y' && objRoomTypeID[i].value=="") 
     	else if(document.forms[1].captureNbrOfDaysYN.value=='N')
     	{
     		if(document.forms[1].wardTypeID.value=='VCE')
     		{
     		if(document.forms[1].vaccineTypeID.value=="")
   			{
    			alert('Please select Vaccination Type.');     			
  	 			document.forms[1].vaccineTypeID.focus();
   				return false; 
   			}//end of if(objRoomTypeID[i].value=="")    
   			}
     	}
    }
    
    /**
    *	Fucntion to check whether the enterd data is proper in the ailement block
    */
     var regexp=new RegExp("^(approvedAilmentAmt){1}[0-9]{1,}$");      
	 var totAilmentAmt=0;
  	 for(i=0;i<document.forms[1].length;i++)
	 {
			if(regexp.test(document.forms[1].elements[i].id))
			{
				id=document.forms[1].elements[i].id;				
				ailCnt=id.substr(id.lastIndexOf('t')+1,id.length);				
				document.forms[1].elements[i].value=trim(document.forms[1].elements[i].value);
				if(document.forms[1].elements[i].value.length>0)
				{
					if(bigdecimal.test(document.forms[1].elements[i].value)==false)
					{
						alert('Ailment Approved Amount should be 10 digits followed by 2 decimal places.');						
						document.forms[1].elements[i].select();										
						return false;
					}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)					
					//totAilmentAmt=totAilmentAmt + parseFloat(document.forms[1].elements[i].value);					
				}//end of if(document.forms[1].elements[i].value.length>0)				
				var regexp1=new RegExp("^(procedureAmt"+ailCnt+"~){1}[0-9]{1,}$");
				//var procedureAmount=0;				
				var procedureAmount=0;
				for(j=0;j<document.forms[1].length;j++)
				{
					if(regexp1.test(document.forms[1].elements[j].id))
					{
						id1=document.forms[1].elements[j].id;
						document.forms[1].elements[j].value=trim(document.forms[1].elements[j].value);
						
						if(document.forms[1].elements[j].value.length>0)
						{
							if(bigdecimal.test(document.forms[1].elements[j].value)==false)
							{
								alert('Procedure Approved Amount should be 10 digits followed by 2 decimal places.');						
								document.forms[1].elements[j].select();																		
								return false;
							}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)								
						}//end of if(document.forms[1].elements[j].value=="")
					}//end of if(regexp1.test(document.forms[1].elements[j].id))					
				}//end of for(j=0;j<document.forms[1].length;j++)				
			}//end of if(regexp.test(objform.elements[i].id))
		}//end of for(i=0;i<objform.length;i++)
    
    if(!JS_SecondSubmit){
    	document.forms[1].mode.value="doCalculate";
    	document.forms[1].action = "/TariffSubmitAction.do";
    	JS_SecondSubmit=true
    	document.forms[1].submit();
    }//end of if(!JS_SecondSubmit)
}//end of onSubmitCalculate()


function calcTotalReqAmt()
{
  //alert('calcTotalReqAmt');
  var totReqAmt=0;
  regexp=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.\[1-9]{1}\d?$|^\s*$/;
  
  if(document.forms[1].wardTypeID !=null && document.forms[1].wardTypeID.length)//if more than one records are there
    {
    	for(i=0;i<document.forms[1].wardTypeID.length;i++)
    	{
    		var obj=document.forms[1].elements['tariffRequestedAmt'];
		    for(i=0;i<obj.length;i++)
		    {
		       if(obj[i].value!="" && regexp.test(obj[i].value))
		       totReqAmt= totReqAmt + parseFloat(obj[i].value);
		    }//end of for
    	}//end of for(i=0;i<document.forms[1].wardTypeID.length;i++)		
    }else
    {
    	if(document.forms[1].tariffRequestedAmt.value!="" && regexp.test(document.forms[1].tariffRequestedAmt.value))
	    	totReqAmt= totReqAmt + parseFloat(document.forms[1].tariffRequestedAmt.value);
    }//end of else    
  	document.forms[1].totReqAmt.value=totReqAmt;
}//end of calcTotalReqAmt()

function calcTotalApprAmt()
{
  //alert('calcTotalReqAmt');
  var totReqAmt=0;
  regexp=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.\[1-9]{1}\d?$|^\s*$/;
  
  if(document.forms[1].wardTypeID !=null && document.forms[1].wardTypeID.length)//if more than one records are there
    {
    	for(i=0;i<document.forms[1].wardTypeID.length;i++)
    	{
    		var obj=document.forms[1].elements['tariffApprovedAmt'];
		    for(i=0;i<obj.length;i++)
		    {
		       if(obj[i].value!="" && regexp.test(obj[i].value))
		       		totReqAmt= totReqAmt + parseFloat(obj[i].value);
		    }//end of for
    	}//end of for(i=0;i<document.forms[1].wardTypeID.length;i++)		
    }else
    {
    	if(document.forms[1].tariffApprovedAmt.value!="" && regexp.test(document.forms[1].tariffApprovedAmt.value))
	    	totReqAmt= totReqAmt + parseFloat(document.forms[1].tariffApprovedAmt.value);
    }//end of else        
  	document.forms[1].totApprAmt.value=Math.round(totReqAmt);
  	calcTotalMaxAmt();
}//end of calcTotalApprAmt()

function calcTotalMaxAmt()
{
  //alert('calcTotalReqAmt');
  var totReqAmt=0;
  regexp=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.\[1-9]{1}\d?$|^\s*$/;
  
  if(document.forms[1].wardTypeID !=null && document.forms[1].wardTypeID.length)//if more than one records are there
    {
    	for(i=0;i<document.forms[1].wardTypeID.length;i++)
    	{
    		var obj=document.forms[1].elements['tariffMaxAmtAllowed'];
		    for(i=0;i<obj.length;i++)
		    {
		       if(obj[i].value!="" && regexp.test(obj[i].value))
		       totReqAmt= totReqAmt + parseFloat(obj[i].value);
		    }//end of for
    	}//end of for(i=0;i<document.forms[1].wardTypeID.length;i++)		
    }else
    {
    	if(document.forms[1].tariffMaxAmtAllowed.value!="" && regexp.test(document.forms[1].tariffMaxAmtAllowed.value))
	    	totReqAmt= totReqAmt + parseFloat(document.forms[1].tariffMaxAmtAllowed.value);
    }//end of else    
  	document.forms[1].totMaxAmt.value=totReqAmt;
}//end of calcTotalApprAmt()


function onUserSubmit1()
{
	setTimeout("onUserSubmit()", 900);
}

function onUserSubmit()
{
	trimForm(document.forms[1]);
	var  bigdecimal=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.\[1-9]{1}\d?$|^\s*$/;
	var  numericValue=/^[0-9]*$/;
	var totReqAmt=0;
	/**
    *	Check requested amount matche with sum of account head requested amount.
    */
    if(document.forms[1].wardTypeID !=null && document.forms[1].wardTypeID.length)//if more than one records are there
    {
    	/*for(i=0;i<document.forms[1].wardTypeID.length;i++)
    	{
    		var obj=document.forms[1].elements['tariffRequestedAmt'];
		    for(i=0;i<obj.length;i++)
		    {
		       if(obj[i].value!="" && bigdecimal.test(obj[i].value))
		       totReqAmt= totReqAmt + parseFloat(obj[i].value);
		    }//end of for
    	}//end of for(i=0;i<document.forms[1].wardTypeID.length;i++)
	*/
	for(i=0;i<document.forms[1].wardTypeID.length;i++)
  	{
  		var obj=document.forms[1].elements['tariffRequestedAmt'];
	    for(i=0;i<obj.length;i++)
	    {
	       var obj1=document.forms[1].elements['tariffApprovedAmt'];
	       for(i=0;i<obj1.length;i++)
	       {
		       if(obj[i].value!="" && bigdecimal.test(obj[i].value))
		       {
		       		totReqAmt= totReqAmt + parseFloat(obj[i].value);	
		       }//end of if(obj[i].value!="" && bigdecimal.test(obj[i].value))
		       if(obj[i].value =='' && parseFloat(obj1[i].value) > 0)
		       {
		       		alert('Enter the Requested Amt.(Rs). agianst correct account head'); 
		       		return false;
		       }//end of if(obj[i].value =='' && parseFloat(obj1[i].value) > 0)
		       if(parseFloat(obj[i].value) < parseFloat(obj1[i].value))
		       {
				  alert('Req. Amt. should be greater than or equal to Appr amt., agianst account head'); 
				  return false;
		       }//end of if(parseFloat(obj[i].value) < parseFloat(obj1[i].value))			
	       }//end of for(i=0;i<obj1.length;i++)	       
	    }//end of for(i=0;i<obj.length;i++)
	}//end of for(i=0;i<document.forms[1].wardTypeID.length;i++)	
    }else
    {
    	if(document.forms[1].tariffRequestedAmt.value!="" && bigdecimal.test(document.forms[1].tariffRequestedAmt.value))
	    	totReqAmt= totReqAmt + parseFloat(document.forms[1].tariffRequestedAmt.value);
    }//end of else    
    
    
    if(totReqAmt!=document.forms[1].reqAmount.value)
    {
    	alert('Total Req. Amt. (Rs) should be equal to Requested Amt. (Rs).');    	
    	return false;
    }
    
	if(document.forms[1].wardTypeID !=null && document.forms[1].wardTypeID.length)//if more than one records are there
    {
    	var objCaptureNbrOfDaysYN=document.forms[1].elements['captureNbrOfDaysYN'];
    	var objRoomTypeID=document.forms[1].elements['roomTypeID'];
    	var objVaccineTypeID=document.forms[1].elements['vaccineTypeID'];//added for maternity
    	var objDaysOfStay=document.forms[1].elements['daysOfStay'];
    	var objTariffRequestedAmt=document.forms[1].elements['tariffRequestedAmt'];
    	var objTariffApprovedAmt=document.forms[1].elements['tariffApprovedAmt'];
    	var objTariffMaxAmtAllowed=document.forms[1].elements['tariffMaxAmtAllowed']; 
    	var objwardTypeID=document.forms[1].elements['wardTypeID'];//added for maternity       	
    	
    	for(i=0;i<document.forms[1].wardTypeID.length;i++)
    	{
   			var checkFlag=false;  
   			
   			if(objCaptureNbrOfDaysYN[i].value=='Y')
     		{
     			
     			if(objRoomTypeID[i].value!="" || objDaysOfStay[i].value.length>0  || objTariffRequestedAmt[i].value.length>0 )
     			{
     				//alert('Inside if flag');
     				checkFlag=true;     				
     			}
     			if(checkFlag)
     			{
	     			if(objRoomTypeID[i].value=="")
	     			{
		     			alert('Please select Room Type.');     			
	    	 			objRoomTypeID[i].focus();
	     				return false; 
	     			}//end of if(objRoomTypeID[i].value=="")     		
		     		if(objDaysOfStay[i].value=="")
		     		{
		     			alert('Please enter No.of Days.');	     			
		     			objDaysOfStay[i].focus();
		     			return false; 
		     		}//end of if(objDaysOfStay[i].value=="")
		     		if(objDaysOfStay[i].value.length>0)
		     		{	     			
		     			if(numericValue.test(objDaysOfStay[i].value)==false)
						{
							alert('No.of Days should be a numeric value.');						
							objDaysOfStay[i].select();										
							return false;
						}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
		     		}
		     	}//end of if(checkFlag)
     		}//end of if(objCaptureNbrOfDaysYN[i].value=='Y' && objRoomTypeID[i].value=="")
     		else if(objCaptureNbrOfDaysYN[i].value == 'N')
     		{
     		if((objwardTypeID[i].value == 'VCE'))
     		{
     			if(objVaccineTypeID[i].value!=""  || objTariffRequestedAmt[i].value.length>0 )
     			{
     				checkFlag=true;     				
     			}
     			if(checkFlag)
     			{
	     			if(objVaccineTypeID[i].value=="")
	     			{
		     			alert('Please select Vaccination Type.');     			
		     			objVaccineTypeID[i].focus();
	     				return false; 
	     			}//end of if(objRoomTypeID[i].value=="")     	

         		}
     		}  
     		}   		 
     		else
     		{
     			if(objTariffRequestedAmt[i].value.length>0)
     			{
     				checkFlag=true;     				
     			}//end of if(objTariffRequestedAmt[i].value.length>0)
     		}//end of else
     		if(checkFlag)
     		{
	     		if(objTariffRequestedAmt[i].value.length==0)
	     		{
	     			alert('Please enter Req. Amt. (Rs).');						
					objTariffRequestedAmt[i].select();										
					return false;
	     		}
	     		if(bigdecimal.test(objTariffRequestedAmt[i].value)==false)
				{
					alert('Req. Amt. (Rs) should be 10 digits followed by 2 decimal places.');						
					objTariffRequestedAmt[i].select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
				
				/*if(objTariffRequestedAmt[i].value.length>0)
				{
					if(objTariffApprovedAmt[i].value.length==0)
					{
						alert('Please enter Appr. Amt. (Rs).');						
						objTariffApprovedAmt[i].select();	
						return false;
					}			
				}*/
			}
			if(objTariffApprovedAmt[i].value.length>0)
     		{
				if(bigdecimal.test(objTariffApprovedAmt[i].value)==false)
				{
					alert('Appr. Amt. (Rs) should be 10 digits followed by 2 decimal places.');						
					objTariffApprovedAmt[i].select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
				
				if(parseFloat(objTariffApprovedAmt[i].value)>parseFloat(objTariffRequestedAmt[i].value))
				{
					alert('Appr. Amt. (Rs) should be less than or equal to Req. Amt. (Rs).');	
					objTariffApprovedAmt[i].select();	
					return false;
				}//end of if(parseFloat(objTariffApprovedAmt[i].value)>parseFloat(objTariffRequestedAmt[i].value))
				
				if(parseFloat(objTariffApprovedAmt[i].value)>parseFloat(objTariffMaxAmtAllowed[i].value))
				{
					alert('Appr. Amt. (Rs) should be less or equal than Max. Amt. (Rs).');	
					objTariffApprovedAmt[i].select();	
					return false;
				}//end of if(parseFloat(objTariffApprovedAmt[i].value)>parseFloat(objTariffMaxAmtAllowed[i].value))				
			}//end of if(objTariffApprovedAmt[i].value.length>0)
     		
     		//objTariffMaxAmtAllowed     		 		
    	}//end of for(i=0;i<objCaptureNbrOfDaysYN.length;i++)
    }//end of if(document.forms[1].wardTypeID !=null && document.forms[1].wardTypeID.length)
    else
    {
    	//if only one record found
    	//alert('Inside else  For');
    	
    	if(document.forms[1].captureNbrOfDaysYN.value=='Y')
     	{
   			if(document.forms[1].roomTypeID.value=="")
   			{
    			alert('Please select Room Type.');     			
  	 			document.forms[1].roomTypeID.focus();
   				return false; 
   			}//end of if(objRoomTypeID[i].value=="") 
   			if(document.forms[1].vaccineTypeID.value=="")
     		{
     			alert('Please select Vaccination Type.');	     			
     			document.forms[1].vaccineTypeID.focus();
     			return false; 
     		}//end of if(objDaysOfStay[i].value=="")    		
     		if(document.forms[1].daysOfStay.value=="")
     		{
     			alert('Please select enter No.of Days.');	     			
     			document.forms[1].daysOfStay.focus();
     			return false; 
     		}//end of if(objDaysOfStay[i].value=="")
     		if(document.forms[1].daysOfStay.value.length>0)
     		{	     			
     			if(numericValue.test(document.forms[1].daysOfStay.value)==false)
				{
					alert('No.of Days should be a numeric value.');						
					document.forms[1].daysOfStay.select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
     		}//end of if(document.forms[1].daysOfStay.value.length>0)
     		
     		if(document.forms[1].tariffRequestedAmt.value.length==0)
     		{
     			alert('Please enter Req. Amt. (Rs).');						
				document.forms[1].tariffRequestedAmt.select();										
				return false;
     		}//end of if(document.forms[1].tariffRequestedAmt.value.length==0)
     		if(bigdecimal.test(document.forms[1].tariffRequestedAmt.value)==false)
			{
				alert('Req. Amt. (Rs) should be 10 digits followed by 2 decimal places.');						
				document.forms[1].tariffRequestedAmt.select();										
				return false;
			}//end of if(bigdecimal.test(document.forms[1].tariffRequestedAmt.value)==false)
			
			/*if(document.forms[1].tariffRequestedAmt.value.length>0)
			{
				
				if(document.forms[1].tariffApprovedAmt.value.length==0)
				{
					alert('Please enter Appr. Amt. (Rs).');						
					document.forms[1].tariffApprovedAmt.select();	
					return false;
				}			
			}*/
						
			
			if(document.forms[1].tariffApprovedAmt.value.length>0)
     		{
				if(bigdecimal.test(document.forms[1].tariffApprovedAmt.value)==false)
				{
					alert('Appr. Amt. (Rs) should be 10 digits followed by 2 decimal places.');						
					document.forms[1].tariffApprovedAmt.select();										
					return false;
				}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
				
				if(parseFloat(document.forms[1].tariffApprovedAmt.value)>parseFloat(document.forms[1].tariffRequestedAmt.value))
				{
					alert('Appr. Amt. (Rs) should be less than or equal to Req. Amt. (Rs).');	
					document.forms[1].tariffApprovedAmt.select();	
					return false;
				}//end of if(parseFloat(objTariffApprovedAmt[i].value)>parseFloat(objTariffRequestedAmt[i].value))
				
				if(parseFloat(document.forms[1].tariffApprovedAmt.value)>parseFloat(document.forms[1].tariffMaxAmtAllowed.value))
				{
					alert('Appr. Amt. (Rs) should be less or equal than Max. Amt. (Rs).');	
					document.forms[1].tariffApprovedAmt.value.select();	
					return false;
				}//end of if(parseFloat(objTariffApprovedAmt[i].value)>parseFloat(objTariffMaxAmtAllowed[i].value))				
			}//end of if(objTariffApprovedAmt[i].value.length>0)
     		
     	}//end of if(objCaptureNbrOfDaysYN[i].value=='Y' && objRoomTypeID[i].value=="")  
     	else if(document.forms[1].captureNbrOfDaysYN.value=='N')
     	{
     		if(document.forms[1].wardTypeID.value=='VCE')
     		{
     		if(document.forms[1].vaccineTypeID.value=="")
   			{
    			alert('Please select Vaccination Type.');     			
  	 			document.forms[1].vaccineTypeID.focus();
   				return false; 
   			}//end of if(objRoomTypeID[i].value=="")    
   			}
     	}
     	 
    }//end of else
  
  
  /***
  	*	Script for the ailement block
  */  
    
  var regexp=new RegExp("^(approvedAilmentAmt){1}[0-9]{1,}$");    
  var  bigdecimal=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.\[1-9]{1}\d?$|^\s*$/; 
  var totAilmentAmt=0;
  for(i=0;i<document.forms[1].length;i++)
		{
			if(regexp.test(document.forms[1].elements[i].id))
			{
				id=document.forms[1].elements[i].id;				
				ailCnt=id.substr(id.lastIndexOf('t')+1,id.length);
				//to do if amount is entered should be 10, 2
				//alert('Value '+document.forms[1].elements[i].value);
				document.forms[1].elements[i].value=trim(document.forms[1].elements[i].value);
				if(document.forms[1].elements[i].value.length>0)
				{
					if(bigdecimal.test(document.forms[1].elements[i].value)==false)
					{
						alert('Ailment Approved Amount should be 10 digits followed by 2 decimal places.');						
						document.forms[1].elements[i].select();										
						return false;
					}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)					
					totAilmentAmt=totAilmentAmt + parseFloat(document.forms[1].elements[i].value);					
				}//end of if(document.forms[1].elements[i].value.length>0)				
				var regexp1=new RegExp("^(procedureAmt"+ailCnt+"~){1}[0-9]{1,}$");
				//var procedureAmount=0;				
				var procedureAmount=0;
				for(j=0;j<document.forms[1].length;j++)
				{
					if(regexp1.test(document.forms[1].elements[j].id))
					{
						id1=document.forms[1].elements[j].id;
						document.forms[1].elements[j].value=trim(document.forms[1].elements[j].value);
						
						if(document.forms[1].elements[j].value.length>0)
						{
							if(bigdecimal.test(document.forms[1].elements[j].value)==false)
							{
								alert('Procedure Approved Amount should be 10 digits followed by 2 decimal places.');						
								document.forms[1].elements[j].select();																		
								return false;
							}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
							//alert(parseFloat(document.forms[1].elements[j].value));
							//alert('procedureAmount1	'+procedureAmount);
							procedureAmount+=parseFloat(document.forms[1].elements[j].value);	
							//alert('procedureAmount'+procedureAmount);						
						}//end of if(document.forms[1].elements[j].value=="")
					}//end of if(regexp1.test(document.forms[1].elements[j].id))
					
				}//end of for(j=0;j<document.forms[1].length;j++)
				if(procedureAmount!=0&&procedureAmount>document.forms[1].elements[i].value)
					{
						//alert('procedureAmount'+procedureAmount);
					    //alert('elements	'+document.forms[1].elements[i].value);	
						alert('Ailment Amount should be greater than Sum of Procedure Amount.');						
						document.forms[1].elements[i].select();																		
						return false;
					}
			}//end of if(regexp.test(objform.elements[i].id))
		}//end of for(i=0;i<objform.length;i++)
 
 if(parseFloat(document.forms[1].totApprAmt.value)>parseFloat(document.forms[1].reqAmount.value))
  {
  	alert('Total Appr. Amt. should be less than or equal to Requested Amt. ');
  	return false;
  }
 
  if(parseFloat(document.forms[1].totApprAmt.value)!=parseFloat(totAilmentAmt))
  {
  	alert('Total Appr. Amt. should be equal to Total Ailment Appr. Amt. ');
  	return false;
  }
  //start Modification  as per KOC 1140 ChangeRequest({SumInsuredRestriction)
    if(document.forms[1].familySIResAmtYN.value == 'Y')
	         {
	        
	            if(parseFloat(document.forms[1].totApprAmt.value)> parseFloat(document.forms[1].totAmt.value))
	              {
	               	alert('You cannot approve more than Available SI Restricted Amt.');
	                 	return false;
	                 }
	
	          }

        else if(document.forms[1].familySIResAmtYN.value != 'Y')
        {
  if(parseFloat(document.forms[1].totApprAmt.value)> parseFloat(document.forms[1].totAmt.value))
  {
  	alert('You cannot approve more than Total Available Amt.');
  	return false;
  }
  }// else if(document.forms[1].familySIResAmtYN.value != 'Y')
  if(!JS_SecondSubmit){
      	document.forms[1].mode.value="doSubmit";
      	document.forms[1].action ="TariffSubmitAction.do";
      	JS_SecondSubmit=true
      	document.forms[1].submit();
      }//end of if(!JS_SecondSubmit)
 
}//end of onValidateRoom

function onReset1()
{
  
  var regexp=new RegExp("^(approvedAilmentAmt){1}[0-9]{1,}$");    
  var  bigdecimal=/^(([0])*[1-9]{1}\d{0,9}(\.\d{1,2})?)$|^[0]*\.\[1-9]{1}\d?$|^\s*$/;
 // alert('after'+regexp);
  for(i=0;i<document.forms[1].length;i++)
		{
			//alert("hi");
			if(regexp.test(document.forms[1].elements[i].id))
			{
				id=document.forms[1].elements[i].id;				
				ailCnt=id.substr(id.lastIndexOf('t')+1,id.length);
				//to do if amount is entered should be 10, 2
				//alert('Value '+document.forms[1].elements[i].value);
				document.forms[1].elements[i].value=trim(document.forms[1].elements[i].value);
				if(document.forms[1].elements[i].value.length>0)
				{
					if(bigdecimal.test(document.forms[1].elements[i].value)==false)
					{
						alert('Ailment Approved Amount should be 10 digits followed by 2 decimal places.');						
						document.forms[1].elements[i].select();										
						return false;
					}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
				}//end of if(document.forms[1].elements[i].value.length>0)				
				var regexp1=new RegExp("^(procedureAmt"+ailCnt+"~){1}[0-9]{1,}$");
				//var procedureAmount=0;				
				var procedureAmount=0;
				for(j=0;j<document.forms[1].length;j++)
				{
					if(regexp1.test(document.forms[1].elements[j].id))
					{
						id1=document.forms[1].elements[j].id;
						document.forms[1].elements[j].value=trim(document.forms[1].elements[j].value);
						
						if(document.forms[1].elements[j].value.length>0)
						{ 
							if(bigdecimal.test(document.forms[1].elements[j].value)==false)
							{
								alert('Procedure Approved Amount should be 10 digits followed by 2 decimal places.');						
								document.forms[1].elements[j].select();																		
								return false;
							}//end of if(bigdecimal.test(document.forms[1].elements[i].value)==false)
							//alert(parseFloat(document.forms[1].elements[j].value));
							//alert('procedureAmount1	'+procedureAmount);
							procedureAmount+=parseFloat(document.forms[1].elements[j].value);	
							//alert('procedureAmount'+procedureAmount);						
						}//end of if(document.forms[1].elements[j].value=="")
					}//end of if(regexp1.test(document.forms[1].elements[j].id))
					
				}//end of for(j=0;j<document.forms[1].length;j++)
				if(procedureAmount!=0&&procedureAmount>document.forms[1].elements[i].value)
					{
						//alert('procedureAmount'+procedureAmount);
					    //alert('elements	'+document.forms[1].elements[i].value);	
						alert('Ailment Amount should be greater than Sum of Procedure Amount.');						
						document.forms[1].elements[i].select();																		
						return false;
					}
			}//end of if(regexp.test(objform.elements[i].id))
		}//end of for(i=0;i<objform.length;i++)
 
}//function onReset

function onDocumentLoad()
 {
 	 var totAmt=0;
	  if(document.forms[1].familySIResAmtYN.value == 'Y')
     {
 	    // alert("if");
		 if(document.forms[1].familySIResAmt.value!=null && document.forms[1].familySIResAmt.value!="")
	 	 {
   
		 	 
		   totAmt=totAmt+ parseFloat(document.forms[1].familySIResAmt.value);
		    
		   //alert("if Y"+totAmt);     
         }
     }
 	 // End Chaanges as per KOC 1140(Sum insured Restriction)
 	 else if(document.forms[1].familySIResAmtYN.value != 'Y')
 	 {
 	 if(document.forms[1].avaSuminsured.value!=null && document.forms[1].avaSuminsured.value!="")
 	 {
 	 	totAmt=totAmt+ parseFloat(document.forms[1].avaSuminsured.value);
 	 }
	 }//else if(document.forms[1].familySIResAmtYN.value != 'Y')
 	 if(document.forms[1].avaBonus.value!=null && document.forms[1].avaBonus.value!="")
 	 {
 	 	totAmt=totAmt+parseFloat(document.forms[1].avaBonus.value);
 	 }
 	 if(document.forms[1].avaBuffer.value!=null && document.forms[1].avaBuffer.value!="")
 	 {
 	 	totAmt=totAmt+parseFloat(document.forms[1].avaBuffer.value);
 	 }
 	 document.forms[1].totAmt.value=totAmt;
 	 //alert(document.forms[1].totAmt.value);
 }//end of onDocumentLoad()
 
</script>
<%
  pageContext.setAttribute("roomsCodeList",Cache.getCacheObject("roomsCode"));
  pageContext.setAttribute("vaccineType",Cache.getCacheObject("vaccineType"));//added for maternity
  boolean viewmode=true;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }//end of if(TTKCommon.isAuthorized(request,"Edit"))
  pageContext.setAttribute("viewmode",new Boolean(viewmode));

%>

<!-- S T A R T : Content/Form Area -->
  <html:form action="/PreAuthTariffAction.do">
  <!-- S T A R T : Page Title -->
  <table align="center" class="<%=TTKCommon.checkNull(PreAuthWebBoardHelper.getShowBandYN(request)).equals("Y") ? "pageTitleHilite" :"pageTitle" %>" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td >Tariff Details - [ <%=PreAuthWebBoardHelper.getClaimantName(request)%> ]
      	<%
		if(PreAuthWebBoardHelper.getEnrollmentId(request) != null && (!"".equals(PreAuthWebBoardHelper.getEnrollmentId(request).trim()))){
		%>
		 [ <%=PreAuthWebBoardHelper.getEnrollmentId(request)%> ]
		<%} %> 
      </td>
    <td align="right" class="webBoard"><%@ include file="/ttk/common/toolbar.jsp" %></td>
    </tr>
  </table>
  <!-- E N D : Page Title -->

    <!-- S T A R T : Form Fields -->
  <div class="contentArea" id="contentArea">
<html:errors/>
<!-- S T A R T : Success Box -->
 <logic:notEmpty name="updated" scope="request">
  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
         <bean:message name="updated" scope="request"/>
     </td>
   </tr>
  </table>
 </logic:notEmpty>

  <logic:notEmpty name="medicalnonpackage" scope="request">
  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
		 <bean:message name="medicalnonpackage" scope="request"/>
	</td>
   </tr>
  </table>
 </logic:notEmpty>
 <!-- E N D : Success Box -->
  
  <logic:notEmpty name="tariffcalculation" scope="request">
  <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
   <tr>
     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
		 <bean:message name="tariffcalculation" scope="request"/>
	</td>
   </tr>
  </table>
 </logic:notEmpty>
 <!-- E N D : Success Box -->
 
    <fieldset>
  <legend>Tariff Details</legend>
   <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="20%" nowrap="nowrap" class="textLabelBold">Requested Amt. (Rs):</td>
      <td width="15%" nowrap="nowrap" class="formLabel">
       <html:text name="frmPreAuthTariffDetail" property="reqAmount" readonly="true" styleClass="textBoxDisabled textBoxTiny"  maxlength="13" />
     <td width="20%">&nbsp;</td>
     <td width="45%">&nbsp;</td>
     </td>
    </tr>
    <tr>
      <td class="textLabelBold" nowrap="nowrap">Available Sum Insured (Rs):</td>
      <td class="formLabel">
       <html:text name="frmPreAuthTariffDetail" property="avaSuminsured" readonly="true" styleClass="textBoxDisabled textBoxTiny"  maxlength="13" />
     </td>
     <td class="textLabelBold" nowrap="nowrap">Available Bonus (Rs):</td>
      <td class="formLabel" nowrap="nowrap">
       <html:text name="frmPreAuthTariffDetail" property="avaBonus" readonly="true" styleClass="textBoxDisabled textBoxTiny"  maxlength="13" />
     </td>
    </tr>
    <tr>
      <td class="textLabelBold" nowrap="nowrap">Available Buffer Amt. (Rs):</td>
      <td class="formLabel" nowrap="nowrap">
       <html:text name="frmPreAuthTariffDetail" property="avaBuffer" readonly="true" styleClass="textBoxDisabled textBoxTiny"  maxlength="13" />
     </td>
     <td nowrap="nowrap" class="textLabelBold">Total Available Amt. (Rs):</td>
      <td nowrap="nowrap" class="formLabel">
       <html:text name="frmPreAuthTariffDetail" property="totAmt" readonly="true" styleClass="textBoxDisabled textBoxTiny"  maxlength="13" />
     </td>
    </tr>
	<!-- start Modification as per KOC 1140 ChangeRequest(SumInsuredRestriction)-1 -->
         <logic:equal name="frmPreAuthTariffDetail" property="familySIResAmtYN" value="Y">
          <tr>
      <td nowrap="nowrap" class="textLabelBold">Available SI Restricted Amt. (Rs):</td>
      <td nowrap="nowrap" class="formLabel">
       <html:text name="frmPreAuthTariffDetail" property="familySIResAmt" readonly="true" styleClass="textBoxDisabled textBoxTiny"  maxlength="13" />
     </td>
    </tr>    
     </logic:equal>
    <tr> <td>
  
     <input type="hidden" name="familySIResAmtYN" value="<bean:write name="frmPreAuthTariffDetail" property="familySIResAmtYN"/>"/>
   
    </td></tr>
      <!--  end Modification as per KOC 1140 ChangeRequest(SumInsuredRestriction)-1-->
         
    <tr>
      <td colspan="2" class="formLabel" height="10"></td>
    </tr>
  </table>
  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" style="border: 1px solid #cccccc; ">
    <tr class="borderBottom">
              <td width="21%" height="20" class="textLabelBold">Account Heads&nbsp;
              <%
	           if(TTKCommon.isAuthorized(request,"AccountHead"))
	      	   {
			 %>
              <a href="#"><img src="ttk/images/EditIcon.gif" border="0" class="icons" align="absmiddle" alt="Select Account Heads" title="Select Account Heads" onClick="javascript:onAccountHeader()"></a>
              <%
	      	   }//end of if(TTKCommon.isAuthorized(request,"AccountHead"))
	      	  %>
              </td>
			  <td width="18%" class="textLabelBold" align="left">Room Type</td> 
			  <td width="8%" class="textLabelBold" align="center">No.of Days</td>
              <td width="11%" align="center" class="textLabelBold">Req. Amt. <br>
              (Rs)</td>
              <td width="11%" align="center" class="textLabelBold">Appr. Amt. <br>
              (Rs) </td>
              <td width="11%" align="center" class="textLabelBold">Max. Amt. <br>
              (Rs) </td>
              <td width="20%" class="textLabelBold">Notes</td>
    </tr>
 <logic:notEmpty name="frmPreAuthTariffDetail" property="tariffInfo">
  <logic:iterate id="tariffInfo" name="frmPreAuthTariffDetail" property="tariffInfo" >
     <html:hidden name="tariffInfo" property="accGroupDesc"/>
          <tr>
                <td class="formLabel"><bean:write name="tariffInfo" property="wardDesc"/>
                 <html:hidden name="tariffInfo" property="wardDesc"/>
                </td>
                <td>                
                <logic:match name="tariffInfo" property="captureNbrOfDaysYN" value="Y">		          
		          <html:select name="tariffInfo" property="roomTypeID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
				        <html:option value="">Select from list</html:option>
						<html:optionsCollection name="roomsCodeList" label="cacheDesc" value="cacheId" />
			 	  </html:select>	
			 	  	<html:hidden name="tariffInfo" property="vaccineTypeID" value=""/>	 	<!-- //added for maternity -->		
		        </logic:match>
		        
		        <logic:notMatch name="tariffInfo" property="captureNbrOfDaysYN" value="Y">		          
		          <logic:equal name="tariffInfo" property="wardTypeID" value="VCE">	          
				          <html:select name="tariffInfo" property="vaccineTypeID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
						        <html:option value="">Select from list</html:option>
								<html:optionsCollection name="vaccineType" label="cacheDesc" value="cacheId" />
					 	  </html:select> 
					 	 </logic:equal>			 			
		        </logic:notMatch>
		        
		        </td>
		        <!-- //added for maternity -->	
				<td align="center" class="formLabel">
				<logic:equal name="tariffInfo" property="captureNbrOfDaysYN" value="Y">
                    <html:text name="tariffInfo" property="daysOfStay"  disabled="<%= viewmode %>" styleClass="textBox textBoxTiny" maxlength="13" />
                </logic:equal>
                </td>
                <logic:notMatch name="tariffInfo" property="captureNbrOfDaysYN" value="Y"><!-- //added for maternity -->	
                 
		         	<logic:notEqual name="tariffInfo" property="wardTypeID" value="VCE">
		         	<html:hidden name="tariffInfo" property="vaccineTypeID" value=""/>
		         	</logic:notEqual>
					<html:hidden name="tariffInfo" property="roomTypeID" value=""/>
		        	<html:hidden name="tariffInfo" property="daysOfStay" value=""/>
		       </logic:notMatch> 
		         <!-- //added for maternity -->	
		       
                <td align="center" class="formLabel">
                    <html:text name="tariffInfo" property="tariffRequestedAmt"  onblur="javascript:calcTotalReqAmt();" disabled="<%= viewmode %>" styleClass="textBox textBoxTiny" maxlength="13" />
                </td>
                <td align="center" class="formLabel">
                    <html:text name="tariffInfo" property="tariffApprovedAmt" onblur="javascript:calcTotalApprAmt();" disabled="<%= viewmode %>" styleClass="textBox textBoxTiny" maxlength="13" />
                </td>
                <td align="center" class="formLabel">
                  <html:text name="tariffInfo" property="tariffMaxAmtAllowed" tabindex="-1" readonly="true" styleClass="textBoxDisabled textBoxTiny" maxlength="13" />
                </td>
                <td class="textLabel"><bean:write name="tariffInfo" property="tariffNotes"/></td>
            </tr>
            <input type="hidden" name="selectedTariffSeqID" value="<bean:write  property='tariffSeqID' name='tariffInfo' />">
            <input type="hidden" name="selectedWardAccGroupSeqID" value="<bean:write property='wardAccGroupSeqID' name='tariffInfo'/>">
            <input type="hidden" name="selectedWardGroupSeqID" value="<bean:write property='wardGrpSeqID' name='tariffInfo'/>">
            <input type="hidden" name="captureNbrOfDaysYN" value="<bean:write property='captureNbrOfDaysYN' name='tariffInfo'/>">
	        <input type="hidden" name="selectedTariffNotes" value="<bean:write name='tariffInfo' property='tariffNotes'/>">
	        <input type="hidden" name="wardTypeID" value="<bean:write name='tariffInfo' property='wardTypeID'/>">
	        
    </logic:iterate>
  </logic:notEmpty>

          <tr class="borderTop">
              <td class="textLabelBold"> Total Amt. (Rs)</td>
              <td>&nbsp;</td>
			  <td>&nbsp;</td>
              <td align="center" class="formLabel">
                <html:text name="frmPreAuthTariffDetail" property="totReqAmt" readonly="true" tabindex="-1" styleClass="textBoxDisabled textBoxTiny" maxlength="13" />
                <html:hidden name="frmPreAuthTariffDetail" property="totHidReqAmt" />
              </td>
              <td align="center" class="formLabel">
               <html:text name="frmPreAuthTariffDetail" property="totApprAmt" readonly="true"  tabindex="-1" styleClass="textBoxDisabled textBoxTiny" maxlength="13" />
               <html:hidden name="frmPreAuthTariffDetail" property="totHidApprAmt" />
              </td>
              <td align="center" class="formLabel">
               <html:text name="frmPreAuthTariffDetail" property="totMaxAmt" readonly="true"  tabindex="-1" styleClass="textBoxDisabled textBoxTiny" maxlength="13"  />
               <html:hidden name="frmPreAuthTariffDetail" property="totHidMaxAmt" />
              </td>
              <td class="pageTitleInfo">&nbsp;</td>
        </tr>
  </table>

  <br>
  <logic:notEmpty name="frmPreAuthTariffDetail" property="ailmentInfo">
  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" style="border: 1px solid #cccccc; ">
    <tr class="borderBottom">
      		  <td width="58%" colspan="4" height="20" class="textLabelBold">Ailment(s)/Procedure(s)</td>
              <td width="10%" align="center" class="textLabelBold">Appr. Amt. 
                <br>
                (Rs) </td>
              <td width="11%" align="center" class="textLabelBold">&nbsp;</td>
              <td width="20%" class="textLabelBold">&nbsp;</td>
    </tr>
    <logic:iterate id="ailmentInfo" indexId="i" name="frmPreAuthTariffDetail" property="ailmentInfo" >
      <tr  style="background:#DDDDDD;"> 
            <td class="formLabel"><bean:write name="ailmentInfo" property="description"/><html:hidden name="ailmentInfo" property="description"/></td>
            <td align="center" class="formLabel">&nbsp;</td>
            <td align="center" class="formLabel">&nbsp;</td>
            <td align="center" class="formLabel">&nbsp;</td>
            <td align="center" class="formLabel">
            <html:text styleId="<%="approvedAilmentAmt"+i%>" name="ailmentInfo" property="approvedAilmentAmt" disabled="<%= viewmode %>" styleClass="textBox textBoxTiny" maxlength="13" onblur="javascript:calcAilAppAmt()"/>
            </td>
            <td align="center" class="formLabel">&nbsp;</td>
            <td class="textLabel">&nbsp;</td>
          </tr>
		          <logic:notEmpty name="ailmentInfo" property="procedureList">
			          <logic:iterate id="ailmentInfolist" indexId="j" name="ailmentInfo" property="procedureList">
				          <tr> 
				            <td class="formLabel">&nbsp;&nbsp;
				            <bean:write name="ailmentInfolist" property="procDesc" />
				            <input type="hidden" name="procDesc" value="<bean:write name="ailmentInfolist" property="procDesc" />">
				            <input type="hidden" name="procSeqID" value="<bean:write name="ailmentInfolist" property="procSeqID" />">
					        <input type="hidden" name="patProcSeqID" value="<bean:write name="ailmentInfolist" property="patProcSeqID" />">
					        <input type="hidden" name="asscICDPCSSeqID" value="<bean:write name="ailmentInfo" property="ICDPCSSeqID" />">
				            </td>
				            <td align="center" class="formLabel">&nbsp;</td>
				            <td align="center" class="formLabel">&nbsp;</td>
				            <td align="center" class="formLabel">&nbsp;</td>
				            <td align="center" class="formLabel">
				            <html:text styleId="<%="procedureAmt"+i+'~'+j%>" name="ailmentInfolist" property="procedureAmt" disabled="<%= viewmode %>" styleClass="textBox textBoxTiny" maxlength="13" indexed="ture" onblur="javascript:calcAilAppAmt()"/></td>
				            <td align="center" class="formLabel">&nbsp;</td>
				          </tr>				          
				       </logic:iterate>
		         </logic:notEmpty>          
            <input type="hidden" name="selectedAilmentCapsSeqID" value="<bean:write  name='ailmentInfo' property='ailmentCapsSeqID'/>">
            <input type="hidden" name="selectedICDPCSSeqID" value="<bean:write name='ailmentInfo' property='ICDPCSSeqID'/>">
            <input type="hidden" name="selectedAilmentNotes" value="<bean:write name='ailmentInfo' property='ailmentNotes'/>">
  	 </logic:iterate>
  </table>
   <html:hidden name="frmPreAuthTariffDetail" property="hidValidate" value="Y"/>
  </logic:notEmpty>
  </fieldset>
  <html:hidden name="frmPreAuthTariffDetail" property="totHidAilAppAmt"/>
    <!-- S T A R T : Buttons -->
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td width="100%" align="center">
           <logic:match name="viewmode" value="false">
            <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSubmitCalculate();"><u>C</u>alculate</button>&nbsp;
         <logic:match name="frmPreAuthTariffDetail" property="showSave" value="Y" >
 	         <button type="button" Id="SV" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUserSubmit1();"><u>S</u>ave</button>&nbsp;
          </logic:match>
          <logic:notMatch name="frmPreAuthTariffDetail" property="showSave" value="Y" >
    	      <button type="button" Id="SV" name="Button" accesskey="s" class="buttons" style="display:none;" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUserSubmit1();"><u>S</u>ave</button>&nbsp;
          </logic:notMatch>

            <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>
            </logic:match>
          </td>
    </tr>
  </table>
  <!-- E N D : Buttons -->
  </div>
   <!-- S T A R T : Form Fields -->
   <INPUT TYPE="hidden" NAME="mode" VALUE="">
  <input type="hidden" name="selectedTariffHdrSeqID" value="<bean:write property='tariffHdrSeqID' name='frmPreAuthTariffDetail'/>">

  <logic:notEmpty name="frmPreAuthTariffDetail" property="tariffInfo">
    <script language="javascript">
      //calcReqAmt();
     // calcAppAmt();
//      calcMaxAmt();
	calcTotalReqAmt();
	calcTotalApprAmt();
	
    </script>
  </logic:notEmpty>
<logic:notEmpty name="frmPreAuthTariffDetail" property="ailmentInfo">
    <script language="javascript">
     //calcAilAppAmt();

    </script>
  </logic:notEmpty>
  <script language="javascript">
			onDocumentLoad();
  </script>
   </html:form>

  <!-- E N D : Content/Form Area -->