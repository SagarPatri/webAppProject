<%/**
 * @ (#) renewaldays.jsp Nov 14th 2007
 * Project       : TTK HealthCare Services
 * File          : configurationlist.jsp
 * Author        : Balaji C R B
 * Company       : Span Systems Corporation
 * Date Created  : June 30,2008
 * @author       : Balaji C R B
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<script language="javascript" src="/ttk/scripts/administration/configurationlist.js"></script>

<!-- S T A R T : Content/Form Area -->	
<html:form action="/RenewalConfiguration"  > 
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>Configuration Information</td>
		<td align="right" class="webBoard"><%@ include file="/ttk/common/toolbar.jsp" %></td>     
    </tr>
	</table>
	<!-- E N D : Page Title --> 
	<div class="contentArea" id="contentArea">	
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<fieldset>
	<legend>Configuration </legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><ul  style="margin-bottom:0px; ">		
		<li class="liPad"><a href="#" onclick="javascript:onPlan()">Configure Plan</a></li>	
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