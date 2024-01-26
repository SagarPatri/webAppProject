<%
/**
 * @ (#) batchpolicylist.jsp 01st Feb 2006
 * Project      : TTK HealthCare Services
 * File         : batchpolicylist.jsp
 * Author       : Lancy A
 * Company      : Span Systems Corporation
 * Date Created : 01st Feb 2006
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

<%@ page import=" com.ttk.common.TTKCommon" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/inwardentry/batchpolicylist.js"></script>
<SCRIPT LANGUAGE="JavaScript">
var TC_Disabled = true; //to avoid the alert message on change of form elements
</SCRIPT>

<!-- S T A R T : Content/Form Area -->
	<html:form action="/BatchPolicyAction.do"  method="post" enctype="multipart/form-data">

	<!-- S T A R T : Page Title -->
		<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><bean:write name="frmBatchPolicy" property="caption"/></td>
			</tr>
		</table>
	<!-- E N D : Page Title -->

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



	<!-- S T A R T : Grid -->
		<ttk:HtmlGrid name="batchPolicyData"/>
	<!-- E N D : Grid -->

	<!-- S T A R T : Buttons and Page Counter -->
		<table align="center" class="buttonsContainerGrid"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="27%" align="left">
				
				 
			       <logic:equal name="frmBatch" property="batchType" value="END">
			        
			        
			       <a href="#" onclick="downloadBulkPolicyLog();" >DownLoadLog</a>&nbsp;
			       <logic:match name="frmBatchPolicy" property="inwardCompletedYN" value="N">
								<html:file property="policyFile"/>
								<button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:uploadPolicies()">Upload File</button>&nbsp;
					&nbsp;<a href="#" onclick="downloadSampleFormat();" >DownLoadSampleFormat</a>
					</logic:match>
					</logic:equal>
				</td>
		        <td width="73%" nowrap align="right">
			      
			       	<%
			       		if(TTKCommon.isAuthorized(request,"Add"))
			       		{
			       	%>
							<logic:match name="frmBatchPolicy" property="inwardCompletedYN" value="N">
								<logic:match name="frmBatchPolicy" property="addFlag" value="Y">
  								    <button type="button" name="Button" accesskey="a" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onAddPolicy()"><u>A</u>dd Policy</button>&nbsp;
		    			   		</logic:match>
							</logic:match>
					<%
						}//end of if(TTKCommon.isAuthorized(request,"Add"))
			       		if(TTKCommon.isDataFound(request,"batchPolicyData") && TTKCommon.isAuthorized(request,"InwardEntryCompleted"))
			       		{
			       	%>
							<logic:match name="frmBatchPolicy" property="inwardCompletedYN" value="N">
								<button type="button" name="Button" accesskey="i" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBatchComplete('INW')"><u>I</u>nward Entry Complete</button>&nbsp;
				        	</logic:match>
			        <%
			        	}//end of if(TTKCommon.isDataFound(request,"batchPolicyData") && TTKCommon.isAuthorized(request,"Batch Complete"))

			        	 if(TTKCommon.isDataFound(request,"batchPolicyData") && TTKCommon.isAuthorized(request,"BatchCompleted"))
			       		{
			       	%>
							<logic:match name="frmBatchPolicy" property="batchCompletedYN" value="N">
								<button type="button" name="Button" accesskey="b" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onBatchComplete('BAT')"><u>B</u>atch Complete</button>&nbsp;
				        	</logic:match>
			        <%
			        	}
			        	if(TTKCommon.isDataFound(request,"batchPolicyData") && TTKCommon.isAuthorized(request,"Delete"))
						{
					%>
							<logic:match name="frmBatchPolicy" property="inwardCompletedYN" value="N">
								<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete()"><u>D</u>elete</button>&nbsp;
			        		</logic:match>
			        <%
			        	}// end of if(TTKCommon.isDataFound(request,"batchPolicyData") && TTKCommon.isAuthorized(request,"Delete"))
			        %>
        		</td>
			</tr>
			<ttk:PageLinks name="batchPolicyData"/>
		</table>
	<!-- E N D : Buttons and Page Counter -->
</div>
	<input type="hidden" name="child" value="">
	<INPUT TYPE="hidden" NAME="rownum" VALUE=''>
	<INPUT TYPE="hidden" NAME="mode" VALUE="">
	<INPUT TYPE="hidden" NAME="sortId" VALUE="">
	<INPUT TYPE="hidden" NAME="pageId" VALUE="">
	<INPUT TYPE="hidden" NAME="batchType" VALUE="">

	</html:form>
<!-- E N D : Content/Form Area -->