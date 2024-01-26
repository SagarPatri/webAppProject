
<%@page import="com.ttk.dao.impl.preauth.PreAuthDAOImpl"%>
<%@page import="java.util.ArrayList"%>
<%
/**
 * @ (#) loghistory.jsp March, 2018
 * Project          : ProjectX
 * File          : loghistory.jsp
 * Author        : Bikash Kumar
 * Company       : RCS Technologies
 * Date Created  : March, 2018
 *
 * @author       : Bikash Kumar
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/trackdatachanges.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/common/healthcarelayout.js"></SCRIPT>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/preauth/historylistpreauthclaim.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/preauth/enrollmentlist.js"></script><!-- bikki -->
<script>
bAction=false;
var TC_Disabled = true;
</script>

<%



String strmemberSeqID = request.getParameter("memberSeqID");
Long lngmemberSeqID = Long.parseLong(strmemberSeqID);

 PreAuthDAOImpl preauth = new PreAuthDAOImpl();
ArrayList<ArrayList<String[]>>authorizationList = (ArrayList<ArrayList<String[]>>)preauth.getCustomerHistory(lngmemberSeqID);

ArrayList<String[]>alPAT =  authorizationList.get(0);

ArrayList<String[]>alCLM = authorizationList.get(1);

session.setAttribute("preauthHistoryListPAT", alPAT);
session.setAttribute("preauthHistoryListCLM", alCLM);
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/PreAuthHistoryAction.do">
    <html:errors/>
    <div class="contentArea" id="contentArea">


    <!-- S T A R T : Search Box -->

    <!-- E N D : Page Title -->
    <html:errors/>
    <div class="contentArea" id="contentArea">
    <logic:notEmpty name="preauthHistoryListPAT" scope="session">
    <fieldset>
    <legend>Pre-Auth History List</legend>
    <table align="center" class="formContainer" border="1" cellspacing="0" cellpadding="0">
            <tr  class="gridHeader">
            <th align="center">Pre-Auth No.</th>
            <th align="center">Authorization No.</th>
            <th align="center">Hospital Name</th>
            <th align="center">Approved Amt</th>
            <th align="center">Admissin Date/Time</th>
            <th align="center">Status</th>

             <th align="center">Completed Date</th>
            </tr>

      <c:forEach items="${sessionScope.preauthHistoryListPAT}" var="historyList">
       <tr>
        <td align="center">
        <a href="#" accesskey="g" onClick="javascript:doViewCustomerHistoryPAT('${historyList[0]}');"><c:out value="${historyList[1]}"/></a>
        </td>
        <td align="center"> <c:out value="${historyList[2]}"/></td>
        <td align="center"><c:out value="${historyList[3]}"/></td>
        <td align="center"><c:out value="${historyList[4]}"/></td>
        <td align="center"><c:out value="${historyList[5]}"/></td>
        <td align="center"><c:out value="${historyList[6]}"/></td>

        <td align="center"><c:out value="${historyList[7]}"/></td>
      </tr>
    </c:forEach>


</table>
</fieldset>

</logic:notEmpty>
<br><br><br><br><br><br>
 <logic:notEmpty name="preauthHistoryListCLM" scope="session">
    <fieldset>
    <legend>Claim History List</legend>
    <table align="center" class="formContainer" border="1" cellspacing="0" cellpadding="0">
            <tr  class="gridHeader">
            <th align="center">Claim No.</th>
            <th align="center">Settlement No.</th>
            <th align="center">Hospital Name</th>
            <th align="center">Approved Amt</th>
            <th align="center">Admissin Date/Time</th>
            <th align="center">Status</th>

             <th align="center">Completed Date</th>
             <th align="center">Payment Date</th>
            </tr>


      <c:forEach items="${sessionScope.preauthHistoryListCLM}" var="historyList">
       <tr>
        <td align="center">
        <a href="#" accesskey="g" onClick="javascript:doViewCustomerHistoryCLM('${historyList[0]}');"><c:out value="${historyList[1]}"/></a>
        </td>
        <td align="center"> <c:out value="${historyList[2]}"/></td>
        <td align="center"><c:out value="${historyList[3]}"/></td>
        <td align="center"><c:out value="${historyList[4]}"/></td>
        <td align="center"><c:out value="${historyList[5]}"/></td>
        <td align="center"><c:out value="${historyList[6]}"/></td>

        <td align="center"><c:out value="${historyList[7]}"/></td>
        <td align="center"><c:out value="${historyList[8]}"/></td>


      </tr>
    </c:forEach>


</table>

</fieldset>
 </logic:notEmpty>
    <!-- S T A R T : Buttons and Page Counter -->

      <table align="center" class="buttonsContainerGrid" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="27%"> </td>
        <td width="73%" nowrap align="right">
        <button name="Button" class="buttons" accessKey="c" onmouseover="this.className='buttons buttonsHover'" onmouseout="this.className='buttons'" onclick="javascript:self.close();" type="button"><u>C</u>lose</button>
      </td>
        </tr>
        </table>
    </div>
    <!-- E N D : Buttons and Page Counter -->

    <input type="hidden" name="authSeqID"/>
    <input type="hidden" name="mode">
    <input type="hidden" name="reforward"/>
</html:form>
<!-- E N D : Content/Form Area -->


 