<%
/** @ (#) hospitalfileupload.jsp
 * Project     : Vidal Health TPA
 *  Author       :Satya Moganti
 * Company      : Rcs Technologies
 * Date Created : march 24 ,2014
 *
 * @author       :Satya moganti
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ page import=" com.ttk.common.TTKCommon" %>
<script type="text/javascript" src="/ttk/scripts/hospital/hospitalfileupload.js" ></script>
<script language="JavaScript" src="/ttk/scripts/validation.js"></script>
<html:form action="/OnlineInsCompFileUploadAction.do" method="post" enctype="multipart/form-data">

 <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
      <tr>
      	<td>Hospital File Upload  <bean:write name="frmInsFileUpload" property="caption"/></td>
      	<td align="right"></td>
        <td align="right" ></td>
      </tr>
    </table>

<div class="contentArea" id="contentArea">
   	<html:errors/>
    
<!-- S T A R T : Error Box -->	
    <logic:notEmpty name="notify" scope="request">
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	    	<tr>
	      		<td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          	<bean:write name="notify" scope="request"/>
	        </td>
	      </tr>
   		</table>
	</logic:notEmpty>
<!-- E N D : Error Box -->
	
<!-- S T A R T : Success Box -->
    <logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
		 		<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success" width="16" height="16" align="absmiddle">&nbsp;
	    			<bean:write name="updated" scope="request"/>
	    		</td>
	 		</tr>
		</table>
	</logic:notEmpty>
<!-- E N D : Success Box -->
	
	<fieldset>
	<legend>Hospital File Upload</legend>
	<table width="50%" align="center">
		<tr>
			<td align="left">Select Upload Type:</td>
			<td>
				<html:select name="frmInsFileUpload" property="uploadFileType" styleId="documentType" styleClass="selectBox selectBoxMedium">
					<html:option value="PAT">Cashless</html:option>
					<html:option value="CLM">Claims</html:option>					
				</html:select>
				<html:select name="frmInsFileUpload" property="subFileType" styleId="subFileType" styleClass="selectBox selectBoxMedium">
					<html:option value="OTH">Others</html:option>	
					<html:option value="BLL">Bills</html:option>
					<html:option value="SRT">Shortfall</html:option>
					<html:option value="DCV">Discharge Voucher</html:option>
										
				</html:select>
			</td>
		</tr>	
		<%-- <tr>
			<td align="center" >Policy Type </td>			
			<td nowrap> 		
				<html:select name="frmInsFileUpload" property="policyType" styleId="policyType" styleClass="selectBox selectBoxMedium">    
				<html:option value="COR">Corporate</html:option>
				<html:option value="IND">Individual</html:option>					
				</html:select>
			</td>
   		</tr>--%>
		<tr>
			<td align="left" >Vidal ID<span class="mandatorySymbol">*</span></td>			
			<td nowrap> 		
           <html:text name="frmInsFileUpload" property="vidalId" styleId="vidalId" styleClass="textBox textBoxLarge"/>
        	</td>
   		</tr>
   		<tr>
			<td align="left" >Scheme Number</td>			
			<td nowrap> 		
           <html:text name="frmInsFileUpload" property="policyNumber" styleId="policyNumber" styleClass="textBox textBoxLarge"/>
        	</td>
   		</tr>
   		<tr>
			<td align="left">Remarks<span class="mandatorySymbol">*</span></td>
			
			<td nowrap> 		
           <html:textarea name="frmInsFileUpload" property="remarks" styleId="remarks" styleClass="textBox textAreaLongHt"/>
        	</td>
        	     

		</tr>
		
		<tr>
			<td align="left">Browse File : </td>
			<td>
				<html:file property="file" styleId="file" />
			</td>
			<td>
			<%-- 	<a href="#" onClick="javascript:onViewDocFiles()">
					<img src="/ttk/images/DocViewIcon.gif" alt="View Uploaded Files" width="16" height="16" border="0" align="absmiddle">
				</a>--%>
			</td>
		</tr>
	</table>
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
		<tr>
	    	<td width="100%" align="center">  
				<button type="button" name="Button"  accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUploadFiles();"><u>U</u>pload File</button>
			</td>
		</tr>
	</table>
	</fieldset>
</div>

<html:hidden property="EmpanelCode" name="frmInsFileUpload"/>
<html:hidden property="LoginUserId" name="frmInsFileUpload"/> 
<html:hidden property="UserSeqId" name="frmInsFileUpload" />
<INPUT TYPE="hidden" NAME="mode" VALUE="">
</html:form>