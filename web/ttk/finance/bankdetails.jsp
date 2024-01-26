<%
/**
 * @ (#) bankdetails.jsp 09th June 2006
 * Project      : TTK HealthCare Services
 * File         : bankdetails.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 09th June 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/bankdetails.js"></script>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>

<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
	pageContext.setAttribute("StateID",Cache.getCacheObject("stateCode"));
	pageContext.setAttribute("CityID",Cache.getCacheObject("cityCode"));
	pageContext.setAttribute("CountryID",Cache.getCacheObject("countryCode"));
	pageContext.setAttribute("ChequeTemplate",Cache.getCacheObject("chequeTemplate"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/BankDetailsAction.do" method="post" enctype="multipart/form-data">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
    	<td width="51%">Add Bank Details</td>
    	<td width="49%" align="right" class="webBoard">&nbsp;</td>
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
	<!-- E N D : Success Box -->

	<fieldset>
    <legend>General</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    	<td width="18%" class="formLabel">Bank Name: <span class="mandatorySymbol">*</span></td>
	    	<td width="33%" class="textLabelBold">
	    		<html:text property="bankName" styleClass="textBox textBoxLarge" onkeypress='ConvertToUpperCase(event.srcElement);' maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	    	</td>
	    	<td width="20%" class="formLabel">Office Type:</td>
	    	<td width="29%" class="textLabelBold"><bean:write name="frmBankDetails" property="officeTypeDesc"/></td>
	  	</tr>
	  	<tr>
	  	    <td width="18%" class="formLabel">Cheque Template: <span class="mandatorySymbol">*</span></td>
	  		<td>
	       		<html:select property="chequeTemplateID" styleClass="selectBox selectBoxLarge" disabled="<%= viewmode %>" >
					<html:option value="">Select from list</html:option>
	  				<html:options collection="ChequeTemplate"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
			</td>
			<td width="20%" class="formLabel">IBAN No: <span class="mandatorySymbol">*</span></td>
			<td width="33%" class="textLabelBold">
	    		<html:text property="iban" styleClass="textBox textBoxLarge" maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	    	</td>	  	
	    </tr>
	  	
    </table>
	</fieldset>
	<fieldset>
	<legend>Address Information</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
        	<td class="formLabel">Contact Person:</td>
        	<td>
        		<html:text property="bankAddressVO.contactPerson" styleClass="textBox textBoxLarge" onkeypress='ConvertToUpperCase(event.srcElement);' maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        	</td>
        	<td class="formLabel">&nbsp;</td>
        	<td>&nbsp;</td>
	    </tr>
      	<tr>
        	<td width="18%" nowrap class="formLabel">Address 1: <span class="mandatorySymbol">*</span></td>
        	<td width="33%" nowrap>
        		<html:text property="bankAddressVO.address1" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        	</td>
        	<td width="20%" nowrap class="formLabel">Address 2:</td>
        	<td width="29%" nowrap>
        		<html:text property="bankAddressVO.address2" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        	</td>
      	</tr>
      	<tr>
        	<td class="formLabel">Address 3:</td>
        	<td>
        		<html:text property="bankAddressVO.address3" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        	</td>
        	<td class="formLabel">Country: <span class="mandatorySymbol">*</span></td>
        	<td>
        		<html:select property="bankAddressVO.countryCode"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>" onchange="onChangeCountry()">
	  				<html:options collection="CountryID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
        	</td>
        	
		</tr>
      	<tr>
      		<td class="formLabel">Governorate/State: <span class="mandatorySymbol">*</span></td>
        	<td>
	       		<html:select property="bankAddressVO.stateCode" styleId="state" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>" onchange="onChangeCity()">
					<html:option value="">Select from list</html:option>
	  				<%-- <html:options collection="StateID"  property="cacheId" labelProperty="cacheDesc"/> --%>.
	  				<html:optionsCollection property="alStateList" label="cacheDesc" value="cacheId"/>
		    	</html:select>
			</td>
	        <td class="formLabel">PO Box: <span class="mandatorySymbol">*</span></td>
        	<td>
        		<html:text property="bankAddressVO.pinCode" styleClass="textBox textBoxSmall" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        	</td>
      	</tr>
      	<tr>
        	<td class="formLabel">Area: <span class="mandatorySymbol">*</span></td>
        	<td>
        		<html:select property="bankAddressVO.cityCode"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
					<html:option value="">Select from list</html:option>
	  				<html:optionsCollection property="alCityList" label="cacheDesc" value="cacheId"/>
		    	</html:select>
        	</td>
        	<td class="formLabel">Email Id:</td>
        	<td>
        		<html:text property="bankAddressVO.emailID" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        	</td>
      	</tr>
      	<tr>
      	
        	<td class="formLabel">Office Phone 1:</td>
        	<td>
        	<logic:empty name="frmBankDetails" property="bankAddressVO.isdCode">
           		<html:text name="frmBankDetails" property="bankAddressVO.isdCode" styleId="isdCode1" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD"  onfocus="if (this.value=='ISD') this.value = ''" onblur="if (this.value==''){ this.value = 'ISD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>
          	</logic:empty>
          	<logic:notEmpty name="frmBankDetails" property="bankAddressVO.isdCode">
            	<html:text name="frmBankDetails" property="bankAddressVO.isdCode" styleId="isdCode1" styleClass="disabledfieldType" size="3" maxlength="3" readonly="readonly"/>
          	</logic:notEmpty>
          	<logic:empty name="frmBankDetails" property="bankAddressVO.stdCode">
            	<html:text name="frmBankDetails" property="bankAddressVO.stdCode" styleId="stdCode1" styleClass="disabledfieldType" size="3" maxlength="3" value="STD" onfocus="if (this.value=='STD') this.value = ''" onblur="if (this.value==''){ this.value = 'STD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>			
          	</logic:empty>
             <logic:notEmpty name="frmBankDetails" property="bankAddressVO.stdCode">
            <html:text name="frmBankDetails" property="bankAddressVO.stdCode" styleId="stdCode1" styleClass="disabledfieldType" size="3" maxlength="15" readonly="readonly"/>
            </logic:notEmpty>
            
            <html:text name="frmBankDetails" property="bankAddressVO.offPhone1" styleClass="disabledfieldType" maxlength="15" />
    			
        		<%-- <html:text property="bankAddressVO.offPhone1" styleClass="textBox textBoxMedium" maxlength="20" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/> --%>
        	</td>
        	<td class="formLabel">Office Phone 2:</td>
        	<td>
        		<logic:empty name="frmBankDetails" property="bankAddressVO.isdCode">
           		<html:text name="frmBankDetails" property="bankAddressVO.isdCode" styleId="isdCode1" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD"  onfocus="if (this.value=='ISD') this.value = ''" onblur="if (this.value==''){ this.value = 'ISD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>
          	</logic:empty>
          	<logic:notEmpty name="frmBankDetails" property="bankAddressVO.isdCode">
            	<html:text name="frmBankDetails" property="bankAddressVO.isdCode" styleId="isdCode1" styleClass="disabledfieldType" size="3" maxlength="3" readonly="readonly"/>
          	</logic:notEmpty>
          	<logic:empty name="frmBankDetails" property="bankAddressVO.stdCode">
            	<html:text name="frmBankDetails" property="bankAddressVO.stdCode" styleId="stdCode1" styleClass="disabledfieldType" size="3" maxlength="3" value="STD" onfocus="if (this.value=='STD') this.value = ''" onblur="if (this.value==''){ this.value = 'STD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>			
          	</logic:empty>
             <logic:notEmpty name="frmBankDetails" property="bankAddressVO.stdCode">
            <html:text name="frmBankDetails" property="bankAddressVO.stdCode" styleId="stdCode1" styleClass="disabledfieldType" size="3" maxlength="3" readonly="readonly"/>
            </logic:notEmpty>
            
            <html:text name="frmBankDetails" property="bankAddressVO.offPhone2" styleClass="disabledfieldType" maxlength="15" />
            </td>
      	</tr>
      	<tr>
        	<td class="formLabel">Mobile:</td>
        	<td>
        		<html:text property="bankAddressVO.mobile" styleClass="textBox textBoxMedium" maxlength="20" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        	</td>
        	<td>Fax:</td>
        	<td>
        	<logic:empty name="frmBankDetails" property="bankAddressVO.isdCode">
           		<html:text name="frmBankDetails" property="bankAddressVO.isdCode" styleId="isdCode1" styleClass="disabledfieldType" size="3" maxlength="3" value="ISD"  onfocus="if (this.value=='ISD') this.value = ''" onblur="if (this.value==''){ this.value = 'ISD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>
          	</logic:empty>
          	<logic:notEmpty name="frmBankDetails" property="bankAddressVO.isdCode">
            	<html:text name="frmBankDetails" property="bankAddressVO.isdCode" styleId="isdCode1" styleClass="disabledfieldType" size="3" maxlength="3" readonly="readonly"/>
          	</logic:notEmpty>
          	<logic:empty name="frmBankDetails" property="bankAddressVO.stdCode">
            	<html:text name="frmBankDetails" property="bankAddressVO.stdCode" styleId="stdCode1" styleClass="disabledfieldType" size="3" maxlength="3" value="STD" onfocus="if (this.value=='STD') this.value = ''" onblur="if (this.value==''){ this.value = 'STD';this.className='disabledfieldType';}" onkeypress="this.className='disabledfieldType2'"/>			
          	</logic:empty>
             <logic:notEmpty name="frmBankDetails" property="bankAddressVO.stdCode">
            <html:text name="frmBankDetails" property="bankAddressVO.stdCode" styleId="stdCode1" styleClass="disabledfieldType" size="3" maxlength="3" readonly="readonly"/>
            </logic:notEmpty>
        		<html:text property="bankAddressVO.fax" styleClass="textBox textBoxMedium" maxlength="15" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        	</td>
      	</tr>
	</table>
	</fieldset>

	<fieldset><legend>Others</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    	<tr>
	    	<td width="18%" class="formLabel">Remarks:</td>
    	  	<td width="82%" colspan="3">
      			<html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
      		</td>
        </tr>
    </table>
	</fieldset>
	
	<fieldset><legend>Upload Document</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    	<tr>
	    	<td width="18%" class="formLabel">Upload file: </td>
    	  	<td>
				<html:file property="file" styleId="file" name="frmBankDetails"/> &nbsp;&nbsp;
				<a href="#" onclick="javascript:showBankFile('<bean:write name="frmBankDetails" property="fileName"/>')"> <bean:write name="frmBankDetails" property="fileName"/> </a>
			</td>
        </tr>
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
		        		<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
				    	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Reset()"><u>R</u>eset</button>&nbsp;
			    <%
				    }// end of if(TTKCommon.isAuthorized(request,"Edit"))
				%>
			     	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Close()"><u>C</u>lose</button>
			</td>
		</tr>
	</table>
<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
<INPUT TYPE="hidden" NAME="tab" VALUE=""/>
<input type="hidden" name="focusID" value="">
<html:hidden property="bankSeqID"/>
<logic:notEmpty name="frmBankDetails" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
</html:form>
<!-- E N D : Content/Form Area -->