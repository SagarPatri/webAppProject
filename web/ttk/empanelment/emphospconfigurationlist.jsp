<%/**
 * @ (#) emphospconfigurationlist.jsp
 * Project       : TTK HealthCare Services
 * File          : emphospconfigurationlist.jsp
 * Author        : Swaroop Kaushik D.S
 * Company       : Span Systems Corporation
 * Date Created  : May 05,2010
 * @author       : Swaroop Kaushik D.S
 * Modified by   :
 * Modified date :
 * Reason        :
 */
 %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon"%>
<script language="javascript" src="/ttk/scripts/empanelment/emphospconfigurationlist.js"></script>
<!-- S T A R T : Content/Form Area -->	
<html:form action="/AssociateCertificatesList"  >
<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  	<tr>
    	<td>Configuration Information - <%=request.getSession().getAttribute("ConfigurationTitle")%> </td>
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
       <%
        	if("Hospital".equals(TTKCommon.getActiveSubLink(request))){
        %>
        <td>
            <ul  style="margin-bottom:0px; ">	
			<li class="liPad"><a href="#" onclick="javascript:onAssociateCertificates()">Associate Certificate</a></li>			
			</ul>
		</td>
		<%
			} 
		%>	
	 </tr>
    </table>
   </fieldset>
    <table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    
	    <td align="center" nowrap>
   			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
	    </td>
	  </tr>
 	</table>
	<!-- E N D : Form Fields -->    
	</div>
	
  <input type="hidden" name="child" value=""> 
  <INPUT TYPE="hidden" NAME="rownum" VALUE="">
  <INPUT TYPE="hidden" NAME="mode" VALUE="">
  <INPUT TYPE="hidden" NAME="identifier" VALUE="">
  <INPUT TYPE="hidden" NAME="sortId" VALUE="">
  <INPUT TYPE="hidden" NAME="pageId" VALUE="">
    </html:form>
	<!-- E N D : Content/Form Area -->