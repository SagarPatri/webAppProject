<%
/** @ (#) detailreportparam.jsp
 * Project     : TTK Healthcare Services
 * File        : detailreportparam.jsp
 * Author      : Kishor kumar S H
 * Company     : RCS Technologies
 * Date Created: August 5,2008
 * @author 		 : Kishor kumar S H
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.action.table.TableData,com.ttk.action.table.Column,java.util.ArrayList" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/misreports/detailreportparam.js"></script>
<script>
bAction=false;
var TC_Disabled = true;
</script>
<%
	pageContext.setAttribute("sProviderName",Cache.getCacheObject("providerNames"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/MISFinanceReportsAction.do" >
	<!-- E N D : Page Title -->
  <div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<html:errors/>
	
	<fieldset>
	 <legend>Report Parameters </legend>
	<table align="center" class="tablePad"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="10%" nowrap class="textLabelBold">Switch to:</td>
        <td width="90%">
	        <html:select property="switchType" styleClass="specialDropDown" styleId="switchType" onchange="javascript:onSwitch()">
	        <html:option value="Hospital">Provider</html:option>
	        <html:option value="Policy">Policy</html:option>
			</html:select>
		</td>
      </tr>   
    </table>
    
    
	<table align="center" class="searchContainer" border="0" cellspacing="0" cellpadding="0">
  		<logic:match name="frmMISFinanceReports" property="switchType" value="Hospital">
  		<tr>
	    	<td nowrap>Provider Name :<br>
	     		<html:select property="sProviderName" styleClass="selectBox selectBoxLarge">
		  	 	  <html:option value="">Any</html:option>
		          <html:optionsCollection name="sProviderName" label="cacheDesc" value="cacheId" />
            </html:select> 
	    	</td>
	    	
	    	<td nowrap>Empanelment Id :<br>
    	  		<html:text property="sEmpanelId" styleClass="textBox textBoxMedium"  maxlength="60" />
      		</td>
      		<td nowrap>Bank Account No. :<br>
    	  		<html:text property="sFloatAccNo" styleClass="textBox textBoxMedium"  maxlength="60" />
      		</td>
	        </tr>
  		</logic:match>
  		
  		
  		<logic:match name="frmMISFinanceReports" property="switchType" value="Policy">
  		<tr>
	    	<td nowrap>Policy No :<br>
	     		<html:text property="sPolicyNo" styleClass="textBox textBoxMedium"  maxlength="60" />
	    	</td>
	    	<td nowrap>Enrolment Id :<br>
    	  		<html:text property="sEnrolId" styleClass="textBox textBoxMedium"  maxlength="60" />
      		</td>
      		<td nowrap>Bank Account No. :<br>
    	  		<html:text property="sFloatAccNo" styleClass="textBox textBoxMedium"  maxlength="60" />
      		</td>
      	</tr>
      	</logic:match>
      	<tr>
      	<logic:match name="frmMISFinanceReports" property="switchType" value="Policy">
	      	 <td nowrap>Group Id :<br>
	            <html:text property="fGroupId" styleClass="textBox textBoxLarge" maxlength="250" onkeypress="javascript:blockEnterkey(event.srcElement);"/>
	        </td>
	     </logic:match>
	     <logic:notMatch name="frmMISFinanceReports" property="switchType" value="Policy">
	     	<html:hidden property="fGroupId"/>
	     </logic:notMatch>
	    	<td nowrap>From Date:<br>
			<html:text property="sChequeFromDate" styleClass="textBox textDate" maxlength="10" value=""/>
			<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmMISFinanceReports.sChequeFromDate',document.frmMISFinanceReports.sChequeFromDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
					<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
				</a>
      		</td>

	    	<td nowrap>To Date:<br>
			<html:text property="sChequeToDate" styleClass="textBox textDate" maxlength="10" value=""/>
			<a name="CalendarObjectempDate11" id="CalendarObjectempDate11" href="#" onClick="javascript:show_calendar('CalendarObjectempDate11','frmMISFinanceReports.sChequeToDate',document.frmMISFinanceReports.sChequeToDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;">
					<img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle">
				</a>
      		</td>
      		
      		<logic:match name="frmMISFinanceReports" property="switchType" value="Hospital">
				<td nowrap>IBAN no :<br>
				<html:text property="ibanNo"  styleId="ibanNo" styleClass="textBox textBoxMedium" size="" />
			</td>   
			<td nowrap>License no :<br>
				<html:text property="dhaLicenseNo"  styleId="dhaLicenseNo" styleClass="textBox textBoxMedium" size="" />
			</td>   
		 	</logic:match> 
      		    		
        </tr>
	</table>
	
	
	<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%" align="center">
		<logic:match name="frmMISFinanceReports" property="switchType" value="Hospital">
			<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateHospitalReport();"><u>G</u>enerateReport</button>
		</logic:match>
		<logic:match name="frmMISFinanceReports" property="switchType" value="Policy">
			<button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGeneratePolicyReport();"><u>G</u>enerateReport</button>
		</logic:match>
			&nbsp;
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
		</td>
	</tr>
    </table>
    
</fieldset>


  </div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>
<!-- E N D : Main Container Table -->