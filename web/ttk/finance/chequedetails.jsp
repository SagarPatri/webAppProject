<%
/**
 * @ (#) chequedetails.jsp Jun 17th 2006
 * Project      : TTK HealthCare Services
 * File         : chequedetails.jsp
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : Jun 17th 2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.WebBoardHelper,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/finance/chequedetail.js"></SCRIPT>
<script>
var JS_Focus_Disabled =true;
function showhidestatusdate(selObj)
{
	var selVal = selObj.options[selObj.selectedIndex].text;
	var selValue = selObj.options[selObj.selectedIndex].value;
           //added as per Stale CR
           var currentDate=document.forms[1].currentDate.value ;
	if(selValue == "CSV")
	{
		document.getElementById("remarks").style.display="";
		document.getElementById("rem").style.display="none";
		//	document.forms[1].clearedDate.value = "";
		//Changed as per STALE CR
		  document.forms[1].clearedDate.value=currentDate;
		//Changed as per STALE CR
	}
	else
	{
		//Added as per Stale
		if(selValue == "CSS")
		{
			document.forms[1].clearedDate.value=currentDate;
		}
		else{
			//document.forms[1].clearedDate.value = "";
		document.forms[1].clearedDate.value="";
		}
		//Added as per Stale
		document.getElementById("remarks").style.display="none";
		document.getElementById("rem").style.display="";
		//document.forms[1].clearedDate.value = "";
	}
	document.forms[1].chngid.value=selVal+" Date:";
}//end of showhidestatusdate(selObj)
</script>
<%
		pageContext.setAttribute("chequeStatus",Cache.getCacheObject("chequeStatus"));
		pageContext.setAttribute("chequeStatusStale",Cache.getCacheObject("chequeStatusStale"));
		//added as per KOC 1261 Change request
		pageContext.setAttribute("chequeStatuswithoutStale",Cache.getCacheObject("chequeStatuswithoutStale"));
		//added as per KOC 1261 Change request
		boolean viewmode=true;
		if(TTKCommon.isAuthorized(request,"Edit"))
		{
			viewmode=false;
		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		
%>
<!-- S T A R T : Content/Form Area -->

<html:form action="/ChequeDetailsAction.do" >
	<!-- S T A R T : Page Title -->
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
			<tr>
	    		<td width="50%"><bean:write name="frmChequeDetail" property="caption"/></td>
	    		<td width="50%" align="right" class="webBoard"> &nbsp;</td>
	    	</tr>
		</table>
	<!-- E N D : Page Title -->
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
	<!-- E N D : Success Box -->
	<html:errors/>

	<div class="contentArea" id="contentArea">
    <!-- S T A R T : Form Fields -->
    <%
    	if(TTKCommon.getActiveSubLink(request).equals("Cheque Information"))
    	{
    %>
	<fieldset>
	    <legend>Payment Transaction details</legend>
	    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="20%" height="20" class="formLabel">Payment Trans Ref No.: </td>
	        <td width="33%" class="textLabelBold"><bean:write name="frmChequeDetail" property="chequeNo"/></td>
	        <td class="formLabel" width="25%">Issued Date:</td>
	        <td class="textLabelBold" width="22%"><bean:write name="frmChequeDetail" property="chequeDate"/></td>
	      </tr>
	      <tr>
	        <td height="20" class="formLabel">In favor of:</td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="inFavorOf"/></td>
	        <td class="formLabel">Cheque Amount (OMR):</td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="chequeAmt"/></td>
	      </tr>
	      <tr>
	        <td height="20" class="formLabel">Float Account No.: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="floatAcctNo"/></td>
	         <td height="20" class="formLabel">Conversion Rate: </td>
	          <td class="textLabel"><bean:write name="frmChequeDetail" property="converSionRatePayment"/></td>
	      </tr>
	    </table>
	</fieldset>
	<%
    	}
	%>
	<fieldset>
	    <legend>Claim Details</legend>
	    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="20%" class="formLabel">Claim Settlement No.:</td>
	        <td width="33%" class="textLabelBold"><bean:write name="frmChequeDetail" property="claimSettNo"/></td>
	        <td width="25%" class="formLabel">Claim Type: </td>
	        <td width="22%" class="textLabelBold"><bean:write name="frmChequeDetail" property="claimTypeDesc"/></td>
	      </tr>
	      <tr>
	        <td height="20" class="formLabel">Approved Date: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="apprDate"/></td>
	        <td class="formLabel">Approved Amount (OMR): </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="approvedAmtClm"/></td>
	      </tr>
	      <tr>
	      <td height="20" class="formLabel">Member Id: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="dhpoMemberId"/></td>
	        <td height="20" class="formLabel">Enrollment Id: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="enrollID"/></td>
	      </tr>
	      <tr>
	        <td height="20" class="formLabel">Policy No.: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="policyNo"/> </td>
	        
	         <td class="formLabel">Conversion Rate: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="converSionRateClaim"/> </td>
	      </tr>
	      <tr>
	       <td class="formLabel">Scheme Type: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="policyType"/> </td>
	        <td class="formLabel">Member Name: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="claimantName"/> </td>
	      </tr>
	    </table>
	</fieldset>
	<fieldset>
		<legend>Insurance Company</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
			<tr>
			    <td width="20%" class="formLabel">Insurance Company:</td>
			    <td width="32%" class="textLabelBold"><bean:write name="frmChequeDetail" property="insCompName"/></td>
			    <td width="25%" class="formLabel">Company Code:</td>
			    <td width="22%" class="textLabelBold"><bean:write name="frmChequeDetail" property="insCompCode"/></td>
			</tr>
		</table>
	</fieldset>
	<fieldset>
	    <legend>Corporate Information</legend>
	    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="20%" class="formLabel">Group Id: </td>
	        <td width="32%" class="textLabelBold"><bean:write name="frmChequeDetail" property="groupID"/></td>
	        <td width="25%" class="formLabel">
		      	Corporate Name:
			 </td>
	        <td width="22%" class="textLabelBold"><bean:write name="frmChequeDetail" property="groupName"/></td>
	      </tr>
	    </table>
	</fieldset>
	<%
    	if(TTKCommon.getActiveSubLink(request).equals("Float Account"))
    	{
    %>
	<fieldset>
	    <legend>Cheque Details</legend>
	    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="20%" height="20" class="formLabel">In Favor of:</td>
	        <td width="33%"><input name="policyno3" type="text" class="textBox textBoxLarge textBoxDisabled" value="<bean:write name="frmChequeDetail" property="inFavorOf"/>" disabled ></td>
	        <td width="25%" class="formLabel">&nbsp;</td>
	        <td width="22%" class="textLabel">&nbsp;</td>
	      </tr>
	      <tr>
	        <td class="formLabel">Remarks:</td>
	        <td colspan="3"><html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/></td>
	      </tr>
	    </table>
	</fieldset>
	<%
    	}//end of if(TTKCommon.getActiveSubLink(request).equals("Float Account"))
    %>
	<fieldset>
		<legend>Address Information</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="20%" nowrap class="formLabel">Address1: </td>
	        <td width="33%" class="textLabel"><bean:write name="frmChequeDetail" property="bankAddressVO.address1"/></td>
	        <td width="25%" class="formLabel">Address 2:</td>
	        <td width="22%" class="textLabel"><bean:write name="frmChequeDetail" property="bankAddressVO.address2"/> </td>
	      </tr>
	      <tr>
	        <td class="formLabel">Address 3:</td>
	        <td class="textLabel"> <bean:write name="frmChequeDetail" property="bankAddressVO.address3"/> </td>
	        <td class="formLabel">State: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="bankAddressVO.stateName"/></td>
	      </tr>
	      <tr>
	        <td height="20" class="formLabel">City: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="bankAddressVO.cityDesc"/></td>
	        <td class="formLabel">Pincode: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="bankAddressVO.pinCode"/></td>
	      </tr>
	      <tr>
	        <td height="20" class="formLabel">Country: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="bankAddressVO.countryName"/></td>
	        <td class="formLabel">Email Id: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="bankAddressVO.emailID"/></td>
	      </tr>
	      <tr>
	        <td height="20" class="formLabel">Office Phone 1:</td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="bankAddressVO.offPhone1"/></td>
	        <td class="formLabel">Office Phone 2:</td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="bankAddressVO.offPhone2"/></td>
	      </tr>
	      <tr>
	        <td height="20" class="formLabel">Home Phone: </td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="bankAddressVO.homePhone"/></td>
	        <td height="20" class="formLabel">Mobile:</td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="bankAddressVO.mobile"/></td>
	      </tr>
	      <tr>
	        <td height="20" class="formLabel">Fax:</td>
	        <td class="textLabel"><bean:write name="frmChequeDetail" property="bankAddressVO.fax"/></td>
	        <td class="formLabel">&nbsp;</td>
	        <td class="textLabel">&nbsp;</td>
	      </tr>
	    </table>
	</fieldset>
	<%
    	if(TTKCommon.getActiveSubLink(request).equals("Cheque Information"))
    	{
    %>
	<fieldset>
	    <legend>Status</legend>
	    <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	       <td width="20%" class="formLabel">Status: </td>
	        <td width="33%" class="textLabel">
	        <%--added as per kOC 1175 Change request --%>
	        <logic:equal name="frmChequeDetail"  property="paymentType" value="EFT">
	        <logic:match name="frmChequeDetail" property="status" value="CSI">
				<html:select property="statusTypeID"  styleClass="selectBox selectBoxMedium" onchange="showhidestatusdate(this);">
  				<html:options collection="chequeStatuswithoutStale"  property="cacheId" labelProperty="cacheDesc"/>
	    		</html:select>
	    	</logic:match>
	        <logic:notMatch name="frmChequeDetail" property="status" value="CSI">
	    			<html:select property="statusTypeID"  styleClass="selectBox selectBoxDisabled" disabled="true">
  					<html:options collection="chequeStatus"  property="cacheId" labelProperty="cacheDesc"/>
  					</html:select>
  				</logic:notMatch>	
	        
	        </logic:equal>
	        <logic:notEqual  name="frmChequeDetail"  property="paymentType" value="EFT">
	                <%--added as per kOC 1175 Change request --%>
	        <logic:match name="frmChequeDetail" property="status" value="CSI">
				<html:select property="statusTypeID"  styleClass="selectBox selectBoxMedium" onchange="showhidestatusdate(this);">
  				<html:options collection="chequeStatus"  property="cacheId" labelProperty="cacheDesc"/>
	    		</html:select>
	    	</logic:match>
	    	<logic:notMatch name="frmChequeDetail" property="status" value="CSI">
	    		<logic:match name="frmChequeDetail" property="status" value="CSS">
	    			<html:select property="statusTypeID"  styleClass="selectBox selectBoxMedium" onchange="showhidestatusdate(this);">
  					<html:options collection="chequeStatusStale"  property="cacheId" labelProperty="cacheDesc"/>
  					</html:select>
  				</logic:match>
  				<logic:notMatch name="frmChequeDetail" property="status" value="CSS">
	    			<html:select property="statusTypeID"  styleClass="selectBox selectBoxDisabled" disabled="true">
  					<html:options collection="chequeStatus"  property="cacheId" labelProperty="cacheDesc"/>
  					</html:select>
  				</logic:notMatch>	
    		</logic:notMatch>
    		        <%--added as per kOC 1175 Change request --%>
    		</logic:notEqual>
    		        <%--added as per kOC 1175 Change request --%>
    		        </td>
	       <%--  <td width="20%" class="formLabel">Status: </td>
	        <td width="33%" class="textLabel">
	        <logic:match name="frmChequeDetail" property="status" value="CSI">
				<html:select property="statusTypeID"  styleClass="selectBox selectBoxMedium" onchange="showhidestatusdate(this);">
  				<html:options collection="chequeStatus"  property="cacheId" labelProperty="cacheDesc"/>
	    		</html:select>
	    	</logic:match>
	    	<logic:notMatch name="frmChequeDetail" property="status" value="CSI">
	    		<logic:match name="frmChequeDetail" property="status" value="CSS">
	    			<html:select property="statusTypeID"  styleClass="selectBox selectBoxMedium" onchange="showhidestatusdate(this);">
  					<html:options collection="chequeStatusStale"  property="cacheId" labelProperty="cacheDesc"/>
  					</html:select>
  				</logic:match>
  				<logic:notMatch name="frmChequeDetail" property="status" value="CSS">
	    			<html:select property="statusTypeID"  styleClass="selectBox selectBoxDisabled" disabled="true">
  					<html:options collection="chequeStatus"  property="cacheId" labelProperty="cacheDesc"/>
  					</html:select>
  				</logic:notMatch>	
    		</logic:notMatch>
			</td>--%>
	        <td width="25%" class="formLabel"><input name="chngid" type="text" class="textBox textBoxMedium" value="" readonly="" style="border:0px solid;"></td>
	        <td width="22%">
	        <logic:match name="frmChequeDetail" property="status" value="CSI">
		       <html:text property="clearedDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>"/>
		       <A NAME="CalendarClearedDate" ID="CalendarClearedDate" HREF="#" onClick="javascript:show_calendar('CalendarClearedDate','frmChequeDetail.clearedDate',document.frmChequeDetail.clearedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
		    </logic:match>
		    <logic:notMatch name="frmChequeDetail" property="status" value="CSI">
		   		 <logic:match name="frmChequeDetail" property="status" value="CSS">
					<html:text property="clearedDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>"/>
					<A NAME="CalendarClearedDate" ID="CalendarClearedDate" HREF="#" onClick="javascript:show_calendar('CalendarClearedDate','frmChequeDetail.clearedDate',document.frmChequeDetail.clearedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>
				</logic:match>
		    	<logic:notMatch name="frmChequeDetail" property="status" value="CSS">
					<html:text property="clearedDate" styleClass="textBox textDate textBoxDisabled" maxlength="10" disabled="<%= viewmode %>" readonly="true"/>
				</logic:notMatch>
            </logic:notMatch>
	        </td>
	      </tr>
	      <tr>
	      <logic:match name="frmChequeDetail" property="statusTypeID" value="CSV">
	        <td id="rem" style="display:none;" class="formLabel">Remarks:</td>
	        <td id="remarks" style="display" class="formLabel">Remarks:<span class="mandatorySymbol">*</span></td>
	        <td colspan="3">
	         <html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
	        </td>
	      </logic:match>
	      <logic:notMatch name="frmChequeDetail" property="statusTypeID" value="CSV">
	      	<td id="rem" style="display" class="formLabel">Remarks:</td>
	        <td id="remarks" style="display:none;" class="formLabel">Remarks:<span class="mandatorySymbol">*</span></td>
	        <td colspan="3">
	         <html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>"/>
	        </td>
	      </logic:notMatch>
	      </tr>
	    </table>
	</fieldset>
	<%
    	}//end of if(TTKCommon.getActiveSubLink(request).equals("Cheque Information"))
    %>
	<!-- E N D : Form Fields -->
    <!-- S T A R T : Buttons -->
<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="100%" align="center">
    <%
		if(TTKCommon.isAuthorized(request,"Edit"))
		{
	%>
    	 	<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
    	   	<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onReset()"><u>R</u>eset</button>&nbsp;
    <%
    	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
     %>
     <%
		if(TTKCommon.isAuthorized(request,"Delete"))
		{
	%>
		<logic:match name="frmAddMember" property="caption" value="Edit">
			<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
		</logic:match>
	    <%
    	}//end of if(TTKCommon.isAuthorized(request,"Delete"))
     %>
     	<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onCancel()"><u>C</u>lose</button>
    </td>
  </tr>
</table>
	<!-- E N D : Buttons -->
</div>
</div>

<!-- E N D : Buttons -->
 	<html:hidden property="mode" />
 	<html:hidden property="seqID" />
 	<html:hidden property="status"/>
 	<html:hidden property="caption" />
      <%--added as per  STALE --%>
 	<html:hidden property="currentDate" />
 	<input type="hidden" name="tab">
 	<%
    	if(TTKCommon.getActiveSubLink(request).equals("Cheque Information"))
    	{
    %>

<script>
	document.forms[1].statusTypeID.disabled=false;
	selObj = document.forms[1].statusTypeID;
	document.forms[1].chngid.value=selObj.options[selObj.selectedIndex].text+" Date :";
	if(document.forms[1].status.value!='CSI' && document.forms[1].status.value!='CSS' )
		document.forms[1].statusTypeID.disabled=true;
</script>
	 <%
    	}
    %>
<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->

</body>
</html:form>