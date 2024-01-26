<%
/**
 * @ (#) validationresult.jsp Oct 12th 2006
 * Project      : TTK HealthCare Services
 * File         : validationresult.jsp
 * Author       : Krishna K H
 * Company      : Span Systems Corporation
 * Date Created : Oct 12th 2006
 *
 * @author       : Krishna K H
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page import="com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script> TC_Disabled = true;</script>
<script>
function onClose()
{
	document.forms[1].mode.value="doClose";
    document.forms[1].action="/ShowValidationError.do";
	document.forms[1].submit();
}
</script>
<!-- S T A R T : Content/Form Area -->
<html:form action="/ShowValidationError.do" >

	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>Validation Error/s</td>
        <td width="43%" align="right" class="webBoard">&nbsp;</td>
      </tr>
    </table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<% String strClass="";
		int i=0; %>
	<html:errors/>

    <!-- S T A R T : Form Fields -->
	<table align="center" border="0" cellspacing="0" cellpadding="0" class="gridWithCheckBox">
	  <tr>
    	<td>
    		<tr>
    		<td width="20%" class="gridHeader" nowrap>Policy Number</td>
    		<td width="20%" class="gridHeader" nowrap>Enrollment Number</td>
    		<td width="20%" class="gridHeader" nowrap>Enrollment ID</td>
    		<td width="20%" class="gridHeader" nowrap>Error Message</td>
			</tr>
    		<logic:notEmpty name="frmShowValidationError" property="alErrors">
	    		<logic:iterate id="error" name="frmShowValidationError" property="alErrors">
					<%
						if((i++)%2==0) {
						strClass="CLASS=gridEvenRow ";
		    			}else
		    			{
		    			 strClass="CLASS=gridOddRow ";
		    			}
		    		 %>
	    			<tr> <td width="10%" <%=strClass%> ><bean:write name="error" property="policyNbr" />&nbsp;</td>
	    			     <td width="10%" <%=strClass%> ><bean:write name="error" property="enrollmentNbr" />&nbsp;</td>
	    			     <td width="10%" <%=strClass%> ><bean:write name="error" property="enrollmentID" />&nbsp;</td>
	    			     <td width="70%" <%=strClass%> class="textLabelBold"><bean:write name="error" property="errorMessage" filter="false" />&nbsp;</td>
			        </tr>
    			</logic:iterate>
    		</logic:notEmpty>
    		<logic:empty name="frmShowValidationError" property="alErrors">
    		<tr>
	    		<td width="10%" colspan="4" >No Data Found</td>
    		</tr>
    		</logic:empty>
	    </td>
	  </tr>
	</table>
	<!-- E N D : Form Fields -->
<p>&nbsp;</p>

   <table align="center" class="formContainer rcContainer" border="0" cellspacing="1" cellpadding="0">
	  	<tr>
    		<td width="10%" align="center" >
    			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>&nbsp;
			</td>
    	</tr>
	</table>
 	<html:hidden property="mode" />
</div>
<!-- E N D : Buttons -->

	<!-- E N D : Content/Form Area -->
<!-- E N D : Main Container Table -->

</html:form>