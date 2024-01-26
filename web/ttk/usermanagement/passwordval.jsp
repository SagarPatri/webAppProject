<%
/**
 * @ (#) webconfig.jsp 4th jan 2008
 * Project       : TTK HealthCare Services
 * File  		 : webconfig.jsp
 * Author        : Yogesh S.C
 * Company       : Span Systems Corporation
 * Date Created  : 4th jan 2008
 * @author       : Yogesh S.C
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/usermanagement/passwordval.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/UserPasswordValidityConfig.do" > 
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	    	<td>Password Scheme Configuration</td>
		</tr>
	</table>
	<!-- E N D : Page Title --> 
	 <html:errors/>
	<div class="contentArea" id="contentArea">	
		
	 <logic:notEmpty name="updated" scope="request">
	  	 <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
		   	 <tr>
			     <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
			     	<bean:message name="updated" scope="request"/>
			     </td>
		     </tr>
	  	 </table>
 	 </logic:notEmpty>
 	 
	<!-- S T A R T : Form Fields -->
	<fieldset> 
	<legend>Password Settings </legend>
		<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="30%" nowrap class="formLabel">Password Validity: <span class="mandatorySymbol">*</span></td>
				<td width="30%">
					<html:text styleClass="textBox textBoxSmall" name="frmPasswordVal" property="passwordValidityDays" maxlength="3"/>&nbsp;(days)
				</td>
				<td class="formLabel">&nbsp;</td>
	    		<td>&nbsp;</td>
			</tr>
			<tr>
				<td width="30%" nowrap class="formLabel">Show Alert: <span class="mandatorySymbol">*</span></td>
				<td width="30%">
					<html:text styleClass="textBox textBoxSmall" name="frmPasswordVal" property="pwdAlert" maxlength="3"/>&nbsp;(days to expire)
				</td>
				<td class="formLabel">&nbsp;</td>
	    		<td>&nbsp;</td>
			</tr>
			<tr>
				<td width="30%" nowrap class="formLabel">No. of attempts : <span class="mandatorySymbol">*</span></td>
				<td width="30%">
					<html:text styleClass="textBox textBoxSmall" name="frmPasswordVal" property="wrongAttempts" maxlength="3"/>&nbsp;(to lock)
				</td>
				<td class="formLabel">&nbsp;</td>
	    		<td>&nbsp;</td>
			</tr>
			<%--Added as per KOC 1257 11PP--%>
			<logic:match name="frmPasswordVal" property="identifier" value="TTK">
			<tr style="display: ">
				<td width="30%" nowrap class="formLabel">Account Expire: <span class="mandatorySymbol">*</span></td>
				<td width="30%">
					<html:text styleClass="textBox textBoxSmall" name="frmPasswordVal" property="lockDays" maxlength="3"/>&nbsp;(days)
				</td>
				<td class="formLabel">&nbsp;</td>
	    		<td>&nbsp;</td>
			</tr>
			</logic:match>
			<logic:notMatch name="frmPasswordVal" property="identifier" value="TTK">
			<tr style="display: none">
				<td width="30%" nowrap class="formLabel">Account Expire: <span class="mandatorySymbol">*</span></td>
				<td width="30%">
					<html:text styleClass="textBox textBoxSmall" name="frmPasswordVal" property="lockDays" maxlength="3"/>&nbsp;(days)
				</td>
				<td class="formLabel">&nbsp;</td>
	    		<td>&nbsp;</td>
			</tr>
			</logic:notMatch>
				<%--Added as per KOC 1257 11PP--%>
			
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
				    	//if(TTKCommon.isAuthorized(request,"ResetPasswordAll"))
		    				//{
			    %>
				  			    
					    		<!-- <button type="button" name="Button2" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onResetPasswordAll();">Reset <u>P</u>assword All</button>&nbsp;-->
								
				<%
			    			//}
			    %>
			    		<logic:equal name="frmPasswordVal" property="identifier" value="TTK">
			    		<button type="button" name="Button1" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onResetPasswordAll();">Reset <u>P</u>assword All</button>&nbsp;
				        </logic:equal> 
				    	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
						<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
		    	<%
					}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		     	%>
		     		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	  		</td>
  		</tr>
	</table>
	</div>  
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="mode" value="">
<%--Added as per KOC 11PP--%>
	<html:hidden name="frmPasswordVal" property="identifier"/>
 </html:form>
<!-- E N D : Content/Form Area -->