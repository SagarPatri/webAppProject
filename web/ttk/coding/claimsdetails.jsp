<%
/**
 * @ (#) claimsdetails.jsp August 28th, 2007
 * Project      : TTK HealthCare Services
 * File         : claimsdetails.jsp
 * Author       : Krupa J
 * Company      : Span Systems Corporation
 * Date Created : August 28th, 2007
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
<%@ page import="com.ttk.common.TTKCommon"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/coding/claimsdetails.js"></script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
%>
<html:form action="/ClaimsDetailAction.do">

<!-- S T A R T : Page Title -->
<logic:empty name="frmPreauthDetails" property="display">
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%">General Details - <bean:write
				name="frmPreauthDetails" property="caption" /></td>
			<td align="right" class="webBoard"><%@ include
				file="/ttk/common/toolbar.jsp"%></td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<table width="98%" align="center" border="0" cellspacing="0"
		cellpadding="0">
			<tr>
				<td></td>
				<td align="right"><strong><bean:write property="eventName"
					name="frmPreauthDetails" /></strong>&nbsp;&nbsp; 
					<logic:match name="viewmode" value="false">
						<ttk:ReviewInformation />
					</logic:match>&nbsp;
				</td>
			</tr>
		</table>
	<html:errors/>
	<fieldset><legend>Member Details</legend>
	<table border="0" align="center" cellpadding="0" cellspacing="0" class="formContainer">
	<tr>
		<td width="22%" height="15" class="formLabel">Enrollment Id:</td>
		<td class="textLabel">
		      	<bean:write name="frmPreauthDetails" property="claimantDetailsVO.enrollmentID"/>
		</td>
		<td width="19%" class="formLabel">Member Name:</td>
		<td width="29%" class="formLabelBold">
		      	<bean:write name="frmPreauthDetails" property="claimantDetailsVO.name"/>
		</td>
	</tr>
	<tr>
		<td class="formLabel">Gender:</td>
		<td class="formLabelBold">
				<bean:write name="frmPreauthDetails" property="claimantDetailsVO.genderDescription"/>
		</td>
		<td class="formLabel">Age (Yrs):</td>
		<td class="formLabelBold">
				<bean:write name="frmPreauthDetails" property="claimantDetailsVO.age"/>
		</td>
	</tr>	
	<tr>
		<td class="formLabel">Date of Inception:</td>
		<td class="textLabel">
				<bean:write name="frmPreauthDetails" property="claimantDetailsVO.dateOfInception"/>
		</td>
		<td class="formLabel">Date of Exit:</td>
		<td class="textLabel">
				<bean:write name="frmPreauthDetails" property="claimantDetailsVO.dateOfExit"/>
		</td>
	</tr>
	
	<tr>
		<td class="formLabel">Employee No.:</td>
		<td class="textLabel">
				<bean:write name="frmPreauthDetails" property="claimantDetailsVO.employeeNbr"/>
		</td>
		<td class="formLabel">Employee Name:</td>
		<td class="textLabelBold">
				<bean:write name="frmPreauthDetails" property="claimantDetailsVO.employeeName"/>
		</td>
	</tr>
	<tr>
		<td class="formLabel">Relationship:</td>
		<td class="textLabel">
				<bean:write name="frmPreauthDetails" property="claimantDetailsVO.relationDesc"/>
		</td>
		<td class="formLabel">Member Phone No.:</td>
		<td class="textLabel">
				<bean:write name="frmPreauthDetails" property="claimantDetailsVO.claimantPhoneNbr"/>
		</td>
	</tr>
	</table>
	</fieldset>
	
	<fieldset>
	<legend>Claim Details</legend>
	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="22%" class="formLabel">Claim No.:</td>
	    <td width="30%" class="textLabelBold"><bean:write name="frmPreauthDetails" property="preAuthNo"/></td>
	    
	    <td class="formLabel" width="19%">Claim File No.:</td>
	    <td class="textLabelBold" width="29%">
	        <bean:write name="frmPreauthDetails" property="claimDetailVO.claimFileNbr"/>
	    </td>
	  </tr>
	  <tr>
	    <td class="formLabel">Request Type:</td>
	    <td class="textLabel">
	    	<bean:write name="frmPreauthDetails" property="claimDetailVO.requestTypeDesc"/>
	      </td>
	    <td class="formLabel">Prev. Claim No.:</td>
	    <td class="textLabelBold">
	    	<bean:write name="frmPreauthDetails" property="prevClaimNbr"/>
	    </td>
	  </tr>
		<tr>
		  <td class="formLabel">Claim Type:</td>
		  <td class="textLabel">
		  	<bean:write name="frmPreauthDetails" property="claimDetailVO.claimTypeDesc"/>
		  </td>
		  <td class="formLabel">Claim Sub Type:</td>
		  <td class="textLabel">
		  	<bean:write name="frmPreauthDetails" property="claimDetailVO.claimSubTypeID"/>
		  </td>
		</tr>
		<tr>
	      <td class="formLabel">Intimation Date:</td>
	      <td class="textLabel">
	      	<bean:write name="frmPreauthDetails" property="claimDetailVO.intimationDate"/>
	      </td>
	      <td class="formLabel">Mode:</td>
	      <td class="textLabel">
		        <bean:write name="frmPreauthDetails" property="claimDetailVO.modeTypeDesc"/>
		  </td>
		  </tr>
		<tr>
		  <td class="formLabel">Received Date / Time:</td>
		  <td class="textLabel">
	        <bean:write name="frmPreauthDetails" property="claimDetailVO.claimReceivedDate"/>
		  </td>
		  <td class="formLabel">Requested Amt. (Rs):</td>
		  <td class="textLabel">
		     <bean:write name="frmPreauthDetails" property="claimRequestAmount"/>
	      </td>
		</tr>
		<tr>
		  <td class="formLabel">Treating Doctor's Name:</td>
		  <td class="textLabel">
				<bean:write name="frmPreauthDetails" property="doctorName"/>
	      </td>
		  <td class="formLabel">In Patient No.:</td>
		  <td class="textLabel">
		  		<bean:write name="frmPreauthDetails" property="claimDetailVO.inPatientNbr"/>
		  </td>
		</tr>
		<tr>
	      <td class="formLabel">Vidal Health Branch:</td>
	      <td class="textLabel">
	      	<bean:write name="frmPreauthDetails" property="officeName"/>
	      </td>
	      <td class="formLabel">Source:</td>
	      <td class="textLabelBold">
	      	<bean:write name="frmPreauthDetails" property="claimDetailVO.sourceDesc"/>
	      </td>
		</tr>
		<tr>
	      <td class="formLabel">Assigned To:</td>
	      <td class="textLabelBold">
	      	<bean:write name="frmPreauthDetails" property="assignedTo"/>
	      </td>
	      <td class="formLabel">Processing Branch:</td>
	      <td class="textLabelBold">
	      	<bean:write name="frmPreauthDetails" property="processingBranch"/>
	      </td>
		</tr>
		<tr>
	      <td class="formLabel">Remarks:</td>
	      <td class="textLabel">
	      	<bean:write name="frmPreauthDetails" property="claimDetailVO.claimRemarks"/>
	      </td>
		  </tr>
		</table>
		</fieldset>
		</div>
		<INPUT TYPE="hidden" NAME="mode" VALUE="">
	</logic:empty>
	
	<logic:notEmpty name="frmPreauthDetails" property="display">
	<html:errors/>
</logic:notEmpty>
<script>
	TC_Disabled=true;
</script>
</html:form>	