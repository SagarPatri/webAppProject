<%
/**
 * @ (#) preauthshortfalls.jsp 30th May 2015
 * Project      : ProjectX
 * File         : preauthshortfalls.jsp
 * Author       : Nagababu K
 * Company      : Vidal Corporation
 * Date Created : 30th May 2015
 *
 * @author       : Nagababu K
 * Modified by   :
 * Modified date :
 * Reason        :
 */
%>

<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/ttk-tags.tld" prefix="ttk" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.ttk.common.TTKCommon,com.ttk.common.security.Cache,com.ttk.common.PreAuthWebBoardHelper,com.ttk.common.ClaimsWebBoardHelper" %>


<SCRIPT type="text/javascript" SRC="/ttk/scripts/validation.js"></SCRIPT>
<script type="text/javascript" src="/ttk/scripts/calendar/calendar.js"></script>
<script type="text/javascript" src="/ttk/scripts/preauth/preauthshortfalls.js"></script>
<script type="text/javascript">
	//var JS_Focus_Disabled =true;
	
</script>
<%
	String claimTypeDesc = (String)request.getSession().getAttribute("claimTypeDesc");//shortfall phase1
	boolean viewmode=true;
	boolean viewmodestatus=true;
	
	if(TTKCommon.isAuthorized(request,"Edit"))
	{
		viewmode=false;
	}
	String ampm[] = {"AM","PM"};
	pageContext.setAttribute("ampm",ampm);
	
	if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
	{
		pageContext.setAttribute("ShortfallTypeID",Cache.getCacheObject("shortfallType"));
	}//end of if(TTKCommon.getActiveLink(request).equals("Pre-Authorization"))
	else if(TTKCommon.getActiveLink(request).equals("Claims"))
	{
		pageContext.setAttribute("ShortfallTypeID",Cache.getCacheObject("claimShortfallType"));
		//KOC1179
		pageContext.setAttribute("ShortfallTypeIDNew",Cache.getCacheObject("claimShortfallTypeNew"));		
		pageContext.setAttribute("ShortfallTemplateType",Cache.getCacheObject("claimShortfallTemplateType"));
		pageContext.setAttribute("ShortfallUnderClause",Cache.getCacheObject1("claimShortfallUnderClause",ClaimsWebBoardHelper.getClaimsSeqId(request)));
		pageContext.setAttribute("ShortfallTemplateNetworkType",Cache.getCacheObject("claimShortfallTemplateNetworkType"));//shortfall phase1
	}//end of else if(TTKCommon.getActiveLink(request).equals("Claims"))

	pageContext.setAttribute("VoucherStatusID",Cache.getCacheObject("voucherStatus"));
	pageContext.setAttribute("ShortFallStatusID",Cache.getCacheObject("shortfallStatus"));
	pageContext.setAttribute("Reason",Cache.getCacheObject("shortfallReason"));
%>
<!-- S T A R T : Content/Form Area -->
<html:form action="/PreAuthShortfallsGeneralAction.do">

<!-- S T A R T : Page Title -->
<table align="center" class="pageTitle" border="0" cellspacing="0" cellpadding="0">
	<tr>
   		<td width="57%"><bean:write name="frmShortFall" property="caption"/></td>
     <logic:notEmpty  name="frmShortFall" property="shortfallSeqID">
		<td width="43%" align="right" class="webBoard">&nbsp;<%@ include file="/ttk/common/toolbar.jsp" %></td>
      </logic:notEmpty>
   	</tr>
</table>


<!-- E N D : Page Title -->

<!-- S T A R T : Form Fields -->
<div class="contentArea" id="contentArea">
<html:errors/>
<logic:notEmpty name="errorMsg" scope="request">
    <table align="center" class="errorContainer"  border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/ttk/images/ErrorIcon.gif" alt="Error" width="16" height="16" align="absmiddle" >&nbsp;
          <bean:write name="errorMsg" scope="request" />
          </td>
      </tr>
    </table>
   </logic:notEmpty>
<!-- S T A R T : Success Box -->
<bean:write name="frmShortFall" property="claimTypeDesc" />
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

		<fieldset><legend>General</legend>
		<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0">
		<tr>
	    	<td height="20" class="formLabel">Shortfall No.:</td>
	    	<td class="textLabelBold"><bean:write name="frmShortFall" property="shortfallNo"/></td>
	    	<td nowrap class="formLabel">&nbsp;</td>
	    	<td nowrap class="textLabelBold">&nbsp;</td>
	    	<html:hidden property="shortfallTypeID"  value="MDI"/>
	  	</tr>
	  	
	  	
	  	<!--hide the this details when fresh case  
	  	</table>
	  	<logic:equal value="Shortfall" scope="session" name="screenPath">
	  	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" style="display:;">			  	
	  	</logic:equal>
	  	<logic:notEqual value="Shortfall" scope="session" name="screenPath">
	  	<table align="center" class="formContainer" border="0" cellspacing="0" cellpadding="0" style="display:none;">		  
	  	</logic:notEqual>
	  	-->
	  	
	  	<%-- <tr>
	  		<td class="formLabel">Status: <span class="mandatorySymbol">*</span></td>
	    	<td class="textLabelBold">
				<html:select property="statusTypeID"  styleClass="selectBox selectBoxMedium">
	  				<html:options collection="ShortFallStatusID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
	      	</td>	      
			<td nowrap class="formLabel">&nbsp;</td>
	    	<td nowrap class="textLabelBold">&nbsp;</td>
		</tr> --%>	
		<tr>
	  		<td class="formLabel">Status: <span class="mandatorySymbol">*</span></td>
	    	<td class="textLabelBold">
	    	<logic:empty name="frmShortFall" property="shortfallSeqID">
				<html:select property="statusTypeID"  styleClass="selectBox selectBoxMedium" onchange="showhideReasonAuth(this);" disabled="<%= viewmode %>">
	  				<html:option value="OPN">Open</html:option>	  				
		    	</html:select>
		    	</logic:empty>
	    	    <logic:notEmpty name="frmShortFall" property="shortfallSeqID">
				<html:select property="statusTypeID"  styleClass="selectBox selectBoxMedium" onchange=""><!--showhideReasonAuth(this);-->
	  				<html:options collection="ShortFallStatusID"  property="cacheId" labelProperty="cacheDesc"/>
		    	</html:select>
		    	</logic:notEmpty>
	      	</td>
	      	<td nowrap class="formLabel">&nbsp;</td>
	    	<td nowrap class="textLabelBold">&nbsp;</td>
		</tr>
		<logic:notEmpty name="frmShortFall" property="shortfallSeqID">
<!-- 		<tr> -->
<!-- 	      	<td nowrap class="formLabel">				 -->
<!-- 			    	Received Date / Time: <span class="mandatorySymbol">*</span>		    	 -->
<!-- 		    </td> -->
<!-- 		    <td class="textLabelBold" >			 -->
<%-- 		    	    <html:text property="receivedDate" styleClass="textBox textDate" maxlength="10" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/> --%>
<!-- 		    		<A NAME="calRecvdDate" ID="calRecvdDate" HREF="#" onClick="javascript:show_calendar('calRecvdDate','frmShortFall.receivedDate',document.frmShortFall.receivedDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar"  width="24" height="17" border="0" align="absmiddle"></a>&nbsp; -->
<%-- 					<html:text property="receivedTime"  styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;<html:select property="receivedDay" name="frmShortFall" styleClass="selectBox" disabled="<%= viewmode %>"><html:options name="ampm" labelName="ampm"/></html:select> --%>
<!-- 			</td> -->
<!-- 			<td nowrap class="formLabel">&nbsp;</td> -->
<!-- 	    	<td nowrap class="textLabelBold">&nbsp;</td> -->
<!-- 		</tr> -->
	  	
	  	<tr>
		  		<td class="formLabel">Reason: <span class="mandatorySymbol">*</span></td>
			  	<td colspan="3">
					<html:select property="reasonTypeID"  styleClass="selectBox selectBoxMedium">
						<html:option value="">Select from list</html:option>
		  				<html:options collection="Reason"  property="cacheId" labelProperty="cacheDesc"/>
			    	</html:select>
				</td>
		  	<td nowrap class="formLabel">&nbsp;</td>
	    	<td nowrap class="textLabelBold">&nbsp;</td>
		<tr>  
		</logic:notEmpty>
		
		<tr>
	      	<td nowrap class="formLabel">Remarks:</td>
	      	<td colspan="3" valign="bottom" nowrap class="formLabel">
	      		<html:textarea property="remarks" styleClass="textBox textAreaLong" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>
	      	</td>
		</tr>
		<tr>
			<%-- <td class="formLabel">New Correspondence:</td>
    	    <td class="textLabelBold"><span class="formLabel">
    	    	<html:checkbox styleClass="margin-left:-4px;" property="correspondenceYN" value="Y" disabled="<%=viewmode%>" onclick="javascript:setCorrespondenceDate()"/></span>
    	    </td> --%>
    	     <td class="formLabel">Correspond Date / Time:</td>
			    <td class="textLabel">
			    <table cellpadding="1" cellspacing="0">
			     <tr>
			      <td><html:text property="correspondenceDate" styleClass="textBox textDate" maxlength="10" disabled="<%= viewmode %>" readonly="<%= viewmode %>"/><A NAME="CalendarObjectPARDate" ID="CalendarObjectPARDate" HREF="#" onClick="javascript:show_calendar('CalendarObjectPARDate','frmShortFall.correspondenceDate',document.frmShortFall.correspondenceDate.value,'',event,148,178);return false;" onMouseOver="window.status='Calendar';return true;" onMouseOut="window.status='';return true;"><img src="/ttk/images/CalendarIcon.gif" alt="Calendar" name="empDate" width="24" height="17" border="0" align="absmiddle"></a>&nbsp;</td>
			      <td><html:text property="correspondenceTime"  styleClass="textBox textTime" maxlength="5" disabled="<%=viewmode%>" readonly="<%=viewmode%>"/>&nbsp;</td>
			      <td><html:select property="correspondenceDay" name="frmShortFall" styleClass="selectBox" disabled="<%=viewmode%>"><html:options name="ampm" labelName="ampm"/></html:select></td>
			     </tr>
			    </table>
			    	
			 </td>
			 <td nowrap class="formLabel">&nbsp;</td>
	    	<td nowrap class="textLabelBold">&nbsp;</td>
		</tr>
		<tr>
			<td class="formLabel">Correspondence Count: </td>
				  <td class="textLabel">
				  	<html:text property="correspondenceCount" styleClass="textBox textBoxSmall textBoxDisabled" maxlength="13" disabled="<%= viewmode %>" readonly="true"/>
			</td>
		</tr>
		</div>
		
		</table>
	</fieldset>	
	
<ttk:ShortFallQueries flow="PAT"/>
		

<!-- E N D : Form Fields -->

<!-- S T A R T : Buttons -->
	<table align="center" class="buttonsContainer"  border="0" cellspacing="0" cellpadding="0">
    	<tr>
        	<td width="100%" align="center">
	       	    <%
    				if(TTKCommon.isAuthorized(request,"Edit"))
					{
			    %>
			    <logic:notEmpty name="frmShortFall" property="shortfallSeqID">
			     <logic:equal name="frmShortFall" property="recievedStatus" value="N">
			    <button type="button" name="Button" accesskey="g" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onGenerateShortFall();"><u>V</u>iew Letter</button>&nbsp;
				</logic:equal>
				</logic:notEmpty>				
					<%-- <logic:notEmpty name="frmShortFall" property="shortfallSeqID">
					<button type="button" name="Button" accesskey="n" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onResendEmail();"><u>S</u>ave &Rese<u>n</u>d</button>&nbsp;
					</logic:notEmpty> --%> 
					<logic:notEqual name="frmShortFall" property="statusTypeID" value="CLS">
				  <button type="button" name="Button" id="processBtn" accesskey="s" style="display:none;" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();">Process Pre-Auth</button>&nbsp;      
		          <button type="button" name="Button" accesskey="s" id="saveBtn" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:onSave();"><u>S</u>ave & S<u>e</u>nd</button>&nbsp;
		    	</logic:notEqual>
						<!-- <button type="button" name="Button" accesskey="r" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick="javascript:Reset();"><u>R</u>eset</button>&nbsp; -->
                    <logic:notMatch name="frmShortFall" property="editYN" value="Y">
		    				<SCRIPT type="text/javascript">
								var TC_Disabled = true; //to avoid the alert message on change of form elements
							</SCRIPT>
		   	 			</logic:notMatch>
	    		<%
	    			}
	    		%>
		    		<button type="button" name="Button" accesskey="c" class="buttons" onMouseout="this.className='buttons'" onMouseover="this.className='buttons buttonsHover'" onClick='javascript:closeShortfalls("<%=session.getAttribute("closeShortfalls")%>");'><u>C</u>lose</button>&nbsp;
		   </td>
		</tr>
	</table>
<!-- E N D : Buttons -->
<logic:equal value="goGeneral" name="closeShortfalls" scope="session">
<script>
bAction=false;
</script>
</logic:equal>

</div>
<INPUT TYPE="hidden" NAME="mode" VALUE=""/>
<html:hidden  property="shortFallQueryIds" styleId="shortFallQueryIds"/>
<html:hidden property="reasonYN"/>
<html:hidden property="editYN"/>
<html:hidden property="caption"/>
<html:hidden property="shortfallSeqID"/>
<html:hidden property="shortfallNo"/>
<html:hidden property="shortfalltype"/>
<html:hidden property="refNbr"/>
<html:hidden property="DMSRefID"/>
<html:hidden property="hiddenDate"/>
<html:hidden property="hiddenTime"/>
<html:hidden property="hiddenTimeStamp"/>
<html:hidden property="displayclaims"/>
<html:hidden property="saveYes"/>
<html:hidden property="saveYes"/>
<html:hidden property="currentDate"/>
<!-- 3 shortfall buttons merge -->

<input type="hidden" name="leftlink">
<input type="hidden" name="sublink">
<input type="hidden" name="tab">
<html:hidden  styleId="preAuthNo" property="preAuthNo" name="frmShortFall"/>
<html:hidden  styleId="preAuthSeqID" property="preAuthSeqID" name="frmShortFall"/>
<html:hidden  styleId="preauthStatus" property="preauthStatus" name="frmShortFall"/>
<input type="hidden" name="authType" value="PAT">
<input type="hidden" id="type" name="shortfallTypeID" value="<bean:write name="frmShortFall" property="shortfallTypeID"/>"/>
<input type="hidden" name="cignaValueYN" value="<bean:write name="frmShortFall" property="cignaYN"/>"/>
<input type="hidden" name="memberClaimYN" value="<bean:write name="frmShortFall" property="memberClaimYN"/>"/>
<input type="hidden" id="shortfallTemplateType" name="shortfallTemplateType" value="<bean:write name="frmShortFall" property="shortfallTemplateType"/>"/>
<INPUT TYPE="hidden" NAME="shortFall" VALUE="SRT"/>
<input type="hidden" id="ShortfallTemplateNetworkType" name="ShortfallTemplateNetworkType" value="<bean:write name="frmShortFall" property="ShortfallTemplateNetworkType"/>"/><!-- shortfall phase1 -->
<input type="hidden" name="reforward">
<input type="hidden" name="shortfallClosedYN">
<%-- 
<logic:notEmpty name="frmShortFall" property="frmChanged">
	<script> //ClientReset=false;//TC_PageDataChanged=true;</script>
</logic:notEmpty> --%>
<!-- 3 shortfall buttons merge -->
<%-- <logic:match name="frmShortFall" property="saveYes" value="Y">
	<logic:match name="frmShortFall" property="statusTypeID" value="OPN">
		<script language="javascript">
				onSendLoad();
		</script>
	</logic:match>
</logic:match> --%>
<!-- 3 shortfall buttons merge -->

</html:form>
<!-- E N D : Content/Form Area -->