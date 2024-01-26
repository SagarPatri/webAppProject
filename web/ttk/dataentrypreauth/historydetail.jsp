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
function online_Claims_shortfall(seqid)
{
	var parametervalue = "|"+seqid+"|CLM|";
	var features = "scrollbars=0,status=0,toolbar=0,top=0,left=0,resizable=1,menubar=0,width=950,height=700";
	window.open("/CustomerCareReportsAction.do?mode=doDefault&reportID=ClaimHistoryList&parameter="+parametervalue+"&reportType=html&fileName=reports/customercare/Shortfall.jrxml",'', features);
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
