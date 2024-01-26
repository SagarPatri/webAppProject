<%
/** @ (#) generatecheque.jsp 14th April 2010
 * Project     : TTK Healthcare Services
 * File        : generatecheque.jsp
 * Author      :Manikanta Kumar
 * Company     : Span Systems Corporation
 * Date Created: 14th April 2010
 *
 * @author 		 :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon" %>
<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/maintenance/generatecheque.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ReportsAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Print Cheque</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
	<html:errors/>
	<logic:notEmpty name="popup" scope="request">
		         <script>
			         window.focus();
			         var w = screen.availWidth - 10;
					 var h = screen.availHeight - 49;
					 var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			         window.open("/GenerateChequeAction.do?mode=doClaimsCheque",'',features);
		         </script>
   </logic:notEmpty>
	<fieldset>
	<legend>Print Cheque</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="formLabel">Batch Number :<span class="mandatorySymbol">*</span>
        &nbsp;&nbsp;
        <html:text property="batchNo" styleClass="textBox textBoxMedium textBoxDisabled" maxlength="20" readonly="true"></html:text>&nbsp;<a href="#" onClick="javascript:getBatchNo();"><img src="/ttk/images/EditIcon.gif" alt="Batch Number" border="0" align="absmiddle"></a>      
         </td>
      </tr>
     </table>
     </fieldset>
     <table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100%" align="center">
						<%
	     					if(TTKCommon.isAuthorized(request,"Edit"))
		    				{
	    				%>
						<button type="button" name="Button2" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onPrintCheque();"><u>P</u>rintCheque</button>&nbsp; 
						<%
		    				}
						%>
						<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>					
						</td>
					</tr>
		</table>
		</div>
			<input type="hidden" name="mode" value="">
			<html:hidden property="fileName"/>
	</html:form>
	<!-- E N D : Content/Form Area -->
 