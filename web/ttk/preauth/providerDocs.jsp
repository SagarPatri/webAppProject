<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<head>
<script type="text/javascript"	src="/ttk/scripts/preauth/providerDocs.js"></script>

</head>
<html:form action="/PreAuthGeneralAction.do" >
<html:errors/>
<!-- <div id="sideHeading">Provider Doc Details</div> -->
<br><br>
<fieldset>
	<legend>Provider Documents Upload</legend>
<table border="0" align="center" cellpadding="0" cellspacing="0" class="gridWithCheckBox zeroMargin">
<tr>
<th CLASS="gridHeader">Description</th>
<th CLASS="gridHeader">File Name</th>
<th CLASS="gridHeader">Date and Time</th>
<th CLASS="gridHeader">User Id</th>
</tr>
<logic:iterate id="providerDoc" name="providerUploadedDocs" scope="session">
<tr>
<td CLASS="gridOddRow"> <bean:write name="providerDoc" property="description"/></td>
<td CLASS="gridOddRow"><a href="#" onclick="doViewProviderDoc('<bean:write name="providerDoc" property="mouDocPath"/>','<bean:write name="providerDoc" property="fileName"/>')"><bean:write name="providerDoc" property="fileName"/></a></td>
<td CLASS="gridOddRow"><bean:write name="providerDoc" property="dateTime"/></td>
<td CLASS="gridOddRow"><bean:write name="providerDoc" property="userId"/></td>
</tr>
</logic:iterate>
</table>

	<br><br>
	<table style="margin-left: 45%;">
	<tr>
	<td></td>
	<td>
	
	<button type="button" name="Button3" accesskey="d"	class="buttons" onMouseout="this.className='buttons'"		onMouseover="this.className='buttons buttonsHover'" 		onClick="onClose()">Close</button>
	&nbsp;	
</td>
	</tr>
	</table>
	</fieldset>
<input type="hidden" name="mode" value="">
<input type="hidden" name="fileName" value="">
<input type="hidden" name="filePath" value="">
</html:form>
