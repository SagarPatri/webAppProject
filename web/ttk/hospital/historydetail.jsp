<%
/**
 * @ (#) history.jsp march 24 ,2014
 * Project      : TTK HealthCare Services
 * File         : history.jsp
 *  Author       :Satya Moganti
 * Company      : Rcs Technologies
 * Date Created : march 24 ,2014
 *
 * @author       :Satya moganti
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.HospPreAuthWebBoardHelper,com.ttk.common.HospClaimsWebBoardHelper"%>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript">
/*
function onClose()
{
    document.forms[1].mode.value="doClose";
    document.forms[1].child.value="";
	document.forms[1].action="/HistoryDetailAction.do";
	document.forms[1].submit();

}//end of onReview()
*/
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

function onReport()
{
  if(TC_GetChangedElements().length>0)
    {
      alert("Please save the modified data, before Generating Letter");
      return false;
    }//end of if(TC_GetChangedElements().length>0)
if(document.forms[0].leftlink.value == "Hospital Information")
{
  if(document.forms[0].sublink.value == "Cashless Status")
   {
      var parameterValue="|"+document.forms[1].preAuthSeqID.value+"|"+document.forms[1].statusTypeID.value+"|PRE|";
      var statusID=document.forms[1].statusTypeID.value;
      var parameter = "";
      var authno = document.getElementById("authorizationNo").value;
      var preauthno = document.getElementById("preAuthNo").value;
     // var DMSRefID = document.getElementById("DMSRefID").value;
      var completedYN = document.forms[1].completedYN.value;
      var authTypeID = document.getElementById("authLtrTypeID").value;
      if(statusID == 'APR')
      {      
	      if(authTypeID == 'NIC')
	      {
	      	parameter = "?mode=doGenerateAuthReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/CitibankAuthAprLetter.jrxml&reportID=CitiAuthLetter&authorizationNo="+authno+"&completedYN="+completedYN;
	      }//end of if(authTypeID == 'NIC')
	      else
	      {
	      	parameter = "?mode=doGenerateReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/AuthAprLetter.jrxml&reportID=AuthLetter&authorizationNo="+authno+"&completedYN="+completedYN;
          }//end of else
      }//end of if(statusID == 'APR')
      else if(statusID == 'REJ')
      {
            parameter = "?mode=doGenerateReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/AuthRejLetter.jrxml&reportID=AuthLetter&authorizationNo="+authno+"&completedYN="+completedYN;
      }//end of else
   }//end of if(document.forms[0].leftlink.value == "Pre-Authorization")
   else if(document.forms[0].sublink.value == "Claims Status")
   {
      var parameterValue="|"+document.forms[1].claimSeqID.value+"|";
      var parameterValuePCO = document.forms[1].claimSeqID.value;
      var statusID=document.forms[1].statusTypeID.value;
      var authno = document.getElementById("authorizationNo").value;
      //added for Mail-SMS Template for Cigna
      var cignastatusYN = document.forms[1].cignastatusYN.value;
     
       if(statusID == 'APR')
       {
    	   parameter = "?mode=doGenerateCompReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/MediClaimComputation.jrxml&reportID=MediClaimCom&authorizationNo="+authno;
        }//end of if(statusID == 'APR')
      else if(statusID == 'REJ')
      {
    	  if(cignastatusYN!="Y")
     	  {  
    		 parameter = "?mode=doGenerateReport&reportType=PDF&parameter="+parameterValue+"&fileName=generalreports/MediClaimCompRej.jrxml&reportID=MediClaimCom&authorizationNo="+authno;
     	  }
    	  else
    	  {
    		  parameter = "?mode=doGenerateCignaRejectReport&reportType=PDF&parameter="+parameterValuePCO+"&fileName=generalreports/ClaimRejectLtrPO.jrxml&reportID=CignaClaimRejectLtrPO&authorizationNo="+authno;
    	  }
      }//end of else if(statusID == 'REJ')
      else if(statusID == 'PCO')
      {
    	  if(cignastatusYN!="Y")
     	  {  
    		 parameter = "?mode=doGenerateClosureReport&reportType=PDF&parameter="+parameterValuePCO+"&fileName=generalreports/ClosureFormat.jrxml&reportID=ClosureFormat";
     	  }
    	  else
    	  {
    		 parameter = "?mode=doGenerateCignaClosureReport&reportType=PDF&parameter="+parameterValuePCO+"&fileName=generalreports/ClaimClosureLtrAdvisor.jrxml&reportID=CignaClaimClosureLtrAdvisor&authorizationNo="+authno; 
    	  }
      }//end of else if(statusID == 'PCO')
   }//end of else if(document.forms[0].leftlink.value == "Claims")
}//end of if(document.forms[0].link.value == "Hospital Information")
   var openPage = "/ReportsAction.do"+parameter;
   var w = screen.availWidth - 10;
   var h = screen.availHeight - 49;
   var features = "scrollbars=0,status=1,toolbar=0,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
   window.open(openPage,'',features);
}//end of onReport()


/*
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
*/
</script>
<%

String strLink=TTKCommon.getActiveSubLink(request);
pageContext.setAttribute("strLink",strLink);
%>
<html:form action="/HospHistoryDetailAction.do" >

<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="57%">
    <logic:match name="strLink" value="Cashless Status">
    Pre - Auth History Details -<td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	</logic:match>
    <logic:match name="strLink" value="Claims Status">
   Claims History Details -<td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
    </logic:match> 
    <%--<bean:write name="frmHistoryDetail" property="caption"/></td>--%>
  </tr>
</table>

<div class="contentArea" id="contentArea">

	<logic:match name="strLink" value="Cashless Status">
	<ttk:OnlineHospitalHistory/>
	<%--<ttk:PolicyDetailHistory/>
	<ttk:PolicyHistory/>
	<ttk:PreAuthHistory/>
	<ttk:ClaimantHistory/>
  	<ttk:HospitalHistory/>
 	<ttk:AuthorizationHistory/>
 	<ttk:NarrationHistory/>--%>
  	</logic:match>
 
 	<logic:match name="strLink" value="Claims Status">
 	
 	<%--<ttk:PolicyDetailHistory/>
 	<ttk:PolicyHistory/>
	<ttk:PreAuthHistory/>
 	<ttk:ClaimantDetailHistory/>
	<ttk:HospitalHistory/>
	<ttk:SettlementHistory/>
	<ttk:NarrationHistory/>
	<ttk:ChequeInfoHistory/>--%>
	<ttk:OnlineHospitalHistory/>
    </logic:match> 

	
    <!-- S T A R T : Buttons -->
	<%-- <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
	    	<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>--%>
	<!-- E N D : Buttons -->
</div>

<INPUT TYPE="hidden" NAME="mode" VALUE="">

</html:form>
