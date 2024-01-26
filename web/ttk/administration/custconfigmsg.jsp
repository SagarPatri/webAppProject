<%
/** @ (#) custconfigmsg.jsp Oct 12, 2010
 * Project     : TTK Healthcare Services
 * File        : custconfigmsg.jsp
 * Author      : Manikanta Kumar G G
 * Company     : Span Systems Corporation
 * Date Created: Oct 12, 2010
 *
 * @author 		 : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
 <%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/custconfigmsg.js"></script>
<html:form action="/CustomConfigMessageAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Custom Message <bean:write name="frmConfigMessage" property="caption"/> </td>     
  		</tr>
	</table>
	<html:errors/>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">
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
			<legend>Custom Message Configuration</legend>
		    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
		    	<tr>
			        <td class="formLabel" width="200px">Show Customisation Message:</td>	 				
	  				<td>	  					
	 					<html:checkbox property="configYN" styleId="configYN"  onclick="javascript:isMessageConfigure()"/>
	 				</td>
		      	</tr>
		      	<tr>
			<logic:match name ="frmConfigMessage" property="configYN" value="Y" >
				<td class="formLabel"id ="messageid" style="display :">Message:</td>
					<td style="display :" id ="messageid1">
						<html:textarea property="message" styleClass="textBox textAreaLong" style="width:300px;"></html:textarea>
					</td>
			</logic:match>
			<logic:notMatch name ="frmConfigMessage" property="configYN" value="Y">
				<td class="formLabel" id ="messageid" style="display :none">Message:</td>
					<td style="display :none"id ="messageid1">
						<html:textarea property="message" styleClass="textBox textAreaLong" style="width:300px;"></html:textarea>
					</td>
		    </logic:notMatch>
		    	</tr>
		    </table>
		</fieldset>
		<!-- S T A R T : Buttons -->
	    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
		        <td width="100%" align="center">
		        <%
		         if(TTKCommon.isAuthorized(request,"Edit"))
		         {
		        %>
					<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
				<%
		         }//end of if(TTKCommon.isAuthorized(request,"Edit"))
		        %>
		        	<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
					<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		        </td>
	      	</tr>
	    </table>
	    <!-- E N D : Buttons -->
	</div>
	
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<html:hidden property="config" />
	<script language="javascript">
	JS_Focus_Disabled = true;
	config();
	</script>
</html:form>