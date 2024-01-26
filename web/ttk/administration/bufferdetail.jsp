<%
/**
 * @ (#) bufferdetail.jsp 19th Jun 2006
 * Project      : Vidal Health TPA  Services
 * File         : bufferdetail.jsp
 * Author       : Pradeep R
 * Company      : Span Systems Corporation
 * Date Created : 19th Jun 2006
 *
 * @author       :Pradeep R
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
  pageContext.setAttribute("listBufferMode",Cache.getCacheObject("bufferMode"));
  boolean viewmode=true;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }
%>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/bufferdetail.js"></script>

<html:form action="/BufferDetailSaveAction" >
<!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td>Buffer Details <bean:write name="frmBufferDetail" property="caption" /></td>
    <td align="right" class="webBoard"></td>
      </tr>
  </table>
<!-- E N D : Page Title -->
<!-- S T A R T : Form Fields -->
<div class="contentArea" id="contentArea">
<html:errors/>

<%-- <logic:empty name="frmBufferDetail" property="adminExistAlert"> --%>
<logic:notEmpty name="error" scope="request">
<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	<tr>
	<td><strong><img src="/ttk/images/ErrorIcon.gif" alt="Error" width="16" height="16" align="absmiddle">
	&nbsp;The following errors have occurred - </strong>
	<ol style="padding:0px;margin-top:3px;margin-bottom:0px;margin-left:25px;">
	<li style="list-style-type:decimal"> <bean:message name="error" scope="request"/></li>
	</ol></td>
	</tr>
</table>
</logic:notEmpty>
<logic:notEmpty name="frmBufferDetail" property="adminExistAlert" >
	<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="frmBufferDetail" property="adminExistAlert"/>
	        </td>
	      </tr>
   	 </table>
</logic:notEmpty>
<%--  </logic:empty> --%>
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

<fieldset>
    <legend>Reference</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="22%" nowrap class="formLabel">Ref. No.: <span class="mandatorySymbol">*</span></td>
        <td width="30%" nowrap class="textLabelBold">
          <html:text styleClass="textBox textBoxMedium" name="frmBufferDetail" property="refNbr" maxlength="60" disabled="<%=viewmode%>"/>
        </td>
        <td width="26%" nowrap class="formLabel">Reference Date: <span class="mandatorySymbol">*</span></td>
        <td width="22%">
            <html:text property="bufferDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>" />
            <a name="CalendarObjectClDate" id="CalendarObjectClDate" href="#" onClick="javascript:show_calendar('CalendarObjectClDate','frmBufferDetail.bufferDate',document.frmBufferDetail.bufferDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a>
        </td>
      </tr>
    </table>
  </fieldset>
<fieldset>
    <legend>Buffer</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="22%" nowrap class="formLabel">Mode: <!--<span class="mandatorySymbol">*</span>--></td>
        <td width="30%" nowrap class="textLabelBold">
         <span class="textLabelBold">
             <html:select property="modeTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
            <html:option value="" >Select from list</html:option>
            <html:options collection="listBufferMode"  property="cacheId" labelProperty="cacheDesc"/>
          </html:select>
          </span>
        </td>
        <td width="26%" class="formLabel"> Type: </td>
        <td width="22%" class="textLabel">
          <bean:write name="frmBufferDetail" property="allocatedTypeDesc"/>
            <html:hidden name="frmBufferDetail" property="allocatedTypeID" />
        </td>
       
      </tr>
      <tr>
     
     <td height="20" class="formLabel">Administering Authority:</td>
        <td class="textLabel">
            <bean:write name="frmBufferDetail" property="admnAuthDesc"/>
            <html:hidden name="frmBufferDetail" property="admnAuthTypeID" />
        </td>
        <td class="formLabel">&nbsp;</td>
        <td class="textLabelBold">&nbsp;</td>
      </tr>
    </table>
  </fieldset>
<fieldset>
    <legend>Normal Buffer Details</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
         <td width="22%" nowrap class="formLabel"> Corpus Fund (OMR): <!--<span class="mandatorySymbol">*</span>--></td>
          <td width="30%" nowrap>
          <html:text property="bufferAmt" styleClass="textBox textBoxMedium" maxlength="13" disabled="<%= viewmode %>" />
         </td>
         <logic:match name="frmBufferDetail" property="allocatedTypeDesc" value="Limited Per Individual">
        <td width="26%" class="formLabel"> Corpus Limit Per Individual (OMR): 
         </td>
        </logic:match>
        <logic:notMatch name="frmBufferDetail" property="allocatedTypeDesc" value="Limited Per Individual">
              <td width="26%" class="formLabel"> Corpus Limit Per Family (OMR): 
              </td>
        </logic:notMatch>
        <td width="22%" class="textLabelBold">
        <html:text styleClass="textBox textBoxMedium" name="frmBufferDetail" property="allocatedAmt" maxlength="13" disabled="<%=viewmode%>"/>
         <!--  <a href="#" onClick="onLevelConfiguration('NCOR')">
		          <img src="/ttk/images/EditIcon.gif" alt="Level Configuration List" width="16"	height="16" border="0" align="absmiddle">
				</a> -->
         </td>
         </tr>
         <tr>
        <td width="22%" nowrap class="formLabel">Medical Fund (OMR): <!--<span class="mandatorySymbol">*</span>--></td>
          <td width="30%" nowrap>
          <html:text property="normMedAmt" styleClass="textBox textBoxMedium" maxlength="13" disabled="<%= viewmode %>" />
         </td>
         <logic:match name="frmBufferDetail" property="allocatedTypeDesc" value="Limited Per Individual">
        <td width="26%" class="formLabel"> Medical Limit Per Individual (OMR):
        </td>
        </logic:match>
        <logic:notMatch name="frmBufferDetail" property="allocatedTypeDesc" value="Limited Per Individual">
        <td width="26%" class="formLabel"> Medical Limit Per Family (OMR): 
        </td>
        </logic:notMatch>
        <td width="22%" class="textLabelBold">
        <html:text styleClass="textBox textBoxMedium" name="frmBufferDetail" property="nrmMedLimit" maxlength="13" disabled="<%=viewmode%>"/>
         <!-- <a href="#" onClick="onLevelConfiguration('NMED')">
		          <img src="/ttk/images/EditIcon.gif" alt="Level Configuration List" width="16"	height="16" border="0" align="absmiddle">
				</a> -->
         </td>
      </tr>
       
      </table>
  </fieldset>
      
<fieldset>
    <legend>Critical Buffer Details</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
       <tr>
         <td width="22%" nowrap class="formLabel">Corpus Fund (OMR): <!--<span class="mandatorySymbol">*</span>--></td>
          <td width="30%" nowrap>
          <html:text property="critiCorpAmt" styleClass="textBox textBoxMedium" maxlength="13" disabled="<%= viewmode %>" />
         </td>
          <logic:match name="frmBufferDetail" property="allocatedTypeDesc" value="Limited Per Individual">
        <td width="26%" class="formLabel"> Corpus Limit Per Individual (OMR):
                </td>
        </logic:match>
        <logic:notMatch name="frmBufferDetail" property="allocatedTypeDesc" value="Limited Per Individual">
        <td width="26%" class="formLabel"> Corpus Limit Per Family (OMR):
        </td>
        </logic:notMatch>
        <td width="22%" class="textLabelBold">
        <html:text styleClass="textBox textBoxMedium" name="frmBufferDetail" property="criCorpLimit" maxlength="13" disabled="<%=viewmode%>"/>
        <!--  <a href="#" onClick="onLevelConfiguration('CCOR')">
		          <img src="/ttk/images/EditIcon.gif" alt="Level Configuration List" width="16"	height="16" border="0" align="absmiddle">
				</a> -->
         </td>
         </tr>
         <tr>
         
        <td width="22%" nowrap class="formLabel">Medical Fund (OMR): <!--<span class="mandatorySymbol">*</span>--></td>
          <td width="30%" nowrap>
          <html:text property="critiMedAmt" styleClass="textBox textBoxMedium" maxlength="13" disabled="<%= viewmode %>" />
         </td>
          <logic:match name="frmBufferDetail" property="allocatedTypeDesc" value="Limited Per Individual">
        <td width="26%" class="formLabel"> Medical Limit Per Individual (OMR):
        </td>
        </logic:match>
        <logic:notMatch name="frmBufferDetail" property="allocatedTypeDesc" value="Limited Per Individual">
        <td width="26%" class="formLabel"> Medical Limit Per Family (OMR): 
         </td>
         </logic:notMatch>
        <td width="22%" class="textLabelBold">
        <html:text styleClass="textBox textBoxMedium" name="frmBufferDetail" property="criMedLimit" maxlength="13" disabled="<%=viewmode%>"/>
              <!--  <a href="#" onClick="onLevelConfiguration('CMED')">
		          <img src="/ttk/images/EditIcon.gif" alt="Level Configuration List" width="16"	height="16" border="0" align="absmiddle">
				</a> -->
         </td>
      </tr>
       <tr>
        <td width="22%" nowrap class="formLabel">CriticalIllness Buffer Fund (OMR): <!--<span class="mandatorySymbol">*</span>--></td>
          <td width="30%" nowrap>
          <html:text property="criIllBufferAmt" styleClass="textBox textBoxMedium" maxlength="13" disabled="<%= viewmode %>" />
         </td>
         <logic:match name="frmBufferDetail" property="allocatedTypeDesc" value="Limited Per Individual">
        <td width="26%" class="formLabel"> CriticalIllness Buffer Limit Per Individual (OMR):
        </td>
        </logic:match>
        <logic:notMatch name="frmBufferDetail" property="allocatedTypeDesc" value="Limited Per Individual">
        <td width="26%" class="formLabel"> CriticalIllness Buffer Limit Per Family (OMR): 
        </td>
        </logic:notMatch>
        <td width="22%" class="textLabelBold">
        <html:text styleClass="textBox textBoxMedium" name="frmBufferDetail" property="criIllBufferLimit" maxlength="13" disabled="<%=viewmode%>"/>
         <!-- <a href="#" onClick="onLevelConfiguration('NMED')">
		          <img src="/ttk/images/EditIcon.gif" alt="Level Configuration List" width="16"	height="16" border="0" align="absmiddle">
				</a> -->
         </td>
      </tr>
    </table>
  </fieldset>
<fieldset>
    <legend>Others</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="22%" class="formLabel">Remarks:</td>
        <td width="78%" colspan="3">
          <html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%= viewmode %>" />
        </td>
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
			  <logic:equal name="frmBufferDetail" property="editYN" value="Y">
			  <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
			  <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
			  </logic:equal>
        <%
         }
        %>

			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
        </td>
      </tr>
    </table>
    <!-- E N D : Buttons -->
    </div>
    <input type="hidden" name="child" value="Buffer Details">
    <INPUT TYPE="hidden" NAME="mode" VALUE=""> 
       <INPUT TYPE="hidden" NAME="adminExistAlert" VALUE=""> 
     <INPUT TYPE="hidden" NAME="identifier" VALUE="levelConfiguration"> 
     
  </html:form>
<!-- E N D : Buttons -->