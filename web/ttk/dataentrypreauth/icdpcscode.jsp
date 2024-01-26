<%
/** @ (#) icdpcscode.jsp
 * Project     		: TTK Healthcare Services
 * File        		: icdpcscode.jsp
 * Author      		: Pradeep R
 * Company     		: Span Systems Corporation
 * Date Created		: 10th May
 *
 * @author 	   		:
 * Modified by   	:
 * Modified date 	:
 * Reason        	:
 *
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper"%>
<%@ page import="com.ttk.common.CodingClaimsWebBoardHelper"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<script language="javascript" src="/ttk/scripts/dataentrypreauth/icdpcscode.js"></script>

<%
  pageContext.setAttribute("listGeneralCodePlan",Cache.getCacheObject("generalCodePlan"));
  pageContext.setAttribute("listHospitalizationType",Cache.getCacheObject("hospitalizationType"));
  pageContext.setAttribute("listTreatmentPlan",Cache.getCacheObject("treatmentPlanICD"));
  pageContext.setAttribute("listDurationType",Cache.getCacheObject("durationType"));
  pageContext.setAttribute("ActiveLink",TTKCommon.getActiveLink(request));
  pageContext.setAttribute("ActiveSubLink",TTKCommon.getActiveSubLink(request));

  boolean viewmode=true;
  boolean codeviewmode=true;
  String strAmmendment="";
  if(TTKCommon.isAuthorized(request,"Edit"))
  {
    viewmode=false;
  }//end of if(TTKCommon.isAuthorized(request,"Edit"))
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/DataEntryICDPCSCodingAction.do">
<bean:define id="CodingReviewYN" name="frmICDPCSCoding" property="codingReviewYN"/>
<%
String codingReviewYN = CodingReviewYN.toString().trim();
if(TTKCommon.isAuthorized(request,"Edit")&&"Y".equals(codingReviewYN))
{
	  //codeviewmode=false;
}//end of if(TTKCommon.isAuthorized(request,"Edit")&&"Y".equals(codingReviewYN))
%>
<!-- S T A R T : Page Title -->
   <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
      <td>ICD / PCS Coding - <bean:write name="frmICDPCSCoding" property="caption"/></td>
    </tr>
  </table>
<!-- E N D : Page Title -->

 <!-- E N D : Success Box -->
<!-- S T A R T : Form Fields -->
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
 <fieldset>
 <legend>Ailment Details</legend>
  <table class="formContainer" width="100%" cellpadding="0" cellspacing="0" border="0">
    <tr>
      <td width="25%" class="indentedLabels">Complaints:</td>
      <td width="75%" class="formLabelBold"> <bean:write name="frmPreAuthMedical" property="ailmentVO.ailmentDesc"/></td>
    </tr>
    	<%
          if(("Claims".equals(TTKCommon.getActiveLink(request))||"Claims".equals(TTKCommon.getActiveSubLink(request))))
          { 
        %>
		<tr>
			<td class="indentedLabels">Final Diagnosis:</td>
			<td class="formLabelBold"><bean:write
				name="frmPreAuthMedical" property="ailmentVO.finalDiagnosis" /></td>
			</td>
		</tr>
		<%
  		  }
    	%>
		<%
          if(("Pre-Authorization".equals(TTKCommon.getActiveLink(request))||"PreAuth".equals(TTKCommon.getActiveSubLink(request))))
          { 
        %>
        <tr>
          <td class="indentedLabels">Provisional Diagnosis:</td>
          <td class="formLabelBold"> <bean:write name="frmPreAuthMedical" property="ailmentVO.provisionalDiagnosis"/></td> 
        </tr>
 		<%
  		  }
    	%>   
  </table>
 </fieldset>
<fieldset>
<legend>ICD / PCS Coding</legend>
  <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
       <tr>
         <td width="25%" nowrap class="formLabel indentedLabels">Ailment Description: <% if("Y".equals(codingReviewYN)){%><span class="mandatorySymbol">*</span><%} %></td>
         <td>
         <bean:write name="frmICDPCSCoding" property="description"/>

         <%
         if(TTKCommon.isAuthorized(request,"Edit")&&"Y".equals(codingReviewYN))
        	{
         %>
               &nbsp;<a href="#" onClick="javscript:onICDIconClick()"><img src="/ttk/images/ICDIcon.gif" alt="ICD Code" width="16" height="16" border="0" align="absmiddle"></a>
         <%
             }//end of if(TTKCommon.isAuthorized(request,"Edit")&&"Y".equals(codingReviewYN))
         %>
         <html:hidden property="description"/>
         <html:hidden property="PEDCodeID"/>
     	 </td>
       </tr>
       <tr>
         <td nowrap class="formLabel indentedLabels">ICD Code:</td>
         <td nowrap class="formLabel">
           <html:text styleClass="textBoxDisabled textBoxSmall" property="ICDCode" disabled="true"/>
           <html:hidden property="ICDCode"/>
         </td>
       </tr>
	  <tr id="procedures" style="display:">
       <td valign="top" class="formLabel indentedLabels">Procedures: </td>
       <td class="formLabel">
         <TABLE WIDTH="70%" BORDER="0" CELLSPACING="0" CELLPADDING="0">
            <TR>
              <TD height="2"></TD>
              <TD></TD>
            </TR>
            <TR>
              <TD ALIGN="left">
                 <html:select property="asscProcedure" styleClass="generaltext selectBoxListMoreLarger" size="10" >
              <html:option value="">---------------------------------------------------------------------------------------------------------------</html:option>
              <html:optionsCollection property="asscCodes" label="procedureDescription" value="procedureID" />
             </html:select>

              </TD>
              <TD WIDTH="40%" ALIGN="left" valign="bottom">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
              <%
              if(!codeviewmode)
              {
             %>
                  <button type="button" name="Button3" accesskey="t" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:assocdProcedures();">Associa<u>t</u>e</button>
                  <br>
                  <br>
                 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                 <button type="button" name="Button2" accesskey="m" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:deleteAsscProc();">Re<u>m</u>ove</button>
            <%
             }//end of if(!codeviewmode)
           %>
            </TD>
            </TR>
            <TR>
              <TD ALIGN="left" height="5"></TD>
              <TD ALIGN="left"></TD>
            </TR>
          </TABLE>
         </td>
     </tr>
   
   <tr>
	<td class="formLabel indentedLabels">AilmentType: <span class="mandatorySymbol">*</span></td>
	<td>
      <html:select name="frmICDPCSCoding" property="primaryAilmentYN"  styleClass="selectBox" disabled="<%=viewmode%>">          
	  <html:option value="PRE">Primary</html:option>
	  <html:option value="SEC">Secondary</html:option>
	  <html:option value="COM">Comorbidities</html:option>
	  </html:select>
	</td>
	</tr>
       
       
       
    <tr>
      <td class="formLabel indentedLabels">Hospitalization Type: <span class="mandatorySymbol">*</span></td>
      <td class="formLabel">
        <html:select property="hospitalTypeID"  styleClass="selectBox selectBoxMedium" onchange="showhideHospType()" disabled="<%=viewmode%>">
          <html:option value="" >Select from list</html:option>
          <html:options collection="listHospitalizationType"  property="cacheId" labelProperty="cacheDesc"/>
        </html:select>
    </td>
    </tr>

   <logic:match name="frmICDPCSCoding" property="hospitalTypeID" value="REP" >
    <tr id="FV" style="display:">
   </logic:match>
    <logic:notMatch name="frmICDPCSCoding" property="hospitalTypeID" value="REP" >
    <tr id="FV" style="display:none;">
   </logic:notMatch>
      <td class="formLabel indentedLabels">Frequency of Visits: <span class="mandatorySymbol">*</span></td>
      <td class="formLabel">
      	<table cellpadding="2" cellspacing="0">
      		<tr>
      			<td>
      			<html:text styleClass="textBox textBoxTiny" name="frmICDPCSCoding" property="frequencyVisit" maxlength="5" disabled="<%=viewmode%>"/></td>
      			<td>
      			<html:select property="frequencyVisitType"  styleClass="selectBox" disabled="<%=viewmode%>">
			    <html:options collection="listDurationType"  property="cacheId" labelProperty="cacheDesc"/>
			  </html:select>
			  </td>
      		</tr>
      	</table>         
        </td>
    </tr>
    <logic:match name="frmICDPCSCoding" property="hospitalTypeID" value="REP" >
    <tr id="NV" style="display:">
   </logic:match>
    <logic:notMatch name="frmICDPCSCoding" property="hospitalTypeID" value="REP" >
     <tr id="NV" style="display:none;">
   </logic:notMatch>

      <td class="formLabel indentedLabels">No.of Visits: <span class="mandatorySymbol">*</span></td>
      <td class="formLabel">
      <html:text styleClass="textBox textBoxTiny" name="frmICDPCSCoding" property="noofVisits" maxlength="5" disabled="<%=viewmode%>"/>
      </tr>
      
      
      
    <tr>
      <td class="formLabel indentedLabels">Treatment Plan: <span class="mandatorySymbol">*</span></td>
      <td class="formLabel">

        <html:select property="treatmentPlanTypeID"  styleClass="selectBox selectBoxMedium" onchange="showhideTariffType(this)" disabled="<%=viewmode%>">
          <html:option value="" >Select from list</html:option>
          <html:options collection="listTreatmentPlan"  property="cacheId" labelProperty="cacheDesc"/>
        </html:select>

      </td>
    </tr>
    </table>
     <logic:match name="frmICDPCSCoding" property="treatmentPlanTypeID" value="SUR" >
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0" id="Tariff"style="display:">
  </logic:match>
    <logic:notMatch name="frmICDPCSCoding" property="treatmentPlanTypeID" value="SUR" >
      <table class="formContainer"  border="0" cellspacing="0" cellpadding="0" id="Tariff" style="display:none;">
   </logic:notMatch>

    <tr>
       <td width="25%" class="formLabel indentedLabels">Tariff Item:</td>
       <td width="75%" class="formLabelBold">
         <html:select property="tariffItemVO.tariffItemType"  styleClass="selectBox selectBoxMedium"  disabled="<%=viewmode%>" onchange="javascript:showhideTariffItem()">
          <html:options collection="listGeneralCodePlan"  property="cacheId" labelProperty="cacheDesc"/>
        </html:select>
       </td>
    </tr>

       <logic:notMatch name="frmICDPCSCoding" property="tariffItemVO.tariffItemType" value="NPK" >
        <tr id="package" style="display:">
       </logic:notMatch>
       <logic:match name="frmICDPCSCoding" property="tariffItemVO.tariffItemType" value="NPK" >
        <tr id="package" style="display:none;">
       </logic:match>
         <td class="formLabel indentedLabels">Package: <span class="mandatorySymbol">*</span></td>
         <td class="formLabelBold">
            <html:text styleClass="textBoxDisabled textBoxLarge" property="tariffItemVO.tariffItemName" disabled="true" styleId="package" />
            <html:hidden property="tariffItemVO.tariffItemId"/>
        <%
        if(TTKCommon.isAuthorized(request,"Edit")&&!"Y".equals(codingReviewYN))
        {
           %>
          &nbsp;<a href="#" onClick="javascript:onSelectPackage()"><img src="ttk/images/EditIcon.gif" alt="Select Package" width="16" height="16" border="0" align="absmiddle"></a>
         <%
             }//end of if(TTKCommon.isAuthorized(request,"Edit")&&!"Y".equals(codingReviewYN))
          %>
         </td>
      </tr>
      <%--<logic:match name="frmICDPCSCoding" property="tariffItemVO.tariffItemType" value="NPK" >
     <tr id="procedures" style="display:">
     </logic:match>
     <logic:notMatch name="frmICDPCSCoding" property="tariffItemVO.tariffItemType" value="NPK" >
      <tr id="procedures" style="display:none;">
     </logic:notMatch>--%>
    
    </table>
</fieldset>
<!-- E N D : Form Fields -->
<!-- S T A R T : Buttons -->
  <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
     <tr>
       <td width="100%" align="center">
       <%--<logic:match name="ActiveSubLink" value="PreAuth">--%>
        
          	<!-- <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
 	      	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp; -->
        
       <%--</logic:match>--%>
       <%--<logic:match name="ActiveSubLink" value="Claims">--%>
         
	 	<%--</logic:match>--%>
	 	
		<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
	 	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
	 	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
       </td>
     </tr>
  </table>
<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="ICDPCS">
<html:hidden property="selectedProcedureCode" value=""/>
<INPUT TYPE="hidden" NAME="deleteSeqId" VALUE="<bean:write name="frmICDPCSCoding" property="deleteSeqId"/>">

<logic:notEmpty name="frmICDPCSCoding" property="frmChanged">
  <script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>

</html:form>