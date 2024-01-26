<%
/**
 * @ (#) icdpcscoding.jsp Sep 13th, 2007
 * Project      : TTK HealthCare Services
 * File         : claimsdetails.jsp
 * Author       : Balakrishna Erram
 * Company      : Span Systems Corporation
 * Date Created : Sep 13th, 2007
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="JavaScript" SRC="/ttk/scripts/dataentrycoding/icdpcscoding.js"></SCRIPT>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>

<%
	boolean viewmode=true;
boolean codeviewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("ActiveLink",TTKCommon.getActiveLink(request));
 	pageContext.setAttribute("ActiveSubLink",TTKCommon.getActiveSubLink(request));
 	pageContext.setAttribute("alDiagnosisList",request.getSession().getAttribute("alDiagnosisList"));
%>
<html:form action="/DataEntryPreauthCodingAction.do" >
<bean:define id="CodingReviewYN" name="frmICDPCSPolicy" property="codingReviewYN"/>
<%
String codingReviewYN = CodingReviewYN.toString().trim();
	if(TTKCommon.isAuthorized(request,"Edit")&&"Y".equals(codingReviewYN))
	{
		  codeviewmode=false;
}//end of if(TTKCommon.isAuthorized(request,"Edit")&&"Y".equals(codingReviewYN))
%>
<!-- S T A R T : Content/Form Area -->
      <!-- S T A R T : Page Title -->


    <!-- <table align="center" class="<%=TTKCommon.checkNull(PreAuthWebBoardHelper.getShowBandYN(request)).equals("Y") ? "pageTitleHilite" :"pageTitle" %>" border="0" cellspacing="0" cellpadding="0"> -->
     <table width="98%" align="center"  border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	   		 <td></td>
	   		  <%if(!TTKCommon.isAuthorized(request,"SpecialPermission"))
	   		  {
	   			%>
	   		  <td align="right">
	   		  <bean:write name="frmICDPCSPolicy" property="codCompleteYN"/>
	   		   <logic:notMatch name="frmICDPCSPolicy" property="codCompleteYN" value="Y">
			  <a href="#" onClick="onDataEntryPromote()"><img src="ttk/images/PromoteButton.gif" alt="DataEntryPromote" width="81" height="17" border="0" align="absbottom"></a>
			  </logic:notMatch>
			  <logic:match name="frmICDPCSPolicy" property="codCompleteYN" value="Y">
			  <a href="#" onClick="onDataEntryRevert()"><img src="ttk/images/RevertButton.gif" alt="DataEntryRevert" width="81" height="17" border="0" align="absbottom"></a>
			  </logic:match>
	   		  <%  
	   		  }
	   		%>	  
			 
		   </td>
	    </tr>
	</table>
     
     
     <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
     <tr>
      <td width="57%">ICD / PCS Coding - <bean:write name="frmICDPCSPolicy" property="caption"/> </td>
      <td  align="right" class="webBoard">
				<%@ include file="/ttk/common/toolbar.jsp" %>
			</td>
      </tr>
    </table>

        <!-- E N D : Page Title -->
        <!-- S T A R T : Form Fields -->
        <!-- <div class="contentArea" id="contentArea">-->
        <!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">

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
		 <html:errors/>
        <fieldset>
        	<legend>Ailment Details</legend>
        	
  			  <logic:match name="ActiveSubLink" value="PreAuth">
  				<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		  			<tr>
     				 	<td width="27%" height="20" class="formLabel"> Diagnostic Description:</td>
     				 	<td width="73%" class="formLabelBold" align="left">
        					<bean:write name="frmPreAuthMedical" property="ailmentVO.provisionalDiagnosis"/>
     				    </td>
   				    </tr>
   				    <tr>
      				<td height="27" width="17%" class="formLabel"> Procedure Description:</td>
      				<td width="73%" class="formLabelBold" align="left"><bean:write name="frmPreAuthMedical" property="ailmentVO.lineOfTreatment"/></td>
     			 </tr>
			 	</table>
	   		  </logic:match>  	
	   		   <logic:match name="ActiveSubLink" value="Claims">
  				<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		  			<tr>
     				 	<td width="27%" height="20" class="formLabel"> Diagnostic Description:</td>
     				 	<td width="73%" class="formLabelBold" align="left">
        					<bean:write name="frmPreAuthMedical" property="ailmentVO.finalDiagnosis"/>
     				    </td>
   				    </tr>
   				    <tr>
      				<td width="27%" height="20" class="formLabel"> Procedure Description:</td>
      				<td width="73%" class="formLabelBold" align="left"><bean:write name="frmPreAuthMedical" property="ailmentVO.lineOfTreatment"/></td>
     			 </tr>
			 	</table>
	   		  </logic:match>		
	   		
        		<ttk:HtmlGrid name="AilmentTable"/>
        </fieldset>
        <fieldset>
        <legend>ICD Coding</legend>

        <table width="100%" border="0" cellpadding="0" cellspacing="0" class="formContainer" style="padding:0px;margin:0px;">

          <tr>
            <logic:match name="ActiveSubLink" value="Claims">
          	<td width="15%" height="25" nowrap class="formLabel">&nbsp;&nbsp;Diagnosis List: <span class="mandatorySymbol">*</span></td>  
            <td>
             <html:select name="frmICDPCSPolicy" property="diagSeqId"  styleClass="selectBox" disabled="<%=viewmode%>">          
	  		 <html:option value="0" >Select from list</html:option>
      		<html:options collection="alDiagnosisList"  property="cacheId" labelProperty="cacheDesc"/>
	  		</html:select>
			</td>
            </logic:match>
            
            <td width="15%" height="25" nowrap class="formLabel">&nbsp;&nbsp;ICD Code:</td>
            <td width="9%"  style="border-right:1px solid #cccccc;">
				<html:text property="sICDCode" style="width:60px;" styleId="sICDCode" styleClass="textBox textBoxMedium" maxlength="10" onchange="javascript:getDescription()" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);"/>
			</td>
            <td width="10%" align="center" nowrap style="border-right:1px solid #cccccc;">
				<a href="#" class="search"><img src="/ttk/images/ICDIcon.gif" alt="ICD Icon" width="16" height="16" border="0" align="absmiddle" onClick="javascript:onICDIconClick();"></a>
			</td>
            <td width="10%" align="center" ><a href="#" class="search" ><img src="/ttk/images/AddIcon.gif" alt="ICDCode" width="16" height="16" border="0" onClick="javascript:getNewICDCode()"></a></td>
            <td width="32%" align="left" ></td>           

          </tr>

          <tr>

            <td height="10" valign="top" nowrap >&nbsp;</td>
            <td colspan="4" class="textLabelBold">&nbsp;</td>
          </tr>
          <tr>
            <td height="20" valign="top" nowrap class="formLabel">&nbsp;&nbsp;Description:</td>
            <td colspan="4" class="textLabel"><!--<input name="companyname2" type="text" class="textBox textBoxLarge" style="width:99%;color:#0C48A2;" value="Nil" disabled="disabled" id="companyname3">
                         -->
			<bean:write name="frmICDPCSPolicy" property="sICDName"/>
			<!-- <html:text property="sICDName" styleClass="textBoxDisabled textBoxLarge" maxlength="60" readonly="true"/>-->
          </td>

          </tr>
          <!-- <tr>
        <td nowrap class="formLabel">Remarks:</td>
        <td colspan="3" nowrap class="textLabel"><textarea name="textarea" class="textBox textAreaLong"></textarea></td>
        </tr>-->
        </table>
        </fieldset>
        <fieldset>
        <legend>PCS Coding </legend>
        <table class="formContainer"  border="0" cellspacing="0" cellpadding="0" style="padding:0px;margin:0px;">
          <tr>
            <td width="15%" height="25" align="left" nowrap class="formLabel">&nbsp;&nbsp;PCS
              Code:</td>
            <td width="9%" style="border-right:1px solid #cccccc;">
					<html:text property="procedurecode" style="width:60px;" styleId="procedurecode" styleClass="textBox textBoxMedium" maxlength="250" onchange="javascript:getPCSDescription()" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);"/>	            
            </td>
            <td width="3%" align="center" nowrap class="formLabel" style="border-right:1px solid #cccccc;"><a href="#" class="search"><img src="/ttk/images/ICDIcon.gif" alt="PCS" width="16" height="16" border="0" align="absmiddle" onClick="javascript:assocdProcedures();"></a></td>
            
            <% if(TTKCommon.isAuthorized(request,"Edit"))
				{            	
	    	%>	
	    	  <logic:notMatch name="frmICDPCSPolicy" property="codCompleteYN" value="Y">
              <td width="72%" align="left" class="textLabel" >&nbsp;<button type="button" name="Button3" accesskey="t" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAssociate()">Associa<u>t</u>e</button></td>
              </logic:notMatch>	
            <%
            
                }//end of if(TTKCommon.isAuthorized(request,"Edit"))
	    	%>  
          </tr>
          <tr>
            <td height="10" valign="top" nowrap class="formLabel"></td>
            <td colspan="4" ></td>
          </tr>
          <tr>
            <td height="20" valign="top" nowrap class="formLabel">&nbsp;&nbsp;Description:</td>
            <td colspan="4" class="textLabel">
            <bean:write name="frmICDPCSPolicy" property="procedurename"/>
          </tr>
          <!-- <tr>
        <td nowrap class="formLabel">Remarks:</td>
        <td colspan="3" nowrap class="textLabel"><textarea name="textarea" class="textBox textAreaLong"></textarea></td>
        </tr>-->
        </table>
        <table width="100%"  border="0" cellpadding="0" cellspacing="0" class="formContainer" id="Tariff" >
          <tr id="package">
            <td width="14%" class="formLabel">&nbsp;</td>
            <td width="87%"  class="formLabelBold">&nbsp;<a href="SelectPackageNonPackage.htm"></a></td>
          </tr>
          <tr id="procedures" >
            <td rowspan="2" valign="top" class="formLabel" >&nbsp;Associated PCS:</td>
            <td align="left" class="formLabel">
            <html:select property="asscProcedure" style="width:100%;" size="10" styleClass="generaltext selectboxlarge">
                <html:option value="--------------------------------------------------------------------------------------------------------------------------------------------"/>
                <html:optionsCollection property="asscCodes" label="procedureDescription" value="procedureID" />
            </html:select>
            </td>
          </tr>
          <tr id="procedures" >
            <logic:notMatch name="frmICDPCSPolicy" property="codCompleteYN" value="Y">
            <td align="left" class="formLabel" style="padding-top:6px;">
             <button type="button" name="Button3" value=" Disassociate" accesskey="i" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:deleteAsscProc();">D<u>i</u>sassociate</button></td>
          	</logic:notMatch>
          </tr>
        </table>
        </fieldset>
        <!-- S T A R T : Buttons and Page Counter -->
        <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="100%" align="center">
            <%
				if(TTKCommon.isAuthorized(request,"Edit"))
				{
			%>
			  <logic:notMatch name="frmICDPCSPolicy" property="codCompleteYN" value="Y">
              <button type="button" name="Button2" accesskey="S" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
              <button type="button" name="Button2" accesskey="R" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
              </logic:notMatch>
              <%
				}
              %>
			<!--              <button type="button" name="Button2" accesskey="C" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button></td>  -->
          </tr>
        </table>
        <!-- E N D : Buttons and Page Counter -->
        <!-- S T A R T : Form Fields -->
      <!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->
<INPUT TYPE="hidden" NAME="mode" VALUE="">
  <input type="hidden" name="child" value="">
  <INPUT TYPE="hidden" NAME="rownum" VALUE="">
  <input type="hidden" name="focusID" value="">
  <input type="hidden" name="codCompleteYN" value="<bean:write name="frmICDPCSPolicy" property="codCompleteYN"/>"></input>
  <INPUT TYPE="hidden" NAME="deleteSeqId" VALUE="<bean:write name="frmICDPCSPolicy" property="deleteSeqId"/>">
  <html:hidden property="selectedProcedureCode" value=""/>
</html:form>


                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           