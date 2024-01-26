<%
/**
 * @ (#) showpolicylist.jsp April 16th 2008
 * Project      : TTK HealthCare Services
 * File         : showpolicylist.jsp
 * Author       : Bala Krishna E
 * Company      : Span Systems Corporation
 * Date Created : April 16th 2008
 *
 * @author       : Bala Krishna E
 * Modified by   : Ramakrishna K M
 * Modified date : April 18th 2008
 * Reason        : Fetching the Policy Information from the VO
 */
%>
<%@ page import="com.ttk.common.WebBoardHelper,java.util.*,com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.dao.impl.onlineforms.OnlineAccessDAOImpl,com.ttk.dto.onlineforms.OnlinePolicyInfoVO"%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<script>
function select(policyNO)
{
var corpPolicyNo=  document.forms[0].strCorpPolicyNo.value;
	if (window.opener && !window.opener.closed)
			//window.opener.document.getElementById("corpPolicyNo").value = policyNO;
			window.opener.document.forms[0].corpPolicyNo.value=policyNO;	
	self.close();	
}
// KOC1227A
function select1(policyNO,policyNO1)
{
	var corpPolicyNo=  document.forms[0].strCorpPolicyNo.value;
	if (window.opener && !window.opener.closed)
	{
			window.opener.document.forms[0].corpPolicyNo.value=policyNO1;
			window.opener.document.forms[0].corpPolicyNo1.value=policyNO;
	}	
	self.close();	
}

function disableCtrlModifer(evt)
{
	var disabled = {a:0, x:0,w:0, n:0, F4:0, j:0};
	var ctrlMod = (window.event)? window.event.ctrlKey : evt.ctrlKey;
	var key = (window.event)? window.event.keyCode : evt.which;
	key = String.fromCharCode(key).toLowerCase();
	return (ctrlMod && (key in disabled))? false : true;
}//end of disableCtrlModifer(evt)
</script>


<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<title>List of Policies</title>
<body onkeypress="return disableCtrlModifer(event);" onkeydown="return disableCtrlModifer(event);">

<%
	pageContext.setAttribute("groupId",TTKCommon.checkNull(request.getParameter("groupId")));
%>	

<%
		String strCorpPolicyNo = TTKCommon.checkNull((String)request.getParameter("corpPolicyNo"));
		String strGroupId= request.getParameter("GroupID");
		java.util.ArrayList al =OnlineAccessDAOImpl.getPolicyNumberInfo(strGroupId);
		pageContext.setAttribute("groupID",strGroupId);
		if(al!= null && al.size()>0){
		   pageContext.setAttribute("onlinePolicyVO", (OnlinePolicyInfoVO[])al.toArray(new OnlinePolicyInfoVO[0]));
		}//end of if
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/OnlineAccessAction.do" onsubmit="onLogin()">
	<div class="contentArea" id="contentArea">
	<html:errors/>

	<table align="center" class="formContainer" border="0" cellspacing="" cellpadding="0">
	<tr>
		<td ID="listsubheader" CLASS="gridHeader">List of Policies - [<%=strGroupId%>] &nbsp;</td>
	</tr><br><br>
	<table border="0" align="center" cellpadding="0" cellspacing="0" class="gridWithCheckBox">
		<tr> 
            <td width="21%" class="gridHeader" style="font-size:12px;"  align='left'>&nbsp;Policy No</td> 
            <td width="35%" class="gridHeader" style="font-size:12px;" align='left'>&nbsp;Group Name</td> 
            <td width="22%" class="gridHeader" style="font-size:12px;" align='left'>&nbsp;Policy Start Date</td>
	    <td width="22%" class="gridHeader" style="font-size:12px;" align='left'>&nbsp;Policy End Date</td>
        </tr>
        <logic:empty name="onlinePolicyVO">
			<tr>
				<td class="generalcontent" colspan="7">&nbsp;&nbsp;No Data Found</td>
			</tr>
		</logic:empty>
	<logic:notEmpty name="onlinePolicyVO">
		<logic:iterate id="onlinePolicyVO" indexId="i" name="onlinePolicyVO">
		<tr class="<%=(i.intValue()%2==0)? "gridOddRow":"gridEvenRow"%>">
		<%
		if(strGroupId.equalsIgnoreCase("A0912"))
		{
		%>
			<td class="textLabel" id="corpPolicyNo" style="font-weight:normal;"><a href="#" onClick="javascript:select1('<bean:write name="onlinePolicyVO" property="policyNbr"/>','<bean:write name="onlinePolicyVO" property="policyNbr1"/>');"><bean:write name="onlinePolicyVO" property="policyNbr1"/></a></td>
			<td class="textLabel" style="color:#000000;"><bean:write name="onlinePolicyVO" property="groupName"/></td>
			<td class="textLabel" style="color:#000000;"><bean:write name="onlinePolicyVO" property="policyStartDate"/></td>
			<td class="textLabel" style="color:#000000;"><bean:write name="onlinePolicyVO" property="policyEndDate"/></td>
			
		<%
		}		
		else
		{ %>
			 <td class="textLabel" id="corpPolicyNo" style="font-weight:normal;"><a href="#" onClick="javascript:select('<bean:write name="onlinePolicyVO" property="policyNbr"/>');"><bean:write name="onlinePolicyVO" property="policyNbr"/></a></td>
			 <td class="textLabel" style="color:#000000;"><bean:write name="onlinePolicyVO" property="groupName"/></td>
			 <td class="textLabel" style="color:#000000;"><bean:write name="onlinePolicyVO" property="policyStartDate"/></td>
			 <td class="textLabel" style="color:#000000;"><bean:write name="onlinePolicyVO" property="policyEndDate"/></td>
			
		<%}%>	
		</tr>
		
		</logic:iterate>
	</logic:notEmpty>
	</table>
	</table>
	  <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
		<td align="center">
			 
			 <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:self.close();"><u>C</u>lose</button>
		</td>
	  </tr>
	</table>
	<!-- E N D : Form Fields -->
<p>&nbsp;</p>
</div>
<input type="hidden" name="strCorpPolicyNo" value="<%=strCorpPolicyNo%>">
<input type="hidden" name="groupId" value="<%=request.getParameter("groupId")%>">
</html:form>
</body>

<!-- E N D : Buttons -->
<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->



