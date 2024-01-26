<%
/**1216B
 * @ (#) enrbufferdetail.jsp 19th Jun 2006
 * Project      : TTK HealthCare Services
 * File         : enrollbufferdetail.jsp
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
pageContext.setAttribute("normalbufferTypeList",Cache.getCacheObject("normalbufferTypeList"));
pageContext.setAttribute("supBufferTypeID",Cache.getCacheObject("supBufferTypeID"));
pageContext.setAttribute("supClaimTypeID",Cache.getCacheObject("supClaimTypeID"));
  boolean viewmode=true;
 
%>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/maintenance/enrollbufferlist.js"></script>

<html:form action="/EnrollBufferDetailSaveAction" >
<!-- S T A R T : Page Title -->
  <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
    <tr>
      <td>Buffer Details <bean:write name="frmEnrBufferDetail" property="caption" />&nbsp;&nbsp;
	  
		
	  
    </tr>
  </table>
<!-- E N D : Page Title -->
<!-- S T A R T : Form Fields -->
<div class="contentArea" id="contentArea">
<html:errors/>
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
    	<logic:match name="frmEnrBufferDetail" property="editYN" value="Y">
		<%
		viewmode = false;
		%>
		</logic:match>
		
		
      <tr>
        <td width="22%" nowrap class="formLabel">Ref. No.: <span class="mandatorySymbol">*</span></td>
        <td width="30%" nowrap class="textLabelBold">
          <html:text styleClass="textBox textBoxMedium" name="frmEnrBufferDetail" property="refNbr" maxlength="60" disabled="<%=viewmode%>"/>
        </td>
        <td width="26%" nowrap class="formLabel">Ref. Date: <span class="mandatorySymbol">*</span></td>
        <td width="22%">
                 <html:text property="addedDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>" /> 
            <a name="CalendarObjectClDate" id="CalendarObjectClDate" href="#" onClick="javascript:show_calendar('CalendarObjectClDate','frmEnrBufferDetail.addedDate',document.frmEnrBufferDetail.addedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a>
        </td>
      </tr>
    </table>
  </fieldset>
<fieldset>
    <legend>Buffer Amounts</legend>
    <table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
      <td width="22%" nowrap class="formLabel">Member Id: </td>
        <td width="30%" nowrap>
        <strong><bean:write name="frmEnrBufferDetail" property="enrollmentId"/></strong>
         <%--  <html:text property="enrollmentId" name="frmEnrBufferDetail" styleClass="textBox textBoxMedium" maxlength="13" disabled="<%= viewmode %>" />--%>
    </td>
    
      <td width="26%" nowrap class="formLabel">Mode: <!--<span class="mandatorySymbol">*</span>--></td>
         <td width="22%" nowrap class="textLabelBold">
                     <strong> <bean:write name="frmEnrBufferDetail" property="modeTypeId"/></strong>
                     <html:hidden name="frmEnrBufferDetail" property="modeTypeId" />
                             <%--    <html:text property="modeTypeID" name="frmEnrBufferDetail" styleClass="textBox textBoxMedium" maxlength="13" disabled="<%= viewmode %>" />--%>
                     
                  
         </td> 
       
       
      </tr>
       <%--  //added for hyundai buffer--%> 
       <tr>
        <td height="25" class="formLabel" >Claim Type:<span class="mandatorySymbol">*</span></td>
        <td height="25" class="textLabel" nowrap="nowrap">
				 <html:select property="claimType" styleClass="selectBox selectBoxMedium" onchange="showhideBufferType()" disabled="<%= viewmode %>">
						<html:option value="">Select from list</html:option>
						 <html:optionsCollection name="supClaimTypeID" label="cacheDesc" value="cacheId" />
					 </html:select>
        </td>
        
            <td height="25" class="formLabel" >Buffer Type:<span class="mandatorySymbol">*</span></td>
              <logic:match name="frmEnrBufferDetail" property="claimType" value="NRML">	 
		         <td id="normalID" height="25" class="textLabel" nowrap="nowrap" style="display: ">
				<html:select property="bufferType" styleClass="selectBox selectBoxMedium"   onchange="ChangeType(this)" disabled="<%= viewmode %>">
				<html:option value="">Select from list</html:option>
				<html:optionsCollection name="normalbufferTypeList" label="cacheDesc" value="cacheId" />
				</html:select>
				 </td>
  			   </logic:match>
  			  <logic:notMatch name="frmEnrBufferDetail" property="claimType" value="NRML">
  		           <td id="normalID" height="25" class="textLabel" nowrap="nowrap" id="criticalId" style="display:none">
          	       <html:select property="bufferType" styleClass="selectBox selectBoxMedium"  onchange="ChangeType(this)" disabled="<%= viewmode %>">
           		  		<html:option value="">Select from list</html:option>
           		    <html:optionsCollection name="normalbufferTypeList" label="cacheDesc" value="cacheId" />
           	      </html:select>
        	      </td>
        	      
        	  </logic:notMatch>
        	    <logic:match name="frmEnrBufferDetail" property="claimType" value="CRTL">	 
		         <td id="criticalID" height="25" class="textLabel" nowrap="nowrap" style="display: ">
				<html:select property="bufferType1" styleClass="selectBox selectBoxMedium"   onchange="ChangeType(this)" disabled="<%= viewmode %>">
				<html:option value="">Select from list</html:option>
			    <html:optionsCollection name="supBufferTypeID" label="cacheDesc" value="cacheId" />
		<%-- 		<html:option value="CRTB">Critical Buffer</html:option> --%>
				</html:select>
				 </td>
  			   </logic:match>
  			  <logic:notMatch name="frmEnrBufferDetail" property="claimType" value="CRTL">
  		           <td id="criticalID" height="25" class="textLabel" nowrap="nowrap" id="criticalId" style="display:none">
          	       <html:select property="bufferType1" styleClass="selectBox selectBoxMedium"  onchange="ChangeType(this)" disabled="<%= viewmode %>">
           		  		<html:option value="">Select from list</html:option>
           		    <html:optionsCollection name="supBufferTypeID" label="cacheDesc" value="cacheId" />
           	     <%--   <html:option value="CRTB">Critical Buffer</html:option> --%>
           	      </html:select>
        	      </td>
        	      
        	  </logic:notMatch>
  	     </tr>
        <%--  //end added for hyundai buffer--%>
      <tr>
       <td width="22%" nowrap class="formLabel">Available Corporate Buffer Amount : <!--<span class="mandatorySymbol">*</span>--></td>
                 <td width="30%" nowrap class="textLabelBold">
                    <strong><bean:write name="frmEnrBufferDetail" property="avCorpBuffer"/></strong>
                    <html:hidden name="frmEnrBufferDetail" property="avCorpBuffer" />
        </td>
        
        <td width="26%" nowrap class="formLabel">Buffer-Family /Member Cap (Rs): <!--<span class="mandatorySymbol">*</span>--></td>
        <td width="22%" nowrap>
          <strong><bean:write name="frmEnrBufferDetail" property="avFamilyBuffer"/></strong>
           <html:hidden name="frmEnrBufferDetail" property="avFamilyBuffer" />
         </td>        
      
      </tr>
      <tr>
      <td width="22%" nowrap class="formLabel">Prev.Approved Member Buffer (Rs): </td>
        <td width="30%" nowrap>
           <strong><bean:write name="frmEnrBufferDetail" property="avMemberBuffer"/></strong>
           <html:hidden name="frmEnrBufferDetail" property="avMemberBuffer" />
   
    </td>
    <td width="26%" nowrap class="formLabel">HR/Insurer Approved Buffer (Rs): </td>
        <td width="22%" nowrap>
                 <html:text property="hrInsurerBuffAmount" name="frmEnrBufferDetail" styleClass="textBox textBoxMedium" maxlength="13" disabled="<%= viewmode %>" />
        
    </td>
      
      </tr>
      
      <tr>
           
        <td width="22%" nowrap class="formLabel">Member Buffer Amount(to be Configured): <span class="mandatorySymbol">*</span></td>
        <td width="30%" nowrap>
          <html:text property="bufferAmt" name="frmEnrBufferDetail" styleClass="textBox textBoxMedium" maxlength="10" disabled="<%= viewmode %>" />
        </td>
          
           <td width="26%" nowrap class="formLabel">Buffer Approved Date: <span class="mandatorySymbol">*</span></td>
        <td width="22%">
                 <html:text property="bufferDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>" /> 
            <a name="CalendarObjectClDate" id="CalendarObjectClDate" href="#" onClick="javascript:show_calendar('CalendarObjectClDate','frmEnrBufferDetail.bufferDate',document.frmEnrBufferDetail.bufferDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="ttk/images/CalendarIcon.gif" alt="Calendar" name="incpDate" width="24" height="17" border="0" align="absmiddle"></a>
        </td>
    
        
        
     </tr>
  
      
       <tr>
      
        <td width="22%" class="formLabel">Approval Authority:<span class="mandatorySymbol">*</span></td>
        <td width="30%" >
        <html:text property="approvedBy" name="frmEnrBufferDetail" styleClass="textBox textBoxMedium"  disabled="<%= viewmode %>" />
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
          <html:textarea property="remarks" styleClass="textBox textAreaLong"  disabled="<%= viewmode %>" />
        </td>
      </tr>
     
          </table>
  </fieldset>

    <!-- S T A R T : Buttons -->
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="center">
        <%
         if(TTKCommon.isAuthorized(request,"Add") || TTKCommon.isAuthorized(request,"Deduct"))
         {
        %>
			<logic:equal name="frmEnrBufferDetail" property="editYN" value="Y">
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
    <INPUT TYPE="hidden" NAME="patclmClaimType" VALUE="">
	<INPUT TYPE="hidden" NAME="patclmBufferType" VALUE="">
	<INPUT TYPE="hidden" NAME="patclmBufferType1" VALUE="">

      <html:hidden property="memberBufferAlloc" name="frmEnrBufferDetail"/>
	  <html:hidden property="memberSeqId" name="frmEnrBufferDetail"/>
    <html:hidden property="policySeqId" name="frmEnrBufferDetail"/>
    <html:hidden property="policyGroupSeqId" name="frmEnrBufferDetail"/>
  </html:form>
<!-- E N D : Buttons -->