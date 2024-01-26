<%
/**
 * @ (#) preauthgeneral.jsp May 10, 2006
 * Project      : TTK HealthCare Services
 * File         : preauthgeneral.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : May 10, 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,java.util.ArrayList"%>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/bankacctgeneral.js"></script>
<%
    boolean viewmode=true;
	boolean blnTransactionYN=true;
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
        viewmode=false;
    }
    pageContext.setAttribute("officeinfo",Cache.getCacheObject("officeInfo"));
   	pageContext.setAttribute("acctStatus",Cache.getCacheObject("acctStatus"));
   	pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>


<!-- S T A R T : Content/Form Area -->
<html:form action="/BankAcctGeneralAction.do" >
<logic:notEmpty name="frmBankAcctGeneral" property="accountSeqID">
    <logic:match name="frmBankAcctGeneral" property="transactionYN" value="N">
	   <logic:match name="frmBankAcctGeneral" property="status" value="ASC">
			<% blnTransactionYN=true; %>
	   </logic:match>
	   <logic:notMatch name="frmBankAcctGeneral" property="status" value="ASC">
			<% blnTransactionYN=false; %>
	   </logic:notMatch>
	</logic:match>
	<logic:notMatch name="frmBankAcctGeneral" property="transactionYN" value="N">
	   <% blnTransactionYN=true; %>
	</logic:notMatch>
</logic:notEmpty>
<logic:empty name="frmBankAcctGeneral" property="accountSeqID">
	<% blnTransactionYN=false; %>
</logic:empty>

<!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="57%">Bank Account Details <bean:write name="frmBankAcctGeneral" property="caption"/></td>
            <td width="43%" align="right" class="webBoard">
                <logic:notEmpty name="frmBankAcctGeneral" property="accountSeqID">
                    <%@ include file="/ttk/common/toolbar.jsp" %>
                </logic:notEmpty >
            </td>
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
        <legend>General</legend>
        <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="21%" class="formLabel">Account No.: <span class="mandatorySymbol">*</span></td>
                <td width="32%">
                <logic:empty name="frmBankAcctGeneral" property="accountSeqID">
                    <html:text property="accountNO" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60"/>
                </logic:empty>
                <logic:notEmpty name="frmBankAcctGeneral" property="accountSeqID">
                	<html:text property="accountNO" styleClass="textBox textBoxMedium" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60" readonly="true" disabled="<%= viewmode %>"/>
                </logic:notEmpty>
                </td>
                <td width="17%" class="formLabel">Account Name: <span class="mandatorySymbol">*</span></td>
                <td width="30%">
                    <html:text property="accountName" styleClass="textBox textBoxLarge" onkeypress="ConvertToUpperCase(event.srcElement);" maxlength="60" disabled="<%= viewmode%>" readonly="<%= viewmode || blnTransactionYN%>"/>
                </td>
              </tr>
              <tr>
                <td class="formLabel">Account Type.: </td>
                <td><bean:write name="frmBankAcctGeneral" property="accountType"/></td>
                <td class="formLabel">Status: <span class="mandatorySymbol">*</span></td>
                <td>
                <logic:notEmpty name="frmBankAcctGeneral" property="accountSeqID">
                   <logic:match name="frmBankAcctGeneral" property="status" value="ASC">
	                    <html:select property="statusTypeID" styleClass="selectBoxMedium selectBoxDisabled" disabled="<%= viewmode %>">
	                    <html:optionsCollection name="acctStatus" label="cacheDesc" value="cacheId" />
	                    </html:select>
                   </logic:match>
                   <logic:notMatch name="frmBankAcctGeneral" property="status" value="ASC">
	                    <html:select property="statusTypeID" styleClass="selectBox selectBoxMedium" onchange="showhidestatus();" disabled="<%= viewmode %>">
	                    <html:option value="">Select from list</html:option>
	                    <html:optionsCollection name="acctStatus" label="cacheDesc" value="cacheId" />
	                    </html:select>
                   </logic:notMatch>
                </logic:notEmpty>
    			<logic:empty name="frmBankAcctGeneral" property="accountSeqID">
                	<html:select property="statusTypeID" styleClass="selectBoxMedium selectBoxDisabled" disabled="true" >
    				<html:optionsCollection name="acctStatus" label="cacheDesc" value="cacheId"/>
    				</html:select>
				</logic:empty>
    			</td>
              </tr>
              <tr>
                <td class="formLabel">Branch Location: <span class="mandatorySymbol">*</span></td>
                <td>
                    <html:select property="officeSeqID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode||blnTransactionYN %>">
                    <html:option value="">Select from list</html:option>
                    <html:optionsCollection name="officeinfo" label="cacheDesc" value="cacheId" />
                    </html:select>
                </td>
                <td class="formLabel">TDS Purpose:</td>
                <td><html:checkbox property="tdsPurposeYN" value="Y" disabled="<%=viewmode%>"/></td>
              </tr>
              <tr>
                <td class="formLabel">Created Date:</td>
                <td>
                    <html:text property="createdDate" styleClass="textBox textDate textBoxDisabled" maxlength="8" disabled="<%= viewmode %>" readonly="true"/>
                </td>
              </tr>
              <tr>
                <tr>
	    			<td class="formLabel">Account Open Date: <span class="mandatorySymbol">*</span></td>
	    			<td>
	    					<html:text property="accOpenDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>" />
	    					<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmBankAcctGeneral.accOpenDate',document.frmBankAcctGeneral.accOpenDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
	    						<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
	    					</a>
	    			</td>
				</tr>

                <%
					if(TTKCommon.isAuthorized(request,"SpecialPermession"))
					{
				%>
                <logic:notEmpty name="frmBankAcctGeneral" property="accountSeqID">
                  <logic:match name="frmBankAcctGeneral" property="status" value="ASC">
			        <td width=class="formLabel" >Closed Date: <span class="mandatorySymbol">*</span></td>
			        <td >
			        	<html:text property="closedDate" styleClass="textBox textDate textBoxDisabled" maxlength="10" disabled="<%= viewmode %>" readonly="true"/>
				    </td>
				  </logic:match>
				  <logic:notMatch name="frmBankAcctGeneral" property="status" value="ASC">
					  	<logic:match name="frmBankAcctGeneral" property="statusTypeID" value="ASC">
						  	<td class="formLabel" id="closedDate" style="display">Closed Date: <span class="mandatorySymbol">*</span></td>
					        <td id="clsDate" style="display">
						        <html:text property="closedDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>"/>
								<a name="CalendarObjectClDate" id="CalendarObjectClDate" href="#" onClick="javascript:show_calendar('CalendarObjectClDate','forms[1].closedDate',document.forms[1].closedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a>
						    </td>
					    </logic:match>
					    <logic:notMatch name="frmBankAcctGeneral" property="statusTypeID" value="ASC">
						  	<td class="formLabel" id="closedDate" style="display:none;">Closed Date: <span class="mandatorySymbol">*</span></td>
					        <td id="clsDate" style="display:none;">
						        <html:text property="closedDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>"/>
								<a name="CalendarObjectClDate" id="CalendarObjectClDate" href="#" onClick="javascript:show_calendar('CalendarObjectClDate','forms[1].closedDate',document.forms[1].closedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a>
						    </td>
					    </logic:notMatch>
				  </logic:notMatch>
			    </logic:notEmpty>
			    <%
				    }
				%>

              </tr>
        </table>
</fieldset>

<fieldset>
    <legend>Bank Details</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td width="21%" class="formLabel">Bank Name: <span class="mandatorySymbol">*</span></td>
            <td width="32%" class="textLabelBold">
                <bean:write name="frmBankAcctGeneral" property="bankName"/>
            <logic:notEmpty name="frmBankAcctGeneral" property="accountSeqID">
    		  <logic:match name="frmBankAcctGeneral" property="transactionYN" value="N">
    			<logic:notMatch name="frmBankAcctGeneral" property="status" value="ASC">
    				<a href="#" onClick="javascript:selectBank();"><img src="/ttk/images/EditIcon.gif" alt="Select Bank" width="16" height="16" border="0" align="absmiddle"></a></td>
    		    </logic:notMatch>
    		  </logic:match>
    		</logic:notEmpty>
    		<logic:empty name="frmBankAcctGeneral" property="accountSeqID">
    			<a href="#" onClick="javascript:selectBank();"><img src="/ttk/images/EditIcon.gif" alt="Select Bank" width="16" height="16" border="0" align="absmiddle"></a></td>
    		</logic:empty>
            </td>
            <td width="17%" class="formLabel">Office Type:</td>
            <td width="30%" class="textLabelBold"><bean:write name="frmBankAcctGeneral" property="officeTypeDesc"/>&nbsp;&nbsp;&nbsp;
        </tr>
    </table>
</fieldset>
<fieldset><legend>Others</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="21%" class="formLabel">Remarks:</td>
        <td width="79%" colspan="3">
            <html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
        </td>
        </tr>
    </table>
</fieldset>

<!-- E N D : Form Fields -->

    <!-- S T A R T : Buttons -->
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100%" align="center">
              <logic:match name="viewmode" value="false">
                <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onUserSubmit()"><u>S</u>ave</button>&nbsp;
                <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
              </logic:match>
            </td>
        </tr>
    </table>
<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="">
<html:hidden property="editmode"/>
<html:hidden property="accountSeqID"/>
<html:hidden property="transactionYN"/>
<INPUT TYPE="hidden" NAME="tab" VALUE="">
<logic:notEmpty name="frmBankAcctGeneral" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
</html:form>