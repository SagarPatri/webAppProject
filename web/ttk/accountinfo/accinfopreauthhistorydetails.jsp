<%
/**
 * @ (#) preauthhistorydetail.jsp.jsp May 10, 2006
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
<%@taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.PolicyAccInfoWebBoardHelper,org.dom4j.Document"%>
<%@page import="org.dom4j.Node,java.util.List,java.util.Iterator" %>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>

<SCRIPT type="text/javascript" SRC="/ttk/scripts/accountinfo/accinfopreauthhistorydetails.js"></SCRIPT><% /**/%>
<script type="text/javascript">
var JS_Focus_Disabled =true;
</script>

<%

String PreAuthHistoryTypeID = (String)request.getAttribute("PreAuthHistoryTypeID");

String strLink="PAT";
pageContext.setAttribute("PreAuthHistoryTypeID",PreAuthHistoryTypeID);
pageContext.setAttribute("strLink",strLink);
%>
<div id="contentArea" class="contentArea">

<ttk:PreAuthHistoryDetails/>
 <% if("PAT".equals(strLink)){ %>
 <form action="/AccInfoMemberActionHistory.do">
 <%}%>
 
 
 <input type="hidden" name="mode" value="historyList"/>
 <input type="hidden" name="activityDtlSeqId"/>
 
 <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
 	 <tr>
	    <td width="27%"> </td>
	    <logic:empty scope="request" name="flag">
	    <td width="73%" nowrap align="right">
	    <button type="submit"  name="Button1" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"><u>C</u>lose</button>
	    </td>
	    </logic:empty>
	     <logic:notEmpty scope="request" name="flag">
	      <td width="73%" nowrap align="right">
	     <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Closeflag();"><u>C</u>lose</button>
	     </td>
	     </logic:notEmpty>
	    </tr>
	    </table>
	   <input type="hidden" id="flaghistory" name="flaghistory" value=""/>
	    
 </form>

</div>

