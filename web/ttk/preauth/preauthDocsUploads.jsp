
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<%@ page import=" com.ttk.common.TTKCommon" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="ttk/styles/style.css" />

	<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
	<script type="text/javascript" src="/ttk/scripts/preauth/preauthgeneral.js"></script>
<%  
    boolean viewmode=true;
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
        viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
    pageContext.setAttribute("viewmode",new Boolean(viewmode));
    String Caption=(String)session.getAttribute("caption");
%>
<html:form action="/PreAuthDocumentsAction.do"  method="post" enctype="multipart/form-data">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Documents Related to the Pre-Authorization Request : <bean:write name="frmPreauthDocsUpload" property="caption"/></td> 
    		<%-- <logic:notEmpty name="frmPreauthDocsUpload" property="claimSeqId">
				<td align="center">&nbsp; <a href="#" onClick="javascript:goToClaimGeneral();" class="search"><u>Back</u></a>
				</td>		
			</logic:notEmpty>   --%>
    	</tr>
	</table>
	<html:errors/>
	<!-- E N D : Page Title --> 
	<div style="width: 99%; float: right;">
	<logic:notEmpty name="notify" scope="session">
		<table align="center" class="errorContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td><img src="/ttk/images/ErrorIcon.gif" alt="Alert" title="Alert" width="16" height="16" align="absmiddle">&nbsp;
	          <bean:write name="notify" scope="session"/>
	        </td>
	      </tr>
   	 </table>
   	 </logic:notEmpty>
   	 
	<!-- S T A R T : Success Box -->

		<logic:notEmpty name="successMsgs" scope="session">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
				<tr>
				  <td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="middle">&nbsp;
						<bean:write name="successMsgs" scope="session"/>
				  </td>
				</tr>
			</table>
	</logic:notEmpty>
	<fieldset>
		<legend>Upload Documents</legend>
		<table class="formContainer" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;">
			
			<tr style="border:1px solid;">
          	         	    
          	     <td align="left">File Description : <span class="mandatorySymbol">*</span></td>
			<td>
			     <html:text property="filedescription" styleId="filedescription" name="frmPreauthDocsUpload" styleClass="textBox textBoxLarge" maxlength="250" onkeyup="onSpaceValidatin();" />
	    	</td>
          	    
          	          
	          <td align="left">Browse File : <span class="mandatorySymbol">*</span></td>
			<td>
				<html:file property="file" styleId="file" name="frmPreauthDocsUpload" styleId="file"/>
			</td>
			 <td colspan="4" align="left">
				<button type="button" name="Button" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUpload()"><u>U</u>pload</button>&nbsp;&nbsp;&nbsp;		
			 </td>
			<%-- <logic:equal name="frmPreauthDocsUpload" property="buttonEnableYN" value="Y">
	   	       	<td colspan="4" align="left">
						<button type="button" name="Button" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onUpload()"><u>U</u>pload</button>&nbsp;&nbsp;&nbsp;
	   	       	</td>
	   	    </logic:equal>
	   	    
	   	    <logic:equal name="frmPreauthDocsUpload" property="buttonEnableYN" value="N">
	   	       	<td colspan="4" align="left">
					<button type="button" name="Button">Upload</button>&nbsp;&nbsp;&nbsp;
	   	       	</td>
	   	    </logic:equal> --%>
	   	       </tr>
			  </table>
		</fieldset>
		<fieldset>
		<legend>View Documents</legend>
	<div id="scroll"   style="height:290px; width:1100px; overflow-x:scroll ; overflow-y:scroll; padding-bottom:10px;">
	<!-- E N D : Success Box -->
	<!-- S T A R T : Form Fields -->
	<br>
	
	<!-- S T A R T : Grid -->
		<ttk:PreauthDocsUpload/>
	<!-- E N D : Grid -->
	</div>
		</fieldset>
	<!-- S T A R T : Buttons -->
	<br>
	<!-- END: Buttons -->
	
		</div>
		<!-- END : Form Fields -->
	
		
	<html:hidden property="mouDocSeqID"/>
	<input  type="hidden"  name="child"  value="">
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<html:hidden  property="preAuthSeqID" name="frmPreauthDocsUpload"/>
	<html:hidden  property="buttonEnableYN" name="frmPreauthDocsUpload"/>
	<input type="hidden" name="leftlink" value="">
	<input type="hidden" name="sublink" value="">
	<input type="hidden" name="tab" value="">
	<html:hidden  property="claimSeqId" name="frmPreauthDocsUpload"/>
		
</html:form>

<!-- E N D : Content/Form Area -->