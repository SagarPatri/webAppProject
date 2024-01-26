<%
/** @ (#) claims.jsp
 * Project     : TTK Healthcare Services
 * File        : claims.jsp
 * Author      : Balakrishna E
 * Company     : Span Systems Corporation
 * Date Created: 09th August 2010
 *
 * @author 		 :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<script language="javascript" src="/ttk/scripts/maintenance/claims.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/MaintenanceClaimsAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Claims</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
		<html:errors/>
		<!-- S T A R T : Form Fields -->
		<fieldset>
			<legend>Claims Maintenance</legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	      		<tr>
	        		<td>
	        			<ul style="margin-bottom:0px; ">
							<li class="liPad"><a href="#" onclick="javascript:onClaimsReqAmt()">Requested Amount Change</a></li>
						</ul>
					</td>
	       		</tr>
	    	</table>
		</fieldset>
		<!-- E N D : Form Fields -->
	</div>
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	</html:form>
	<!-- E N D : Content/Form Area -->
