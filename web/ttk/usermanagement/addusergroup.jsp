<%
/**
 * @ (#) addusergroup.jsp 29th Dec 2005
 * Project      : TTK HealthCare Services
 * File         : addusergroup.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 29th Dec 2005
 *
 * @author       :Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%
  boolean viewmode=true;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }
  pageContext.setAttribute("officeinfo",Cache.getCacheObject("officeInfo"));
  pageContext.setAttribute("userinfo",Cache.getCacheObject("userInfo"));
	pageContext.setAttribute("userRestriction",Cache.getCacheObject("userRestriction"));//KOC Cigna_insurance_resriction  

%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/usermanagement/addusergroup.js"></script>
  <!-- S T A R T : Content/Form Area -->
  <html:form action="EditUserGroupAction.do" >
  <!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td> <bean:write name="frmEditUserGroup" property="caption"/></td>
      <td width="43%" align="right" class="webBoard">&nbsp;</td>
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
      <legend>User Group Information</legend>
      <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="formLabel">User Group: <span class="mandatorySymbol">*</span></td>
            <td class="formLabel">
              <html:text property="groupName" styleClass="textBox textBoxMedium"  onkeypress="javascript:blockEnterkey(event.srcElement);" maxlength="60"  disabled="<%= viewmode %>" readonly="<%= viewmode %>"/>
            </td>
          </tr>
          <tr>
            <td class="formLabel">Vidal Health Branch: <span class="mandatorySymbol">*</span></td>
            <td class="formLabel">
              <html:select property="officeSeqID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
                 <html:option value="">Select from list</html:option>
                  <html:optionsCollection name="officeinfo" label="cacheDesc" value="cacheId" />
                </html:select>
            </td>
          </tr>
           <!--KOC Cigna_insurance_resriction  -->
		<tr>
            <td class="formLabel">User Restriction: </td>
            <td class="formLabel">
              <html:select property="userRestriction" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
                 <html:option value="">Select from list</html:option>
                  <html:optionsCollection name="userRestriction" label="cacheDesc" value="cacheId" />
                </html:select>
            </td>
          </tr>
          <!--KOC Cigna_insurance_resriction  -->
          <tr>
            <td width="13%" nowrap class="formLabel">Description:</td>
            <td width="87%" class="formLabel" nowrap>
              <html:textarea property="groupDesc" styleClass="textBox textAreaLong" disabled="<%= viewmode %>" />
            </td>
          </tr>
            <tr>
            <td class="formLabel">User Type: <span class="mandatorySymbol">*</span></td>
            <td class="formLabel">
              <html:select property="userTypeID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
                 <html:option value="">Select from list</html:option>
                  <html:optionsCollection name="userinfo" label="cacheDesc" value="cacheId" />
                </html:select>
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

          <button type="button" name="Button1" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSubmit()"><u>S</u>ave</button>&nbsp;

          <button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;

        <%
        }//end of if(TTKCommon.isAuthorized(request,"Edit"))
        %>
          <logic:notEmpty name="frmEditUserGroup" property="groupSeqID">
        <%
        if(TTKCommon.isAuthorized(request,"Delete"))
        {
          %>
               <button type="button" name="Button2" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onDelete()"><u>D</u>elete</button>&nbsp;
          <%
          }//end of if(TTKCommon.isAuthorized(request,"Delete"))
        %>
           </logic:notEmpty>
            <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onClose()"><u>C</u>lose</button>&nbsp;
         </td>
      </tr>
  </table>
  <!-- E N D : Buttons -->
    <INPUT TYPE="hidden" NAME="mode" VALUE="">
    <INPUT TYPE="hidden" NAME="groupSeqID" VALUE="<bean:write name="frmEditUserGroup" property="groupSeqID" />">
    <INPUT TYPE="hidden" NAME="groupBranchSeqID" VALUE="<bean:write name="frmEditUserGroup" property="groupBranchSeqID" />">
    <INPUT TYPE="hidden" NAME="rownum" VALUE='<%=TTKCommon.checkNull(request.getParameter("rownum"))%>'>
    <input type="hidden" name="child" value="EditUserGroup">
    </div>
  </html:form>
  <!-- E N D : Content/Form Area -->
