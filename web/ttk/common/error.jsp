<%
/** @ (#) error.jsp 25th July 2005
 * Project     : TTK Healthcare Services
 * File        : error.jsp
 * Author      : Nagaraj D.V
 * Company     : Span Systems Corporation
 * Date Created: 25th July 2005
 *
 * @author Nagaraj D.V
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page isErrorPage="true"%>
<form name="errors">
<logic:messagesPresent>
	<html:errors/>
	<%
		request.setAttribute("display","display");
	%>
</logic:messagesPresent>

<logic:empty name="display">
	<%
		request.setAttribute("display","error.general");
		request.setAttribute("header","errors.header");
		request.setAttribute("footer","errors.footer");
	%>
			<bean:message name="header" scope="request"/>
	        <logic:notEmpty name="display">
				<bean:message name="display" scope="request"/>
			</logic:notEmpty>
			<bean:message name="footer" scope="request"/>
			
			<!-- <img alt="Please Contact Administrator" src="/ttk/images/Insurance/under_construction_557.jpg"> -->

</logic:empty>
<script>
	TC_Disabled=true;
</script>
<br><br>
</form>
