<%
/**
 * @ (#) edithospfeedback.jsp 21st Sep 2005
 * Project      : TTK HealthCare Services
 * File         : edithospfeedback.jsp
 * Author       : Chandrasekaran J
 * Company      : Span Systems Corporation
 * Date Created : 21st Sep 2005
 *
 * @author       :Chandrasekaran J
 * Modified by   :kishor kumar S H
 * Modified date :05 02-2015
 * Reason        :Feedback changes
 */
%>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ page import="com.ttk.common.TTKCommon,java.util.ArrayList, com.ttk.dto.empanelment.FeedbackDetailVO, com.ttk.dto.common.CacheObject" %>
<%
	CacheObject cacheObject=null;
	FeedbackDetailVO feedbackDetailVO=null;
	ArrayList alFeedbackList=(ArrayList)request.getAttribute("alFeedbackList");
    String viewmode="disabled";
    boolean bViewmode=true;
    
 	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode="";
		bViewmode=false;
	}//end of if(TTKCommon.isAuthorized(request,"Edit"))
	pageContext.setAttribute("bViewmode",new Boolean(bViewmode));		
%>
<script language="javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<SCRIPT LANGUAGE="JavaScript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script language="javascript" src="/ttk/scripts/empanelment/edithospfeedback.js"></script>

	<!-- S T A R T : Content/Form Area -->
	<html:form action="/EditHospFeedbackAction" >
	<!-- S T A R T : Page Title -->
        <table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	      <tr>
	        <td width="51%"><bean:write name="frmEditFeedback" property="caption"/></td>
	        <td>&nbsp;</td>
	      </tr>
	    </table>
	<!-- E N D : Page Title -->
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
	<!-- S T A R T : Form Fields -->
	<fieldset>
    <legend>General</legend>
    <table class="formContainer"  border="0" cellspacing="0" cellpadding="0">
    <tr>
    	<td colspan="2">
    		<strong>Your feed-back will help us to provide better service .Please take the time to complete the Questionnaire that follows.</strong>
    	</td>
    </tr>
      <tr>
        <td width="30%" nowrap class="formLabel indentedLabels">Feedback Date: <span class="mandatorySymbol">*</span></td>
        <td width="70%" nowrap class="formLabel">
          <input name="feedbackdate" type="text" class="textBox textDate" maxlength="10" <%=viewmode%> onkeypress="javascript:blockEnterkey(event.srcElement);" value="<bean:write name="frmEditFeedback" property="feedbackdate" />"><logic:match name="bViewmode" value="false"><A NAME="CalendarObjectInvDate" ID="CalendarObjectInvDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectInvDate','forms[1].feedbackdate',document.forms[1].feedbackdate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absbottom"></a></logic:match>
        </td>
        </tr>
 		
	   <%
	   		// In Add mode we will be using this
	   		String strQuestionId="";
	   		String strAnswerId="";
	   		ArrayList alAnswerList = null;
        	if(alFeedbackList!=null && alFeedbackList.size()>0)
        	{
        		for(int i=0;i<alFeedbackList.size();i++)
        		{
        			feedbackDetailVO =(FeedbackDetailVO)alFeedbackList.get(i);
        %>
        			<tr>
        				<td width="30%" nowrap class="formLabel indentedLabels"><%=feedbackDetailVO.getQuestionDesc() %>	</td>
        				<td width="40%"  align="left">
        				<%
        				%>
        				<select name="answers" class="selectBox selectBoxMedium" <%=viewmode%>>
        				<option value="">Select From List</option>
 		<%
 						strQuestionId=feedbackDetailVO.getQuestionId();
        				alAnswerList=feedbackDetailVO.getAnswerList();
        				strAnswerId=feedbackDetailVO.getAnswerId();
        				if(alAnswerList!=null && alAnswerList.size()>0)
        				{
        					for(int j=0;j<alAnswerList.size();j++)
        					{
        						cacheObject =(CacheObject)alAnswerList.get(j);
        %>
        						<option value='<%=cacheObject.getCacheId()%>' <%=TTKCommon.isSelected(strAnswerId ,cacheObject.getCacheId())%>><%=cacheObject.getCacheDesc()%> </option>
        <%
        			    	}// end of for(int j=0;j<alAnswerList.size();j++)
       					}// end of if(alAnswerList!=null && alAnswerList.size()>0)
 		%>
 						</select>
        				</td>
        			</tr>
        			<INPUT TYPE="hidden" NAME="questions" VALUE="<%=strQuestionId %>">
 		<%
        		}// end of for(int i=0;i<alFeedbackList.size();i++)
        	}// end of if(alFeedbackList!=null && alFeedbackList.size()>0)
 		%>
      <tr>
        <td valign="top" nowrap class="formLabel indentedLabels">We welcome & value your Suggestion:</td>
        <td nowrap class="formLabel"><textarea name="suggestions" class="textBox textAreaLong" <%=viewmode%>><bean:write name="frmEditFeedback" property="suggestions" /></textarea></td>
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
				<button type="button" name="Button" accesskey="s" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSubmit();"><u>S</u>ave</button>&nbsp;
				<button type="button" name="Button2" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onReset();"><u>R</u>eset</button>&nbsp;
		  <%
		  		}//end of if(TTKCommon.isAuthorized(request,"Edit"))
		  %>
		  <logic:notEmpty name="frmEditFeedback" property="feedBackSeqId">
			  <%
			        if(TTKCommon.isAuthorized(request,"Delete"))
					{
		     %>
				      	<button type="button" name="Button" accesskey="d" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onDelete();"><u>D</u>elete</button>&nbsp;
		     <%
			    	}// end of if(TTKCommon.isAuthorized(request,"Delete"))
		     %>
	     </logic:notEmpty>
 	     	  <button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onCancel();"><u>C</u>lose</button>
	    </td>
	  </tr>
	</table>
	</div>
	<!-- E N D : Buttons -->
	<INPUT TYPE="hidden" NAME="feedBackSeqId" VALUE="<bean:write name="frmEditFeedback" property="feedBackSeqId" />">
    <INPUT TYPE="hidden" NAME="rownum" VALUE='<%= TTKCommon.checkNull(request.getParameter("rownum"))%>'>
    <INPUT TYPE="hidden" NAME="mode" VALUE="">
    <input type="hidden" name="systemDate" value="<%=TTKCommon.getFormattedDate(TTKCommon.getDate())%>">
	</html:form>
	<!-- E N D : Content/Form Area -->
