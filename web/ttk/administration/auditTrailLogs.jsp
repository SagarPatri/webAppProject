<%@page import="com.ttk.dto.enrollment.PolicyDetailVO"%>
<%@page import="com.ttk.dto.administration.ProductVO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
    <%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>



<!-- <script language="javascript" src="/ttk/scripts/administration/productPremiumConfiguration.js"></script> -->

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" type="text/css" href="ttk/styles/style.css" />
<script type="text/javascript">

function onClose()
{
	if(!TrackChanges()) return false;
	document.forms[1].mode.value="doConfigureProductPremium";
	document.forms[1].action="/Configuration.do";
	document.forms[1].submit();
 }


</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>

<body>
<div class="contentArea" id="contentArea">
<html:form action="/Configuration.do" method="post">
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>Audit logs</td>
		<td align="right" >&nbsp;</td>     
    </tr>
	</table>
<html:errors/>
<%-- <%if(request.getParameter("successMsg")!=null){ %> --%>
<logic:notEmpty name="successMsg" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="middle">&nbsp;
						<bean:write name="successMsg" scope="request"/>
				  </td>
				</tr>
			</table>
	</logic:notEmpty>

		  <%
        if("Policies".equals(TTKCommon.getActiveSubLink(request)))
        {
        	
        %>
        <fieldset style="height:290px;">
<legend>Audit Logs for Policy
	
</legend>
<div class="scrollableGrid" style="height:280px;">

 <ttk:MemberEligibilityAuditLogs/>
</div>

<table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="center">
			<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
			</td>
			
		</tr>
	</table>
</fieldset>
		
</div>
        <%
        }
        %>
        
          <%
          
        if("Products".equals(TTKCommon.getActiveSubLink(request))){
         %>
           <fieldset style="height:290px;">
        <legend>Audit Logs for Product
   
     </legend>
     <div class="scrollableGrid" style="height:280px;">
     <ttk:MemberEligibilityAuditLogs/>	
     </div>
      <table align="center" class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="center">
			<button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
			</td>
			
		</tr>
	</table>
        </fieldset>
       <%  	
        }
          
        %>

<INPUT TYPE="hidden" NAME="rownum" VALUE="">
<INPUT TYPE="hidden" NAME="premiumConfigSeqId" VALUE="">

<input type="hidden" name="mode">
 <INPUT TYPE="hidden" NAME="sortId" VALUE="">
	    <INPUT TYPE="hidden" NAME="pageId" VALUE="">
	      <INPUT TYPE="hidden" NAME="pageId" VALUE="">
<html:hidden name="frmRenewalDays"  property="premiumConfigSwrId"/>	      
<html:hidden name="frmRenewalDays"  property="capitationYN"/>
<!-- <input type="hidden" name="tab"> -->
</html:form>
</div>
</body>
</html>