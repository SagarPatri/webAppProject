<%/**
 * @ (#) customisedreportslist.jsp
 * Project       : TTK HealthCare Services
 * File          : customisedreportslist.jsp
 * Author        : Manikanta Kumar G G
 * Company       : Span Systems Corporation
 * Date Created  : April 18,2011
 * @author       : Manikanta Kumar G G
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
 <%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/onlinereports/customisedreportslist.js"></script>
<!-- S T A R T : Content/Form Area -->	
<html:form action="/OnlineReportsAction.do"  >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>Customize Reports</td>
		<td align="right" >&nbsp;</td>     
    </tr>
	</table>
	<!-- E N D : Page Title --> 
	<div class="contentArea" id="contentArea">	
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<fieldset>
	<legend>Customize Reports</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>
        	<ul  style="margin-bottom:0px; ">
				<li class="liPad"><a href="#" onclick="javascript:onFloatLink()">Float and Premium Report</a></li>
				<!-- Added for KOC-1255 -->
				<li class="liPad"><a href="#" onclick="javascript:onReportLink()">Online Dashboard Report</a></li>
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