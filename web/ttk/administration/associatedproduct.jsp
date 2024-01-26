<%/**
 * @ (#) associatedproduct.jsp Oct 5th 2007
 * Project       : Vidal Health TPA  Services
 * File          : saveusergroup.jsp
 * Author        : Krupa J
 * Company       : Span Systems Corporation
 * Date Created  : Oct 5th 2007
 * @author       : Krupa J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/administration/associatedproduct.js"></script>

<!-- S T A R T : Content/Form Area -->	
	<html:form action="/AssociatedProductAction.do"> 	
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td ><bean:write name="frmAssociateProduct" property="caption"/></td>
	    <td  align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
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
    <legend>Associated to</legend>
<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">     
  <tr>
    <td class="formLabelBold">Office Type</td>
    <td>&nbsp;</td>
    <td class="formLabel">&nbsp;</td>
    <td><span class="formLabelBold">Effective From</span></td>
    <td >&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="20%" class="formLabel">Head Office</td>
    <td width="4%"><html:checkbox styleId="H" property="headOfficeYN" value="Y" onclick="javascript:isChecked(this)"/>
    <input type="hidden" name="headOfficeYN" value="N">
    </td>
    <td width="4%" class="formLabel">&nbsp;</td>
    <td width="39%"><html:text property="headOfficeEffDate"  maxlength="10"  styleClass="textBox textDate"/><span id="headDate"><A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmAssociateProduct.headOfficeEffDate',document.frmAssociateProduct.headOfficeEffDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="headOfficeEffDate" width="24" height="17" border="0" align="absmiddle"></a></span></td>
    <td width="20%" >&nbsp;</td>
    <td width="13%">&nbsp;</td>
  </tr>
   <tr>
    <td width="20%" class="formLabel">Regional Office</td>
    <td width="4%"><html:checkbox styleId="R" property="regionalOfficeYN" value="Y" onclick="javascript:isChecked(this)"/>
    <input type="hidden" name="regionalOfficeYN" value="N">
    <html:hidden property="regOfficeSeqIds" />
    </td>
    <td width="4%" class="formLabel"><span id="regImg"><a href="#" onclick="javascript:onAssociateOffice('IRO')"><img src="/ttk/images/RatesIcon.gif"  alt="Regional Office" width="16" height="16" border="0"></a></span></td>
    <td width="39%"><html:text property="regOfficeEffDate"  maxlength="10" styleClass="textBox textDate"/><span id="regDate"><A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmAssociateProduct.regOfficeEffDate',document.frmAssociateProduct.regOfficeEffDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></span></td>
    <td width="20%" >&nbsp;</td>
    <td width="13%">&nbsp;</td>
  </tr>
   <tr>
    <td width="20%" class="formLabel">Divisional Office</td>
    <td width="4%"><html:checkbox styleId="D" property="divisionalOfficeYN" value="Y" onclick="javascript:isChecked(this)"/>
    <input type="hidden" name="divisionalOfficeYN" value="N">
    <html:hidden property="divOfficeSeqIds"/>
    </td>
    <td width="4%" class="formLabel"><span id="divImg"><a href="#" onclick="javascript:onAssociateOffice('IDO')"><img src="/ttk/images/RatesIcon.gif"  alt="Divisional Office" width="16" height="16" border="0"></a></span></td>
    <td width="39%"><html:text property="divOfficeEffDate"  maxlength="10" styleClass="textBox textDate"/><span id="divDate"><A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmAssociateProduct.divOfficeEffDate',document.frmAssociateProduct.divOfficeEffDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></span></td>
    <td width="20%" >&nbsp;</td>
    <td width="13%">&nbsp;</td>
  </tr>
   <tr>
    <td width="20%" class="formLabel">Branch Office</td>
    <td width="4%"><html:checkbox styleId="B" property="branchOfficeYN" value="Y" onclick="javascript:isChecked(this)"/>
    <input type="hidden" name="branchOfficeYN" value="N">
    <html:hidden property="branchOfficeSeqIds"/>
    </td>
    <td width="4%" class="formLabel"><span id="brchImg"><a href="#" onclick="javascript:onAssociateOffice('IBO')"><img src="/ttk/images/RatesIcon.gif"  alt="Branch Office" width="16" height="16" border="0"></a></span></td>
    <td width="39%"> <html:text property="branchOfficeEffDate" maxlength="10" styleClass="textBox textDate"/><span id="brchDate"><A NAME="CalendarObjectempDate" ID="CalendarObjectempDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate','frmAssociateProduct.branchOfficeEffDate',document.frmAssociateProduct.branchOfficeEffDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></span></td>
    <td width="20%" >&nbsp;</td>
    <td width="13%">&nbsp;</td>
  </tr>
 </table>
 </fieldset>
 <fieldset><legend>Enrollment type</legend>
 	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">     
  <tr>
    <td class="formLabelBold">Policy Type</td>
    <td>&nbsp;</td>
    <td class="formLabel">&nbsp;</td>
    <td><span class="formLabelBold">Service Charges</span></td>
    <td >&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td width="20%" class="formLabel">Individual</td>
    <td width="4%"><html:checkbox styleId="I" property="individualYN" value="Y" onclick="javascript:isChecked(this)"/>
    <input type="hidden" name="individualYN" value="N">
  	</td>
    <td width="4%" class="formLabel">&nbsp;</td>
    <td width="39%"><html:text property="indServiceCharges" styleClass="textBox textBoxSmall"/></td>
    <td width="20%" >&nbsp;</td>
    <td width="13%">&nbsp;</td>
  </tr>
   <tr>
    <td width="20%" class="formLabel">Individual as group</td>
    <td width="4%"><html:checkbox styleId="G" property="individualAsGroupYN" value="Y" onclick="javascript:isChecked(this)"/>
    <input type="hidden" name="individualAsGroupYN" value="N">
    </td>
    <td width="4%" class="formLabel">&nbsp;</td>
    <td width="39%"><html:text property="ingServiceCharges" styleClass="textBox textBoxSmall"/></td>
    <td width="20%" >&nbsp;</td>
    <td width="13%">&nbsp;</td>
  </tr>
   <tr>
    <td width="20%" class="formLabel">Corporate</td>
    <td width="4%"><html:checkbox styleId="C" property="corporateYN" value="Y" onclick="javascript:isChecked(this)"/>
    <input type="hidden" name="corporateYN" value="N">
    </td>
    <td width="4%" class="formLabel">&nbsp;</td>
    <td width="39%"><html:text property="corServiceCharges" styleClass="textBox textBoxSmall"/></td>
    <td width="20%" >&nbsp;</td>
    <td width="13%">&nbsp;</td>
  </tr>
   <tr>
    <td width="20%" class="formLabel">Non-Corporate</td>
    <td width="4%"><html:checkbox styleId="N" property="nonCorporateYN" value="Y" onclick="javascript:isChecked(this)"/>
    <input type="hidden" name="nonCorporateYN" value="N">
    </td>
    <td width="4%" class="formLabel">&nbsp;</td>
    <td width="39%"><html:text property="ncrServiceCharges" styleClass="textBox textBoxSmall"/></td>
    <td width="20%" >&nbsp;</td>
    <td width="13%">&nbsp;</td>
  </tr>
 </table>
	</fieldset>
	
	<!-- E N D : Form Fields -->
	<!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <%
        if(TTKCommon.isAuthorized(request,"Edit"))
        {
    %>
    <button type="button" name="Button2" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onAssociateExecute()"><u>E</u>xecute</button>&nbsp;
	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button></td>
	<%
		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	%>
  </tr>
</table>

<script language="javascript">
			onDocumentLoad();
</script>
<logic:notEmpty name="frmAssociateProduct" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
<INPUT TYPE="hidden" NAME="strOfficeType">
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="">
	<!-- E N D : Buttons -->
</div>
</html:form>
