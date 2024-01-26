<%
/** @ (#) querydetails.jsp 20th Oct 2008
 * Project     : TTK Healthcare Services
 * File        : querydetails.jsp
 * Author      : Chandrasekaran J
 * Company     : Span Systems Corporation
 * Date Created: 20th Oct 2008
 *
 * @author 		 : Chandrasekaran J
 * Modified by   : Manikanta Kumar G G
 * Modified date : 
 * Reason        :
 *
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.dto.usermanagement.UserSecurityProfile" %>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/onlineforms/querydetails.js"></script>
<%
UserSecurityProfile userSecurityProfile=(UserSecurityProfile)request.getSession().getAttribute("UserSecurityProfile");
	boolean viewmode=true;
	if(TTKCommon.isAuthorized(request,"Edit"))
    {
    	viewmode=false;
    }//end of if(TTKCommon.isAuthorized(request,"Edit"))
	pageContext.setAttribute("reqRelated",Cache.getCacheObject("reqRelated"));
    pageContext.setAttribute("onlineFeedbackType",Cache.getCacheObject("onlineFeedbackType"));
    pageContext.setAttribute("onlineFeedbackStatus",Cache.getCacheObject("onlineFeedbackStatus"));
%>

<html:form action="/QueryDetailsAction.do">
	<!-- S T A R T : Page Title -->
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
        	<tr>
            	<td>Query Details<bean:write name="frmOnlineQueryDetails" property="caption"/></td>
          	</tr>
        </table>
	<!-- E N D : Page Title -->
	<div class="contentArea" id="contentArea">
	<html:errors/>
	<!-- S T A R T : Success Box -->
	<logic:notEmpty name="updated" scope="request">
		<table align="center" class="successContainer" style="display:" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td><img src="/ttk/images/SuccessIcon.gif" alt="Success" title="Success" width="16" height="16" align="absmiddle">&nbsp;
					<bean:message name="updated" scope="request"/>
				</td>
			</tr>
		</table>
	</logic:notEmpty>
	<!-- E N D : Success Box -->
		<fieldset>
        <legend>Employee  Information</legend>
        	<table align="center" class="formContainerWeblogin" border="0" cellspacing="0" cellpadding="0">
	            <tr>
	            	<td width="17%" class="formLabelWeblogin">Request ID: </td>
	            	<td width="37%" class="textLabelBold"><bean:write name="frmOnlineQueryDetails" property="requestID"/></td>
	              	<td class="formLabelWeblogin">Email ID: <span class="mandatorySymbol">*</span></td>
	              	<td>
	              		<html:text property="emailID" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="60" disabled="<%=viewmode%>"/>
	              	</td>
	            </tr>
	            <tr>
	            	<td class="formLabelWeblogin">Mobile No.:</td>
	              	<td>
	              		<html:text property="mobileNbr" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="15" disabled="<%=viewmode%>"/>
	              	</td>
	              	<td class="formLabelWeblogin">Office Phone No.:</td>
	              	<td>
	              		<html:text property="phoneNbr" styleClass="textBoxWeblogin textBoxMediumWeblogin"  maxlength="25" disabled="<%=viewmode%>"/>
	              	</td>
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
        <table align="center" class="formContainerWeblogin" border="0" cellspacing="0" cellpadding="0">
        <tr>
        <td width="15%" class="formLabelWeblogin">Request Type: <span class="mandatorySymbol">*</span></td>
        <logic:empty name="frmOnlineQueryDetails" property="onlineQueryVO.latestReqDate">
	    <td colspan="3">
	    <html:select property="onlineQueryVO.queryGnlTypeID"  styleClass="selectBoxWeblogin selectBoxMediumWeblogin" style="width:220px; margin-top:6px;" disabled="<%=viewmode%>">
		<html:option value="">Select from list</html:option>
		<html:options collection="reqRelated"  property="cacheId" labelProperty="cacheDesc"/>
	    </html:select>
        </td>
        </logic:empty>
        <logic:notEmpty name="frmOnlineQueryDetails" property="onlineQueryVO.latestReqDate">
	    <td colspan="3" disabled="true">
	    <html:select property="onlineQueryVO.queryGnlTypeID"  styleClass="selectBoxWeblogin selectBoxMediumWeblogin" style="width:220px; margin-top:6px;" disabled="true">
		<html:option value="">Select from list</html:option>
		<html:options collection="reqRelated"  property="cacheId" labelProperty="cacheDesc"/>
	    </html:select>
        </td>
        </logic:notEmpty>
        </tr>
        <tr>
              <td class="formLabelWeblogin" valign="top">Questions: <span class="mandatorySymbol">*</span></td>
              <logic:empty name="frmOnlineQueryDetails" property="onlineQueryVO.latestReqDate">
              <td colspan="3">
              	<html:textarea property="onlineQueryVO.queryDesc" styleClass="textBoxWeblogin textAreaLongHt" />
              </td>
              </logic:empty>
              <logic:notEmpty name="frmOnlineQueryDetails" property="onlineQueryVO.latestReqDate">
              <td colspan="3" disabled="true">
              	<html:textarea property="onlineQueryVO.queryDesc" styleClass="textBoxWeblogin textAreaLongHt" disabled="true"/>
              </td>
              </logic:notEmpty>
              <td valign="top" >
              <a href="#" onclick="javascript:onAddNewQuery()"><img src="/ttk/images/AddIconBig.gif" alt="Add new query" title="Add new query" border="0"></a>
              </td>
            </tr>
	    	<tr>			
              <td width="15%" class="formLabelWeblogin">Status:</td>
              <td colspan="3"><bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.status"/></td>
            </tr>
            <tr>
              <td width="15%" class="formLabelWeblogin">Submit: </td>
              <td colspan="3">
             <logic:notEmpty name="frmOnlineQueryDetails" property="onlineQueryVO.latestReqDate">
              <html:checkbox property="onlineQueryVO.submittedYN"  style="margin-top:3px;" value="Y" disabled="true"  />&nbsp;&nbsp;<strong>(Click Submit for Vidal Health to process)</strong>
              </logic:notEmpty>
             <logic:empty name="frmOnlineQueryDetails" property="onlineQueryVO.latestReqDate">
              <html:checkbox property="onlineQueryVO.submittedYN"  style="margin-top:3px;" value="Y"  />&nbsp;&nbsp;<strong>(Click Submit for Vidal Health to process)</strong>
              </logic:empty>
              </td>
            </tr>
          </table>
      </fieldset>
      <fieldset>
          <legend>Vidal Health Comments</legend>
            <table align="center" class="formContainerWeblogin" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td width="17%" class="formLabelWeblogin">Responded Date / Time:</td>
              <td width="37%" class="textLabelBold"><bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.respondedDate"/>&nbsp;<bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.respondedTime"/>&nbsp;<bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.respondedDay"/></td>
              <td width="17%" class="formLabelWeblogin">&nbsp;</td>
              <td width="29%">&nbsp;</td>
            </tr>
            <tr>
            <logic:match name="frmOnlineQueryDetails" property="onlineQueryVO.status" value="Submitted to TTK">
              <td class="formLabelWeblogin labelTopBotPadding">Vidal Health Remarks:</td>
              <td colspan="3"></td>
              </logic:match>
              <logic:notMatch name="frmOnlineQueryDetails" property="onlineQueryVO.status" value="Submitted to TTK">
              <td class="formLabelWeblogin labelTopBotPadding">Vidal Health Remarks:</td>
              <td colspan="3"><bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.TTKRemarks"/></td>
              </logic:notMatch>
            </tr>
          </table>
      </fieldset>
      <%
		if(userSecurityProfile.getOnlineRatingTypeID().equals("ORA")) 
		{
	  %>
	  <logic:match  name="frmOnlineQueryDetails" property="onlineQueryVO.status" value="TTK Responded">
      <fieldset>
      	<legend>Employee FeedBack</legend>   	
      	<table align="center" class="formContainerWeblogin" border="0" cellspacing="0" cellpadding="0">
      	<logic:empty  name="frmOnlineQueryDetails" property="onlineQueryVO.feedbackSubmitDate" >
      	<tr>
      		<td width="15%" class="formLabelWeblogin">FeedBack Type: <span class="mandatorySymbol">*</span></td>
      		<td colspan="3">
	    		<html:select property="onlineQueryVO.queryFeedbackTypeID" styleId="queryFeedbackTypeID" styleClass="selectBoxWeblogin selectBoxMediumWeblogin" style="width:220px; margin-top:6px;" disabled="false" onchange="onFeedBackChange();">
					<html:option value="">Select from list</html:option>
					<html:options collection="onlineFeedbackType"  property="cacheId" labelProperty="cacheDesc"/>
	   			</html:select>
       		</td>
      	</tr>
      	</logic:empty>
      	<logic:notEmpty  name="frmOnlineQueryDetails" property="onlineQueryVO.feedbackSubmitDate" >
      	<tr>
      		<td width="15%" class="formLabelWeblogin">FeedBack Type: <span class="mandatorySymbol">*</span></td>
      		<td colspan="3">
	    		<html:select property="onlineQueryVO.queryFeedbackTypeID" styleId="queryFeedbackTypeID" styleClass="selectBoxWeblogin selectBoxMediumWeblogin" style="width:220px; margin-top:6px;" disabled="true" onchange="onFeedBackChange();">
					<html:option value="">Select from list</html:option>
					<html:options collection="onlineFeedbackType"  property="cacheId" labelProperty="cacheDesc"/>
	   			</html:select>
       		</td>
      	</tr>
      	</logic:notEmpty>
      	<logic:match  name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackTypeID" value="OFU">
      	<tr id="remarks" style="display:">
          <td class="formLabelWeblogin labelTopBotPadding">Remarks: <span class="mandatorySymbol">*</span></td>
          <logic:notEmpty  name="frmOnlineQueryDetails" property="onlineQueryVO.feedbackSubmitDate" >
          <td colspan="3">
              	<html:textarea property="onlineQueryVO.queryRemarksDesc" styleClass="textBoxWeblogin textAreaLongHt"  disabled="true" />
          </td>
          </logic:notEmpty>
          <logic:empty  name="frmOnlineQueryDetails" property="onlineQueryVO.feedbackSubmitDate" >
          <td colspan="3">
              	<html:textarea property="onlineQueryVO.queryRemarksDesc" styleClass="textBoxWeblogin textAreaLongHt" disabled="false" />
          </td>
          </logic:empty>
         </tr>
         </logic:match>
         <logic:notMatch  name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackTypeID" value="OFU">
      	 <tr id="remarks" style="display:none;">
          <td class="formLabelWeblogin labelTopBotPadding">Remarks: <span class="mandatorySymbol">*</span></td>
          <td colspan="3">
              	<html:textarea property="onlineQueryVO.queryRemarksDesc" styleClass="textBoxWeblogin textAreaLongHt" />
          </td>
         </tr>
         </logic:notMatch>
         <logic:match  name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackTypeID" value="OFU">
         <tr id="status" style="display:">			
           <td width="15%" class="formLabelWeblogin">Status:</td>
           <td colspan="3"><bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.feedbackStatus"/></td>
         </tr>
         </logic:match>
         <logic:notMatch  name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackTypeID" value="OFU">
         <tr id="status" style="display:none;">			
           <td width="15%" class="formLabelWeblogin">Status:</td>
           <td colspan="3"><bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.feedbackStatus"/></td>
         </tr>
         </logic:notMatch>
         <logic:match  name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackTypeID" value="OFU">
         <tr id="submit" style="display:">
           <td width="15%" class="formLabelWeblogin">Submit: </td>
           <logic:notEmpty  name="frmOnlineQueryDetails" property="onlineQueryVO.feedbackSubmitDate" >
           <td colspan="3">
           <html:checkbox property="onlineQueryVO.feedBackSubmittedYN"  style="margin-top:3px;" value="Y" disabled="true"/>&nbsp;&nbsp;<strong>(Click Submit for Vidal Health to view the FeedBack)</strong>
           </td>
           </logic:notEmpty>
           <logic:empty  name="frmOnlineQueryDetails" property="onlineQueryVO.feedbackSubmitDate" >
           <td colspan="3">
           <input name="onlineQueryVO.feedBackSubmittedYN" type="checkbox" value="Y"  >&nbsp;&nbsp;<strong>(Click Submit for Vidal Health to view the FeedBack)</strong>
           </td>
           </logic:empty>
         </tr>
         </logic:match>
         <logic:notMatch  name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackTypeID" value="OFU">
         <tr id="submit" style="display:none;">
           <td width="15%" class="formLabelWeblogin">Submit: </td>
           <td colspan="3">
           <!--<html:checkbox property="onlineQueryVO.feedBackSubmittedYN"  style="margin-top:3px;" value="Y" />&nbsp;&nbsp;<strong>(Click Submit for Vidal Health TPA to process)</strong>
           --><input name="onlineQueryVO.feedBackSubmittedYN" type="checkbox" value="Y" checked >&nbsp;&nbsp;<strong>(Click Submit for Vidal Health to view the FeedBack)</strong>
           </td>
         </tr>
         </logic:notMatch>
      	</table>
      </fieldset>
      </logic:match>
      <logic:match  name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackTypeID" value="OFU">
      <fieldset id="feedbackresp" style="display:">
      	<legend>Vidal Health FeedBack Response</legend>
      	<table align="center" class="formContainerWeblogin" border="0" cellspacing="0" cellpadding="0">
      	 <tr>
            <td width="17%" class="formLabelWeblogin">Responded Date / Time:</td>
            <td width="37%" class="textLabelBold"><bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.clarifiedDate"/>&nbsp;<bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.clarifiedTime"/>&nbsp;<bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.clarifiedDay"/></td>
            <td width="17%" class="formLabelWeblogin">&nbsp;</td>
            <td width="29%">&nbsp;</td>
         </tr>
       	 <tr>			
          <td width="15%" class="formLabelWeblogin">Remarks:</td>
          <logic:notEmpty name="frmOnlineQueryDetails" property="onlineQueryVO.clarifiedDate">
           		<td colspan="3"><bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.ttkfeedBackRemarks"/></td>
          </logic:notEmpty>
         </tr>
        </table>
      </fieldset>
      </logic:match>
      <logic:notMatch  name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackTypeID" value="OFU">
      <fieldset id="feedbackresp" style="display:none;">
      	<legend>Vidal Health FeedBack Response</legend>
      	<table align="center" class="formContainerWeblogin" border="0" cellspacing="0" cellpadding="0">
      	 <tr>
            <td width="17%" class="formLabelWeblogin">Responded Date / Time:</td>
            <td width="37%" class="textLabelBold"><bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.clarifiedDate"/>&nbsp;<bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.clarifiedTime"/>&nbsp;<bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.clarifiedDay"/></td>
            <td width="17%" class="formLabelWeblogin">&nbsp;</td>
            <td width="29%">&nbsp;</td>
         </tr>
       	 <tr>			
           <td width="15%" class="formLabelWeblogin">Remarks:</td>
           <logic:notEmpty name="frmOnlineQueryDetails" property="onlineQueryVO.clarifiedDate">
           	<td colspan="3"><bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.ttkfeedBackRemarks"/></td>
           </logic:notEmpty>
         </tr>
        </table>
      </fieldset>
      </logic:notMatch>
      <logic:notEmpty  name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackTypeID">
      <fieldset>
      <legend>Employee FeedBack Response</legend>
      	<table align="center" class="formContainerWeblogin" border="0" cellspacing="0" cellpadding="0">
      	 <tr>
      	   <td width="15%" class="formLabelWeblogin">FeedBack Status:</td>
      	   <logic:notMatch name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackStatusID" value="FSC">
      	   <logic:match name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackTypeID" value="OFU">
      	   <logic:notEmpty name="frmOnlineQueryDetails" property="onlineQueryVO.clarifiedDate">
      	   <td colspan="3">
	    		<html:select property="onlineQueryVO.queryFeedbackStatusID"  styleClass="selectBoxWeblogin selectBoxMediumWeblogin" style="width:100px; margin-top:6px;" disabled="<%=viewmode%>">
					<html:options collection="onlineFeedbackStatus"  property="cacheId" labelProperty="cacheDesc"/>
	   			</html:select>
       		</td>
       		</logic:notEmpty>
       		</logic:match>
       		</logic:notMatch>
       		<logic:match name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackStatusID" value="FSC">
       			<td colspan="3"><bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackStatusDesc"/></td>
       		</logic:match>
       		<logic:empty name="frmOnlineQueryDetails" property="onlineQueryVO.clarifiedDate">
       		<logic:match name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackStatusID" value="FSO">
		       	<td colspan="3"><bean:write name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackStatusDesc"/></td>
       		</logic:match>
       		</logic:empty>
      	 </tr>
       </table>
      </fieldset>
      </logic:notEmpty>
     <%
		}//end of if(userSecurityProfile.getOnlineRatingTypeID().equals("ORA"))
     %>
      <!-- E N D : Form Fields -->
	<!-- S T A R T : Buttons -->
  	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
  		<tr>
  			<td width="100%" align="center">
  			<%
		    	if(TTKCommon.isAuthorized(request,"Edit"))
    			{
    		%>	
    		<logic:empty name="frmOnlineQueryDetails" property="onlineQueryVO.latestReqDate">
    			<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
    		</logic:empty>	
    		<%
				if(userSecurityProfile.getOnlineRatingTypeID().equals("ORA")) 
				{
	        %>
	        <logic:notEmpty name="frmOnlineQueryDetails" property="onlineQueryVO.respondedDate"> 
	           <logic:match name="frmOnlineQueryDetails" property="onlineQueryVO.feedbackStatus" value="Yet to be Submitted">
	               <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
	           </logic:match>
	        </logic:notEmpty>
	        <logic:notEmpty name="frmOnlineQueryDetails" property="onlineQueryVO.clarifiedDate">
	           <logic:match name="frmOnlineQueryDetails" property="onlineQueryVO.queryFeedbackStatusDesc" value="Open">
	               <button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave()"><u>S</u>ave</button>&nbsp;
	           </logic:match>
	        </logic:notEmpty>
	        <%
			   }//end of if(userSecurityProfile.getOnlineRatingTypeID().equals("ORA")) 
  			%>
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
  	<INPUT TYPE="hidden" NAME="onlineQueryVO.submittedYN" value="">
  	<html:hidden property="onlineQueryVO.TTKRemarks"/>
  	<html:hidden property="queryHdrSeqId"/>
  	<html:hidden property="onlineQueryVO.queryFeedbackTypeID"/>
  	<html:hidden property="onlineQueryVO.respondedDate"/>
  	<logic:notEmpty name="frmOnlineQueryDetails" property="frmChanged">
		<script> ClientReset=false;TC_PageDataChanged=true;</script>
	</logic:notEmpty>
</html:form>