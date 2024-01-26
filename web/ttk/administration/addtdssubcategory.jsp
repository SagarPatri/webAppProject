<%
/**
 * @ (#) addtdssubcategory.jsp 30th July 2009
 * Project      :Vidal Health TPA Services
 * File         : addtdssubcategory.jsp
 * Author       : Navin Kumar R
 * Company      : Span Systems Corporation
 * Date Created : 30th July 2009
 *
 * @author       :
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%
	boolean viewmode=true;
  	boolean bEnabled=false;  
  	if(TTKCommon.isAuthorized(request,"Edit"))
  	{
    	viewmode=false;
  	}// end of if(TTKCommon.isAuthorized(request,"Edit"))

%>
<script language="javascript" src="/ttk/scripts/utils.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/administration/addtdssubcategory.js"></script>
<script language="javascript">
var JS_Focus_ID="<%=TTKCommon.checkNull(request.getParameter("focusID"))%>";
</script>

<!-- S T A R T : Content/Form Area -->
<html:form action="/AddTDSConfigurationAction.do" method="post" >
	<logic:match name="frmAddtdsConfiguration" property="enabled" value="false">
    	<% bEnabled=true; %>
    </logic:match>

	<!-- S T A R T : Page Title -->
    <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td><bean:write name="frmAddtdsConfiguration" property="caption"/> </td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
		<html:errors/>
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
  		<!-- S T A R T : Form Fields -->
  		<fieldset><legend>General</legend>
	    	<table align="center" class="formContainer"  border="0" cellspacing="0" cellpadding="0">
	        	<tr>
	    			<td width="20%" class="formLabel" nowrap="nowrap">Sub Category Name: <span class="mandatorySymbol">*</span></td>
	    			<td width="80%" nowrap="nowrap">
	    				<html:text property="tdsSubCatName" styleClass="textBox textBoxLarge" maxlength="60" onkeypress="ConvertToUpperCase(event.srcElement);blockEnterkey(event.srcElement);" disabled="<%=viewmode%>" />	    				
	    			</td>	    			
	  			</tr>
	  			<tr>
	  				<td class="formLabel">Description: </td>
	  				<td class="formLabel">
	  					<html:textarea property="tdsSubCatDesc" styleClass="textBox textAreaLong" disabled="<%=viewmode%>" />
	  				</td>
	  			</tr>
	    	</table>
	  	</fieldset>	  
  		<!-- E N D : Form Fields -->
  		<!-- S T A R T :  Buttons -->
		<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="100%" align="center">
				<%
					if(TTKCommon.isAuthorized(request,"Edit"))
					{
				%>
            		<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
					<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
       			<%
          			}//end of checking for Edit permission
       			%>
          			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>
        		</td>
      		</tr>
  		</table>
  		<!-- E N D : Buttons and Page Counter -->
  		<html:hidden property="tdsCatTypeID"/>
  		<html:hidden property="tdsSubCatTypeID" />
  		<input type="hidden" name="child" 		value="AddSubCategory">	
  		<input type="hidden" name="mode" 		value="">
  		<input type="hidden" name="activeYN" 	value="">  	
  		<input type="hidden" name="focusID" 	value="">	
	</div>
  	<logic:notEmpty name="frmAddtdsConfiguration" property="frmChanged">
  		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</html:form>