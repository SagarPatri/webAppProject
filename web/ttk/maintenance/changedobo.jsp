<%
/** @ (#) changedobo.jsp 2Oth August 2009
 * Project     : TTK Healthcare Services
 * File        : changedobo.jsp
 * Author      : Navin Kumar R
 * Company     : Span Systems Corporation
 * Date Created: 20th August 2009
 *
 * @author 		 : Navin Kumar R
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/maintenance/changedobo.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ChangeDoBoAction.do" method="post">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Change DO/BO</td>    		
   	 	</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">
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
		<html:errors/>	
		<fieldset>
			<legend>Scheme Number</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
		        	<td class="formLabel">Scheme Number:<span class="mandatorySymbol">*</span></td>
		        	<td class="textLabelBold">
		        		<html:text property="prodPolicyNumber" name="frmChangeDOBO" styleClass="textBox textBoxPolicyNum" maxlength="60" />
		        	</td>
				</tr>
			</table>
		</fieldset>
		<fieldset>
			<legend>New Healthcare Company Information</legend>
			<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
   	 			<tr>
        			<td class="formLabel">Healthcare Company:</td>
        			<td class="textLabelBold">
        				<bean:write property="companyName" name="frmChangeDOBO"/>
        			</td>
        			<td class="formLabel">Company Code:<span class="mandatorySymbol">*</span></td>
        			<td class="textLabelBold">
        				<bean:write property="companyCodeNbr" name="frmChangeDOBO"/>&nbsp;&nbsp;&nbsp;
    					<a href="#" onClick="javascript:changeOffice();"><img src="/ttk/images/EditIcon.gif" alt="Change Office" width="16" height="16" border="0" align="absmiddle"></a>           
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
					<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>A</u>pply</button>&nbsp;
					<%
        			}
        			%>
					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
				</td>
			</tr>
		</table>
		<!-- E N D : Buttons -->
		<!-- E N D : Content/Form Area -->
		<input type="hidden" name="child" value="">
		<html:hidden property="insuranceSeqID" name="frmChangeDOBO"/>
		<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
	</div>	
</html:form>
<!-- E N D : Main Container Table -->