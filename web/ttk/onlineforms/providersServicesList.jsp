<%
/** @ (#) grading.jsp 15th Oct 2005
 * Project     : TTK Healthcare Services
 * File        : grading.jsp
 * Author      : Srikanth H M
 * Company     : Span Systems Corporation
 * Date Created: 15th Oct 2005
 *
 * @author 		 : Srikanth H M
 * Modified by   : Raghavendra T M
 * Modified date : March 10,2006
 * Reason        :
 *
 */
%>
<%@ page import="com.ttk.common.TTKCommon"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/onlineforms/providersServicesList.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true;
</SCRIPT>
<%
	boolean viewmode=true;
	String strWidth = "";
	
	if((TTKCommon.isAuthorized(request,"Approve"))||(TTKCommon.isAuthorized(request,"Edit")))
		{
			viewmode=false;
		}
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/NewEnrollAction.do" >
	<!-- S T A R T : Page Title -->
	
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<!-- S T A R T : Form Fields -->
	<html:errors />
		<!-- S T A R T : Success Box -->
		<logic:notEmpty name="updated" scope="request">
			<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
			  		<td>
			  			<img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
						<bean:message name="updated" scope="request"/>
			  		</td>
				</tr>
			</table>
		</logic:notEmpty>
		<!-- E N D : Success Box -->
	<fieldset>
	<legend>Provider Information Details</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>
        	<ul  style="margin-bottom:0px; ">
        	<!-- removed Genral srevices as for projectX and moved this in Overall Infrastructural Details -->
				<!-- li class="liPad"><a href="#" onClick="javascript:onGeneral()">General</a></li-->
				<li class="liPad"><a href="#" onClick="javascript:onServices()" >Specialities</a></li>
                <li class="liPad"><a href="#" OnClick="javascript:goMID()">Medical Infrastructural Details</a></li>
				<li class="liPad"><a href="#" OnClick="javascript:goClinicalSupport()">Clinical Support Services</a></li>
				<li class="liPad"><a href="#" onClick="javascript:onOther()">Other Details</a></li>
				<li class="liPad"><a href="#" onClick="javascript:onOverAll()">Overall Infrastructural Details</a></li>
			</ul>
		</td>
     </tr>
    </table>
	</fieldset>
	
	<!-- S T A R T : Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  			<tr>
    			<td width="100%" align="center">
		    		<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>B</u>ack</button>&nbsp;
 				</td>
  			</tr>  		
		</table>
		<!-- E N D : Buttons -->
	

	<!-- E N D : Form Fields -->
</div>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="systemGradedId" VALUE="">
	<!-- E N D : Buttons -->
	</html:form>

<!-- E N D : Content/Form Area -->