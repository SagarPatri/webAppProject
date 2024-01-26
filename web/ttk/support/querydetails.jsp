<%
/** @ (#) querydetails.jsp 20th Oct 2008
 * Project     : TTK Healthcare Services
 * File        : querydetails.jsp
 * Author      : Balakrishna E
 * Company     : Span Systems Corporation
 * Date Created: 20th Oct 2008
 *
 * @author 		 : Balakrishna E
 * Modified by   : Manikanta Kumar G G
 * Modified date : 17th Dec 2010
 * Reason        : Online Rating System
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/support/querydetails.js"></script>

<html:form action="/EditOnlineAssistanceAction.do">
	<!-- S T A R T : Page Title -->
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
        	<tr>
            	<td>Query Details - <bean:write name="frmSupportQueryDetails" property="caption" /></td>
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
		<fieldset>
        <legend>Employee  Information</legend>
        	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	            	<td width="17%" class="formLabel">Request ID: </td>
	            	<td width="37%" class="textLabelBold"><bean:write name="frmSupportQueryDetails" property="requestID" /></td>
	              	<td width="17%" class="formLabel">Email ID: <span class="mandatorySymbol">*</span></td>
					<td width="33%" class="textLabelBold"><bean:write name="frmSupportQueryDetails" property="emailID"/></td>
	            </tr>
	            <tr>
	            	<td width="17%" class="formLabel">Mobile No.:</td>
	            	<td width="37%" class="textLabelBold"><bean:write name="frmSupportQueryDetails" property="mobileNbr"/></td>
	              	<td width="17%" class="formLabel">Office Phone No.:</td>
	              	<td width="33%" class="textLabelBold"><bean:write name="frmSupportQueryDetails" property="phoneNbr"/></td>
	            </tr>
        	</table>
        </fieldset>
        <fieldset>
        <legend>Query List</legend>
			<div class="scrollableGrid" style="height:158px; margin:0 auto; width:98%;">
	        	<ttk:HtmlGrid name="tableDataQueryList" className="gridWithCheckBox zeroMargin"/>
			</div>
        </fieldset>
        <fieldset>
        <legend>Query Information</legend>
        <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
        <tr>
        <td width="17%" class="formLabel" valign="top">Request Type: </td>
	      <td colspan="3">
	      <bean:write name="frmSupportQueryDetails" property="onlineQueryVO.queryTypeDesc" />
              </td>
         </tr>
        <tr>
              <td width="17%" class="formLabel" valign="top">Questions: </td>
              <td colspan="3">
              <bean:write name="frmSupportQueryDetails" property="onlineQueryVO.queryDesc" />
              	<!-- <html:textarea property="onlineQueryVO.queryDesc" styleClass="textBox textAreaLongHt"/> -->
              </td>              
            </tr>
		<tr>
              <td width="17%" class="formLabel" valign="top">Status:</td>
              <td colspan="3"><bean:write name="frmSupportQueryDetails" property="onlineQueryVO.status"/></td>
            </tr>
            <tr>
              <td width="17%" class="formLabel" valign="top">Submit: </td>
              <td colspan="3">
              <logic:empty name="frmSupportQueryDetails" property="onlineQueryVO.status">
              		<html:checkbox property="onlineQueryVO.submittedYN" value="N" disabled="true" />&nbsp;&nbsp;<strong>(Click Submit for Vidal Health to process)</strong>
              </logic:empty>
              <logic:notEmpty name="frmSupportQueryDetails" property="onlineQueryVO.status">
	         		<html:checkbox property="onlineQueryVO.submittedYN" value="Y" disabled="true" />&nbsp;&nbsp;<strong>(Click Submit for Vidal Health to process)</strong>
              </logic:notEmpty>
              </td>
            </tr>
          </table>
      </fieldset>
      <fieldset>
          <legend>Vidal Health Comments</legend>
            <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
            <logic:match name="frmSupportQueryDetails" property="onlineQueryVO.status" value="TTK Responded">
            <tr>
              <td width="17%" class="formLabel" valign="top">Vidal Health Remarks: <span class="mandatorySymbol">*</span></td>
              <td colspan="3">
              <html:textarea property="onlineQueryVO.TTKRemarks" styleClass="textBox textAreaLongHt" disabled="true"/>
              </td>
            </tr>
			</logic:match>
			<logic:notMatch name="frmSupportQueryDetails" property="onlineQueryVO.status" value="TTK Responded">
			<tr>
              <td width="17%" class="formLabel" valign="top">Vidal Health Remarks: <span class="mandatorySymbol">*</span></td>
              <td colspan="3">
              <html:textarea property="onlineQueryVO.TTKRemarks" styleClass="textBox textAreaLongHt"/>
              </td>
            </tr>
			</logic:notMatch>
            <tr>
			  <td width="17%">Submit: </td>
			  <td width="37%">
			  <logic:match name="frmSupportQueryDetails" property="onlineQueryVO.supportEditYN" value="N">
			   <input name="onlineQueryVO.ttkSubmittedYN" type="checkbox" value="Y" checked disabled="disabled">&nbsp;&nbsp;<strong>(Click Submit for Employee to view)</strong>
			   </logic:match>
			   <logic:match name="frmSupportQueryDetails" property="onlineQueryVO.supportEditYN" value="Y">
			    <input name="onlineQueryVO.ttkSubmittedYN" type="checkbox" value="Y" >&nbsp;&nbsp;<strong>(Click Submit for Employee to view)</strong>
			   </logic:match>
			</td>
		      <td width="17%" class="formLabel">Responded Date / Time:</td>
		      <td width="33%" class="textLabelBold"><bean:write name="frmSupportQueryDetails" property="onlineQueryVO.respondedDate"/>&nbsp;<bean:write name="frmSupportQueryDetails" property="onlineQueryVO.respondedTime"/>&nbsp;<bean:write name="frmSupportQueryDetails" property="onlineQueryVO.respondedDay"/></td>
            </tr>
          </table>
      </fieldset>
      <logic:match name="frmSupportQueryDetails" property="feedbackAllowedYN" value="ORA">
      <logic:match name="frmSupportQueryDetails" property="onlineQueryVO.status" value="TTK Responded">
      <logic:notMatch name="frmSupportQueryDetails" property="onlineQueryVO.feedbackStatus" value="Yet to be Submitted">
      <logic:match name="frmSupportQueryDetails" property="onlineQueryVO.feedBackDesc" value="Unsatisfactory">
      <fieldset>
      	<legend>Employee FeedBack</legend>
      	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      	  <tr>
           <td width="17%" class="formLabel" valign="top">FeedBack Type: </td>
	       <td colspan="3">
	        <bean:write name="frmSupportQueryDetails" property="onlineQueryVO.feedBackDesc" />
           </td>
          </tr>
          <tr>
            <td width="17%" class="formLabel" valign="top">Remarks: </td>
            <td colspan="3">
             <bean:write name="frmSupportQueryDetails" property="onlineQueryVO.queryRemarksDesc" />
            </td>              
          </tr>
		  <tr>
            <td width="17%" class="formLabel" valign="top">Status:</td>
            <td colspan="3"><bean:write name="frmSupportQueryDetails" property="onlineQueryVO.feedbackStatus"/></td>
          </tr>
          <tr>
            <td width="17%" class="formLabel" valign="top">Submit: </td>
            <td colspan="3">
            	<html:checkbox property="onlineQueryVO.feedBackSubmittedYN" value="Y" disabled="true" />&nbsp;&nbsp;<strong>(Click Submit for Vidal Health to process)</strong>
            </td>
          </tr>
         </table>
      </fieldset>
      </logic:match>
      </logic:notMatch>
      </logic:match>
      </logic:match>
      <logic:match name="frmSupportQueryDetails" property="feedbackAllowedYN" value="ORA">
      <logic:match name="frmSupportQueryDetails" property="onlineQueryVO.status" value="TTK Responded">
      <logic:notMatch name="frmSupportQueryDetails" property="onlineQueryVO.feedBackDesc" value="Unsatisfactory">
      <fieldset>
      <legend>Employee FeedBack</legend>
       <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
      	  <tr>
           <td width="17%" class="formLabel" valign="top">FeedBack Type: </td>
	       <td colspan="3">
	        <bean:write name="frmSupportQueryDetails" property="onlineQueryVO.feedBackDesc" />
           </td>
          </tr>
       </table>
       </fieldset>
      </logic:notMatch>
      </logic:match>
      </logic:match>
      <logic:match name="frmSupportQueryDetails" property="feedbackAllowedYN" value="ORA">
      <logic:match name="frmSupportQueryDetails" property="onlineQueryVO.status" value="TTK Responded">
      <logic:notMatch name="frmSupportQueryDetails" property="onlineQueryVO.feedbackStatus" value="Yet to be Submitted">
      <logic:match name="frmSupportQueryDetails" property="onlineQueryVO.feedBackDesc" value="Unsatisfactory">
      <fieldset>
      	<legend>Vidal Health FeedBack Response</legend>
      	   <table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="17%" class="formLabel" valign="top">Remarks: <span class="mandatorySymbol">*</span></td>
              <td colspan="3">
              	<html:textarea property="onlineQueryVO.ttkfeedBackRemarks" styleClass="textBox textAreaLongHt" />
              </td>
            </tr>
            <tr>
              <td width="17%" class="formLabel" valign="top">Submit: </td>
              <td width="37%">
              	<html:checkbox property="onlineQueryVO.ttkfeedBackSubmittedYN" value="Y" />&nbsp;&nbsp;<strong>(Click Submit for Employee to view)</strong>
              </td>
               <td width="17%" class="formLabel">Responded Date / Time:</td>
		       <td width="33%" class="textLabelBold"><bean:write name="frmSupportQueryDetails" property="onlineQueryVO.clarifiedDate"/>&nbsp;<bean:write name="frmSupportQueryDetails" property="onlineQueryVO.clarifiedTime"/>&nbsp;<bean:write name="frmSupportQueryDetails" property="onlineQueryVO.clarifiedDay"/></td>
            </tr>
           </table>
      </fieldset>
      </logic:match>
      </logic:notMatch>
      </logic:match>
      </logic:match>
      <logic:match name="frmSupportQueryDetails" property="feedbackAllowedYN" value="ORA">
      <logic:notEmpty name="frmSupportQueryDetails" property="onlineQueryVO.feedBackDesc">
      <fieldset>
      <legend>Employee FeedBack Response</legend>
      	<table align="center" class="formContainerWeblogin" border="0" cellspacing="0" cellpadding="0">
	      <tr>
      	    <td width="17%" class="formLabelWeblogin">FeedBack Status:</td>
       		<td colspan="3"><bean:write name="frmSupportQueryDetails" property="onlineQueryVO.queryFeedbackStatusDesc"/></td>
       	  </tr>
       	</table>
       	</fieldset>
      </logic:notEmpty>
      </logic:match>
      <!-- E N D : Form Fields -->
	<!-- S T A R T : Buttons -->
  	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td width="100%" align="center">
  			<%
		    	if(TTKCommon.isAuthorized(request,"Edit"))
    			{
    		%>	
    		<logic:match name="frmSupportQueryDetails" property="onlineQueryVO.status" value="Submitted to TTK">
    			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
    		</logic:match>
    		<logic:match name="frmSupportQueryDetails" property="onlineQueryVO.feedbackStatus" value="Feedback Submitted to TTK">
  				 <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
    		</logic:match>		
    			<button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset()"><u>R</u>eset</button>&nbsp;
  			<%
  				}//end of if(TTKCommon.isAuthorized(request,"Edit"))
  			%>		
  				<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onClose()"><u>C</u>lose</button>
  			</td>
  		</tr>
  	</table>
  	</div>
  	<INPUT TYPE="hidden" NAME="mode" value="">
  	<INPUT TYPE="hidden" NAME="rownum" VALUE="">
  	<INPUT TYPE="hidden" NAME="onlineQueryVO.submittedYN" value="Y">
  	<INPUT TYPE="hidden" NAME="tab" VALUE="">
  	<html:hidden property="queryHdrSeqId"/>
</html:form>