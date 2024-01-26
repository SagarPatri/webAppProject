<%
/** @ (#) productgeneralinfo.jsp 14th Nov 2005
 * Project     : TTK Healthcare Services
 * File        : productgeneralinfo.jsp
 * Author      : Arun K N
 * Company     : Span Systems Corporation
 * Date Created: 14th Nov 2005
 *
 * @author 			Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<bean:define id="tempAuthID" property="healthAuthority" name="frmProductGeneralInfo"  />
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
	pageContext.setAttribute("alProductTypeCode",Cache.getCacheObject("productTypeCode"));
//	pageContext.setAttribute("listInsuranceCompany",Cache.getCacheObject("insuranceCompany")); 
	pageContext.setAttribute("healthAuthorities",Cache.getCacheObject("healthAuthorities"));
	pageContext.setAttribute("alProductStatusCode",Cache.getCacheObject("productStatusCode"));
	pageContext.setAttribute("CardTemplateID",Cache.getCacheObject("template"));
	pageContext.setAttribute("VoucherRequired",Cache.getCacheObject("voucherRequired"));
	pageContext.setAttribute("alInsCompany",Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("alAuthLetterType",Cache.getCacheObject("notificinfo"));
	pageContext.setAttribute("surgerytypeMandatory",Cache.getCacheObject("surgerytypeMandatoryYN"));
	pageContext.setAttribute("productNetworkTypes",Cache.getCacheObject("primaryNetwork"));
	
	if(tempAuthID==null||"".equals(tempAuthID))tempAuthID="DHA";
	pageContext.setAttribute("AuthInsuranceCompanies",Cache.getDynamicCacheObject("AuthInsurance", (String)tempAuthID));
	pageContext.setAttribute("alProductSubType",Cache.getCacheObject("productSubType"));
%>

	
<script type="text/javascript" src="/ttk/scripts/validation.js"></script>
<script  type="text/javascript" src="/ttk/scripts/administration/productgeneralinfo.js"></script>
<head>
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	<link rel="stylesheet" type="text/css" href="css/autoComplete.css" />
	<script language="javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script>
	<script language="javascript" src="/ttk/scripts/jquery.autocomplete.js"></script>
<SCRIPT>
  $(document).ready(function() {
    $("#productName").autocomplete("auto.jsp?mode=productName");
});  
  
  function chg() {
	  document.forms[1].templateID.value="";
}

</SCRIPT>
</head>
<!-- S T A R T : Content/Form Area -->
<html:form action="/EditProductAction.do" method="post">

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    	<tr>
      		<td><%-- <%=tempAuthID %>| --%>Product Details - <bean:write name="frmProductGeneralInfo" property="caption" /></td>
      		<td align="right">
	      		<logic:notEmpty name="frmProductGeneralInfo" property="prodPolicySeqID">
					<a href="#" onClick="onConfiguration()"><img src="/ttk/images/EditIcon.gif" alt="Configuration List" title="Configuration List" width="16" height="16" border="0" align="absmiddle"></a>
				</logic:notEmpty >
			</td>
        	<td align="right" class="webBoard">&nbsp;<logic:match name="frmProductGeneralInfo" property="caption" value="Edit"><%@ include file="/ttk/common/toolbar.jsp" %></logic:match></td>
      	</tr>
	</table>
	<!-- E N D : Page Title -->
	
	<div class="contentArea" id="contentArea">
		<html:errors/>

		<!-- S T A R T : Success Box -->
	 	<logic:notEmpty name="updated" scope="request">
	  		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   			<tr>
	     			<td>
	     				<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
	         			<bean:message name="updated" scope="request"/>
	     			</td>
	   			</tr>
	  		</table>
	 	</logic:notEmpty>
 		<!-- E N D : Success Box -->

		<!-- S T A R T : Form Fields -->
		<fieldset>
			<legend>General</legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	  			<tr>
	    			<td width="20%" class="formLabel">Product Type:  <span class="mandatorySymbol">*</span></td>
	    			<td width="30%">
	    	 			<html:select property="prodTypeId" styleClass="selectBox" disabled="<%= viewmode %>">
                  			<html:option value="">Select from list</html:option>
                  			<html:options collection="alProductTypeCode" property="cacheId" labelProperty="cacheDesc"/>
            			</html:select>
	    			</td>
	    			<td width="20%">&nbsp;</td>
	    			<td width="30%">&nbsp;</td>
	  			</tr>
	  			<tr>
	    			<td class="formLabel">Product  Name: <span class="mandatorySymbol">*</span></td>
	    			<td>
	    				<html:text property="productName" styleClass="textBox textBoxLarge" maxlength="250" disabled="<%= viewmode %>" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);"/>
	    			</td>
	    			<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	  			</tr>
	  			<tr>
	  				<td class="formLabel">Product Code: <span class="mandatorySymbol">*</span></td>
	  				<td>
	  					<html:text property="insProductCode" styleClass="textBox textBoxMedium" maxlength="10" disabled="<%= viewmode %>"/>
	  				</td>
	  				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	  			</tr>
	  			
	  			
	  			
	  			
	  			<tr>
	    			<td class="formLabel">Pre-defined/SME product   :<span class="mandatorySymbol">*</span> </td>
	    			<td colspan="3">
	       				<html:select property="SMEproductYN" name="frmProductGeneralInfo" styleClass="selectBox" disabled="<%= viewmode %>" onchange="selectPolicySubType();">
                  			<html:option value="">Select from list</html:option>
                  			<html:option value="Y">Individual</html:option>
                  			<html:option value="N">Corporate</html:option>
            			</html:select>
        			</td>
	  			</tr>			
	  			<tr>
	    			<td width="20%" class="formLabel">Product Sub Type: <span class="mandatorySymbol">*</span></td>
	    			<td width="30%">
	    	 			<html:select property="productSubtype" styleClass="selectBox" >
                  			<html:option value="">Select from list</html:option>
                  			<html:options collection="alProductSubType" property="cacheId" labelProperty="cacheDesc"/>
            			</html:select>
	    			</td>
	    			<td width="20%">&nbsp;</td>
	    			<td width="30%">&nbsp;</td>
	  			</tr>
	  			<tr>
	    			<td class="formLabel">Product Network Type:<span class="mandatorySymbol">*</span> </td>
	    			<td colspan="3">
	       				<html:select property="productNetworkType" name="frmProductGeneralInfo" styleClass="selectBox" disabled="<%= viewmode %>">
                  			<html:option value="">Select from list</html:option>
                  			<html:options collection="productNetworkTypes" property="cacheId" labelProperty="cacheDesc"/>
            			</html:select>
        			</td>
	  			</tr>
	  			<tr>
	    			<td class="formLabel">Description:</td>
	    			<td colspan="3">
		    			<html:textarea property="prodDesc" styleClass="textBox textAreaLong" disabled="<%= viewmode %>"/>
		    		</td>
	    		</tr>
	  			<tr>
	    			<td class="formLabel">
	    			Health Authority:
	    			<span class="mandatorySymbol">*</span></td>
	    			<td colspan="3">
	       				<html:select property="healthAuthority" styleId="healthAuthority" styleClass="selectBox" disabled="<%= viewmode %>" onchange="getInsuranceCompany()">
                  			<html:option value="">Select from list</html:option>  
                  			<html:options collection="healthAuthorities" property="cacheId" labelProperty="cacheDesc"/>
            			</html:select>
        			</td>
	  			</tr>
	  			<!-- AK -->
	  				<tr>
	    			<td class="formLabel">Insurance Company: <span class="mandatorySymbol">*</span></td>
	    			<td colspan="3">
	       				<html:select property="insSeqId"  styleId="insSeqId"  styleClass="selectBox"  disabled="<%= viewmode %>" onchange="changeInsCmp()">
                  			<html:option value="">Select from list</html:option>
								<html:optionsCollection name="frmProductGeneralInfo" property="alInsuranceCompany" label="cacheDesc" value="cacheId" />
						<%--			
								<logic:equal name="frmProductGeneralInfo" property="insFlag" value="YES">
								<html:optionsCollection name="frmProductGeneralInfo" property="alInsuranceCompany" label="cacheDesc" value="cacheId" />
								</logic:equal>
								
								<logic:notEqual name="frmProductGeneralInfo" property="insFlag" value="YES">
								<html:options collection="listInsuranceCompany" property="cacheId" labelProperty="cacheDesc"/>
								</logic:notEqual>
						--%>		
									
            			</html:select>
        			</td>
	  			</tr>
	  		
	  	
	  			<tr>
	    			<td class="formLabel">Status: </td>
	    			<td colspan="3">
	         			<html:select property="prodStatTypeId" styleClass="selectBox" style="width:160px;" disabled="<%= viewmode %>">
                  			<html:options collection="alProductStatusCode" property="cacheId" labelProperty="cacheDesc"/>
            			</html:select>
      				</td>
	  			</tr>
	  			<tr>
	  			
      				<td class="formLabel">Card Template: </td>
    				<td colspan="3">
    					<html:select property="templateID"   styleClass="selectBox selectBoxMedium"  disabled="<%= viewmode %>">
							<html:option value="">Select from list</html:option>
							<logic:notEmpty name="frmProductGeneralInfo" property="cardTempList">
  							<html:optionsCollection name="frmProductGeneralInfo" property="cardTempList" label="cacheDesc" value="cacheId" />
  							</logic:notEmpty>
  						 	</html:select>
					</td>
					
						
      			</tr>
      			
      			
      			
      			
      				<tr>
	    			<td class="formLabel">Premium to be refunded on policy cancellation: <span class="mandatorySymbol">*</span> </td>
	    			<td colspan="3">
	       				<html:select property="refundedYN" name="frmProductGeneralInfo" styleClass="selectBox" disabled="<%= viewmode %>">
                  			<html:option value="">Select from list</html:option>
                  				<html:option value="Y">YES</html:option>
                  					<html:option value="N">NO</html:option>
            			</html:select>
            			
            			<logic:notEmpty name="frmProductGeneralInfo" property="prodSeqId">
            			  &nbsp; <a href="#" onclick="modificationRemarks(240,530);"><b>Modification Remarks</b></a>
            			  </logic:notEmpty>
        			</td>
        			
	  			</tr>			
	  			
      			
      			
      			
      			
      			
      			<tr>
	    			<td class="formLabel">Discharge Voucher: </td>
	    			<td>
	    				<html:select property="dischargeVoucherMandatoryYN"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
							<html:option value="">Select from list</html:option>
  							<html:options collection="VoucherRequired"  property="cacheId" labelProperty="cacheDesc"/>
	    				</html:select>
	    			</td>
	    			<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	  			</tr>
	  			
	  			<tr>
	  				<td class="formLabel">Tenure:</td>
	  				<td>
	  					<html:text property="tenure" styleClass="textBox textBoxSmall" maxlength="3" disabled="<%= viewmode %>"/>
	  				</td>
	  				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	  			</tr>
	  			<tr>
	  				<td class="formLabel">Auth Letter Type:</td>
	  				<td>
						<html:select property="authLtrTypeID" styleClass="selectBox" disabled="<%= viewmode %>">
                  			<html:options collection="alAuthLetterType" property="cacheId" labelProperty="cacheDesc"/>
            			</html:select>
	  				</td>
	  				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	  			</tr>
	 			<tr>
	 				<td class="formLabel">Stop Cashless:</td>	 				
	  				<td>	  					
	 					<html:checkbox property="stopPreAuthsYN" styleId="stopPreAuthsYN"/>
	 				</td>	 				
	 				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	 			</tr>
	 			<tr>
	 				<td class="formLabel">Stop Claims:</td>	 				
	  				<td>	  				
	 					<html:checkbox property="stopClaimsYN" styleId="stopClaimsYN"/>	 					
	 				</td>
	 				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	 			</tr>
				<!-- KOC 1286 for OPD Benefit -->
	 			<tr>
	 				<td class="formLabel">OPD Benefit(OutSideSumInsured):</td>	 				
	  				<td>	  				
	 					<html:checkbox property="opdClaimsYN" styleId="opdClaimsYN"/>	 					
	 				</td>
	 				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	 			</tr>
	 			<!-- KOC 1286 for OPD Benefit -->
	 			<tr>
	 				<td class="formLabel">Survival Period:</td>	 				
	  				<td>	  				
	 					<html:checkbox property="survivalPeriodYN" styleId="survivalPeriodYN"/>	 					
	 				</td>
	 				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	 			</tr>
	 			<tr>
	    			<td class="formLabel">Surgery Type: </td>
	    			<td colspan="3">
	       				<html:select property="surgeryMandtryID" styleClass="selectBox" disabled="<%= viewmode %>">
                  			<html:options collection="surgerytypeMandatory" property="cacheId" labelProperty="cacheDesc"/>
            			</html:select>
        			</td>
	  			</tr>
	  			
	  			
	  				 
	  			 <tr>
        	  <td  class="formLabel">VAT(%): <span class="mandatorySymbol">*</span></td>
        	   <td><html:text property="vatPercent" styleClass="textBox textBoxSmall" maxlength="5" onkeyup="isNumaricOnly(this);" disabled="<%=viewmode%>" />
	      	
	      	
	      	</td>	 
	  		</tr>		
	  		<tr>
        	  <td  class="formLabel">Sum Insured :<span class="mandatorySymbol">*</span></td>
        	  <td> <html:text property="totalSumInsured" styleClass="textBox textBoxStandard" onkeyup="isNumeric(this)" /></td>	 
	  		</tr>		
			</table>
		</fieldset>
		<!--KOC 1270 for hospital cash benefit-->
			<fieldset>
			<legend>Cash Benefit</legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		
	 			<tr>
	 				<td class="formLabel" width="20%">Provider Cash Benefit(WithInSumInsured):</td>	 				
	  				<td width="4%">	  				
	 					<html:checkbox property="cashBenefitsYN" styleId="cashBenefitsYN"/>	 					
	 				</td>
	 				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	 			</tr>
	 			
	 			<tr>
	 				<td class="formLabel" width="20%">Canvalescence Cash Benefit(WithInSumInsured):</td>	 				
	  				<td width="4%">	  				
	 					<html:checkbox property="convCashBenefitsYN" styleId="convCashBenefitsYN"/>	 					
	 				</td>
	 				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
	 			</tr>
	 			
			</table>
		</fieldset>
		<!--KOC 1270 for hospital cash benefit-->
		<%
	   	if(TTKCommon.isAuthorized(request,"SpecialPermission"))
	   	{
		%>
		<logic:notEmpty name="frmProductGeneralInfo" property="prodSeqId">
			<fieldset>
 				<legend>Copy Rules</legend>
    			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
					<tr>
	    				<td width="20%" class="formLabel">Insurance Company: <span class="mandatorySymbol">*</span></td>
	    				<td>
	      					<html:select property="insuranceSeqId" styleClass="selectBox" onchange="onChangeInsurance()" disabled="<%= viewmode %>">
                  				<html:option value="">Select from list</html:option>
				  			  <html:options collection="alInsCompany" property="cacheId" labelProperty="cacheDesc"/>
				  				 <logic:notEmpty name="alInsuranceCompany" >
                  			     <html:options collection="alInsuranceCompany" property="cacheId" labelProperty="cacheDesc"/>
									</logic:notEmpty> 
				  				
            				</html:select>
        				</td>
        				<td>&nbsp;</td>
	    				<td>&nbsp;</td>
					</tr>
					<tr>
	    				<td class="formLabel">Product Name: </td>
	    				<td>
	    					<html:select property="productSeqID" styleClass="selectBox" disabled="<%= viewmode %>">
                  				<html:option value="">Select from list</html:option>
                 				<html:optionsCollection property="productList" label="cacheDesc" value="cacheId"/>
            				</html:select>
	    				</td>
	    				<td>&nbsp;</td>
	    				<td>&nbsp;</td>
					</tr>
					<tr>
	    				<td class="formLabel"></td>
	    				<td>
	    					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" ><u>C</u>opy Rules</button>&nbsp;
	    					<!-- onClick="javascript:onCopyRules();" -->
	    				</td>
	    				<td>&nbsp;</td>
	    				<td>&nbsp;</td>
					</tr>
    			</table>
			</fieldset>
		</logic:notEmpty>
		<%
	  	}	
		%>
		<fieldset>
 			<legend>Agreement</legend>
    		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      			<tr>
        			<td width="20%" nowrap class="formLabel">Card Clearance (Days):</td>
        			<td width="30%" nowrap>
        				<html:text property="cardClearanceDays" styleClass="textBox textBoxSmall" maxlength="3" disabled="<%= viewmode %>"/>
        			</td>
        			<td width="20%" class="formLabel" width="21%">Pre-Auth Clearance (Hrs): </td>
        			<td width="30%" class="textLabelBold" width="28%">
        				<html:text property="paClearanceHours" styleClass="textBox textBoxSmall" maxlength="2" disabled="<%= viewmode %>"/>
        			</td>
      			</tr>
      			<tr>
        			<td class="formLabel">Claim Clearance (Days):</td>
        			<td class="textLabelBold">
        				<html:text property="claimClearanceDays" styleClass="textBox textBoxSmall" maxlength="3" disabled="<%=viewmode %>"/>
        			</td>
        			<td class="formLabel">&nbsp;</td>
        			<td class="textLabelBold">&nbsp;</td>
      			</tr>
      			<!-- added for KOC - 1273 -->
      			<tr>
      			<td class="formLabel">Critical Illness Benefit:(within sum insured):</td>	 				
	  				<td>	  					
	 					<html:checkbox property="criticalBenefitYN" styleId="criticalBenefitYN"/>
	 				</td>	 				
	 				<td>&nbsp;</td>
	    			<td>&nbsp;</td>
      			</tr>
      			
    		</table>
		</fieldset>
		<fieldset>
			<legend>Mail Notificaion </legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="formLabel" width="25%">Pre-Auth mail Notification</td>
					<td class="textLabelBold" width="25%"><html:select property="patEnableYN" name="frmProductGeneralInfo" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
							<html:option value="N">Disable</html:option>
							<html:option value="Y">Enable</html:option>
       					</html:select>
					</td>
					
					<td class="formLabel" width="25%">Claim mail Notification</td>
					<td class="textLabelBold" width="25%"><html:select property="clmEnableYN" name="frmProductGeneralInfo" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
							<html:option value="N">Disable</html:option>
							<html:option value="Y">Enable</html:option>
					</html:select>
					</td>
					
				</tr>
				<tr>
					<td class="formLabel" width="25%">To  :</td>
				<td class="textLabelBold" width="25%"><html:text styleClass="textBox textBoxMedium" name="frmProductGeneralInfo" property="patMailTo" maxlength="100" disabled="<%=viewmode%>" />
				</td>
					<td class="formLabel" width="25%">To  :</td>
				<td class="textLabelBold" width="25%"><html:text styleClass="textBox textBoxMedium" name="frmProductGeneralInfo" property="clmMailTo" maxlength="100" disabled="<%=viewmode%>" />
				</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">CC  :</td>
				<td class="textLabelBold" width="25%"><html:text styleClass="textBox textBoxMedium" name="frmProductGeneralInfo" property="patMailCC" maxlength="100" disabled="<%=viewmode%>" />
				</td>
					<td class="formLabel" width="25%">CC  :</td>
				<td class="textLabelBold" width="25%"><html:text styleClass="textBox textBoxMedium" name="frmProductGeneralInfo" property="clmMailCC" maxlength="100" disabled="<%=viewmode%>" />
				</td>
				</tr>
			</table>
		</fieldset>
		
		<fieldset>
			<legend>Card Benefits</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			
			<tr>
					<td class="formLabel" width="25%">Co-insurance</td>
					<td class="textLabelBold" width="25%">
					<html:text onkeyup="isNumeric(this);" styleClass="textBox textBoxSmall" name="frmProductGeneralInfo" property="coInsurance" maxlength="3" disabled="<%=viewmode%>" />(%)
					</td>
					<td class="formLabel" width="25%">Deductable</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmProductGeneralInfo" property="deductable" disabled="<%=viewmode%>" />
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Class</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmProductGeneralInfo" property="classRoomType" disabled="<%=viewmode%>" />
					</td>
					<td class="formLabel" width="25%">Plan</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmProductGeneralInfo" property="planType" disabled="<%=viewmode%>" />
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Maternity(Y/N)</td>
					<td class="textLabelBold" width="25%"><html:select property="maternityYN" name="frmProductGeneralInfo" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="validateCopay(this,'MT');">
							<html:option value="N">NO</html:option>
							<html:option value="Y">YES</html:option>
       					</html:select>
					</td>
					
					<td class="formLabel" width="25%">Maternity Co-Pay</td>
					<td class="textLabelBold" width="25%">
					<logic:equal name="frmProductGeneralInfo" property="maternityYN" value="Y">
					<html:text styleClass="textBox textBoxLarge" name="frmProductGeneralInfo" property="maternityCopay" disabled="<%=viewmode%>"/>
					</logic:equal>
					<logic:notEqual name="frmProductGeneralInfo" property="maternityYN" value="Y">
					<html:text styleClass="textBox textBoxLarge disabledBox" name="frmProductGeneralInfo" property="maternityCopay" readonly="true" disabled="<%=viewmode%>"/>
					</logic:notEqual>
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Optical(Y/N)</td>
					<td class="textLabelBold" width="25%">
					<html:select property="opticalYN" name="frmProductGeneralInfo" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="validateCopay(this,'OT');">
							<html:option value="N">NO</html:option>
							<html:option value="Y">YES</html:option>
       					</html:select>
					</td>
					<td class="formLabel" width="25%">Optical Co-Pay</td>
					<td class="textLabelBold" width="25%">
					<logic:equal name="frmProductGeneralInfo" property="opticalYN" value="Y">
					<html:text styleClass="textBox textBoxLarge" name="frmProductGeneralInfo" property="opticalCopay" disabled="<%=viewmode%>"/>
					</logic:equal>
					<logic:notEqual name="frmProductGeneralInfo" property="opticalYN" value="Y">
					<html:text styleClass="textBox textBoxLarge disabledBox" name="frmProductGeneralInfo" property="opticalCopay" readonly="true" disabled="<%=viewmode%>"/>
					</logic:notEqual>
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Dental(Y/N)</td>
					<td class="textLabelBold" width="25%">
					<html:select property="dentalYN" name="frmProductGeneralInfo" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="validateCopay(this,'DT');">
							<html:option value="N">NO</html:option>
							<html:option value="Y">YES</html:option>
       					</html:select>
					</td>
					<td class="formLabel" width="25%">Dental Co-Pay</td>
					<td class="textLabelBold" width="25%">
					<logic:equal name="frmProductGeneralInfo" property="dentalYN" value="Y">
					<html:text styleClass="textBox textBoxLarge" name="frmProductGeneralInfo" property="dentalCopay" disabled="<%=viewmode%>"/>
					</logic:equal>
					<logic:notEqual name="frmProductGeneralInfo" property="dentalYN" value="Y">
					<html:text styleClass="textBox textBoxLarge disabledBox" name="frmProductGeneralInfo" property="dentalCopay" readonly="true" disabled="<%=viewmode%>"/>
					</logic:notEqual>
					
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Eligibility</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmProductGeneralInfo" property="eligibility"  disabled="<%=viewmode%>" />
					</td>
					<td class="formLabel" width="25%">IP/OP Services</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmProductGeneralInfo" property="ipopServices" disabled="<%=viewmode%>" />
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Pharmaceutical</td>
					<td class="textLabelBold" width="25%">
					<html:text styleClass="textBox textBoxLarge" name="frmProductGeneralInfo" property="pharmaceutical"  disabled="<%=viewmode%>" />
					</td>
					<td class="formLabel" width="25%"></td>
					<td class="textLabelBold" width="25%">
					</td>
				</tr>
				</table>
				</fieldset>
				<logic:equal property="healthAuthority" name="frmProductGeneralInfo" value="DHA" scope="session">
				<fieldset>
				<legend>Plan Benefits</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td class="formLabel" width="25%">Maternity Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmProductGeneralInfo" property="planMaternityCoPay" cols="30" rows="2" disabled="<%=viewmode%>"  onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
					<td class="formLabel" width="25%">IP Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmProductGeneralInfo" property="planIpCoPay" cols="30" rows="2" disabled="<%=viewmode%>" onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
				</tr>
				<tr>
					<td class="formLabel" width="25%">Optical Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmProductGeneralInfo" property="planOpticalCoPay" cols="30" rows="2" disabled="<%=viewmode%>" onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
					<td class="formLabel" width="25%">OP Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmProductGeneralInfo" property="planOpCoPay" cols="30" rows="2" disabled="<%=viewmode%>" onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
				</tr>
				
				<tr>
					<td class="formLabel" width="25%">Dental Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmProductGeneralInfo" property="planDentalCoPay" cols="30" rows="2" disabled="<%=viewmode%>" onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
					<td class="formLabel" width="25%">OP Consultation Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmProductGeneralInfo" property="planOpConsultationCoPay" cols="30" rows="2" disabled="<%=viewmode%>" onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
				</tr>
				
				<tr>
					<td class="formLabel" width="25%" />
					<td class="textLabelBold" width="25%" />
					<td class="formLabel" width="25%">Pharmacy Co-Pay <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold" width="25%">
					<html:textarea name="frmProductGeneralInfo" property="planPharmacyCoPay" cols="30" rows="2" disabled="<%=viewmode%>" onkeypress="onRestrictChar(event, this, keybNumberAndAlpha);"/>
					</td>
				</tr>
				</table>
				</fieldset>
				</logic:equal>
				
		<!-- E N D : Form Fields -->

    	<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  		<tr>
	    		<td width="100%" align="center">
  	    		<%
	    		if(TTKCommon.isAuthorized(request,"Edit"))
	    		{
	    		%>
	       			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
	       			<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>
				<%
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
				%>
       			</td>
	  		</tr>
		</table>
	</div>
	<!-- E N D : Buttons -->
	<input type="hidden" name="mode">
	<html:hidden property="prodSeqId"/>
	<html:hidden property="prodPolicySeqID"/>	
    <html:hidden property="caption" />
    <html:hidden property="stopPreAuth" />        
    <html:hidden property="stopClaim" />
	<html:hidden property="opdClaim" /> <!--KOC 1286 FOR OPD -->
	<html:hidden property="cashBenefit" /> <!--KOC 1270 for hospital cash benefit-->
    <html:hidden property="convCashBenefit" /> <!--KOC 1270 for hospital cash benefit-->
    <!-- added for KOC-1273 -->
    <html:hidden property="criticalBenefit"/>
    <html:hidden property="survivalPeriod"/>
    <html:hidden property="refundFalg" name="frmProductGeneralInfo"/>
    <%--  <html:hidden property="previousRefundedYN" value="<bean:write name="frmProductGeneralInfo" property="refundedYN"/>"/> --%>
     <input type="hidden" name="previousRefundedYN" value="<bean:write name="frmProductGeneralInfo" property="refundedYN"/>" />
     
     
    <html:hidden property="refundedYNRemarks" name="frmProductGeneralInfo"/>
     <html:hidden property="insFlag" name="frmProductGeneralInfo"/>
    
    
    <script>
    stopPreAuthClaim();
    </script>
    <!-- added for KOC -1273 -->
    <script>
    criticalBenefit();
    survivalPeriod();
    selectPolicySubType();
    </script>
</html:form>
<!-- E N D : Content/Form Area -->