<%@page import="com.ttk.dto.enrollment.PolicyDetailVO"%>
<%@page import="com.ttk.dto.administration.ProductVO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
    <%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>


<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<!-- <script type="text/javascript" SRC="/ttk/scripts/trackdatachanges.js"></script> -->

<script language="javascript" src="/ttk/scripts/administration/productPremiumConfiguration.js"></script> 






<script type="text/javascript">
/* function addconfigurationDetails(){
	 document.forms[1].mode.value = "doAdd";
	 
	    //document.forms[1].tab.value="General";
	    document.forms[1].action = "/Configuration.do";
	    document.forms[1].submit();
} */

function editAuditlogs(premiumConfigSeqId)
{
	var healthauthority =document.getElementById("authority_type").value;
	 var capitationYN = document.getElementById("capitationYN").value; 
    document.forms[1].mode.value="doAuditLogPremium";
    //document.forms[1].child.value="";
    document.forms[1].premiumConfigSeqId.value=premiumConfigSeqId;
    document.forms[1].action = "/Configuration.do?healthauthority="+healthauthority+"&flagedit="+"Y"+"&Auditlogflag="+"Y"+"&capitationYN="+capitationYN;
    document.forms[1].submit();

}

function edit2(rownum)
{
	
	var healthauthority =document.getElementById("authority_type").value;
    var capitationYN = document.getElementById("capitationYN").value; 
    document.forms[1].mode.value="doAuditLogPremium";
    //document.forms[1].child.value="";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/Configuration.do?healthauthority="+healthauthority+"&flagedit="+"Y"+"&Auditlogflag="+"Y"+"&capitationYN="+capitationYN;

    document.forms[1].submit();
}


/* function addconfigurationDetailsASO()
{
	var healthauthority =document.getElementById("authority_type").value;
	 document.forms[1].mode.value = "doAdd";
	   document.forms[1].capitationYN.value="1";
	    //document.forms[1].tab.value="General";
	    document.forms[1].action = "/Configuration.do?healthauthority="+healthauthority;
	    document.forms[1].submit();
} */
	
/* function addconfigurationDetailsASPlus()
{
	var healthauthority =document.getElementById("authority_type").value;
	  document.forms[1].capitationYN.value="2";
	 // document.forms[1].capitationYN.value="3";
	    //document.forms[1].tab.value="General";
	     document.forms[1].mode.value = "doAdd";
	     document.forms[1].action = "/Configuration.do?healthauthority="+healthauthority;
		 document.forms[1].submit();
} */
 function onClose()
{
	document.forms[1].mode.value = "doClosePremiumDetails";
	document.forms[1].action = "/Configuration.do";
	document.forms[1].submit();	
}
function edit(rownum)
{
	var healthauthority =document.getElementById("authority_type").value;

    document.forms[1].mode.value="doSelectPremium";
    //document.forms[1].child.value="";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/Configuration.do?healthauthority="+healthauthority+"&flagedit="+"Y";

    document.forms[1].submit();
}//end of edit(rownum)

function editCapitationYN(premiumConfigSeqId)
{
	var healthauthority =document.getElementById("authority_type").value;
    document.forms[1].mode.value="doSelectPremium";
    //document.forms[1].child.value="";
    document.forms[1].premiumConfigSeqId.value=premiumConfigSeqId;
    document.forms[1].action = "/Configuration.do?healthauthority="+healthauthority+"&flagedit="+"Y";
    document.forms[1].submit();
}//end of edit(rownum)
function onDeleteIcon(rownum)
{
	var msg = confirm("Do you want to Delete Member Eligibility Rules?");
	
	if(msg)
    {
    
	document.forms[1].mode.value="doDeletePremium";
    //document.forms[1].child.value="";
    document.forms[1].rownum.value=rownum;
    document.forms[1].action = "/ConfigurationDelete.do";
    document.forms[1].submit();
    }

}

function onDeleteIconCapitationYN(premiumConfigSeqId){
	
	var msg = confirm("Do you want to Delete Member Eligibility Rules?");
		if (msg) {
			document.forms[1].mode.value = "doDeletePremium";
			//document.forms[1].child.value="";
			document.forms[1].premiumConfigSeqId.value = premiumConfigSeqId;
			document.forms[1].action = "/Configuration.do";
			document.forms[1].submit();
		}

	}

	function pageIndex(pagenumber) {
		document.forms[1].reset();
		document.forms[1].mode.value = "doSearch";
		document.forms[1].pageId.value = pagenumber;
		document.forms[1].action = "/Configuration.do?claimlist=claimlist";
		document.forms[1].submit();
	}//end of pageIndex(pagenumber)

	//function to display previous set of pages
	function PressBackWard() {
		document.forms[1].reset();
		document.forms[1].mode.value = "doBackward";
		document.forms[1].action = "/Configuration.do?claimlist=claimlist";
		document.forms[1].submit();
	}//end of PressBackWard()

	//function to display next set of pages
	function PressForward() {
		document.forms[1].reset();
		document.forms[1].mode.value = "doForward";
		document.forms[1].action = "/Configuration.do?claimlist=claimlist";
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
    	<td>Configuration Information - <%=request.getSession().getAttribute("ConfigurationTitle")%> </td>
		<td align="right" >&nbsp;</td>     
    </tr>
	</table>
<html:errors/>
<%-- <%if(request.getParameter("successMsg")!=null){ %> --%>
<logic:notEmpty name="successMsg" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success"width="16" height="16" align="middle">&nbsp;
						<bean:write name="successMsg" scope="request"/>
				  </td>
				</tr>
			</table>
	</logic:notEmpty>
 <logic:notEmpty name="errorMsg" scope="request">
	  <table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	   <tr>
	     <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	         <bean:message name="errorMsg" scope="request"/>
	     </td>
	   </tr>
	  </table>
	 </logic:notEmpty>



		  <%
        if("Policies".equals(TTKCommon.getActiveSubLink(request)))
        {
        	
        %>
        <fieldset>
<legend>Premium Details



<logic:notEqual value="1" name="frmRenewalDays" property="capitationYN" scope="session">
							<a href="#" accesskey="a"
							onClick="javascript:addSchemeConfigurationDetailsASPlus()"><img
							src="/ttk/images/AddIcon.gif" ALT="Add Activity Details" title="Add Activity Details"
							width="13" height="13" border="0" align="absmiddle"></a>
							</logic:notEqual>
				
				<logic:equal value="1" name="frmRenewalDays" property="capitationYN" scope="session">
				<a href="#" accesskey="a"
							onClick="javascript:addSchemeConfigurationDetailsASO()"><img
							src="/ttk/images/AddIcon.gif" ALT="Add Activity Details" title="Add Activity Details"
							width="13" height="13" border="0" align="absmiddle"></a>
				
				</logic:equal>





          <%--   <logic:equal value="Y" name="frmRenewalDays" property="capitationYN" scope="session">
							<a href="#" accesskey="a"
							onClick="javascript:addconfigurationDetailsASPlus()"><img
							src="/ttk/images/AddIcon.gif" ALT="Add Activity Details"
							width="13" height="13" border="0" align="absmiddle"></a>
				</logic:equal>	
				<logic:notEqual value="Y" name="frmRenewalDays" property="capitationYN" scope="session">
				<a href="#" accesskey="a"
							onClick="javascript:addconfigurationDetailsASO()"><img
							src="/ttk/images/AddIcon.gif" ALT="Add Activity Details"
							width="13" height="13" border="0" align="absmiddle"></a>
				</logic:notEqual>	 --%>					
 </legend>
 <logic:notEmpty name="tableData" scope="session">
<ttk:HtmlGrid name="tableData"/>
</logic:notEmpty>

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
        
          <%
          
        if("Products".equals(TTKCommon.getActiveSubLink(request))){
        	ProductVO  productVO=(ProductVO)request.getSession().getAttribute("productVO");

        %>
        
  <ttk:PremiumConfigurationDetails/>
  <input type="hidden" name="authority_type"  id="authority_type" value="<%=productVO.getHealthAuthority()%>">
  
 <%
        }
        else if("Policies".equals(TTKCommon.getActiveSubLink(request)))
        {
        	PolicyDetailVO policyDetailVO=(PolicyDetailVO) request.getSession().getAttribute("policyDetailVO");
        %>
         <input type="hidden" name="authority_type"  id="authority_type" value="<%=policyDetailVO.getHealthAuthority()%>">
        
       <%  	
        }
          
        %>

<INPUT TYPE="hidden" NAME="rownum" VALUE="">
<INPUT TYPE="hidden" NAME="premiumConfigSeqId" VALUE="">

<input type="hidden" name="mode">
<input type="hidden" name="forward">
 <INPUT TYPE="hidden" NAME="sortId" VALUE="">
	    <INPUT TYPE="hidden" NAME="pageId" VALUE="">
	      <INPUT TYPE="hidden" NAME="pageId" VALUE="">
<html:hidden name="frmRenewalDays"  property="premiumConfigSwrId"/>	      
<html:hidden name="frmRenewalDays"  styleId="capitationYN"   property="capitationYN"/>

<html:hidden name="frmRenewalDays"  property="asoFromDate"/>
<html:hidden name="frmRenewalDays"  property="asoToDate"/>
<html:hidden name="frmRenewalDays"  property="asPlusFromDate"/>
<html:hidden name="frmRenewalDays"  property="asPlusToDate"/>




<!-- <input type="hidden" name="tab"> -->
</html:form>
</div>
</body>
</html>