<%
/** @ (#) enrollment.jsp
 * Project     : TTK Healthcare Services
 * File        : enrollment.jsp
 * Author      : Navin Kumar R
 * Company     : Span Systems Corporation
 * Date Created: 24th August 2009
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
<script language="javascript" src="/ttk/scripts/maintenance/enrollment.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/EnrollmentAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td>Enrollment</td>
	  </tr>
	</table>
	<!-- E N D : Page Title -->

	<div class="contentArea" id="contentArea">
		<html:errors/>
		<!-- S T A R T : Form Fields -->
		<fieldset>
			<legend>Enrollment Maintenance</legend>
			<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	      		<tr>
	        		<td>
	        			<ul style="margin-bottom:0px; ">
							<li class="liPad"><a href="#" onclick="javascript:onPolicySubType()">Scheme Sub Type</a></li>
							<%
		        			if(TTKCommon.isAuthorized(request,"DO/BO Changes"))
		        			{
		    				%>
							<li class="liPad"><a href="#" onclick="javascript:onDOBOChanges()">DO/BO Changes</a></li>
							<%
        					}
		        			if(TTKCommon.isAuthorized(request,"Policy Period"))
		        			{
		    				%>
							<li class="liPad"><a href="#" onclick="javascript:onPolicyPeriod()">Scheme Period</a></li>
							<%
        					}
 						if(TTKCommon.isAuthorized(request,"Buffer Config"))
		        			{//1216B CR
		    				%>
							<li class="liPad"><a href="#" onclick="javascript:onEnrBufferConfig()">Buffer Config(Enrollment)</a></li>
							<%
							 }   
        					%>			
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
