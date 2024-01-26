<%
/**
 * @ (#) financeAccountValidate.jsp 10th June 2006
 * Project      : TTK HealthCare Services
 * File         : financeAccountValidate.jsp
 * Author       : Kishor kumar S H
 * Company      : RCS
 * Date Created : 115h May 2015
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.action.table.TableData,com.ttk.action.table.Column,java.util.ArrayList" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/finance/financeRevisedAccountValidate.js"></script>
<script>
bAction=false;
var TC_Disabled = true;
</script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/CustomerBankDetailsAccountList.do" >
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	    <td>List of Bank Accounts For Hospital
	    <%-- <bean:write name="frmCustomerBankAccountList" property="caption"/> --%></td>     
	    <td align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
	    </tr>
	    </table>
	
	<!-- E N D : Page Title -->
  <div class="contentArea" id="contentArea">
	<!-- S T A R T : Search Box -->
	<html:errors/>
	
	<!-- S T A R T : Grid -->

	<ttk:HtmlGrid name="tableData"/>
	
	<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
      <tr>
      <td width="27%">&nbsp;</td>
        <td width="73%" align="right">
      <%
        	/* if(TTKCommon.isDataFound(request,"tableData"))
	    	{ */
        		//TableData tableData = (TableData)request.getSession().getAttribute("tableData");
        		//if(((Column)((ArrayList)tableData.getTitle()).get(1)).isLink())
        		//{
	    %>
<!-- 	    	<button type="button" name="Button" accesskey="c" class="buttonsCopyWB" onMouseout="this.className='buttonsCopyWB'" onMouseover="this.className='buttonsCopyWB buttonsCopyWBHover'" onClick="javascript:copyToWebBoard()"><u>C</u>opy to Web Board</button>&nbsp;
 -->        <%
        	//}
    /*   } */
	%>
	</td>
	
	</tr>
	  <ttk:PageLinks name="tableData"/>
	</table>
	
	
	
	
  </div>
	<!-- E N D : Buttons and Page Counter -->
	<!-- E N D : Content/Form Area -->
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="tab" VALUE="">
	<INPUT TYPE="hidden" NAME="switchType" VALUE="HOSP">
	<INPUT TYPE="hidden" NAME="from" VALUE="from">
	<INPUT TYPE="hidden" NAME="Flag" VALUE="Rnw">
	<INPUT TYPE="hidden" NAME="sublink" VALUE="<%=TTKCommon.getActiveSubLink(request)%>">
	<html:hidden property="policySeqID"/>
</html:form>
<!-- E N D : Main Container Table -->