<%
/** @ (#) iprupreauthlist.jsp 13th Nov 2008
 * Project     : TTK Healthcare Services
 * File        : iprupreauthlist.jsp
 * Author      : Ramakrishna K M
 * Company     : Span Systems Corporation
 * Date Created: 13th Nov 2008
 *
 * @author 		 : Ramakrishna K M
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import=" com.ttk.common.TTKCommon"%>
<head>
<title>IPRU Weblogin Access</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link href="/ttk/styles/style.css" media="screen" rel="stylesheet"></link>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/utils.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
</head>

<html:form action="/IpruListAction.do" >
	<h2>Cashless Status</h2>
	<table width="100%" cellspacing="0" cellpadding="0" id="grid">
  		<tr>
    		<th><strong>Cashless Request #</strong></th>
    		<th><strong>Registration Date</strong></th>
    		<th><strong>Hospital</strong></th>
    		<th><strong>Status</strong></th>
    	</tr>
  		<tr>
    		<td>Data1</td>
    		<td>Data1</td>
    		<td>Data1</td>
    	</tr>
  		<tr>
    		<td>Data1</td>
    		<td>Data1</td>
    		<td>Data1</td>
    	</tr>
	</table>
	
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>