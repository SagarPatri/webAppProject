<%
/**
 * @ (#) addttkoffice.jsp 21th Mar 2006
 * Project      : Vidal Health TPA Services
 * File         : addttkoffice.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 21th Mar 2006
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
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%
  boolean viewmode=true;
  boolean bEnabled=false;
  pageContext.setAttribute("stateCode", Cache.getCacheObject("stateCode"));
  pageContext.setAttribute("cityCode", Cache.getCacheObject("cityCode"));
  pageContext.setAttribute("countryCode", Cache.getCacheObject("countryCode"));
  pageContext.setAttribute("officeTypeID", Cache.getCacheObject("ttkofficetype"));
  pageContext.setAttribute("parentOfficeSequenceID", Cache.getCacheObject("ttkheadofficelist"));
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }// end of if(TTKCommon.isAuthorized(request,"Edit"))

%>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/addttkoffice.js"></script>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/AddTTKOfficeAction.do" method="post" >

  <logic:match name="frmAddTTKOffice" property="enabled" value="false">
    <% bEnabled=true; %>
    </logic:match>

  <!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><bean:write name="frmAddTTKOffice" property="caption"/> </td>
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
  <fieldset><legend>General</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td class="formLabel" nowrap>Office Type: </td>
          <td  nowrap class="formLabelBold">
            <bean:write name="frmAddTTKOffice" property="officeTypeDesc"/>
          </td>
          <logic:notMatch name="frmAddTTKOffice" property="reportTo" value="false">
            <td class="formLabel"  nowrap>Report To: </td>
            <td  nowrap>
              <html:select property ="parentOfficeSequenceID" styleClass="selectBox selectBoxMedium" disabled="<%=(viewmode || bEnabled)%>">
                      <html:options collection="parentOfficeSequenceID" property="cacheId" labelProperty="cacheDesc"/>
                 </html:select>
            </td>
          </logic:notMatch>
          <logic:match name="frmAddTTKOffice" property="reportTo" value="false">
            <td class="formLabel"  nowrap>Registration No.: </td>
            <td  nowrap class="formLabelBold"><bean:write name="frmAddTTKOffice" property="registrationNbr"/></td>
          </logic:match>
      </tr>
      <tr>
        <td width="18%" class="formLabel" nowrap>Office Name: <span class="mandatorySymbol">*</span></td>
        <td width="35%"  nowrap>
          <html:text property="officeName"  onkeypress='ConvertToUpperCase(event.srcElement);' styleClass="textBox textBoxLarge" maxlength="60" disabled="<%=viewmode%>"/>
        </td>
        <td class="formLabel" width="18%"  nowrap>Office Code: <span class="mandatorySymbol">*</span></td>
        <td width="29%"  nowrap>
          <html:text property="officeCode"  onkeypress='ConvertToUpperCase(event.srcElement);' styleClass="textBox textBoxMedium" maxlength="3" disabled="<%=viewmode%>"/>
        </td>
      </tr>
      <tr>
        <td class="formLabel" nowrap>Active:</td>
        <td nowrap>
          <html:checkbox property="activeYN" value="Y" disabled="<%=viewmode%>"/>
        </td>
        <td class="formLabel"  nowrap>&nbsp;</td>
        <td nowrap>&nbsp;</td>
      </tr>
    </table>
  </fieldset>
  <fieldset><legend>Address Information</legend>
  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td width="18%" nowrap class="formLabel">Address 1: <span class="mandatorySymbol">*</span></td>
          <td width="35%" nowrap>
            <html:text property="addressVO.address1"  styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>"/>
          </td>
          <td width="18%" nowrap class="formLabel">Address 2:</td>
          <td width="29%" nowrap>
            <html:text property="addressVO.address2"  styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>"/>
          </td>
        </tr>
        <tr>
          <td class="formLabel">Address 3:</td>
          <td><html:text property="addressVO.address3"  styleClass="textBox textBoxMedium" maxlength="250" disabled="<%=viewmode%>"/></td>
          <td class="formLabel">State: <span class="mandatorySymbol">*</span></td>
          <td>
            <html:select property ="addressVO.stateCode" styleId="state" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>" onchange="onStateChange()">
                  <html:option value="">Select from list</html:option>
                  <html:options collection="stateCode" property="cacheId" labelProperty="cacheDesc"/>
             </html:select>
          </td>
    </tr>
      <tr>
      <td class="formLabel">City: <span class="mandatorySymbol">*</span></td>
          <td>
            <html:select property ="addressVO.cityCode" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                  <html:option value="">Select from list</html:option>
                  <html:optionsCollection property="alCityList" label="cacheDesc" value="cacheId" />
              </html:select>
          </td>
          <td class="formLabel">Pincode:</td>
          <td>

            <html:text property="addressVO.pinCode"  styleClass="textBox textBoxSmall" maxlength="10" disabled="<%=viewmode%>"/>
          </td>
      </tr>
      <tr>
          <td class="formLabel">Country: <span class="mandatorySymbol">*</span></td>
          <td>
            <html:select property ="addressVO.countryCode" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
                  <html:options collection="countryCode" property="cacheId" labelProperty="cacheDesc"/>
              </html:select>
          </td>
          <td class="formLabel">STD Code: </td>
          <td><html:text property="stdCode"  styleClass="textBox textBoxSmall" maxlength="10" disabled="<%=viewmode%>"/></td>
      </tr>
      <tr>
          <td class="formLabel">Primary Email Id: </td>
          <td><html:text property="emailID"  styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/></td>

          <td>Alternate Email Id:</td>
          <td><html:text property="alternativeEmailID"  styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>"/></td>
      </tr>
      <tr>
          <td class="formLabel">Office Phone 1:</td>
          <td><html:text property="officePhone1"  styleClass="textBox textBoxMedium" maxlength="25" disabled="<%=viewmode%>"/></td>
          <td class="formLabel">Office Phone 2:</td>
          <td><html:text property="officePhone2"  styleClass="textBox textBoxMedium" maxlength="25" disabled="<%=viewmode%>"/></td>
      </tr>
      <tr>
          <td>Fax:</td>
          <td><html:text property="fax1"  styleClass="textBox textBoxMedium" maxlength="15" disabled="<%=viewmode%>"/></td>
          <td>Alternate Fax:</td>
          <td><html:text property="fax2"  styleClass="textBox textBoxMedium" maxlength="15" disabled="<%=viewmode%>"/></td>
      </tr>
      <tr>
          <td>Toll Free No.:</td>
          <td><html:text property="tollFreeNbr"  styleClass="textBox textBoxMedium" maxlength="25" disabled="<%=viewmode%>"/></td>
          <td>&nbsp;</td>
          <td>&nbsp;</td>
      </tr>
    </table>
  </fieldset>
  <fieldset><legend>TDS Information</legend>
  	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td width="18%" nowrap class="formLabel">PAN No.:</td>
          <td width="35%" nowrap>
            <html:text property="panNo"  styleClass="textBox textBoxMedium" maxlength="20" disabled="<%=viewmode%>"/>
          </td>
          <td width="18%" nowrap class="formLabel">Tax Deduction Acct. No.:</td>
          <td width="29%" nowrap>
          	<html:text property="taxDeductionAcctNo"  styleClass="textBox textBoxMedium" maxlength="20" disabled="<%=viewmode%>"/>            
          </td>
        </tr>
    </table>
  </fieldset>
  <!-- E N D : Form Fields -->
  <!-- S T A R T :  Buttons -->
  <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="center">
        <%
        if(TTKCommon.isAuthorized(request,"Edit"))
        {
       %>
            <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
			<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
       <%
          }//end of checking for Edit permission
       %>
          <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
        </td>
      </tr>
  </table>
  <!-- E N D : Buttons and Page Counter -->
  <input type="hidden" name="child" value="AddOffice">	
  <INPUT TYPE="hidden" NAME="mode" VALUE="">
  <INPUT TYPE="hidden" NAME="activeYN" VALUE="">
  <html:hidden property="officeSequenceID"/>
  <input type="hidden" name="focusID" value="">

</div>
  <logic:notEmpty name="frmAddTTKOffice" property="frmChanged">
  <script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
</html:form>
