<%
/** @ (#) miscellaneous.jsp
 * Project     : TTK Healthcare Services
 * File        : miscellaneous.jsp
 * Author      : Navin Kumar R
 * Company     : Span Systems Corporation
 * Date Created: 7th September 2009
 *
 * @author 		 :
 * Modified by   :
 * Modified date :
 * Reason        :
 *
 */
 %>

<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<script language="javascript" src="/ttk/scripts/administration/miscellaneous.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/MiscellaneousAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Miscellaneous</td>
	    <td align="right" class="webBoard"><%@ include file="/ttk/common/toolbar.jsp" %></td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
		<html:errors/>
		<!-- S T A R T : Form Fields -->
		<fieldset>
			<legend>Miscellaneous</legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	      		<tr>
	        		<td>
	        			<ul style="margin-bottom:0px; ">
							<li class="liPad"><a href="#" onclick="javascript:onFamiliesWOSI()">Families without SI</a></li>
							<li class="liPad"><a href="#" onClick="javascript:onSelectLink('MiscePreAuthsandClaims','Cashless and Claims','PAT_CLM')">Cashless and Claims</a></li>
							<li class="liPad"><a href="#" onclick="javascript:onSelectLink('MisceBufferUtil','Buffer Utilization','BUFFER')">Buffer Utilization</a></li>
							<li class="liPad"><a href="#" onclick="javascript:onSelectLink('MisceSIUtil','SI Utilization','UTILISED SUM')">SI Utilization</a></li>
						</ul>
					</td>
	       		</tr>
	    	</table>
		</fieldset>
		<!-- E N D : Form Fields -->
	</div>		
	<input type="hidden" name="mode" value="">
	<input type="hidden" name="reporttype" value="">
	<input type="hidden" name="reportID" value="">
	<input type="hidden" name="reportName" value="">	
</html:form>
<!-- E N D : Content/Form Area -->
