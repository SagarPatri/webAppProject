<%
/**
 * @ (#) itemlist.jsp Aug Oct 16, 2007
 * Project      : TTK HealthCare Services
 * File         : itemlist.jsp
 * Author       : Arun K N
 * Company      : Span Systems Corporation
 * Date Created : Oct 16, 2007
 *
 * @author       : Arun K N
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<link href="/ttk/styles/Default.css" media="screen" rel="stylesheet"></link>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>


<!-- S T A R T : Content/Form Area -->
<html:form action="/ShowListAction.do" >
<title>List of - <bean:write name="frmShowList" property="caption"/></title>
	<div class="contentArea" id="contentArea">
	<html:errors/>

	<table align="center" class="gridWithCheckBox" border="0" cellspacing="1" cellpadding="0">
	<tr>
		<td width="100%" ID="listsubheader" CLASS="gridHeader"><bean:write name="frmShowList" property="caption"/>&nbsp;</td>
	</tr>
	<logic:notEmpty name="frmShowList" property="itemList">
		<logic:iterate id="item" name="frmShowList" property="itemList" indexId="i">
		<tr class="<%=(i.intValue()%2)==0? "gridOddRow":"gridEvenRow"%>">
				<td width="100%"><bean:write name="item" property="cacheDesc" /></td>
			</tr>
		</logic:iterate>
	</logic:notEmpty>
	<logic:empty name="frmShowList" property="itemList">
		<tr><td>No Data found</td></tr>
	</logic:empty>
	  <tr>
		<td align="center" colspan="2" class="buttonsContainerGrid">
			<input type="button" name="Button" value="Close" class="buttons" onClick="javascript:self.close();">
		</td>
	  </tr>
	</table>
	<!-- E N D : Form Fields -->
<p>&nbsp;</p>
</div>
<!-- E N D : Buttons --></div>
<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->

</html:form>
