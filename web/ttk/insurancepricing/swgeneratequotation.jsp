<html>
<body>
<%
/**
 * @ (#) usersupport.jsp
 * Project      : TTK Software Support
 * File         : usersupport.jsp
 * Author       : Vamsi Krishna CH
 * Company      : 
 * Date Created : 
 *
 * @author       : Vamsi Krishna CH
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.business.common.SecurityManagerBean,com.ttk.dto.usermanagement.UserSecurityProfile"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@page import="java.util.ArrayList,com.ttk.dto.insurancepricing.PolicyConfigVO" %>


<script type="text/javascript" SRC="/ttk/scripts/validation.js"></script>
    <script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
    <script type="text/javascript" src="/ttk/scripts/insurancepricing/swpolicydesignconfig.js"></script>	
     <script type="text/javascript" src="/ttk/scripts/jquery-1.4.2.min.js"></script> 
<SCRIPT LANGUAGE="JavaScript">
	bAction = false; //to avoid change in web board in product list screen //to clarify
	var TC_Disabled = true;
</SCRIPT>
 <%

	int iRowCount = 0;

%>
<!-- S T A R T : Search Box -->
	 

	<html:form action="/SwUpdateGenerateQuotationAction.do" method="post">
	
	<%-- <logic:notEmpty name="fileName" scope="request">
		<script language="JavaScript">
			var w = screen.availWidth - 10;
			var h = screen.availHeight - 82;
			var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open("/SwFinalOutputAction.do?mode=doPrintpdf&displayFile=<bean:write name="fileName"/>&reportType=PDF",'PrintAcknowledgement',features);
		</script>
	</logic:notEmpty> --%>
	
	
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		  <tr>
		    <td width="65%"></td> 
		  </tr>
	</table>
	<div class="contentArea" id="contentArea">
	<html:errors />
	<%-- <logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="middle">&nbsp;
						<bean:message name="updated" scope="request"/>
				  </td>
				</tr>
			</table>
		</logic:notEmpty> --%>
	<%-- <logic:match name="frmSwPolicyConfigQuote" property="Message" value="N"> --%>
	
	<br>
	 <fieldSet><legend>Net risk premium</legend>
  
  <table width ="85%">
	<tr><td>
  <table align="left" class="gridWithPricing" border="0" cellspacing="1" cellpadding="1">
	<tr>
		<td width="50%" ID="listsubheader" align="center" colspan="0" CLASS="gridHeader">Benefit&nbsp;</td>
		<td width="50%" ID="listsubheader" align="center" colspan="0" CLASS="gridHeader">Net risk premium per covered member per policy year (OMR)&nbsp;</td>
	</tr>
		 <logic:iterate id="cursor2" name="frmSwGrosspremium" indexId="q" property="cursor2">
		
		
	 	<% if(q%2==0) { %>
			<tr class="gridOddRow">
			<%} else { %>
  				<tr class="gridEvenRow">
  			<%} %>  
  			
  			
			
			  	<td width="20%" class="formLabel" align="center"><bean:write name="cursor2" property="benefit" /></td>
                <td width="20%" class="formLabel" align="center"><bean:write name="cursor2" property="riskPremAED" /></td>				

			  
			</tr>
			</logic:iterate> 
			
  </table>
            
  </td>
  </tr>
  
  <logic:equal name="cursor2" property="showMsg" value="Y"> 
  	<tr><td>
	<table align="center"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			
			<td width="60%" align="left" class="formLabel">

  	 <font color="red">Maternity inpatient and Maternity outpatient cost is included in the overall Inpatient and Outpatient cost respectively.</font>
		
		</td>
		
			
		</tr>
	</table>
	
	</td></tr>
	
	</logic:equal>
	
	  <logic:equal name="cursor2" property="showMsg" value="N"> 
  	<tr><td>
	<table align="center"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			
			<td width="60%" align="left" class="formLabel">

  	 <font color="red">Maternity premium is applicable only for maternity eligible covered members.</font>
		
		</td>
		
			
		</tr>
	</table>
	
	</td></tr>
	
	</logic:equal>
	
	</table>
	</fieldSet>
	
	<br>
	
	
	<br>
	<fieldset><legend>Gross  premium</legend>
   <table width="85%">
   <tr><td>
     <table align="left" class="gridWithPricing" border="0" cellspacing="1" cellpadding="0">
  <tr>
		<td width="50%" ID="listsubheader"  align="center" CLASS="gridHeader">Benefit &nbsp;</td>
		<td width="50%" ID="listsubheader"  align="center" CLASS="gridHeader">Gross premium per covered member per policy year (OMR) &nbsp;</td>
		
  </tr>
  
		 <logic:iterate id="cursor4" name="frmSwGrosspremium" indexId="q" property="cursor4">
		
		
		<% if(q%2==0) { %>
			<tr class="gridOddRow">
			<%} else { %>
  				<tr class="gridEvenRow">
  			<%} %>  
  			
  			
			
			  	<td width="50%" class="formLabel" align="center"><bean:write name="cursor4" property="benefit" /></td>
                <td width="50%" class="formLabel" align="center"><bean:write name="cursor4" property="riskPremAED" /></td>				

			  
			</tr>
			</logic:iterate> 
  </table>
  </td></tr>
  
      <logic:equal name="cursor2" property="showMsg" value="Y"> 
  	<tr><td>
	<table align="center"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			
			<td width="60%" align="left" class="formLabel">

  	 <font color="red">Maternity inpatient and Maternity outpatient cost is included in the overall Inpatient and Outpatient cost respectively.</font>
		
		</td>
		
			
		</tr>
	</table>
	
	</td></tr>
	
	</logic:equal>
	
	      <logic:equal name="cursor2" property="showMsg" value="N"> 
  	<tr><td>
	<table align="center"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			
			<td width="60%" align="left" class="formLabel">

  	 <font color="red">Maternity premium is applicable only for maternity eligible covered members.</font>
		
		</td>
		
			
		</tr>
	</table>
	
	</td></tr>
	
	</logic:equal>
  <tr>
  <td>

  

  
 </table>
  
	</fieldset>
	
	<fieldset>
	<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="left">
			&nbsp;<a href="#" id="discrepancies" onclick="javascript:onViewInputSummary();"><i><b>Click to view pricing summary</b></i></a>&nbsp;&nbsp;&nbsp;
			
			<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'"	onMouseover="this.className='buttons buttonsHover'"	onClick="javascript:onCloseOutput();">Back</button>&nbsp;
		</td>
		
			
		</tr>
	</table>
	</fieldset> 
	
	<%-- </logic:match> --%>
	 </div>
   <input type="hidden" name="mode" value=""/>
   <input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="groupseqid" VALUE="<bean:write name="frmSwGrosspremium" property="lngGroupProfileSeqID" />">
	<INPUT TYPE="hidden" NAME="addedBy" VALUE="<%=(TTKCommon.getUserSeqId(request))%>">

   <input type="hidden" name="parameter" value=""/>
   <input type="hidden" name="reportType" value=""/>
   
   </html:form> 
   </body>
   </html>



