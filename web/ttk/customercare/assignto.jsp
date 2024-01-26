<%
/**
 * @ (#) assignto.jsp 05th Oct 2006
 * Project      : TTK HealthCare Services
 * File         : assignto.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 05th Oct 2006
 *
 * @author       : Lancy A
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>

<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script language="javascript" src="/ttk/scripts/customercare/assignto.js"></script>
<script language="javascript">
	var JS_Focus_Disabled =true;
</script>
<%
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
	pageContext.setAttribute("TTKBranchID",Cache.getCacheObject("officeInfo"));
 	pageContext.setAttribute("DepartmentID",Cache.getCacheObject("departmentID"));
%>

<!-- S T A R T : Content/Form Area -->
<html:form action="/AssignAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
   		<td width="57%">Assign To</td>
		<td width="43%" align="right" class="webBoard">&nbsp;</td>
   	</tr>
</table>
<!-- E N D : Page Title -->

<!-- S T A R T : Form Fields -->
<div class="contentArea" id="contentArea">
<html:errors/>

<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
	   	<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
		    	<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
		    		<bean:message name="updated" scope="request"/>
		    	</td>
		 	</tr>
		</table>
	</logic:notEmpty>
<!-- E N D : Success Box -->

	<fieldset><legend>General</legend>
	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	  	<tr>
	    	<td valign="top" class="formLabel">Call Selected:</td>
	    	<td valign="top" class="textLabelBold"><bean:write name="frmAssignTo" property="logNumber"/></td>
	  	</tr>
	  	<tr>
			<td class="formLabel">Vidal Healthcare Branch: <span class="mandatorySymbol">*</span></td>
			<td class="formLabel">
        		<html:select property="officeSeqID"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
					<html:option value="">Select from list</html:option>
	  				<html:options collection="TTKBranchID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
          	</td>
	  	</tr>
	  	<tr>
	   		<td width="18%" class="formLabel">Department: <span class="mandatorySymbol">*</span></td>
	    	<td class="formLabel"><span class="formLabelBold">
	    		<html:select property="deptTypeID"  styleClass="selectBox selectBoxMedium" disabled="<%= viewmode %>">
					<html:option value="">Select from list</html:option>
	  				<html:options collection="DepartmentID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select></span>
	      	</td>
		</tr>
	  	<tr>
	    	<td class="formLabel">Remarks:</td>
			<td class="formLabel">
				<html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%= viewmode %>"/>
			</td>
		</tr>
	</table>
</fieldset>

<!-- E N D : Form Fields -->

<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td width="100%" align="center">
	       	    <%
    				if(TTKCommon.isAuthorized(request,"Edit"))
					{
			    %>
	       				<button type="button" name="Button2" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="onSave()"><u>S</u>ave</button>&nbsp;
		    			<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Reset()"><u>R</u>eset</button>&nbsp;
	    		<%
	    			}
	    		%>
		        	    <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="Close()"><u>C</u>lose</button>
			</td>
		</tr>
	</table>
<!-- E N D : Buttons -->
</div>
<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
<html:hidden property="logNumber"/>
<html:hidden property="logSeqID"/>
<html:hidden property="clarifySeqID"/>
<input type="hidden" name="child" value="">
<logic:notEmpty name="frmAssignTo" property="frmChanged">
	<script> ClientReset=false;TC_PageDataChanged=true;</script>
</logic:notEmpty>
</html:form>
<!-- E N D : Content/Form Area -->