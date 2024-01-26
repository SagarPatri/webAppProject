<%
/**
 * @ (#) coverage.jsp Aug 25th, 2008
 * Project      : TTK HealthCare Services
 * File         : coverage.jsp
 * Author       : Sendhil Kumar V
 * Company      : Span Systems Corporation
 * Date Created : Aug 25th, 2008
 *
 * @author       : Sendhil Kumar V
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/coverage.js"></script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
%>

<html:form action="/CoverageAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td><bean:write name="frmCoverage" property="caption"/></td>
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
	<!-- S T A R T : Grid -->
	<ttk:HtmlGrid name="tableDataClause" className="gridWithCheckBox zeroMargin"/>
	<!-- E N D : Grid -->


	<!-- S T A R T : Buttons -->
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
    <legend>Coverage Details</legend>
    	<table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
     	 <tr>
        	<td align="left" class="indentedLabels">Coverage Number: <span class="mandatorySymbol">*</span> <br>
        	<html:text property="clauseNbr"  styleClass="textBox textBoxMedium" maxlength="20" disabled="<%= viewmode %>"/>
        	</td>
        	<td align="left" nowrap>Coverage Description: <span class="mandatorySymbol">*</span> <br>
	    	<html:textarea  property="clauseDesc" styleClass="textBox textAreaLong" disabled="<%= viewmode %>"/>
	    	</td>
      	</tr>
      	</table>
	</fieldset>
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="100%" align="center">
      	  <%
		   		if(TTKCommon.isAuthorized(request,"Edit"))
				{
    	 %>
					<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
	  	 			<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
	      <%
				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		 %>
	      <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
      </td>
    </tr>
  </table>
	<!-- E N D : Buttons -->
	<html:hidden property="clauseSeqID"/>
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<html:hidden property="caption"/>
	<logic:notEmpty name="frmCoverage" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</div>
</html:form>
