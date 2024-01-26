<%
/**
 * @ (#) resolvediscrepancy.jsp 27th Sep 2005
 * Project      : TTK HealthCare Services
 * File         : resolvedisc.jsp
 * Author       : Srikanth H M
 * Company      : Span Systems Corporation
 * Date Created : 27th Sep 2005
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/empanelment/resolvediscrepancy.js"></SCRIPT>
	<!-- S T A R T : Content/Form Area -->
	<html:form action="/GeneralDiscrepancyAction.do">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
  		<tr>
	    	<td width="51%">Resolve Discrepancy</td>
	     	<td width="49%" align="right" class="webBoard">
		   	</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
	<br>
   <div class="contentArea" id="contentArea">
      <table align="center" class="gridWithCheckBox zeroMargin"  border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="45%" class="gridHeader">Provider Name</td>
          <td width="17%" class="gridHeader">Empanelment&nbsp;No.</td>
          <td width="23%" class="gridHeader">Vidal Health Branch</td>
          <td width="15%" class="gridHeader">Status</td>
        </tr>

   		<% int i=0; %>
        <logic:iterate id="discrepancy" name="alDiscrepancy">
			<%
				String strClass=i%2==0 ? "gridOddRow" : "gridEvenRow" ;
				i++;
			%>
				<tr class=<%=strClass%>>
		          <td><bean:write name="discrepancy" property="hospitalName" />&nbsp;</td>
		          <td><bean:write name="discrepancy" property="emplNumber" />&nbsp;</td>
		          <td><bean:write name="discrepancy" property="ttkBranch" />&nbsp;</td>
		          <td><bean:write name="discrepancy" property="status" />&nbsp;</td>
		        </tr>
		</logic:iterate>
     <table class="buttonsContainer" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="100%" align="right" nowrap>
          <button type="button" name="Button" accesskey="i" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:invalidate();"><u>I</u>nvalidate Empanelment</button>&nbsp;
		  <button type="button" name="Button2" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:ignore();">I<u>g</u>nore</button>&nbsp;
          <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Close();"><u>C</u>lose</button>
         </td>
      </tr>
    </table>
    </div>
	<!-- E N D : Form Fields -->

<INPUT TYPE="hidden" NAME="flagInvalidate" VALUE="">
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<INPUT TYPE="hidden" NAME="tab" VALUE="">
</html:form>