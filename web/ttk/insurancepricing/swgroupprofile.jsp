<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.WebBoardHelper,java.util.Date,java.text.SimpleDateFormat"%>

<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta http-equiv="X-UA-Compatible" content="IE=10">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9"/>
	<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>

    <script type="text/javascript" src="/ttk/scripts/insurancepricing/swpricinghome.js"></script>	
     <script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>  
     <script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
	<script>
	var JS_SecondSubmit=false;
    var JS_Focus_Disabled =true;
    var JS_Focus_ID="<%=TTKCommon.checkNull(request.getAttribute("focusId"))%>";
 /*    $(document).ready(function(){
    	setInitialStates();
    }); commented by govind */ 
      
	</script>
	
<style>
.selectBoxMedium
{
  width:155px;
  height: 20px;
}

.selectasd
{
  width:155px;
  height: 20px;
  
}
.textBoxMediumPT1{
 width:153px;
}

.textBoxMediumPT{
 width:153px;
}


.selectBoxMedium1
{
  width:155px;
  height: 20px;
  margin-left: 21px;
}

.textBoxMediumPT2{
 width:153px;
 margin-left: 21px;
}

.selectBoxMedium2
{
  width:155px;
  height: 20px;
  margin-left: 66px;
}


.textBoxMediumPT3{
 width:153px;
 margin-left: 66px;
}
.textAreaLong1
{
  width:563px;
  height:25px;
  margin-left: 21px;
}
.textBoxMediumPT4{
 width:250px;
 margin-left: 21px;
}
</style>

     <SCRIPT LANGUAGE="JavaScript">
     
 	function alertMsg(){
		//alert('Govind');
		var dbMsg = document.getElementById("msg3").value;
		var code = document.getElementById("code").value;
		
		if( code == "Y" ){
			if (confirm(dbMsg)) {
				document.forms[1].tab.value ="Income Profile";
				document.forms[1].mode.value="doSave";
				   document.forms[1].action="/SwInsPricingActionAdd.do?singlebutton=saveProceed&validate=true";
				   //JS_SecondSubmit=true;
				   document.forms[1].submit();
			// document.getElementById('coverStartDate').value = dateString;
			 return true;
			} else {
			//  document.getElementById('coverStartDate').value="";
			} 
		 }
	
	}

     
  
     
      function freezePastPolicy(obj)
     {
    	  var sel = document.getElementById("portfloLevel");
    	  var sv = sel.options[sel.selectedIndex].value;
    	//  alert('freezePastPolicy');
    	//  alert("freezePastPolicy:"+sv);
    	
    	if(sv == ""){
    		
    		document.forms[1].previousPolicyNo.disabled=true;
    		document.forms[1].previousProductName.disabled=true;
    		document.forms[1].incomeGroup.disabled=true;
    		
    		document.forms[1].previousPolicyNo.value="";
    		document.forms[1].previousProductName.value="";
    		document.forms[1].incomeGroup.value="";
    		
    		document.getElementById('previousPolicyNo').style.backgroundColor="#d9dce0";
    		document.getElementById('previousProductName').style.backgroundColor="#d9dce0";
    		document.getElementById('incomeGroup').style.backgroundColor="#d9dce0";
    		return true;
    	}
     	 
    //	alert('Sv is space??');
     	 if(sv != "PL")
     		{
     			
     		 $(".spanPrevPolNum").attr("style","display:none");
     		 document.forms[1].previousPolicyNo.value="";
      		document.forms[1].previousPolicyNo.disabled=true;
      		document.getElementById('previousPolicyNo').style.backgroundColor="#d9dce0";
      		document.forms[1].previousProductName.disabled=false;
      		document.getElementById('previousProductName').style.backgroundColor="white";
      		fetchPastProdNm();
     		 
     		}else if(sv == "PL"){

     			$(".spanPrevPolNum").removeAttr("style");
     			document.forms[1].previousPolicyNo.disabled=false;
     			document.getElementById('previousPolicyNo').style.backgroundColor="white";
     			document.forms[1].previousProductName.value="";
     			document.forms[1].previousProductName.disabled=true;
     			document.getElementById('previousProductName').style.backgroundColor="#d9dce0";
     			
     		} 
     	 

     }//end of edit(rownum)
     
     function freezePastPolicyOnLOad()
     {
    	 document.forms[1].previousPolicyNo.disabled=false;
 		document.forms[1].previousProductName.disabled=false;
 		document.forms[1].incomeGroup.disabled=false;
 		
 		document.getElementById('previousPolicyNo').style.backgroundColor="white";
		document.getElementById('previousProductName').style.backgroundColor="white";
		document.getElementById('incomeGroup').style.backgroundColor="white";
    	  
    	//  alert('freezePastPolicy');
    	  var sel = document.getElementById("portfloLevel");
    	  var sv = sel.options[sel.selectedIndex].value;
    	// alert('val:'+sv);
    	if(sv == ""){
    		
    		 $(".spanPrevPolNum").attr("style","display:none");
    		document.forms[1].previousPolicyNo.disabled=true;
    		document.forms[1].previousProductName.disabled=true;
    		document.forms[1].incomeGroup.disabled=true;
    		
    		document.forms[1].previousPolicyNo.value="";
    		document.forms[1].previousProductName.value="";
    		document.forms[1].incomeGroup.value="";
    		
    		document.getElementById('previousPolicyNo').style.backgroundColor="#d9dce0";
    		document.getElementById('previousProductName').style.backgroundColor="#d9dce0";
    		document.getElementById('incomeGroup').style.backgroundColor="#d9dce0";
    		
    	}
    	else if(sv!= "PL"){
    		 $(".spanPrevPolNum").attr("style","display:none");
     		 document.forms[1].previousPolicyNo.value="";
     		document.forms[1].previousPolicyNo.disabled=true;
     		document.getElementById('previousPolicyNo').style.backgroundColor="#d9dce0";
     		
     	}
     	else{
     		$(".spanPrevPolNum").removeAttr("style");
     		
     		document.forms[1].previousPolicyNo.disabled=false;
     		
     	}
     	
     //	fetchPastProdNm(sv)
     }//end of edit(rownum)
	
    
     
     function fetchPastProdNm(obj)
     {
    	 //alert("fetchPastProdNm");
    	 resetAutoPopulateData();
    	 var flag='';
    	 var selportfloLevel = document.getElementById("portfloLevel");
    	 //alert("selportfloLevel======="+selportfloLevel);
   	  var portFolioLevel = selportfloLevel.options[selportfloLevel.selectedIndex].value;
   	// alert("portFolioLevel========"+portFolioLevel);
    	  
     
   //   var ppn1 = document.getElementById("previousPolicyNo").value;
    	// alert('fetchPastProdNm'); 
    	var ppn;
    	 if (document.getElementById('previousPolicyNo') != null && document.getElementById('previousPolicyNo')!= undefined) {
    		 ppn = document.getElementById("previousPolicyNo").value;
    		 //alert("ppn-------"+ppn);
    		}
     		if(ppn!=''){
     			 flag='PL';
     			document.forms[1].previousProductName.disabled=false;
          		document.getElementById('previousProductName').style.backgroundColor="white";
     		}else if(portFolioLevel == 'SME'){
     			flag='SME';
     		}else if(portFolioLevel == 'DHA'){
     			flag='DHA';
     		}
     		//alert('fetchPastProdNm:');
     		// var providerAuthority= document.getElementById("authorityType").value;
     		var  myselect1=document.getElementById("previousProductName");
     		
     		while (myselect1.hasChildNodes()) {   
     	   	    myselect1.removeChild(myselect1.firstChild);
     	     }
     		var  path= "";
     		 myselect1.options.add(new Option("Select from list",""));
 
     			path="/asynchronAction.do?mode=getPreviousProd&previousPolicyNo="+ppn+"&flag="+flag;
                    

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
     		
     
       
     }//end of edit(rownum)
     
     function resetAutoPopulateData(){
    	 
    	 document.getElementById("inpatientBenefit").value="";
    	 document.getElementById("outpatientBenefit").value="";
    	 document.getElementById("maxBenifitList").value="";
    	 document.getElementById("networkList").value="";
    	 document.getElementById("maternityInpatient").value="";
    	 document.getElementById("maternityOutpatient").value="";
    	 document.getElementById("othKeyCov").value="";
    	 onchangeMaxBenefitType(this);
    	 validateIP();
    	 validateOP();
    	 validateMaternityIP();
    	 validateMaternityOP();
    	 
    	 
    	 
     }
     
     function enableIncomeData(){
    		
    		//alert("enableIncomeData========");
    		var previousProductName = document.getElementById("previousProductName").value;
    		//alert("previousProductName========"+previousProductName);
    		if(previousProductName != ""){
    			document.forms[1].incomeGroup.disabled=false;
    			document.getElementById("incomeGroup").style.backgroundColor="white";
    		}else{
    			//alert("previousProductName1========"+previousProductName);
    			document.forms[1].incomeGroup.value="";
    			document.forms[1].incomeGroup.disabled=true;
    			document.getElementById("incomeGroup").style.backgroundColor="#d9dce0";
    			
    		}
    		
    	}
     
</SCRIPT>
</head>



<bean:define id="tempAuthID" property="authorityType" name="frmSwPricing"  />
<bean:define id="tempPPN" property="previousPolicyNo" name="frmSwPricing"  />
<bean:define id="tempNetwork" property="networkList" name="frmSwPricing"  />
<%
	boolean viewmode=true;
	boolean bEnabled=false;
	boolean viewmode1=true;
	String strSubmissionType="";
	String ampm[] = {"AM","PM"};
	
	boolean blnAmmendmentFlow=false;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
		viewmode1=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	    pageContext.setAttribute("ampm",ampm);	 
	    pageContext.setAttribute("viewmode",new Boolean(viewmode));	 
	    pageContext.setAttribute("incomeGroup", Cache.getCacheObject("incomeGroup"));  
	    pageContext.setAttribute("maxBenifitList", Cache.getCacheObject("maxBenifitList")); 
	    
 	    pageContext.setAttribute("networkList", Cache.getCacheObject("networkList")); 	
 	    pageContext.setAttribute("alroomType", Cache.getCacheObject("roomType1")); 	
 	    pageContext.setAttribute("maxChildAgeLimit", Cache.getCacheObject("maxChildAgeLimit")); 	
 	    pageContext.setAttribute("companionCharges", Cache.getCacheObject("companionCharges"));  
 	    pageContext.setAttribute("additionalHospitalCoverage", Cache.getCacheObject("additionalHospitalCoverage"));  
 	    pageContext.setAttribute("consultationCD", Cache.getCacheObject("consultationCD"));  
 	    pageContext.setAttribute("drugsCopay", Cache.getCacheObject("drugsCopay"));
 	   pageContext.setAttribute("drugsLimit", Cache.getCacheObject("drugsLimit"));
 	    pageContext.setAttribute("laboratoryCD", Cache.getCacheObject("laboratoryCD"));  
 	    pageContext.setAttribute("physiotherapyLimit", Cache.getCacheObject("physiotherapyLimit")); 
 	    /* pageContext.setAttribute("physiotherapyCopay", Cache.getCacheObject("physiotherapyCopay"));  */ 
 	    pageContext.setAttribute("normalDeliveryLimit", Cache.getCacheObject("normalDeliveryLimit"));  
 	    pageContext.setAttribute("normalDeliveryLimitOth", Cache.getCacheObject("normalDeliveryLimitOth"));  
 	    pageContext.setAttribute("physiotherapyCopay", Cache.getCacheObject("physiotherapyCopay"));  
 	    pageContext.setAttribute("CSectionLimit", Cache.getCacheObject("CSectionLimit"));  
 	    pageContext.setAttribute("CSectionLimitOth", Cache.getCacheObject("CSectionLimitOth")); 
 	    
 	    pageContext.setAttribute("loadingForInpatient", Cache.getCacheObject("loadingForInpatient")); 
 	    pageContext.setAttribute("commentsInpatient", Cache.getCacheObject("commentsInpatient")); 
 	    /* pageContext.setAttribute("outpatientAreaOfCover", Cache.getCacheObject("outpatientAreaOfCover"));  */
 	    pageContext.setAttribute("loadingForOutpatient", Cache.getCacheObject("loadingForOutpatient")); 
 	    pageContext.setAttribute("commentsOutpatient", Cache.getCacheObject("commentsOutpatient"));
 	   pageContext.setAttribute("inpatientAreaOfCover", Cache.getCacheObject("inpatientAreaOfCover"));
 	   
 	  pageContext.setAttribute("maternityOutpatientCopay", Cache.getCacheObject("maternityOutpatientCopay"));
 	  pageContext.setAttribute("maternityInpatientCopay", Cache.getCacheObject("maternityInpatientCopay"));
 	  pageContext.setAttribute("maternityOutpatientVisits", Cache.getCacheObject("maternityOutpatientVisits"));
 	  pageContext.setAttribute("dinpatientCopay", Cache.getCacheObject("dinpatientCopay"));
 	   pageContext.setAttribute("inpatientCopay", Cache.getCacheObject("inpatientCopay"));
 	  pageContext.setAttribute("premiumRefundApplicable", Cache.getCacheObject("premiumRefundApplicable"));
 	  pageContext.setAttribute("inpatientBenefit", Cache.getCacheObject("inpatientBenefit"));
 	  pageContext.setAttribute("outpatientBenefit", Cache.getCacheObject("outpatientBenefit"));
 	  //b
 	  pageContext.setAttribute("renewalYN", Cache.getCacheObject("renewalYN"));
 	  pageContext.setAttribute("administrativeServiceType", Cache.getCacheObject("administrativeServiceType"));
 	  pageContext.setAttribute("authorityType", Cache.getCacheObject("authorityType"));
 	//  pageContext.setAttribute("insuranceCompanyName", Cache.getCacheObjectIns("insuranceCompanyName","MOH"));
 	//  pageContext.setAttribute("insuranceCompanyName", Cache.getCacheObject("insuranceCompanyName"));
 	  pageContext.setAttribute("maternityPricing", Cache.getCacheObject("maternityPricing"));
 	 pageContext.setAttribute("existCompProd1", Cache.getCacheObject("existCompProd1"));
 	pageContext.setAttribute("existCompProd2", Cache.getCacheObject("existCompProd2"));
	boolean network=false;
	
	
     //System.out.println("tempPPN--------"+tempPPN);
	
	if(tempAuthID==null||"".equals(tempAuthID))tempAuthID="DHA";
	pageContext.setAttribute("AuthInsuranceCompanies",Cache.getDynamicCacheObject("AuthInsurance", (String)tempAuthID));
	
	 if(tempPPN==null||"".equals(tempPPN))tempPPN="";
	pageContext.setAttribute("previousProductName",Cache.getDynamicCacheObject("PreviousProduct", (String)tempPPN)); 
	
	if(tempNetwork==null||"".equals(tempNetwork))tempNetwork="";
	pageContext.setAttribute("outpatientAreaOfCover",Cache.getDynamicCacheObject("outpatientAreaOfCover", (String)tempNetwork));
%>

	  
	<% 
	String insCompNm=(String) request.getSession().getAttribute("insCmpNm");
	%>
	<logic:notEmpty name="frmSwPricing" property="authorityType">
	
	</logic:notEmpty> 
	
	
<html:form action="/SwInsPricingActionWeb.do" method="post" enctype="multipart/form-data"> 
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="65%"></td> 
		  </tr>
		  	<tr>
	    	 <td><bean:write name="frmSwPricing" property="caption"/></td>
			 <td align="right" class="webBoard">
			 		<logic:notEmpty name="frmSwPricing" property="groupProfileSeqID">
			 		<%@ include file="/ttk/common/toolbar.jsp" %>
			 		</logic:notEmpty >
			 </td>
		</tr>	  
		  
	</table>
		
	<div class="contentArea" id="contentArea">
	<html:errors/>
		<logic:notEmpty name="frmSwPricing"	property="alertmsgscreen1">
				<table align="center" class="errorContainer" border="0"	cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Warning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmSwPricing" property="alertmsgscreen1" /></td>
					</tr>
				</table>
			</logic:notEmpty>
			
			<logic:notEmpty name="frmSwPricing"	property="pricingNumberAlert">
				<table align="center" class="errorContainer" border="0"	cellspacing="0" cellpadding="0">
					<tr>
						<td><img src="/ttk/images/warning.gif" alt="Warning"
							width="16" height="16" align="absmiddle">&nbsp; <bean:write
								name="frmSwPricing" property="pricingNumberAlert" /></td>
					</tr>
				</table>
			</logic:notEmpty>
	<%-- 		
				<logic:notEmpty name="frmSwPricing"	property="errorCode">
				<logic:equal name="frmSwPricing" property="errorCode" value="MSG">
				
				
				<table align="center" class="errorContainer" style="display:; color=#FF0000;" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td><strong><img src="/ttk/images/ErrorIcon.gif" alt="Error"
							width="16" height="16" align="absmiddle">&nbsp;The following errors have occurred - </strong>
							<ol style="padding:0px;margin-top:3px;margin-bottom:0px;margin-left:25px;"><li style="list-style-type:decimal"> <bean:write
								name="frmSwPricing" property="errorMsg" /></li></ol></td>
					</tr>
				</table>
				</logic:equal>
				
			</logic:notEmpty> --%>
			
   
   	<logic:notEmpty name="errorMsg" scope="request">
    <table align="center" class="errorContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/ttk/images/ErrorIcon.gif" alt="Error" width="16" height="16" align="middle" >&nbsp;
          <bean:write name="errorMsg" scope="request" />
          </td>
      </tr>
    </table>
   </logic:notEmpty>
	
	<!-- S T A R T : Success Box -->

		<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="middle">&nbsp;
						<bean:message name="updated" scope="request"/>
				  </td>
				</tr>
			</table>
		</logic:notEmpty>
		<logic:notEmpty name="successMsg" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="middle">&nbsp;
						<bean:write name="successMsg" scope="request"/>
				  </td>
				</tr>
			</table>
		</logic:notEmpty> 
		
		 <!-- S T A R T : Form Fields -->
	
	<fieldset>
			<legend>Corporate details</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			
				   <tr>
			       
			      <td class="formLabel" width="30%">New client/renewal  :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="renewalYN" styleId="renewalYN"  styleClass="selectBox selectBoxMedium" onchange="showMandatory()">	
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="renewalYN" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	
			      <td class="formLabel"  width="30%"> Pricing reference number :</td>
			      <td class="formLabelspecial"><b> <bean:write name="frmSwPricing" property="pricingRefno"/> </b><html:hidden styleId="pricingRefno" property="pricingRefno" /></td>
			      
			      </tr>
	
			  
			     <tr> 
			      
			      <td class="formLabel" width="30%">Client code :<span class="mandatorySymbol">*</span></td> <!-- <span class="mandatorySymbol">*</span> -->
			      <td class="textLabel">
			     <html:text name="frmSwPricing" styleId ="clientcodeId" property="clientCode" readonly="true" styleClass="textBox textBoxSmall" />
			      <html:link  href="javascript:changeCorporate();"  ><img src="/ttk/images/EditIcon.gif" alt="Change Corporate"  width="16" height="16" border="0" align="absmiddle"></html:link>
			      </td>	
			      
			       <td class="formLabel" width="30%">Client name :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
			     <html:text name="frmSwPricing"  property="clientName" styleId="clientName" readonly="" styleClass="textBox textBoxMediumPT" /> <!-- readonly="true" -->
			      </td>	
			      
			  </tr>
			  
			  <tr>
			   
			       <td class="formLabel" width="30%">Authority type :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="authorityType" styleId="authorityType" onchange="getInsuranceCompanyName()" styleClass="selectBox selectBoxMedium">	
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="authorityType" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	 
			      
			     
			      <td class="formLabel" width="30%">Administrative service type :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="administrativeServiceType" styleId="administrativeServiceType"  styleClass="selectBox selectBoxMedium">	
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="administrativeServiceType" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	

			
			  </tr>
			  
			     	    <tr>
			    	    
			    	    		      
			      <td class="formLabel" width="30%">Insurance company name :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">					  
					    <html:select name="frmSwPricing" property="insuranceCompanyName"  styleId="insuranceCompanyName" onchange="enableInsCompNmOth();"  styleClass="selectBox selectBoxMedium">	
				      	<html:option value="">Select from list</html:option>						
							  <%-- <html:optionsCollection name="insuranceCompanyName" value="cacheId" label="cacheDesc"/>  --%> 
							  <html:options collection="AuthInsuranceCompanies" property="cacheId" labelProperty="cacheDesc"/>
					  </html:select> 
			      </td>	
			   
			      <td class="formLabel" width="30%">Insurance company name (Others) :<span class="mandatorySymbol spanIncCompOth">*</span></td>
			      <td class="textLabel">
				    	 <html:text name="frmSwPricing" property="insuranceCompanyNameOth"  styleId="insuranceCompanyNameOth"   styleClass="textBox textBoxMediumPT disabledOption" />  <!--  -->

			      </td>
				
			    </tr>
			  
			      <tr>
			   
			   <td class="formLabel" width="30%">Existing comparable product - 1 :</td> <!-- <span class="mandatorySymbol">*</span> -->
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="existCompProd1" styleId="existCompProd1"  styleClass="selectBox selectBoxMedium" onchange="enableExistCompPrd2();">	
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="existCompProd1" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	
			      
			      <td class="formLabel" width="30%">Existing comparable product - 2 :</td> <!-- <span class="mandatorySymbol">*</span> -->
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="existCompProd2" styleId="existCompProd2" onchange="chkExistCompPrd2();"  styleClass="selectBox selectBoxMedium disabledOption">	
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="existCompProd2" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	
			    </tr> 
			  
			  <tr>
			   <td class="formLabel"  width="30%">Coverage start date :<span class="mandatorySymbol">*</span></td>
			      <td  width="30%" class="textLabel">	
			      <html:text property="coverStartDate" styleId="coverStartDate" onfocus="javascript:warnFutureDate();" onchange="dateValidate(this);javascript:warnFutureDate();" name="frmSwPricing" styleClass="textBox textDate" maxlength="10"/>
			      <A NAME="coverStartDate" ID="coverStartDate" HREF="#" onClick="javascript:show_calendar('coverStartDate','frmSwPricing.coverStartDate',document.frmSwPricing.coverStartDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
			       </td>
			      	
			     <td class="formLabel" width="30%">Coverage end date :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
				    <html:text property="coverEndDate" styleId="coverEndDate" name="frmSwPricing" styleClass="textBox textDate" maxlength="10" disabled="" readonly="" onchange="dateValidate(this);"/>
			      <A NAME="coverEndDate" ID="coverEndDate" HREF="#" onClick="javascript:show_calendar('coverEndDate','frmSwPricing.coverEndDate',document.frmSwPricing.coverEndDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>
			      
			      </td>
			     </tr>   
			 
			    
			    <tr>
			    
			       <td class="formLabel" width="30%">Total covered lives :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
				    	 <html:text name="frmSwPricing" property="totalCovedLives"  styleId="totalCovedLives"  onkeyup="isNumericOnlyPT(this);"   styleClass="textBox textBoxMediumPT" />  <!-- onchange="validateLives(this);gettotallivesType();" -->

			      </td>
			    
			    </tr>
			    
			    
			    </table>
			   </fieldset><br>

			   
			   
			   
	 		   <fieldset>
			<legend>Details to extract past policy experience</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td class="formLabel" width="29%">Pricing at portfolio/policy level? :<span class="mandatorySymbol spanportfloLevel">*</span></td>  
			      <td class="textLabel" align="left" width="29.5%">
								  
					   <html:select name="frmSwPricing" property="portfloLevel"  styleId="portfloLevel"  styleClass="selectBox selectBoxMedium"  onblur ="freezePastPolicy(this);">	
				      	<html:option value="">Select from list</html:option>
				      	<html:option value="SME">Portfolio level - SME</html:option>
				      	<html:option value="DHA">Portfolio level - DHA Basic</html:option>
				      	<html:option value="PL">Policy level</html:option>						
					  </html:select>
			      </td>	
	    <td class="formLabel"  width="29%">Past policy number :<span class="mandatorySymbol spanPrevPolNum">*</span></td>
			      <td   class="textLabel">		      
			     <html:text name="frmSwPricing" styleId="previousPolicyNo" title="" property="previousPolicyNo" readonly="" styleClass="textBox textBoxMediumPT" onchange="fetchPastProdNm(this);" /> <!-- onblur ="fetchPastProdNm(this.blur);" -->
			      </td>	
			   
			     	
		</tr>

			   <tr>
			     
			       <td class="formLabel">Past product name :</td>  <!-- <span class="mandatorySymbol">*</span> -->
			       <td class="textLabel">
			        <html:select name="frmSwPricing" property="previousProductName" styleId="previousProductName"  styleClass="selectBox selectBoxMedium disabledOption" onblur="enableIncomeData();">	
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="previousProductName" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>
			      
			      <td class="formLabel">Income group for which the past data should be extracted :</td>  <!-- <span class="mandatorySymbol">*</span>  -->
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="incomeGroup" styleId="incomeGroup"  styleClass="selectBox selectBoxMedium disabledOption">	
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="incomeGroup" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	
			      </tr>
			      <tr>
			      <td colspan="3"></td>
			      <td  align="right">
			      
			      <button type="button" name="Button" accesskey="a" class="buttonsPricingLarge" onMouseout="this.className='buttonsPricingLarge'" onMouseover="this.className='buttonsPricingLarge buttonsHover'"	onClick="fetchScreen1();">Fetch Details</button>&nbsp;
			      
			      </td>
			   </tr> 
			   
			   </table>
			   </fieldset><br> 
			   
			   <!-- bikki -->
			   <fieldset>
			   <legend>Key coverages</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			      <td class="formLabel" width="30%">Inpatient (IP) :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel" width="30%">
								  
					   <html:select name="frmSwPricing" property="inpatientBenefit" onchange="validateIP()" styleId="inpatientBenefit" styleClass="selectBox selectBoxMedium">	
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="inpatientBenefit" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	
			   
			       <td class="formLabel" width="30%" align="left">Outpatient (OP) :<span class="mandatorySymbol">*</span></td><!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
			      <td class="textLabel" width="30%" align="right">
								  
					   <html:select name="frmSwPricing" property="outpatientBenefit" onchange="validateOP()"  styleId="outpatientBenefit" styleClass="selectBox selectBoxMedium">	
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="outpatientBenefit" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	
			      
		</tr>
		 <tr>
		 
	              <td class="formLabel">Maximum benefit limit (MBL) :<span class="mandatorySymbol">*</span></td><!-- width="30%" -->
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="maxBenifitList" styleId="maxBenifitList" onchange="onchangeMaxBenefitType(this)" styleClass="selectBox selectBoxMedium">	
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="maxBenifitList" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	

			   
			       <td class="formLabel"  width="30%" >Maximum benefit limit (Others) :<span class="mandatorySymbol spanmaxBeneLimitOth">*</span></td><!--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  -->
			      <td  width="30%" class="textLabel" align="right">		      
			     <html:text name="frmSwPricing"  property="maxBeneLimitOth" styleId="maxBeneLimitOth" readonly="" onkeyup="isNumericOnlyPT(this);" onchange="onchangeMaxBenefitLimit(this)" styleClass="textBox textBoxMediumPT disabledOption"  /> <!-- onkeyup="isNumericOnlyPT(this);"  -->
			      </td>	
			   
		</tr>
			   <tr>
	              <td class="formLabel" width="30%">Network :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="networkList" styleId="networkList" styleClass="selectBox selectBoxMedium" onchange="onChngNtwrk();onChngNtwrkToOp();" >	<!--  -->
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="networkList" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	
			   </tr>   
			   
			      
 </table>
 </fieldset><br>
			   <!-- end bikki -->
			<fieldset>
			   <legend>Inpatient</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			      <td class="formLabel" width="30%">Inpatient area of cover :<span class="mandatorySymbol spanipAreaCov">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="inpatientAreaOfCover" styleId="inpatientAreaOfCover" styleClass="selectBox selectBoxMedium disabledOption">	
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="inpatientAreaOfCover" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	
			   
			       <td class="formLabel"  width="30%">Inpatient area of cover variations (if any) :</td><!-- <span class="mandatorySymbol">*</span> -->
			      <td  width="30%" class="textLabel">		      
			     <html:text name="frmSwPricing"  property="inpatientAreaOfCoverVariations" styleId="inpatientAreaOfCoverVariations" readonly="" styleClass="textBox textBoxMediumPT disabledOption" onchange="onChangeIpAreaOfCov();" />
			      </td>	
		</tr>

		 <tr>
	              <td class="formLabel"  width="30%">Loading for inpatient area of cover variations :<span class="mandatorySymbol spanloadingForIp">*</span></td> <!--  -->
			      <td  width="30%" class="textLabel">		      
			     <html:text name="frmSwPricing"  property="loadingForInpatient"  onkeyup="isNumericPT(this);" styleId="loadingForInpatient" readonly="" styleClass="textBox textBoxMediumPT disabledOption" onchange="onchangeLdIpCovVar();"  /><b style="color: black;">%</b>
			      </td>	
			      <!-- value="0" -->
			   
			       <td class="formLabel"  width="30%">Comments (inpatient area of cover loading) :<span class="mandatorySymbol spancommIp">*</span></td>
			      <td  width="30%" class="textLabel">		      
			     <html:text name="frmSwPricing"  property="commentsInpatient" styleId="commentsInpatient" readonly="" styleClass="textBox textBoxMediumPT disabledOption"  />
			      </td>	
			      	
		</tr>
			   <tr>
	              <td class="formLabel"  width="30%">Room type :<span class="mandatorySymbol spanroomType1">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="roomType1" styleId="roomType" styleClass="selectBox selectBoxMedium disabledOption">	
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="alroomType" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	
			      
			   
			       
			      <td class="formLabel"  width="30%">Inpatient copay :<span class="mandatorySymbol spaninpatientCopay">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="inpatientCopay" styleId="inpatientCopay" styleClass="selectBox selectBoxMedium disabledOption">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="inpatientCopay" value="cacheId" label="cacheDesc"/>					 
             			 </html:select>
			      </td>	
		</tr>
		<tr>
	              <td class="formLabel"  width="30%">Max child age limit for companion eligibility :<span class="mandatorySymbol spanmaxChildAgeLimit">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="maxChildAgeLimit" styleId="maxChildAgeLimit" styleClass="selectBox selectBoxMedium disabledOption" onchange="chlAgeLmtForCompEgbl(this)">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="maxChildAgeLimit" value="cacheId" label="cacheDesc"/>					 
             			 </html:select>
			      </td>	
			   
			       <td class="formLabel"  width="30%">Companion charges per day limit :<span class="mandatorySymbol spancompanionCharges">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="companionCharges" styleId="companionCharges" styleClass="selectBox selectBoxMedium disabledOption">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="companionCharges" value="cacheId" label="cacheDesc"/>			
             			</html:select>
			      </td>	
		</tr>
 </table>
 </fieldset><br>
			   
			   
			  <fieldset >
			<legend>Outpatient</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    
			      <td class="formLabel" width="30%">Outpatient area of cover :<span class="mandatorySymbol spanopAreaOfCov">*</span></td>
			      <td class="textLabel" width="25%" >
								  
					   <html:select name="frmSwPricing" property="outpatientAreaOfCover" styleId="outpatientAreaOfCover" styleClass="selectBox selectBoxMedium disabledOption" onchange="onChngOpAreaCov();">	
				      	<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="outpatientAreaOfCover" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	
			   
			       <td class="formLabel"  width="30%">Outpatient area of cover variations (if any) :</td>  <!-- <span class="mandatorySymbol">*</span> -->
			      <td  width="24%" class="textLabel">		      
			     <html:text name="frmSwPricing"  property="outpatientAreaOfCoverVariations" styleId="outpatientAreaOfCoverVariations" readonly="" styleClass="textBox textBoxMediumPT disabledOption" onchange="onChangeOpAreaOfCov();" />
			      </td>	
		</tr>

		 <tr>
	              <td class="formLabel"  width="30%">Loading for outpatient area of cover variations :<span class="mandatorySymbol spanldForOp">*</span></td>
			      <td  width="30%" class="textLabel">		      
			     <html:text name="frmSwPricing"  property="loadingForOutpatient" styleId="loadingForOutpatient"  onkeyup="isNumericPT(this);" readonly="" styleClass="textBox textBoxMediumPT disabledOption" onchange="onchangeLdOpCovVar();"  /><b style="color: black;">%</b>
			      </td>	
			      <!--  value="0" -->
			   
			       <td class="formLabel"  width="30%" >Comments (outpatient area of cover loading) :<span class="mandatorySymbol spancommOp">*</span></td>
			      <td  width="30%" class="textLabel">		      
			     <html:text name="frmSwPricing"  property="commentsOutpatient" styleId="commentsOutpatient" readonly="" styleClass="textBox textBoxMediumPT disabledOption"  />
			      </td>	
			      
		</tr>
			   <tr>
	              
		 <td class="formLabel"  width="30%">Additional hospital coverage :</td>
		  <td  width="30%" class="textLabel">		      
			     <html:text name="frmSwPricing"  property="additionalHospitalCoverage" styleId="additionalHospitalCoverage" onclick="compNtwrk();" onchange="addHsplCov();" readonly="" styleClass="textBox textBoxMediumPT disabledOption"  />
			      </td>	
			      

			      
			            <td class="formLabel"  width="30%">Loading for additional hospital coverage :<span class="mandatorySymbol spanldForAddHosp">*</span></td>
			      <td  width="30%" class="textLabel">		      
			     <html:text name="frmSwPricing"  property="loadingForAddHospital" styleId="loadingForAddHospital" readonly="" styleClass="textBox textBoxMediumPT disabledOption" onchange="onChangeAddHospCov();" /><b style="color: black;">%</b>
			      </td>	
			      
			    
		</tr>
		<tr>
	              <td class="formLabel"  width="30%">Comments (loading for additional hospital coverage) :<span class="mandatorySymbol spanhosComments">*</span></td>
			      <td  width="30%" class="textLabel">		      
			     <html:text name="frmSwPricing"  property="hosComments" styleId="hosComments" readonly="" styleClass="textBox textBoxMediumPT disabledOption"  />
			      </td>	
			   
			       <td class="formLabel"  width="30%">Consultation copay/deductible :<span class="mandatorySymbol spanconsultCD">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="consultationCD" styleId="consultationCD" styleClass="selectBox selectBoxMedium disabledOption">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="consultationCD" value="cacheId" label="cacheDesc"/>					  </html:select>
			      </td>	
		</tr>
		<tr>
	              <td class="formLabel"  width="30%">Drugs limit :<span class="mandatorySymbol spandrugsLimit">*</span></td>
			      
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="drugsLimit" styleId="drugsLimit" styleClass="selectBox selectBoxMedium disabledOption"  onchange="onchangeDrugsLimit(this)">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="drugsLimit" value="cacheId" label="cacheDesc"/>					  </html:select>
			      </td>	
			   
			       <td class="formLabel"  width="30%">Drugs limit (Others) :<span class="mandatorySymbol spandrugsLimitOth">*</span></td>
			      <td  width="30%" class="textLabel">		      
			     <html:text name="frmSwPricing"  property="drugsLimitOth" styleId="drugsLimitOth" readonly="" onkeyup="isNumericOnlyPT(this);" styleClass="textBox textBoxMediumPT disabledOption"  />
			      </td>	
			      
		</tr>
		<tr>
	              <td class="formLabel"  width="30%">Drugs copay :<span class="mandatorySymbol spandrugsCopay">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="drugsCopay" styleId="drugsCopay" styleClass="selectBox selectBoxMedium disabledOption">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="drugsCopay" value="cacheId" label="cacheDesc"/>					  
             			</html:select>
			      </td>		
			   
			       <td class="formLabel"  width="30%">Laboratory copay/deductible  :<span class="mandatorySymbol spanlaboratoryCD">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="laboratoryCD" styleId="laboratoryCD" styleClass="selectBox selectBoxMedium disabledOption">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="laboratoryCD" value="cacheId" label="cacheDesc"/>					 
             			 </html:select>
			      </td>		
		</tr>
		<tr>
	              <td class="formLabel"  width="30%">Physiotherapy limit :<span class="mandatorySymbol spanphysiLmt">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="physiotherapyLimit" styleId="physiotherapyLimit" styleClass="selectBox selectBoxMedium disabledOption">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="physiotherapyLimit" value="cacheId" label="cacheDesc"/>					
             			  </html:select>
			      </td>	
			   
			       <td class="formLabel"  width="30%">Physiotherapy copay   :<span class="mandatorySymbol spanphysiCpy">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="physiotherapyCopay" styleId="physiotherapyCopay" styleClass="selectBox selectBoxMedium disabledOption">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="physiotherapyCopay" value="cacheId" label="cacheDesc"/>
			  </html:select>
			      </td>		
		</tr>
		<tr>
	              
			      <td class="formLabel"  width="30%" >Direct specialist access (valid only for OP)   :<span class="mandatorySymbol spanDIpCpy">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="dinpatientCopay" styleId="dinpatientCopay" styleClass="selectBox selectBoxMedium disabledOption" onchange="onChngDirSpclAccess();">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="dinpatientCopay" value="cacheId" label="cacheDesc"/>
			  </html:select>
			      </td>		
			   
			       <td class="formLabel"  width="30%">Comments (direct specialist access) :<span class="mandatorySymbol spancommentsDirect">*</span></td>
			      <td  width="30%" class="textLabel">		      
			     <html:text name="frmSwPricing"  property="commentsDirect" styleId="commentsDirect" readonly="" styleClass="textBox textBoxMediumPT disabledOption"  />
			      </td>	
		</tr>
 </table>
 </fieldset>
			   
			  
<br>
 
 <fieldset>
			<legend>Maternity</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    <td class="formLabel" width="30%">Maternity Inpatient :<span class="mandatorySymbol spanipAreaCov1">*</span></td>
			      <td class="textLabel">
								  
					    <html:select name="frmSwPricing" property="maternityInpatient" styleId="maternityInpatient" onchange="validateMaternityIP()" styleClass="selectBox selectBoxMedium1">  <!-- onchangeOutpatient(this,'N');  -->
							  <html:option value="">Select from list</html:option>
						 	  <html:option value="Y">Covered</html:option>
							  <html:option value="N">Not Covered</html:option>
					  </html:select>
			      </td>	
			   	
			   	<td class="formLabel" width="30%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			        	Maternity Outpatient :<span class="mandatorySymbol spanopAreaOfCov1">*</span></td>  
			            <td class="textLabel">
					       <html:select name="frmSwPricing" property="maternityOutpatient" styleId="maternityOutpatient" onchange="validateMaternityOP()" styleClass="selectBox selectBoxMedium2"> <!-- onchangeOutpatient(this,'N');  -->
							  <html:option value="">Select from list</html:option>
						 	  <html:option value="Y">Covered</html:option>
							  <html:option value="N">Not Covered</html:option>
					  </html:select>
			      </td>	
		</tr>

		 <tr>
	              <td class="formLabel"  width="30%">Total covered lives eligible for maternity :<span class="mandatorySymbol spanttlLivMat">*</span></td>
			      <td  width="30%" class="textLabel">	      
			     <html:text name="frmSwPricing"  property="totalLivesMaternity" styleId="totalLivesMaternity" onkeyup="isNumericOnlyPT(this);"  readonly="" styleClass="textBox textBoxMediumPT2 disabledOption"/> <!-- onchange="validateMatCovLives()" -->
			      </td>	
			   
			      
			      <td class="formLabel"  width="30%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			            Maternity IP and OP pricing  :<span class="mandatorySymbol spanMatIpOp">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="maternityPricing" styleId="maternityPricing" styleClass="selectBox selectBoxMedium2">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="maternityPricing" value="cacheId" label="cacheDesc"/>		
             			 </html:select>
			      </td>	
		</tr>
			   <tr>
	             
			      <td class="formLabel" id="maternityOutpatientVisitsLabel"  width="30%">Maternity outpatient visits  :<span class="mandatorySymbol spanMatOpVisit">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="maternityOutpatientVisits" styleId="maternityOutpatientVisits" styleClass="selectBox selectBoxMedium1">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="maternityOutpatientVisits" value="cacheId" label="cacheDesc"/>		
             			 </html:select>
			      </td>	
			   
			       
			     <td class="formLabel" id="maternityOutpatientCopayLabel"  width="30%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       Maternity outpatient copay  :<span class="mandatorySymbol spanMatOpCpy">*</span></td><td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="maternityOutpatientCopay" styleId="maternityOutpatientCopay" styleClass="selectBox selectBoxMedium2">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="maternityOutpatientCopay" value="cacheId" label="cacheDesc"/>		
             			 </html:select>
			      </td>	
		</tr>
		
		<tr>
	              <td class="formLabel" id="normalDeliveryLimitLabel"  width="30%">Normal delivery limit  :<span class="mandatorySymbol spanNrmDelLmt">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="normalDeliveryLimit" styleId="normalDeliveryLimit" styleClass="selectBox selectBoxMedium1" onchange="onchangeNmlDlivLmt(this)">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="normalDeliveryLimit" value="cacheId" label="cacheDesc"/>		
             			 </html:select>
			      </td>	
			   
			       <td class="formLabel"  width="30%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			       Normal delivery limit (Others) :<span class="mandatorySymbol spanNrmDelLmtOth">*</span></td>
			      <td  class="textLabel">      
			     <html:text name="frmSwPricing"  property="normalDeliveryLimitOth" styleId="normalDeliveryLimitOth" onkeyup="isNumericOnlyPT(this);" onchange="onChngNmlDlvLmtOth();" readonly="" styleClass="textBox textBoxMediumPT3 disabledOption"  />
			      </td>	
			      
		</tr>
		<tr>
	              <td class="formLabel" id="CSectionLimitLabel" width="30%">C - section limit  :<span class="mandatorySymbol spanCSecLmt">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="CSectionLimit" styleId="CSectionLimit" styleClass="selectBox selectBoxMedium1" onchange="onchangeCSecLmt(this)">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="CSectionLimit" value="cacheId" label="cacheDesc"/>					
             			  </html:select>
			      </td>		
			   
			       
			      <td class="formLabel"  width="30%">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			      C- section limit (Others) :<span class="mandatorySymbol spanCSecLmtOth">*</span></td>
			      <td class="textLabel">      
			     <html:text name="frmSwPricing"  property="CSectionLimitOth" readonly="" styleId="CSectionLimitOth" onkeyup="isNumericOnlyPT(this);" onchange="onChngCsecLmtOth();" styleClass="textBox textBoxMediumPT3 disabledOption"  />
			      </td>	
			      	
		</tr>
		<tr>
	              
			      <td class="formLabel" id="maternityInpatientCopayLabel"  width="30%">Maternity inpatient copay  :<span class="mandatorySymbol spanMatIpCpy">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="maternityInpatientCopay" styleId="maternityInpatientCopay" styleClass="selectBox selectBoxMedium1">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="maternityInpatientCopay" value="cacheId" label="cacheDesc"/>		
             			 </html:select>
			      </td>	
		</tr>
		<tr> <td colspan="5" >&nbsp;</td> </tr>
			    <tr>
					<td class="formLabel" id="othKeyCov">Other key coverages : </td>
			    <td nowrap colspan="4">	<html:textarea  property="comments" styleClass="textBox textAreaLong1" />
			    	</td>
			    	<td></td>
			    	
			    	</tr>
			    	 <tr><td></td>
			    <td colspan="4">
					<font color=black><i><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Note :</b>Any other coverages which are not covered in tool right now e.g Dental may be considered by the user later in 'Loadings and Management' section. </i></font> 
				</td>
			    </tr>

			    <tr>
			     
			      <td class="formLabel"  width="30%">Premium refund applicable? :<span class="mandatorySymbol spanPRA">*</span></td>
			      <td class="textLabel">
								  
					   <html:select name="frmSwPricing" property="premiumRefundApplicable" styleId="premiumRefundApplicable" styleClass="selectBox selectBoxMedium1 disabledOption" onchange="valPremRefnd()">	
				      	<html:option value="">Select from list</html:option>						
             			<html:optionsCollection name="premiumRefundApplicable" value="cacheId" label="cacheDesc"/>		
             			 </html:select>
			      </td>	
			
			    </tr>
			    <tr>
			       <td class="formLabel" width="30%">Trend :</td>
			      <td class="textLabel">
				   	<html:text name="frmSwPricing" property="trendFactor" value="5" disabled="true" styleId="trendFactor"  onkeyup="isNumeric(this);" onchange="validateTrendPercent();"  styleClass="textBox textBoxMediumPT2" /><b style="color: black;">%</b>
			      </td>
			      <!-- value="5" -->
			    </tr> 
			    
			    <!-- for pdf -->
			    <tr><td colspan="4">&nbsp;</td></tr>
			    	<tr>
			    	<td height="20" class="formLabel">Source documents/other attachments :</td>
	    			<td class="textLabelBold"><html:file property="file1" styleId="file1" styleClass="textBoxMediumPT4"  /> </td>
	    			<%-- <td colspan="2"><html:link href="javascript:onViewDocument('file1');"><img src="/ttk/images/ModifiedIcon.gif" alt="Pricing Source Documents" width="16" height="16" border="0" align="absmiddle"></html:link> --%>
	    			<td colspan="2"><html:link href="javascript:onViewDocument();"><img src="/ttk/images/ModifiedIcon.gif" alt="Pricing Source Documents" width="16" height="16" border="0" align="absmiddle"></html:link>
			    	 <bean:write name="frmSwPricing" property="attachmentname1"/></td>
			    </tr>
			    
			    <tr>
			    	<td></td>
	    			<td class="textLabelBold"><html:file property="file2" styleId="file2" styleClass="textBoxMediumPT4"/> </td>
	    			<%-- <td colspan="2" ><html:link href="javascript:onViewDocument('file2');"><img src="/ttk/images/ModifiedIcon.gif" alt="Pricing Source Documents" width="16" height="16" border="0" align="absmiddle"></html:link> --%>
	    			<td colspan="2" ><html:link href="javascript:onViewDocument2();"><img src="/ttk/images/ModifiedIcon.gif" alt="Pricing Source Documents" width="16" height="16" border="0" align="absmiddle"></html:link>
			    	<bean:write name="frmSwPricing" property="attachmentname2"/></td>
			    </tr>
			    
			    <tr>
			  		 <td></td>
	    			<td class="textLabelBold"><html:file property="file3" styleId="file3" styleClass="textBoxMediumPT4"/> </td>
	    			<%-- <td colspan="2" ><html:link href="javascript:onViewDocument('file3');"><img src="/ttk/images/ModifiedIcon.gif" alt="Pricing Source Documents" width="16" height="16" border="0" align="absmiddle"></html:link> --%>
	    			<td colspan="2" ><html:link href="javascript:onViewDocument3();"><img src="/ttk/images/ModifiedIcon.gif" alt="Pricing Source Documents" width="16" height="16" border="0" align="absmiddle"></html:link>
	    			 <bean:write name="frmSwPricing" property="attachmentname3"/></td>
			    </tr>
			    
			    <tr>
			    	<td></td>
	    			<td class="textLabelBold"><html:file property="file4" styleId="file4" styleClass="textBoxMediumPT4"/> </td>
			    	<%-- <td colspan="2" ><html:link href="javascript:onViewDocument('file4');"><img src="/ttk/images/ModifiedIcon.gif" alt="Pricing Source Documents" width="16" height="16" border="0" align="absmiddle"></html:link> --%>
			    	<td colspan="2" ><html:link href="javascript:onViewDocument4();"><img src="/ttk/images/ModifiedIcon.gif" alt="Pricing Source Documents" width="16" height="16" border="0" align="absmiddle"></html:link>
			    	 <bean:write name="frmSwPricing" property="attachmentname4"/></td>
			    	
			    	<td></td>
			    </tr>
			    
			    <tr>
			    	<td></td>
	    			<td class="textLabelBold"><html:file property="file5" styleId="file5" styleClass="textBoxMediumPT4"/> </td>
	    		     <%-- <td colspan="2" >	<html:link href="javascript:onViewDocument('file5');"><img src="/ttk/images/ModifiedIcon.gif" alt="Pricing Source Documents" width="16" height="16" border="0" align="absmiddle"></html:link> --%>
	    		     <td colspan="2" >	<html:link href="javascript:onViewDocument5();"><img src="/ttk/images/ModifiedIcon.gif" alt="Pricing Source Documents" width="16" height="16" border="0" align="absmiddle"></html:link>
	    			 <bean:write name="frmSwPricing" property="attachmentname5"/></td>
			    	
			    	<td></td>
			    </tr>
 </table>
 </fieldset><br>
 
 
  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			    
			    <tr>
			  <td colspan="4" align="center">
		
            <%
             if(TTKCommon.isAuthorized(request,"Edit")) {
             %>
             <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onPartialSave('save')">Pa<u>r</u>tial save</button>&nbsp;
            <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave('save')"><u>S</u>ave</button>&nbsp;
         	<button type="button" name="Button2" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"  onClick="onSave('saveProceed')"><u>P</u>roceed >></button>&nbsp; <!--  onClick="onIncomeprofile()" -->
         
          <%
            }
          %>
			  </td>
			  </tr>			  
			  </table>
			  
       
      
      <fieldSet>
      
      			 <table align="center" class="buttonsContainer" border="1" cellspacing="0" cellpadding="0">
			
  				<tr>
  				<td class="formLabel" >UAE  = All UAE</td>
  				<td class="formLabel" >Govt. = Government</td>
  				<td class="formLabel" >Cent pvt = Central Private hospital</td>
  				</tr>
  				<tr>
  				<td class="formLabel" >UAE A = All UAE excluding Abu Dhabi and Al Ain</td>
  				<td class="formLabel" >C = Clinics</td>
  				<td class="formLabel" >SP. Med Care = Specialised Medical Care hospital</td>
  				</tr>
  				<tr>
  				<td class="formLabel" >ISC = All ISC </td>
  				<td class="formLabel" >P = Pharmacy</td>
  				<td class="formLabel" >NMC DEIRA = NMC Deira hospital </td>
  				</tr>
  				<tr>
  				<td class="formLabel" >ISC A = All ISC excluding Maldives</td>
  				<td class="formLabel" >D = Diagnostic centre</td>
  				<td class="formLabel" >Zahr = Zahrawi hospital </td>
  				
  				</tr>
  				<tr>
  				<td class="formLabel" >SEA = All SEA</td>
  				<td class="formLabel" >S = Specialist physician clinic</td>
  				<td class="formLabel" >NMC(S) = NMC Speciality hospital </td>
  				
  				</tr>
  				<tr>
  				<td class="formLabel" >SEA A = All SEA excluding Timor-Leste, Cambodia</td>
  			     <td class="formLabel" >LLH = LLH hospital </td>
  			     <td class="formLabel" >BELH(E) = Belhoul European hospital </td>
  				
  				</tr>
  				<tr>
  				<td class="formLabel" >WW = Worldwide</td>
  					<td class="formLabel" >Tawm = Tawam hospital</td>
  					<td class="formLabel" >BELH(S) = Belhoul Speciality hospital </td>
  				
  				</tr>
  				<tr>
  				<td class="formLabel" >GCC = Gulf countries</td>
  				<td class="formLabel" >IR = Iranian hospital</td>
  				<td class="formLabel" >THU Al-Nah = Thumbay Al Nahda hospital </td>
  				</tr>
  				
  				<!-- bk -->
  				<tr>
  				<td class="formLabel" >N = Network</td>
  				<td class="formLabel" >CJA = Cedar Jebel Ali hospital</td>
  				<td class="formLabel" >Skh Khal = Sheikh Khalifa medical</td>
  				</tr>
  				
  				<tr>
  				<td class="formLabel" >NN = Non Network</td>
  				<td class="formLabel" >Nat = National hospital</td>
  				<td class="formLabel" >Reimb. = Reimbursement</td>
  				</tr>
  				
  				<tr>
  				<td class="formLabel" >HOS = Hospital</td>
  				<td class="formLabel" >GMC = GMC hospital</td>
  				<td class="formLabel" >-</td>
  				</tr>

  			
			</table>
      
      </fieldSet>  
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="child" value="">
	<html:hidden property="groupProfileSeqID" />
	<html:hidden property="newdataentry" />
	<INPUT TYPE="hidden" NAME="fetchData" id="fetchData" VALUE='<%=request.getAttribute("fetchData") %>'/>
	<INPUT TYPE="hidden" NAME="msg3" id="msg3" VALUE='<%=session.getAttribute("msg3") %>'/>
	<INPUT TYPE="hidden" NAME="code" id="code" VALUE='<%=session.getAttribute("errorCode") %>'/>
	<INPUT TYPE="hidden" NAME="dvVal" id="dvVal" VALUE='<%=session.getAttribute("dvVal") %>'/>
	
	 <html:hidden property="attachmentname1" styleId="attachmentname1" name="frmSwPricing"/>
	  <html:hidden property="attachmentname2" styleId="attachmentname2" name="frmSwPricing"/>	
	   <html:hidden property="attachmentname3" styleId="attachmentname3" name="frmSwPricing"/>	
	   <html:hidden property="attachmentname4" styleId="attachmentname4" name="frmSwPricing"/>	
	   <html:hidden property="attachmentname5" styleId="attachmentname5" name="frmSwPricing"/>		
	</div>		
</html:form>
 	<script type="text/javascript">

	freezePastPolicyOnLOad();
	validateIP();
	validateOP();
	validateMaternityIP();
	validateMaternityOP();
	onchangeMaxBenefitType(this);
	onchangeDrugsLimit(this);
	onchangeNmlDlivLmt(this);
	onchangeCSecLmt(this);
	validateInsComp();
    onChangeIpAreaOfCov(); 
	onChangeOpAreaOfCov();
	onChangeAddHospCov();
	chkExistCompPrd2();
	showMandatory();
	chlAgeLmtForCompEgbl(this);
	onChngDirSpclAccess();
	addHsplCov();
	enableInsCompNmOth();
	ntworkToAddHospCov();
	onChngOpAreaCov();
	onChngNtwrkToOp();
	onchangeLdIpCovVarOnLoad();
	onchangeLdOpCovVarOnLoad();
	
	
	
	
</script>

<%-- 	onChngNtwrk();	<script type="text/javascript">
				
				alert(<%=session.getAttribute("errorMsg") %>);
				function alertMsg(){
					alert('Govind');
					alert(<%=session.getAttribute("errorMsg") %>);
				}
<logic:notEmpty name="frmSwPricing"	property="errorCode">
				
				<logic:equal name="frmSwPricing" property="errorCode" value="ALERT">
				
				<% 
				
				System.out.println("Hare govind:");
				%>
					
				alertMsg();
				
				
				
				</logic:equal>	
			</logic:notEmpty>  --%>
