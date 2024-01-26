<%
/**
 * @ (#) showpolicylist.jsp April 16th 2008
 * Project      : TTK HealthCare Services
 * File         : showpolicylist.jsp
 * Author       : Kishor
 * Company      : RCS
 * Date Created : Oct 30 2014
 *
 * @author       : Kishor
 * Modified by   : Kishor
 * Modified date : Oct 30 2014
 * Reason        : Fetching the Test details for DC
 */
%>

<%@ page import="com.ttk.common.WebBoardHelper,java.util.*,com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.dao.impl.onlineforms.OnlineAccessDAOImpl,com.ttk.dto.onlineforms.OnlinePolicyInfoVO"%>
<%@ page import="com.ttk.dto.preauth.CashlessDetailVO"%>
<%@ page import="com.ttk.dto.usermanagement.UserSecurityProfile"%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<script language="javascript" src="/ttk/scripts/hospital/cashlessAddNew.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>


<script>
function addTests(obj)
{
	alert(obj.value);
	
}
function select()
{
var corpPolicyNo=  document.forms[0].strCorpPolicyNo.value;
	if (window.opener && !window.opener.closed)
			//window.opener.document.getElementById("corpPolicyNo").value = policyNO;
			window.opener.document.forms[0].corpPolicyNo.value=policyNO;	
	self.close();	
}


</script>


<title>List of Tests</title>

<%
	pageContext.setAttribute("groupId",TTKCommon.checkNull(request.getParameter("groupId")));
UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
Long hospSeqId	=	userSecurityProfile.getHospSeqId();
%>	

<%		
	
		String strCorpPolicyNo = TTKCommon.checkNull((String)request.getParameter("corpPolicyNo"));
		String strGroupId= ""+hospSeqId;
//				request.getParameter("GroupID");
		//out.println("strGroupId ::"+strGroupId);
		java.util.ArrayList al =OnlineAccessDAOImpl.getTestsForDC(strGroupId);
		pageContext.setAttribute("groupID",strGroupId);
		if(al!= null && al.size()>0){
			   pageContext.setAttribute("cashlessVO", (CashlessDetailVO[])al.toArray(new CashlessDetailVO[0]));
			}//end of if
		//ArrayList al	=	new ArrayList();
	//out.print("Array Size ::"+al.size());
		
		
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/OnlineCashlessHospAction.do">
	<div class="contentArea" id="contentArea">
	<html:errors/>

	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td ID="listsubheader" CLASS="gridHeader">List of Tests - [<%=strGroupId%>] &nbsp;</td>
	</tr>
	</table><br>
	<table border="0" align="center" cellpadding="0" cellspacing="0" class="gridWithCheckBox">
		<tr style="display: table-row;"> 
            <td width="35%" class="gridHeader" style="font-size:12px;"  align='left'>&nbsp;Test Name</td> 
            <td width="20%" class="gridHeader" style="font-size:12px;" align='left'>&nbsp;Select</td></td>
            
        </tr>
        
        <logic:empty name="cashlessVO">
			<tr>
				<td class="generalcontent" colspan="7">&nbsp;&nbsp;No Data Found</td>
			</tr>
		</logic:empty>
		
		<logic:notEmpty name="cashlessVO">
		<logic:iterate id="cashlessVO" indexId="i" name="cashlessVO">
		
		<tr class="<%=(i.intValue()%2)==0? "gridOddRow":"gridEvenRow"%>" style="display: table-row;">
			 <td class="textLabel" style="color:#000000;"><bean:write name="cashlessVO" property="testName"/></td>
			 <td><input type="checkbox" id="chkid<%=i.intValue()%>" name="chkopt" value="<bean:write name="cashlessVO" property="diagSeqId"/>" ></td>	<!-- onclick="addTests(this) -->				        
			 
		</tr>
		
		</logic:iterate>
	</logic:notEmpty>
	
<!-- S T A R T : Grid -->
	<!-- ttk:HtmlGrid name="tableTestsDetails" className="gridWithCheckBox zeroMargin"/-->
	<!-- E N D : Grid -->

	
	<tr>
		<td align="center">
			 <button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSubmitTests()"><u>S</u>ubmit</button>
			 &nbsp;&nbsp;&nbsp;&nbsp;
		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCloseOTP();"><u>C</u>lose</button>&nbsp;		       
		
	  </tr>
		
	
	</table>

	<!-- E N D : Form Fields -->
<p>&nbsp;</p>
</div>
<logic:notEmpty name="frmCashlessAdd" property="sOffIds">
<script language="javascript">selectValues("<bean:write name="frmCashlessAdd" property="sOffIds"/>");</script>
</logic:notEmpty>
<INPUT TYPE="hidden" NAME="flag" VALUE="<bean:write name="frmCashlessAdd" property="flag"/>">
<INPUT TYPE="hidden" NAME="sOffIds" id="sOffIds" VALUE="">
<input type="hidden" name="strCorpPolicyNo" value="<%=strCorpPolicyNo%>">
<input type="hidden" name="groupId" value="<%=request.getParameter("groupId")%>">
</html:form>


<!-- E N D : Buttons -->
<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->



