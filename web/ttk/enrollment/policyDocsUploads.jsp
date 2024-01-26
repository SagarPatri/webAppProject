<%
/**
 * @ (#) policyDocsUploads.java 31 Dec 2014
 * Project      : TTK HealthCare Services
 * File         :policyDocsUploads.java
 * Author       : Kishor kumar S H
 * Company      : RCS Technologies
 * Date Created : 20th Aug 2015
 *
 * @author       : Kishor kumar S H
 * Modified by   : Rishi Sharma
 * Modified date : 25th June 2018
 */
%>

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
<script language="javascript" src="/ttk/scripts/enrollment/policyDocsUploads.js"></script>
<%  
 
    boolean viewmode=true;
  
    if(TTKCommon.isAuthorized(request,"Edit"))
    {
        viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
    pageContext.setAttribute("viewmode",new Boolean(viewmode));
    String Caption=(String)session.getAttribute("caption");
//test nag
%>
<html:form action="/UploadPolicyDocs.do"  method="post" enctype="multipart/form-data">
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
    		<td>Document Attachments: <bean:write name="frmPolicyUploads" property="caption"/></td>   
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
<%-- 	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:message name="updated" scope="request"/>
				</td>
			</tr>
		</table>
	</logic:notEmpty> --%>
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
				
          		<td width="10%" align="left" nowrap>&nbsp;&nbsp;File Type:<span class="mandatorySymbol">*</span></td>
          		<td width="18%" align="left" nowrap>
          			<html:select property ="fileType" name="frmPolicyUploads" styleClass="selectBox selectBoxMedium" disabled="<%=viewmode%>">
       					<html:option value="">--Select from list--</html:option>
              			<html:option value="CEN">Census list</html:option>
              			<html:option value="TOB">TOB</html:option>
              			<html:option value="POS">UND to POS form</html:option>
              			<html:option value="TL">Trade License</html:option>
              			<html:option value="EC">Establishment card</html:option>
              			<html:option value="CP">Client Photos</html:option>
              			<html:option value="RP">Rename Photos</html:option>
              			<html:option value="MAF">MAF for Above 65yrs</html:option>
              			<html:option value="CEMAIL">Confirmation E-mail</html:option>
              			<html:option value="OTH">Others</html:option>
              		</html:select>
          		</td>
          	    
          	     <td align="left">File Description : </td>
			<td>
			     <html:text property="filedescription" styleClass="textBox textBoxLarge" maxlength="250" />
	    	</td>
          	    
          	          
	          <td align="left">Browse File : <span class="mandatorySymbol">*</span></td>
			<td>
				<html:file property="file" name="frmPolicyUploads" styleId="file"/>
			</td>
			
	   	       	<td colspan="4" align="left">
	   	       		<%-- <logic:match name="viewmode" value="false">
	   	       		<logic:notEqual name="frmPolicyUploads" property="lngEventSeqID" value="4">
		   	       		<logic:notEqual value="Y" name="policyCompletedYN">
							<button type="button" name="Button" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>U</u>pload</button>&nbsp;&nbsp;&nbsp;
						</logic:notEqual>
					</logic:notEqual>
					<logic:equal name="frmPolicyUploads" property="lngEventSeqID" value="4">
					 		<logic:notEqual value="Y" name="policyCompletedYN">
							<button type="button" name="Button" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'"  ><u>U</u>pload</button>&nbsp;&nbsp;&nbsp;
						</logic:notEqual>
					</logic:equal>
					</logic:match> --%>
					<button type="button" name="Button" accesskey="u" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>U</u>pload</button>&nbsp;&nbsp;&nbsp;
					
	   	       	</td>
	   	       </tr>
			  </table>
		</fieldset>
		<fieldset>
		<legend>View Documents</legend>
	<div id="scroll" width="100%"  style="height:240px; width:1150px; overflow-x:scroll ; overflow-y: scroll; padding-bottom:10px;">
	
	<!-- E N D : Success Box -->
	<!-- S T A R T : Form Fields -->
	<br>
	
	<!-- S T A R T : Grid -->
	<%-- <ttk:HtmlGrid name="tablePolicyDocs" className="gridWithCheckBox zeroMargin"/> --%>
	
		<ttk:PolicyDocsUploadLogs/>

	<!-- E N D : Grid -->

	
	</div>
		</fieldset>
	<!-- S T A R T : Buttons -->
	<br>
	<table class="buttonsSavetolistGrid" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" align="right" nowrap class="formLabel">
			<%
	    		if(TTKCommon.isDataFound(request,"tableDataAssociateCertificates") && TTKCommon.isAuthorized(request,"Delete"))
				{
		    %>
					<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>
			<%
        		}// end of if(TTKCommon.isDataFound(request,"tableDataLinkDetails") && TTKCommon.isAuthorized(request,"Delete"))
        	%>		
			</td>
		</tr>
	</table>
	<!-- END: Buttons -->
	<table class="formContainer" border="0" cellspacing="0" cellpadding="0" style="margin-top:5px;">
			
			<tr style="border:1px solid;">
			<td align="center">
		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>		
		     </td>
		     </tr>
		     </table>
		</div>
		<!-- END : Form Fields -->
	
		
	<html:hidden property="mouDocSeqID"/>
	   <input  type="hidden"  name="child"  value="">
	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<%--     <html:hidden  name="lngEventSeqID" value="<%=(String)request.getAttribute("lngEventSeqID") %>"/>
	    <html:hidden    name="switchtype"  value="<%=(String)request.getAttribute("switchtype") %>"/> --%>
	     <input  type="hidden"  name="lngEventSeqID"  value="<%=(String)request.getAttribute("lngEventSeqID") %>">
	     <input  type="hidden"  name="switchtype"  value="<%=(String)request.getAttribute("switchtype") %>">
</html:form>

<!-- E N D : Content/Form Area -->