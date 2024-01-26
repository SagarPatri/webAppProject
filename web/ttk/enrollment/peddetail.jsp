<%
/** @ (#) peddetail.jsp Feb 17, 2006
 * Project       : TTK Healthcare Services
 * File          : peddetail.jsp
 * Author        : Pradeep R
 * Company     	 : Span Systems Corporation
 * Date Created  : Feb 17, 2006
 *
 * @author 		 : Pradeep R
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.ClaimsWebBoardHelper"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/enrollment/peddetail.js"></SCRIPT>
<%
  pageContext.setAttribute("descriptionList",Cache.getCacheObject("icdDescription"));
  pageContext.setAttribute("durationMonths",Cache.getCacheObject("durationMonths"));
  //added for koc 1278
  pageContext.setAttribute("listDurationType",Cache.getCacheObject("durationType"));
  pageContext.setAttribute("listAilmentType",Cache.getCacheObject("ailmentType"));
  //added for koc 1278
  boolean viewmode=true;
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }//end of if(TTKCommon.isAuthorized(request,"Edit"))
  
    if(TTKCommon.getActiveLink(request).equals("Claims"))
	{
		pageContext.setAttribute("AmmendMent_Flow",ClaimsWebBoardHelper.getAmmendmentYN(request));
		if("Y".equals(ClaimsWebBoardHelper.getAmmendmentYN(request)))
		{
			viewmode=true;	//If ammendment flow disable the fields
		}//end of if("Y".equals(ClaimsWebBoardHelper.getAmmendmentYN(request)))
	}//end of if(TTKCommon.getActiveLink(request).equals("Claims"))
	else
	{
		pageContext.setAttribute("AmmendMent_Flow","N");
	}//end of else

%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/AddPEDAction.do" >

<!-- S T A R T : Page Title -->
  <logic:match name="frmAddPED" property="switchType" value="ENM">
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  </logic:match>
  <logic:match name="frmAddPED" property="switchType" value="END">
    <table align="center" class="pageTitleHilite" border="0" cellspacing="0" cellpadding="0">
  </logic:match>

      <tr>
        <td width="100%">PED Information <bean:write name="frmAddPED" property="caption1" /></td>
        <td align="right">&nbsp;&nbsp;&nbsp;</td>
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
  <div >
    <fieldset>
    <legend>General</legend>
      <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
			 <%
			 if(!(TTKCommon.getActiveLink(request).equals("Enrollment")))
          	   {
             %>
			<tr>
				<td nowrap class="formLabel">Policy Number:</td>
				<td nowrap class="textLabelBold"><bean:write name="frmAddPED"property="policyNbr" /> 
					<html:hidden property="policyNbr" />
				</td>
			</tr>
			<%} %>
			<tr>
             <td nowrap class="formLabel">Description: <span class="mandatorySymbol">*</span></td>
             <td colspan="3">
             <bean:write name="frmAddPED" property="description"/>
             <%--
               if(TTKCommon.isAuthorized(request,"Edit"))
          	   {
             %>
                 <logic:match name="frmAddPED" property="editYN" value="Y">
                 <logic:notEqual name="AmmendMent_Flow" value="Y">
                 	&nbsp;<a href="#" onClick="javscript:onICDIconClick()"><img src="/ttk/images/ICDIcon.gif" alt="ICD Code" width="16" height="16" border="0" align="absmiddle"></a>
                 </logic:notEqual>	
                 </logic:match>
             <%
               }//end of if(TTKCommon.isAuthorized(request,"Edit"))
             --%>
             <html:hidden property="description"/>
             <html:hidden property="PEDCodeID"/>
             </td>
             <td valign="bottom" nowrap class="formLabel">&nbsp;</td>
           </tr>
           <tr>
             <td width="15%" height="25" nowrap class="formLabel">ICD Code:</td>
             <td>
             	<table>
             		<tr>
             			<td>
             				<html:text property="ICDCode" style="width:60px;" styleId="ICDCode" styleClass="textBoxDisabled textBoxSmall" disabled="true" maxlength="10" onchange="javascript:getDescription()" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);"/>
							<html:hidden property="ICDCode"/>
						</td>
			 			<%-- added for koc 1278 --%>
			 			<%
			 			if(TTKCommon.getActiveLink(request).equals("Enrollment"))
          	  			{
             			%>
             			<td nowrap class="formLabel">
             				<a href="#" ><img src="/ttk/images/ICDIcon.gif" alt="ICDCode" width="16" height="16" border="0" onClick="javascript:onICDIconClick()"></a>
             			</td>
             			<%}%>
             			<%-- added for koc 1278 --%>
             		</tr>
             	</table>
			 </td>
          </tr>
           <tr>
             <td nowrap class="formLabel">Since (Month / Year): <span class="mandatorySymbol">*</span></td>
             <td valign="bottom" nowrap class="formLabel" width="10%">
             	<table cellpadding="1" cellspacing="0">
             		<tr>
             			<td>
             				<html:select property="durationMonths" name="frmAddPED" styleClass="selectBox selectBoxSmall"  disabled="<%=viewmode%>">
					<html:option value="">Select from list</html:option>
					<html:options collection="durationMonths" property="cacheId" labelProperty="cacheDesc"/>
			     		</html:select>	
             			</td>
             			<td>
             				<html:text styleClass="textBox textBoxSmall" property="durationYears" maxlength="4" disabled="<%=viewmode%>"/>
             			</td>
             		</tr>
             	</table> 
             </td>
        
             <td valign="bottom" nowrap class="formLabel">&nbsp;</td>
           </tr>
           <tr>
             <td nowrap class="formLabel">Remarks / Description: <span class="mandatorySymbol">*</span></td><%-- Description added for koc 1278 --%>
             <td valign="bottom" nowrap class="formLabel">
              <html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%= viewmode %>"/>
             </td>
             <td valign="bottom" nowrap class="formLabel">&nbsp;</td>
           </tr>
           <!-- added for koc 1278 -->
           <%
			if(TTKCommon.getActiveLink(request).equals("Enrollment"))
          	{
           %>
           <tr>
      			<td class="formLabel">Ailment Type: <span class="mandatorySymbol">*</span></td>
			    <td class="formLabel">
        			<html:select property="ailmentTypeID"  styleClass="selectBox selectBoxMedium" onchange="showhideAilmentType()"  disabled="<%=viewmode%>">
          			<html:option value="" >Select from list</html:option>
          			<html:options collection="listAilmentType"  property="cacheId" labelProperty="cacheDesc"/>
        			</html:select>
      			</td>
    	   </tr>
           <tr>
           		<td nowrap class="formLabel">Waiting Period: <span class="mandatorySymbol">*</span></td>
           		<td valign="bottom" nowrap class="formLabel" width="10%">
             		<table cellpadding="1" cellspacing="0">
             			<tr>
      						<td>
      							<html:text styleClass="textBox textBoxTiny" property="waitingPeriod" maxlength="5" disabled="<%=viewmode%>"/>
      						</td>
      						<td>
      							<html:select property="personalWaitingPeriod"  styleClass="selectBox" disabled="<%=viewmode%>">
			    				<html:options collection="listDurationType"  property="cacheId" labelProperty="cacheDesc"/>
			  					</html:select>
							</td>
						</tr>
					</table>
				</td>
      	   </tr>
      	  <%}%>
      	  <!-- added for koc 1278 -->
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
	             <logic:match name="frmAddPED" property="editYN" value="Y">
					<logic:notEqual name="AmmendMent_Flow" value="Y">
						<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
						<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
					</logic:notEqual>
	            </logic:match>
	         <%
	           }//end of if(TTKCommon.isAuthorized(request,"Edit"))
	          %>
			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>
	<!-- E N D : Buttons -->
	</div>
	<html:hidden property="description"/>
    <html:hidden property="PEDCodeID"/>
    <html:hidden property='leftlink' name='frmAddPED' value="<%=TTKCommon.getActiveLink(request)%>"/>
    <html:hidden property='tab' name='frmAddPED' value="<%=TTKCommon.getActiveTab(request)%>" />
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<logic:notEmpty name="frmAddPED" property="frmChanged">
	  <script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</html:form>
