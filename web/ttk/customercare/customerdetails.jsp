<%
/**
 * Project      : TTK HealthCare Services
 * File         : customerdetails.jsp
 * Author       : Bikash Kumar
 * Company      : RCS Technologies
 * Date Created : March,2018
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
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,org.dom4j.Document"%>
<%@page import="org.dom4j.Node,java.util.List,java.util.Iterator" %>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT type="text/javascript" SRC="/ttk/scripts/preauth/preauthhistorydetails.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/preauth/enrollmentlist.js"></script>
<script type="text/javascript">
var JS_Focus_Disabled =true;

</script>
<%
String PreAuthHistoryTypeID = (String)request.getAttribute("PreAuthHistoryTypeID");
String strLink=TTKCommon.getActiveLink(request);
pageContext.setAttribute("PreAuthHistoryTypeID",PreAuthHistoryTypeID);
pageContext.setAttribute("strLink",strLink);
%>
<div id="contentArea" class="contentArea">

<ttk:PreAuthHistoryDetails/>
<ttk:ClaimHistoryDetails/>
 <form action="/EnrollmentListAction.do">
 <input type="hidden" name="mode" value="doGeneral"/>
 <input type="hidden" name="activityDtlSeqId"/>
	    <table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
		 <tr>
		    <td align="right" colspan="4">
		    	<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBack();"><u>C</u>lose</button>
	        </td>
		</tr>
	</table>
 </form>

</div>

