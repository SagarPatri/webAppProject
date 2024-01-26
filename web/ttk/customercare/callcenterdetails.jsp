<%
/**
 * @ (#) callcenterdetails.jsp 28th July 2006
 * Project      : TTK HealthCare Services
 * File         : callcenterdetails.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 28th July 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/customercare/callcenterdetails.js"></script>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/childwindow.js"></SCRIPT>
<script language="javascript">
	var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>

<%
	boolean viewmode=true;
	boolean viewmode1=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}

	String ampm[] = {"AM","PM"};
	pageContext.setAttribute("ampm",ampm);
	pageContext.setAttribute("viewmode",new Boolean(viewmode));
	pageContext.setAttribute("CallerTypeID",Cache.getCacheObject("callerType"));
	pageContext.setAttribute("SourceID",Cache.getCacheObject("callSource"));
	pageContext.setAttribute("LogTypeID",Cache.getCacheObject("callLogType"));
	pageContext.setAttribute("CategoryID",Cache.getCacheObject("callCategory"));
	pageContext.setAttribute("SubCategoryID",Cache.getCacheObject("callSubCategory"));
	pageContext.setAttribute("PriorityID",Cache.getCacheObject("preauthPriority"));
	pageContext.setAttribute("CallRelToID",Cache.getCacheObject("callRelateTo"));
	pageContext.setAttribute("LoggedByID",Cache.getCacheObject("callLoggedBY"));
	pageContext.setAttribute("StatusID",Cache.getCacheObject("callStatus"));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/CallCenterDetailsAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
   		<td><bean:write name="frmCallCenterDetails" property="caption"/></td>
   		<logic:empty name="frmCallCenterDetails" property="logSeqID">
			<td width="49%" align="right" class="webBoard">&nbsp;</td>
		</logic:empty >

		<logic:notEmpty name="frmCallCenterDetails" property="logSeqID">
			<td width="49%" align="right" class="webBoard">
			<a href="#" onClick="javascript:onViewHistory();"><img src="/ttk/images/HistoryIcon.gif" alt="View History" width="16" height="16" border="0" align="absmiddle" class="icons"></a></td>
		</logic:notEmpty >
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
		<!--KOC FOR Grievance-->
	<logic:notEmpty name="seniorCitizen" scope="request">
    <table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
          <bean:message name="seniorCitizen" scope="request"/>
        </td>
      </tr>
    </table>
  </logic:notEmpty>
  <!--KOC FOR Grievance-->
	<!-- E N D : Success Box -->

<logic:empty name="frmCallCenterDetails" property="logSeqID">
		<fieldset><legend>Caller Details</legend>
	    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	    	<tr>
	    		<td width="20%" height="14"  class="formLabel">Type of Caller: <span class="mandatorySymbol">*</span></td>
		        <td width="27%" class="textLabel">
		        	<html:select property="callerTypeID"  styleClass="selectBox selectBoxMedium" onchange="changeCallerFields(this);" disabled="<%= viewmode %>">
						<html:option value="">Select from list</html:option>
		  				<html:options collection="CallerTypeID"  property="cacheId" labelProperty="cacheDesc"/>
			    	</html:select>
		        </td>
		        <td width="18%" class="formLabel">Source:</td>
		        <td width="35%" class="textLabel">
		        	<html:select property="sourceTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
						<html:options collection="SourceID"  property="cacheId" labelProperty="cacheDesc"/>
			    	</html:select>
		        </td>
			</tr>
			<tr>
				<td class="formLabel">Log Type: </td>
				<td class="textLabel">
					<html:select property="logTypeID"  styleClass="selectBox selectBoxMedium" onchange="showRelatedFieldset(this);" disabled="<%= viewmode %>">
		  				<html:options collection="LogTypeID"  property="cacheId" labelProperty="cacheDesc"/>
			    	</html:select>
				</td>
				<td  class="formLabel">Caller Name: <span class="mandatorySymbol">*</span></td>
				<td class="textLabelBold">
					<html:text property="callerName" styleClass="textBox textBoxMedium" onkeypress='ConvertToUpperCase(event.srcElement);' maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
				</td>
			</tr>
			<!-- shortfall phase1 -->
			<tr>
			<td width="20%" height="14"  class="formLabel">Intimation Date: <span class="mandatorySymbol">*</span></td>
		        <td width="27%" class="textLabel">			    	     
			     <html:text property="intimationDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>" readonly="<%= viewmode %>" value="<%=TTKCommon.getServerDateNewFormat() %>"/><A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','forms[1].intimationDate',document.forms[1].intimationDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;</td>			     
			 </tr>
		<!-- shortfall phase1 -->
			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policynameValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policynameValue" style="display:none;">
			</logic:notMatch>

					<logic:empty name="frmCallCenterDetails" property="enrollmentID">
						<td class="formLabel">Beneficiary Name: <span class="mandatorySymbol">*</span></td>
						<td class="subHeader"><html:text property="claimantName" onkeypress='ConvertToUpperCase(event.srcElement);' styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
					</logic:empty>
					<logic:notEmpty name="frmCallCenterDetails" property="enrollmentID">
						<td  class="formLabel">Name:</td>
			    		<td class="subHeader"><bean:write name="frmCallCenterDetails" property="claimantName"/></td>
					</logic:notEmpty>

		    		<td class="formLabel">Enrollment Id:</td>
	          		<td class="subHeader"><bean:write name="frmCallCenterDetails" property="enrollmentID"/>&nbsp;&nbsp;
	          		<a href="#" onClick="SelectEnrollment('callDetailsTab')"><img src="/ttk/images/EditIcon.gif" alt="Select Enrollment Id" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
	          		<a href="#" onClick="ClearEnrollment()"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Enrollment Id" width="16" height="16" border="0" align="absmiddle"></a>
	          		</td>
	        	</tr>

			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policyidValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policyidValue" style="display:none;">
			</logic:notMatch>
					<td class="formLabel">Policy Number: <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold"><html:text property="policyNumber" onkeypress='ConvertToUpperCase(event.srcElement);' styleClass="textBox textBoxPolicyNum" maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
					<%-- <td  class="formLabel">Policy Name:</td>
	        		<td class="textLabelBold">
						<html:text property="insScheme" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>" onkeypress="ConvertToUpperCase(event.srcElement);"/>
	        		</td> --%>
					
				</tr>
			<%-- <logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policyidValue1" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policyidValue1" style="display:none;">
			</logic:notMatch>
					<td class="formLabel">Certificate Number:</td>
					<td class="textLabelBold"><html:text property="certificateNo" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>" onkeypress="ConvertToUpperCase(event.srcElement);"/></td>
					<td  class="formLabel">Credit Card Number: </td>
	        		<td class="textLabelBold">
						<html:text property="creditCardNbr" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	        		</td>
				</tr> --%>
			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policyidValue2" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policyidValue2" style="display:none;">
			</logic:notMatch>
					<td class="formLabel">Customer Code:</td>
					<td class="textLabelBold"><html:text property="insCustCode" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
					<td  class="formLabel">&nbsp;</td>
	        		<td class="textLabelBold">&nbsp;</td>
					
				</tr>	
	        <logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTC">
				<tr id="coridValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTC">
				<tr id="coridValue" style="display:none;">
			</logic:notMatch>
					<td class="formLabel">Policy Number: <span class="mandatorySymbol">*</span></td>
					<td class="textLabelBold"><html:text property="corPolicyNumber" onkeypress='ConvertToUpperCase(event.srcElement);' styleClass="textBox textBoxPolicyNum" maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
					<td  class="formLabel">&nbsp;</td>
	        		<td class="textLabel">&nbsp;</td>
				</tr>

			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTC">
				<tr id="cornameValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTC">
				<tr id="cornameValue" style="display:none;">
			</logic:notMatch>
	  				<td  class="formLabel">Group Id: <span class="mandatorySymbol">*</span></td>
					<td class="subHeader"><bean:write name="frmCallCenterDetails" property="groupID"/></td>
					<td class="formLabel">Corporate Name: </td>
					<td class="subHeader"><bean:write name="frmCallCenterDetails" property="groupName"/>&nbsp;&nbsp;
					<a href="#" onClick="SelectCorporate()"><img src="/ttk/images/EditIcon.gif" alt="Select Corporate Company" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
	          		<a href="#" onClick="ClearCorporate()"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Corporate Company" width="16" height="16" border="0" align="absmiddle"></a>
					</td>
				</tr>

			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CNC">
				<tr id="hospidValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CNC">
				<tr id="hospidValue" style="display:none;">
			</logic:notMatch>
					<td  class="formLabel">Hospital Name: <span class="mandatorySymbol">*</span></td>
				    <td class="subHeader"><bean:write name="frmCallCenterDetails" property="hospName"/></td>
				    <td class="formLabel">Empanelment Number: </td>
				    <td class="subHeader"><bean:write name="frmCallCenterDetails" property="empanelmentNbr"/>&nbsp;&nbsp;
				    <a href="#" onClick="SelectHospital()"><img src="/ttk/images/EditIcon.gif" alt="Select Empanelment Number" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
	          		<a href="#" onClick="ClearHospital()"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Empanelment Number" width="16" height="16" border="0" align="absmiddle"></a>
				    </td>
				</tr>

			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CIC">
				<tr id="policynameInsValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CIC">
				<tr id="policynameInsValue" style="display:none;">
			</logic:notMatch>

					<logic:empty name="frmCallCenterDetails" property="enrollmentID">
						<td class="formLabel">Beneficiary Name: </td>
						<td class="subHeader"><html:text property="claimantName" onkeypress='ConvertToUpperCase(event.srcElement);' styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
					</logic:empty>
					<logic:notEmpty name="frmCallCenterDetails" property="enrollmentID">
						<td  class="formLabel">Name:</td>
			    		<td class="subHeader"><bean:write name="frmCallCenterDetails" property="claimantName"/></td>
					</logic:notEmpty>

		    		<td class="formLabel">Enrollment Id:</td>
	          		<td class="subHeader"><bean:write name="frmCallCenterDetails" property="enrollmentID"/>&nbsp;&nbsp;
	          		<a href="#" onClick="SelectEnrollment('callDetailsTab')"><img src="/ttk/images/EditIcon.gif" alt="Select Enrollment Id" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
	          		<a href="#" onClick="ClearEnrollment()"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Enrollment Id" width="16" height="16" border="0" align="absmiddle"></a>
	          		</td>
	        	</tr>

			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CIC">
				<tr id="policyidInsValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CIC">
				<tr id="policyidInsValue" style="display:none;">
			</logic:notMatch>
					<td class="formLabel">Policy Number: </td>
					<td class="textLabelBold"><html:text property="policyNumber" onkeypress='ConvertToUpperCase(event.srcElement);' styleClass="textBox textBoxPolicyNum" maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></td>
					<td  class="formLabel">&nbsp;</td>
	        		<td class="textLabel">&nbsp;</td>
				</tr>
			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CIC">
				<tr id="insidValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CIC">
				<tr id="insidValue" style="display:none;">
			</logic:notMatch>
					<td  class="formLabel">Healthcare Company: <span class="mandatorySymbol">*</span></td>
				    <td class="subHeader"><bean:write name="frmCallCenterDetails" property="insCompName"/></td>
				    <td class="formLabel">Company Code:</td>
				    <td class="subHeader"><bean:write name="frmCallCenterDetails" property="insCompCodeNbr"/>&nbsp;&nbsp;
				    <a href="#" onClick="SelectInsurance()"><img src="/ttk/images/EditIcon.gif" alt="Select Company" width="16" height="16" border="0" align="absmiddle"></a>&nbsp;&nbsp;
	          		<a href="#" onClick="ClearInsurance()"><img src="/ttk/images/DeleteIcon.gif" alt="Clear Company" width="16" height="16" border="0" align="absmiddle"></a>
				    </td>
				</tr>

	      	<tr>
				<td  class="formLabel"> Office Phone:</td>
				<td class="textLabel"><span class="textLabelBold">
					<html:text property="officePhoneNbr" styleClass="textBox textBoxMedium" maxlength="25" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
					</span>
				</td>
				<td class="formLabel"> Home Phone:</td>
				<td class="textLabel"><span class="textLabelBold">
					<html:text property="homePhoneNbr" styleClass="textBox textBoxMedium" maxlength="25" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
					</span>
				</td>
	        </tr>
	        <tr>
				<td  class="formLabel"> Mobile  Number:<span class="mandatorySymbol">*</span></td>
				<td class="textLabel"><span class="textLabelBold">
					<html:text property="mobileNumber" styleClass="textBox textBoxMedium" maxlength="15" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
					</span>
				</td>
				<td class="formLabel">Email Id:</td>
				<td class="textLabel"><span class="textLabelBold">
					<html:text property="emailID" styleClass="textBox textBoxMedium" maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
					</span>
				</td>
			</tr>
			<!--KOC FOR Grievance-->
			
			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTP">
			<tr id="policyidValue5" style="display:">
			</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTP">
			<tr id="policyidValue5" style="display:none;">
			</logic:notMatch>
				<td  class="formLabel"> Age:</td>
				<td class="textLabel"><span class="textLabelBold">
					<html:text property="age" styleClass="textBox textBoxMedium" maxlength="5" disabled="<%=viewmode%>" readonly="true"/>
					</span>
				</td>	
			</tr>
			
			
			
			
			
			<!--KOC FOR Grievance-->
			<tr>
				<td valign="top"  class="formLabel">Call Content: <span class="mandatorySymbol">*</span></td>
				<td colspan="3" class="textLabel"><span class="formLabel">
					<html:textarea property="callContent" styleClass="textBox textAreaStandard" onchange="javascript:selectage()" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/><!--KOC FOR Grievance-->
					</span>
				</td>
			</tr>
		</table>
	    </fieldset>

	<logic:match name="frmCallCenterDetails" property="logTypeID" value="LTC">
		<div id="claimidValue" style="display:">
	</logic:match>
	<logic:notMatch name="frmCallCenterDetails" property="logTypeID" value="LTC">
		<div id="claimidValue" style="display:none;">
	</logic:notMatch>

		<fieldset><legend>Claim Intimation Details</legend>
	    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		    <tr>
		    	<td width="20%"  class="formLabel">Estimated Amount (Rs): <span class="mandatorySymbol">*</span></td>
		        <td width="27%" class="formLabel"><span class="textLabelBold">
		        	<html:text property="claimIntimationVO.estimatedAmt" styleClass="textBox textBoxMedium" maxlength="13" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></span>
		       	</td>
		        <td  class="formLabel" width="18%" >Likely Date of Hospt.: <span class="mandatorySymbol">*</span></td>
		        <td class="textLabelBold" width="35%">
		        	<html:text property="claimIntimationVO.hospitalizationDate"  styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;
					<A NAME="calHospDate" ID="calHospDate" HREF="#" onClick="javascript:show_calendar('calHospDate','frmCallCenterDetails.elements[\'claimIntimationVO.hospitalizationDate\']',document.frmCallCenterDetails.elements['claimIntimationVO.hospitalizationDate'].value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;
					<html:text property="claimIntimationVO.hospitalizationTime"  styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;<html:select property="claimIntimationVO.hospitalizationDay" name="frmCallCenterDetails" styleClass="selectBox" disabled="<%= viewmode %>"><html:options name="ampm" labelName="ampm"/></html:select>
				</td>
			</tr>
			<tr>
		    	<td valign="top"  class="formLabel"> Ailment Description: <span class="mandatorySymbol">*</span></td>
		        <td colspan="3" class="formLabel"><span class="textLabel">
		        	<html:textarea property="claimIntimationVO.ailmentDesc" styleClass="textBox textAreaSingleLine" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></span>
		        </td>
			</tr>
			<tr>
	        	<td valign="top"  class="formLabel">Hospital Name: <span class="mandatorySymbol">*</span></td>
	        	<td colspan="3" class="formLabel"><span class="textLabelBold">
					<html:text property="claimIntimationVO.hospitalName" styleClass="textBox textBoxMedium" onkeypress='ConvertToUpperCase(event.srcElement);' maxlength="60" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></span>
				</td>
	      	</tr>
	      	<tr>
	        	<td valign="top"  class="formLabel">Hospital Address: <span class="mandatorySymbol">*</span></td>
	        	<td colspan="3" class="formLabel"><span class="textLabel">
	        		<html:textarea property="claimIntimationVO.hospitalAaddress" styleClass="textBox textAreaLarge" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/></span>
	        	</td>
	      	</tr>
	    </table>
	    </fieldset>
	</div>

	<logic:match name="frmCallCenterDetails" property="logTypeID" value="LTC">
		<div id="logidValue" style="display:none;">
	</logic:match>
	<logic:notMatch name="frmCallCenterDetails" property="logTypeID" value="LTC">
		<div id="logidValue" style="display:">
	</logic:notMatch>
		<fieldset><legend>Call Details</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="20%"  class="formLabel">Category: <span class="mandatorySymbol">*</span></td>
				<td width="27%"class="textLabel">
					<html:select property="categoryTypeID" styleId="category" styleClass="selectBox selectBoxMedium" onchange="onChangeCategory();" disabled="<%= viewmode %>">
						<html:option value="">Select from list</html:option>
		  				<html:options collection="CategoryID"  property="cacheId" labelProperty="cacheDesc"/>
			    	</html:select>
				</td>
				<td width="18%" class="formLabel">Sub Category:</td>
				<td width="35%"class="textLabel">
					<html:select property="subCategoryTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
						<html:option value="">Select from list</html:option>
		  				<html:options collection="SubCategoryID"  property="cacheId" labelProperty="cacheDesc"/>
			    	</html:select>
				</td>
			</tr>
			<tr>
				<td  class="formLabel">Reason: <span class="mandatorySymbol">*</span></td>
				<td colspan="3" class="formLabel">
					<html:select property="reasonTypeID" styleId="reason" styleClass="selectBox selectBoxListMoreLargest" onchange="onChangeReason();" disabled="<%= viewmode %>">
						<html:option value="">Select from list</html:option>
		  				<logic:notEmpty name="frmCallCenterDetails"  property="alCategoryReasonList">
		  					<html:optionsCollection property="alCategoryReasonList" label="cacheDesc" value="cacheId"/>
		  				</logic:notEmpty>
			    	</html:select>
				</td>

				<td>&nbsp;</td>
			</tr>
			<%--<tr>
	        	<td class="formLabel">Priority:</td>
	        	<td class="formLabel"><span class="textLabelBold">
	        		<html:select property="priorityTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
						<html:option value="">Select from list</html:option>
		  				<html:options collection="PriorityID"  property="cacheId" labelProperty="cacheDesc"/>
			    	</html:select></span>
	          	</td>
	          	 <td class="formLabel">Logged By: <span class="mandatorySymbol">*</span></td>
        		<td class="formLabel">
       				<span class="textLabel">
       					<html:select property="loggedByTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
							<html:option value="">Select from list</html:option>
	  						<html:options collection="LoggedByID"  property="cacheId" labelProperty="cacheDesc"/>
			    		</html:select>
        			</span>
        		</td> 
        		
			</tr>--%>
			<tr>
		        <td  class="formLabel">Vipul Better Care Branch: </td>
		        <td class="textLabelBold"><bean:write name="frmCallCenterDetails" property="officeName"/></td>
		   
		        <td class="formLabel">Possibility of Fraud:</td>
	    	    <td class="textLabelBold"><span class="formLabel">
	    	    	<html:checkbox styleClass="margin-left:-4px;" property="fraudYN" value="Y"/></span>
	    	    </td>
	  	 	</tr>

	    </table>
	    </fieldset>
		</div>

		<logic:match name="frmCallCenterDetails" property="savedYN" value="N">
			<fieldset><legend>Response</legend>
		    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
				<tr>
					<logic:match name="frmCallCenterDetails" property="logTypeID" value="LTC">
						<%viewmode1 = true; %>
					</logic:match>
					<logic:notMatch name="frmCallCenterDetails" property="logTypeID" value="LTC">
						<%viewmode1 = false; %>
					</logic:notMatch>

					<td width="20%"  class="formLabel">Status:</td>
			        <td width="27%" class="textLabel">
			        	<html:select property="statusTypeID" styleId="statusTypeID" styleClass="selectBox selectBoxMedium" disabled="<%= viewmode1 %>">
			  				<html:options collection="StatusID"  property="cacheId" labelProperty="cacheDesc"/>
				    	</html:select>
					</td>
					<!-- intX -->
		<td width="20%"  class="formLabel">Call back required:</td>
	    <td width="27%" class="textLabel">
	    	<html:checkbox styleClass="margin-left:-4px;" property="callbackYN" value="Y"/>
		</td>
	        	</tr>
	        	
	        	<tr>
	        	<td width="20%"  class="formLabel">Call Type:</td>
			        <td width="27%" class="textLabel">
			        	<html:select property="callType" styleClass="selectBox selectBoxMedium">
				        	<html:option value="IN">In Bound</html:option>
				        	<html:option value="OUT">Out Bound</html:option>
				    	</html:select>
					</td>
	        	    
			        <td class="formLabel">Escalation:</td>
		    	    <td class="textLabelBold"><span class="formLabel">
		    	    	<html:checkbox styleClass="margin-left:-4px;" property="escalationYN" value="Y"/></span>
		    	    </td>
	  	 		</tr>

				<logic:match name="frmCallCenterDetails" property="logTypeID" value="LTC">
					<tr id="answer" style="display:none;">
				</logic:match>
				<logic:notMatch name="frmCallCenterDetails" property="logTypeID" value="LTC">
					<tr id="answer" style="display:">
				</logic:notMatch>
					<td class="formLabel">Answer:</td>
					<td colspan="3" class="textLabelBold"><span class="textLabel">
						<html:select property="callAnswerTypeID"  styleClass="selectBox selectBoxListMoreLargest" disabled="<%= viewmode %>">
							<html:option value="">Select from list</html:option>
			        		<logic:notEmpty name="frmCallCenterDetails"  property="alCallAnswer">
		  						<html:optionsCollection property="alCallAnswer" label="cacheDesc" value="cacheId"/>
		  					</logic:notEmpty>
			        	</html:select></span>
					</td>
					<td>&nbsp;</td>
	        	</tr>

				<tr>
					<td  class="formLabel">Attended By:</td>
			        <td class="textLabelBold"><bean:write name="frmCallCenterDetails" property="recordedBy"/></td>
			        <td class="formLabel">Attended Date / Time:</td>
			        <td class="textLabelBold">
			        	<bean:write name="frmCallCenterDetails" property="logRecordDate"/>&nbsp;
		        		<bean:write name="frmCallCenterDetails" property="logRecordTime"/>&nbsp;
		        		<bean:write name="frmCallCenterDetails" property="logRecordDay"/>
					</td>
				</tr>
				<tr>
					<td valign="top" class="formLabel">Call Response Remarks:</td>
					<td colspan="3" class="textLabel">
						<html:textarea property="callRemarks" styleClass="textBox textAreaStandard" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
					</td>
				</tr>
		    </table>
		    </fieldset>
	    </logic:match>
</logic:empty>

<logic:notEmpty name="frmCallCenterDetails" property="logSeqID">
	<fieldset><legend>Caller Details</legend>
	    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			<tr>
        		<td height="14" class="formLabel">Log Number:</td>
        		<td class="textLabel"><bean:write name="frmCallCenterDetails" property="logNumber"/></td>
        		<td class="formLabel">&nbsp;</td>
        		<td class="textLabel">&nbsp;</td>
	      	</tr>
	    	<tr>
	    		<td width="20%" height="14"  class="formLabel">Type of Caller:</td>
		        <td width="27%" class="textLabel"><bean:write name="frmCallCenterDetails" property="callerTypeDesc"/></td>
        		<td width="18%" class="formLabel">Source:</td>
		        <td width="35%" class="textLabel"><bean:write name="frmCallCenterDetails" property="sourceTypeDesc"/></td>
			</tr>
			<tr>
				<td class="formLabel">Log Type: </td>
        		<td class="textLabel"><bean:write name="frmCallCenterDetails" property="logTypeDesc"/></td>
    	      	<td  class="formLabel">Caller Name: </td>
	          	<td class="textLabel"><bean:write name="frmCallCenterDetails" property="callerName"/></td>
			</tr>
			<!-- shortfall phase1 -->
			<tr>
			<td width="20%" height="14"  class="formLabel">Intimation Date: </td>
		        <td class="textLabel"><bean:write name="frmCallCenterDetails" property="intimationDate"/></td>		    	     
			 </tr>
		<!-- shortfall phase1 -->

			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policynameValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policynameValue" style="display:none;">
			</logic:notMatch>

					<logic:empty name="frmCallCenterDetails" property="enrollmentID">
						<td class="formLabel">Beneficiary Name:</td>
	          			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="claimantName"/></td>
	          			<td class="formLabel">Enrollment Id: </td>
    	      			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="enrollmentID"/>&nbsp;&nbsp;</td>
					</logic:empty>
					<logic:notEmpty name="frmCallCenterDetails" property="enrollmentID">
						<td  class="formLabel">Name:</td>
						<td class="textLabel"><bean:write name="frmCallCenterDetails" property="claimantName"/></td>
				        <td class="formLabel">Enrollment Id: </td>
    	      			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="enrollmentID"/>&nbsp;&nbsp;</td>
					</logic:notEmpty>
	        	</tr>

			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policyidValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policyidValue" style="display:none;">
			</logic:notMatch>
					<td class="formLabel">Policy Number:</td>
          			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="policyNumber"/></td>
          			<%-- <td class="formLabel">Policy Name:</td>
	        		<td class="textLabel"><bean:write name="frmCallCenterDetails" property="insScheme"/></td> --%>
				</tr>
			<%-- <logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policyidValue1" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policyidValue1" style="display:none;">
			</logic:notMatch>
					<td class="formLabel">Certificate Number:</td>
          			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="certificateNo"/></td>
          			<td class="formLabel">Credit Card Number:</td>
	        		<td class="textLabel"><bean:write name="frmCallCenterDetails" property="creditCardNbr"/></td>
				</tr> --%>
			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policyidValue2" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTP">
				<tr id="policyidValue2" style="display:none;">
			</logic:notMatch>
					<td class="formLabel">Customer Code:</td>
          			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="insCustCode"/></td>
          			<td  class="formLabel">&nbsp;</td>
	        		<td class="textLabel">&nbsp;</td>
				</tr>		

	        <logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTC">
				<tr id="coridValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTC">
				<tr id="coridValue" style="display:none;">
			</logic:notMatch>
					<td class="formLabel">Policy Number:</td>
          			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="corPolicyNumber"/></td>
					<td  class="formLabel">&nbsp;</td>
	        		<td class="textLabel">&nbsp;</td>
				</tr>

			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTC">
				<tr id="cornameValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTC">
				<tr id="cornameValue" style="display:none;">
			</logic:notMatch>
					<td  class="formLabel">Group Id:</td>
          			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="groupID"/></td>
          			<td class="formLabel">Corporate Name: </td>
          			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="groupName"/>&nbsp;&nbsp;</td>
				</tr>

			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CNC">
				<tr id="hospidValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CNC">
				<tr id="hospidValue" style="display:none;">
			</logic:notMatch>
					<td  class="formLabel">Hospital Name:</td>
			        <td class="textLabel"><bean:write name="frmCallCenterDetails" property="hospName"/></td>
          			<td class="formLabel">Empanelment Number: </td>
          			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="empanelmentNbr"/>&nbsp;&nbsp;</td>
				</tr>
			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CIC">
				<tr id="policynameInsValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CIC">
				<tr id="policynameInsValue" style="display:none;">
			</logic:notMatch>

					<logic:empty name="frmCallCenterDetails" property="enrollmentID">
						<td class="formLabel">Beneficiary Name:</td>
	          			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="claimantName"/></td>
	          			<td class="formLabel">Enrollment Id: </td>
    	      			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="enrollmentID"/>&nbsp;&nbsp;</td>
					</logic:empty>
					<logic:notEmpty name="frmCallCenterDetails" property="enrollmentID">
						<td  class="formLabel">Name:</td>
						<td class="textLabel"><bean:write name="frmCallCenterDetails" property="claimantName"/></td>
				        <td class="formLabel">Enrollment Id: </td>
    	      			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="enrollmentID"/>&nbsp;&nbsp;</td>
					</logic:notEmpty>
	        	</tr>

			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CIC">
				<tr id="policyidInsValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CIC">
				<tr id="policyidInsValue" style="display:none;">
			</logic:notMatch>
					<td class="formLabel">Policy Number:</td>
          			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="policyNumber"/></td>
          			<td  class="formLabel">&nbsp;</td>
	        		<td class="textLabel">&nbsp;</td>
				</tr>
			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CIC">
				<tr id="insidValue" style="display:">
	   		</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CIC">
				<tr id="insidValue" style="display:none;">
			</logic:notMatch>
					<td  class="formLabel">Healthcare Company :</td>
          			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="insCompName"/></td>
          			<td class="formLabel">Company Code:</td>
          			<td class="textLabel"><bean:write name="frmCallCenterDetails" property="insCompCodeNbr"/>&nbsp;&nbsp;</td>
				</tr>

	      	<tr>
	      		<td  class="formLabel"> Office Phone:</td>
          		<td class="textLabel"><bean:write name="frmCallCenterDetails" property="officePhoneNbr"/></td>
          		<td class="formLabel"> Home Phone:</td>
          		<td class="textLabel"><bean:write name="frmCallCenterDetails" property="homePhoneNbr"/></td>
	        </tr>
	        <tr>
	        	<td  class="formLabel"> Mobile  Number:</td>
        		<td class="textLabel"><bean:write name="frmCallCenterDetails" property="mobileNumber"/></td>
        		<td class="formLabel">Email Id:</td>
        		<td class="textLabel"><bean:write name="frmCallCenterDetails" property="emailID"/></td>
			</tr>
			<!--KOC FOR Grievance-->
			<logic:match name="frmCallCenterDetails" property="callerTypeID" value="CTP">
			<tr id="policyidValue5" style="display:">
			</logic:match>
			<logic:notMatch name="frmCallCenterDetails" property="callerTypeID" value="CTP">
			<tr id="policyidValue5" style="display:none;">
			</logic:notMatch>
				<td  class="formLabel"> Age:</td>				
				<td class="textLabel"><bean:write name="frmCallCenterDetails" property="age"/>
				<td class="formLabel">&nbsp;</td>
        		<td class="textLabel">&nbsp;</td>
				</td>
				
			</tr>
			<!--KOC FOR Grievance-->
			<tr>
				<td valign="top"  class="formLabel">Call Content:</td>
        		<td colspan="3" class="textLabel">
					<html:textarea property="callContent" styleClass="textBox textAreaStandard" readonly="true"/>
				</td>
			</tr>
		</table>
	    </fieldset>

	<logic:match name="frmCallCenterDetails" property="logTypeID" value="LTC">
		<div id="claimidValue" style="display:">
	</logic:match>
	<logic:notMatch name="frmCallCenterDetails" property="logTypeID" value="LTC">
		<div id="claimidValue" style="display:none;">
	</logic:notMatch>

		<fieldset><legend>Claim Intimation Details</legend>
	    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		    <tr>
		    	<td width="20%"  class="formLabel">Estimated Amount (Rs): </td>
		        <td width="27%" class="formLabel"><span class="textLabel">
		        	<bean:write name="frmCallCenterDetails" property="claimIntimationVO.estimatedAmt"/></span>
		       	</td>
		        <td  class="formLabel" width="18%" >Likely Date of Hospt.: </td>
		        <td class="formLabel" width="35%"><span class="textLabel">
		        	<bean:write name="frmCallCenterDetails" property="claimIntimationVO.hospitalizationDate"/>&nbsp;
			        <bean:write name="frmCallCenterDetails" property="claimIntimationVO.hospitalizationTime"/>&nbsp;
		        	<bean:write name="frmCallCenterDetails" property="claimIntimationVO.hospitalizationDay"/></span>
				</td>
			</tr>
			<tr>
				<td valign="top"  class="formLabel"> Ailment Description: </td>
		        <td colspan="3" class="formLabel"><span class="textLabel">
		        	<html:textarea property="claimIntimationVO.ailmentDesc" styleClass="textBox textAreaSingleLine" readonly="true"/></span>
		        </td>
			</tr>
			<tr>
	        	<td valign="top"  class="formLabel">Hospital Name: </td>
	        	<td colspan="3" class="formLabel"><span class="textLabel">
		        	<bean:write name="frmCallCenterDetails" property="claimIntimationVO.hospitalName"/></span>
				</td>
	      	</tr>
	      	<tr>
	        	<td valign="top"  class="formLabel">Hospital Address: </td>
	        	<td colspan="3" class="formLabel"><span class="textLabel">
		        	<html:textarea property="claimIntimationVO.hospitalAaddress" styleClass="textBox textAreaLarge" readonly="true"/></span>
	        	</td>
	      	</tr>
	    </table>
	    </fieldset>
	</div>

	<logic:match name="frmCallCenterDetails" property="logTypeID" value="LTC">
		<div id="logidValue" style="display:none;">
	</logic:match>
	<logic:notMatch name="frmCallCenterDetails" property="logTypeID" value="LTC">
		<div id="logidValue" style="display:">
	</logic:notMatch>
		<fieldset><legend>Call Details</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td  class="formLabel">Category:</td>
        		<td class="textLabel"><bean:write name="frmCallCenterDetails" property="categoryDesc"/></td>
        		<td  class="formLabel">Sub Category:</td>
        		<td class="textLabel"><bean:write name="frmCallCenterDetails" property="subCategoryDesc"/></td>
			</tr>
			<tr>
				<td width="20%" class="formLabel">Reason:</td>
        		<td width="27%" class="textLabel"><bean:write name="frmCallCenterDetails" property="reasonTypeDesc"/></td>
        	</tr>
			<%-- <tr>
				<td class="formLabel">Priority:</td>
        		<td class="formLabel"><span class="textLabel">
        			<bean:write name="frmCallCenterDetails" property="priorityDesc"/></span>
        		</td>
        		<td class="formLabel">Logged By:</td>
        		<td class="formLabel"><span class="textLabel">
        			<bean:write name="frmCallCenterDetails" property="loggedBy"/></span>
        		</td>
			</tr> --%>
			<tr>
        		<td class="formLabel">Vidal Health Branch: </td>
        		<td class="textLabel"><bean:write name="frmCallCenterDetails" property="officeName"/></td>
	  	 		<td class="formLabel">Possibility of Fraud:</td>
        		<td class="formLabel"><span class="textLabel">
        			<bean:write name="frmCallCenterDetails" property="fraudYN"/></span>
        		</td>
	      	</tr>
			<tr>
        		<td class="formLabel">Call back required:</td>
        		<td class="formLabel"><span class="textLabel">
        			<bean:write name="frmCallCenterDetails" property="callbackYN"/></span>
        		</td>
        		<td class="formLabel">&nbsp;</td>
        		<td class="textLabel">&nbsp;</td>
      		</tr>
	    </table>
	    </fieldset>
		</div>

		<br>
		<div class="scrollableGrid">
			<table align="center" class="gridWithCheckBox zeroMargin"  border="0" cellspacing="0" cellpadding="0">
		    	<tr>
		        	<td width="17%" class="gridHeader">Log Date/Time</td>
		          	<td width="15%" class="gridHeader">Attended by</td>
		          	<td width="15%" class="gridHeader">Status</td>
		          	<td width="20%" class="gridHeader">Call Response Remarks</td>
		          	<td width="20%" class="gridHeader">Call Content</td>
		          	<td width="13%" class="gridHeader">Call Type</td>
		          	<td width="13%" class="gridHeader">Mobile Number</td>
		        </tr>

		   		<% int i=0; %>
		        <logic:iterate id="logdetails" name="frmCallCenterDetails" property="alLogDetails">
					<%
						String strClass=i%2==0 ? "gridOddRow" : "gridEvenRow" ;
						i++;
					%>
					<logic:equal value="Y" name="logdetails" property="escalationYN" >
					<tr bgcolor="#F5A9A9">
				    	<td><bean:write name="logdetails" property="logAttendedDate" />&nbsp;</td>
				        <td><bean:write name="logdetails" property="recordedBy" />&nbsp;</td>
				        <td><bean:write name="logdetails" property="statusDesc" />&nbsp;</td>
				        <td><bean:write name="logdetails" property="description" />&nbsp;</td>
				        <td><bean:write name="logdetails" property="childCallContent" />&nbsp;</td>
				        <td><bean:write name="logdetails" property="childCallType" />&nbsp;</td>
				        <td><bean:write name="logdetails" property="childMobileNbr" />&nbsp;</td>
					</tr>
					</logic:equal>
					
					<logic:notEqual value="Y" name="logdetails" property="escalationYN" >
					<tr class=<%=strClass%>>
				    	<td><bean:write name="logdetails" property="logAttendedDate" />&nbsp;</td>
				        <td><bean:write name="logdetails" property="recordedBy" />&nbsp;</td>
				        <td><bean:write name="logdetails" property="statusDesc" />&nbsp;</td>
				        <td><bean:write name="logdetails" property="description" />&nbsp;</td>
				        <td><bean:write name="logdetails" property="childCallContent" />&nbsp;</td>
				        <td><bean:write name="logdetails" property="childCallType" />&nbsp;</td>
				        <td><bean:write name="logdetails" property="childMobileNbr" />&nbsp;</td>
					</tr>
					</logic:notEqual>
				</logic:iterate>
			</table>
		</div>
		<br>

		<logic:match name="frmCallCenterDetails" property="savedYN" value="N">
			<fieldset><legend>Response</legend>
		    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		    	<tr>
	        		<td valign="top" class="formLabel">Call Content: <span class="mandatorySymbol">*</span></td>
	        		<td colspan="3" class="textLabel">
		        		<html:textarea property="childCallContent" styleClass="textBox textAreaStandard" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	       			</td>
	      		</tr>
	      		<tr>
	        	<td width="20%"  class="formLabel">Call Type:</td>
			        <td width="27%" class="textLabel">
			        	<html:select property="callType" styleClass="selectBox selectBoxMedium">
				        	<html:option value="IN">In Bound</html:option>
				        	<html:option value="OUT">Out Bound</html:option>
				    	</html:select>
					</td>
	        	</tr>
	        	<tr>    
			        <td class="formLabel">Escalation:</td>
		    	    <td class="textLabelBold"><span class="formLabel">
		    	    	<html:checkbox styleClass="margin-left:-4px;" property="escalationYN" value="Y"/></span>
		    	    </td>
	  	 		</tr>
	      		<tr>
	        		<td  class="formLabel">Mobile Number:</td>
	        		<td class="textLabel">
		        		<html:text property="childMobileNbr" styleClass="textBox textBoxMedium" maxlength="15" onkeypress="javascript:blockEnterkey(event.srcElement);" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	        		</td>
	        		<td class="formLabel">&nbsp;</td>
	        		<td class="textLabelBold">&nbsp;</td>
	      		</tr>
				<tr>
					<td width="20%" class="formLabel">Status:</td>
			        <td width="27%" class="textLabel">
			        	<html:select property="statusTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
			  				<html:options collection="StatusID"  property="cacheId" labelProperty="cacheDesc"/>
				    	</html:select>
					</td>
					<td class="formLabel" width="18%">&nbsp;</td>
	        		<td class="textLabelBold" width="35%">&nbsp;</td>
	        	</tr>

				<logic:match name="frmCallCenterDetails" property="logTypeID" value="LTC">
					<tr style="display:none;" id="answer">
				</logic:match>
				<logic:notMatch name="frmCallCenterDetails" property="logTypeID" value="LTC">
					<tr style="display:" id="answer">
				</logic:notMatch>
					<td class="formLabel">Answer:</td>
					<td colspan="3" class="textLabelBold"><span class="textLabel">
						<html:select property="callAnswerTypeID"  styleClass="selectBox selectBoxListMoreLargest" disabled="<%= viewmode %>">
							<html:option value="">Select from list</html:option>
			        		<html:optionsCollection property="alCallAnswer" label="cacheDesc" value="cacheId" />
				    	</html:select></span>
					</td>
				</tr>

				<tr>
					<td  class="formLabel">Attended By:</td>
			        <td class="textLabel"><bean:write name="frmCallCenterDetails" property="recordedBy"/></td>
			        <td class="formLabel">Attended Date / Time:</td>
			        <td class="textLabelBold">
			        	<bean:write name="frmCallCenterDetails" property="logRecordDate"/>&nbsp;
		        		<bean:write name="frmCallCenterDetails" property="logRecordTime"/>&nbsp;
		        		<bean:write name="frmCallCenterDetails" property="logRecordDay"/>
					</td>
				</tr>
				<tr>
					<td valign="top" class="formLabel">Call Response Remarks:</td>
					<td colspan="3" class="textLabel">
						<html:textarea property="callRemarks" styleClass="textBox textAreaStandard" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
					</td>
				</tr>
		    </table>
		    </fieldset>
		</logic:match>
</logic:notEmpty>

	<!-- E N D : Form Fields -->

	<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	    	<tr>
	        	<td width="100%" align="center">
		       	    <%
	    				if(TTKCommon.isAuthorized(request,"Edit"))
						{
					%>
							<logic:match name="frmCallCenterDetails" property="savedYN" value="N">
						    	<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
								<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Reset()"><u>R</u>eset</button>&nbsp;
							</logic:match>
		    		<%
		    			}// end of if(TTKCommon.isAuthorized(request,"Edit"))
		    		%>
		    			<button type="button" name="Button2" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Close()"><u>B</u>ack</button>
				</td>
			</tr>
		</table>
	<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
<html:hidden property="logNumber"/>
<html:hidden property="officeName"/>
<html:hidden property="logSeqID"/>
<html:hidden property="callerTypeID"/>
<html:hidden property="logTypeID"/>
<html:hidden property="caption"/>
<html:hidden property="editableYN"/>

	<input type="hidden" name="callbackYN" value="N">
	<input type="hidden" name="escalationYN" value="N">
<html:hidden property="savedYN"/>
<html:hidden property="age"/><!--KOC FOR Grievance-->
<html:hidden property="gender"/><!--KOC FOR Grievance-->
<input type="hidden" name="focusID" value="">
<INPUT TYPE="hidden" NAME="tab" VALUE="">
<INPUT TYPE="hidden" NAME="child" VALUE="">
<logic:notEmpty name="frmCallCenterDetails" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
</html:form>
<!-- E N D : Content/Form Area -->