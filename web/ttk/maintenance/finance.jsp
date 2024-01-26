<%
/** @ (#) finance.jsp 11th November 2009
 * Project     	 : TTK Healthcare Services
 * File        	 : finance.jsp
 * Author      	 : Navin Kumar R
 * Company     	 : Span Systems Corporation
 * Date Created  : 11th November 2009
 *
 * @author 		 :
 * Modified by   : Manikanta Kumar
 * Modified date : 14th April 2010
 * Reason        : New screen is created in Finance Maintenance link and this screen is shifted to new sublink.
 *
 */
 %>

<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>

<script language="javascript" src="/ttk/scripts/maintenance/finance.js"></script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/MaintainFinanceAction.do" >
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>Finance</td>
	</table>
	<!-- E N D : Page Title --> 
	<div class="contentArea" id="contentArea">	
	<html:errors/>
	<!-- S T A R T : Form Fields -->
	<fieldset>
	<legend>Finance Maintenance</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
          <td>
        	<ul  style="margin-bottom:0px;">
			<li class="liPad"><a href="#" onclick="javascript:onChequeDetails()">Cheque Details</a></li>
			<li class="liPad"><a href="#" onclick="javascript:onGenerateCheque()">Print Cheque</a></li>
	       </ul>
		</td>
     </table>
	</fieldset>
	<!-- E N D : Form Fields -->    
	</div>
	 <input type=hidden name="mode"/>
	
 </html:form>
    
	<!-- E N D : Content/Form Area -->