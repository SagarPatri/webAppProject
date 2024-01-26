<%
/*1274A*/
 %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk"%>
<script type="text/javascript" src="/ttk/scripts/preauth/unfreezeuploadfile.js" ></script>
<script language="JavaScript" src="/ttk/scripts/validation.js"></script>

<html:form action="/FileUploadUnfreeze.do" method="post" enctype="multipart/form-data">
 <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td><bean:write name="frmUnfreezePreauth" property="caption"/> </td>
      	<td align="right"></td>
        <td align="right" ></td>
      </tr>
    </table>
    	<html:errors/>
    	 <logic:notEmpty name="notify" scope="request">
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="notify" scope="request"/>
	        </td>
	      </tr>
   	 </table>
	   </logic:notEmpty>
    	<logic:notEmpty name="updated" scope="request">
	   	<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
			 	<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
		    		<bean:write name="updated" scope="request"/>
		    	</td>
		 	</tr>
		</table>
	</logic:notEmpty>
		<div class="contentArea" id="contentArea">
			<table>
		<!--  	<tr>
			<td align="center" colspan="2">Select Document Type:</td>
			<td nowrap>
			
			<html:select name="frmUnfreezePreauth" property="documentType" styleId="documentType" styleClass="selectBox selectBoxMedium">
			<html:option value="claim">Claim Docs</html:option>
			<html:option value="addPrf">address proof</html:option>
			<html:option value="scan">Scan Doc</html:option>
			<html:option value="bill">Bill Docs</html:option>
			</html:select></td>
		</tr>-->
		<tr>
			<td align="center" colspan="2">Remarks</td>
			
			<td nowrap> 		
           <html:textarea name="frmUnfreezePreauth" property="remarks" styleId="remarks" styleClass="textBox textAreaLongHt"/>
        	</td>
        	     

		</tr>
		
		<tr>
			<td align="center" colspan="2">File Name</td>
			<td nowrap><html:file property="file" styleId="file" /></td>
		</tr>
     

		
		
	</table>
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="100%" align="center">  
		<button type="button" name="Button"  accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onuUploadPdfFiles();"><u>U</u>plaod File</button>&nbsp;
	    <button type="button" name="Button2" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
	    <logic:equal name="frmUnfreezePreauth" property="overrideYN" value="Y">
		    <button type="button" name="Button3" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:UnfreezePreAuth();">Overri<u>d</u>e</button>
		</logic:equal>
	</td>
	  </tr>
	</table>
	  </div>
	
<INPUT TYPE="hidden" NAME="mode" VALUE="">
<html:hidden name="frmUnfreezePreauth" property="overrideYN"/>
</html:form>
