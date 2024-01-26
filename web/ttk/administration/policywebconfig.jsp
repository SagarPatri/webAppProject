<%
/**
 * @ (#) policywebconfig.jsp 19th Dec 2007
 * Project      : TTK HealthCare Services
 * File         : policywebconfig.jsp
 * Author       : Yogesh S.C
 * Company      : Span Systems Corporation
 * Date Created : 19th Dec 2007
 *
 * @author       :Yogesh S.C
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/policywebconfig.js"></script>

<!-- S T A R T : Content/Form Area -->	
<html:form action="/WebConfigAction"  > 
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>Web Configuration Information</td>
	<td align="right" class="webBoard"><%@ include file="/ttk/common/toolbar.jsp" %></td>     
    </tr>
</table>
	<!-- E N D : Page Title --> 
	<div class="contentArea" id="contentArea">	
	<html:errors/>
	<!-- S T A R T : Success Box -->
	

	<!-- S T A R T : Form Fields -->
	<fieldset>
	<legend>Configuration </legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><ul  style="margin-bottom:0px; ">
		<li class="liPad"><a href="#" onclick="javascript:onHomePage()">Home Page  </a></li>
		<li class="liPad"><a href="#" onclick="javascript:onMemberDetails()">Member Details</a></li>
		<li class="liPad"><a href="#" onclick="javascript:onWebLogin()">Web Login</a></li>
		<li class="liPad"><a href="#" onclick="javascript:onInsCompany()">Healthcare Company Information</a></li>
		<li class="liPad"><a href="#" onclick="javascript:onRelInformation()">Relationship Information</a></li>
		</ul></td>
        </tr>
    </table>
	</fieldset>
	<!-- E N D : Form Fields -->
    
</div>
 
  <input type="hidden" name="child" value=""> 
  <INPUT TYPE="hidden" NAME="rownum" VALUE="">
  <INPUT TYPE="hidden" NAME="mode" VALUE="">
  <INPUT TYPE="hidden" NAME="sortId" VALUE="">
  <INPUT TYPE="hidden" NAME="pageId" VALUE="">
    </html:form>
	<!-- E N D : Content/Form Area -->




