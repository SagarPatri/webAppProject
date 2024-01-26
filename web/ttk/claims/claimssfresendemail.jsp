<%
/** @ (#) claimssfresendemail.jsp
 * Project     : TTK Healthcare Services
 * File        : /ttk/claims/claimssfresendemail.jsp
 * Author      : Manohar
 * Company     : RCS
 * Date Created: Jan 02 2013
 *
 * @author 		 : Manohar
 * Modified by   :
 * Modified date :KOC 1179
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.HashMap,java.util.ArrayList"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile,com.ttk.dto.administration.WorkflowVO"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/claims/claimssfresendemail.js"></SCRIPT>

<html:form action="/ShortfallResendEmailAction.do">

	<logic:notEmpty name="fileName" scope="request">
		<script language="JavaScript">
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 82;
			var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open("/ShortfallResendEmailAction.do?mode=doViewFilePdf&displayFile=<bean:write name="fileName"/>",'ShortfallScreen',features);
		</script>
	</logic:notEmpty>
	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">To Resend Claim Shortfall Emails</td>		
		</tr>
	</table>
	<!-- E N D : Page Title -->
	
	
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
	
	<!-- S T A R T : Content/Form Area -->
	<div class="contentArea" id="contentArea">
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="25%" align="left"><b>Request's</b></td><td width="30%" align="left"><b>Document's</b></td><td width="20%" align="left"><b>Email Id</b></td><td width="15%" align="left"><b>Sent Through</b></td><td width="10%" align="left"><b>Resend</b></td>
	</tr>	
	</table>
	<br/>
		
	<fieldset>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="25%"  class="formLabel">Initial Request</td>
		<logic:empty name="frmShortfallResendEmail" property="initialDocSentDate">
		<td width="30%"  class="formLabel"></td>
		</logic:empty>
		<logic:notEmpty name="frmShortfallResendEmail" property="initialDocSentDate">
		<td width="30%"  class="formLabel"><a href="#" id="request" onClick="javascript:onViewFile('<bean:write name="frmShortfallResendEmail" property="initialDoc"/>');"><bean:write name="frmShortfallResendEmail" property="initialDoc"/></a></td>
		</logic:notEmpty>        	
    	<td width="20%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="insuredEmailId"/></td>  
    	<td width="15%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="initialDocSentBy"/></td>
    	<logic:empty name="frmShortfallResendEmail" property="initialDocSentDate">
		<td width="10%"  class="formLabel"></td>
		</logic:empty> 
		<logic:notEmpty name="frmShortfallResendEmail" property="initialDocSentDate">
		
 		<logic:equal name="frmShortfallResendEmail" property="cignaYN" value="Y">
		<logic:equal name="frmShortfallResendEmail" property="memberClaimYN" value="Y">
		<td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType" accesskey="R" style="display:" value="RER" class="buttons" onClick="javascript:onResendShortfallEmail('','CIGNA_CLM_DOC_SHFALL_EMAIL_AD|CIGNA_CLM_DOC_SHFALL_EMAIL_PO');"><u>R</u>esend</button></td>
		</logic:equal>
		</logic:equal>
		
		
		<logic:notEqual name="frmShortfallResendEmail" property="cignaYN" value="Y">
		<logic:notEqual name="frmShortfallResendEmail" property="memberClaimYN" value="Y">
	    <td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType" accesskey="R" style="display:" value="RER" class="buttons" onClick="javascript:onResendShortfallEmail('','INITIAL_SHORTFALL_REQUEST');"><u>R</u>esend</button></td>
 		</logic:notEqual>
 		</logic:notEqual>
 		
 		<logic:notEqual name="frmShortfallResendEmail" property="cignaYN" value="Y">
		<logic:equal name="frmShortfallResendEmail" property="memberClaimYN" value="Y">
	    <td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType" accesskey="R" style="display:" value="RER" class="buttons" onClick="javascript:onResendShortfallEmail('','INITIAL_SHORTFALL_REQUEST');"><u>R</u>esend</button></td>
 		</logic:equal>
 		</logic:notEqual>
 		
 		<logic:notEqual name="frmShortfallResendEmail" property="cignaYN" value="N">
		<logic:equal name="frmShortfallResendEmail" property="memberClaimYN" value="N">
	    <td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType" accesskey="R" style="display:" value="RER" class="buttons" onClick="javascript:onResendShortfallEmail('','INITIAL_SHORTFALL_REQUEST');"><u>R</u>esend</button></td>
 		</logic:equal>
 		</logic:notEqual> 	
		
 		</logic:notEmpty>    		
 		<html:hidden name="frmShortfallResendEmail"  property="shrtSeqID"/>		 		
	</tr> 
	
	
	<tr>
		<td width="25%"  class="formLabel">Reminder Request</td>
		<logic:empty name="frmShortfallResendEmail" property="reminderDocSentDate">
		<td width="30%"  class="formLabel"></td>
		</logic:empty>
		<logic:notEmpty name="frmShortfallResendEmail" property="reminderDocSentDate">
		<td width="30%"  class="formLabel"><a href="#" id="request" onClick="javascript:onViewFile('<bean:write name="frmShortfallResendEmail" property="reminderDoc"/>');"><bean:write name="frmShortfallResendEmail" property="reminderDoc"/></a></td>
		</logic:notEmpty>        	
    	<td width="20%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="insuredEmailId"/></td>  
    	<td width="15%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="reminderDocSentBy"/></td>
    	<logic:empty name="frmShortfallResendEmail" property="reminderDocSentDate">
		<td width="10%"  class="formLabel"></td>
		</logic:empty>  
		<logic:notEmpty name="frmShortfallResendEmail" property="reminderDocSentDate">
		
 		<logic:equal name="frmShortfallResendEmail" property="cignaYN" value="Y">
		<logic:equal name="frmShortfallResendEmail" property="memberClaimYN" value="Y">
		<td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType" accesskey="R" style="display:" value="RER" class="buttons" onClick="javascript:onResendShortfallEmail('RER','CIGNA_CLM_SHFALL_REM1_EMAIL_AD|CIGNA_CLM_SHFALL_REM1_EMAIL_PO');"><u>R</u>esend</button></td>
 		</logic:equal>
 		</logic:equal>
 		
 		<logic:notEqual name="frmShortfallResendEmail" property="cignaYN" value="Y">
		<logic:notEqual name="frmShortfallResendEmail" property="memberClaimYN" value="Y">
	 	<td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType" accesskey="R" style="display:" value="RER" class="buttons" onClick="javascript:onResendShortfallEmail('RER','SHORTFALL_REMINDER_REQUEST');"><u>R</u>esend</button></td>
 		</logic:notEqual>
 		</logic:notEqual> 
 		
 		<logic:notEqual name="frmShortfallResendEmail" property="cignaYN" value="Y">
		<logic:equal name="frmShortfallResendEmail" property="memberClaimYN" value="Y">
	    <td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType" accesskey="R" style="display:" value="RER" class="buttons" onClick="javascript:onResendShortfallEmail('RER','SHORTFALL_REMINDER_REQUEST');"><u>R</u>esend</button></td>
 		</logic:equal>
 		</logic:notEqual>
 		
 		<logic:notEqual name="frmShortfallResendEmail" property="cignaYN" value="N">
		<logic:equal name="frmShortfallResendEmail" property="memberClaimYN" value="N">
	    <td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType" accesskey="R" style="display:" value="RER" class="buttons" onClick="javascript:onResendShortfallEmail('RER','SHORTFALL_REMINDER_REQUEST');"><u>R</u>esend</button></td>
 		</logic:equal>
 		</logic:notEqual> 	
		
 		</logic:notEmpty>         
	</tr> 		         	
	
	<tr>
		<td width="25%" class="formLabel">Closure Notice</td>
		<logic:empty name="frmShortfallResendEmail" property="closerDocSentDate">
		<td width="30%"  class="formLabel"></td>
		</logic:empty>
		<logic:notEmpty name="frmShortfallResendEmail" property="closerDocSentDate">
		<td width="30%"  class="formLabel"><a href="#"  id="request" onClick="javascript:onViewFile('<bean:write name="frmShortfallResendEmail" property="closerDoc"/>');"><bean:write name="frmShortfallResendEmail" property="closerDoc"/></a></td>
		</logic:notEmpty>          	
    	<td width="20%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="insuredEmailId"/></td>
    	<td width="15%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="closerDocSentBy"/></td>
    	<logic:empty name="frmShortfallResendEmail" property="closerDocSentDate">
		<td width="10%"  class="formLabel"></td>
		</logic:empty>  
		<logic:notEmpty name="frmShortfallResendEmail" property="closerDocSentDate">
		
 		<logic:equal name="frmShortfallResendEmail" property="cignaYN" value="Y">
		<logic:equal name="frmShortfallResendEmail" property="memberClaimYN" value="Y">
		<td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType" accesskey="R" style="display:" class="buttons" onClick="javascript:onResendShortfallEmail('CNE','CIGNA_CLM_SHFALL_REM2_EMAIL_AD|CIGNA_CLM_SHFALL_REM2_EMAIL_PO');"><u>R</u>esend</button></td>
 		</logic:equal>
 		</logic:equal>
 		
 		<logic:notEqual name="frmShortfallResendEmail" property="cignaYN" value="Y">
		<logic:notEqual name="frmShortfallResendEmail" property="memberClaimYN" value="Y">
	 	<td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType" accesskey="R" style="display:" class="buttons" onClick="javascript:onResendShortfallEmail('CNE','SHORTFALL_CLOSURE_NOTICE');"><u>R</u>esend</button></td>
 		</logic:notEqual>
 		</logic:notEqual>
 		
 		<logic:notEqual name="frmShortfallResendEmail" property="cignaYN" value="Y">
		<logic:equal name="frmShortfallResendEmail" property="memberClaimYN" value="Y">
	    <td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType" accesskey="R" style="display:" value="RER" class="buttons" onClick="javascript:onResendShortfallEmail('CNE','SHORTFALL_CLOSURE_NOTICE');"><u>R</u>esend</button></td>
 		</logic:equal>
 		</logic:notEqual>
 		
 		<logic:notEqual name="frmShortfallResendEmail" property="cignaYN" value="N">
		<logic:equal name="frmShortfallResendEmail" property="memberClaimYN" value="N">
	    <td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType" accesskey="R" style="display:" value="RER" class="buttons" onClick="javascript:onResendShortfallEmail('CNE','SHORTFALL_CLOSURE_NOTICE');"><u>R</u>esend</button></td>
 		</logic:equal>
 		</logic:notEqual> 	
		
 		</logic:notEmpty>                   
	</tr> 
		
	<tr>
		<td width="25%"  class="formLabel">Closure Approval</td>
		<logic:empty name="frmShortfallResendEmail" property="closerArrrovalDocSentDate">
		<td width="30%"  class="formLabel"></td>
		</logic:empty>	
		<logic:notEmpty name="frmShortfallResendEmail" property="closerArrrovalDocSentDate">
		<td width="30%"  class="formLabel"><a href="#" id="request" onClick="javascript:onViewFile('<bean:write name="frmShortfallResendEmail" property="closerArrrovalDoc"/>');"><bean:write name="frmShortfallResendEmail" property="closerArrrovalDoc"/></a></td>          	
		</logic:notEmpty>  
   		<td width="20%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="insurerEmailId"/></td>
   		<td width="15%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="closerArrrovalDocSentBy"/></td>
   		<logic:empty name="frmShortfallResendEmail" property="closerArrrovalDocSentDate">
		<td width="10%"  class="formLabel"></td>
		</logic:empty>  
   		<logic:notEmpty name="frmShortfallResendEmail" property="closerArrrovalDocSentDate">
 		<td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType"  accesskey="R" style="display:" class="buttons" onClick="javascript:onResendShortfallEmail('CLA','SHORTFALL_CLOSURE_APPROVAL');"><u>R</u>esend</button></td>
 		</logic:notEmpty>                   
	</tr>
		
	<tr>
		<td width="25%"  class="formLabel">Closure Letter</td>
		<logic:empty name="frmShortfallResendEmail" property="closureLetterDocSentDate">
		<td width="30%"  class="formLabel"></td>
		</logic:empty>	
		<logic:notEmpty name="frmShortfallResendEmail" property="closureLetterDocSentDate">
		<td width="30%"  class="formLabel"><a href="#" id="request" onClick="javascript:onViewFile('<bean:write name="frmShortfallResendEmail" property="closureLetterDoc"/>');"><bean:write name="frmShortfallResendEmail" property="closureLetterDoc"/></a></td>
		</logic:notEmpty>            	
    	<td width="20%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="insuredEmailId"/></td>
   		<td width="15%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="closureLetterDocSentBy"/></td>
   		<logic:empty name="frmShortfallResendEmail" property="closureLetterDocSentDate">
		<td width="10%"  class="formLabel"></td>
		</logic:empty>	
		<logic:notEmpty name="frmShortfallResendEmail" property="closureLetterDocSentDate">
		<td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType"  accesskey="R" style="display:" class="buttons" onClick="javascript:onResendShortfallEmail('CLL','SHORTFALL_CLOSURE_LETTER');"><u>R</u>esend</button></td>                   
		</logic:notEmpty>
	</tr>  

	<tr>
		<td width="25%"  class="formLabel">Regret</td>
		<logic:empty name="frmShortfallResendEmail" property="regretLetterDocSentDate">
		<td width="30%"  class="formLabel"></td>
		</logic:empty>	
		<logic:notEmpty name="frmShortfallResendEmail" property="regretLetterDocSentDate">
		<td width="30%"  class="formLabel"><a href="#" id="request" onClick="javascript:onViewFile('<bean:write name="frmShortfallResendEmail" property="regretLetterDoc"/>');"><bean:write name="frmShortfallResendEmail" property="regretLetterDoc"/></a></td>
		</logic:notEmpty>
        <td width="20%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="insuredEmailId"/></td>
    	<td width="15%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="regretLetterDocSentBy"/></td>
    	<logic:empty name="frmShortfallResendEmail" property="regretLetterDocSentDate">
		<td width="10%"  class="formLabel"></td>
		</logic:empty>	
		<logic:notEmpty name="frmShortfallResendEmail" property="regretLetterDocSentDate">
 		<td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType"  accesskey="R" style="display:" class="buttons" onClick="javascript:onResendShortfallEmail('RGT','CLAIM_SHORTFALL_REGRET');"><u>R</u>esend</button></td>
 		</logic:notEmpty>                   
	</tr>  
	
	<tr>
		<td width="25%"  class="formLabel">Re-Open Recommandation</td>
		<logic:empty name="frmShortfallResendEmail" property="reopenRecDocSentDate">
		<td width="30%"  class="formLabel"></td>
		</logic:empty>	
		<logic:notEmpty name="frmShortfallResendEmail" property="reopenRecDocSentDate">
	 	<td width="30%"  class="formLabel"><a href="#" id="request" onClick="javascript:onViewFile('<bean:write name="frmShortfallResendEmail" property="reopenRecDoc"/>');"><bean:write name="frmShortfallResendEmail" property="reopenRecDoc"/></a></td>
		</logic:notEmpty>     	
    	<td width="20%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="insurerEmailId"/></td>
    	<td width="15%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="reopenRecDocSentBy"/></td>
    	<logic:empty name="frmShortfallResendEmail" property="reopenRecDocSentDate">
		<td width="30%"  class="formLabel"></td>
		</logic:empty>
		<logic:notEmpty name="frmShortfallResendEmail" property="reopenRecDocSentDate">	
 		<td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType"  accesskey="R" style="display:" class="buttons" onClick="javascript:onResendShortfallEmail('ROR','CLAIM_SHORTFALL_REOPEN_RECOMMANDATION');"><u>R</u>esend</button></td>
 		</logic:notEmpty>                   
	</tr> 
	
	<tr>
		<td width="25%" class="formLabel">Re-Open Non-Recommandation</td>
		<logic:empty name="frmShortfallResendEmail" property="reopenNonRecDocSentDate">
		<td width="30%"  class="formLabel"></td>
		</logic:empty>	
		<logic:notEmpty name="frmShortfallResendEmail" property="reopenNonRecDocSentDate">	
		<td width="30%" class="formLabel"><a href="#"  id="request" onClick="javascript:onViewFile('<bean:write name="frmShortfallResendEmail" property="reopenNonRecDoc"/>');"><bean:write name="frmShortfallResendEmail" property="reopenNonRecDoc"/></a></td>
		</logic:notEmpty>
        <td width="20%" class="textLabel"><bean:write name="frmShortfallResendEmail" property="insurerEmailId"/></td>
    	<td width="15%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="reopenNonRecDocSentBy"/></td>
    	<logic:empty name="frmShortfallResendEmail" property="reopenNonRecDocSentDate">
		<td width="10%"  class="formLabel"></td>
		</logic:empty>
		<logic:notEmpty name="frmShortfallResendEmail" property="reopenNonRecDocSentDate">	
 		<td width="10%" class="formLabel"><button type="button" name="Button" id="ShortfallType"  accesskey="R" style="display:" class="buttons" onClick="javascript:onResendShortfallEmail('RON','CLAIM_SHORTFALL_NONREOPEN_RECOMMANDATION');"><u>R</u>esend</button></td>
 		</logic:notEmpty>                           
	</tr> 
	
	<tr>
		<td width="25%"  class="formLabel">Recommending Waiver of Delay</td>
		<logic:empty name="frmShortfallResendEmail" property="recommendingWaiverDocSentDate">
		<td width="30%"  class="formLabel"></td>
		</logic:empty>	
		<logic:notEmpty name="frmShortfallResendEmail" property="recommendingWaiverDocSentDate">
		<td width="30%"  class="formLabel"><a href="#" id="request" onClick="javascript:onViewFile('<bean:write name="frmShortfallResendEmail" property="recommendingWaiverDoc"/>');"><bean:write name="frmShortfallResendEmail" property="recommendingWaiverDoc"/></a></td>	
		</logic:notEmpty>          	
    	<td width="20%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="insurerEmailId"/></td>
    	<td width="15%"  class="textLabel"><bean:write name="frmShortfallResendEmail" property="recommendingWaiverDocSentBy"/></td>
    	<logic:empty name="frmShortfallResendEmail" property="recommendingWaiverDocSentDate">
		<td width="10%"  class="formLabel"></td>
		</logic:empty>
		<logic:notEmpty name="frmShortfallResendEmail" property="recommendingWaiverDocSentDate">
 		<td width="10%"  class="formLabel"><button type="button" name="Button" id="ShortfallType"  accesskey="R" style="display:" class="buttons" onClick="javascript:onResendShortfallEmail('RWD','RECOMMENDING_WAIVER_OF_DELAY');"><u>R</u>esend</button></td>
 		</logic:notEmpty>                   
	</tr>         	
	</table>
	</fieldset>
	
	<br/>
	<br/>
	
	
	<fieldset>
	<legend>Shortfall Email Sent Summary</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td nowrap="nowrap">
   		<html:textarea property="viewDetails" readonly="true" styleClass="textBox textAreaLongHt"></html:textarea>
    	</td>   
	</tr>
	</table>	
	</fieldset>
	
	<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td width="100%" align="center">
        	
			<%-- <button type="button" name="Button" id="send" accesskey="p" style="display:" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;--%>
		<button type="button" name="Button" id="close" accesskey="f" style="display:" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onResendEmailClose();"><u>R</u>esend Close</button>&nbsp;
		
		</td>
		
		</tr>
	</table>
	<!-- E N D : Buttons -->
	
	</div>
	<html:hidden name="frmShortfallResendEmail"  property="shrtEmailSeqID"/>
	<html:hidden name="frmShortfallResendEmail"  property="shrtSeqID"/>
	<input type="hidden" name="cignaYN"  VALUE="<bean:write name="frmShortfallResendEmail" property="cignaYN"/>"/>  
	<input type="hidden" name="memberClaimYN"  VALUE="<bean:write name="frmShortfallResendEmail" property="memberClaimYN"/>"/>
	<INPUT TYPE="hidden" NAME="child" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>
<!-- E N D : Content/Form Area -->