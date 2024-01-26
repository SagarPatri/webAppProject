<%
/** @ (#) certificategeneration.jsp
 * Project     : TTK Healthcare Services
 * File        : certificategeneration.jsp
 * Author      : Balakrishna E
 * Company     : Span Systems Corporation
 * Date Created: 21st Mar 2010
 *
 * @author 		 :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<script language="javascript" src="/ttk/scripts/finance/certificategeneration.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/CertificateGenAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Certificate Generation</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
		<html:errors/>
		<!-- S T A R T : Form Fields -->
		<fieldset>
			<legend>Certificate Generation</legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	      		<tr>
	        		<td>
	        			<ul style="margin-bottom:0px; ">
							<li class="liPad"><a href="#" onclick="javascript:onGenerateBatch()">Generate Batch</a></li>
							<li class="liPad"><a href="#" onclick="javascript:onGenerateInd()">Generate Individually</a></li>										
						</ul>
					</td>
	       		</tr>
	    	</table>
		</fieldset>
		<!-- E N D : Form Fields -->
	</div>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>
	<!-- E N D : Content/Form Area -->
