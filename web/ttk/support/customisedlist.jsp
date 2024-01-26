<%/**
 * @ (#) customisedlist.jsp July 24 2008
 * Project       : TTK HealthCare Services
 * File          : customisedlist.jsp
 * Author        : Sanjay.G.Nayaka
 * Company       : Span Systems Corporation
 * Date Created  : July 24 2008
 * @author       : Sanjay.G.Nayaka
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>

<script language="javascript" src="/ttk/scripts/support/customisedlist.js"></script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/CustomisedListAction">
  
  <!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>List of Customised Items</td>
		<td align="right" class="webBoard"><%@ include file="/ttk/common/toolbar.jsp" %></td>     
    </tr>
	</table>
	<!-- E N D : Page Title --> 
	<div class="contentArea" id="contentArea">	
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<fieldset>
	<legend>Customised List </legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td>
        	<ul  style="margin-bottom:0px;">
			<li class="liPad"><a href="#" onclick="javascript:onCustomisedItem('HDFC Certificate Generation')">HDFC Certificate Generation</a></li>
	       </ul>
		</td>
      </tr>
     </table>
	</fieldset>
	<!-- E N D : Form Fields -->    
	</div>
	 <input type=hidden name="mode"/>
	 <input type="hidden" name="caption"  value=''>
 </html:form>
    
	<!-- E N D : Content/Form Area -->