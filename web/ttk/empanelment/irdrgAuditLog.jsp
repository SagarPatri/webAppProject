<%@page import="org.apache.struts.action.DynaActionForm"%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<script language="javascript" src="/ttk/scripts/empanelment/hospitalgeneral.js"></script>

<html:form action="/AddIrdrgParameters.do" method="post" enctype="multipart/form-data">

	<div class="contentArea" id="contentArea">

		<fieldset>
			<legend>Audit Log :</legend>
			<div class="scrollableGrid" style="height: 290px;">
				<ttk:IrdrgAuditLog />
			</div>
		</fieldset>
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">
			<button type="button" name="Button" accesskey="c" onClick="javascript:onCloseAuditLog()"><u>C</u>lose</button>
		</td>
	  </tr>
	</table>
	</div>
<INPUT TYPE="hidden" NAME="mode" VALUE="">	
	
	
</html:form>
