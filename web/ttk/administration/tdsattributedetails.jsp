<%
/**
 * @ (#)  tdsattributedetails.jsp Aug 3, 2009
 * Project      : TTKPROJECT
 * File         : tdsattributedetails.jsp
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : Aug 3, 2009
 *
 * @author       :  Balakrishna Erram
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 *
 * Modified by   :  
 * Modified date :  
 * Reason        :  
 */
 %>
 
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/calendar/calendar.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/tdsattributedetails.js"></script>
<%@ page import=" com.ttk.common.TTKCommon" %>
<html:form action="/TDSTaxAttributesAction.do" >
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td><bean:write name="frmTDSAttrDetails" property="caption"/></td>
	  </tr>
	</table>
	<div class="contentArea" id="contentArea">
	<html:errors/>
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
<fieldset>
<legend>Attribute Details</legend>
 <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" width="100%">
            <tr>
              <td width="10%" nowrap="nowrap">Valid From: <span class="mandatorySymbol">*</span></td>
              <td width="40%" class="textLabel">
              <html:text property="revDateFrom" styleClass="textBox textDate" maxlength="10" />
              <A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','frmTDSAttrDetails.revDateFrom',document.frmTDSAttrDetails.revDateFrom.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
              <img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a></td>
              <logic:notEmpty name="frmTDSAttrDetails" property="revDateTo">
			  <td width="10%" nowrap="nowrap">Valid To:</td>
              <td width="40%" class="textLabel">
              <bean:write name="frmTDSAttrDetails" property="revDateTo"/>
              </td>
              </logic:notEmpty>
              <logic:empty name="frmTDSAttrDetails" property="revDateTo">
              <td width="50%"> </td>
               </logic:empty>
            </tr>
            <tr>
              <td colspan="2" class="formLabel" height="10"></td>
            </tr>
          </table>
          
<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" style="border: 1px solid #cccccc; ">

            <tr class="borderBottom">
              <td width="10%" height="20" class="textLabelBold">TDS Attributes</td>
			  <td width="40%" align="center" class="textLabelBold">Percentage</td>
			  <td width="50%">&nbsp;</td>
            </tr>
  <logic:notEmpty name="frmTDSAttrDetails" property="tdscatratelist">
  <logic:iterate id="tdscatratelist" name="frmTDSAttrDetails" property="tdscatratelist" >
            <tr>
              		  <td align="left" class="formLabel">
              		  <bean:write name="tdscatratelist" property="tdsAtrDesc"/><html:hidden name="tdscatratelist" property="tdsAtrTypeID"/></td>
			  
			  <td align="center" class="formLabel">
			  <%
			  			    if(request.getParameter("tdsCatTypeID").startsWith("TE")){
			  			   %> 
			  		 
			  			  
			  			   <html:text name="tdscatratelist" property="applRatePerc" styleClass="textBox textBoxTiny" disabled="true"/></td>
			                
			                <%} else{%>
			                
			                 <html:text name="tdscatratelist" property="applRatePerc" styleClass="textBox textBoxTiny" disabled="false"/></td>
               <%}%>
              <td align="center" class="formLabel">
              </td>
              <td>&nbsp;</td>
            </tr>
            <input type="hidden" name="selTdsCatRateSeqIDs" value="<bean:write  property='tdsCatRateSeqID' name='tdscatratelist' />">
            
    </logic:iterate>
    </logic:notEmpty>
    
            <tr class="borderTop">
              <td width="20%" class="textLabelBold" align="right"> Total TDS Percentage:</td>
              <td width="30%" align="center" class="formLabel">
              <html:text name="frmTDSAttrDetails" property="totTDSAmount" readonly="true" styleClass="textBox textBoxTiny textBoxDisabled"/></td>
            <td width="50%">&nbsp;</td>
            </tr>
          </table>
          </fieldset>
        <!-- E N D : Form Fields -->
        <!-- S T A R T :  Buttons -->
       <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <logic:empty name="frmTDSAttrDetails" property="revDateTo">
    <%
			    if(TTKCommon.isAuthorized(request,"Edit"))
				{
			%>
    		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;		
            <button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
            <% 
            	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
            %>
            </logic:empty>
            <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
    </tr>
</table>
        <!-- E N D : Buttons and Page Counter -->
        </div>
        <input type="hidden" name="mode"/>   
       <html:hidden property="baseTaxAmt" value=""/>
       <html:hidden property="tdsCatTypeID"/>
        
        
</html:form> 