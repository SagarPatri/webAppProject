<%
/**
 * @ (#)  copayconfig.jsp Jun 23, 2011
 * Project      : TTK Healthcare Services
 * File         : copayconfig.jsp
 * Author       : Manikanta Kumar
 * Company      : Span Systems Corporation
 * Date Created : Jun 23, 2011
 *
 * @author       :  Manikanta Kumar
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 *
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */
 %>
 <%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import=" com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/copayconfig.js"></script>
<!-- S T A R T : Content/Form Area -->
	<html:form action="/CopayConfigurationAction.do" method="post">
	<!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td>Copayment Details -<bean:write name="frmConfigCopay" property="caption"/></td>
      	<td align="right"></td>
        <td align="right" ></td>
      </tr>
    </table>

	<!-- E N D : Page Title -->
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
 	<!-- E N D : Success Box -->

    <!-- S T A R T : Form Fields -->	
	<fieldset>
 	<legend>Copayment Charges</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="21%" nowrap class="formLabel">Copayment Charges will be applicable if the Approved Amount is greater (Rs.) 
			<html:text property="claimApprAmt" styleClass="textBox textBoxSmall" maxlength="13"/>
		</td>   
      </tr>     
    </table>
	</fieldset>

	 <fieldset>
 <legend>Beneficiary region Configuration</legend>
 <table align="center" class="formContainer" border="0" cellspacing="1" cellpadding="1">
        <tr>
	         <td class="formLabel">Beneficiary Scheme region 
	           <html:select property="insuredRegion" name="frmConfigCopay"  style="width:100px;" styleClass="selectBox selectBoxMedium">
		      		      	    <html:option value="N">Not Mandatory</html:option>
		      		      	 	<html:option value="Y">Mandatory</html:option> 
		      		      	 
		      	 </html:select>
		      		</td>
		</tr>
		</table>
		 </fieldset> <!--Changes aa per kOC 1184 Change Request -->
	 <%-- Start Changes as per ChangeRequest KOC1142 on 18 march 2012--%>
    <fieldset> <legend>Copayment Charges Restriction</legend>
 <table align="center" class="formContainer" border="0" cellspacing="1" cellpadding="1">
        <tr>
	         <td class="formLabel">Configure Copay
	           <html:select property="copaypercentageYN" name="frmConfigCopay"  style="width:100px;" onchange="javascript:showHideType();" styleClass="selectBox selectBoxMedium">
		      		      	    <html:option value="N">NO</html:option>
		      		      	 	<html:option value="Y">YES</html:option> 
		      		      	 
		      	 </html:select>
		      		</td>
		</tr>
         <logic:match name="frmConfigCopay" property="copaypercentageYN" value="Y">
		   <tr id="copaypercentage" style="display: ">
		   <td class="formLabel">
		   <table cellpadding="0" cellspacing="0">
					<tr>
	         		
    	 	         <td class="formLabel"> Configure the Copay (MEMBERS) Percentage(%)
                        <html:text property="copaypercentage" name="frmConfigCopay" styleClass="textBox textBoxSmall" maxlength="2"/><span class="mandatorySymbol">*</span>
                     </td>
                 <td class="formLabel">&nbsp;of SumInsured
                	     
		      	 </td> 
		      	 </tr>
				</table>
				</td> 
           </tr>
		</logic:match>
		<logic:notMatch name="frmConfigCopay" property="copaypercentageYN" value="Y">
		  <tr id="copaypercentage" style="display: none">
    	 		     <td class="formLabel"> 
    	 		     <table cellpadding="0" cellspacing="0">
					<tr>
	         		  <td class="formLabel">
    	 		     Configure the Copay (MEMBERS):Percentage(%)
                        <html:text property="copaypercentage" name="frmConfigCopay" styleClass="textBox textBoxSmall" maxlength="2"/><span class="mandatorySymbol">*</span>
                     </td>
                     <td class="formLabel">&nbsp;of SumInsured
                   	 </td> 
		      	 </tr>
				</table>
				</td> 
		     </tr>
		</logic:notMatch>
	     <%-- End  Changes as per ChangeRequest(Neetha) KOC1142 on 18 march 2012 --%>
 </table>
	</fieldset>
	<!-- E N D : Form Fields -->

    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">  	
	    <%
	       if(TTKCommon.isAuthorized(request,"Edit"))
	       {
    	%>    
	       	<button type="button" name="Button"  accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
	       	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	    <%
	    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		%>
	       	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		</td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->	
	<input type="hidden" name="mode">
    <html:hidden property="caption" />
	</html:form>
	<!-- E N D : Content/Form Area -->