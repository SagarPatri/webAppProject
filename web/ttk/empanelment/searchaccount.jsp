<%
/**
 * @ (#) clauses.jsp 9th July 2007
 * Project      : TTK HealthCare Services
 * File         : clauses.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : 9th July 2006
 *
 * @author       :Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/searchaccount.js"></script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		
	
%>

<html:form action="/AccountsAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td><bean:write name="frmAccounts" property="caption"/></td>
    	<td align="right" class="webBoard"></td>
  	</tr>
	</table>
	<!-- E N D : Page Title -->
	<html:errors/>
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
    <!-- S T A R T : Form Fields -->
	<br>

 	
 	


	<br>
	<table class="buttonsSavetolistGrid"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="right" nowrap class="formLabel">
        <%
	    		if(TTKCommon.isDataFound(request,"tableDataClause") && TTKCommon.isAuthorized(request,"Delete"))
				{
		%>
        <button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
        </td>
     	<%
        		}
        %>
      </tr>
    </table>
    
	<fieldset>
    <legend>Account Details</legend>
    	<table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
     	 <tr>
     	 
        	<td width="20%" class="indentedLabels" nowrap align="left">Bank Name: <span class="mandatorySymbol">*</span> <br>
        	<html:text name="frmAccounts" property="bankNameAdd"  styleClass="textBox textBoxMedium" maxlength="20" disabled="<%= viewmode %>"/>
        	</td>
        	
        	<td align="left" width="40%" class="indentedLabels" nowrap>Account Number: <span class="mandatorySymbol">*</span> <br>
	    	<html:text name="frmAccounts" property="accountNumberAdd" styleClass="textBox textBoxLarge" disabled="<%= viewmode %>"/>
	    	</td>
	    	
	    	 <td align="left" width="20%" class="indentedLabels" nowrap> Account Name : <br>
             <html:text name="frmAccounts" property="accountNameAdd" styleClass="textBox textBoxLarge" disabled="<%= viewmode %>"/>
		    </td>
		    
      	</tr>
      	</table>
	</fieldset>
	
	<!-- E N D : Search Box -->
	
	

		<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="tableData"/>
 	<!-- E N D : Grid -->
 	
 	<!-- S T A R T :Buttons and  Page Counter -->
		<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
			<tr>
			    <td width="27%"></td>
			    <td width="73%" align="right">
			    
	          				<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAdd();"><u>A</u>dd</button>&nbsp;
	          	</td>
	          	</tr>
		</table>	          				
	<!-- E N D :Buttons and  Page Counter -->	    
	
	
	<html:hidden property="accountSeqID"/>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<html:hidden property="caption"/>
	<logic:notEmpty name="frmAccounts" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</div>
</html:form>
