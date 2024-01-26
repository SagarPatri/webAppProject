<%
/** @ (#) claimdetails.jsp July 18, 2006
 * Project    	 : TTK Healthcare Services
 * File       	 : claimdetails.jsp
 * Author     	 : Raghavendra T M
 * Company    	 : Span Systems Corporation
 * Date Created	 : July 18, 2006
 * @author 		 : Raghavendra T M
 * Modified by   : Krupa J
 * Modified date :
 * Reason        :
 */
 %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/preauth/investigation.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/inwardentry/claimdetails.js"></script>
<%
	//String strLink=TTKCommon.getActiveLink(request);
	//String strSubLink = TTKCommon.getActiveSubLink(request);
	String ampm[] = {"AM","PM"};
	String strStyleText="textBox textBoxMedium";
	String strStyleSelect = "selectBox selectBoxMedium";
	boolean strDisable=true;
	boolean viewmode=true;
	boolean viewenroll=true;
	if((TTKCommon.isAuthorized(request,"Add"))||(TTKCommon.isAuthorized(request,"Edit")))
	{
		viewmode=false;
%>
	<logic:empty name="frmClaimDetails" property="claimantVO.enrollmentID">
		<%
		viewenroll = false;strDisable=false;%>
	</logic:empty>
	<logic:notEmpty name="frmClaimDetails" property="claimantVO.enrollmentID">
		<%
		viewenroll = true;strDisable=true;strStyleText="textBox textBoxMedium textBoxDisabled";strStyleSelect = "selectBox selectBoxMedium selectBoxDisabled";%>
	</logic:notEmpty>

<%	}
	pageContext.setAttribute("ampm",ampm);
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("claimDocumentType",Cache.getCacheObject("claimDocumentType"));
	pageContext.setAttribute("claimSource",Cache.getCacheObject("claimSource"));
	pageContext.setAttribute("claimType", Cache.getCacheObject("claimType"));
	pageContext.setAttribute("insCompany",Cache.getCacheObject("insuranceCompany"));
	pageContext.setAttribute("inwardStatus",Cache.getCacheObject("inwardStatus"));
	pageContext.setAttribute("ttkBranch",Cache.getCacheObject("officeInfo"));
%>

	<html:form action="/EditClaimDetailsAction.do">
	<bean:define id="alPrevClaim" name="frmClaimDetails" property="alPrevClaim"/>

	<!-- S T A R T : Content/Form Area -->
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Claim Details</td>
  </tr>
</table>
	<!-- E N D : Page Title -->
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
	<!-- E N D : Success Box -->
	<fieldset>
    <legend>General</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" class="formLabel">Inward No.: </td>
        <td width="30%" class="textLabelBold"><bean:write property="inwardNbr" name="frmClaimDetails"/></td>
        <td width="19%" class="formLabel">Claim No.:</td>
        <td width="30%" class="textLabelBold"><bean:write property="currentClaimNbr" name="frmClaimDetails"/></td>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Document Type: <span class="mandatorySymbol">*</span></td>
        <td class="textLabelBold">
        <logic:empty name="frmClaimDetails" property="inwardNbr">
       		<html:select property="documentTypeID"  styleId="docType" styleClass="selectBox selectBoxMedium" onchange="showRelated(document.forms[1].sourceTypeID.value);" disabled="<%=viewmode%>">
				<html:option value="" >Select from list</html:option>
 				<html:options collection="claimDocumentType"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select>
	    </logic:empty>
	    <logic:notEmpty name="frmClaimDetails" property="inwardNbr">
	    <bean:write property="documentTypeDesc" name="frmClaimDetails"/>
	    </logic:notEmpty>
        </td>
        <td nowrap="nowrap" class="formLabel"> Received Date: <span class="mandatorySymbol">*</span></td>
        <td class="textLabelBold">
        <html:text property="receivedDate"  styleClass="textBox textDate" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
        <logic:match name="viewmode" value="false">
        <A NAME="calStartDate" ID="calStartDate" HREF="#" onClick="javascript:show_calendar('calStartDate','forms[1].receivedDate',document.forms[1].receivedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
        </logic:match>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Source: <span class="mandatorySymbol">*</span></td>
        <td class="textLabelBold">
        	<html:select property="sourceTypeID"  styleId="source" styleClass="selectBox selectBoxMedium" onchange="showHideChklist();" disabled="<%=viewmode%>">
				<html:option value="" >Select from list</html:option>
 				<html:options collection="claimSource"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select>
		</td>
		<logic:notMatch name="frmClaimDetails" property="sourceTypeID" value="CCR">
		<td class="formLabel" ></td>
		<td class="textLabelBold" >
		</logic:notMatch>
        <logic:match name="frmClaimDetails" property="sourceTypeID" value="CCR">
        <td class="formLabel" >Courier No.: </td>
        <td class="textLabelBold" >
        <bean:write property="courierNbr" name="frmClaimDetails"/>&nbsp;&nbsp;&nbsp;
        <logic:match name="viewmode" value="false">
        <a href="#" onclick="selectCourier()"><img src="/ttk/images/EditIcon.gif" alt="Select Courier No." width="16" height="16" border="0" align="absmiddle"></a>
        </logic:match>
		</logic:match>
		</td>
      </tr>
      <tr>
      	<td width="20%" class="formLabel">Bar Code No. : </td>
      	<td width="30%" class="textLabelBold">
        <html:text property="barCodeNbr" styleClass="textBox textBoxMedium" maxlength="10"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	  </tr>
    </table>
	</fieldset>

	<logic:match name="frmClaimDetails" property="documentTypeID" value="DTC">
	<fieldset>
    <legend>Claim Details </legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" class="formLabel">Claim Type: <span class="mandatorySymbol">*</span></td>
        <td width="30%" class="textLabelBold">
        	<html:select property="claimTypeID"  styleId="select" styleClass="selectBox selectBoxMedium" onchange="showRelatedData(document.forms[1].sourceTypeID.value);" disabled="<%=viewmode%>">
				<html:option value="" >Select from list</html:option>
 				<html:options collection="claimType"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select>
         </td>
        <td width="19%" class="formLabel"> Requested  Amt. : <span class="mandatorySymbol">*</span></td>
        <td width="31%" class="textLabelBold">
        <html:text property="requestedAmt"  styleClass="textBox textBoxMedium" maxlength="13"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Enrollment ID: </td>
        <td nowrap class="textLabelBold"><bean:write property="claimantVO.enrollmentID" name="frmClaimDetails"/>&nbsp;&nbsp;&nbsp;<logic:match name="viewmode" value="false"><a href="#" onClick="javascript:selectEnrollmentID();"><img src="/ttk/images/EditIcon.gif" alt="Select Enrollment ID" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;<a href="#" onclick="clearEnrollmentID();"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Enrollment Id" width="16" height="16" border="0" align="absmiddle"></a></logic:match></td>
        <td class="formLabel">Member Name:</td>
        <td class="textLabelBold">
        <html:text property="claimantVO.name"  styleClass="<%=strStyleText%>" maxlength="60"  disabled="<%=viewmode%>" readonly="<%=viewenroll%>"/>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Policy No.: </td>
        <td class="textLabelBold">
        <html:text property="claimantVO.policyNbr" styleClass="<%=strStyleText%>" maxlength="60"  disabled="<%=viewmode%>" readonly="<%=viewenroll%>"/>
        </td>
        <td class="formLabel">Healthcare Company:</td>
        <td class="textLabelBold">
        	<html:select property="claimantVO.insSeqID"  name="frmClaimDetails" styleId="select" disabled="<%=strDisable%>" styleClass="<%=strStyleSelect%>" >
				<html:option value="" >Select from list</html:option>
 				<html:options collection="insCompany"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Corporate Name:</td>
        <td class="textLabelBold">
		<html:text property="claimantVO.groupName" styleClass="<%=strStyleText%>" maxlength="120"  disabled="<%=viewmode%>" readonly="<%=viewenroll%>"/>
        </td>
        <td class="formLabel">Policy Holder Name: </td>
        <td class="textLabelBold">
        <html:text property="claimantVO.policyHolderName" styleClass="<%=strStyleText%>" maxlength="60"  disabled="<%=viewmode%>" readonly="<%=viewenroll%>"/>
		</td>
      </tr>
      <tr>
        <td class="formLabel">Employee No.: </td>
        <td class="textLabelBold">
        <html:text property="claimantVO.employeeNbr" styleClass="<%=strStyleText%>" maxlength="60"  disabled="<%=viewmode%>" readonly="<%=viewenroll%>"/>
        </td>
        <td class="formLabel">Employee Name: </td>
        <td class="textLabelBold">
        <html:text property="claimantVO.employeeName"  styleClass="<%=strStyleText%>" maxlength="60"  disabled="<%=viewmode%>" readonly="<%=viewenroll%>"/>
        </td>
      </tr>
      <tr>
     	<td class="formLabel">Policy Name: </td>
		<td class="formLabel">
			<html:text property="claimantVO.insScheme" styleClass="<%=strStyleText%>" maxlength="60" disabled="<%= viewmode %>" readonly="<%=viewenroll%>"/>
		</td>
		<td class="formLabel">Certificate No.: </td>
		<td class="formLabel">
			<html:text property="claimantVO.certificateNo" styleClass="<%=strStyleText%>" maxlength="60" disabled="<%= viewmode %>" readonly="<%=viewenroll%>"/>
		</td>
	  </tr>
	  <tr>
	  	<td class="formLabel">Customer Code: </td>
		<td class="formLabel">
			<html:text property="claimantVO.insCustCode" styleClass="<%=strStyleText%>" maxlength="60" disabled="<%= viewmode %>" readonly="<%=viewenroll%>"/>
		</td>
	  </tr>
      <tr>
      	<td class="formLabel">Phone (Notify): </td>
        <td class="textLabelBold">
        <html:text property="claimantVO.notifyPhoneNbr"  styleClass="textBox textBoxMedium" maxlength="13"  disabled="<%=viewmode%>" />
        </td>
        <td class="formLabel">Email Id: </td>
        <td class="textLabelBold">
        <html:text property="claimantVO.emailID" styleClass="textBox textBoxLarge" maxlength="60"  disabled="<%=viewmode%>" />
        </td>
        
      </tr>
      <tr>
      <td class="formLabel">Vidal Health Branch: <span class="mandatorySymbol">*</span></td>
        <td>
        	<html:select  property="officeSeqID"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>" >
        		<html:option value="">Select from list</html:option>
          		<html:optionsCollection name="ttkBranch" value="cacheId" label="cacheDesc"/>
            </html:select>
        </td>
        <td class="formLabel"> Inward Status:</td>
        <td class="textLabelBold">
        	<html:select property="statusTypeID"  styleId="select" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
 				<html:options collection="inwardStatus"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Remarks: </td>
        <td colspan="3" class="textLabel">
        <html:textarea property="remarks"  styleClass="textBox textAreaLong"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        </td>
      </tr>
    </table>
	</fieldset>
</logic:match>

	<logic:match name="frmClaimDetails" property="documentTypeID" value="DTS">
	<fieldset>
    <legend>Claim Details</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" class="formLabel">Claim No.: <span class="mandatorySymbol">*</span></td>
        <td width="30%" nowrap class="textLabelBold"><bean:write property="claimantVO.claimNbr" name="frmClaimDetails"/>&nbsp;&nbsp;&nbsp;<logic:match name="viewmode" value="false"><a href="#" onClick="javascript:selectClaimID();"><img src="/ttk/images/EditIcon.gif" alt="Select Claim No" width="16" height="16" border="0" align="absmiddle"></a></logic:match>&nbsp;&nbsp;</td>
        <td width="19%" class="formLabel">Member Name: </td>
        <td width="31%" class="textLabelBold">
        	<html:text property="claimantVO.name"  styleClass="<%=strStyleText%>" maxlength="60"  disabled="<%=viewmode%>" readonly="<%=viewenroll%>"/>
        <%--<html:text property="claimantVO.name"  styleClass="<%=strStyleText%>" maxlength="60"  disabled="<%=viewmode%>" readonly="<%=viewenroll%>"/>--%>
        </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="formLabel">Enrollment ID: </td>
        <td class="textLabelBold">
        	<bean:write property="claimantVO.enrollmentID" name="frmClaimDetails" />
        	<%--<html:text property="claimNbr"  styleClass="textBox textBoxMedium" maxlength="60"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>--%>
        </td>
        <td class="formLabel">Shortfall No.: <span class="mandatorySymbol">*</span></td>
        <td class="textLabelBold">
        <logic:notEmpty name="frmClaimDetails" property="alShortfall" >
        	<html:select property="shortfallSeqID"  name="frmClaimDetails" styleId="select" styleClass="selectBox selectBoxMoreMedium" disabled="<%= viewmode %>" >
        		<html:option value="">Select from list</html:option>
 				<html:optionsCollection property="alShortfall" label="cacheDesc" value="cacheId"/> 				
	    	</html:select>
	    </logic:notEmpty>
	    <logic:empty name="frmClaimDetails" property="alShortfall" >
	    	<html:select property="shortfallSeqID"  styleId="select" styleClass="selectBox selectBoxMoreMedium" disabled="<%= viewmode %>" >
        		<html:option value="">Select from list</html:option>
        	</html:select>	
	    </logic:empty>	
        <%--<html:text property="shortfallID"  styleClass="textBox textBoxMedium" maxlength="60"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>--%>
		</td>
      </tr>
      <tr>
        <td class="formLabel">Vidal Health Branch: <span class="mandatorySymbol">*</span></td>
        <td>
        	<html:select  property="officeSeqID"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>" >
        		<html:option value="">Select from list</html:option>
          		<html:optionsCollection name="ttkBranch" value="cacheId" label="cacheDesc"/>
            </html:select>
        </td>
        <td class="formLabel"> Inward Status:</td>
        <td class="textLabelBold">
        	<html:select property="statusTypeID"  styleId="select" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
 				<html:options collection="inwardStatus"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select></td>
      </tr>
      <tr>
        <td class="formLabel">Remarks: </td>
        <td colspan="3" class="textLabel">
        <html:textarea property="remarks" styleClass="textBox textAreaLong"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        </td>
      </tr>
    </table>
	</fieldset>
</logic:match>

	<logic:match name="frmClaimDetails" property="documentTypeID" value="DTA">
		<fieldset>
    <legend>Claim Details</legend>
    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" class="formLabel">Enrollment ID: <span class="mandatorySymbol">*</span></td>
        <td width="30%" nowrap class="textLabelBold"><bean:write property="claimantVO.enrollmentID" name="frmClaimDetails"/>&nbsp;&nbsp;&nbsp;<logic:match name="viewmode" value="false"><a href="#" onClick="javascript:selectEnrollmentID();"><img src="/ttk/images/EditIcon.gif" alt="Select Enrollment ID" width="16" height="16" border="0" align="absmiddle"></a></logic:match>&nbsp;&nbsp;</td>
        <td width="19%" class="formLabel">Claimant Name:</td>
        <td width="31%" class="textLabelBold">
        <html:text property="claimantVO.name"  styleClass="<%=strStyleText%>" maxlength="60"  disabled="<%=viewmode%>" readonly="<%=viewenroll%>"/>
        </td>
      </tr>
      <tr>
        <td nowrap="nowrap" class="formLabel">Claim No.: <span class="mandatorySymbol">*</span></td>
        <td class="textLabelBold">
        <bean:define id="statusTypeId" name="frmClaimDetails" property="statusTypeID"/>
     	<bean:define id="inwardNum" name="frmClaimDetails" property="inwardNbr"/>	
       <logic:empty name="frmClaimDetails" property="alPrevClaim" >
       		<%if("IWC".equals(statusTypeId)&& !"".equals(inwardNum.toString().trim())){ %>
				<bean:write property="claimNbr" name="frmClaimDetails"/></td>
		    <%}else if("IIP".equals(statusTypeId)||"".equals(statusTypeId.toString().trim())||("IWC".equals(statusTypeId)&&"".equals(inwardNum.toString().trim()))){ %>

        		<html:select property="claimNbr"  styleId="select" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
					<html:option value="" >Select from list</html:option>
				</html:select>
			<%} %>	
        </logic:empty>
		<logic:notEmpty name="frmClaimDetails" property="alPrevClaim" >
            <%if("IWC".equals(statusTypeId)&& !"".equals(inwardNum.toString().trim())){ %>
				<bean:write property="claimNbr" name="frmClaimDetails"/></td>
		    <%}else if("IIP".equals(statusTypeId)||"".equals(statusTypeId.toString().trim())||("IWC".equals(statusTypeId)&&"".equals(inwardNum.toString().trim()))){ %>

        		<html:select property="parentClmSeqID"  styleId="select" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
					<html:option value="" >Select from list</html:option>
 					<html:options collection="alPrevClaim"  property="cacheId" labelProperty="cacheDesc"/>
	    		</html:select>
			<%} %>	
        </logic:notEmpty>
		</td>
        <td class="formLabel">Requested Amt. : <span class="mandatorySymbol">*</span></td>
        <td class="textLabelBold">
        <html:text property="requestedAmt"  styleClass="textBox textBoxMedium" maxlength="13"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        </td>
      </tr>
      <tr>
        <td class="formLabel">Vidal Health Branch: <span class="mandatorySymbol">*</span></td>
        <td>
        	<html:select  property="officeSeqID"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>" >
        		<html:option value="">Select from list</html:option>
          		<html:optionsCollection name="ttkBranch" value="cacheId" label="cacheDesc"/>
            </html:select>
        </td>
        <td class="formLabel"> Inward Status:</td>
        <td class="textLabelBold">
        	<html:select property="statusTypeID"  styleId="select" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
 				<html:options collection="inwardStatus"  property="cacheId" labelProperty="cacheDesc"/>
	    	</html:select></td>
      </tr>
      <tr>
        <td class="formLabel">Remarks: </td>
        <td colspan="3" class="textLabel">
        <html:textarea property="remarks"  styleClass="textBox textAreaLong"  disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
        </td>
      </tr>
    </table>
	</fieldset>
</logic:match>
	<br><br>
	<!-- E N D : Form Fields -->

	<logic:match name="frmClaimDetails" property="sourceTypeID" value="CPN">
		<ttk:DocumentChkList/>
	</logic:match>

    <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <logic:notEmpty name="frmClaimDetails" property="inwardSeqID">
	    <logic:match name="frmClaimDetails" property="statusTypeID" value="IWC">
	        <button type="button" name="Button" accesskey="p" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReport()"><u>P</u>rint Acknowledgement</button>&nbsp;
	    </logic:match>
	 </logic:notEmpty >
	<logic:match name="viewmode" value="false">
	<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
	<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
	</logic:match>
  </tr>
</table>
</div>

	<!-- E N D : Buttons -->
	<!-- E N D : Content/Form Area -->
<logic:notEmpty name="frmClaimDetails" property="frmChanged">
 <script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>
<!-- E N D : Main Container Table -->
	<input type="hidden" name="child" value="">
	<input type="hidden" name="mode" value=""/>
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<input type="hidden" name="rownum" value=""/>
	<html:hidden property="caption"/>
	<html:hidden property="inwardSeqID"/>
	<html:hidden property="claimNbr"/>
	<html:hidden property="claimType"/>
	<html:hidden property="officeName"/>
	<html:hidden property="officeSeqID"/>
	<input type="hidden" name="focusID" value="">
</html:form>