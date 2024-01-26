<%@page import="com.ttk.dto.finance.CustomerBankDetailsVO"%>
<%
/**
 * @ (#) accountreferencedetail.jsp 29th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : accountreferencedetail.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : 29th Sep 2005
 *
 * @author       :
 * Modified by   : Rishi Sharma	
 * Modified date :  16th March 2017
 * Reason        :
 */
%>
<%@page import="com.ttk.dto.finance.CustomerBankDetailsVO"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ page import="com.ttk.common.security.Cache" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/finance/financeaccountreferencedetail.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<%
	pageContext.setAttribute("modReason",Cache.getCacheObject("modReason"));
 %>
<!-- S T A R T : Content/Form Area -->
<html:form action="/SaveBankAcctRefGeneralActionTest.do" method="post" enctype="multipart/form-data">
<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="51%">Reference Details</td>
     <td width="49%" align="right" class="webBoard">&nbsp;</td>
  </tr>
</table>

	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<logic:notEmpty name="updated" scope="session">
        <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
                     <%=request.getSession().getAttribute("updated")%>
              </td>
            </tr>
        </table>
    </logic:notEmpty>
		
	<logic:notEmpty name="notify" scope="session">
	
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	    
	        <%=request.getSession().getAttribute("notify")%>
	        </td>
	      </tr>
   	 </table>
   	 </logic:notEmpty>
	<fieldset>
    <legend>General</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="17%" class="formLabel">Modified Reason: <span class="mandatorySymbol">*</span></td>
        <td width="32%">
        	<html:select property ="modReson" styleClass="selectBox selectBoxMedium">
                 <html:option value="">Select from list</html:option>
                 <html:options collection="modReason" property="cacheId" labelProperty="cacheDesc"/>
          	</html:select>
	    </td>
        <td width="16%">&nbsp; </td>
        <td width="35%">&nbsp;</td>
      </tr>
      <tr>
        <td class="formLabel">Reference Date: <span class="mandatorySymbol">*</span></td>
        <td>
        	<html:text property="refDate" name="frmCustomerBankAcctGeneral" styleClass="textBox textDate" maxlength="10" /><A NAME="CalendarObjectRefDate" ID="CalendarObjectRefDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectRefDate','frmCustomerBankAcctGeneral.elements[\'refDate\']',document.frmCustomerBankAcctGeneral.elements['refDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
        </td>
        <td>Reference No.: </td>
        <td>
        	<html:text property="refNmbr" name="frmCustomerBankAcctGeneral" styleClass="textBox textBoxMedium" maxlength="60" />
        </td>
      </tr>
      <tr>
          <td>Browse File :</td>
			<td>
				<html:file property="file" styleId="file"/>
			</td>
			<td><button type="button" name="Button" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUpload()"><u>U</u>pload</button>&nbsp;&nbsp;&nbsp;</td>
			
      </tr>
      <tr>
      <td  style="padding-top:30px;padding-left:20px;width:30%;padding-bottom:10px;">
      <logic:notEmpty name="alLinkDetailsList" scope="session">
       <%! int i=1; %>
       
       <%   
            ArrayList<CustomerBankDetailsVO>alLinkDetailsList  = (ArrayList<CustomerBankDetailsVO>)request.getSession().getAttribute("alLinkDetailsList");
            for(CustomerBankDetailsVO  ct : alLinkDetailsList)
            {  %>
            	
            	<%=i%><%="        "+ct.getFileName()%><br/>
       <%       i++;     	
            }
       %> 
       </logic:notEmpty>
      </td>
      </tr>
      <tr><td></td></tr>
      <tr>
      <tr>
        <td valign="top" class="formLabel">Remarks: <span class="mandatorySymbol">*</span></td>
        <td colspan="3"><html:textarea property="remarks" name="frmCustomerBankAcctGeneral" styleClass="textBox textAreaLarge"/></td>
      </tr>
    </table>
    </fieldset>
    <!-- E N D : Form Fields -->
     <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
	    <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUserSubmit();"><u>S</u>ave</button>&nbsp;
		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	</td>
  </tr>
</table>
	<!-- E N D : Buttons -->
	<%
request.getSession().removeAttribute("alLinkDetailsList");
i=1;
%>
	
</div>
<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>