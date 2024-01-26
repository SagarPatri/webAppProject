<%
/**
 * @ (#) preauthgeneral.jsp May 10, 2006
 * Project      : TTK HealthCare Services
 * File         : preauthgeneral.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : May 10, 2006
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript">

function onClose()
{
    document.forms[1].mode.value="doClose";
    document.forms[1].child.value="";
	document.forms[1].action="/HistoryDetailAction.do";
	document.forms[1].submit();

}//end of onReview()

function online_Preauth_shortfall(seqid)
{
	var features = "scrollbars=0,status=0,toolbar=0,top=0,left=0,resizable=1,menubar=0,width=950,height=700";
	var parametervalue = "|"+seqid+"|PAT|";
	window.open("/CustomerCareReportsAction.do?mode=doDefault&reportID=PreAuthHistoryList&parameter="+parametervalue+"&reportType=html&fileName=reports/customercare/Shortfall.jrxml",'', features);
}
function online_Claims_shortfall(seqid,cigna_yn)
{
	var parametervalue = "|"+seqid+"|CLM|";
	var cignayn = cigna_yn;
	//alert("cignayn--:"+cignayn);
	var features = "scrollbars=0,status=0,toolbar=0,top=0,left=0,resizable=1,menubar=0,width=950,height=700";
	window.open("/CustomerCareReportsAction.do?mode=doDefault&reportID=ClaimHistoryList&parameter="+parametervalue+"&cignayn="+cignayn+"&reportType=html&fileName=reports/customercare/Shortfall.jrxml",'', features);
}
function online_DisallowedBill(seqid,enrID,clmNo,settlNo)
{
	var features = "scrollbars=0,status=0,toolbar=0,top=0,left=0,resizable=1,menubar=0,width=950,height=700";
	window.open("/CustomerCareReportsAction.do?mode=doDefault&parameter="
	+seqid+
	"&fileName=reports/customercare/DisallowedBill.jrxml&reportType=pdf&reportID=DisallowedBillList1&enrollmentID="
	+enrID+
	"&claimNumber="
	+clmNo+
	"&claimSettlNumber="
	+settlNo,'', features);
}
</script>
<%
String PreAuthHistoryTypeID = (String)request.getAttribute("PreAuthHistoryTypeID");
String strLink=TTKCommon.getActiveLink(request);
pageContext.setAttribute("PreAuthHistoryTypeID",PreAuthHistoryTypeID);
pageContext.setAttribute("strLink",strLink);
%>
<html:form action="/PreAuthGeneralAction.do" >

<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="57%">
    <logic:notMatch name="strLink" value="Account Info">
    History Details -
	</logic:notMatch>
    <logic:match name="strLink" value="Account Info">
    Summary Details -
    </logic:match> 
    <bean:write name="frmHistoryDetail" property="caption"/></td>
  </tr>
</table>

<div class="contentArea" id="contentArea">
	<logic:match name="PreAuthHistoryTypeID" value="CEH">
		<ttk:CitibankEnrolHistory/>
	</logic:match>
	<logic:match name="PreAuthHistoryTypeID" value="CCH">
		<ttk:CitibankClaimHistory/>
	</logic:match>
	<%
		if(!(PreAuthHistoryTypeID.equals("CEH")) && !(PreAuthHistoryTypeID.equals("CCH")))
		{	
	%>
		<ttk:PolicyDetailHistory/>
		<ttk:PreAuthHistory/>
		<logic:match name="PreAuthHistoryTypeID" value="HPR">
		<ttk:ClaimantHistory/>
		</logic:match>
		<logic:match name="PreAuthHistoryTypeID" value="HMP">
		<ttk:ClaimantHistory/>
		</logic:match>
		<logic:match name="PreAuthHistoryTypeID" value="HCL">
		<ttk:ClaimantDetailHistory/>
		</logic:match>
		<ttk:PolicyHistory/>
		<logic:notMatch name="PreAuthHistoryTypeID" value="POL">
			<ttk:IcsPcsHistory/>
		</logic:notMatch>
	
		<ttk:HospitalHistory/>
		<logic:match name="PreAuthHistoryTypeID" value="HCL">
		<ttk:SettlementHistory/>
		</logic:match>
		<logic:match name="PreAuthHistoryTypeID" value="HPR">
		<ttk:AuthorizationHistory/>
		</logic:match>
		<logic:match name="PreAuthHistoryTypeID" value="HMP">
		<ttk:AuthorizationHistory/>
		</logic:match>
		<ttk:NarrationHistory/>
		<logic:match name="PreAuthHistoryTypeID" value="HCL">
		<ttk:ChequeInfoHistory/>
		</logic:match>
		<logic:match name="PreAuthHistoryTypeID" value="HIN">
		
        
		<fieldset>
    		<legend>General</legend>
			<table class="formContainer"  border="0" cellspacing="0" cellpadding="0">			
			<tr>
       			<td width="20%"  nowrap class="formLabel indentedLabels">Investigation Type:</td>
        		<td width="30%" nowrap class="textLabelBold"><bean:write property="typeDesc" name="frmInvestigation"/></td>
        		<td width="22%" nowrap class="formLabel">Cashless / Claim No.:</td>
        		<td width="28%" class="textLabelBold"><bean:write property="preAuthClaimNo" name="frmInvestigation"/></td>
      		</tr>
      		<tr>
        <td  nowrap class="formLabel indentedLabels">Doctor's Name: </td>
        <td  valign="bottom" nowrap class="textLabel"><bean:write property="contactName" name="frmInvestigation"/></td>
        <td  nowrap class="formLabel">Doctor's Phone number:</td>
        <td  class="textLabel"><bean:write property="doctorPhoneNbr" name="frmInvestigation"/></td>
      </tr>
      <tr>
        <td  nowrap class="formLabel indentedLabels">Doctor's email ID:</td>
        <td  nowrap class="textLabel"><bean:write property="emailID" name="frmInvestigation"/></td>
        <td  height="20" nowrap class="formLabel">Marked Date / Time:</td>
        <td  nowrap class="textLabel"><bean:write property="markedDate" name="frmInvestigation"/>&nbsp;<bean:write property="markedTime" name="frmInvestigation"/>&nbsp;<bean:write property="markedDay" name="frmInvestigation"/></td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Vidal Healthcare Branch: </td>
        <td nowrap class="textLabel"><bean:write property="officeName" name="frmInvestigation"/></td>
        <td  nowrap class="formLabel"> Investigation Mandatory:</td>
        <td  class="textLabel">
        <logic:match name="frmInvestigation" property="investMandatoryYN"  value="Y">
       	 Yes
	   	</logic:match>
	   	<logic:notMatch name="frmInvestigation" property="investMandatoryYN"  value="Y">
       	 No
	   	</logic:notMatch>
        </td>
      </tr>
       <tr>
        <td class="formLabel indentedLabels">Remarks:</td>
        <td colspan="3" class="textLabel"><bean:write property="remarks" name="frmInvestigation"/></td>              
       </tr>
      </table> 
      </fieldset>
    <fieldset>
    <legend>Investigation Details Support</legend>
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" nowrap class="formLabel indentedLabels">Investigating Agency: </td>
        <td width="30%" class="textLabel"><bean:write property="investAgencyDesc" name="frmInvestigation"/></td>
        <td width="22%" nowrap class="formLabel">&nbsp;</td>
        <td width="28%" nowrap class="textLabel">&nbsp;</td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Payment Done:<br></td>
        <td class="textLabel">
		<logic:match name="frmInvestigation" property="paymentDoneYN"  value="Y" >
       	 Yes
	   	</logic:match>
		</td>
        <!--<td nowrap class="formLabel"></td>
        <td nowrap class="textLabel"></td>
        --><logic:match property="typeDesc" name="frmInvestigation"  value="Claims">
        <td width="22%" nowrap class="formLabel">Investigation Lock Release Date: </td><td  nowrap class="textLabel"><bean:write property="increasedInvdate" name="frmInvestigation"/>
		</logic:match>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Investigated By:<br></td>
        <td class="textLabel"><bean:write property="investigatedBy" name="frmInvestigation"/></td>
        <td nowrap class="formLabel">Invest. Date / Time:</td>
        <td nowrap class="textLabel">
        <bean:write property="investDate" name="frmInvestigation"/>&nbsp;<bean:write property="investTime" name="frmInvestigation"/>&nbsp;<bean:write property="investDay" name="frmInvestigation"/></td>
      </tr>
      <tr>
        <td nowrap class="formLabel indentedLabels">Report Received:<br></td>
        <td class="textLabel">
        <logic:match name="frmInvestigation" property="reportReceivedYN"  value="Y" >
       	 Yes
	   	</logic:match> </td>
        <td nowrap class="formLabel">Received Date / Time:</td>
        <td nowrap class="textLabel"><bean:write property="investReceivedDate" name="frmInvestigation"/>&nbsp;<bean:write property="investReceivedTime" name="frmInvestigation"/>&nbsp;<bean:write property="investReceivedDay" name="frmInvestigation"/></td>
       </tr>
		<tr>
        <td class="formLabel indentedLabels">Recommendation:</td>
        <td colspan="1"  class="textLabel"><bean:write property="recommDesc" name="frmInvestigation"/></td>
        <td nowrap class="formLabel">Claim Amt:</td>        
        <td  nowrap class="textLabel"><bean:write property="claimAmt" name="frmInvestigation"/></td>
        </tr>
      <tr>
	  	<logic:match property="typeDesc" name="frmInvestigation"  value="Claims">        
        <td nowrap class="formLabel indentedLabels">Investigated Remarks:</td><td  nowrap class="textLabel"><bean:write property="invRemark" name="frmInvestigation"/></td>
        <!-- <td nowrap class="formLabel indentedLabels">Investigated Remarks:<br></td>        
        -->
        </logic:match>
      </tr>
	</table>
	</fieldset>
	<fieldset>
    <legend>Investigation Status </legend>
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="20%" nowrap class="formLabel indentedLabels">Status:</td>
        <td nowrap class="textLabel"><bean:write property="statusDesc" name="frmInvestigation"/></td>
       <logic:match name="frmInvestigation" property="statusTypeID"  value="STP">
        <td width="22%" nowrap class="formLabel">Reason:</td>
        <td width="28%" nowrap class="textLabel"><bean:write property="reasonDesc" name="frmInvestigation"/></td>
       </logic:match>
        </tr>
      <tr>
        <td  class="formLabel indentedLabels">Remarks:</td>
        <td colspan="3" class="textLabel"><bean:write property="investRemarks" name="frmInvestigation"/></td>
      </tr>
    </table>
	</fieldset>
      		
      		
		</logic:match>
	<%
		}//end of if(!(PreAuthHistoryTypeID.equals("CEH")) && !(PreAuthHistoryTypeID.equals("CCH")))
	%>
    <!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>
	<!-- E N D : Buttons -->
</div>

<INPUT TYPE="hidden" NAME="mode" VALUE="">
<input type="hidden" name="child" value="Details">
</html:form>
