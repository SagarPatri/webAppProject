<%
/**
 * @ (#)  tdsackdetials.jsp Aug 17, 2009
 * Project      : TTKPROJECT
 * File         : tdsackdetials.jsp
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : Aug 17, 2009
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
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/calendar/calendar.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/finance/tdsackdetails.js"></SCRIPT>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%
pageContext.setAttribute("tdsInsuranceInfo",Cache.getCacheObject("tdsInsuranceInfo"));
pageContext.setAttribute("tdsAckInfo",Cache.getCacheObject("tdsAckInfo"));
%>
<script language="javascript">
function finendyear(financialYear)
{
	var i = 1;
	i = i+ parseInt(financialYear);
  	if(i >1)
	{
		document.forms[1].finYearTo.value = i;
  	}//end of if(i >1)
}//end of function finendyear(financialYear)
</script>
<html:form action="/SaveTDSAckAction.do">
        <!-- S T A R T : Page Title -->
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td >Acknowledgement Details</td>
          </tr>
        </table>
        <!-- E N D : Page Title -->
        <!-- S T A R T : Form Fields -->
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
        <legend>General</legend>
        <table  align="center"  class="formContainer"  border="0" cellspacing="0" cellpadding="0" width="100%" >
          <tr>
            <td width="20%">Financial Year: <span class="mandatorySymbol">*</span></td>
            <td width="27%">
            <html:text name="frmTDSAckDetail" property="financialYear" maxlength="4" styleClass="textBox textBoxTiny" onblur="javascript:finendyear(document.forms[1].financialYear.value);"/>
	        - <html:text name="frmTDSAckDetail" property="finYearTo" readonly="true" styleClass="textBox textBoxTiny textBoxDisabled" />
	         </td>
            <td width="21%">Quarter: <span class="mandatorySymbol">*</span></td>
            <td width="34%">
            <html:select property="quarterTypeID" styleId="select10" styleClass="selectBox selectBoxMedium">
              <html:optionsCollection name="tdsAckInfo" value="cacheId" label="cacheDesc"/>              
              </html:select></td>
          </tr>
          <tr>
            <td>Insurance Company: <span class="mandatorySymbol">*</span></td>
            <td align="left"  width="30%">
            <html:select property="insID" styleId="select10" styleClass="selectBox selectBoxLargest">
              <html:optionsCollection name="tdsInsuranceInfo" value="cacheId" label="cacheDesc"/>              
              </html:select></td>
            <td></td>           
          </tr>
        <tr>
            <td width="20%">Acknowledgement No.: <span class="mandatorySymbol">*</span></td>
            <td width="27%"><html:text name="frmTDSAckDetail" property="ackNbr"/></td>
            <td width="20%">Acknowledgement Date: <span class="mandatorySymbol">*</span></td>
            <td width="34%"><html:text name="frmTDSAckDetail" property="ackDate" styleClass="textBox textDate" maxlength="10" />
              <A NAME="CalendarObjectMarkDate" ID="CalendarObjectMarkDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectMarkDate','frmTDSAckDetail.ackDate',document.frmTDSAckDetail.ackDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
              <img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="mrkDate" width="24" height="17" border="0" align="absmiddle"></a></td>
              </tr>
		   <tr>
		            <td style="color:#0C48A2"><b>Disclaimer:</b><span class="mandatorySymbol">*</span></td>
		            <tr>
		            <td colspan="3"><font color="#A52A2A">
		            	<html:checkbox property="chckBoxValueYN" />
		              	&nbsp; Update Acknowledgement Information and do not allow further modifications. </font>
		            </td>
		            </tr>
          		</tr>  
        </table>
        </fieldset>
        
        <!-- S T A R T : Buttons -->
        <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100%" align="center" colspan="2">
            <%
			    if(TTKCommon.isAuthorized(request,"Edit"))
				{
			%>
            <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
              &nbsp;
              <button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
              &nbsp;
              <%
              	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
              %>
              <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
              </td>
          </tr>
        </table>
    </div>
        <INPUT TYPE="hidden" NAME="mode" VALUE="">
        <INPUT TYPE="hidden" NAME="tab" VALUE="">
        <html:hidden name="frmTDSAckDetail" property="ackSeqID"/>
    </html:form>