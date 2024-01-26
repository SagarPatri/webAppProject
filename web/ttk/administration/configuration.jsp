<%
/** @ (#) configuration.jsp 13th September 2010
 * Project     	 : TTK Healthcare Services
 * File        	 : configuration.jsp
 * Author      	 : Manikanta Kumar G G
 * Company     	 : Span Systems Corporation
 * Date Created  : 13th September 2010
 *
 * @author 		 :
 * Modified by   : 
 * Modified date : 
 * Reason        : 
 *
 */
 %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<script language="javascript" src="/ttk/scripts/administration/configuration.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ConfigurationAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Configuration</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
		<html:errors/>
		<!-- S T A R T : Form Fields -->
		<fieldset>
			<legend>Configuration</legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	      		<tr>
	        		<td>
	        			<ul style="margin-bottom:0px;">
							<li class="liPad"><a href="#" onclick="javascript:onTDSCongiguration()">TDS Configuration</a></li>
							<li class="liPad"><a href="#" onclick="javascript:onServTaxCongiguration()">Service Tax Configuration</a></li>
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

