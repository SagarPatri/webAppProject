<%
/** @ (#) addupdatehospital.jsp Nov 09th, 2005
 * Project       : Vidal Health TPA  Services
 * File          : addupdatehospital.jsp
 * Author        : Bhaskar Sandra
 * Company       : Span Systems Corporation
 * Date Created  : Nov 09th, 2005
 * @author 		 : Bhaskar Sandra
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT language="javascript" src="/ttk/scripts/administration/addupdatehospital.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>

<%
  boolean viewmode=true;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }//end of if(TTKCommon.isAuthorized(request,"Edit"))
  pageContext.setAttribute("alOfficeInfo",Cache.getCacheObject("officeInfo"));
  pageContext.setAttribute("alEmpanelTypeCode",Cache.getCacheObject("empanelTypeCode"));
  pageContext.setAttribute("alHospCode",Cache.getCacheObject("hospCode"));
  pageContext.setAttribute("alStateCode",Cache.getCacheObject("stateCode"));
  pageContext.setAttribute("alCityCode",Cache.getCacheObject("cityCode"));
  pageContext.setAttribute("alCountryCode",Cache.getCacheObject("countryCode"));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/AdminAddUpdateHospitalsAction.do" method="post" >

  <!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="51%">Hospital Details - <bean:write name="frmAdminAddUpdateHospital" property="caption"/></td>
    </tr>
  </table>
  <!-- E N D : Page Title -->
  <div class="contentArea" id="contentArea">
  <!-- S T A R T : Form Fields -->
  <!-- S T A R T : Form Fields -->
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

  <html:errors/>
  <fieldset><legend>General</legend>
  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="21%" class="formLabel">TPA Reference No.: </td>
      <td class="formLabelBold"><bean:write name="frmAdminAddUpdateHospital" property="tpaRefNmbr"/></td>
      <td width="17%" class="formLabel">Empanelment Type: <span class="mandatorySymbol">*</span></td>
      <td width="23%">
        <html:select property="emplTypeId" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
             <html:option value="">Select from list</html:option>
             <html:options collection="alEmpanelTypeCode" property="cacheId" labelProperty="cacheDesc"/>
        </html:select></td>
    </tr>
    <tr>
      <td class="formLabel">TPA Registration Date: <span class="mandatorySymbol">*</span></td>
      <td><html:text property="tpaRegDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" /><A NAME="CalendarObjectempDate11" ID="CalendarObjectempDate11" HREF="#" onClick="javascript:show_calendar('CalendarObjectempDate11','forms[1].tpaRegDate',document.forms[1].tpaRegDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a></td>
      <td class="formLabel">Branch Location: <span class="mandatorySymbol">*</span></td>
      <td>
             <html:select property="tpaOfficeSeqId" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                  <html:option value="">Select from list</html:option>
                  <html:options collection="alOfficeInfo" property="cacheId" labelProperty="cacheDesc"/>
            </html:select></td>
    </tr>
  </table>
  </fieldset>

  <fieldset><legend>Hospital Details</legend>
  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="21%" class="formLabel">Hospital Name: <span class="mandatorySymbol">*</span></td>
      <td width="39%" ><html:text property="hospitalName" styleClass="textBox textBoxLarge" maxlength="250" disabled="<%=viewmode%>"/></td>
      <td width="17%" class="formLabel">Type: <span class="mandatorySymbol">*</span></td>
    <td width="23%">
              <html:select property="typeCode" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                  <html:option value="">Select from list</html:option>
                  <html:options collection="alHospCode" property="cacheId" labelProperty="cacheDesc"/>
             </html:select></td>
    </tr>
    <tr>
      <td class="formLabel">Address 1: <span class="mandatorySymbol">*</span></td>
      <td width="39%"><html:text property="addressVO.address1" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>"/></td>
      <td width="17%" class="formLabel">Address 2:</td>
      <td width="23%"><html:text property="addressVO.address2" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>"/></td>
      </tr>
    <tr>
      <td class="formLabel">Address 3:</td>
      <td><html:text property="addressVO.address3" styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>"/></td>
      <td class="formLabel">State: <span class="mandatorySymbol">*</span></td>
      <td>
            <html:select property="addressVO.stateCode" styleId="state" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="onStateChange()">
                  <html:option value="">Select from list</html:option>
                  <html:options collection="alStateCode" property="cacheId" labelProperty="cacheDesc"/>
             </html:select></td>
    </tr>
    <tr>
      <td class="formLabel">City: <span class="mandatorySymbol">*</span></td>
      <td>
           <html:select property="addressVO.cityCode" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                  <html:option value="">Select from list</html:option>
                  <html:optionsCollection property="alCityList" label="cacheDesc" value="cacheId" />
             </html:select></td>
      <td class="formLabel">Pincode:</td>
      <td><html:text property="addressVO.pinCode" styleClass="textBox textBoxSmall" maxlength="10" disabled="<%=viewmode%>"/></td>
    </tr>
    <tr>
      <td class="formLabel">Country: <span class="mandatorySymbol">*</span></td>
      <td>
         <html:select property="addressVO.countryCode" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                  <html:options collection="alCountryCode" property="cacheId" labelProperty="cacheDesc"/>
             </html:select></td>
      <td>&nbsp;</td>
      <td>&nbsp;</td>
    </tr>
    <tr>
      <td class="formLabel">Landmark, if any:</td>
      <td colspan="3"><html:textarea property="landmarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/></td>
    </tr>
  </table>
  </fieldset>
  <!-- E N D : Form Fields -->

    <!-- S T A R T : Buttons -->
  <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td width="100%" align="center">
      <%if(TTKCommon.isAuthorized(request,"Edit"))
    {
      %>
        <logic:empty name="frmAdminAddUpdateHospital" property="emplNumber">
          	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
			<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
        </logic:empty>
        <logic:notEmpty name="frmAdminAddUpdateHospital" property="emplNumber">
        <SCRIPT LANGUAGE="JavaScript">
        var TC_Disabled = true; //to avoid the alert message on change of form elements
      </SCRIPT>
        </logic:notEmpty>
     <%}%>
        <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
      </td>
    </tr>
  </table>
  </div>
  <!-- E N D : Buttons -->
	
  <html:hidden property="stausGnrlTypeId" />
  <html:hidden property="emplNumber" />
  <input type="hidden" name="child" value="AddUpdate">
  <INPUT TYPE="hidden" NAME="mode" VALUE="">
  <input type="hidden" name="focusID" value="">
  <logic:notEmpty name="frmAdminAddUpdateHospital" property="frmChanged">
  <script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
</html:form>
<!-- E N D : Content/Form Area -->	</td>