<%
/** @ (#) addtariffitem.jsp 30th July 2005
 * Project     :Vidal Health TPA  Services
 * File        : addtariffitem.jsp
 * Author      : Srikanth H.M
 * Company     : Span Systems Corporation
 * Date Created: 30th July 2005
 *
 * @author 	   : Srikanth H M
 * Modified by   : pradeep
 * Modified date : 11th Mar 2006
 * Reason        :
 *
 */
%>


<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/addtariffitem.js"></script>

<%
  pageContext.setAttribute("listGeneralCodePlan",Cache.getCacheObject("generalCodePlan"));
  pageContext.setAttribute("listDurationType",Cache.getCacheObject("durationType"));
  boolean viewmode=true;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }//end of if(TTKCommon.isAuthorized(request,"Edit"))
  pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/EditTarrifItemAction.do" >

<!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>Tariff Item Details - <bean:write name="frmTariffItem" property="caption" /></td>
    </tr>
  </table>
<!-- E N D : Page Title -->
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
<div class="contentArea" id="contentArea">
  <fieldset>
  <legend>General Information</legend>
  <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="formLabel">Type: <span class="mandatorySymbol">*</span></td>
         <td colspan="3">
            <logic:empty name="frmTariffItem" property="tariffItemId" >
           <html:select property="tariffItemType"  styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>" onchange="javascript:showHidePackageType(this)">
            <html:option value="" >Select from list</html:option>
            <html:options collection="listGeneralCodePlan"  property="cacheId" labelProperty="cacheDesc"/>
          </html:select>
       </logic:empty>
        <logic:notEmpty name="frmTariffItem" property="tariffItemId" >
               <b><bean:write name="frmTariffItem" property="typeDescription"/></b>
         </logic:notEmpty>
      </td>
    </tr>
      <tr>
        <td width="20%" class="formLabel">Name: <span class="mandatorySymbol">*</span></td>
        <td width="30%">
            <html:text name="frmTariffItem" property="tariffItemName" maxlength="250" styleClass="textBox textBoxLarge" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" disabled="<%=viewmode%>"/>
         </td>
    <td width="20%">&nbsp;</td>
        <td width="30%">&nbsp;</td>
      </tr>
      <tr>
        <td class="formLabel">Description: <span class="mandatorySymbol">*</span></td>
        <td colspan="3">
          <html:textarea property="tariffItemDescription" styleClass="textBox textAreaLong" disabled="<%= viewmode %>" />
        </td>
      </tr>
    </table>

  <logic:match name="frmTariffItem" property="tariffItemType" value="NPK">
    <table align="center" class="formContainerWithoutPad" id="NonPackage" style="display:" border="0" cellspacing="0" cellpadding="0">
  </logic:match>
  <logic:notMatch name="frmTariffItem" property="tariffItemType" value="NPK">
    <table align="center" class="formContainerWithoutPad" id="NonPackage" style="display:none" border="0" cellspacing="0" cellpadding="0">
  </logic:notMatch>
   <tr>
       <td class="formLabel" width="20%">Medical:</td>
        <td width="30%">
          <logic:empty name="frmTariffItem" property="tariffItemId" >
            <html:checkbox styleClass="margin-left:-4px;"  onclick="showHideProcedures(this)" property="medicalPackageYn" styleId="chkMedicalPackageYn" value="Y" disabled="<%=viewmode%>"/>
            </logic:empty>
             <logic:notEmpty name="frmTariffItem" property="tariffItemId" >
              <b><bean:write name="frmTariffItem" property="medicalPackageYn"/>
                 <html:hidden name="frmTariffItem" property="medicalPackageYn"/></b>
            </logic:notEmpty>
    </td>
        <td width="20%">&nbsp;</td>
        <td width="30%">&nbsp;</td>
      </tr>
  </table>
  <table align="center" class="formContainerWithoutPad" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td width="20%">Avg. length of stay: </td>
        <td width="8%" align="left">
          <html:text property="stayDays" styleClass="textBox textBoxTiny" maxlength="3" disabled="<%= viewmode %>"/>
            
        </td>
        <td width="20%">
		<html:select property="durationTypeID" styleClass="selectBox" disabled="<%= viewmode %>">
		      <html:options collection="listDurationType" property="cacheId" labelProperty="cacheDesc"/>
		    </html:select>
         </td>
        <td width="30%">&nbsp;</td>
        <td width="30%">&nbsp;</td>
    </tr>
  </table>
</fieldset>
<logic:notMatch name="frmTariffItem" property="medicalPackageYn" value="Y" >
  <div id="listPro" style="display:">
</logic:notMatch>
<logic:match name="frmTariffItem" property="medicalPackageYn" value="Y" >
  <div id="listPro" style="display:none">
</logic:match>

  <fieldset><legend>List of Procedures</legend>
  <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" align="center">&nbsp;</td>
        <td width="80%"><TABLE WIDTH="75%" BORDER="0" CELLSPACING="0" CELLPADDING="0">
          <TR>
            <TD height="2"></TD>
          </TR>
          <TR>
            <TD WIDTH="40%" ALIGN="left" CLASS="formLabelBold">Associated Procedures:</TD>
            </TR>
          <TR>
            <TD height="5" ALIGN="left"></TD>
          </TR>
          <TR>
            <TD WIDTH="40%" ALIGN="left">
              <html:select property="asscProcedure" styleClass="generaltext selectBoxListMoreLargest" size="10" disabled="<%=viewmode%>">
            <html:option value="">----------------------------------------------------------------------------------------------------------------------------------</html:option>
            <html:optionsCollection property="asscCodes" label="procedureDescription" value="procedureID" />
           </html:select>
            </TD>
            </TR>
          <TR>
            <TD ALIGN="left" height="5"></TD>
          </TR>
          <TR>
            <TR>
            <logic:match name="viewmode" value="false">
            <TD ALIGN="left">
              <button type="button" name="Button" accesskey="t" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAssociate();">Associa<u>t</u>e</button>&nbsp;
			  <button type="button" name="Button2" accesskey="m" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:deleteAsscProc();">Re<u>m</u>ove</button>
			</TD>
           </logic:match>
          </TR>
        </TABLE></td>
      </tr>
    </table>
  </fieldset></div>
  <!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
   <logic:match name="viewmode" value="false">
        <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
		<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
    </logic:match>
    <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCancel();"><u>C</u>lose</button>
	</td>
  </tr>
</table>
</div>
<!-- E N D : Buttons -->

<html:hidden property="rownum"/>
<input type="hidden" name="child" value="EditTariffItem">
<html:hidden property="selectedProcedureCode" value=""/>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="medicalPackageYn" VALUE="N">
<INPUT TYPE="hidden" NAME="deleteSeqId" VALUE="<bean:write name="frmTariffItem" property="deleteSeqId"/>">
<logic:notEmpty name="frmTariffItem" property="frmChanged">
  <script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
</html:form>