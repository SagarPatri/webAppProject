<%
/**
 * @ (#) bufferdetail.jsp 30th June 2006
 * Project      : TTK HealthCare Services
 * File         : bufferdetail.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 30th June 2006
 *
 * @author       :Chandrasekaran J
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import=" com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/preauth/bufferdetail.js"></script>
<%

	boolean bufferapproved=true;
	boolean requesteddate=true;

	boolean approveddate=true;
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Buffer Approved"))
	{
		bufferapproved=false;
		
	}//end of if(TTKCommon.isAuthorized(request,"Buffer Approved"))
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}// end of if(TTKCommon.isAuthorized(request,"Edit"))
	pageContext.setAttribute("bufferStatus",Cache.getCacheObject("bufferStatus"));
	pageContext.setAttribute("supClaimTypeID",Cache.getCacheObject("supClaimTypeID"));
	pageContext.setAttribute("normalbufferTypeList",Cache.getCacheObject("normalbufferTypeList"));//
	pageContext.setAttribute("supBufferTypeID",Cache.getCacheObject("supBufferTypeID")); // for criricalclaimType
	
%>

<!-- S T A R T : Content/Form Area -->

<html:form action="/BufferDetailsAction.do" method="post" enctype="multipart/form-data">
<logic:notEmpty name="fileExistYN" scope="request">
		<script language="JavaScript">
		    var w = screen.availWidth - 10;
			var h = screen.availHeight - 82;
			var features = "scrollbars=0,status=1,toolbar=1,top=0,left=0,resizable=0,menubar=yes,width="+w+",height="+h;
			window.open("/BufferDetailsAction.do?mode=doViewFilePdf&displayFile=<bean:write name="fileExistYN"/>",'ShowFile',features);
		</script>
	</logic:notEmpty>
	<!-- S T A R T : Page Title -->
	<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="57%"><bean:write name="frmBufferDetails" property="caption"/></td>
			<td width="43%" align="right" class="webBoard">&nbsp;&nbsp;</td>
		</tr>
	</table>
	<!-- E N D : Page Title -->
	<!-- S T A R T : Form Fields -->
	<div class="contentArea" id="contentArea">
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
	<logic:empty name="frmBufferDetails" property="requestedDate" >
		<% requesteddate=false; %>
	</logic:empty>
	<logic:empty name="frmBufferDetails" property="approvedDate" >
		<% approveddate=false; %>
	</logic:empty>

	 <fieldset>
    	<legend>Buffer Amount Details</legend>
    	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    	    <tr>
    	   <td width="20%" height="25"class="formLabel">Claim Type:<span class="mandatorySymbol">*</span></td>
			<td height="25" class="textLabel" nowrap="nowrap">
	                <html:select property="claimType" styleClass="selectBox selectBoxMedium" onchange="showhideBufferType()" disabled="<%= (viewmode || approveddate)%>">
					    	<html:option value="">Select from list</html:option>
					       <html:optionsCollection name="supClaimTypeID" label="cacheDesc" value="cacheId" />
		            </html:select>
            </td>
	 		<td width="20%" height="25"class="formLabel">Buffer Type:<span class="mandatorySymbol">*</span></td>
			  <logic:match name="frmBufferDetails" property="claimType" value="NRML">	 
			         <td id="normalID" height="25" class="textLabel" nowrap="nowrap" style="display: ">
							<html:select property="bufferType" styleClass="selectBox selectBoxMedium"   onchange="ChangeType(this)" disabled="<%= (viewmode || approveddate) %>">
								<html:option value="">Select from list</html:option>
								<html:optionsCollection name="normalbufferTypeList" label="cacheDesc" value="cacheId" />
							</html:select>
					 </td>
  			   </logic:match>
  			  <logic:notMatch name="frmBufferDetails" property="claimType" value="NRML">
	  			   <td id="normalID" height="25" class="textLabel" nowrap="nowrap"  style="display:none">
				          	       <html:select property="bufferType" styleClass="selectBox selectBoxMedium"  onchange="ChangeType(this)" disabled="<%= (viewmode || approveddate)%>">
				           		  		<html:option value="">Select from list</html:option>
				           		        <html:optionsCollection name="normalbufferTypeList" label="cacheDesc" value="cacheId" />
				           	      </html:select>
		        </td>  
  		      </logic:notMatch>
        	 <logic:match name="frmBufferDetails" property="claimType" value="CRTL">	 
					         <td id="criticalID" height="25" class="textLabel" nowrap="nowrap" style="display: ">
								<html:select property="bufferType1" styleClass="selectBox selectBoxMedium"   onchange="ChangeType(this)" disabled="<%= (viewmode || approveddate) %>">
										<html:option value="">Select from list</html:option>
										<html:optionsCollection name="supBufferTypeID" label="cacheDesc" value="cacheId" />
								</html:select>
							 </td>
	  			   </logic:match>
 			     <logic:notMatch name="frmBufferDetails" property="claimType" value="CRTL">
		  		           <td id="criticalID" height="25" class="textLabel" nowrap="nowrap"  style="display:none">
		          	                 <html:select property="bufferType1" styleClass="selectBox selectBoxMedium"  onchange="ChangeType(this)" disabled="<%=(viewmode || approveddate) %>">
		           		  		          <html:option value="">Select from list</html:option>
		           		                  <html:optionsCollection name="supBufferTypeID" label="cacheDesc" value="cacheId" />
		           	   				 </html:select>
		        	        </td>
		        </logic:notMatch>
            </tr>
    	
    	    <tr>
    			<td width="20%" height="25">Buffer Allocation Mode.: </td>
    			<td width="30%" class="textLabel"><strong><bean:write name="frmBufferDetails" property="bufferMode"/></strong></td>
    			<td class="formLabel">&nbsp;</td>
    			<td class="textLabel">&nbsp;</td>
    		</tr>
    		<tr>
    			<td width="20%" height="25" class="formLabel">Buffer Family/Member Cap: </td>
    			<td width="30%" class="textLabel">
    			 <strong><bean:write name="frmBufferDetails" property="bufferFamilyCap"/>&nbsp;</strong>
    		     <html:hidden name="frmBufferDetails" property="bufferFamilyCap" />
    			</td>
    			<td width="20%" height="25" class="formLabel">HR/Insurer approved buffer: </td>
    			<td width="30%" class="textLabel">
    		       <strong><bean:write name="frmBufferDetails" property="hrInsurerBuffAmount"/>&nbsp;</strong>
    			   <html:hidden name="frmBufferDetails" property="hrInsurerBuffAmount" />
    			</td>
    			</tr>
    			
    			<tr>
    			<td width="20%" height="25" class="formLabel">Member Buffer amount. (Rs): </td>
    			<td width="30%" class="textLabel">
    				<strong><bean:write name="frmBufferDetails" property="memberBufferAmt"/>&nbsp;</strong>
    		            <html:hidden name="frmBufferDetails" property="memberBufferAmt" />
    			</td>
    			<td width="20%" height="25" class="formLabel">Utilized buffer amount: </td>
    			<td width="30%" class="textLabel">
    				<strong><bean:write name="frmBufferDetails" property="utilizedMemberBuffer"/>&nbsp;</strong>
    			  <html:hidden name="frmBufferDetails" property="utilizedMemberBuffer" />
    			</td>
    			</tr>
    	</table>
    </fieldset>
        <fieldset>
    	<legend>Buffer Details</legend>
    	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
    		<tr>
    			<td height="25">Buffer Request No.: </td>
    			<td class="textLabel"><strong><bean:write name="frmBufferDetails" property="bufferNbr"/></strong></td>
    			<td class="formLabel">&nbsp;</td>
    			<td class="textLabel">&nbsp;</td>
    		</tr>
    		<tr>
    			<td height="25">Administering Authority:</td>
    			<td class="textLabel"><strong><bean:write name="frmBufferDetails" property="adminAuthTypeID"/></strong></td>
    			<td class="formLabel">Balance Buffer Amt. (Rs):</td>
    			<td class="textLabel"><strong><bean:write name="frmBufferDetails" property="availBufferAmt"/></strong></td>
    		</tr>
    		<%--   <tr>
 			<td height="25">Buffer Remarks:</td>
    			<td class="textLabel"><strong><bean:write name="frmBufferDetails" property="bufferRemarks"/></strong></td>    			
    		</tr>--%>
    		<tr>
    			<td width="20%" class="formLabel">Requested Buffer Amt. (Rs): <span class="mandatorySymbol">*</span></td>
    			<td width="30%" class="textLabel">
    					<html:text property="requestedAmt" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=(viewmode || requesteddate)%>"/>
    			</td>
    			<td width="20%" height="25" class="formLabel">Requested Date / Time: </td>
    			<td class="textLabel">
    				<strong><bean:write name="frmBufferDetails" property="requestedDate"/>&nbsp;<bean:write name="frmBufferDetails" property="requestedTime"/>&nbsp;<bean:write name="frmBufferDetails" property="requestedDay"/></strong>
    			</td>
    		</tr>
    		<tr>
    			<td height="25" class="formLabel">Status:</td>
    			<td class="textLabel">
    			<logic:empty name="frmBufferDetails" property="approvedDate" >
        			<html:select property="statusTypeID" styleClass="selectBox selectBoxMedium" onchange="showhideApproved()" disabled="<%=(viewmode || bufferapproved )%>">
        				<html:option value="">Select from list</html:option>
						<html:optionsCollection name="bufferStatus" label="cacheDesc" value="cacheId" />
					</html:select>
				</logic:empty>
				<logic:notEmpty name="frmBufferDetails" property="approvedDate" >
        			<html:select property="statusTypeID" styleClass="selectBox selectBoxMedium" onchange="showhideApproved()" disabled="true">
        				<html:option value="">Select from list</html:option>
						<html:optionsCollection name="bufferStatus" label="cacheDesc" value="cacheId" />
					</html:select>
				</logic:notEmpty>
    			</td>
    			<td class="formLabel">&nbsp;</td>
    			<td class="textLabel">&nbsp;</td>
    		</tr>
	    		<logic:match name="frmBufferDetails" property="statusTypeID" value="BAP">
	    			<tr id="Approved" style="display">
	    		</logic:match>
	    		<logic:notMatch name="frmBufferDetails" property="statusTypeID" value="BAP">
					<tr id="Approved" style="display:none;">
				</logic:notMatch>
		    			<td height="25" class="formLabel">Approved Buffer  Amt. (Rs): <span class="mandatorySymbol">*</span></td>
		    			<td class="textLabel">
	    					<html:text property="approvedAmt" styleClass="textBox textBoxSmall" maxlength="13" disabled="<%=(viewmode || approveddate)%>"/>
		    			</td>
		    			<td class="formLabel">Approved Date / Time:</td>
		    			<td class="textLabel">
		    				<strong><bean:write name="frmBufferDetails" property="approvedDate"/>&nbsp;<bean:write name="frmBufferDetails" property="approvedTime"/>&nbsp;<bean:write name="frmBufferDetails" property="approvedDay"/></strong>
		    			</td>
	    			</tr>
	    		<logic:match name="frmBufferDetails" property="statusTypeID" value="BAP">
	    			<tr id="Approved1" style="display">
	    		</logic:match>
	    		<logic:notMatch name="frmBufferDetails" property="statusTypeID" value="BAP">
					<tr id="Approved1" style="display:none;">
				</logic:notMatch>
						<td height="25" class="formLabel">Approved By: <span class="mandatorySymbol">*</span></td>
						<td>
   							<html:text property="approvedBy" onkeypress="ConvertToUpperCase(event.srcElement);" styleClass="textBox textBoxLarge" maxlength="60"  disabled="<%=(viewmode || approveddate)%>" />
						</td>
					</tr>
				<logic:match name="frmBufferDetails" property="statusTypeID" value="BRJ">
	    			<tr id="Rejected" style="display">
	    		</logic:match>
	    		<logic:notMatch name="frmBufferDetails" property="statusTypeID" value="BRJ">
					<tr id="Rejected" style="display:none;">
				</logic:notMatch>
						<td height="25" class="formLabel">Rejected By: <span class="mandatorySymbol">*</span></td>
						<td>
	    					<html:text property="rejectedBy"  onkeypress="ConvertToUpperCase(event.srcElement);" styleClass="textBox textBoxLarge" maxlength="60"  disabled="<%=(viewmode || approveddate)%>"/>
						</td>
						<td class="formLabel">Rejected Date / Time:</td>
		    			<td class="textLabel">
		    				<strong><bean:write name="frmBufferDetails" property="approvedDate"/>&nbsp;<bean:write name="frmBufferDetails" property="approvedTime"/>&nbsp;<bean:write name="frmBufferDetails" property="approvedDay"/></strong>
		    			</td>
					</tr>
					<logic:match name="frmBufferDetails" property="HrAppYN" value="Y">
					<logic:match name="frmBufferDetails" property="statusTypeID" value="BAP">
					  <tr id="fileId" style="display: ">
			               <td height="25" class="formLabel">File Name<span class="mandatorySymbol">*</span></td>
			               <td nowrap><html:file property="file" styleId="file" disabled="<%=(viewmode || approveddate)%>"/>
			                
			               </td>
			               <td class="formLabel">&nbsp;</td>
    			           <td class="textLabel">&nbsp;</td>
		              </tr>
		          	</logic:match>
		          	<logic:notMatch name="frmBufferDetails" property="statusTypeID" value="BAP">
					   <tr id="fileId" style="display: none">
			               <td height="25" class="formLabel">File Name<span class="mandatorySymbol">*</span></td>
			               <td nowrap><html:file property="file" styleId="file" disabled="<%=(viewmode || approveddate)%>" /></td>
			               <td class="formLabel">&nbsp;</td>
    			           <td class="textLabel">&nbsp;</td>
		              </tr>
		          	</logic:notMatch>
		  			</logic:match>
		  		<logic:notMatch  name="frmBufferDetails" property="HrAppYN" value="Y">
		  		    <tr id="fileId" style="display: none">
			               <td height="25" class="formLabel">File Name<span class="mandatorySymbol">*</span></td>
			               <td nowrap><html:file property="file" styleId="file" disabled="<%=(viewmode || approveddate)%>"/>
			               </td>
		  	              
			               <td class="formLabel">&nbsp;</td>
    			           <td class="textLabel">&nbsp;</td>
		              </tr>
		  		</logic:notMatch>
		  		<tr>
		  		   <td>
		  		   <logic:notEmpty name="frmBufferDetails" property="fileExistYN">
			               <a href="#" onclick="onDocumentViewerHyundaiBuffer('<bean:write name= "frmBufferDetails" property="fileName" />');">View Document</a>
		  	        </logic:notEmpty></td>
		  	        <td></td>
		  	         <td></td>
		  	          <td></td>
		  		</tr>
		  		
		<tr>
                       <td class="formLabel" height="25">Buffer Remarks: </td>
                          <td colspan="3" nowrap="nowrap">
                          <html:textarea  property="bufferRemarks" name="frmBufferDetails" styleClass="textBox textAreaLong" readonly="true"/>
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
	    			<logic:empty name="frmBufferDetails" property="requestedDate" >
    					<button type="button" name="Button4" id="send" accesskey="e" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSendRequest();">S<u>e</u>nd Request</button>&nbsp;
    				</logic:empty>

					<logic:empty name="frmBufferDetails" property="approvedDate" >
		    			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave</button>&nbsp;
	        			<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
		    		</logic:empty>
		    	<%
		  		}//end of checking for Edit permission
		  		%>
	    			<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose();"><u>C</u>lose</button>&nbsp;
	    		</td>
	    	</tr>
	    </table>
	<!-- E N D : Buttons -->
    </div>
    <INPUT TYPE="hidden" NAME="rownum" VALUE='<%=(TTKCommon.checkNull(request.getParameter("rownum")))%>'>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="fileExistYN" VALUE="<bean:write name="frmBufferDetails" property="fileName"/>">
    <INPUT TYPE="hidden" NAME="patclmClaimType" VALUE="">
	<INPUT TYPE="hidden" NAME="patclmBufferType" VALUE="">
	<INPUT TYPE="hidden"  id="hrapproval"  NAME="hrapproval" VALUE="<bean:write name="frmBufferDetails" property="HrAppYN"/>">
	<html:hidden property="availBufferAmt"/>
	<html:hidden property="requestedDate"/>

	<html:hidden property="requestedDate"/>
	<logic:notEmpty name="frmBufferDetails" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</html:form>
<!-- E N D : Content/Form Area -->