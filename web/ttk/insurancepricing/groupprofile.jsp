<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper"%>

	<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
    <script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="/ttk/scripts/insurancepricing/pricinghome.js"></script>	
     <script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>  
<!-- 	<script>
	function getProviderInvoiceNO(){
		document.forms[2].providerInvoiceNO.value='';
		 var claimSeqID= document.forms[2].previousClaimNO.value;
		 if(claimSeqID===null || claimSeqID==="" || claimSeqID.length<1)return;
     var  path="/asynchronAction.do?mode=getProviderInvoiceNO&claimSeqID="+claimSeqID;		                 

	 $.ajax({
	     url :path,
	     dataType:"text",
	     success : function(data) {
	    	 document.forms[2].providerInvoiceNO.value=data;
	     }
	 });

}//getAreas
   </script> -->
<style>

</style>
</head>
<body>
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
	pageContext.setAttribute("groupCurrency",Cache.getCacheObject("groupCurrency"));
	pageContext.setAttribute("noofEmployees",Cache.getCacheObject("noofEmployees"));
	pageContext.setAttribute("averageAgeEmployees", Cache.getCacheObject("averageAgeEmployees"));
	pageContext.setAttribute("employeeGenderBreak", Cache.getCacheObject("employeeGenderBreak"));
	pageContext.setAttribute("viewmode",new Boolean(viewmode));	
	pageContext.setAttribute("globalCoverge", Cache.getCacheObject("globalCoverge"));
	pageContext.setAttribute("familyCoverage", Cache.getCacheObject("familyCoverage"));
	pageContext.setAttribute("groupCountry", Cache.getCacheObject("groupCountry"));
	pageContext.setAttribute("ethnicProfile", Cache.getCacheObject("ethnicProfile"));
	pageContext.setAttribute("nameOfInsurer", Cache.getCacheObject("nameOfInsurer"));
	pageContext.setAttribute("nameOfTPA", Cache.getCacheObject("nameOfTPA"));
	pageContext.setAttribute("eligibility", Cache.getCacheObject("eligibility"));
	pageContext.setAttribute("planName", Cache.getCacheObject("planName"));
	pageContext.setAttribute("areaOfCover", Cache.getCacheObject("areaOfCover"));
	
	boolean network=false;
%>

<html:form action="/InsPricingActionAdd.do" >

		
	<div class="contentArea" id="contentArea">
	<html:errors/>
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
			<legend>Group Profile</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			
				 <tr>
			    <td class="formLabel" width="19%">Name of Insurer :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
				      <html:select name="frmPricing" property="nameOfInsurer" styleClass="selectBox selectBoxMedium">
							<html:option value="">Select From List</html:option>
							<html:optionsCollection name="nameOfInsurer" label="cacheDesc" value="cacheId" />
					  </html:select>
			      </td>
			      <td class="formLabel" width="19%">Eligibility :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
				      <html:select name="frmPricing" property="eligibility" styleClass="selectBox selectBoxMedium">	
				      		<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="eligibility" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	
			      </tr>
			
			    <tr>
			    <tr>
			    <td class="formLabel" width="19%">Area of Cover :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
				      <html:select name="frmPricing" property="areaOfCover" styleClass="selectBox selectBoxMedium">
							<html:option value="">Select From List</html:option>
							<html:optionsCollection name="areaOfCover" label="cacheDesc" value="cacheId" />
					  </html:select>
			      </td>
			      <td class="formLabel" width="19%">No Of Family Members covered :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
			      <html:text name="frmPricing" property="familyCoverage" styleClass="textBox textBoxMedium" />	
				      <%-- <html:select name="frmPricing" property="familyCoverage" styleClass="selectBox selectBoxMedium">	
				      		<html:option value="">Select from list</html:option>						
							<html:optionsCollection name="familyCoverage" value="cacheId" label="cacheDesc"/>
					  </html:select> --%>
			      </td>	
			      </tr>
			      
			       <tr>
			    <td class="formLabel">Group Name :<span class="mandatorySymbol">*</span></td>
			      <td  width="30%" class="textLabel">		      
			      <html:text name="frmPricing" property="groupName" styleClass="textBox textBoxLarge" />
			      </td>	
			   <td class="formLabel">No Of Employees :<span class="mandatorySymbol">*</span></td>
			      <td  width="30%" class="textLabel">	
			       <html:text name="frmPricing" property="noofEmployees" styleClass="textBox textBoxMedium" />	      
			     <%-- <html:select name="frmPricing" property="noofEmployees" styleClass="selectBox selectBoxMedium">
			     <html:option value="">Select from list</html:option>							
							<html:optionsCollection name="noofEmployees" value="cacheId" label="cacheDesc"/>
					  </html:select> --%>
			      </td>	
			      	
			  </tr>
			  <tr>
			       
			      <td class="formLabel" width="19%">Plan Name :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
				      <html:select name="frmPricing" property="planName" styleClass="selectBox selectBoxMedium">	
				      <html:option value="">Select from list</html:option>						
							<html:optionsCollection name="planName" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>
			      <td class="formLabel" width="19%">Average Age of Group :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
				      <html:select name="frmPricing" property="averageAge" styleClass="selectBox selectBoxMedium">
							<html:option value="">Select From List</html:option>
							<html:optionsCollection name="averageAgeEmployees" label="cacheDesc" value="cacheId" />
					  </html:select>
			      </td>
			    </tr>
			    <tr>
			      <td class="formLabel" width="19%">Currency :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
				      <html:select name="frmPricing" property="currency" styleClass="selectBox selectBoxMedium">	
				      <html:option value="">Select from list</html:option>						
							<html:optionsCollection name="groupCurrency" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	
			       <td class="formLabel" width="19%">Gender Males  %  :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
				      <html:select name="frmPricing" property="employeeGender" styleClass="selectBox selectBoxMedium">	
				      <html:option value="">Select From List</html:option>						
							<html:optionsCollection name="employeeGenderBreak" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>
			    </tr>
			    <tr>
			      <td class="formLabel" width="19%">Name of TPA :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
				      <html:select name="frmPricing" property="nameOfTPA" styleClass="selectBox selectBoxMedium">	
				      	<html:option value="">Select From List</html:option>						
							<html:optionsCollection name="nameOfTPA" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>	
			       <td class="formLabel" width="19%">Display Additional premium for Dependents :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
				       <html:select name="frmPricing" property="additionalPremium" styleClass="selectBox selectBoxMedium"  >
							  <html:option value="">Select from list</html:option>
							  <html:option value="Y">Yes</html:option>
							  <html:option value="N">No</html:option>
					  </html:select>
			      </td>	
			    </tr>
			    <tr>
			      <td class="formLabel" width="19%">Takaful Quote :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
				       <html:select name="frmPricing" property="takafulQuote" styleClass="selectBox selectBoxMedium"  >
							  <html:option value="">Select from list</html:option>
							  <html:option value="Y">Yes</html:option>
							  <html:option value="N">No</html:option>
					  </html:select>
			      </td>	
			      <td class="formLabel" width="19%">Global Coverage :<span class="mandatorySymbol">*</span></td>
			      <td class="textLabel">
				      <html:select name="frmPricing" property="globalCoverge" styleClass="selectBox selectBoxMedium">	
				      <html:option value="">Select from list</html:option>						
							<html:optionsCollection name="globalCoverge" value="cacheId" label="cacheDesc"/>
					  </html:select>
			      </td>
			    </tr>
			   
			    
			    <tr>
			  <td colspan="4" align="center">
		
            <%
             if(TTKCommon.isAuthorized(request,"Edit")) {
             %>
            <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
          <%
            }
          %>
			<button type="button" name="Button2" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onIncomeprofile()"><u>P</u>roceed >></button>&nbsp;
			  </td>
			  </tr>			  
			  </table>
		</fieldset>

<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="child" value="">
	<html:hidden property="groupProfileSeqID" />
</html:form>

</div>
</body>
</html>


